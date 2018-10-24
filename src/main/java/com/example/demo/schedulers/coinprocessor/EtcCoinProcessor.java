package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Repository

public class EtcCoinProcessor implements CoinProcessor {

    @Value("${etc.endpoint}")
    private String etcEndpoint;

    @Autowired
    Client client;

    public CoinWrapper process(Coin coin) {
        Response response = client.target(etcEndpoint).request(MediaType.APPLICATION_JSON_TYPE).get();
        String stringJsonResponse = response.readEntity(String.class);
        JSONObject jsonObject = new JSONObject(stringJsonResponse);
        double etcBalance = jsonObject.
                getJSONObject("balance").
                getDouble("ether");
        return CoinWrapper.builder().coin(coin).actualBalance(new BigDecimal(etcBalance)).build();
    }
}
