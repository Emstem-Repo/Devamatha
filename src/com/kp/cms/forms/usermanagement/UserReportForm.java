package com.kp.cms.forms.usermanagement;

import java.util.Map;

import com.kp.cms.forms.BaseActionForm;

public class UserReportForm  extends BaseActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String searchDob;
	private String searchDepartment;
	private String firstsearchName;
	private String middlesearchName;
	private String lastsearchName;
	private Map<Integer, String> department;
	private int roleId;
	
	public String getSearchDob() {
		return searchDob;
	}
	public String getSearchDepartment() {
		return searchDepartment;
	}
	public String getFirstsearchName() {
		return firstsearchName;
	}
	public String getMiddlesearchName() {
		return middlesearchName;
	}
	public String getLastsearchName() {
		return lastsearchName;
	}
	public void setSearchDob(String searchDob) {
		this.searchDob = searchDob;
	}
	public void setSearchDepartment(String searchDepartment) {
		this.searchDepartment = searchDepartment;
	}
	public void setFirstsearchName(String firstsearchName) {
		this.firstsearchName = firstsearchName;
	}
	public void setMiddlesearchName(String middlesearchName) {
		this.middlesearchName = middlesearchName;
	}
	public void setLastsearchName(String lastsearchName) {
		this.lastsearchName = lastsearchName;
	}
	public Map<Integer, String> getDepartment() {
		return department;
	}
	public void setDepartment(Map<Integer, String> department) {
		this.department = department;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
}
