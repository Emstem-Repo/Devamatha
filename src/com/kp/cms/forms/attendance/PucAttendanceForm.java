package com.kp.cms.forms.attendance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;

public class PucAttendanceForm extends BaseActionForm{
	private FormFile theFile;
	private String academicYear;
	private List<String> regNosList;
	public FormFile getTheFile() {
		return theFile;
	}
	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public void reset(){
		this.theFile=null;
		this.academicYear=null;
		this.regNosList=null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public List<String> getRegNosList() {
		return regNosList;
	}
	public void setRegNosList(List<String> regNosList) {
		this.regNosList = regNosList;
	}
}
