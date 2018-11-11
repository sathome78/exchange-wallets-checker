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

@Service("iotaProcessor")
public class IOTAProcessor implements CoinProcessor {

    @Value("${iota.endpoint.basic}")
    private String iotaEndpointBasic;

    @Autowired
    private Client client;

    public CoinWrapper process(Coin coin) {
        Response response = client.target(iotaEndpointBasic + coin.getCoinAddress()).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        return CoinWrapper.builder().coin(coin).actualBalance(new JSONObject(s).getBigDecimal("balance")).build();
    }

    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.target(iotaEndpointBasic + wallet).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        return new JSONObject(s).getBigDecimal("balance");
    }
}
