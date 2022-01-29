package com.kp.cms.forms.admission;

import java.util.HashMap;
import java.util.LinkedList;
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
import com.kp.cms.to.admin.CandidatePGIDetailsTO;
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
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.to.admin.SubjectGroupTO;
import com.kp.cms.to.admin.TermsConditionChecklistTO;
import com.kp.cms.to.admin.UGCoursesTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.to.admission.AddressTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.to.admission.PreferenceTO;
import com.kp.cms.to.admission.coursesToPrint;

public class OnlineApplicationForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private String pucInterviewDate;
	private String recomendedBy;
	private boolean pucApplicationDetailView;
	private String cancellationDate;
	private HashMap<Integer, String> secondLanguageList;
	private boolean remove;
	private List<Integer> applnNos;
	private boolean workExpMandatory;
	private String totalYearOfExp;
	private String mode;
	private Map<String,String> monthMap;
	private Map<String,String> yearMap;
	private boolean isCjc;
	private Boolean removeRegNo;
	private Boolean isCjc1;
	private String msgForPGI;
	private String applicantName;
	private String applicantDob;
	private String responseMsg;
	private String pgiStatus;
	private String residentCategoryForOnlineAppln;
	private String mobileNo1;
	private String mobileNo2;
	private String email;
	private String confirmEmail;
	private String ddNo;
	private String ddDrawnOn;
	private String ddIssuingBank;
	private String ddDate;
	private String ddAmount;
	private String ddBankCode;
	private String selectedFeePayment;
	private Boolean paymentSuccess;
	private String candidateRefNo;
	private String txnDate;
	private String txnRefNo;
	private String txnAmt;
	Map<Integer, String> programMap;
	Map<Integer, String> courseMap;
	private Boolean indianCandidate;
	private Boolean preRequisiteExists;
	private List<SingleFieldMasterTO> applicantFeedbackList;
	private String internationalApplnFeeCurrencyId;
	private String equivalentApplnFeeINR;
	private String equivalentCalcApplnFeeINR;
	private String internationalCurrencyName;
	private boolean backLogs;
	private boolean showWorkExp;
	private List<String> messageList;
	private boolean acknowledgement;
	private String tempState;
	private String tempState1;
	private String tempState2;
	private String tempState3;
	private String tempState4;
	private String tempPref1;
	private String tempPref2;
	private String nativeCountry;
	private String prgId;
	private boolean displayExamCenterDetails;
	private Map<Integer, String> interviewSelectionSchedule;
	private Map<Integer, String> interviewVenueSelection;
	private Map<Integer, String> interviewTimeSelection;
	private String interviewSelectionDate;
	private String interviewVenue;
	private String interviewTime;
	private String tempVenue;
	private String selectedDate;
	private String selectedVenue;
	private String admApplnId;
	private Boolean isTnxStatusSuccess;
	private String transactionRefNO;
	private String isInterviewSelectionSchedule;
	private String reviewed;
	private boolean acceptAll;
	private boolean tempChecked;
	private String programYear;
	private String serverDownMessage;
	private String pgmName;
	private String pgmTypeName;
	private String courseName1;
	private int studentId;
	
	//newly addded in christ
	private String institute;
	private String uniqueId;
	private Map<Integer,String> stateMap;
	private boolean iAgree;
	private String displayUniqueId;
	private String displayEmail;
	private String displayMobile;
	List<String> invalidBloodGroup;
	private Boolean isDraftMode;
	private Boolean isDraftCancelled;
	private String currentPageNo;
	private String displayPage;
	private String currentPageSaveDatabase;
	private boolean semesterMarkPage;
	private boolean detailMarkPage;
	private List<AdmApplnTO> applnToList;
	private List<CourseTO> courseListStartApp;
	private String autoSave;
	private String appStatusOnSubmition;
	private String universityId;
	private String focusValue;
	private boolean preferenceListSize;
	private String workExpListSize;
	private String coursePref2Mandatory;
	private int ednQualificationListSize;
	private boolean isExamConfigured;
	private int birthCountryId;
	private int curAddrCountyId;
	private int perAddrCountyId;
	private Map<Integer,String> curAddrStateMap;
	private Map<Integer,String> perAddrStateMap;
	private Map<Integer,String> currencyMap;
	private int parentAddrCountyId;
	private int guardianAddrCountyId;
	private Map<Integer,String> parentStateMap;
	private Map<Integer,String> guardianStateMap;
	private String isCourseDraft;
	private String lastExam;
	private String consolidated;
	private String isDisplayExtraDetails;
	private List<ApplicantWorkExperienceTO> workExpList;
	private Boolean savedDraftAlertMsg;
	
	
	//our old properties
	
	private String nccgrade;
	private boolean ncccertificate;
	private boolean viewextradetails;
	private boolean viewparish;
	private List<DioceseTo> dioceseList;
	private List<ParishTo> parishList;
  
	private String admissionNumber;
	private Integer checkReligionId;
	private boolean hostelcheck;
	private Boolean isHostelAdmission;
	private List<PreferencesTO> preferencesList;
	
	//for ug
	private Map<Integer,String> admSubjectMap;
	private Map<Integer,String> admSubjectLangMap;
	
	private String admLangsubId;
	private boolean class12check;
	private boolean classdegcheck;
	private List<AdmSubjectMarkForRankTO> admsubMarkList=new LinkedList<AdmSubjectMarkForRankTO>();
	private String totalobtainedmark;
	private String totalmaxmark;
	private boolean isSaypass;
	private String prefName;

	
	
	private Map<Integer,String> subReligionMap;
	private Map<Integer,String> subCasteMap;
	private boolean sameParentAddr;

	
	//raghu added for billdesk
	
	private String mihpayid;
	private String mode1;
	private String status;
	private String key;
	private String txnid;
	private String amount;
	private String productinfo;
	private String hash;
	private String error1;
	private String PG_TYPE;
	private String bank_ref_num;
	private String unmappedstatus;
	private String payuMoneyId;
	private String refNo;
	
	private String test;
	private String motherMobile;
	private String fatherMobile;
	private String sports;
	private String arts;
	private String sportsParticipate;
	private String artsParticipate;
	private String addonCourse;
	private String additionalCharges;
	private String applicationAmount1;
	private String paymentMail;
	
	
	
	//for pg
	
	private Map<Integer,String>  admCoreMap;
	private Map<Integer,String>  admComplMap;
	private Map<Integer,String>  admCommonMap;
	private Map<Integer,String>  admMainMap;
	private Map<Integer,String>  admSubMap;
	

	
	
	
	
	private String degtotalobtainedmark;
	private String degtotalmaxmark;
	private String degtotalobtainedmarkother;
	private String degtotalmaxmarkother;
	
	private boolean memo;
	
	private List<UGCoursesTO> ugcourseList;
	private String patternofStudy;
	private Integer preferenceSize;
	private CandidateMarkTO detailMarkClass12;
	private CandidateMarkTO detailMarkDegree;
	private List<DistrictTO> editPermanentDistrict;
	private List<DistrictTO> editCurrentDistrict;
	private Map<Integer,String>  streamMap;
	private List<CandidatePGIDetailsTO> candidatePendingTransaction;
	private String verificationRefNo;
	
	private Integer checkRCSCId;
	private Integer categoryRCSCId;
	private Boolean mgquota;
	private boolean dateExpired;
	private boolean aided;
	private String nssgrade;
	private boolean nsscertificate;
	private String recommedationApplicationNo;
	private boolean courseListed;
	private List<coursesToPrint> coursesList;
	private boolean aidedCourse;
	
	
	
	private PersonalDataTO personalData;
	
	private FormFile studentPhoto;
	private boolean  isUpdated;
	private Map<Integer, String> mbaEntranceExamMap;
	private boolean isByPass;
	
	private String authzCode;
	private String responceCode;
	private String statusDes;
	private String isCommuQuota;
	private String mngQuota;
	private boolean isPhotoEdited;
    private boolean isPDataEdited;
	

	public int getEdnQualificationListSize() {
		return ednQualificationListSize;
	}

	public void setEdnQualificationListSize(int ednQualificationListSize) {
		this.ednQualificationListSize = ednQualificationListSize;
	}

	public String getCoursePref2Mandatory() {
		return coursePref2Mandatory;
	}

	public void setCoursePref2Mandatory(String coursePref2Mandatory) {
		this.coursePref2Mandatory = coursePref2Mandatory;
	}

	public boolean isPreferenceListSize() {
		return preferenceListSize;
	}

	public void setPreferenceListSize(boolean preferenceListSize) {
		this.preferenceListSize = preferenceListSize;
	}

	public String getWorkExpListSize() {
		return workExpListSize;
	}

	public void setWorkExpListSize(String workExpListSize) {
		this.workExpListSize = workExpListSize;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	public OnlineApplicationForm() {
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

	
	public String getApplicationNumber() {
		return applicationNumber;
	}
	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
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
	public String getConfirmEmailId() {
		return confirmEmailId;
	}
	public void setConfirmEmailId(String confirmEmailId) {
		this.confirmEmailId = confirmEmailId;
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
	public String getResidentCategory() {
		return residentCategory;
	}
	public void setResidentCategory(String residentCategory) {
		this.residentCategory = residentCategory;
	}
	public List<ResidentCategoryTO> getResidentTypes() {
		return residentTypes;
	}
	public void setResidentTypes(List<ResidentCategoryTO> residentTypes) {
		this.residentTypes = residentTypes;
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
	public String getMotherEmail() {
		return motherEmail;
	}
	public void setMotherEmail(String motherEmail) {
		this.motherEmail = motherEmail;
	}
	public AddressTO getParentAddress() {
		return parentAddress;
	}
	public void setParentAddress(AddressTO parentAddress) {
		this.parentAddress = parentAddress;
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
	public List<CasteTO> getCasteList() {
		return casteList;
	}
	public void setCasteList(List<CasteTO> casteList) {
		this.casteList = casteList;
	}
	public List<ProgramTypeTO> getProgramtypeList() {
		return programtypeList;
	}
	public void setProgramtypeList(List<ProgramTypeTO> programtypeList) {
		this.programtypeList = programtypeList;
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
	public List<CountryTO> getCountries() {
		return countries;
	}
	public void setCountries(List<CountryTO> countries) {
		this.countries = countries;
	}
	public List<CurrencyTO> getCurrencies() {
		return currencies;
	}
	public void setCurrencies(List<CurrencyTO> currencies) {
		this.currencies = currencies;
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
	public String getFatherEmail() {
		return fatherEmail;
	}
	public void setFatherEmail(String fatherEmail) {
		this.fatherEmail = fatherEmail;
	}
	public String getChallanNo() {
		return challanNo;
	}
	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}
	public String getJournalNo() {
		return journalNo;
	}
	public void setJournalNo(String journalNo) {
		this.journalNo = journalNo;
	}
	public String getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getApplicationAmount() {
		return applicationAmount;
	}
	public void setApplicationAmount(String applicationAmount) {
		this.applicationAmount = applicationAmount;
	}
	public List<UniversityTO> getUniversities() {
		return universities;
	}
	public void setUniversities(List<UniversityTO> universities) {
		this.universities = universities;
	}
	public List<EdnQualificationTO> getQualifications() {
		return qualifications;
	}
	public void setQualifications(List<EdnQualificationTO> qualifications) {
		this.qualifications = qualifications;
	}
	public CandidateMarkTO getDetailMark() {
		return detailMark;
	}
	public void setDetailMark(CandidateMarkTO detailMark) {
		this.detailMark = detailMark;
	}
	public List<ApplnDocTO> getUploadDocs() {
		return uploadDocs;
	}
	public void setUploadDocs(List<ApplnDocTO> uploadDocs) {
		this.uploadDocs = uploadDocs;
	}
	public List<OccupationTO> getOccupations() {
		return occupations;
	}
	public void setOccupations(List<OccupationTO> occupations) {
		this.occupations = occupations;
	}
	public List<IncomeTO> getIncomes() {
		return incomes;
	}
	public void setIncomes(List<IncomeTO> incomes) {
		this.incomes = incomes;
	}
	public List<CourseTO> getPrefcourses() {
		return prefcourses;
	}
	public void setPrefcourses(List<CourseTO> prefcourses) {
		this.prefcourses = prefcourses;
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
	public List<NationalityTO> getNationalities() {
		return nationalities;
	}
	public void setNationalities(List<NationalityTO> nationalities) {
		this.nationalities = nationalities;
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
	public boolean isGuidelineExist() {
		return guidelineExist;
	}
	public void setGuidelineExist(boolean guidelineExist) {
		this.guidelineExist = guidelineExist;
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
	public boolean isWorkExpNeeded() {
		return workExpNeeded;
	}
	public void setWorkExpNeeded(boolean workExpNeeded) {
		this.workExpNeeded = workExpNeeded;
	}
	public List<CoursePrerequisiteTO> getCoursePrerequisites() {
		return coursePrerequisites;
	}
	public void setCoursePrerequisites(
			List<CoursePrerequisiteTO> coursePrerequisites) {
		this.coursePrerequisites = coursePrerequisites;
	}
	public List<CoursePrerequisiteTO> getEligPrerequisites() {
		return eligPrerequisites;
	}
	public void setEligPrerequisites(List<CoursePrerequisiteTO> eligPrerequisites) {
		this.eligPrerequisites = eligPrerequisites;
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
	public List<EntrancedetailsTO> getEntranceList() {
		return entranceList;
	}
	public void setEntranceList(List<EntrancedetailsTO> entranceList) {
		this.entranceList = entranceList;
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
	public String getTermConditions() {
		return termConditions;
	}
	public void setTermConditions(String termConditions) {
		this.termConditions = termConditions;
	}
	public String getGuidelines() {
		return guidelines;
	}
	public void setGuidelines(String guidelines) {
		this.guidelines = guidelines;
	}
	public List<TermsConditionChecklistTO> getConditionChecklists() {
		return conditionChecklists;
	}
	public void setConditionChecklists(
			List<TermsConditionChecklistTO> conditionChecklists) {
		this.conditionChecklists = conditionChecklists;
	}
	public List<GuideLinesCheckListTO> getGuidelineChecklists() {
		return guidelineChecklists;
	}
	public void setGuidelineChecklists(
			List<GuideLinesCheckListTO> guidelineChecklists) {
		this.guidelineChecklists = guidelineChecklists;
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
	public List<String> getPendingDocList() {
		return pendingDocList;
	}
	public void setPendingDocList(List<String> pendingDocList) {
		this.pendingDocList = pendingDocList;
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
	public boolean isEligibleCheck() {
		return eligibleCheck;
	}
	public void setEligibleCheck(boolean eligibleCheck) {
		this.eligibleCheck = eligibleCheck;
	}
	public boolean isReviewWarned() {
		return reviewWarned;
	}
	public void setReviewWarned(boolean reviewWarned) {
		this.reviewWarned = reviewWarned;
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
	public AddressTO getGuardianAddress() {
		return guardianAddress;
	}
	public void setGuardianAddress(AddressTO guardianAddress) {
		this.guardianAddress = guardianAddress;
	}
	public String getGuardianPhone1() {
		return guardianPhone1;
	}
	public void setGuardianPhone1(String guardianPhone1) {
		this.guardianPhone1 = guardianPhone1;
	}
	public String getGuardianPhone2() {
		return guardianPhone2;
	}
	public void setGuardianPhone2(String guardianPhone2) {
		this.guardianPhone2 = guardianPhone2;
	}
	public String getGuardianPhone3() {
		return guardianPhone3;
	}
	public void setGuardianPhone3(String guardianPhone3) {
		this.guardianPhone3 = guardianPhone3;
	}
	public String getGuardianMobile1() {
		return guardianMobile1;
	}
	public void setGuardianMobile1(String guardianMobile1) {
		this.guardianMobile1 = guardianMobile1;
	}
	public String getGuardianMobile2() {
		return guardianMobile2;
	}
	public void setGuardianMobile2(String guardianMobile2) {
		this.guardianMobile2 = guardianMobile2;
	}
	public String getGuardianMobile3() {
		return guardianMobile3;
	}
	public void setGuardianMobile3(String guardianMobile3) {
		this.guardianMobile3 = guardianMobile3;
	}
	public String getSportsDescription() {
		return sportsDescription;
	}
	public void setSportsDescription(String sportsDescription) {
		this.sportsDescription = sportsDescription;
	}
	public String getHadnicappedDescription() {
		return hadnicappedDescription;
	}
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
	public Map<Integer, String> getDetailedSubjectsMap() {
		return detailedSubjectsMap;
	}
	public void setDetailedSubjectsMap(Map<Integer, String> detailedSubjectsMap) {
		this.detailedSubjectsMap = detailedSubjectsMap;
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
	public String getSecondLanguage() {
		return secondLanguage;
	}
	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
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
	public String getGuardianName() {
		return guardianName;
	}
	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}
	public String getEntranceId() {
		return entranceId;
	}
	public void setEntranceId(String entranceId) {
		this.entranceId = entranceId;
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
	public String getEntranceRollNo() {
		return entranceRollNo;
	}
	public void setEntranceRollNo(String entranceRollNo) {
		this.entranceRollNo = entranceRollNo;
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
	public boolean isCheckOfflineAppNo() {
		return checkOfflineAppNo;
	}
	public void setCheckOfflineAppNo(boolean checkOfflineAppNo) {
		this.checkOfflineAppNo = checkOfflineAppNo;
	}
	public boolean isApplicationError() {
		return applicationError;
	}
	public void setApplicationError(boolean applicationError) {
		this.applicationError = applicationError;
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
	public void setTransferDetails(List<ApplicantTransferDetailsTO> transferDetails) {
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
	public String getPendingDocumentTemplate() {
		return pendingDocumentTemplate;
	}
	public void setPendingDocumentTemplate(String pendingDocumentTemplate) {
		this.pendingDocumentTemplate = pendingDocumentTemplate;
	}
	public String getOfficeInstrTemplate() {
		return officeInstrTemplate;
	}
	public void setOfficeInstrTemplate(String officeInstrTemplate) {
		this.officeInstrTemplate = officeInstrTemplate;
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
	public boolean isCasteDisplay() {
		return casteDisplay;
	}
	public void setCasteDisplay(boolean casteDisplay) {
		this.casteDisplay = casteDisplay;
	}
	public List<StateTO> getEdnStates() {
		return ednStates;
	}
	public void setEdnStates(List<StateTO> ednStates) {
		this.ednStates = ednStates;
	}
	public boolean isOutsideCourseSelected() {
		return outsideCourseSelected;
	}
	public void setOutsideCourseSelected(boolean outsideCourseSelected) {
		this.outsideCourseSelected = outsideCourseSelected;
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
	public boolean isDataSaved() {
		return isDataSaved;
	}
	public void setDataSaved(boolean isDataSaved) {
		this.isDataSaved = isDataSaved;
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
	
	public boolean isPucApplicationDetailView() {
		return pucApplicationDetailView;
	}
	public void setPucApplicationDetailView(boolean pucApplicationDetailView) {
		this.pucApplicationDetailView = pucApplicationDetailView;
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
	
	public List<Integer> getApplnNos() {
		return applnNos;
	}
	public void setApplnNos(List<Integer> applnNos) {
		this.applnNos = applnNos;
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
	public boolean isCjc() {
		return isCjc;
	}
	public void setCjc(boolean isCjc) {
		this.isCjc = isCjc;
	}
	public Boolean getRemoveRegNo() {
		return removeRegNo;
	}
	public void setRemoveRegNo(Boolean removeRegNo) {
		this.removeRegNo = removeRegNo;
	}
	public Boolean getIsCjc1() {
		return isCjc1;
	}
	public void setIsCjc1(Boolean isCjc1) {
		this.isCjc1 = isCjc1;
	}
	public String getMsgForPGI() {
		return msgForPGI;
	}
	public void setMsgForPGI(String msgForPGI) {
		this.msgForPGI = msgForPGI;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getApplicantDob() {
		return applicantDob;
	}
	public void setApplicantDob(String applicantDob) {
		this.applicantDob = applicantDob;
	}
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	public String getPgiStatus() {
		return pgiStatus;
	}
	public void setPgiStatus(String pgiStatus) {
		this.pgiStatus = pgiStatus;
	}
	public String getResidentCategoryForOnlineAppln() {
		return residentCategoryForOnlineAppln;
	}
	public void setResidentCategoryForOnlineAppln(
			String residentCategoryForOnlineAppln) {
		this.residentCategoryForOnlineAppln = residentCategoryForOnlineAppln;
	}
	public String getMobileNo1() {
		return mobileNo1;
	}
	public void setMobileNo1(String mobileNo1) {
		this.mobileNo1 = mobileNo1;
	}
	public String getMobileNo2() {
		return mobileNo2;
	}
	public void setMobileNo2(String mobileNo2) {
		this.mobileNo2 = mobileNo2;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getConfirmEmail() {
		return confirmEmail;
	}
	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}
	public String getDdNo() {
		return ddNo;
	}
	public void setDdNo(String ddNo) {
		this.ddNo = ddNo;
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
	public String getDdDate() {
		return ddDate;
	}
	public void setDdDate(String ddDate) {
		this.ddDate = ddDate;
	}
	public String getDdAmount() {
		return ddAmount;
	}
	public void setDdAmount(String ddAmount) {
		this.ddAmount = ddAmount;
	}
	public String getDdBankCode() {
		return ddBankCode;
	}
	public void setDdBankCode(String ddBankCode) {
		this.ddBankCode = ddBankCode;
	}
	public String getSelectedFeePayment() {
		return selectedFeePayment;
	}
	public void setSelectedFeePayment(String selectedFeePayment) {
		this.selectedFeePayment = selectedFeePayment;
	}
	public Boolean getPaymentSuccess() {
		return paymentSuccess;
	}
	public void setPaymentSuccess(Boolean paymentSuccess) {
		this.paymentSuccess = paymentSuccess;
	}
	public String getCandidateRefNo() {
		return candidateRefNo;
	}
	public void setCandidateRefNo(String candidateRefNo) {
		this.candidateRefNo = candidateRefNo;
	}
	public String getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	public String getTxnRefNo() {
		return txnRefNo;
	}
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}
	public String getTxnAmt() {
		return txnAmt;
	}
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	public Map<Integer, String> getProgramMap() {
		return programMap;
	}
	public void setProgramMap(Map<Integer, String> programMap) {
		this.programMap = programMap;
	}
	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}
	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}
	public Boolean getIndianCandidate() {
		return indianCandidate;
	}
	public void setIndianCandidate(Boolean indianCandidate) {
		this.indianCandidate = indianCandidate;
	}
	public Boolean getPreRequisiteExists() {
		return preRequisiteExists;
	}
	public void setPreRequisiteExists(Boolean preRequisiteExists) {
		this.preRequisiteExists = preRequisiteExists;
	}
	public List<SingleFieldMasterTO> getApplicantFeedbackList() {
		return applicantFeedbackList;
	}
	public void setApplicantFeedbackList(
			List<SingleFieldMasterTO> applicantFeedbackList) {
		this.applicantFeedbackList = applicantFeedbackList;
	}
	public String getInternationalApplnFeeCurrencyId() {
		return internationalApplnFeeCurrencyId;
	}
	public void setInternationalApplnFeeCurrencyId(
			String internationalApplnFeeCurrencyId) {
		this.internationalApplnFeeCurrencyId = internationalApplnFeeCurrencyId;
	}
	public String getEquivalentApplnFeeINR() {
		return equivalentApplnFeeINR;
	}
	public void setEquivalentApplnFeeINR(String equivalentApplnFeeINR) {
		this.equivalentApplnFeeINR = equivalentApplnFeeINR;
	}
	public String getEquivalentCalcApplnFeeINR() {
		return equivalentCalcApplnFeeINR;
	}
	public void setEquivalentCalcApplnFeeINR(String equivalentCalcApplnFeeINR) {
		this.equivalentCalcApplnFeeINR = equivalentCalcApplnFeeINR;
	}
	public String getInternationalCurrencyName() {
		return internationalCurrencyName;
	}
	public void setInternationalCurrencyName(String internationalCurrencyName) {
		this.internationalCurrencyName = internationalCurrencyName;
	}
	public boolean isBackLogs() {
		return backLogs;
	}
	public void setBackLogs(boolean backLogs) {
		this.backLogs = backLogs;
	}
	public boolean isShowWorkExp() {
		return showWorkExp;
	}
	public void setShowWorkExp(boolean showWorkExp) {
		this.showWorkExp = showWorkExp;
	}
	public List<String> getMessageList() {
		return messageList;
	}
	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}
	public boolean isAcknowledgement() {
		return acknowledgement;
	}
	public void setAcknowledgement(boolean acknowledgement) {
		this.acknowledgement = acknowledgement;
	}
	public String getTempState() {
		return tempState;
	}
	public void setTempState(String tempState) {
		this.tempState = tempState;
	}
	public String getTempState1() {
		return tempState1;
	}
	public void setTempState1(String tempState1) {
		this.tempState1 = tempState1;
	}
	public String getTempState2() {
		return tempState2;
	}
	public void setTempState2(String tempState2) {
		this.tempState2 = tempState2;
	}
	public String getTempState3() {
		return tempState3;
	}
	public void setTempState3(String tempState3) {
		this.tempState3 = tempState3;
	}
	public String getTempState4() {
		return tempState4;
	}
	public void setTempState4(String tempState4) {
		this.tempState4 = tempState4;
	}
	public String getTempPref1() {
		return tempPref1;
	}
	public void setTempPref1(String tempPref1) {
		this.tempPref1 = tempPref1;
	}
	public String getTempPref2() {
		return tempPref2;
	}
	public void setTempPref2(String tempPref2) {
		this.tempPref2 = tempPref2;
	}
	public String getNativeCountry() {
		return nativeCountry;
	}
	public void setNativeCountry(String nativeCountry) {
		this.nativeCountry = nativeCountry;
	}
	public String getPrgId() {
		return prgId;
	}
	public void setPrgId(String prgId) {
		this.prgId = prgId;
	}
	public boolean isDisplayExamCenterDetails() {
		return displayExamCenterDetails;
	}
	public void setDisplayExamCenterDetails(boolean displayExamCenterDetails) {
		this.displayExamCenterDetails = displayExamCenterDetails;
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
	public String getAdmApplnId() {
		return admApplnId;
	}
	public void setAdmApplnId(String admApplnId) {
		this.admApplnId = admApplnId;
	}
	public Boolean getIsTnxStatusSuccess() {
		return isTnxStatusSuccess;
	}
	public void setIsTnxStatusSuccess(Boolean isTnxStatusSuccess) {
		this.isTnxStatusSuccess = isTnxStatusSuccess;
	}
	public String getTransactionRefNO() {
		return transactionRefNO;
	}
	public void setTransactionRefNO(String transactionRefNO) {
		this.transactionRefNO = transactionRefNO;
	}
	public String getIsInterviewSelectionSchedule() {
		return isInterviewSelectionSchedule;
	}
	public void setIsInterviewSelectionSchedule(String isInterviewSelectionSchedule) {
		this.isInterviewSelectionSchedule = isInterviewSelectionSchedule;
	}
	public String getReviewed() {
		return reviewed;
	}
	public void setReviewed(String reviewed) {
		this.reviewed = reviewed;
	}
	public boolean isAcceptAll() {
		return acceptAll;
	}
	public void setAcceptAll(boolean acceptAll) {
		this.acceptAll = acceptAll;
	}
	public boolean isTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(boolean tempChecked) {
		this.tempChecked = tempChecked;
	}
	public String getProgramYear() {
		return programYear;
	}
	public void setProgramYear(String programYear) {
		this.programYear = programYear;
	}
	public String getServerDownMessage() {
		return serverDownMessage;
	}
	public void setServerDownMessage(String serverDownMessage) {
		this.serverDownMessage = serverDownMessage;
	}
	public String getPgmName() {
		return pgmName;
	}
	public void setPgmName(String pgmName) {
		this.pgmName = pgmName;
	}
	public String getPgmTypeName() {
		return pgmTypeName;
	}
	public void setPgmTypeName(String pgmTypeName) {
		this.pgmTypeName = pgmTypeName;
	}
	public String getCourseName1() {
		return courseName1;
	}
	public void setCourseName1(String courseName1) {
		this.courseName1 = courseName1;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	
	public String getInstitute() {
		return institute;
	}
	public void setInstitute(String institute) {
		this.institute = institute;
	}
	
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public Map<Integer, String> getStateMap() {
		return stateMap;
	}
	public void setStateMap(Map<Integer, String> stateMap) {
		this.stateMap = stateMap;
	}
	public boolean isiAgree() {
		return iAgree;
	}
	public void setiAgree(boolean iAgree) {
		this.iAgree = iAgree;
	}
	
	
	public String getDisplayUniqueId() {
		return displayUniqueId;
	}
	public void setDisplayUniqueId(String displayUniqueId) {
		this.displayUniqueId = displayUniqueId;
	}
	public String getDisplayEmail() {
		return displayEmail;
	}
	public void setDisplayEmail(String displayEmail) {
		this.displayEmail = displayEmail;
	}
	public String getDisplayMobile() {
		return displayMobile;
	}
	public void setDisplayMobile(String displayMobile) {
		this.displayMobile = displayMobile;
	}
	public List<String> getInvalidBloodGroup() {
		return invalidBloodGroup;
	}
	public void setInvalidBloodGroup(List<String> invalidBloodGroup) {
		this.invalidBloodGroup = invalidBloodGroup;
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

	public String getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(String currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public String getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	public String getCurrentPageSaveDatabase() {
		return currentPageSaveDatabase;
	}

	public void setCurrentPageSaveDatabase(String currentPageSaveDatabase) {
		this.currentPageSaveDatabase = currentPageSaveDatabase;
	}

	

	public boolean isSemesterMarkPage() {
		return semesterMarkPage;
	}

	public void setSemesterMarkPage(boolean semesterMarkPage) {
		this.semesterMarkPage = semesterMarkPage;
	}

	public boolean isDetailMarkPage() {
		return detailMarkPage;
	}

	public void setDetailMarkPage(boolean detailMarkPage) {
		this.detailMarkPage = detailMarkPage;
	}

	public List<AdmApplnTO> getApplnToList() {
		return applnToList;
	}

	public void setApplnToList(List<AdmApplnTO> applnToList) {
		this.applnToList = applnToList;
	}

	public List<CourseTO> getCourseListStartApp() {
		return courseListStartApp;
	}

	public void setCourseListStartApp(List<CourseTO> courseListStartApp) {
		this.courseListStartApp = courseListStartApp;
	}

	public String getAutoSave() {
		return autoSave;
	}

	public void setAutoSave(String autoSave) {
		this.autoSave = autoSave;
	}

	public String getAppStatusOnSubmition() {
		return appStatusOnSubmition;
	}

	public void setAppStatusOnSubmition(String appStatusOnSubmition) {
		this.appStatusOnSubmition = appStatusOnSubmition;
	}

	public String getUniversityId() {
		return universityId;
	}

	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}

	public String getFocusValue() {
		return focusValue;
	}

	public void setFocusValue(String focusValue) {
		this.focusValue = focusValue;
	}

	
	public boolean isExamConfigured() {
		return isExamConfigured;
	}

	public void setExamConfigured(boolean isExamConfigured) {
		this.isExamConfigured = isExamConfigured;
	}

	public int getBirthCountryId() {
		return birthCountryId;
	}

	public void setBirthCountryId(int birthCountryId) {
		this.birthCountryId = birthCountryId;
	}

	public int getCurAddrCountyId() {
		return curAddrCountyId;
	}

	public void setCurAddrCountyId(int curAddrCountyId) {
		this.curAddrCountyId = curAddrCountyId;
	}

	public int getPerAddrCountyId() {
		return perAddrCountyId;
	}

	public void setPerAddrCountyId(int perAddrCountyId) {
		this.perAddrCountyId = perAddrCountyId;
	}

	public Map<Integer, String> getCurAddrStateMap() {
		return curAddrStateMap;
	}

	public void setCurAddrStateMap(Map<Integer, String> curAddrStateMap) {
		this.curAddrStateMap = curAddrStateMap;
	}

	public Map<Integer, String> getPerAddrStateMap() {
		return perAddrStateMap;
	}

	public void setPerAddrStateMap(Map<Integer, String> perAddrStateMap) {
		this.perAddrStateMap = perAddrStateMap;
	}

	public Map<Integer, String> getCurrencyMap() {
		return currencyMap;
	}

	public void setCurrencyMap(Map<Integer, String> currencyMap) {
		this.currencyMap = currencyMap;
	}

	public int getParentAddrCountyId() {
		return parentAddrCountyId;
	}

	public void setParentAddrCountyId(int parentAddrCountyId) {
		this.parentAddrCountyId = parentAddrCountyId;
	}

	public int getGuardianAddrCountyId() {
		return guardianAddrCountyId;
	}

	public void setGuardianAddrCountyId(int guardianAddrCountyId) {
		this.guardianAddrCountyId = guardianAddrCountyId;
	}

	public Map<Integer, String> getParentStateMap() {
		return parentStateMap;
	}

	public void setParentStateMap(Map<Integer, String> parentStateMap) {
		this.parentStateMap = parentStateMap;
	}

	public Map<Integer, String> getGuardianStateMap() {
		return guardianStateMap;
	}

	public void setGuardianStateMap(Map<Integer, String> guardianStateMap) {
		this.guardianStateMap = guardianStateMap;
	}

	public String getIsCourseDraft() {
		return isCourseDraft;
	}

	public void setIsCourseDraft(String isCourseDraft) {
		this.isCourseDraft = isCourseDraft;
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

	public boolean isViewextradetails() {
		return viewextradetails;
	}

	public void setViewextradetails(boolean viewextradetails) {
		this.viewextradetails = viewextradetails;
	}

	public boolean isViewparish() {
		return viewparish;
	}

	public void setViewparish(boolean viewparish) {
		this.viewparish = viewparish;
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

	public String getAdmissionNumber() {
		return admissionNumber;
	}

	public void setAdmissionNumber(String admissionNumber) {
		this.admissionNumber = admissionNumber;
	}

	public Integer getCheckReligionId() {
		return checkReligionId;
	}

	public void setCheckReligionId(Integer checkReligionId) {
		this.checkReligionId = checkReligionId;
	}

	public boolean isHostelcheck() {
		return hostelcheck;
	}

	public void setHostelcheck(boolean hostelcheck) {
		this.hostelcheck = hostelcheck;
	}

	public Boolean getIsHostelAdmission() {
		return isHostelAdmission;
	}

	public void setIsHostelAdmission(Boolean isHostelAdmission) {
		this.isHostelAdmission = isHostelAdmission;
	}

	public List<PreferencesTO> getPreferencesList() {
		return preferencesList;
	}

	public void setPreferencesList(List<PreferencesTO> preferencesList) {
		this.preferencesList = preferencesList;
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

	public boolean getIsSaypass() {
		return isSaypass;
	}

	public void setIsSaypass(boolean isSaypass) {
		this.isSaypass = isSaypass;
	}

	public String getPrefName() {
		return prefName;
	}

	public void setPrefName(String prefName) {
		this.prefName = prefName;
	}

	

	

	

	

	public Map<Integer, String> getSubReligionMap() {
		return subReligionMap;
	}

	public void setSubReligionMap(Map<Integer, String> subReligionMap) {
		this.subReligionMap = subReligionMap;
	}

	public Map<Integer, String> getSubCasteMap() {
		return subCasteMap;
	}

	public void setSubCasteMap(Map<Integer, String> subCasteMap) {
		this.subCasteMap = subCasteMap;
	}

	public boolean isSameParentAddr() {
		return sameParentAddr;
	}

	public void setSameParentAddr(boolean sameParentAddr) {
		this.sameParentAddr = sameParentAddr;
	}

	public String getMihpayid() {
		return mihpayid;
	}

	public void setMihpayid(String mihpayid) {
		this.mihpayid = mihpayid;
	}

	public String getMode1() {
		return mode1;
	}

	public void setMode1(String mode1) {
		this.mode1 = mode1;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTxnid() {
		return txnid;
	}

	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getProductinfo() {
		return productinfo;
	}

	public void setProductinfo(String productinfo) {
		this.productinfo = productinfo;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getError1() {
		return error1;
	}

	public void setError1(String error1) {
		this.error1 = error1;
	}

	public String getPG_TYPE() {
		return PG_TYPE;
	}

	public void setPG_TYPE(String pGTYPE) {
		PG_TYPE = pGTYPE;
	}

	public String getBank_ref_num() {
		return bank_ref_num;
	}

	public void setBank_ref_num(String bankRefNum) {
		bank_ref_num = bankRefNum;
	}

	public String getUnmappedstatus() {
		return unmappedstatus;
	}

	public void setUnmappedstatus(String unmappedstatus) {
		this.unmappedstatus = unmappedstatus;
	}

	public String getPayuMoneyId() {
		return payuMoneyId;
	}

	public void setPayuMoneyId(String payuMoneyId) {
		this.payuMoneyId = payuMoneyId;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String getMotherMobile() {
		return motherMobile;
	}

	public void setMotherMobile(String motherMobile) {
		this.motherMobile = motherMobile;
	}

	public String getFatherMobile() {
		return fatherMobile;
	}

	public void setFatherMobile(String fatherMobile) {
		this.fatherMobile = fatherMobile;
	}

	public String getSports() {
		return sports;
	}

	public void setSports(String sports) {
		this.sports = sports;
	}

	public String getArts() {
		return arts;
	}

	public void setArts(String arts) {
		this.arts = arts;
	}

	public String getSportsParticipate() {
		return sportsParticipate;
	}

	public void setSportsParticipate(String sportsParticipate) {
		this.sportsParticipate = sportsParticipate;
	}

	public String getArtsParticipate() {
		return artsParticipate;
	}

	public void setArtsParticipate(String artsParticipate) {
		this.artsParticipate = artsParticipate;
	}

	public String getAddonCourse() {
		return addonCourse;
	}

	public void setAddonCourse(String addonCourse) {
		this.addonCourse = addonCourse;
	}

	public String getAdditionalCharges() {
		return additionalCharges;
	}

	public void setAdditionalCharges(String additionalCharges) {
		this.additionalCharges = additionalCharges;
	}

	public String getApplicationAmount1() {
		return applicationAmount1;
	}

	public void setApplicationAmount1(String applicationAmount1) {
		this.applicationAmount1 = applicationAmount1;
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

	

	public boolean isMemo() {
		return memo;
	}

	public void setMemo(boolean memo) {
		this.memo = memo;
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

	public Integer getPreferenceSize() {
		return preferenceSize;
	}

	public void setPreferenceSize(Integer preferenceSize) {
		this.preferenceSize = preferenceSize;
	}
	
	public String getLastExam() {
			return lastExam;
		}
	
	public void setLastExam(String lastExam) {
		this.lastExam = lastExam;
	}
	
	public String getConsolidated() {
		return consolidated;
	}
	
	public void setConsolidated(String consolidated) {
		this.consolidated = consolidated;
	}
	
	public String getIsDisplayExtraDetails() {
		return isDisplayExtraDetails;
	}
	
	public void setIsDisplayExtraDetails(String isDisplayExtraDetails) {
		this.isDisplayExtraDetails = isDisplayExtraDetails;
	}
	
	
	public List<ApplicantWorkExperienceTO> getWorkExpList() {
		return workExpList;
	}
	
	public void setWorkExpList(List<ApplicantWorkExperienceTO> workExpList) {
		this.workExpList = workExpList;
	}
	
	public Boolean getSavedDraftAlertMsg() {
		return savedDraftAlertMsg;
	}
	
	public void setSavedDraftAlertMsg(Boolean savedDraftAlertMsg) {
		this.savedDraftAlertMsg = savedDraftAlertMsg;
	}
	
	public CandidateMarkTO getDetailMarkClass12() {
		return detailMarkClass12;
	}
	
	public void setDetailMarkClass12(CandidateMarkTO detailMarkClass12) {
		this.detailMarkClass12 = detailMarkClass12;
	}
	
	public CandidateMarkTO getDetailMarkDegree() {
		return detailMarkDegree;
	}
	
	public void setDetailMarkDegree(CandidateMarkTO detailMarkDegree) {
		this.detailMarkDegree = detailMarkDegree;
	}
	
	public String getPaymentMail() {
		return paymentMail;
	}
	
	public void setPaymentMail(String paymentMail) {
		this.paymentMail = paymentMail;
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
	
	public Map<Integer, String> getStreamMap() {
		return streamMap;
	}
	
	public void setStreamMap(Map<Integer, String> streamMap) {
		this.streamMap = streamMap;
	}
	
	public List<CandidatePGIDetailsTO> getCandidatePendingTransaction() {
		return candidatePendingTransaction;
	}
	
	public void setCandidatePendingTransaction(
			List<CandidatePGIDetailsTO> candidatePendingTransaction) {
		this.candidatePendingTransaction = candidatePendingTransaction;
	}
	
	public String getVerificationRefNo() {
		return verificationRefNo;
	}
	
	public void setVerificationRefNo(String verificationRefNo) {
		this.verificationRefNo = verificationRefNo;
	}
	
	public Integer getCheckRCSCId() {
		return checkRCSCId;
	}
	
	public void setCheckRCSCId(Integer checkRCSCId) {
		this.checkRCSCId = checkRCSCId;
	}
	
	public Integer getCategoryRCSCId() {
		return categoryRCSCId;
	}
	
	public void setCategoryRCSCId(Integer categoryRCSCId) {
		this.categoryRCSCId = categoryRCSCId;
	}
	
	
	public Boolean getMgquota() {
		return mgquota;
	}
	
	public void setMgquota(Boolean mgquota) {
		this.mgquota = mgquota;
	}
	
	public boolean isDateExpired() {
		return dateExpired;
	}
	
	public void setDateExpired(boolean dateExpired) {
		this.dateExpired = dateExpired;
	}
	
	public boolean isAided() {
		return aided;
	}
	
	public void setAided(boolean aided) {
		this.aided = aided;
	}
	
	public String getNssgrade() {
		return nssgrade;
	}
	
	public void setNssgrade(String nssgrade) {
		this.nssgrade = nssgrade;
	}
	
	public boolean isNsscertificate() {
		return nsscertificate;
	}
	
	public void setNsscertificate(boolean nsscertificate) {
		this.nsscertificate = nsscertificate;
	}
	
	public PersonalDataTO getPersonalData() {
		return personalData;
	}
	
	public void setPersonalData(PersonalDataTO personalData) {
		this.personalData = personalData;
	}
	
	public FormFile getStudentPhoto() {
		return studentPhoto;
	}
	
	public void setStudentPhoto(FormFile studentPhoto) {
		this.studentPhoto = studentPhoto;
	}
	
	public Boolean getIsUpdated() {
		return isUpdated;
	}
	
	public void setIsUpdated(Boolean isUpdated) {
		this.isUpdated = isUpdated;
	}
	
	public Map<Integer, String> getMbaEntranceExamMap() {
		return mbaEntranceExamMap;
	}
	
	public void setMbaEntranceExamMap(Map<Integer, String> mbaEntranceExamMap) {
		this.mbaEntranceExamMap = mbaEntranceExamMap;
	}

	public boolean isByPass() {
		return isByPass;
	}

	public void setByPass(boolean isByPass) {
		this.isByPass = isByPass;
	}

	public String getRecommedationApplicationNo() {
		return recommedationApplicationNo;
	}

	public void setRecommedationApplicationNo(String recommedationApplicationNo) {
		this.recommedationApplicationNo = recommedationApplicationNo;
	}

	public boolean isCourseListed() {
		return courseListed;
	}

	public void setCourseListed(boolean courseListed) {
		this.courseListed = courseListed;
	}

	public List<coursesToPrint> getCoursesList() {
		return coursesList;
	}

	public void setCoursesList(List<coursesToPrint> coursesList) {
		this.coursesList = coursesList;
	}

	public boolean isAidedCourse() {
		return aidedCourse;
	}

	public void setAidedCourse(boolean aidedCourse) {
		this.aidedCourse = aidedCourse;
	}
	
	public String getAuthzCode() {
		return authzCode;
	}

	public void setAuthzCode(String authzCode) {
		this.authzCode = authzCode;
	}

	public String getResponceCode() {
		return responceCode;
	}

	public void setResponceCode(String responceCode) {
		this.responceCode = responceCode;
	}

	public String getStatusDes() {
		return statusDes;
	}

	public void setStatusDes(String statusDes) {
		this.statusDes = statusDes;
	}

	public String getIsCommuQuota() {
		return isCommuQuota;
	}

	public void setIsCommuQuota(String isCommuQuota) {
		this.isCommuQuota = isCommuQuota;
	}

	public String getMngQuota() {
		return mngQuota;
	}

	public void setMngQuota(String mngQuota) {
		this.mngQuota = mngQuota;
	}

	public boolean isPhotoEdited() {
		return isPhotoEdited;
	}

	public void setPhotoEdited(boolean isPhotoEdited) {
		this.isPhotoEdited = isPhotoEdited;
	}

	public boolean isPDataEdited() {
		return isPDataEdited;
	}

	public void setPDataEdited(boolean isPDataEdited) {
		this.isPDataEdited = isPDataEdited;
	}
	
	 
}
