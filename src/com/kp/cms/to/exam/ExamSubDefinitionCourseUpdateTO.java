package com.kp.cms.to.exam;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExamSubDefinitionCourseUpdateTO implements Serializable{
	
	
	
	private int id;
	private int subjectId;
	private int courseId;
	private String interpretation;
	private String startPercentage;
	private String endPercentage;
	private String grade;
	private String resultClass;
	private String gradePoint;
	private String userId;
	private String mode;
	private String orgStartPercentage;
	private String orgGrade;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStartPercentage() {
		return startPercentage;
	}
	public void setStartPercentage(String startPercentage) {
		this.startPercentage = startPercentage;
	}
	public String getEndPercentage() {
		return endPercentage;
	}
	public void setEndPercentage(String endPercentage) {
		this.endPercentage = endPercentage;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getResultClass() {
		return resultClass;
	}
	public void setResultClass(String resultClass) {
		this.resultClass = resultClass;
	}
	public String getGradePoint() {
		return gradePoint;
	}
	public void setGradePoint(String gradePoint) {
		this.gradePoint = gradePoint;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setInterpretation(String interpretation) {
		this.interpretation = interpretation;
	}
	public String getInterpretation() {
		return interpretation;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setOrgGrade(String orgGrade) {
		this.orgGrade = orgGrade;
	}
	public String getOrgGrade() {
		return orgGrade;
	}
	private String orgEndPercentage;
	public String getOrgEndPercentage() {
		return orgEndPercentage;
	}
	public void setOrgEndPercentage(String orgEndPercentage) {
		this.orgEndPercentage = orgEndPercentage;
	}
	public String getOrgStartPercentage() {
		return orgStartPercentage;
	}
	public void setOrgStartPercentage(String orgStartPercentage) {
		this.orgStartPercentage = orgStartPercentage;
	}

}
