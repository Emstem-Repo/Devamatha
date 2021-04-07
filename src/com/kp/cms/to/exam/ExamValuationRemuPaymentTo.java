package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.Map;

public class ExamValuationRemuPaymentTo implements Serializable{
private int id;
private String name;
private int vocherNo;
private Map<String,String> modeOfPaymentMap;
private String modeOfPayment;
private String date;
private String department;
private String panNo;
private String accountNo;
private String ifscCode;
private String bankName;
private String totalAmount;
private String checked;
private String otherEmpId;
private String totalNoOfConveyance;
private String totalBoardMeetings;
private String boardMeeetingRate;
private String da;
private String ta;
private String otherCost;
private String mobileNo;
private String address;



public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getMobileNo() {
	return mobileNo;
}
public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
}
public String getOtherEmpId() {
	return otherEmpId;
}
public void setOtherEmpId(String otherEmpId) {
	this.otherEmpId = otherEmpId;
}
public String getTotalNoOfConveyance() {
	return totalNoOfConveyance;
}
public void setTotalNoOfConveyance(String totalNoOfConveyance) {
	this.totalNoOfConveyance = totalNoOfConveyance;
}
public String getTotalBoardMeetings() {
	return totalBoardMeetings;
}
public void setTotalBoardMeetings(String totalBoardMeetings) {
	this.totalBoardMeetings = totalBoardMeetings;
}
public String getBoardMeeetingRate() {
	return boardMeeetingRate;
}
public void setBoardMeeetingRate(String boardMeeetingRate) {
	this.boardMeeetingRate = boardMeeetingRate;
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
public String getOtherCost() {
	return otherCost;
}
public void setOtherCost(String otherCost) {
	this.otherCost = otherCost;
}
public String getPanNo() {
	return panNo;
}
public void setPanNo(String panNo) {
	this.panNo = panNo;
}
public String getChecked() {
	return checked;
}
public void setChecked(String checked) {
	this.checked = checked;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getVocherNo() {
	return vocherNo;
}
public void setVocherNo(int vocherNo) {
	this.vocherNo = vocherNo;
}
public Map<String, String> getModeOfPaymentMap() {
	return modeOfPaymentMap;
}
public void setModeOfPaymentMap(Map<String, String> modeOfPaymentMap) {
	this.modeOfPaymentMap = modeOfPaymentMap;
}
public String getModeOfPayment() {
	return modeOfPayment;
}
public void setModeOfPayment(String modeOfPayment) {
	this.modeOfPayment = modeOfPayment;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getDepartment() {
	return department;
}
public void setDepartment(String department) {
	this.department = department;
}
public String getAccountNo() {
	return accountNo;
}
public void setAccountNo(String accountNo) {
	this.accountNo = accountNo;
}
public String getIfscCode() {
	return ifscCode;
}
public void setIfscCode(String ifscCode) {
	this.ifscCode = ifscCode;
}
public String getBankName() {
	return bankName;
}
public void setBankName(String bankName) {
	this.bankName = bankName;
}
public String getTotalAmount() {
	return totalAmount;
}
public void setTotalAmount(String totalAmount) {
	this.totalAmount = totalAmount;
}

}
