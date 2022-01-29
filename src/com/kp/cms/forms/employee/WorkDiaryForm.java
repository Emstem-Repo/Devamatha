package com.kp.cms.forms.employee;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;

public class WorkDiaryForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String date;
	private String workTypeId;
	private String comments;
	private FormFile thefile;
	
	public String getDate() {
		return date;
	}
	public String getComments() {
		return comments;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getWorkTypeId() {
		return workTypeId;
	}
	public void setWorkTypeId(String workTypeId) {
		this.workTypeId = workTypeId;
	}
	public FormFile getThefile() {
		return thefile;
	}
	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

}
