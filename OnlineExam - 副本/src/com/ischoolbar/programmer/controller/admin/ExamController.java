package com.ischoolbar.programmer.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ischoolbar.programmer.entity.admin.Exam;
import com.ischoolbar.programmer.entity.admin.Question;
import com.ischoolbar.programmer.page.admin.Page;
import com.ischoolbar.programmer.service.admin.ExamService;
import com.ischoolbar.programmer.service.admin.QuestionService;
import com.ischoolbar.programmer.service.admin.SubjectService;

/**
 * 习题管理后台控制器
 * @author Administrator
 *
 */
@RequestMapping("/admin/exam")
@Controller
public class ExamController {
	
	@Autowired
	private ExamService examService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private SubjectService subjectService;
	
	/**
	 * 习题列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model){
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", 0);
		queryMap.put("pageSize", 99999);
		model.addObject("subjectList", subjectService.findList(queryMap));
		model.addObject("singleScore", Question.QUESTION_TYPE_SINGLE_SCORE);
		model.addObject("muiltScore", Question.QUESTION_TYPE_MUILT_SCORE);
		model.addObject("chargeScore", Question.QUESTION_TYPE_CHARGE_SCORE);
        model.setViewName("exam/list");
		return model;
	}
	
	/**
	 * 模糊搜索分页显示列表查询
	 * @param name
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(
			@RequestParam(name="name",defaultValue="") String name,
			@RequestParam(name="subjectId",required=false) Long subjectId,
			@RequestParam(name="startTime",required=false) String startTime,
			@RequestParam(name="endTime",required=false) String endTime,
			Page page
			){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", name);
		if(subjectId != null){
			queryMap.put("subjectId", subjectId);
		}
		if(!StringUtils.isEmpty(startTime)){
			queryMap.put("startTime", startTime);
		}
		if(!StringUtils.isEmpty(endTime)){
			queryMap.put("endTime", endTime);
		}
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", examService.findList(queryMap));
		ret.put("total", examService.getTotal(queryMap));
		return ret;
	}
	
	/**
	 * 添加习题
	 * @param exam
	 * @return
	 */
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Exam exam){
		Map<String, String> ret = new HashMap<String, String>();
		if(exam == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的习题信息!");
			return ret;
		}
		if(StringUtils.isEmpty(exam.getName())){
			ret.put("type", "error");
			ret.put("msg", "请填写习题名称!");
			return ret;
		}
		if(exam.getSubjectId() == null){
			ret.put("type", "error");
			ret.put("msg", "请选择所属科目!");
			return ret;
		}
		if(exam.getStartTime() == null){
			ret.put("type", "error");
			ret.put("msg", "请填写习题开始时间!");
			return ret;
		}
		if(exam.getEndTime() == null){
			ret.put("type", "error");
			ret.put("msg", "请填写习题结束时间!");
			return ret;
		}
		if(exam.getPassScore() == 0){
			ret.put("type", "error");
			ret.put("msg", "请填写习题及格分数!");
			return ret;
		}
		if(exam.getSingleQuestionNum() == 0 && exam.getMuiltQuestionNum() == 0 && exam.getChargeQuestionNum() == 0){
			ret.put("type", "error");
			ret.put("msg", "单选题、多选题、判断题至少有一种类型的题目!");
			return ret;
		}
		exam.setCreateTime(new Date());
		//此时去查询所填写的题型数量是否满足
		//获取单选题总数
		Map<String, Long> queryMap = new HashMap<String, Long>();
		queryMap.put("questionType", Long.valueOf(Question.QUESTION_TYPE_SINGLE));
		queryMap.put("subjectId", exam.getSubjectId());
		int singleQuestionTotalNum = questionService.getQuestionNumByType(queryMap);
		if(exam.getSingleQuestionNum() > singleQuestionTotalNum){
			ret.put("type", "error");
			ret.put("msg", "单选题数量超过题库单选题总数，请修改!");
			return ret;
		}
		//获取多选题总数
		queryMap.put("questionType", Long.valueOf(Question.QUESTION_TYPE_MUILT));
		int muiltQuestionTotalNum = questionService.getQuestionNumByType(queryMap);
		if(exam.getMuiltQuestionNum() > muiltQuestionTotalNum){
			ret.put("type", "error");
			ret.put("msg", "多选题数量超过题库多选题总数，请修改!");
			return ret;
		}
		//获取判断题总数
		queryMap.put("questionType", Long.valueOf(Question.QUESTION_TYPE_CHARGE));
		int chargeQuestionTotalNum = questionService.getQuestionNumByType(queryMap);
		if(exam.getChargeQuestionNum() > chargeQuestionTotalNum){
			ret.put("type", "error");
			ret.put("msg", "判断题数量超过题库判断题总数，请修改!");
			return ret;
		}
		exam.setQuestionNum(exam.getSingleQuestionNum() + exam.getMuiltQuestionNum() + exam.getChargeQuestionNum());
		exam.setTotalScore(exam.getSingleQuestionNum() * Question.QUESTION_TYPE_SINGLE_SCORE + exam.getMuiltQuestionNum() * Question.QUESTION_TYPE_MUILT_SCORE + exam.getChargeQuestionNum() * Question.QUESTION_TYPE_CHARGE_SCORE);
		if(examService.add(exam) <= 0){
			ret.put("type", "error");
			ret.put("msg", "添加失败，请联系管理员!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "添加成功!");
		return ret;
	}
	
	/**
	 * 编辑测试
	 * @param exam
	 * @return
	 */
	@RequestMapping(value="edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Exam exam){
		Map<String, String> ret = new HashMap<String, String>();
		if(exam == null){
			ret.put("type", "error");
			ret.put("msg", "请选择正确的测试信息进行编辑!");
			return ret;
		}
		if(StringUtils.isEmpty(exam.getName())){
			ret.put("type", "error");
			ret.put("msg", "请填写测试名称!");
			return ret;
		}
		if(exam.getSubjectId() == null){
			ret.put("type", "error");
			ret.put("msg", "请选择所属科目!");
			return ret;
		}
		if(exam.getStartTime() == null){
			ret.put("type", "error");
			ret.put("msg", "请填写测试开始时间!");
			return ret;
		}
		if(exam.getEndTime() == null){
			ret.put("type", "error");
			ret.put("msg", "请填写测试结束时间!");
			return ret;
		}
		if(exam.getPassScore() == 0){
			ret.put("type", "error");
			ret.put("msg", "请填写测试及格分数!");
			return ret;
		}
		if(exam.getSingleQuestionNum() == 0 && exam.getMuiltQuestionNum() == 0 && exam.getChargeQuestionNum() == 0){
			ret.put("type", "error");
			ret.put("msg", "单选题、多选题、判断题至少有一种类型的题目!");
			return ret;
		}
		//此时去查询所填写的题型数量是否满足
		//获取单选题总数
		Map<String, Long> queryMap = new HashMap<String, Long>();
		queryMap.put("questionType", Long.valueOf(Question.QUESTION_TYPE_SINGLE));
		queryMap.put("subjectId", exam.getSubjectId());
		int singleQuestionTotalNum = questionService.getQuestionNumByType(queryMap);
		if(exam.getSingleQuestionNum() > singleQuestionTotalNum){
			ret.put("type", "error");
			ret.put("msg", "单选题数量超过题库单选题总数，请修改!");
			return ret;
		}
		//获取多选题总数
		queryMap.put("questionType", Long.valueOf(Question.QUESTION_TYPE_MUILT));
		int muiltQuestionTotalNum = questionService.getQuestionNumByType(queryMap);
		if(exam.getMuiltQuestionNum() > muiltQuestionTotalNum){
			ret.put("type", "error");
			ret.put("msg", "多选题数量超过题库多选题总数，请修改!");
			return ret;
		}
		//获取判断题总数
		queryMap.put("questionType", Long.valueOf(Question.QUESTION_TYPE_CHARGE));
		int chargeQuestionTotalNum = questionService.getQuestionNumByType(queryMap);
		if(exam.getChargeQuestionNum() > chargeQuestionTotalNum){
			ret.put("type", "error");
			ret.put("msg", "判断题数量超过题库判断题总数，请修改!");
			return ret;
		}
		exam.setQuestionNum(exam.getSingleQuestionNum() + exam.getMuiltQuestionNum() + exam.getChargeQuestionNum());
		exam.setTotalScore(exam.getSingleQuestionNum() * Question.QUESTION_TYPE_SINGLE_SCORE + exam.getMuiltQuestionNum() * Question.QUESTION_TYPE_MUILT_SCORE + exam.getChargeQuestionNum() * Question.QUESTION_TYPE_CHARGE_SCORE);
		if(examService.edit(exam) <= 0){
			ret.put("type", "error");
			ret.put("msg", "编辑失败，请联系管理员!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "编辑成功!");
		return ret;
	}
	
	/**
	 * 删除习题
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(Long id){
		Map<String, String> ret = new HashMap<String, String>();
		if(id == null){
			ret.put("type", "error");
			ret.put("msg", "请选择要删除的数据!");
			return ret;
		}
		try {
			if(examService.delete(id) <= 0){
				ret.put("type", "error");
				ret.put("msg", "删除失败，请联系管理员!");
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "该习题下存在试卷或习题记录信息，不能删除!");
			return ret;
		}
		
		ret.put("type", "success");
		ret.put("msg", "删除成功!");
		return ret;
	}
	
	
	
}
