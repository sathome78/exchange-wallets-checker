package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("xemProcessor")
public class XEMProcessor implements CoinProcessor {

    @Autowired
    private Client client;

    @Value("${xem.endpoint.basic}")
    private String xemBasicEndpoint;

    public CoinWrapper process(Coin coin) {
        BigDecimal newBalance = getBigDecimal(coin.getCoinAddress());

        return CoinWrapper.builder().coin(coin).actualBalance(newBalance).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        return getBigDecimal(wallet);
    }

    private BigDecimal getBigDecimal(String coinAddress) {
        Response response = client.target(xemBasicEndpoint + coinAddress).request(MediaType.APPLICATION_JSON_TYPE).get();
        JSONObject jsonObject = new JSONObject(response.readEntity(String.class));
        BigDecimal bigDecimal = jsonObject.getJSONObject("account").getBigDecimal("balance");
        return bigDecimal.divide(new BigDecimal(1000000));
    }
}
