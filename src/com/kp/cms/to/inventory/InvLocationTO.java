package com.kp.cms.to.inventory;

import com.kp.cms.to.admin.EmployeeTO;


public class InvLocationTO {
	private int id;
	private String name;
	private boolean isActive;
	private EmployeeTO employeeTO;
	private String invCampusName;
	private int invCampusId;

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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public EmployeeTO getEmployeeTO() {
		return employeeTO;
	}

	public void setEmployeeTO(EmployeeTO employeeTO) {
		this.employeeTO = employeeTO;
	}

	public String getInvCampusName() {
		return invCampusName;
	}

	public void setInvCampusName(String invCampusName) {
		this.invCampusName = invCampusName;
	}

	public int getInvCampusId() {
		return invCampusId;
	}

	public void setInvCampusId(int invCampusId) {
		this.invCampusId = invCampusId;
	}
}
