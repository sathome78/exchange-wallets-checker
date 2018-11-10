package com.example.demo.schedulers.coinprocessor.btccoinsprocessor;

import com.example.demo.domain.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("abbcCoinProcessor")
public class ABBCCoinProcessor implements BTCGenericProcessor {


    @Value("${abbc.endpoint}")
    private String abbcCoinProcessor;

    @Autowired
    private Client client;

    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.target(abbcCoinProcessor + wallet).request(MediaType.TEXT_HTML_TYPE).get();
        String s = response.readEntity(String.class);
        s = s.substring(s.lastIndexOf("</td><td>") + "</td><td>".length(), s.indexOf("</td></tr>"));
        return new BigDecimal(s);
    }
}
