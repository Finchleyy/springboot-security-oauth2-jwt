package com.ypw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author yupengwu
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServer {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServer.class);
    }
}
