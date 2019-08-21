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
public class AchainCoinProcessor implements CoinProcessor {

    @Autowired
    Client client;

    @Value("${achain.endpoint.basic}")
    private String basicWallet;

    @Override
    public CoinWrapper process(Coin coin) {
        final BigDecimal actualBalance = getBalance(coin, coin.getCoinAddress());

        return CoinWrapper.builder().coin(coin).actualBalance(actualBalance).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String coinAddress) {
        Response response = client.target(basicWallet + coinAddress).request(MediaType.APPLICATION_JSON_TYPE).get();

        Map<String, String> collect2 = new JSONObject(response.readEntity(String.class)).
                getJSONArray("data").
                toList().
                stream().
                map(item -> (HashMap<String, Object>) item).
                map(element -> Pair.of(valueOf(element.get("coinType")), valueOf(element.getOrDefault("balance", "0"))))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

        return new BigDecimal(collect2.get(coin.getName()));
    }
}