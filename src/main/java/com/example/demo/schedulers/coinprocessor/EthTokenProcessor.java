package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

@Service
public class EthTokenProcessor implements CoinProcessor {

    @Value("${eth.endpoint}")
    private String ethTokenAddress;

    @Autowired
    private Client client;

    public CoinWrapper process(Coin coin) {
        try {

            JSONObject jsonObject = getEthTokens();

            Map<String, String> collect2 = jsonObject.
                    getJSONArray("balances").
                    toList().
                    stream().
                    map(item -> (HashMap<String, Object>) item).
                    map(element -> new Pair<>(valueOf(element.get("contract")), valueOf(element.getOrDefault("balance", "0"))))
                    .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

            String balance = collect2.get(coin.getEthTokenContract());

            return CoinWrapper.builder().coin(coin).actualBalance(new BigDecimal(balance)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return CoinWrapper.builder().coin(coin).actualBalance(new BigDecimal(0)).build();
        }
    }

    @Cacheable("createInvocationScriptFolder")
    public JSONObject getEthTokens() {
        String response = client.target(ethTokenAddress).request(MediaType.APPLICATION_JSON_TYPE).get().readEntity(String.class);
        System.out.println(response);
        return new JSONObject(response);
    }
}
