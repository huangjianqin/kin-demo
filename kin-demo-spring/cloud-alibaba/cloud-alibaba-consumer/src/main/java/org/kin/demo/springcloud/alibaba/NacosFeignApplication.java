package org.kin.demo.springcloud.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author huangjianqin
 * @date 2020-07-19
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class NacosFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosFeignApplication.class, args);
    }
}
