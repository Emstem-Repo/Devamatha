package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.employee.ShiftEntryTo;

public class ShiftEntryForm extends BaseActionForm {
	
	private String id;
	private String name;
	private String button;
	private Map<Integer, String> employeeMap;
	private String employeeId;
	private List<ShiftEntryTo> entryTos;
	private List<EmployeeTO> empList;
	/**
	 * 
	 */
	
	public void resetId(){
		this.id=null;
	}
	public void reset(){
		this.name=null;
		this.button="Add";
	}
	
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getButton() {
		return button;
	}
	public void setButton(String button) {
		this.button = button;
	}
	public Map<Integer, String> getEmployeeMap() {
		return employeeMap;
	}
	public void setEmployeeMap(Map<Integer, String> employeeMap) {
		this.employeeMap = employeeMap;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public List<ShiftEntryTo> getEntryTos() {
		return entryTos;
	}
	public void setEntryTos(List<ShiftEntryTo> entryTos) {
		this.entryTos = entryTos;
	}
	public List<EmployeeTO> getEmpList() {
		return empList;
	}
	public void setEmpList(List<EmployeeTO> empList) {
		this.empList = empList;
	}
		

}
