package com.kp.cms.to.employee;

import java.io.Serializable;

public class EmpAttributeTO implements Serializable{

	private int id;
	private String name;
	private String value;
	private Boolean isEmployee;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIsEmployee() {
		return isEmployee;
	}
	public void setIsEmployee(Boolean isEmployee) {
		this.isEmployee = isEmployee;
	}
	
}
