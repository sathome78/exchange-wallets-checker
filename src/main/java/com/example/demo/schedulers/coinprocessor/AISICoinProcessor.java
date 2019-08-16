package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Service("aisiCoinProcessor")
public class AISICoinProcessor implements CoinProcessor {

    @Value("${aisi.endpoint.basic}")
    private String aisiEndpoint;

    @Autowired
    Client client;

    @Override
    public CoinWrapper process(Coin coin) {
        return CoinWrapper.builder().coin(coin).actualBalance(getAmount()).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        return getAmount();
    }

    private BigDecimal getAmount() {
        String response = client.target(aisiEndpoint).request(MediaType.APPLICATION_JSON_TYPE).get().readEntity(String.class);
        return new JSONObject(response).getBigDecimal("Balance");
    }

}