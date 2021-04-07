package com.kp.cms.to.admin;

import java.util.Date;

import com.kp.cms.bo.admin.CertificateOnlineStudentRequest;

public class CertificateRequestMarksCardTO {
	
	private int id;
	private CertificateOnlineStudentRequest certificateRequestId;
	private String type;
	private String month;
	private String year;
	private String semester;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private int marksCardLength;
	private int certDetailsId;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public CertificateOnlineStudentRequest getCertificateRequestId() {
		return certificateRequestId;
	}
	public void setCertificateRequestId(
			CertificateOnlineStudentRequest certificateRequestId) {
		this.certificateRequestId = certificateRequestId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
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
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	
	public int getCertDetailsId() {
		return certDetailsId;
	}
	public void setCertDetailsId(int certDetailsId) {
		this.certDetailsId = certDetailsId;
	}
	public int getMarksCardLength() {
		return marksCardLength;
	}
	public void setMarksCardLength(int marksCardLength) {
		this.marksCardLength = marksCardLength;
	}
	
	
	
	

}
