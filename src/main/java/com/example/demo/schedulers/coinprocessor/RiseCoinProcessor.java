package com.example.demo.schedulers.coinprocessor;

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

@Service("riseCoinProcessor")
public class RiseCoinProcessor implements CoinProcessor {

    @Value("${rise.coin}")
    private String riseEndpoint;

    @Autowired
    private Client client;

    @Override
    public CoinWrapper process(Coin coin) {
        Response response = client.target(riseEndpoint + coin.getEthTokenContract()).request(MediaType.APPLICATION_JSON_TYPE).get();
        String balance = new JSONObject(response.readEntity(String.class)).getString("balance");
        BigDecimal actualBalance = new BigDecimal(balance).divide(new BigDecimal(Math.pow(10, 8)));

        return CoinWrapper.builder().coin(coin).actualBalance(actualBalance).build();
    }

    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.target(riseEndpoint + coin.getEthTokenContract()).request(MediaType.APPLICATION_JSON_TYPE).get();
        String balance = new JSONObject(response.readEntity(String.class)).getString("balance");
        return new BigDecimal(balance).divide(new BigDecimal(Math.pow(10, 8)));
    }
}
