package com.example.demo.schedulers.coinprocessor.btccoinsprocessor;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("crypCoinProcessor")
public class CRYPCoinProcessor implements BTCGenericProcessor {

    @Value("${btc.cryp.coin}")
    private String baseURL;

    private final Client client;

    @Autowired
    public CRYPCoinProcessor(Client client) {
        this.client = client;
    }

    @Override
    public BigDecimal getBalance(String coinAddress) {
        Response response = client.target(String.format(baseURL, coinAddress)).request(MediaType.APPLICATION_JSON_TYPE).get();

        String s = response.readEntity(String.class);

        return new JSONObject(s).getBigDecimal("balance");
    }
}