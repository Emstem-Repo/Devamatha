package com.kp.cms.to.employee;

public class EmpEducationalDetailsTO {
	private String degree;
	private int yearOfPassing;
	private String university;
	private String marks;
	
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public int getYearOfPassing() {
		return yearOfPassing;
	}
	public void setYearOfPassing(int yearOfPassing) {
		this.yearOfPassing = yearOfPassing;
	}
	
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getUniversity() {
		return university;
	}
	
}
