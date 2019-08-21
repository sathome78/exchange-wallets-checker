package com.example.demo.schedulers.coinprocessor.btccoinsprocessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("bbxCoinProcessor")
public class BBXCoinProcessor implements BTCGenericProcessor {

    @Value("${btc.bbx.coin}")
    private String address;

    @Autowired
    private Client client;

    @Override
    public BigDecimal getBalance(String coinAddress) {
        Response response = client.target(address + coinAddress).request(MediaType.TEXT_HTML).get();

        String s = response.readEntity(String.class).trim();
        String substring = s.substring(s.lastIndexOf("</td><td>") + "</td><td>".length(), s.indexOf("</td></tr>"));

        return new BigDecimal(substring);
    }
}