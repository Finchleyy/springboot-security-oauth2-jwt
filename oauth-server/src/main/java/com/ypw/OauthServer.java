package com.ypw;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author yupengwu
 */
@SpringBootApplication
@EnableResourceServer
@EnableEurekaClient
public class OauthServer {

    public static void main(String[] args) {
        new SpringApplicationBuilder(OauthServer.class)
                .web(true).run(args);
    }
}