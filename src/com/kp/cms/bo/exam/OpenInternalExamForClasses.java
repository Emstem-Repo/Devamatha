package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Classes;

public class OpenInternalExamForClasses implements Serializable {
	
	private int id;
	private OpenInternalMark openExam;
	private Classes classes;
	private Boolean isActive;
	private Date createdDate;
	private Date lastModifiedDate;
	private String createdBy;
	private String modifiedBy;
	
	
	public OpenInternalExamForClasses() {
		super();
		// TODO Auto-generated constructor stub
	}


	public OpenInternalExamForClasses(int id, OpenInternalMark openExam,
			Classes classes, Boolean isActive, Date createdDate,
			Date lastModifiedDate, String createdBy, String modifiedBy) {
		this.id = id;
		this.openExam = openExam;
		this.classes = classes;
		this.isActive = isActive;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
	}



	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public OpenInternalMark getOpenExam() {
		return openExam;
	}


	public void setOpenExam(OpenInternalMark openExam) {
		this.openExam = openExam;
	}


	public Classes getClasses() {
		return classes;
	}


	public void setClasses(Classes classes) {
		this.classes = classes;
	}


	public Boolean getIsActive() {
		return isActive;
	}


	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	
}
