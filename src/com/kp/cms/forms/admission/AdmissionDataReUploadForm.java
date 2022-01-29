package com.kp.cms.forms.admission;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;

public class AdmissionDataReUploadForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String applicationYear;
	private FormFile csvFile;
	
	public String getApplicationYear() {
		return applicationYear;
	}
	public void setApplicationYear(String applicationYear) {
		this.applicationYear = applicationYear;
	}
	public FormFile getCsvFile() {
		return csvFile;
	}
	public void setCsvFile(FormFile csvFile) {
		this.csvFile = csvFile;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public void clear() {
		super.setProgramId("");
		super.setProgramTypeId("");
		super.setCourseId("");
		this.csvFile=null;
		this.setApplicationYear("");
	}

}
