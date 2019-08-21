package com.example.demo.schedulers.fiatprocessor.perfectmoney;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.example.demo.repository.PerfectmoneyRepository;
import com.example.demo.schedulers.fiatprocessor.FiatProcessor;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
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
    private String perfectMoneyURL;

    @Autowired
    public PerfectmoneyProcessor(AWSSimpleSystemsManagement ssmClient,
                                 Client client,
                                 PerfectmoneyRepository payeerRepository) {
        this.ssmClient = ssmClient;
        this.client = client;
        this.payeerRepository = payeerRepository;
    }

    public void process() {
        advCashAccountList().forEach(this::getBalance);
    }


    public void getBalance(PerfectmoneyAccount perfectmoneyAccount) {
        String password = ssmClient.getParameter(createParameterRequest(perfectmoneyAccount.getPassword())).getParameter().getValue();

        Response post = client.target(String.format(perfectMoneyURL, perfectmoneyAccount.getAccountId(), password)).request().get();
        String responseEntity = post.readEntity(String.class);
        Document document = Jsoup.parse(responseEntity);
        Elements select1 = document.select(String.format("input[name=%s]", perfectmoneyAccount.getFirstWallet()));
        Elements select2 = document.select(String.format("input[name=%s]", perfectmoneyAccount.getSecondWallet()));
        Elements select3 = document.select(String.format("input[name=%s]", perfectmoneyAccount.getThirdWallet()));
        Elements select4 = document.select(String.format("input[name=%s]", perfectmoneyAccount.getFourthWallet()));

        perfectmoneyAccount.setFirstWalletBalance(select1.attr("value"));
        perfectmoneyAccount.setSecondWalletBalance(select2.attr("value"));
        perfectmoneyAccount.setThirdWalletBalance(select3.attr("value"));
        perfectmoneyAccount.setFourthWalletBalance(select4.attr("value"));

        payeerRepository.save(perfectmoneyAccount);
    }

    private List<PerfectmoneyAccount> advCashAccountList() {
        return payeerRepository.findAll();
    }
}