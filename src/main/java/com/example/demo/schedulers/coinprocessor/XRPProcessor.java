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

@Service("xrpProcessor")
public class XRPProcessor implements CoinProcessor {

    @Value("${xrp.endpoint}")
    private String xrpEndpoint;

    @Autowired
    Client client;

    public CoinWrapper process(Coin coin) {
        Response response = client.target(xrpEndpoint).request(MediaType.APPLICATION_JSON_TYPE).get();

         String s = response.readEntity(String.class);

        JSONObject jsonObject = new JSONObject(s);

        String string = jsonObject.
                getJSONArray("transactions").
                getJSONObject(0).
                getJSONObject("meta").
                getJSONArray("AffectedNodes").
                getJSONObject(1).
                getJSONObject("ModifiedNode").
                getJSONObject("FinalFields").
                getString("Balance");

        return CoinWrapper.builder().coin(coin).actualBalance(new BigDecimal(string)).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        return null;
    }
}
