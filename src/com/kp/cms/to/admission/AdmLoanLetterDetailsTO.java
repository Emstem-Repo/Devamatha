package com.kp.cms.to.admission;

import java.util.Date;
import java.util.List;

public class AdmLoanLetterDetailsTO {

	private int id;
	private int admApplnId;
	private String isIssued;
	private String isActive;
	private String admittedDate;
	List<AdmLoanLetterDetailsTO> letterTolist;
	private String studentName;
	private String courseName;
	private String registerNo;
	private int applicationNo;
	private String checked1;
	private boolean tempChecked;
	
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
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getIsIssued() {
		return isIssued;
	}
	public void setIsIssued(String isIssued) {
		this.isIssued = isIssued;
	}
	public String getAdmittedDate() {
		return admittedDate;
	}
	public void setAdmittedDate(String admittedDate) {
		this.admittedDate = admittedDate;
	}
	public List<AdmLoanLetterDetailsTO> getLetterTolist() {
		return letterTolist;
	}
	public void setLetterTolist(List<AdmLoanLetterDetailsTO> letterTolist) {
		this.letterTolist = letterTolist;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public int getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(int applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getChecked1() {
		return checked1;
	}
	public void setChecked1(String checked1) {
		this.checked1 = checked1;
	}
	public boolean isTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(boolean tempChecked) {
		this.tempChecked = tempChecked;
	}

}