package com.kp.cms.forms.admission;

import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.forms.BaseActionForm;

public class UploadUniversityRegNoForm  extends BaseActionForm{
	private String academicYear;
	private Classes classes;
	private String classId;
	
	private FormFile csvFile;
	private Map<Integer,String> classMap;
	
	public Map<Integer, String> getClassMap() {
		return classMap;
	}
	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}
	public FormFile getCsvFile() {
		return csvFile;
	}
	public void setCsvFile(FormFile csvFile) {
		this.csvFile = csvFile;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public Classes getClasses() {
		return classes;
	}
	public void setClasses(Classes classes) {
		this.classes = classes;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	
}
