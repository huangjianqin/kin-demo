package org.kin.demo.spring.redis;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * ps: spring aop代理, 在原生类实例内部调用方法, 无法触发aop 拦截
 *
 * @author huangjianqin
 * @date 2021/6/6
 */
@Component
@CacheConfig(cacheNames = PersonCache.CACHE_NAME)
public class PersonCache {
    public static final String CACHE_NAME = "demo";
    public static final String KEY = "person";

    @Cacheable(key = "#key")
    public Object getPersonCache(String key) {
//        ValueOperations<String, Object> valueOprs = template.opsForValue();
//        return valueOprs.get(key);
        return null;
    }

    @CachePut(key = KEY)
    public Person updatePersonCache() {
        return new Person("C", 12);
    }
}
