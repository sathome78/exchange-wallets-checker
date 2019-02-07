package com.example.demo.schedulers.fiatprocessor.payeer;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.example.demo.repository.PayeerRepository;
import com.example.demo.schedulers.fiatprocessor.FiatProcessor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;

@Service
public class PayeerProcessor implements FiatProcessor {

    private final Client client;

    private final AWSSimpleSystemsManagement ssmClient;

    private final PayeerRepository payeerRepository;

    @Value("${payeer.balance.url}")
    private String payeerBalanceURL;

    @Autowired
    public PayeerProcessor(AWSSimpleSystemsManagement ssmClient, Client client, PayeerRepository payeerRepository) {
        this.ssmClient = ssmClient;
        this.client = client;
        this.payeerRepository = payeerRepository;
    }

    public void process() {
        advCashAccountList().forEach(this::getBalance);
    }


    public void getBalance(PayeerAccount payyerAccount) {
        String password = ssmClient.getParameter(createParameterRequest(payyerAccount.getPasswordPath())).getParameter().getValue();
        String apiID = ssmClient.getParameter(createParameterRequest(payyerAccount.getApiID())).getParameter().getValue();
        String account = ssmClient.getParameter(createParameterRequest(payyerAccount.getAccount())).getParameter().getValue();

        MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
        formData.add("account", account);
        formData.add("apiId", apiID);
        formData.add("apiPass", password);

        Response post = client.target(payeerBalanceURL).request(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(Entity.form(formData));
        String responseEntity = post.readEntity(String.class);
        JSONObject jsonObject = new JSONObject(responseEntity);
        JSONObject balance = jsonObject.getJSONObject("balance");

        String usdNewAmount = balance.getJSONObject("USD").getString("BUDGET");
        String eurNewAmount = balance.getJSONObject("EUR").getString("BUDGET");
        String rubNewAmount = balance.getJSONObject("RUB").getString("BUDGET");

        payyerAccount.setEurBalance(eurNewAmount);
        payyerAccount.setRubBalance(rubNewAmount);
        payyerAccount.setUsdBalance(usdNewAmount);


        payeerRepository.save(payyerAccount);
    }

    public List<PayeerAccount> advCashAccountList() {
        return payeerRepository.findAll();
    }

}
