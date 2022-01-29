package com.kp.cms.to.admin;

import java.io.Serializable;


public class ApplicantTransferDetailsTO implements Serializable,Comparable<ApplicantTransferDetailsTO>{
	private int id;
	private int admApplnId;
	private String semesterName;
	private String maxMarks;
	private String minMarks;
	private String marksObtained;
	private String yearPass;
	private String monthPass;
	private String universityAppNo;
	private String registationNo;
	private String arrearDetail;
	private String instituteAddr;
	private int semesterNo;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getAdmApplnId() {
		return admApplnId;
	}
	public void setAdmApplnId(int admApplnId) {
		this.admApplnId = admApplnId;
	}
	public String getSemesterName() {
		return semesterName;
	}
	public void setSemesterName(String semesterName) {
		this.semesterName = semesterName;
	}
	public String getMaxMarks() {
		return maxMarks;
	}
	public void setMaxMarks(String maxMarks) {
		this.maxMarks = maxMarks;
	}
	public String getMinMarks() {
		return minMarks;
	}
	public void setMinMarks(String minMarks) {
		this.minMarks = minMarks;
	}
	public String getMarksObtained() {
		return marksObtained;
	}
	public void setMarksObtained(String marksObtained) {
		this.marksObtained = marksObtained;
	}
	public String getYearPass() {
		return yearPass;
	}
	public void setYearPass(String yearPass) {
		this.yearPass = yearPass;
	}
	public String getMonthPass() {
		return monthPass;
	}
	public void setMonthPass(String monthPass) {
		this.monthPass = monthPass;
	}
	public String getUniversityAppNo() {
		return universityAppNo;
	}
	public void setUniversityAppNo(String universityAppNo) {
		this.universityAppNo = universityAppNo;
	}
	public String getRegistationNo() {
		return registationNo;
	}
	public void setRegistationNo(String registationNo) {
		this.registationNo = registationNo;
	}
	public String getArrearDetail() {
		return arrearDetail;
	}
	public void setArrearDetail(String arrearDetail) {
		this.arrearDetail = arrearDetail;
	}
	public String getInstituteAddr() {
		return instituteAddr;
	}
	public void setInstituteAddr(String instituteAddr) {
		this.instituteAddr = instituteAddr;
	}
	public int getSemesterNo() {
		return semesterNo;
	}
	public void setSemesterNo(int semesterNo) {
		this.semesterNo = semesterNo;
	}
	@Override
	public int compareTo(ApplicantTransferDetailsTO arg0) {
		if(arg0!=null && this!=null){
			if(this.getSemesterNo() > arg0.getSemesterNo())
				return 1;
			else if(this.getSemesterNo() < arg0.getSemesterNo())
				return -1;
			else
				return 0;
		}
		return 0;
	}
	
}
