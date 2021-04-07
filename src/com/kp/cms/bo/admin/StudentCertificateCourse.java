package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class StudentCertificateCourse implements Serializable{
	
	private int id;
	private Student student;
	private CertificateCourse certificateCourse;
	private Integer schemeNo;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isCancelled;
	private Boolean isOptional;
	private Subject subject;
	private Boolean isExtraCurricular;
	private CCGroup groups;
	private Date transactionDate;
	private String status;
	private String bankConfirmationId;
	private Boolean isPaymentFailed; 
	private BigDecimal feeAmount;
	private Boolean isOnline;
	
	public StudentCertificateCourse(){
		
	}
	
	
	public StudentCertificateCourse(int id, Student student,
			CertificateCourse certificateCourse, Integer schemeNo,
			String createdBy, String modifiedBy, Date createdDate,
			Date lastModifiedDate,Boolean isCancelled,CCGroup groups,Date transactionDate,String status,String bankConfirmationId,BigDecimal feeAmount,Boolean isOnline) {
		super();
		this.id = id;
		this.student = student;
		this.certificateCourse = certificateCourse;
		this.schemeNo = schemeNo;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isCancelled=isCancelled;
		this.groups=groups;
		this.transactionDate=transactionDate;
		this.status=status;
		this.bankConfirmationId=bankConfirmationId;
		this.feeAmount=feeAmount;
		this.isOnline=isOnline;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public CertificateCourse getCertificateCourse() {
		return certificateCourse;
	}
	public void setCertificateCourse(CertificateCourse certificateCourse) {
		this.certificateCourse = certificateCourse;
	}
	public Integer getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(Integer schemeNo) {
		this.schemeNo = schemeNo;
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


	public Boolean getIsCancelled() {
		return isCancelled;
	}


	public void setIsCancelled(Boolean isCancelled) {
		this.isCancelled = isCancelled;
	}


	public Boolean getIsOptional() {
		return isOptional;
	}


	public void setIsOptional(Boolean isOptional) {
		this.isOptional = isOptional;
	}


	public Subject getSubject() {
		return subject;
	}


	public void setSubject(Subject subject) {
		this.subject = subject;
	}


	public Boolean getIsExtraCurricular() {
		return isExtraCurricular;
	}


	public void setIsExtraCurricular(Boolean isExtraCurricular) {
		this.isExtraCurricular = isExtraCurricular;
	}


	public CCGroup getGroups() {
		return groups;
	}


	public void setGroups(CCGroup groups) {
		this.groups = groups;
	}


	public Date getTransactionDate() {
		return transactionDate;
	}


	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getBankConfirmationId() {
		return bankConfirmationId;
	}


	public void setBankConfirmationId(String bankConfirmationId) {
		this.bankConfirmationId = bankConfirmationId;
	}


	public Boolean getIsPaymentFailed() {
		return isPaymentFailed;
	}


	public void setIsPaymentFailed(Boolean isPaymentFailed) {
		this.isPaymentFailed = isPaymentFailed;
	}


	public BigDecimal getFeeAmount() {
		return feeAmount;
	}


	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}


	public Boolean getIsOnline() {
		return isOnline;
	}


	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}
	
	
}
