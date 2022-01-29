package com.kp.cms.bo.admin;

import java.io.Serializable;

public class TCPrefix implements Serializable {

	private int id;
	private String name;
	private String code;
	private Boolean isActive;
	
	public TCPrefix() {
	}


	public TCPrefix(int id, String name, String code,Boolean isActive) {
		this.id = id;
		this.name = name;
		this.code = code;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
