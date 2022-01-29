package com.kp.cms.forms.exam;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class ExamStudentSGPAForm extends BaseActionForm {
	
	public void resetFields(){
		super.setProgramTypeId(null);
		super.setSchemeNo(null);
		super.setProgramId(null);
		super.setCourseId(null);
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	
}
