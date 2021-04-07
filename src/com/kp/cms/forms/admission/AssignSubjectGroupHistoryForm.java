package com.kp.cms.forms.admission;

import java.util.List;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.AssignSubjectGroupHistoryTO;
import com.kp.cms.to.attendance.SubjectGroupDetailsTo;

public class AssignSubjectGroupHistoryForm extends BaseActionForm{
  private int id;
  private String method;
  private String year;
  private String classSchemeWiseId;
  private boolean flag;
  private boolean flag1;
  private int halfLength;
  private List<AssignSubjectGroupHistoryTO> studentDetailsList;
  private List<SubjectGroupDetailsTo> subjectGroupList;
  private List<SubjectGroupDetailsTo> subjectGroupsDetailsList;
  private List<String> list;
  private String displayMessage;
  private String displayMsg;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getMethod() {
	return method;
}
public void setMethod(String method) {
	this.method = method;
}
public String getYear() {
	return year;
}
public void setYear(String year) {
	this.year = year;
}

public boolean isFlag() {
	return flag;
}
public void setFlag(boolean flag) {
	this.flag = flag;
}
public boolean isFlag1() {
	return flag1;
}
public void setFlag1(boolean flag1) {
	this.flag1 = flag1;
}

public void setClassSchemeWiseId(String classSchemeWiseId) {
	this.classSchemeWiseId = classSchemeWiseId;
}
public String getClassSchemeWiseId() {
	return classSchemeWiseId;
}
public void setStudentDetailsList(List<AssignSubjectGroupHistoryTO> studentDetailsList) {
	this.studentDetailsList = studentDetailsList;
}
public List<AssignSubjectGroupHistoryTO> getStudentDetailsList() {
	return studentDetailsList;
}
public void setHalfLength(int halfLength) {
	this.halfLength = halfLength;
}
public int getHalfLength() {
	return halfLength;
}
public void setSubjectGroupList(List<SubjectGroupDetailsTo> subjectGroupList) {
	this.subjectGroupList = subjectGroupList;
}
public List<SubjectGroupDetailsTo> getSubjectGroupList() {
	return subjectGroupList;
}
public void setSubjectGroupsDetailsList(List<SubjectGroupDetailsTo> subjectGroupsDetailsList) {
	this.subjectGroupsDetailsList = subjectGroupsDetailsList;
}
public List<SubjectGroupDetailsTo> getSubjectGroupsDetailsList() {
	return subjectGroupsDetailsList;
}
/**
 * 
 */
public void clearList() {
	
	this.setStudentDetailsList(null);
	this.setSubjectGroupList(null);
	this.setSubjectGroupsDetailsList(null);
	this.setSubjectGroupId(null);
	this.flag=false;
	this.flag1=false;
}
/**
 * 
 */
public void clearList1() {
	this.setStudentDetailsList(null);
	this.setSubjectGroupList(null);
	this.setSubjectGroupsDetailsList(null);
	this.setSubjectGroupId(null);
}
public List<String> getList() {
	return list;
}
public void setList(List<String> list) {
	this.list = list;
}
public String getDisplayMessage() {
	return displayMessage;
}
public void setDisplayMessage(String displayMessage) {
	this.displayMessage = displayMessage;
}
public String getDisplayMsg() {
	return displayMsg;
}
public void setDisplayMsg(String displayMsg) {
	this.displayMsg = displayMsg;
}
}
