package com.example.demo.domain.dto;

import com.example.demo.domain.Coin;
import com.example.demo.domain.PriceStatus;
import lombok.Data;

import static com.example.demo.util.NumberFormatter.format;

@Data
public class CoinDto {

    private long id;
    private String name;
    private String currentAmount;
    private String minAmount;
    private String maxAmount;
    private boolean lowThanMinAmountNotified;
    private String detailName;
    private PriceStatus priceStatus;
    private String coinImage;

    public CoinDto(Coin coin) {
        this.id = coin.getId();
        this.name = coin.getName();
        this.currentAmount = format(coin.getCurrentAmount());
        this.minAmount = coin.getMinAmount().toString();
        this.maxAmount = coin.getMaxAmount().toString();
        this.lowThanMinAmountNotified = coin.isLowThanMinAmountNotified();
        this.detailName = coin.getDetailName();
        this.priceStatus = coin.getPriceStatus();
        this.coinImage = coin.getCoinImage();
    }
}
