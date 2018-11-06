package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import com.example.demo.util.RequestUtil;
import javafx.util.Pair;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

@Service
@Log4j2
public class EthTokenProcessor implements CoinProcessor {


    @Autowired
    RequestUtil requestUtil;

    public static final String EMPTY_BALANCE = "0";


    public CoinWrapper process(Coin coin) {
        JSONObject jsonObject = requestUtil.getEthTokens();
        JSONObject tokens = jsonObject.getJSONObject("tokens");
        JSONObject ethToken = tokens.getJSONObject(coin.getEthTokenContract());
        String decimal = String.valueOf(ethToken.get("decimals"));
        Map<String, String> collect2 = jsonObject.
                getJSONArray("balances").
                toList().
                stream().
                map(item -> (HashMap<String, Object>) item).
                map(element -> new Pair<>(valueOf(element.get("contract")), valueOf(element.getOrDefault("balance", "0"))))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

        String balance = balance(collect2.get(coin.getEthTokenContract()), decimal);

        return CoinWrapper.builder().coin(coin).actualBalance(new BigDecimal(balance)).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        try {
            String[] split = wallet.split(",");
            JSONObject jsonObject = requestUtil.getEthTokens(split[0]);
            JSONObject tokens = jsonObject.getJSONObject("tokens");
            JSONObject ethToken = tokens.getJSONObject(split[1].toLowerCase());
            String decimal = String.valueOf(ethToken.get("decimals"));
            Map<String, String> collect2 = jsonObject.
                    getJSONArray("balances").
                    toList().
                    stream().
                    map(item -> (HashMap<String, Object>) item).
                    map(element -> new Pair<>(valueOf(element.get("contract")), valueOf(element.getOrDefault("balance", "0"))))
                    .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

            return new BigDecimal(balance(collect2.get(split[1].toLowerCase()), decimal));
        } catch (Exception e) {
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }

    private String balance(String balance, String decimal) {
        if (balance.equals(EMPTY_BALANCE)) {
            return EMPTY_BALANCE;
        }
        if (decimal.equals("0")) {
            return balance;
        }
        if (balance.contains("e") || balance.contains("+")) {
            return calculateWitheBalance(balance, decimal);
        }
        return divide(balance, decimal);
    }

    public String calculateWitheBalance(String balance, String decimal) {
        int indexOfPlusSymbol = balance.indexOf("+");
        String substring = balance.substring(indexOfPlusSymbol + 1);
        int i = Integer.valueOf(substring) - Integer.valueOf(decimal);
        double multiplyer = Math.pow(10.0, i);
        String cleanBalance = balance.substring(0, indexOfPlusSymbol - 1);
        BigDecimal multiply = new BigDecimal(cleanBalance).multiply(new BigDecimal(multiplyer));

        return multiply.toString();
    }

    public String divide(String balance, String decimal) {
        double pow = Math.pow(10.0, Double.valueOf(decimal));
        BigDecimal divide = new BigDecimal(balance).divide(new BigDecimal(pow));
        return divide.toString();
    }
}

