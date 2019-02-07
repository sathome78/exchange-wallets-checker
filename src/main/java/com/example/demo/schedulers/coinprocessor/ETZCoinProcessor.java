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

@Service("etzCoinProcessor")
public class ETZCoinProcessor implements CoinProcessor {

    @Value("${etz.endpoint}")
    private String endpoint;

    @Autowired
    private Client client;

    @Override
    public CoinWrapper process(Coin coin) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("addr", coin.getEthTokenContract());
        requestBody.put("options", singletonList("balance"));
        Response post = client.target(endpoint).request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(requestBody, MediaType.APPLICATION_JSON_TYPE));
        String balance = new JSONObject(post.readEntity(String.class)).getString("balance");
        return CoinWrapper.builder().coin(coin).actualBalance(new BigDecimal(balance)).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("addr", wallet);
        requestBody.put("options", singletonList("balance"));
        Response post = client.target(endpoint).request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(requestBody, MediaType.APPLICATION_JSON_TYPE));
        String balance = new JSONObject(post.readEntity(String.class)).getString("balance");
        return new BigDecimal(balance);
    }
}
