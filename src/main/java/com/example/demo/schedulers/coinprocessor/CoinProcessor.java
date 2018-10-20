package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;

public interface CoinProcessor {
    CoinWrapper process(Coin coin);
}
