package com.example.demo.domain.enums;

public enum CoinType {
    BTC_COIN(0),
    ETH_TOKEN_COIN(1),
    NOT_AVAILABLE(2),
    ETH(3), ETC(4),
    ACHAIN(5),
    XRP(6),
    STELLAR_COIN(7),
    XEM_COIN(8),
    XEM_MOSAIC(9),
    FIAT(10);

    private final int i;

    CoinType(int i) {
        this.i = i;
    }
}
