package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service
public class EdrCoinProcessor implements CoinProcessor {

    @Autowired
    private Client client;


    public CoinWrapper process(Coin coin) {
        Response response = client.
                target(String.format("http://exad.service/getWalletBalanceByCurrencyName?currency=EDR&token=ZXzG8z13nApRXDzvOv7hU41kYHAJSLET&address=" + coin.getCoinAddress())).
                request(MediaType.APPLICATION_JSON_TYPE).get();

        String s = response.readEntity(String.class);

        JSONObject jsonObject = new JSONObject(s);
        String balance = jsonObject.getString("EDR");

        return CoinWrapper.builder().actualBalance(new BigDecimal(balance)).coin(coin).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.
                target(String.format("http://exad.service/getWalletBalanceByCurrencyName?currency=EDR&token=ZXzG8z13nApRXDzvOv7hU41kYHAJSLET&address=" + wallet)).
                request(MediaType.APPLICATION_JSON_TYPE).get();

        String s = response.readEntity(String.class);

        JSONObject jsonObject = new JSONObject(s);
        String balance = jsonObject.getString("EDR");

        return new BigDecimal(balance);
    }
}
