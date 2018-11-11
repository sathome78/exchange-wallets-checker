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

@Service("cloProcessor")
public class CLOCoinProcessor implements CoinProcessor {


    @Value("${clo.endpoint.basic}")
    private String cloEndpointBasic;

    @Autowired
    private Client client;

    public CoinWrapper process(Coin coin) {
        Response response = client.target(cloEndpointBasic + coin.getCoinAddress()).request(MediaType.TEXT_HTML).get();
        String substring = response.readEntity(String.class).substring(1385, 1398);
        return CoinWrapper.builder().coin(coin).actualBalance(new BigDecimal(substring)).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.target(cloEndpointBasic + wallet).request(MediaType.TEXT_HTML).get();
        String substring = response.readEntity(String.class).substring(1385, 1398);
        return new BigDecimal(substring);
    }

}

