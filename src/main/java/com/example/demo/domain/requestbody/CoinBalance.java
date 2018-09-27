package com.example.demo.domain.requestbody;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CoinBalance {

    private BigDecimal limit;

    private BalanceType balanceType;

}
