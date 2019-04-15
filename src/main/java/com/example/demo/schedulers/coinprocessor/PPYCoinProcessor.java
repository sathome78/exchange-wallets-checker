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

@Service("ppyCoinProcessor")
public class PPYCoinProcessor implements CoinProcessor {

    @Value("${ppy.endpoint.basic}")
    private String ppyBasicEndpoint;
    @Autowired
    private Client client;

    public CoinWrapper process(Coin coin) {
        return CoinWrapper.builder().coin(coin).actualBalance(getBalance(coin.getCoinAddress())).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        return getBalance(wallet);
    }

    private BigDecimal getBalance(String address) {
        Response response = client.target(ppyBasicEndpoint + address).request(MediaType.TEXT_HTML_TYPE).get();
        String s = response.readEntity(String.class).replaceAll("\n", "").replaceAll(" ", "");
        int i = s.indexOf("<td>PPY</td><tdclass=\"rightaligned\">");
        String firstSubstring = s.substring(i + "<td>PPY</td><tdclass=\"rightaligned\">".length());
        String balance = firstSubstring.substring(0, firstSubstring.indexOf("<")).replaceAll(",", "");
        return new BigDecimal(balance);
    }
}