package com.kp.cms.to.employee;

import java.util.Date;
import java.util.Map;

import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.StudentTO;

public class EmpQualificationLevelTo implements Comparable<EmpQualificationLevelTo>{
	
	private int id;
	private String educationId;
	private String course;
	private String specialization;
	private String yearOfComp;
	private String grade;
	private String institute;
	private Map<String,String> fixedMap;
	private Map<String,String> levelMap;
	private String qualification;
	private String year;
	private int educationDetailsID; 
	private String focus;
	private String degree;
	private String nameOfUniversity;
	private String qstate;
	private String percentage;
	private String attempts;
	private String fixedDisplay;
	
	public String getEducationId() {
		return educationId;
	}
	public void setEducationId(String educationId) {
		this.educationId = educationId;
	}
	public String getCourse() {
		return course;
	}
	public String getSpecialization() {
		return specialization;
	}
	public String getYearOfComp() {
		return yearOfComp;
	}
	public String getGrade() {
		return grade;
	}
	public String getInstitute() {
		return institute;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	public void setYearOfComp(String yearOfComp) {
		this.yearOfComp = yearOfComp;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public void setInstitute(String institute) {
		this.institute = institute;
	}
	public Map<String, String> getFixedMap() {
		return fixedMap;
	}
	public Map<String, String> getLevelMap() {
		return levelMap;
	}
	public void setFixedMap(Map<String, String> fixedMap) {
		this.fixedMap = fixedMap;
	}
	public void setLevelMap(Map<String, String> levelMap) {
		this.levelMap = levelMap;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public int getEducationDetailsID() {
		return educationDetailsID;
	}
	public void setEducationDetailsID(int educationDetailsID) {
		this.educationDetailsID = educationDetailsID;
	}
	public void setFocus(String focus) {
		this.focus = focus;
	}
	public String getFocus() {
		return focus;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getNameOfUniversity() {
		return nameOfUniversity;
	}
	public void setNameOfUniversity(String nameOfUniversity) {
		this.nameOfUniversity = nameOfUniversity;
	}
	
	public String getQstate() {
		return qstate;
	}
	public void setQstate(String qstate) {
		this.qstate = qstate;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public String getAttempts() {
		return attempts;
	}
	public void setAttempts(String attempts) {
		this.attempts = attempts;
	}
	@Override
	public int compareTo(EmpQualificationLevelTo arg0) {
		if(arg0 instanceof EmpQualificationLevelTo && arg0.getFixedDisplay()!=null ){
			return this.getFixedDisplay().compareTo(arg0.fixedDisplay);
	}else
		return 0;
}
	public String getFixedDisplay() {
		return fixedDisplay;
	}
	public void setFixedDisplay(String fixedDisplay) {
		this.fixedDisplay = fixedDisplay;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
