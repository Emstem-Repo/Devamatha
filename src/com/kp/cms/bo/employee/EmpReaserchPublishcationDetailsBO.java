package com.kp.cms.bo.employee;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Employee;

public class EmpReaserchPublishcationDetailsBO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private String journalName;
	private String year;
	private String ISBNISSNNo;
	private String UGC;
	private String createdBy;
	private Date createdDate;
	private Employee employee;
	private GuestFaculty guest;
	
	private String modifiedBy;
	private Date lastModifiedDate;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getJournalName() {
		return journalName;
	}
	public void setJournalName(String journalName) {
		this.journalName = journalName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getISBNISSNNo() {
		return ISBNISSNNo;
	}
	public void setISBNISSNNo(String iSBNISSNNo) {
		ISBNISSNNo = iSBNISSNNo;
	}
	public String getUGC() {
		return UGC;
	}
	public void setUGC(String uGC) {
		UGC = uGC;
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
