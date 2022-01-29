package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.math.BigDecimal;

public class PucClassHeld implements Serializable{
     private int id;
     private String classes;
     private String classPrac;
     private String subLCode;
     private String batchNo;
     private BigDecimal classHeld;
     private String elective;
     private Integer academicYear;
     public PucClassHeld(){
    	 
     }
	public PucClassHeld(int id, String classes, String classPrac,
			String subLCode, String batchNo, BigDecimal classHeld,
			String elective) {
		super();
		this.id = id;
		this.classes = classes;
		this.classPrac = classPrac;
		this.subLCode = subLCode;
		this.batchNo = batchNo;
		this.classHeld = classHeld;
		this.elective = elective;
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
	public String getClassPrac() {
		return classPrac;
	}
	public void setClassPrac(String classPrac) {
		this.classPrac = classPrac;
	}
	public String getSubLCode() {
		return subLCode;
	}
	public void setSubLCode(String subLCode) {
		this.subLCode = subLCode;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public BigDecimal getClassHeld() {
		return classHeld;
	}
	public void setClassHeld(BigDecimal classHeld) {
		this.classHeld = classHeld;
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
