package com.kp.cms.forms.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class GradesForm extends BaseActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String grade;
	private String mark;
	private String origGrade;
	private String origMark;

	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.grade = null;
		this.mark = "";
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public String getOrigGrade() {
		return origGrade;
	}
	public void setOrigGrade(String origGrade) {
		this.origGrade = origGrade;
	}
	public String getOrigMark() {
		return origMark;
	}
	public void setOrigMark(String origMark) {
		this.origMark = origMark;
	}
	
	
}
