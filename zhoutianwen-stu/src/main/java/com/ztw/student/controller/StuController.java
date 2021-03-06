package com.ztw.student.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.xiaofeng.student.entity.Area;
import com.xiaofeng.student.entity.Student;
import com.xiaofeng.student.entity.Subject;
import com.xiaofeng.student.service.StudentService;

@Controller
public class StuController {

	@Autowired
	StudentService stuService;
	
	@RequestMapping("list")
	public String list(HttpServletRequest request,@RequestParam(defaultValue="1") int page,
			@RequestParam(defaultValue="") String name,@RequestParam(defaultValue="") int[] selSubId ) {
		
		PageInfo<Student> pageStudent = stuService.list(page,name,selSubId);
		request.setAttribute("pageStudent", pageStudent);
		
		List<Subject> subjects = stuService.listAllSubjects();
		request.setAttribute("subjects", subjects);
				
		
		return "list";
	}
	
	@RequestMapping("toAdd")
	public String toAdd(HttpServletRequest request) {
		List<Area> provinces= stuService.listAreasByParentId(1);
		List<Subject> subjects = stuService.listAllSubjects();
		
		request.setAttribute("provinces", provinces);
		request.setAttribute("subjects", subjects);
		
		return "add";
	}
	@RequestMapping("add")
	public String add(Student stu,int[] selSubId ) {
		
		stuService.addStudent(stu,selSubId);
		
		
		return "redirect:list";
	}
	
	@RequestMapping("update")
	public String update(Student stu,int[] selSubId ) {
		
		stuService.updateStudent(stu,selSubId);
		
		
		return "redirect:list";
	}
	@RequestMapping("delBatch")
	@ResponseBody
	public String delBatch(@RequestParam(value="ids[]") int[] ids) {
		stuService.delBatch(ids);
		return "success";
	}
	
	@RequestMapping("getAeas")
	@ResponseBody
	public List<Area> getAeas(int parentId){
		List<Area> areas= stuService.listAreasByParentId(parentId);
		return areas;
	}
	
	@RequestMapping("toupdate")
	public String toupdate(HttpServletRequest request,int id) {
		Student stu = stuService.getById(id);
		request.setAttribute("stu", stu);
		List<Area> provinces= stuService.listAreasByParentId(1);
		List<Subject> subjects = stuService.listAllSubjects();
		request.setAttribute("provinces", provinces);
		request.setAttribute("subjects", subjects);
		return "update";
		
	}
}
