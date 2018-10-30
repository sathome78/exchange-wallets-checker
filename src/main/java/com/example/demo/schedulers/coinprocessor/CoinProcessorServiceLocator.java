package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.PriceStatus;
import com.example.demo.domain.enums.CoinType;
import com.example.demo.schedulers.coinprocessor.btccoinsprocessor.BTCProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.example.demo.schedulers.NotificatorService.*;

@Service
public class CoinProcessorServiceLocator {

    @Autowired
    private CoinProcessor btcProcessor;

    @Autowired
    private CoinProcessor ethTokenProcessor;

    @Autowired
    private CoinProcessor notAvailableTokensProcessor;

    @Autowired
    private CoinProcessor ethProcessor;

    @Autowired
    private CoinProcessor etcCoinProcessor;

    @Autowired
    private CoinProcessor achainCoinProcessor;

    @Autowired
    private CoinProcessor xrpProcessor;

    @Autowired
    private CoinProcessor stellarCoinProcessor;

    @Autowired
    private CoinProcessor xemProcessor;

    @Autowired
    private CoinProcessor xemMosaicProcessor;

    @Bean
    public Map<CoinType, CoinProcessor> processorMap() {
        Map<CoinType, CoinProcessor> processorMap = new HashMap<>();
        processorMap.put(CoinType.BTC_COIN, btcProcessor);
        processorMap.put(CoinType.ETH_TOKEN_COIN, ethTokenProcessor);
        processorMap.put(CoinType.NOT_AVAILABLE, notAvailableTokensProcessor);
        processorMap.put(CoinType.ETH, ethProcessor);
        processorMap.put(CoinType.ETC, etcCoinProcessor);
        processorMap.put(CoinType.ACHAIN, achainCoinProcessor);
        processorMap.put(CoinType.XRP, xrpProcessor);
        processorMap.put(CoinType.STELLAR_COIN, stellarCoinProcessor);
        processorMap.put(CoinType.XEM_COIN, xemProcessor);
        processorMap.put(CoinType.XEM_MOSAIC, xemMosaicProcessor);
        return processorMap;
    }

    @Bean
    public Map<PriceStatus, String> templatesMap() {
        Map<PriceStatus, String> templatesMap = new HashMap<>();
        templatesMap.put(PriceStatus.ABOVE, ABOVE_MAX_LIMIT);
        templatesMap.put(PriceStatus.LOW, LOW_THAN_MIN_AMOUNT);
        templatesMap.put(PriceStatus.NORMAL, PERMISSIBLE_RANGE);
        templatesMap.put(null, ABOVE_MAX_LIMIT);
        return templatesMap;
    }
}
