package com.ischoolbar.programmer.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ischoolbar.programmer.entity.admin.Question;

/**
 *  ‘Ã‚service¿‡
 * @author Administrator
 *
 */
@Service
public interface QuestionService {
	public int add(Question question);
	public int edit(Question question);
	public List<Question> findList(Map<String, Object> queryMap);
	public int delete(Long id);
	public Integer getTotal(Map<String, Object> queryMap);
	public Question findByTitle(String title);
	public int getQuestionNumByType(Map<String, Long> queryMap);
	public Question findById(Long id);
}
