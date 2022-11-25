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
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 开启授权服务功能
 *
 * @author yupengwu
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    /**
     * 配置客户端基本信息
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 创建两个客户端
        clients.inMemory()
                .withClient("demoClient1")
                .secret("demoClientSecret1")
                .scopes("service", "biz_scope_1", "biz_scope_2")
                .authorizedGrantTypes("client_credentials")
                .accessTokenValiditySeconds(7200)
                .and()
                .withClient("demoClient2")
                .secret("demoClientSecret2")
                .scopes("all")
                //开启自动授权
                .autoApprove(false)
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "client_credentials")
                .redirectUris("https://www.baidu.com/")
                .accessTokenValiditySeconds(3600);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        //开启 /oauth/check_token：用于资源服务访问的令牌解析端点。
        oauthServer.checkTokenAccess("permitAll()")
                //开启 /oauth/token_key：提供公有密匙的端点，如果你使用JWT令牌的话。
                .tokenKeyAccess("permitAll()")
                //支持 client_id 和 client_secret 做登陆认证
                .allowFormAuthenticationForClients()
        ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .tokenEnhancer(jwtTokenEnhancer())
                .authenticationManager(authenticationManager)
                //密码模式(放到了WebSecurityConfig)
                //.userDetailsService(passwordUserDetailService)
                //授权码模式
                .authorizationCodeServices(new InMemoryAuthorizationCodeServices())
        ;
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

    /**
     * 配置登录页面的视图信息（其实可以独立一个配置类，这样会更规范）
     */
    @Configuration
    static class MvcConfig implements WebMvcConfigurer {
        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/login").setViewName("login");
        }
    }
}