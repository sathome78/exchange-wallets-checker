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

@Service("btcpCoinProcessor")
public class BTCPCoinProcessor implements BTCGenericProcessor {

    @Value("${btc.btcp.coin}")
    private String coinBaseURL;

    @Autowired
    private Client client;

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.target(coinBaseURL + wallet).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        return new JSONObject(s).getBigDecimal("balance");
    }
}
