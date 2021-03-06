package com.kp.cms.bo.admin;

// Generated Sep 22, 2009 4:21:49 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * EmpFunctionalArea generated by hbm2java
 */
public class EmpFunctionalArea implements java.io.Serializable {

	private int id;
	private String name;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<EmpOnlineResume> empOnlineResumes = new HashSet<EmpOnlineResume>(
			0);

	public EmpFunctionalArea() {
	}

	public EmpFunctionalArea(int id) {
		this.id = id;
	}

	public EmpFunctionalArea(int id, String name, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive, Set<EmpOnlineResume> empOnlineResumes) {
		this.id = id;
		this.name = name;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.empOnlineResumes = empOnlineResumes;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<EmpOnlineResume> getEmpOnlineResumes() {
		return this.empOnlineResumes;
	}

	public void setEmpOnlineResumes(Set<EmpOnlineResume> empOnlineResumes) {
		this.empOnlineResumes = empOnlineResumes;
	}

}
