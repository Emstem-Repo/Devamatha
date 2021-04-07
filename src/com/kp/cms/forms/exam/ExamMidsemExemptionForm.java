package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.StudentTO;

public class ExamMidsemExemptionForm extends BaseActionForm {
	
	private int id;
	private String regNo;
	private String year;
	private Map<Integer, String> examList;
	private String examId;
	private String examName;
	private Map<Integer, String> classList;
	private String classId;
	private String className;
	private String studentName;
	private String reason;
	private StudentTO student;
	private int examMidsemExemptionId;
	
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Map<Integer, String> getExamList() {
		return examList;
	}

	public void setExamList(Map<Integer, String> examList) {
		this.examList = examList;
	}

	public Map<Integer, String> getClassList() {
		return classList;
	}

	public void setClassList(Map<Integer, String> classList) {
		this.classList = classList;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}
	

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	
	public StudentTO getStudent() {
		return student;
	}

	public void setStudent(StudentTO student) {
		this.student = student;
	}

	public int getExamMidsemExemptionId() {
		return examMidsemExemptionId;
	}

	public void setExamMidsemExemptionId(int examMidsemExemptionId) {
		this.examMidsemExemptionId = examMidsemExemptionId;
	}

	public void clearAll()
	{
		this.regNo=null;
		this.year=null;
		this.examId=null;
		this.examName=null;
		this.classList=null;
		this.classId=null;
		this.className=null;
		this.studentName=null;
		this.examMidsemExemptionId=0;
		this.reason=null;
		
	}
	
	
	
}
