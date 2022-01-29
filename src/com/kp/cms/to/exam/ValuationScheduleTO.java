package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.Date;

public class ValuationScheduleTO implements Serializable {
	
	private int id;
	private String examName;
	private int examId;
	private String subjectName;
	private int subjectId;
	private String employeeName;
	private int employeeId;
	private String otherEmployee;
	private int otherEmployeeId;
	private String valuator;
	private String boardValuationDate;
	private String valuationFrom;
	private String valuationTo;
	private int userId;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getOtherEmployeeId() {
		return otherEmployeeId;
	}
	public void setOtherEmployeeId(int otherEmployeeId) {
		this.otherEmployeeId = otherEmployeeId;
	}
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
	public String getValuator() {
		return valuator;
	}
	public void setValuator(String valuator) {
		this.valuator = valuator;
	}
	public String getBoardValuationDate() {
		return boardValuationDate;
	}
	public void setBoardValuationDate(String boardValuationDate) {
		this.boardValuationDate = boardValuationDate;
	}
	public String getValuationFrom() {
		return valuationFrom;
	}
	public void setValuationFrom(String valuationFrom) {
		this.valuationFrom = valuationFrom;
	}
	public String getValuationTo() {
		return valuationTo;
	}
	public void setValuationTo(String valuationTo) {
		this.valuationTo = valuationTo;
	}
	

}
