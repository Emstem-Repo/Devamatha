package com.kp.cms.to.admission;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.AdmapplnAdditionalInfo;
import com.kp.cms.bo.admin.ApplicantLateralDetails;
import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.ApplicantTransferDetails;
import com.kp.cms.bo.admin.CandidatePreference;
import com.kp.cms.bo.admin.CandidatePrerequisiteMarks;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentQualifyexamDetail;
import com.kp.cms.bo.admin.StudentSpecializationPrefered;
import com.kp.cms.to.admin.AdmSubjectMarkForRankTO;
import com.kp.cms.to.admin.ApplicantLateralDetailsTO;
import com.kp.cms.to.admin.ApplicantTransferDetailsTO;
import com.kp.cms.to.admin.ApplicantWorkExperienceTO;
import com.kp.cms.to.admin.ApplnDocTO;
import com.kp.cms.to.admin.CandidateEntranceDetailsTO;
import com.kp.cms.to.admin.CandidatePreferenceTO;
import com.kp.cms.to.admin.CandidatePrerequisiteMarksTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admin.StudentVehicleDetailsTO;
/**
 * 
 *
 * TO Class for AdmAppln BO
 * 
 */
public class AdmApplnTO {

	private int id;
  
	private PersonalDataTO personalData;
	private int personalDataid;
	private CourseTO course;
	private CourseTO selectedCourse;
	private int applnNo;
	private String challanRefNo;
	private String journalNo;
	private String bankBranch;
	private Date createdDate;
	private String createdBy;
	private Date lastModifiedDate;
	private Date date;
	private String amount;
	private Boolean isSelected;
	private Boolean isBypassed;
	private Boolean isApproved;
	private Boolean isInterviewSelected;
	private Integer appliedYear;
	private Set<Student> students = new HashSet<Student>(0);
	private List<String> originalList;
	private List<String> pendingDocList;
	private int applicationId;
	private List<EdnQualificationTO> ednQualificationList;
	private PreferenceTO preference;
	private List<ApplnDocTO> documentsList;
	private List<ApplnDocTO> editDocuments;
	private List<InterviewResultTO> interviewResultList;
	private String challanDate;
	private Set<InterviewSelectedTO> interviewSelecteds = new HashSet<InterviewSelectedTO>(
			0);
	private StudentVehicleDetailsTO vehicleDetail;
	private String totalWeightage;
	private String weightageAdjustMark;
	private String admittedThroughId;
	private String admittedThroughName;
	private String[] subjectGroupIds;
	private String subjectGroupNames;
	List<ApplicantWorkExperienceTO> workExpList = null;
	private List<ApplicantSubjectGroup> applicantSubjectGroups;
	private String photoPath;
	private String remark;
	private String approvalRemark;
	private Boolean isCancelled;
	private Boolean isFreeShip;
	// added by smitha for challan verification
	private Boolean isChallanVerified;
	private Boolean isLIG;
	private Boolean isFinalMeritApproved;
	private String markscardDate;
	private String tcDate;
	private String tcNo;
	private String tcType;
	private String markscardNo;
	private Boolean hidedetailsRadio;
	private String hidedetailsDate;
	private String hidedetailReasons;
	private boolean proforma;
	private String collegeCode;
	private String yearOfPass;
	private String courseChangeDate;
	private String admStatus;
	private Boolean isHide;
	private boolean backLogs;
	private Boolean isWaiting;
	private Boolean notSelected;
	private String interviewSelectionScheduleId;
	private int studentId;
	private CourseTO admittedCourse;
	private Boolean isAided;
	private String admissionNumber;
	private String isCommuQuota;
	private String ismngQuota;
	private String onlinPaymentStatus;
	
	
	
	private CandidateEntranceDetailsTO entranceDetail;
	private List<ApplicantWorkExperienceTO> workExperienceList;
	private Set<CandidatePrerequisiteMarks> candidatePrerequisiteMarks = new HashSet<CandidatePrerequisiteMarks>(
			0);
	
	private List<CandidatePrerequisiteMarksTO> prerequisiteTos;
	
	private List<ApplicantLateralDetailsTO> lateralDetails;
	
	private Set<ApplicantLateralDetails> lateralDetailBos = new HashSet<ApplicantLateralDetails>(
			0);
	
	private List<ApplicantTransferDetailsTO> transferDetails;
	private Set<ApplicantTransferDetails> transferDetailBos = new HashSet<ApplicantTransferDetails>(
			0);
	private Boolean displaySecondLanguage;
	private Boolean displayExtraDetails;
	private Boolean displayMotherTongue;
	private Boolean displayHeightWeight;
	private Boolean displayLanguageKnown;
	private Boolean displayTrainingDetails;
	private Boolean displayAdditionalInfo;
	private Boolean displayExtracurricular;
	private Boolean displayTCDetails;
	private Boolean displayFamilyBackground;
	private Boolean displayLateralDetails;
	private Boolean displayTransferDetails;
	private Boolean displayLateralTransfer;
	private StudentQualifyExamDetailsTO qualifyDetailsTo;
	private Set<StudentQualifyexamDetail> originalQualDetails;
	private Set<CandidatePreference> originalPreferences;
	
	private String admissionDate;
	private int examCenterId;
	private String examCenterName;
	private int commonSubjectId;
	private String interviewDate;
	private String verifiedBy;
	private String submissionDate;
	private String status;
	private String shortStatus;
	private String studentTcNo;
	private String studentTcDate;
	private String studentTcType;
	private Map<Integer,Integer> subIdMap;
	private String mode;
	//added for canceledAdmissionRepayment option
	private String admissionCancelDate;
	private String admissionCancelRemarks;
	private String studentName;
	private String courseName;
	private String regNo;
	//added for student specialization preferred and admApplnAdditionalInfo-Smitha
	private Set<StudentSpecializationPrefered> studentSpecializationPrefered;
	private Set<AdmapplnAdditionalInfo> admapplnAdditionalInfos;
	private String ddDrawnOn;
	private String ddIssuingBank;
	private Boolean ddPayment;
	private Boolean onlinePayment;
	private Boolean challanPayment;
	
	private Boolean isPreferenceUpdated;
	private String seatNo;
	private Date finalMeritListApproveDate;
	private String preRequisiteObtMarks;
	private String preRequisiteRollNo;
	private String preRequisiteExamYear;
	private String preRequisiteExamMonth;
	private String applicantFeedbackId;
	private String titleOfFather;
	private String titleOfMother;
	private String internationalCurrencyId;
	private Boolean hasWorkExp;
	private Boolean isComeDk;
	private String commSentTo;
	private String entranceDate;
	private String entranceVenue;
	private String entranceTime;
	private String ligPrint;
	
	
	
	//raghu
	private Boolean isSaypass;
	
	private Boolean isDDRecieved;
	private String recievedDDNo;
	private String recievedDate;
	
	private Boolean isChallanRecieved;
	private String recievedChallanNo;
	private String recievedChallanDate;
	
	//vibin
	private List<AdmSubjectMarkForRankTO> pucsubjectmarkto;
	private List<CandidatePreferenceTO> preflist;
	private int bonsmark;
	private int handicapmark;
	private int programType;
	private int curretcourseid;
	private int pref;
	private int rank;
	private Boolean isRank;
	private Double index;
	
	//new asmission
	private Boolean isDraftMode;
	private Boolean isDraftCancelled;
	private String currentPageName;
	private int uniqueId;
	private String feeCategoryId;
	private String feeCategoryName;
	private String recommedationApplicationNo;
	private Boolean isTcApproved;
	private String applyYear;

	public Boolean getIsDraftMode() {
		return isDraftMode;
	}

	public void setIsDraftMode(Boolean isDraftMode) {
		this.isDraftMode = isDraftMode;
	}

	public Boolean getIsDraftCancelled() {
		return isDraftCancelled;
	}

	public void setIsDraftCancelled(Boolean isDraftCancelled) 		{
		this.isDraftCancelled = isDraftCancelled;
	}

	public String getCurrentPageName() {
		return currentPageName;
	}

	public void setCurrentPageName(String 	currentPageName) {
		this.currentPageName = currentPageName;
	}
	public Boolean getIsDDRecieved() {
		return isDDRecieved;
	}

	public void setIsDDRecieved(Boolean isDDRecieved) {
		this.isDDRecieved = isDDRecieved;
	}

	public String getRecievedDDNo() {
		return recievedDDNo;
	}

	public void setRecievedDDNo(String recievedDDNo) {
		this.recievedDDNo = recievedDDNo;
	}

	public String getRecievedDate() {
		return recievedDate;
	}

	public void setRecievedDate(String recievedDate) {
		this.recievedDate = recievedDate;
	}

	public Boolean getIsChallanRecieved() {
		return isChallanRecieved;
	}

	public void setIsChallanRecieved(Boolean isChallanRecieved) {
		this.isChallanRecieved = isChallanRecieved;
	}

	public String getRecievedChallanNo() {
		return recievedChallanNo;
	}

	public void setRecievedChallanNo(String recievedChallanNo) {
		this.recievedChallanNo = recievedChallanNo;
	}

	public String getRecievedChallanDate() {
		return recievedChallanDate;
	}

	public void setRecievedChallanDate(String recievedChallanDate) {
		this.recievedChallanDate = recievedChallanDate;
	}

	public Boolean getIsSaypass() {
		return isSaypass;
	}


	public void setIsSaypass(Boolean isSaypass) {
		this.isSaypass = isSaypass;
	}

	public String getLigPrint() {
		return ligPrint;
	}
	public void setLigPrint(String ligPrint) {
		this.ligPrint = ligPrint;
	}
	public String getStudentTcNo() {
		return studentTcNo;
	}
	public void setStudentTcNo(String studentTcNo) {
		this.studentTcNo = studentTcNo;
	}
	public String getStudentTcDate() {
		return studentTcDate;
	}
	public void setStudentTcDate(String studentTcDate) {
		this.studentTcDate = studentTcDate;
	}
	public String getStudentTcType() {
		return studentTcType;
	}
	public void setStudentTcType(String studentTcType) {
		this.studentTcType = studentTcType;
	}
	public Boolean getDisplayLateralTransfer() {
		return displayLateralTransfer;
	}
	public void setDisplayLateralTransfer(Boolean displayLateralTransfer) {
		this.displayLateralTransfer = displayLateralTransfer;
	}
	public Boolean getDisplayLateralDetails() {
		return displayLateralDetails;
	}
	public void setDisplayLateralDetails(Boolean displayLateralDetails) {
		this.displayLateralDetails = displayLateralDetails;
	}
	public Boolean getDisplayTransferDetails() {
		return displayTransferDetails;
	}
	public void setDisplayTransferDetails(Boolean displayTransferDetails) {
		this.displayTransferDetails = displayTransferDetails;
	}
	
	public List<ApplicantWorkExperienceTO> getWorkExperienceList() {
		return workExperienceList;
	}
	public void setWorkExperienceList(
			List<ApplicantWorkExperienceTO> workExperienceList) {
		this.workExperienceList = workExperienceList;
	}
	public AdmApplnTO(){
		this.vehicleDetail= new StudentVehicleDetailsTO();
	}
	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public List<EdnQualificationTO> getEdnQualificationList() {
		return ednQualificationList;
	}

	public void setEdnQualificationList(
			List<EdnQualificationTO> ednQualificationList) {
		this.ednQualificationList = ednQualificationList;
	}

	public PreferenceTO getPreference() {
		return preference;
	}

	public void setPreference(PreferenceTO preference) {
		this.preference = preference;
	}

	public List<ApplnDocTO> getDocumentsList() {
		return documentsList;
	}

	public void setDocumentsList(List<ApplnDocTO> documentsList) {
		this.documentsList = documentsList;
	}

	public List<InterviewResultTO> getInterviewResultList() {
		return interviewResultList;
	}

	public void setInterviewResultList(List<InterviewResultTO> interviewResultList) {
		this.interviewResultList = interviewResultList;
	}

	public String getChallanDate() {
		return challanDate;
	}

	public void setChallanDate(String challanDate) {
		this.challanDate = challanDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Boolean getIsInterviewSelected() {
		return isInterviewSelected;
	}

	public void setIsInterviewSelected(Boolean isInterviewSelected) {
		this.isInterviewSelected = isInterviewSelected;
	}

	public Integer getAppliedYear() {
		return appliedYear;
	}

	public void setAppliedYear(Integer appliedYear) {
		this.appliedYear = appliedYear;
	}

	public PersonalDataTO getPersonalData() {
		return personalData;
	}

	public void setPersonalData(PersonalDataTO personalData) {
		this.personalData = personalData;
	}

	public int getApplnNo() {
		return applnNo;
	}

	public CourseTO getCourse() {
		return course;
	}

	public void setCourse(CourseTO course) {
		this.course = course;
	}

	public void setApplnNo(int applnNo) {
		this.applnNo = applnNo;
	}

	public String getChallanRefNo() {
		return challanRefNo;
	}

	public void setChallanRefNo(String challanRefNo) {
		this.challanRefNo = challanRefNo;
	}

	public String getJournalNo() {
		return journalNo;
	}

	public void setJournalNo(String journalNo) {
		this.journalNo = journalNo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public Set<InterviewSelectedTO> getInterviewSelecteds() {
		return interviewSelecteds;
	}

	public void setInterviewSelecteds(Set<InterviewSelectedTO> interviewSelecteds) {
		this.interviewSelecteds = interviewSelecteds;
	}
	public StudentVehicleDetailsTO getVehicleDetail() {
		return vehicleDetail;
	}
	public void setVehicleDetail(StudentVehicleDetailsTO vehicleDetail) {
		this.vehicleDetail = vehicleDetail;
	}
	public String getTotalWeightage() {
		return totalWeightage;
	}
	public void setTotalWeightage(String totalWeightage) {
		this.totalWeightage = totalWeightage;
	}
	public int getPersonalDataid() {
		return personalDataid;
	}
	public void setPersonalDataid(int personalDataid) {
		this.personalDataid = personalDataid;
	}
	public String getAdmittedThroughId() {
		return admittedThroughId;
	}
	public void setAdmittedThroughId(String admittedThroughId) {
		this.admittedThroughId = admittedThroughId;
	}
	
	public String[] getSubjectGroupIds() {
		return subjectGroupIds;
	}
	public void setSubjectGroupIds(String[] subjectGroupIds) {
		this.subjectGroupIds = subjectGroupIds;
	}
	public List<ApplicantWorkExperienceTO> getWorkExpList() {
		return workExpList;
	}
	public void setWorkExpList(List<ApplicantWorkExperienceTO> workExpList) {
		this.workExpList = workExpList;
	}
	public List<ApplnDocTO> getEditDocuments() {
		return editDocuments;
	}
	public void setEditDocuments(List<ApplnDocTO> editDocuments) {
		this.editDocuments = editDocuments;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	public List<ApplicantSubjectGroup> getApplicantSubjectGroups() {
		return applicantSubjectGroups;
	}
	public void setApplicantSubjectGroups(
			List<ApplicantSubjectGroup> applicantSubjectGroups) {
		this.applicantSubjectGroups = applicantSubjectGroups;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getWeightageAdjustMark() {
		return weightageAdjustMark;
	}
	public void setWeightageAdjustMark(String weightageAdjustMark) {
		this.weightageAdjustMark = weightageAdjustMark;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Boolean getIsCancelled() {
		return isCancelled;
	}
	public void setIsCancelled(Boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
	public Boolean getIsFreeShip() {
		return isFreeShip;
	}
	public void setIsFreeShip(Boolean isFreeShip) {
		this.isFreeShip = isFreeShip;
	}
	public Boolean getIsLIG() {
		return isLIG;
	}
	public void setIsLIG(Boolean isLIG) {
		this.isLIG = isLIG;
	}
	public Set<CandidatePrerequisiteMarks> getCandidatePrerequisiteMarks() {
		return candidatePrerequisiteMarks;
	}
	public void setCandidatePrerequisiteMarks(
			Set<CandidatePrerequisiteMarks> candidatePrerequisiteMarks) {
		this.candidatePrerequisiteMarks = candidatePrerequisiteMarks;
	}
	public Boolean getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}
	public String getApprovalRemark() {
		return approvalRemark;
	}
	public void setApprovalRemark(String approvalRemark) {
		this.approvalRemark = approvalRemark;
	}
	public List<CandidatePrerequisiteMarksTO> getPrerequisiteTos() {
		return prerequisiteTos;
	}
	public void setPrerequisiteTos(
			List<CandidatePrerequisiteMarksTO> prerequisiteTos) {
		this.prerequisiteTos = prerequisiteTos;
	}
	public CourseTO getSelectedCourse() {
		return selectedCourse;
	}
	public void setSelectedCourse(CourseTO selectedCourse) {
		this.selectedCourse = selectedCourse;
	}
	public String getMarkscardDate() {
		return markscardDate;
	}
	public void setMarkscardDate(String markscardDate) {
		this.markscardDate = markscardDate;
	}
	public String getTcDate() {
		return tcDate;
	}
	public void setTcDate(String tcDate) {
		this.tcDate = tcDate;
	}
	public String getTcNo() {
		return tcNo;
	}
	public void setTcNo(String tcNo) {
		this.tcNo = tcNo;
	}
	public String getMarkscardNo() {
		return markscardNo;
	}
	public void setMarkscardNo(String markscardNo) {
		this.markscardNo = markscardNo;
	}
	public CandidateEntranceDetailsTO getEntranceDetail() {
		return entranceDetail;
	}
	public void setEntranceDetail(CandidateEntranceDetailsTO entranceDetail) {
		this.entranceDetail = entranceDetail;
	}
	public Boolean getIsBypassed() {
		return isBypassed;
	}
	public void setIsBypassed(Boolean isBypassed) {
		this.isBypassed = isBypassed;
	}
	public List<ApplicantLateralDetailsTO> getLateralDetails() {
		return lateralDetails;
	}
	public void setLateralDetails(List<ApplicantLateralDetailsTO> lateralDetails) {
		this.lateralDetails = lateralDetails;
	}
	public Set<ApplicantLateralDetails> getLateralDetailBos() {
		return lateralDetailBos;
	}
	public void setLateralDetailBos(Set<ApplicantLateralDetails> lateralDetailBos) {
		this.lateralDetailBos = lateralDetailBos;
	}
	public List<ApplicantTransferDetailsTO> getTransferDetails() {
		return transferDetails;
	}
	public void setTransferDetails(List<ApplicantTransferDetailsTO> transferDetails) {
		this.transferDetails = transferDetails;
	}
	public Set<ApplicantTransferDetails> getTransferDetailBos() {
		return transferDetailBos;
	}
	public void setTransferDetailBos(Set<ApplicantTransferDetails> transferDetailBos) {
		this.transferDetailBos = transferDetailBos;
	}
	public Boolean getDisplaySecondLanguage() {
		return displaySecondLanguage;
	}
	public void setDisplaySecondLanguage(Boolean displaySecondLanguage) {
		this.displaySecondLanguage = displaySecondLanguage;
	}
	public Boolean getDisplayExtraDetails() {
		return displayExtraDetails;
	}
	public void setDisplayExtraDetails(Boolean displayExtraDetails) {
		this.displayExtraDetails = displayExtraDetails;
	}
	public Boolean getDisplayMotherTongue() {
		return displayMotherTongue;
	}
	public void setDisplayMotherTongue(Boolean displayMotherTongue) {
		this.displayMotherTongue = displayMotherTongue;
	}
	public Boolean getDisplayHeightWeight() {
		return displayHeightWeight;
	}
	public void setDisplayHeightWeight(Boolean displayHeightWeight) {
		this.displayHeightWeight = displayHeightWeight;
	}
	public Boolean getDisplayLanguageKnown() {
		return displayLanguageKnown;
	}
	public void setDisplayLanguageKnown(Boolean displayLanguageKnown) {
		this.displayLanguageKnown = displayLanguageKnown;
	}
	public Boolean getDisplayTrainingDetails() {
		return displayTrainingDetails;
	}
	public void setDisplayTrainingDetails(Boolean displayTrainingDetails) {
		this.displayTrainingDetails = displayTrainingDetails;
	}
	public Boolean getDisplayAdditionalInfo() {
		return displayAdditionalInfo;
	}
	public void setDisplayAdditionalInfo(Boolean displayAdditionalInfo) {
		this.displayAdditionalInfo = displayAdditionalInfo;
	}
	public Boolean getDisplayExtracurricular() {
		return displayExtracurricular;
	}
	public void setDisplayExtracurricular(Boolean displayExtracurricular) {
		this.displayExtracurricular = displayExtracurricular;
	}
	public Boolean getDisplayTCDetails() {
		return displayTCDetails;
	}
	public void setDisplayTCDetails(Boolean displayTCDetails) {
		this.displayTCDetails = displayTCDetails;
	}
	public Boolean getDisplayFamilyBackground() {
		return displayFamilyBackground;
	}
	public void setDisplayFamilyBackground(Boolean displayFamilyBackground) {
		this.displayFamilyBackground = displayFamilyBackground;
	}
	public StudentQualifyExamDetailsTO getQualifyDetailsTo() {
		return qualifyDetailsTo;
	}
	public void setQualifyDetailsTo(StudentQualifyExamDetailsTO qualifyDetailsTo) {
		this.qualifyDetailsTo = qualifyDetailsTo;
	}
	public Set<StudentQualifyexamDetail> getOriginalQualDetails() {
		return originalQualDetails;
	}
	public void setOriginalQualDetails(
			Set<StudentQualifyexamDetail> originalQualDetails) {
		this.originalQualDetails = originalQualDetails;
	}
	public String getAdmittedThroughName() {
		return admittedThroughName;
	}
	public void setAdmittedThroughName(String admittedThroughName) {
		this.admittedThroughName = admittedThroughName;
	}
	public String getSubjectGroupNames() {
		return subjectGroupNames;
	}
	public void setSubjectGroupNames(String subjectGroupNames) {
		this.subjectGroupNames = subjectGroupNames;
	}
	public String getAdmissionDate() {
		return admissionDate;
	}
	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}
	public Set<CandidatePreference> getOriginalPreferences() {
		return originalPreferences;
	}
	public void setOriginalPreferences(Set<CandidatePreference> originalPreferences) {
		this.originalPreferences = originalPreferences;
	}
	public Boolean getIsFinalMeritApproved() {
		return isFinalMeritApproved;
	}
	public void setIsFinalMeritApproved(Boolean isFinalMeritApproved) {
		this.isFinalMeritApproved = isFinalMeritApproved;
	}
	public int getExamCenterId() {
		return examCenterId;
	}
	public void setExamCenterId(int examCenterId) {
		this.examCenterId = examCenterId;
	}
	public String getExamCenterName() {
		return examCenterName;
	}
	public void setExamCenterName(String examCenterName) {
		this.examCenterName = examCenterName;
	}
	public int getCommonSubjectId() {
		return commonSubjectId;
	}
	public void setCommonSubjectId(int commonSubjectId) {
		this.commonSubjectId = commonSubjectId;
	}
	public String getInterviewDate() {
		return interviewDate;
	}
	public void setInterviewDate(String interviewDate) {
		this.interviewDate = interviewDate;
	}
	public String getVerifiedBy() {
		return verifiedBy;
	}
	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}
	public void setOriginalList(List<String> originalList) {
		this.originalList = originalList;
	}
	public List<String> getOriginalList() {
		return originalList;
	}
	public void setPendingDocList(List<String> pendingDocList) {
		this.pendingDocList = pendingDocList;
	}
	public List<String> getPendingDocList() {
		return pendingDocList;
	}
	public void setSubmissionDate(String submissionDate) {
		this.submissionDate = submissionDate;
	}
	public String getSubmissionDate() {
		return submissionDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setTcType(String tcType) {
		this.tcType = tcType;
	}
	public String getTcType() {
		return tcType;
	}
	
	public String getHidedetailsDate() {
		return hidedetailsDate;
	}
	public void setHidedetailsDate(String hidedetailsDate) {
		this.hidedetailsDate = hidedetailsDate;
	}
	public String getHidedetailReasons() {
		return hidedetailReasons;
	}
	public void setHidedetailReasons(String hidedetailReasons) {
		this.hidedetailReasons = hidedetailReasons;
	}
	public Boolean getHidedetailsRadio() {
		return hidedetailsRadio;
	}
	public void setHidedetailsRadio(Boolean hidedetailsRadio) {
		this.hidedetailsRadio = hidedetailsRadio;
	}
	public boolean getProforma() {
		return proforma;
	}
	public void setProforma(boolean proforma) {
		this.proforma = proforma;
	}
	public String getCollegeCode() {
		return collegeCode;
	}
	public void setCollegeCode(String collegeCode) {
		this.collegeCode = collegeCode;
	}
	public String getYearOfPass() {
		return yearOfPass;
	}
	public void setYearOfPass(String yearOfPass) {
		this.yearOfPass = yearOfPass;
	}
	public String getShortStatus() {
		return shortStatus;
	}
	public void setShortStatus(String shortStatus) {
		this.shortStatus = shortStatus;
	}
	public String getCourseChangeDate() {
		return courseChangeDate;
	}
	public void setCourseChangeDate(String courseChangeDate) {
		this.courseChangeDate = courseChangeDate;
	}
	public Map<Integer, Integer> getSubIdMap() {
		return subIdMap;
	}
	public void setSubIdMap(Map<Integer, Integer> subIdMap) {
		this.subIdMap = subIdMap;
	}
	public Boolean getIsChallanVerified() {
		return isChallanVerified;
	}
	public void setIsChallanVerified(Boolean isChallanVerified) {
		this.isChallanVerified = isChallanVerified;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getAdmStatus() {
		return admStatus;
	}
	public void setAdmStatus(String admStatus) {
		this.admStatus = admStatus;
	}
	public String getAdmissionCancelDate() {
		return admissionCancelDate;
	}
	public void setAdmissionCancelDate(String admissionCancelDate) {
		this.admissionCancelDate = admissionCancelDate;
	}
	public String getAdmissionCancelRemarks() {
		return admissionCancelRemarks;
	}
	public void setAdmissionCancelRemarks(String admissionCancelRemarks) {
		this.admissionCancelRemarks = admissionCancelRemarks;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public Set<StudentSpecializationPrefered> getStudentSpecializationPrefered() {
		return studentSpecializationPrefered;
	}
	public void setStudentSpecializationPrefered(
			Set<StudentSpecializationPrefered> studentSpecializationPrefered) {
		this.studentSpecializationPrefered = studentSpecializationPrefered;
	}
	public Set<AdmapplnAdditionalInfo> getAdmapplnAdditionalInfos() {
		return admapplnAdditionalInfos;
	}
	public void setAdmapplnAdditionalInfos(
			Set<AdmapplnAdditionalInfo> admapplnAdditionalInfos) {
		this.admapplnAdditionalInfos = admapplnAdditionalInfos;
	}
	public Boolean getIsPreferenceUpdated() {
		return isPreferenceUpdated;
	}
	public void setIsPreferenceUpdated(Boolean isPreferenceUpdated) {
		this.isPreferenceUpdated = isPreferenceUpdated;
	}
	public String getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}
	public Date getFinalMeritListApproveDate() {
		return finalMeritListApproveDate;
	}
	public void setFinalMeritListApproveDate(Date finalMeritListApproveDate) {
		this.finalMeritListApproveDate = finalMeritListApproveDate;
	}
	public Boolean getIsHide() {
		return isHide;
	}
	public void setIsHide(Boolean isHide) {
		this.isHide = isHide;
	}
	public String getDdDrawnOn() {
		return ddDrawnOn;
	}
	public void setDdDrawnOn(String ddDrawnOn) {
		this.ddDrawnOn = ddDrawnOn;
	}
	public String getDdIssuingBank() {
		return ddIssuingBank;
	}
	public void setDdIssuingBank(String ddIssuingBank) {
		this.ddIssuingBank = ddIssuingBank;
	}
	public Boolean getDdPayment() {
		return ddPayment;
	}
	public void setDdPayment(Boolean ddPayment) {
		this.ddPayment = ddPayment;
	}
	public Boolean getOnlinePayment() {
		return onlinePayment;
	}
	public void setOnlinePayment(Boolean onlinePayment) {
		this.onlinePayment = onlinePayment;
	}
	public Boolean getChallanPayment() {
		return challanPayment;
	}
	public void setChallanPayment(Boolean challanPayment) {
		this.challanPayment = challanPayment;
	}
	public String getPreRequisiteObtMarks() {
		return preRequisiteObtMarks;
	}
	public void setPreRequisiteObtMarks(String preRequisiteObtMarks) {
		this.preRequisiteObtMarks = preRequisiteObtMarks;
	}
	public String getPreRequisiteRollNo() {
		return preRequisiteRollNo;
	}
	public void setPreRequisiteRollNo(String preRequisiteRollNo) {
		this.preRequisiteRollNo = preRequisiteRollNo;
	}
	public String getPreRequisiteExamYear() {
		return preRequisiteExamYear;
	}
	public void setPreRequisiteExamYear(String preRequisiteExamYear) {
		this.preRequisiteExamYear = preRequisiteExamYear;
	}
	public String getPreRequisiteExamMonth() {
		return preRequisiteExamMonth;
	}
	public void setPreRequisiteExamMonth(String preRequisiteExamMonth) {
		this.preRequisiteExamMonth = preRequisiteExamMonth;
	}
	public String getApplicantFeedbackId() {
		return applicantFeedbackId;
	}
	public void setApplicantFeedbackId(String applicantFeedbackId) {
		this.applicantFeedbackId = applicantFeedbackId;
	}
	public String getTitleOfFather() {
		return titleOfFather;
	}
	public void setTitleOfFather(String titleOfFather) {
		this.titleOfFather = titleOfFather;
	}
	public String getTitleOfMother() {
		return titleOfMother;
	}
	public void setTitleOfMother(String titleOfMother) {
		this.titleOfMother = titleOfMother;
	}
	public String getInternationalCurrencyId() {
		return internationalCurrencyId;
	}
	public void setInternationalCurrencyId(String internationalCurrencyId) {
		this.internationalCurrencyId = internationalCurrencyId;
	}
	public Boolean getHasWorkExp() {
		return hasWorkExp;
	}
	public void setHasWorkExp(Boolean hasWorkExp) {
		this.hasWorkExp = hasWorkExp;
	}
	public boolean isBackLogs() {
		return backLogs;
	}
	public void setBackLogs(boolean backLogs) {
		this.backLogs = backLogs;
	}
	public Boolean getIsComeDk() {
		return isComeDk;
	}
	public void setIsComeDk(Boolean isComeDk) {
		this.isComeDk = isComeDk;
	}
	public String getCommSentTo() {
		return commSentTo;
	}
	public void setCommSentTo(String commSentTo) {
		this.commSentTo = commSentTo;
	}
	public Boolean getIsWaiting() {
		return isWaiting;
	}
	public void setIsWaiting(Boolean isWaiting) {
		this.isWaiting = isWaiting;
	}
	public Boolean getNotSelected() {
		return notSelected;
	}
	public void setNotSelected(Boolean notSelected) {
		this.notSelected = notSelected;
	}
	public String getEntranceDate() {
		return entranceDate;
	}
	public void setEntranceDate(String entranceDate) {
		this.entranceDate = entranceDate;
	}
	public String getEntranceVenue() {
		return entranceVenue;
	}
	public void setEntranceVenue(String entranceVenue) {
		this.entranceVenue = entranceVenue;
	}
	public String getEntranceTime() {
		return entranceTime;
	}
	public void setEntranceTime(String entranceTime) {
		this.entranceTime = entranceTime;
	}
	public String getInterviewSelectionScheduleId() {
		return interviewSelectionScheduleId;
	}
	public void setInterviewSelectionScheduleId(String interviewSelectionScheduleId) {
		this.interviewSelectionScheduleId = interviewSelectionScheduleId;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public CourseTO getAdmittedCourse() {
		return admittedCourse;
	}
	public void setAdmittedCourse(CourseTO admittedCourse) {
		this.admittedCourse = admittedCourse;
	}
	public Boolean getIsAided() {
		return isAided;
	}
	public void setIsAided(Boolean isAided) {
		this.isAided = isAided;
	}
	public String getAdmissionNumber() {
		return admissionNumber;
	}
	public void setAdmissionNumber(String admissionNumber) {
		this.admissionNumber = admissionNumber;
	}

	public List<AdmSubjectMarkForRankTO> getPucsubjectmarkto() {
		return pucsubjectmarkto;
	}

	public void setPucsubjectmarkto(List<AdmSubjectMarkForRankTO> pucsubjectmarkto) {
		this.pucsubjectmarkto = pucsubjectmarkto;
	}

	public List<CandidatePreferenceTO> getPreflist() {
		return preflist;
	}

	public void setPreflist(List<CandidatePreferenceTO> preflist) {
		this.preflist = preflist;
	}

	public int getBonsmark() {
		return bonsmark;
	}

	public void setBonsmark(int bonsmark) {
		this.bonsmark = bonsmark;
	}

	public int getHandicapmark() {
		return handicapmark;
	}

	public void setHandicapmark(int handicapmark) {
		this.handicapmark = handicapmark;
	}

	public void setProgramType(int programType) {
		this.programType = programType;
	}

	public int getProgramType() {
		return programType;
	}

	public void setCurretcourseid(int curretcourseid) {
		this.curretcourseid = curretcourseid;
	}

	public int getCurretcourseid() {
		return curretcourseid;
	}

	public void setPref(int pref) {
		this.pref = pref;
	}

	public int getPref() {
		return pref;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getRank() {
		return rank;
	}

	public void setIsRank(Boolean isRank) {
		this.isRank = isRank;
	}

	public Boolean getIsRank() {
		return isRank;
	}

	public void setIndex(Double index) {
		this.index = index;
	}

	public Double getIndex() {
		return index;
	}

	public int getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getFeeCategoryId() {
		return feeCategoryId;
	}

	public void setFeeCategoryId(String feeCategoryId) {
		this.feeCategoryId = feeCategoryId;
	}

	public String getFeeCategoryName() {
		return feeCategoryName;
	}

	public void setFeeCategoryName(String feeCategoryName) {
		this.feeCategoryName = feeCategoryName;
	}

	public String getRecommedationApplicationNo() {
		return recommedationApplicationNo;
	}

	public void setRecommedationApplicationNo(String recommedationApplicationNo) {
		this.recommedationApplicationNo = recommedationApplicationNo;
	}

	public Boolean getIsTcApproved() {
		return isTcApproved;
	}

	public void setIsTcApproved(Boolean isTcApproved) {
		this.isTcApproved = isTcApproved;
	}

	public String getApplyYear() {
		return applyYear;
	}

	public void setApplyYear(String applyYear) {
		this.applyYear = applyYear;
	}

	public String getIsCommuQuota() {
		return isCommuQuota;
	}

	public void setIsCommuQuota(String isCommuQuota) {
		this.isCommuQuota = isCommuQuota;
	}

	public String getIsmngQuota() {
		return ismngQuota;
	}

	public void setIsmngQuota(String ismngQuota) {
		this.ismngQuota = ismngQuota;
	}

	public String getOnlinPaymentStatus() {
		return onlinPaymentStatus;
	}

	public void setOnlinPaymentStatus(String onlinPaymentStatus) {
		this.onlinPaymentStatus = onlinPaymentStatus;
	}
	

	
}