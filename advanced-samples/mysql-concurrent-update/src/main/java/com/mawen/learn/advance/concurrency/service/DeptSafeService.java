package com.mawen.learn.advance.concurrency.service;

import java.util.concurrent.Phaser;

import com.mawen.learn.advance.concurrency.entity.DeptEntity;
import com.mawen.learn.advance.concurrency.mapper.DeptMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/6/19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeptSafeService {

	private static final String KEY = "DEPT:";

	private final DeptMapper deptMapper;
	private final LockService lockService;

	@Autowired
	@Qualifier("twoPhaser")
	private Phaser twoParser;

	@Transactional
	public void deleteById(long id) {
		twoParser.arriveAndAwaitAdvance();

		try {
			Thread.sleep(10L);
		}
		catch (InterruptedException e) {

		}

		boolean result = lockService.lock(KEY + id, "DeptSafeService#deleteById", () -> {
			int ret = deptMapper.delete(id);
			assert ret == 1;
		});

		log.info("Lock {}", result);
	}

	@Transactional
	public void updateParent(long id, long parentId) {
		twoParser.arriveAndAwaitAdvance();

		boolean result = lockService.lock(new String[]{KEY + parentId, KEY + id}, "DeptSafeService#updateParent", () -> {
			DeptEntity deptEntity = selectById(parentId);
			assert deptEntity != null;

			int ret = deptMapper.updateParent(id, parentId);
			assert ret == 1;
		});

		log.info("Lock {}", result);
	}

	public DeptEntity selectById(long id) {
		return deptMapper.selectById(id);
	}
}
