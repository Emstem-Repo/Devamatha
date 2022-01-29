package com.kp.cms.to.exam;

import java.io.Serializable;

public class ValuationChallanTO implements Serializable {
	
	private int id;
	private String examName;
	private int examId;
	private String subjectName;
	private String employeeName;
	private String otherEmployee;
	private String issueDate;
	private int answerScripts;
	private String valuator;
	private int totalScripts;
	private String totalAmount;
	private int subjectId;
	
	
	//for Challan Print
	private int nnumerOfScripts;
	private String rate;
	private String totalScrptsAmount;
	
	private int totalAbsentees;
	private String startDate;
	private String endDate;
	private String billNo;
	private String isPaid;
	
	//for board meeting
	private String isBoardMeetingApplicable;
	private String checked;
	private int boardMeetingChargePerSub;
	private int remunerationDetailId;
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getOtherEmployee() {
		return otherEmployee;
	}
	public void setOtherEmployee(String otherEmployee) {
		this.otherEmployee = otherEmployee;
	}
	public String getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	public int getAnswerScripts() {
		return answerScripts;
	}
	public void setAnswerScripts(int answerScripts) {
		this.answerScripts = answerScripts;
	}
	public String getValuator() {
		return valuator;
	}
	public void setValuator(String valuator) {
		this.valuator = valuator;
	}
	public int getTotalScripts() {
		return totalScripts;
	}
	public void setTotalScripts(int totalScripts) {
		this.totalScripts = totalScripts;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public int getNnumerOfScripts() {
		return nnumerOfScripts;
	}
	public void setNnumerOfScripts(int nnumerOfScripts) {
		this.nnumerOfScripts = nnumerOfScripts;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getTotalScrptsAmount() {
		return totalScrptsAmount;
	}
	public void setTotalScrptsAmount(String totalScrptsAmount) {
		this.totalScrptsAmount = totalScrptsAmount;
	}
	public int getTotalAbsentees() {
		return totalAbsentees;
	}
	public void setTotalAbsentees(int totalAbsentees) {
		this.totalAbsentees = totalAbsentees;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getIsPaid() {
		return isPaid;
	}
	public void setIsPaid(String isPaid) {
		this.isPaid = isPaid;
	}
	public String getIsBoardMeetingApplicable() {
		return isBoardMeetingApplicable;
	}
	public void setIsBoardMeetingApplicable(String isBoardMeetingApplicable) {
		this.isBoardMeetingApplicable = isBoardMeetingApplicable;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public int getBoardMeetingChargePerSub() {
		return boardMeetingChargePerSub;
	}
	public void setBoardMeetingChargePerSub(int boardMeetingChargePerSub) {
		this.boardMeetingChargePerSub = boardMeetingChargePerSub;
	}
	public int getRemunerationDetailId() {
		return remunerationDetailId;
	}
	public void setRemunerationDetailId(int remunerationDetailId) {
		this.remunerationDetailId = remunerationDetailId;
	}
	
	
	
	
}