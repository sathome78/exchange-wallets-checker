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

    @Autowired
    private BTCGenericProcessor btcTargetProcessor;

    @Autowired
    private BTCGenericProcessor bcaCoinProcessor;

    @Autowired
    private BTCGenericProcessor btcpCoinProcessor;

    @Autowired
    private BTCGenericProcessor lccCoinProcessor;

    @Autowired
    private BTCGenericProcessor szcCoinProcessor;

    @Autowired
    private BTCGenericProcessor toaCoinProcessor;

    @Autowired
    private BTCGenericProcessor ltcCoinProcessor;

    @Autowired
    private BTCGenericProcessor dogeCoinProcessor;

    @Autowired
    private BTCGenericProcessor bclCoinProcessor;

    @Autowired
    private BTCGenericProcessor fgcCoinProcessor;

    @Autowired
    private BTCGenericProcessor clxGenericCoinProcessor;

    @Autowired
    private BTCGenericProcessor nycCoinProcessor;

    @Autowired
    private BTCGenericProcessor dcrCoinProcessor;

    @Autowired
    private BTCGenericProcessor abbcCoinProcessor;

    @Autowired
    private BTCGenericProcessor cbcCoinProcessor;

    @Autowired
    private BTCGenericProcessor qCoinProcessor;

    @Autowired
    private BTCGenericProcessor CTXCoinProcessor;

    @Autowired
    private BTCGenericProcessor brbCoinProcessor;

    @Bean
    @Order(999)
    public Map<String, BTCGenericProcessor> btcProcessorMap() {
        Map<String, BTCGenericProcessor> btcProcessorMap = new HashMap<>();
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
        btcProcessorMap.put("BTC", btcTargetProcessor);
        btcProcessorMap.put("BCA", bcaCoinProcessor);
        btcProcessorMap.put("BTCP", btcpCoinProcessor);
        btcProcessorMap.put("LCC", lccCoinProcessor);
        btcProcessorMap.put("SZC", szcCoinProcessor);
        btcProcessorMap.put("TOA", toaCoinProcessor);
        btcProcessorMap.put("DOGE", dogeCoinProcessor);
        btcProcessorMap.put("FGC", fgcCoinProcessor);
        btcProcessorMap.put("BCL", bclCoinProcessor);
        btcProcessorMap.put("LTC", ltcCoinProcessor);
        btcProcessorMap.put("CLX", clxGenericCoinProcessor);
        btcProcessorMap.put("NYC", nycCoinProcessor);
        btcProcessorMap.put("DCR", dcrCoinProcessor);
        btcProcessorMap.put("ABBC", abbcCoinProcessor);
        btcProcessorMap.put("CBC", cbcCoinProcessor);
        btcProcessorMap.put("Q", qCoinProcessor);
        btcProcessorMap.put("CTX", CTXCoinProcessor);
        btcProcessorMap.put("BRB", brbCoinProcessor);
        return btcProcessorMap;
    }
}