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
public class TronProcessor implements CoinProcessor {

    @Value("${tron.endpoint.basic}")
    private String tronEndpointBasic;

    @Autowired
    private Client client;

    @Override
    public CoinWrapper process(Coin coin) {
        final BigDecimal actualBalance = getBalance(coin, coin.getCoinAddress());

        return CoinWrapper.builder().coin(coin).actualBalance(actualBalance).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String coinAddress) {
        Response response = client.target(tronEndpointBasic + coinAddress).request(MediaType.APPLICATION_JSON_TYPE).get();

        String s = response.readEntity(String.class);
        double pow = Math.pow(10, 6);

        return new JSONObject(s).getBigDecimal("balance").divide(BigDecimal.valueOf(pow));
    }
}