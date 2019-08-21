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

@Service("arkCoinProcessor")
public class ARKCoinProcessor implements CoinProcessor {

    @Autowired
    private Client client;

    @Value("${ark.endpoint.basic}")
    private String arkEndpointBasic;

    @Override
    public CoinWrapper process(Coin coin) {
        final BigDecimal actualBalance = getBalance(coin, coin.getCoinAddress());

        return CoinWrapper.builder().coin(coin).actualBalance(actualBalance).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String coinAddress) {
        Response response = client.target(arkEndpointBasic + coinAddress).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        String balance = new JSONObject(s).getJSONObject("account").getString("balance");
        return new BigDecimal(balance).divide(new BigDecimal(100000000));
    }
}