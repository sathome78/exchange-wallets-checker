package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import com.example.demo.util.RequestUtil;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
@Log4j2
public class EthProcessor implements CoinProcessor {

    @Autowired
    RequestUtil requestUtil;

    public CoinWrapper process(Coin coin) {
        BigDecimal ethAddressAmount = requestUtil.getEthAddressAmount(coin.getCoinAddress());
        return CoinWrapper.builder()
                          .coin(coin)
                          .actualBalance(ethAddressAmount)
                          .build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        return requestUtil.getEthAddressAmount(wallet);
    }
}
