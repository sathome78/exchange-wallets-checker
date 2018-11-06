package com.example.demo;

import com.example.demo.schedulers.coinprocessor.EthTokenProcessor;
import org.json.JSONObject;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.security.cert.X509Certificate;

public class UtilTes {

    @Test
    public void test() {
        EthTokenProcessor ethTokenProcessor = new EthTokenProcessor();
        String s = ethTokenProcessor.calculateWitheBalance("1.2054730384119e+22", "18");
        org.junit.Assert.assertEquals("12054.7303841190000", s);
    }

    @Test
    public void testDividing() {
        EthTokenProcessor ethTokenProcessor = new EthTokenProcessor();
        String divide = ethTokenProcessor.divide("6468534800000000000", "18");
        System.out.println(divide);
    }

    @Test
    public void getBalanceOfCLO() {
        Client client = ClientBuilder.newClient();
        Response response = client.target("https://explorer.callisto.network/account/0xe4f3cab1f11d5a917ac73c80927e64ee4b1a445a").request(MediaType.TEXT_HTML).get();
        String substring = response.readEntity(String.class).substring(1385, 1398);
        System.out.println(substring);
    }

    @Test
    public void getBalanceOfQTUM() {
        Client client = ClientBuilder.newClient();
        Response response = client.target("https://qtum.info/address/QgeijFyD4akxPshDkq6fg6Vk2gFMK4D8iU").request(MediaType.TEXT_HTML).get();
        String s = response.readEntity(String.class).trim();
        String substring = s.substring(s.indexOf("info-value monospace"));
        String replace = substring.substring(substring.indexOf(">") + 1, substring.indexOf("QTUM")).trim();
        System.out.println(replace);
    }

    @Test
    public void ltc() {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://explorer.litecoin.net/address/LZkTXdQKQaLdBaG7nsg4bwejiT3NcW5XtG").request(MediaType.TEXT_HTML).get();
        String s = response.readEntity(String.class).trim();
        String substring = s.substring(s.indexOf("Balance:"));
        String replace = substring.substring(8, substring.indexOf("LTC")).trim();
        System.out.println(replace);
    }

    @Test
    public void doge() {
        Client client = ClientBuilder.newClient();
        Response response = client.target("https://dogechain.info/address/D5GTpiBnAX3dioHDEmghQkSCU3KLQxutY8").request(MediaType.TEXT_HTML).get();
        String s = response.readEntity(String.class).trim();
        String first = s.substring(s.indexOf("currency"));
        String replace = first.substring(first.indexOf(">") + 1, first.indexOf("<")).trim();
        replace.replace(",", "");
    }

    @Test
    public void bcl() {
        Client client = ClientBuilder.newClient();
        Response response = client.target("https://cleanblocks.info/address/12HTEQReYsGPSQFyaaT1ZNweXKJpjfVqgD").request(MediaType.TEXT_HTML_TYPE).get();
        String s = response.readEntity(String.class).trim();
        String substring = s.substring(s.indexOf("<div class=\"col-12 col-md-9 col-lg-10\">") + "<div class=\"col-12 col-md-9 col-lg-10\">".length());
        String substring1 = substring.substring(substring.indexOf("<span class=\"hide\">"));
        System.out.println(substring1);
    }

    @Test
    public void riz(){
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://rizblockchain.com/address/RSePMnGFiVz6yE8CTzDhY1pfCH6Zv5VeXG").request(MediaType.TEXT_HTML ).get();
        String s = response.readEntity(String.class).trim();
        String substring = s.substring(s.indexOf("<tbody>"),s.indexOf("</tbody>"));
        String substring1 = substring.substring(substring.indexOf("<td>"),substring.indexOf("</td>"));
        System.out.println(substring1);
    }

    @Test
    public void fng(){
        Client client = ClientBuilder.newClient();
        Response response = client.target("https://fantasygold.network/address/FBeeBGXa5Kbp3HyBwCMNmmW3hzeJEqcfLp").request(MediaType.TEXT_HTML ).get();
        String s = response.readEntity(String.class).trim();
        String substring = s.substring(s.indexOf("</th></tr></thead><tbody>"));
        String substring1 = substring.substring("</th></tr></thead><tbody>".length()+"            <tr><td>".length(),substring.indexOf("</td>"));
        System.out.println(substring1);
    }

    @Test
    public void bbx(){
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://explorer.xon-wallet.com:3001/address/GdRbgS5vD8RvqhMaMMGCe1H8LwYkKZ4MpE").request(MediaType.TEXT_HTML).get();
        String s = response.readEntity(String.class).trim();
        String substring = s.substring(s.lastIndexOf("</td><td>")+"</td><td>".length(),s.indexOf("</td></tr>"));
        System.out.println(substring);
    }

    @Test
    public void lpc(){
        Response response = ClientBuilder.newClient().target("http://explorer.lightpaycoin.org:3001/address/Lv9h41BY6qoW6R7gaH16AeH1B7aZ2Q6Yup").request(MediaType.TEXT_HTML).get();
        String s = response.readEntity(String.class);
        s = s.substring(s.lastIndexOf("</td><td>")+"</td><td>".length(),s.indexOf("</td></tr>"));
        System.out.println(s);
    }

    @Test
    public void diet(){
        Response response = ClientBuilder.newClient().target("https://explorer.dietbitcoincore.org/address/D6GWDBY6tVSyLifxaengPEwkLgSWEa6iSs").request(MediaType.TEXT_HTML).get();
        String s = response.readEntity(String.class);
        s = s.substring(s.lastIndexOf("</td><td>")+"</td><td>".length(),s.indexOf("</td></tr>"));
        System.out.println(s);
    }


    @Test
    public void apl() throws Exception {
        double pow = Math.pow(10, 7);
        Response response = client().target("https://explorer.apollowallet.org:4443/apl?&requestType=getAccount&includeEffectiveBalance=true&account=APL-LFZZ-ECDW-LXRG-4V4D9").request(MediaType.TEXT_HTML).get();
        String s = response.readEntity(String.class);
        BigDecimal balanceATM = new BigDecimal(new JSONObject(s).getString("balanceATM")).divide(new BigDecimal(pow));
        System.out.println(balanceATM);
    }

    @Test
    public void testPow() {
        BigDecimal npxs = new BigDecimal(33560599029L);
        double pow = Math.pow(10, 6);
        BigDecimal divide = npxs.divide(new BigDecimal(pow));
        System.out.println(divide);

        BigDecimal bigDecimal = new BigDecimal(3656302270923L);
        BigDecimal dimCoin = bigDecimal.divide(new BigDecimal(pow));
        System.out.println(dimCoin);

        BigDecimal bigDecimal1 = new BigDecimal(1071482217);
        BigDecimal dimToken = bigDecimal1.divide(new BigDecimal(pow));
        System.out.println(dimToken);

        BigDecimal bigDecimal2 = new BigDecimal(5000000);
        BigDecimal cache = bigDecimal2.divide(new BigDecimal(pow));
        System.out.println(cache);
    }

    public static Client client() throws Exception {
        SSLContext sslcontext = SSLContext.getInstance("TLS");

        sslcontext.init(null, new TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) {}
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) {}
            public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
        }}, new java.security.SecureRandom());

        return ClientBuilder.newBuilder()
                .sslContext(sslcontext)
                .hostnameVerifier((s1, s2) -> true)
                .build();

    }
}
