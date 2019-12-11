package com.ypw;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author yupengwu
 */
@EnableFeignClients
@SpringBootApplication
@EnableEurekaClient
public class UserService {

    public static void main(String[] args) {
        new SpringApplicationBuilder(UserService.class)
                .web(true).run(args);
    }
}
