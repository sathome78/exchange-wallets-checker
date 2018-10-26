package com.example.demo;

import com.example.demo.domain.Coin;
import com.example.demo.domain.requestbody.Fuck;
import com.example.demo.repository.CoinRepository;
import com.example.demo.schedulers.NotificatorService;
import com.example.demo.util.NumberFormatter;
import com.example.demo.util.RequestUtil;
import javafx.util.Pair;
import net.bytebuddy.asm.Advice;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.demo.schedulers.NotificatorService.ABOVE_MAX_LIMIT;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.stream.Collectors.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	public JavaMailSender emailSender;

	@Autowired
	Client client;

	@Autowired
    private CoinRepository coinRepository;

	@Test
	public void contextLoads() {
//		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//		simpleMailMessage.setTo("dmitrenkonikita1213@gmail.com");
//		simpleMailMessage.setFrom("sender@upholding.biz");
//		simpleMailMessage.setText("hello");
//		emailSender.send(simpleMailMessage);
		BigDecimal bigDecimal = new BigDecimal(300000000);
		final DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.000");
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setGroupingSeparator(' ');

        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
		System.out.println(decimalFormat.format(bigDecimal));
	}

	@Test
    public void testFormatting(){
        Coin coin = coinRepository.findByName("BTC");
        String text = format(NotificatorService.ABOVE_MAX_LIMIT,
                coin.getName(),
                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()),
                valueOf(NumberFormatter.format(coin.getCurrentAmount())),
                valueOf(NumberFormatter.format(coin.getAmountInUSD())),
                valueOf(coin.getMinAmount()),
                valueOf(coin.getMaxAmount()),
                valueOf(coin.getMinAmountInUSD()),
                valueOf(coin.getMaxAmountInUSD()));
        System.out.println(text);
    }

	@SpyBean
	RequestUtil requestUtil;
	@Test
	public void te(){
		requestUtil.getEthTokens();
		requestUtil.getEthTokens();


		verify(requestUtil, times(1)).getEthTokens();
	}
//
//	@Test
//	public void testEtcToken() throws InterruptedException, IOException {
////		Response invoke =  ClientBuilder.newClient().target("http://etherhub.io/web3relay").
////				request(MediaType.APPLICATION_JSON_TYPE).
////				header("Connection", "keep-alive").
////				header("Content-Length", "94").
////				header("Accept", "application/json, text/plain, */*").
////				header("Origin", "http://etherhub.io").
////				header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36").
////				header("Content-Type", "application/json;charset=UTF-8").
////				header("Referer", "http://etherhub.io/addr/0x6cf080280a37eff8f163f004b9da6962764368a0").
////				header("Accept-Encoding", "gzip, deflate").
////				header("Origin", "http://etherhub.io").
////				header("Accept-Language", "en,uk-UA;q=0.9,uk;q=0.8,en-US;q=0.7").
////				header("Origin", "http://etherhub.io").
////				header("X-Compress", "null").
////				cookie("__cfduid", "d1c5d988869a526e449f37c4f1df6d32b1540327579").cookie("cf_clearance", "713a1fe624bf483de343ed5903d6bc55b227c19f-1540328683-900-150").buildPost(Entity.entity(String.class, MediaType.APPLICATION_JSON_TYPE)).invoke();
////		String s = invoke.readEntity(String.class);
////		System.out.println(s);
//		String s1 = "curl 'http://etherhub.io/web3relay' -H 'Origin: http://etherhub.io' -H 'Accept-Encoding: gzip, deflate' -H 'Accept-Language: en,uk-UA;q=0.9,uk;q=0.8,en-US;q=0.7' -H 'X-Compress: null' -H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36' -H 'Content-Type: application/json;charset=UTF-8' -H 'Accept: application/json, text/plain, */*' -H 'Referer: http://etherhub.io/addr/0x6cf080280a37eff8f163f004b9da6962764368a0' -H 'Cookie: __cfduid=d1c5d988869a526e449f37c4f1df6d32b1540327579; cf_clearance=713a1fe624bf483de343ed5903d6bc55b227c19f-1540328683-900-150' -H 'Connection: keep-alive' --data-binary '{\"addr\":\"0x6cf080280a37eff8f163f004b9da6962764368a0\",\"options\":[\"balance\",\"count\",\"bytecode\"]}' --compressed";
//		Process p = Runtime.getRuntime().exec(s1);
//		p.waitFor();
//		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
//		String line = "";
//		while ((line = reader.readLine()) != null) {
//			String s = reader.readLine();
//			System.out.println(s);
//		}
//
//	}

}
