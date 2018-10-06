package com.example.demo;

import com.example.demo.domain.requestbody.Fuck;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.demo.schedulers.NotificatorService.ABOVE_MAX_LIMIT;
import static java.util.stream.Collectors.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ApplicationTests {

//	@Autowired
	public JavaMailSender emailSender;

	@Test
	public void contextLoads() {
//		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//		simpleMailMessage.setTo("dmitrenkonikita1213@gmail.com");
//		simpleMailMessage.setFrom("sender@upholding.biz");
//		simpleMailMessage.setText("hello");
//		emailSender.send(simpleMailMessage);
		BigDecimal bigDecimal = new BigDecimal(1000000);
		final DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
		System.out.println(decimalFormat.format(bigDecimal));
	}

}
