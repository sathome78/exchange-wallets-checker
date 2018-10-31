package com.example.demo.configuration;

import com.example.demo.schedulers.coinprocessor.btccoinsprocessor.BTCGenericProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BeansConfiguration {

    @Autowired
    private BTCGenericProcessor lbtcCoinProcessor;

    @Autowired
    private BTCGenericProcessor ftoCoinProcessor;

    @Autowired
    private BTCGenericProcessor bcxCoinProcessor;

    @Autowired
    private BTCGenericProcessor btcDiamondCoinProcessor;

    @Autowired
    private BTCGenericProcessor btgCoinProcessor;

    @Autowired
    private BTCGenericProcessor btxCoinProcessor;

    @Autowired
    private BTCGenericProcessor eqlCoinProcessor;

    @Autowired
    private BTCGenericProcessor bciCoinProcessor;

    @Autowired
    private BTCGenericProcessor bchCoinProcessor;

    @Autowired
    private BTCGenericProcessor atbCoinProcessor;

    @Autowired
    private BTCGenericProcessor xfcCoinProcessor;

    @Autowired
    private BTCGenericProcessor dashCoinProcessor;

    @Bean
    @Order(999)
    public Map<String, BTCGenericProcessor> btcProcessorMap() {
        Map<String, BTCGenericProcessor> btcProcessorMap = new HashMap<>();
        btcProcessorMap.put("LBTC", lbtcCoinProcessor);
        btcProcessorMap.put("FTO", ftoCoinProcessor);
        btcProcessorMap.put("EQL", eqlCoinProcessor);
        btcProcessorMap.put("BTX", btxCoinProcessor);
        btcProcessorMap.put("BTG", btgCoinProcessor);
        btcProcessorMap.put("BCD", btcDiamondCoinProcessor);
        btcProcessorMap.put("BCX", bcxCoinProcessor);
        btcProcessorMap.put("BCI", bciCoinProcessor);
        btcProcessorMap.put("BCH", bchCoinProcessor);
        btcProcessorMap.put("ATB", atbCoinProcessor);
        btcProcessorMap.put("XFC", xfcCoinProcessor);
        btcProcessorMap.put("DASH", dashCoinProcessor);
        return btcProcessorMap;
    }

}
