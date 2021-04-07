package com.kp.cms.forms.supportrequest;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.supportrequest.CategoryTo;

public class CategoryForm extends BaseActionForm{
private Map<Integer,String> departmentMap;
private String name;
private String categoryFor;
List<CategoryTo> categoryList;
private int id;
private String departmentId;
private String mode;
private String email;


public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getMode() {
	return mode;
}
public void setMode(String mode) {
	this.mode = mode;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDepartmentId() {
	return departmentId;
}
public void setDepartmentId(String departmentId) {
	this.departmentId = departmentId;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public List<CategoryTo> getCategoryList() {
	return categoryList;
}
public void setCategoryList(List<CategoryTo> categoryList) {
	this.categoryList = categoryList;
}
public Map<Integer, String> getDepartmentMap() {
	return departmentMap;
}
public void setDepartmentMap(Map<Integer, String> departmentMap) {
	this.departmentMap = departmentMap;
}

public String getCategoryFor() {
	return categoryFor;
}
public void setCategoryFor(String categoryFor) {
	this.categoryFor = categoryFor;
}
/**
 * form validation
 */
public ActionErrors validate(ActionMapping mapping,
		HttpServletRequest request) {
	String formName = request.getParameter("formName");
	ActionErrors actionErrors = super.validate(mapping, request, formName);

	return actionErrors;
}
}
