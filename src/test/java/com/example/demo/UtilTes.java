package com.example.demo;

import com.example.demo.schedulers.coinprocessor.EthTokenProcessor;
import org.junit.Test;

import java.math.BigDecimal;

public class UtilTes {

    @Test
    public void test(){
        EthTokenProcessor ethTokenProcessor = new EthTokenProcessor();
        String s = ethTokenProcessor.calculateWitheBalance("1.2054730384119e+22", "18");
        org.junit.Assert.assertEquals("12054.7303841190000",s);
    }

    @Test
    public void testDividing(){
        EthTokenProcessor ethTokenProcessor = new EthTokenProcessor();
        String divide = ethTokenProcessor.divide("6468534800000000000", "18");
        System.out.println(divide);
    }

    @Test
    public void testPow(){
        BigDecimal npxs = new BigDecimal(33560599029L);
        double pow = Math.pow(10, 6);
        BigDecimal divide = npxs.divide(new BigDecimal(pow));
        System.out.println(divide);

        BigDecimal bigDecimal = new BigDecimal(3656302270923L);
        BigDecimal dimCoin = bigDecimal.divide(new BigDecimal(pow));
        System.out.println(dimCoin);

        BigDecimal bigDecimal1 = new BigDecimal(1071482217);
        BigDecimal dimToken = bigDecimal1.divide(new BigDecimal(pow));
        System.out.println(dimToken);

        BigDecimal bigDecimal2 = new BigDecimal(5000000);
        BigDecimal cache = bigDecimal2.divide(new BigDecimal(pow));
        System.out.println(cache    );
    }
}
