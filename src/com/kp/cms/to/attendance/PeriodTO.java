package com.kp.cms.to.attendance;

import java.util.Date;
import java.util.List;


public class PeriodTO {
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
	private String checked;
	private String tempChecked;
	private String session;
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
	public String getModifiedBy() {
		return modifiedBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(String tempChecked) {
		this.tempChecked = tempChecked;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	

}
