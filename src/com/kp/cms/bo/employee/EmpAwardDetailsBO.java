package com.kp.cms.bo.employee;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Employee;

public class EmpAwardDetailsBO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String award;
	private String activityName;
	private String awardName;
	private String awardBody;
	private String year;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Employee employee;
	private GuestFaculty guest;
	
	
	public GuestFaculty getGuest() {
		return guest;
	}
	public void setGuest(GuestFaculty guest) {
		this.guest = guest;
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
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAward() {
		return award;
	}
	public void setAward(String award) {
		this.award = award;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getAwardName() {
		return awardName;
	}
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	public String getAwardBody() {
		return awardBody;
	}
	public void setAwardBody(String awardBody) {
		this.awardBody = awardBody;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
