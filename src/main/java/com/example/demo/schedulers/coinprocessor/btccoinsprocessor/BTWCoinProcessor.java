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

    @Value("${btw.endpoint.basic}")
    private String btwEndpointBasic;

    public CoinWrapper process(Coin coin) {
        BigDecimal balance = getBalance(coin.getCoinAddress());
        return CoinWrapper.builder().coin(coin).actualBalance(balance).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        return getBalance(wallet);
    }

    private BigDecimal getBalance(String address) {
        Response response = client.target(btwEndpointBasic + address).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        double pow = Math.pow(10, 8);
        return new JSONObject(s).getBigDecimal("balance").divide(new BigDecimal(pow));
    }
}
