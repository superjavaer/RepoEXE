package com.ischoolbar.programmer.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ischoolbar.programmer.entity.admin.ExamPaper;
import com.ischoolbar.programmer.page.admin.Page;
import com.ischoolbar.programmer.service.admin.ExamPaperService;
import com.ischoolbar.programmer.service.admin.ExamService;
import com.ischoolbar.programmer.service.admin.StudentService;

/**
 * 试卷管理后台控制器
 * @author Administrator
 *
 */
@RequestMapping("/admin/examPaper")
@Controller
public class ExamPaperController {
	
	@Autowired
	private ExamPaperService examPaperService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private ExamService examService;
	
	/**
	 * 试卷列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model){
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", 0);
		queryMap.put("pageSize", 99999);
		model.addObject("examList", examService.findList(queryMap));
		model.addObject("studentList", studentService.findList(queryMap));
		model.setViewName("examPaper/list");
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
			@RequestParam(name="examId",required=false) Long examId,
			@RequestParam(name="studentId",required=false) Long studentId,
			@RequestParam(name="status",required=false) Integer status,
			Page page
			){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if(examId != null){
			queryMap.put("examId", examId);
		}
		if(studentId != null){
			queryMap.put("studentId", studentId);
		}
		if(status != null){
			queryMap.put("status", status);
		}
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", examPaperService.findList(queryMap));
		ret.put("total", examPaperService.getTotal(queryMap));
		return ret;
	}
	
	/**
	 * 添加试卷
	 * @param examPaper
	 * @return
	 */
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(ExamPaper examPaper){
		Map<String, String> ret = new HashMap<String, String>();
		if(examPaper == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的试卷信息!");
			return ret;
		}
		if(examPaper.getExamId() == null){
			ret.put("type", "error");
			ret.put("msg", "请选择试卷所属考试!");
			return ret;
		}
		if(examPaper.getStudentId() == null){
			ret.put("type", "error");
			ret.put("msg", "请选择所属学生!");
			return ret;
		}
		if(examPaperService.add(examPaper) <= 0){
			ret.put("type", "error");
			ret.put("msg", "添加失败，请联系管理员!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "添加成功!");
		return ret;
	}
	
	/**
	 * 编辑试卷
	 * @param examPaper
	 * @return
	 */
	@RequestMapping(value="edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(ExamPaper examPaper){
		Map<String, String> ret = new HashMap<String, String>();
		if(examPaper == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的试卷信息!");
			return ret;
		}
		if(examPaper.getExamId() == null){
			ret.put("type", "error");
			ret.put("msg", "请选择试卷所属考试!");
			return ret;
		}
		if(examPaper.getStudentId() == null){
			ret.put("type", "error");
			ret.put("msg", "请选择所属学生!");
			return ret;
		}if(examPaperService.edit(examPaper) <= 0){
			ret.put("type", "error");
			ret.put("msg", "编辑失败，请联系管理员!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "编辑成功!");
		return ret;
	}
	
	/**
	 * 删除试卷
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
			if(examPaperService.delete(id) <= 0){
				ret.put("type", "error");
				ret.put("msg", "删除失败，请联系管理员!");
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "该试卷下存在答题信息，不能删除!");
			return ret;
		}
		
		ret.put("type", "success");
		ret.put("msg", "删除成功!");
		return ret;
	}
	
}
