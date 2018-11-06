package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("lunesCoinProcessor")
public class LunesCoinProcessor implements CoinProcessor {

    @Value("${lunes.address.endpoint}")
    private String lunesAddressEndpoint;
    @Value("${lunes.transaction.endpoint}")
    private String lunesTransactionEndpoint;


    @Autowired
    private Client client;

    public CoinWrapper process(Coin coin) {
        Response response = client.target(lunesAddressEndpoint + coin.getEthTokenContract()).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        BigDecimal available = new JSONObject(s).getBigDecimal("available");

        return CoinWrapper.builder().coin(coin).actualBalance(available.divide(new BigDecimal(Math.pow(10, 8)))).build();

    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.target(lunesAddressEndpoint + wallet).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        BigDecimal available = new JSONObject(s).getBigDecimal("available");

        return available.divide(new BigDecimal(Math.pow(10, 8)));

    }
}
