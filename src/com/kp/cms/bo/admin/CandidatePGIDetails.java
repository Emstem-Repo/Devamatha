package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CandidatePGIDetails implements Serializable{

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
	private ResidentCategory residentCategory;
	private Integer mailCount; 
	private Boolean settlementGenerated;
	
	private String mihpayId;
	private String pgType;
	private String unmappedStatus;
	private String mode;
	private BigDecimal additionalCharges;
	
	//new for Admission
	private StudentOnlineApplication uniqueId;
	private String paymentEmail;
	
	private String authzCode;
	private String responseCode;
	public CandidatePGIDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public CandidatePGIDetails(int id, String candidateRefNo,
			String candidateName, Date candidateDob, String applnNo,
			Course course, String txnStatus, BigDecimal txnAmount,
			Date txnDate, String txnRefNo, String bankId, String bankRefNo,
			String errorStatus, String errorDesc, String createdBy,
			String modifiedBy, Date createdDate, Date lastModifiedDate,
			AdmAppln admAppln, String mobileNo1, String mobileNo2,
			String email, Boolean refundGenerated,
			ResidentCategory residentCategory, Integer mailCount,
			Boolean settlementGenerated, String mihpayId, String pgType,
			String unmappedStatus, String mode, BigDecimal additionalCharges,
			StudentOnlineApplication uniqueId, String paymentEmail) {
		super();
		this.id = id;
		this.candidateRefNo = candidateRefNo;
		this.candidateName = candidateName;
		this.candidateDob = candidateDob;
		this.applnNo = applnNo;
		this.course = course;
		this.txnStatus = txnStatus;
		this.txnAmount = txnAmount;
		this.txnDate = txnDate;
		this.txnRefNo = txnRefNo;
		this.bankId = bankId;
		this.bankRefNo = bankRefNo;
		this.errorStatus = errorStatus;
		this.errorDesc = errorDesc;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.admAppln = admAppln;
		this.mobileNo1 = mobileNo1;
		this.mobileNo2 = mobileNo2;
		this.email = email;
		this.refundGenerated = refundGenerated;
		this.residentCategory = residentCategory;
		this.mailCount = mailCount;
		this.settlementGenerated = settlementGenerated;
		this.mihpayId = mihpayId;
		this.pgType = pgType;
		this.unmappedStatus = unmappedStatus;
		this.mode = mode;
		this.additionalCharges = additionalCharges;
		this.uniqueId = uniqueId;
		this.paymentEmail = paymentEmail;
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
	public ResidentCategory getResidentCategory() {
		return residentCategory;
	}
	public void setResidentCategory(ResidentCategory residentCategory) {
		this.residentCategory = residentCategory;
	}
	public Integer getMailCount() {
		return mailCount;
	}
	public void setMailCount(Integer mailCount) {
		this.mailCount = mailCount;
	}
	public Boolean getSettlementGenerated() {
		return settlementGenerated;
	}
	public void setSettlementGenerated(Boolean settlementGenerated) {
		this.settlementGenerated = settlementGenerated;
	}
	public String getMihpayId() {
		return mihpayId;
	}
	public void setMihpayId(String mihpayId) {
		this.mihpayId = mihpayId;
	}
	public String getPgType() {
		return pgType;
	}
	public void setPgType(String pgType) {
		this.pgType = pgType;
	}
	public String getUnmappedStatus() {
		return unmappedStatus;
	}
	public void setUnmappedStatus(String unmappedStatus) {
		this.unmappedStatus = unmappedStatus;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public BigDecimal getAdditionalCharges() {
		return additionalCharges;
	}
	public void setAdditionalCharges(BigDecimal additionalCharges) {
		this.additionalCharges = additionalCharges;
	}

	public StudentOnlineApplication getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(StudentOnlineApplication uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	public String getPaymentEmail() {
		return paymentEmail;
	}
	public void setPaymentEmail(String paymentEmail) {
		this.paymentEmail = paymentEmail;
	}

	public String getAuthzCode() {
		return authzCode;
	}

	public void setAuthzCode(String authzCode) {
		this.authzCode = authzCode;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	
	
}
