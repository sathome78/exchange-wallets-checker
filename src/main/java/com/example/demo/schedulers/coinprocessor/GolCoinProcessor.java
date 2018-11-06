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

@Service("golCoinProcessor")
public class GolCoinProcessor implements CoinProcessor {

    @Value("${gol.coin}")
    private String golBasicEndpoint;

    @Autowired
    private Client client;

    @Override
    public CoinWrapper process(Coin coin) {
        Response response = client.target(golBasicEndpoint + coin.getEthTokenContract()).request(MediaType.APPLICATION_JSON_TYPE).get();
        String jsonResponse = response.readEntity(String.class);
        String balance = new JSONObject(jsonResponse).getJSONArray("info").getJSONObject(0).getString("balance");
        return CoinWrapper.builder().actualBalance(new BigDecimal(balance)).coin(coin).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.target(golBasicEndpoint + coin.getEthTokenContract()).request(MediaType.APPLICATION_JSON_TYPE).get();
        String jsonResponse = response.readEntity(String.class);
        String balance = new JSONObject(jsonResponse).getJSONArray("info").getJSONObject(0).getString("balance");
        return new BigDecimal(balance);
    }
}
