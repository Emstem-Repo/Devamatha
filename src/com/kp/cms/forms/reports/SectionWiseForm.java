package com.kp.cms.forms.reports;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class SectionWiseForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String method;
	private String academicYear;
	private List<String> sectionWiseList = new ArrayList<String>();
	private boolean kjcReport;
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public List<String> getSectionWiseList() {
		return sectionWiseList;
	}

	public void setSectionWiseList(List<String> sectionWiseList) {
		this.sectionWiseList = sectionWiseList;
	}


	public boolean isKjcReport() {
		return kjcReport;
	}

	public void setKjcReport(boolean kjcReport) {
		this.kjcReport = kjcReport;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void resetFields() {
		super.setProgramTypeId(null);
		super.setProgramId(null);
		super.setSemister(null);
		super.setCourseId(null);
		super.setYear(null);
	}
}
