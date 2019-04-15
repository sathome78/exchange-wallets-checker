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

@Service("cscCoinProcessor")
public class CSCCoinProcessor implements CoinProcessor {

    @Value("${csc.endpoint.basic}")
    private String cscBasicEndpoint;
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
        Response response = client.target(cscBasicEndpoint + address).request(MediaType.TEXT_HTML_TYPE).get();
        String balance = response.readEntity(String.class);
        return new BigDecimal(balance);
    }
}