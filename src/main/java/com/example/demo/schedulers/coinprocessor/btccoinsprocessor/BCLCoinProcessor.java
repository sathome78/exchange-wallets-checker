package com.example.demo.schedulers.coinprocessor.btccoinsprocessor;

import com.example.demo.domain.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("bclCoinProcessor")
public class BCLCoinProcessor implements BTCGenericProcessor {

    @Value(value = "${btc.bcl.coin}")
    private String address;

    @Autowired
    private Client client;

    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.target(address + wallet).request(MediaType.TEXT_HTML_TYPE).get();
        String s = response.readEntity(String.class).trim();
        String substring = s.substring(s.indexOf("<div class=\"col-12 col-md-9 col-lg-10\">") + "<div class=\"col-12 col-md-9 col-lg-10\">".length());
        String substring1 = substring.substring(substring.indexOf("<span class=\"hide\">")).replace(",", "");
        return new BigDecimal(substring1);
    }
}
