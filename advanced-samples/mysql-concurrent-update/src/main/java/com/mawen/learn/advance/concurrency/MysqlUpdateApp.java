package com.mawen.learn.advance.concurrency;

import org.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/6/19
 */
@MapperScan(basePackages = "com.mawen.learn.advance.concurrency.mapper")
@SpringBootApplication
public class MysqlUpdateApp {

	public static void main(String[] args) {
		SpringApplication.run(MysqlUpdateApp.class, args);
	}
}
