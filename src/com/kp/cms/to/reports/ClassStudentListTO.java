package com.kp.cms.to.reports;

public class ClassStudentListTO {
	private String className;
	private int slNo;
	private String rollRegNo;
	private String studentName;
	private String language;
	private String studentCategory;
	private String religion;
	private String caste;
	private String casteCategory;
	
	public int getSlNo() {
		return slNo;
	}
	public String getRollRegNo() {
		return rollRegNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public String getLanguage() {
		return language;
	}
	public String getStudentCategory() {
		return studentCategory;
	}
	public String getReligion() {
		return religion;
	}
	public String getCaste() {
		return caste;
	}
	public String getCasteCategory() {
		return casteCategory;
	}
	public void setSlNo(int slNo) {
		this.slNo = slNo;
	}
	public void setRollRegNo(String rollRegNo) {
		this.rollRegNo = rollRegNo;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public void setStudentCategory(String studentCategory) {
		this.studentCategory = studentCategory;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public void setCaste(String caste) {
		this.caste = caste;
	}
	public void setCasteCategory(String casteCategory) {
		this.casteCategory = casteCategory;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
}
