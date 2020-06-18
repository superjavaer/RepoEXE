package com.ischoolbar.programmer.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ischoolbar.programmer.entity.admin.Subject;

/**
 * 学科专业dao层
 * @author Administrator
 *
 */
@Repository
public interface SubjectDao {
	public int add(Subject subject);
	public int edit(Subject subject);
	public List<Subject> findList(Map<String, Object> queryMap);
	public int delete(Long id);
	public Integer getTotal(Map<String, Object> queryMap);
	public Subject findById(Long id);
}
