package com.kp.cms.forms.admission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.handlers.admin.CandidateMarkTO;
import com.kp.cms.to.admin.AdmSubjectMarkForRankTO;
import com.kp.cms.to.admin.AdmittedThroughTO;
import com.kp.cms.to.admin.ApplicantLateralDetailsTO;
import com.kp.cms.to.admin.ApplicantMarkDetailsTO;
import com.kp.cms.to.admin.ApplicantTransferDetailsTO;
import com.kp.cms.to.admin.ApplicantWorkExperienceTO;
import com.kp.cms.to.admin.ApplnDocTO;
import com.kp.cms.to.admin.CandidatePreferenceEntranceDetailsTO;
import com.kp.cms.to.admin.CandidatePreferenceTO;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.admin.CoursePrerequisiteTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.CurrencyTO;
import com.kp.cms.to.admin.DioceseTo;
import com.kp.cms.to.admin.DistrictTO;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admin.EntrancedetailsTO;
import com.kp.cms.to.admin.ExamCenterTO;
import com.kp.cms.to.admin.ExtracurricularActivityTO;
import com.kp.cms.to.admin.GuideLinesCheckListTO;
import com.kp.cms.to.admin.IncomeTO;
import com.kp.cms.to.admin.LanguageTO;
import com.kp.cms.to.admin.NationalityTO;
import com.kp.cms.to.admin.OccupationTO;
import com.kp.cms.to.admin.ParishTo;
import com.kp.cms.to.admin.PreferencesTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.kp.cms.to.admin.ReligionTO;
import com.kp.cms.to.admin.ResidentCategoryTO;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.to.admin.StudentCourseAllotmentTo;
import com.kp.cms.to.admin.SubjectGroupTO;
import com.kp.cms.to.admin.TermsConditionChecklistTO;
import com.kp.cms.to.admin.UGCoursesTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.to.admission.AddressTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.PreferenceTO;

public class ApplicationEditForm extends BaseActionForm {
	private String applicationNumber;
	private String applicationYear;
	private AdmApplnTO applicantDetails;
	private String firstName;
	private String middleName;
	private String lastName;
	private String dateOfBirth;
	private String gender;
	private String castCategory;
	private String nationality;
	private String country;
	private String emailId;
	private String confirmEmailId;
	private String birthPlace;
	private String birthState;
	// rural or urban
	private char areaType = 'R';
	private String motherTongue;
	private String bloodGroup;
	private String region;
	private String phone1;
	private String phone2;
	private String phone3;
	private String mobile1;
	private String mobile2;
	private String mobile3;
	private String residentCategory;
	private List<ResidentCategoryTO> residentTypes;
	private PreferenceTO firstPref;
	private PreferenceTO secondPref;
	private PreferenceTO thirdPref;
	private String subReligion;
	private AddressTO permAddr;
	private AddressTO tempAddr;
	private String fatherName;
	private String motherName;
	private String fatherEducation;
	private String motherEducation;
	private String fatherOccupation;
	private String motherOccupation;
	private String parentPhone1;
	private String parentPhone2;
	private String parentPhone3;
	private String parentMobile1;
	private String parentMobile2;
	private String parentMobile3;
	private String fatherIncome;
	private String motherIncome;
	private String motherEmail;
	private AddressTO parentAddress;
	private boolean sameTempAddr;
	private String method;
	private String fatherCurrency;
	private String motherCurrency;
	private List<CasteTO> casteList;
	private List<ProgramTypeTO> programtypeList;
	private List<LanguageTO> mothertongues;
	private List<LanguageTO> languages;
	private List<CountryTO> countries;
	private List<CurrencyTO> currencies;
	private List<ReligionTO> religions;
	private List<ReligionSectionTO> subReligions;
	private String passportNo;
	private String passportcountry;
	private String passportValidity;
	private String residentPermit;
	private String permitDate;
	private String fatherEmail;
	private String challanNo;
	private String journalNo;
	private String applicationDate;
	private String applicationAmount;
	private List<UniversityTO> universities;
	private List<EdnQualificationTO> qualifications;
	private CandidateMarkTO detailMark;
	private List<ApplnDocTO> uploadDocs;
	private List<OccupationTO> occupations;
	private List<IncomeTO> incomes;
	private List<CourseTO> prefcourses;
	private List<ProgramTypeTO> prefProgramtypes;
	private List<ProgramTO> prefprograms;
	private List<NationalityTO> nationalities;
	private List<CourseTO> editcourses;
	private List<ProgramTypeTO> editProgramtypes;
	private List<ProgramTO> editprograms;
	private List<StateTO> editStates;
	private List<StateTO> editPermanentStates;
	private List<StateTO> editCurrentStates;
	private List<StateTO> editParentStates;
	private List<StateTO> editGuardianStates;
	private List<IncomeTO> incomeList;
	private List<CurrencyTO> currencyList;
	private String otherCastCategory;
	private String otherSubReligion;
	private String otherReligion;
	private String otherBirthState;
	private boolean guidelineExist;
	private int catScore;
	private int matScore;
	private boolean onlineApply;
	private String challanRefNo;
	private String courseName;
	private String coursePayCode;
	private String courseAmount;
	private boolean workExpNeeded;
	private List<CoursePrerequisiteTO> coursePrerequisites;
	private List<CoursePrerequisiteTO> eligPrerequisites;
	private ApplicantWorkExperienceTO firstExp;
	private ApplicantWorkExperienceTO secExp;
	private ApplicantWorkExperienceTO thirdExp;
	private List<CandidatePreferenceTO> preferenceList= null;
	private List<AdmittedThroughTO> admittedThroughList= null;
	private List<EntrancedetailsTO> entranceList= null;
	private List<SubjectGroupTO> subGroupList= null;
	private List<ApplicantMarkDetailsTO> semesterList;
	private String termConditions;
	private String guidelines;
	private List<TermsConditionChecklistTO> conditionChecklists;
	private List<GuideLinesCheckListTO> guidelineChecklists;
	private String bankBranch;
	private List<String> originalDocList;
	private List<String> pendingDocList;
	private boolean sportsPerson;
	private boolean handicapped;
	private String submitDate;
	private String remark;
	private boolean quotaCheck;
	private boolean eligibleCheck;
	private boolean reviewWarned;
	private FormFile csvFile;
	private String programName;
	private String progTypeName;
	private AddressTO guardianAddress;
	private String guardianPhone1;
	private String guardianPhone2;
	private String guardianPhone3;
	private String guardianMobile1;
	private String guardianMobile2;
	private String guardianMobile3;
	private String sportsDescription;
	private String hadnicappedDescription;

	private String cancellationReason;

	private boolean admissionEdit;

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
	
	private String languageSpeak;
	private String languageWrite;
	private String languageRead;
	private String height;
	private String weight;
	private Map<Integer,String> detailedSubjectsMap;
	private String trainingProgName;
	private String trainingInstAddr;
	private String trainingDuration;
	private String trainingPurpose;
	
	private String courseKnownBy;
	private String courseOptReason;
	private String strength;
	private String weakness;
	private String otherAddnInfo;
	private String organizationName;
	
	private List<ExtracurricularActivityTO> extracurriculars;
	private String[] extracurricularIds;
	
	private String secondLanguage;
	
	private String brotherName;
	private String brotherEducation;
	private String brotherIncome;
	private String brotherOccupation;
	private String brotherAge;
	private String sisterName;
	private String sisterEducation;
	private String sisterIncome;
	private String sisterOccupation;
	private String sisterAge;
	
	private String guardianName;
	
	private String entranceId;
	private String entranceMarkObtained;
	private String entranceTotalMark;
	private String entranceRollNo;
	private String entranceYearPass;
	private String entranceMonthPass;
	
	private boolean checkOfflineAppNo = false;
	private boolean applicationError = false;
	private String tcNo;
	private String tcDate;
	private String markcardNo;
	private String markcardDate;
	private boolean needApproval;
	private String isLanguageWiseMarks;
	
	private List<ApplicantLateralDetailsTO> lateralDetails;
	private String lateralUniversityName;
	private String lateralStateName;
	private String lateralInstituteAddress;
	
	private List<ApplicantTransferDetailsTO> transferDetails;
	private String transUnvrAppNo;
	private String transRegistationNo;
	private String transArrearDetail;
	private String transInstituteAddr;
	private boolean applicationConfirm;
	private String instrTemplate;
	private String pendingDocumentTemplate;
	private String officeInstrTemplate;
	private String orgAddress;
	private boolean detailMarksPrint;
	
	private int ageFromLimit;
	private int ageToLimit;
	private boolean casteDisplay;
	private List<StateTO> ednStates;
	private boolean outsideCourseSelected;
	
	private boolean singlePageAppln;
	
	private String regNumber;
	private String rollNo;
	private String totalobtainedMarkWithLanguage;
	private String totalMarkWithLanguage;
	private String totalobtainedMarkWithoutLan;
	private String totalMarkWithoutLan;
	private boolean isDataSaved;
	private List<ExamCenterTO> examCenters;
	private boolean examCenterRequired;
	private boolean pucApplicationEdit;
	private String pucInterviewDate;
	private String recomendedBy;
	private boolean pucApplicationDetailEdit;
	private boolean pucApplicationDetailView;
	private String cancellationDate;
	private HashMap<Integer, String> secondLanguageList;
	private boolean remove;
	private boolean isPresidance;
	private List<Integer> applnNos;
	private boolean workExpMandatory;
	private String totalYearOfExp;
	private String mode;
	private Map<String,String> monthMap;
	private Map<String,String> yearMap;
	private boolean backLogs;
	private Boolean preRequisiteExists;
	private Map<Integer, String> interviewSelectionSchedule;
	private Map<Integer, String> interviewVenueSelection;
	private Map<Integer, String> interviewTimeSelection;
	private String interviewSelectionDate;
	private String interviewVenue;
	private String interviewTime;
	private String tempVenue;
	private String selectedDate;
	private String selectedVenue;
	private String isInterviewSelectionSchedule;
	private String programYear;
	private String programId;
	private String enableData;
	private String tempSelectedDate;
	private String tempVenuId;
	private boolean viewextradetails;
	private String nccgrade;
	private boolean ncccertificate;
	private List<DioceseTo> dioceseList;
	private List<ParishTo> parishList;
    private Integer checkReligionId;
    
    //raghu
    
    private List<DistrictTO> editPermanentDistrict;
	private List<DistrictTO> editCurrentDistrict;
	private String docname;
    
	//raghu added newly
    
    Map<Integer,String> admSubjectMap;
	Map<Integer,String> admSubjectLangMap;
	private List<PreferencesTO> preferencesList;
	private String admLangsubId;
	private boolean class12check;
	private boolean classdegcheck;
	private List<AdmSubjectMarkForRankTO> admsubMarkList;
	private String totalobtainedmark;
	private String totalmaxmark;
	private Boolean isSaypass;
	private List<ProgramTypeTO> programTypeList;
	private int cutoffRank;
	private int totalSeats;
	private int preNo;
	private List<StudentCourseAllotmentTo> studentList;
	private int halfLength;
	private Integer allotedNo;
	private List<CandidatePreferenceEntranceDetailsTO> preferenceEntranceDetailsList;

	private String subid_0;
	private String subid_1;
	private String subid_2;
	private String subid_3;
	private String subid_4;
	private String subid_5;
	private String subid_6;
	private String subid_7;
	private String subid_8;
	
	private String obtainedmark_0;
	private String obtainedmark_1;
	private String obtainedmark_2;
	private String obtainedmark_3;
	private String obtainedmark_4;
	private String obtainedmark_5;
	private String obtainedmark_6;
	private String obtainedmark_7;
	private String obtainedmark_8;
	
	private String maxmark_0;
	private String maxmark_1;
	private String maxmark_2;
	private String maxmark_3;
	private String maxmark_4;
	private String maxmark_5;
	private String maxmark_6;
	private String maxmark_7;
	private String maxmark_8;
	
	
	private String admsubmarkid_0;
	private String admsubmarkid_1;
	private String admsubmarkid_2;
	private String admsubmarkid_3;
	private String admsubmarkid_4;
	private String admsubmarkid_5;
	private String admsubmarkid_6;
	private String admsubmarkid_7;
	private String admsubmarkid_8;
	private String admsubmarkid_9;
	private String admsubmarkid_10;
	private String admsubmarkid_11;
	private String admsubmarkid_12;
	private String admsubmarkid_13;
	private String admsubmarkid_14;
	private String admsubmarkid_15;
	private String admsubmarkid_16;
	private String admsubmarkid_17;
	
	private String admsubgrpname_0;
	private String admsubgrpname_1;
	private String admsubgrpname_2;
	private String admsubgrpname_3;
	private String admsubgrpname_4;
	private String admsubgrpname_5;
	private String admsubgrpname_6;
	private String admsubgrpname_7;
	private String admsubgrpname_8;
	private String admsubgrpname_9;
	private String admsubgrpname_10;
	private String admsubgrpname_11;
	private String admsubgrpname_12;
	private String admsubgrpname_13;
	private String admsubgrpname_14;
	private String admsubgrpname_15;
	private String admsubgrpname_16;
	private String admsubgrpname_17;
	
	private boolean allot_0;
	private boolean allot_1;
	private boolean allot_2;
	private boolean allot_3;
	private boolean allot_4;
	private boolean allot_5;
	private boolean allot_6;
	private boolean allot_7;
	private boolean allot_8;
	private boolean allot_9;
	private boolean allot_10;
	private boolean allot_11;
	private boolean allot_12;
	private boolean allot_13;
	private boolean allot_14;
	private boolean allot_15;
	private boolean allot_16;
	private boolean allot_17;
	
	Map<String,String> GroupSubjectsMap;
	
	
//for pg
	
	Map<Integer,String>  admCoreMap;
	Map<Integer,String>  admComplMap;
	Map<Integer,String>  admCommonMap;
	Map<Integer,String>  admMainMap;
	Map<Integer,String>  admSubMap;
	

	private String degsubid_0;
	private String degsubid_1;
	private String degsubid_2;
	private String degsubid_3;
	private String degsubid_4;
	private String degsubid_5;
	private String degsubid_6;
	private String degsubid_7;
	private String degsubid_8;
	private String degsubid_9;
	private String degsubid_10;
	private String degsubid_11;
	private String degsubid_12;
	private String degsubid_13;
	private String degsubid_14;
	
	
	
	private String degobtainedmark_0;
	private String degobtainedmark_1;
	private String degobtainedmark_2;
	private String degobtainedmark_3;
	private String degobtainedmark_4;
	private String degobtainedmark_5;
	private String degobtainedmark_6;
	private String degobtainedmark_7;
	private String degobtainedmark_8;
	private String degobtainedmark_9;
	private String degobtainedmark_10;
	private String degobtainedmark_11;
	private String degobtainedmark_12;
	private String degobtainedmark_13;
	private String degobtainedmark_14;
	
	
	private String degmaxmark_0;
	private String degmaxmark_1;
	private String degmaxmark_2;
	private String degmaxmark_3;
	private String degmaxmark_4;
	private String degmaxmark_5;
	private String degmaxmark_6;
	private String degmaxmark_7;
	private String degmaxmark_8;
	private String degmaxmark_9;
	private String degmaxmark_10;
	private String degmaxmark_11;
	private String degmaxmark_12;
	private String degmaxmark_13;
	private String degmaxmark_14;
	
	private String degtotalobtainedmark;
	private String degtotalmaxmark;
	private String degtotalobtainedmarkother;
	private String degtotalmaxmarkother;
	
	private String degmaxcgpa_0;
	private String degmaxcgpa_1;
	private String degmaxcgpa_2;
	private String degmaxcgpa_3;
	private String degmaxcgpa_4;
	private String degmaxcgpa_5;
	private String degmaxcgpa_6;
	private String degmaxcgpa_7;
	private String degmaxcgpa_14;
	
	private String admsubname_0;
	private String admsubname_1;
	private String admsubname_2;
	private String admsubname_3;
	private String admsubname_4;
	private String admsubname_5;
	private String admsubname_6;
	private String admsubname_7;
	private String admsubname_8;
	private String admsubname_9;
	private String admsubname_10;
	private String admsubname_11;
	private String admsubname_12;
	private String admsubname_13;
	private String admsubname_14;
	private String admsubname_15;
	private String admsubname_16;
	private String admsubname_17;
	
	Map<Integer,String> degGroupCoreMap;
	Map<Integer,String> degGroupCommonMap;
	Map<Integer,String> degGroupComplMap;
	Map<Integer,String> degGroupOpenMap;
	Map<Integer,String> degGroupSubMap;
	
	private List<UGCoursesTO> ugcourseList;
	
	private String patternofStudy;
	private List<AdmSubjectMarkForRankTO> admsubMarkListUG;
	Map<String,String> GroupLangSubjectsMap;
	//raghu newly added 
	private Integer preferenceSize;
	private Map<Integer, String> courseMap;
	private Map<Integer, String> streamMap;
	
	
	/**
	 * Constructor initialization
	 */
	public  ApplicationEditForm(){
		this.permAddr = new AddressTO();
		this.tempAddr = new AddressTO();
		this.parentAddress = new AddressTO();
		this.guardianAddress = new AddressTO();
		this.firstPref = new PreferenceTO();
		this.secondPref = new PreferenceTO();
		this.thirdPref = new PreferenceTO();
		this.detailMark = new CandidateMarkTO();
		this.firstExp = new ApplicantWorkExperienceTO();
		this.secExp = new ApplicantWorkExperienceTO();
		this.thirdExp = new ApplicantWorkExperienceTO();
	}

	
	
	public String getDocname() {
		return docname;
	}



	public void setDocname(String docname) {
		this.docname = docname;
	}



	public List<DistrictTO> getEditPermanentDistrict() {
		return editPermanentDistrict;
	}



	public void setEditPermanentDistrict(List<DistrictTO> editPermanentDistrict) {
		this.editPermanentDistrict = editPermanentDistrict;
	}



	public List<DistrictTO> getEditCurrentDistrict() {
		return editCurrentDistrict;
	}



	public void setEditCurrentDistrict(List<DistrictTO> editCurrentDistrict) {
		this.editCurrentDistrict = editCurrentDistrict;
	}



	public boolean isGuidelineExist() {
		return guidelineExist;
	}

	public void setGuidelineExist(boolean guidelineExist) {
		this.guidelineExist = guidelineExist;
	}

	public List<NationalityTO> getNationalities() {
		return nationalities;
	}

	public void setNationalities(List<NationalityTO> nationalities) {
		this.nationalities = nationalities;
	}

	
	public List<ProgramTypeTO> getPrefProgramtypes() {
		return prefProgramtypes;
	}

	public void setPrefProgramtypes(List<ProgramTypeTO> prefProgramtypes) {
		this.prefProgramtypes = prefProgramtypes;
	}

	public List<ProgramTO> getPrefprograms() {
		return prefprograms;
	}

	public void setPrefprograms(List<ProgramTO> prefprograms) {
		this.prefprograms = prefprograms;
	}

	public List<IncomeTO> getIncomes() {
		return incomes;
	}

	public void setIncomes(List<IncomeTO> incomes) {
		this.incomes = incomes;
	}

	public List<OccupationTO> getOccupations() {
		return occupations;
	}

	public void setOccupations(List<OccupationTO> occupations) {
		this.occupations = occupations;
	}

	public List<ApplnDocTO> getUploadDocs() {
		return uploadDocs;
	}

	public void setUploadDocs(List<ApplnDocTO> uploadDocs) {
		this.uploadDocs = uploadDocs;
	}

	public CandidateMarkTO getDetailMark() {
		return detailMark;
	}

	public void setDetailMark(CandidateMarkTO detailMark) {
		this.detailMark = detailMark;
	}

	public List<EdnQualificationTO> getQualifications() {
		return qualifications;
	}

	public void setQualifications(List<EdnQualificationTO> qualifications) {
		this.qualifications = qualifications;
	}

	public List<UniversityTO> getUniversities() {
		return universities;
	}

	public void setUniversities(List<UniversityTO> universities) {
		this.universities = universities;
	}

	public String getApplicationAmount() {
		return applicationAmount;
	}

	public void setApplicationAmount(String applicationAmount) {
		this.applicationAmount = applicationAmount;
	}

	public String getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getJournalNo() {
		return journalNo;
	}

	public void setJournalNo(String journalNo) {
		this.journalNo = journalNo;
	}

	public String getChallanNo() {
		return challanNo;
	}

	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getPassportcountry() {
		return passportcountry;
	}

	public void setPassportcountry(String passportcountry) {
		this.passportcountry = passportcountry;
	}

	public String getPassportValidity() {
		return passportValidity;
	}

	public void setPassportValidity(String passportValidity) {
		this.passportValidity = passportValidity;
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

	

	public String getFatherCurrency() {
		return fatherCurrency;
	}

	public void setFatherCurrency(String fatherCurrency) {
		this.fatherCurrency = fatherCurrency;
	}

	public String getMotherCurrency() {
		return motherCurrency;
	}

	public void setMotherCurrency(String motherCurrency) {
		this.motherCurrency = motherCurrency;
	}

	public List<CurrencyTO> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(List<CurrencyTO> currencies) {
		this.currencies = currencies;
	}

	public List<CountryTO> getCountries() {
		return countries;
	}

	public void setCountries(List<CountryTO> countries) {
		this.countries = countries;
	}

	public List<LanguageTO> getMothertongues() {
		return mothertongues;
	}

	public void setMothertongues(List<LanguageTO> mothertongues) {
		this.mothertongues = mothertongues;
	}

	public List<ProgramTypeTO> getProgramtypeList() {
		return programtypeList;
	}

	public void setProgramtypeList(List<ProgramTypeTO> programtypeList) {
		this.programtypeList = programtypeList;
	}

	public List<CasteTO> getCasteList() {
		return casteList;
	}

	public void setCasteList(List<CasteTO> casteList) {
		this.casteList = casteList;
	}

	
	public boolean isSameTempAddr() {
		return sameTempAddr;
	}

	public void setSameTempAddr(boolean sameTempAddr) {
		this.sameTempAddr = sameTempAddr;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCastCategory() {
		return castCategory;
	}

	public void setCastCategory(String castCategory) {
		this.castCategory = castCategory;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getBirthState() {
		return birthState;
	}

	public void setBirthState(String birthState) {
		this.birthState = birthState;
	}

	public char getAreaType() {
		return areaType;
	}

	public void setAreaType(char areaType) {
		this.areaType = areaType;
	}

	public String getMotherTongue() {
		return motherTongue;
	}

	public void setMotherTongue(String motherTongue) {
		this.motherTongue = motherTongue;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public List<ResidentCategoryTO> getResidentTypes() {
		return residentTypes;
	}

	public void setResidentTypes(List<ResidentCategoryTO> residentTypes) {
		this.residentTypes = residentTypes;
	}

	public String getResidentCategory() {
		return residentCategory;
	}

	public void setResidentCategory(String residentCategory) {
		this.residentCategory = residentCategory;
	}

	public PreferenceTO getFirstPref() {
		return firstPref;
	}

	public void setFirstPref(PreferenceTO firstPref) {
		this.firstPref = firstPref;
	}

	public PreferenceTO getSecondPref() {
		return secondPref;
	}

	public void setSecondPref(PreferenceTO secondPref) {
		this.secondPref = secondPref;
	}

	public PreferenceTO getThirdPref() {
		return thirdPref;
	}

	public void setThirdPref(PreferenceTO thirdPref) {
		this.thirdPref = thirdPref;
	}

	public String getSubReligion() {
		return subReligion;
	}

	public void setSubReligion(String subReligion) {
		this.subReligion = subReligion;
	}

	public AddressTO getPermAddr() {
		return permAddr;
	}

	public void setPermAddr(AddressTO permAddr) {
		this.permAddr = permAddr;
	}

	public AddressTO getTempAddr() {
		return tempAddr;
	}

	public void setTempAddr(AddressTO tempAddr) {
		this.tempAddr = tempAddr;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getFatherEducation() {
		return fatherEducation;
	}

	public void setFatherEducation(String fatherEducation) {
		this.fatherEducation = fatherEducation;
	}

	public String getMotherEducation() {
		return motherEducation;
	}

	public void setMotherEducation(String motherEducation) {
		this.motherEducation = motherEducation;
	}

	public String getFatherOccupation() {
		return fatherOccupation;
	}

	public void setFatherOccupation(String fatherOccupation) {
		this.fatherOccupation = fatherOccupation;
	}

	public String getMotherOccupation() {
		return motherOccupation;
	}

	public void setMotherOccupation(String motherOccupation) {
		this.motherOccupation = motherOccupation;
	}

	public String getParentPhone1() {
		return parentPhone1;
	}

	public void setParentPhone1(String parentPhone1) {
		this.parentPhone1 = parentPhone1;
	}

	public String getParentPhone2() {
		return parentPhone2;
	}

	public void setParentPhone2(String parentPhone2) {
		this.parentPhone2 = parentPhone2;
	}

	public String getParentPhone3() {
		return parentPhone3;
	}

	public void setParentPhone3(String parentPhone3) {
		this.parentPhone3 = parentPhone3;
	}

	public String getParentMobile1() {
		return parentMobile1;
	}

	public void setParentMobile1(String parentMobile1) {
		this.parentMobile1 = parentMobile1;
	}

	public String getParentMobile2() {
		return parentMobile2;
	}

	public void setParentMobile2(String parentMobile2) {
		this.parentMobile2 = parentMobile2;
	}

	public String getParentMobile3() {
		return parentMobile3;
	}

	public void setParentMobile3(String parentMobile3) {
		this.parentMobile3 = parentMobile3;
	}

	public String getMotherEmail() {
		return motherEmail;
	}

	public void setMotherEmail(String motherEmail) {
		this.motherEmail = motherEmail;
	}

	public String getFatherEmail() {
		return fatherEmail;
	}

	public void setFatherEmail(String fatherEmail) {
		this.fatherEmail = fatherEmail;
	}

	public String getFatherIncome() {
		return fatherIncome;
	}

	public void setFatherIncome(String fatherIncome) {
		this.fatherIncome = fatherIncome;
	}

	public String getMotherIncome() {
		return motherIncome;
	}

	public void setMotherIncome(String motherIncome) {
		this.motherIncome = motherIncome;
	}

	public String getParentEmail() {
		return motherEmail;
	}

	public void setParentEmail(String motherEmail) {
		this.motherEmail = motherEmail;
	}

	public AddressTO getParentAddress() {
		return parentAddress;
	}

	public void setParentAddress(AddressTO parentAddress) {
		this.parentAddress = parentAddress;
	}

	public String getOtherCastCategory() {
		return otherCastCategory;
	}

	public void setOtherCastCategory(String otherCastCategory) {
		this.otherCastCategory = otherCastCategory;
	}

	public String getOtherSubReligion() {
		return otherSubReligion;
	}

	public void setOtherSubReligion(String otherSubReligion) {
		this.otherSubReligion = otherSubReligion;
	}

	public String getOtherReligion() {
		return otherReligion;
	}

	public void setOtherReligion(String otherReligion) {
		this.otherReligion = otherReligion;
	}

	public String getOtherBirthState() {
		return otherBirthState;
	}

	public void setOtherBirthState(String otherBirthState) {
		this.otherBirthState = otherBirthState;
	}

	public String getApplicationYear() {
		return applicationYear;
	}

	public void setApplicationYear(String applicationYear) {
		this.applicationYear = applicationYear;
	}

	public AdmApplnTO getApplicantDetails() {
		return applicantDetails;
	}

	public void setApplicantDetails(AdmApplnTO applicantDetails) {
		this.applicantDetails = applicantDetails;
	}

	public List<CourseTO> getEditcourses() {
		return editcourses;
	}

	public void setEditcourses(List<CourseTO> editcourses) {
		this.editcourses = editcourses;
	}

	public List<ProgramTypeTO> getEditProgramtypes() {
		return editProgramtypes;
	}

	public void setEditProgramtypes(List<ProgramTypeTO> editProgramtypes) {
		this.editProgramtypes = editProgramtypes;
	}

	public List<ProgramTO> getEditprograms() {
		return editprograms;
	}

	public void setEditprograms(List<ProgramTO> editprograms) {
		this.editprograms = editprograms;
	}

	public List<StateTO> getEditStates() {
		return editStates;
	}

	public void setEditStates(List<StateTO> editStates) {
		this.editStates = editStates;
	}

	public int getCatScore() {
		return catScore;
	}

	public void setCatScore(int catScore) {
		this.catScore = catScore;
	}

	public int getMatScore() {
		return matScore;
	}

	public void setMatScore(int matScore) {
		this.matScore = matScore;
	}

	public boolean isOnlineApply() {
		return onlineApply;
	}

	public void setOnlineApply(boolean onlineApply) {
		this.onlineApply = onlineApply;
	}

	public String getChallanRefNo() {
		return challanRefNo;
	}

	public void setChallanRefNo(String challanRefNo) {
		this.challanRefNo = challanRefNo;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public List<CoursePrerequisiteTO> getCoursePrerequisites() {
		return coursePrerequisites;
	}

	public void setCoursePrerequisites(
			List<CoursePrerequisiteTO> coursePrerequisites) {
		this.coursePrerequisites = coursePrerequisites;
	}

	public ApplicantWorkExperienceTO getFirstExp() {
		return firstExp;
	}

	public void setFirstExp(ApplicantWorkExperienceTO firstExp) {
		this.firstExp = firstExp;
	}

	public ApplicantWorkExperienceTO getSecExp() {
		return secExp;
	}

	public void setSecExp(ApplicantWorkExperienceTO secExp) {
		this.secExp = secExp;
	}

	public ApplicantWorkExperienceTO getThirdExp() {
		return thirdExp;
	}

	public void setThirdExp(ApplicantWorkExperienceTO thirdExp) {
		this.thirdExp = thirdExp;
	}

	public boolean isWorkExpNeeded() {
		return workExpNeeded;
	}

	public void setWorkExpNeeded(boolean workExpNeeded) {
		this.workExpNeeded = workExpNeeded;
	}

	public String getTermConditions() {
		return termConditions;
	}

	public void setTermConditions(String termConditions) {
		this.termConditions = termConditions;
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

	public String getMobile1() {
		return mobile1;
	}

	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}

	public String getMobile2() {
		return mobile2;
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	public String getMobile3() {
		return mobile3;
	}

	public void setMobile3(String mobile3) {
		this.mobile3 = mobile3;
	}

	public List<CandidatePreferenceTO> getPreferenceList() {
		return preferenceList;
	}

	public void setPreferenceList(List<CandidatePreferenceTO> preferenceList) {
		this.preferenceList = preferenceList;
	}

	public List<AdmittedThroughTO> getAdmittedThroughList() {
		return admittedThroughList;
	}

	public void setAdmittedThroughList(List<AdmittedThroughTO> admittedThroughList) {
		this.admittedThroughList = admittedThroughList;
	}

	public List<SubjectGroupTO> getSubGroupList() {
		return subGroupList;
	}

	public void setSubGroupList(List<SubjectGroupTO> subGroupList) {
		this.subGroupList = subGroupList;
	}

	public List<ApplicantMarkDetailsTO> getSemesterList() {
		return semesterList;
	}

	public void setSemesterList(List<ApplicantMarkDetailsTO> semesterList) {
		this.semesterList = semesterList;
	}

	public String getCoursePayCode() {
		return coursePayCode;
	}

	public void setCoursePayCode(String coursePayCode) {
		this.coursePayCode = coursePayCode;
	}

	public String getCourseAmount() {
		return courseAmount;
	}

	public void setCourseAmount(String courseAmount) {
		this.courseAmount = courseAmount;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public List<String> getOriginalDocList() {
		return originalDocList;
	}

	public void setOriginalDocList(List<String> originalDocList) {
		this.originalDocList = originalDocList;
	}

	public boolean isSportsPerson() {
		return sportsPerson;
	}

	public void setSportsPerson(boolean sportsPerson) {
		this.sportsPerson = sportsPerson;
	}

	public boolean isHandicapped() {
		return handicapped;
	}

	public void setHandicapped(boolean handicapped) {
		this.handicapped = handicapped;
	}

	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

	public boolean isQuotaCheck() {
		return quotaCheck;
	}

	public void setQuotaCheck(boolean quotaCheck) {
		this.quotaCheck = quotaCheck;
	}

	public FormFile getCsvFile() {
		return csvFile;
	}

	public void setCsvFile(FormFile csvFile) {
		this.csvFile = csvFile;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getProgTypeName() {
		return progTypeName;
	}

	public void setProgTypeName(String progTypeName) {
		this.progTypeName = progTypeName;
	}

	/* (non-Javadoc)
	 * validation call
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	/**
	 * @return the guardianAddress
	 */
	public AddressTO getGuardianAddress() {
		return guardianAddress;
	}

	/**
	 * @param guardianAddress the guardianAddress to set
	 */
	public void setGuardianAddress(AddressTO guardianAddress) {
		this.guardianAddress = guardianAddress;
	}

	/**
	 * @return the guardianPhone1
	 */
	public String getGuardianPhone1() {
		return guardianPhone1;
	}

	/**
	 * @param guardianPhone1 the guardianPhone1 to set
	 */
	public void setGuardianPhone1(String guardianPhone1) {
		this.guardianPhone1 = guardianPhone1;
	}

	/**
	 * @return the guardianPhone2
	 */
	public String getGuardianPhone2() {
		return guardianPhone2;
	}

	/**
	 * @param guardianPhone2 the guardianPhone2 to set
	 */
	public void setGuardianPhone2(String guardianPhone2) {
		this.guardianPhone2 = guardianPhone2;
	}

	/**
	 * @return the guardianPhone3
	 */
	public String getGuardianPhone3() {
		return guardianPhone3;
	}

	/**
	 * @param guardianPhone3 the guardianPhone3 to set
	 */
	public void setGuardianPhone3(String guardianPhone3) {
		this.guardianPhone3 = guardianPhone3;
	}

	/**
	 * @return the guardianMobile1
	 */
	public String getGuardianMobile1() {
		return guardianMobile1;
	}

	/**
	 * @param guardianMobile1 the guardianMobile1 to set
	 */
	public void setGuardianMobile1(String guardianMobile1) {
		this.guardianMobile1 = guardianMobile1;
	}

	/**
	 * @return the guardianMobile2
	 */
	public String getGuardianMobile2() {
		return guardianMobile2;
	}

	/**
	 * @param guardianMobile2 the guardianMobile2 to set
	 */
	public void setGuardianMobile2(String guardianMobile2) {
		this.guardianMobile2 = guardianMobile2;
	}

	/**
	 * @return the guardianMobile3
	 */
	public String getGuardianMobile3() {
		return guardianMobile3;
	}

	/**
	 * @param guardianMobile3 the guardianMobile3 to set
	 */
	public void setGuardianMobile3(String guardianMobile3) {
		this.guardianMobile3 = guardianMobile3;
	}

	public List<StateTO> getEditGuardianStates() {
		return editGuardianStates;
	}

	public void setEditGuardianStates(List<StateTO> editGuardianStates) {
		this.editGuardianStates = editGuardianStates;
	}

	/**
	 * @return the sportsDescription
	 */
	public String getSportsDescription() {
		return sportsDescription;
	}

	/**
	 * @param sportsDescription the sportsDescription to set
	 */
	public void setSportsDescription(String sportsDescription) {
		this.sportsDescription = sportsDescription;
	}

	/**
	 * @return the hadnicappedDescription
	 */
	public String getHadnicappedDescription() {
		return hadnicappedDescription;
	}

	/**
	 * @param hadnicappedDescription the hadnicappedDescription to set
	 */
	public void setHadnicappedDescription(String hadnicappedDescription) {
		this.hadnicappedDescription = hadnicappedDescription;
	}

	public String getCancellationReason() {
		return cancellationReason;
	}

	public void setCancellationReason(String cancellationReason) {
		this.cancellationReason = cancellationReason;
	}


	public boolean isAdmissionEdit() {
		return admissionEdit;
	}

	public void setAdmissionEdit(boolean admissionEdit) {
		this.admissionEdit = admissionEdit;
	}

	public String getResidentPermit() {
		return residentPermit;
	}

	public void setResidentPermit(String residentPermit) {
		this.residentPermit = residentPermit;
	}

	public String getPermitDate() {
		return permitDate;
	}

	public void setPermitDate(String permitDate) {
		this.permitDate = permitDate;
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

	public String getLanguageSpeak() {
		return languageSpeak;
	}

	public void setLanguageSpeak(String languageSpeak) {
		this.languageSpeak = languageSpeak;
	}

	public String getLanguageWrite() {
		return languageWrite;
	}

	public void setLanguageWrite(String languageWrite) {
		this.languageWrite = languageWrite;
	}

	public String getLanguageRead() {
		return languageRead;
	}

	public void setLanguageRead(String languageRead) {
		this.languageRead = languageRead;
	}

	public boolean isDisplayHeightWeight() {
		return displayHeightWeight;
	}

	public void setDisplayHeightWeight(boolean displayHeightWeight) {
		this.displayHeightWeight = displayHeightWeight;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public List<LanguageTO> getLanguages() {
		return languages;
	}

	public void setLanguages(List<LanguageTO> languages) {
		this.languages = languages;
	}

	public boolean isDisplayTrainingDetails() {
		return displayTrainingDetails;
	}

	public void setDisplayTrainingDetails(boolean displayTrainingDetails) {
		this.displayTrainingDetails = displayTrainingDetails;
	}

	public String getTrainingProgName() {
		return trainingProgName;
	}

	public void setTrainingProgName(String trainingProgName) {
		this.trainingProgName = trainingProgName;
	}

	public String getTrainingInstAddr() {
		return trainingInstAddr;
	}

	public void setTrainingInstAddr(String trainingInstAddr) {
		this.trainingInstAddr = trainingInstAddr;
	}

	/**
	 * @return the detailedSubjectsMap
	 */
	public Map<Integer, String> getDetailedSubjectsMap() {
		return detailedSubjectsMap;
	}

	/**
	 * @param detailedSubjectsMap the detailedSubjectsMap to set
	 */
	public void setDetailedSubjectsMap(Map<Integer, String> detailedSubjectsMap) {
		this.detailedSubjectsMap = detailedSubjectsMap;
	}
	
	public String getTrainingDuration() {
		return trainingDuration;
	}

	public void setTrainingDuration(String trainingDuration) {
		this.trainingDuration = trainingDuration;
	}

	public String getTrainingPurpose() {
		return trainingPurpose;
	}

	public void setTrainingPurpose(String trainingPurpose) {
		this.trainingPurpose = trainingPurpose;
	}

	public boolean isDisplayAdditionalInfo() {
		return displayAdditionalInfo;
	}

	public void setDisplayAdditionalInfo(boolean displayAdditionalInfo) {
		this.displayAdditionalInfo = displayAdditionalInfo;
	}

	public String getCourseKnownBy() {
		return courseKnownBy;
	}

	public void setCourseKnownBy(String courseKnownBy) {
		this.courseKnownBy = courseKnownBy;
	}

	public String getCourseOptReason() {
		return courseOptReason;
	}

	public void setCourseOptReason(String courseOptReason) {
		this.courseOptReason = courseOptReason;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	public String getWeakness() {
		return weakness;
	}

	public void setWeakness(String weakness) {
		this.weakness = weakness;
	}

	public String getOtherAddnInfo() {
		return otherAddnInfo;
	}

	public void setOtherAddnInfo(String otherAddnInfo) {
		this.otherAddnInfo = otherAddnInfo;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public boolean isDisplayExtracurricular() {
		return displayExtracurricular;
	}

	public void setDisplayExtracurricular(boolean displayExtracurricular) {
		this.displayExtracurricular = displayExtracurricular;
	}

	public List<ExtracurricularActivityTO> getExtracurriculars() {
		return extracurriculars;
	}

	public void setExtracurriculars(List<ExtracurricularActivityTO> extracurriculars) {
		this.extracurriculars = extracurriculars;
	}

	public String[] getExtracurricularIds() {
		return extracurricularIds;
	}

	public void setExtracurricularIds(String[] extracurricularIds) {
		this.extracurricularIds = extracurricularIds;
	}

	public boolean isDisplaySecondLanguage() {
		return displaySecondLanguage;
	}

	public void setDisplaySecondLanguage(boolean displaySecondLanguage) {
		this.displaySecondLanguage = displaySecondLanguage;
	}

	public String getSecondLanguage() {
		return secondLanguage;
	}

	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}

	public boolean isDisplayFamilyBackground() {
		return displayFamilyBackground;
	}

	public void setDisplayFamilyBackground(boolean displayFamilyBackground) {
		this.displayFamilyBackground = displayFamilyBackground;
	}

	public String getBrotherName() {
		return brotherName;
	}

	public void setBrotherName(String brotherName) {
		this.brotherName = brotherName;
	}

	public String getBrotherEducation() {
		return brotherEducation;
	}

	public void setBrotherEducation(String brotherEducation) {
		this.brotherEducation = brotherEducation;
	}

	public String getBrotherIncome() {
		return brotherIncome;
	}

	public void setBrotherIncome(String brotherIncome) {
		this.brotherIncome = brotherIncome;
	}

	public String getBrotherOccupation() {
		return brotherOccupation;
	}

	public void setBrotherOccupation(String brotherOccupation) {
		this.brotherOccupation = brotherOccupation;
	}

	public String getBrotherAge() {
		return brotherAge;
	}

	public void setBrotherAge(String brotherAge) {
		this.brotherAge = brotherAge;
	}

	public String getSisterName() {
		return sisterName;
	}

	public void setSisterName(String sisterName) {
		this.sisterName = sisterName;
	}

	public String getSisterEducation() {
		return sisterEducation;
	}

	public void setSisterEducation(String sisterEducation) {
		this.sisterEducation = sisterEducation;
	}

	public String getSisterIncome() {
		return sisterIncome;
	}

	public void setSisterIncome(String sisterIncome) {
		this.sisterIncome = sisterIncome;
	}

	public String getSisterOccupation() {
		return sisterOccupation;
	}

	public void setSisterOccupation(String sisterOccupation) {
		this.sisterOccupation = sisterOccupation;
	}

	public String getSisterAge() {
		return sisterAge;
	}

	public void setSisterAge(String sisterAge) {
		this.sisterAge = sisterAge;
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



	public String getEntranceId() {
		return entranceId;
	}

	public void setEntranceId(String entranceId) {
		this.entranceId = entranceId;
	}

	public String getEntranceRollNo() {
		return entranceRollNo;
	}

	public void setEntranceRollNo(String entranceRollNo) {
		this.entranceRollNo = entranceRollNo;
	}

	

	public String getEntranceMarkObtained() {
		return entranceMarkObtained;
	}

	public void setEntranceMarkObtained(String entranceMarkObtained) {
		this.entranceMarkObtained = entranceMarkObtained;
	}

	public String getEntranceTotalMark() {
		return entranceTotalMark;
	}

	public void setEntranceTotalMark(String entranceTotalMark) {
		this.entranceTotalMark = entranceTotalMark;
	}

	public String getEntranceYearPass() {
		return entranceYearPass;
	}

	public void setEntranceYearPass(String entranceYearPass) {
		this.entranceYearPass = entranceYearPass;
	}

	public String getEntranceMonthPass() {
		return entranceMonthPass;
	}

	public void setEntranceMonthPass(String entranceMonthPass) {
		this.entranceMonthPass = entranceMonthPass;
	}

	public boolean isDisplayTCDetails() {
		return displayTCDetails;
	}

	public void setDisplayTCDetails(boolean displayTCDetails) {
		this.displayTCDetails = displayTCDetails;
	}

	public String getTcNo() {
		return tcNo;
	}

	public void setTcNo(String tcNo) {
		this.tcNo = tcNo;
	}

	public String getTcDate() {
		return tcDate;
	}

	public void setTcDate(String tcDate) {
		this.tcDate = tcDate;
	}

	public String getMarkcardNo() {
		return markcardNo;
	}

	public void setMarkcardNo(String markcardNo) {
		this.markcardNo = markcardNo;
	}

	public String getMarkcardDate() {
		return markcardDate;
	}

	public void setMarkcardDate(String markcardDate) {
		this.markcardDate = markcardDate;
	}

	/**
	 * @return the checkOfflineAppNo
	 */
	public boolean isCheckOfflineAppNo() {
		return checkOfflineAppNo;
	}

	/**
	 * @param checkOfflineAppNo the checkOfflineAppNo to set
	 */
	public void setCheckOfflineAppNo(boolean checkOfflineAppNo) {
		this.checkOfflineAppNo = checkOfflineAppNo;
	}

	/**
	 * @return the applicationError
	 */
	public boolean isApplicationError() {
		return applicationError;
	}

	/**
	 * @param applicationError the applicationError to set
	 */
	public void setApplicationError(boolean applicationError) {
		this.applicationError = applicationError;
	}

	public boolean isNeedApproval() {
		return needApproval;
	}

	public void setNeedApproval(boolean needApproval) {
		this.needApproval = needApproval;
	}

	public String getIsLanguageWiseMarks() {
		return isLanguageWiseMarks;
	}

	public void setIsLanguageWiseMarks(String isLanguageWiseMarks) {
		this.isLanguageWiseMarks = isLanguageWiseMarks;
	}

	public List<CoursePrerequisiteTO> getEligPrerequisites() {
		return eligPrerequisites;
	}

	public void setEligPrerequisites(List<CoursePrerequisiteTO> eligPrerequisites) {
		this.eligPrerequisites = eligPrerequisites;
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

	public List<ApplicantTransferDetailsTO> getTransferDetails() {
		return transferDetails;
	}

	public void setTransferDetails(List<ApplicantTransferDetailsTO> transferDetails) {
		this.transferDetails = transferDetails;
	}

	public boolean isEligibleCheck() {
		return eligibleCheck;
	}

	public void setEligibleCheck(boolean eligibleCheck) {
		this.eligibleCheck = eligibleCheck;
	}

	public List<TermsConditionChecklistTO> getConditionChecklists() {
		return conditionChecklists;
	}

	public void setConditionChecklists(
			List<TermsConditionChecklistTO> conditionChecklists) {
		this.conditionChecklists = conditionChecklists;
	}

	public boolean isApplicationConfirm() {
		return applicationConfirm;
	}

	public void setApplicationConfirm(boolean applicationConfirm) {
		this.applicationConfirm = applicationConfirm;
	}

	public String getInstrTemplate() {
		return instrTemplate;
	}

	public void setInstrTemplate(String instrTemplate) {
		this.instrTemplate = instrTemplate;
	}

	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	public boolean isDetailMarksPrint() {
		return detailMarksPrint;
	}

	public void setDetailMarksPrint(boolean detailMarksPrint) {
		this.detailMarksPrint = detailMarksPrint;
	}

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public boolean isReviewWarned() {
		return reviewWarned;
	}

	public void setReviewWarned(boolean reviewWarned) {
		this.reviewWarned = reviewWarned;
	}

	public String getGuidelines() {
		return guidelines;
	}

	public void setGuidelines(String guidelines) {
		this.guidelines = guidelines;
	}

	public List<GuideLinesCheckListTO> getGuidelineChecklists() {
		return guidelineChecklists;
	}

	public void setGuidelineChecklists(
			List<GuideLinesCheckListTO> guidelineChecklists) {
		this.guidelineChecklists = guidelineChecklists;
	}

	public int getAgeFromLimit() {
		return ageFromLimit;
	}

	public void setAgeFromLimit(int ageFromLimit) {
		this.ageFromLimit = ageFromLimit;
	}

	public int getAgeToLimit() {
		return ageToLimit;
	}

	public void setAgeToLimit(int ageToLimit) {
		this.ageToLimit = ageToLimit;
	}

	public List<StateTO> getEdnStates() {
		return ednStates;
	}

	public void setEdnStates(List<StateTO> ednStates) {
		this.ednStates = ednStates;
	}

	public String getConfirmEmailId() {
		return confirmEmailId;
	}

	public void setConfirmEmailId(String confirmEmailId) {
		this.confirmEmailId = confirmEmailId;
	}

	public boolean isCasteDisplay() {
		return casteDisplay;
	}

	public void setCasteDisplay(boolean casteDisplay) {
		this.casteDisplay = casteDisplay;
	}

	public List<CourseTO> getPrefcourses() {
		return prefcourses;
	}

	public void setPrefcourses(List<CourseTO> prefcourses) {
		this.prefcourses = prefcourses;
	}

	public List<EntrancedetailsTO> getEntranceList() {
		return entranceList;
	}

	public void setEntranceList(List<EntrancedetailsTO> entranceList) {
		this.entranceList = entranceList;
	}

	public boolean isOutsideCourseSelected() {
		return outsideCourseSelected;
	}

	public void setOutsideCourseSelected(boolean outsideCourseSelected) {
		this.outsideCourseSelected = outsideCourseSelected;
	}

	public String getOfficeInstrTemplate() {
		return officeInstrTemplate;
	}

	public void setOfficeInstrTemplate(String officeInstrTemplate) {
		this.officeInstrTemplate = officeInstrTemplate;
	}

	public boolean isSinglePageAppln() {
		return singlePageAppln;
	}

	public void setSinglePageAppln(boolean singlePageAppln) {
		this.singlePageAppln = singlePageAppln;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
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

	public List<ExamCenterTO> getExamCenters() {
		return examCenters;
	}

	public void setExamCenters(List<ExamCenterTO> examCenters) {
		this.examCenters = examCenters;
	}

	public boolean isExamCenterRequired() {
		return examCenterRequired;
	}

	public void setExamCenterRequired(boolean examCenterRequired) {
		this.examCenterRequired = examCenterRequired;
	}

	public boolean isDataSaved() {
		return isDataSaved;
	}

	public void setDataSaved(boolean isDataSaved) {
		this.isDataSaved = isDataSaved;
	}

	public boolean getPucApplicationEdit() {
		return pucApplicationEdit;
	}

	public void setPucApplicationEdit(boolean pucApplicationEdit) {
		this.pucApplicationEdit = pucApplicationEdit;
	}

	public String getPucInterviewDate() {
		return pucInterviewDate;
	}

	public void setPucInterviewDate(String pucInterviewDate) {
		this.pucInterviewDate = pucInterviewDate;
	}

	public String getRecomendedBy() {
		return recomendedBy;
	}

	public void setRecomendedBy(String recomendedBy) {
		this.recomendedBy = recomendedBy;
	}

	public boolean getPucApplicationDetailEdit() {
		return pucApplicationDetailEdit;
	}

	public void setPucApplicationDetailEdit(boolean pucApplicationDetailEdit) {
		this.pucApplicationDetailEdit = pucApplicationDetailEdit;
	}

	public boolean isPucApplicationDetailView() {
		return pucApplicationDetailView;
	}

	public void setPucApplicationDetailView(boolean pucApplicationDetailView) {
		this.pucApplicationDetailView = pucApplicationDetailView;
	}
	public void setPendingDocList(List<String> pendingDocList) {
		this.pendingDocList = pendingDocList;
	}

	public List<String> getPendingDocList() {
		return pendingDocList;
	}

	public void setPendingDocumentTemplate(String pendingDocumentTemplate) {
		this.pendingDocumentTemplate = pendingDocumentTemplate;
	}

	public String getPendingDocumentTemplate() {
		return pendingDocumentTemplate;
	}

	public String getCancellationDate() {
		return cancellationDate;
	}

	public void setCancellationDate(String cancellationDate) {
		this.cancellationDate = cancellationDate;
	}

	public HashMap<Integer, String> getSecondLanguageList() {
		return secondLanguageList;
	}

	public void setSecondLanguageList(HashMap<Integer, String> secondLanguageList) {
		this.secondLanguageList = secondLanguageList;
	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	public boolean getIsPresidance() {
		return isPresidance;
	}

	public void setIsPresidance(boolean isPresidance) {
		this.isPresidance = isPresidance;
	}

	public void setApplnNos(List<Integer> applnNos) {
		this.applnNos = applnNos;
	}

	public List<Integer> getApplnNos() {
		return applnNos;
	}

	public boolean isWorkExpMandatory() {
		return workExpMandatory;
	}

	public void setWorkExpMandatory(boolean workExpMandatory) {
		this.workExpMandatory = workExpMandatory;
	}

	public String getTotalYearOfExp() {
		return totalYearOfExp;
	}

	public void setTotalYearOfExp(String totalYearOfExp) {
		this.totalYearOfExp = totalYearOfExp;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Map<String, String> getMonthMap() {
		return monthMap;
	}

	public void setMonthMap(Map<String, String> monthMap) {
		this.monthMap = monthMap;
	}

	public Map<String, String> getYearMap() {
		return yearMap;
	}

	public void setYearMap(Map<String, String> yearMap) {
		this.yearMap = yearMap;
	}

	public boolean isBackLogs() {
		return backLogs;
	}

	public void setBackLogs(boolean backLogs) {
		this.backLogs = backLogs;
	}

	public Boolean getPreRequisiteExists() {
		return preRequisiteExists;
	}

	public void setPreRequisiteExists(Boolean preRequisiteExists) {
		this.preRequisiteExists = preRequisiteExists;
	}

	public Map<Integer, String> getInterviewSelectionSchedule() {
		return interviewSelectionSchedule;
	}

	public void setInterviewSelectionSchedule(
			Map<Integer, String> interviewSelectionSchedule) {
		this.interviewSelectionSchedule = interviewSelectionSchedule;
	}

	public Map<Integer, String> getInterviewVenueSelection() {
		return interviewVenueSelection;
	}

	public void setInterviewVenueSelection(
			Map<Integer, String> interviewVenueSelection) {
		this.interviewVenueSelection = interviewVenueSelection;
	}

	public Map<Integer, String> getInterviewTimeSelection() {
		return interviewTimeSelection;
	}

	public void setInterviewTimeSelection(
			Map<Integer, String> interviewTimeSelection) {
		this.interviewTimeSelection = interviewTimeSelection;
	}

	public String getInterviewSelectionDate() {
		return interviewSelectionDate;
	}

	public void setInterviewSelectionDate(String interviewSelectionDate) {
		this.interviewSelectionDate = interviewSelectionDate;
	}

	public String getInterviewVenue() {
		return interviewVenue;
	}

	public void setInterviewVenue(String interviewVenue) {
		this.interviewVenue = interviewVenue;
	}

	public String getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(String interviewTime) {
		this.interviewTime = interviewTime;
	}

	public String getTempVenue() {
		return tempVenue;
	}

	public void setTempVenue(String tempVenue) {
		this.tempVenue = tempVenue;
	}

	public String getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}

	public String getSelectedVenue() {
		return selectedVenue;
	}

	public void setSelectedVenue(String selectedVenue) {
		this.selectedVenue = selectedVenue;
	}

	public void setPresidance(boolean isPresidance) {
		this.isPresidance = isPresidance;
	}

	public String getIsInterviewSelectionSchedule() {
		return isInterviewSelectionSchedule;
	}

	public void setIsInterviewSelectionSchedule(String isInterviewSelectionSchedule) {
		this.isInterviewSelectionSchedule = isInterviewSelectionSchedule;
	}

	public String getProgramYear() {
		return programYear;
	}

	public void setProgramYear(String programYear) {
		this.programYear = programYear;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getEnableData() {
		return enableData;
	}

	public void setEnableData(String enableData) {
		this.enableData = enableData;
	}

	public String getTempSelectedDate() {
		return tempSelectedDate;
	}

	public void setTempSelectedDate(String tempSelectedDate) {
		this.tempSelectedDate = tempSelectedDate;
	}

	public String getTempVenuId() {
		return tempVenuId;
	}

	public void setTempVenuId(String tempVenuId) {
		this.tempVenuId = tempVenuId;
	}

	public boolean isViewextradetails() {
		return viewextradetails;
	}

	public void setViewextradetails(boolean viewextradetails) {
		this.viewextradetails = viewextradetails;
	}

	public String getNccgrade() {
		return nccgrade;
	}

	public void setNccgrade(String nccgrade) {
		this.nccgrade = nccgrade;
	}

	public boolean isNcccertificate() {
		return ncccertificate;
	}

	public void setNcccertificate(boolean ncccertificate) {
		this.ncccertificate = ncccertificate;
	}

	public List<DioceseTo> getDioceseList() {
		return dioceseList;
	}

	public void setDioceseList(List<DioceseTo> dioceseList) {
		this.dioceseList = dioceseList;
	}

	public List<ParishTo> getParishList() {
		return parishList;
	}

	public void setParishList(List<ParishTo> parishList) {
		this.parishList = parishList;
	}

	public Integer getCheckReligionId() {
		return checkReligionId;
	}

	public void setCheckReligionId(Integer checkReligionId) {
		this.checkReligionId = checkReligionId;
	}



	public Map<Integer, String> getAdmSubjectMap() {
		return admSubjectMap;
	}



	public void setAdmSubjectMap(Map<Integer, String> admSubjectMap) {
		this.admSubjectMap = admSubjectMap;
	}



	public Map<Integer, String> getAdmSubjectLangMap() {
		return admSubjectLangMap;
	}



	public void setAdmSubjectLangMap(Map<Integer, String> admSubjectLangMap) {
		this.admSubjectLangMap = admSubjectLangMap;
	}



	public List<PreferencesTO> getPreferencesList() {
		return preferencesList;
	}



	public void setPreferencesList(List<PreferencesTO> preferencesList) {
		this.preferencesList = preferencesList;
	}



	public String getAdmLangsubId() {
		return admLangsubId;
	}



	public void setAdmLangsubId(String admLangsubId) {
		this.admLangsubId = admLangsubId;
	}



	public boolean isClass12check() {
		return class12check;
	}



	public void setClass12check(boolean class12check) {
		this.class12check = class12check;
	}



	public boolean isClassdegcheck() {
		return classdegcheck;
	}



	public void setClassdegcheck(boolean classdegcheck) {
		this.classdegcheck = classdegcheck;
	}



	public List<AdmSubjectMarkForRankTO> getAdmsubMarkList() {
		return admsubMarkList;
	}



	public void setAdmsubMarkList(List<AdmSubjectMarkForRankTO> admsubMarkList) {
		this.admsubMarkList = admsubMarkList;
	}



	public String getTotalobtainedmark() {
		return totalobtainedmark;
	}



	public void setTotalobtainedmark(String totalobtainedmark) {
		this.totalobtainedmark = totalobtainedmark;
	}



	public String getTotalmaxmark() {
		return totalmaxmark;
	}



	public void setTotalmaxmark(String totalmaxmark) {
		this.totalmaxmark = totalmaxmark;
	}



	public Boolean getIsSaypass() {
		return isSaypass;
	}



	public void setIsSaypass(Boolean isSaypass) {
		this.isSaypass = isSaypass;
	}



	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}



	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}



	public int getCutoffRank() {
		return cutoffRank;
	}



	public void setCutoffRank(int cutoffRank) {
		this.cutoffRank = cutoffRank;
	}



	public int getTotalSeats() {
		return totalSeats;
	}



	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}



	public int getPreNo() {
		return preNo;
	}



	public void setPreNo(int preNo) {
		this.preNo = preNo;
	}



	public List<StudentCourseAllotmentTo> getStudentList() {
		return studentList;
	}



	public void setStudentList(List<StudentCourseAllotmentTo> studentList) {
		this.studentList = studentList;
	}



	public int getHalfLength() {
		return halfLength;
	}



	public void setHalfLength(int halfLength) {
		this.halfLength = halfLength;
	}



	public Integer getAllotedNo() {
		return allotedNo;
	}



	public void setAllotedNo(Integer allotedNo) {
		this.allotedNo = allotedNo;
	}



	public List<CandidatePreferenceEntranceDetailsTO> getPreferenceEntranceDetailsList() {
		return preferenceEntranceDetailsList;
	}



	public void setPreferenceEntranceDetailsList(
			List<CandidatePreferenceEntranceDetailsTO> preferenceEntranceDetailsList) {
		this.preferenceEntranceDetailsList = preferenceEntranceDetailsList;
	}



	public String getSubid_0() {
		return subid_0;
	}



	public void setSubid_0(String subid_0) {
		this.subid_0 = subid_0;
	}



	public String getSubid_1() {
		return subid_1;
	}



	public void setSubid_1(String subid_1) {
		this.subid_1 = subid_1;
	}



	public String getSubid_2() {
		return subid_2;
	}



	public void setSubid_2(String subid_2) {
		this.subid_2 = subid_2;
	}



	public String getSubid_3() {
		return subid_3;
	}



	public void setSubid_3(String subid_3) {
		this.subid_3 = subid_3;
	}



	public String getSubid_4() {
		return subid_4;
	}



	public void setSubid_4(String subid_4) {
		this.subid_4 = subid_4;
	}



	public String getSubid_5() {
		return subid_5;
	}



	public void setSubid_5(String subid_5) {
		this.subid_5 = subid_5;
	}



	public String getSubid_6() {
		return subid_6;
	}



	public void setSubid_6(String subid_6) {
		this.subid_6 = subid_6;
	}



	public String getSubid_7() {
		return subid_7;
	}



	public void setSubid_7(String subid_7) {
		this.subid_7 = subid_7;
	}



	public String getSubid_8() {
		return subid_8;
	}



	public void setSubid_8(String subid_8) {
		this.subid_8 = subid_8;
	}



	public String getObtainedmark_0() {
		return obtainedmark_0;
	}



	public void setObtainedmark_0(String obtainedmark_0) {
		this.obtainedmark_0 = obtainedmark_0;
	}



	public String getObtainedmark_1() {
		return obtainedmark_1;
	}



	public void setObtainedmark_1(String obtainedmark_1) {
		this.obtainedmark_1 = obtainedmark_1;
	}



	public String getObtainedmark_2() {
		return obtainedmark_2;
	}



	public void setObtainedmark_2(String obtainedmark_2) {
		this.obtainedmark_2 = obtainedmark_2;
	}



	public String getObtainedmark_3() {
		return obtainedmark_3;
	}



	public void setObtainedmark_3(String obtainedmark_3) {
		this.obtainedmark_3 = obtainedmark_3;
	}



	public String getObtainedmark_4() {
		return obtainedmark_4;
	}



	public void setObtainedmark_4(String obtainedmark_4) {
		this.obtainedmark_4 = obtainedmark_4;
	}



	public String getObtainedmark_5() {
		return obtainedmark_5;
	}



	public void setObtainedmark_5(String obtainedmark_5) {
		this.obtainedmark_5 = obtainedmark_5;
	}



	public String getObtainedmark_6() {
		return obtainedmark_6;
	}



	public void setObtainedmark_6(String obtainedmark_6) {
		this.obtainedmark_6 = obtainedmark_6;
	}



	public String getObtainedmark_7() {
		return obtainedmark_7;
	}



	public void setObtainedmark_7(String obtainedmark_7) {
		this.obtainedmark_7 = obtainedmark_7;
	}



	public String getObtainedmark_8() {
		return obtainedmark_8;
	}



	public void setObtainedmark_8(String obtainedmark_8) {
		this.obtainedmark_8 = obtainedmark_8;
	}



	public String getMaxmark_0() {
		return maxmark_0;
	}



	public void setMaxmark_0(String maxmark_0) {
		this.maxmark_0 = maxmark_0;
	}



	public String getMaxmark_1() {
		return maxmark_1;
	}



	public void setMaxmark_1(String maxmark_1) {
		this.maxmark_1 = maxmark_1;
	}



	public String getMaxmark_2() {
		return maxmark_2;
	}



	public void setMaxmark_2(String maxmark_2) {
		this.maxmark_2 = maxmark_2;
	}



	public String getMaxmark_3() {
		return maxmark_3;
	}



	public void setMaxmark_3(String maxmark_3) {
		this.maxmark_3 = maxmark_3;
	}



	public String getMaxmark_4() {
		return maxmark_4;
	}



	public void setMaxmark_4(String maxmark_4) {
		this.maxmark_4 = maxmark_4;
	}



	public String getMaxmark_5() {
		return maxmark_5;
	}



	public void setMaxmark_5(String maxmark_5) {
		this.maxmark_5 = maxmark_5;
	}



	public String getMaxmark_6() {
		return maxmark_6;
	}



	public void setMaxmark_6(String maxmark_6) {
		this.maxmark_6 = maxmark_6;
	}



	public String getMaxmark_7() {
		return maxmark_7;
	}



	public void setMaxmark_7(String maxmark_7) {
		this.maxmark_7 = maxmark_7;
	}



	public String getMaxmark_8() {
		return maxmark_8;
	}



	public void setMaxmark_8(String maxmark_8) {
		this.maxmark_8 = maxmark_8;
	}



	public String getAdmsubmarkid_0() {
		return admsubmarkid_0;
	}



	public void setAdmsubmarkid_0(String admsubmarkid_0) {
		this.admsubmarkid_0 = admsubmarkid_0;
	}



	public String getAdmsubmarkid_1() {
		return admsubmarkid_1;
	}



	public void setAdmsubmarkid_1(String admsubmarkid_1) {
		this.admsubmarkid_1 = admsubmarkid_1;
	}



	public String getAdmsubmarkid_2() {
		return admsubmarkid_2;
	}



	public void setAdmsubmarkid_2(String admsubmarkid_2) {
		this.admsubmarkid_2 = admsubmarkid_2;
	}



	public String getAdmsubmarkid_3() {
		return admsubmarkid_3;
	}



	public void setAdmsubmarkid_3(String admsubmarkid_3) {
		this.admsubmarkid_3 = admsubmarkid_3;
	}



	public String getAdmsubmarkid_4() {
		return admsubmarkid_4;
	}



	public void setAdmsubmarkid_4(String admsubmarkid_4) {
		this.admsubmarkid_4 = admsubmarkid_4;
	}



	public String getAdmsubmarkid_5() {
		return admsubmarkid_5;
	}



	public void setAdmsubmarkid_5(String admsubmarkid_5) {
		this.admsubmarkid_5 = admsubmarkid_5;
	}



	public String getAdmsubmarkid_6() {
		return admsubmarkid_6;
	}



	public void setAdmsubmarkid_6(String admsubmarkid_6) {
		this.admsubmarkid_6 = admsubmarkid_6;
	}



	public String getAdmsubmarkid_7() {
		return admsubmarkid_7;
	}



	public void setAdmsubmarkid_7(String admsubmarkid_7) {
		this.admsubmarkid_7 = admsubmarkid_7;
	}



	public String getAdmsubmarkid_8() {
		return admsubmarkid_8;
	}



	public void setAdmsubmarkid_8(String admsubmarkid_8) {
		this.admsubmarkid_8 = admsubmarkid_8;
	}



	public String getAdmsubgrpname_0() {
		return admsubgrpname_0;
	}



	public void setAdmsubgrpname_0(String admsubgrpname_0) {
		this.admsubgrpname_0 = admsubgrpname_0;
	}



	public String getAdmsubgrpname_1() {
		return admsubgrpname_1;
	}



	public void setAdmsubgrpname_1(String admsubgrpname_1) {
		this.admsubgrpname_1 = admsubgrpname_1;
	}



	public String getAdmsubgrpname_2() {
		return admsubgrpname_2;
	}



	public void setAdmsubgrpname_2(String admsubgrpname_2) {
		this.admsubgrpname_2 = admsubgrpname_2;
	}



	public String getAdmsubgrpname_3() {
		return admsubgrpname_3;
	}



	public void setAdmsubgrpname_3(String admsubgrpname_3) {
		this.admsubgrpname_3 = admsubgrpname_3;
	}



	public String getAdmsubgrpname_4() {
		return admsubgrpname_4;
	}



	public void setAdmsubgrpname_4(String admsubgrpname_4) {
		this.admsubgrpname_4 = admsubgrpname_4;
	}



	public String getAdmsubgrpname_5() {
		return admsubgrpname_5;
	}



	public void setAdmsubgrpname_5(String admsubgrpname_5) {
		this.admsubgrpname_5 = admsubgrpname_5;
	}



	public String getAdmsubgrpname_6() {
		return admsubgrpname_6;
	}



	public void setAdmsubgrpname_6(String admsubgrpname_6) {
		this.admsubgrpname_6 = admsubgrpname_6;
	}



	public String getAdmsubgrpname_7() {
		return admsubgrpname_7;
	}



	public void setAdmsubgrpname_7(String admsubgrpname_7) {
		this.admsubgrpname_7 = admsubgrpname_7;
	}



	public String getAdmsubgrpname_8() {
		return admsubgrpname_8;
	}



	public void setAdmsubgrpname_8(String admsubgrpname_8) {
		this.admsubgrpname_8 = admsubgrpname_8;
	}



	public boolean isAllot_0() {
		return allot_0;
	}



	public void setAllot_0(boolean allot_0) {
		this.allot_0 = allot_0;
	}



	public boolean isAllot_1() {
		return allot_1;
	}



	public void setAllot_1(boolean allot_1) {
		this.allot_1 = allot_1;
	}



	public boolean isAllot_2() {
		return allot_2;
	}



	public void setAllot_2(boolean allot_2) {
		this.allot_2 = allot_2;
	}



	public boolean isAllot_3() {
		return allot_3;
	}



	public void setAllot_3(boolean allot_3) {
		this.allot_3 = allot_3;
	}



	public boolean isAllot_4() {
		return allot_4;
	}



	public void setAllot_4(boolean allot_4) {
		this.allot_4 = allot_4;
	}



	public boolean isAllot_5() {
		return allot_5;
	}



	public void setAllot_5(boolean allot_5) {
		this.allot_5 = allot_5;
	}



	public boolean isAllot_6() {
		return allot_6;
	}



	public void setAllot_6(boolean allot_6) {
		this.allot_6 = allot_6;
	}



	public boolean isAllot_7() {
		return allot_7;
	}



	public void setAllot_7(boolean allot_7) {
		this.allot_7 = allot_7;
	}



	public boolean isAllot_8() {
		return allot_8;
	}



	public void setAllot_8(boolean allot_8) {
		this.allot_8 = allot_8;
	}



	public Map<String, String> getGroupSubjectsMap() {
		return GroupSubjectsMap;
	}



	public void setGroupSubjectsMap(Map<String, String> groupSubjectsMap) {
		GroupSubjectsMap = groupSubjectsMap;
	}



	public String getAdmsubmarkid_9() {
		return admsubmarkid_9;
	}



	public void setAdmsubmarkid_9(String admsubmarkid_9) {
		this.admsubmarkid_9 = admsubmarkid_9;
	}



	public String getAdmsubmarkid_10() {
		return admsubmarkid_10;
	}



	public void setAdmsubmarkid_10(String admsubmarkid_10) {
		this.admsubmarkid_10 = admsubmarkid_10;
	}



	public String getAdmsubmarkid_11() {
		return admsubmarkid_11;
	}



	public void setAdmsubmarkid_11(String admsubmarkid_11) {
		this.admsubmarkid_11 = admsubmarkid_11;
	}



	public String getAdmsubmarkid_12() {
		return admsubmarkid_12;
	}



	public void setAdmsubmarkid_12(String admsubmarkid_12) {
		this.admsubmarkid_12 = admsubmarkid_12;
	}



	public String getAdmsubmarkid_13() {
		return admsubmarkid_13;
	}



	public void setAdmsubmarkid_13(String admsubmarkid_13) {
		this.admsubmarkid_13 = admsubmarkid_13;
	}



	public String getAdmsubmarkid_14() {
		return admsubmarkid_14;
	}



	public void setAdmsubmarkid_14(String admsubmarkid_14) {
		this.admsubmarkid_14 = admsubmarkid_14;
	}



	public String getAdmsubmarkid_15() {
		return admsubmarkid_15;
	}



	public void setAdmsubmarkid_15(String admsubmarkid_15) {
		this.admsubmarkid_15 = admsubmarkid_15;
	}



	public String getAdmsubmarkid_16() {
		return admsubmarkid_16;
	}



	public void setAdmsubmarkid_16(String admsubmarkid_16) {
		this.admsubmarkid_16 = admsubmarkid_16;
	}



	public String getAdmsubmarkid_17() {
		return admsubmarkid_17;
	}



	public void setAdmsubmarkid_17(String admsubmarkid_17) {
		this.admsubmarkid_17 = admsubmarkid_17;
	}



	public String getAdmsubgrpname_9() {
		return admsubgrpname_9;
	}



	public void setAdmsubgrpname_9(String admsubgrpname_9) {
		this.admsubgrpname_9 = admsubgrpname_9;
	}



	public String getAdmsubgrpname_10() {
		return admsubgrpname_10;
	}



	public void setAdmsubgrpname_10(String admsubgrpname_10) {
		this.admsubgrpname_10 = admsubgrpname_10;
	}



	public String getAdmsubgrpname_11() {
		return admsubgrpname_11;
	}



	public void setAdmsubgrpname_11(String admsubgrpname_11) {
		this.admsubgrpname_11 = admsubgrpname_11;
	}



	public String getAdmsubgrpname_12() {
		return admsubgrpname_12;
	}



	public void setAdmsubgrpname_12(String admsubgrpname_12) {
		this.admsubgrpname_12 = admsubgrpname_12;
	}



	public String getAdmsubgrpname_13() {
		return admsubgrpname_13;
	}



	public void setAdmsubgrpname_13(String admsubgrpname_13) {
		this.admsubgrpname_13 = admsubgrpname_13;
	}



	public String getAdmsubgrpname_14() {
		return admsubgrpname_14;
	}



	public void setAdmsubgrpname_14(String admsubgrpname_14) {
		this.admsubgrpname_14 = admsubgrpname_14;
	}



	public String getAdmsubgrpname_15() {
		return admsubgrpname_15;
	}



	public void setAdmsubgrpname_15(String admsubgrpname_15) {
		this.admsubgrpname_15 = admsubgrpname_15;
	}



	public String getAdmsubgrpname_16() {
		return admsubgrpname_16;
	}



	public void setAdmsubgrpname_16(String admsubgrpname_16) {
		this.admsubgrpname_16 = admsubgrpname_16;
	}



	public String getAdmsubgrpname_17() {
		return admsubgrpname_17;
	}



	public void setAdmsubgrpname_17(String admsubgrpname_17) {
		this.admsubgrpname_17 = admsubgrpname_17;
	}



	public boolean isAllot_9() {
		return allot_9;
	}



	public void setAllot_9(boolean allot_9) {
		this.allot_9 = allot_9;
	}



	public boolean isAllot_10() {
		return allot_10;
	}



	public void setAllot_10(boolean allot_10) {
		this.allot_10 = allot_10;
	}



	public boolean isAllot_11() {
		return allot_11;
	}



	public void setAllot_11(boolean allot_11) {
		this.allot_11 = allot_11;
	}



	public boolean isAllot_12() {
		return allot_12;
	}



	public void setAllot_12(boolean allot_12) {
		this.allot_12 = allot_12;
	}



	public boolean isAllot_13() {
		return allot_13;
	}



	public void setAllot_13(boolean allot_13) {
		this.allot_13 = allot_13;
	}



	public boolean isAllot_14() {
		return allot_14;
	}



	public void setAllot_14(boolean allot_14) {
		this.allot_14 = allot_14;
	}



	public boolean isAllot_15() {
		return allot_15;
	}



	public void setAllot_15(boolean allot_15) {
		this.allot_15 = allot_15;
	}



	public boolean isAllot_16() {
		return allot_16;
	}



	public void setAllot_16(boolean allot_16) {
		this.allot_16 = allot_16;
	}



	public boolean isAllot_17() {
		return allot_17;
	}



	public void setAllot_17(boolean allot_17) {
		this.allot_17 = allot_17;
	}



	public Map<Integer, String> getAdmCoreMap() {
		return admCoreMap;
	}



	public void setAdmCoreMap(Map<Integer, String> admCoreMap) {
		this.admCoreMap = admCoreMap;
	}



	public Map<Integer, String> getAdmComplMap() {
		return admComplMap;
	}



	public void setAdmComplMap(Map<Integer, String> admComplMap) {
		this.admComplMap = admComplMap;
	}



	public Map<Integer, String> getAdmCommonMap() {
		return admCommonMap;
	}



	public void setAdmCommonMap(Map<Integer, String> admCommonMap) {
		this.admCommonMap = admCommonMap;
	}



	public Map<Integer, String> getAdmMainMap() {
		return admMainMap;
	}



	public void setAdmMainMap(Map<Integer, String> admMainMap) {
		this.admMainMap = admMainMap;
	}



	public Map<Integer, String> getAdmSubMap() {
		return admSubMap;
	}



	public void setAdmSubMap(Map<Integer, String> admSubMap) {
		this.admSubMap = admSubMap;
	}



	public String getDegsubid_0() {
		return degsubid_0;
	}



	public void setDegsubid_0(String degsubid_0) {
		this.degsubid_0 = degsubid_0;
	}



	public String getDegsubid_1() {
		return degsubid_1;
	}



	public void setDegsubid_1(String degsubid_1) {
		this.degsubid_1 = degsubid_1;
	}



	public String getDegsubid_2() {
		return degsubid_2;
	}



	public void setDegsubid_2(String degsubid_2) {
		this.degsubid_2 = degsubid_2;
	}



	public String getDegsubid_3() {
		return degsubid_3;
	}



	public void setDegsubid_3(String degsubid_3) {
		this.degsubid_3 = degsubid_3;
	}



	public String getDegsubid_4() {
		return degsubid_4;
	}



	public void setDegsubid_4(String degsubid_4) {
		this.degsubid_4 = degsubid_4;
	}



	public String getDegsubid_5() {
		return degsubid_5;
	}



	public void setDegsubid_5(String degsubid_5) {
		this.degsubid_5 = degsubid_5;
	}



	public String getDegsubid_6() {
		return degsubid_6;
	}



	public void setDegsubid_6(String degsubid_6) {
		this.degsubid_6 = degsubid_6;
	}



	public String getDegsubid_7() {
		return degsubid_7;
	}



	public void setDegsubid_7(String degsubid_7) {
		this.degsubid_7 = degsubid_7;
	}



	public String getDegsubid_8() {
		return degsubid_8;
	}



	public void setDegsubid_8(String degsubid_8) {
		this.degsubid_8 = degsubid_8;
	}



	public String getDegsubid_9() {
		return degsubid_9;
	}



	public void setDegsubid_9(String degsubid_9) {
		this.degsubid_9 = degsubid_9;
	}



	public String getDegsubid_10() {
		return degsubid_10;
	}



	public void setDegsubid_10(String degsubid_10) {
		this.degsubid_10 = degsubid_10;
	}



	public String getDegsubid_11() {
		return degsubid_11;
	}



	public void setDegsubid_11(String degsubid_11) {
		this.degsubid_11 = degsubid_11;
	}



	public String getDegsubid_12() {
		return degsubid_12;
	}



	public void setDegsubid_12(String degsubid_12) {
		this.degsubid_12 = degsubid_12;
	}



	public String getDegsubid_13() {
		return degsubid_13;
	}



	public void setDegsubid_13(String degsubid_13) {
		this.degsubid_13 = degsubid_13;
	}



	public String getDegsubid_14() {
		return degsubid_14;
	}



	public void setDegsubid_14(String degsubid_14) {
		this.degsubid_14 = degsubid_14;
	}



	public String getDegobtainedmark_0() {
		return degobtainedmark_0;
	}



	public void setDegobtainedmark_0(String degobtainedmark_0) {
		this.degobtainedmark_0 = degobtainedmark_0;
	}



	public String getDegobtainedmark_1() {
		return degobtainedmark_1;
	}



	public void setDegobtainedmark_1(String degobtainedmark_1) {
		this.degobtainedmark_1 = degobtainedmark_1;
	}



	public String getDegobtainedmark_2() {
		return degobtainedmark_2;
	}



	public void setDegobtainedmark_2(String degobtainedmark_2) {
		this.degobtainedmark_2 = degobtainedmark_2;
	}



	public String getDegobtainedmark_3() {
		return degobtainedmark_3;
	}



	public void setDegobtainedmark_3(String degobtainedmark_3) {
		this.degobtainedmark_3 = degobtainedmark_3;
	}



	public String getDegobtainedmark_4() {
		return degobtainedmark_4;
	}



	public void setDegobtainedmark_4(String degobtainedmark_4) {
		this.degobtainedmark_4 = degobtainedmark_4;
	}



	public String getDegobtainedmark_5() {
		return degobtainedmark_5;
	}



	public void setDegobtainedmark_5(String degobtainedmark_5) {
		this.degobtainedmark_5 = degobtainedmark_5;
	}



	public String getDegobtainedmark_6() {
		return degobtainedmark_6;
	}



	public void setDegobtainedmark_6(String degobtainedmark_6) {
		this.degobtainedmark_6 = degobtainedmark_6;
	}



	public String getDegobtainedmark_7() {
		return degobtainedmark_7;
	}



	public void setDegobtainedmark_7(String degobtainedmark_7) {
		this.degobtainedmark_7 = degobtainedmark_7;
	}



	public String getDegobtainedmark_8() {
		return degobtainedmark_8;
	}



	public void setDegobtainedmark_8(String degobtainedmark_8) {
		this.degobtainedmark_8 = degobtainedmark_8;
	}



	public String getDegobtainedmark_9() {
		return degobtainedmark_9;
	}



	public void setDegobtainedmark_9(String degobtainedmark_9) {
		this.degobtainedmark_9 = degobtainedmark_9;
	}



	public String getDegobtainedmark_10() {
		return degobtainedmark_10;
	}



	public void setDegobtainedmark_10(String degobtainedmark_10) {
		this.degobtainedmark_10 = degobtainedmark_10;
	}



	public String getDegobtainedmark_11() {
		return degobtainedmark_11;
	}



	public void setDegobtainedmark_11(String degobtainedmark_11) {
		this.degobtainedmark_11 = degobtainedmark_11;
	}



	public String getDegobtainedmark_12() {
		return degobtainedmark_12;
	}



	public void setDegobtainedmark_12(String degobtainedmark_12) {
		this.degobtainedmark_12 = degobtainedmark_12;
	}



	public String getDegobtainedmark_13() {
		return degobtainedmark_13;
	}



	public void setDegobtainedmark_13(String degobtainedmark_13) {
		this.degobtainedmark_13 = degobtainedmark_13;
	}



	public String getDegobtainedmark_14() {
		return degobtainedmark_14;
	}



	public void setDegobtainedmark_14(String degobtainedmark_14) {
		this.degobtainedmark_14 = degobtainedmark_14;
	}



	public String getDegmaxmark_0() {
		return degmaxmark_0;
	}



	public void setDegmaxmark_0(String degmaxmark_0) {
		this.degmaxmark_0 = degmaxmark_0;
	}



	public String getDegmaxmark_1() {
		return degmaxmark_1;
	}



	public void setDegmaxmark_1(String degmaxmark_1) {
		this.degmaxmark_1 = degmaxmark_1;
	}



	public String getDegmaxmark_2() {
		return degmaxmark_2;
	}



	public void setDegmaxmark_2(String degmaxmark_2) {
		this.degmaxmark_2 = degmaxmark_2;
	}



	public String getDegmaxmark_3() {
		return degmaxmark_3;
	}



	public void setDegmaxmark_3(String degmaxmark_3) {
		this.degmaxmark_3 = degmaxmark_3;
	}



	public String getDegmaxmark_4() {
		return degmaxmark_4;
	}



	public void setDegmaxmark_4(String degmaxmark_4) {
		this.degmaxmark_4 = degmaxmark_4;
	}



	public String getDegmaxmark_5() {
		return degmaxmark_5;
	}



	public void setDegmaxmark_5(String degmaxmark_5) {
		this.degmaxmark_5 = degmaxmark_5;
	}



	public String getDegmaxmark_6() {
		return degmaxmark_6;
	}



	public void setDegmaxmark_6(String degmaxmark_6) {
		this.degmaxmark_6 = degmaxmark_6;
	}



	public String getDegmaxmark_7() {
		return degmaxmark_7;
	}



	public void setDegmaxmark_7(String degmaxmark_7) {
		this.degmaxmark_7 = degmaxmark_7;
	}



	public String getDegmaxmark_8() {
		return degmaxmark_8;
	}



	public void setDegmaxmark_8(String degmaxmark_8) {
		this.degmaxmark_8 = degmaxmark_8;
	}



	public String getDegmaxmark_9() {
		return degmaxmark_9;
	}



	public void setDegmaxmark_9(String degmaxmark_9) {
		this.degmaxmark_9 = degmaxmark_9;
	}



	public String getDegmaxmark_10() {
		return degmaxmark_10;
	}



	public void setDegmaxmark_10(String degmaxmark_10) {
		this.degmaxmark_10 = degmaxmark_10;
	}



	public String getDegmaxmark_11() {
		return degmaxmark_11;
	}



	public void setDegmaxmark_11(String degmaxmark_11) {
		this.degmaxmark_11 = degmaxmark_11;
	}



	public String getDegmaxmark_12() {
		return degmaxmark_12;
	}



	public void setDegmaxmark_12(String degmaxmark_12) {
		this.degmaxmark_12 = degmaxmark_12;
	}



	public String getDegmaxmark_13() {
		return degmaxmark_13;
	}



	public void setDegmaxmark_13(String degmaxmark_13) {
		this.degmaxmark_13 = degmaxmark_13;
	}



	public String getDegmaxmark_14() {
		return degmaxmark_14;
	}



	public void setDegmaxmark_14(String degmaxmark_14) {
		this.degmaxmark_14 = degmaxmark_14;
	}



	public String getDegtotalobtainedmark() {
		return degtotalobtainedmark;
	}



	public void setDegtotalobtainedmark(String degtotalobtainedmark) {
		this.degtotalobtainedmark = degtotalobtainedmark;
	}



	public String getDegtotalmaxmark() {
		return degtotalmaxmark;
	}



	public void setDegtotalmaxmark(String degtotalmaxmark) {
		this.degtotalmaxmark = degtotalmaxmark;
	}



	public String getDegtotalobtainedmarkother() {
		return degtotalobtainedmarkother;
	}



	public void setDegtotalobtainedmarkother(String degtotalobtainedmarkother) {
		this.degtotalobtainedmarkother = degtotalobtainedmarkother;
	}



	public String getDegtotalmaxmarkother() {
		return degtotalmaxmarkother;
	}



	public void setDegtotalmaxmarkother(String degtotalmaxmarkother) {
		this.degtotalmaxmarkother = degtotalmaxmarkother;
	}



	public String getDegmaxcgpa_0() {
		return degmaxcgpa_0;
	}



	public void setDegmaxcgpa_0(String degmaxcgpa_0) {
		this.degmaxcgpa_0 = degmaxcgpa_0;
	}



	public String getDegmaxcgpa_1() {
		return degmaxcgpa_1;
	}



	public void setDegmaxcgpa_1(String degmaxcgpa_1) {
		this.degmaxcgpa_1 = degmaxcgpa_1;
	}



	public String getDegmaxcgpa_2() {
		return degmaxcgpa_2;
	}



	public void setDegmaxcgpa_2(String degmaxcgpa_2) {
		this.degmaxcgpa_2 = degmaxcgpa_2;
	}



	public String getDegmaxcgpa_3() {
		return degmaxcgpa_3;
	}



	public void setDegmaxcgpa_3(String degmaxcgpa_3) {
		this.degmaxcgpa_3 = degmaxcgpa_3;
	}



	public String getDegmaxcgpa_4() {
		return degmaxcgpa_4;
	}



	public void setDegmaxcgpa_4(String degmaxcgpa_4) {
		this.degmaxcgpa_4 = degmaxcgpa_4;
	}



	public String getDegmaxcgpa_5() {
		return degmaxcgpa_5;
	}



	public void setDegmaxcgpa_5(String degmaxcgpa_5) {
		this.degmaxcgpa_5 = degmaxcgpa_5;
	}



	public String getDegmaxcgpa_6() {
		return degmaxcgpa_6;
	}



	public void setDegmaxcgpa_6(String degmaxcgpa_6) {
		this.degmaxcgpa_6 = degmaxcgpa_6;
	}



	public String getDegmaxcgpa_7() {
		return degmaxcgpa_7;
	}



	public void setDegmaxcgpa_7(String degmaxcgpa_7) {
		this.degmaxcgpa_7 = degmaxcgpa_7;
	}



	public String getDegmaxcgpa_14() {
		return degmaxcgpa_14;
	}



	public void setDegmaxcgpa_14(String degmaxcgpa_14) {
		this.degmaxcgpa_14 = degmaxcgpa_14;
	}



	public List<UGCoursesTO> getUgcourseList() {
		return ugcourseList;
	}



	public void setUgcourseList(List<UGCoursesTO> ugcourseList) {
		this.ugcourseList = ugcourseList;
	}



	public String getPatternofStudy() {
		return patternofStudy;
	}



	public void setPatternofStudy(String patternofStudy) {
		this.patternofStudy = patternofStudy;
	}



	public List<AdmSubjectMarkForRankTO> getAdmsubMarkListUG() {
		return admsubMarkListUG;
	}



	public void setAdmsubMarkListUG(List<AdmSubjectMarkForRankTO> admsubMarkListUG) {
		this.admsubMarkListUG = admsubMarkListUG;
	}



	public String getAdmsubname_0() {
		return admsubname_0;
	}



	public void setAdmsubname_0(String admsubname_0) {
		this.admsubname_0 = admsubname_0;
	}



	public String getAdmsubname_1() {
		return admsubname_1;
	}



	public void setAdmsubname_1(String admsubname_1) {
		this.admsubname_1 = admsubname_1;
	}



	public String getAdmsubname_2() {
		return admsubname_2;
	}



	public void setAdmsubname_2(String admsubname_2) {
		this.admsubname_2 = admsubname_2;
	}



	public String getAdmsubname_3() {
		return admsubname_3;
	}



	public void setAdmsubname_3(String admsubname_3) {
		this.admsubname_3 = admsubname_3;
	}



	public String getAdmsubname_4() {
		return admsubname_4;
	}



	public void setAdmsubname_4(String admsubname_4) {
		this.admsubname_4 = admsubname_4;
	}



	public String getAdmsubname_5() {
		return admsubname_5;
	}



	public void setAdmsubname_5(String admsubname_5) {
		this.admsubname_5 = admsubname_5;
	}



	public String getAdmsubname_6() {
		return admsubname_6;
	}



	public void setAdmsubname_6(String admsubname_6) {
		this.admsubname_6 = admsubname_6;
	}



	public String getAdmsubname_7() {
		return admsubname_7;
	}



	public void setAdmsubname_7(String admsubname_7) {
		this.admsubname_7 = admsubname_7;
	}



	public String getAdmsubname_8() {
		return admsubname_8;
	}



	public void setAdmsubname_8(String admsubname_8) {
		this.admsubname_8 = admsubname_8;
	}



	public String getAdmsubname_9() {
		return admsubname_9;
	}



	public void setAdmsubname_9(String admsubname_9) {
		this.admsubname_9 = admsubname_9;
	}



	public String getAdmsubname_10() {
		return admsubname_10;
	}



	public void setAdmsubname_10(String admsubname_10) {
		this.admsubname_10 = admsubname_10;
	}



	public String getAdmsubname_11() {
		return admsubname_11;
	}



	public void setAdmsubname_11(String admsubname_11) {
		this.admsubname_11 = admsubname_11;
	}



	public String getAdmsubname_12() {
		return admsubname_12;
	}



	public void setAdmsubname_12(String admsubname_12) {
		this.admsubname_12 = admsubname_12;
	}



	public String getAdmsubname_13() {
		return admsubname_13;
	}



	public void setAdmsubname_13(String admsubname_13) {
		this.admsubname_13 = admsubname_13;
	}



	public String getAdmsubname_14() {
		return admsubname_14;
	}



	public void setAdmsubname_14(String admsubname_14) {
		this.admsubname_14 = admsubname_14;
	}



	public String getAdmsubname_15() {
		return admsubname_15;
	}



	public void setAdmsubname_15(String admsubname_15) {
		this.admsubname_15 = admsubname_15;
	}



	public String getAdmsubname_16() {
		return admsubname_16;
	}



	public void setAdmsubname_16(String admsubname_16) {
		this.admsubname_16 = admsubname_16;
	}



	public String getAdmsubname_17() {
		return admsubname_17;
	}



	public void setAdmsubname_17(String admsubname_17) {
		this.admsubname_17 = admsubname_17;
	}



	public Map<Integer, String> getDegGroupCoreMap() {
		return degGroupCoreMap;
	}



	public void setDegGroupCoreMap(Map<Integer, String> degGroupCoreMap) {
		this.degGroupCoreMap = degGroupCoreMap;
	}



	public Map<Integer, String> getDegGroupCommonMap() {
		return degGroupCommonMap;
	}



	public void setDegGroupCommonMap(Map<Integer, String> degGroupCommonMap) {
		this.degGroupCommonMap = degGroupCommonMap;
	}



	public Map<Integer, String> getDegGroupComplMap() {
		return degGroupComplMap;
	}



	public void setDegGroupComplMap(Map<Integer, String> degGroupComplMap) {
		this.degGroupComplMap = degGroupComplMap;
	}



	public Map<Integer, String> getDegGroupOpenMap() {
		return degGroupOpenMap;
	}



	public void setDegGroupOpenMap(Map<Integer, String> degGroupOpenMap) {
		this.degGroupOpenMap = degGroupOpenMap;
	}



	public Map<Integer, String> getDegGroupSubMap() {
		return degGroupSubMap;
	}



	public void setDegGroupSubMap(Map<Integer, String> degGroupSubMap) {
		this.degGroupSubMap = degGroupSubMap;
	}



	public Map<String, String> getGroupLangSubjectsMap() {
		return GroupLangSubjectsMap;
	}



	public void setGroupLangSubjectsMap(Map<String, String> groupLangSubjectsMap) {
		GroupLangSubjectsMap = groupLangSubjectsMap;
	}



	public Integer getPreferenceSize() {
		return preferenceSize;
	}



	public void setPreferenceSize(Integer preferenceSize) {
		this.preferenceSize = preferenceSize;
	}



	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}



	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}



	public Map<Integer, String> getStreamMap() {
		return streamMap;
	}



	public void setStreamMap(Map<Integer, String> streamMap) {
		this.streamMap = streamMap;
	}

	

	
	
}
