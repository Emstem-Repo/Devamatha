package com.kp.cms.forms.employee;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class LeaveInitializationForm extends BaseActionForm {
	
	private String month;
//	private String employeeTypeId;
	private Map<Integer,String> empTypeMap;
	private Map<String,String> monthMap;
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
//	public String getEmployeeTypeId() {
//		return employeeTypeId;
//	}
//	public void setEmployeeTypeId(String employeeTypeId) {
//		this.employeeTypeId = employeeTypeId;
//	}
	public Map<Integer, String> getEmpTypeMap() {
		return empTypeMap;
	}
	public void setEmpTypeMap(Map<Integer, String> empTypeMap) {
		this.empTypeMap = empTypeMap;
	}
	public Map<String, String> getMonthMap() {
		return monthMap;
	}
	public void setMonthMap(Map<String, String> monthMap) {
		this.monthMap = monthMap;
	}
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	/**
	 * 
	 */
	public void resetFields() {
//		this.employeeTypeId=null;
		this.month=null;
		super.setYear(null);
	}	
	
}
