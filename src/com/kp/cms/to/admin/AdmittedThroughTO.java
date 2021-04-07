package com.kp.cms.to.admin;

import java.util.Date;

public class AdmittedThroughTO {
	private int id;
	private String name;
	private Date createdDate;
	
	private Date lastModifiedDate;
	private String admittedThroughCode;
	public AdmittedThroughTO(){
		
	}
	public AdmittedThroughTO(int id, String name, Date createdDate,
			Date lastModifiedDate, String admittedThroughCode) {
		super();
		this.id = id;
		this.name = name;
		this.createdDate = (Date)createdDate.clone();
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
		this.admittedThroughCode = admittedThroughCode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreatedDate() {
		return (Date)createdDate.clone();
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = (Date)createdDate.clone();
	}
	public Date getLastModifiedDate() {
		return (Date)lastModifiedDate.clone();
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
	}
	public void setAdmittedThroughCode(String admittedThroughCode) {
		this.admittedThroughCode = admittedThroughCode;
	}
	public String getAdmittedThroughCode() {
		return admittedThroughCode;
	}
	
}
