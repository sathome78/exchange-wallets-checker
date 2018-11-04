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

@Service("neoCoinProcessor")
public class NEOCoinProcessor implements CoinProcessor {

    @Value("${neo.endpoint}")
    private String neoEndpoint;

    @Value("${neo.endpoint.basic}")
    private String endpointBasic;

    @Autowired
    private Client client;

    @Override
    public CoinWrapper process(Coin coin) {
        Response response = client.target(neoEndpoint).request(MediaType.APPLICATION_JSON_TYPE).get();

        return null;
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        return null;
    }
}
