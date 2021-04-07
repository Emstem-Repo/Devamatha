package com.kp.cms.bo.exam;

import java.io.Serializable;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CourseScheme;
import com.kp.cms.bo.admin.Program;

public class CourseSchemeDetails implements Serializable {
	
	private int id;
	private ExamDefinition examDefinition;
	private Course course;
	private Program program;
	private Boolean isActive;
	private int schemeNo;
	private CourseScheme courseScheme;
	
	public CourseSchemeDetails() {
		super();
	}
	
	public CourseSchemeDetails(int id, ExamDefinition examDefinition,
			Course course, Program program, Boolean isActive, int schemeNo,
			CourseScheme courseScheme) {
		super();
		this.id = id;
		this.examDefinition = examDefinition;
		this.course = course;
		this.program = program;
		this.isActive = isActive;
		this.schemeNo = schemeNo;
		this.courseScheme = courseScheme;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ExamDefinition getExamDefinition() {
		return examDefinition;
	}
	public void setExamDefinition(ExamDefinition examDefinition) {
		this.examDefinition = examDefinition;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Program getProgram() {
		return program;
	}
	public void setProgram(Program program) {
		this.program = program;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public int getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}
	public CourseScheme getCourseScheme() {
		return courseScheme;
	}
	public void setCourseScheme(CourseScheme courseScheme) {
		this.courseScheme = courseScheme;
	}
}