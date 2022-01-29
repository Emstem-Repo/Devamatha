package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.RevaluationOrRetotallingMarksEntryTo;

public class RevaluationOrRetotallingMarksEntryForm extends BaseActionForm{
private String examType;
private String examId;
private Map<Integer, String> examNameList;
private String registerNo;
private int studentId;
private List<RevaluationOrRetotallingMarksEntryTo> studentDetailsList;
private boolean flag; 
private boolean flag1;
private int count;
private String year;
private Integer schemeno;
private Map<Integer, Double> maxMarksMap;
private Integer selectYear;
private int courseid;
public String getExamType() {
	return examType;
}
public void setExamType(String examType) {
	this.examType = examType;
}
public String getExamId() {
	return examId;
}
public void setExamId(String examId) {
	this.examId = examId;
}
public Map<Integer, String> getExamNameList() {
	return examNameList;
}
public void setExamNameList(Map<Integer, String> examNameList) {
	this.examNameList = examNameList;
}
public ActionErrors validate(ActionMapping mapping,
		HttpServletRequest request) {
	String formName = request.getParameter("formName");
	ActionErrors actionErrors = super.validate(mapping, request, formName);
	
	return actionErrors;
}
public void resetFields() {
	this.examType="Regular";
	this.flag=false;
	this.studentDetailsList=null;
	this.registerNo=null;
	this.examId=null;
	this.year=null;
}
public String getRegisterNo() {
	return registerNo;
}
public void setRegisterNo(String registerNo) {
	this.registerNo = registerNo;
}
public int getStudentId() {
	return studentId;
}
public void setStudentId(int studentId) {
	this.studentId = studentId;
}
public List<RevaluationOrRetotallingMarksEntryTo> getStudentDetailsList() {
	return studentDetailsList;
}
public void setStudentDetailsList(
		List<RevaluationOrRetotallingMarksEntryTo> studentDetailsList) {
	this.studentDetailsList = studentDetailsList;
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
public int getCount() {
	return count;
}
public void setCount(int count) {
	this.count = count;
}
public String getYear() {
	return year;
}
public void setYear(String year) {
	this.year = year;
}
public Map<Integer, Double> getMaxMarksMap() {
	return maxMarksMap;
}
public void setMaxMarksMap(Map<Integer, Double> maxMarksMap) {
	this.maxMarksMap = maxMarksMap;
}

public Integer getSelectYear() {
	return selectYear;
}
public void setSelectYear(Integer selectYear) {
	this.selectYear = selectYear;
}
public Integer getSchemeno() {
	return schemeno;
}
public void setSchemeno(Integer schemeno) {
	this.schemeno = schemeno;
}
public int getCourseid() {
	return courseid;
}
public void setCourseid(int courseid) {
	this.courseid = courseid;
}




}
