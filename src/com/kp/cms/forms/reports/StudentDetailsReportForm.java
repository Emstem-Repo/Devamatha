package com.kp.cms.forms.reports;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.StudentTO;

public class StudentDetailsReportForm extends BaseActionForm {
	
	public int id;
	public String status;
	public List<ProgramTypeTO> programTypeList;
	public String programTypeId;
	public List<String> deaneryList;
	public String deaneryName;
	private String[] selectedClass;
	private List<StudentTO> studentToList;
	private String downloadExcel;
	private String selectedColumnsArray[];
	private String unselectedColumnsArray[];
	private String selectedIndex;
	public String semester;
	public String isFinalYrStudents;
	public String isCurrentYear;
	public String previousYears;
	
	
	public void clear(){
		this.programTypeList=null;
		this.programTypeId=null;
		this.deaneryName=null;
		this.status=null;
		this.downloadExcel=null;
		this.selectedClass=null;
		this.semester=null;
		this.isFinalYrStudents="No";
		this.isCurrentYear="current";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}
	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}
	public String getDownloadExcel() {
		return downloadExcel;
	}
	public void setDownloadExcel(String downloadExcel) {
		this.downloadExcel = downloadExcel;
	}
	public List<StudentTO> getStudentToList() {
		return studentToList;
	}
	public void setStudentToList(List<StudentTO> studentToList) {
		this.studentToList = studentToList;
	}
	public String getProgramTypeId() {
		return programTypeId;
	}
	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
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
	public List<String> getDeaneryList() {
		return deaneryList;
	}
	public void setDeaneryList(List<String> deaneryList) {
		this.deaneryList = deaneryList;
	}
	public String getDeaneryName() {
		return deaneryName;
	}
	public void setDeaneryName(String deaneryName) {
		this.deaneryName = deaneryName;
	}
	public String[] getSelectedClass() {
		return selectedClass;
	}
	public void setSelectedClass(String[] selectedClass) {
		this.selectedClass = selectedClass;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getIsFinalYrStudents() {
		return isFinalYrStudents;
	}
	public void setIsFinalYrStudents(String isFinalYrStudents) {
		this.isFinalYrStudents = isFinalYrStudents;
	}

	public String getIsCurrentYear() {
		return isCurrentYear;
	}

	public void setIsCurrentYear(String isCurrentYear) {
		this.isCurrentYear = isCurrentYear;
	}

	public String getPreviousYears() {
		return previousYears;
	}

	public void setPreviousYears(String previousYears) {
		this.previousYears = previousYears;
	}
	
	
	
	
}
