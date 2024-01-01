package org.kin.demo.spring.ingress.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author huangjianqin
 * @date 2023/12/31
 */
@SpringBootApplication
@EnableWebMvc
@EnableDiscoveryClient
public class IngressServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(IngressServiceApplication.class);
    }
}
