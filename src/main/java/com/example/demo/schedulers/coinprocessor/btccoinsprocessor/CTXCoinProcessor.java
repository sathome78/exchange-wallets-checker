package com.example.demo.schedulers.coinprocessor.btccoinsprocessor;

import com.example.demo.domain.Coin;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Service
public class CTXCoinProcessor implements BTCGenericProcessor {

    @Value("${btc.ctx.coin}")
    private String btcCtxCoinBaseaddress;

    @Autowired
    private Client client;


    public BigDecimal getBalance(Coin coin, String wallet) {
        String s = client.target(btcCtxCoinBaseaddress + wallet).request(MediaType.APPLICATION_JSON_TYPE).get()
                .readEntity(String.class);
        String balance = new JSONObject(s).getString("balance");

        return new BigDecimal(balance);
    }
}
