package com.example.demo.configuration;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.security.cert.X509Certificate;

@Configuration
public class HttpClientConfiguration {

    @Bean
    public Client client() throws Exception {
        SSLContext sslcontext = SSLContext.getInstance("TLS");

        sslcontext.init(null, new TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) {}
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) {}
            public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
        }}, new java.security.SecureRandom());

        ClientConfig configuration = new ClientConfig();
        configuration.register(ClientProperties.CONNECT_TIMEOUT,10000);

        return ClientBuilder.newBuilder()
                .sslContext(sslcontext)
                .withConfig(configuration)
                .hostnameVerifier((s1, s2) -> true)
                .build();

    }
}
