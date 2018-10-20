package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.enums.CoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    @Bean
    public Map<CoinType, CoinProcessor> processorMap() {
        Map<CoinType, CoinProcessor> processorMap = new HashMap<>();
        processorMap.put(CoinType.BTC_COIN, btcProcessor);
        processorMap.put(CoinType.ETH_TOKEN_COIN, ethTokenProcessor);
        processorMap.put(CoinType.NOT_AVAILABLE, notAvailableTokensProcessor);
        processorMap.put(CoinType.ETH, ethProcessor);
        return processorMap;
    }
}
