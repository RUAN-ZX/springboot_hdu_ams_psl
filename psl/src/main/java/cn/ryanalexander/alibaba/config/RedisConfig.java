package cn.ryanalexander.alibaba.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Autowired RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisTemplate<String,Object> ryanRedisTemplate(RedisConnectionFactory redisConnectionFactory){

        RedisTemplate<String,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);


        //Json序列化
        Jackson2JsonRedisSerializer jsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jsonRedisSerializer.setObjectMapper(om);

        //String serializer
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        //Key String
        template.setKeySerializer(stringRedisSerializer);
        //hash String serializer
        template.setHashKeySerializer(stringRedisSerializer);
        //value jackson
        template.setValueSerializer(jsonRedisSerializer);
        //hash value - jackson serializer
        template.setHashKeySerializer(jsonRedisSerializer);
        template.afterPropertiesSet();

        return template;

    }
}
