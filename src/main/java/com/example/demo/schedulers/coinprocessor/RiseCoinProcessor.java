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

@Service("riseCoinProcessor")
public class RiseCoinProcessor implements CoinProcessor {

    @Value("${rise.coin}")
    private String riseEndpoint;

    @Autowired
    private Client client;

    @Override
    public CoinWrapper process(Coin coin) {
        final BigDecimal actualBalance = getBalance(coin, coin.getCoinAddress());

        return CoinWrapper.builder().coin(coin).actualBalance(actualBalance).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String coinAddress) {
        Response response = client.target(riseEndpoint + coinAddress).request(MediaType.APPLICATION_JSON_TYPE).get();

        String balance = new JSONObject(response.readEntity(String.class)).getString("balance");

        return new BigDecimal(balance).divide(new BigDecimal(Math.pow(10, 8)));
    }
}