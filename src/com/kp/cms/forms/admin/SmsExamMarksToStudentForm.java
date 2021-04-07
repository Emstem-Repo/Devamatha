package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.StudentTO;

public class SmsExamMarksToStudentForm extends BaseActionForm {
	private String examType;
	private String examName;
	private String academicYear ;
	private Map<Integer, String> classMap;
	private String[] classIds;
	private String classID;
	private Map<Integer, String> examNameList;
	private String examNameForDisplay;
	private String classNamesForDisplay;
	private List<StudentTO> studentList;
	public void clearAll()
	{
		this.examType = null;
		this.examName = null;
		this.academicYear =null;
		this.classMap = null;
		this.classID = null;
		this.classIds = null;
		this.examNameList = null;
		this.examNameForDisplay  = null;
		this.classNamesForDisplay = null;
		this.studentList = null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public Map<Integer, String> getClassMap() {
		return classMap;
	}
	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}
	public String[] getClassIds() {
		return classIds;
	}
	public void setClassIds(String[] classIds) {
		this.classIds = classIds;
	}
	public String getClassID() {
		return classID;
	}
	public void setClassID(String classID) {
		this.classID = classID;
	}
	public Map<Integer, String> getExamNameList() {
		return examNameList;
	}
	public void setExamNameList(Map<Integer, String> examNameList) {
		this.examNameList = examNameList;
	}
	public String getExamNameForDisplay() {
		return examNameForDisplay;
	}
	public void setExamNameForDisplay(String examNameForDisplay) {
		this.examNameForDisplay = examNameForDisplay;
	}
	public String getClassNamesForDisplay() {
		return classNamesForDisplay;
	}
	public void setClassNamesForDisplay(String classNamesForDisplay) {
		this.classNamesForDisplay = classNamesForDisplay;
	}
	public List<StudentTO> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<StudentTO> studentList) {
		this.studentList = studentList;
	}
	
}
