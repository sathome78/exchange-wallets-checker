package com.example.demo.domain.dto;

import com.example.demo.domain.Coin;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CoinWrapper {
    private Coin coin;

    private BigDecimal actualBalance;
}
