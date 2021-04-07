package com.kp.cms.to.admission;

import java.util.List;

import com.kp.cms.to.admin.GradeTO;

public class InterviewResultTO {

	private int id;
//	private int interviewResultDetailId;
//	private Set<InterviewResultDetail> interviewResultDetails;
	private List<InterviewResultDetailTO> interviewResultDetail;
	private int interviewTypeId;
	private String interviewType;
	private String interviewStatus;
	private String marksObtained;
	private String comments;
	private String referredBy;
	private String selectedPrefId;
	private String applicationId;
	private String applicationNo;
	private String applicantName;
	private String appliedYear;
	private String interviewStatusId;
//	private List<String> gradeObtainedList;
	private InterviewProgramCourseTO interviewProgramCourseTO;
	private String courseId;
	private String interviewProgramCourseId;
	private InterviewStatusTO interviewStatusTO;
	private GradeTO gradeTO;
	private AdmApplnTO admApplnTO;
	private String interviewDate;
	private String startTimeHours;
	private String startTimeMins;
	private String endTimeHours;
	private String endTimeMins;
	private int interviewSubroundId;
	private String subroundName;
	private List<GradeTO> gradeList;
	private String percentage;
	private List<InterviewResultTO> selectedCandidates;
	private int interviewPerPanel;
	
	
	public String getSubroundName() {
		return subroundName;
	}

	public void setSubroundName(String subroundName) {
		this.subroundName = subroundName;
	}

	public int getInterviewSubroundId() {
		return interviewSubroundId;
	}

	public void setInterviewSubroundId(int interviewSubroundId) {
		this.interviewSubroundId = interviewSubroundId;
	}

	public InterviewStatusTO getInterviewStatusTO() {
		return interviewStatusTO;
	}

	public void setInterviewStatusTO(InterviewStatusTO interviewStatusTO) {
		this.interviewStatusTO = interviewStatusTO;
	}

	public GradeTO getGradeTO() {
		return gradeTO;
	}

	public void setGradeTO(GradeTO gradeTO) {
		this.gradeTO = gradeTO;
	}

	public AdmApplnTO getAdmApplnTO() {
		return admApplnTO;
	}

	public void setAdmApplnTO(AdmApplnTO admApplnTO) {
		this.admApplnTO = admApplnTO;
	}

	public int getInterviewTypeId() {
		return interviewTypeId;
	}

	public void setInterviewTypeId(int interviewTypeId) {
		this.interviewTypeId = interviewTypeId;
	}
	
	public String getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(String interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getStartTimeHours() {
		return startTimeHours;
	}

	public void setStartTimeHours(String startTimeHours) {
		this.startTimeHours = startTimeHours;
	}

	public String getStartTimeMins() {
		return startTimeMins;
	}

	public void setStartTimeMins(String startTimeMins) {
		this.startTimeMins = startTimeMins;
	}

	public String getEndTimeHours() {
		return endTimeHours;
	}

	public void setEndTimeHours(String endTimeHours) {
		this.endTimeHours = endTimeHours;
	}

	public String getEndTimeMins() {
		return endTimeMins;
	}

	public void setEndTimeMins(String endTimeMins) {
		this.endTimeMins = endTimeMins;
	}

	public String getInterviewProgramCourseId() {
		return interviewProgramCourseId;
	}

	public void setInterviewProgramCourseId(String interviewProgramCourseId) {
		this.interviewProgramCourseId = interviewProgramCourseId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getAppliedYear() {
		return appliedYear;
	}

	public void setAppliedYear(String appliedYear) {
		this.appliedYear = appliedYear;
	}	

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public InterviewProgramCourseTO getInterviewProgramCourseTO() {
		return interviewProgramCourseTO;
	}

	public void setInterviewProgramCourseTO(
			InterviewProgramCourseTO interviewProgramCourseTO) {
		this.interviewProgramCourseTO = interviewProgramCourseTO;
	}

	public String getSelectedPrefId() {
		return selectedPrefId;
	}

	public void setSelectedPrefId(String selectedPrefId) {
		this.selectedPrefId = selectedPrefId;
	}

	public String getReferredBy() {
		return referredBy;
	}

	public void setReferredBy(String referredBy) {
		this.referredBy = referredBy;
	}

	public String getInterviewType() {
		return interviewType;
	}

	public void setInterviewType(String interviewType) {
		this.interviewType = interviewType;
	}

	public String getInterviewStatus() {
		return interviewStatus;
	}

	public void setInterviewStatus(String interviewStatus) {
		this.interviewStatus = interviewStatus;
	}

	public String getMarksObtained() {
		return marksObtained;
	}

	public void setMarksObtained(String marksObtained) {
		this.marksObtained = marksObtained;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getInterviewStatusId() {
		return interviewStatusId;
	}

	public void setInterviewStatusId(String interviewStatusId) {
		this.interviewStatusId = interviewStatusId;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public List<InterviewResultDetailTO> getInterviewResultDetail() {
		return interviewResultDetail;
	}

	public void setInterviewResultDetail(
			List<InterviewResultDetailTO> interviewResultDetail) {
		this.interviewResultDetail = interviewResultDetail;
	}

	public List<GradeTO> getGradeList() {
		return gradeList;
	}

	public void setGradeList(List<GradeTO> gradeList) {
		this.gradeList = gradeList;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public List<InterviewResultTO> getSelectedCandidates() {
		return selectedCandidates;
	}

	public void setSelectedCandidates(List<InterviewResultTO> selectedCandidates) {
		this.selectedCandidates = selectedCandidates;
	}

	public int getInterviewPerPanel() {
		return interviewPerPanel;
	}

	public void setInterviewPerPanel(int interviewPerPanel) {
		this.interviewPerPanel = interviewPerPanel;
	}
	

//	public List<String> getGradeObtainedList() {
//		return gradeObtainedList;
//	}
//
//	public void setGradeObtainedList(List<String> gradeObtainedList) {
//		this.gradeObtainedList = gradeObtainedList;
//	}
//
//	public int getInterviewResultDetailId() {
//		return interviewResultDetailId;
//	}
//
//	public void setInterviewResultDetailId(int interviewResultDetailId) {
//		this.interviewResultDetailId = interviewResultDetailId;
//	}
//
//	public Set<InterviewResultDetail> getInterviewResultDetails() {
//		return interviewResultDetails;
//	}
//
//	public void setInterviewResultDetails(
//			Set<InterviewResultDetail> interviewResultDetails) {
//		this.interviewResultDetails = interviewResultDetails;
//	}	
	@Override
	public boolean equals(Object obj) {
	    if(obj instanceof InterviewResultTO)
	    {
	    	InterviewResultTO temp = (InterviewResultTO) obj;
	    	if(this.admApplnTO!=null && temp.admApplnTO!=null)
	        if(this.admApplnTO.getId() == temp.admApplnTO.getId())
	            return true;
	    }
	    return false;
	}
	public static double count=0.0;
	@Override
	public int hashCode() {
		if(this.admApplnTO!=null)
	    return (Integer.valueOf(this.admApplnTO.getId()).hashCode());
		else{
			return (Double.valueOf(++count).hashCode());
		}
	}
}