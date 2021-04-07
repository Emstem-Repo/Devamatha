package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class StudentSpecializationPrefered implements Serializable {

	private int id;
	private AdmAppln admAppln;
	private String specializationPrefered;
	private Boolean backLogs;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	
	public StudentSpecializationPrefered(int id, AdmAppln admAppln,
			String specializationPrefered, Boolean backLogs, String createdBy,
			String modifiedBy, Date createdDate, Date lastModifiedDate) {
		super();
		this.id = id;
		this.admAppln = admAppln;
		this.specializationPrefered = specializationPrefered;
		this.backLogs = backLogs;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}
	public StudentSpecializationPrefered() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public AdmAppln getAdmAppln() {
		return admAppln;
	}
	public void setAdmAppln(AdmAppln admAppln) {
		this.admAppln = admAppln;
	}
	public String getSpecializationPrefered() {
		return specializationPrefered;
	}
	public void setSpecializationPrefered(String specializationPrefered) {
		this.specializationPrefered = specializationPrefered;
	}
	public Boolean getBackLogs() {
		return backLogs;
	}
	public void setBackLogs(Boolean backLogs) {
		this.backLogs = backLogs;
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
