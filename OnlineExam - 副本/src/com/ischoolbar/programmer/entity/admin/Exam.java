package com.ischoolbar.programmer.entity.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * 考试信息实体
 * @author Administrator
 *
 */
@Component
public class Exam {
	
	private Long id;
	private String name;//考试名称
	private Long subjectId;//所属学科专业ID
	private Date startTime;//考试开始时间
	private Date endTime;//考试结束时间
	private int avaliableTime;//考试所需时间
	private int questionNum;//试题总数
	private int totalScore;//总分
	private int passScore;//及格分数
	private int singleQuestionNum;//单选题数量
	private int muiltQuestionNum;//多选题数量
	private int chargeQuestionNum;//判断题数量
	private int paperNum;//试卷数量
	private int examedNum;//已考人数
	private int passNum;//及格人数
	private Date createTime;//添加时间
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			this.startTime = sdf.parse(startTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			this.endTime = sdf.parse(endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getQuestionNum() {
		return questionNum;
	}
	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}
	public int getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	public int getPassScore() {
		return passScore;
	}
	public void setPassScore(int passScore) {
		this.passScore = passScore;
	}
	public int getSingleQuestionNum() {
		return singleQuestionNum;
	}
	public void setSingleQuestionNum(int singleQuestionNum) {
		this.singleQuestionNum = singleQuestionNum;
	}
	public int getMuiltQuestionNum() {
		return muiltQuestionNum;
	}
	public void setMuiltQuestionNum(int muiltQuestionNum) {
		this.muiltQuestionNum = muiltQuestionNum;
	}
	public int getChargeQuestionNum() {
		return chargeQuestionNum;
	}
	public void setChargeQuestionNum(int chargeQuestionNum) {
		this.chargeQuestionNum = chargeQuestionNum;
	}
	public int getPaperNum() {
		return paperNum;
	}
	public void setPaperNum(int paperNum) {
		this.paperNum = paperNum;
	}
	public int getExamedNum() {
		return examedNum;
	}
	public void setExamedNum(int examedNum) {
		this.examedNum = examedNum;
	}
	public int getPassNum() {
		return passNum;
	}
	public void setPassNum(int passNum) {
		this.passNum = passNum;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getAvaliableTime() {
		return avaliableTime;
	}
	public void setAvaliableTime(int avaliableTime) {
		this.avaliableTime = avaliableTime;
	}
	
}
