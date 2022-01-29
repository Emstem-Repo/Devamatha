package com.kp.cms.forms.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class GuideLinesCheckListForm extends BaseActionForm {
	private String organizationId;
	private String description;
	private int id;
	private int duplId;
	private String origDesc;
	private int origOrgId;
	
	public String getOrganizationId() {
		return organizationId;
	}
	public String getDescription() {
		return description;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDuplId() {
		return duplId;
	}
	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}
	
	public String getOrigDesc() {
		return origDesc;
	}

	public int getOrigOrgId() {
		return origOrgId;
	}
	public void setOrigDesc(String origDesc) {
		this.origDesc = origDesc;
	}
	public void setOrigOrgId(int origOrgId) {
		this.origOrgId = origOrgId;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		organizationId = null;
		description = null;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

}
