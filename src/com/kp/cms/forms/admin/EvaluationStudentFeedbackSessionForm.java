package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EvaluationStudentFeedbackSessionTo;

public class EvaluationStudentFeedbackSessionForm extends BaseActionForm{
 private int id;
 private String name;
 private String month;
 private String year;
 private String academicYear;
 List<EvaluationStudentFeedbackSessionTo> sessionList;
 private EvaluationStudentFeedbackSessionTo feedbackSessionTo;
 
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getMonth() {
	return month;
}
public void setMonth(String month) {
	this.month = month;
}
public String getYear() {
	return year;
}
public void setYear(String year) {
	this.year = year;
}

public List<EvaluationStudentFeedbackSessionTo> getSessionList() {
	return sessionList;
}
public void setSessionList(List<EvaluationStudentFeedbackSessionTo> sessionList) {
	this.sessionList = sessionList;
}
public EvaluationStudentFeedbackSessionTo getFeedbackSessionTo() {
	return feedbackSessionTo;
}
public void setFeedbackSessionTo(
		EvaluationStudentFeedbackSessionTo feedbackSessionTo) {
	this.feedbackSessionTo = feedbackSessionTo;
}
public void clear(ActionMapping mapping, HttpServletRequest request) {
	super.reset(mapping, request);
	this.name = null;
	this.month = null;
	this.year = null;
	this.academicYear = null;
}
public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
	String formName = request.getParameter(CMSConstants.FORMNAME);
	ActionErrors actionErrors = super.validate(mapping, request, formName);
	return actionErrors;
}
public String getAcademicYear() {
	return academicYear;
}
public void setAcademicYear(String academicYear) {
	this.academicYear = academicYear;
}
}
