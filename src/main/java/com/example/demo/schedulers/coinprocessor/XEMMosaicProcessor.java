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
import java.util.stream.Collectors;

import static java.lang.Double.valueOf;
import static java.lang.Math.pow;
import static java.lang.String.format;
import static java.lang.String.valueOf;

@Service("xemMosaicProcessor")
public class XEMMosaicProcessor implements CoinProcessor {

    @Autowired
    private Client client;

    @Value("${xem.mosaic.endpoint}")
    private String mosaicEndpoint;

    @Override
    public CoinWrapper process(Coin coin) {
        final BigDecimal actualBalance = getBalance(coin, coin.getCoinAddress());

        return CoinWrapper.builder().coin(coin).actualBalance(actualBalance).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String coinAddress) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("address", coinAddress);
        Response response = client.target(mosaicEndpoint).request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(jsonObject.toString()));

        List<HashMap<String, Object>> collect1 = new JSONArray(response.readEntity(String.class)).
                toList().
                stream().
                map(item -> (HashMap<String, Object>) item).
                collect(Collectors.toList());

        for (HashMap<String, Object> collect : collect1) {
            String nameSpace = format("%s:%s", collect.get("namespace"), collect.get("mosaic"));
            boolean isCoin = nameSpace.equalsIgnoreCase(coin.getDetailName());
            if (isCoin) {
                BigDecimal quantity = new BigDecimal(valueOf(collect.get("quantity")));
                return quantity.divide(new BigDecimal(pow(10, valueOf(valueOf(collect.get("div"))))));
            }
        }
        return BigDecimal.ZERO;
    }
}