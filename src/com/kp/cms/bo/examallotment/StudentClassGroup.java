package com.kp.cms.bo.examallotment;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Student;

public class StudentClassGroup implements Serializable {

	private int id;
	private Student student;
	private GroupClasses groupClasses;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	
	public StudentClassGroup() {
	}


	public StudentClassGroup(int id, Student student,
			GroupClasses groupClasses, String createdBy, String modifiedBy,
			Date createdDate, Date lastModifiedDate, Boolean isActive) {
		super();
		this.id = id;
		this.student = student;
		this.groupClasses = groupClasses;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}






	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
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
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}






	public GroupClasses getGroupClasses() {
		return groupClasses;
	}






	public void setGroupClasses(GroupClasses groupClasses) {
		this.groupClasses = groupClasses;
	}
	
	
	
}
