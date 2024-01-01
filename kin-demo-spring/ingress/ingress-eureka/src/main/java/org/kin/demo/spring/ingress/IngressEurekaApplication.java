package org.kin.demo.spring.ingress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author huangjianqin
 * @date 2024/1/1
 */
@EnableEurekaServer
@SpringBootApplication
public class IngressEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(IngressEurekaApplication.class);
    }
}
