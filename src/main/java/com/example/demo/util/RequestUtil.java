package com.example.demo.util;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;

@Service
public class RequestUtil {


    @Value("${eth.endpoint}")
    private String ethTokenAddress;

    @Autowired
    private Client client;

    @Cacheable("addresses")
    public JSONObject getEthTokens() {
        String response = client.target(ethTokenAddress).request(MediaType.APPLICATION_JSON_TYPE).get().readEntity(String.class);
        return new JSONObject(response);
    }

}
