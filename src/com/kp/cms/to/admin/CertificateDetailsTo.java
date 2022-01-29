package com.kp.cms.to.admin;

import java.util.List;

import com.kp.cms.bo.admin.CertificateDetails;
import com.kp.cms.bo.admin.Roles;

public class CertificateDetailsTo {
	private int id;
	private String certificateName;
	private String fees;
	private String originalFees;
	private String marksCard;
	private String certificateRolesId;
	private String certificateId;
	private boolean tempChecked;
	private boolean checked;
	private List<CertificateRequestMarksCardTO> marksCardTo;
	private List<AssignCertificateRequestPurposeTO> assignPurposeTo;
	private List<CertificateDetailsTemplateTO> certTemplateAssignedTo;
	private String description;
	private String isReasonRequired;
	private String studentRemarks;
	private String adminRemarks;
	private String isIdCard;
	private String purposeExist;
	private String purposeOrRemarksExist;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCertificateName() {
		return certificateName;
	}
	public String getFees() {
		return fees;
	}
	public String getMarksCard() {
		return marksCard;
	}
	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}
	public void setFees(String fees) {
		this.fees = fees;
	}
	public void setMarksCard(String marksCard) {
		this.marksCard = marksCard;
	}
	public String getCertificateRolesId() {
		return certificateRolesId;
	}
	public void setCertificateRolesId(String certificateRolesId) {
		this.certificateRolesId = certificateRolesId;
	}
	public String getCertificateId() {
		return certificateId;
	}
	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId;
	}
	public boolean isTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(boolean tempChecked) {
		this.tempChecked = tempChecked;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getOriginalFees() {
		return originalFees;
	}
	public void setOriginalFees(String originalFees) {
		this.originalFees = originalFees;
	}
	public List<CertificateRequestMarksCardTO> getMarksCardTo() {
		return marksCardTo;
	}
	public void setMarksCardTo(List<CertificateRequestMarksCardTO> marksCardTo) {
		this.marksCardTo = marksCardTo;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<AssignCertificateRequestPurposeTO> getAssignPurposeTo() {
		return assignPurposeTo;
	}
	public void setAssignPurposeTo(
			List<AssignCertificateRequestPurposeTO> assignPurposeTo) {
		this.assignPurposeTo = assignPurposeTo;
	}
	public List<CertificateDetailsTemplateTO> getCertTemplateAssignedTo() {
		return certTemplateAssignedTo;
	}
	public void setCertTemplateAssignedTo(
			List<CertificateDetailsTemplateTO> certTemplateAssignedTo) {
		this.certTemplateAssignedTo = certTemplateAssignedTo;
	}
	
	
	public String getStudentRemarks() {
		return studentRemarks;
	}
	public void setStudentRemarks(String studentRemarks) {
		this.studentRemarks = studentRemarks;
	}
	public String getAdminRemarks() {
		return adminRemarks;
	}
	public void setAdminRemarks(String adminRemarks) {
		this.adminRemarks = adminRemarks;
	}
	public String getIsIdCard() {
		return isIdCard;
	}
	public void setIsIdCard(String isIdCard) {
		this.isIdCard = isIdCard;
	}
	public String getIsReasonRequired() {
		return isReasonRequired;
	}
	public void setIsReasonRequired(String isReasonRequired) {
		this.isReasonRequired = isReasonRequired;
	}
	public String getPurposeExist() {
		return purposeExist;
	}
	public void setPurposeExist(String purposeExist) {
		this.purposeExist = purposeExist;
	}
	public String getPurposeOrRemarksExist() {
		return purposeOrRemarksExist;
	}
	public void setPurposeOrRemarksExist(String purposeOrRemarksExist) {
		this.purposeOrRemarksExist = purposeOrRemarksExist;
	}
	
	
}
