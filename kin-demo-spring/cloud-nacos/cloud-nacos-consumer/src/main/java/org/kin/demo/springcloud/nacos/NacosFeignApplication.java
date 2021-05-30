package org.kin.demo.springcloud.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author huangjianqin
 * @date 2020-07-19
 */
@EnableFeignClients
@SpringBootApplication
public class NacosFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosFeignApplication.class, args);
    }
}
