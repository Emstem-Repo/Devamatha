package com.kp.cms.to.studentfeedback;

import java.util.List;
import java.util.Map;

public class FacultyEvaluationReportTo {
	
	int semester;
	private String programme;
	private String teacherName;
	private String courseName;
	private String className;
	private String subject;	
	private String totalAverage;
	private String totalEntries;
	private List<FacultyEvaluationReportPrincipalTo> resultList;
	private Map<Integer, String> remarks;

	
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public String getProgramme() {
		return programme;
	}
	public void setProgramme(String programme) {
		this.programme = programme;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public List<FacultyEvaluationReportPrincipalTo> getResultList() {
		return resultList;
	}
	public void setResultList(List<FacultyEvaluationReportPrincipalTo> resultList) {
		this.resultList = resultList;
	}
	public String getTotalAverage() {
		return totalAverage;
	}
	public void setTotalAverage(String totalAverage) {
		this.totalAverage = totalAverage;
	}	
	public String getTotalEntries() {
		return totalEntries;
	}
	public void setTotalEntries(String totalEntries) {
		this.totalEntries = totalEntries;
	}
	public Map<Integer, String> getRemarks() {
		return remarks;
	}
	public void setRemarks(Map<Integer, String> remarks) {
		this.remarks = remarks;
	}
	
}
