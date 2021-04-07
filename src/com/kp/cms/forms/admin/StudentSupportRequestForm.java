package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.StudentSupportRequestTo;

public class StudentSupportRequestForm extends BaseActionForm{
	private String categoryId;
	private String description;
	private Map<Integer,String> categoryMap;
	private List<StudentSupportRequestTo> list;
	private String status;
	private int id;
	private String remarks;
	private String deptId;
	private Map<Integer,String> deptMap;
	private String noOfDays;
	private String regOrUserId;
	private List<StudentSupportRequestTo> previousList;
	private String previousName;
	private String classOrDepartment;
	private String flag;
	
	
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getPreviousName() {
		return previousName;
	}
	public void setPreviousName(String previousName) {
		this.previousName = previousName;
	}
	public String getClassOrDepartment() {
		return classOrDepartment;
	}
	public void setClassOrDepartment(String classOrDepartment) {
		this.classOrDepartment = classOrDepartment;
	}
	public List<StudentSupportRequestTo> getPreviousList() {
		return previousList;
	}
	public void setPreviousList(List<StudentSupportRequestTo> previousList) {
		this.previousList = previousList;
	}
	public String getRegOrUserId() {
		return regOrUserId;
	}
	public void setRegOrUserId(String regOrUserId) {
		this.regOrUserId = regOrUserId;
	}
	public String getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public Map<Integer, String> getDeptMap() {
		return deptMap;
	}
	public void setDeptMap(Map<Integer, String> deptMap) {
		this.deptMap = deptMap;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public List<StudentSupportRequestTo> getList() {
		return list;
	}
	public void setList(List<StudentSupportRequestTo> list) {
		this.list = list;
	}
	public Map<Integer, String> getCategoryMap() {
		return categoryMap;
	}
	public void setCategoryMap(Map<Integer, String> categoryMap) {
		this.categoryMap = categoryMap;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}
