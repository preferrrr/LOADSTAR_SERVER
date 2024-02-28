package com.lodestar.lodestar_server.config.redis;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Value("${spring.cache.redis.host}")
    private String cacheHost;

    @Value("${spring.cache.redis.port}")
    private int cachePort;

    @Bean(name = "cacheRedisConnectionFactory")
    public RedisConnectionFactory cacheRedisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(cacheHost, cachePort));
    }

    public ObjectMapper redisObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.activateDefaultTyping(BasicPolymorphicTypeValidator
                        .builder()
                        .allowIfSubType(Object.class)
                        .build()
                , ObjectMapper.DefaultTyping.EVERYTHING);
        objectMapper.registerModules(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);

        return objectMapper;
    }
    @Bean(name = "cacheManager")
    public RedisCacheManager redisCacheManager(
            @Qualifier("cacheRedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .disableCachingNullValues()
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(new GenericJackson2JsonRedisSerializer(redisObjectMapper())));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

}
