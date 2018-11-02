package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("qtumProcessor")
public class QTUMCoinProcessor implements CoinProcessor {

    @Value("${qtum.endpoint}")
    private String qtumEndpoint;

    @Value("${qtum.endpoint.basic}")
    private String qtumBasicEndpoint;

    @Autowired
    private Client client;

    public CoinWrapper process(Coin coin) {
        Response response = client.target(qtumEndpoint).request(MediaType.TEXT_HTML).get();
        String s = response.readEntity(String.class).trim();
        String substring = s.substring(s.indexOf("info-value monospace"));
        String newBalance = substring.substring(substring.indexOf(">") + 1, substring.indexOf("QTUM")).trim();
        return CoinWrapper.builder().coin(coin).actualBalance(new BigDecimal(newBalance)).build();
    }


    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.target(qtumBasicEndpoint + wallet).request(MediaType.TEXT_HTML).get();
        String s = response.readEntity(String.class).trim();
        String substring = s.substring(s.indexOf("info-value monospace"));
        String newBalance = substring.substring(substring.indexOf(">") + 1, substring.indexOf("QTUM")).trim();
        return new BigDecimal(newBalance);
    }

}
