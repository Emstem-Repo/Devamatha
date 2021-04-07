package com.kp.cms.to.admin;

import java.io.Serializable;


public class ApplicantLateralDetailsTO implements Serializable,Comparable<ApplicantLateralDetailsTO> {
	private int id;
	private int admApplnId;
	private String semesterName;
	private String maxMarks;
	private String minMarks;
	private String marksObtained;
	private String yearPass;
	private String monthPass;
	private int semesterNo;
	private String universityName;
	private String stateName;
	private String instituteAddress;
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
	public String getUniversityName() {
		return universityName;
	}
	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getInstituteAddress() {
		return instituteAddress;
	}
	public void setInstituteAddress(String instituteAddress) {
		this.instituteAddress = instituteAddress;
	}
	public int getSemesterNo() {
		return semesterNo;
	}
	public void setSemesterNo(int semesterNo) {
		this.semesterNo = semesterNo;
	}
	@Override
	public int compareTo(ApplicantLateralDetailsTO arg0) {
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
