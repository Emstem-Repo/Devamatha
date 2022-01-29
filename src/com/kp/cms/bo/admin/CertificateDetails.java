package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CertificateDetails implements Serializable{
	
	private int id;
	private String certificateName;
	private BigDecimal fees;
	private Boolean marksCard;
	private String createdby;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String description;
	private Boolean isReasonRequired;
	private Boolean isIdCard;

	private Set<AssignCertificateRequestPurpose> assignedCertPurpose = new HashSet<AssignCertificateRequestPurpose>();
	private Set<CertificateDetailsTemplate> certTemplate = new HashSet<CertificateDetailsTemplate>();
	
	
	public CertificateDetails()
	{
		
	}
	public CertificateDetails(int id,String certificateName,BigDecimal fees,Boolean marksCard,String createdby,Date createdDate,
			                  String modifiedBy,Date lastModifiedDate,Boolean isActive,String description,
			                  Boolean isReasonRequired,Boolean isIdCard){
		this.id=id;
		this.certificateName=certificateName;
		this.fees=fees;
		this.marksCard=marksCard;
		this.createdby=createdby;
		this.createdDate=createdDate;
		this.modifiedBy=modifiedBy;
		this.lastModifiedDate=lastModifiedDate;
		this.isActive=isActive;
		this.description=description;
		this.isReasonRequired=isReasonRequired;
		this.isIdCard=isIdCard;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCertificateName() {
		return certificateName;
	}
	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}
	public BigDecimal getFees() {
		return fees;
	}
	public void setFees(BigDecimal fees) {
		this.fees = fees;
	}
	public Boolean getMarksCard() {
		return marksCard;
	}
	public void setMarksCard(Boolean marksCard) {
		this.marksCard = marksCard;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<AssignCertificateRequestPurpose> getAssignedCertPurpose() {
		return assignedCertPurpose;
	}
	public void setAssignedCertPurpose(Set<AssignCertificateRequestPurpose> assignedCertPurpose) {
		this.assignedCertPurpose = assignedCertPurpose;
	}
	public Set<CertificateDetailsTemplate> getCertTemplate() {
		return certTemplate;
	}
	public void setCertTemplate(Set<CertificateDetailsTemplate> certTemplate) {
		this.certTemplate = certTemplate;
	}
	
	public Boolean getIsIdCard() {
		return isIdCard;
	}
	public void setIsIdCard(Boolean isIdCard) {
		this.isIdCard = isIdCard;
	}
	public Boolean getIsReasonRequired() {
		return isReasonRequired;
	}
	public void setIsReasonRequired(Boolean isReasonRequired) {
		this.isReasonRequired = isReasonRequired;
	}
	
	
}
