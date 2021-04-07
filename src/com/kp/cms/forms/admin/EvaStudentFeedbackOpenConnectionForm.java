package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EvaStudentFeedbackOpenConnectionTo;
import com.kp.cms.to.admin.EvaluationStudentFeedbackSessionTo;

public class EvaStudentFeedbackOpenConnectionForm extends BaseActionForm{
	private int id;
	private String startDate;
	private String endDate;
	private String[] classesId;
	List<EvaStudentFeedbackOpenConnectionTo> openConnectionTo;
	private String academicYear;
	private boolean flag;
	Map<Integer,String> sessionToList;
	private String sessionId;
	private String specializationId;
	private String isExitEval;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public List<EvaStudentFeedbackOpenConnectionTo> getOpenConnectionTo() {
		return openConnectionTo;
	}
	public void setOpenConnectionTo(
			List<EvaStudentFeedbackOpenConnectionTo> openConnectionTo) {
		this.openConnectionTo = openConnectionTo;
	}
	/**
	 * @param mapping
	 * @param request
	 */
	public void clear() {
		this.id = 0;
		this.startDate = null;
		this.endDate = null;
		this.classesId = null;
		this.academicYear = null;
		this.sessionId = null;
		this.isExitEval="false";
	}
	public String[] getClassesId() {
		return classesId;
	}
	public void setClassesId(String[] classesId) {
		this.classesId = classesId;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public Map<Integer, String> getSessionToList() {
		return sessionToList;
	}
	public void setSessionToList(Map<Integer, String> sessionToList) {
		this.sessionToList = sessionToList;
	}
	
	
	public String getIsExitEval() {
		return isExitEval;
	}
	public void setIsExitEval(String isExitEval) {
		this.isExitEval = isExitEval;
	}
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getSpecializationId() {
		return specializationId;
	}
	public void setSpecializationId(String specializationId) {
		this.specializationId = specializationId;
	}
	
	
	
}
	
