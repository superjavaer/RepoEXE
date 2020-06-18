package com.ischoolbar.programmer.entity.admin;

import org.springframework.stereotype.Component;

/**
 * �Ծ������Ϣʵ��
 * @author Administrator
 *
 */
@Component
public class ExamPaperAnswer {
	
	private Long id;
	private Long studentId;//����ѧ��ID
	private Long examId;//��������ID
	private Long examPaperId;//�����Ծ�ID
	private Long questionId;//��������ID
	private String answer;//�ύ��
	private int isCorrect;//�Ƿ���ȷ
	private Question question;//���⼯

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Long getExamPaperId() {
		return examPaperId;
	}
	public void setExamPaperId(Long examPaperId) {
		this.examPaperId = examPaperId;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getIsCorrect() {
		return isCorrect;
	}
	public void setIsCorrect(int isCorrect) {
		this.isCorrect = isCorrect;
	}
	public Long getExamId() {
		return examId;
	}
	public void setExamId(Long examId) {
		this.examId = examId;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}

	
}
