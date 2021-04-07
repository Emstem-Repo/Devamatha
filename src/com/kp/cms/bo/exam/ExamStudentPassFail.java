package com.kp.cms.bo.exam;

import java.math.BigDecimal;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;

public class ExamStudentPassFail {
	private Integer id;
	private Student student;
	private Integer schemeNo;
	private Classes classes;
	private Character passFail;
	private BigDecimal percentage;
	private String result;
	
	public ExamStudentPassFail(){
		
	}
	
	public ExamStudentPassFail(Integer id, Student student, Integer schemeNo,
			Classes classes, Character passFail, BigDecimal percentage, String result) {
		super();
		this.id = id;
		this.student = student;
		this.schemeNo = schemeNo;
		this.classes = classes;
		this.passFail = passFail;
		this.percentage = percentage;
		this.result = result;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Integer getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(Integer schemeNo) {
		this.schemeNo = schemeNo;
	}
	public Classes getClasses() {
		return classes;
	}
	public void setClasses(Classes classes) {
		this.classes = classes;
	}
	public Character getPassFail() {
		return passFail;
	}
	public void setPassFail(Character passFail) {
		this.passFail = passFail;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
}
