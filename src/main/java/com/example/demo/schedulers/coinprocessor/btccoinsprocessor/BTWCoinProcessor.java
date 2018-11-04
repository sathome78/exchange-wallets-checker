package com.example.demo.schedulers.coinprocessor.btccoinsprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import com.example.demo.schedulers.coinprocessor.CoinProcessor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("btwCoinProcessor")
public class BTWCoinProcessor implements CoinProcessor {


    @Autowired
    private Client client;

    @Value("${btw.endpoint}")
    private String btwEndpoint;

    @Value("${btw.endpoint.basic}")
    private String btwEndpointBasic;

    public CoinWrapper process(Coin coin) {
        Response response = client.target(btwEndpoint).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        String balance = new JSONObject(s).getString("balance");
        BigDecimal divide = new BigDecimal(balance).divide(new BigDecimal(100000000));
        return CoinWrapper.builder().coin(coin).actualBalance(divide).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.target(btwEndpointBasic + wallet).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        String balance = new JSONObject(s).getString("balance");
        return new BigDecimal(balance).divide(new BigDecimal(100000000));
    }

}
