package org.kin.demo.spring.cloud.diamond;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 注册中心2, 注册中心相互注册
 *
 * @author huangjianqin
 * @date 2020-07-17
 */
@EnableEurekaServer
@SpringCloudApplication
public class Diamond2 {
    public static void main(String[] args) {
        SpringApplication.run(Diamond2.class, args);
    }
}
