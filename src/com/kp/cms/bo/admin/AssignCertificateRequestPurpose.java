package com.kp.cms.bo.admin;
import java.io.Serializable;
import java.util.Date;

public class AssignCertificateRequestPurpose implements Serializable{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private int id;
		private CertificateRequestPurpose certificatePurposeId;
		private CertificateDetails certificateId;
		private String createdBy;
		private Date createdDate;
		private String modifiedBy;
		private Date lastModifiedDate;
		private Boolean isActive;
		
		public AssignCertificateRequestPurpose()
		{
			
		}
		public AssignCertificateRequestPurpose(int id,CertificateRequestPurpose certificatePurposeId,CertificateDetails certificateId,String createdBy,Date createdDate,
				                  String modifiedBy,Date lastModifiedDate,Boolean isActive){
			this.id=id;
			this.certificatePurposeId=certificatePurposeId;
			this.certificateId=certificateId;
			this.createdBy=createdBy;
			this.createdDate=createdDate;
			this.modifiedBy=modifiedBy;
			this.lastModifiedDate=lastModifiedDate;
			this.isActive=isActive;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		
		public CertificateDetails getCertificateId() {
			return certificateId;
		}
		public void setCertificateId(CertificateDetails certificateId) {
			this.certificateId = certificateId;
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
		
		public String getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		public CertificateRequestPurpose getCertificatePurposeId() {
			return certificatePurposeId;
		}
		public void setCertificatePurposeId(
				CertificateRequestPurpose certificatePurposeId) {
			this.certificatePurposeId = certificatePurposeId;
		}
		
	}

	
	
	
	
	


