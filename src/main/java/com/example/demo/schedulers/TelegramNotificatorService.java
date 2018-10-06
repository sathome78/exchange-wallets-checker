package com.example.demo.schedulers;

import com.example.demo.domain.Coin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static java.lang.String.format;
import static java.lang.String.valueOf;

@Service
@Slf4j(topic = "TelegramNotificatorService")
public class TelegramNotificatorService implements NotificatorService {

    @Autowired
    Client client;

    @Value("${telegram.api.bot.url}")
    private String botUrl;

    @Override
    public void notificate(String template, Coin coin) throws UnsupportedEncodingException {
        String text = format(template, coin.getName(), getCurrentDate(), valueOf(decimalFormat.format(coin.getCurrentAmount())), valueOf(coin.getMinAmount()), valueOf(coin.getMaxAmount()));
        text = URLEncoder.encode(text,"UTF-8");
        Response response = client.target(format(botUrl, text)).request(MediaType.APPLICATION_JSON_TYPE).get();

        log.info("Send notification to telegramm {}.", text, response.readEntity(String.class));

    }
}
