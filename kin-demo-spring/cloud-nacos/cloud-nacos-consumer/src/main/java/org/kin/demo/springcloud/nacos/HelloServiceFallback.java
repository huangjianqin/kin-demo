package org.kin.demo.springcloud.nacos;

import org.springframework.stereotype.Service;

/**
 * @author huangjianqin
 * @date 2020-07-19
 */
@Service
public class HelloServiceFallback implements HelloService {
    @Override
    public String hello() {
        return "local hello";
    }
}