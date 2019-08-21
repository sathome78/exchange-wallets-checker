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

    @Override
    public CoinWrapper process(Coin coin) {
        final BigDecimal actualBalance = getBalance(coin, coin.getCoinAddress());

        return CoinWrapper.builder().coin(coin).actualBalance(actualBalance).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String coinAddress) {
        Response response = client.target(basicEndpoint + coinAddress).request(MediaType.APPLICATION_JSON_TYPE).get();

        String stringJsonResponse = response.readEntity(String.class);
        JSONObject jsonObject = new JSONObject(stringJsonResponse);

        double etcBalance = jsonObject.
                getJSONObject("balance").
                getDouble("ether");

        return new BigDecimal(etcBalance);
    }
}