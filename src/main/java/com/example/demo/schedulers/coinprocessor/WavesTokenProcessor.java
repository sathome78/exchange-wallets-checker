package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import com.example.demo.exceptions.UnsupportedCoinType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Service("wavesTokenProcessor")
public class WavesTokenProcessor implements CoinProcessor {

    @Autowired
    Client client;

    @Value("wavesToken.endpoint.explorer")
    private String wavesTokenEndpoint;

    @Override
    public CoinWrapper process(Coin coin) {
        final BigDecimal actualBalance = getBalance(coin, coin.getCoinAddress());

        return CoinWrapper.builder().coin(coin).actualBalance(actualBalance).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String coinAddress) {
        final String coinName = coin.getName();

        JSONArray balances = new JSONObject(client.target(wavesTokenEndpoint + coinAddress).request(MediaType.APPLICATION_JSON).get().readEntity(String.class)).getJSONArray("balances");
        for (Object balance : balances) {
            JSONObject jsonObject = (JSONObject) balance;
            if (jsonObject.getJSONObject("issueTransaction").getString("name").equalsIgnoreCase(coinName))
                return jsonObject.getBigDecimal("balance").divide(new BigDecimal(Math.pow(10, 2)));

        }

        for (int i = 0; i < balances.length(); i++) {
            JSONObject jsonObject = balances.getJSONObject(i);
            if (jsonObject.getJSONObject("issueTransaction").getString("name").equalsIgnoreCase(coinName))
                return jsonObject.getBigDecimal("balance").divide(new BigDecimal(Math.pow(10, 2)));
        }
        throw new UnsupportedCoinType(coinName + " not found!");
    }
}