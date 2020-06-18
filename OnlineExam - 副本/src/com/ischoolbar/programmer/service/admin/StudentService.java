package com.ischoolbar.programmer.service.admin;

import java.util.List;
import java.util.Map;

import com.ischoolbar.programmer.entity.admin.Rank;
import org.springframework.stereotype.Service;

import com.ischoolbar.programmer.entity.admin.Student;

/**
 * øº…˙service¿‡
 * @author Administrator
 *
 */
@Service
public interface StudentService {
	public int add(Student student);
	public int edit(Student student);
	public List<Student> findList(Map<String, Object> queryMap);
	public int delete(Long id);
	public Integer getTotal(Map<String, Object> queryMap);
	public Student findByName(String name);
	public List<Rank> queryrankBytestName(String testName);
}
