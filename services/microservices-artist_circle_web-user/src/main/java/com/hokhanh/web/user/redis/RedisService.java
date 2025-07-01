package com.hokhanh.web.user.redis;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper objectMapper;
	
	public void setValue(String key, Object value, Duration ttl) {
	    redisTemplate.opsForValue().set(key, value, ttl);
	}
	
	public <T> T getValue(String key, Class<T> clazz) {
	    Object value = redisTemplate.opsForValue().get(key);
	    if (value == null) return null;

	    return objectMapper.convertValue(value, clazz);
	}
	
	public void deleteKey(String key) {
		redisTemplate.delete(key);
	}
}
