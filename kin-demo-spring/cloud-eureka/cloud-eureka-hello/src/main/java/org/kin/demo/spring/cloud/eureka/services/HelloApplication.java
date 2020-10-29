package org.kin.demo.spring.cloud.eureka.services;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author huangjianqin
 * @date 2020-07-16
 */
@SpringCloudApplication
@MapperScan("org.kin.demo.spring.cloud.eureka.services")
@EnableTransactionManagement(proxyTargetClass = true)
public class HelloApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class);
    }
}
