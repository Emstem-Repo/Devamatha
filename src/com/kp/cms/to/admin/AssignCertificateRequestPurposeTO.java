package com.kp.cms.to.admin;

import java.util.Date;

import com.kp.cms.bo.admin.CertificateDetails;
import com.kp.cms.bo.admin.CertificateRequestPurpose;

public class AssignCertificateRequestPurposeTO {
	
	private int id;
	private CertificateRequestPurpose certificatePurposeId;
	private CertificateDetails certificateId;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private String purposeName;
	private boolean tempPurposeChecked;
	private boolean purposeChecked;
	private Boolean isActive;
	private String assignChecked;
	//private String assignTempChecked;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public CertificateRequestPurpose getCertificatePurposeId() {
		return certificatePurposeId;
	}
	public void setCertificatePurposeId(
			CertificateRequestPurpose certificatePurposeId) {
		this.certificatePurposeId = certificatePurposeId;
	}
	public CertificateDetails getCertificateId() {
		return certificateId;
	}
	public void setCertificateId(CertificateDetails certificateId) {
		this.certificateId = certificateId;
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
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public boolean isTempPurposeChecked() {
		return tempPurposeChecked;
	}
	public void setTempPurposeChecked(boolean tempPurposeChecked) {
		this.tempPurposeChecked = tempPurposeChecked;
	}
	public boolean isPurposeChecked() {
		return purposeChecked;
	}
	public void setPurposeChecked(boolean purposeChecked) {
		this.purposeChecked = purposeChecked;
	}
	public String getPurposeName() {
		return purposeName;
	}
	public void setPurposeName(String purposeName) {
		this.purposeName = purposeName;
	}
	/*public String getAssignTempChecked() {
		return assignTempChecked;
	}
	public void setAssignTempChecked(String assignTempChecked) {
		this.assignTempChecked = assignTempChecked;
	}
	public String getAssignChecked() {
		return assignChecked;
	}
	public void setAssignChecked(String assignChecked) {
		this.assignChecked = assignChecked;
	}*/
	public String getAssignChecked() {
		return assignChecked;
	}
	public void setAssignChecked(String assignChecked) {
		this.assignChecked = assignChecked;
	}
	

}
