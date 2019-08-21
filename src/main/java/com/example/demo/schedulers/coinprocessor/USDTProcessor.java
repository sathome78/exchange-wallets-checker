package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service
public class USDTProcessor implements CoinProcessor {

    @Autowired
    private Client client;

    @Value("${usdt.base.url}")
    private String usdtUrl;

    @Override
    public CoinWrapper process(Coin coin) {
        final BigDecimal actualBalance = getBalance(coin, coin.getCoinAddress());

        return CoinWrapper.builder().coin(coin).actualBalance(actualBalance).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String coinAddress) {
        WebTarget webTarget = client.target(usdtUrl);

        MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
        formData.add("query", coin.getCoinAddress());
        Response response = webTarget.request().post(Entity.form(formData));
        JSONObject jsonObject = new JSONObject(response.readEntity(String.class));
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONObject("address").getJSONArray("balance");
        String value = jsonArray.getJSONObject(0).getString("value");

        return new BigDecimal(value).divide(new BigDecimal(Math.pow(10, 8)));
    }
}