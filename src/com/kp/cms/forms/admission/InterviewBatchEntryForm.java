package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.InterviewResultTO;

public class InterviewBatchEntryForm extends BaseActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<InterviewResultTO> selectedCandidates;
	private List<ProgramTypeTO> programTypeList;
	private Map<Integer, String> interviewStatus;
	private Map<Integer, String> grades;
	private String appliedYear;
	private String interviewType;
	private String interviewStatusId;
	private String gradeObtainedId;
	private String interviewDate;
	private String startingTimeHours;
	private String startingTimeMins;
	private String endingTimeHours;
	private String endingTimeMins;
	private Map<Integer, String> interviewSubrounds;
	private String interviewSubroundId;
	private String subroundCount;
	private int interviewersPerPanel;
	
	public String getSubroundCount() {
		return subroundCount;
	}

	public void setSubroundCount(String subroundCount) {
		this.subroundCount = subroundCount;
	}

	public String getInterviewSubroundId() {
		return interviewSubroundId;
	}

	public void setInterviewSubroundId(String interviewSubroundId) {
		this.interviewSubroundId = interviewSubroundId;
	}

	public Map<Integer, String> getInterviewSubrounds() {
		return interviewSubrounds;
	}

	public void setInterviewSubrounds(Map<Integer, String> interviewSubrounds) {
		this.interviewSubrounds = interviewSubrounds;
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

	public String getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(String interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getInterviewStatusId() {
		return interviewStatusId;
	}

	public void setInterviewStatusId(String interviewStatusId) {
		this.interviewStatusId = interviewStatusId;
	}

	public String getGradeObtainedId() {
		return gradeObtainedId;
	}

	public void setGradeObtainedId(String gradeObtainedId) {
		this.gradeObtainedId = gradeObtainedId;
	}
	
	public Map<Integer, String> getInterviewStatus() {
		return interviewStatus;
	}

	public void setInterviewStatus(Map<Integer, String> interviewStatus) {
		this.interviewStatus = interviewStatus;
	}

	public Map<Integer, String> getGrades() {
		return grades;
	}

	public void setGrades(Map<Integer, String> grades) {
		this.grades = grades;
	}
	
	public String getInterviewType() {
		return interviewType;
	}

	public void setInterviewType(String interviewType) {
		this.interviewType = interviewType;
	}

	public String getAppliedYear() {
		return appliedYear;
	}

	public void setAppliedYear(String appliedYear) {
		this.appliedYear = appliedYear;
	}
	
	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	public List<InterviewResultTO> getSelectedCandidates() {
		return selectedCandidates;
	}

	public void setSelectedCandidates(List<InterviewResultTO> selectedCandidates) {
		this.selectedCandidates = selectedCandidates;
	}
	
	public int getInterviewersPerPanel() {
		return interviewersPerPanel;
	}

	public void setInterviewersPerPanel(int interviewersPerPanel) {
		this.interviewersPerPanel = interviewersPerPanel;
	}

	@Override
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
		this.setAppliedYear(null);
		super.setInterviewTypeId(null);
		this.setInterviewDate(null);
		this.setInterviewSubroundId(null);
		this.startingTimeHours ="";
		this.startingTimeMins = "";
		this.endingTimeHours = "";
		this.endingTimeMins = "";
	}
}
