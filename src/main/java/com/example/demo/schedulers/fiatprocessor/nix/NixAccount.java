package com.example.demo.schedulers.fiatprocessor.nix;

import lombok.Data;

import javax.persistence.*;

@Table(name = "NIX")
@Entity
@Data
public class NixAccount {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String passsword;

    @Column(name = "USD_BALANCE")
    private String usdBalance;
    @Column(name = "EUR_BALANCE")
    private String eurBalance;
    @Column(name = "RUB_BALANCE")
    private String rubBalance;
    @Column(name = "MAIN")
    private boolean main;
    @Column(name = "ENABLE")
    private boolean enable;


}
