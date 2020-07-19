package org.kin.demo.spring.cloud.reference;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author huangjianqin
 * @date 2020-07-19
 */
@EnableFeignClients
@SpringCloudApplication
public class ReferenceApplication2 {
    public static void main(String[] args) {
        SpringApplication.run(ReferenceApplication2.class, args);
    }
}
