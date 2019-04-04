package com.example.demo.domain;

import com.example.demo.domain.enums.CoinType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "COIN")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
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

    @Column(name = "MAIN")
    private boolean main;

    @Column(name = "COIN_ADDRESS")
    private String coinAddress;

    @Column(name = "LAST_UPDATED_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "LAST_UPDATED_SCHEDULE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduleDate;

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

    public Coin(){}
}
