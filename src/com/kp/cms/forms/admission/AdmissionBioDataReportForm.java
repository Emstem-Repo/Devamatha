package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.AdmBioDataCJCTo;

public class AdmissionBioDataReportForm extends BaseActionForm {
	
	private List<AdmBioDataCJCTo> admBioDataList;
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

	public void setAdmBioDataList(List<AdmBioDataCJCTo> admBioDataList) {
		this.admBioDataList = admBioDataList;
	}

	public List<AdmBioDataCJCTo> getAdmBioDataList() {
		return admBioDataList;
	}
}
