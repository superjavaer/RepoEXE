package com.ischoolbar.programmer.controller.home;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ischoolbar.programmer.entity.admin.Exam;
import com.ischoolbar.programmer.entity.admin.ExamPaper;
import com.ischoolbar.programmer.entity.admin.ExamPaperAnswer;
import com.ischoolbar.programmer.entity.admin.Question;
import com.ischoolbar.programmer.entity.admin.Student;
import com.ischoolbar.programmer.service.admin.ExamPaperAnswerService;
import com.ischoolbar.programmer.service.admin.ExamPaperService;
import com.ischoolbar.programmer.service.admin.ExamService;
import com.ischoolbar.programmer.service.admin.QuestionService;

/**
 * 前台用户答题控制器
 * @author Administrator
 *
 */
@RequestMapping("/home/exam")
@Controller
public class HomeExamController {
	
	
	@Autowired
	private ExamService examService;
	@Autowired
	private ExamPaperService examPaperService;
	@Autowired
	private ExamPaperAnswerService examPaperAnswerService;
	@Autowired
	private QuestionService questionService;
	/**
	 * 开始前检查合法性，随机生成试题
	 * @param examId
	 * @return
	 */
	@RequestMapping(value="/statr_exam",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> startExam(Long examId,HttpServletRequest request){
		Map<String, String> ret = new HashMap<String, String>();
		Exam exam = examService.findById(examId);
		if(exam == null){
			ret.put("type", "error");
			ret.put("msg", "测试信息不存在!");
			return ret;
		}
		if(exam.getEndTime().getTime() < new Date().getTime()){
			ret.put("type", "error");
			ret.put("msg", "该测试已结束!");
			return ret;
		}
		Student student = (Student)request.getSession().getAttribute("student");
		if(exam.getSubjectId().longValue() != student.getSubjectId().longValue()){
			ret.put("type", "error");
			ret.put("msg", "学科不同，无权进行");
			return ret;
		}
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("examId", exam.getId());
		queryMap.put("studentId", student.getId());
		ExamPaper find = examPaperService.find(queryMap);
		if(find != null){
			if(find.getStatus() == 1){
				//表示已经考过
				ret.put("type", "error");
				ret.put("msg", "您已经提交，不能再次作答");
				return ret;
			}
			//走到这里表示习题已经生成，但是没有提交答题，可以开始重新答题
			ret.put("type", "success");
			ret.put("msg", "可以开始");
			return ret;
		}
		//此时，说明符合条件，随机生成习题试题
		//要做判断，看是否满足生成习题的条件
		//获取单选题总数
		Map<String, Long> qMap = new HashMap<String, Long>();
		qMap.put("questionType", Long.valueOf(Question.QUESTION_TYPE_SINGLE));
		qMap.put("subjectId", exam.getSubjectId());
		int singleQuestionTotalNum = questionService.getQuestionNumByType(qMap);
		if(exam.getSingleQuestionNum() > singleQuestionTotalNum){
			ret.put("type", "error");
			ret.put("msg", "单选题数量超过题库单选题总数，无法生成习题!");
			return ret;
		}
		//获取多选题总数
		qMap.put("questionType", Long.valueOf(Question.QUESTION_TYPE_MUILT));
		int muiltQuestionTotalNum = questionService.getQuestionNumByType(qMap);
		if(exam.getMuiltQuestionNum() > muiltQuestionTotalNum){
			ret.put("type", "error");
			ret.put("msg", "多选题数量超过题库多选题总数，无法生成习题!");
			return ret;
		}
		//获取判断题总数
		qMap.put("questionType", Long.valueOf(Question.QUESTION_TYPE_CHARGE));
		int chargeQuestionTotalNum = questionService.getQuestionNumByType(qMap);
		if(exam.getChargeQuestionNum() > chargeQuestionTotalNum){
			ret.put("type", "error");
			ret.put("msg", "判断题数量超过题库判断题总数，无法生成习题!");
			return ret;
		}


		//所有条件都满足，开始创建习题，随机生成试题
		ExamPaper examPaper = new ExamPaper();
		examPaper.setCreateTime(new Date());
		examPaper.setExamId(examId);
		examPaper.setStatus(0);
		examPaper.setStudentId(student.getId());
		examPaper.setTotalScore(exam.getTotalScore());
		examPaper.setUseTime(0);
		if(examPaperService.add(examPaper) <= 0){
			ret.put("type", "error");
			ret.put("msg", "习题生成失败，请联系管理员!");
			return ret;
		}

		//习题已经正确生成，随机生成试题
		Map<String, Object> queryQuestionMap = new HashMap<String, Object>();
		queryQuestionMap.put("questionType", Question.QUESTION_TYPE_SINGLE);
		queryQuestionMap.put("subjectId", exam.getSubjectId());
		queryQuestionMap.put("offset", 0);
		queryQuestionMap.put("pageSize", 99999);
		if(exam.getSingleQuestionNum() > 0){
			//答题规定单选题数量大于0
			//获取所有的单选题
			List<Question> singleQuestionList = questionService.findList(queryQuestionMap);
			//随机选取答题规定数量的单选题，插入到数据库中
			List<Question> selectedSingleQuestionList = getRandomList(singleQuestionList, exam.getSingleQuestionNum());
			insertQuestionAnswer(selectedSingleQuestionList, examId, examPaper.getId(), student.getId());
		}
		if(exam.getMuiltQuestionNum() > 0){
			queryQuestionMap.put("questionType", Question.QUESTION_TYPE_MUILT);
			//获取所有的多选题
			List<Question> muiltQuestionList = questionService.findList(queryQuestionMap);
			//随机选取答题规定数量的多选题，插入到数据库中
			List<Question> selectedMuiltQuestionList = getRandomList(muiltQuestionList, exam.getMuiltQuestionNum());
			insertQuestionAnswer(selectedMuiltQuestionList, examId, examPaper.getId(), student.getId());
		}
		if(exam.getChargeQuestionNum() > 0){
			//获取所有的判断题
			queryQuestionMap.put("questionType", Question.QUESTION_TYPE_CHARGE);
			List<Question> chargeQuestionList = questionService.findList(queryQuestionMap);
			//随机选取答题规定数量的判断题，插入到数据库中
			List<Question> selectedChargeQuestionList = getRandomList(chargeQuestionList, exam.getChargeQuestionNum());
			insertQuestionAnswer(selectedChargeQuestionList, examId, examPaper.getId(), student.getId());
		}
		exam.setPaperNum(exam.getPaperNum() + 1);
		examService.updateExam(exam);
		ret.put("type", "success");
		ret.put("msg", "习题生成成功!");
		return ret;


		//生成指定试题方法



	}




	/**
	 * 开始进行
	 * @param model
	 * @param examId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/examing",method = RequestMethod.GET)
	public ModelAndView index(ModelAndView model,Long examId,HttpServletRequest request){
		Student student = (Student)request.getSession().getAttribute("student");
		Exam exam = examService.findById(examId);
		if(exam == null){
			model.setViewName("/home/exam/error");
			model.addObject("msg", "当前测试不存在!");
			return model;
		}
		if(exam.getEndTime().getTime() < new Date().getTime()){
			model.setViewName("/home/exam/error");
			model.addObject("msg", "测试时间已经过期!");
			return model;
		}
		if(exam.getSubjectId().longValue() != student.getSubjectId().longValue()){
			model.setViewName("/home/exam/error");
			model.addObject("msg", "您所属科目与测试科目不符合，不能进行!");
			return model;
		}
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("examId", examId);
		queryMap.put("studentId", student.getId());
		//根据测试信息和学生信息获取习题
		ExamPaper examPaper = examPaperService.find(queryMap);
		if(examPaper == null){
			model.setViewName("/home/exam/error");
			model.addObject("msg", "当前测试不存在习题");
			return model;
		}
		if(examPaper.getStatus() == 1){
			model.setViewName("/home/exam/error");
			model.addObject("msg", "您已经做过这次习题了");
			return model;
		}
		Date now = new Date();
		Object attributeStartTime = request.getSession().getAttribute("startExamTime");
		if(attributeStartTime == null){
			request.getSession().setAttribute("startExamTime", now);
		}
		Date startExamTime = (Date)request.getSession().getAttribute("startExamTime");
		int passedTime = (int)(now.getTime() - startExamTime.getTime())/1000/60;
		if(passedTime >= exam.getAvaliableTime()){
			//表示时间已经耗尽，按零分处理
			examPaper.setScore(0);
			examPaper.setStartExamTime(startExamTime);
			examPaper.setStatus(1);
			examPaper.setUseTime(passedTime);
			examPaperService.submitPaper(examPaper);
			model.setViewName("/home/exam/error");
			model.addObject("msg", "当前答题时间已耗尽");
			return model;
		}
		Integer hour = (exam.getAvaliableTime()-passedTime)/60;
		Integer minitute = (exam.getAvaliableTime()-passedTime)%60;
		Integer second = (exam.getAvaliableTime()*60-(int)(now.getTime() - startExamTime.getTime())/1000)%60;
		queryMap.put("examPaperId", examPaper.getId());
		List<ExamPaperAnswer> findListByUser = examPaperAnswerService.findListByUser(queryMap);
		model.addObject("title", exam.getName()+"-开始");
		//获取答案和分数
		model.addObject("singleQuestionList", getExamPaperAnswerList(findListByUser, Question.QUESTION_TYPE_SINGLE));
//		System.out.println(getExamPaperAnswerList(findListByUser, Question.QUESTION_TYPE_SINGLE));
		model.addObject("muiltQuestionList", getExamPaperAnswerList(findListByUser, Question.QUESTION_TYPE_MUILT));
		model.addObject("chargeQuestionList", getExamPaperAnswerList(findListByUser, Question.QUESTION_TYPE_CHARGE));
		model.addObject("hour",hour);
		model.addObject("minitute",minitute);
		model.addObject("second",second);
		model.addObject("exam", exam);
		model.addObject("examPaper", examPaper);
		model.addObject("singleScore", Question.QUESTION_TYPE_SINGLE_SCORE);
		model.addObject("muiltScore", Question.QUESTION_TYPE_MUILT_SCORE);
		model.addObject("chargeScore", Question.QUESTION_TYPE_CHARGE_SCORE);
		model.addObject("singleQuestion", Question.QUESTION_TYPE_SINGLE);
		model.addObject("muiltQuestion", Question.QUESTION_TYPE_MUILT);
		model.addObject("chargeQuestion", Question.QUESTION_TYPE_CHARGE);
		model.setViewName("/home/exam/examing");
		return model;
	}
	
	/**
	 * 用户选择提交单个答案
	 * @param examPaperAnswer
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/submit_answer",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> submitAnswer(ExamPaperAnswer examPaperAnswer,HttpServletRequest request){
		Map<String, String> ret = new HashMap<String, String>();
		if(examPaperAnswer == null){
			ret.put("type", "error");
			ret.put("msg", "请正确操作!");
			return ret;
		}
		Exam exam = examService.findById(examPaperAnswer.getExamId());
		if(exam == null){
			ret.put("type", "error");
			ret.put("msg", "答题信息不存在!");
			return ret;
		}
		if(exam.getEndTime().getTime() < new Date().getTime()){
			ret.put("type", "error");
			ret.put("msg", "该答题已结束!");
			return ret;
		}
		Student student = (Student)request.getSession().getAttribute("student");
		if(exam.getSubjectId().longValue() != student.getSubjectId().longValue()){
			ret.put("type", "error");
			ret.put("msg", "学科不同，无权进行答题!");
			return ret;
		}
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("examId", exam.getId());
		queryMap.put("studentId", student.getId());
		ExamPaper findExamPaper = examPaperService.find(queryMap);
		if(findExamPaper == null){
			ret.put("type", "error");
			ret.put("msg", "不存在习题!");
			return ret;
		}
		if(findExamPaper.getId().longValue() != examPaperAnswer.getExamPaperId().longValue()){
			ret.put("type", "error");
			ret.put("msg", "答题习题不正确，请规范操作!");
			return ret;
		}
		Question question = questionService.findById(examPaperAnswer.getQuestionId());
		if(question == null){
			ret.put("type", "error");
			ret.put("msg", "试题不存在，请规范操作!");
			return ret;
		}

		//此时，可以将答案插入到数据库中
		examPaperAnswer.setStudentId(student.getId());
		if(question.getAnswer().equals(examPaperAnswer.getAnswer())){
			examPaperAnswer.setIsCorrect(1);
		}
		if(examPaperAnswerService.submitAnswer(examPaperAnswer) <= 0){
			ret.put("type", "error");
			ret.put("msg", "出错，请联系管理员!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "成功!");
		return ret;
	}
	
	@RequestMapping(value="/submit_exam",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> submitExam(Long examId,Long examPaperId,HttpServletRequest request){
		Map<String, String> ret = new HashMap<String, String>();
		Exam exam = examService.findById(examId);
		if(exam == null){
			ret.put("type", "error");
			ret.put("msg", "答题不存在，请正确操作!");
			return ret;
		}
		if(exam.getEndTime().getTime() < new Date().getTime()){
			ret.put("type", "error");
			ret.put("msg", "该答题已结束!");
			return ret;
		}
		Student student = (Student)request.getSession().getAttribute("student");
		if(exam.getSubjectId().longValue() != student.getSubjectId().longValue()){
			ret.put("type", "error");
			ret.put("msg", "学科不同，无权进行操作!");
			return ret;
		}
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("examId", exam.getId());
		queryMap.put("studentId", student.getId());
		ExamPaper findExamPaper = examPaperService.find(queryMap);
		if(findExamPaper == null){
			ret.put("type", "error");
			ret.put("msg", "不存在习题!");
			return ret;
		}
		if(findExamPaper.getId().longValue() != examPaperId.longValue()){
			ret.put("type", "error");
			ret.put("msg", "答题习题不正确，请规范操作!");
			return ret;
		}
		if(findExamPaper.getStatus() == 1){
			ret.put("type", "error");
			ret.put("msg", "请勿重复交卷!");
			return ret;
		}
		//此时计算答题得分
		queryMap.put("examPaperId", examPaperId);
		List<ExamPaperAnswer> examPaperAnswerList = examPaperAnswerService.findListByUser(queryMap);
		//此时，可以将答案插入到数据库中
		int score = 0;
		for(ExamPaperAnswer examPaperAnswer:examPaperAnswerList){
			if(examPaperAnswer.getIsCorrect() == 1){
				score += examPaperAnswer.getQuestion().getScore();
			}
		}
		findExamPaper.setEndExamTime(new Date());
		findExamPaper.setScore(score);
		findExamPaper.setStartExamTime((Date)request.getSession().getAttribute("startExamTime"));
		findExamPaper.setStatus(1);
		findExamPaper.setUseTime((int)(findExamPaper.getEndExamTime().getTime()-findExamPaper.getStartExamTime().getTime())/1000/60);
		examPaperService.submitPaper(findExamPaper);
		//计算答题统计结果,更新答题的已考人数，及格人数
		exam.setExamedNum(exam.getExamedNum() + 1);
		if(findExamPaper.getScore() >= exam.getPassScore()){
			//说明及格了
			exam.setPassNum(exam.getPassNum() + 1);
		}
		request.getSession().setAttribute("startExamTime", null);
		//更新统计结果
		examService.updateExam(exam);
		ret.put("type", "success");
		ret.put("msg", "提交成功!");
		return ret;
	}
	
	
	/**
	 * 随机从给定的list中选取给定数量的元素
	 * @param questionList
	 * @param n
	 * @return
	 */
	private List<Question> getRandomList(List<Question> questionList,int n){
		if(questionList.size() <= n)return questionList;
		Map<Integer, String> selectedMap = new HashMap<Integer, String>();
		List<Question> selectedList = new ArrayList<Question>();
		while(selectedList.size() < n){
			for(Question question:questionList){
				int index = (int)(Math.random() * questionList.size());
				if(!selectedMap.containsKey(index)){
					selectedMap.put(index, "");
					selectedList.add(questionList.get(index));
					if(selectedList.size() >= n)break;
				}
			}
		}
		return selectedList;
	}
	
	/**
	 * 插入指定数量的试题到详情表
	 * @param questionList
	 * @param examId
	 * @param examPaperId
	 * @param studentId
	 */
	private void insertQuestionAnswer(List<Question> questionList,Long examId,Long examPaperId,Long studentId){
		for(Question question:questionList){
			ExamPaperAnswer examPaperAnswer = new ExamPaperAnswer();
			examPaperAnswer.setExamId(examId);
			examPaperAnswer.setExamPaperId(examPaperId);
			examPaperAnswer.setIsCorrect(0);
			examPaperAnswer.setQuestionId(question.getId());
			examPaperAnswer.setStudentId(studentId);
			examPaperAnswerService.add(examPaperAnswer);
		}
	}
	
	/**
	 * 返回指定类型的试题
	 * @param examPaperAnswers
	 * @param questionType
	 * @return
	 */
	private List<ExamPaperAnswer> getExamPaperAnswerList(List<ExamPaperAnswer> examPaperAnswers,int questionType){
		List<ExamPaperAnswer> newExamPaperAnswers = new ArrayList<ExamPaperAnswer>();
		for(ExamPaperAnswer examPaperAnswer:examPaperAnswers){
			if(examPaperAnswer.getQuestion().getQuestionType() == questionType){
				newExamPaperAnswers.add(examPaperAnswer);
			}
		}
		return newExamPaperAnswers;
	}
}	
