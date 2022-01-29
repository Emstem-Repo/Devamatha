package com.kp.cms.to.admin;

import java.util.Date;
import java.util.List;

import com.kp.cms.to.attendance.ClassSchemewiseTO;

public class PeriodTONew {

	private int id;
	private String periodName;
	private String startTime;
	private String endTime;
	private ClassSchemewiseTO classSchemewiseTO;
	private Date periodDate;
	private int classSchemewiseId;
	private String isActive;
	private String createdBy;
	private String modifiedBy;
	private String date;
	
	private String createdDate;
	private String lastModifiedDate;
	private String hoursTaken;
	private String name;
	
	private String attendanceId;
	private List<String> periodNames;
	private Boolean coLeave;
	private int position;
	private String attendanceType;
	private String subjectName;
	private boolean checked;
	private boolean tempChecked;
	private String activity;
	private Boolean isAbsent;
	private String cocurricularDetailId;
	private String attStudentId;
	private String mailAttId;
	private String subjectId;
	private String appStatus;
	private String activityId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public ClassSchemewiseTO getClassSchemewiseTO() {
		return classSchemewiseTO;
	}
	public void setClassSchemewiseTO(ClassSchemewiseTO classSchemewiseTO) {
		this.classSchemewiseTO = classSchemewiseTO;
	}
	public Date getPeriodDate() {
		return periodDate;
	}
	public void setPeriodDate(Date periodDate) {
		this.periodDate = periodDate;
	}
	public int getClassSchemewiseId() {
		return classSchemewiseId;
	}
	public void setClassSchemewiseId(int classSchemewiseId) {
		this.classSchemewiseId = classSchemewiseId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public String getHoursTaken() {
		return hoursTaken;
	}
	public void setHoursTaken(String hoursTaken) {
		this.hoursTaken = hoursTaken;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAttendanceId() {
		return attendanceId;
	}
	public void setAttendanceId(String attendanceId) {
		this.attendanceId = attendanceId;
	}
	public List<String> getPeriodNames() {
		return periodNames;
	}
	public void setPeriodNames(List<String> periodNames) {
		this.periodNames = periodNames;
	}
	public Boolean getCoLeave() {
		return coLeave;
	}
	public void setCoLeave(Boolean coLeave) {
		this.coLeave = coLeave;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getAttendanceType() {
		return attendanceType;
	}
	public void setAttendanceType(String attendanceType) {
		this.attendanceType = attendanceType;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean isTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(boolean tempChecked) {
		this.tempChecked = tempChecked;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public Boolean getIsAbsent() {
		return isAbsent;
	}
	public void setIsAbsent(Boolean isAbsent) {
		this.isAbsent = isAbsent;
	}
	public String getCocurricularDetailId() {
		return cocurricularDetailId;
	}
	public void setCocurricularDetailId(String cocurricularDetailId) {
		this.cocurricularDetailId = cocurricularDetailId;
	}
	public String getAttStudentId() {
		return attStudentId;
	}
	public void setAttStudentId(String attStudentId) {
		this.attStudentId = attStudentId;
	}
	public String getMailAttId() {
		return mailAttId;
	}
	public void setMailAttId(String mailAttId) {
		this.mailAttId = mailAttId;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	
}
