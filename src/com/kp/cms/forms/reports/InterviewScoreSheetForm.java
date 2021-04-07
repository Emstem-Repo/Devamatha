package com.kp.cms.forms.reports;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;

public class InterviewScoreSheetForm extends BaseActionForm {
	
	private List<ProgramTypeTO> programTypeList;
	private String[] interviewType;
	private String interviewStartDate;
	private String interviewEndDate;
	private String startingTimeHours;
	private String startingTimeMins;
	private String endingTimeHours;
	private String endingTimeMins;
	private String studentPerGroup;
	private int totalStudents;
	private boolean print;
	private List<String> messageList;
	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}
	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}
	public String[] getInterviewType() {
		return interviewType;
	}
	public void setInterviewType(String[] interviewType) {
		this.interviewType = interviewType;
	}
	public String getInterviewStartDate() {
		return interviewStartDate;
	}
	public void setInterviewStartDate(String interviewStartDate) {
		this.interviewStartDate = interviewStartDate;
	}
	public String getInterviewEndDate() {
		return interviewEndDate;
	}
	public void setInterviewEndDate(String interviewEndDate) {
		this.interviewEndDate = interviewEndDate;
	}
	public String getStartingTimeHours() {
		return startingTimeHours;
	}
	public void setStartingTimeHours(String startingTimeHours) {
		this.startingTimeHours = startingTimeHours;
	}
	public String getStartingTimeMins() {
		return startingTimeMins;
	}
	public void setStartingTimeMins(String startingTimeMins) {
		this.startingTimeMins = startingTimeMins;
	}
	public String getEndingTimeHours() {
		return endingTimeHours;
	}
	public void setEndingTimeHours(String endingTimeHours) {
		this.endingTimeHours = endingTimeHours;
	}
	public String getEndingTimeMins() {
		return endingTimeMins;
	}
	public void setEndingTimeMins(String endingTimeMins) {
		this.endingTimeMins = endingTimeMins;
	}
	public String getStudentPerGroup() {
		return studentPerGroup;
	}
	public void setStudentPerGroup(String studentPerGroup) {
		this.studentPerGroup = studentPerGroup;
	}
	
	public int getTotalStudents() {
		return totalStudents;
	}
	public void setTotalStudents(int totalStudents) {
		this.totalStudents = totalStudents;
	}
	public boolean isPrint() {
		return print;
	}
	public void setPrint(boolean print) {
		this.print = print;
	}
	public List<String> getMessageList() {
		return messageList;
	}
	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public void resetFields() {
		super.setProgramTypeId(null);
		super.setProgramId(null);
		super.setCourseId(null);
		this.setInterviewEndDate(null);
		this.setInterviewStartDate(null);
		this.interviewType = null;
		this.studentPerGroup=null;
		this.totalStudents=0;
		this.messageList=null;
		this.print=false;
	}
	
	
}
