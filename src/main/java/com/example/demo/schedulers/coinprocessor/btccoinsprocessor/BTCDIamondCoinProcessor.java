package com.example.demo.schedulers.coinprocessor.btccoinsprocessor;

import com.example.demo.domain.Coin;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("btcDiamondCoinProcessor")
public class BTCDIamondCoinProcessor implements BTCGenericProcessor {

    @Value("${btc.bcd.coin}")
    private String bcdCoin;

    @Autowired
    private Client client;

    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.target(String.format(bcdCoin, wallet)).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        return new JSONObject(s).getJSONObject("data").getBigDecimal("balance").divide(new BigDecimal(10000000));
    }
}
