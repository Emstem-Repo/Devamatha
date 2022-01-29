package com.kp.cms.forms.employee;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class AttributeMasterForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String isEmployee;
	private int duplId;
	private String origName;
	private String origIsEmployee;
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getIsEmployee() {
		return isEmployee;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setIsEmployee(String isEmployee) {
		this.isEmployee = isEmployee;
	}
	public int getDuplId() {
		return duplId;
	}
	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}
	public String getOrigName() {
		return origName;
	}
	public String getOrigIsEmployee() {
		return origIsEmployee;
	}
	public void setOrigName(String origName) {
		this.origName = origName;
	}
	public void setOrigIsEmployee(String origIsEmployee) {
		this.origIsEmployee = origIsEmployee;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
}
