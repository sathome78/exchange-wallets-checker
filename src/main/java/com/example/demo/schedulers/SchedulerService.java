package com.example.demo.schedulers;

import com.example.demo.domain.Coin;
import com.example.demo.domain.PriceStatus;
import com.example.demo.domain.dto.CoinWrapper;
import com.example.demo.domain.enums.CoinType;
import com.example.demo.repository.CoinRepository;
import com.example.demo.schedulers.coinprocessor.CoinProcessor;
import com.example.demo.schedulers.fiatprocessor.FiatProcessor;
import com.example.demo.util.NumberFormatter;
import javafx.util.Pair;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.example.demo.schedulers.NotificatorService.getCurrentDate;
import static java.lang.String.format;
import static java.lang.String.valueOf;

@Service
@Log4j2
public class SchedulerService {

    private final CoinRepository coinRepository;
    private final Map<String, NotificatorService> notificatorServiceMap;
    private final Map<CoinType, CoinProcessor> processorMap;
    private final Map<PriceStatus, String> templatesMap;
    private final Client client;
    private final FiatProcessor payeerProcessor;
    private final FiatProcessor advCashProcessor;



    @Value("${currency.usd.api}")
    private String currencyUsd;

    @Autowired
    public SchedulerService(CoinRepository coinRepository, Map<String, NotificatorService> notificatorServiceMap, Map<CoinType, CoinProcessor> processorMap, Map<PriceStatus, String> templatesMap, Client client, FiatProcessor advCashProcessor, FiatProcessor payeerProcessor) {
        this.coinRepository = coinRepository;
        this.notificatorServiceMap = notificatorServiceMap;
        this.processorMap = processorMap;
        this.templatesMap = templatesMap;
        this.client = client;
        this.advCashProcessor = advCashProcessor;
        this.payeerProcessor = payeerProcessor;
    }


    @Scheduled(fixedDelay = 1800000, initialDelay = 0)
    public void allCoins() throws InterruptedException {
        coinRepository.findCoinTypes().forEach(element->{
            log.info("Send request with coinType " + element);
            client.target("http://localhost:8080/process/" + element).request(MediaType.APPLICATION_JSON_TYPE).get();
            log.info("Finish request with coinType  " + element);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Scheduled(fixedDelay = 1800000, initialDelay = 10000)
    public void processAdvcash() {
        payeerProcessor.process();
    }

    @Scheduled(fixedDelay = 1800000, initialDelay = 10000)
    public void processPayeerMoney() {
        advCashProcessor.process();
    }

    public CoinWrapper process(Coin coin) {
        try {
            return processorMap.get(coin.getCoinType()).process(coin);
        } catch (Exception e) {
            log.warn("Unable to retrieve balance of token " + coin.getName());
            return CoinWrapper.builder().coin(coin).actualBalance(coin.getCurrentAmount()).build();
        }
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
            coin.setAmountInUSD(actualBalance.multiply(coin.getRateToUSD()));
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
        btcCoin.setDate(new Date());
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
        PriceStatus priceStatus = btcCoin.getPriceStatus();
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
