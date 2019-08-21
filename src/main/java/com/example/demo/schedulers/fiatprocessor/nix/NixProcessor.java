package com.example.demo.schedulers.fiatprocessor.nix;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.example.demo.repository.NixRepository;
import com.example.demo.schedulers.fiatprocessor.FiatProcessor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
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

    private final NixRepository nixRepository;

    @Value("${nix.balance.url}")
    private String nixBalanceUrl;

    @Autowired
    public NixProcessor(AWSSimpleSystemsManagement ssmClient, Client client, NixRepository payeerRepository) {
        this.ssmClient = ssmClient;
        this.client = client;
        this.nixRepository = payeerRepository;
    }

    public void process() {
        advCashAccountList().forEach(this::getBalance);
    }

    public void getBalance(NixAccount nixAccount) {
        String password = ssmClient.getParameter(createParameterRequest(nixAccount.getPassword())).getParameter().getValue();

        MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
        formData.add("ACCOUNTID", nixAccount.getAccountId());
        formData.add("PASSPHRASE", password);

        Response post = client.target(nixBalanceUrl).request(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(Entity.form(formData));

        String responseEntity = post.readEntity(String.class);
        Document document = Jsoup.parse(responseEntity);
        Elements select1 = document.select(String.format("input[name=%s]", nixAccount.getFirstWallet()));
        Elements select2 = document.select(String.format("input[name=%s]", nixAccount.getSecondWallet()));
        Elements select3 = document.select(String.format("input[name=%s]", nixAccount.getThirdWallet()));
        Elements select4 = document.select(String.format("input[name=%s]", nixAccount.getFourthWallet()));

        nixAccount.setFirstWalletBalance(select1.attr("value"));
        nixAccount.setSecondWalletBalance(select2.attr("value"));
        nixAccount.setThirdWalletBalance(select3.attr("value"));
        nixAccount.setFourthWalletBalance(select4.attr("value"));

        nixRepository.save(nixAccount);
    }

    private List<NixAccount> advCashAccountList() {
        return nixRepository.findAll();
    }
}