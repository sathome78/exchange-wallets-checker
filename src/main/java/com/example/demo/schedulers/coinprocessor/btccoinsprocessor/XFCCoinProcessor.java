package com.example.demo.schedulers.coinprocessor.btccoinsprocessor;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("xfcCoinProcessor")
public class XFCCoinProcessor implements BTCGenericProcessor {

    @Value("${btc.xfc.coin}")
    private String xfcAPIBaseURL;

    @Autowired
    private Client client;

    @Override
    public BigDecimal getBalance(String coinAddress) {
        Response response = client.target(String.format(xfcAPIBaseURL, coinAddress)).request(MediaType.APPLICATION_JSON_TYPE).get();

        String s = response.readEntity(String.class);

        return new JSONObject(s).getBigDecimal("balance");
    }
}