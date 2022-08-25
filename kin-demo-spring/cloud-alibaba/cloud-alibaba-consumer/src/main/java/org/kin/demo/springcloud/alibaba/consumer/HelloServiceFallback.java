package org.kin.demo.springcloud.alibaba.consumer;

import org.springframework.stereotype.Service;

/**
 * @author huangjianqin
 * @date 2020-07-19
 */
@Service
public class HelloServiceFallback implements HelloService {
    @Override
    public String hello() {
        return "local hello()";
    }

    @Override
    public String config() {
        return "local config()";
    }
}
