package com.ischoolbar.programmer.controller.admin;

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

import com.ischoolbar.programmer.entity.admin.Subject;
import com.ischoolbar.programmer.page.admin.Page;
import com.ischoolbar.programmer.service.admin.SubjectService;

/**
 * 学科专业管理后台控制器
 * @author Administrator
 *
 */
@RequestMapping("/admin/subject")
@Controller
public class SubjectController {
	
	@Autowired
	private SubjectService subjectService;
	
	/**
	 * 学科专业列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model){
		model.setViewName("subject/list");
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
			Page page
			){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", name);
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", subjectService.findList(queryMap));
		ret.put("total", subjectService.getTotal(queryMap));
		return ret;
	}
	
	/**
	 * 添加学科专业
	 * @param subject
	 * @return
	 */
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Subject subject){
		Map<String, String> ret = new HashMap<String, String>();
		if(subject == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的学科信息!");
			return ret;
		}
		if(StringUtils.isEmpty(subject.getName())){
			ret.put("type", "error");
			ret.put("msg", "请填写学科名称!");
			return ret;
		}
		if(subjectService.add(subject) <= 0){
			ret.put("type", "error");
			ret.put("msg", "添加失败，请联系管理员!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "添加成功!");
		return ret;
	}
	
	/**
	 * 编辑学科专业
	 * @param subject
	 * @return
	 */
	@RequestMapping(value="edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Subject subject){
		Map<String, String> ret = new HashMap<String, String>();
		if(subject == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的学科信息!");
			return ret;
		}
		if(StringUtils.isEmpty(subject.getName())){
			ret.put("type", "error");
			ret.put("msg", "请填写学科名称!");
			return ret;
		}
		if(subjectService.edit(subject) <= 0){
			ret.put("type", "error");
			ret.put("msg", "编辑失败，请联系管理员!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "编辑成功!");
		return ret;
	}
	
	/**
	 * 删除学科专业
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
			if(subjectService.delete(id) <= 0){
				ret.put("type", "error");
				ret.put("msg", "删除失败，请联系管理员!");
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "该学科下存在考生信息，不能删除!");
			return ret;
		}
		
		ret.put("type", "success");
		ret.put("msg", "删除成功!");
		return ret;
	}
}
