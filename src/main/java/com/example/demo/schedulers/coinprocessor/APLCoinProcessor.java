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
public class APLCoinProcessor implements CoinProcessor {

    @Value("${apl.endpoint}")
    private String endpoint;

    @Value("${apl.endpoint.basic}")
    private String baseEndpoint;

    @Autowired
    private Client client;

    public CoinWrapper process(Coin coin) {
        double pow = Math.pow(10, 7);
        Response response = client.target(endpoint).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        BigDecimal balanceATM = new BigDecimal(new JSONObject(s).getString("balanceATM")).divide(new BigDecimal(pow));
        return CoinWrapper.builder().coin(coin).actualBalance(balanceATM).build();

    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        double pow = Math.pow(10, 7);
        Response response = client.target(baseEndpoint + wallet).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        return new BigDecimal(new JSONObject(s).getString("balanceATM")).divide(new BigDecimal(pow));
    }
}
