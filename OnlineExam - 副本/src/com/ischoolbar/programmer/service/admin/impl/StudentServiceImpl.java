package com.ischoolbar.programmer.service.admin.impl;
/**
 * 考生service实现类
 */
import java.util.List;
import java.util.Map;

import com.ischoolbar.programmer.entity.admin.Rank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ischoolbar.programmer.dao.admin.StudentDao;
import com.ischoolbar.programmer.entity.admin.Student;
import com.ischoolbar.programmer.service.admin.StudentService;
@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDao studentDao;

	@Override
	public int add(Student student) {
		// TODO Auto-generated method stub
		return studentDao.add(student);
	}

	@Override
	public int edit(Student student) {
		// TODO Auto-generated method stub
		return studentDao.edit(student);
	}

	@Override
	public List<Student> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return studentDao.findList(queryMap);
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return studentDao.delete(id);
	}

	@Override
	public Integer getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return studentDao.getTotal(queryMap);
	}

	@Override
	public Student findByName(String name) {
		// TODO Auto-generated method stub
		return studentDao.findByName(name);
	}

	@Override
	public List<Rank> queryrankBytestName(String testName) {
		return studentDao.queryrankBytestName(testName);
	}


}
