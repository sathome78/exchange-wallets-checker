package com.example.demo.schedulers.coinprocessor.btccoinsprocessor;

import com.example.demo.domain.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("cbcCoinProcessor")
public class CBCCoinProcessor implements BTCGenericProcessor {

    @Value("${cbc.endpoint}")
    private String cbcEndpoint;

    @Autowired
    private Client client;

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.target(cbcEndpoint + wallet).request(MediaType.TEXT_HTML_TYPE).get();
        String s = response.readEntity(String.class);
        s = s.substring(s.lastIndexOf("</td><td>") + "</td><td>".length(), s.indexOf("</td></tr>"));
        return new BigDecimal(s);
    }
}