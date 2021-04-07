package com.kp.cms.forms.smartcard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.smartcard.ScLostCorrectionTo;

public class ScLostCorrectionForm extends BaseActionForm {
	
	private int id;
	private String regNo;
	private String name;
	private String stuId;
	private String className;
	private String cardType;
	private String isFileTypeRequired;
	private String status;
	private Map<Integer,List<StudentTO>> smartCardData;
	//private List<Integer> studentIds;
	private List<ScLostCorrectionTo> scHistory;
	private String displayHistory;
	private String oldSmartCardNo;
	private String remarks;
	private String appliedDate;
	private String accountNo;
	private String currentAddress;
	private String studentCourseDuration;
	private String lastFiveDigitAccNo;
	private String isEmployee;
	private String empDepartment;
	private String empDesignation;
	private String empId;
	private String empFingerPrintId;
	
	
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getOldSmartCardNo() {
		return oldSmartCardNo;
	}
	public void setOldSmartCardNo(String oldSmartCardNo) {
		this.oldSmartCardNo = oldSmartCardNo;
	}
	public String getDisplayHistory() {
		return displayHistory;
	}
	public void setDisplayHistory(String displayHistory) {
		this.displayHistory = displayHistory;
	}
	public List<ScLostCorrectionTo> getScHistory() {
		return scHistory;
	}
	public void setScHistory(List<ScLostCorrectionTo> scHistory) {
		this.scHistory = scHistory;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Map<Integer, List<StudentTO>> getSmartCardData() {
		return smartCardData;
	}
	public void setSmartCardData(Map<Integer, List<StudentTO>> smartCardData) {
		this.smartCardData = smartCardData;
	}
	public String getStuId() {
		return stuId;
	}
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getIsFileTypeRequired() {
		return isFileTypeRequired;
	}
	public void setIsFileTypeRequired(String isFileTypeRequired) {
		this.isFileTypeRequired = isFileTypeRequired;
	}
	public String getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(String appliedDate) {
		this.appliedDate = appliedDate;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getCurrentAddress() {
		return currentAddress;
	}
	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}
	public String getStudentCourseDuration() {
		return studentCourseDuration;
	}
	public void setStudentCourseDuration(String studentCourseDuration) {
		this.studentCourseDuration = studentCourseDuration;
	}
	public String getLastFiveDigitAccNo() {
		return lastFiveDigitAccNo;
	}
	public void setLastFiveDigitAccNo(String lastFiveDigitAccNo) {
		this.lastFiveDigitAccNo = lastFiveDigitAccNo;
	}
	public String getIsEmployee() {
		return isEmployee;
	}
	public void setIsEmployee(String isEmployee) {
		this.isEmployee = isEmployee;
	}
	public String getEmpDepartment() {
		return empDepartment;
	}
	public void setEmpDepartment(String empDepartment) {
		this.empDepartment = empDepartment;
	}
	public String getEmpDesignation() {
		return empDesignation;
	}
	public void setEmpDesignation(String empDesignation) {
		this.empDesignation = empDesignation;
	}
	public String getEmpFingerPrintId() {
		return empFingerPrintId;
	}
	public void setEmpFingerPrintId(String empFingerPrintId) {
		this.empFingerPrintId = empFingerPrintId;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	
	
	
	public void clearAll()
	{
		this.regNo=null;
		this.cardType="Lost";
		this.isFileTypeRequired="NO";
		this.name=null;
		this.className=null;
		this.status=null;
		this.displayHistory=null;
		this.remarks=null;
		this.appliedDate=null;
		this.accountNo=null;
		this.currentAddress=null;
		this.studentCourseDuration=null;
		this.lastFiveDigitAccNo=null;
		this.oldSmartCardNo=null;
		this.isEmployee="Student";
		this.empDepartment=null;
		this.empDesignation=null;
		this.empFingerPrintId=null;
	}

	
}
