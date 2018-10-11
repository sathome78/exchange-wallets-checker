package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

@Configuration
public class HttpClientConfiguration {

    @Bean
    public Client client() {
        return ClientBuilder.newClient();
    }
}
