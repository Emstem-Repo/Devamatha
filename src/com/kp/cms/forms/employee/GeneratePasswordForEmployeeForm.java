package com.kp.cms.forms.employee;

import com.kp.cms.forms.BaseActionForm;

public class GeneratePasswordForEmployeeForm extends BaseActionForm {
	private String employeeType;
	private String empWorkEmail;
	private String empFingerPrintId;
	
	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getEmpWorkEmail() {
		return empWorkEmail;
	}

	public void setEmpWorkEmail(String empWorkEmail) {
		this.empWorkEmail = empWorkEmail;
	}

	public String getEmpFingerPrintId() {
		return empFingerPrintId;
	}

	public void setEmpFingerPrintId(String empFingerPrintId) {
		this.empFingerPrintId = empFingerPrintId;
	}
	public void resetFields() {
		this.setEmployeeType("Teaching");
		this.setEmpWorkEmail("No");
		this.setEmpFingerPrintId("No");
		
	}
}
