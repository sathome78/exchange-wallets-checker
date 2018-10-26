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
public class WavesProcessor implements CoinProcessor {

    @Value("${waves.endpoint}")
    private String wavesEndpoint;

    @Autowired
    private Client client;

    public CoinWrapper process(Coin coin) {
        Response response = client.target(wavesEndpoint).request(MediaType.APPLICATION_JSON_TYPE).get();
        String stringResponse = response.readEntity(String.class);
        JSONObject jsonResponse = new JSONObject(stringResponse);

        return CoinWrapper.builder().coin(coin).actualBalance(null).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        return null;
    }
}
