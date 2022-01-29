package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class AssignPeersGroups implements Serializable{
	private int id;
	private Department department;
	private Users users;
	private PeersEvaluationGroups peersEvaluationGroups;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	public AssignPeersGroups() {
		super();
	}
	public AssignPeersGroups(int id, Department department, Users users,
			PeersEvaluationGroups peersEvaluationGroups, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive) {
		super();
		this.id = id;
		this.department = department;
		this.users = users;
		this.setPeersEvaluationGroups(peersEvaluationGroups);
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
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
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public void setPeersEvaluationGroups(PeersEvaluationGroups peersEvaluationGroups) {
		this.peersEvaluationGroups = peersEvaluationGroups;
	}
	public PeersEvaluationGroups getPeersEvaluationGroups() {
		return peersEvaluationGroups;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
}
