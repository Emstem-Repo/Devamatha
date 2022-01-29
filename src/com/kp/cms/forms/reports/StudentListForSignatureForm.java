package com.kp.cms.forms.reports;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class StudentListForSignatureForm extends BaseActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String columnHeading;
	
	public String getColumnHeading() {
		return columnHeading;
	}
	public void setColumnHeading(String columnHeading) {
		this.columnHeading = columnHeading;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public void resetFields() {
		columnHeading = null;
		super.setClassName(null);
		super.setClassId(null);
	}	
}
