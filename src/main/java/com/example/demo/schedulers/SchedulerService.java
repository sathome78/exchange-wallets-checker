package com.example.demo.schedulers;

import com.example.demo.domain.Coin;
import com.example.demo.domain.PriceStatus;
import com.example.demo.domain.dto.CoinWrapper;
import com.example.demo.domain.enums.CoinType;
import com.example.demo.repository.CoinRepository;
import com.example.demo.schedulers.coinprocessor.CoinProcessor;
import com.example.demo.util.NumberFormatter;
import javafx.util.Pair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.example.demo.schedulers.NotificatorService.getCurrentDate;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.stream.Collectors.toList;

@Service
public class SchedulerService {

    @Autowired
    CoinRepository coinRepository;

    @Autowired
    Map<String, NotificatorService> notificatorServiceMap;

    @Autowired
    Map<CoinType, CoinProcessor> processorMap;

    @Autowired
    Map<PriceStatus, String> templatesMap;

    @Autowired
    private Client client;

    @Value("${currency.usd.api}")
    private String currencyUsd;


    @Scheduled(fixedDelay = 30000, initialDelay = 0)
    public void allCoins() {
        List<CoinWrapper> collect = coinRepository.findByEnableTrue().parallelStream().map(coin -> processorMap.get(coin.getCoinType()).process(coin)).collect(toList());
        collect.forEach(this::process);
    }

    public void process(CoinWrapper coinWrapper) {
        if (coinWrapper.getCoin().isEnable())
            process(coinWrapper.getCoin(), coinWrapper.getActualBalance());
    }

    public void process(Coin coin, BigDecimal actualBalance) {
        try {
            if (actualBalance.compareTo(coin.getCurrentAmount()) == 0) {
                return;
            }
            coin.setCurrentAmount(actualBalance);
            check(coin, actualBalance);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Coin id " + coin.getName());
        }
    }

    public void check(Coin btcCoin, BigDecimal newAmount) {
        Pair<PriceStatus, Boolean> status = getStatus(btcCoin, newAmount);
        if (status.getValue()) {
            String template = renderTemplate(templatesMap.get(status.getKey()), btcCoin);
            notificatorServiceMap.forEach((s, notificatorService) -> notificatorService.notificate(template));
        }
        coinRepository.save(btcCoin);
    }

    @Scheduled(fixedDelay = 30000, initialDelay = 0)
    public void updateUSD() {
        Response response = client.target(currencyUsd).request(MediaType.APPLICATION_JSON_TYPE).get();
        String stringResponse = response.readEntity(String.class);
        JSONObject resp = new JSONObject(stringResponse);
        List<Coin> all = coinRepository.findAll();
        all.forEach(coin -> convert(resp, coin));
        coinRepository.saveAll(all);
    }

    private void convert(JSONObject resp, Coin coin) {
        try {
            double usdRate = resp.getJSONObject(coin.getName()).getDouble("usd_rate");
            coin.updateUSDData(usdRate);
        } catch (RuntimeException ignored) {
        }
    }

    private String renderTemplate(String template, Coin coin) {
        return format(
                template,
                coin.getName(),
                getCurrentDate(),
                valueOf(NumberFormatter.format(coin.getCurrentAmount())),
                valueOf(NumberFormatter.format(coin.getAmountInUSD())),
                valueOf(NumberFormatter.format(coin.getMinAmount())),
                valueOf(NumberFormatter.format(coin.getMaxAmount())),
                valueOf(NumberFormatter.format(coin.getMinAmountInUSD())),
                valueOf(NumberFormatter.format(coin.getMaxAmountInUSD()))
        );
    }

    private Pair<PriceStatus, Boolean> getStatus(Coin btcCoin, BigDecimal newAmount) {
        PriceStatus priceStatus = null;
        boolean sendNotification = false;
        if (newAmount.compareTo(btcCoin.getMaxAmount()) > 0) {
            priceStatus = PriceStatus.ABOVE;
            sendNotification = true;
        }

        if (newAmount.compareTo(btcCoin.getMinAmount()) < 0 && !btcCoin.isLowThanMinAmountNotified()) {
            btcCoin.setLowThanMinAmountNotified(true);
            priceStatus = PriceStatus.LOW;
            sendNotification = true;
        }

        if (newAmount.compareTo(btcCoin.getMinAmount()) > 0 && newAmount.compareTo(btcCoin.getMaxAmount()) < 0 && (btcCoin.isLowThanMinAmountNotified() || btcCoin.getPriceStatus() == PriceStatus.ABOVE)) {
            btcCoin.setLowThanMinAmountNotified(false);
            priceStatus = PriceStatus.NORMAL;
            sendNotification = true;
        }

        btcCoin.setPriceStatus(priceStatus);

        return new Pair<>(priceStatus, sendNotification);
    }


}
