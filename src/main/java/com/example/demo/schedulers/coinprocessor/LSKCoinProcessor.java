package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("lskCoinProcessor")
public class LSKCoinProcessor implements CoinProcessor {

    @Autowired
    private Client client;

    @Value("${lsk.endpoint}")
    private String lskEndpoint;

    @Value("${lsk.endpoint.basic}")
    private String lskEndpointBasic;

    public CoinWrapper process(Coin coin) {
        Response response = client.target(lskEndpoint).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        String balance = new JSONObject(s).getString("balance");
        BigDecimal divide = new BigDecimal(balance).divide(new BigDecimal(Math.pow(10,8)));
        return CoinWrapper.builder().coin(coin).actualBalance(divide).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.target(lskEndpointBasic + wallet).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        String balance = new JSONObject(s).getString("balance");
        return new BigDecimal(balance).divide(new BigDecimal(Math.pow(10,8)));
    }
}
