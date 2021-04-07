package com.kp.cms.forms.exam;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.GrievanceStudentTo;


public class GrievanceStudentForm extends BaseActionForm{
	
	private int id;
	private String examId;
	private String classId;
	Map<Integer, String> examNameMap;
	Map<Integer, String> classMap;
	private String examType;
	private List<GrievanceStudentTo> studentList;
	private String courseCode;
	private String courseTitle;
	private String studentName;
	private String registerNo;
	private String semester;
	private String grievanceNo;
	private List<GrievanceStudentTo> studentresultList;
	private String roleId;
	private String date;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public Map<Integer, String> getExamNameMap() {
		return examNameMap;
	}
	public void setExamNameMap(Map<Integer, String> examNameMap) {
		this.examNameMap = examNameMap;
	}
	public Map<Integer, String> getClassMap() {
		return classMap;
	}
	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public List<GrievanceStudentTo> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<GrievanceStudentTo> studentList) {
		this.studentList = studentList;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getCourseTitle() {
		return courseTitle;
	}
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getGrievanceNo() {
		return grievanceNo;
	}
	public void setGrievanceNo(String grievanceNo) {
		this.grievanceNo = grievanceNo;
	}
	public List<GrievanceStudentTo> getStudentresultList() {
		return studentresultList;
	}
	public void setStudentresultList(List<GrievanceStudentTo> studentresultList) {
		this.studentresultList = studentresultList;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void resetFields() {
		this.examType=null;
		this.examId=null;
		this.classId=null;
		this.grievanceNo=null;
	}


}
