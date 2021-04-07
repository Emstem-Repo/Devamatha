package com.kp.cms.to.attendance;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.CollegeTO;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.admin.CoursePrerequisiteTO;
import com.kp.cms.to.admin.CourseSchemeTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.CurrencyMasterTO;
import com.kp.cms.to.admin.DetailedSubjectsTO;
import com.kp.cms.to.admin.DocTypeTO;
import com.kp.cms.to.admin.EligibilityCriteriaTO;
import com.kp.cms.to.admin.ExtracurricularActivityTO;
import com.kp.cms.to.admin.GradeTO;
import com.kp.cms.to.admin.GuidelinesEntryTO;
import com.kp.cms.to.admin.NationalityTO;
import com.kp.cms.to.admin.NewsEventsTO;
import com.kp.cms.to.admin.PreferencesTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.RecommendedByTO;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.kp.cms.to.admin.RemarkTypeTO;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.to.admin.SubjectGroupTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.admin.TermsConditionTO;
import com.kp.cms.to.admission.AdmissionStatusTO;
import com.kp.cms.to.admission.ApplicationNumberTO;
import com.kp.cms.to.admission.InterviewProgramCourseTO;
import com.kp.cms.to.admission.InterviewSubroundsTO;
import com.kp.cms.to.fee.FeeAccountTO;
import com.kp.cms.to.fee.FeeAdditionalTO;
import com.kp.cms.to.fee.FeeBillNumberTO;
import com.kp.cms.to.fee.FeeGroupTO;
import com.kp.cms.to.fee.FeeHeadingTO;
import com.kp.cms.to.usermanagement.MenusTO;
import com.kp.cms.to.usermanagement.ModuleTO;

public class MasterReportTO implements Serializable{

	private String createdBy;;
	private String modifiedBy;
	private String name;
	private Date createdDate;
	private Date lastModifiedDate;
	private String isActive;
	private CasteTO casteTO;
	private CountryTO country;
	private AdmissionStatusTO admissionStatusTO;
	private CourseSchemeTO courseSchemeTO;
	private PreferencesTO preferencesTO;
	private CurrencyMasterTO currencyMasterTO;
	private ReligionSectionTO religionSectionTO;
	private StateTO stateTO;
	private CoursePrerequisiteTO coursePrerequisiteTO;
	private ProgramTO  programTO;
	private CourseTO  courseTO;
	private GuidelinesEntryTO guidelinesEntryTO;
	private TermsConditionTO termsConditionTO;
	private SubjectTO subjectTO;
	private ClassesTO classesTO;
	private DocTypeTO docTypeTO;
	private CollegeTO collegeTO;
	private GradeTO gradeTO;
	private RecommendedByTO recommendedByTO;
	private SubjectGroupTO subjectGroupTO;
	private EligibilityCriteriaTO eligibilityCriteriaTO;
	private NewsEventsTO newsEventsTO;
	private ExtracurricularActivityTO extracurricularActivityTO;
	private FeeBillNumberTO feeBillNumberTO;
	private FeeGroupTO feeGroupTO;
	private FeeHeadingTO feeHeadingTO;
	private FeeAdditionalTO feeAdditionalTO;
	private FeeAccountTO feeAccountTO;
	private PeriodTO periodTO;
	private InterviewSubroundsTO interviewSubroundsTO;
	private InterviewProgramCourseTO interviewProgramCourseTO;
	private ApplicationNumberTO applicationNumberTO;
	private ActivityTO activityTO;
	private AttendanceTypeTO attendanceTypeTO;
	private String cDate;
	private String lDate;
	private ModuleTO moduleTO;
	private MenusTO menusTO;
	private RemarkTypeTO remarkTypeTO;
	private NationalityTO nationalityTO;
	private String fieldName;
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getCDate() {
		return cDate;
	}
	public void setCDate(String date) {
		cDate = date;
	}
	public String getLDate() {
		return lDate;
	}
	public void setLDate(String date) {
		lDate = date;
	}
	public SubjectGroupTO getSubjectGroupTO() {
		return subjectGroupTO;
	}
	public DetailedSubjectsTO getDetailedSubjectsTO() {
		return detailedSubjectsTO;
	}
	public void setSubjectGroupTO(SubjectGroupTO subjectGroupTO) {
		this.subjectGroupTO = subjectGroupTO;
	}
	public void setDetailedSubjectsTO(DetailedSubjectsTO detailedSubjectsTO) {
		this.detailedSubjectsTO = detailedSubjectsTO;
	}
	private DetailedSubjectsTO detailedSubjectsTO;
	
	public ClassesTO getClassesTO() {
		return classesTO;
	}
	public void setClassesTO(ClassesTO classesTO) {
		this.classesTO = classesTO;
	}
	public SubjectTO getSubjectTO() {
		return subjectTO;
	}
	public void setSubjectTO(SubjectTO subjectTO) {
		this.subjectTO = subjectTO;
	}
	public TermsConditionTO getTermsConditionTO() {
		return termsConditionTO;
	}
	public void setTermsConditionTO(TermsConditionTO termsConditionTO) {
		this.termsConditionTO = termsConditionTO;
	}
	public GuidelinesEntryTO getGuidelinesEntryTO() {
		return guidelinesEntryTO;
	}
	public void setGuidelinesEntryTO(GuidelinesEntryTO guidelinesEntryTO) {
		this.guidelinesEntryTO = guidelinesEntryTO;
	}
	public ProgramTO getProgramTO() {
		return programTO;
	}
	public void setProgramTO(ProgramTO programTO) {
		this.programTO = programTO;
	}
	public CoursePrerequisiteTO getCoursePrerequisiteTO() {
		return coursePrerequisiteTO;
	}
	public void setCoursePrerequisiteTO(CoursePrerequisiteTO coursePrerequisiteTO) {
		this.coursePrerequisiteTO = coursePrerequisiteTO;
	}
	public StateTO getStateTO() {
		return stateTO;
	}
	public void setStateTO(StateTO stateTO) {
		this.stateTO = stateTO;
	}
	public ReligionSectionTO getReligionSectionTO() {
		return religionSectionTO;
	}
	public void setReligionSectionTO(ReligionSectionTO religionSectionTO) {
		this.religionSectionTO = religionSectionTO;
	}
	public CurrencyMasterTO getCurrencyMasterTO() {
		return currencyMasterTO;
	}
	public void setCurrencyMasterTO(CurrencyMasterTO currencyMasterTO) {
		this.currencyMasterTO = currencyMasterTO;
	}
	public PreferencesTO getPreferencesTO() {
		return preferencesTO;
	}
	public void setPreferencesTO(PreferencesTO preferencesTO) {
		this.preferencesTO = preferencesTO;
	}
	public CourseSchemeTO getCourseSchemeTO() {
		return courseSchemeTO;
	}
	public void setCourseSchemeTO(CourseSchemeTO courseSchemeTO) {
		this.courseSchemeTO = courseSchemeTO;
	}
	public AdmissionStatusTO getAdmissionStatusTO() {
		return admissionStatusTO;
	}
	public void setAdmissionStatusTO(AdmissionStatusTO admissionStatusTO) {
		this.admissionStatusTO = admissionStatusTO;
	}
	public CountryTO getCountry() {
		return country;
	}
	public void setCountry(CountryTO country) {
		this.country = country;
	}
	public CasteTO getCasteTO() {
		return casteTO;
	}
	public void setCasteTO(CasteTO casteTO) {
		this.casteTO = casteTO;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public CourseTO getCourseTO() {
		return courseTO;
	}
	public void setCourseTO(CourseTO courseTO) {
		this.courseTO = courseTO;
	}
	public DocTypeTO getDocTypeTO() {
		return docTypeTO;
	}
	public CollegeTO getCollegeTO() {
		return collegeTO;
	}
	public GradeTO getGradeTO() {
		return gradeTO;
	}
	public RecommendedByTO getRecommendedByTO() {
		return recommendedByTO;
	}
	public void setDocTypeTO(DocTypeTO docTypeTO) {
		this.docTypeTO = docTypeTO;
	}
	public void setCollegeTO(CollegeTO collegeTO) {
		this.collegeTO = collegeTO;
	}
	public void setGradeTO(GradeTO gradeTO) {
		this.gradeTO = gradeTO;
	}
	public void setRecommendedByTO(RecommendedByTO recommendedByTO) {
		this.recommendedByTO = recommendedByTO;
	}
	public EligibilityCriteriaTO getEligibilityCriteriaTO() {
		return eligibilityCriteriaTO;
	}
	public void setEligibilityCriteriaTO(EligibilityCriteriaTO eligibilityCriteriaTO) {
		this.eligibilityCriteriaTO = eligibilityCriteriaTO;
	}
	public NewsEventsTO getNewsEventsTO() {
		return newsEventsTO;
	}
	public void setNewsEventsTO(NewsEventsTO newsEventsTO) {
		this.newsEventsTO = newsEventsTO;
	}
	public ExtracurricularActivityTO getExtracurricularActivityTO() {
		return extracurricularActivityTO;
	}
	public void setExtracurricularActivityTO(
			ExtracurricularActivityTO extracurricularActivityTO) {
		this.extracurricularActivityTO = extracurricularActivityTO;
	}
	public FeeBillNumberTO getFeeBillNumberTO() {
		return feeBillNumberTO;
	}
	public void setFeeBillNumberTO(FeeBillNumberTO feeBillNumberTO) {
		this.feeBillNumberTO = feeBillNumberTO;
	}
	public FeeGroupTO getFeeGroupTO() {
		return feeGroupTO;
	}
	public void setFeeGroupTO(FeeGroupTO feeGroupTO) {
		this.feeGroupTO = feeGroupTO;
	}
	public FeeHeadingTO getFeeHeadingTO() {
		return feeHeadingTO;
	}
	public void setFeeHeadingTO(FeeHeadingTO feeHeadingTO) {
		this.feeHeadingTO = feeHeadingTO;
	}
	public FeeAdditionalTO getFeeAdditionalTO() {
		return feeAdditionalTO;
	}
	public void setFeeAdditionalTO(FeeAdditionalTO feeAdditionalTO) {
		this.feeAdditionalTO = feeAdditionalTO;
	}
	public FeeAccountTO getFeeAccountTO() {
		return feeAccountTO;
	}
	public void setFeeAccountTO(FeeAccountTO feeAccountTO) {
		this.feeAccountTO = feeAccountTO;
	}
	public PeriodTO getPeriodTO() {
		return periodTO;
	}
	public void setPeriodTO(PeriodTO periodTO) {
		this.periodTO = periodTO;
	}
	public InterviewSubroundsTO getInterviewSubroundsTO() {
		return interviewSubroundsTO;
	}
	public void setInterviewSubroundsTO(InterviewSubroundsTO interviewSubroundsTO) {
		this.interviewSubroundsTO = interviewSubroundsTO;
	}
	public InterviewProgramCourseTO getInterviewProgramCourseTO() {
		return interviewProgramCourseTO;
	}
	public void setInterviewProgramCourseTO(
			InterviewProgramCourseTO interviewProgramCourseTO) {
		this.interviewProgramCourseTO = interviewProgramCourseTO;
	}
	public ApplicationNumberTO getApplicationNumberTO() {
		return applicationNumberTO;
	}
	public void setApplicationNumberTO(ApplicationNumberTO applicationNumberTO) {
		this.applicationNumberTO = applicationNumberTO;
	}
	public ActivityTO getActivityTO() {
		return activityTO;
	}
	public void setActivityTO(ActivityTO activityTO) {
		this.activityTO = activityTO;
	}
	public AttendanceTypeTO getAttendanceTypeTO() {
		return attendanceTypeTO;
	}
	public void setAttendanceTypeTO(AttendanceTypeTO attendanceTypeTO) {
		this.attendanceTypeTO = attendanceTypeTO;
	}
	public ModuleTO getModuleTO() {
		return moduleTO;
	}
	public void setModuleTO(ModuleTO moduleTO) {
		this.moduleTO = moduleTO;
	}
	public MenusTO getMenusTO() {
		return menusTO;
	}
	public void setMenusTO(MenusTO menusTO) {
		this.menusTO = menusTO;
	}
	public RemarkTypeTO getRemarkTypeTO() {
		return remarkTypeTO;
	}
	public void setRemarkTypeTO(RemarkTypeTO remarkTypeTO) {
		this.remarkTypeTO = remarkTypeTO;
	}
	public NationalityTO getNationalityTO() {
		return nationalityTO;
	}
	public void setNationalityTO(NationalityTO nationalityTO) {
		this.nationalityTO = nationalityTO;
	}

	
}
