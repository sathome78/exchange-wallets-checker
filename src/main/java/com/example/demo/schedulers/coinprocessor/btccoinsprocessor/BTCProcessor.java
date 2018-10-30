package com.example.demo.schedulers.coinprocessor.btccoinsprocessor;

import com.example.demo.domain.Coin;

import java.math.BigDecimal;

public interface BTCProcessor {

    BigDecimal getBalance(Coin coin, String wallet);
}
