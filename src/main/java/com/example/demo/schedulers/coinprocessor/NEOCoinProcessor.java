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

@Service("neoCoinProcessor")
public class NEOCoinProcessor implements CoinProcessor {

    @Value("${neo.endpoint.basic}")
    private String endpointBasic;

    @Autowired
    private Client client;

    @Override
    public CoinWrapper process(Coin coin) {
        final BigDecimal actualBalance = getBalance(coin, coin.getCoinAddress());

        return CoinWrapper.builder().actualBalance(actualBalance).coin(coin).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String coinAddress) {
        Response response = client.target(endpointBasic + coinAddress).request(MediaType.APPLICATION_JSON_TYPE).get();

        JSONObject jsonObject = new JSONObject(response.readEntity(String.class));

        return jsonObject.getJSONArray("balances").getJSONObject(0).getBigDecimal("total");
    }
}