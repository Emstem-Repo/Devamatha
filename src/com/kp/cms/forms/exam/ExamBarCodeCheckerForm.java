package com.kp.cms.forms.exam;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

@SuppressWarnings("serial")
public class ExamBarCodeCheckerForm extends BaseActionForm{
	private String registrNo;

	
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public void setRegistrNo(String registrNo) {
		this.registrNo = registrNo;
	}

	public String getRegistrNo() {
		return registrNo;
	}

}
