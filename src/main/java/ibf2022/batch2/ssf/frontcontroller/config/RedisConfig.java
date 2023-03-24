package ibf2022.batch2.ssf.frontcontroller.config;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    // value redis host from appln.properties
    @Value("${spring.redis.host}")
    private String redisHost;

    // value redis port from appln.properties
    @Value("${spring.redis.port}")
    private Optional<Integer> redisPort;

    @Value("${spring.redis.username}")
    private String redisUsername;

    @Value("${spring.redis.password}")
    private String redisPassword;

    // define the return redis template bean as single Object
    // throughout the runtime.
    // Return the RedisTemplate
    @Bean("auth")
    @Scope("singleton")
    public RedisTemplate<String, String> redisTemplate() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();

        config.setHostName(redisHost);
        config.setPort(redisPort.get());

        if (!redisUsername.isEmpty() && !redisPassword.isEmpty()) {
            config.setUsername(redisUsername);
            config.setPassword(redisPassword);
        }
        config.setDatabase(0);

        Duration readTimeout = Duration.ofMillis(180 * 1000);
        Duration connectTimeout = Duration.ofMillis(180 * 1000);
        final JedisClientConfiguration jedisClient = JedisClientConfiguration
                .builder()
                .readTimeout(readTimeout)
                .connectTimeout(connectTimeout)
                .usePooling()
                .build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();

        RedisTemplate<String, String> r = new RedisTemplate<String, String>();
        // associate with the redis connection
        r.setConnectionFactory(jedisFac);

        r.setKeySerializer(new StringRedisSerializer());
        // set the map key/value serialization type to String
        r.setHashKeySerializer(new StringRedisSerializer());

        //RedisSerializer<Object> objSerializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());

        r.setValueSerializer(new StringRedisSerializer());
        r.setHashValueSerializer(new StringRedisSerializer());

        return r;
    }
}
