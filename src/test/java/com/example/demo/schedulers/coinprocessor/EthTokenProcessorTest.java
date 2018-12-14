package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.dto.CoinWrapper;
import com.example.demo.util.RequestUtil;
import javafx.util.Pair;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;
import static org.junit.Assert.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class EthTokenProcessorTest {

    @Autowired
    private RequestUtil requestUtil;


    @Test
    public void getBalance() {
    }


    public boolean containsDuplicate(List<String> strings) {
        Set<String> stringSet = new HashSet<>(strings);
        return stringSet.size() != strings.size();
    }

    @Test
    public void list_contain_duplicates() {
        List<String> strings = Arrays.asList("a", "b", "a");
        assertTrue(containsDuplicate(strings));
    }

    @Test
    public void list_does_not_contain_duplicates() {
        List<String> strings = Arrays.asList("a", "b");
        assertFalse(containsDuplicate(strings));
    }


}