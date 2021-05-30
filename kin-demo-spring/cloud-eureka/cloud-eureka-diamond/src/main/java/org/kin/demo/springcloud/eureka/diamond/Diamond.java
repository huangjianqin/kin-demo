package org.kin.demo.springcloud.eureka.diamond;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * 注册中心1, 注册中心相互注册
 *
 * @author huangjianqin
 * @date 2020-07-17
 */
@EnableTurbine
@EnableHystrixDashboard
@EnableEurekaServer
@SpringCloudApplication
public class Diamond {
    public static void main(String[] args) {
        SpringApplication.run(Diamond.class, args);
    }
}
