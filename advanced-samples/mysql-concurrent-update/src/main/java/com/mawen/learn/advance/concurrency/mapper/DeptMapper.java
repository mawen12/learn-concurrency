package com.mawen.learn.advance.concurrency.mapper;

import com.mawen.learn.advance.concurrency.entity.DeptEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/6/19
 */
@Mapper
public interface DeptMapper {

	int delete(@Param("id") long id);

	int updateParent(@Param("id") long id, @Param("pid") long parentId);

	DeptEntity selectById(@Param("id") long id);

	DeptEntity selectByIdForUpdate(@Param("id") long id);
}
