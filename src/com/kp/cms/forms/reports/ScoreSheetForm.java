package com.kp.cms.forms.reports;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;

public class ScoreSheetForm extends BaseActionForm{
	
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
	private String examCenterId;
	private Map<Integer,String> examCenterMap;
	private String programid;
	
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
		this.examCenterId=null;
		this.examCenterMap=null;
		this.programid=null;
	}
	public String getExamCenterId() {
		return examCenterId;
	}
	public void setExamCenterId(String examCenterId) {
		this.examCenterId = examCenterId;
	}
	public Map<Integer, String> getExamCenterMap() {
		return examCenterMap;
	}
	public void setExamCenterMap(Map<Integer, String> examCenterMap) {
		this.examCenterMap = examCenterMap;
	}
	public String getProgramid() {
		return programid;
	}
	public void setProgramid(String programid) {
		this.programid = programid;
	}
	
	
}
