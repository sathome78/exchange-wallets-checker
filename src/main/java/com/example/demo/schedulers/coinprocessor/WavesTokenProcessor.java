package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Service
@Scope("prototype")
@NoArgsConstructor
public class WavesTokenProcessor implements CoinProcessor{

    @Autowired
    Client client;

    private String wavesTokenEndpoint = "https://nodes.wavesnodes.com/assets/balance/3PNu2t61vQFw9fDT8B9h881H4AtBejnN7mV";

    private  String name;

    public BigDecimal getAmount(String address, String name){
        JSONArray balances = new JSONObject(client.target("https://nodes.wavesnodes.com/assets/balance/3PNu2t61vQFw9fDT8B9h881H4AtBejnN7mV").request(MediaType.APPLICATION_JSON).get().readEntity(String.class)).getJSONArray("balances");
        for (int i = 0; i < balances.length(); i++) {
            JSONObject jsonObject = balances.getJSONObject(i);
            if (jsonObject.getJSONObject("issueTransaction").getString("name").equalsIgnoreCase(name)) return jsonObject.getBigDecimal("balance").divide(new BigDecimal(Math.pow(10, 2)));
        }
        throw new RuntimeException();//todo
    }

    public WavesTokenProcessor(String name) {
        this.name = name;
    }

    @Override
    public CoinWrapper process(Coin coin) {
        return CoinWrapper.builder().coin(coin).actualBalance(getAmount(coin.getCoinAddress(), name)).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        return null;
    }
}
