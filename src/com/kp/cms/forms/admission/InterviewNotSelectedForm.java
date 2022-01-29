package com.kp.cms.forms.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.InterviewNotSelectedTO;

public class InterviewNotSelectedForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ProgramTypeTO> programTypeList;
	private String appliedYear;
	private List<InterviewNotSelectedTO> notSelectedCandidatesList;
	private String templateDescription;
	
	public String getAppliedYear() {
		return appliedYear;
	}
	public void setAppliedYear(String appliedYear) {
		this.appliedYear = appliedYear;
	}
	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}
	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}
	
	public List<InterviewNotSelectedTO> getNotSelectedCandidatesList() {
		return notSelectedCandidatesList;
	}
	public void setNotSelectedCandidatesList(
			List<InterviewNotSelectedTO> notSelectedCandidatesList) {
		this.notSelectedCandidatesList = notSelectedCandidatesList;
	}
	public String getTemplateDescription() {
		return templateDescription;
	}
	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void resetFields() {
		super.setProgramTypeId(null);
		super.setProgramId(null);
		super.setCourseId(null);
		this.setAppliedYear(null);
		super.setInterviewTypeId(null);
	}
}
