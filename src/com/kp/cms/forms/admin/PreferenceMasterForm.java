package com.kp.cms.forms.admin;

import com.kp.cms.forms.BaseActionForm;

public class PreferenceMasterForm  extends BaseActionForm {
	private String programPreference;
	private String coursedPreference;
	private String programTypeName;
	private String courseName;
	private String programName;
	private String method;

	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getProgramPreference() {
		return programPreference;
	}
	public void setProgramPreference(String programPreference) {
		this.programPreference = programPreference;
	}
	public String getCoursedPreference() {
		return coursedPreference;
	}
	public void setCoursedPreference(String coursedPreference) {
		this.coursedPreference = coursedPreference;
	}
	public String getProgramTypeName() {
		return programTypeName;
	}
	public void setProgramTypeName(String programTypeName) {
		this.programTypeName = programTypeName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	
	
}
