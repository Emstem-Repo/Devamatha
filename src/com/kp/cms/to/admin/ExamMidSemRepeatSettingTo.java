package com.kp.cms.to.admin;

import java.io.Serializable;

public class ExamMidSemRepeatSettingTo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String applicationStartDate;
	private String applicationEndDate;
	private String feePaymentEndDate;
	private String examName;
	private String examType;
	private String academicYear;
	private String feesPerSubject;
	private int repeatSettingId;
	private int examId;
	
	
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	public String getApplicationStartDate() {
		return applicationStartDate;
	}
	public void setApplicationStartDate(String applicationStartDate) {
		this.applicationStartDate = applicationStartDate;
	}
	public String getApplicationEndDate() {
		return applicationEndDate;
	}
	public void setApplicationEndDate(String applicationEndDate) {
		this.applicationEndDate = applicationEndDate;
	}
	public String getFeePaymentEndDate() {
		return feePaymentEndDate;
	}
	public void setFeePaymentEndDate(String feePaymentEndDate) {
		this.feePaymentEndDate = feePaymentEndDate;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public String getFeesPerSubject() {
		return feesPerSubject;
	}
	public void setFeesPerSubject(String feesPerSubject) {
		this.feesPerSubject = feesPerSubject;
	}
	public int getRepeatSettingId() {
		return repeatSettingId;
	}
	public void setRepeatSettingId(int repeatSettingId) {
		this.repeatSettingId = repeatSettingId;
	}

}
