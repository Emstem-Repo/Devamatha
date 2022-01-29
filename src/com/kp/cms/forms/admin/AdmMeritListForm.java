package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.AdmBioDataCJCTo;
import com.kp.cms.to.admin.AdmMeritTO;

public class AdmMeritListForm extends BaseActionForm{
	private FormFile theFile;
	private List<Integer> appNos;
	private String academicYear;
	private List<String> regNos;
	private List<AdmMeritTO> admMeritTO;
	 private String mode;
	 private String downloadExcel;
	 private String selectedColumnsArray[];
	 private String unselectedColumnsArray[];
	 private String selectedIndex;
	 private Map<String,String> courses;
	 private String courseName;
	public Map<String, String> getCourses() {
		return courses;
	}
	public void setCourses(Map<String, String> courses) {
		this.courses = courses;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public FormFile getTheFile() {
		return theFile;
	}
	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}
	public void resetFields(){
		this.theFile=null;
	}
	public List<Integer> getAppNos() {
		return appNos;
	}
	public void setAppNos(List<Integer> appNos) {
		this.appNos = appNos;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public void reset(){
		this.theFile=null;
		this.academicYear=null;
		this.regNos=null;
		this.appNos=null;
		this.courseName=null;
	}
	public List<String> getRegNos() {
		return regNos;
	}
	public void setRegNos(List<String> regNos) {
		this.regNos = regNos;
	}
	public List<AdmMeritTO> getAdmMeritTO() {
		return admMeritTO;
	}
	public void setAdmMeritTO(List<AdmMeritTO> admMeritTO) {
		this.admMeritTO = admMeritTO;
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
}
