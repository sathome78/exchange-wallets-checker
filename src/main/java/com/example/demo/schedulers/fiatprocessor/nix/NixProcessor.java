package com.example.demo.schedulers.fiatprocessor.nix;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.example.demo.repository.NixRepository;
import com.example.demo.schedulers.fiatprocessor.FiatProcessor;
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
public class NixProcessor implements FiatProcessor {

    private final Client client;

    private final AWSSimpleSystemsManagement ssmClient;

    private final NixRepository payeerRepository;

    @Value("${nix.balance.url}")
    private String nixBalanceUrl;

    @Autowired
    public NixProcessor(AWSSimpleSystemsManagement ssmClient, Client client, NixRepository payeerRepository) {
        this.ssmClient = ssmClient;
        this.client = client;
        this.payeerRepository = payeerRepository;
    }

    public void process() {
        advCashAccountList().forEach(this::getBalance);
    }


    public void getBalance(NixAccount nixAccount) {
        String password = ssmClient.getParameter(createParameterRequest(nixAccount.getPasssword())).getParameter().getValue();
        String email = ssmClient.getParameter(createParameterRequest(nixAccount.getEmail())).getParameter().getValue();

        MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
        formData.add("ACCOUNTID", email);
        formData.add("PASSPHRASE", password);

        Response post = client.target(nixBalanceUrl).request(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(Entity.form(formData));
        String responseEntity = post.readEntity(String.class);


        payeerRepository.save(nixAccount);
    }

    private List<NixAccount> advCashAccountList() {
        return payeerRepository.findAll();
    }
}
