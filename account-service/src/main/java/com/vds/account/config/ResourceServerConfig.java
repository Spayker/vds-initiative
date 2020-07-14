package com.vds.account.config;

import com.vds.account.service.security.CustomUserInfoTokenServices;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 *   Configuration class that overrides implementation to adjust the access rules and paths that are protected by
 *   OAuth2 security.
 **/
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final ResourceServerProperties sso;

    @Autowired
    public ResourceServerConfig(ResourceServerProperties sso) {
        this.sso = sso;
    }

    /**
     *  Set up an OAuth2 context for this test using client credentials grant type.
     *  @return ClientCredentialsResourceDetails instance
     **/
    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
        return new ClientCredentialsResourceDetails();
    }

    /**
     *  Initializes Pre-defined custom RequestInterceptor for Feign Requests.
     *  @return OAuth2FeignRequestInterceptor instance
     **/
    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(){
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientCredentialsResourceDetails());
    }

    /**
     *  Rest template that is able to make OAuth2-authenticated REST requests with the credentials of the provided
     *  resource.
     *  @return OAuth2RestTemplate instance
     **/
    @Bean
    public OAuth2RestTemplate clientCredentialsRestTemplate() {
        return new OAuth2RestTemplate(clientCredentialsResourceDetails());
    }

    /**
     *  Retruns an extended implementation of UserInfoTokenServices.
     *  @return ResourceServerTokenServices instance
     **/
    @Bean
    public ResourceServerTokenServices tokenServices() {
        return new CustomUserInfoTokenServices(sso.getUserInfoUri(), sso.getClientId());
    }


    /**
     *  Configures HttpSecurity object with basic features:
     *  - which requests must be authanticated
     *  - csrf option
     *  @param http - instance of HttpSecurity to initialize
     *  @throws Exception instance
     **/
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/" , "/demo").permitAll()
                .anyRequest().authenticated();
    }
}