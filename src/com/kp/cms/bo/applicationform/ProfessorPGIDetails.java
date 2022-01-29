package com.kp.cms.bo.applicationform;

import java.math.BigDecimal;
import java.util.Date;

import com.kp.cms.bo.admin.Department;

public class ProfessorPGIDetails {
	private int id;
	private String firstName;
	private Date dateOfBirth;
	private Department departmentId;
	private String txnStatus;
	private BigDecimal txnAmount;
	private String mobileNo;
	private String email;
	private String createdBy;
	private Date createdDate;
	private String applicantRefNo;
	private String txnRefNo;
	private String mode;

	private Date txnDate;
	private ProfessorPersonalData professorPersonalId;
	




	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Department getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Department departmentId) {
		this.departmentId = departmentId;
	}
	public String getTxnStatus() {
		return txnStatus;
	}
	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}
	public BigDecimal getTxnAmount() {
		return txnAmount;
	}
	public void setTxnAmount(BigDecimal txnAmount) {
		this.txnAmount = txnAmount;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getApplicantRefNo() {
		return applicantRefNo;
	}
	public void setApplicantRefNo(String applicantRefNo) {
		this.applicantRefNo = applicantRefNo;
	}
	public String getTxnRefNo() {
		return txnRefNo;
	}
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public Date getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}
	
	public ProfessorPersonalData getProfessorPersonalId() {
		return professorPersonalId;
	}
	public void setProfessorPersonalId(ProfessorPersonalData professorPersonalId) {
		this.professorPersonalId = professorPersonalId;
	}

	

}
