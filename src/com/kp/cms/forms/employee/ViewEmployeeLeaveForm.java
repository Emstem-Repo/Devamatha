package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.EmployeeLeaveTO;
@SuppressWarnings("serial")
public class ViewEmployeeLeaveForm extends BaseActionForm{
	private String year;
	private String employeeName;
	private Map<Integer, String> listOfEmployee;
	private List<EmployeeLeaveTO> listOfEmployeeLeave;
	private boolean temp;
	private String eCodeName;
	public void resetFields() {
		this.setListOfEmployee(null);
		this.year=null;
		this.employeeName=null;
		this.eCodeName="eCode";
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public void setListOfEmployee(Map<Integer, String> listOfEmployee) {
		this.listOfEmployee = listOfEmployee;
	}

	public Map<Integer, String> getListOfEmployee() {
		return listOfEmployee;
	}

	public void setListOfEmployeeLeave(List<EmployeeLeaveTO> listOfEmployeeLeave) {
		this.listOfEmployeeLeave = listOfEmployeeLeave;
	}

	public List<EmployeeLeaveTO> getListOfEmployeeLeave() {
		return listOfEmployeeLeave;
	}

	public boolean isTemp() {
		return temp;
	}

	public void setTemp(boolean temp) {
		this.temp = temp;
	}

	public String geteCodeName() {
		return eCodeName;
	}

	public void seteCodeName(String eCodeName) {
		this.eCodeName = eCodeName;
	}



}
