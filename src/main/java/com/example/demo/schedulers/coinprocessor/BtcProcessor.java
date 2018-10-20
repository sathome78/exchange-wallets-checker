package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Repository
public class BtcProcessor implements CoinProcessor {

    @Autowired
    Client client;

    public CoinWrapper process(Coin coin) {
        Response response = client.
                target(String.format("https://exrates.me/getWalletBalanceByCurrencyName?currency=%s&token=ZXzG8z13nApRXDzvOv7hU41kYHAJSLET", coin.getName())).
                request(MediaType.APPLICATION_JSON_TYPE).get();

        String s = response.readEntity(String.class);

        JSONObject jsonObject = new JSONObject(s);
        String balance = jsonObject.getString(coin.getDetailName());

        return CoinWrapper.builder().actualBalance(new BigDecimal(balance)).coin(coin).build();
    }
}
