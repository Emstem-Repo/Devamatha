package com.kp.cms.bo.admin;

import java.util.Date;

public class CertificateRequestMarksCardDetails {
	
	
	
	private static final long serialVersionUID = 1L;
	private int id;
	private CertificateOnlineStudentRequest certificateRequestId;
	private String type;
	private String month;
	private int year;
	private int semester;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	
		
	public CertificateRequestMarksCardDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CertificateRequestMarksCardDetails(int id,
			CertificateOnlineStudentRequest certificateRequestId, String type,
			String month, int year, int semester, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate) {
		super();
		this.id = id;
		this.certificateRequestId = certificateRequestId;
		this.type = type;
		this.month = month;
		this.year = year;
		this.semester = semester;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
	}
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
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
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

}
