package com.example.demo;

import com.example.demo.domain.requestbody.Fuck;
import javafx.util.Pair;
import org.json.JSONObject;
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
import java.util.*;
import java.util.stream.Collectors;

import static com.example.demo.schedulers.NotificatorService.ABOVE_MAX_LIMIT;
import static java.lang.String.valueOf;
import static java.util.stream.Collectors.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ApplicationTests {

	@Autowired
	public JavaMailSender emailSender;

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
	public void test(){
		String json =  "{\"balances\": [\n" +
				"    {\n" +
				"      \"contract\": \"0x049399a6b048d52971f7d122ae21a1532722285f\",\n" +
				"      \"balance\": 1.2054730384119e+22\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x05d412ce18f24040bb3fa45cf2c69e506586d8e8\",\n" +
				"      \"balance\": 2.2079876871508e+23\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x069bc4608a8764924ab991cb9eb6d6b6caad74c8\",\n" +
				"      \"balance\": 2.5573886393867e+23\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x07a7ed332c595b53a317afcee50733af571475e7\",\n" +
				"      \"balance\": 2.8874903857939e+23\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x0db8d8b76bc361bacbb72e2c491e06085a97ab31\",\n" +
				"      \"balance\": 4.1041659435577e+21\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x0df721639ca2f7ff0e1f618b918a65ffb199ac4e\",\n" +
				"      \"balance\": 1.7999948759334e+24\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x131f193692b5cce8c87d12ff4f7aa1d4e1668f1e\",\n" +
				"      \"balance\": 13997229\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x13c2fab6354d3790d8ece4f0f1a3280b4a25ad96\",\n" +
				"      \"balance\": 6.9085872060161e+22\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x1985365e9f78359a9b6ad760e32412f4a445e862\",\n" +
				"      \"balance\": 6.4685348e+18\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x1a2277c83930b7a64c3e3d5544eaa8c4f946b1b7\",\n" +
				"      \"balance\": 2.865281838348e+22\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x1c83501478f1320977047008496dacbd60bb15ef\",\n" +
				"      \"balance\": 4.6982541548686e+22\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x1dea979ae76f26071870f824088da78979eb91c8\",\n" +
				"      \"balance\": 3.4538e+22\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x1ed7ae1f0e2fa4276dd7ddc786334a3df81d50c0\",\n" +
				"      \"balance\": 7.9558760916972e+20\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x1fe70be734e473e5721ea57c8b5b01e6caa52686\",\n" +
				"      \"balance\": 1.746864130569e+23\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x212d95fccdf0366343350f486bda1ceafc0c2d63\",\n" +
				"      \"balance\": 0\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x241ba672574a78a3a604cdd0a94429a73a84a324\",\n" +
				"      \"balance\": 8.7911370333381e+22\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x2baac9330cf9ac479d819195794d79ad0c7616e3\",\n" +
				"      \"balance\": 2.6261763e+21\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x2cf618c19041d9db330d8222b860a624021f30fb\",\n" +
				"      \"balance\": 1.7873700057095e+24\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x2ee3b3804f695355ddc4f8e1c54654416d7ee95a\",\n" +
				"      \"balance\": 1.322608772644e+15\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x342ba159f988f24f0b033f3cc5232377ee500543\",\n" +
				"      \"balance\": 4.97e+19\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x3839d8ba312751aa0248fed6a8bacb84308e20ed\",\n" +
				"      \"balance\": 1.4107468735138e+23\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x3fd8f39a962efda04956981c31ab89fab5fb8bc8\",\n" +
				"      \"balance\": 1.8146274058907e+22\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x417d6feeae8b2fcb73d14d64be7f192e49431978\",\n" +
				"      \"balance\": 3.6265772263745e+23\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x45b73654e464945a268032cdcb8d551fe8b733ca\",\n" +
				"      \"balance\": 1.3735566350811e+25\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x5136c98a80811c3f46bdda8b5c4555cfd9f812f0\",\n" +
				"      \"balance\": 1365018646510\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x516e5436bafdc11083654de7bb9b95382d08d5de\",\n" +
				"      \"balance\": 4855424404\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x519475b31653e46d20cd09f9fdcf3b12bdacb4f5\",\n" +
				"      \"balance\": 1.01220538852e+20\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x52494fbffe10f8c29411521040ae8618c334981e\",\n" +
				"      \"balance\": 5.9059988610936e+23\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x53e28b07e0795869b727ee4d585b3c025b016952\",\n" +
				"      \"balance\": 4.0686291045855e+24\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x554c20b7c486beee439277b4540a434566dc4c02\",\n" +
				"      \"balance\": 2.223359927e+20\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x5554e04e76533e1d14c52f05beef6c9d329e1e30\",\n" +
				"      \"balance\": 76292\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x57c75eccc8557136d32619a191fbcdc88560d711\",\n" +
				"      \"balance\": 15224624\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x5b26c5d0772e5bbac8b3182ae9a13f9bb2d03765\",\n" +
				"      \"balance\": 2.0978513489181e+14\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x5ce8e61f28f5948de4913bcaada90039481f1f53\",\n" +
				"      \"balance\": 5000000000\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x5dbac24e98e2a4f43adc0dc82af403fca063ce2c\",\n" +
				"      \"balance\": 8.53696551016e+21\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x6292cec07c345c6c6953e9166324f58db6d9f814\",\n" +
				"      \"balance\": 4.761385479168e+22\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x632f62fcf63cb56380ffd27d63afcf5f1349f73f\",\n" +
				"      \"balance\": 2.2682240075e+14\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x67a9099f0008c35c61c00042cd9fb03684451097\",\n" +
				"      \"balance\": 3.893783778443e+22\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x6c22b815904165f3599f0a4a092d458966bd8024\",\n" +
				"      \"balance\": 3.6055914765706e+23\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x72430a612adc007c50e3b6946dbb1bb0fd3101d1\",\n" +
				"      \"balance\": 4.6730884520623e+15\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x737f98ac8ca59f2c68ad658e3c3d8c8963e40a4c\",\n" +
				"      \"balance\": 2.92615e+22\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x7528e3040376edd5db8263db2f5bd1bed91467fb\",\n" +
				"      \"balance\": 1.4173749235323e+24\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x78b7fada55a64dd895d8c8c35779dd8b67fa8a05\",\n" +
				"      \"balance\": 3.2007074566282e+22\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x7a07e1a0c2514d51132183ecfea2a880ec3b7648\",\n" +
				"      \"balance\": 3.8737502601981e+24\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x895f5d0b8456b980786656a33f21642807d1471c\",\n" +
				"      \"balance\": 47242259474522\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x8a854288a5976036a725879164ca3e91d30c6a1b\",\n" +
				"      \"balance\": 1.5975e+19\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x8d80de8a78198396329dfa769ad54d24bf90e7aa\",\n" +
				"      \"balance\": 1.06396888535e+23\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x8dd5fbce2f6a956c3022ba3663759011dd51e73e\",\n" +
				"      \"balance\": 2.4906499026156e+22\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x905e337c6c8645263d3521205aa37bf4d034e745\",\n" +
				"      \"balance\": 1.83317235295e+21\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x92e52a1a235d9a103d970901066ce910aacefd37\",\n" +
				"      \"balance\": 3.2379438340169e+14\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x98bde3a768401260e7025faf9947ef1b81295519\",\n" +
				"      \"balance\": 6.19157128e+18\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x9a0587eae7ef64b2b38a10442a44cfa43edd7d2a\",\n" +
				"      \"balance\": 2.7695831827357e+24\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x9e88613418cf03dca54d6a2cf6ad934a78c7a17a\",\n" +
				"      \"balance\": 1.7410107950239e+21\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x9eec65e5b998db6845321baa915ec3338b1a469b\",\n" +
				"      \"balance\": 2.0e+19\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0x9f195617fa8fbad9540c5d113a99a0a0172aaedc\",\n" +
				"      \"balance\": 2.912708528983e+22\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xa74476443119a942de498590fe1f2454d7d4ac0d\",\n" +
				"      \"balance\": 5.8859043288e+20\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xab95e915c123fded5bdfb6325e35ef5515f1ea69\",\n" +
				"      \"balance\": 2.3445139053284e+20\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xaf47ebbd460f21c2b3262726572ca8812d7143b0\",\n" +
				"      \"balance\": 5\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xb056c38f6b7dc4064367403e26424cd2c60655e1\",\n" +
				"      \"balance\": 1.8113102531972e+21\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xb0e6f83eba6a4ea20617e134b1aee30fcb0ac634\",\n" +
				"      \"balance\": 7\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xb8c77482e45f1f44de1745f52c74426c631bdd52\",\n" +
				"      \"balance\": 3.93379391e+18\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xba5f11b16b155792cf3b2e6880e8706859a8aeb6\",\n" +
				"      \"balance\": 4187654853\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xbf8aa0617df5c542f533b0e95fe2f877906ac327\",\n" +
				"      \"balance\": 1.0311012e+15\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xc20464e0c373486d2b3335576e83a218b1618a5e\",\n" +
				"      \"balance\": 1.5553513017173e+24\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xc56b13ebbcffa67cfb7979b900b736b3fb480d78\",\n" +
				"      \"balance\": 8781958480637\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xc6be00f7ed386015a3c751d38c126c62f231138d\",\n" +
				"      \"balance\": 1.57726267277e+21\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xc9859fccc876e6b4b3c749c5d29ea04f48acb74f\",\n" +
				"      \"balance\": 63075\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xcce629ba507d7256cce7d30628279a155c5309c5\",\n" +
				"      \"balance\": 2000\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xce958ecf2c752c74973e89674faa30404b15a498\",\n" +
				"      \"balance\": 6.1841260475e+22\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xd01db73e047855efb414e6202098c4be4cd2423b\",\n" +
				"      \"balance\": 2.4242992536946e+21\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xd26114cd6ee289accf82350c8d8487fedb8a0c07\",\n" +
				"      \"balance\": 3.01009816e+18\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xd685c3f8beab51b4c7aa163ca81968f2a5df69b3\",\n" +
				"      \"balance\": 1.0e+19\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xd7394087e1dbbe477fe4f1cf373b9ac9459565ff\",\n" +
				"      \"balance\": 45729773300010\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xdac15794f0fadfdcf3a93aeaabdc7cac19066724\",\n" +
				"      \"balance\": 8.670497445e+21\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xdd690d8824c00c84d64606ffb12640e932c1af56\",\n" +
				"      \"balance\": 2639216994881\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xdd92e60563250012ee1c4cb4b26810c45a0591da\",\n" +
				"      \"balance\": 2.4740672125e+25\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xdf67cf04f1f268e431bfecf2c76843afb8e536c1\",\n" +
				"      \"balance\": 2.2907334065675e+14\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xe11609b9a51caf7d32a55896386ac52ed90e66f1\",\n" +
				"      \"balance\": 8.5210694926195e+16\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xe8780b48bdb05f928697a5e8155f672ed91462f7\",\n" +
				"      \"balance\": 1.0043486662821e+24\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xef51c9377feb29856e61625caf9390bd0b67ea18\",\n" +
				"      \"balance\": 21283228377388\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xf3b3cad094b89392fce5fafd40bc03b80f2bc624\",\n" +
				"      \"balance\": 1.657712187823e+22\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xf4065e4477e91c177ded71a7a6fb5ee07dc46bc9\",\n" +
				"      \"balance\": 1.6703025045307e+21\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xf433089366899d83a9f26a773d59ec7ecf30355e\",\n" +
				"      \"balance\": 457940075484\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xf4c90e18727c5c76499ea6369c856a6d61d3e92e\",\n" +
				"      \"balance\": 33237396160000\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xf8c595d070d104377f58715ce2e6c93e49a87f3c\",\n" +
				"      \"balance\": 839052228541\n" +
				"    },\n" +
				"    {\n" +
				"      \"contract\": \"0xf8e06e4e4a80287fdca5b02dccecaa9d0954840f\",\n" +
				"      \"balance\": 1.6985294520726e+22\n" +
				"    }\n" +
				"  ]\n" +
				"}";
		JSONObject jsonObject = new JSONObject(json);
		List<Object> balances = jsonObject.getJSONArray("balances").toList();
		Map<String, String> collect2 = balances.
				stream().
				map(item -> (HashMap<String, Object>) item).
				map(element -> new Pair<>(valueOf(element.get("contract")), valueOf(element.get("balance"))))
				.collect(Collectors.toMap(Pair::getKey, Pair::getValue));

	}

}
