package com.kp.cms.forms.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class AdmittedThroughForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int admittedThroughId;
	private String admittedThrough;
	private String origAdmittedThrough;
	private int duplId;
	private String admittedThroughCode;
	private String origAdmittedThroughCode;
	
	public int getAdmittedThroughId() {
		return admittedThroughId;
	}

	public void setAdmittedThroughId(int admittedThroughId) {
		this.admittedThroughId = admittedThroughId;
	}

	public String getAdmittedThrough() {
		return admittedThrough;
	}

	public void setAdmittedThrough(String admittedThrough) {
		this.admittedThrough = admittedThrough;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		admittedThrough = null;
		admittedThroughCode =null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		//ActionErrors actionErrors = new ActionErrors();
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public String getOrigAdmittedThrough() {
		return origAdmittedThrough;
	}

	public void setOrigAdmittedThrough(String origAdmittedThrough) {
		this.origAdmittedThrough = origAdmittedThrough;
	}

	public int getDuplId() {
		return duplId;
	}

	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}

	public void setAdmittedThroughCode(String admittedThroughCode) {
		this.admittedThroughCode = admittedThroughCode;
	}

	public String getAdmittedThroughCode() {
		return admittedThroughCode;
	}

	public void setOrigAdmittedThroughCode(String origAdmittedThroughCode) {
		this.origAdmittedThroughCode = origAdmittedThroughCode;
	}

	public String getOrigAdmittedThroughCode() {
		return origAdmittedThroughCode;
	}

}
