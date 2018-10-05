package com.example.demo.controller;

import com.example.demo.domain.Coin;
import com.example.demo.domain.PriceStatus;
import com.example.demo.domain.requestbody.CoinBalance;
import com.example.demo.repository.CoinRepository;
import com.example.demo.schedulers.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.example.demo.domain.requestbody.BalanceType.MAX;
import static com.example.demo.domain.requestbody.BalanceType.MIN;


@Controller
@CrossOrigin(value = {"http://localhost:8080", "http://172.31.3.72:8080/", "http://localhost:63342"})
public class CoinController {

    private final CoinRepository coinRepository;

    private final SchedulerService schedulerService;

    @Autowired
    public CoinController(CoinRepository coinRepository, SchedulerService schedulerService) {
        this.coinRepository = coinRepository;
        this.schedulerService = schedulerService;
    }

    @GetMapping(value = "/currencies", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(value = {"http://localhost:8080", "http://172.31.3.72:8080/", "http://localhost:63342"})
    public ResponseEntity<List<Coin>> getCurrencies() {
        List<Coin> all = coinRepository.findAll(Sort.by(Sort.Direction.ASC,"name"));
        return new ResponseEntity<>(all, HttpStatus.OK);
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
        schedulerService.process(coin);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String index() {
        return "index";
    }


}
