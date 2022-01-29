package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamUniversityRegisterNumberEntryTO;
import com.kp.cms.to.exam.KeyValueTO;

@SuppressWarnings("serial")
public class ExamUniversityRegisterNumberEntryForm extends BaseActionForm {

	private String id;
	private String academicYear;
	private String academicYearFull;
	private int listStudentSize;
	private String studentName;
	private String programName;
	private String courseName;
	private String schemeName;
	private String specName;
	private String langName;
	private String rollNo;
	private String displayOrder;
	private String scheme;
	private String regNo;
	private ArrayList<KeyValueTO> secondLanguage;
	ArrayList<ExamUniversityRegisterNumberEntryTO> studentDetails;
	private HashMap<Integer, String> map;
	private HashMap<Integer, String> mapProgram;
	
	private Map<Integer, String> mapCourse;
	
	private ArrayList<KeyValueTO> listSpecialization;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getAcademicYearFull() {
		return academicYearFull;
	}

	public void setAcademicYearFull(String academicYearFull) {
		this.academicYearFull = academicYearFull;
	}

	public void clearPage() {
		this.programName = "";
		this.schemeName = "";
		this.specName = "";
		this.langName = "";
		this.rollNo = "";
		this.regNo = "";
	}

	public ArrayList<ExamUniversityRegisterNumberEntryTO> getStudentDetails() {
		return studentDetails;
	}

	public void setStudentDetails(
			ArrayList<ExamUniversityRegisterNumberEntryTO> studentDetails) {
		this.studentDetails = studentDetails;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public ArrayList<KeyValueTO> getListSpecialization() {
		return listSpecialization;
	}

	public void setListSpecialization(ArrayList<KeyValueTO> listSpecialization) {
		this.listSpecialization = listSpecialization;
	}

	public HashMap<Integer, String> getMap() {
		return map;
	}

	public void setMap(HashMap<Integer, String> map) {
		this.map = map;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
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

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public String getLangName() {
		return langName;
	}

	public void setLangName(String langName) {
		this.langName = langName;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}

	public ArrayList<KeyValueTO> getSecondLanguage() {
		return secondLanguage;
	}

	public void setSecondLanguage(ArrayList<KeyValueTO> secondLanguage) {
		this.secondLanguage = secondLanguage;
	}

	public int getListStudentSize() {
		return listStudentSize;
	}

	public void setListStudentSize(int listStudentSize) {
		this.listStudentSize = listStudentSize;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public void setMapProgram(HashMap<Integer, String> mapProgram) {
		this.mapProgram = mapProgram;
	}

	public HashMap<Integer, String> getMapProgram() {
		return mapProgram;
	}

	public void setMapCourse(Map<Integer, String> mapCourse) {
		this.mapCourse = mapCourse;
	}

	public Map<Integer, String> getMapCourse() {
		return mapCourse;
	}

	

	

}
