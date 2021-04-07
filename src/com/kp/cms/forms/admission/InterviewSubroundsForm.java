package com.kp.cms.forms.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.InterviewSubroundsTO;

public class InterviewSubroundsForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int activationRefId;
	private List<ProgramTypeTO> programTypeList;
	private String originalInterviewTypeId;
	private String originalSubroundName;
	private String subroundName;
	private String appliedYear;
	private List<InterviewSubroundsTO> interviewSubroundsList;
	private String interviewsPerPanel;
	private String fromYear;
	private String toYear;

	
	public String getOriginalInterviewTypeId() {
		return originalInterviewTypeId;
	}

	public void setOriginalInterviewTypeId(String originalInterviewTypeId) {
		this.originalInterviewTypeId = originalInterviewTypeId;
	}

	public List<InterviewSubroundsTO> getInterviewSubroundsList() {
		return interviewSubroundsList;
	}

	public void setInterviewSubroundsList(
			List<InterviewSubroundsTO> interviewSubroundsList) {
		this.interviewSubroundsList = interviewSubroundsList;
	}

	public String getSubroundName() {
		return subroundName;
	}

	public void setSubroundName(String subroundName) {
		this.subroundName = subroundName;
	}

	public String getAppliedYear() {
		return appliedYear;
	}

	public void setAppliedYear(String appliedYear) {
		this.appliedYear = appliedYear;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getActivationRefId() {
		return activationRefId;
	}

	public void setActivationRefId(int activationRefId) {
		this.activationRefId = activationRefId;
	}

	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	public String getOriginalSubroundName() {
		return originalSubroundName;
	}

	public void setOriginalSubroundName(String originalSubroundName) {
		this.originalSubroundName = originalSubroundName;
	}
	
	public String getInterviewsPerPanel() {
		return interviewsPerPanel;
	}

	public void setInterviewsPerPanel(String interviewsPerPanel) {
		this.interviewsPerPanel = interviewsPerPanel;
	}

	public String getFromYear() {
		return fromYear;
	}

	public void setFromYear(String fromYear) {
		this.fromYear = fromYear;
	}

	public String getToYear() {
		return toYear;
	}

	public void setToYear(String toYear) {
		this.toYear = toYear;
	}


	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	@Override
	public void clear() {
		super.clear();
		
		this.setInterviewTypeId(null);
		this.setSubroundName(null);
		this.setAppliedYear(null);
		this.setInterviewsPerPanel(null);
		this.setFromYear(null);
		this.setToYear(null);
	}
}