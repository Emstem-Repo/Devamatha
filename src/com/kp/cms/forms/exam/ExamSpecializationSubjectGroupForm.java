package com.kp.cms.forms.exam;

/**
 * Jan 4, 2010 Created By 9Elements
 */
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamSpecializationSubjectGroupTO;
import com.kp.cms.to.exam.KeyValueTO;

public class ExamSpecializationSubjectGroupForm extends BaseActionForm {

	private String academicYear;
	private String academicYear_value;
	private String courseName;
	private String schemeName;
	private String courseId;
	private String schemeId;
	private String universitySubjectCode;
	private String[] subjectIds;
	private List<KeyValueTO> listCourses;
	private Map<Integer, String> schemeNOMap;
	private List<ExamSpecializationSubjectGroupTO> listSpecializations;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
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

	public void clearPage() {
		this.academicYear = "";
		this.courseId = "";
		this.schemeId = "";
	}

	

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getUniversitySubjectCode() {
		return universitySubjectCode;
	}

	public void setUniversitySubjectCode(String universitySubjectCode) {
		this.universitySubjectCode = universitySubjectCode;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	public List<ExamSpecializationSubjectGroupTO> getListSpecializations() {
		return listSpecializations;
	}

	public void setListSpecializations(
			List<ExamSpecializationSubjectGroupTO> listSpecializations) {
		this.listSpecializations = listSpecializations;
	}

	

	public List<KeyValueTO> getListCourses() {
		return listCourses;
	}

	public void setListCourses(List<KeyValueTO> listCourses) {
		this.listCourses = listCourses;
	}

	public void setSubjectIds(String[] subjectIds) {
		this.subjectIds = subjectIds;
	}

	public String[] getSubjectIds() {
		return subjectIds;
	}

	public void setSchemeNOMap(Map<Integer, String> schemeNOMap) {
		this.schemeNOMap = schemeNOMap;
	}

	public Map<Integer, String> getSchemeNOMap() {
		return schemeNOMap;
	}

}
