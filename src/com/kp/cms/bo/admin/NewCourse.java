package com.kp.cms.bo.admin;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class NewCourse implements java.io.Serializable{

	private int id;
	private String createdBy;;
	private String modifiedBy;
	private Program program;
	private String name;
	private String code;
	private Boolean isActive;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isAutonomous;
	private Integer maxIntake;
	private Boolean isWorkExperienceRequired;
	private String payCode;
	private BigDecimal amount;
	private Boolean isDetailMarksPrint;
	
	private Set<AdmAppln> admApplnsForSelectedCourseId = new HashSet<AdmAppln>(0);
	private Set<AdmAppln> admApplnsForCourseId = new HashSet<AdmAppln>(0);
	private Set<FeeConcession> feeConcessions = new HashSet<FeeConcession>(0);
	private Set<Preferences> preferencesesForPrefCourseId = new HashSet<Preferences>(
			0);
	private Set<DocChecklist> docChecklists = new HashSet<DocChecklist>(0);
	private Set<AttendanceMarks> attendanceMarkses = new HashSet<AttendanceMarks>(
			0);
	private Set<Weightage> weightages = new HashSet<Weightage>(0);
	private Set<ApplnType> applnTypes = new HashSet<ApplnType>(0);
	private Set<AdmAppln> admApplns = new HashSet<AdmAppln>(0);
	private Set<ProgCourseDoc> progCourseDocs = new HashSet<ProgCourseDoc>(0);
	private Set<CourseTerm> courseTerms = new HashSet<CourseTerm>(0);
	private Set<SeatAllocation> seatAllocations = new HashSet<SeatAllocation>(0);
	private Set<Fee> fees = new HashSet<Fee>(0);
	private Set<SubjectGroup> subjectGroups = new HashSet<SubjectGroup>(0);
	private Set<Preferences> preferencesesForCourseId = new HashSet<Preferences>(
			0);
	private Set<CoursePrerequisite> coursePrerequisites = new HashSet<CoursePrerequisite>(
			0);
	private Set<CurriculumScheme> curriculumSchemes = new HashSet<CurriculumScheme>(
			0);
	private Set<RegistrationNumberFormat> registrationNumberFormats = new HashSet<RegistrationNumberFormat>(
			0);
	private Set<TermsConditions> termsConditionses = new HashSet<TermsConditions>(
			0);
	private Set<SelectionProcess> selectionProcesses = new HashSet<SelectionProcess>(
			0);
	private Set<CandidatePreference> candidatePreferences = new HashSet<CandidatePreference>(
			0);
	
	private Set<ProgramCourseIntake> programCourseIntakes = new HashSet<ProgramCourseIntake>(
			0);
	private Set<GuidelinesDoc> guidelinesDocs = new HashSet<GuidelinesDoc>(0);
	private Set<EligibilityCriteria> eligibilityCriterias = new HashSet<EligibilityCriteria>(
			0);
	private Set<InterviewProgramCourse> interviewProgramCourses = new HashSet<InterviewProgramCourse>(
			0);
	private Set<CourseApplicationNumber> courseApplicationNumbers = new HashSet<CourseApplicationNumber>(
			0);
	private Set<DetailedSubjects> detailedSubjectses = new HashSet<DetailedSubjects>(
			0);
	private Set<TermsConditionChecklist> termsConditionChecklists = new HashSet<TermsConditionChecklist>(
			0);
	private Set<GroupTemplate> groupTemplates = new HashSet<GroupTemplate>(0);
	public NewCourse() {
	}

	public NewCourse(int id) {
		this.id = id;
	}

	public NewCourse(int id, String createdBy,
			String modifiedBy, Program program, String name,
			String code, Boolean isActive, Date createdDate,
			Date lastModifiedDate, Boolean isAutonomous, Integer maxIntake,
			Set<AdmAppln> admApplnsForCourseId,
			Set<FeeConcession> feeConcessions,
			Set<Preferences> preferencesesForPrefCourseId,
			Set<DocChecklist> docChecklists,
			Set<TermsConditionChecklist> termsConditionChecklists,
			Set<AttendanceMarks> attendanceMarkses, Set<Weightage> weightages,
			Set<ApplnType> applnTypes, Set<AdmAppln> admApplns,
			Set<ProgCourseDoc> progCourseDocs, Set<CourseTerm> courseTerms,
			Set<SeatAllocation> seatAllocations, Set<Fee> fees,
			Set<SubjectGroup> subjectGroups,
			Set<Preferences> preferencesesForCourseId,
			Set<CoursePrerequisite> coursePrerequisites,
			Set<CurriculumScheme> curriculumSchemes,
			Set<RegistrationNumberFormat> registrationNumberFormats,
			Set<TermsConditions> termsConditionses,
			Set<SelectionProcess> selectionProcesses,
			Set<CandidatePreference> candidatePreferences,
			Set<DetailedSubjects> detailedSubjectses,
			Set<ProgramCourseIntake> programCourseIntakes,
			Set<GuidelinesDoc> guidelinesDocs,
			Set<EligibilityCriteria> eligibilityCriterias,
			Set<InterviewProgramCourse> interviewProgramCourses,
			Set<CourseApplicationNumber> courseApplicationNumbers,
			Set<GroupTemplate> groupTemplates, Boolean isDetailMarksPrint) {
		this.id = id;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.program = program;
		this.name = name;
		this.code = code;
		this.isActive = isActive;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isAutonomous = isAutonomous;
		this.maxIntake = maxIntake;
		this.admApplnsForCourseId = admApplnsForCourseId;
		this.feeConcessions = feeConcessions;
		this.preferencesesForPrefCourseId = preferencesesForPrefCourseId;
		this.docChecklists = docChecklists;
		this.termsConditionChecklists = termsConditionChecklists;
		this.attendanceMarkses = attendanceMarkses;
		this.weightages = weightages;
		this.applnTypes = applnTypes;
		this.admApplns = admApplns;
		this.coursePrerequisites = coursePrerequisites;
		this.progCourseDocs = progCourseDocs;
		this.courseTerms = courseTerms;
		this.seatAllocations = seatAllocations;
		this.fees = fees;
		this.subjectGroups = subjectGroups;
		this.preferencesesForCourseId = preferencesesForCourseId;
		this.curriculumSchemes = curriculumSchemes;
		this.registrationNumberFormats = registrationNumberFormats;
		this.termsConditionses = termsConditionses;
		this.selectionProcesses = selectionProcesses;
		this.candidatePreferences = candidatePreferences;
		this.detailedSubjectses=detailedSubjectses;
		this.programCourseIntakes = programCourseIntakes;
		this.guidelinesDocs = guidelinesDocs;
		this.eligibilityCriterias = eligibilityCriterias;
		this.interviewProgramCourses = interviewProgramCourses;
		this.courseApplicationNumbers = courseApplicationNumbers;
		this.groupTemplates = groupTemplates;
		this.isDetailMarksPrint = isDetailMarksPrint;
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

	public Program getProgram() {
		return this.program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public Boolean getIsAutonomous() {
		return this.isAutonomous;
	}

	public void setIsAutonomous(Boolean isAutonomous) {
		this.isAutonomous = isAutonomous;
	}

	public Integer getMaxIntake() {
		return this.maxIntake;
	}

	public void setMaxIntake(Integer maxIntake) {
		this.maxIntake = maxIntake;
	}

	public Set<FeeConcession> getFeeConcessions() {
		return this.feeConcessions;
	}

	public void setFeeConcessions(Set<FeeConcession> feeConcessions) {
		this.feeConcessions = feeConcessions;
	}

	public Set<Preferences> getPreferencesesForPrefCourseId() {
		return this.preferencesesForPrefCourseId;
	}

	public void setPreferencesesForPrefCourseId(
			Set<Preferences> preferencesesForPrefCourseId) {
		this.preferencesesForPrefCourseId = preferencesesForPrefCourseId;
	}

	public Set<DocChecklist> getDocChecklists() {
		return this.docChecklists;
	}

	public void setDocChecklists(Set<DocChecklist> docChecklists) {
		this.docChecklists = docChecklists;
	}
	public Set<TermsConditionChecklist> getTermsConditionChecklists() {
		return this.termsConditionChecklists;
	}

	public void setTermsConditionChecklists(
			Set<TermsConditionChecklist> termsConditionChecklists) {
		this.termsConditionChecklists = termsConditionChecklists;
	}

	public Set<AttendanceMarks> getAttendanceMarkses() {
		return this.attendanceMarkses;
	}

	public void setAttendanceMarkses(Set<AttendanceMarks> attendanceMarkses) {
		this.attendanceMarkses = attendanceMarkses;
	}

	public Set<Weightage> getWeightages() {
		return this.weightages;
	}

	public void setWeightages(Set<Weightage> weightages) {
		this.weightages = weightages;
	}

	public Set<ApplnType> getApplnTypes() {
		return this.applnTypes;
	}

	public void setApplnTypes(Set<ApplnType> applnTypes) {
		this.applnTypes = applnTypes;
	}

	public Set<AdmAppln> getAdmApplns() {
		return this.admApplns;
	}

	public void setAdmApplns(Set<AdmAppln> admApplns) {
		this.admApplns = admApplns;
	}
	
	public Set<CoursePrerequisite> getCoursePrerequisites() {
		return this.coursePrerequisites;
	}

	public void setCoursePrerequisites(
			Set<CoursePrerequisite> coursePrerequisites) {
		this.coursePrerequisites = coursePrerequisites;
	}
	public Set<ProgCourseDoc> getProgCourseDocs() {
		return this.progCourseDocs;
	}

	public void setProgCourseDocs(Set<ProgCourseDoc> progCourseDocs) {
		this.progCourseDocs = progCourseDocs;
	}

	public Set<CourseTerm> getCourseTerms() {
		return this.courseTerms;
	}

	public void setCourseTerms(Set<CourseTerm> courseTerms) {
		this.courseTerms = courseTerms;
	}

	public Set<SeatAllocation> getSeatAllocations() {
		return this.seatAllocations;
	}

	public void setSeatAllocations(Set<SeatAllocation> seatAllocations) {
		this.seatAllocations = seatAllocations;
	}

	public Set<Fee> getFees() {
		return this.fees;
	}

	public void setFees(Set<Fee> fees) {
		this.fees = fees;
	}

	public Set<SubjectGroup> getSubjectGroups() {
		return this.subjectGroups;
	}

	public void setSubjectGroups(Set<SubjectGroup> subjectGroups) {
		this.subjectGroups = subjectGroups;
	}

	public Set<Preferences> getPreferencesesForCourseId() {
		return this.preferencesesForCourseId;
	}

	public void setPreferencesesForCourseId(
			Set<Preferences> preferencesesForCourseId) {
		this.preferencesesForCourseId = preferencesesForCourseId;
	}

	public Set<CurriculumScheme> getCurriculumSchemes() {
		return this.curriculumSchemes;
	}

	public void setCurriculumSchemes(Set<CurriculumScheme> curriculumSchemes) {
		this.curriculumSchemes = curriculumSchemes;
	}

	public Set<RegistrationNumberFormat> getRegistrationNumberFormats() {
		return this.registrationNumberFormats;
	}

	public void setRegistrationNumberFormats(
			Set<RegistrationNumberFormat> registrationNumberFormats) {
		this.registrationNumberFormats = registrationNumberFormats;
	}

	public Set<TermsConditions> getTermsConditionses() {
		return this.termsConditionses;
	}

	public void setTermsConditionses(Set<TermsConditions> termsConditionses) {
		this.termsConditionses = termsConditionses;
	}

	public Set<SelectionProcess> getSelectionProcesses() {
		return this.selectionProcesses;
	}

	public void setSelectionProcesses(Set<SelectionProcess> selectionProcesses) {
		this.selectionProcesses = selectionProcesses;
	}

	public Set<CandidatePreference> getCandidatePreferences() {
		return this.candidatePreferences;
	}

	public void setCandidatePreferences(
			Set<CandidatePreference> candidatePreferences) {
		this.candidatePreferences = candidatePreferences;
	}



	public Set<ProgramCourseIntake> getProgramCourseIntakes() {
		return this.programCourseIntakes;
	}

	public void setProgramCourseIntakes(
			Set<ProgramCourseIntake> programCourseIntakes) {
		this.programCourseIntakes = programCourseIntakes;
	}

	public Set<GuidelinesDoc> getGuidelinesDocs() {
		return this.guidelinesDocs;
	}

	public void setGuidelinesDocs(Set<GuidelinesDoc> guidelinesDocs) {
		this.guidelinesDocs = guidelinesDocs;
	}

	public Set<EligibilityCriteria> getEligibilityCriterias() {
		return this.eligibilityCriterias;
	}

	public void setEligibilityCriterias(
			Set<EligibilityCriteria> eligibilityCriterias) {
		this.eligibilityCriterias = eligibilityCriterias;
	}

	public Set<InterviewProgramCourse> getInterviewProgramCourses() {
		return this.interviewProgramCourses;
	}

	public void setInterviewProgramCourses(
			Set<InterviewProgramCourse> interviewProgramCourses) {
		this.interviewProgramCourses = interviewProgramCourses;
	}

	public Boolean getIsWorkExperienceRequired() {
		return isWorkExperienceRequired;
	}

	public void setIsWorkExperienceRequired(Boolean isWorkExperienceRequired) {
		this.isWorkExperienceRequired = isWorkExperienceRequired;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Set<CourseApplicationNumber> getCourseApplicationNumbers() {
		return this.courseApplicationNumbers;
	}

	public void setCourseApplicationNumbers(
			Set<CourseApplicationNumber> courseApplicationNumbers) {
		this.courseApplicationNumbers = courseApplicationNumbers;
	}

	public Set<AdmAppln> getAdmApplnsForCourseId() {
		return admApplnsForCourseId;
	}

	public void setAdmApplnsForCourseId(Set<AdmAppln> admApplnsForCourseId) {
		this.admApplnsForCourseId = admApplnsForCourseId;
	}

	public Set<AdmAppln> getAdmApplnsForSelectedCourseId() {
		return admApplnsForSelectedCourseId;
	}

	public void setAdmApplnsForSelectedCourseId(
			Set<AdmAppln> admApplnsForSelectedCourseId) {
		this.admApplnsForSelectedCourseId = admApplnsForSelectedCourseId;
	}

	public Set<DetailedSubjects> getDetailedSubjectses() {
		return detailedSubjectses;
	}

	public void setDetailedSubjectses(Set<DetailedSubjects> detailedSubjectses) {
		this.detailedSubjectses = detailedSubjectses;
	}

	public Set<GroupTemplate> getGroupTemplates() {
		return groupTemplates;
	}

	public void setGroupTemplates(Set<GroupTemplate> groupTemplates) {
		this.groupTemplates = groupTemplates;
	}

	public Boolean getIsDetailMarksPrint() {
		return isDetailMarksPrint;
	}

	public void setIsDetailMarksPrint(Boolean isDetailMarksPrint) {
		this.isDetailMarksPrint = isDetailMarksPrint;
	}
	

}
