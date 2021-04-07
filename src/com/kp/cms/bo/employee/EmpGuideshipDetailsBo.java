package com.kp.cms.bo.employee;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Employee;

public class EmpGuideshipDetailsBo implements Serializable {
		
	private static final long serialVersionUID = 1L;
	private int id;
	private String scholarName;
	private String registraionYear;
	private String awarded;
	private String awardedYear;
	private String awardedThesisName;
	private String createdBy;
	private Date createdDate;
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
	private String modifiedBy;
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
	public String getScholarName() {
		return scholarName;
	}
	public void setScholarName(String scholarName) {
		this.scholarName = scholarName;
	}
	public String getRegistraionYear() {
		return registraionYear;
	}
	public void setRegistraionYear(String registraionYear) {
		this.registraionYear = registraionYear;
	}
	public String getAwarded() {
		return awarded;
	}
	public void setAwarded(String awarded) {
		this.awarded = awarded;
	}
	public String getAwardedYear() {
		return awardedYear;
	}
	public void setAwardedYear(String awardedYear) {
		this.awardedYear = awardedYear;
	}
	public String getAwardedThesisName() {
		return awardedThesisName;
	}
	public void setAwardedThesisName(String awardedThesisName) {
		this.awardedThesisName = awardedThesisName;
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
