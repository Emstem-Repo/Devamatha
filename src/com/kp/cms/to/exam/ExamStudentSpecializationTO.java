package com.kp.cms.to.exam;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExamStudentSpecializationTO implements Serializable,Comparable<ExamStudentSpecializationTO> {

	private int studentId;
	private int id;
	private String rollNo;
	private String regNo;
	private int appNo;
	private String studentName;
	private String specName;
	private String isChecked;
	private boolean isCheckedDummy;
	private String sectionName;
	private int schemeNo;
	private String dummyOnOrOff;

	public ExamStudentSpecializationTO() {
		super();
	}

	public ExamStudentSpecializationTO(int studentId, String rollNo,
			String regNo, int appNo, String studentName, String specName) {
		super();
		this.studentId = studentId;
		this.rollNo = rollNo;
		this.regNo = regNo;
		this.appNo = appNo;
		this.studentName = studentName;
		this.specName = specName;
		this.isCheckedDummy = false;
	}

	public ExamStudentSpecializationTO(int id, int appNo, String rollNo,
			String regNo, int studentId, String studentName) {
		super();
		this.id = id;
		this.appNo = appNo;
		this.rollNo = rollNo;
		this.regNo = regNo;
		this.studentId = studentId;
		this.studentName = studentName;
		this.isCheckedDummy = false;
	}

	public ExamStudentSpecializationTO(int id, int appNo, String regNo,
			String rollNo, int studentId, String studentName,
			String sectionName, int schemeNo) {
		super();
		this.id = id;
		this.appNo = appNo;
		this.regNo = regNo;
		this.rollNo = rollNo;
		this.studentId = studentId;
		this.studentName = studentName;
		this.sectionName = sectionName;
		this.schemeNo = schemeNo;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public int getSchemeNo() {
		return schemeNo;
	}

	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public int getAppNo() {
		return appNo;
	}

	public void setAppNo(int appNo) {
		this.appNo = appNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setDummyOnOrOff(String dummyOnOrOff) {
		this.dummyOnOrOff = dummyOnOrOff;
	}

	public String getDummyOnOrOff() {
		return dummyOnOrOff;
	}

	public void setIsCheckedDummy(boolean isCheckedDummy) {
		this.isCheckedDummy = isCheckedDummy;
	}

	public boolean getIsCheckedDummy() {
		return isCheckedDummy;
	}

	@Override
	public int compareTo(ExamStudentSpecializationTO arg0) {
		if(arg0!=null && this!=null && arg0.getSpecName()!=null 
				 && this.getSpecName()!=null){
				return this.getSpecName().compareTo(arg0.getSpecName());
		}else
		return 0;
	}

}
