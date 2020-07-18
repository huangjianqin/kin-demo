package org.kin.demo.spring.cloud.diamond;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * 注册中心2, 注册中心相互注册
 *
 * @author huangjianqin
 * @date 2020-07-17
 */
@EnableTurbine
@EnableHystrixDashboard
@EnableEurekaServer
@SpringCloudApplication
public class Diamond2 {
    public static void main(String[] args) {
        SpringApplication.run(Diamond2.class, args);
    }
}
