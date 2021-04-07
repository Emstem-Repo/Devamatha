package com.kp.cms.to.pettycash;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;

public class DailyCollectionsTo {
	private int studentId;
	private String receiptOrStudentnum;
	private String  regNo;
	private String  applicationNo;
	private String name;
	private String time;
	private String comments;
	private String date;

	private String lastModifiedDate;
	private String coursecode;
	private String classname;
	private	 String accountName;
	private double totalAmount;
	private String accCode;
	private double total;
	private double amount;
	
	private Set<ApplicantSubjectGroup> applicantSubjectGroups = new HashSet<ApplicantSubjectGroup>(
			0);
	
	
	private List<AccountHeadTO> accountHeadList = new ArrayList<AccountHeadTO>();
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getTotal() {
		return total;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getCoursecode() {
		return coursecode;
	}
	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getReceiptOrStudentnum() {
		return receiptOrStudentnum;
	}
	public void setReceiptOrStudentnum(String receiptOrStudentnum) {
		this.receiptOrStudentnum = receiptOrStudentnum;
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getAccCode() {
		return accCode;
	}
	public void setAccCode(String accCode) {
		this.accCode = accCode;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public List<AccountHeadTO> getAccountHeadList() {
		return accountHeadList;
	}
	public void setAccountHeadList(List<AccountHeadTO> accountHeadList) {
		this.accountHeadList = accountHeadList;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getClassname() {
		return classname;
	}
	
	public Set<ApplicantSubjectGroup> getApplicantSubjectGroups() {
		return applicantSubjectGroups;
	}
	public void setApplicantSubjectGroups(
			Set<ApplicantSubjectGroup> applicantSubjectGroups) {
		this.applicantSubjectGroups = applicantSubjectGroups;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	
	
	 
}
