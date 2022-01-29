package com.kp.cms.bo.employee;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeTypeBO;

public class ShiftEntry implements Serializable{
	
	private int id;
	private String day;
	private String timeIn;
	private String timeOut;
	private Boolean isActive;
	private String createdBy;
	private String lastModifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Employee employee;
	/**
	 * 
	 */
	public ShiftEntry(){
		
	}
	
	/**
	 * @param id
	 * @param day
	 * @param timeIn
	 * @param timeOut
	 * @param isActive
	 * @param createdBy
	 * @param lastModifiedBy
	 * @param createdDate
	 * @param lastModifiedDate
	 * @param employee
	 */
	public ShiftEntry(int id, String day, String timeIn, String timeOut,
			Boolean isActive, String createdBy, String lastModifiedBy,
			Date createdDate, Date lastModifiedDate, Employee employee) {
		super();
		this.id = id;
		this.day = day;
		this.timeIn = timeIn;
		this.timeOut = timeOut;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.lastModifiedBy = lastModifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.employee = employee;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(String timeIn) {
		this.timeIn = timeIn;
	}

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
}
