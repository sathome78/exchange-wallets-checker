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

    @Autowired
    RequestUtil requestUtil;

    public CoinWrapper process(Coin coin) {
        BigDecimal tokenValue = requestUtil.getTokenValue(coin.getCoinAddress(), coin.getName());
        return CoinWrapper.builder().coin(coin).actualBalance(tokenValue).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        return requestUtil.getTokenValue(wallet, coin.getName());
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

