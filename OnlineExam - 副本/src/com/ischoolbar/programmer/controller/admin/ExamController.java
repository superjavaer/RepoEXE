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
 * ϰ������̨������
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
	 * ϰ���б�ҳ��
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
	 * ģ��������ҳ��ʾ�б��ѯ
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
	 * ���ϰ��
	 * @param exam
	 * @return
	 */
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Exam exam){
		Map<String, String> ret = new HashMap<String, String>();
		if(exam == null){
			ret.put("type", "error");
			ret.put("msg", "����д��ȷ��ϰ����Ϣ!");
			return ret;
		}
		if(StringUtils.isEmpty(exam.getName())){
			ret.put("type", "error");
			ret.put("msg", "����дϰ������!");
			return ret;
		}
		if(exam.getSubjectId() == null){
			ret.put("type", "error");
			ret.put("msg", "��ѡ��������Ŀ!");
			return ret;
		}
		if(exam.getStartTime() == null){
			ret.put("type", "error");
			ret.put("msg", "����дϰ�⿪ʼʱ��!");
			return ret;
		}
		if(exam.getEndTime() == null){
			ret.put("type", "error");
			ret.put("msg", "����дϰ�����ʱ��!");
			return ret;
		}
		if(exam.getPassScore() == 0){
			ret.put("type", "error");
			ret.put("msg", "����дϰ�⼰�����!");
			return ret;
		}
		if(exam.getSingleQuestionNum() == 0 && exam.getMuiltQuestionNum() == 0 && exam.getChargeQuestionNum() == 0){
			ret.put("type", "error");
			ret.put("msg", "��ѡ�⡢��ѡ�⡢�ж���������һ�����͵���Ŀ!");
			return ret;
		}
		exam.setCreateTime(new Date());
		//��ʱȥ��ѯ����д�����������Ƿ�����
		//��ȡ��ѡ������
		Map<String, Long> queryMap = new HashMap<String, Long>();
		queryMap.put("questionType", Long.valueOf(Question.QUESTION_TYPE_SINGLE));
		queryMap.put("subjectId", exam.getSubjectId());
		int singleQuestionTotalNum = questionService.getQuestionNumByType(queryMap);
		if(exam.getSingleQuestionNum() > singleQuestionTotalNum){
			ret.put("type", "error");
			ret.put("msg", "��ѡ������������ⵥѡ�����������޸�!");
			return ret;
		}
		//��ȡ��ѡ������
		queryMap.put("questionType", Long.valueOf(Question.QUESTION_TYPE_MUILT));
		int muiltQuestionTotalNum = questionService.getQuestionNumByType(queryMap);
		if(exam.getMuiltQuestionNum() > muiltQuestionTotalNum){
			ret.put("type", "error");
			ret.put("msg", "��ѡ��������������ѡ�����������޸�!");
			return ret;
		}
		//��ȡ�ж�������
		queryMap.put("questionType", Long.valueOf(Question.QUESTION_TYPE_CHARGE));
		int chargeQuestionTotalNum = questionService.getQuestionNumByType(queryMap);
		if(exam.getChargeQuestionNum() > chargeQuestionTotalNum){
			ret.put("type", "error");
			ret.put("msg", "�ж���������������ж������������޸�!");
			return ret;
		}
		exam.setQuestionNum(exam.getSingleQuestionNum() + exam.getMuiltQuestionNum() + exam.getChargeQuestionNum());
		exam.setTotalScore(exam.getSingleQuestionNum() * Question.QUESTION_TYPE_SINGLE_SCORE + exam.getMuiltQuestionNum() * Question.QUESTION_TYPE_MUILT_SCORE + exam.getChargeQuestionNum() * Question.QUESTION_TYPE_CHARGE_SCORE);
		if(examService.add(exam) <= 0){
			ret.put("type", "error");
			ret.put("msg", "���ʧ�ܣ�����ϵ����Ա!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "��ӳɹ�!");
		return ret;
	}
	
	/**
	 * �༭����
	 * @param exam
	 * @return
	 */
	@RequestMapping(value="edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Exam exam){
		Map<String, String> ret = new HashMap<String, String>();
		if(exam == null){
			ret.put("type", "error");
			ret.put("msg", "��ѡ����ȷ�Ĳ�����Ϣ���б༭!");
			return ret;
		}
		if(StringUtils.isEmpty(exam.getName())){
			ret.put("type", "error");
			ret.put("msg", "����д��������!");
			return ret;
		}
		if(exam.getSubjectId() == null){
			ret.put("type", "error");
			ret.put("msg", "��ѡ��������Ŀ!");
			return ret;
		}
		if(exam.getStartTime() == null){
			ret.put("type", "error");
			ret.put("msg", "����д���Կ�ʼʱ��!");
			return ret;
		}
		if(exam.getEndTime() == null){
			ret.put("type", "error");
			ret.put("msg", "����д���Խ���ʱ��!");
			return ret;
		}
		if(exam.getPassScore() == 0){
			ret.put("type", "error");
			ret.put("msg", "����д���Լ������!");
			return ret;
		}
		if(exam.getSingleQuestionNum() == 0 && exam.getMuiltQuestionNum() == 0 && exam.getChargeQuestionNum() == 0){
			ret.put("type", "error");
			ret.put("msg", "��ѡ�⡢��ѡ�⡢�ж���������һ�����͵���Ŀ!");
			return ret;
		}
		//��ʱȥ��ѯ����д�����������Ƿ�����
		//��ȡ��ѡ������
		Map<String, Long> queryMap = new HashMap<String, Long>();
		queryMap.put("questionType", Long.valueOf(Question.QUESTION_TYPE_SINGLE));
		queryMap.put("subjectId", exam.getSubjectId());
		int singleQuestionTotalNum = questionService.getQuestionNumByType(queryMap);
		if(exam.getSingleQuestionNum() > singleQuestionTotalNum){
			ret.put("type", "error");
			ret.put("msg", "��ѡ������������ⵥѡ�����������޸�!");
			return ret;
		}
		//��ȡ��ѡ������
		queryMap.put("questionType", Long.valueOf(Question.QUESTION_TYPE_MUILT));
		int muiltQuestionTotalNum = questionService.getQuestionNumByType(queryMap);
		if(exam.getMuiltQuestionNum() > muiltQuestionTotalNum){
			ret.put("type", "error");
			ret.put("msg", "��ѡ��������������ѡ�����������޸�!");
			return ret;
		}
		//��ȡ�ж�������
		queryMap.put("questionType", Long.valueOf(Question.QUESTION_TYPE_CHARGE));
		int chargeQuestionTotalNum = questionService.getQuestionNumByType(queryMap);
		if(exam.getChargeQuestionNum() > chargeQuestionTotalNum){
			ret.put("type", "error");
			ret.put("msg", "�ж���������������ж������������޸�!");
			return ret;
		}
		exam.setQuestionNum(exam.getSingleQuestionNum() + exam.getMuiltQuestionNum() + exam.getChargeQuestionNum());
		exam.setTotalScore(exam.getSingleQuestionNum() * Question.QUESTION_TYPE_SINGLE_SCORE + exam.getMuiltQuestionNum() * Question.QUESTION_TYPE_MUILT_SCORE + exam.getChargeQuestionNum() * Question.QUESTION_TYPE_CHARGE_SCORE);
		if(examService.edit(exam) <= 0){
			ret.put("type", "error");
			ret.put("msg", "�༭ʧ�ܣ�����ϵ����Ա!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "�༭�ɹ�!");
		return ret;
	}
	
	/**
	 * ɾ��ϰ��
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(Long id){
		Map<String, String> ret = new HashMap<String, String>();
		if(id == null){
			ret.put("type", "error");
			ret.put("msg", "��ѡ��Ҫɾ��������!");
			return ret;
		}
		try {
			if(examService.delete(id) <= 0){
				ret.put("type", "error");
				ret.put("msg", "ɾ��ʧ�ܣ�����ϵ����Ա!");
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "��ϰ���´����Ծ��ϰ���¼��Ϣ������ɾ��!");
			return ret;
		}
		
		ret.put("type", "success");
		ret.put("msg", "ɾ���ɹ�!");
		return ret;
	}
	
	
	
}
