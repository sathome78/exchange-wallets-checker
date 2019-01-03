package com.example.demo;

import com.example.demo.util.RequestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.Client;
import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    Client client;

    @Autowired
    RequestUtil requestUtil;


    @Test
    public void te() {
        BigDecimal taxi = requestUtil.getTokenValue("0x645830ee2c984bb6ec14da7eeca8ee30b399fb11", "TAXI");
        BigDecimal any = requestUtil.getTokenValue("0x645830ee2c984bb6ec14da7eeca8ee30b399fb11", "ANY");
        BigDecimal atl = requestUtil.getTokenValue("0x645830ee2c984bb6ec14da7eeca8ee30b399fb11", "ATL");
        BigDecimal gnt = requestUtil.getTokenValue("0x645830ee2c984bb6ec14da7eeca8ee30b399fb11", "GNT");
        BigDecimal bnb = requestUtil.getTokenValue("0x645830ee2c984bb6ec14da7eeca8ee30b399fb11", "BNB");
        BigDecimal rntb = requestUtil.getTokenValue("0x645830ee2c984bb6ec14da7eeca8ee30b399fb11", "RNTB");
        BigDecimal nio = requestUtil.getTokenValue("0x645830ee2c984bb6ec14da7eeca8ee30b399fb11", "NIO");
        BigDecimal bptn = requestUtil.getTokenValue("0x645830ee2c984bb6ec14da7eeca8ee30b399fb11", "BPTN");
        BigDecimal ink = requestUtil.getTokenValue("0x645830ee2c984bb6ec14da7eeca8ee30b399fb11", "INK");
        BigDecimal wdsc = requestUtil.getTokenValue("0x645830ee2c984bb6ec14da7eeca8ee30b399fb11", "WDSC");
        BigDecimal wdsc1 = requestUtil.getTokenValue("0x5b31f76dAe4485F0DC0e7d1577c2c1A33CD0fD20", "WDSC");
        BigDecimal wdsc2 = requestUtil.getTokenValue("0xaa84CeCD0832b64de2630494435BD49ECA9C2b65", "WDSC");
//        BigDecimal wdsc3 = requestUtil.getTokenValue("0x7724a774Ccb09EC8D0032d0a78c676B1640ed2E8", "WDSC");
        BigDecimal wdsc4 = requestUtil.getTokenValue("0x5b31f76dAe4485F0DC0e7d1577c2c1A33CD0fD20", "WDSC");
        BigDecimal wdsc5 = requestUtil.getTokenValue("0xaa84CeCD0832b64de2630494435BD49ECA9C2b65", "WDSC");

        org.junit.Assert.assertNotNull(taxi);
        org.junit.Assert.assertNotNull(any);
        org.junit.Assert.assertNotNull(atl);
        org.junit.Assert.assertNotNull(gnt);
    }


}
