package com.kp.cms.forms.attendance;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.AttnBioDataPucTo;

public class AttendanceDataMigrationForm extends BaseActionForm {
	private List<AttnBioDataPucTo> attnBioDataList;
	private String mode;
	 private String downloadExcel;
	 private String selectedColumnsArray[];
	 private String unselectedColumnsArray[];
	 private String selectedIndex;
	 private String testIdent;
	 private String courseName;
	 private Map<String,String> testIdentMap;
	 private Map<String,String> classesMap;
	 private Map<String,String> courses;

	public Map<String, String> getCourses() {
		return courses;
	}

	public void setCourses(Map<String, String> courses) {
		this.courses = courses;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getDownloadExcel() {
		return downloadExcel;
	}

	public void setDownloadExcel(String downloadExcel) {
		this.downloadExcel = downloadExcel;
	}

	public String[] getSelectedColumnsArray() {
		return selectedColumnsArray;
	}

	public void setSelectedColumnsArray(String[] selectedColumnsArray) {
		this.selectedColumnsArray = selectedColumnsArray;
	}

	public String[] getUnselectedColumnsArray() {
		return unselectedColumnsArray;
	}

	public void setUnselectedColumnsArray(String[] unselectedColumnsArray) {
		this.unselectedColumnsArray = unselectedColumnsArray;
	}

	public String getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(String selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public void setAttnBioDataList(List<AttnBioDataPucTo> attnBioDataList) {
		this.attnBioDataList = attnBioDataList;
	}

	public List<AttnBioDataPucTo> getAttnBioDataList() {
		return attnBioDataList;
	}

	public String getTestIdent() {
		return testIdent;
	}

	public void setTestIdent(String testIdent) {
		this.testIdent = testIdent;
	}
	public void reset(){
		this.testIdent=null;
		super.clear();
		
	}

	public Map<String, String> getTestIdentMap() {
		return testIdentMap;
	}

	public void setTestIdentMap(Map<String, String> testIdentMap) {
		this.testIdentMap = testIdentMap;
	}

	public Map<String, String> getClassesMap() {
		return classesMap;
	}

	public void setClassesMap(Map<String, String> classesMap) {
		this.classesMap = classesMap;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
 		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
}
