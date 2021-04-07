package com.kp.cms.forms.reports;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.reports.StudentAdmittedCategoryTO;

public class StudentAdmittedCategoryForm extends BaseActionForm {
	private String castCategory;
	private List<CasteTO> casteList;
	private String reportdate;
	private String programName;
	private String programTypeName;
	private String casteName;
	private List<StudentAdmittedCategoryTO> searchedStudents;
	
	public List<StudentAdmittedCategoryTO> getSearchedStudents() {
		return searchedStudents;
	}
	public void setSearchedStudents(List<StudentAdmittedCategoryTO> searchedStudents) {
		this.searchedStudents = searchedStudents;
	}
	public String getCastCategory() {
		return castCategory;
	}
	public void setCastCategory(String castCategory) {
		this.castCategory = castCategory;
	}
	public List<CasteTO> getCasteList() {
		return casteList;
	}
	public void setCasteList(List<CasteTO> casteList) {
		this.casteList = casteList;
	}
	
	public String getReportdate() {
		return reportdate;
	}
	public void setReportdate(String reportdate) {
		this.reportdate = reportdate;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getProgramTypeName() {
		return programTypeName;
	}
	public void setProgramTypeName(String programTypeName) {
		this.programTypeName = programTypeName;
	}
	public String getCasteName() {
		return casteName;
	}
	public void setCasteName(String casteName) {
		this.casteName = casteName;
	}
	/* (non-Javadoc)
	 * validation call
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

}
