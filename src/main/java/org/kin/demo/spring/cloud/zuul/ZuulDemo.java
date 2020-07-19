package org.kin.demo.spring.cloud.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author huangjianqin
 * @date 2020-07-19
 */
@EnableZuulProxy
@SpringCloudApplication
public class ZuulDemo {
    public static void main(String[] args) {
        SpringApplication.run(ZuulDemo.class, args);
    }
}
