package com.kp.cms.forms.admission;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;

public class PromoteBioDataUploadForm extends BaseActionForm{

	private String academicYear;
	private FormFile theFile;
	private String courseName;
	private Map<String,String> courses;

	public FormFile getTheFile() {
		return theFile;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
 		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void resetFields(){
		this.theFile=null;
		this.courseName=null;
		
	}

	public Map<String, String> getCourses() {
		return courses;
	}

	public void setCourses(Map<String, String> courses) {
		this.courses = courses;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	

}
