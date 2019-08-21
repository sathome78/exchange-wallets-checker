package com.example.demo.schedulers.coinprocessor.btccoinsprocessor;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("btcTargetProcessor")
public class BTCTargetProcessor implements BTCGenericProcessor {

    @Value("${btc.btc.coin}")
    private String coinBaseURL;

    @Autowired
    private Client client;

    @Override
    public BigDecimal getBalance(String coinAddress) {
        Response response = client.target(String.format(coinBaseURL, coinAddress)).request(MediaType.APPLICATION_JSON_TYPE).get();

        String s = response.readEntity(String.class);
        BigDecimal bigDecimal = new JSONObject(s).
                getJSONObject("data").
                getJSONObject("bitcoin_addresses").
                getJSONObject("data").
                getJSONObject(coinAddress).
                getJSONObject("address").
                getBigDecimal("balance");

        return bigDecimal.divide(new BigDecimal(Math.pow(10, 8)));
    }
}