package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.UpdateStudentDataTO;

public class UpdateStudentDataForm extends BaseActionForm{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String academicYear;
	private String classId;
	private String className;
	private String method;
	private List<UpdateStudentDataTO> studentList;
	private Map<Integer, String> classMap;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	
	
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public List<UpdateStudentDataTO> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<UpdateStudentDataTO> studentList) {
		this.studentList = studentList;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	
	public Map<Integer, String> getClassMap() {
		return classMap;
	}
	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}
	public void reset(){
		this.academicYear = null;
		this.className = null;
		this.classId = null;
	}
}
