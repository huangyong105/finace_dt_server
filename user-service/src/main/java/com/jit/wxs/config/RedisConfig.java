package com.jit.wxs.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.cache.interceptor.KeyGenerator;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author fhp
 * @date 2018/9/25
 */
@Configuration
@ConditionalOnClass({JedisCluster.class})
public class RedisConfig  extends CachingConfigurerSupport {


    private final Logger logger = LoggerFactory.getLogger(RedisConfig.class);
/*
    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;*/
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;
    @Value("${spring.redis.commandTimeout}")
    private int commandTimeout;

 /*   @Bean
    public JedisCluster jedisCluster(){
        String[] cNodes = clusterNodes.split(",");
        Set<HostAndPort> nodes = new HashSet<>();
        //分割出集群节点
        for (String node: cNodes) {
            String[] hp = node.split(":");
            nodes.add(new HostAndPort(hp[0],Integer.parseInt(hp[1])));
        }
        GenericObjectPoolConfig  pool = new GenericObjectPoolConfig();
        pool.setMaxIdle(maxIdle);
        pool.setMaxWaitMillis(maxWaitMillis);
        pool.setTestOnBorrow(true);
        pool.setMaxIdle(maxIdle);
        pool.setMaxWaitMillis(maxWaitMillis);
        //创建集群对象
        JedisCluster jedisCluster=  new JedisCluster(nodes,commandTimeout,100,pool);
        System.out.println(jedisCluster.getClusterNodes());
        jedisCluster.set("key1","test");
        System.out.println(jedisCluster.get("key1"));
        return jedisCluster;
    }*/

    /**
     * 设置数据存入redis 的序列化方式
     *</br>redisTemplate序列化默认使用的jdkSerializeable,存储二进制字节码,导致key会出现乱码，所以自定义
     *序列化类
     *
     * @paramredisConnectionFactory
     */
    @Bean
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 注解@Cache key生成规则
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            public Object generate(Object target, Method method,
                                   Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    };

    /**
     * 注解@Cache的管理器，设置过期时间的单位是秒
     *
     * @Description:
     * @param redisTemplate
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setDefaultExpiration(600); // 设置key-value超时时间
        return cacheManager;
    }


    /**
     *配置当redis挂掉或者出现问题时,程序继续向下运行操作数据库查询
     */
    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache,
                                            Object key, Object value) {
                RedisErrorException(exception, key);
            }

            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache,
                                            Object key) {
                RedisErrorException(exception, key);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache,
                                              Object key) {
                RedisErrorException(exception, key);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                RedisErrorException(exception, null);
            }
        };
        return cacheErrorHandler;
    }

    protected void RedisErrorException(Exception exception,Object key){
        logger.error("redis异常：key=[{}]", key, exception);
    }
}
