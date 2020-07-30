package org.kin.demo.spring.cloud.zookeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huangjianqin
 * @date 2020-07-30
 */
@SpringCloudApplication
@RestController
@EnableFeignClients
public class HelloClient {
    @Resource
    private HelloServiceClient helloServiceClient;

    @GetMapping("/consumeHello")
    public String consumeHello() {
        return helloServiceClient.hello();
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloClient.class, args);
    }
}
