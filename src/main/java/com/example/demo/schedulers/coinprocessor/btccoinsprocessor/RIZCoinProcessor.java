package com.example.demo.schedulers.coinprocessor.btccoinsprocessor;

import com.example.demo.domain.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("rizCoinProcessor")
public class RIZCoinProcessor implements BTCGenericProcessor {

    @Autowired
    private Client client;

    public BigDecimal getBalance(Coin coin, String wallet) {
        Response response = client.target("http://rizblockchain.com/address/" + wallet).request(MediaType.TEXT_HTML).get();
        String s = response.readEntity(String.class).trim();
        String substring = s.substring(s.indexOf("<tbody>"), s.indexOf("</tbody>"));
        String substring1 = substring.substring(substring.indexOf("<td>"), substring.indexOf("</td>"));
        return new BigDecimal(substring1);
    }
}
