package com.kp.cms.bo.employee;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Employee;

public class EmpPaperPresentationBO implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String paperTile;
	private String proceedingsTile;
	private Date date;
	private String institution;
	private String interRegoinal;
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
	private String modifiedBy;
	private Date lastModifiedDate;
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
	public String getPaperTile() {
		return paperTile;
	}
	public void setPaperTile(String paperTile) {
		this.paperTile = paperTile;
	}
	public String getProceedingsTile() {
		return proceedingsTile;
	}
	public void setProceedingsTile(String proceedingsTile) {
		this.proceedingsTile = proceedingsTile;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	public String getInterRegoinal() {
		return interRegoinal;
	}
	public void setInterRegoinal(String interRegoinal) {
		this.interRegoinal = interRegoinal;
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
