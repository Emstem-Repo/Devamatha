package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.AdmissionTcDetailsTo;

public class AdmissionTcDetailsForm extends BaseActionForm{
	private String academicYear;
	private FormFile theFile;
	private String mode;
	 private String downloadExcel;
	 private String selectedColumnsArray[];
	 private String unselectedColumnsArray[];
	 private String selectedIndex;
	 private List<AdmissionTcDetailsTo> admTcDetails;
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

	public List<AdmissionTcDetailsTo> getAdmTcDetails() {
		return admTcDetails;
	}

	public void setAdmTcDetails(List<AdmissionTcDetailsTo> admTcDetails) {
		this.admTcDetails = admTcDetails;
	}

	public FormFile getTheFile() {
		return theFile;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
 		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void resetFields(){
		this.theFile=null;
		
	}
	
}
