package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class ApplnDocDetails implements Serializable {
	
	private int id;
	private String semesterNo;
	private Boolean isHardCopySubmited;
	private ApplnDoc applnDoc;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;

	public ApplnDocDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ApplnDocDetails(int id, String semesterNo,
			Boolean isHardCopySubmited, ApplnDoc applnDoc, String createdBy,
			String modifiedBy, Date createdDate, Date lastModifiedDate) {
		super();
		this.id = id;
		this.semesterNo = semesterNo;
		this.isHardCopySubmited = isHardCopySubmited;
		this.applnDoc = applnDoc;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSemesterNo() {
		return semesterNo;
	}
	public void setSemesterNo(String semesterNo) {
		this.semesterNo = semesterNo;
	}
	public Boolean getIsHardCopySubmited() {
		return isHardCopySubmited;
	}
	public void setIsHardCopySubmited(Boolean isHardCopySubmited) {
		this.isHardCopySubmited = isHardCopySubmited;
	}
	public ApplnDoc getApplnDoc() {
		return applnDoc;
	}
	public void setApplnDoc(ApplnDoc applnDoc) {
		this.applnDoc = applnDoc;
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
}