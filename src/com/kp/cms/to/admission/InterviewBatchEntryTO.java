package com.kp.cms.to.admission;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InterviewBatchEntryTO implements Serializable {

	private int applicationId;
	private int applicationNo;
	private int appliedYear;
	private String applicantName;
	private Integer interviewStatusId;
	private ArrayList<InterviewSubroundTO> grade_interviewerList;
	private Integer totalInterviewers = 0;
	private int interviewProgramCourseId;
	private int courseId;
	private String comments;
	private Integer selectedPrefId = 0;

	public void addGrade_interviewerList(
			ArrayList<InterviewSubroundTO> grade_interviewerList) {

		if (this.grade_interviewerList == null) {
			this.grade_interviewerList = grade_interviewerList;
		} else {
			this.grade_interviewerList.addAll(grade_interviewerList);
		}
	}

	public void ensureAllSubRounds(ArrayList<Integer> listSubRoundIDs,
			int noOfInterviewers) {
	
		ArrayList<Integer> listSubRoundIDs_forUse = new ArrayList<Integer>(listSubRoundIDs);
		for (InterviewSubroundTO ito : grade_interviewerList) {
			if (listSubRoundIDs_forUse.contains(ito.getSubroundId())) {
				listSubRoundIDs_forUse.remove(Integer.valueOf(ito.getSubroundId()));
			}
		}
		if (listSubRoundIDs_forUse.size() > 0) {
			for (Integer subId : listSubRoundIDs_forUse) {
				for (int i = 1; i <= noOfInterviewers; i++) {
					InterviewSubroundTO iSTO = new InterviewSubroundTO();
					iSTO.setSubroundId_noOfInterviewer(subId + "_" + i);
					grade_interviewerList.add(iSTO);
				}
			}
		}

	}

	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public int getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(int applicationNo) {
		this.applicationNo = applicationNo;
	}

	public int getAppliedYear() {
		return appliedYear;
	}

	public void setAppliedYear(int appliedYear) {
		this.appliedYear = appliedYear;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public Integer getInterviewStatusId() {
		return interviewStatusId;
	}

	public void setInterviewStatusId(Integer interviewStatusId) {
		this.interviewStatusId = interviewStatusId;
	}

	public void setGrade_interviewerList(
			ArrayList<InterviewSubroundTO> grade_interviewerList) {
		this.grade_interviewerList = grade_interviewerList;
	}

	public ArrayList<InterviewSubroundTO> getGrade_interviewerList() {
		Collections.sort(this.grade_interviewerList);
		return this.grade_interviewerList;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getComments() {
		return comments;
	}

	public void setTotalInterviewers(Integer totalInterviewers) {
		this.totalInterviewers = totalInterviewers;
	}

	public Integer getTotalInterviewers() {
		return totalInterviewers;
	}

	public int getInterviewProgramCourseId() {
		return interviewProgramCourseId;
	}

	public void setInterviewProgramCourseId(int interviewProgramCourseId) {
		this.interviewProgramCourseId = interviewProgramCourseId;
	}

	public void setTotalInterviewers_add(Integer noOfInterviewsPerPanel) {
		totalInterviewers = totalInterviewers + noOfInterviewsPerPanel;

	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setSelectedPrefId(Integer selectedPrefId) {
		this.selectedPrefId = selectedPrefId;
	}

	public Integer getSelectedPrefId() {
		return selectedPrefId;
	}

}
