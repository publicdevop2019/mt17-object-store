package com.hw.config;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
//import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
//import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Configuration
@Description("add self signed cert to restTemplate")
//@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class RestTemplateConfig {
//    @Value("${server.ssl.key-store}")
//    private Resource trustStore;
//    @Value("${security.oauth2.client.clientId}")
//    private String clientId;
//    @Value("${security.oauth2.client.clientSecret}")
//    private String clientSecret;
//    @Value("${security.oauth2.client.accessTokenUri}")
//    private String accessTokenUri;
//    @Value("${server.ssl.key-store-password}")
//    private String trustStorePassword;

//    @Bean
//    public HttpComponentsClientHttpRequestFactory getHttpComponentsClientHttpRequestFactory() {
//        SSLContext sslContext = null;
//        try {
//            sslContext = new SSLContextBuilder().loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray()).build();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        } catch (CertificateException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
//        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
//        return new HttpComponentsClientHttpRequestFactory(httpClient);
//    }

//    @Bean
//    @Description("config get token & check_token restTemplates")
//    public OAuth2RestTemplate oauth2RestTemplate(HttpComponentsClientHttpRequestFactory factory) {
//        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
//        resourceDetails.setAccessTokenUri(accessTokenUri);
//        resourceDetails.setClientId(clientId);
//        resourceDetails.setClientSecret(clientSecret);
//        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
//        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);
//
//        oAuth2RestTemplate.setRequestFactory(factory);
//
//        ClientCredentialsAccessTokenProvider clientCredentialsAccessTokenProvider = new ClientCredentialsAccessTokenProvider();
//        clientCredentialsAccessTokenProvider.setRequestFactory(factory);
//        oAuth2RestTemplate.setAccessTokenProvider(clientCredentialsAccessTokenProvider);
//        return oAuth2RestTemplate;
//    }
}
