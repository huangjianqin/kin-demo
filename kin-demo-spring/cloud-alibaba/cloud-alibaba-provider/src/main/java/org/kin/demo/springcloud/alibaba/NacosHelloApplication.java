package org.kin.demo.springcloud.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author huangjianqin
 * @date 2020-07-16
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosHelloApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosHelloApplication.class);
    }
}
