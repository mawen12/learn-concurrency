package com.mawen.learn.advance.concurrency.config;

import java.util.concurrent.Phaser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/6/19
 */
@Configuration
public class ConcurrencyConfig {

	@Bean
	@Primary
	public Phaser twoPhaser() {
		return new Phaser(2);
	}

}
