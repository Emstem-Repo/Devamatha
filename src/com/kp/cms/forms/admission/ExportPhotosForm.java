package com.kp.cms.forms.admission;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class ExportPhotosForm extends BaseActionForm{
	private String admittedYear;
	//private String folderName;
	private Map<Integer, String> listProgram;
	private Map<Integer, String> listCourse;
	private String admitOrAll;
	private String imageNametype;
	
	
	
	public String getAdmitOrAll() {
		return admitOrAll;
	}
	public void setAdmitOrAll(String admitOrAll) {
		this.admitOrAll = admitOrAll;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public void setListProgram(Map<Integer, String> listProgram) {
		this.listProgram = listProgram;
	}

	public Map<Integer, String> getListProgram() {
		return listProgram;
	}

	public void setAdmittedYear(String admittedYear) {
		this.admittedYear = admittedYear;
	}

	public String getAdmittedYear() {
		return admittedYear;
	}
	public Map<Integer, String> getListCourse() {
		return listCourse;
	}
	public void setListCourse(Map<Integer, String> listCourse) {
		this.listCourse = listCourse;
	}
	public String getImageNametype() {
		return imageNametype;
	}
	public void setImageNametype(String imageNametype) {
		this.imageNametype = imageNametype;
	}
	
	
	

}
