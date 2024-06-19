package com.mawen.learn.advance.concurrency.service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/6/19
 */
@Slf4j
@Component
public class LockService {

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;

	public boolean lock(String key, String desc, Runnable runnable) {
		if (lock(key, desc)) {
			try {
				runnable.run();
				return true;
			}
			finally {
				unlock(key);
			}
		}
		return false;
	}

	public boolean lock(String[] keys, String desc, Runnable runnable) {
		List<KeyValue<String, Boolean>> result = Stream.of(keys).map(key -> new KeyValue<>(key, lock(key, desc))).collect(Collectors.toList());
		if (result.stream().allMatch(r -> Boolean.TRUE.equals(r.getValue()))) {
			try {
				runnable.run();
				return true;
			}
			finally {
				Stream.of(keys).forEach(this::unlock);
			}
		}
		else {
			result.stream().filter(value -> Boolean.TRUE.equals(value.getValue())).forEach(value -> unlock(value.getKey()));
			return false;
		}
	}

	public boolean lock(String key, String desc) {
		Boolean result = redisTemplate.opsForValue().setIfAbsent(key, System.currentTimeMillis(), Duration.ofSeconds(30));
		log.info("{} Lock {} {}", desc, key, result);
		return Boolean.TRUE.equals(result);
	}

	public void unlock(String key) {
		redisTemplate.delete(key);
	}

	@Data
	private static class KeyValue<T, V> {
		private final T key;
		private final V value;
	}

}
