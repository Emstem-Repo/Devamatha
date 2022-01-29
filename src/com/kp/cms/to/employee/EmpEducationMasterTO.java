package com.kp.cms.to.employee;

import com.kp.cms.bo.admin.EmpQualificationLevel;

public class EmpEducationMasterTO {
	private int id;
	private EmpQualificationLevel empQualificationLevel;
	private String name;
	
	public int getId() {
		return id;
	}
	public EmpQualificationLevel getEmpQualificationLevel() {
		return empQualificationLevel;
	}
	public String getName() {
		return name;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setEmpQualificationLevel(EmpQualificationLevel empQualificationLevel) {
		this.empQualificationLevel = empQualificationLevel;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
