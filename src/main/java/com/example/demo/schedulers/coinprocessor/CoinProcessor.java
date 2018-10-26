package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;

import java.math.BigDecimal;

public interface CoinProcessor {
    CoinWrapper process(Coin coin);

    BigDecimal getBalance(Coin coin, String wallet);
}
