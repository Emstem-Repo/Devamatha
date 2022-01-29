package com.kp.cms.bo.admin;

// Generated Apr 8, 2009 1:00:53 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * AttendanceTypeMandatory generated by hbm2java
 */
public class AttendanceTypeMandatory implements java.io.Serializable,Comparable<AttendanceTypeMandatory> {

	private int id;
	private AttendanceType attendanceType;
	private String name;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;

	public AttendanceTypeMandatory() {
	}

	public AttendanceTypeMandatory(int id) {
		this.id = id;
	}

	public AttendanceTypeMandatory(int id, AttendanceType attendanceType,
			String name, String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, Boolean isActive) {
		this.id = id;
		this.attendanceType = attendanceType;
		this.name = name;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AttendanceType getAttendanceType() {
		return this.attendanceType;
	}

	public void setAttendanceType(AttendanceType attendanceType) {
		this.attendanceType = attendanceType;
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

	@Override
	public int compareTo(AttendanceTypeMandatory arg0) {
		if(arg0!=null && this!=null){
			if (arg0.getId() != 0 && this.getId()!=0){
			if(this.getId() > arg0.getId())
				return 1;
			}else if(this.getId() < arg0.getId()){
				return -1;
			}else{
				return 0;
			}
		}
		return 0;
	}

}
