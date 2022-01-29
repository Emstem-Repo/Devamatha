package com.kp.cms.bo.admin;

import java.io.Serializable;

public class PucDefineClassSubject implements Serializable{
	private int id;
    private String classes;
    private String subjectLCode;
    private String language;
    private Boolean lang;
    private String classPracticle;
    private Integer noOfBatches;
    private Integer subjectNo;
    private Integer subLveNo;
    private String teachCode;
    private Integer classTaken;
    private String elective;
    private Integer academicYear;
    public PucDefineClassSubject(){
    	
    }
	public PucDefineClassSubject(int id, String classes, String subjectLCode,
			String language, Boolean lang, String classPracticle,
			Integer noOfBatches, Integer subjectNo, Integer subLveNo,
			String teachCode, Integer classTaken, String elective,Integer academicYear) {
		super();
		this.id = id;
		this.classes = classes;
		this.subjectLCode = subjectLCode;
		this.language = language;
		this.lang = lang;
		this.classPracticle = classPracticle;
		this.noOfBatches = noOfBatches;
		this.subjectNo = subjectNo;
		this.subLveNo = subLveNo;
		this.teachCode = teachCode;
		this.classTaken = classTaken;
		this.elective = elective;
		this.academicYear = academicYear;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public String getSubjectLCode() {
		return subjectLCode;
	}
	public void setSubjectLCode(String subjectLCode) {
		this.subjectLCode = subjectLCode;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Boolean getLang() {
		return lang;
	}
	public void setLang(Boolean lang) {
		this.lang = lang;
	}
	public String getClassPracticle() {
		return classPracticle;
	}
	public void setClassPracticle(String classPracticle) {
		this.classPracticle = classPracticle;
	}
	public Integer getNoOfBatches() {
		return noOfBatches;
	}
	public void setNoOfBatches(Integer noOfBatches) {
		this.noOfBatches = noOfBatches;
	}
	public Integer getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(Integer subjectNo) {
		this.subjectNo = subjectNo;
	}
	public Integer getSubLveNo() {
		return subLveNo;
	}
	public void setSubLveNo(Integer subLveNo) {
		this.subLveNo = subLveNo;
	}
	public String getTeachCode() {
		return teachCode;
	}
	public void setTeachCode(String teachCode) {
		this.teachCode = teachCode;
	}
	public Integer getClassTaken() {
		return classTaken;
	}
	public void setClassTaken(Integer classTaken) {
		this.classTaken = classTaken;
	}
	public String getElective() {
		return elective;
	}
	public void setElective(String elective) {
		this.elective = elective;
	}
	public Integer getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}
}
