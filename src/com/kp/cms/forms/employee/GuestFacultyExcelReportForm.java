package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.employee.GuestFacultyTO;

public class GuestFacultyExcelReportForm extends BaseActionForm{
	private int id;
	 private String streamDetails;
	 private String department;
	 private String designation;
	 private String workLocation;
	 private String active;
	 private Map<String,String> streamMap;
	 private Map<String,String> departmentMap;
	 private Map<String,String> designationMap;
	 private Map<String,String> workLocationMap;
	 private List<GuestFacultyTO> guestFacultyToList;
	 private String method;
	 private String mode;
	 private String downloadExcel;
	 private String selectedColumnsArray[];
	 private String unselectedColumnsArray[];
	 private String selectedIndex;
	 private int maxListSize;
	 
	public int getMaxListSize() {
		return maxListSize;
	}
	public void setMaxListSize(int maxListSize) {
		this.maxListSize = maxListSize;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStreamDetails() {
		return streamDetails;
	}
	public void setStreamDetails(String streamDetails) {
		this.streamDetails = streamDetails;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getWorkLocation() {
		return workLocation;
	}
	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Map<String, String> getStreamMap() {
		return streamMap;
	}
	public void setStreamMap(Map<String, String> streamMap) {
		this.streamMap = streamMap;
	}
	public Map<String, String> getDepartmentMap() {
		return departmentMap;
	}
	public void setDepartmentMap(Map<String, String> departmentMap) {
		this.departmentMap = departmentMap;
	}
	public Map<String, String> getDesignationMap() {
		return designationMap;
	}
	public void setDesignationMap(Map<String, String> designationMap) {
		this.designationMap = designationMap;
	}
	public Map<String, String> getWorkLocationMap() {
		return workLocationMap;
	}
	public void setWorkLocationMap(Map<String, String> workLocationMap) {
		this.workLocationMap = workLocationMap;
	}

	public List<GuestFacultyTO> getGuestFacultyToList() {
		return guestFacultyToList;
	}
	public void setGuestFacultyToList(List<GuestFacultyTO> guestFacultyToList) {
		this.guestFacultyToList = guestFacultyToList;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
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
