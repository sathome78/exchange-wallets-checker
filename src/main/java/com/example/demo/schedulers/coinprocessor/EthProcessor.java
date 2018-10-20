package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import com.example.demo.util.RequestUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository

public class EthProcessor implements CoinProcessor {

    @Autowired
    RequestUtil requestUtil;

    public CoinWrapper process(Coin coin) {
        JSONObject response = requestUtil.getEthTokens();

        return CoinWrapper.builder().coin(coin).actualBalance(null).build();
    }
}
