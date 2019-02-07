package com.example.demo.schedulers.fiatprocessor.perfectmoney;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.example.demo.repository.PerfectmoneyRepository;
import com.example.demo.schedulers.fiatprocessor.FiatProcessor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.util.List;

@Service
public class PerfectmoneyProcessor implements FiatProcessor {


    private final Client client;

    private final AWSSimpleSystemsManagement ssmClient;

    private final PerfectmoneyRepository payeerRepository;

    @Value("${perfectmoney.balance.url}")
    private String perfectMoneyRepository;

    @Autowired
    public PerfectmoneyProcessor(AWSSimpleSystemsManagement ssmClient, Client client, PerfectmoneyRepository payeerRepository) {
        this.ssmClient = ssmClient;
        this.client = client;
        this.payeerRepository = payeerRepository;
    }

    public void process() {
        advCashAccountList().forEach(this::getBalance);
    }


    public void getBalance(PerfectmoneyAccount perfectmoneyAccount) {
        String password = ssmClient.getParameter(createParameterRequest(perfectmoneyAccount.getPassword())).getParameter().getValue();
        String apiID = ssmClient.getParameter(createParameterRequest(perfectmoneyAccount.getAccountId())).getParameter().getValue();

        Response post = client.target(String.format(perfectMoneyRepository, apiID, password)).request().get();
        String responseEntity = post.readEntity(String.class);
        JSONObject jsonObject = new JSONObject(responseEntity);
        JSONObject balance = jsonObject.getJSONObject("balance");
        //TODO
        String usdNewAmount = balance.getJSONObject("USD").getString("BUDGET");
        String eurNewAmount = balance.getJSONObject("EUR").getString("BUDGET");

        perfectmoneyAccount.setEurBalance(eurNewAmount);
        perfectmoneyAccount.setUsdBalance(usdNewAmount);

        payeerRepository.save(perfectmoneyAccount);
    }

    private List<PerfectmoneyAccount> advCashAccountList() {
        return payeerRepository.findAll();
    }


}
