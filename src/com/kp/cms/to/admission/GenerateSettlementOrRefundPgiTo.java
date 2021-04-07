package com.kp.cms.to.admission;

import java.math.BigDecimal;
import java.util.Date;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Course;

public class GenerateSettlementOrRefundPgiTo {
	private int id;
	private String candidateRefNo;
	private String candidateName;
	private Date candidateDob;
	private String applnNo;
	private Course course;
	private String txnStatus;
	private BigDecimal txnAmount;
	private Date txnDate;
	private String txnRefNo;
	private String bankId;
	private String bankRefNo;
	private String errorStatus;
	private String errorDesc;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private AdmAppln admAppln;
	private String mobileNo1;
	private String mobileNo2;
	private String email;
	private Boolean refundGenerated;
	private Boolean settlementGenerated;
	
	
	public Boolean getSettlementGenerated() {
		return settlementGenerated;
	}
	public void setSettlementGenerated(Boolean settlementGenerated) {
		this.settlementGenerated = settlementGenerated;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCandidateRefNo() {
		return candidateRefNo;
	}
	public void setCandidateRefNo(String candidateRefNo) {
		this.candidateRefNo = candidateRefNo;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public Date getCandidateDob() {
		return candidateDob;
	}
	public void setCandidateDob(Date candidateDob) {
		this.candidateDob = candidateDob;
	}
	public String getApplnNo() {
		return applnNo;
	}
	public void setApplnNo(String applnNo) {
		this.applnNo = applnNo;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
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
	public Date getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}
	public String getTxnRefNo() {
		return txnRefNo;
	}
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getBankRefNo() {
		return bankRefNo;
	}
	public void setBankRefNo(String bankRefNo) {
		this.bankRefNo = bankRefNo;
	}
	public String getErrorStatus() {
		return errorStatus;
	}
	public void setErrorStatus(String errorStatus) {
		this.errorStatus = errorStatus;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public AdmAppln getAdmAppln() {
		return admAppln;
	}
	public void setAdmAppln(AdmAppln admAppln) {
		this.admAppln = admAppln;
	}
	public String getMobileNo1() {
		return mobileNo1;
	}
	public void setMobileNo1(String mobileNo1) {
		this.mobileNo1 = mobileNo1;
	}
	public String getMobileNo2() {
		return mobileNo2;
	}
	public void setMobileNo2(String mobileNo2) {
		this.mobileNo2 = mobileNo2;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getRefundGenerated() {
		return refundGenerated;
	}
	public void setRefundGenerated(Boolean refundGenerated) {
		this.refundGenerated = refundGenerated;
	}
	
}
