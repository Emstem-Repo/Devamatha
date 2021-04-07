package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.StudentTO;

public class BulkSmsToStudentForm  extends BaseActionForm{
	private String academicYear ;
	private Map<Integer, String> classMap;
	private String[] classIds;
	private List<StudentTO> studentList;
	private String smsMessage;
	private String classNamesForDisplay;
	public void clearAll() {
		
		this.academicYear = null;
		this.classMap = null;
		this.classIds = null;
		this.studentList = null;
		this.smsMessage = null;
		this.classNamesForDisplay = null;
		
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public Map<Integer, String> getClassMap() {
		return classMap;
	}
	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}
	public String[] getClassIds() {
		return classIds;
	}
	public void setClassIds(String[] classIds) {
		this.classIds = classIds;
	}
	public List<StudentTO> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<StudentTO> studentList) {
		this.studentList = studentList;
	}
	public String getSmsMessage() {
		return smsMessage;
	}
	public void setSmsMessage(String smsMessage) {
		this.smsMessage = smsMessage;
	}
	public String getClassNamesForDisplay() {
		return classNamesForDisplay;
	}
	public void setClassNamesForDisplay(String classNamesForDisplay) {
		this.classNamesForDisplay = classNamesForDisplay;
	}
	
	
 
}
