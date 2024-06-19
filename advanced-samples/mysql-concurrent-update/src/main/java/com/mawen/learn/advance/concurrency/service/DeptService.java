package com.mawen.learn.advance.concurrency.service;

import java.util.concurrent.Phaser;

import com.mawen.learn.advance.concurrency.entity.DeptEntity;
import com.mawen.learn.advance.concurrency.mapper.DeptMapper;
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
public class DeptService {

	@Autowired
	private  DeptMapper deptMapper;

	@Autowired
	@Qualifier("twoPhaser")
	private Phaser twoParser;

	@Transactional
	public void deleteById(long id) {
		twoParser.arriveAndAwaitAdvance();

		int ret = deptMapper.delete(id);
		assert ret == 1;
	}

	@Transactional
	public void updateParent(long id, long parentId) {
		twoParser.arriveAndAwaitAdvance();

//		DeptEntity deptEntity = selectById(parentId); // can not used
		DeptEntity deptEntity = selectByIdForUpdate(parentId);
		assert deptEntity != null;

		int ret = deptMapper.updateParent(id, parentId);
		assert ret == 1;
	}

	public DeptEntity selectById(long id) {
		return deptMapper.selectById(id);
	}

	@Transactional
	public DeptEntity selectByIdForUpdate(long id) {
		return deptMapper.selectByIdForUpdate(id);
	}
}
