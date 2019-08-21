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

@Service("qtumTokenProcessor")
public class QtumTokenProcessor implements CoinProcessor {

    @Value("${qtum.token.endpoint}")
    private String qtumTokenEndpoint;

    @Autowired
    private Client client;

    @Override
    public CoinWrapper process(Coin coin) {
        final BigDecimal actualBalance = getBalance(coin, coin.getCoinAddress());

        return CoinWrapper.builder().coin(coin).actualBalance(actualBalance).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String coinAddress) {
        Response response = client.target(qtumTokenEndpoint + coinAddress).request(MediaType.APPLICATION_JSON_TYPE).get();

        String s = response.readEntity(String.class);
        JSONArray jsonArray = new JSONArray(s);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String amount = jsonObject.getString("amount");
            String symbol = jsonObject.getJSONObject("contract").getString("symbol");
            if (symbol.equals(coin.getName())) {
                String decimals = jsonObject.getJSONObject("contract").getString("decimals");
                return new BigDecimal(amount).divide(new BigDecimal(Math.pow(10, Double.valueOf(decimals))));
            }
        }
        return BigDecimal.ZERO;
    }
}