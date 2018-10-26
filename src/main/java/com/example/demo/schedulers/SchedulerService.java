package com.example.demo.schedulers;

import com.example.demo.domain.Coin;
import com.example.demo.domain.PriceStatus;
import com.example.demo.domain.dto.CoinWrapper;
import com.example.demo.domain.enums.CoinType;
import com.example.demo.repository.CoinRepository;
import com.example.demo.schedulers.coinprocessor.CoinProcessor;
import org.glassfish.jersey.client.ClientBackgroundScheduler;
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

import static com.example.demo.schedulers.NotificatorService.ABOVE_MAX_LIMIT;
import static com.example.demo.schedulers.NotificatorService.LOW_THAN_MIN_AMOUNT;
import static com.example.demo.schedulers.NotificatorService.PERMISSIBLE_RANGE;
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
    private Client client;

    @Value("${currency.usd.api}")
    private String currencyUsd;


    @Scheduled(fixedDelay = 30000, initialDelay = 0)
    public void allCoins() {
        List<CoinWrapper> collect = coinRepository.findByEnableTrue().stream().map(coin -> processorMap.get(coin.getCoinType()).process(coin)).collect(toList());
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
        }
    }

    public void check(Coin btcCoin, BigDecimal newAmount) {

        if (newAmount.compareTo(btcCoin.getMaxAmount()) > 0) {
            notificatorServiceMap.forEach((s, notificatorService) -> notificatorService.notificate(ABOVE_MAX_LIMIT, btcCoin));
            btcCoin.setPriceStatus(PriceStatus.ABOVE);
        }

        if (newAmount.compareTo(btcCoin.getMinAmount()) < 0 && !btcCoin.isLowThanMinAmountNotified()) {
            notificatorServiceMap.forEach((s, notificatorService) -> notificatorService.notificate(LOW_THAN_MIN_AMOUNT, btcCoin));
            btcCoin.setLowThanMinAmountNotified(true);
            btcCoin.setPriceStatus(PriceStatus.LOW);
        }

        if (newAmount.compareTo(btcCoin.getMinAmount()) > 0 && newAmount.compareTo(btcCoin.getMaxAmount()) < 0 && (btcCoin.isLowThanMinAmountNotified() || btcCoin.getPriceStatus() == PriceStatus.ABOVE)) {
            notificatorServiceMap.forEach((s, notificatorService) -> notificatorService.notificate(PERMISSIBLE_RANGE, btcCoin));
            btcCoin.setLowThanMinAmountNotified(false);
            btcCoin.setPriceStatus(PriceStatus.NORMAL);
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
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }


}
