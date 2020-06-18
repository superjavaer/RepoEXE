package com.ischoolbar.programmer.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ischoolbar.programmer.entity.admin.Subject;

/**
 * 学科专业service类
 * @author Administrator
 *
 */
@Service
public interface SubjectService {
	public int add(Subject subject);
	public int edit(Subject subject);
	public List<Subject> findList(Map<String, Object> queryMap);
	public int delete(Long id);
	public Integer getTotal(Map<String, Object> queryMap);
	public Subject findById(Long id);
}
