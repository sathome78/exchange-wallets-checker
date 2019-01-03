package com.example.demo.util;

import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Service
@Log4j2
public class RequestUtil {


    @Value("${eth.endpoint.basic}")
    private String ethTokenBaseAddress;

    @Value("${eth.endpoint.etherchain.basic}")
    private String etherchainBasicAddress;

    @Autowired
    private Client client;

    @Cacheable(value = "getEthTokens", key = "#p0")
    public JSONObject getEthTokens(String coinAddress) {
        String url = ethTokenBaseAddress + coinAddress + "&showTx=all";
        try {
            String response = client.target(url).request(MediaType.APPLICATION_JSON_TYPE).get().readEntity(String.class);
            return new JSONObject(response);
        } catch (Exception e) {
            log.warn("Unable to retrieve information about eth token with wallet " + coinAddress);
            return new JSONObject();
        }
    }

    public BigDecimal getEthAddressAmount(String ethAddress) {
        String s = client.target(etherchainBasicAddress + ethAddress).request(MediaType.TEXT_HTML_TYPE).get().readEntity(String.class);
        int i = s.indexOf("<p class=\"mb-4\">");
        String firstSubString = s.substring(i + "<p class=\"mb-4\">".length());
        String eth = firstSubString.substring(0, firstSubString.indexOf(" "));
        return new BigDecimal(eth);
    }


}
