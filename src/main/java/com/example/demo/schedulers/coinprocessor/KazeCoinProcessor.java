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

@Service("kazeCoinProcessor")
public class KazeCoinProcessor implements CoinProcessor {

    @Value("${kaze.endpoint.basic}")
    private String kazeEndpointBasic;

    @Autowired
    private Client client;

    @Override
    public CoinWrapper process(Coin coin) {
        Response response = client.target(kazeEndpointBasic+coin.getEthTokenContract()).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        BigDecimal bigDecimal = new JSONObject(s).getJSONArray("balance").getJSONObject(0).getBigDecimal("amount");
        return CoinWrapper.builder().coin(coin).actualBalance(bigDecimal).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.target(kazeEndpointBasic + wallet).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        return new JSONObject(s).getJSONArray("balance").getJSONObject(0).getBigDecimal("amount");
    }
}
