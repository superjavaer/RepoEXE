package com.ischoolbar.programmer.controller.home;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ischoolbar.programmer.entity.admin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ischoolbar.programmer.service.admin.ExamPaperAnswerService;
import com.ischoolbar.programmer.service.admin.ExamPaperService;
import com.ischoolbar.programmer.service.admin.ExamService;
import com.ischoolbar.programmer.service.admin.StudentService;
import com.ischoolbar.programmer.service.admin.SubjectService;
import com.ischoolbar.programmer.util.DateFormatUtil;

/**
 * 前台学生中心控制器
 * @author Administrator
 *
 */
@RequestMapping("/home/user")
@Controller
public class HomeStudentController {
	
	@Autowired
	private StudentService studentService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private ExamService examService;
	@Autowired
	private ExamPaperService examPaperService;
	
	@Autowired
	private ExamPaperAnswerService examPaperAnswerService;
	
	private int pageSize = 10;
	/**
	 * 学生中心首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public ModelAndView index(ModelAndView model){
		model.addObject("title", "学生中心");
		model.setViewName("/home/user/index");
		return model;
	}
	
	/**
	 * 学生中心欢迎页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/welcome",method = RequestMethod.GET)
	public ModelAndView welcome(ModelAndView model,HttpServletRequest request){
		model.addObject("title", "学生中心");
		Student student = (Student)request.getSession().getAttribute("student");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("subjectId", student.getSubjectId());
		queryMap.put("startTime", DateFormatUtil.getDate("yyyy-MM-dd hh:mm:ss", new Date()));
		queryMap.put("endTime", DateFormatUtil.getDate("yyyy-MM-dd hh:mm:ss", new Date()));
		queryMap.put("offset", 0);
		queryMap.put("pageSize", 10);
		model.addObject("examList", examService.findListByUser(queryMap));
		queryMap.remove("subjectId");
		queryMap.put("studentId", student.getId());
		model.addObject("historyList", examPaperService.findHistory(queryMap));
		model.addObject("subject", subjectService.findById(student.getSubjectId()));
		model.setViewName("/home/user/welcome");
		return model;
	}
	
	/**
	 * 获取当前登录用户的用户名
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/get_current",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> getCurrent(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		Map<String, String> ret = new HashMap<String, String>();
		Object attribute = request.getSession().getAttribute("student");
		if(attribute == null){
			ret.put("type", "error");
			ret.put("msg", "登录信息失效！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "获取成功！");
		Student student  = (Student)attribute;
		ret.put("username", student.getName());
		ret.put("truename", student.getTrueName());
		return ret;
	}
	
	/**
	 * 用户基本信息页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/profile",method = RequestMethod.GET)
	public ModelAndView profile(ModelAndView model,HttpServletRequest request){
		Student student = (Student)request.getSession().getAttribute("student");
		model.addObject("title", "学生信息");
		model.addObject("student", student);
		model.addObject("subject", subjectService.findById(student.getSubjectId()));
		model.setViewName("/home/user/profile");
		return model;
	}
	
	/**
	 * 修改用户信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update_info",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> updateInfo(Student student,HttpServletRequest request){
		Map<String, String> ret = new HashMap<String, String>();
		Student onlineStudent  = (Student)request.getSession().getAttribute("student");
		onlineStudent.setTel(student.getTel());
		onlineStudent.setTrueName(student.getTrueName());
		if(studentService.edit(onlineStudent) <= 0){
			ret.put("type", "error");
			ret.put("msg", "修改失败，请联系管理员！");
			return ret;
		}
		//重置session中的用户信息
		request.getSession().setAttribute("student", onlineStudent);
		ret.put("type", "success");
		ret.put("msg", "获取成功！");
		return ret;
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout",method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
		request.getSession().setAttribute("student", null);
		return "redirect:login";
	}
	
	/**
	 * 用户修改密码
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/password",method = RequestMethod.GET)
	public ModelAndView password(ModelAndView model,HttpServletRequest request){
		Student student = (Student)request.getSession().getAttribute("student");
		model.addObject("student", student);
		model.setViewName("/home/user/password");
		return model;
	}
	
	/**
	 * 修改密码提交
	 * @param student
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update_password",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> updatePassword(Student student,String oldPassword,HttpServletRequest request){
		Map<String, String> ret = new HashMap<String, String>();
		Student onlineStudent  = (Student)request.getSession().getAttribute("student");
		if(!onlineStudent.getPassword().equals(oldPassword)){
			ret.put("type", "error");
			ret.put("msg", "旧密码错误！");
			return ret;
		}
		onlineStudent.setPassword(student.getPassword());
		if(studentService.edit(onlineStudent) <= 0){
			ret.put("type", "error");
			ret.put("msg", "修改失败，请联系管理员！");
			return ret;
		}
		//重置session中的用户信息
		request.getSession().setAttribute("student", onlineStudent);
		ret.put("type", "success");
		ret.put("msg", "获取成功！");
		return ret;
	}
	
	/**
	 * 获取当前学生正在进行的考试信息
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/exam_list",method = RequestMethod.GET)
	public ModelAndView exameList(ModelAndView model,
			@RequestParam(name="name",defaultValue="") String name,
			@RequestParam(name="page",defaultValue="1") Integer page,
			HttpServletRequest request){
		Student student = (Student)request.getSession().getAttribute("student");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("subjectId", student.getSubjectId());
		queryMap.put("name", name);
		queryMap.put("offset", getOffset(page, pageSize));
		queryMap.put("pageSize", pageSize);
		model.addObject("examList", examService.findListByUser(queryMap));
		model.addObject("name", name);
		model.addObject("subject", subjectService.findById(student.getSubjectId()));
		model.setViewName("/home/user/exam_list");
		if(page < 1)page = 1;
		model.addObject("page", page);
		model.addObject("nowTime", System.currentTimeMillis());
		return model;
	}
	/**
	 * 获取当前学生考过的考试信息
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/history_list",method = RequestMethod.GET)
	public ModelAndView historyList(ModelAndView model,
			@RequestParam(name="name",defaultValue="") String name,
			@RequestParam(name="page",defaultValue="1") Integer page,
			HttpServletRequest request){
		Student student = (Student)request.getSession().getAttribute("student");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", name);
		queryMap.put("studentId", student.getId());
		queryMap.put("offset", getOffset(page, pageSize));
		queryMap.put("pageSize", pageSize);
		model.addObject("historyList", examPaperService.findHistory(queryMap));
		model.addObject("name", name);
		model.addObject("subject", subjectService.findById(student.getSubjectId()));
		model.setViewName("/home/user/history_list");
		if(page < 1)page = 1;
		model.addObject("page", page);
		return model;
	}
	
	/**
	 * 
	 * @param model
	 * @param examPaperId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/review_exam",method = RequestMethod.GET)
	public ModelAndView index(ModelAndView model,Long examId,Long examPaperId,HttpServletRequest request){
		Student student = (Student)request.getSession().getAttribute("student");
		Exam exam = examService.findById(examId);
		if(exam == null){
			model.setViewName("/home/exam/error");
			model.addObject("msg", "当前考试不存在!");
			return model;
		}
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("examId", examId);
		queryMap.put("studentId", student.getId());
		//根据考试信息和学生信息获取试卷
		ExamPaper examPaper = examPaperService.find(queryMap);
		if(examPaper == null){
			model.setViewName("/home/exam/error");
			model.addObject("msg", "当前考试不存在试卷");
			return model;
		}
		if(examPaper.getStatus() == 0){
			model.setViewName("/home/exam/error");
			model.addObject("msg", "您还没有考过这门考试！");
			return model;
		}
		queryMap.put("examPaperId", examPaper.getId());
		List<ExamPaperAnswer> findListByUser = examPaperAnswerService.findListByUser(queryMap);
		model.addObject("title", exam.getName()+"-回顾试卷");
		model.addObject("singleQuestionList", getExamPaperAnswerList(findListByUser, Question.QUESTION_TYPE_SINGLE));
		model.addObject("muiltQuestionList", getExamPaperAnswerList(findListByUser, Question.QUESTION_TYPE_MUILT));
		model.addObject("chargeQuestionList", getExamPaperAnswerList(findListByUser, Question.QUESTION_TYPE_CHARGE));
		model.addObject("exam", exam);
		model.addObject("examPaper", examPaper);
		model.addObject("singleScore", Question.QUESTION_TYPE_SINGLE_SCORE);
		model.addObject("muiltScore", Question.QUESTION_TYPE_MUILT_SCORE);
		model.addObject("chargeScore", Question.QUESTION_TYPE_CHARGE_SCORE);
		model.addObject("singleQuestion", Question.QUESTION_TYPE_SINGLE);
		model.addObject("muiltQuestion", Question.QUESTION_TYPE_MUILT);
		model.addObject("chargeQuestion", Question.QUESTION_TYPE_CHARGE);
		model.setViewName("/home/user/review_exam");
		return model;
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
	
	/**
	 * 计算数据库查询游标位置
	 * @param page
	 * @param pageSize
	 * @return
	 */
	private int getOffset(int page,int pageSize){
		if(page < 1)page = 1;
		return (page - 1) * pageSize;
	}


	@RequestMapping(value = "/rank",method = RequestMethod.GET)
	public ModelAndView rank(String querytestName,ModelAndView model, HttpServletRequest request){
//		if (rankList.isEmpty()){
//			booksList=bookService.queryAllBook();
//			model.addObject("NotFound","未查询到相关信息");
//		}
		if (querytestName!=null){
			List<Rank> rankList =studentService.queryrankBytestName(querytestName);
			model.addObject("list",rankList);
		}
		model.setViewName("/home/user/rank");
		return model;
	}

}
