package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Service("gasProcessor")
public class GASProcessor implements CoinProcessor {

    @Value("gas.endpoint.basic")
    private String gasEndpoint;

    @Autowired
    Client client;

    @Override
    public CoinWrapper process(Coin coin) {
        return CoinWrapper.builder().coin(coin).actualBalance(getAmount(coin.getCoinAddress())).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        return getAmount(wallet);
    }

    private BigDecimal getAmount(String address) {
        String response = client.target(gasEndpoint + address).request(MediaType.TEXT_HTML).get().readEntity(String.class);
        String from = "<div class=\"total-gas-balance\">\n" +
                "<p class=\"balance-amount\">";
        int indexOfBegin = response.indexOf(from);
        int indexOfEnd = response.indexOf("</p>", indexOfBegin);
        return new BigDecimal(response.substring(indexOfBegin + from.length(), indexOfEnd));
    }

}
