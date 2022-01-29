package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class TeacherClass implements Serializable{
private int id;

private Users teacherId;
private ClassSchemewise classId;
private String createdBy;
private Date createdDate;
private String modifiedBy;
private Date lastModifiedDate;
private Boolean isActive;
private String teacherType;
private String academicYear;

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

public Users getTeacherId() {
	return teacherId;
}
public void setTeacherId(Users teacherId) {
	this.teacherId = teacherId;
}

public ClassSchemewise getClassId() {
	return classId;
}
public void setClassId(ClassSchemewise classId) {
	this.classId = classId;
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
public Boolean getIsActive() {
	return isActive;
}
public void setIsActive(Boolean isActive) {
	this.isActive = isActive;
}
public TeacherClass(int id,Users teacherId, ClassSchemewise classId,
		String createdBy, Date createdDate, String modifiedBy,
		Date lastModifiedDate, Boolean isActive, String teacherType, String academicYear) {
	super();
	this.id = id;

	this.teacherId = teacherId;
	this.classId = classId;
	this.createdBy = createdBy;
	this.createdDate = createdDate;
	this.modifiedBy = modifiedBy;
	this.lastModifiedDate = lastModifiedDate;
	this.isActive = isActive;
	this.teacherType = teacherType;
	this.academicYear= academicYear;
}
public TeacherClass(){
	
}
public String getTeacherType() {
	return teacherType;
}
public void setTeacherType(String teacherType) {
	this.teacherType = teacherType;
}
public String getAcademicYear() {
	return academicYear;
}
public void setAcademicYear(String academicYear) {
	this.academicYear = academicYear;
}


}
