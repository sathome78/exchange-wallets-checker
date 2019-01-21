package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.PriceStatus;
import com.example.demo.domain.enums.CoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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


    @Autowired
    private CoinProcessor wavesProcessor;

    @Autowired
    private CoinProcessor tronProcessor;


    @Autowired
    private CoinProcessor qtumProcessor;


    @Autowired
    private CoinProcessor ntyProcessor;


    @Autowired
    private CoinProcessor dcrProcessor;

    @Autowired
    private CoinProcessor cloProcessor;

    @Autowired
    private CoinProcessor iotaProcessor;

    @Autowired
    private CoinProcessor lskCoinProcessor;

    @Autowired
    private CoinProcessor etzCoinProcessor;

    @Autowired
    private CoinProcessor aplCoinProcessor;

    @Autowired
    private CoinProcessor kazeCoinProcessor;

    @Autowired
    private CoinProcessor streamCoinProcessor;

    @Autowired
    private CoinProcessor etiCoinProcessor;

    @Autowired
    private CoinProcessor lunesCoinProcessor;

    @Autowired
    private CoinProcessor golCoinProcessor;

    @Autowired
    private CoinProcessor arkCoinProcessor;

    @Autowired
    private CoinProcessor riseCoinProcessor;

    @Autowired
    private CoinProcessor qtumTokenProcessor;

    @Autowired
    private CoinProcessor neoCoinProcessor;

    @Autowired
    private CoinProcessor gasProcessor;

    @Autowired
    private CoinProcessor wavesTokenProcessor;

    @Autowired
    private CoinProcessor edrCoinProcessor;

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
        processorMap.put(CoinType.WAVES, wavesProcessor);
        processorMap.put(CoinType.WAVES_COIN, wavesTokenProcessor);
        processorMap.put(CoinType.TRON, tronProcessor);
        processorMap.put(CoinType.QTUM, qtumProcessor);
        processorMap.put(CoinType.NTY, ntyProcessor);
        processorMap.put(CoinType.DCR, dcrProcessor);
        processorMap.put(CoinType.CLO, cloProcessor);
        processorMap.put(CoinType.IOTA, iotaProcessor);
        processorMap.put(CoinType.LSK, lskCoinProcessor);
        processorMap.put(CoinType.ETZ, etzCoinProcessor);
        processorMap.put(CoinType.APL, aplCoinProcessor);
        processorMap.put(CoinType.KAZE, kazeCoinProcessor);
        processorMap.put(CoinType.STREAM, streamCoinProcessor);
        processorMap.put(CoinType.ETI, etiCoinProcessor);
        processorMap.put(CoinType.LUNES, lunesCoinProcessor);
        processorMap.put(CoinType.GOL, golCoinProcessor);
        processorMap.put(CoinType.ARK, arkCoinProcessor);
        processorMap.put(CoinType.RISE, riseCoinProcessor);
        processorMap.put(CoinType.QTUM_TOKEN_COIN, qtumTokenProcessor);
        processorMap.put(CoinType.NEO, neoCoinProcessor);
        processorMap.put(CoinType.GAS, gasProcessor);
        processorMap.put(CoinType.EDR, edrCoinProcessor);
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
