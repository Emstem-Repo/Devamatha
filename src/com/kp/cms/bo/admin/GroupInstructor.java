package com.kp.cms.bo.admin;

// Generated Jan 22, 2009 3:35:47 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * GroupInstructor generated by hbm2java
 */
public class GroupInstructor implements java.io.Serializable {

	private int id;
	private String createdBy;;
	private String modifiedBy;
	private Group group;
	private Date createdDate;
	private Date lastModifiedDate;

	private Integer employeeId;
	public GroupInstructor() {
	}

	public GroupInstructor(int id) {
		this.id = id;
	}

	public GroupInstructor(int id, String createdBy,
			String modifiedBy, Group group,Integer employeeId,
			 Date createdDate,
			Date lastModifiedDate) {
		this.id = id;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.group = group;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.employeeId = employeeId;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy)  {
		this.createdBy = createdBy;
	}

	public String getModifiedBy()  {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

}