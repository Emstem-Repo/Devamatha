package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.InterviewTemplateTo;
import com.kp.cms.to.admin.ProgramTypeTO;

public class InterviewTemplateForm extends BaseActionForm {
	
	private static final long serialVersionUID = 1L;
	public String desc;
	public String applnMailDesc;
	private int templateId;
	private List<ProgramTypeTO> programTypeList;
	public String templateName;
	public String templateDescription;
	public List<InterviewTemplateTo> templateList;
	public String interviewId;
	private String appliedYear;
	private String interviewSubroundId;
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getApplnMailDesc() {
		return applnMailDesc;
	}
	public void setApplnMailDesc(String applnMailDesc) {
		this.applnMailDesc = applnMailDesc;
	}
	public int getTemplateId() {
		return templateId;
	}
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}
	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplateDescription() {
		return templateDescription;
	}
	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}
	public List<InterviewTemplateTo> getTemplateList() {
		return templateList;
	}
	public void setTemplateList(List<InterviewTemplateTo> templateList) {
		this.templateList = templateList;
	}
	public String getInterviewId() {
		return interviewId;
	}
	public void setInterviewId(String interviewId) {
		this.interviewId = interviewId;
	}
	public String getAppliedYear() {
		return appliedYear;
	}
	public void setAppliedYear(String appliedYear) {
		this.appliedYear = appliedYear;
	}
	public String getInterviewSubroundId() {
		return interviewSubroundId;
	}
	public void setInterviewSubroundId(String interviewSubroundId) {
		this.interviewSubroundId = interviewSubroundId;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void clear(){
		this.templateName=null;
		this.appliedYear=null;
		this.interviewId=null;
		this.interviewSubroundId=null;
		super.setProgramId(null);
		super.setProgramTypeId(null);
		super.setCourseId(null);
		this.templateDescription=null;
	}
}
