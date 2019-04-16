package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import com.example.demo.util.RequestUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Log4j2
public class EthTokenProcessor implements CoinProcessor {

    private final RequestUtil requestUtil;

    @Autowired
    public EthTokenProcessor(RequestUtil requestUtil) {
        this.requestUtil = requestUtil;
    }

    public CoinWrapper process(Coin coin) {
        BigDecimal tokenValue = requestUtil.getTokenValue(coin.getCoinAddress(), coin.getEthTokenContract(), coin.getName());
        return CoinWrapper.builder().coin(coin).actualBalance(tokenValue).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        return requestUtil.getTokenValue(wallet, coin.getEthTokenContract(), coin.getName());
    }
}

