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
    FIAT(10),
    WAVES(11),
    WAVES_COIN(12),
    TRON(13),
    QTUM(14),
    NTY(15),
    DCR(16),
    CLO(17),
    IOTA(18),
    LSK(19),
    ETZ(20),
    APL(21),
    KAZE(22),
    STREAM(23),
    ETI(24),
    LUNES(25),
    GOL(26),
    ARK(27),
    RISE(28),
    QTUM_TOKEN_COIN(29),
    NEO(30),
    GAS(31), EDR(32),
    USDT(33),
    PAYEER(34),
    CREA(35),
    CASINO_COIN(36),
    PPY(37),
    TRON_TOKEN_COIN(38),
    BTW(39);

    private final int i;

    CoinType(int i) {
        this.i = i;
    }
}
