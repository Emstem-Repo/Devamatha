package com.kp.cms.to.admin;

import java.util.List;

import com.kp.cms.bo.admin.CertificateDetails;
import com.kp.cms.bo.admin.Student;
public class CertificateRequestOnlineTO {
	
	private String id;
	private String certificateName;
	private String certificateId;
	private String studentName;
	private String className;
	private String registerNo;
	private Boolean isCompleted;
	private String appliedDate;
	private String marksCard;
	private boolean tempChecked;
	private boolean checked;
	private boolean tempPurposeChecked;
	private boolean purposeChecked;
	private String studentId;
	private List<Student> student;
	private boolean isRejected;
	private String rejectReason;
	private String rejectDate;
	private String completedDate;
	private String certificateStatus;
	private List<CertificateDetails> certificateDetails;
	private List<CertificateDetailsTo> certificateDetailsTo;
	private List<CertificateRequestMarksCardTO> marksCardTo;
	private List<CertificateDetailsTemplateTO> certificateTemplateTo;
	private List<CertificatePurposeTO> purposeTO;
	private String certificateDescription;
	private String studentRemarks;
	private String isReasonRequired;
	private String adminRemarks;
	private Boolean isIssued;
	private boolean tempIssuedChecked;
	private String issuedDate;
	private boolean tempCompletedChecked;
	private boolean isOnlinePayment;

	public List<Student> getStudent() {
		return student;
	}
	public void setStudent(List<Student> student) {
		this.student = student;
	}
	public List<CertificateDetails> getCertificateDetails() {
		return certificateDetails;
	}
	public void setCertificateDetails(List<CertificateDetails> certificateDetails) {
		this.certificateDetails = certificateDetails;
	}
	public List<CertificateRequestMarksCardTO> getMarksCardTo() {
		return marksCardTo;
	}
	public void setMarksCardTo(List<CertificateRequestMarksCardTO> marksCardTo) {
		this.marksCardTo = marksCardTo;
	}
	public String getCertificateName() {
		return certificateName;
	}
	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	
	public String getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(String appliedDate) {
		this.appliedDate = appliedDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMarksCard() {
		return marksCard;
	}
	public void setMarksCard(String marksCard) {
		this.marksCard = marksCard;
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
	
	public String getCertificateId() {
		return certificateId;
	}
	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public List<CertificateDetailsTo> getCertificateDetailsTo() {
		return certificateDetailsTo;
	}
	public void setCertificateDetailsTo(
			List<CertificateDetailsTo> certificateDetailsTo) {
		this.certificateDetailsTo = certificateDetailsTo;
	}
	public List<CertificatePurposeTO> getPurposeTO() {
		return purposeTO;
	}
	public void setPurposeTO(List<CertificatePurposeTO> purposeTO) {
		this.purposeTO = purposeTO;
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
	public boolean isRejected() {
		return isRejected;
	}
	public void setRejected(boolean isRejected) {
		this.isRejected = isRejected;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	public String getRejectDate() {
		return rejectDate;
	}
	public void setRejectDate(String rejectDate) {
		this.rejectDate = rejectDate;
	}
	public String getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}
	public List<CertificateDetailsTemplateTO> getCertificateTemplateTo() {
		return certificateTemplateTo;
	}
	public void setCertificateTemplateTo(
			List<CertificateDetailsTemplateTO> certificateTemplateTo) {
		this.certificateTemplateTo = certificateTemplateTo;
	}
	public String getCertificateStatus() {
		return certificateStatus;
	}
	public void setCertificateStatus(String certificateStatus) {
		this.certificateStatus = certificateStatus;
	}
	public String getCertificateDescription() {
		return certificateDescription;
	}
	public void setCertificateDescription(String certificateDescription) {
		this.certificateDescription = certificateDescription;
	}
	public String getStudentRemarks() {
		return studentRemarks;
	}
	public void setStudentRemarks(String studentRemarks) {
		this.studentRemarks = studentRemarks;
	}
	
	public String getIssuedDate() {
		return issuedDate;
	}
	public void setIssuedDate(String issuedDate) {
		this.issuedDate = issuedDate;
	}
	public boolean isTempIssuedChecked() {
		return tempIssuedChecked;
	}
	public void setTempIssuedChecked(boolean tempIssuedChecked) {
		this.tempIssuedChecked = tempIssuedChecked;
	}
	public boolean isTempCompletedChecked() {
		return tempCompletedChecked;
	}
	public void setTempCompletedChecked(boolean tempCompletedChecked) {
		this.tempCompletedChecked = tempCompletedChecked;
	}
	public Boolean getIsCompleted() {
		return isCompleted;
	}
	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	public Boolean getIsIssued() {
		return isIssued;
	}
	public void setIsIssued(Boolean isIssued) {
		this.isIssued = isIssued;
	}
	public boolean isOnlinePayment() {
		return isOnlinePayment;
	}
	public void setOnlinePayment(boolean isOnlinePayment) {
		this.isOnlinePayment = isOnlinePayment;
	}
	public String getIsReasonRequired() {
		return isReasonRequired;
	}
	public void setIsReasonRequired(String isReasonRequired) {
		this.isReasonRequired = isReasonRequired;
	}
	public String getAdminRemarks() {
		return adminRemarks;
	}
	public void setAdminRemarks(String adminRemarks) {
		this.adminRemarks = adminRemarks;
	}
}
