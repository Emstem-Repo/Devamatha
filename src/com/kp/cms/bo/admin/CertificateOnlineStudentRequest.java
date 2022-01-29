package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.CertificateDetails;
import com.kp.cms.bo.admin.CertificateDetailsRoles;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.employee.EmpLoan;

public class CertificateOnlineStudentRequest implements Serializable{
		
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private int id;
		private CertificateDetails certificateDetailsId;
		private Student studentId;
		private Date appliedDate;
		private Boolean isCompleted;
		private Date completedDate;
		private String createdBy;
		private Date createdDate;
		private String modifiedBy;
		private Date lastModifiedDate;
		private Boolean isActive;
		private String rejectReason;
		private Date rejectDate;
		private Boolean isRejected;
		private String adminRemarks;
		private String studentRemarks;
		private Date issuedDate;
		private Boolean isIssued;
		private Set<CertificateRequestMarksCardDetails> marksCardReq = new HashSet<CertificateRequestMarksCardDetails>(0);
		private Set<CertificateRequestPurposeDetails> certReqPurpose = new HashSet<CertificateRequestPurposeDetails>(0);
		
		
		public CertificateOnlineStudentRequest() {
			super();
			
		}
		public CertificateOnlineStudentRequest(int id,
				CertificateDetails certificateDetailsId,
				Student studentId,
				Boolean isCompleted, Date completedDate, String createdBy,
				Date createdDate, String modifiedBy, Date lastModifiedDate,
				Boolean isActive, Set<CertificateRequestMarksCardDetails> marksCardReq, 
				Set<CertificateRequestPurposeDetails> certReqPurpose, String rejectReason,
				Date rejectDate, Boolean isRejected,String adminRemarks, String studentRemarks,
				Date issuedDate, Boolean isIssued) {
			super();
			this.id = id;
			this.certificateDetailsId = certificateDetailsId;
			this.studentId = studentId;
			this.isCompleted = isCompleted;
			this.completedDate = completedDate;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.modifiedBy = modifiedBy;
			this.lastModifiedDate = lastModifiedDate;
			this.isActive = isActive;
			this.marksCardReq= marksCardReq;
			this.certReqPurpose=certReqPurpose;
			this.rejectReason=rejectReason;
			this.rejectDate=rejectDate;
			this.isRejected=isRejected;
			this.adminRemarks=adminRemarks;
			this.studentRemarks=studentRemarks;
			this.isIssued=isIssued;
			this.issuedDate=issuedDate;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public CertificateDetails getCertificateDetailsId() {
			return certificateDetailsId;
		}
		public void setCertificateDetailsId(CertificateDetails certificateDetailsId) {
			this.certificateDetailsId = certificateDetailsId;
		}
		public Boolean getIsCompleted() {
			return isCompleted;
		}
		public void setIsCompleted(Boolean isCompleted) {
			this.isCompleted = isCompleted;
		}
		public Date getCompletedDate() {
			return completedDate;
		}
		public void setCompletedDate(Date completedDate) {
			this.completedDate = completedDate;
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
		public Student getStudentId() {
			return studentId;
		}
		public void setStudentId(Student studentId) {
			this.studentId = studentId;
		}
		public Date getAppliedDate() {
			return appliedDate;
		}
		public void setAppliedDate(Date appliedDate) {
			this.appliedDate = appliedDate;
		}
		public Set<CertificateRequestMarksCardDetails> getMarksCardReq() {
			return marksCardReq;
		}
		public void setMarksCardReq(Set<CertificateRequestMarksCardDetails> marksCardReq) {
			this.marksCardReq = marksCardReq;
		}
		public Set<CertificateRequestPurposeDetails> getCertReqPurpose() {
			return certReqPurpose;
		}
		public void setCertReqPurpose(
				Set<CertificateRequestPurposeDetails> certReqPurpose) {
			this.certReqPurpose = certReqPurpose;
		}
		public String getRejectReason() {
			return rejectReason;
		}
		public void setRejectReason(String rejectReason) {
			this.rejectReason = rejectReason;
		}
		public Date getRejectDate() {
			return rejectDate;
		}
		public void setRejectDate(Date rejectDate) {
			this.rejectDate = rejectDate;
		}
		public Boolean getIsRejected() {
			return isRejected;
		}
		public void setIsRejected(Boolean isRejected) {
			this.isRejected = isRejected;
		}
		public String getAdminRemarks() {
			return adminRemarks;
		}
		public void setAdminRemarks(String adminRemarks) {
			this.adminRemarks = adminRemarks;
		}
		public String getStudentRemarks() {
			return studentRemarks;
		}
		public void setStudentRemarks(String studentRemarks) {
			this.studentRemarks = studentRemarks;
		}
		public Date getIssuedDate() {
			return issuedDate;
		}
		public void setIssuedDate(Date issuedDate) {
			this.issuedDate = issuedDate;
		}
		public Boolean getIsIssued() {
			return isIssued;
		}
		public void setIsIssued(Boolean isIssued) {
			this.isIssued = isIssued;
		}
		
		
		
		
}
