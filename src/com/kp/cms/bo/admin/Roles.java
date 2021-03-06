package com.kp.cms.bo.admin;

// Generated Mar 9, 2009 5:02:52 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Roles generated by hbm2java
 */
public class Roles implements java.io.Serializable {

	private int id;
	private String name;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<StudentLogin> studentLogins = new HashSet<StudentLogin>(0);
	private Set<Users> userses = new HashSet<Users>(0);
	private Set<AccessPrivileges> accessPrivilegeses = new HashSet<AccessPrivileges>(
			0);

	public Roles() {
	}

	public Roles(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Roles(int id, String name, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive,
			Set<StudentLogin> studentLogins, Set<Users> userses,
			Set<AccessPrivileges> accessPrivilegeses) {
		this.id = id;
		this.name = name;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.studentLogins = studentLogins;
		this.userses = userses;
		this.accessPrivilegeses = accessPrivilegeses;
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

	public Set<Users> getUserses() {
		return this.userses;
	}

	public void setUserses(Set<Users> userses) {
		this.userses = userses;
	}

	public Set<AccessPrivileges> getAccessPrivilegeses() {
		return this.accessPrivilegeses;
	}

	public void setAccessPrivilegeses(Set<AccessPrivileges> accessPrivilegeses) {
		this.accessPrivilegeses = accessPrivilegeses;
	}

	public Set<StudentLogin> getStudentLogins() {
		return studentLogins;
	}
	
	public void setStudentLogins(Set<StudentLogin> studentLogins) {
		this.studentLogins = studentLogins;
	}
}
