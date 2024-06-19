package com.mawen.learn.advance.concurrency.entity;

import lombok.Data;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/6/19
 */
@Data
public class DeptEntity {

	private Long id;

	private String name;

	private Long parentId;
}
