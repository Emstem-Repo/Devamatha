package com.kp.cms.bo.admin;

// Generated Jan 22, 2009 3:35:47 PM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admission.AdmLoanLetterDetails;
import com.kp.cms.bo.admission.InterviewSelectionSchedule;
import com.kp.cms.bo.fees.FeeCategory;



/**
 * AdmAppln generated by hbm2java
 */
public class AdmAppln implements java.io.Serializable {

	private int id;
	private Course courseBySelectedCourseId;
	private String createdBy;;
	private String modifiedBy;
	private PersonalData personalData;
	private Course course;
	private int applnNo;
	private String challanRefNo;
	private String journalNo;
	private Date createdDate;
	private Date lastModifiedDate;
	private Date date;
	private BigDecimal amount;
	private Boolean isSelected;
	private Boolean isInterviewSelected;
	private Boolean isCancelled;
	private Boolean isBypassed;
	private Boolean isFreeShip;
	private Boolean isLig;
	private Boolean isApproved;
	private Integer appliedYear;
	private BigDecimal totalWeightage;
	private AdmittedThrough admittedThrough;
	private String bankBranch;
	private BigDecimal weightageAdjustedMarks;
	private String remarks;
	private String cancelRemarks;
	private String approvalRemark;
	private Date markscardDate;
	private Date tcDate;
	private String tcNo;
	private String markscardNo;
	private Date admissionDate;
	private Boolean isFinalMeritApproved;
	private Boolean isPreferenceUpdated;
	private ExamCenter examCenter;
	private String admStatus;
	private String seatNo;
	private InterviewSelectionSchedule interScheduleSelection;
	private Set<StudentVehicleDetails> studentVehicleDetailses = new HashSet<StudentVehicleDetails>(0);

	private Set<InterviewCard> interviewCards = new HashSet<InterviewCard>(0);
	private Set<CandidatePreference> candidatePreferences = new HashSet<CandidatePreference>(
			0);
	private Set<Student> students = new HashSet<Student>(0);
	private Set<ApplnDoc> applnDocs = new HashSet<ApplnDoc>(0);
	private Set<ApplicantWorkExperience> applicantWorkExperiences = new HashSet<ApplicantWorkExperience>(0);
	private Set<ApplicantRecommendor> applicantRecommendors = new HashSet<ApplicantRecommendor>(
			0);
	private Set<InterviewResult> interviewResults = new HashSet<InterviewResult>(
			0);
	private Set<InterviewSelected> interviewSelecteds = new HashSet<InterviewSelected>(
			0);
	private Set<ApplicantSubjectGroup> applicantSubjectGroups = new HashSet<ApplicantSubjectGroup>(
			0);
	private Set<CandidatePrerequisiteMarks> candidatePrerequisiteMarks = new HashSet<CandidatePrerequisiteMarks>(
			0);
	private Set<CandidateEntranceDetails> candidateEntranceDetailses = new HashSet<CandidateEntranceDetails>(
			0);
	private Set<ApplicantTransferDetails> applicantTransferDetailses = new HashSet<ApplicantTransferDetails>(
			0);
	private Set<ApplicantLateralDetails> applicantLateralDetailses = new HashSet<ApplicantLateralDetails>(
			0);
	private Set<StudentQualifyexamDetail> studentQualifyexamDetails = new HashSet<StudentQualifyexamDetail>(
			0);
	private Set<HlApplicationForm> hlApplicationForms = new HashSet<HlApplicationForm>(
			0);
	private Set<StudentSpecializationPrefered> studentSpecializationPrefered = new HashSet<StudentSpecializationPrefered>(
			0);
	
	private String verifiedBy;
	private Date cancelDate;
	private Date finalMeritListApproveDate;
	private Date courseChangeDate;
	private Boolean isChallanVerified;
	private String mode;
	private Set<StudentCancellationDetails> studentCancellationDetails = new HashSet<StudentCancellationDetails>(
			0);
	private Set<AdmapplnAdditionalInfo> admapplnAdditionalInfo= new HashSet<AdmapplnAdditionalInfo>(
			0);
	private String ddDrawnOn;
	private String ddIssuingBank;
	//added for saving international currency temporarily  in online appln -smithar
	private String internationalCurrencyId;
	private Set<AdmLoanLetterDetails> admLoanLetterDetails = new HashSet<AdmLoanLetterDetails>();
	private Boolean isWaiting;
	private Boolean notSelected;
	private Course admittedCourseId;
	private Boolean isAided;
	private String admissionNumber;
	//raghu
	private Boolean isDDRecieved;
	private String recievedDDNo;
	private Date recievedDate;
	
	private Boolean isChallanRecieved;
	private String recievedChallanNo;
	private Date recievedChallanDate;
	
//new admission
	
	private StudentOnlineApplication studentOnlineApplication;
	//private Boolean isWithdrawn;
	//private InterdiscipSessionDetails intrSessionDetails;
	private Boolean isDraftMode;
	private Boolean isDraftCancelled;
	private String currentPageName;
	private FeeCategory feeCategory;
	private String recommedationApplicationNo;
	
	public AdmAppln() {
	}

	public AdmAppln(int id) {
		this.id = id;
	}

	
	
	
	public AdmAppln(int id, Course courseBySelectedCourseId, String createdBy,
			String modifiedBy, PersonalData personalData, Course course,
			int applnNo, String challanRefNo, String journalNo,
			Date createdDate, Date lastModifiedDate, Date date,
			BigDecimal amount, Boolean isSelected, Boolean isInterviewSelected,
			Boolean isCancelled, Boolean isBypassed, Boolean isFreeShip,
			Boolean isLig, Boolean isApproved, Integer appliedYear,
			BigDecimal totalWeightage, AdmittedThrough admittedThrough,
			String bankBranch, BigDecimal weightageAdjustedMarks,
			String remarks, String cancelRemarks, String approvalRemark,
			Date markscardDate, Date tcDate, String tcNo, String markscardNo,
			Date admissionDate, Boolean isFinalMeritApproved,
			Boolean isPreferenceUpdated, ExamCenter examCenter,
			String admStatus, String seatNo,
			InterviewSelectionSchedule interScheduleSelection,
			Set<StudentVehicleDetails> studentVehicleDetailses,
			Set<InterviewCard> interviewCards,
			Set<CandidatePreference> candidatePreferences,
			Set<Student> students, Set<ApplnDoc> applnDocs,
			Set<ApplicantWorkExperience> applicantWorkExperiences,
			Set<ApplicantRecommendor> applicantRecommendors,
			Set<InterviewResult> interviewResults,
			Set<InterviewSelected> interviewSelecteds,
			Set<ApplicantSubjectGroup> applicantSubjectGroups,
			Set<CandidatePrerequisiteMarks> candidatePrerequisiteMarks,
			Set<CandidateEntranceDetails> candidateEntranceDetailses,
			Set<ApplicantTransferDetails> applicantTransferDetailses,
			Set<ApplicantLateralDetails> applicantLateralDetailses,
			Set<StudentQualifyexamDetail> studentQualifyexamDetails,
			Set<HlApplicationForm> hlApplicationForms,
			Set<StudentSpecializationPrefered> studentSpecializationPrefered,
			String verifiedBy, Date cancelDate, Date finalMeritListApproveDate,
			Date courseChangeDate, Boolean isChallanVerified, String mode,
			Set<StudentCancellationDetails> studentCancellationDetails,
			Set<AdmapplnAdditionalInfo> admapplnAdditionalInfo,
			String ddDrawnOn, String ddIssuingBank,
			String internationalCurrencyId,
			Set<AdmLoanLetterDetails> admLoanLetterDetails, Boolean isWaiting,
			Boolean notSelected, Course admittedCourseId, Boolean isAided,
			String admissionNumber, Boolean isDDRecieved, String recievedDDNo,
			Date recievedDate, Boolean isChallanRecieved,
			String recievedChallanNo, Date recievedChallanDate,
			StudentOnlineApplication studentOnlineApplication,
			Boolean isDraftMode, Boolean isDraftCancelled,
			String currentPageName,FeeCategory feeCategory,String recommedationApplicationNo) {
		super();
		this.id = id;
		this.courseBySelectedCourseId = courseBySelectedCourseId;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.personalData = personalData;
		this.course = course;
		this.applnNo = applnNo;
		this.challanRefNo = challanRefNo;
		this.journalNo = journalNo;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.date = date;
		this.amount = amount;
		this.isSelected = isSelected;
		this.isInterviewSelected = isInterviewSelected;
		this.isCancelled = isCancelled;
		this.isBypassed = isBypassed;
		this.isFreeShip = isFreeShip;
		this.isLig = isLig;
		this.isApproved = isApproved;
		this.appliedYear = appliedYear;
		this.totalWeightage = totalWeightage;
		this.admittedThrough = admittedThrough;
		this.bankBranch = bankBranch;
		this.weightageAdjustedMarks = weightageAdjustedMarks;
		this.remarks = remarks;
		this.cancelRemarks = cancelRemarks;
		this.approvalRemark = approvalRemark;
		this.markscardDate = markscardDate;
		this.tcDate = tcDate;
		this.tcNo = tcNo;
		this.markscardNo = markscardNo;
		this.admissionDate = admissionDate;
		this.isFinalMeritApproved = isFinalMeritApproved;
		this.isPreferenceUpdated = isPreferenceUpdated;
		this.examCenter = examCenter;
		this.admStatus = admStatus;
		this.seatNo = seatNo;
		this.interScheduleSelection = interScheduleSelection;
		this.studentVehicleDetailses = studentVehicleDetailses;
		this.interviewCards = interviewCards;
		this.candidatePreferences = candidatePreferences;
		this.students = students;
		this.applnDocs = applnDocs;
		this.applicantWorkExperiences = applicantWorkExperiences;
		this.applicantRecommendors = applicantRecommendors;
		this.interviewResults = interviewResults;
		this.interviewSelecteds = interviewSelecteds;
		this.applicantSubjectGroups = applicantSubjectGroups;
		this.candidatePrerequisiteMarks = candidatePrerequisiteMarks;
		this.candidateEntranceDetailses = candidateEntranceDetailses;
		this.applicantTransferDetailses = applicantTransferDetailses;
		this.applicantLateralDetailses = applicantLateralDetailses;
		this.studentQualifyexamDetails = studentQualifyexamDetails;
		this.hlApplicationForms = hlApplicationForms;
		this.studentSpecializationPrefered = studentSpecializationPrefered;
		this.verifiedBy = verifiedBy;
		this.cancelDate = cancelDate;
		this.finalMeritListApproveDate = finalMeritListApproveDate;
		this.courseChangeDate = courseChangeDate;
		this.isChallanVerified = isChallanVerified;
		this.mode = mode;
		this.studentCancellationDetails = studentCancellationDetails;
		this.admapplnAdditionalInfo = admapplnAdditionalInfo;
		this.ddDrawnOn = ddDrawnOn;
		this.ddIssuingBank = ddIssuingBank;
		this.internationalCurrencyId = internationalCurrencyId;
		this.admLoanLetterDetails = admLoanLetterDetails;
		this.isWaiting = isWaiting;
		this.notSelected = notSelected;
		this.admittedCourseId = admittedCourseId;
		this.isAided = isAided;
		this.admissionNumber = admissionNumber;
		this.isDDRecieved = isDDRecieved;
		this.recievedDDNo = recievedDDNo;
		this.recievedDate = recievedDate;
		this.isChallanRecieved = isChallanRecieved;
		this.recievedChallanNo = recievedChallanNo;
		this.recievedChallanDate = recievedChallanDate;
		this.studentOnlineApplication = studentOnlineApplication;
		this.isDraftMode = isDraftMode;
		this.isDraftCancelled = isDraftCancelled;
		this.currentPageName = currentPageName;
		this.feeCategory = feeCategory;
		this.recommedationApplicationNo = recommedationApplicationNo;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy)  {
		this.createdBy = createdBy;
	}

	public String getModifiedBy()  {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public PersonalData getPersonalData() {
		return this.personalData;
	}

	public void setPersonalData(PersonalData personalData) {
		this.personalData = personalData;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public int getApplnNo() {
		return this.applnNo;
	}

	public void setApplnNo(int applnNo) {
		this.applnNo = applnNo;
	}

	public String getChallanRefNo() {
		return this.challanRefNo;
	}

	public void setChallanRefNo(String challanRefNo) {
		this.challanRefNo = challanRefNo;
	}

	public String getJournalNo() {
		return this.journalNo;
	}

	public void setJournalNo(String journalNo) {
		this.journalNo = journalNo;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Boolean getIsSelected() {
		return this.isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Boolean getIsInterviewSelected() {
		return this.isInterviewSelected;
	}

	public void setIsInterviewSelected(Boolean isInterviewSelected) {
		this.isInterviewSelected = isInterviewSelected;
	}

	public Integer getAppliedYear() {
		return this.appliedYear;
	}

	public void setAppliedYear(Integer appliedYear) {
		this.appliedYear = appliedYear;
	}

	public Set<InterviewCard> getInterviewCards() {
		return this.interviewCards;
	}

	public void setInterviewCards(Set<InterviewCard> interviewCards) {
		this.interviewCards = interviewCards;
	}

	public Set<CandidatePreference> getCandidatePreferences() {
		return this.candidatePreferences;
	}

	public void setCandidatePreferences(
			Set<CandidatePreference> candidatePreferences) {
		this.candidatePreferences = candidatePreferences;
	}

	public Set<Student> getStudents() {
		return this.students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public Set<ApplnDoc> getApplnDocs() {
		return this.applnDocs;
	}

	public void setApplnDocs(Set<ApplnDoc> applnDocs) {
		this.applnDocs = applnDocs;
	}

	public Set<ApplicantRecommendor> getApplicantRecommendors() {
		return this.applicantRecommendors;
	}

	public void setApplicantRecommendors(
			Set<ApplicantRecommendor> applicantRecommendors) {
		this.applicantRecommendors = applicantRecommendors;
	}

	public Set<InterviewResult> getInterviewResults() {
		return this.interviewResults;
	}

	public void setInterviewResults(Set<InterviewResult> interviewResults) {
		this.interviewResults = interviewResults;
	}
	

	public BigDecimal getTotalWeightage() {
		return totalWeightage;
	}

	public void setTotalWeightage(BigDecimal totalWeightage) {
		this.totalWeightage = totalWeightage;
	}

	public Set<InterviewSelected> getInterviewSelecteds() {
		return interviewSelecteds;
	}

	public void setInterviewSelecteds(Set<InterviewSelected> interviewSelecteds) {
		this.interviewSelecteds = interviewSelecteds;
	}
	public Set<StudentVehicleDetails> getStudentVehicleDetailses() {
		return this.studentVehicleDetailses;
	}

	public void setStudentVehicleDetailses(
			Set<StudentVehicleDetails> studentVehicleDetailses) {
		this.studentVehicleDetailses = studentVehicleDetailses;
	}

	public Set<ApplicantWorkExperience> getApplicantWorkExperiences() {
		return applicantWorkExperiences;
	}

	public void setApplicantWorkExperiences(
			Set<ApplicantWorkExperience> applicantWorkExperiences) {
		this.applicantWorkExperiences = applicantWorkExperiences;
	}
	
	public AdmittedThrough getAdmittedThrough() {
		return this.admittedThrough;
	}

	public void setAdmittedThrough(AdmittedThrough admittedThrough) {
		this.admittedThrough = admittedThrough;
	}

	

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public Set<ApplicantSubjectGroup> getApplicantSubjectGroups() {
		return applicantSubjectGroups;
	}

	public Boolean getIsFinalMeritApproved() {
		return isFinalMeritApproved;
	}

	public void setIsFinalMeritApproved(Boolean isFinalMeritApproved) {
		this.isFinalMeritApproved = isFinalMeritApproved;
	}

	public void setApplicantSubjectGroups(
			Set<ApplicantSubjectGroup> applicantSubjectGroups) {
		this.applicantSubjectGroups = applicantSubjectGroups;
	}
	
	public BigDecimal getWeightageAdjustedMarks() {
		return weightageAdjustedMarks;
	}
	
	public void setWeightageAdjustedMarks(BigDecimal weightageAdjustedMarks) {
		this.weightageAdjustedMarks = weightageAdjustedMarks;
	}
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public String getCancelRemarks() {
		return cancelRemarks;
	}

	public void setCancelRemarks(String cancelRemarks) {
		this.cancelRemarks = cancelRemarks;
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

	public Course getCourseBySelectedCourseId() {
		return courseBySelectedCourseId;
	}

	public void setCourseBySelectedCourseId(Course courseBySelectedCourseId) {
		this.courseBySelectedCourseId = courseBySelectedCourseId;
	}
	public Set<CandidateEntranceDetails> getCandidateEntranceDetailses() {
		return this.candidateEntranceDetailses;
	}

	public void setCandidateEntranceDetailses(
			Set<CandidateEntranceDetails> candidateEntranceDetailses) {
		this.candidateEntranceDetailses = candidateEntranceDetailses;
	}

	public Date getMarkscardDate() {
		return markscardDate;
	}

	public void setMarkscardDate(Date markscardDate) {
		this.markscardDate = markscardDate;
	}

	public Date getTcDate() {
		return tcDate;
	}

	public void setTcDate(Date tcDate) {
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

	public Boolean getIsBypassed() {
		return isBypassed;
	}

	public void setIsBypassed(Boolean isBypassed) {
		this.isBypassed = isBypassed;
	}
	public Date getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(Date admissionDate) {
		this.admissionDate = admissionDate;
	}

	public Boolean getIsPreferenceUpdated() {
		return isPreferenceUpdated;
	}

	public void setIsPreferenceUpdated(Boolean isPreferenceUpdated) {
		this.isPreferenceUpdated = isPreferenceUpdated;
	}
	
	
	public Boolean getIsLig() {
		return isLig;
	}

	public void setIsLig(Boolean isLig) {
		this.isLig = isLig;
	}

	public Set<ApplicantTransferDetails> getApplicantTransferDetailses() {
		return this.applicantTransferDetailses;
	}

	public void setApplicantTransferDetailses(
			Set<ApplicantTransferDetails> applicantTransferDetailses) {
		this.applicantTransferDetailses = applicantTransferDetailses;
	}

	public Set<ApplicantLateralDetails> getApplicantLateralDetailses() {
		return this.applicantLateralDetailses;
	}

	public void setApplicantLateralDetailses(
			Set<ApplicantLateralDetails> applicantLateralDetailses) {
		this.applicantLateralDetailses = applicantLateralDetailses;
	}
	public Set<StudentQualifyexamDetail> getStudentQualifyexamDetails() {
		return this.studentQualifyexamDetails;
	}

	public void setStudentQualifyexamDetails(
			Set<StudentQualifyexamDetail> studentQualifyexamDetails) {
		this.studentQualifyexamDetails = studentQualifyexamDetails;
	}

	public Set<HlApplicationForm> getHlApplicationForms() {
		return hlApplicationForms;
	}

	public void setHlApplicationForms(Set<HlApplicationForm> hlApplicationForms) {
		this.hlApplicationForms = hlApplicationForms;
	}

	public ExamCenter getExamCenter() {
		return examCenter;
	}

	public void setExamCenter(ExamCenter examCenter) {
		this.examCenter = examCenter;
	}

	public String getAdmStatus() {
		return admStatus;
	}

	public void setAdmStatus(String admStatus) {
		this.admStatus = admStatus;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public String getVerifiedBy() {
		return verifiedBy;
	}

	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public Date getFinalMeritListApproveDate() {
		return finalMeritListApproveDate;
	}

	public void setFinalMeritListApproveDate(Date finalMeritListApproveDate) {
		this.finalMeritListApproveDate = finalMeritListApproveDate;
	}

	public Date getCourseChangeDate() {
		return courseChangeDate;
	}

	public void setCourseChangeDate(Date courseChangeDate) {
		this.courseChangeDate = courseChangeDate;
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

	public Set<StudentSpecializationPrefered> getStudentSpecializationPrefered() {
		return studentSpecializationPrefered;
	}

	public void setStudentSpecializationPrefered(
			Set<StudentSpecializationPrefered> studentSpecializationPrefered) {
		this.studentSpecializationPrefered = studentSpecializationPrefered;
	}

	public Set<StudentCancellationDetails> getStudentCancellationDetails() {
		return studentCancellationDetails;
	}

	public void setStudentCancellationDetails(
			Set<StudentCancellationDetails> studentCancellationDetails) {
		this.studentCancellationDetails = studentCancellationDetails;
	}

	public Set<AdmapplnAdditionalInfo> getAdmapplnAdditionalInfo() {
		return admapplnAdditionalInfo;
	}

	public void setAdmapplnAdditionalInfo(
			Set<AdmapplnAdditionalInfo> admapplnAdditionalInfo) {
		this.admapplnAdditionalInfo = admapplnAdditionalInfo;
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

	public String getInternationalCurrencyId() {
		return internationalCurrencyId;
	}

	public void setInternationalCurrencyId(String internationalCurrencyId) {
		this.internationalCurrencyId = internationalCurrencyId;
	}

	public Set<AdmLoanLetterDetails> getAdmLoanLetterDetails() {
		return admLoanLetterDetails;
	}

	public void setAdmLoanLetterDetails(
			Set<AdmLoanLetterDetails> admLoanLetterDetails) {
		this.admLoanLetterDetails = admLoanLetterDetails;
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

	public InterviewSelectionSchedule getInterScheduleSelection() {
		return interScheduleSelection;
	}

	public void setInterScheduleSelection(
			InterviewSelectionSchedule interScheduleSelection) {
		this.interScheduleSelection = interScheduleSelection;
	}

	public Course getAdmittedCourseId() {
		return admittedCourseId;
	}

	public void setAdmittedCourseId(Course admittedCourseId) {
		this.admittedCourseId = admittedCourseId;
	}

	public boolean isAided() {
		return isAided;
	}

	public void setAided(boolean isAided) {
		this.isAided = isAided;
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

	public Date getRecievedDate() {
		return recievedDate;
	}

	public void setRecievedDate(Date recievedDate) {
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

	public Date getRecievedChallanDate() {
		return recievedChallanDate;
	}

	public void setRecievedChallanDate(Date recievedChallanDate) {
		this.recievedChallanDate = recievedChallanDate;
	}

	public StudentOnlineApplication getStudentOnlineApplication() {
		return studentOnlineApplication;
	}

	public void setStudentOnlineApplication(
			StudentOnlineApplication studentOnlineApplication) {
		this.studentOnlineApplication = studentOnlineApplication;
	}

	public Boolean getIsDraftMode() {
		return isDraftMode;
	}

	public void setIsDraftMode(Boolean isDraftMode) {
		this.isDraftMode = isDraftMode;
	}

	public Boolean getIsDraftCancelled() {
		return isDraftCancelled;
	}

	public void setIsDraftCancelled(Boolean isDraftCancelled) {
		this.isDraftCancelled = isDraftCancelled;
	}

	public String getCurrentPageName() {
		return currentPageName;
	}

	public void setCurrentPageName(String currentPageName) {
		this.currentPageName = currentPageName;
	}

	public FeeCategory getFeeCategory() {
		return feeCategory;
	}

	public void setFeeCategory(FeeCategory feeCategory) {
		this.feeCategory = feeCategory;
	}

	public String getRecommedationApplicationNo() {
		return recommedationApplicationNo;
	}

	public void setRecommedationApplicationNo(String recommedationApplicationNo) {
		this.recommedationApplicationNo = recommedationApplicationNo;
	}
    

	
	
	
}