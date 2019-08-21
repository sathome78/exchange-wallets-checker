package com.example.demo.schedulers.coinprocessor.btccoinsprocessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("dogeCoinProcessor")
public class DogeCoinProcessor implements BTCGenericProcessor {

    @Value("${btc.doge.coin}")
    private String dogeEndpoint;

    @Autowired
    private Client client;

    @Override
    public BigDecimal getBalance(String coinAddress) {
        Response response = client.target(dogeEndpoint + coinAddress).request(MediaType.TEXT_HTML).get();

        String s = response.readEntity(String.class).trim();
        String first = s.substring(s.indexOf("currency"));
        String replace = first.substring(first.indexOf(">") + 1, first.indexOf("<")).trim();

        return new BigDecimal(replace.replace(",", ""));
    }
}