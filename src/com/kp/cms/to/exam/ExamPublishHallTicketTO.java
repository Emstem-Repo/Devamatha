package com.kp.cms.to.exam;

import java.io.Serializable;

public class ExamPublishHallTicketTO implements Serializable,Comparable<ExamPublishHallTicketTO>{

	private int id;
	private Integer examId;
	private Integer classId;
	private Integer agreementId;
	private Integer footerId;
	private String publishFor;
	private String downloadStartDate;
	private String downloadEndDate;
	private String programType;
	private String classcode;
	private String examName;
	private String examType;
	private boolean checked;
	private String revaluationEndDate;
	private String extendedDate;
	
	private String mobileNo1;
	private String mobileNo2;

	public ExamPublishHallTicketTO() {
		super();
	}

	public ExamPublishHallTicketTO(int id, Integer examId, Integer classId,
			Integer agreementId, Integer footerId, String publishFor,
			String downloadStartDate, String downloadEndDate,
			String programType, String classcode, String examName,
			String examType,String mobileNo1,String mobileNo2) {
		super();
		this.id = id;
		this.examId = examId;
		this.classId = classId;
		this.agreementId = agreementId;
		this.footerId = footerId;
		this.publishFor = publishFor;
		this.downloadStartDate = downloadStartDate;
		this.downloadEndDate = downloadEndDate;
		this.programType = programType;
		this.classcode = classcode;
		this.examName = examName;
		this.examType = examType;
		this.examType = mobileNo1;
		this.examType = mobileNo2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(Integer agreementId) {
		this.agreementId = agreementId;
	}

	public Integer getFooterId() {
		return footerId;
	}

	public void setFooterId(Integer footerId) {
		this.footerId = footerId;
	}

	public String getPublishFor() {
		return publishFor;
	}

	public void setPublishFor(String publishFor) {
		this.publishFor = publishFor;
	}

	public String getDownloadStartDate() {
		return downloadStartDate;
	}

	public void setDownloadStartDate(String downloadStartDate) {
		this.downloadStartDate = downloadStartDate;
	}

	public String getDownloadEndDate() {
		return downloadEndDate;
	}

	public void setDownloadEndDate(String downloadEndDate) {
		this.downloadEndDate = downloadEndDate;
	}

	public String getProgramType() {
		return programType;
	}

	public void setProgramType(String programType) {
		this.programType = programType;
	}

	public String getClasscode() {
		return classcode;
	}

	public void setClasscode(String classcode) {
		this.classcode = classcode;
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

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getRevaluationEndDate() {
		return revaluationEndDate;
	}

	public void setRevaluationEndDate(String revaluationEndDate) {
		this.revaluationEndDate = revaluationEndDate;
	}

	@Override
	public int compareTo(ExamPublishHallTicketTO arg0) {
		if(arg0!=null && this!=null && arg0.getExamType()!=null
				 && this.getExamType()!=null){
			return this.getExamType().compareTo(arg0.getExamType());
		}else
		return 0;
	}

	public String getMobileNo1() {
		return mobileNo1;
	}

	public void setMobileNo1(String mobileNo1) {
		this.mobileNo1 = mobileNo1;
	}

	public String getMobileNo2() {
		return mobileNo2;
	}

	public void setMobileNo2(String mobileNo2) {
		this.mobileNo2 = mobileNo2;
	}

	public String getExtendedDate() {
		return extendedDate;
	}

	public void setExtendedDate(String extendedDate) {
		this.extendedDate = extendedDate;
	}
	

}
