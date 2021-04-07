package com.kp.cms.bo.exam;

/**
 * Jan 16, 2010 Created By 9Elements Team
 */
public class EmployeeUtilBO  {
	
	private int id;
	private String firstName;
	private String middleName;
	private String lastName;
	private int departmentId;
	
	private DepartmentUtilBO departmentUtilBO;
	
	
	public EmployeeUtilBO() {
		super();
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public DepartmentUtilBO getDepartmentUtilBO() {
		return departmentUtilBO;
	}

	public void setDepartmentUtilBO(DepartmentUtilBO departmentUtilBO) {
		this.departmentUtilBO = departmentUtilBO;
	}
	
}
