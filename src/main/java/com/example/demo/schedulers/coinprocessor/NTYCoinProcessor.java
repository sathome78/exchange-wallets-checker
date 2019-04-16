package com.example.demo.schedulers.coinprocessor;

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

@Service("ntyProcessor")
public class NTYCoinProcessor implements CoinProcessor {

    @Value("${nty.endpoint}")
    private String endpoint;

    @Autowired
    private Client client;

    @Override
    public CoinWrapper process(Coin coin) {
        BigDecimal balance = getBalance(coin.getCoinAddress());
        return CoinWrapper.builder().coin(coin).actualBalance(balance).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        return getBalance(wallet);
    }

    private BigDecimal getBalance(String address) {
        Response response = client.target(endpoint + address).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        String balance = new JSONObject(s).getString("result");
        double pow = Math.pow(10, 18);
        return new BigDecimal(balance).divide(BigDecimal.valueOf(pow));
    }
}
