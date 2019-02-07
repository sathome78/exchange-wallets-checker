package com.example.demo.schedulers.fiatprocessor.advcash;

import advcash.wsm.*;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.example.demo.repository.AdvCashRepository;
import com.example.demo.schedulers.fiatprocessor.FiatProcessor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.xml.rpc.ServiceException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static advcash.wsm.utils.AuthHelper.getAuthenticationToken;
import static java.lang.String.valueOf;

@Service
@Log4j2
public class AdvCashProcessor implements FiatProcessor {

    @Autowired
    private Client client;

    @Autowired
    private AWSSimpleSystemsManagement ssmClient;

    @Autowired
    private AdvCashRepository advCashRepository;

    public void process() {
        advCashAccountList().forEach(this::getBalance);
    }


    public void getBalance(AdvCashAccount advCash) {

        String password = ssmClient.getParameter(createParameterRequest(advCash.getPasswordPath())).getParameter().getValue();
        String account = ssmClient.getParameter(createParameterRequest(advCash.getAccount())).getParameter().getValue();
        String accountEmail = ssmClient.getParameter(createParameterRequest(advCash.getEmail())).getParameter().getValue();

        MerchantWebService_Service mws_service = new MerchantWebService_ServiceLocator();
        MerchantWebService_PortType mws = null;
        try {
            mws = mws_service.getMerchantWebServicePort();
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        AuthDTO arg0 = new AuthDTO();
        arg0.setApiName(account);
        arg0.setAccountEmail(accountEmail);

        try {
            arg0.setAuthenticationToken(getAuthenticationToken(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            WalletBalanceDTO[] wallets = mws.getBalances(arg0);
            for (WalletBalanceDTO wallet : wallets) {
                if (wallet.getId().contains("U")) {
                    advCash.setUsdBalance(valueOf(wallet.getAmount()));
                }
                if (wallet.getId().contains("E")) {
                    advCash.setEurBalance(valueOf(wallet.getAmount()));
                }
                if (wallet.getId().contains("R")) {
                    advCash.setRubBalance(valueOf(wallet.getAmount()));
                }
            }
        } catch (Exception e) {
            log.warn("Unable to update advcashcoin");
            e.printStackTrace();
        }

        advCashRepository.save(advCash);

    }

    private List<AdvCashAccount> advCashAccountList() {
        return advCashRepository.findAll();
    }

}
