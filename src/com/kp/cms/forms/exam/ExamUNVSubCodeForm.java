package com.kp.cms.forms.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamUnvSubCodeTO;

@SuppressWarnings("serial")
public class ExamUNVSubCodeForm extends BaseActionForm {

	private String academicYear;
	private String academicYear_value;
	private String courseName;
	private String schemeName;
	private String course;
	private String scheme;
	private String universitySubjectCode;
	private String unvSubCodeListSize;
	private List<ExamUnvSubCodeTO> unvSubCodeList;
	private String universitySubjectValue;
	
	private Map<Integer, String> courseMap;
	private HashMap<Integer, String> schemeNameList;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
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

	public String getAcademicYear_value() {
		return academicYear_value;
	}

	public void setAcademicYear_value(String academicYear_value) {
		this.academicYear_value = academicYear_value;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public List<ExamUnvSubCodeTO> getUnvSubCodeList() {
		return unvSubCodeList;
	}

	public void setUnvSubCodeList(List<ExamUnvSubCodeTO> unvSubCodeList) {
		this.unvSubCodeList = unvSubCodeList;
	}

	public void setUniversitySubjectCode(String universitySubjectCode) {
		this.universitySubjectCode = universitySubjectCode;
	}

	public String getUniversitySubjectCode() {
		return universitySubjectCode;
	}

	public String getUnvSubCodeListSize() {
		return unvSubCodeListSize;
	}

	public void setUnvSubCodeListSize(String unvSubCodeListSize) {
		this.unvSubCodeListSize = unvSubCodeListSize;
	}

	public void setUniversitySubjectValue(String universitySubjectValue) {
		this.universitySubjectValue = universitySubjectValue;
	}

	public String getUniversitySubjectValue() {
		return universitySubjectValue;
	}

	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}

	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}

	public void setSchemeNameList(HashMap<Integer, String> schemeNameList) {
		this.schemeNameList = schemeNameList;
	}

	public HashMap<Integer, String> getSchemeNameList() {
		return schemeNameList;
	}



}
