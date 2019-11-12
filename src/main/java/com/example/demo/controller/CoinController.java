package com.example.demo.controller;

import com.example.demo.domain.Coin;
import com.example.demo.domain.PriceStatus;
import com.example.demo.domain.dto.CoinCsvDto;
import com.example.demo.domain.dto.CoinDto;
import com.example.demo.domain.dto.CoinWrapper;
import com.example.demo.domain.dto.ReservedWalletDto;
import com.example.demo.domain.enums.CoinType;
import com.example.demo.domain.requestbody.CoinBalance;
import com.example.demo.repository.CoinRepository;
import com.example.demo.schedulers.SchedulerService;
import com.example.demo.schedulers.coinprocessor.CoinProcessor;
import com.example.demo.schedulers.coinprocessor.CoinProcessorServiceLocator;
import com.google.common.base.Strings;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.demo.domain.requestbody.BalanceType.MAX;
import static com.example.demo.domain.requestbody.BalanceType.MIN;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;


@Controller
@CrossOrigin(value = {"http://localhost:8080", "http://172.31.3.72:8080/"})
@Log4j2
public class CoinController {

    private final CoinRepository coinRepository;

    private final SchedulerService schedulerService;

    private final CoinProcessorServiceLocator coinProcessorServiceLocator;

    @Autowired
    public CoinController(CoinRepository coinRepository, SchedulerService schedulerService, CoinProcessorServiceLocator coinProcessorServiceLocator) {
        this.coinRepository = coinRepository;
        this.schedulerService = schedulerService;
        this.coinProcessorServiceLocator = coinProcessorServiceLocator;
    }

    @GetMapping(value = "/currencies", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<CoinDto>> getCurrencies() {
        List<CoinDto> name = coinRepository.findByEnableTrueAndMainTrue(Sort.by(Sort.Direction.ASC, "name")).
                stream().
                map(CoinDto::new).
                collect(Collectors.toList());

        return new ResponseEntity<>(name, HttpStatus.OK);
    }

    @GetMapping(value = "/process/{coinType}")
    public ResponseEntity<String> processWallets(@PathVariable("coinType") CoinType coinType) {
        List<CoinWrapper> collect = coinRepository.findByEnableTrueAndCoinType(coinType).stream().map(schedulerService::process).collect(toList());
        collect.forEach(schedulerService::process);
        return new ResponseEntity<>("{\"status\":\"success\"}", HttpStatus.OK);
    }

    @GetMapping(value = "/process")
    public ResponseEntity<String> processAll() {
        List<CoinWrapper> collect = coinRepository.findByEnableTrue().stream().map(schedulerService::process).collect(toList());
        collect.forEach(schedulerService::process);
        coinRepository.updadateAllCoins(new Date());
        log.info("Update all coins");
        return new ResponseEntity<>("{\"status\":\"success\"}", HttpStatus.OK);
    }

    @PostMapping(value = "/currencies/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> addWallet(@RequestBody ReservedWalletDto request) {
        log.info("Request body is " + request);
        final String ticker = request.getTicker().trim();
        final String address = request.getAddress().trim();
        final String ethContract = Optional.ofNullable(request.getEthContract()).orElse("");

        List<Coin> byName = coinRepository.findAllByName(ticker);

        Coin coin = byName.stream().findFirst().get();

        Coin coinBase = Coin.builder().
                name(ticker).
                priceStatus(PriceStatus.NORMAL).
                detailName(ticker).
                coinAddress(address).
                ethTokenContract(ethContract).
                coinType(coin.getCoinType()).
                main(false).
                rateToUSD(coin.getRateToUSD()).
                amountInUSD(new BigDecimal(0)).
                minAmountInUSD(new BigDecimal(-99999)).
                maxAmountInUSD(new BigDecimal(999999999)).
                currentAmount(new BigDecimal(0)).
                maxAmount(new BigDecimal(999999999)).minAmount(new BigDecimal(-99999)).
                enable(true).build();

        coinRepository.save(coinBase);

        return ResponseEntity.ok(Boolean.TRUE);
    }

    @DeleteMapping(value = "/currencies/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteWallet(@RequestBody ReservedWalletDto request) {

        final String ticker = request.getTicker();
        final String address = request.getAddress();
        final String ethContract = request.getEthContract();

        coinRepository.deleteByNameAndEthTokenContractAndCoinAddressAndMain(ticker, ethContract, address, false);

        return ResponseEntity.ok(Boolean.TRUE);
    }


    @GetMapping(value = "/currencies/{currencyTiker}/balance")
    public ResponseEntity<Map<String, Object>> getBalanceByWallet(@PathVariable String currencyTiker,
                                                                  @RequestParam(value = "wallet") String wallet,
                                                                  @RequestParam(value = "eth_contract", required = false) String ethContract) {
        List<Coin> coin = coinRepository.findAllByName(currencyTiker);
        CoinProcessor coinProcessor = coinProcessorServiceLocator.processorMap().getOrDefault(coin.stream().findFirst().get().getCoinType(), null);
        if (coinProcessor == null) {
            Map<String, Object> objectObjectMap = new HashMap<>();
            objectObjectMap.put("balance", 0);
            return new ResponseEntity<>(objectObjectMap, HttpStatus.OK);
        }
        if (!Strings.isNullOrEmpty(ethContract)) wallet = wallet.trim() + "," + ethContract.trim();
        BigDecimal balance = coinProcessor.getBalance(coin.get(0), wallet);
        Map<String, Object> response = new HashMap<>();
        response.put("balance", Optional.ofNullable(balance).orElse(new BigDecimal(0)));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/currencies/balance/reserved")
    public ResponseEntity<Map<String, Object>> responseEntity() {
        Map<String, Object> response = new HashMap<>();
        List<Coin> byMainFalse = coinRepository.findByMainFalse();
        byMainFalse.forEach(coin -> {
            String coinAddress = coin.getCoinAddress() == null ? "" : coin.getCoinAddress();
            String ethTokenContract = coin.getEthTokenContract() == null ? "" : coin.getEthTokenContract();
            String date = coin.getDate() == null ? "" : String.valueOf(coin.getDate());

            String key = String.join("||", coin.getName(), coinAddress, ethTokenContract, date);
            key = key.endsWith("||") ? key.substring(0, key.length() - 2) : key;

            response.put(key, coin.getCurrentAmount());
        });

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

    @PutMapping("/currencies/{currencyName}")
    public ResponseEntity<Map<String, Object>> updateBalance(@PathVariable String currencyName,
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
        coin.setDate(new Date());
        coinRepository.save(coin);
        schedulerService.check(coin, coin.getCurrentAmount());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/currencies")
    public ResponseEntity<Map<String, Object>> updateWithFile(@RequestParam("file") MultipartFile file) throws IOException {
        String string = IOUtils.toString(file.getInputStream(), "UTF-8");

        List<String[]> csvRow = stream(string.split("\\r?\\n")).
                map(unit -> unit.split(";")).
                collect(Collectors.toList());
        csvRow.remove(0);
        Map<String, String[]> collect = csvRow.
                stream().
                collect(toMap(element -> element[1], element -> element));

        List<String> listOfCoins = csvRow.stream().map(row -> row[1]).collect(Collectors.toList());

        List<Coin> coins = coinRepository.findByNameInNames(listOfCoins);

        coins.forEach(coin -> coin.updateBalanceAndLimits(collect.get(coin.getName())));

        coinRepository.saveAll(coins);

        return new ResponseEntity<>(Collections.emptyMap(), HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String index() {
        return "index";
    }


    private String getCoinsCSV() {
        List<Coin> result = coinRepository.findAll();
        return result.stream().map(CoinCsvDto::new).map(CoinCsvDto::toString)
                .collect(Collectors.joining("", CoinCsvDto.getFullTitle(), ""));
    }

    private String getCoinsCSVWithoutUSD() {
        List<Coin> result = coinRepository.findAll();
        return result.stream().map(CoinCsvDto::new).map(CoinCsvDto::toStringWithoutUSD)
                .collect(Collectors.joining("", CoinCsvDto.getTitleWithoutUSD(), ""));
    }

}
