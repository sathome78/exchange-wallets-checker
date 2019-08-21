package com.example.demo.schedulers;

import com.example.demo.domain.Coin;
import com.example.demo.domain.PriceStatus;
import com.example.demo.domain.dto.CoinWrapper;
import com.example.demo.domain.enums.CoinType;
import com.example.demo.repository.CoinRepository;
import com.example.demo.schedulers.coinprocessor.CoinProcessor;
import com.example.demo.schedulers.fiatprocessor.FiatProcessor;
import com.example.demo.util.NumberFormatter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.example.demo.schedulers.NotificatorService.getCurrentDate;
import static java.lang.String.format;
import static java.lang.String.valueOf;

@EnableScheduling
@Service
@Log4j2
public class SchedulerService {

    private final CoinRepository coinRepository;
    private final Map<String, NotificatorService> notificatorServiceMap;
    private final Map<CoinType, CoinProcessor> processorMap;
    private final Map<PriceStatus, String> templatesMap;
    private final Client client;
    private final FiatProcessor payeerProcessor;
    private final FiatProcessor advCashProcessor;
    private final FiatProcessor nixProcessor;
    private final FiatProcessor perfectmoneyProcessor;

    private String currencyUsd;

    @Autowired
    public SchedulerService(CoinRepository coinRepository,
                            Map<String, NotificatorService> notificatorServiceMap,
                            Map<CoinType, CoinProcessor> processorMap,
                            Map<PriceStatus, String> templatesMap,
                            Client client,
                            @Value("${currency.usd.api}") String currencyUsd,
                            FiatProcessor advCashProcessor,
                            FiatProcessor payeerProcessor,
                            FiatProcessor nixProcessor,
                            FiatProcessor perfectmoneyProcessor) {
        this.coinRepository = coinRepository;
        this.notificatorServiceMap = notificatorServiceMap;
        this.processorMap = processorMap;
        this.templatesMap = templatesMap;
        this.client = client;
        this.currencyUsd = currencyUsd;
        this.advCashProcessor = advCashProcessor;
        this.payeerProcessor = payeerProcessor;
        this.nixProcessor = nixProcessor;
        this.perfectmoneyProcessor = perfectmoneyProcessor;
    }

    @Scheduled(fixedDelay = 900000, initialDelay = 0)
    public void allWithoutEthereumTokenCoins() {
        StopWatch stopWatch = StopWatch.createStarted();

        coinRepository.findCoinTypes()
                .parallelStream()
                .map(CoinType::valueOf)
                .forEach(type -> {
                    log.info("Send request with coinType " + type);
                    coinRepository.findByEnableTrueAndCoinType(type)
                            .stream()
                            .map(this::process)
                            .forEach(this::process);
                    log.info("Finish request with coinType  " + type);
                });
        coinRepository.updadateAllCoins(new Date());
        log.info("The process of update all coins has finished, Time (seconds): {}", stopWatch.getTime(TimeUnit.SECONDS));
    }

    @Scheduled(fixedDelay = 900000)
    public void processAdvcash() {
        payeerProcessor.process();
    }

    @Scheduled(fixedDelay = 900000)
    public void processPayeerMoney() {
        advCashProcessor.process();
    }

    @Scheduled(fixedDelay = 900000)
    public void processNixMoney() {
        nixProcessor.process();
    }

    @Scheduled(fixedDelay = 900000)
    public void processPerfectMoney() {
        perfectmoneyProcessor.process();
    }

    public CoinWrapper process(Coin coin) {
        try {
            return processorMap.get(coin.getCoinType()).process(coin);
        } catch (Exception e) {
            log.warn("Unable to retrieve balance of token: {}, address: {}", coin.getName(), coin.getCoinAddress());
            return CoinWrapper.builder()
                    .coin(coin)
                    .actualBalance(coin.getCurrentAmount())
                    .build();
        }
    }

    public void process(CoinWrapper coinWrapper) {
        if (coinWrapper.getCoin().isEnable())
            process(coinWrapper.getCoin(), coinWrapper.getActualBalance());
    }

    public void process(Coin coin, BigDecimal actualBalance) {
        try {
            if (actualBalance.compareTo(coin.getCurrentAmount()) == 0) {
                return;
            }
            coin.setCurrentAmount(actualBalance);
            coin.setAmountInUSD(actualBalance.multiply(coin.getRateToUSD()));
            check(coin, actualBalance);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Coin id " + coin.getName());
        }
    }

    public void check(Coin coin, BigDecimal newAmount) {
        Pair<PriceStatus, Boolean> status = getStatus(coin, newAmount);
        if (status.getValue()) {
            String template = renderTemplate(templatesMap.get(status.getKey()), coin);
            notificatorServiceMap.forEach((s, notificatorService) -> notificatorService.notificate(template));
        }
        coin.setDate(new Date());
        coinRepository.save(coin);
    }

    @Scheduled(fixedDelay = 30000, initialDelay = 0)
    public void updateUSD() {
        Response response = client.target(currencyUsd).request(MediaType.APPLICATION_JSON_TYPE).get();
        String stringResponse = response.readEntity(String.class);
        JSONObject resp = new JSONObject(stringResponse);
        List<Coin> all = coinRepository.findAll();
        all.forEach(coin -> convert(resp, coin));
        coinRepository.saveAll(all);
    }

    private void convert(JSONObject resp, Coin coin) {
        try {
            double usdRate = resp.getJSONObject(coin.getName()).getDouble("usd_rate");
            coin.updateUSDData(usdRate);
        } catch (RuntimeException ignored) {
        }
    }

    private String renderTemplate(String template, Coin coin) {
        return format(
                template,
                coin.getName(),
                getCurrentDate(),
                valueOf(NumberFormatter.format(coin.getCurrentAmount())),
                valueOf(NumberFormatter.format(coin.getAmountInUSD())),
                valueOf(NumberFormatter.format(coin.getMinAmount())),
                valueOf(NumberFormatter.format(coin.getMaxAmount())),
                valueOf(NumberFormatter.format(coin.getMinAmountInUSD())),
                valueOf(NumberFormatter.format(coin.getMaxAmountInUSD()))
        );
    }

    private Pair<PriceStatus, Boolean> getStatus(Coin btcCoin, BigDecimal newAmount) {
        PriceStatus priceStatus = btcCoin.getPriceStatus();
        boolean sendNotification = false;
        if (newAmount.compareTo(btcCoin.getMaxAmount()) > 0) {
            priceStatus = PriceStatus.ABOVE;
            sendNotification = true;
        }

        if (newAmount.compareTo(btcCoin.getMinAmount()) < 0 && !btcCoin.isLowThanMinAmountNotified()) {
            btcCoin.setLowThanMinAmountNotified(true);
            priceStatus = PriceStatus.LOW;
            sendNotification = true;
        }

        if (newAmount.compareTo(btcCoin.getMinAmount()) > 0 && newAmount.compareTo(btcCoin.getMaxAmount()) < 0 && (btcCoin.isLowThanMinAmountNotified() || btcCoin.getPriceStatus() == PriceStatus.ABOVE)) {
            btcCoin.setLowThanMinAmountNotified(false);
            priceStatus = PriceStatus.NORMAL;
            sendNotification = true;
        }

        btcCoin.setPriceStatus(priceStatus);

        return Pair.of(priceStatus, sendNotification);
    }
}