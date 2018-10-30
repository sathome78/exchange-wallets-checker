package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import com.example.demo.util.RequestUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository

public class EthProcessor implements CoinProcessor {

    @Autowired
    RequestUtil requestUtil;

    public CoinWrapper process(Coin coin) {
        JSONObject response = requestUtil.getEthTokens();
        BigDecimal actualBalance = response.getBigDecimal("balance");

        return CoinWrapper.builder().coin(coin).actualBalance(actualBalance).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        JSONObject ethTokens = requestUtil.getEthTokens(wallet);

        return ethTokens.getBigDecimal("balance");
    }
}
