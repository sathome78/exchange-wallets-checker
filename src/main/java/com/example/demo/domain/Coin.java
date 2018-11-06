package com.example.demo.domain;

import com.example.demo.domain.enums.CoinType;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "COIN")
@Entity
@Data
public class Coin {

    @Id
    @Column(name = "ID")
    private long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "CURRENT_AMOUNT")
    private BigDecimal currentAmount;
    @Column(name = "MIN_AMOUNT")
    private BigDecimal minAmount;
    @Column(name = "MAX_AMOUNT")
    private BigDecimal maxAmount;
    @Column(name = "LOW_THAN_MIN_AMOUNT_NOTIFIED")
    private boolean lowThanMinAmountNotified;
    @Column(name = "DETAIL_NAME")
    private String detailName;
    @Column(name = "PRICE_STATUS")
    @Enumerated(EnumType.STRING)
    private PriceStatus priceStatus;
    @Column(name = "COIN_IMAGE")
    private String coinImage;
    @Column(name = "COIN_TYPE")
    @Enumerated(EnumType.STRING)
    private CoinType coinType;

    @Column(name = "ETH_TOKEN_CONTRACT")
    private String ethTokenContract;

    @Column(name = "ENABLE")
    private boolean enable;

    @Column(name = "RATE_TO_USD")
    private BigDecimal rateToUSD;

    @Column(name = "MIN_AMOUNT_USD")
    private BigDecimal minAmountInUSD;

    @Column(name = "MAX_AMOUNT_USD")
    private BigDecimal maxAmountInUSD;

    @Column(name = "AMOUNT_IN_USD")
    private BigDecimal amountInUSD;

    public void updateUSDData(double coinUSDRates) {
        this.rateToUSD = new BigDecimal(coinUSDRates);
        this.amountInUSD = this.currentAmount.multiply(this.rateToUSD);
        this.minAmountInUSD = this.minAmount.multiply(this.rateToUSD);
        this.maxAmountInUSD = this.maxAmount.multiply(this.rateToUSD);
    }

    public void updateBalanceAndLimits(String[] data){
        this.minAmount = new BigDecimal(data[1]);
        this.maxAmount = new BigDecimal(data[2]);
        this.minAmountInUSD = this.minAmount.multiply(this.rateToUSD);
        this.maxAmountInUSD = this.maxAmount.multiply(this.rateToUSD);

    }

    public Coin(String[] csvRow){
        this.name = csvRow[1];

    }
}
