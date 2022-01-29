package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.PeersFeedbackSessionTo;

public class PeersFeedbackSessionForm extends BaseActionForm{
	private int id;
	private String sessionName;
	private String month;
	private String year;
	private String academicYear;
	List<PeersFeedbackSessionTo> sessionTos;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSessionName() {
		return sessionName;
	}
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
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
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public List<PeersFeedbackSessionTo> getSessionTos() {
		return sessionTos;
	}
	public void setSessionTos(List<PeersFeedbackSessionTo> sessionTos) {
		this.sessionTos = sessionTos;
	}
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.academicYear = null;
		this.sessionName=null;
		this.month=null;
		this.year=null;
		this.sessionTos = null;
	}
}
