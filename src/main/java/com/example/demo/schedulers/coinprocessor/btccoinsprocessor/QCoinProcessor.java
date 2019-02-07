package com.example.demo.schedulers.coinprocessor.btccoinsprocessor;

import com.example.demo.domain.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service("qCoinProcessor")
public class QCoinProcessor implements BTCGenericProcessor {

    @Value("${btc.q.coin}")
    private String baseURL;

    private final Client client;

    @Autowired
    public QCoinProcessor(Client client) {
        this.client = client;
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {

        String formated = String.format(baseURL, wallet);
        Response response = client.target(formated).request(MediaType.APPLICATION_JSON_TYPE).get();
        String s = response.readEntity(String.class);
        int end = s.indexOf("</td></tr></tbody></table>");
        String concated = s.substring(0, end);
        int preBalanceIndex = concated.lastIndexOf("</td><td>");

        return new BigDecimal(concated.substring(preBalanceIndex + "</td><td>".length()));
    }

}
