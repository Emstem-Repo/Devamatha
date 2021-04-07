package com.kp.cms.to.admission;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.kp.cms.to.admin.CandidatePreferenceTO;
import com.kp.cms.to.admin.StudentCourseAllotmentTo;

/**
 * 
 * @author kshirod.k 
 * A TO class for Admission Status
 * 
 */

@SuppressWarnings("serial")
public class AdmissionStatusTO implements Serializable {
	
	private int id;
	private int applicationNo;
	private String dateOfBirth;
	private String isSelected;
	private String isInterviewSelected;
	private int personalDataId;
	private String interviewStatus;
	private String interviewRoundName;
	private int interviewResultId;
	private int interviewProgramCourseId;
	private boolean isCancelled;
	private String createdBy;
	private String modifiedBy;
	private String createdDate;
	private String lastModifiedDate;
	private String email;
	private int appliedYear;
	private int courseId;
	private String name;
	private String isActive;
	private String admStatus;
	private boolean isByPassed;
	private boolean isAdmitted;
	private String finalTemplate;
	private String url;
	private String interviewTime;
	private Date interviewDate;
	private String seatNo;
	private String interviewSelectionSchedule;
	
	//vibin for status
	private String indexmark;
	private String currentcourse;
	private int rank;
	private int pref;
	private int allotmentno;
	private boolean alloted;
	private boolean assigned;
	private boolean cancel;
	private boolean allotmentflag = false;
	private boolean almntgeneral;
	private boolean almntcaste;
	private String altmntcategory;
	private String isChallanRecieved;
	
	
	//new
	private boolean isChallenVerified;
	private String courseName;
	private int studentId;
	private Boolean onlineAcknowledgement;
	private boolean isDraftMode;
	private boolean isDraftCancelled;
	private String applnMode;
	private List<CandidatePreferenceTO> memoList = new LinkedList<CandidatePreferenceTO>();

	// vinodha
	private String dateTime;
	private String generalFee;
	private String casteFee;
	private int currentcourseid;

	public boolean isChallenVerified() {
		return isChallenVerified;
	}

	public void setChallenVerified(boolean isChallenVerified) {
		this.isChallenVerified = isChallenVerified;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public Boolean getOnlineAcknowledgement() {
		return onlineAcknowledgement;
	}

	public void setOnlineAcknowledgement(Boolean onlineAcknowledgement) {
		this.onlineAcknowledgement = onlineAcknowledgement;
	}

	public boolean isDraftMode() {
		return isDraftMode;
	}

	public void setDraftMode(boolean isDraftMode) {
		this.isDraftMode = isDraftMode;
	}

	public boolean isDraftCancelled() {
		return isDraftCancelled;
	}

	public void setDraftCancelled(boolean isDraftCancelled) {
		this.isDraftCancelled = isDraftCancelled;
	}

	public String getApplnMode() {
		return applnMode;
	}

	public void setApplnMode(String applnMode) {
		this.applnMode = applnMode;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public String getIsInterviewSelected() {
		return isInterviewSelected;
	}

	public void setIsInterviewSelected(String isInterviewSelected) {
		this.isInterviewSelected = isInterviewSelected;
	}

	public int getInterviewResultId() {
		return interviewResultId;
	}

	public void setInterviewResultId(int interviewResultId) {
		this.interviewResultId = interviewResultId;
	}

	public int getInterviewProgramCourseId() {
		return interviewProgramCourseId;
	}

	public void setInterviewProgramCourseId(int interviewProgramCourseId) {
		this.interviewProgramCourseId = interviewProgramCourseId;
	}

	private Set<InterviewResultTO> interviewResultTO;
	
	public String getInterviewStatus() {
		return interviewStatus;
	}

	public void setInterviewStatus(String interviewStatus) {
		this.interviewStatus = interviewStatus;
	}

	public String getInterviewRoundName() {
		return interviewRoundName;
	}

	public void setInterviewRoundName(String interviewRoundName) {
		this.interviewRoundName = interviewRoundName;
	}

	public String getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}

	public Set<InterviewResultTO> getInterviewResultTO() {
		return interviewResultTO;
	}

	public void setInterviewResultTO(Set<InterviewResultTO> interviewResultTO) {
		this.interviewResultTO = interviewResultTO;
	}

	public int getPersonalDataId() {
		return personalDataId;
	}

	public void setPersonalDataId(int personalDataId) {
		this.personalDataId = personalDataId;
	}

	public int getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(int applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAppliedYear() {
		return appliedYear;
	}

	public void setAppliedYear(int appliedYear) {
		this.appliedYear = appliedYear;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getAdmStatus() {
		return admStatus;
	}

	public void setAdmStatus(String admStatus) {
		this.admStatus = admStatus;
	}

	public boolean isByPassed() {
		return isByPassed;
	}

	public void setByPassed(boolean isByPassed) {
		this.isByPassed = isByPassed;
	}

	public boolean isAdmitted() {
		return isAdmitted;
	}

	public void setAdmitted(boolean isAdmitted) {
		this.isAdmitted = isAdmitted;
	}

	public String getFinalTemplate() {
		return finalTemplate;
	}

	public void setFinalTemplate(String finalTemplate) {
		this.finalTemplate = finalTemplate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(String interviewTime) {
		this.interviewTime = interviewTime;
	}

	public Date getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public String getInterviewSelectionSchedule() {
		return interviewSelectionSchedule;
	}

	public void setInterviewSelectionSchedule(String interviewSelectionSchedule) {
		this.interviewSelectionSchedule = interviewSelectionSchedule;
	}

	public String getIndexmark() {
		return indexmark;
	}

	public void setIndexmark(String indexmark) {
		this.indexmark = indexmark;
	}

	public String getCurrentcourse() {
		return currentcourse;
	}

	public void setCurrentcourse(String currentcourse) {
		this.currentcourse = currentcourse;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getPref() {
		return pref;
	}

	public void setPref(int pref) {
		this.pref = pref;
	}

	public int getAllotmentno() {
		return allotmentno;
	}

	public void setAllotmentno(int allotmentno) {
		this.allotmentno = allotmentno;
	}

	public boolean isAlloted() {
		return alloted;
	}

	public void setAlloted(boolean alloted) {
		this.alloted = alloted;
	}

	public boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}

	

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	public boolean isCancel() {
		return cancel;
	}

	public void setAllotmentflag(boolean allotmentflag) {
		this.allotmentflag = allotmentflag;
	}

	public boolean isAllotmentflag() {
		return allotmentflag;
	}

	public void setAlmntgeneral(boolean almntgeneral) {
		this.almntgeneral = almntgeneral;
	}

	public boolean isAlmntgeneral() {
		return almntgeneral;
	}

	public void setAlmntcaste(boolean almntcaste) {
		this.almntcaste = almntcaste;
	}

	public boolean isAlmntcaste() {
		return almntcaste;
	}

	public void setAltmntcategory(String altmntcategory) {
		this.altmntcategory = altmntcategory;
	}

	public String getAltmntcategory() {
		return altmntcategory;
	}

	public String getIsChallanRecieved() {
		return isChallanRecieved;
	}

	public void setIsChallanRecieved(String isChallanRecieved) {
		this.isChallanRecieved = isChallanRecieved;
	}

	public void setMemoList(List<CandidatePreferenceTO> memoList) {
		this.memoList = memoList;
	}

	public List<CandidatePreferenceTO> getMemoList() {
		return memoList;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getGeneralFee() {
		return generalFee;
	}

	public void setGeneralFee(String generalFee) {
		this.generalFee = generalFee;
	}

	public String getCasteFee() {
		return casteFee;
	}

	public void setCasteFee(String casteFee) {
		this.casteFee = casteFee;
	}

	public int getCurrentcourseid() {
		return currentcourseid;
	}

	public void setCurrentcourseid(int currentcourseid) {
		this.currentcourseid = currentcourseid;
	}

	}
