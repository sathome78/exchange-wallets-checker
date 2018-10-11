package com.example.demo.schedulers;

import com.example.demo.domain.Coin;
import com.example.demo.domain.PriceStatus;
import com.example.demo.repository.CoinRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.example.demo.schedulers.NotificatorService.ABOVE_MAX_LIMIT;
import static com.example.demo.schedulers.NotificatorService.LOW_THAN_MIN_AMOUNT;
import static com.example.demo.schedulers.NotificatorService.PERMISSIBLE_RANGE;

@Service
public class SchedulerService {

    @Autowired
    Client client;

    @Autowired
    CoinRepository coinRepository;

    @Autowired
    Map<String, NotificatorService> notificatorServiceMap;


    @Scheduled(fixedDelay = 30000, initialDelay = 0)
    public void allCoins() {
        List<Coin> all = coinRepository.findAll();
        all.forEach(this::process);
    }

    public void process(Coin coin) {
        try {
            Response response = client.
                    target(String.format("https://exrates.me/getWalletBalanceByCurrencyName?currency=%s&token=ZXzG8z13nApRXDzvOv7hU41kYHAJSLET", coin.getName())).
                    request(MediaType.APPLICATION_JSON_TYPE).get();

            String s = response.readEntity(String.class);

            JSONObject jsonObject = new JSONObject(s);
            String balance = jsonObject.getString(coin.getDetailName());
            BigDecimal newAmount = new BigDecimal(balance);
            if (newAmount.compareTo(coin.getCurrentAmount()) == 0) {
                return;
            }


            coin.setCurrentAmount(newAmount);

            check(coin, newAmount);
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


}
