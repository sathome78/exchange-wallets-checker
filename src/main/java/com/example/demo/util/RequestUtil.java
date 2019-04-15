package com.example.demo.util;

import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static com.example.demo.configuration.CacheConfiguration.CACHE_ETHEREUM_SEARCH;

@Service
@Log4j2
public class RequestUtil {

    @Value("${eth.endpoint.basic}")
    private String ethTokenBaseAddress;

    @Autowired
    private Client client;

    @Autowired
    @Qualifier(CACHE_ETHEREUM_SEARCH)
    private Cache ethereumResponseCache;

    public BigDecimal getEthAddressAmount(String ethAddress) {
        try {
            String response = getResponse(ethAddress);
            JSONObject jsonObject = new JSONObject(response);
            JSONObject eth = jsonObject.getJSONObject("ETH");
            return eth.getBigDecimal("balance");
        } catch (Exception ex) {
            log.warn("Unable to retrieve information about eth token with wallet " + ethAddress);
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal getTokenValue(String ethAddress, String tokenName) {
        try {
            String response = getResponse(ethAddress);
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("tokens");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject tokenObject = jsonArray.getJSONObject(i);
                JSONObject tokenInfoObject = tokenObject.getJSONObject("tokenInfo");
                String symbol = tokenInfoObject.getString("symbol").toLowerCase();
                if (symbol.equals(tokenName.toLowerCase())) {
                    int decimals = Integer.parseInt(tokenInfoObject.get("decimals").toString());
                    double pow = Math.pow(10, decimals);
                    return tokenObject.getBigDecimal("balance").divide(new BigDecimal(pow));
                }
            }
            return BigDecimal.ZERO;
        } catch (Exception ex) {
            log.warn("Unable to retrieve information about eth token with wallet " + ethAddress);
            return BigDecimal.ZERO;
        }
    }

    private String getResponse(String ethAddress) {
        return ethereumResponseCache.get(ethAddress, () -> {
            TimeUnit.SECONDS.sleep(5);
            return client.target(String.format(ethTokenBaseAddress, ethAddress)).request(MediaType.APPLICATION_JSON_TYPE).get().readEntity(String.class);
        });
    }
}
