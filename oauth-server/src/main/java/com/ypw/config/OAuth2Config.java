package com.ypw.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * 开启授权服务功能
 *
 * @author yupengwu
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    /**
     * 配置客户端基本信息
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 创建一个客户端 名字是user-service
        clients.inMemory().withClient("test-service")
                .secret("123456")
                .scopes("service")
                .authorizedGrantTypes("client_credentials")
                .accessTokenValiditySeconds(3600)
                .and()
                .withClient("user-service")
                .secret("123456")
                .scopes("service")
                .authorizedGrantTypes("password")
                .accessTokenValiditySeconds(3600);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtTokenEnhancer())
                .authenticationManager(authenticationManager);
    }

    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    private JwtAccessTokenConverter jwtTokenEnhancer() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("ypw-jwt.jks"),
                "ypw666".toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("ypw-jwt"));
        return converter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.equals(encodedPassword);
            }

            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }
        };
        // MessageDigestPasswordEncoder pwdEncoder = new MessageDigestPasswordEncoder("sha-256");
        // pwdEncoder.setEncodeHashAsBase64(true);
    }
}