package com.kp.cms.forms.pettycash;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class StudentCollectionReportForm extends BaseActionForm{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String studentId;
	private String appRegRollNo;
	private String startDate;
	private String endDate;
	private String date;
	private String time;
	private String recNumber;
	private String accCode;
	private String accName;
	private String amount;
	 private String appNo;
	 private String regNo;
	 private String rollNo;
	 private String academicYear;
	 private String name;
	 private String refNo;
	
	
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getAppRegRollNo() {
		return appRegRollNo;
	}
	public void setAppRegRollNo(String appRegRollNo) {
		this.appRegRollNo = appRegRollNo;
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
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRecNumber() {
		return recNumber;
	}
	public void setRecNumber(String recNumber) {
		this.recNumber = recNumber;
	}
	public String getAccCode() {
		return accCode;
	}
	public void setAccCode(String accCode) {
		this.accCode = accCode;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public void resetFields() {
		// TODO Auto-generated method stub
		this.appRegRollNo=null;
		this.startDate = null;
		this.endDate = null;
		this.accCode=null;
		this.accName=null;
		this.amount=null;
		this.appNo=null;
		this.regNo=null;
		this.rollNo=null;
		
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	
}
