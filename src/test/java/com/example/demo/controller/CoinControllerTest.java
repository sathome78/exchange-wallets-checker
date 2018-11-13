package com.example.demo.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = "classpath:dao-tests.xml")
@PropertySource(value = "classpath:application.properties")
public class CoinControllerTest {


    @Test
    public void deleteWallet() throws Exception{
        System.out.println("ok");
//
////        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
//
//        System.out.println("ok");
//        jdbcTemplate.execute("CREATE TABLE `COIN` (\n" +
//                "  `ID` int(11) NOT NULL AUTO_INCREMENT,\n" +
//                "  `NAME` varchar(30) NOT NULL,\n" +
//                "  `CURRENT_AMOUNT` double NOT NULL DEFAULT '0',\n" +
//                "  `MIN_AMOUNT` double NOT NULL DEFAULT '0',\n" +
//                "  `MAX_AMOUNT` double NOT NULL DEFAULT '0',\n" +
//                "  `LOW_THAN_MIN_AMOUNT_NOTIFIED` tinyint(1) NOT NULL DEFAULT '0',\n" +
//                "  `PRICE_STATUS` varchar(40) DEFAULT NULL,\n" +
//                "  `DETAIL_NAME` varchar(100) DEFAULT NULL,\n" +
//                "  `COIN_IMAGE` varchar(140) DEFAULT NULL,\n" +
//                "  `COIN_TYPE` varchar(20) DEFAULT NULL,\n" +
//                "  `ETH_TOKEN_CONTRACT` varchar(100) DEFAULT NULL,\n" +
//                "  `ENABLE` tinyint(1) DEFAULT '1',\n" +
//                "  `RATE_TO_USD` decimal(10,4) DEFAULT '0.0000',\n" +
//                "  `MIN_AMOUNT_USD` decimal(10,4) DEFAULT '0.0000',\n" +
//                "  `MAX_AMOUNT_USD` decimal(20,2) DEFAULT '0.00',\n" +
//                "  `AMOUNT_IN_USD` decimal(20,2) DEFAULT '0.00',\n" +
//                "  `MAIN` tinyint(1) DEFAULT NULL,\n" +
//                "  `COIN_ADDRESS` varchar(100) DEFAULT NULL,\n" +
//                "  PRIMARY KEY (`ID`),\n" +
//                "  UNIQUE KEY `COIN_ID_uindex` (`ID`)\n" +
//                ") ENGINE=InnoDB AUTO_INCREMENT=10029 DEFAULT CHARSET=latin1;\n" +
//                "/*!40101 SET character_set_client = @saved_cs_client */;");
//        jdbcTemplate.execute("INSERT INTO `COIN` VALUES (2,'USD',0,0,0,0,'NORMAL','USD','','FIAT','',0,1.0000,0.0000,0.00,0.00,1,NULL)");
//        List<Coin> query = jdbcTemplate.query("SELECT * FROM COIN LIMIT 1", new BeanPropertyRowMapper<>(Coin.class));
//        System.out.println(query.get(0));
    }
}