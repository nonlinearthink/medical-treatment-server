package com.example.server.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

/**
 * @author nonlinearthink
 */
@Configuration
public class RedisConfigurer {

    @Value("${redis.database.auth}")
    private int authDb;

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.timeout}")
    private int timeout;

    @Value("${redis.pool.max-active}")
    private int maxActive;

    @Value("${redis.pool.max-idle}")
    private int maxIdle;

    @Value("${redis.pool.min-idle}")
    private int minIdle;

    @Value("${redis.pool.max-wait}")
    private int maxWait;

    private StringRedisTemplate getStringRedisTemplate(int database) {
        // 构建工厂对象
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
        config.setPassword(RedisPassword.of(password));
        LettucePoolingClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(timeout))
                .poolConfig(getPoolConfig())
                .build();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(config, clientConfig);
        // 设置使用的redis数据库
        factory.setDatabase(database);
        // 重新初始化工厂
        factory.afterPropertiesSet();
        return new StringRedisTemplate(factory);
    }

    @Bean
    public GenericObjectPoolConfig getPoolConfig(){
        // 配置redis连接池
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWaitMillis(maxWait);
        return poolConfig;
    }

    @Bean(name = "authRedisTemplate")
    public StringRedisTemplate getAuthRedisTemplate(){
        return getStringRedisTemplate(authDb);
    }

}
