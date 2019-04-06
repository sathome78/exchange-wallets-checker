package com.example.demo.util;

import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

import static java.util.Objects.nonNull;

@Service
@Log4j2
public class RequestUtil {


    @Value("${eth.endpoint.basic}")
    private String ethTokenBaseAddress;

    @Value("${eth.endpoint.etherchain.basic}")
    private String etherchainBasicAddress;

    @Value("${eth.endpoint.token.basic}")
    private String ethTokenBasicAddress;

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

    public BigDecimal getTokenValue(String ethAddress, String ethContract, String tokenName) {
        final String query = nonNull(ethContract) && !ethContract.isEmpty() ? ethContract : tokenName;
        Response response = client.target(String.format(ethTokenBasicAddress, ethAddress, query)).request(MediaType.TEXT_HTML_TYPE).get();
        String jsonResponse = response.readEntity(String.class);
        String layout = new JSONObject(jsonResponse).getString("layout");
        int i = layout.indexOf("</span></div></td><td>");
        String firstSubstring;
        if (i == -1) {
            i = layout.indexOf("-</span></td><td>");
            firstSubstring = layout.substring(i + "-</span></td><td>".length());
        } else {
            firstSubstring = layout.substring(i + "</span></div></td><td>".length());
        }
        String ethToken = firstSubstring.substring(0, firstSubstring.indexOf(" ")).replaceAll(",", "");
        return new BigDecimal(ethToken);
    }

}
