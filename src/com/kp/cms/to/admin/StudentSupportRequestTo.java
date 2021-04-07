package com.kp.cms.to.admin;

import java.util.Map;

public class StudentSupportRequestTo {
private String categoryName;
private String dateOfSubmssion;
private String status;
private String description;
private int id;
private String email;
private String mobileNo;
private int catgryId;
private Map<Integer,String> categoryMap;
private Map<String,String> statusMap;
private String employeeId;
private String regNo;
private String remarks;
private String issueRaisedBy;
private String requestId;
private String campus;
private String categoryEMail;


public String getCategoryEMail() {
	return categoryEMail;
}
public void setCategoryEMail(String categoryEMail) {
	this.categoryEMail = categoryEMail;
}
public String getCampus() {
	return campus;
}
public void setCampus(String campus) {
	this.campus = campus;
}
public String getRequestId() {
	return requestId;
}
public void setRequestId(String requestId) {
	this.requestId = requestId;
}
public String getIssueRaisedBy() {
	return issueRaisedBy;
}
public void setIssueRaisedBy(String issueRaisedBy) {
	this.issueRaisedBy = issueRaisedBy;
}
public String getRemarks() {
	return remarks;
}
public void setRemarks(String remarks) {
	this.remarks = remarks;
}
public String getEmployeeId() {
	return employeeId;
}
public void setEmployeeId(String employeeId) {
	this.employeeId = employeeId;
}
public String getRegNo() {
	return regNo;
}
public void setRegNo(String regNo) {
	this.regNo = regNo;
}
public int getCatgryId() {
	return catgryId;
}
public void setCatgryId(int catgryId) {
	this.catgryId = catgryId;
}
public Map<Integer, String> getCategoryMap() {
	return categoryMap;
}
public void setCategoryMap(Map<Integer, String> categoryMap) {
	this.categoryMap = categoryMap;
}
public Map<String, String> getStatusMap() {
	return statusMap;
}
public void setStatusMap(Map<String, String> statusMap) {
	this.statusMap = statusMap;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getMobileNo() {
	return mobileNo;
}
public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
}
public String getCategoryName() {
	return categoryName;
}
public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
}
public String getDateOfSubmssion() {
	return dateOfSubmssion;
}
public void setDateOfSubmssion(String dateOfSubmssion) {
	this.dateOfSubmssion = dateOfSubmssion;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

}
