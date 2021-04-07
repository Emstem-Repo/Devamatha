package com.kp.cms.to.fee;

import java.util.List;

public class BulkChalanTo 
{
	private List<PrintChalanTO>printChalanList;
	private String billNo;
	private String challanPrintDate;
	private String chalanCreatedTime;
	private String studentName;
	private String applicationId;
	private String className;
	private String accwiseTotalPrintString;
	private String currencyCode;
	
	public List<PrintChalanTO> getPrintChalanList() {
		return printChalanList;
	}
	public void setPrintChalanList(List<PrintChalanTO> printChalanList) {
		this.printChalanList = printChalanList;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getChallanPrintDate() {
		return challanPrintDate;
	}
	public void setChallanPrintDate(String challanPrintDate) {
		this.challanPrintDate = challanPrintDate;
	}
	public String getChalanCreatedTime() {
		return chalanCreatedTime;
	}
	public void setChalanCreatedTime(String chalanCreatedTime) {
		this.chalanCreatedTime = chalanCreatedTime;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getAccwiseTotalPrintString() {
		return accwiseTotalPrintString;
	}
	public void setAccwiseTotalPrintString(String accwiseTotalPrintString) {
		this.accwiseTotalPrintString = accwiseTotalPrintString;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	
}
