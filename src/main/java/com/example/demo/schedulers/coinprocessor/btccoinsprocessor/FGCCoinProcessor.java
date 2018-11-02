package com.example.demo.schedulers.coinprocessor.btccoinsprocessor;

import com.example.demo.domain.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("fgcCoinProcessor")
public class FGCCoinProcessor implements BTCGenericProcessor {

    @Value("${btc.fgc.coin}")
    private String fngbaseUrl;

    @Autowired
    private Client client;

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.target("https://fantasygold.network/address/" + wallet).request(MediaType.TEXT_HTML).get();
        String baseString = response.readEntity(String.class).trim();
        baseString = baseString.substring(baseString.indexOf("</th></tr></thead><tbody>"));
        String balance = baseString.substring("</th></tr></thead><tbody>".length() + "            <tr><td>".length(), baseString.indexOf("</td>"));
        return new BigDecimal(balance);
    }
}
