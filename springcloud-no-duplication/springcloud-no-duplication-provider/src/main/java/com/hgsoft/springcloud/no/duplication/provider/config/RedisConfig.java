package com.hgsoft.springcloud.no.duplication.provider.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;


@Configuration
public class RedisConfig {

    @Value("${spring.redis.host:192.168.1.139}")
    private String redisHost;

    @Value("${spring.redis.port:6379}")
    private Integer redisPort;

    @Value("${spring.redis.database:0}")
    private Integer database;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private Integer maxIdle;

    @Value("${spring.redis.jedis.pool.max-active}")
    private Integer maxTotal;

    @Value("${spring.redis.connect.timeout}")
    private Integer connectTimeout;

    @Value("${spring.redis.read.timeout}")
    private Integer readTimeout;

    @Value("${spring.redis.timeout}")
    private Integer timeout;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private Integer maxWaitMillis;

    @Value("${spring.redis.testOnCreate:true}")
    private boolean testOnCreate;
    @Value("${spring.redis.testOnBorrow:false}")
    private boolean testOnBorrow;
    @Value("${spring.redis.testWhileIdle:false}")
    private boolean testWhileIdle;

//    @ConfigurationProperties(prefix="spring")
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大连接数
        jedisPoolConfig.setMaxTotal(maxTotal);
        //最小空闲连接数
        jedisPoolConfig.setMinIdle(maxIdle);
        //当池内没有可用的连接时，最大等待时间
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setTestOnCreate(testOnCreate);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setTestWhileIdle(testWhileIdle);
        return jedisPoolConfig;
    }

    @Bean
    public JedisClientConfiguration jedisClientConfiguration(JedisPoolConfig jedisPoolConfig) {

        //return JedisClientConfiguration.builder().connectTimeout(Duration.ofMillis(connectTimeout)).readTimeout(Duration.ofMillis(readTimeout)).build();

        JedisClientConfiguration.DefaultJedisClientConfigurationBuilder djcb = (JedisClientConfiguration.DefaultJedisClientConfigurationBuilder) JedisClientConfiguration.builder();
        djcb.readTimeout(Duration.ofMillis(readTimeout));
        djcb.connectTimeout(Duration.ofMillis(connectTimeout));
        djcb.usePooling();
        djcb.poolConfig(jedisPoolConfig);

        //通过构造器来构造jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = djcb.build();
        return jedisClientConfiguration;
    }

    private RedisConfiguration init(int db) {
        RedisStandaloneConfiguration redisConfiguration =
                new RedisStandaloneConfiguration(redisHost, redisPort);
        redisConfiguration.setDatabase(db);
        if (null != password && !"".equals(password))
            redisConfiguration.setPassword(password);
        return redisConfiguration;
    }

    private RedisConfiguration redisConfiguration() {
        return init(database);
    }


    /**
     * Jedis 连接工厂.
     *
     * @return 配置好的Jedis连接工厂
     */
    @Bean("jedisConnectionFactory")
    @Primary
    public JedisConnectionFactory jedisConnectionFactory(JedisClientConfiguration jedisClientConfiguration) {
        RedisConfiguration redisConfiguration = redisConfiguration();
        JedisConnectionFactory jedisConnectionFactory = null;
        if (redisConfiguration instanceof RedisStandaloneConfiguration) {
            jedisConnectionFactory = new JedisConnectionFactory((RedisStandaloneConfiguration) redisConfiguration, jedisClientConfiguration);
        } else if (redisConfiguration instanceof RedisSentinelConfiguration) {
            jedisConnectionFactory = new JedisConnectionFactory((RedisSentinelConfiguration) redisConfiguration, jedisClientConfiguration);
        } else if (redisConfiguration instanceof RedisClusterConfiguration) {
            jedisConnectionFactory = new JedisConnectionFactory((RedisClusterConfiguration) redisConfiguration, jedisClientConfiguration);
        } else {
            jedisConnectionFactory = new JedisConnectionFactory((RedisStandaloneConfiguration) redisConfiguration, jedisClientConfiguration);
        }
        return jedisConnectionFactory;
    }

    @Bean(value = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        /*
         * Redis 序列化器.
         *
         * RedisTemplate 默认的系列化类是 JdkSerializationRedisSerializer,用JdkSerializationRedisSerializer序列化的话,
         * 被序列化的对象必须实现Serializable接口。在存储内容时，除了属性的内容外还存了其它内容在里面，总长度长，且不容易阅读。
         *
         * Jackson2JsonRedisSerializer 和 GenericJackson2JsonRedisSerializer，两者都能系列化成 json，
         * 但是后者会在 json 中加入 @class 属性，类的全路径包名，方便反系列化。前者如果存放了 List 则在反系列化的时候如果没指定
         * TypeReference 则会报错 java.util.LinkedHashMap cannot be cast to
         */
        RedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        RedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // 定义RedisTemplate，并设置连接工程
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();

        // key 的序列化采用 StringRedisSerializer
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // value 值的序列化采用 GenericJackson2JsonRedisSerializer
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        // 设置连接工厂
        redisTemplate.setConnectionFactory(jedisConnectionFactory);

        return redisTemplate;
    }

}
