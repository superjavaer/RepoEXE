package com.ischoolbar.programmer.service.admin.impl;
/**
 * øº ‘service µœ÷¿‡
 */
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ischoolbar.programmer.dao.admin.ExamDao;
import com.ischoolbar.programmer.entity.admin.Exam;
import com.ischoolbar.programmer.service.admin.ExamService;
@Service
public class ExamServiceImpl implements ExamService {

	@Autowired
	private ExamDao examDao;

	@Override
	public int add(Exam exam) {
		// TODO Auto-generated method stub
		return examDao.add(exam);
	}

	@Override
	public int edit(Exam exam) {
		// TODO Auto-generated method stub
		return examDao.edit(exam);
	}

	@Override
	public List<Exam> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return examDao.findList(queryMap);
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return examDao.delete(id);
	}

	@Override
	public Integer getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return examDao.getTotal(queryMap);
	}

	@Override
	public List<Exam> findListByUser(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return examDao.findListByUser(queryMap);
	}

	@Override
	public Integer getTotalByUser(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return examDao.getTotalByUser(queryMap);
	}

	@Override
	public Exam findById(Long id) {
		// TODO Auto-generated method stub
		return examDao.findById(id);
	}

	@Override
	public int updateExam(Exam exam) {
		// TODO Auto-generated method stub
		return examDao.updateExam(exam);
	}


}
