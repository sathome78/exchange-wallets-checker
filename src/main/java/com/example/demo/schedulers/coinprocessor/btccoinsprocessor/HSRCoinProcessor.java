package com.example.demo.schedulers.coinprocessor.btccoinsprocessor;

import com.example.demo.domain.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("hsrCoinProcessor")
public class HSRCoinProcessor implements BTCGenericProcessor {


    @Value("${btc.hsr.coin}")
    private String baseURL;

    @Autowired
    private Client client;

    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.target(baseURL + wallet).request(MediaType.TEXT_PLAIN).get();
        String stringResponse = response.readEntity(String.class);
        stringResponse = stringResponse.replace('\"', ' ').trim();
        return new BigDecimal(stringResponse);
    }
}
