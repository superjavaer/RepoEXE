package com.ischoolbar.programmer.entity.admin;

import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * 试题实体类
 * @author Administrator
 *
 */
@Component
public class Question {
	
	//试题类型定义
	public static int QUESTION_TYPE_SINGLE = 0;//单选题型
	public static int QUESTION_TYPE_MUILT = 1;//多选题型
	public static int QUESTION_TYPE_CHARGE = 2;//判断题型
	//试题分值定义，根据试题类型定义分值
	public static int QUESTION_TYPE_SINGLE_SCORE = 2;//单选题型，每题2分
	public static int QUESTION_TYPE_MUILT_SCORE = 4;//多选题型,每题4分
	public static int QUESTION_TYPE_CHARGE_SCORE = 2;//判断题型,每题两分
	
	private Long id;
	private Long subjectId;//学科专业类型
	private int questionType;//试题类型
	private String title;//题目
	private String photo; //图片
	private int score;//分值
	private String attrA;//选项A
	private String attrB;//选项B
	private String attrC;//选项C
	private String attrD;//选项D
	private String answer;//正确答案
	private Date createTime;//添加时间
	
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getQuestionType() {
		return questionType;
	}
	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAttrA() {
		return attrA;
	}
	public void setAttrA(String attrA) {
		this.attrA = attrA;
	}
	public String getAttrB() {
		return attrB;
	}
	public void setAttrB(String attrB) {
		this.attrB = attrB;
	}
	public String getAttrC() {
		return attrC;
	}
	public void setAttrC(String attrC) {
		this.attrC = attrC;
	}
	public String getAttrD() {
		return attrD;
	}
	public void setAttrD(String attrD) {
		this.attrD = attrD;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * 根据试题类型设置分值
	 */
	public void setScoreByType(){
		if(questionType == QUESTION_TYPE_SINGLE)score = QUESTION_TYPE_SINGLE_SCORE;
		if(questionType == QUESTION_TYPE_MUILT)score = QUESTION_TYPE_MUILT_SCORE;
		if(questionType == QUESTION_TYPE_CHARGE)score = QUESTION_TYPE_CHARGE_SCORE;
	}
}
