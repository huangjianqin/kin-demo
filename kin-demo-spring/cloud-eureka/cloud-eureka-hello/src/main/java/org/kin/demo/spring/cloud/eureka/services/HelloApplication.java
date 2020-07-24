package org.kin.demo.spring.cloud.eureka.services;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author huangjianqin
 * @date 2020-07-16
 */
@SpringCloudApplication
public class HelloApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class);
    }
}
