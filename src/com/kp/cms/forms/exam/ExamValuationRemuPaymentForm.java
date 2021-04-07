package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamValuationRemuPaymentTo;
import com.kp.cms.to.exam.ValuationChallanTO;

public class ExamValuationRemuPaymentForm extends BaseActionForm{
private String valuatorsType;
private List<ExamValuationRemuPaymentTo> list;
private int vocherId;
private int id;
//hai
private String otherEmpId;
private String totalNoOfConveyance;
private String totalBoardMeetings;
private String boardMeeetingRate;
private String da;
private String ta;
private String otherCost;
//to display
private String noOfBoardMeetings;
private String totalBoardMeetingRate;
private boolean viewFields;
private String anyOther;
private int totalScripts;
private String totalScriptsAmount;
private String totalBoardMeetingCharge;
private String totalConveyanceCharge;
private String conveyanceCharge;
private String grandTotal;
private Map<String,Map<Integer, ValuationChallanTO>> map;
private String challanNo;
private Boolean printTa;
private Boolean printDa;
private Boolean printOther;
private Boolean printconveyence;
private String totalInWords;
private String currentDate;
private String fingerPrintId;
private String employeeName;
private String accountNo;
private String departmentName;
private String panNo;
private Map<Integer,ExamValuationRemuPaymentTo> tosMap;
private String bankName;
private String bankBranch;
private String address;
private String bankIfscCode;
private String displayGuest;
private String paidStauts;


public String getPaidStauts() {
	return paidStauts;
}

public void setPaidStauts(String paidStauts) {
	this.paidStauts = paidStauts;
}

public String getDisplayGuest() {
	return displayGuest;
}

public void setDisplayGuest(String displayGuest) {
	this.displayGuest = displayGuest;
}

public String getBankIfscCode() {
	return bankIfscCode;
}

public void setBankIfscCode(String bankIfscCode) {
	this.bankIfscCode = bankIfscCode;
}

public String getBankName() {
	return bankName;
}

public void setBankName(String bankName) {
	this.bankName = bankName;
}

public String getBankBranch() {
	return bankBranch;
}

public void setBankBranch(String bankBranch) {
	this.bankBranch = bankBranch;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public Map<Integer, ExamValuationRemuPaymentTo> getTosMap() {
	return tosMap;
}

public void setTosMap(Map<Integer, ExamValuationRemuPaymentTo> tosMap) {
	this.tosMap = tosMap;
}

public String getPanNo() {
	return panNo;
}

public void setPanNo(String panNo) {
	this.panNo = panNo;
}

public String getCurrentDate() {
	return currentDate;
}

public void setCurrentDate(String currentDate) {
	this.currentDate = currentDate;
}

public String getFingerPrintId() {
	return fingerPrintId;
}

public void setFingerPrintId(String fingerPrintId) {
	this.fingerPrintId = fingerPrintId;
}

public String getEmployeeName() {
	return employeeName;
}

public void setEmployeeName(String employeeName) {
	this.employeeName = employeeName;
}

public String getAccountNo() {
	return accountNo;
}

public void setAccountNo(String accountNo) {
	this.accountNo = accountNo;
}

public String getDepartmentName() {
	return departmentName;
}

public void setDepartmentName(String departmentName) {
	this.departmentName = departmentName;
}

public String getTotalInWords() {
	return totalInWords;
}

public void setTotalInWords(String totalInWords) {
	this.totalInWords = totalInWords;
}

public Boolean getPrintconveyence() {
	return printconveyence;
}

public void setPrintconveyence(Boolean printconveyence) {
	this.printconveyence = printconveyence;
}

public Boolean getPrintTa() {
	return printTa;
}

public void setPrintTa(Boolean printTa) {
	this.printTa = printTa;
}

public Boolean getPrintDa() {
	return printDa;
}

public void setPrintDa(Boolean printDa) {
	this.printDa = printDa;
}

public Boolean getPrintOther() {
	return printOther;
}

public void setPrintOther(Boolean printOther) {
	this.printOther = printOther;
}

public String getChallanNo() {
	return challanNo;
}

public void setChallanNo(String challanNo) {
	this.challanNo = challanNo;
}

public String getNoOfBoardMeetings() {
	return noOfBoardMeetings;
}

public void setNoOfBoardMeetings(String noOfBoardMeetings) {
	this.noOfBoardMeetings = noOfBoardMeetings;
}

public String getBoardMeeetingRate() {
	return boardMeeetingRate;
}

public void setBoardMeeetingRate(String boardMeeetingRate) {
	this.boardMeeetingRate = boardMeeetingRate;
}

public String getTotalBoardMeetingRate() {
	return totalBoardMeetingRate;
}

public void setTotalBoardMeetingRate(String totalBoardMeetingRate) {
	this.totalBoardMeetingRate = totalBoardMeetingRate;
}

public boolean isViewFields() {
	return viewFields;
}

public void setViewFields(boolean viewFields) {
	this.viewFields = viewFields;
}

public String getTotalNoOfConveyance() {
	return totalNoOfConveyance;
}

public void setTotalNoOfConveyance(String totalNoOfConveyance) {
	this.totalNoOfConveyance = totalNoOfConveyance;
}

public String getDa() {
	return da;
}

public void setDa(String da) {
	this.da = da;
}

public String getTa() {
	return ta;
}

public void setTa(String ta) {
	this.ta = ta;
}

public String getAnyOther() {
	return anyOther;
}

public void setAnyOther(String anyOther) {
	this.anyOther = anyOther;
}

public String getOtherCost() {
	return otherCost;
}

public void setOtherCost(String otherCost) {
	this.otherCost = otherCost;
}

public int getTotalScripts() {
	return totalScripts;
}

public void setTotalScripts(int totalScripts) {
	this.totalScripts = totalScripts;
}

public String getTotalScriptsAmount() {
	return totalScriptsAmount;
}

public void setTotalScriptsAmount(String totalScriptsAmount) {
	this.totalScriptsAmount = totalScriptsAmount;
}

public String getOtherEmpId() {
	return otherEmpId;
}

public void setOtherEmpId(String otherEmpId) {
	this.otherEmpId = otherEmpId;
}

public String getTotalBoardMeetings() {
	return totalBoardMeetings;
}

public void setTotalBoardMeetings(String totalBoardMeetings) {
	this.totalBoardMeetings = totalBoardMeetings;
}

public String getTotalBoardMeetingCharge() {
	return totalBoardMeetingCharge;
}

public void setTotalBoardMeetingCharge(String totalBoardMeetingCharge) {
	this.totalBoardMeetingCharge = totalBoardMeetingCharge;
}

public String getTotalConveyanceCharge() {
	return totalConveyanceCharge;
}

public void setTotalConveyanceCharge(String totalConveyanceCharge) {
	this.totalConveyanceCharge = totalConveyanceCharge;
}

public String getConveyanceCharge() {
	return conveyanceCharge;
}

public void setConveyanceCharge(String conveyanceCharge) {
	this.conveyanceCharge = conveyanceCharge;
}

public String getGrandTotal() {
	return grandTotal;
}

public void setGrandTotal(String grandTotal) {
	this.grandTotal = grandTotal;
}

public Map<String, Map<Integer, ValuationChallanTO>> getMap() {
	return map;
}

public void setMap(Map<String, Map<Integer, ValuationChallanTO>> map) {
	this.map = map;
}

public int getVocherId() {
	return vocherId;
}

public void setVocherId(int vocherId) {
	this.vocherId = vocherId;
}

public List<ExamValuationRemuPaymentTo> getList() {
	return list;
}

public void setList(List<ExamValuationRemuPaymentTo> list) {
	this.list = list;
}

public String getValuatorsType() {
	return valuatorsType;
}

public void setValuatorsType(String valuatorsType) {
	this.valuatorsType = valuatorsType;
}

}
