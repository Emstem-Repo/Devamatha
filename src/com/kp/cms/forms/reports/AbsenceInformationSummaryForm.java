package com.kp.cms.forms.reports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.reports.AbsenceInformationSummaryTO;

public class AbsenceInformationSummaryForm extends BaseActionForm {
	
	private String semister;
	private String academicYear;
	private String startDate;
	private String endDate;
	private String courseName;
	private List<AbsenceInformationSummaryTO> absenceInfoSummaryList;
	private String startRegisterNo;
	private String endRegisterNo;
	private String startRollNo;
	private String endRollNo;
	private String[] classesName;
	private List<String> subjectList;
	private Map<Integer, String> classMap = new HashMap<Integer, String>();	
	
	public String getSemister() {
		return semister;
	}

	public void setSemister(String semister) {
		this.semister = semister;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
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

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void resetFields() {
		this.classesName = null;
		this.semister = null;
		this.academicYear = null;
		this.startDate = null;
		this.endDate = null;
		this.courseName = null;
		this.startRegisterNo= null;
		this.endRegisterNo= null;
		this.startRollNo= null;
		this.endRollNo= null;
		super.setProgramTypeId(null);
		super.setProgramId(null);
		super.setCourseId(null);
	}

	public List<AbsenceInformationSummaryTO> getAbsenceInfoSummaryList() {
		return absenceInfoSummaryList;
	}

	public void setAbsenceInfoSummaryList(
			List<AbsenceInformationSummaryTO> absenceInfoSummaryList) {
		this.absenceInfoSummaryList = absenceInfoSummaryList;
	}

	public List<String> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<String> subjectList) {
		this.subjectList = subjectList;
	}

	public String getStartRegisterNo() {
		return startRegisterNo;
	}

	public void setStartRegisterNo(String startRegisterNo) {
		this.startRegisterNo = startRegisterNo;
	}

	public String getEndRegisterNo() {
		return endRegisterNo;
	}

	public void setEndRegisterNo(String endRegisterNo) {
		this.endRegisterNo = endRegisterNo;
	}

	public String getStartRollNo() {
		return startRollNo;
	}

	public void setStartRollNo(String startRollNo) {
		this.startRollNo = startRollNo;
	}

	public String getEndRollNo() {
		return endRollNo;
	}

	public void setEndRollNo(String endRollNo) {
		this.endRollNo = endRollNo;
	}

	public Map<Integer, String> getClassMap() {
		return classMap;
	}

	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}

	public String[] getClassesName() {
		return classesName;
	}

	public void setClassesName(String[] classesName) {
		this.classesName = classesName;
	}

}
