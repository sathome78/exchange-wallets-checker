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

    @Override
    public CoinWrapper process(Coin coin) {
        final BigDecimal actualBalance = getBalance(coin, coin.getCoinAddress());

        return CoinWrapper.builder().actualBalance(actualBalance).coin(coin).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String coinAddress) {
        Response response = client.
                target(String.format("http://exad.service/getWalletBalanceByCurrencyName?currency=EDR&token=ZXzG8z13nApRXDzvOv7hU41kYHAJSLET&address=%s", coinAddress)).
                request(MediaType.APPLICATION_JSON_TYPE).get();

        String s = response.readEntity(String.class);

        JSONObject jsonObject = new JSONObject(s);
        String balance = jsonObject.getString("EDC");

        return new BigDecimal(balance);
    }
}