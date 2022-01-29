package com.kp.cms.forms.fee;

import java.util.Map;

import com.kp.cms.forms.BaseActionForm;

public class SemesterWiseCourseEntryForm  extends BaseActionForm{
	private static final long serialVersionUID = 1L;
	private int id;
	private String academicYear;
	private String semester;
	private boolean isSemesterWise;
	private String courseId;
	private Map<Integer,String> courseMap;
	private String courseName;
	private String method;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public boolean getIsSemesterWise() {
		return isSemesterWise;
	}
	public void setIsSemesterWise(boolean isSemesterWise) {
		this.isSemesterWise = isSemesterWise;
	}
	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}
	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public void clear(){
		super.clear();
		this.academicYear = null;
		this.courseName = null;
		this.semester = null;
		this.isSemesterWise = false;
		this.courseId = "";
	}
	
}
