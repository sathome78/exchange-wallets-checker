package com.example.demo.schedulers.coinprocessor.btccoinsprocessor;

import com.example.demo.domain.Coin;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("bcxCoinProcessor")
public class BCXCoinProcessor implements BTCProcessor {

    @Value("${btc.bcx.coin}")
    private String bcxBaseUrl;

    @Autowired
    private Client client;

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.target(String.format(bcxBaseUrl, wallet)).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        JSONObject jsonObject = new JSONObject(s);
        return jsonObject.getBigDecimal("balance");
    }
}
