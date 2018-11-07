package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.dto.CoinWrapper;
import com.example.demo.util.RequestUtil;
import javafx.util.Pair;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EthTokenProcessorTest {

    @Autowired
    private RequestUtil requestUtil;

    @Test
    public void process() {
        JSONObject jsonObject = requestUtil.getEthTokens();
        JSONObject tokens = jsonObject.getJSONObject("tokens");
        JSONObject ethToken = tokens.getJSONObject("0x5ce8e61f28f5948de4913bcaada90039481f1f53");
        Map<String, String> collect2 = jsonObject.
                getJSONArray("balances").
                toList().
                stream().
                map(item -> (HashMap<String, Object>) item).
                map(element -> new Pair<>(valueOf(element.get("contract")), valueOf(element.getOrDefault("balance", "0"))))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

        String s = collect2.get("0x5cE8e61f28f5948dE4913bcaaDA90039481f1f53");
        System.out.println(s);

    }

    @Test
    public void getBalance() {
    }


}