package com.example.demo.domain.dto;

import com.example.demo.domain.Coin;
import lombok.Data;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class CoinCsvDto {

    private final long id;
    private final String name;
    private final BigDecimal currentAmount;
    private final BigDecimal maxAmount;
    private final BigDecimal maxAmountInUSD;
    private final BigDecimal minAmountInUSD;
    private final BigDecimal amountInUSD;
    private final BigDecimal minAmount;

    public CoinCsvDto(Coin coin) {
        this.id = coin.getId();
        this.name = coin.getName();
        this.currentAmount = coin.getCurrentAmount();
        this.maxAmount = coin.getMaxAmount();
        this.minAmount = coin.getMinAmount();
        this.amountInUSD = coin.getAmountInUSD();
        this.minAmountInUSD = coin.getMinAmountInUSD();
        this.maxAmountInUSD = coin.getMaxAmountInUSD();

    }

    public static String getFullTitle() {
        return Stream.of("id.", "name", "balance", "min_limit", "max_limit", "balance_in_$", "min_balance_in_$", "max_balance_in_$")
                .collect(Collectors.joining(";", "", "\r\n"));
    }

    public static String getTitleWithoutUSD() {
        return Stream.of("id.", "name", "balance", "min_limit", "max_limit")
                .collect(Collectors.joining(";", "", "\r\n"));
    }

    public String toStringWithoutUSD() {
        return Stream.of(
                String.valueOf(this.id),
                String.valueOf(this.name),
                String.valueOf(this.currentAmount),
                String.valueOf(this.minAmount),
                String.valueOf(this.maxAmount)
        ).collect(Collectors.joining(";", "", "\r\n"));
    }

    public String toString() {
        return Stream.of(
                String.valueOf(this.id),
                String.valueOf(this.name),
                String.valueOf(this.currentAmount),
                String.valueOf(this.minAmount),
                String.valueOf(this.maxAmount),
                String.valueOf(this.amountInUSD),
                String.valueOf(this.minAmountInUSD),
                String.valueOf(this.maxAmountInUSD)
        ).collect(Collectors.joining(";", "", "\r\n"));
    }
}
