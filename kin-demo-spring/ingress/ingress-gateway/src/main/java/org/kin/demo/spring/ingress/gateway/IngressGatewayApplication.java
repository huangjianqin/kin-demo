package org.kin.demo.spring.ingress.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author huangjianqin
 * @date 2024/1/1
 */
@SpringBootApplication
@EnableDiscoveryClient
public class IngressGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(IngressGatewayApplication.class, args);
    }
}
