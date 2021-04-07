package com.kp.cms.to.admin;

import java.util.Date;

public class UGCoursesTO {
	private int id;
	private String name;
	private Date createdDate;
	private Date lastModifiedDate;
	
	public UGCoursesTO(){
		
	}
	public UGCoursesTO(int id, String name, Date createdDate,
			Date lastModifiedDate) {
		super();
		this.id = id;
		this.name = name;
		this.createdDate = (Date)createdDate.clone();
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
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

}
