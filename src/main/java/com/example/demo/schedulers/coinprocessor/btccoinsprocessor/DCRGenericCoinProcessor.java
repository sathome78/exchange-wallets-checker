package com.example.demo.schedulers.coinprocessor.btccoinsprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("dcrCoinProcessor")
public class DCRGenericCoinProcessor implements BTCGenericProcessor {

    @Value("${dcr.endpoint.basic}")
    private String dcrBasicEndpoint;

    @Autowired
    private Client client;

    public CoinWrapper process(Coin coin) {
        final BigDecimal actualBalance = getBalance(coin.getCoinAddress());

        return CoinWrapper.builder().coin(coin).actualBalance(actualBalance).build();
    }

    @Override
    public BigDecimal getBalance(String coinAddress) {
        Response response = client.target(String.format(dcrBasicEndpoint, coinAddress)).request(MediaType.APPLICATION_JSON_TYPE).get();

        String s = response.readEntity(String.class);

        return new JSONObject(s).getBigDecimal("balance");
    }
}