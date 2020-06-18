package com.ischoolbar.programmer.controller.home;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ischoolbar.programmer.entity.admin.Student;
import com.ischoolbar.programmer.service.admin.StudentService;
import com.ischoolbar.programmer.service.admin.SubjectService;

/**
 * ǰ̨��ҳ������
 * @author Administrator
 *
 */
@RequestMapping("/home")
@Controller
public class HomeController {
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private StudentService studentService;
	
	/**
	 * ǰ̨��ҳ
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public ModelAndView index(ModelAndView model){
		return model;
	}
	
	/**
	 * ǰ̨�û���¼
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public ModelAndView login(ModelAndView model){
		model.addObject("title", "�û���¼");
		model.setViewName("/home/login");
		return model;
	}
	
	/**
	 * ǰ̨�û�ע��
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/register",method = RequestMethod.GET)
	public ModelAndView register(ModelAndView model){
		model.addObject("title", "�û�ע��");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", 0);
		queryMap.put("pageSize", 99999);
		model.addObject("subjectList", subjectService.findList(queryMap));
		model.setViewName("/home/register");
		return model;
	}
	
	/**
	 * �û�ע���ύ
	 * @param student
	 * @return
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> register(Student student){
		Map<String, String> ret = new HashMap<String, String>();
		if(student == null){
			ret.put("type", "error");
			ret.put("msg", "����д��ȷ����Ϣ��");
			return ret;
		}
		if(StringUtils.isEmpty(student.getName())){
			ret.put("type", "error");
			ret.put("msg", "����д��¼����");
			return ret;
		}
		if(StringUtils.isEmpty(student.getPassword())){
			ret.put("type", "error");
			ret.put("msg", "����д��¼���룡");
			return ret;
		}
		if(student.getSubjectId() == null){
			ret.put("type", "error");
			ret.put("msg", "��ѡ������ѧ��רҵ��");
			return ret;
		}
		Student existStudent = studentService.findByName(student.getName());
		if(existStudent != null){
			ret.put("type", "error");
			ret.put("msg", "���û����Ѵ��ڣ�");
			return ret;
		}
		student.setCreateTime(new Date());
		try {
			if(studentService.add(student) <= 0){
				ret.put("type", "error");
				ret.put("msg", "ע��ʧ�ܣ�����ϵ����Ա��");
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "���ݿ������쳣");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "ע��ɹ���");
		return ret;
	}
	/**
	 * �û���¼�ύ
	 * @param student
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> login(Student student,HttpServletRequest request){
		Map<String, String> ret = new HashMap<String, String>();
		if(student == null){
			ret.put("type", "error");
			ret.put("msg", "����д��ȷ�ĵ�¼��Ϣ��");
			return ret;
		}
		if(StringUtils.isEmpty(student.getName())){
			ret.put("type", "error");
			ret.put("msg", "����д��¼����");
			return ret;
		}
		if(StringUtils.isEmpty(student.getPassword())){
			ret.put("type", "error");
			ret.put("msg", "����д��¼���룡");
			return ret;
		}

		Student existStudent = studentService.findByName(student.getName());
		if(existStudent == null){
			ret.put("type", "error");
			ret.put("msg", "���û��������ڣ�");
			return ret;
		}

		if(!student.getPassword().equals(existStudent.getPassword())){
			ret.put("type", "error");
			ret.put("msg", "�������");
			return ret;
		}

		request.getSession().setAttribute("student", existStudent);

		ret.put("type", "success");
		ret.put("msg", "��¼�ɹ���");
		return ret;
	}
}
