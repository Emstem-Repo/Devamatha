package com.kp.cms.forms.hostel;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class HostelExportPhotosForm extends BaseActionForm{
	private String academicYear;
	private Map<String,String> hostelMap;
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	
	public void reset() {
		this.academicYear = null;
		super.setHostelId(null);
		super.setBlockId(null);
		super.setUnitId(null);
		
	}
	public Map<String, String> getHostelMap() {
		return hostelMap;
	}
	public void setHostelMap(Map<String, String> hostelMap) {
		this.hostelMap = hostelMap;
	}
	

}
