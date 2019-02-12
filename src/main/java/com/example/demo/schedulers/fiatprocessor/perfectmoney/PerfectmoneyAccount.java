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

    @Column(name = "MAIN")
    private boolean main;

    @Column(name = "ENABLE")
    private boolean enable;

    @Column(name = "FIRST_WALLET")
    private String firstWallet;

    @Column(name = "SECOND_WALLET")
    private String secondWallet;

    @Column(name = "THIRD_WALLET")
    private String thirdWallet;

    @Column(name = "FOURTH_WALLET")
    private String fourthWallet;

    @Column(name = "FIRST_WALLET_BALANCE")
    private String firstWalletBalance;

    @Column(name = "SECOND_WALLET_BALANCE")
    private String secondWalletBalance;

    @Column(name = "THIRD_WALLET_BALANCE")
    private String thirdWalletBalance;

    @Column(name = "FOURTH_WALLET_BALANCE")
    private String fourthWalletBalance;

}
