package org.kin.demo.spring.redis;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * CachingConfigurerSupport用于提供自定义{@link CacheManager}和{@link KeyGenerator}
 * 默认使用{@link org.springframework.cache.interceptor.SimpleKeyGenerator}
 * aop拦截用的是{@link org.springframework.cache.interceptor.CacheInterceptor}
 * cache注解解析是{@link org.springframework.cache.annotation.CacheAnnotationParser}
 *
 * @author huangjianqin
 * @date 2021/6/5
 */
@Configuration
public class RedisConfiguration extends CachingConfigurerSupport {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisSerializer<Object> serializer = redisSerializer();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedisSerializer<Object> redisSerializer() {
        //会自动序列化类型信息进入json, 然后反序列化时, 转换成对应对象
        //感觉原始json更好, 省去类型兼容问题, 也减少空间消耗, 但是需要开发者根据实际开发自定义组件, 方便开发
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    public RedisCacheManager redisCacheManager(LettuceConnectionFactory redisConnectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        //设置Redis缓存有效期为1天
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer()))
                .entryTtl(Duration.ofDays(1))
                /**
                 * 如果使用前缀, 则是{customPrefix + cacheName}::key, 否则只有key
                 */
                .disableKeyPrefix()
                .disableCachingNullValues();
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }

    @Override
    public CacheManager cacheManager() {
        return redisCacheManager(null);
    }
}