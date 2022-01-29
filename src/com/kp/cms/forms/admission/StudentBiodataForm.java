package com.kp.cms.forms.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamStudentBioDataBO;
import com.kp.cms.bo.exam.ExamStudentPreviousClassDetailsBO;
import com.kp.cms.bo.exam.ExamStudentSubGrpHistoryBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.handlers.admin.CandidateMarkTO;
import com.kp.cms.to.admin.AdmittedThroughTO;
import com.kp.cms.to.admin.ApplicantLateralDetailsTO;
import com.kp.cms.to.admin.ApplicantMarkDetailsTO;
import com.kp.cms.to.admin.ApplicantTransferDetailsTO;
import com.kp.cms.to.admin.CandidatePreferenceTO;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.CurrencyTO;
import com.kp.cms.to.admin.EntrancedetailsTO;
import com.kp.cms.to.admin.IncomeTO;
import com.kp.cms.to.admin.LanguageTO;
import com.kp.cms.to.admin.NationalityTO;
import com.kp.cms.to.admin.OccupationTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.kp.cms.to.admin.ReligionTO;
import com.kp.cms.to.admin.RemarkTypeTO;
import com.kp.cms.to.admin.ResidentCategoryTO;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectGroupTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.StudentRemarksTO;
import com.kp.cms.to.exam.ExamStudentDetentionDetailsTO;
import com.kp.cms.to.exam.ExamStudentDiscontinuationDetailsTO;
import com.kp.cms.to.exam.ExamStudentPreviousClassTo;
import com.kp.cms.to.exam.ExamStudentRejoinTO;
import com.kp.cms.to.exam.ExamSubjectGroupDetailsTO;
import com.kp.cms.to.exam.KeyValueTO;

public class StudentBiodataForm extends BaseActionForm {
	private String method;
	private String applicationNo;
	private String regNo;
	private String academicYear;
	private String semister;
	private String rollNo;
	private String firstName;
	private String selectedAppNo;
	private String selectedYear;
	private int classSchemeId;
	private String classSchemeName;
	private boolean quotaCheck;
	private boolean eligibleCheck;
	private AdmApplnTO applicantDetails;
	private boolean workExpNeeded;
	private List<ProgramTypeTO> editProgramtypes;
	private List<CourseTO> editcourses;
	private boolean displayMotherTongue;
	private boolean displayLanguageKnown;
	private boolean displayHeightWeight;
	private boolean displayTrainingDetails;
	private boolean displayAdditionalInfo;
	private boolean displayExtracurricular;
	private boolean displaySecondLanguage;
	private boolean displayFamilyBackground;
	private boolean displayLateralDetails;
	private boolean displayTransferCourse;
	private boolean displayEntranceDetails;
	private boolean displayTCDetails;
	private boolean displayExtraDetails;
	private boolean displayLateralTransfer;
	private List<ProgramTO> editprograms;
	private List<CountryTO> countries;
	private List<StateTO> editStates;
	private List<StateTO> editPermanentStates;
	private List<StateTO> editCurrentStates;
	private List<StateTO> editParentStates;
	private List<StateTO> editGuardianStates;
	private List<NationalityTO> nationalities;
	private List<LanguageTO> mothertongues;
	private List<LanguageTO> languages;
	private String organizationName;
	private boolean needApproval;
	private List<ResidentCategoryTO> residentTypes;
	private List<ReligionTO> religions;
	private List<ReligionSectionTO> subReligions;
	private List<IncomeTO> incomeList;
	private List<CurrencyTO> currencyList;
	private List<OccupationTO> occupations;
	private List<UniversityTO> universities;
	private List<CasteTO> casteList;
	private List<AdmittedThroughTO> admittedThroughList = null;
	private List<SubjectGroupTO> subGroupList = null;
	private String submitDate;
	private List<CandidatePreferenceTO> preferenceList = null;
	private Student originalStudent;
	private CandidateMarkTO detailMark;
	private List<ApplicantMarkDetailsTO> semesterList;
	private String isLanguageWiseMarks;
	private List<ApplicantLateralDetailsTO> lateralDetails;
	private String lateralUniversityName;
	private String lateralStateName;
	private String lateralInstituteAddress;
	private List<EntrancedetailsTO> entranceList = null;
	private List<ApplicantTransferDetailsTO> transferDetails;
	private String transUnvrAppNo;
	private String transRegistationNo;
	private String transArrearDetail;
	private String transInstituteAddr;

	private List<ProgramTypeTO> programTypeList;
	private List<StudentTO> studentTOList;

	private boolean detailsView;
	private boolean sameTempAddr;

	private List<String> originalDocList;
	private Map<Integer, String> detailedSubjectsMap;
	private Map<Integer, String> classesMap;
	private int studentId;

	private int currentStudId;
	private List<RemarkTypeTO> remarkTypeList;
	private List<StudentRemarksTO> studentRemarksList;

	private String remarkTypeId;
	private String remarkComment;

	private boolean accessRemarks;
	private boolean editRemarks;
	private boolean viewRemarks;
	private List<StateTO> ednStates;
	private String viewStudentId;
	// Added by 9-elements
	private ArrayList<KeyValueTO> specialisationList;
	private String[] specialisationId;
	private HashMap<Integer, String> secondLanguageList;
	private String secondLanguageId;
	private String consolidateMarksNo;
	private String courseNameForMarkscard;
	private String detentiondetailsRadio;
	private String detentiondetailsDate;
	private String detantiondetailReasons;
	private String discontinuedDetailsRadio;
	private String discontinuedDetailsDate;
	private String discontinuedDetailsReasons;
	private String rejoinDetailsDate;
	private String readmittedClass;
	private String subjectGroupsDetailsLink;
	private String discontinuedDetailsLink;
	private String rejoinDetailsLink;
	private String detentionDetailsLink;
	private HashMap<Integer, String> readmittedClassList;
	private HashMap<Integer, String> batchList;
	private ArrayList<ExamStudentDiscontinuationDetailsTO> discontinueDetailsTO = null;
	private ArrayList<ExamStudentDetentionDetailsTO> detentionDetailsTO = null;
	private ArrayList<ExamSubjectGroupDetailsTO> subjectGroupDetailsTo;
	private ArrayList<ExamStudentRejoinTO> studentRejoinTO = null;
	private String batch;
	private String originalBatch;
	private String originalClassAdmitted;
	private String reMark;
	private String originalRegNo;
	private String tempYear;
	private String tempApplNo;
	private String tempRegNo;
	private String tempRollNo;
	private String tempcourseId;
	private String tempProgId;
	private String tempFirstName;
	private String tempSemNo;
	private String tempProgTypeId;
	private String examRegNo;
	private String studentNo;
	private int examStudentBiodataId;
	private int detentionId;
	private int disContinuedId;
	private List<ExamStudentDetentionDetailsTO> detentionDetHistoryList; 
	private List<ExamStudentDetentionDetailsTO> discontinuedDetHistoryList;
	private List<ExamStudentDetentionDetailsTO> rejoinDetHistoryList;

	private String totalobtainedMarkWithLanguage;
	private String totalMarkWithLanguage;
	private String totalobtainedMarkWithoutLan;
	private String totalMarkWithoutLan;
	private Map<Integer, String> rejoinClassMap;
	List<ExamStudentBioDataBO> studentBoList;
	private List<SubjectGroupTO> subGroupHistoryList = null;
	private List<ExamStudentSubGrpHistoryBO> studentSubHistoryList=null;
	private String[] subjGroupHistId;
	private String classHistId;
	Map<Integer,ExamStudentSubGrpHistoryBO> subjHistMap;
	Map<Integer, String> classesHistMap;
	private String bankAccNo;
	private String tcNo;
	private Date tcDate;
	private String tcType;
	private boolean tcDetails=false;
	private String previousClassId;
	private ArrayList<ExamStudentPreviousClassDetailsBO> previousClassDetailsTo;
	private List<ExamStudentPreviousClassTo> previousClassDetails;
	private Boolean isCjc;
	// added by chandra 
	private String prgId;
	
	
	
	public String getTcNo() {
		return tcNo;
	}

	public void setTcNo(String tcNo) {
		this.tcNo = tcNo;
	}

	public Date getTcDate() {
		return tcDate;
	}

	public void setTcDate(Date tcDate) {
		this.tcDate = tcDate;
	}

	public String getTcType() {
		return tcType;
	}

	public void setTcType(String tcType) {
		this.tcType = tcType;
	}

	public ArrayList<KeyValueTO> getSpecialisationList() {
		return specialisationList;
	}

	public void setSpecialisationList(ArrayList<KeyValueTO> specialisationList) {
		this.specialisationList = specialisationList;
	}

	public String[] getSpecialisationId() {
		return specialisationId;
	}

	public void setSpecialisationId(String[] specialisationId) {
		this.specialisationId = specialisationId;
	}

	public HashMap<Integer, String> getSecondLanguageList() {
		return secondLanguageList;
	}

	public void setSecondLanguageList(
			HashMap<Integer, String> secondLanguageList) {
		this.secondLanguageList = secondLanguageList;
	}

	public String getSecondLanguageId() {
		return secondLanguageId;
	}

	public void setSecondLanguageId(String secondLanguageId) {
		this.secondLanguageId = secondLanguageId;
	}

	public String getConsolidateMarksNo() {
		return consolidateMarksNo;
	}

	public void setConsolidateMarksNo(String consolidateMarksNo) {
		this.consolidateMarksNo = consolidateMarksNo;
	}

	public String getCourseNameForMarkscard() {
		return courseNameForMarkscard;
	}

	public void setCourseNameForMarkscard(String courseNameForMarkscard) {
		this.courseNameForMarkscard = courseNameForMarkscard;
	}

	public String getDetentiondetailsRadio() {
		return detentiondetailsRadio;
	}

	public void setDetentiondetailsRadio(String detentiondetailsRadio) {
		this.detentiondetailsRadio = detentiondetailsRadio;
	}

	public String getDetentiondetailsDate() {
		return detentiondetailsDate;
	}

	public void setDetentiondetailsDate(String detentiondetailsDate) {
		this.detentiondetailsDate = detentiondetailsDate;
	}

	public String getDetantiondetailReasons() {
		return detantiondetailReasons;
	}

	public void setDetantiondetailReasons(String detantiondetailReasons) {
		this.detantiondetailReasons = detantiondetailReasons;
	}

	public String getDiscontinuedDetailsRadio() {
		return discontinuedDetailsRadio;
	}

	public void setDiscontinuedDetailsRadio(String discontinuedDetailsRadio) {
		this.discontinuedDetailsRadio = discontinuedDetailsRadio;
	}

	public String getDiscontinuedDetailsDate() {
		return discontinuedDetailsDate;
	}

	public void setDiscontinuedDetailsDate(String discontinuedDetailsDate) {
		this.discontinuedDetailsDate = discontinuedDetailsDate;
	}

	public String getDiscontinuedDetailsReasons() {
		return discontinuedDetailsReasons;
	}

	public void setDiscontinuedDetailsReasons(String discontinuedDetailsReasons) {
		this.discontinuedDetailsReasons = discontinuedDetailsReasons;
	}

	public String getRejoinDetailsDate() {
		return rejoinDetailsDate;
	}

	public void setRejoinDetailsDate(String rejoinDetailsDate) {
		this.rejoinDetailsDate = rejoinDetailsDate;
	}

	public String getReadmittedClass() {
		return readmittedClass;
	}

	public void setReadmittedClass(String readmittedClass) {
		this.readmittedClass = readmittedClass;
	}

	public String getSubjectGroupsDetailsLink() {
		return subjectGroupsDetailsLink;
	}

	public void setSubjectGroupsDetailsLink(String subjectGroupsDetailsLink) {
		this.subjectGroupsDetailsLink = subjectGroupsDetailsLink;
	}

	public String getDiscontinuedDetailsLink() {
		return discontinuedDetailsLink;
	}

	public void setDiscontinuedDetailsLink(String discontinuedDetailsLink) {
		this.discontinuedDetailsLink = discontinuedDetailsLink;
	}

	public String getRejoinDetailsLink() {
		return rejoinDetailsLink;
	}

	public void setRejoinDetailsLink(String rejoinDetailsLink) {
		this.rejoinDetailsLink = rejoinDetailsLink;
	}

	public String getDetentionDetailsLink() {
		return detentionDetailsLink;
	}

	public void setDetentionDetailsLink(String detentionDetailsLink) {
		this.detentionDetailsLink = detentionDetailsLink;
	}

	public HashMap<Integer, String> getReadmittedClassList() {
		return readmittedClassList;
	}

	public void setReadmittedClassList(
			HashMap<Integer, String> readmittedClassList) {
		this.readmittedClassList = readmittedClassList;
	}

	public HashMap<Integer, String> getBatchList() {
		return batchList;
	}

	public void setBatchList(HashMap<Integer, String> batchList) {
		this.batchList = batchList;
	}

	public ArrayList<ExamStudentDiscontinuationDetailsTO> getDiscontinueDetailsTO() {
		return discontinueDetailsTO;
	}

	public void setDiscontinueDetailsTO(
			ArrayList<ExamStudentDiscontinuationDetailsTO> discontinueDetailsTO) {
		this.discontinueDetailsTO = discontinueDetailsTO;
	}

	public ArrayList<ExamStudentDetentionDetailsTO> getDetentionDetailsTO() {
		return detentionDetailsTO;
	}

	public void setDetentionDetailsTO(
			ArrayList<ExamStudentDetentionDetailsTO> detentionDetailsTO) {
		this.detentionDetailsTO = detentionDetailsTO;
	}

	public ArrayList<ExamSubjectGroupDetailsTO> getSubjectGroupDetailsTo() {
		return subjectGroupDetailsTo;
	}

	public void setSubjectGroupDetailsTo(
			ArrayList<ExamSubjectGroupDetailsTO> subjectGroupDetailsTo) {
		this.subjectGroupDetailsTo = subjectGroupDetailsTo;
	}

	public ArrayList<ExamStudentRejoinTO> getStudentRejoinTO() {
		return studentRejoinTO;
	}

	public void setStudentRejoinTO(
			ArrayList<ExamStudentRejoinTO> studentRejoinTO) {
		this.studentRejoinTO = studentRejoinTO;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getReMark() {
		return reMark;
	}

	public void setReMark(String reMark) {
		this.reMark = reMark;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getSemister() {
		return semister;
	}

	public void setSemister(String semister) {
		this.semister = semister;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	public List<StudentTO> getStudentTOList() {
		return studentTOList;
	}

	public void setStudentTOList(List<StudentTO> studentTOList) {
		this.studentTOList = studentTOList;
	}

	public String getSelectedAppNo() {
		return selectedAppNo;
	}

	public void setSelectedAppNo(String selectedAppNo) {
		this.selectedAppNo = selectedAppNo;
	}

	public String getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(String selectedYear) {
		this.selectedYear = selectedYear;
	}

	public AdmApplnTO getApplicantDetails() {
		return applicantDetails;
	}

	public void setApplicantDetails(AdmApplnTO applicantDetails) {
		this.applicantDetails = applicantDetails;
	}

	public boolean isWorkExpNeeded() {
		return workExpNeeded;
	}

	public void setWorkExpNeeded(boolean workExpNeeded) {
		this.workExpNeeded = workExpNeeded;
	}

	public List<ProgramTypeTO> getEditProgramtypes() {
		return editProgramtypes;
	}

	public void setEditProgramtypes(List<ProgramTypeTO> editProgramtypes) {
		this.editProgramtypes = editProgramtypes;
	}

	public List<CourseTO> getEditcourses() {
		return editcourses;
	}

	public void setEditcourses(List<CourseTO> editcourses) {
		this.editcourses = editcourses;
	}

	public boolean isDisplayMotherTongue() {
		return displayMotherTongue;
	}

	public void setDisplayMotherTongue(boolean displayMotherTongue) {
		this.displayMotherTongue = displayMotherTongue;
	}

	public boolean isDisplayLanguageKnown() {
		return displayLanguageKnown;
	}

	public void setDisplayLanguageKnown(boolean displayLanguageKnown) {
		this.displayLanguageKnown = displayLanguageKnown;
	}

	public boolean isDisplayHeightWeight() {
		return displayHeightWeight;
	}

	public void setDisplayHeightWeight(boolean displayHeightWeight) {
		this.displayHeightWeight = displayHeightWeight;
	}

	public boolean isDisplayTrainingDetails() {
		return displayTrainingDetails;
	}

	public void setDisplayTrainingDetails(boolean displayTrainingDetails) {
		this.displayTrainingDetails = displayTrainingDetails;
	}

	public boolean isDisplayAdditionalInfo() {
		return displayAdditionalInfo;
	}

	public void setDisplayAdditionalInfo(boolean displayAdditionalInfo) {
		this.displayAdditionalInfo = displayAdditionalInfo;
	}

	public boolean isDisplayExtracurricular() {
		return displayExtracurricular;
	}

	public void setDisplayExtracurricular(boolean displayExtracurricular) {
		this.displayExtracurricular = displayExtracurricular;
	}

	public boolean isDisplaySecondLanguage() {
		return displaySecondLanguage;
	}

	public void setDisplaySecondLanguage(boolean displaySecondLanguage) {
		this.displaySecondLanguage = displaySecondLanguage;
	}

	public boolean isDisplayFamilyBackground() {
		return displayFamilyBackground;
	}

	public void setDisplayFamilyBackground(boolean displayFamilyBackground) {
		this.displayFamilyBackground = displayFamilyBackground;
	}

	public boolean isDisplayLateralDetails() {
		return displayLateralDetails;
	}

	public void setDisplayLateralDetails(boolean displayLateralDetails) {
		this.displayLateralDetails = displayLateralDetails;
	}

	public boolean isDisplayTransferCourse() {
		return displayTransferCourse;
	}

	public void setDisplayTransferCourse(boolean displayTransferCourse) {
		this.displayTransferCourse = displayTransferCourse;
	}

	public boolean isDisplayEntranceDetails() {
		return displayEntranceDetails;
	}

	public void setDisplayEntranceDetails(boolean displayEntranceDetails) {
		this.displayEntranceDetails = displayEntranceDetails;
	}

	public boolean isDisplayTCDetails() {
		return displayTCDetails;
	}

	public void setDisplayTCDetails(boolean displayTCDetails) {
		this.displayTCDetails = displayTCDetails;
	}

	public boolean isDisplayExtraDetails() {
		return displayExtraDetails;
	}

	public void setDisplayExtraDetails(boolean displayExtraDetails) {
		this.displayExtraDetails = displayExtraDetails;
	}

	public boolean isDisplayLateralTransfer() {
		return displayLateralTransfer;
	}

	public void setDisplayLateralTransfer(boolean displayLateralTransfer) {
		this.displayLateralTransfer = displayLateralTransfer;
	}

	public List<ProgramTO> getEditprograms() {
		return editprograms;
	}

	public void setEditprograms(List<ProgramTO> editprograms) {
		this.editprograms = editprograms;
	}

	public List<CountryTO> getCountries() {
		return countries;
	}

	public void setCountries(List<CountryTO> countries) {
		this.countries = countries;
	}

	public List<StateTO> getEditStates() {
		return editStates;
	}

	public void setEditStates(List<StateTO> editStates) {
		this.editStates = editStates;
	}

	public List<StateTO> getEditPermanentStates() {
		return editPermanentStates;
	}

	public void setEditPermanentStates(List<StateTO> editPermanentStates) {
		this.editPermanentStates = editPermanentStates;
	}

	public List<StateTO> getEditCurrentStates() {
		return editCurrentStates;
	}

	public void setEditCurrentStates(List<StateTO> editCurrentStates) {
		this.editCurrentStates = editCurrentStates;
	}

	public List<StateTO> getEditParentStates() {
		return editParentStates;
	}

	public void setEditParentStates(List<StateTO> editParentStates) {
		this.editParentStates = editParentStates;
	}

	public List<StateTO> getEditGuardianStates() {
		return editGuardianStates;
	}

	public void setEditGuardianStates(List<StateTO> editGuardianStates) {
		this.editGuardianStates = editGuardianStates;
	}

	public List<NationalityTO> getNationalities() {
		return nationalities;
	}

	public void setNationalities(List<NationalityTO> nationalities) {
		this.nationalities = nationalities;
	}

	public List<LanguageTO> getMothertongues() {
		return mothertongues;
	}

	public void setMothertongues(List<LanguageTO> mothertongues) {
		this.mothertongues = mothertongues;
	}

	public List<LanguageTO> getLanguages() {
		return languages;
	}

	public void setLanguages(List<LanguageTO> languages) {
		this.languages = languages;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public boolean isNeedApproval() {
		return needApproval;
	}

	public void setNeedApproval(boolean needApproval) {
		this.needApproval = needApproval;
	}

	public List<ResidentCategoryTO> getResidentTypes() {
		return residentTypes;
	}

	public void setResidentTypes(List<ResidentCategoryTO> residentTypes) {
		this.residentTypes = residentTypes;
	}

	public List<ReligionTO> getReligions() {
		return religions;
	}

	public void setReligions(List<ReligionTO> religions) {
		this.religions = religions;
	}

	public List<ReligionSectionTO> getSubReligions() {
		return subReligions;
	}

	public void setSubReligions(List<ReligionSectionTO> subReligions) {
		this.subReligions = subReligions;
	}

	public List<IncomeTO> getIncomeList() {
		return incomeList;
	}

	public void setIncomeList(List<IncomeTO> incomeList) {
		this.incomeList = incomeList;
	}

	public List<CurrencyTO> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<CurrencyTO> currencyList) {
		this.currencyList = currencyList;
	}

	public List<OccupationTO> getOccupations() {
		return occupations;
	}

	public void setOccupations(List<OccupationTO> occupations) {
		this.occupations = occupations;
	}

	public List<UniversityTO> getUniversities() {
		return universities;
	}

	public void setUniversities(List<UniversityTO> universities) {
		this.universities = universities;
	}

	public List<CasteTO> getCasteList() {
		return casteList;
	}

	public void setCasteList(List<CasteTO> casteList) {
		this.casteList = casteList;
	}

	public List<AdmittedThroughTO> getAdmittedThroughList() {
		return admittedThroughList;
	}

	public void setAdmittedThroughList(
			List<AdmittedThroughTO> admittedThroughList) {
		this.admittedThroughList = admittedThroughList;
	}

	public List<SubjectGroupTO> getSubGroupList() {
		return subGroupList;
	}

	public void setSubGroupList(List<SubjectGroupTO> subGroupList) {
		this.subGroupList = subGroupList;
	}

	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	public List<CandidatePreferenceTO> getPreferenceList() {
		return preferenceList;
	}

	public void setPreferenceList(List<CandidatePreferenceTO> preferenceList) {
		this.preferenceList = preferenceList;
	}

	public Student getOriginalStudent() {
		return originalStudent;
	}

	public void setOriginalStudent(Student originalStudent) {
		this.originalStudent = originalStudent;
	}

	public CandidateMarkTO getDetailMark() {
		return detailMark;
	}

	public void setDetailMark(CandidateMarkTO detailMark) {
		this.detailMark = detailMark;
	}

	public List<ApplicantMarkDetailsTO> getSemesterList() {
		return semesterList;
	}

	public void setSemesterList(List<ApplicantMarkDetailsTO> semesterList) {
		this.semesterList = semesterList;
	}

	public String getIsLanguageWiseMarks() {
		return isLanguageWiseMarks;
	}

	public void setIsLanguageWiseMarks(String isLanguageWiseMarks) {
		this.isLanguageWiseMarks = isLanguageWiseMarks;
	}

	public List<ApplicantLateralDetailsTO> getLateralDetails() {
		return lateralDetails;
	}

	public void setLateralDetails(List<ApplicantLateralDetailsTO> lateralDetails) {
		this.lateralDetails = lateralDetails;
	}

	public String getLateralUniversityName() {
		return lateralUniversityName;
	}

	public void setLateralUniversityName(String lateralUniversityName) {
		this.lateralUniversityName = lateralUniversityName;
	}

	public String getLateralStateName() {
		return lateralStateName;
	}

	public void setLateralStateName(String lateralStateName) {
		this.lateralStateName = lateralStateName;
	}

	public String getLateralInstituteAddress() {
		return lateralInstituteAddress;
	}

	public void setLateralInstituteAddress(String lateralInstituteAddress) {
		this.lateralInstituteAddress = lateralInstituteAddress;
	}

	public List<ApplicantTransferDetailsTO> getTransferDetails() {
		return transferDetails;
	}

	public void setTransferDetails(
			List<ApplicantTransferDetailsTO> transferDetails) {
		this.transferDetails = transferDetails;
	}

	public String getTransUnvrAppNo() {
		return transUnvrAppNo;
	}

	public void setTransUnvrAppNo(String transUnvrAppNo) {
		this.transUnvrAppNo = transUnvrAppNo;
	}

	public String getTransRegistationNo() {
		return transRegistationNo;
	}

	public void setTransRegistationNo(String transRegistationNo) {
		this.transRegistationNo = transRegistationNo;
	}

	public String getTransArrearDetail() {
		return transArrearDetail;
	}

	public void setTransArrearDetail(String transArrearDetail) {
		this.transArrearDetail = transArrearDetail;
	}

	public String getTransInstituteAddr() {
		return transInstituteAddr;
	}

	public void setTransInstituteAddr(String transInstituteAddr) {
		this.transInstituteAddr = transInstituteAddr;
	}

	public boolean isDetailsView() {
		return detailsView;
	}

	public void setDetailsView(boolean detailsView) {
		this.detailsView = detailsView;
	}

	public List<String> getOriginalDocList() {
		return originalDocList;
	}

	public void setOriginalDocList(List<String> originalDocList) {
		this.originalDocList = originalDocList;
	}

	public Map<Integer, String> getDetailedSubjectsMap() {
		return detailedSubjectsMap;
	}

	public void setDetailedSubjectsMap(Map<Integer, String> detailedSubjectsMap) {
		this.detailedSubjectsMap = detailedSubjectsMap;
	}

	public boolean isQuotaCheck() {
		return quotaCheck;
	}

	public void setQuotaCheck(boolean quotaCheck) {
		this.quotaCheck = quotaCheck;
	}

	public boolean isEligibleCheck() {
		return eligibleCheck;
	}

	public void setEligibleCheck(boolean eligibleCheck) {
		this.eligibleCheck = eligibleCheck;
	}

	public Map<Integer, String> getClassesMap() {
		return classesMap;
	}

	public void setClassesMap(Map<Integer, String> classesMap) {
		this.classesMap = classesMap;
	}

	public int getClassSchemeId() {
		return classSchemeId;
	}

	public void setClassSchemeId(int classSchemeId) {
		this.classSchemeId = classSchemeId;
	}

	public boolean isSameTempAddr() {
		return sameTempAddr;
	}

	public void setSameTempAddr(boolean sameTempAddr) {
		this.sameTempAddr = sameTempAddr;
	}

	public String getClassSchemeName() {
		return classSchemeName;
	}

	public void setClassSchemeName(String classSchemeName) {
		this.classSchemeName = classSchemeName;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getCurrentStudId() {
		return currentStudId;
	}

	public void setCurrentStudId(int currentStudId) {
		this.currentStudId = currentStudId;
	}

	public List<RemarkTypeTO> getRemarkTypeList() {
		return remarkTypeList;
	}

	public void setRemarkTypeList(List<RemarkTypeTO> remarkTypeList) {
		this.remarkTypeList = remarkTypeList;
	}

	public String getRemarkTypeId() {
		return remarkTypeId;
	}

	public void setRemarkTypeId(String remarkTypeId) {
		this.remarkTypeId = remarkTypeId;
	}

	public String getRemarkComment() {
		return remarkComment;
	}

	public void setRemarkComment(String remarkComment) {
		this.remarkComment = remarkComment;
	}

	public List<StudentRemarksTO> getStudentRemarksList() {
		return studentRemarksList;
	}

	public void setStudentRemarksList(List<StudentRemarksTO> studentRemarksList) {
		this.studentRemarksList = studentRemarksList;
	}

	public boolean isAccessRemarks() {
		return accessRemarks;
	}

	public void setAccessRemarks(boolean accessRemarks) {
		this.accessRemarks = accessRemarks;
	}

	public boolean isEditRemarks() {
		return editRemarks;
	}

	public void setEditRemarks(boolean editRemarks) {
		this.editRemarks = editRemarks;
	}

	public boolean isViewRemarks() {
		return viewRemarks;
	}

	public void setViewRemarks(boolean viewRemarks) {
		this.viewRemarks = viewRemarks;
	}

	public List<StateTO> getEdnStates() {
		return ednStates;
	}

	public void setEdnStates(List<StateTO> ednStates) {
		this.ednStates = ednStates;
	}

	public List<EntrancedetailsTO> getEntranceList() {
		return entranceList;
	}

	public void setEntranceList(List<EntrancedetailsTO> entranceList) {
		this.entranceList = entranceList;
	}

	/*
	 * (non-Javadoc) validation call
	 * 
	 * @see
	 * org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.
	 * action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void setViewStudentId(String viewStudentId) {
		this.viewStudentId = viewStudentId;
	}

	public String getViewStudentId() {
		return viewStudentId;
	}

	public String getTempYear() {
		return tempYear;
	}

	public void setTempYear(String tempYear) {
		this.tempYear = tempYear;
	}

	public String getTempApplNo() {
		return tempApplNo;
	}

	public void setTempApplNo(String tempApplNo) {
		this.tempApplNo = tempApplNo;
	}

	public String getTempRegNo() {
		return tempRegNo;
	}

	public void setTempRegNo(String tempRegNo) {
		this.tempRegNo = tempRegNo;
	}

	public String getTempRollNo() {
		return tempRollNo;
	}

	public void setTempRollNo(String tempRollNo) {
		this.tempRollNo = tempRollNo;
	}

	public String getTempcourseId() {
		return tempcourseId;
	}

	public void setTempcourseId(String tempcourseId) {
		this.tempcourseId = tempcourseId;
	}

	public String getTempProgId() {
		return tempProgId;
	}

	public void setTempProgId(String tempProgId) {
		this.tempProgId = tempProgId;
	}

	public String getTempFirstName() {
		return tempFirstName;
	}

	public void setTempFirstName(String tempFirstName) {
		this.tempFirstName = tempFirstName;
	}

	public String getTempSemNo() {
		return tempSemNo;
	}

	public void setTempSemNo(String tempSemNo) {
		this.tempSemNo = tempSemNo;
	}

	public String getTempProgTypeId() {
		return tempProgTypeId;
	}

	public void setTempProgTypeId(String tempProgTypeId) {
		this.tempProgTypeId = tempProgTypeId;
	}

	public void setOriginalBatch(String originalBatch) {
		this.originalBatch = originalBatch;
	}

	public String getOriginalBatch() {
		return originalBatch;
	}

	public void setOriginalClassAdmitted(String originalClassAdmitted) {
		this.originalClassAdmitted = originalClassAdmitted;
	}

	public String getOriginalClassAdmitted() {
		return originalClassAdmitted;
	}

	public String getExamRegNo() {
		return examRegNo;
	}

	public void setExamRegNo(String examRegNo) {
		this.examRegNo = examRegNo;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public int getExamStudentBiodataId() {
		return examStudentBiodataId;
	}

	public void setExamStudentBiodataId(int examStudentBiodataId) {
		this.examStudentBiodataId = examStudentBiodataId;
	}

	public int getDetentionId() {
		return detentionId;
	}

	public void setDetentionId(int detentionId) {
		this.detentionId = detentionId;
	}

	public int getDisContinuedId() {
		return disContinuedId;
	}

	public void setDisContinuedId(int disContinuedId) {
		this.disContinuedId = disContinuedId;
	}

	public List<ExamStudentDetentionDetailsTO> getDetentionDetHistoryList() {
		return detentionDetHistoryList;
	}

	public void setDetentionDetHistoryList(
			List<ExamStudentDetentionDetailsTO> detentionDetHistoryList) {
		this.detentionDetHistoryList = detentionDetHistoryList;
	}

	public List<ExamStudentDetentionDetailsTO> getDiscontinuedDetHistoryList() {
		return discontinuedDetHistoryList;
	}

	public void setDiscontinuedDetHistoryList(
			List<ExamStudentDetentionDetailsTO> discontinuedDetHistoryList) {
		this.discontinuedDetHistoryList = discontinuedDetHistoryList;
	}

	public List<ExamStudentDetentionDetailsTO> getRejoinDetHistoryList() {
		return rejoinDetHistoryList;
	}

	public void setRejoinDetHistoryList(
			List<ExamStudentDetentionDetailsTO> rejoinDetHistoryList) {
		this.rejoinDetHistoryList = rejoinDetHistoryList;
	}
	public String getTotalobtainedMarkWithLanguage() {
		return totalobtainedMarkWithLanguage;
	}

	public void setTotalobtainedMarkWithLanguage(
			String totalobtainedMarkWithLanguage) {
		this.totalobtainedMarkWithLanguage = totalobtainedMarkWithLanguage;
	}

	public String getTotalMarkWithLanguage() {
		return totalMarkWithLanguage;
	}

	public void setTotalMarkWithLanguage(String totalMarkWithLanguage) {
		this.totalMarkWithLanguage = totalMarkWithLanguage;
	}

	public String getTotalobtainedMarkWithoutLan() {
		return totalobtainedMarkWithoutLan;
	}

	public void setTotalobtainedMarkWithoutLan(String totalobtainedMarkWithoutLan) {
		this.totalobtainedMarkWithoutLan = totalobtainedMarkWithoutLan;
	}

	public String getTotalMarkWithoutLan() {
		return totalMarkWithoutLan;
	}

	public void setTotalMarkWithoutLan(String totalMarkWithoutLan) {
		this.totalMarkWithoutLan = totalMarkWithoutLan;
	}

	public Map<Integer, String> getRejoinClassMap() {
		return rejoinClassMap;
	}

	public void setRejoinClassMap(Map<Integer, String> rejoinClassMap) {
		this.rejoinClassMap = rejoinClassMap;
	}

	public List<ExamStudentBioDataBO> getStudentBoList() {
		return studentBoList;
	}

	public void setStudentBoList(List<ExamStudentBioDataBO> studentBoList) {
		this.studentBoList = studentBoList;
	}

	public List<SubjectGroupTO> getSubGroupHistoryList() {
		return subGroupHistoryList;
	}

	public void setSubGroupHistoryList(List<SubjectGroupTO> subGroupHistoryList) {
		this.subGroupHistoryList = subGroupHistoryList;
	}

	public List<ExamStudentSubGrpHistoryBO> getStudentSubHistoryList() {
		return studentSubHistoryList;
	}

	public void setStudentSubHistoryList(
			List<ExamStudentSubGrpHistoryBO> studentSubHistoryList) {
		this.studentSubHistoryList = studentSubHistoryList;
	}

	public String[] getSubjGroupHistId() {
		return subjGroupHistId;
	}

	public void setSubjGroupHistId(String[] subjGroupHistId) {
		this.subjGroupHistId = subjGroupHistId;
	}

	public String getClassHistId() {
		return classHistId;
	}

	public void setClassHistId(String classHistId) {
		this.classHistId = classHistId;
	}

	public Map<Integer, ExamStudentSubGrpHistoryBO> getSubjHistMap() {
		return subjHistMap;
	}

	public void setSubjHistMap(Map<Integer, ExamStudentSubGrpHistoryBO> subjHistMap) {
		this.subjHistMap = subjHistMap;
	}

	public String getPreviousClassId() {
		return previousClassId;
	}

	public void setPreviousClassId(String previousClassId) {
		this.previousClassId = previousClassId;
	}

	public Map<Integer, String> getClassesHistMap() {
		return classesHistMap;
	}

	public void setClassesHistMap(Map<Integer, String> classesHistMap) {
		this.classesHistMap = classesHistMap;
	}

	public String getBankAccNo() {
		return bankAccNo;
	}

	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}

	public void setTcDetails(boolean tcDetails) {
		this.tcDetails = tcDetails;
	}

	public boolean getTcDetails() {
		return tcDetails;
	}

	public ArrayList<ExamStudentPreviousClassDetailsBO> getPreviousClassDetailsTo() {
		return previousClassDetailsTo;
	}

	public void setPreviousClassDetailsTo(
			ArrayList<ExamStudentPreviousClassDetailsBO> previousClassDetailsTo) {
		this.previousClassDetailsTo = previousClassDetailsTo;
	}

	public List<ExamStudentPreviousClassTo> getPreviousClassDetails() {
		return previousClassDetails;
	}

	public void setPreviousClassDetails(
			List<ExamStudentPreviousClassTo> previousClassDetails) {
		this.previousClassDetails = previousClassDetails;
	}

	public Boolean getIsCjc() {
		return isCjc;
	}

	public void setIsCjc(Boolean isCjc) {
		this.isCjc = isCjc;
	}

	public String getPrgId() {
		return prgId;
	}

	public void setPrgId(String prgId) {
		this.prgId = prgId;
	}

	public String getOriginalRegNo() {
		return originalRegNo;
	}

	public void setOriginalRegNo(String originalRegNo) {
		this.originalRegNo = originalRegNo;
	}

	

	
	
	
}