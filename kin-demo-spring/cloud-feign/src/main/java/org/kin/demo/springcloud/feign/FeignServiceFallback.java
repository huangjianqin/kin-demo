package org.kin.demo.springcloud.feign;

import org.springframework.stereotype.Service;

/**
 * @author huangjianqin
 * @date 2020-07-19
 */
@Service
public class FeignServiceFallback implements FeignService {
    @Override
    public String hello() {
        return "local hello";
    }

    @Override
    public String hello1(String name) {
        return "local hello1----".concat(name);
    }

    @Override
    public String helloHystrix() {
        return "local helloHystrix";
    }
}
