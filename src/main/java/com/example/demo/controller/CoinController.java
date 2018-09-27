package com.example.demo.controller;

import com.example.demo.domain.Coin;
import com.example.demo.domain.PriceStatus;
import com.example.demo.domain.requestbody.CoinBalance;
import com.example.demo.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

import static com.example.demo.domain.requestbody.BalanceType.MAX;
import static com.example.demo.domain.requestbody.BalanceType.MIN;


@Controller
@CrossOrigin(value = {"http://localhost:8080", "http://172.31.3.72:8080/", "http://localhost:63342"})
public class CoinController {

    @Autowired
    CoinRepository coinRepository;

    @GetMapping(value = "/currencies", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(value = {"http://localhost:8080", "http://172.31.3.72:8080/", "http://localhost:63342"})
    public ResponseEntity<Map<Long, Coin>> getCurrencies() {
        Map<Long, Coin> collect = coinRepository.findAll().stream().collect(Collectors.toMap(Coin::getId, item -> item));
        return new ResponseEntity<>(collect, HttpStatus.OK);
    }

    @PutMapping("/currencies/{currencyName}")
    @CrossOrigin(value = {"http://localhost:8080", "http://172.31.3.72:8080/", "http://localhost:63342"})
    public ResponseEntity<Map<String, Object>> updateMinBalance(@PathVariable String currencyName,
                                                                @RequestBody CoinBalance coinBalance) {

        if (coinBalance.getBalanceType() == MIN)
            coinRepository.updateMinLimit(coinBalance.getLimit(), currencyName);
        else if (coinBalance.getBalanceType() == MAX)
            coinRepository.updateMaxLimit(coinBalance.getLimit(), currencyName);

        Coin coin = coinRepository.findByName(currencyName);
        if (coin.getCurrentAmount().compareTo(coin.getMinAmount()) < 0) {
            coin.setPriceStatus(PriceStatus.LOW);
        }

        if (coin.getCurrentAmount().compareTo(coin.getMaxAmount()) > 0) {
            coin.setPriceStatus(PriceStatus.ABOVE);
        }

        if (coin.getCurrentAmount().compareTo(coin.getMinAmount()) > 0 && coin.getCurrentAmount().compareTo(coin.getMaxAmount()) < 0) {
            coin.setPriceStatus(PriceStatus.NORMAL);
        }
        coinRepository.save(coin);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String index() {
        return "index";
    }


}
