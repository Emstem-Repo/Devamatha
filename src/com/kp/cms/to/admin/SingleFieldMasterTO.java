package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.Date;

public class SingleFieldMasterTO implements Serializable,Comparable<SingleFieldMasterTO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private Date createdDate;
	
	private Date lastModifiedDate;
	private String orderType;
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public SingleFieldMasterTO(){
		
	}
	public SingleFieldMasterTO(int id, String name, Date createdDate,
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
	@Override
	public int compareTo(SingleFieldMasterTO arg0) {
		if(arg0.getName()!=null && this.getName()!=null){
			return this.getName().compareTo(arg0.getName());
		}else
		return 0;
	}
	
}
