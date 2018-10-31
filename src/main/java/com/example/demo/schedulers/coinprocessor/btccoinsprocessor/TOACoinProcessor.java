package com.example.demo.schedulers.coinprocessor.btccoinsprocessor;

import com.example.demo.domain.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("toaCoinProcessor")
public class TOACoinProcessor implements BTCGenericProcessor {


    @Value("${btc.toa.coin}")
    private String baseURL;

    @Autowired
    private Client client;

    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.target(String.format(baseURL, wallet)).request(MediaType.TEXT_PLAIN).get();
        String stringResponse = response.readEntity(String.class);
        return new BigDecimal(stringResponse);
    }

}
