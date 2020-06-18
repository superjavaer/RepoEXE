package com.ischoolbar.programmer.service.admin.impl;
/**
 * 试题service实现类
 */
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ischoolbar.programmer.dao.admin.QuestionDao;
import com.ischoolbar.programmer.entity.admin.Question;
import com.ischoolbar.programmer.service.admin.QuestionService;
@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionDao questionDao;

	@Override
	public int add(Question question) {
		// TODO Auto-generated method stub
		return questionDao.add(question);
	}

	@Override
	public int edit(Question question) {
		// TODO Auto-generated method stub
		return questionDao.edit(question);
	}

	@Override
	public List<Question> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return questionDao.findList(queryMap);
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return questionDao.delete(id);
	}

	@Override
	public Integer getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return questionDao.getTotal(queryMap);
	}

	@Override
	public Question findByTitle(String title) {
		// TODO Auto-generated method stub
		return questionDao.findByTitle(title);
	}

	@Override
	public int getQuestionNumByType(Map<String, Long> queryMap) {
		// TODO Auto-generated method stub
		return questionDao.getQuestionNumByType(queryMap);
	}

	@Override
	public Question findById(Long id) {
		// TODO Auto-generated method stub
		return questionDao.findById(id);
	}
	
	

}
