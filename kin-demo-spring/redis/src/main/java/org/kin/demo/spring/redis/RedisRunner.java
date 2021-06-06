package org.kin.demo.spring.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author huangjianqin
 * @date 2021/6/5
 */
@Component
public class RedisRunner implements ApplicationRunner {
    @Autowired
    private RedisTemplate<String, Object> template;
    @Autowired
    private RedisCacheManager redisCacheManager;
    @Autowired
    private PersonCache personCache;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(template.keys("*"));
//        valueOprs.set(key, person);
//        updatePersonCache();
        System.out.println(personCache.getPersonCache(PersonCache.KEY));
    }

}
