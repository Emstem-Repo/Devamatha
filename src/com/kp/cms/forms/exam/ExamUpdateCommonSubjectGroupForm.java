package com.kp.cms.forms.exam;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

@SuppressWarnings("serial")
public class ExamUpdateCommonSubjectGroupForm extends BaseActionForm {
	private String selectedClasses;
	private HashMap classSelected;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void setSelectedClasses(String selectedClasses) {
		this.selectedClasses = selectedClasses;
	}

	public String getSelectedClasses() {
		return selectedClasses;
	}

	public void setClassSelected(HashMap classSelected) {
		this.classSelected = classSelected;
	}

	public HashMap getClassSelected() {
		return classSelected;
	}

	

}