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

@Service
public class WavesCoinProcessor implements CoinProcessor {


    @Value("${waves.endpoint}")
    private String endpoint;

    @Value("${waves.endpoint.basic}")
    private String basicEndpoint;

    @Autowired
    private Client client;

    public CoinWrapper process(Coin coin) {
        double pow = Math.pow(10, 8);
        Response response = client.target(endpoint).request(MediaType.APPLICATION_JSON_TYPE).get();
        JSONObject jsonObject = new JSONObject(response.readEntity(String.class));
        BigDecimal balance = jsonObject.getBigDecimal("available").divide(new BigDecimal(pow));
        return CoinWrapper.builder().coin(coin).actualBalance(balance).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        double pow = Math.pow(10, 8);
        Response response = client.target(basicEndpoint + wallet).request(MediaType.APPLICATION_JSON_TYPE).get();
        JSONObject jsonObject = new JSONObject(response.readEntity(String.class));
        return jsonObject.getBigDecimal("available").divide(new BigDecimal(pow));
    }
}
