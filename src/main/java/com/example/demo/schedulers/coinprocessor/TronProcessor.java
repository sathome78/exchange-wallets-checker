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

    public CoinWrapper process(Coin coin) {
        Response response = client.target(tronEndpointBasic + coin.getCoinAddress()).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        BigDecimal actualBalance = new JSONObject(s).getJSONArray("balances").getJSONObject(0).getBigDecimal("balance");
        return CoinWrapper.builder().coin(coin).actualBalance(actualBalance).build();

    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.target(tronEndpointBasic + wallet).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        return new JSONObject(s).getJSONArray("balances").getJSONObject(0).getBigDecimal("balance");
    }


}
