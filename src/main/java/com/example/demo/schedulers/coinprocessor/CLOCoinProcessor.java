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

    private String cloEndpoint = "https://callistoexplorer.com/web3relay";

    @Autowired
    Client client;

    @Override
    public CoinWrapper process(Coin coin) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("addr", coin.getCoinAddress());
        requestBody.put("options", singletonList("balance"));
        Response post = client.target(cloEndpoint).request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(requestBody, MediaType.APPLICATION_JSON_TYPE));
        String s = post.readEntity(String.class);
        String balance = new JSONObject(s).getString("balance");
        return CoinWrapper.builder().coin(coin).actualBalance(new BigDecimal(balance)).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("addr", wallet);
        requestBody.put("options", singletonList("balance"));
        Response post = client.target(cloEndpoint).request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(requestBody, MediaType.APPLICATION_JSON_TYPE));
        String s = post.readEntity(String.class);
        String balance = new JSONObject(s).getString("balance");
        return new BigDecimal(balance);
    }

}

