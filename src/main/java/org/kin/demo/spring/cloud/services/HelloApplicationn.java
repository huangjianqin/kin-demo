package org.kin.demo.spring.cloud.services;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author huangjianqin
 * @date 2020-07-16
 */
@EnableDiscoveryClient
@SpringCloudApplication
public class HelloApplicationn {
    public static void main(String[] args) {
        SpringApplication.run(HelloApplicationn.class, args);
    }
}
