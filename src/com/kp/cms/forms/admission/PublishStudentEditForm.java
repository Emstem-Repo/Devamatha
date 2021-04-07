package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admission.PublishStudentEditTO;

public class PublishStudentEditForm extends BaseActionForm{
	
	private int id;
	private int dupId;
	private String regNoFrom;
	private String regNoTo;
	List<String> messageList;	
	private String isWholeStudent;
	private String academicYear;
	private String programm;
	private String semester;
	private String classes;
	private List<ProgramTO> programToList;
	private String[] classIds;
	private List<PublishStudentEditTO> publishStudentEditTOs;
	private String editStartDate;
	private String editEndDate;
	
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	public String getRegNoFrom() {
		return regNoFrom;
	}
	public void setRegNoFrom(String regNoFrom) {
		this.regNoFrom = regNoFrom;
	}
	public String getRegNoTo() {
		return regNoTo;
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
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
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
	public String[] getClassIds() {
		return classIds;
	}
	public void setClassIds(String[] classIds) {
		this.classIds = classIds;
	}
	public String getIsWholeStudent() {
		return isWholeStudent;
	}
	public void setIsWholeStudent(String isWholeStudent) {
		this.isWholeStudent = isWholeStudent;
	}
	public List<PublishStudentEditTO> getPublishStudentEditTOs() {
		return publishStudentEditTOs;
	}
	public void setPublishStudentEditTOs(
			List<PublishStudentEditTO> publishStudentEditTOs) {
		this.publishStudentEditTOs = publishStudentEditTOs;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDupId() {
		return dupId;
	}
	public void setDupId(int dupId) {
		this.dupId = dupId;
	}
	public String getEditStartDate() {
		return editStartDate;
	}
	public void setEditStartDate(String editStartDate) {
		this.editStartDate = editStartDate;
	}
	public String getEditEndDate() {
		return editEndDate;
	}
	public void setEditEndDate(String editEndDate) {
		this.editEndDate = editEndDate;
	}
	
	
	

}
