package com.kp.cms.forms.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.CheckListTO;
import com.kp.cms.to.admission.DocTO;
import com.kp.cms.to.admission.InterviewProgramCourseTO;

public class CopyInterviewDefinitionForm extends BaseActionForm {
	private int id;
	private String fromYear;
	private String toYear;
	private List<InterviewProgramCourseTO> displayInterviewDefinition;
	private String method;
	private List<InterviewProgramCourseTO> backupInterviewDefinition;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public List<InterviewProgramCourseTO> getDisplayInterviewDefinition() {
		return displayInterviewDefinition;
	}
	public void setDisplayInterviewDefinition(
			List<InterviewProgramCourseTO> displayInterviewDefinition) {
		this.displayInterviewDefinition = displayInterviewDefinition;
	}
	public List<InterviewProgramCourseTO> getBackupInterviewDefinition() {
		return backupInterviewDefinition;
	}
	public void setBackupInterviewDefinition(
			List<InterviewProgramCourseTO> backupInterviewDefinition) {
		this.backupInterviewDefinition = backupInterviewDefinition;
	}
	public void clear(){
		this.id=0;
		this.fromYear=null;
		this.toYear=null;
		this.method=null;
		this.displayInterviewDefinition=null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
}
