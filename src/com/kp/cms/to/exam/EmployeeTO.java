/**
 *Jan 1, 2010
 * developed by 9Elements Team
 */
package com.kp.cms.to.exam;

public class EmployeeTO {

	private int id;
	private String firstName;
	private String lastName;
	private String department;
	private int empId;

	public EmployeeTO(int id, String firstName, String lastName,
			String department) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
	}

	public EmployeeTO(int id, String firstName, String lastName, int empId,
			String department) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
		this.empId = empId;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

}
