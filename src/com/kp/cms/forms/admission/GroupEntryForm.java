package com.kp.cms.forms.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.CCGroupTo;

public class GroupEntryForm extends BaseActionForm {
	
	private int id;
	private String name;
	private String startDate;
	private String endDate;
	private List<CCGroupTo> ccGroupTos;
	private String mode;
	
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public List<CCGroupTo> getCcGroupTos() {
		return ccGroupTos;
	}
	public void setCcGroupTos(List<CCGroupTo> ccGroupTos) {
		this.ccGroupTos = ccGroupTos;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
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
		this.name=null;
		this.startDate=null;
		this.endDate=null;
		this.id=0;
		this.mode="add";
	}
}
