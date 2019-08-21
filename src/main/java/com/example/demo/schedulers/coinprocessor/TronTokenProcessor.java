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

@Service("tronTokenProcessor")
public class TronTokenProcessor implements CoinProcessor {

    @Value("${tron.endpoint.basic}")
    private String tronEndpointBasic;

    @Value("${tron-token.endpoint.basic}")
    private String tronTokenEndpointBasic;

    @Autowired
    private Client client;

    @Override
    public CoinWrapper process(Coin coin) {
        final BigDecimal actualBalance = getBalance(coin, coin.getCoinAddress());

        return CoinWrapper.builder().coin(coin).actualBalance(actualBalance).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String coinAddress) {
        final String tokenId = coin.getEthTokenContract();

        Response response = client.target(tronEndpointBasic + coinAddress).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        JSONObject jsonObject = new JSONObject(s);
        JSONArray jsonArray = jsonObject.getJSONArray("tokenBalances");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tokenObject = jsonArray.getJSONObject(i);
            String tokenName = tokenObject.getString("name");
            if (tokenId.equals(tokenName)) {
                int decimals = getDecimals(tokenId);
                double pow = Math.pow(10, decimals);
                return tokenObject.getBigDecimal("balance").divide(new BigDecimal(pow));
            }
        }
        return BigDecimal.ZERO;
    }

    private int getDecimals(String tokenId) {
        Response response = client.target(tronTokenEndpointBasic + tokenId).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        JSONObject jsonObject = new JSONObject(s);
        JSONObject tokenInfoObject = jsonObject.getJSONArray("data").getJSONObject(0);
        return tokenInfoObject.getInt("precision");
    }
}