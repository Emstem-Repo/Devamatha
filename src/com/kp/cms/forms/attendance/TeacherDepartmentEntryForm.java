package com.kp.cms.forms.attendance;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.TeacherDepartmentTO;

public class TeacherDepartmentEntryForm extends BaseActionForm{
 private Map<Integer,String> teachersMap;
 private Map<Integer,String> departmentMap;
 private String teacher;
 private String department;
 private int id;
 private List<TeacherDepartmentTO> teacherDepartmentTO;
public Map<Integer, String> getTeachersMap() {
	return teachersMap;
}
public void setTeachersMap(Map<Integer, String> teachersMap) {
	this.teachersMap = teachersMap;
}
public Map<Integer, String> getDepartmentMap() {
	return departmentMap;
}
public void setDepartmentMap(Map<Integer, String> departmentMap) {
	this.departmentMap = departmentMap;
}
public String getTeacher() {
	return teacher;
}
public void setTeacher(String teacher) {
	this.teacher = teacher;
}
public String getDepartment() {
	return department;
}
public void setDepartment(String department) {
	this.department = department;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public List<TeacherDepartmentTO> getTeacherDepartmentTO() {
	return teacherDepartmentTO;
}
public void setTeacherDepartmentTO(List<TeacherDepartmentTO> teacherDepartmentTO) {
	this.teacherDepartmentTO = teacherDepartmentTO;
}
public ActionErrors validate(ActionMapping mapping,
		HttpServletRequest request) {
	String formName = request.getParameter(CMSConstants.FORMNAME);
	ActionErrors actionErrors = super.validate(mapping, request, formName);

	return actionErrors;
}
}
