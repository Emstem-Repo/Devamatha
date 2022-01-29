package com.kp.cms.bo.employee;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Employee;

public class EmpSeminarAttendedDetailsBO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String workShop;
	private String title;
	private String participation;
	private Date fromDate2;
	private Date toDate2;
	private String institution;
	private String interRegional;
	private String createdBY;
	private Date createddate;
	private Employee employee;
	private String modifiedBy;
	private Date lastModifiedDate;
	private GuestFaculty guest;
	public GuestFaculty getGuest() {
		return guest;
	}
	public void setGuest(GuestFaculty guest) {
		this.guest = guest;
	}
	public Date getFromDate2() {
		return fromDate2;
	}
	public void setFromDate2(Date fromDate2) {
		this.fromDate2 = fromDate2;
	}
	public Date getToDate2() {
		return toDate2;
	}
	public void setToDate2(Date toDate2) {
		this.toDate2 = toDate2;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWorkShop() {
		return workShop;
	}
	public void setWorkShop(String workShop) {
		this.workShop = workShop;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getParticipation() {
		return participation;
	}
	public void setParticipation(String participation) {
		this.participation = participation;
	}
	
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	public String getInterRegional() {
		return interRegional;
	}
	public void setInterRegional(String interRegional) {
		this.interRegional = interRegional;
	}
	public String getCreatedBY() {
		return createdBY;
	}
	public void setCreatedBY(String createdBY) {
		this.createdBY = createdBY;
	}
	public Date getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
