package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class NotAvailableTokensProcessor implements CoinProcessor {
    
    @Override
    public CoinWrapper process(Coin coin) {
        return CoinWrapper.builder().coin(coin).actualBalance(new BigDecimal(0)).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        return null;
    }
}