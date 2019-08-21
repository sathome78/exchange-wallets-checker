package com.example.demo.schedulers;

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

@Service
@Slf4j(topic = "TelegramNotificatorService")
public class TelegramNotificatorService implements NotificatorService {

    @Autowired
    Client client;

    @Value("${telegram.api.bot.url}")
    private String botUrl;

    @Override
    public void notificate(String renderedTemplate)  {
        try {
            renderedTemplate = URLEncoder.encode(renderedTemplate,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Response response = client.target(format(botUrl, renderedTemplate)).request(MediaType.APPLICATION_JSON_TYPE).get();

        log.info("Send notification to telegramm {}.", renderedTemplate, response.readEntity(String.class));
    }
}