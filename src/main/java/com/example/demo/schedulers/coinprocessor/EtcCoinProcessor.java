package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Repository
@Log4j2
public class EtcCoinProcessor implements CoinProcessor {

    @Value("${etc.endpoint.basic}")
    private String basicEndpoint;

    @Autowired
    Client client;

    public CoinWrapper process(Coin coin) {
        Response response = client.target(basicEndpoint + coin.getCoinAddress()).request(MediaType.APPLICATION_JSON_TYPE).get();
        String stringJsonResponse = response.readEntity(String.class);
        JSONObject jsonObject = new JSONObject(stringJsonResponse);
        double etcBalance = jsonObject.
                getJSONObject("balance").
                getDouble("ether");
        return CoinWrapper.builder().coin(coin).actualBalance(new BigDecimal(etcBalance)).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.target(basicEndpoint + wallet).request(MediaType.APPLICATION_JSON_TYPE).get();

        String stringJsonResponse = response.readEntity(String.class);
        JSONObject jsonObject = new JSONObject(stringJsonResponse);

        double etcBalance = jsonObject.
                getJSONObject("balance").
                getDouble("ether");

        return new BigDecimal(etcBalance);
    }
}
