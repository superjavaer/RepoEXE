package com.ischoolbar.programmer.dao.admin;

import java.util.List;
import java.util.Map;

import com.ischoolbar.programmer.entity.admin.Rank;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ischoolbar.programmer.entity.admin.Student;

/**
 * Ñ§Éúdao²ã
 * @author Administrator
 *
 */
@Repository
public interface StudentDao {
	public int add(Student student);
	public int edit(Student student);
	public List<Student> findList(Map<String, Object> queryMap);
	public int delete(Long id);
	public Integer getTotal(Map<String, Object> queryMap);
	public Student findByName(String name);
	public List<Rank> queryrankBytestName(@Param("testName") String testName);
}
