package com.example.demo.controller;

import com.example.demo.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = "classpath:dao-tests.xml")
@PropertySource("classpath:application.properties")
@SpringBootTest(classes = Application.class)
public class CoinControllerTest {


    @Test
    public void deleteWallet() throws Exception{
        System.out.println("ok");

    }
}