package com.kp.cms.to.pettycash;

import java.io.Serializable;

import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.bo.admin.Student;

public class CashCollectionTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String number;
	private String paidDateTime;
	private String fineNumber;
	private String fineType;
	private String name;
	private String academicYear;
	private String studentId;
	private String accHeadId;
	private String finYearId;
	private String amount;
	private String isCancelled;
	private String cancelComments;
	private String isActive;
	
	private String accountCode;
	private String accName;
	private String total;
	private String appRegRollno;
	private String nameWithCode;
	private boolean isFixedAmount;
	private String accId;
	private String hour;
	private String minutes;
	private String paidDate;
	private PcAccountHead pcAccountHead;
	private PcFinancialYear pcFinancialYear;
	private Student student;
	private String refNo;
	private String reftype;
	//for printing receipt
	private String userName;
	private String details;
	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean isFixedAmount() {
		return isFixedAmount;
	}
	public void setFixedAmount(boolean isFixedAmount) {
		this.isFixedAmount = isFixedAmount;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNameWithCode() {
		return nameWithCode;
	}
	public void setNameWithCode(String nameWithCode) {
		this.nameWithCode = nameWithCode;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPaidDateTime() {
		return paidDateTime;
	}
	public void setPaidDateTime(String paidDateTime) {
		this.paidDateTime = paidDateTime;
	}
	public String getFineNumber() {
		return fineNumber;
	}
	public void setFineNumber(String fineNumber) {
		this.fineNumber = fineNumber;
	}
	public String getFineType() {
		return fineType;
	}
	public void setFineType(String fineType) {
		this.fineType = fineType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getAccHeadId() {
		return accHeadId;
	}
	public void setAccHeadId(String accHeadId) {
		this.accHeadId = accHeadId;
	}
	public String getFinYearId() {
		return finYearId;
	}
	public void setFinYearId(String finYearId) {
		this.finYearId = finYearId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getIsCancelled() {
		return isCancelled;
	}
	public void setIsCancelled(String isCancelled) {
		this.isCancelled = isCancelled;
	}
	public String getCancelComments() {
		return cancelComments;
	}
	public void setCancelComments(String cancelComments) {
		this.cancelComments = cancelComments;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getAppRegRollno() {
		return appRegRollno;
	}
	public void setAppRegRollno(String appRegRollno) {
		this.appRegRollno = appRegRollno;
	}
	public String getAccId() {
		return accId;
	}
	public void setAccId(String accId) {
		this.accId = accId;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getMinutes() {
		return minutes;
	}
	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}
	public String getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}

	public PcAccountHead getPcAccountHead() {
		return pcAccountHead;
	}
	public void setPcAccountHead(PcAccountHead pcAccountHead) {
		this.pcAccountHead = pcAccountHead;
	}
	public PcFinancialYear getPcFinancialYear() {
		return pcFinancialYear;
	}
	public void setPcFinancialYear(PcFinancialYear pcFinancialYear) {
		this.pcFinancialYear = pcFinancialYear;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getReftype() {
		return reftype;
	}
	public void setReftype(String reftype) {
		this.reftype = reftype;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	

}
