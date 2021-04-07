package com.kp.cms.forms.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;


public class InstituteForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private int duplInstId; 
	private String origInstName;
	private int editUniId;
	
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
	public int getDuplInstId() {
		return duplInstId;
	}
	public void setDuplInstId(int duplInstId) {
		this.duplInstId = duplInstId;
	}
	public String getOrigInstName() {
		return origInstName;
	}
	public void setOrigInstName(String origInstName) {
		this.origInstName = origInstName;
	}
	public int getEditUniId() {
		return editUniId;
	}
	public void setEditUniId(int editUniId) {
		this.editUniId = editUniId;
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.name = null;
		this.origInstName = null;
		this.editUniId = 0;
		super.setUniversityId(null);
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	

}
