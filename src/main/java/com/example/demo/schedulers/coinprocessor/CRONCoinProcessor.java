package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("cronCoinProcessor")
public class CRONCoinProcessor implements CoinProcessor {

    @Value("${cron.endpoint.basic}")
    private String cronBasicEndpoint;

    @Override
    public CoinWrapper process(Coin coin) {
        final BigDecimal actualBalance = getBalance(coin, coin.getCoinAddress());

        return CoinWrapper.builder().coin(coin).actualBalance(actualBalance).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String coinAddress) {
        return BigDecimal.ZERO;
    }
}