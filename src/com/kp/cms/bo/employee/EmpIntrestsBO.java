package com.kp.cms.bo.employee;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Employee;

public class EmpIntrestsBO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String topic;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Employee employee;
	private GuestFaculty guest;
	
	
	public GuestFaculty getGuest() {
		return guest;
	}
	public void setGuest(GuestFaculty guest) {
		this.guest = guest;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	private Date lastModifiedDate;
	
	
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
