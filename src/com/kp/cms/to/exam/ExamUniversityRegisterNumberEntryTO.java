package com.kp.cms.to.exam;

import java.io.Serializable;

public class ExamUniversityRegisterNumberEntryTO implements Serializable,Comparable<ExamUniversityRegisterNumberEntryTO>{
	private int id;
	private String rollNo;
	private String studentName;
	private String specialization;
	private String secondlanguage;
	private String regNo;
	
	public ExamUniversityRegisterNumberEntryTO(String rollNo, String studentName,
			String regNo, String specialization, String secondlanguage,int id) {
		super();
		this.rollNo = rollNo;
		this.studentName = studentName;
		this.regNo = regNo;
		this.specialization = specialization;
		this.secondlanguage = secondlanguage;
		this.id = id;
	}
	public ExamUniversityRegisterNumberEntryTO() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	public String getSecondlanguage() {
		return secondlanguage;
	}
	
	public void setSecondlanguage(String secondlanguage) {
		this.secondlanguage = secondlanguage;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	@Override
	public int compareTo(ExamUniversityRegisterNumberEntryTO arg0) {
		if(arg0!=null && this!=null && arg0.getRegNo()!=null && this.getRegNo()!=null){
			return this.getRegNo().compareTo(arg0.getRegNo());
		}else
			return 0;
	}
	
	
	

}
