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

@Service("gasProcessor")
public class GASProcessor implements CoinProcessor {

    @Value("${gas.endpoint.basic}")
    private String gasEndpoint;

    @Autowired
    Client client;

    @Override
    public CoinWrapper process(Coin coin) {
        return CoinWrapper.builder().coin(coin).actualBalance(getAmount(coin.getCoinAddress())).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        return getAmount(wallet);
    }

    private BigDecimal getAmount(String address) {
        String response = client.target(gasEndpoint + address).request(MediaType.APPLICATION_JSON_TYPE).get().readEntity(String.class);
        return new JSONObject(response).getJSONArray("balance").getJSONObject(5).getBigDecimal("amount");
    }

}
