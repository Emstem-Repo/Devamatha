package com.kp.cms.bo.examallotment;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.CourseTermSubjectGroup;
import com.kp.cms.bo.admin.Student;

public class GroupClasses implements Serializable {

	private int id;
	private Classes classId;
	private ClassGroup classGroup;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<StudentClassGroup> studentClassGroupSet = new HashSet<StudentClassGroup>(0);
	
	
	public GroupClasses() {
	}

	

	public GroupClasses(int id, Classes classId, ClassGroup classGroup,
			String createdBy, String modifiedBy, Date createdDate,
			Date lastModifiedDate, Boolean isActive,
			Set<StudentClassGroup> studentClassGroupSet) {
		super();
		this.id = id;
		this.classId = classId;
		this.classGroup = classGroup;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.studentClassGroupSet = studentClassGroupSet;
	}



	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Classes getClassId() {
		return classId;
	}


	public void setClassId(Classes classId) {
		this.classId = classId;
	}


	public ClassGroup getClassGroup() {
		return classGroup;
	}


	public void setClassGroup(ClassGroup classGroup) {
		this.classGroup = classGroup;
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


	public Set<StudentClassGroup> getStudentClassGroupSet() {
		return studentClassGroupSet;
	}


	public void setStudentClassGroupSet(Set<StudentClassGroup> studentClassGroupSet) {
		this.studentClassGroupSet = studentClassGroupSet;
	}
    
	
	
}
