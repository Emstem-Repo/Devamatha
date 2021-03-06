package com.kp.cms.bo.admin;

// Generated Apr 20, 2009 4:50:12 PM by Hibernate Tools 3.2.0.b9

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Period generated by hbm2java
 */
public class Period implements java.io.Serializable,Comparable<Period> {

	private int id;
	private ClassSchemewise classSchemewise;
	private String periodName;
	private String startTime;
	private String endTime;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String session;
	private Set<AttendancePeriod> attendancePeriods = new HashSet<AttendancePeriod>(
			0);

	public Period() {
	}

	public Period(int id) {
		this.id = id;
	}

	public Period(int id, ClassSchemewise classSchemewise, String periodName,
			String startTime, String endTime, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive,
			Set<AttendancePeriod> attendancePeriods,String session) {
		this.id = id;
		this.classSchemewise = classSchemewise;
		this.periodName = periodName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.attendancePeriods = attendancePeriods;
		this.session=session;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ClassSchemewise getClassSchemewise() {
		return this.classSchemewise;
	}

	public void setClassSchemewise(ClassSchemewise classSchemewise) {
		this.classSchemewise = classSchemewise;
	}

	public String getPeriodName() {
		return this.periodName;
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

	public Set<AttendancePeriod> getAttendancePeriods() {
		return this.attendancePeriods;
	}

	public void setAttendancePeriods(Set<AttendancePeriod> attendancePeriods) {
		this.attendancePeriods = attendancePeriods;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	@Override
	public int compareTo(Period arg0) {
		if(arg0!=null && this!=null){
			DateFormat dateFormat = new SimpleDateFormat("k:mm:ss");
			Date startTime;
			try {
				startTime = dateFormat.parse(this.getStartTime());
				Date startTime1 = dateFormat.parse(arg0.getStartTime());
				if(startTime.compareTo(startTime1) > 0)
					return 1;
				else if(startTime.compareTo(startTime1) < 0)
					return -1;
				else{
					return 0;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		return 0;
	}

}
