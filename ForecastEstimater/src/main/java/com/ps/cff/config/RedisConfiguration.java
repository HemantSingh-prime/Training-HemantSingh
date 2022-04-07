package com.ps.cff.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.ps.cff.entity.ForecastManager;
import com.ps.cff.entity.User;

@Configuration
@EnableCaching
public class RedisConfiguration {

	@Value("${spring.redis.host}")
	private String host;
	
	@Value("${spring.redis.port}")
	private int port;
	
	@Value("${spring.cache.type}")
	private String type;
	
	@Bean
	public RedisConnectionFactory redisConnectionsFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration=new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(host);
		redisStandaloneConfiguration.setPort(port);
		
		return new LettuceConnectionFactory(redisStandaloneConfiguration);
	}
	
	@Bean
	public RedisTemplate<String,Object> redisTemplate(){
		RedisTemplate<String,Object> restTemplate=new RedisTemplate();
		restTemplate.setConnectionFactory(redisConnectionsFactory());
		restTemplate.setKeySerializer(new StringRedisSerializer());
		restTemplate.setHashKeySerializer(new StringRedisSerializer());
		restTemplate.setHashKeySerializer(new JdkSerializationRedisSerializer());
		restTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		
		return restTemplate;
	}
	
	
	
}
