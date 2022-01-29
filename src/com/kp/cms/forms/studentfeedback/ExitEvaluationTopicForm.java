package com.kp.cms.forms.studentfeedback;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EducationStreamTO;
import com.kp.cms.to.studentfeedback.ExitEvaluationTopicTo;

public class ExitEvaluationTopicForm extends BaseActionForm 
{
	
	private int exitEvalTopicId;
	private String topicName;
	private int reactivateid;
	private String orgName;
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	private List<ExitEvaluationTopicTo> exitEvalTopicList;
	
	
	
	
	public List<ExitEvaluationTopicTo> getExitEvalTopicList() {
		return exitEvalTopicList;
	}
	public void setExitEvalTopicList(List<ExitEvaluationTopicTo> exitEvalTopicList) {
		this.exitEvalTopicList = exitEvalTopicList;
	}
	public int getReactivateid() {
		return reactivateid;
	}
	public void setReactivateid(int reactivateid) {
		this.reactivateid = reactivateid;
	}
	
	
	public int getExitEvalTopicId() {
		return exitEvalTopicId;
	}
	public void setExitEvalTopicId(int exitEvalTopicId) {
		this.exitEvalTopicId = exitEvalTopicId;
	}
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	

}
