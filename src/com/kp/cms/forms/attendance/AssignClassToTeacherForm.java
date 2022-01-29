package com.kp.cms.forms.attendance;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.TeacherClassEntryTo;

public class AssignClassToTeacherForm extends BaseActionForm {
private int id;
private String academicYear;
private String teachers;
private String classesSelected;
private Map<Integer, String> classMap;
private Map<Integer,String> teachersMap; 
private List<TeacherClassEntryTo> listTeacherClassEntry;
private String teacherType;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

public String getTeachers() {
	return teachers;
}
public void setTeachers(String teachers) {
	this.teachers = teachers;
}
public String getClassesSelected() {
	return classesSelected;
}
public void setClassesSelected(String classesSelected) {
	this.classesSelected = classesSelected;
}
public Map<Integer, String> getClassMap() {
	return classMap;
}
public void setClassMap(Map<Integer, String> classMap) {
	this.classMap = classMap;
}
public Map<Integer, String> getTeachersMap() {
	return teachersMap;
}
public void setTeachersMap(Map<Integer, String> teachersMap) {
	this.teachersMap = teachersMap;
}
public List<TeacherClassEntryTo> getListTeacherClassEntry() {
	return listTeacherClassEntry;
}
public void setListTeacherClassEntry(
		List<TeacherClassEntryTo> listTeacherClassEntry) {
	this.listTeacherClassEntry = listTeacherClassEntry;
}
@Override
public void reset(ActionMapping mapping, HttpServletRequest request) {
	classesSelected = null;
	teachers = null;
	teacherType=null;
	this.setYear(null);
	
}
@Override
public ActionErrors validate(ActionMapping mapping,
		HttpServletRequest request) {
	
	String formName = request.getParameter("formName");
	//ActionErrors actionErrors = new ActionErrors();
	//actionErrors = super.validate(mapping, request, formName);

	return super.validate(mapping, request, formName);
}

public String getTeacherType() {
	return teacherType;
}
public void setTeacherType(String teacherType) {
	this.teacherType = teacherType;
}
public String getAcademicYear() {
	return academicYear;
}
public void setAcademicYear(String academicYear) {
	this.academicYear = academicYear;
}
}
