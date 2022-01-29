package com.kp.cms.bo.admission;

import java.io.Serializable;

public class PromoteBioData implements Serializable{
	private int id;
	private String regNo;
	private String classCode;
	private String name;
	private String section;
	private String fatherName;
	private String secndLang;
	private String rank;
	private String studentNo;
	private String motherName;
	private String academicYear;
	
	public PromoteBioData() {
		
	}

	public PromoteBioData(int id, String regNo, String classCode, String name,
			String section, String fatherName, String secndLang, String rank,
			String studentNo, String motherName, String academicYear) {
		super();
		this.id = id;
		this.regNo = regNo;
		this.classCode = classCode;
		this.name = name;
		this.section = section;
		this.fatherName = fatherName;
		this.secndLang = secndLang;
		this.rank = rank;
		this.studentNo = studentNo;
		this.motherName = motherName;
		this.academicYear = academicYear;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getSecndLang() {
		return secndLang;
	}

	public void setSecndLang(String secndLang) {
		this.secndLang = secndLang;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	
}
