package com.ischoolbar.programmer.entity.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * ������Ϣʵ��
 * @author Administrator
 *
 */
@Component
public class Exam {
	
	private Long id;
	private String name;//��������
	private Long subjectId;//����ѧ��רҵID
	private Date startTime;//���Կ�ʼʱ��
	private Date endTime;//���Խ���ʱ��
	private int avaliableTime;//��������ʱ��
	private int questionNum;//��������
	private int totalScore;//�ܷ�
	private int passScore;//�������
	private int singleQuestionNum;//��ѡ������
	private int muiltQuestionNum;//��ѡ������
	private int chargeQuestionNum;//�ж�������
	private int paperNum;//�Ծ�����
	private int examedNum;//�ѿ�����
	private int passNum;//��������
	private Date createTime;//���ʱ��
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
