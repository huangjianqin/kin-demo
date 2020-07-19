package org.kin.demo.spring.cloud.reference;

import org.springframework.stereotype.Service;

/**
 * @author huangjianqin
 * @date 2020-07-19
 */
@Service
public class ReferenceServiceFallback2 implements ReferenceService2 {
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
