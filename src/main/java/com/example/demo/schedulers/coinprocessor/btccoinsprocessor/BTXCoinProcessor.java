package com.example.demo.schedulers.coinprocessor.btccoinsprocessor;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;


@Service("btxCoinProcessor")
public class BTXCoinProcessor implements BTCGenericProcessor {

    @Value("{btc.btx.coin}")
    private String btcBaseAddress;

    @Autowired
    private Client client;

    @Override
    public BigDecimal getBalance(String coinAddress) {
        Response response = client.target(String.format(btcBaseAddress, coinAddress)).request(MediaType.APPLICATION_JSON_TYPE).get();

        String s = response.readEntity(String.class);

        return new JSONObject(s).getBigDecimal("balance");
    }
}