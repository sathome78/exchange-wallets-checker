package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

@Service
public class StellarCoinProcessor implements CoinProcessor {

    @Autowired
    private Client client;

    @Value("${stellar.endpoint.basic}")
    private String stellarBasicEndpoint;

    @Override
    public CoinWrapper process(Coin coin) {
        final BigDecimal actualBalance = getBalance(coin, coin.getCoinAddress());

        return CoinWrapper.builder().coin(coin).actualBalance(actualBalance).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String coinAddress) {
        Response response = client.target(String.format(stellarBasicEndpoint, coinAddress)).request(MediaType.APPLICATION_JSON_TYPE).get();

        String stringResponse = response.readEntity(String.class);
        JSONObject jsonObject = new JSONObject(stringResponse);
        Map<String, String> collect2 = jsonObject.
                getJSONArray("balances").
                toList().
                stream().
                map(item -> (HashMap<String, Object>) item).
                map(element -> Pair.of(valueOf(element.getOrDefault("asset_code", "XLM")), valueOf(element.get("balance"))))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        String coinBalance = collect2.get(coin.getName());

        return new BigDecimal(coinBalance);
    }
}