package com.example.demo.schedulers.fiatprocessor.perfectmoney;

import lombok.Data;

import javax.persistence.*;

@Table(name = "PERFECT_MONEY")
@Entity
@Data
public class PerfectmoneyAccount {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "ACCOUNT_ID")
    private String accountId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "USD_BALANCE")
    private String usdBalance;

    @Column(name = "EUR_BALANCE")
    private String eurBalance;

    @Column(name = "USD_WALLET_ADDRESS")
    private String usdWalletAddress;

    @Column(name = "EUR_WALLET_ADDRESS")
    private String eurWalletAddress;

    @Column(name = "MAIN")
    private boolean main;

    @Column(name = "ENABLE")
    private boolean enable;
}
