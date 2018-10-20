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

}
