package com.kp.cms.bo.admin;

// Generated Apr 15, 2009 5:41:48 PM by Hibernate Tools 3.2.0.b9

import java.util.Calendar;
import java.util.Date;

/**
 * AttendancePeriod generated by hbm2java
 */
public class AttendancePeriod implements java.io.Serializable,Comparable<AttendancePeriod> {

	private int id;
	private Period period;
	private Attendance attendance;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;

	public AttendancePeriod() {
	}

	public AttendancePeriod(int id) {
		this.id = id;
	}

	public AttendancePeriod(int id, Period period, Attendance attendance,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate) {
		this.id = id;
		this.period = period;
		this.attendance = attendance;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Period getPeriod() {
		return this.period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public Attendance getAttendance() {
		return this.attendance;
	}

	public void setAttendance(Attendance attendance) {
		this.attendance = attendance;
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

	@Override
	public int compareTo(AttendancePeriod arg0) {
		if(arg0!=null){
			
			Calendar startCal = Calendar.getInstance();
			Calendar endCal = Calendar.getInstance();
			String[] periodStartTime =	arg0.getPeriod().getStartTime().split(":");
			
			startCal.setTime(new Date());
			startCal.set(Calendar.HOUR, Integer.valueOf(periodStartTime[0]));
			startCal.set(Calendar.MINUTE, Integer.valueOf(periodStartTime[1]));
			startCal.set(Calendar.SECOND, Integer.valueOf(periodStartTime[2]));
			
			String[] periodEndTime = this.getPeriod().getStartTime().split(":");
			endCal.setTime(new Date());
			endCal.set(Calendar.HOUR, Integer.valueOf(periodEndTime[0]));
			endCal.set(Calendar.MINUTE, Integer.valueOf(periodEndTime[1]));
			endCal.set(Calendar.SECOND, Integer.valueOf(periodEndTime[2]));

			return endCal.compareTo(startCal);
	}
	return 0;	
  }

}