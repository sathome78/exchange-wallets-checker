package com.example.demo.schedulers.fiatprocessor.payeer;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

@Entity
@Table(name = "PAYEER")
@Data
public class PayeerAccount {

    @Column(name = "ID")
    @GeneratedValue
    private int id;
    @Column(name = "PASSWORD_PATH")
    private String passwordPath;
    @Column(name = "ACCOUNT")
    private String account;
    @Column(name = "API_ID")
    private String apiID;
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