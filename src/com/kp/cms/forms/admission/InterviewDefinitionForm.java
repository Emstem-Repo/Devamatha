package com.kp.cms.forms.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.InterviewProgramCourseTO;

public class InterviewDefinitionForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<InterviewProgramCourseTO> interviewDefinitionList;
	private List<ProgramTypeTO> programTypeList;
	private String interviewType;
	private String sequence;
	private int originalCourseId;
	private int originalYear;
	private String originalSequence;
	private int id;
	private int activationRefId;
	private String intCardContent;
	private String viewDesc;
	private String interviewsPerPanel;
	
	public int getActivationRefId() {
		return activationRefId;
	}

	public void setActivationRefId(int activationRefId) {
		this.activationRefId = activationRefId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOriginalSequence() {
		return originalSequence;
	}

	public void setOriginalSequence(String originalSequence) {
		this.originalSequence = originalSequence;
	}

	public int getOriginalCourseId() {
		return originalCourseId;
	}

	public void setOriginalCourseId(int originalCourseId) {
		this.originalCourseId = originalCourseId;
	}

	public int getOriginalYear() {
		return originalYear;
	}

	public void setOriginalYear(int originalYear) {
		this.originalYear = originalYear;
	}

	public String getInterviewType() {
		return interviewType;
	}

	public void setInterviewType(String interviewType) {
		this.interviewType = interviewType;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	public List<InterviewProgramCourseTO> getInterviewDefinitionList() {
		return interviewDefinitionList;
	}

	public void setInterviewDefinitionList(
			List<InterviewProgramCourseTO> interviewDefinitionList) {
		this.interviewDefinitionList = interviewDefinitionList;
	}
	
	public String getIntCardContent() {
		return intCardContent;
	}

	public void setIntCardContent(String intCardContent) {
		this.intCardContent = intCardContent;
	}

	public String getViewDesc() {
		return viewDesc;
	}

	public void setViewDesc(String viewDesc) {
		this.viewDesc = viewDesc;
	}

	public String getInterviewsPerPanel() {
		return interviewsPerPanel;
	}

	public void setInterviewsPerPanel(String interviewsPerPanel) {
		this.interviewsPerPanel = interviewsPerPanel;
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
		
		interviewType = null;
		sequence = null;
		intCardContent = null;
		interviewsPerPanel = null;
	}
}