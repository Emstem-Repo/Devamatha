package com.kp.cms.to.attendance;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class AttendanceTypeTO implements Serializable{
	
	private int id;
	private String defaultValue;
	private String createdBy;
	private String modifiedBy;
	private String attendanceTypeName;
	private String isActive;
	private String createdDate;
	private String lastModifiedDate ;
	private List<AttendanceTypeMandatoryTO>attendanceTypeMandatoryTOList;
	private int conductedClasses;
	private int classesPresent;
	private int classesAbsent;
	private float percentage;
	private String attendanceID;
	private String attendanceTypeID;
	private String studentId;
	private String subjectId;
	private boolean flag;
	private String activityID;
	private int listCount;
	private Boolean isAdditionalSubject;
	private String checked;
	private String conductedClassesHours;
	private String classesPresentHours;
	private String classesAbsentHours;
	private String subjectHrspercentage;
	
	
	
	public Boolean getIsAdditionalSubject() {
		return isAdditionalSubject;
	}
	public void setIsAdditionalSubject(Boolean isAdditionalSubject) {
		this.isAdditionalSubject = isAdditionalSubject;
	}
	public List<AttendanceTypeMandatoryTO> getAttendanceTypeMandatoryTOList() {
		return attendanceTypeMandatoryTOList;
	}
	public void setAttendanceTypeMandatoryTOList(
			List<AttendanceTypeMandatoryTO> attendanceTypeMandatoryTOList) {
		this.attendanceTypeMandatoryTOList = attendanceTypeMandatoryTOList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getAttendanceTypeName() {
		return attendanceTypeName;
	}
	public void setAttendanceTypeName(String attendanceTypeName) {
		this.attendanceTypeName = attendanceTypeName;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public int getConductedClasses() {
		return conductedClasses;
	}
	public int getClassesPresent() {
		return classesPresent;
	}
	public int getClassesAbsent() {
		return classesAbsent;
	}
	public float getPercentage() {
		return percentage;
	}
	public void setConductedClasses(int conductedClasses) {
		this.conductedClasses = conductedClasses;
	}
	public void setClassesPresent(int classesPresent) {
		this.classesPresent = classesPresent;
	}
	public void setClassesAbsent(int classesAbsent) {
		this.classesAbsent = classesAbsent;
	}
	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}
	public String getAttendanceID() {
		return attendanceID;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setAttendanceID(String attendanceID) {
		this.attendanceID = attendanceID;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getAttendanceTypeID() {
		return attendanceTypeID;
	}
	public void setAttendanceTypeID(String attendanceTypeID) {
		this.attendanceTypeID = attendanceTypeID;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public String getActivityID() {
		return activityID;
	}
	public void setActivityID(String activityID) {
		this.activityID = activityID;
	}
	
	public int getListCount() {
		return listCount;
	}
	public void setListCount(int listCount) {
		this.listCount = listCount;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getConductedClassesHours() {
		return conductedClassesHours;
	}
	public void setConductedClassesHours(String conductedClassesHours) {
		this.conductedClassesHours = conductedClassesHours;
	}
	public String getClassesPresentHours() {
		return classesPresentHours;
	}
	public void setClassesPresentHours(String classesPresentHours) {
		this.classesPresentHours = classesPresentHours;
	}
	public String getClassesAbsentHours() {
		return classesAbsentHours;
	}
	public void setClassesAbsentHours(String classesAbsentHours) {
		this.classesAbsentHours = classesAbsentHours;
	}
	public String getSubjectHrspercentage() {
		return subjectHrspercentage;
	}
	public void setSubjectHrspercentage(String subjectHrspercentage) {
		this.subjectHrspercentage = subjectHrspercentage;
	}
	
}
