package org.kin.demo.springcloud.eureka.diamond;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 注册中心1, 注册中心相互注册
 *
 * @author huangjianqin
 * @date 2020-07-17
 */
@EnableEurekaServer
@SpringBootApplication
public class Diamond {
    public static void main(String[] args) {
        SpringApplication.run(Diamond.class, args);
    }
}
