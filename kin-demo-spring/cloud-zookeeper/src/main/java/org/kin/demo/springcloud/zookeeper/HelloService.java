package org.kin.demo.springcloud.zookeeper;

import org.kin.framework.utils.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 与spring cloud其余微服务整合差不多, 但是缺乏一些工具的支持, 比如一些类似eureka界面
 *
 * @author huangjianqin
 * @date 2020-07-30
 */
@SpringCloudApplication
@RestController
public class HelloService {
    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/hello")
    public String hello() {
        return StringUtils.mkString(discoveryClient.getServices());
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloService.class, args);
    }
}
