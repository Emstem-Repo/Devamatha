package com.kp.cms.to.exam;

import java.io.Serializable;

public class ExamValidationDetailsTO implements Serializable {
	
	private int id;
	private String examName;
	private int examId;
	private String subjectName;
	private String employeeName;
	private String otherEmployee;
	private String issueDate;
	private String answerScripts;
	private String valuator;
	private String newLine="\n";
	private int total;
	
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
	public String getAnswerScripts() {
		return answerScripts;
	}
	public void setAnswerScripts(String answerScripts) {
		this.answerScripts = answerScripts;
	}
	public String getValuator() {
		return valuator;
	}
	public void setValuator(String valuator) {
		this.valuator = valuator;
	}
	public String getNewLine() {
		return newLine;
	}
	public void setNewLine(String newLine) {
		this.newLine = newLine;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
}