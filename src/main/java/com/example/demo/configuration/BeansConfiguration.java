package com.example.demo.configuration;

import com.example.demo.schedulers.coinprocessor.btccoinsprocessor.BTCProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BeansConfiguration {

    @Autowired
    private BTCProcessor lbtcCoinProcessor;

    @Autowired
    private BTCProcessor ftoCoinProcessor;

    @Autowired
    private BTCProcessor bcxCoinProcessor;

    @Autowired
    private BTCProcessor btcDiamondCoinProcessor;

    @Autowired
    private BTCProcessor btgCoinProcessor;

    @Autowired
    private BTCProcessor btxCoinProcessor;

    @Autowired
    private BTCProcessor eqlCoinProcessor;

    @Autowired
    private BTCProcessor bciCoinProcessor;

    @Bean
    @Order(999)
    public Map<String, BTCProcessor> btcProcessorMap() {
        Map<String, BTCProcessor> btcProcessorMap = new HashMap<>();
        btcProcessorMap.put("LBTC", lbtcCoinProcessor);
        btcProcessorMap.put("FTO", ftoCoinProcessor);
        btcProcessorMap.put("EQL", eqlCoinProcessor);
        btcProcessorMap.put("BTX", btxCoinProcessor);
        btcProcessorMap.put("BTG", btgCoinProcessor);
        btcProcessorMap.put("BCD", btcDiamondCoinProcessor);
        btcProcessorMap.put("BCX", bcxCoinProcessor);
        btcProcessorMap.put("BCI", bciCoinProcessor);
        return btcProcessorMap;
    }

}
