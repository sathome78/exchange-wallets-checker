package com.example.demo.schedulers;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component

@Slf4j
public class SlackNotificatorService implements NotificatorService {

    @Autowired
    Client client;

    @Value("${slack.api.base.url}")
    private String slackBaseUrl;

    @Value("${slack.bot.access.token}")
    private String botAccessToken;

    @Override
    public void notificate(String renderedTemplate){
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("channel", "alarm");
            jsonObject.put("text", renderedTemplate);

            Response response = client.
                    target(slackBaseUrl).
                    request(MediaType.APPLICATION_JSON_TYPE).
                    header("Authorization", "Bearer " + botAccessToken).
                    get();

            String responseEntity = response.readEntity(String.class);
            log.info("Post message to slack chat . Response is {}",  responseEntity);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
