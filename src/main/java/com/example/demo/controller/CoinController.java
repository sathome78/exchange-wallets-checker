package com.example.demo.controller;

import com.example.demo.domain.Coin;
import com.example.demo.domain.PriceStatus;
import com.example.demo.domain.dto.CoinCsvDto;
import com.example.demo.domain.dto.CoinDto;
import com.example.demo.domain.requestbody.CoinBalance;
import com.example.demo.exceptions.UnsupportedCoinType;
import com.example.demo.repository.CoinRepository;
import com.example.demo.schedulers.SchedulerService;
import com.example.demo.schedulers.coinprocessor.CoinProcessor;
import com.example.demo.schedulers.coinprocessor.CoinProcessorServiceLocator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.demo.domain.requestbody.BalanceType.MAX;
import static com.example.demo.domain.requestbody.BalanceType.MIN;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;


@Controller
@CrossOrigin(value = {"http://localhost:8080", "http://172.31.3.72:8080/"})
@Log4j2
public class CoinController {

    private final CoinRepository coinRepository;

    private final SchedulerService schedulerService;

    @Autowired
    private CoinProcessorServiceLocator coinProcessorServiceLocator;

    @Autowired
    public CoinController(CoinRepository coinRepository, SchedulerService schedulerService) {
        this.coinRepository = coinRepository;
        this.schedulerService = schedulerService;
    }

    @GetMapping(value = "/currencies", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<CoinDto>> getCurrencies() {
        List<CoinDto> name = coinRepository.findByEnableTrue(Sort.by(Sort.Direction.ASC, "name")).
                stream().
                map(CoinDto::new).
                collect(Collectors.toList());

        return new ResponseEntity<>(name, HttpStatus.OK);
    }

    @GetMapping(value = "/currencies/{currencyTiker}/balance")
    public ResponseEntity<Map<String, Object>> getBalanceByWallet(@PathVariable String currencyTiker, @RequestParam("wallet") String wallet) {
        Coin coin = coinRepository.findByName(currencyTiker);
        CoinProcessor coinProcessor = coinProcessorServiceLocator.processorMap().getOrDefault(coin.getCoinType(), null);
        if (coinProcessor == null) {
            throw new UnsupportedCoinType("Coin is " + currencyTiker + " . Wallet is " + wallet);
        }
        BigDecimal balance = coinProcessor.getBalance(coin, wallet);
        Map<String, Object> response = new HashMap<>();
        response.put("balance", balance);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/currencies/load", produces = APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<String> loadCSV(@RequestParam("usd") boolean withUsd) {
        if (withUsd) {
            return new ResponseEntity<>(getCoinsCSV(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(getCoinsCSVWithoutUSD(), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/currencies/")

    @PutMapping("/currencies/{currencyName}")
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
        schedulerService.check(coin, coin.getCurrentAmount());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String index() {
        return "index";
    }


    public String getCoinsCSV() {
        List<Coin> result = coinRepository.findAll();
        return result.stream().map(CoinCsvDto::new).map(CoinCsvDto::toString)
                .collect(Collectors.joining("", CoinCsvDto.getFullTitle(), ""));
    }

    public String getCoinsCSVWithoutUSD() {
        List<Coin> result = coinRepository.findAll();
        return result.stream().map(CoinCsvDto::new).map(CoinCsvDto::toStringWithoutUSD)
                .collect(Collectors.joining("", CoinCsvDto.getTitleWithoutUSD(), ""));
    }

}
