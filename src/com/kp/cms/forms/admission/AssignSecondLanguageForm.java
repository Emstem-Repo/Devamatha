package com.kp.cms.forms.admission;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admission.AssignSecondLanguageTo;

public class AssignSecondLanguageForm extends BaseActionForm {
	
	private String year;
	private String courseId;
	private String schemeNo;
	private String section;
	private String secondLanguageId;
	private String searchSecondLanguageId;
	private List<CourseTO> courseList;
	HashMap<Integer, String> secondLanguageList;
	private List<AssignSecondLanguageTo> list;
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getSecondLanguageId() {
		return secondLanguageId;
	}
	public void setSecondLanguageId(String secondLanguageId) {
		this.secondLanguageId = secondLanguageId;
	}
	public List<CourseTO> getCourseList() {
		return courseList;
	}
	public void setCourseList(List<CourseTO> courseList) {
		this.courseList = courseList;
	}
	public HashMap<Integer, String> getSecondLanguageList() {
		return secondLanguageList;
	}
	public void setSecondLanguageList(HashMap<Integer, String> secondLanguageList) {
		this.secondLanguageList = secondLanguageList;
	}
	public List<AssignSecondLanguageTo> getList() {
		return list;
	}
	public void setList(List<AssignSecondLanguageTo> list) {
		this.list = list;
	}
	public String getSearchSecondLanguageId() {
		return searchSecondLanguageId;
	}
	public void setSearchSecondLanguageId(String searchSecondLanguageId) {
		this.searchSecondLanguageId = searchSecondLanguageId;
	}
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	/**
	 * 
	 */
	public void resetFields() {
		this.courseId=null;
		this.year=null;
		this.schemeNo=null;
		this.section=null;
		this.secondLanguageId=null;
	}
}
