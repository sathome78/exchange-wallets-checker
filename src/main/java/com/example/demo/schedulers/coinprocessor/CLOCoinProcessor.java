package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;

@Service("cloProcessor")
public class CLOCoinProcessor implements CoinProcessor {

    @Value("${clo.endpoint.basic}")
    private String cloEndpoint;

    @Autowired
    private Client client;

    @Override
    public CoinWrapper process(Coin coin) {
        final BigDecimal actualBalance = getBalance(coin, coin.getCoinAddress());

        return CoinWrapper.builder().coin(coin).actualBalance(actualBalance).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String coinAddress) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("addr", coinAddress);
        requestBody.put("options", singletonList("balance"));
        Response post = client.target(cloEndpoint).request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(requestBody, MediaType.APPLICATION_JSON_TYPE));

        String s = post.readEntity(String.class);
        String balance = new JSONObject(s).getString("balance");

        return new BigDecimal(balance);
    }
}