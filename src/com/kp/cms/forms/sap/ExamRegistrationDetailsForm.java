package com.kp.cms.forms.sap;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.sap.ExamRegistrationDetailsTo;

public class ExamRegistrationDetailsForm extends BaseActionForm{
private int id;
private int workLocationId;
private int examScheduleDateId;
private Map<Integer, String> workLocationMap;
private Map<String,List<ExamRegistrationDetailsTo>> dateSessionMap;
private int venueId;
private int studentId;
private String workLocationName;
private String venueName;
private boolean isPaymentRequired;
private String feeAmount;
private boolean isOnline;
private int financialYear;
private String message;
private String examDate;
private String sessionName;
private int admApplnId;
private int curriculumSchemeDurationId;
/* offline SAP REGISTRATION properties */
private String isConcessionReg;
private String concessionAmount;
private String concessionDetails;
private String netAmount;
private String tempNetAmount;
private String isPrint;
private String userName;
private int receiptNumber;
private String paidDate;
private String pcAccHead;
private String details;
private String total;
private String rupeesInWords;
private String receiptListSize;
/*-------------------*/
private String checkRegOrSupp;
public int getCurriculumSchemeDurationId() {
	return curriculumSchemeDurationId;
}
public void setCurriculumSchemeDurationId(int curriculumSchemeDurationId) {
	this.curriculumSchemeDurationId = curriculumSchemeDurationId;
}
public int getAdmApplnId() {
	return admApplnId;
}
public void setAdmApplnId(int admApplnId) {
	this.admApplnId = admApplnId;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Map<Integer, String> getWorkLocationMap() {
	return workLocationMap;
}
public void setWorkLocationMap(Map<Integer, String> workLocationMap) {
	this.workLocationMap = workLocationMap;
}
public int getWorkLocationId() {
	return workLocationId;
}
public void setWorkLocationId(int workLocationId) {
	this.workLocationId = workLocationId;
}
public Map<String, List<ExamRegistrationDetailsTo>> getDateSessionMap() {
	return dateSessionMap;
}
public void setDateSessionMap(
		Map<String, List<ExamRegistrationDetailsTo>> dateSessionMap) {
	this.dateSessionMap = dateSessionMap;
}
public int getExamScheduleDateId() {
	return examScheduleDateId;
}
public void setExamScheduleDateId(int examScheduleDateId) {
	this.examScheduleDateId = examScheduleDateId;
}
public int getVenueId() {
	return venueId;
}
public void setVenueId(int venueId) {
	this.venueId = venueId;
}
public void reset() {
	this.workLocationId = 0;
	this.examScheduleDateId = 0;
	this.workLocationMap= null;
	this.dateSessionMap = null;
	this.venueId =0;
	this.workLocationName = null;
	this.venueName = null;
	this.studentId = 0;
	this.financialYear =0;
	super.setSmartCardNo(null);
	super.setValidThruYear(null);
	super.setValidThruMonth(null);
	this.message = null;
	this.isOnline= false;
	this.feeAmount = null;
	this.sessionName = null;
	super.setNameOfStudent(null);
	super.setDob(null);
	super.setClassName(null);
	super.setOriginalDob(null);
	super.setRegNo(null);
	super.setSmartCardNo(null);
	this.admApplnId = 0;
	super.setAcademicYear(null);
	super.setCourseId(null);
	this.curriculumSchemeDurationId =0;
	this.isConcessionReg = "false";
	this.isPrint="false";
	super.setErrorMessage(null);
}
public int getStudentId() {
	return studentId;
}
public void setStudentId(int studentId) {
	this.studentId = studentId;
}
public String getWorkLocationName() {
	return workLocationName;
}
public void setWorkLocationName(String workLocationName) {
	this.workLocationName = workLocationName;
}
public String getVenueName() {
	return venueName;
}
public void setVenueName(String venueName) {
	this.venueName = venueName;
}
public boolean isPaymentRequired() {
	return isPaymentRequired;
}
public void setPaymentRequired(boolean isPaymentRequired) {
	this.isPaymentRequired = isPaymentRequired;
}

public String getFeeAmount() {
	return feeAmount;
}
public void setFeeAmount(String feeAmount) {
	this.feeAmount = feeAmount;
}
public boolean isOnline() {
	return isOnline;
}
public void setOnline(boolean isOnline) {
	this.isOnline = isOnline;
}
public int getFinancialYear() {
	return financialYear;
}
public void setFinancialYear(int financialYear) {
	this.financialYear = financialYear;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String getExamDate() {
	return examDate;
}
public void setExamDate(String examDate) {
	this.examDate = examDate;
}
public String getSessionName() {
	return sessionName;
}
public void setSessionName(String sessionName) {
	this.sessionName = sessionName;
}

public String getIsConcessionReg() {
	return isConcessionReg;
}
public void setIsConcessionReg(String isConcessionReg) {
	this.isConcessionReg = isConcessionReg;
}
public String getConcessionAmount() {
	return concessionAmount;
}
public void setConcessionAmount(String concessionAmount) {
	this.concessionAmount = concessionAmount;
}
public String getConcessionDetails() {
	return concessionDetails;
}
public void setConcessionDetails(String concessionDetails) {
	this.concessionDetails = concessionDetails;
}
public String getNetAmount() {
	return netAmount;
}
public void setNetAmount(String netAmount) {
	this.netAmount = netAmount;
}
public String getTempNetAmount() {
	return tempNetAmount;
}
public void setTempNetAmount(String tempNetAmount) {
	this.tempNetAmount = tempNetAmount;
}
public String getIsPrint() {
	return isPrint;
}
public void setIsPrint(String isPrint) {
	this.isPrint = isPrint;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public int getReceiptNumber() {
	return receiptNumber;
}
public void setReceiptNumber(int receiptNumber) {
	this.receiptNumber = receiptNumber;
}
public String getPaidDate() {
	return paidDate;
}
public void setPaidDate(String paidDate) {
	this.paidDate = paidDate;
}
public String getPcAccHead() {
	return pcAccHead;
}
public void setPcAccHead(String pcAccHead) {
	this.pcAccHead = pcAccHead;
}
public String getDetails() {
	return details;
}
public void setDetails(String details) {
	this.details = details;
}
public String getTotal() {
	return total;
}
public void setTotal(String total) {
	this.total = total;
}
public String getRupeesInWords() {
	return rupeesInWords;
}
public void setRupeesInWords(String rupeesInWords) {
	this.rupeesInWords = rupeesInWords;
}
public void resetStudentDetails() {
	super.setNameOfStudent(null);
	super.setDob(null);
	super.setClassName(null);
	super.setOriginalDob(null);
	super.setRegNo(null);
	super.setSmartCardNo(null);
	super.setValidThruMonth(null);
	super.setValidThruYear(null);
	this.financialYear =0;
	this.message = null;
	this.admApplnId = 0;
	super.setAcademicYear(null);
	super.setCourseId(null);
	this.curriculumSchemeDurationId =0;
	this.isConcessionReg = "false";
	this.concessionAmount = null;
	this.concessionDetails =null;
	this.netAmount = null;
	this.tempNetAmount = null;
	this.isPrint="false";
	this.userName = null;
	this.receiptNumber=0;
	this.rupeesInWords = null;
	this.details = null;
	this.total =null;
	this.paidDate =null;
	this.pcAccHead=null;
	this.receiptListSize =null;
}
public String getReceiptListSize() {
	return receiptListSize;
}
public void setReceiptListSize(String receiptListSize) {
	this.receiptListSize = receiptListSize;
}
public String getCheckRegOrSupp() {
	return checkRegOrSupp;
}
public void setCheckRegOrSupp(String checkRegOrSupp) {
	this.checkRegOrSupp = checkRegOrSupp;
}
}
