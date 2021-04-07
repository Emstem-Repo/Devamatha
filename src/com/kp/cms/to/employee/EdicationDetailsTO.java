package com.kp.cms.to.employee;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EdicationDetailsTO implements Serializable{
	private int id;
	private String courseName;
	private String course;
	private String yearOfPassing;
	private String grade;
	private String instituteUniversity;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getYearOfPassing() {
		return yearOfPassing;
	}
	public void setYearOfPassing(String yearOfPassing) {
		this.yearOfPassing = yearOfPassing;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getInstituteUniversity() {
		return instituteUniversity;
	}
	public void setInstituteUniversity(String instituteUniversity) {
		this.instituteUniversity = instituteUniversity;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseName() {
		return courseName;
	}

}
