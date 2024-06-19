package com.mawen.learn.advance.concurrency.controller;

import com.mawen.learn.advance.concurrency.service.DeptSafeService;
import com.mawen.learn.advance.concurrency.service.DeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/6/19
 */
@Slf4j
@RestController
@RequestMapping("/dept")
@RequiredArgsConstructor
public class DeptController {

	private final DeptService deptService;
	private final DeptSafeService deptSafeService;

	@DeleteMapping("/{id}")
	public String deleteById(@PathVariable long id) {
		deptService.deleteById(id);
		return "OK";
	}

	@PostMapping("/{id}/{parentId}")
	public String updateParent(@PathVariable long id, @PathVariable long parentId) {
		deptService.updateParent(id, parentId);
		return "OK";
	}

	@DeleteMapping("/safe/{id}")
	public String safeDeleteById(@PathVariable long id) {
		deptSafeService.deleteById(id);
		return "OK";
	}

	@PostMapping("/safe/{id}/{parentId}")
	public String safeUpdateParent(@PathVariable long id, @PathVariable long parentId) {
		deptSafeService.updateParent(id, parentId);
		return "OK";
	}
}
