package com.example.demo;

import com.example.demo.schedulers.coinprocessor.EthTokenProcessor;
import org.junit.Test;

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
}
