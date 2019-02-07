package com.example.demo.schedulers.fiatprocessor.advcash;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ADV_CASH")
@Data
public class AdvCashAccount {


    @Column(name = "ID")
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "PASSWORD_PATH")
    private String passwordPath;
    @Column(name = "ACCOUNT")
    private String account;
    @Column(name = "API_ID")
    private String email;
    @Column(name = "USD_BALANCE")
    private String usdBalance;
    @Column(name = "EUR_BALANCE")
    private String eurBalance;
    @Column(name = "RUB_BALANCE")
    private String rubBalance;
    @Column(name = "ENABLE")
    private boolean enable;
    @Column(name = "MAIN")
    private boolean main;

}
