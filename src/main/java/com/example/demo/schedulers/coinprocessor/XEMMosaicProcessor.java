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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("xemMosaicProcessor")
public class XEMMosaicProcessor implements CoinProcessor {

    @Autowired
    private Client client;

    @Value("${xem.mosaic.endpoint}")
    private String mosaicEndpoint;

    @Value("${xem.main.address}")
    private String mainAddress;

    public CoinWrapper process(Coin coin) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("address", mainAddress);
        Response response = client.target(mosaicEndpoint).request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(jsonObject.toString()));
        String stringResponse = response.readEntity(String.class);
        JSONArray jsonResponse = new JSONArray(stringResponse);

        List<Object> objects = jsonResponse.toList();
        Map<String, BigDecimal> collect = objects.stream().
                map(item -> (HashMap<String, Object>) item).collect(Collectors.toMap(k -> k.get("namespace") + ":" + k.get("mosaic"), v -> new BigDecimal(String.valueOf(v.get("quantity")))));

        BigDecimal bigDecimal = collect.get(coin.getDetailName());

        return CoinWrapper.builder().coin(coin).actualBalance(bigDecimal).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        return null;
    }
}
