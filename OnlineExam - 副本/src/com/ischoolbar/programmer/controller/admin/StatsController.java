package com.ischoolbar.programmer.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ischoolbar.programmer.service.admin.ExamPaperService;
import com.ischoolbar.programmer.service.admin.ExamService;

/**
 * �ɼ�ͳ�ƿ�����
 * @author Administrator
 *
 */
@RequestMapping("/admin/stats")
@Controller
public class StatsController {

	@Autowired
	private ExamService examService;
	@Autowired
	private ExamPaperService examPaperService;
	/**
	 * �ɼ�ͳ��ҳ��
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/exam_stats",method=RequestMethod.GET)
	public ModelAndView stats(ModelAndView model){
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", 0);
		queryMap.put("pageSize", 99999);
		model.addObject("examList", examService.findList(queryMap));
		model.setViewName("stats/exam_stats");
		return model;
	}
	
	/**
	 * ���ݿ�����Ϣͳ�ƽ��
	 * @param examId
	 * @return
	 */
	@RequestMapping(value="/get_stats",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getStats(Long examId){
		Map<String, Object> ret = new HashMap<String, Object>();
		if(examId == null){
			ret.put("type", "error");
			ret.put("msg", "ѡ��Ҫͳ�ƵĿ�����Ϣ��");
			return ret;
		}
		List<Map<String, Object>> examStats = examPaperService.getExamStats(examId);
		ret.put("type", "success");
		ret.put("msg", "ͳ�Ƴɹ���");
		ret.put("studentList", getListByMap(examStats, "sname"));
		ret.put("studentScore", getListByMap(examStats, "score"));
		return ret;
	}
	
	private List<Object> getListByMap(List<Map<String, Object>> mapList,String key){
		List<Object> ret = new ArrayList<Object>();
		for(Map<String, Object> map:mapList){
			ret.add(map.get(key));
		}
		return ret;
	}
}
