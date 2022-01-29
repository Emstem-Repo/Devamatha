package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class TTSubjectBatchHistory implements Serializable {

	private int id;
	private TTPeriodWeekHistory ttPeriodWeekHistory;
	private Batch batch;
	private Subject subject;
	private String roomNo;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private AttendanceType attendanceType;
	private Set<TTUsersHistory> ttUsersHistory;
	private Activity activity;
	
	public TTSubjectBatchHistory() {
		super();
	}

	public TTSubjectBatchHistory(int id, TTPeriodWeekHistory ttPeriodWeek, Batch batch,
			Subject subject, String roomNo, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive, Activity activity) {
		super();
		this.id = id;
		this.ttPeriodWeekHistory = ttPeriodWeek;
		this.batch = batch;
		this.subject = subject;
		this.roomNo = roomNo;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.activity=activity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TTPeriodWeekHistory getTtPeriodWeekHistory() {
		return ttPeriodWeekHistory;
	}

	public void setTtPeriodWeekHistory(TTPeriodWeekHistory ttPeriodWeekHistory) {
		this.ttPeriodWeekHistory = ttPeriodWeekHistory;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
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

	public AttendanceType getAttendanceType() {
		return attendanceType;
	}

	public void setAttendanceType(AttendanceType attendanceType) {
		this.attendanceType = attendanceType;
	}

	public Set<TTUsersHistory> getTtUsersHistory() {
		return ttUsersHistory;
	}

	public void setTtUsersHistory(Set<TTUsersHistory> ttUsersHistory) {
		this.ttUsersHistory = ttUsersHistory;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
}