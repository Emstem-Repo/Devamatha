package com.kp.cms.forms.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

/**
 * 
 * @author
 */

public class SubReligionForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String subReligionName;
	private int subReligionId;
	private int duplId;
	private String origSubRelName;
	private int origRelId;

	public String getSubReligionName() {
		return subReligionName;
	}

	public void setSubReligionName(String subReligionName) {
		this.subReligionName = subReligionName;
	}

	

	public int getSubReligionId() {
		return subReligionId;
	}

	public void setSubReligionId(int subReligionId) {
		this.subReligionId = subReligionId;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		subReligionName = null;
		subReligionId = 0;
		super.setReligionId(null);
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public int getDuplId() {
		return duplId;
	}

	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}

	public int getOrigRelId() {
		return origRelId;
	}


	public void setOrigRelId(int origRelId) {
		this.origRelId = origRelId;
	}

	public String getOrigSubRelName() {
		return origSubRelName;
	}

	public void setOrigSubRelName(String origSubRelName) {
		this.origSubRelName = origSubRelName;
	}

	
}
