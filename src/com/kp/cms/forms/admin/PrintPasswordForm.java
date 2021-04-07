package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTO;

public class PrintPasswordForm extends BaseActionForm {
	
	private String regNoFrom;
	private String regNoTo;
	List<String> messageList;	
	private String isRollNo;
	private String isStudent;
	private String academicYear;
	private String programm;
	private String semester;
	private String classes;
	private List<ProgramTO> programToList;
	
	public String getRegNoFrom() {
		return regNoFrom;
	}
	public String getRegNoTo() {
		return regNoTo;
	}
	public void setRegNoFrom(String regNoFrom) {
		this.regNoFrom = regNoFrom;
	}
	public void setRegNoTo(String regNoTo) {
		this.regNoTo = regNoTo;
	}
	public List<String> getMessageList() {
		return messageList;
	}
	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}
	public String getIsRollNo() {
		return isRollNo;
	}
	public void setIsRollNo(String isRollNo) {
		this.isRollNo = isRollNo;
	}
	
	public String getIsStudent() {
		return isStudent;
	}
	public void setIsStudent(String isStudent) {
		this.isStudent = isStudent;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getProgramm() {
		return programm;
	}
	public void setProgramm(String programm) {
		this.programm = programm;
	}
	
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public List<ProgramTO> getProgramToList() {
		return programToList;
	}
	public void setProgramToList(List<ProgramTO> programToList) {
		this.programToList = programToList;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		regNoFrom = null;
		regNoTo = null;
		programm=null;
		semester=null;
		classes=null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
}
