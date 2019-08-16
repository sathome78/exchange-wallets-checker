package com.example.demo.schedulers.coinprocessor;

import com.example.demo.domain.Coin;
import com.example.demo.domain.dto.CoinWrapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import static com.yandex.money.api.utils.Numbers.bytesToHex;

@Slf4j
@Service("usdxCoinProcessor")
public class USDXCoinProcessor implements CoinProcessor {

    @Value("${usdx.endpoint.basic}")
    private String usdxEndpoint;

    @Value("${usdx.api.key}")
    private String apiKey;

    @Override
    public CoinWrapper process(Coin coin) {
        return CoinWrapper.builder().coin(coin).actualBalance(getAmount(coin)).build();
    }

    @Override
    public BigDecimal getBalance(Coin coin, String wallet) {
        return getAmount(coin);
    }

    private BigDecimal getAmount(Coin coin) {
        RestTemplate restTemplate = new RestTemplate();

        HttpClientBuilder clientBuilder = HttpClientBuilder.create();

        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("X-Usdx-Signature", generateSecurityHeaderValue(String.valueOf(System.currentTimeMillis()), StringUtils.EMPTY)));

        clientBuilder.setDefaultHeaders(headers);

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(clientBuilder.build());

        restTemplate.setRequestFactory(requestFactory);

        UsdxApiResponse<UsdxAccountBalance> accountBalanceUsdxApiResponse = restTemplate.exchange(usdxEndpoint, HttpMethod.GET,
                null, new ParameterizedTypeReference<UsdxApiResponse<UsdxAccountBalance>>() {
                }).getBody();

        checkResponseAndThrowUsdxApiExceptionWhenHasErrorOrFail(accountBalanceUsdxApiResponse.getStatus(), accountBalanceUsdxApiResponse.getMessage(),
                accountBalanceUsdxApiResponse.getData().getErrorCode(), accountBalanceUsdxApiResponse.getData().getFailReason());

        UsdxAccountBalance data = accountBalanceUsdxApiResponse.getData();

        switch (coin.getName()) {
            case "LHT":
                return data.getLhtBalance();
            case "USDX":
                return data.getUsdxBalance();
            default:
                return BigDecimal.ZERO;
        }
    }

    private String generateSecurityHeaderValue(String timestamp, String body) {
        String value;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String payload = String.join(StringUtils.EMPTY, body, apiKey, timestamp);
            md.update(payload.getBytes());

            value = bytesToHex(md.digest());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return "t=" + timestamp + ", v1=" + value;
    }

    private void checkResponseAndThrowUsdxApiExceptionWhenHasErrorOrFail(String status, String message, String errorCode, String failReason) {
        if (status.equals(UsdxApiRequestStatus.ERROR.getName())) {
            log.error("USDX Wallet. Error message: " + message);
            throw new RuntimeException(message);
        } else if (status.equals(UsdxApiRequestStatus.FAIL.getName())) {
            log.error("USDX Wallet. Error code: " + errorCode + " | Fail reasone: " + failReason);
            throw new RuntimeException("Error code: " + errorCode + " | Fail reasone: " + failReason);
        }
    }

    @Data
    private static class UsdxApiResponse<T> {
        private T data;
        private String message;
        private String status;
    }

    @Data
    private static class UsdxAccountBalance {
        @JsonProperty("USDX")
        private BigDecimal usdxBalance;
        @JsonProperty("LHT")
        private BigDecimal lhtBalance;

        private String errorCode;
        private String failReason;
    }

    private enum UsdxApiRequestStatus {

        SUCCESS("success"), FAIL("fail"), ERROR("error");

        UsdxApiRequestStatus(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return name;
        }
    }
}