package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("rbcCoinProcessor")
public class RBCCoinProcessor implements CoinProcessor {

    @Autowired
    private Client client;

    @Value("${rbc.endpoint.basic}")
    private String rbcBasicEndpoint;

    @Override
    public CoinWrapper process(Coin coin) {
        final BigDecimal actualBalance = getBalance(coin, coin.getCoinAddress());

        return CoinWrapper.builder().coin(coin).actualBalance(actualBalance).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String coinAddress) {
        Response response = client.target(rbcBasicEndpoint + coinAddress).request(MediaType.APPLICATION_JSON_TYPE).get();

        final String detailName = coin.getDetailName();

        String s = response.readEntity(String.class);
        JSONObject jsonObject = new JSONObject(s);
        JSONArray jsonArray = jsonObject.getJSONArray("balances");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tokenObject = jsonArray.getJSONObject(i);
            String tokenName = tokenObject.getJSONObject("issueTransaction").getString("name");
            if (detailName.equals(tokenName)) {
                int decimals = tokenObject.getJSONObject("issueTransaction").getInt("decimals");
                double pow = Math.pow(10, decimals);
                return tokenObject.getBigDecimal("balance").divide(new BigDecimal(pow));
            }
        }
        return BigDecimal.ZERO;
    }
}