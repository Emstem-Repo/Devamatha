package com.kp.cms.forms.examallotment;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.examallotment.InvigilatorsForExamTo;

public class InvigilatorsForExamForm extends BaseActionForm{
private Map<Integer,String> examMap;
private Map<Integer,String> deanaryMap;
private Map<Integer,String> deptMap;
private Map<Integer,String> workLocationMap;
private String examId;
private String deanaryId;
private String deptId;
private List<InvigilatorsForExamTo> list;
private Map<Integer,Integer> examInvigilatorsMap;
private String tempAcademicYear;


public String getTempAcademicYear() {
	return tempAcademicYear;
}
public void setTempAcademicYear(String tempAcademicYear) {
	this.tempAcademicYear = tempAcademicYear;
}
public Map<Integer, Integer> getExamInvigilatorsMap() {
	return examInvigilatorsMap;
}
public void setExamInvigilatorsMap(Map<Integer, Integer> examInvigilatorsMap) {
	this.examInvigilatorsMap = examInvigilatorsMap;
}
public List<InvigilatorsForExamTo> getList() {
	return list;
}
public void setList(List<InvigilatorsForExamTo> list) {
	this.list = list;
}
public Map<Integer, String> getExamMap() {
	return examMap;
}
public void setExamMap(Map<Integer, String> examMap) {
	this.examMap = examMap;
}
public Map<Integer, String> getDeanaryMap() {
	return deanaryMap;
}
public void setDeanaryMap(Map<Integer, String> deanaryMap) {
	this.deanaryMap = deanaryMap;
}
public Map<Integer, String> getDeptMap() {
	return deptMap;
}
public void setDeptMap(Map<Integer, String> deptMap) {
	this.deptMap = deptMap;
}
public Map<Integer, String> getWorkLocationMap() {
	return workLocationMap;
}
public void setWorkLocationMap(Map<Integer, String> workLocationMap) {
	this.workLocationMap = workLocationMap;
}
public String getExamId() {
	return examId;
}
public void setExamId(String examId) {
	this.examId = examId;
}
public String getDeanaryId() {
	return deanaryId;
}
public void setDeanaryId(String deanaryId) {
	this.deanaryId = deanaryId;
}
public String getDeptId() {
	return deptId;
}
public void setDeptId(String deptId) {
	this.deptId = deptId;
}
public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
	String formName = request.getParameter(CMSConstants.FORMNAME);
	ActionErrors actionErrors = super.validate(mapping, request, formName);
	return actionErrors;
}
}
