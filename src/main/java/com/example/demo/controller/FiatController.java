package com.example.demo.controller;


import com.example.demo.repository.AdvCashRepository;
import com.example.demo.repository.NixRepository;
import com.example.demo.repository.PayeerRepository;
import com.example.demo.repository.PerfectmoneyRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin(value = {"http://localhost:8080", "http://172.31.3.72:8080/"})
@Log4j2
public class FiatController {

    @Autowired
    private AdvCashRepository advCashRepository;

    @Autowired
    private PayeerRepository payeerRepository;

    @Autowired
    private NixRepository nixRepository;

    @Autowired
    private PerfectmoneyRepository perfectmoneyRepository;

    @GetMapping("/fiat")
    public ResponseEntity<Map<String, Object>> fiat(@RequestParam("merchant") String merchant) {
        Map<String, Object> response = new HashMap<>();
        if (merchant.equals("advcash")) {
            response.put("balance", advCashRepository.findAll());
        }
        if (merchant.equals("payeer")) {
            response.put("balance", payeerRepository.findAll());
        }
        if(merchant.equals("nix")){
            response.put("balance", nixRepository.findAll());
        }
        if(merchant.equals("perfectmoney")){
            response.put("balance", perfectmoneyRepository.findAll());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
