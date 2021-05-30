package org.kin.demo.springcloud.eureka.services;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.kin.framework.utils.JSON;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author huangjianqin
 * @date 2020-07-16
 */
@SpringBootApplication
@MapperScan("org.kin.demo.spring.cloud.eureka.services")
@EnableTransactionManagement(proxyTargetClass = true)
@EnableRedisHttpSession
public class HelloApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class);
    }

    /**
     * redis客户端
     */
    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }

    /**
     * 默认的session存储数据序列化方式
     */
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
//        JSON.PARSER.registerModules(SecurityJackson2Modules.getModules(this.loader));
        JSON.PARSER.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY);
        JSON.PARSER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        JSON.PARSER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        return new GenericJackson2JsonRedisSerializer(JSON.PARSER);
    }
}
