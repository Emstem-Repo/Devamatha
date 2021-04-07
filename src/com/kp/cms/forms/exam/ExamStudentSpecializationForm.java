package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamStudentSpecializationTO;
import com.kp.cms.to.exam.KeyValueTO;

@SuppressWarnings("serial")
public class ExamStudentSpecializationForm extends BaseActionForm {

	private String academicYear;
	private String course;
	private String scheme;
	private String section;
	private String searchSpec;
	private String academicYear_value;
	private String courseName;
	private String schemeName;
	private String isChecked;
	private String specId;
	private String sectionId;
	private String schemeNo;
	private List<KeyValueTO> listCourses;
	private List<KeyValueTO> listSpecialization;
	private String searchSpecId;
	
	private Map<String, String> schemeNameList;
	private ArrayList<KeyValueTO> sectionList;

	public Map<String, String> getSchemeNameList() {
		return schemeNameList;
	}

	public void setSchemeNameList(Map<String, String> schemeNameList) {
		this.schemeNameList = schemeNameList;
	}

	

	public String getSearchSpecId() {
		return searchSpecId;
	}

	public void setSearchSpecId(String searchSpecId) {
		this.searchSpecId = searchSpecId;
	}

	ArrayList<ExamStudentSpecializationTO> listStudentSpec;

	public ArrayList<ExamStudentSpecializationTO> getListStudentSpec() {
		return listStudentSpec;
	}

	public void setListStudentSpec(
			ArrayList<ExamStudentSpecializationTO> listStudentSpec) {
		this.listStudentSpec = listStudentSpec;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public List<KeyValueTO> getListSpecialization() {
		return listSpecialization;
	}

	public void setListSpecialization(List<KeyValueTO> listSpecialization) {

		this.listSpecialization = listSpecialization;
	}

	public List<KeyValueTO> getListCourses() {
		return listCourses;
	}

	public void setListCourses(List<KeyValueTO> listCourses) {
		this.listCourses = listCourses;
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

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public void clearPage() {
		this.course = "";
		this.scheme = "";
		this.searchSpec = "";
		this.isChecked = "";
		this.section = "";
		this.academicYear="";
		this.sectionList=null;
		this.schemeNameList=null;

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

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getSearchSpec() {
		return searchSpec;
	}

	public void setSearchSpec(String searchSpec) {
		this.searchSpec = searchSpec;
	}

	public void setSpecId(String specId) {
		this.specId = specId;
	}

	public String getSpecId() {
		return specId;
	}

	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}

	public String getSchemeNo() {
		return schemeNo;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionList(ArrayList<KeyValueTO> sectionList) {
		this.sectionList = sectionList;
	}

	public ArrayList<KeyValueTO> getSectionList() {
		return sectionList;
	}

	

	

	

}
