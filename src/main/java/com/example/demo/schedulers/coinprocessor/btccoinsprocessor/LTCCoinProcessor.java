package com.example.demo.schedulers.coinprocessor.btccoinsprocessor;

import com.example.demo.domain.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("ltcCoinProcessor")
public class LTCCoinProcessor implements BTCGenericProcessor {

    @Value("${btc.ltc.coin}")
    private String ltcEndpoint;

    @Autowired
    private Client client;

    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.target(ltcEndpoint + wallet).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class).trim();
        String substring = s.substring(s.indexOf("Balance:"));
        String replace = substring.substring(8, substring.indexOf("LTC")).trim();
        return new BigDecimal(replace);
    }
}
