package com.kp.cms.to.admin;

import java.util.Date;

import com.kp.cms.bo.admin.EmpImmigration;

public class EmpImmigrationTO {
	private String id;
	private String employeeId;
	private String passportNo;
	private String passportCountryId;
	private String passportCountryName;
	private String passportStatus;
	private String passportReviewStatus;
	private String passportIssueDate;
	private String passportExpiryDate;
	private String passportComments;
	private String visaNo;
	private String visaCountryId;
	private String visaCountryName;
	private String visaStatus;
	private String visaReviewStatus;
	private String visaIssueDate;
	private String visaExpiryDate;
	private String visaComments;
	
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private boolean isActive;
	private boolean originalPresent;
	private String dataPresent;
	private EmpImmigration originalimmigration;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	public String getPassportCountryId() {
		return passportCountryId;
	}
	public void setPassportCountryId(String passportCountryId) {
		this.passportCountryId = passportCountryId;
	}
	public String getPassportStatus() {
		return passportStatus;
	}
	public void setPassportStatus(String passportStatus) {
		this.passportStatus = passportStatus;
	}
	public String getPassportReviewStatus() {
		return passportReviewStatus;
	}
	public void setPassportReviewStatus(String passportReviewStatus) {
		this.passportReviewStatus = passportReviewStatus;
	}
	public String getPassportIssueDate() {
		return passportIssueDate;
	}
	public void setPassportIssueDate(String passportIssueDate) {
		this.passportIssueDate = passportIssueDate;
	}
	public String getPassportExpiryDate() {
		return passportExpiryDate;
	}
	public void setPassportExpiryDate(String passportExpiryDate) {
		this.passportExpiryDate = passportExpiryDate;
	}
	public String getPassportComments() {
		return passportComments;
	}
	public void setPassportComments(String passportComments) {
		this.passportComments = passportComments;
	}
	public String getVisaNo() {
		return visaNo;
	}
	public void setVisaNo(String visaNo) {
		this.visaNo = visaNo;
	}
	public String getVisaCountryId() {
		return visaCountryId;
	}
	public void setVisaCountryId(String visaCountryId) {
		this.visaCountryId = visaCountryId;
	}
	public String getVisaStatus() {
		return visaStatus;
	}
	public void setVisaStatus(String visaStatus) {
		this.visaStatus = visaStatus;
	}
	public String getVisaReviewStatus() {
		return visaReviewStatus;
	}
	public void setVisaReviewStatus(String visaReviewStatus) {
		this.visaReviewStatus = visaReviewStatus;
	}
	public String getVisaIssueDate() {
		return visaIssueDate;
	}
	public void setVisaIssueDate(String visaIssueDate) {
		this.visaIssueDate = visaIssueDate;
	}
	public String getVisaExpiryDate() {
		return visaExpiryDate;
	}
	public void setVisaExpiryDate(String visaExpiryDate) {
		this.visaExpiryDate = visaExpiryDate;
	}
	public String getVisaComments() {
		return visaComments;
	}
	public void setVisaComments(String visaComments) {
		this.visaComments = visaComments;
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
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public EmpImmigration getOriginalimmigration() {
		return originalimmigration;
	}
	public void setOriginalimmigration(EmpImmigration originalimmigration) {
		this.originalimmigration = originalimmigration;
	}
	public boolean isOriginalPresent() {
		return originalPresent;
	}
	public void setOriginalPresent(boolean originalPresent) {
		this.originalPresent = originalPresent;
	}
	public String getPassportCountryName() {
		return passportCountryName;
	}
	public void setPassportCountryName(String passportCountryName) {
		this.passportCountryName = passportCountryName;
	}
	public String getVisaCountryName() {
		return visaCountryName;
	}
	public void setVisaCountryName(String visaCountryName) {
		this.visaCountryName = visaCountryName;
	}
	public String getDataPresent() {
		return dataPresent;
	}
	public void setDataPresent(String dataPresent) {
		this.dataPresent = dataPresent;
	}
	
}
