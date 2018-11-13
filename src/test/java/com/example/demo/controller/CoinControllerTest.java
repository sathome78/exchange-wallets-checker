package com.example.demo.controller;

import com.example.demo.Application;
import com.example.demo.domain.Coin;
import com.example.demo.repository.CoinRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = "classpath:dao-tests.xml")
@PropertySource("classpath:application.properties")
@SpringBootTest(classes = Application.class)
public class CoinControllerTest {

    @Autowired
    CoinRepository coinRepository;

    @Test
    public void deleteWallet() throws Exception{
        List<Coin> listOfWDSC = coinRepository.findAllByName("ACT");
        Coin coin = listOfWDSC.get(0);
        int sizeBefore = listOfWDSC.size();
        coinRepository.deleteByNameAndEthTokenContractAndCoinAddressAndMain(coin.getName(), coin.getEthTokenContract(), coin.getCoinAddress(), false);
        List<Coin> listAfterDelete = coinRepository.findAllByName("ACT");
        assertEquals(sizeBefore, listAfterDelete.size() - 1);
        for (Coin coin1 : listAfterDelete) {
            assert !coin1.equals(coin);
        }

    }
}