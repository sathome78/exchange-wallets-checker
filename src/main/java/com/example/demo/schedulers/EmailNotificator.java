package com.example.demo.schedulers;

import com.example.demo.domain.Coin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static java.lang.String.valueOf;

@Service
@Slf4j(topic = "EmailNotificator")
public class EmailNotificator implements NotificatorService {


    @Autowired
    public JavaMailSender emailSender;

    @Value("${spring.mail.recepeintsaddress}")
    private String recepeints;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void notificate(String template, Coin coin) {
        try {
            String messageFormat = format(template, coin.getName(), getCurrentDate(), valueOf(coin.getCurrentAmount()), valueOf(coin.getMinAmount()), valueOf(coin.getMaxAmount()));
            String[] recepients = recepeints.split(",");

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(fromEmail);
            simpleMailMessage.setText(messageFormat);
            simpleMailMessage.setTo(recepients);
            emailSender.send(simpleMailMessage);

            log.info("Send notification email");
        } catch (Exception e) {
            log.error("Some exception");
        }
    }


}
