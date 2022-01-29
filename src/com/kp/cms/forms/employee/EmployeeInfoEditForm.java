package com.kp.cms.forms.employee;

	import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.bo.employee.EditMyProfile;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EmpAcheivementTO;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.admin.EmpLeaveTypeTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.employee.EligibilityTestTO;
import com.kp.cms.to.employee.EmpLeaveAllotTO;
import com.kp.cms.to.employee.EmpTypeTo;
import com.kp.cms.to.employee.EmployeeInfoEditTO;
import com.kp.cms.to.employee.EmployeeStreamTO;
	
	public class EmployeeInfoEditForm  extends BaseActionForm{
		
		private static final long serialVersionUID = 1L;
		private String employeeId;
		private String tempEmployeeId;
		private String homePhone1;
		private String homePhone2;
		private String homePhone3;
		private String mobPhone1;
		private String mobPhone2;
		private String mobPhone3;
		private String panno;
		private String religion;
		private String bloodGroup;
		private byte[] photo;
		private byte[] photoBytes;
		
		
		private String mobile;
		
		private String telNo;
		private String expectedSalaryLack;
		private String passportCountryId;
		private String visaCountryId;
		private String payScaleId;
		private String nationalityId;
		private String religionId;
		private String countryId;
		private String empJobTypeId;
		private String departmentId;
		private String stateId;
		private String empEducationDetailsId;
		private String empAcheivementId;
		private String emptypeId;
		private String streamId;
		private String workLocationId;
		private String reportToId;
		
		private String name;
		private String addressLine1;
		private String addressLine2;
		private String addressLine3;
		private String code;
		private String gender;
		private String maritalStatus;
		private String city;
		private String dateOfBirth;
		private String workPhNo1;
		private String workPhNo2;
		private String workPhNo3;
		private String age;
		private String mobileNo1;
		private String mobileNo2;
		private String mobileNo3;
		private String email;
		private String employmentStatus;
		private String expectedSalaryLakhs;
		private String expectedSalaryThousands;
		private String desiredPost;
		private String dateOfJoining;
		private String informationKnown;
		private String recommendedBy;
		private FormFile empPhoto;
		
		private String bankAccNo;
		private String pfNo;
		private String vehicleNo;
		private String twoWheelerNo;
		private String fourWheelerNo;
		

		private String status;
		
		private String vacancyInformation;
		private String designationId;
		private String jobCode;
		private String currentAddressLine1;
		private String currentAddressLine2;
		private String currentAddressLine3;
		private String currentCity;
		private String currentState;
		private String currentCountryId;
		private String permanentZipCode;
		private String currentZipCode;
		private String reservationCategory;
		
		private String empQualificationLevelId;
		private String emJobTypeId;
		private String[] eligibilityTest;
		private String noOfPublicationsRefered;
		private String noOfPublicationsNotRefered;
		private String books;
		private String otherInfo; 
		private String applicationNO;
		private String empSubjectAreaId;
		
		
		private Map<String,String> designationMap;
		private Map<String,String> departmentMap;
		private Map<String,String> countryMap;
		private Map<String,String> stateMap;
		private Map<String,String> nationalityMap;
		private Map<String,String> qualificationLevelMap;
		private Map<String,String> qualificationFixedMap;
		private Map<String,String> qualificationMap;
		private Map<String,String> subjectAreaMap;
		private Map<String,String> lakhsAndThousands;
		private Map<String,String> religionMap;
		private Map<String,String> empTypeMap;
		private Map<String,String> streamMap;
		private Map<String,String> workLocationMap;
		private Map<String,String> reportToMap;
		private Map<String,String> payScaleMap;
		private Map<String,String> passportCountryMap;
		private Map<String,String> visaCountryMap;
		private Map<String,String> titleMap;
		private String sameAddress;
		
		private String currentlyWorking;
		private String designationPfId;
		private String orgAddress;
		
		private boolean adminUser;
		private String mode;
		private String jobType;
		private String yearComp;
		private String appliedYear;
		private FormFile file;
		private Map<String,String> postAppliedMap;
		private Map<String,String> currentCountryMap;
		private Map<String,String> currentStateMap;
		private String teachingExpLength;
		private String industryExpLength;
		private String currentStateOthers;
		private String tempState;
		private String tempPermanentState;
		private Map<String,String> jobTypeMap;
		private String qualificationId;
		private String expYears;
		private String expMonths;
		private EmployeeInfoEditTO employeeInfoTONew;
	    private String teachingStaff;
		private String officialEmail;
		
		private List<EmpLeaveTypeTO> leaveTypeTOs;
		private List<EmpLeaveTO> leaveTOs;
		private List<EmpAcheivementTO> achievementTOs;
		private List<EmpAcheivementTO> newAchievementTOs;
		
		private List<EmpTypeTo> empTypeTo;
		private List<EmpLeaveAllotTO> empLeaveAllotTo;
		
		private List<EmployeeTO> reportingTos;
		private List<EmployeeTO> employeeToList;
		private List<EmployeeStreamTO> streamTO;
		//private List<EmployeeInfoEditTO> employeeToList;

		private boolean employeeFound;
		
		private EmployeeTO employeeDetail;
		private String rejoinDate;
		private String retirementDate;
		private String superAnnuationDate;
		private String active;
		private String grade;
		private String scale;
		private String hra;
		private String da;
		private String grossPay; 
		private String basicPay;
		private String dependantName;
		private String dependantRelationShip;
		private String dependantDOB;
		private String emContactRelationship;
		private String emContactName;
		private String emContactHomeTel;
		private String emContactWorkTel;
		private String emContactMobile;
		private String passportNo;
		private String passportIssueDate;
		private String passportStatus;
		private String passportExpiryDate;
		private String passportReviewStatus;
		private String passportComments;
		
		private String visaNo;
		private String visaIssueDate;
		private String visaStatus;
		private String visaExpiryDate;
		private String visaReviewStatus;
		private String visaComments;
		private String startingTimeHours;
		private String startingTimeMins;
		private String endingTimeHours;
		private String endingTimeMins;
		private String satEndingTimeMins;
		private String satEndingTimeHours;
		private String startTimeInEndsHours;
		private String startTimeInEndsMins;
		private String reasonOfLeaving;
		private String dateOfResignation;
		private String dateOfLeaving;
		private String acheivementName;
		private String details;
		private String loanDate;
		private String loanAmount;
		private String loanDetails;
		private String financialDate;
		private String financialAmount;
		private String financialDetails;
		private String incentivesDate;
		private String incentivesAmount;
		private String incentivesDetails;
		private String feeConcessionDate;
		private String feeConcessionAmount;
		private String feeConcessionDetails;
		private String remarkDate;
		private String enteredBy;
		private String eligibilityTestNET;
		private String eligibilityTestSLET;
		private String eligibilityTestSET;
		private String eligibilityTestNone;
		//code added by sudhir
		private String teaching;
		private String industry;
		private String displayInWebsite;
		private String researchPapersRefereed;
		private String researchPapersNonRefereed;
		private String researchPapersProceedings;
		private String nationalPublications;
		private String internationalPublications;
		private String localPublications;
		private String international;
		private String national;
		private String majorProjects;
		private String minorProjects;
		private String consultancyPrjects1;
		private String consultancyProjects2;
		private String phdResearchGuidance;
		private String mphilResearchGuidance;
		private String fdp1Training;
		private String fdp2Training;
		private String regionalConference;
		private String internationalConference;
		private String nationalConference;
		private String localConference;
		private String additionalRemarks;
		private String refresherCourse;
		private String orientationCourse;
		
		public String getDisplayInWebsite() {
			return displayInWebsite;
		}
		public void setDisplayInWebsite(String displayInWebsite) {
			this.displayInWebsite = displayInWebsite;
		}
		public String getEnteredBy() {
			return enteredBy;
		}
		public void setEnteredBy(String enteredBy) {
			this.enteredBy = enteredBy;
		}
		private String remarkDetails;
		private String timeIn;
		private String timeInEnds;
		private String timeOut;
		private String saturdayTimeOut;
		private String halfDayStartTime;
		private String halfDayEndTime;
		private String timeInMin;
		private String timeInEndMIn;
		private String timeOutMin;
		private String saturdayTimeOutMin;
		private String halfDayStartTimeMin;
		private String halfDayEndTimeMin;
		private String uId;
		private String fingerPrintId;
		private String loanListSize;
		private String remarksListSize;
		private String feeListSize;
		private String financialListSize;
		private String dependantsListSize;
		private String immigrationListSize;
		private String incentivesListSize;
		private String achievementListSize;
		private String levelSize;
		private String designation;
		private String highQualifForAlbum;
		private String relevantExpMonths;
		private String relevantExpYears;
		private String otherPermanentState;
		private String otherCurrentState;
		private String titleId;
		private String reservationCategory1;
		private String isSmartCardDataGenerated;
		private String isSmartCardDataDelivered;
		private String smartCardNo;
		
		
		private String tempUid;
		private String tempFingerPrintId;
		private String tempName;
		private String tempCode;
		private String tempDesignationPfId;
		private String tempDepartmentId;
		private Map<String,String> tempDesignationMap;
		private Map<String,String> tempDepartmentMap;
		private String tempDesignationName;
		private String tempDepartmentName;
		private Map<String,String> tempStreamMap;
		private String tempStreamId;
		private String tempStreamName;
		private String selectedEmployeeId;
		private String tempActive;
		private String tempTeachingStaff;
		private String isPunchingExcemption;
		private String empTypeIdOld;
		private String empLeaveInitOldMonth;
		
		private String empLeaveInitNewMonth;
		private EditMyProfile editMyProfile;
		private String tempEmptypeId;
		private Map<String,String> tempEmpTypeMap;
		private String empImageId;
		private String focusValue;
		private String listSize;
		
		private String otherEligibilityTestValue;
		private String eligibilityTestOther;
		private String industryFunctionalArea;
		private String handicappedDescription;
		private String emContactAddress;
		private List<EligibilityTestTO> eligibilityList;
		private Boolean isCjc;
		private String albumDesignation;
		private int currentExpYears;
		private int currentExpMonths;
		private int totalCurrentExpYears;
		private int totalCurrentExpMonths;
		/*added by sudhir */
		private String extensionNumber;
		private String licGratuityNo;
	    private String licGratuityDate;
	    private String nomineePfNo;
	    private String nomineePfDate;
	    private String nameAdressNominee;
		private String memberRelationship;
		private String dobMember;
		private String share;
		private String nameAdressGuardian;
		private String pfGratuityListSize;
		private String fatherName;
		private String motherName;
		private String deputationDepartmentId;
		private String appointmentLetterDate;
		private String referenceNumberForAppointment;
		private String referenceNumberForReleaving;
		private String releavingOrderDate;
		private Map<String, String> deputationDepartmentMap;
		private String isDirectpage;
		private String titleOthers;
		private String qualificationOthers;
		private String highestQualification;
	    
		public void reset(){
			albumDesignation= null;
			eligibilityList = null;
			selectedEmployeeId=null;
			age=null;
			employeeId=null;
			otherPermanentState=null;
			otherCurrentState=null;
			relevantExpMonths="0";
			relevantExpYears="0";
			highQualifForAlbum=null;
			designation=null;
			feeListSize=null;
		    financialListSize=null;
			dependantsListSize=null;
			immigrationListSize=null;
			incentivesListSize=null;
		    achievementListSize=null;
			levelSize=null;
			code=null;
			fingerPrintId= null;
			uId=null;
			remarkDetails= null;
			enteredBy= null;
			remarkDate= null;
			incentivesDetails= null;
			incentivesAmount= null;
			incentivesDate= null;
			feeConcessionDate= null;
			feeConcessionAmount= null;
			feeConcessionDetails= null;
			loanDate= null;
			loanAmount= null;
			loanDetails= null;
			financialDate= null;
			financialAmount= null;
			financialDetails= null;
			acheivementName= null;
			details= null;
			dateOfLeaving= null;
			dateOfResignation= null;
			reasonOfLeaving= null;
			endingTimeHours= null;
			endingTimeMins= null;
			satEndingTimeMins= null;
			satEndingTimeHours= null;
			startTimeInEndsHours= null;
			passportComments= null;
			passportReviewStatus= null;
			passportExpiryDate= null;
			passportIssueDate= null;
			passportStatus= null;
			passportNo= null;
			emContactWorkTel=null;
			emContactHomeTel=null;
			emContactName=null;
			emContactMobile=null;
			emContactRelationship=null;
			dependantName=null;
			dependantRelationShip=null;
			dependantDOB=null;
			scale=null;
			hra=null;
			da=null;
			grossPay=null;
			basicPay=null;
			nationalityId=null;
			countryId=null;
			empJobTypeId=null;
			departmentId=null;
			stateId=null;
			empEducationDetailsId=null;
			empAcheivementId=null;
			emptypeId=null;
			

			name=null;
			addressLine1=null;
			addressLine2=null;
			addressLine3=null;
			code=null;
			gender=null;
			maritalStatus=null;
			city=null;
			dateOfBirth=null;
			
			age=null;
			mobileNo1=null;
			mobileNo2=null;
			mobileNo3=null;
			email=null;
			employmentStatus=null;
			expectedSalaryLakhs=null;
			expectedSalaryThousands=null;
			desiredPost=null;
			dateOfJoining=null;
			informationKnown=null;
			recommendedBy=null;
			empPhoto=null;
			
			status=null;
			
			vacancyInformation=null;
			designationId=null;
			jobCode=null;
			currentAddressLine1=null;
			currentAddressLine2=null;
			currentAddressLine3=null;
			currentCity=null;
			currentState=null;
			currentCountryId=null;
			permanentZipCode=null;
			currentZipCode=null;
			reservationCategory=null;
			empQualificationLevelId=null;
			emJobTypeId=null;
			eligibilityTest=null;
			noOfPublicationsRefered="0";
			noOfPublicationsNotRefered="0";
			books="0";
			otherInfo=null; 
			applicationNO=null;
			empSubjectAreaId=null;
			
			sameAddress="true";
			currentlyWorking="YES";
			designationPfId=null;
			orgAddress=null;
			mode=null;
			jobType=null;
			yearComp=null;
			appliedYear=null;
			file=null;
			qualificationId=null;
			expYears="0";
			expMonths="0";
			qualificationLevelMap=null;
			reservationCategory=null;
			extensionNumber=null;
//			employeeInfoTONew=new EmployeeInfoTONew();
			licGratuityNo=null;
		    licGratuityDate=null;
		    nomineePfNo=null;
		    nomineePfDate=null;
		    nameAdressNominee=null;
			memberRelationship=null;
			dobMember=null;
			share=null;
			nameAdressGuardian=null;
			pfGratuityListSize=null;
			deputationDepartmentId=null;
			appointmentLetterDate=null;
			referenceNumberForReleaving=null;
			releavingOrderDate=null;
			
		}
		public EmployeeInfoEditForm() {
		    this.employeeInfoTONew=new EmployeeInfoEditTO();
		}
		
		public String getNationalityId() {
			return nationalityId;
		}
		public String getCountryId() {
			return countryId;
		}
		public String getEmpJobTypeId() {
			return empJobTypeId;
		}
		public String getDepartmentId() {
			return departmentId;
		}
		public String getStateId() {
			return stateId;
		}
		public String getEmpEducationDetailsId() {
			return empEducationDetailsId;
		}
		public String getEmpAcheivementId() {
			return empAcheivementId;
		}
		
		public String getName() {
			return name;
		}
		public String getAddressLine1() {
			return addressLine1;
		}
		public String getAddressLine2() {
			return addressLine2;
		}
		public String getAddressLine3() {
			return addressLine3;
		}
		public String getCode() {
			return code;
		}
		public String getGender() {
			return gender;
		}
		public String getMaritalStatus() {
			return maritalStatus;
		}
		public String getCity() {
			return city;
		}
		public String getDateOfBirth() {
			return dateOfBirth;
		}
		
		public String getAge() {
			return age;
		}
		public String getMobileNo1() {
			return mobileNo1;
		}
		public String getMobileNo2() {
			return mobileNo2;
		}
		public String getMobileNo3() {
			return mobileNo3;
		}
		public String getEmail() {
			return email;
		}
		public String getEmploymentStatus() {
			return employmentStatus;
		}
		public String getExpectedSalaryLakhs() {
			return expectedSalaryLakhs;
		}
		public String getExpectedSalaryThousands() {
			return expectedSalaryThousands;
		}
		public String getDesiredPost() {
			return desiredPost;
		}
		public String getDateOfJoining() {
			return dateOfJoining;
		}
		public String getInformationKnown() {
			return informationKnown;
		}
		public String getRecommendedBy() {
			return recommendedBy;
		}
		public FormFile getEmpPhoto() {
			return empPhoto;
		}
		public String getStatus() {
			return status;
		}
		public String getVacancyInformation() {
			return vacancyInformation;
		}
		public String getDesignationId() {
			return designationId;
		}
		public String getJobCode() {
			return jobCode;
		}
		public String getCurrentAddressLine1() {
			return currentAddressLine1;
		}
		public String getCurrentAddressLine2() {
			return currentAddressLine2;
		}
		public String getCurrentAddressLine3() {
			return currentAddressLine3;
		}
		public String getCurrentCity() {
			return currentCity;
		}
		public String getCurrentState() {
			return currentState;
		}
		public String getCurrentCountryId() {
			return currentCountryId;
		}
		public String getPermanentZipCode() {
			return permanentZipCode;
		}
		public String getCurrentZipCode() {
			return currentZipCode;
		}
		
		public String getEmpQualificationLevelId() {
			return empQualificationLevelId;
		}
		public String getEmJobTypeId() {
			return emJobTypeId;
		}
		public String[] getEligibilityTest() {
			return eligibilityTest;
		}
		public String getNoOfPublicationsRefered() {
			return noOfPublicationsRefered;
		}
		public String getNoOfPublicationsNotRefered() {
			return noOfPublicationsNotRefered;
		}
		public String getBooks() {
			return books;
		}
		public String getOtherInfo() {
			return otherInfo;
		}
		public String getApplicationNO() {
			return applicationNO;
		}
		public String getEmpSubjectAreaId() {
			return empSubjectAreaId;
		}
		public void setNationalityId(String nationalityId) {
			this.nationalityId = nationalityId;
		}
		public void setCountryId(String countryId) {
			this.countryId = countryId;
		}
		public void setEmpJobTypeId(String empJobTypeId) {
			this.empJobTypeId = empJobTypeId;
		}
		public void setDepartmentId(String departmentId) {
			this.departmentId = departmentId;
		}
		public void setStateId(String stateId) {
			this.stateId = stateId;
		}
		public void setEmpEducationDetailsId(String empEducationDetailsId) {
			this.empEducationDetailsId = empEducationDetailsId;
		}
		public void setEmpAcheivementId(String empAcheivementId) {
			this.empAcheivementId = empAcheivementId;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		public void setAddressLine1(String addressLine1) {
			this.addressLine1 = addressLine1;
		}
		public void setAddressLine2(String addressLine2) {
			this.addressLine2 = addressLine2;
		}
		public void setAddressLine3(String addressLine3) {
			this.addressLine3 = addressLine3;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public void setMaritalStatus(String maritalStatus) {
			this.maritalStatus = maritalStatus;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public void setDateOfBirth(String dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
		}
		
		public void setAge(String age) {
			this.age = age;
		}
		public void setMobileNo1(String mobileNo1) {
			this.mobileNo1 = mobileNo1;
		}
		public void setMobileNo2(String mobileNo2) {
			this.mobileNo2 = mobileNo2;
		}
		public void setMobileNo3(String mobileNo3) {
			this.mobileNo3 = mobileNo3;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public void setEmploymentStatus(String employmentStatus) {
			this.employmentStatus = employmentStatus;
		}
		public void setExpectedSalaryLakhs(String expectedSalaryLakhs) {
			this.expectedSalaryLakhs = expectedSalaryLakhs;
		}
		public void setExpectedSalaryThousands(String expectedSalaryThousands) {
			this.expectedSalaryThousands = expectedSalaryThousands;
		}
		public void setDesiredPost(String desiredPost) {
			this.desiredPost = desiredPost;
		}
		public void setDateOfJoining(String dateOfJoining) {
			this.dateOfJoining = dateOfJoining;
		}
		public void setInformationKnown(String informationKnown) {
			this.informationKnown = informationKnown;
		}
		public void setRecommendedBy(String recommendedBy) {
			this.recommendedBy = recommendedBy;
		}
		public void setEmpPhoto(FormFile empPhoto) {
			this.empPhoto = empPhoto;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public void setVacancyInformation(String vacancyInformation) {
			this.vacancyInformation = vacancyInformation;
		}
		public void setDesignationId(String designationId) {
			this.designationId = designationId;
		}
		public void setJobCode(String jobCode) {
			this.jobCode = jobCode;
		}
		public void setCurrentAddressLine1(String currentAddressLine1) {
			this.currentAddressLine1 = currentAddressLine1;
		}
		public void setCurrentAddressLine2(String currentAddressLine2) {
			this.currentAddressLine2 = currentAddressLine2;
		}
		public void setCurrentAddressLine3(String currentAddressLine3) {
			this.currentAddressLine3 = currentAddressLine3;
		}
		public void setCurrentCity(String currentCity) {
			this.currentCity = currentCity;
		}
		public void setCurrentState(String currentState) {
			this.currentState = currentState;
		}
		public void setCurrentCountryId(String currentCountryId) {
			this.currentCountryId = currentCountryId;
		}
		public void setPermanentZipCode(String permanentZipCode) {
			this.permanentZipCode = permanentZipCode;
		}
		public void setCurrentZipCode(String currentZipCode) {
			this.currentZipCode = currentZipCode;
		}
		
		public void setEmpQualificationLevelId(String empQualificationLevelId) {
			this.empQualificationLevelId = empQualificationLevelId;
		}
		public void setEmJobTypeId(String emJobTypeId) {
			this.emJobTypeId = emJobTypeId;
		}
		public void setEligibilityTest(String[] eligibilityTest) {
			this.eligibilityTest = eligibilityTest;
		}
		public void setNoOfPublicationsRefered(String noOfPublicationsRefered) {
			this.noOfPublicationsRefered = noOfPublicationsRefered;
		}
		public void setNoOfPublicationsNotRefered(String noOfPublicationsNotRefered) {
			this.noOfPublicationsNotRefered = noOfPublicationsNotRefered;
		}
		public void setBooks(String books) {
			this.books = books;
		}
		public void setOtherInfo(String otherInfo) {
			this.otherInfo = otherInfo;
		}
		public void setApplicationNO(String applicationNO) {
			this.applicationNO = applicationNO;
		}
		public void setEmpSubjectAreaId(String empSubjectAreaId) {
			this.empSubjectAreaId = empSubjectAreaId;
		}
		public Map<String, String> getDesignationMap() {
			return designationMap;
		}
		public Map<String, String> getDepartmentMap() {
			return departmentMap;
		}
		public Map<String, String> getCountryMap() {
			return countryMap;
		}
		public Map<String, String> getStateMap() {
			return stateMap;
		}
		public Map<String, String> getNationalityMap() {
			return nationalityMap;
		}
		public Map<String, String> getQualificationLevelMap() {
			return qualificationLevelMap;
		}
		public void setDesignationMap(Map<String, String> designationMap) {
			this.designationMap = designationMap;
		}
		public void setDepartmentMap(Map<String, String> departmentMap) {
			this.departmentMap = departmentMap;
		}
		public void setCountryMap(Map<String, String> countryMap) {
			this.countryMap = countryMap;
		}
		public void setStateMap(Map<String, String> stateMap) {
			this.stateMap = stateMap;
		}
		public void setNationalityMap(Map<String, String> nationalityMap) {
			this.nationalityMap = nationalityMap;
		}
		public void setQualificationLevelMap(Map<String, String> qualificationLevelMap) {
			this.qualificationLevelMap = qualificationLevelMap;
		}
		
		
		public String getDesignationPfId() {
			return designationPfId;
		}
		public void setDesignationPfId(String designationPfId) {
			this.designationPfId = designationPfId;
		}
		public String getOrgAddress() {
			return orgAddress;
		}
		public void setOrgAddress(String orgAddress) {
			this.orgAddress = orgAddress;
		}
		
		public boolean isAdminUser() {
			return adminUser;
		}
		
		public void setAdminUser(boolean adminUser) {
			this.adminUser = adminUser;
		}
		public String getMode() {
			return mode;
		}
		public void setMode(String mode) {
			this.mode = mode;
		}
		
		public Map<String, String> getSubjectAreaMap() {
			return subjectAreaMap;
		}

		public void setSubjectAreaMap(Map<String, String> subjectAreaMap) {
			this.subjectAreaMap = subjectAreaMap;
		}

		public String getJobType() {
			return jobType;
		}

		public void setJobType(String jobType) {
			this.jobType = jobType;
		}


		public String getYearComp() {
			return yearComp;
		}

		public void setYearComp(String yearComp) {
			this.yearComp = yearComp;
		}

		public String getAppliedYear() {
			return appliedYear;
		}

		public void setAppliedYear(String appliedYear) {
			this.appliedYear = appliedYear;
		}

		public Map<String, String> getQualificationFixedMap() {
			return qualificationFixedMap;
		}

		public void setQualificationFixedMap(Map<String, String> qualificationFixedMap) {
			this.qualificationFixedMap = qualificationFixedMap;
		}

		public FormFile getFile() {
			return file;
		}

		public void setFile(FormFile file) {
			this.file = file;
		}

		public Map<String, String> getPostAppliedMap() {
			return postAppliedMap;
		}

		public Map<String, String> getCurrentCountryMap() {
			return currentCountryMap;
		}

		public Map<String, String> getCurrentStateMap() {
			return currentStateMap;
		}

		public void setPostAppliedMap(Map<String, String> postAppliedMap) {
			this.postAppliedMap = postAppliedMap;
		}

		public void setCurrentCountryMap(Map<String, String> currentCountryMap) {
			this.currentCountryMap = currentCountryMap;
		}

		public void setCurrentStateMap(Map<String, String> currenttateMap) {
			this.currentStateMap = currenttateMap;
		}

		public Map<String, String> getJobTypeMap() {
			return jobTypeMap;
		}

		public void setJobTypeMap(Map<String, String> jobTypeMap) {
			this.jobTypeMap = jobTypeMap;
		}

		public Map<String, String> getLakhsAndThousands() {
			return lakhsAndThousands;
		}

		public void setLakhsAndThousands(Map<String, String> lakhsAndThousands) {
			this.lakhsAndThousands = lakhsAndThousands;
		}

		public Map<String, String> getQualificationMap() {
			return qualificationMap;
		}
		public void setQualificationMap(Map<String, String> qualificationMap) {
			this.qualificationMap = qualificationMap;
		}
		public String getQualificationId() {
			return qualificationId;
		}
		public void setQualificationId(String qualificationId) {
			this.qualificationId = qualificationId;
		}
		public String getExpYears() {
			return expYears;
		}
		public String getExpMonths() {
			return expMonths;
		}
		public void setExpYears(String expYears) {
			this.expYears = expYears;
		}
		public void setExpMonths(String expMonths) {
			this.expMonths = expMonths;
		}
		/* (non-Javadoc)
		 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
		 */
		public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) {
			String formName = request.getParameter("formName");
			ActionErrors actionErrors = super.validate(mapping, request, formName);
			return actionErrors;
		}
		
		
		public String getMobPhone1() {
			return mobPhone1;
		}
		public void setMobPhone1(String mobPhone1) {
			this.mobPhone1 = mobPhone1;
		}
		public String getMobPhone2() {
			return mobPhone2;
		}
		public void setMobPhone2(String mobPhone2) {
			this.mobPhone2 = mobPhone2;
		}
		public String getMobPhone3() {
			return mobPhone3;
		}
		public void setMobPhone3(String mobPhone3) {
			this.mobPhone3 = mobPhone3;
		}
		public String getPanno() {
			return panno;
		}
		public void setPanno(String panno) {
			this.panno = panno;
		}
		public String getReligion() {
			return religion;
		}
		public void setReligion(String religion) {
			this.religion = religion;
		}
		public String getBloodGroup() {
			return bloodGroup;
		}
		public void setBloodGroup(String bloodGroup) {
			this.bloodGroup = bloodGroup;
		}
		
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getVehicleNo() {
			return vehicleNo;
		}
		public void setVehicleNo(String vehicleNo) {
			this.vehicleNo = vehicleNo;
		}
		
		
		
		
		public String getBankAccNo() {
			return bankAccNo;
		}
		public void setBankAccNo(String bankAccNo) {
			this.bankAccNo = bankAccNo;
		}
		public String getTelNo() {
			return telNo;
		}
		public void setTelNo(String telNo) {
			this.telNo = telNo;
		}
		public String getExpectedSalaryLack() {
			return expectedSalaryLack;
		}
		public void setExpectedSalaryLack(String expectedSalaryLack) {
			this.expectedSalaryLack = expectedSalaryLack;
		}
		
		
		public String getTeachingExpLength() {
			return teachingExpLength;
		}
		public void setTeachingExpLength(String teachingExpLength) {
			this.teachingExpLength = teachingExpLength;
		}
		public String getIndustryExpLength() {
			return industryExpLength;
		}
		public void setIndustryExpLength(String industryExpLength) {
			this.industryExpLength = industryExpLength;
		}
		public String getCurrentStateOthers() {
			return currentStateOthers;
		}
		public void setCurrentStateOthers(String currentStateOthers) {
			this.currentStateOthers = currentStateOthers;
		}
		public String getTempState() {
			return tempState;
		}
		public void setTempState(String tempState) {
			this.tempState = tempState;
		}
		public String getTempPermanentState() {
			return tempPermanentState;
		}
		public void setTempPermanentState(String tempPermanentState) {
			this.tempPermanentState = tempPermanentState;
		}
		public String getTeachingStaff() {
			return teachingStaff;
		}
		public void setTeachingStaff(String teachingStaff) {
			this.teachingStaff = teachingStaff;
		}
		public String getReligionId() {
			return religionId;
		}
		public void setReligionId(String religionId) {
			this.religionId = religionId;
		}
		public Map<String, String> getReligionMap() {
			return religionMap;
		}
		public void setReligionMap(Map<String, String> religionMap) {
			this.religionMap = religionMap;
		}
		public String getOfficialEmail() {
			return officialEmail;
		}
		public void setOfficialEmail(String officialEmail) {
			this.officialEmail = officialEmail;
		}
		public String getPfNo() {
			return pfNo;
		}
		public void setPfNo(String pfNo) {
			this.pfNo = pfNo;
		}
		public String getTwoWheelerNo() {
			return twoWheelerNo;
		}
		public void setTwoWheelerNo(String twoWheelerNo) {
			this.twoWheelerNo = twoWheelerNo;
		}
		public String getFourWheelerNo() {
			return fourWheelerNo;
		}
		public void setFourWheelerNo(String fourWheelerNo) {
			this.fourWheelerNo = fourWheelerNo;
		}
		public List<EmpLeaveTypeTO> getLeaveTypeTOs() {
			return leaveTypeTOs;
		}
		public void setLeaveTypeTOs(List<EmpLeaveTypeTO> leaveTypeTOs) {
			this.leaveTypeTOs = leaveTypeTOs;
		}
		public List<EmpLeaveTO> getLeaveTOs() {
			return leaveTOs;
		}
		public void setLeaveTOs(List<EmpLeaveTO> leaveTOs) {
			this.leaveTOs = leaveTOs;
		}
		public List<EmpAcheivementTO> getAchievementTOs() {
			return achievementTOs;
		}
		public void setAchievementTOs(List<EmpAcheivementTO> achievementTOs) {
			this.achievementTOs = achievementTOs;
		}
		public List<EmpAcheivementTO> getNewAchievementTOs() {
			return newAchievementTOs;
		}
		public void setNewAchievementTOs(List<EmpAcheivementTO> newAchievementTOs) {
			this.newAchievementTOs = newAchievementTOs;
		}
		public List<EmployeeTO> getReportingTos() {
			return reportingTos;
		}
		public void setReportingTos(List<EmployeeTO> reportingTos) {
			this.reportingTos = reportingTos;
		}
		public List<EmployeeStreamTO> getStreamTO() {
			return streamTO;
		}
		public void setStreamTO(List<EmployeeStreamTO> streamTO) {
			this.streamTO = streamTO;
		}
		public boolean isEmployeeFound() {
			return employeeFound;
		}
		public void setEmployeeFound(boolean employeeFound) {
			this.employeeFound = employeeFound;
		}
		public EmployeeTO getEmployeeDetail() {
			return employeeDetail;
		}
		public void setEmployeeDetail(EmployeeTO employeeDetail) {
			this.employeeDetail = employeeDetail;
		}
		public String getEmptypeId() {
			return emptypeId;
		}
		public void setEmptypeId(String emptypeId) {
			this.emptypeId = emptypeId;
		}
		public Map<String, String> getEmpTypeMap() {
			return empTypeMap;
		}
		public void setEmpTypeMap(Map<String, String> empTypeMap) {
			this.empTypeMap = empTypeMap;
		}
		public String getRejoinDate() {
			return rejoinDate;
		}
		public void setRejoinDate(String rejoinDate) {
			this.rejoinDate = rejoinDate;
		}
		public String getRetirementDate() {
			return retirementDate;
		}
		public void setRetirementDate(String retirementDate) {
			this.retirementDate = retirementDate;
		}
		public String getSuperAnnuationDate() {
			return superAnnuationDate;
		}
		public void setSuperAnnuationDate(String superAnnuationDate) {
			this.superAnnuationDate = superAnnuationDate;
		}
		public String getActive() {
			return active;
		}
		public void setActive(String active) {
			this.active = active;
		}
		public Map<String, String> getStreamMap() {
			return streamMap;
		}
		public void setStreamMap(Map<String, String> streamMap) {
			this.streamMap = streamMap;
		}
		public String getStreamId() {
			return streamId;
		}
		public void setStreamId(String streamId) {
			this.streamId = streamId;
		}
		public Map<String, String> getWorkLocationMap() {
			return workLocationMap;
		}
		public void setWorkLocationMap(Map<String, String> workLocationMap) {
			this.workLocationMap = workLocationMap;
		}
		public String getWorkLocationId() {
			return workLocationId;
		}
		public void setWorkLocationId(String workLocationId) {
			this.workLocationId = workLocationId;
		}
		public String getReportToId() {
			return reportToId;
		}
		public void setReportToId(String reportToId) {
			this.reportToId = reportToId;
		}
		
		public String getGrade() {
			return grade;
		}
		public void setGrade(String grade) {
			this.grade = grade;
		}
		public Map<String, String> getReportToMap() {
			return reportToMap;
		}
		public void setReportToMap(Map<String, String> reportToMap) {
			this.reportToMap = reportToMap;
		}
		
		public Map<String, String> getPayScaleMap() {
			return payScaleMap;
		}
		public void setPayScaleMap(Map<String, String> payScaleMap) {
			this.payScaleMap = payScaleMap;
		}
		public String getPayScaleId() {
			return payScaleId;
		}
		public void setPayScaleId(String payScaleId) {
			this.payScaleId = payScaleId;
		}
		public String getScale() {
			return scale;
		}
		public void setScale(String scale) {
			this.scale = scale;
		}
		public String getHra() {
			return hra;
		}
		public void setHra(String hra) {
			this.hra = hra;
		}
		public String getDa() {
			return da;
		}
		public void setDa(String da) {
			this.da = da;
		}
		public String getGrossPay() {
			return grossPay;
		}
		public void setGrossPay(String grossPay) {
			this.grossPay = grossPay;
		}
		public String getBasicPay() {
			return basicPay;
		}
		public void setBasicPay(String basicPay) {
			this.basicPay = basicPay;
		}
		public String getDependantName() {
			return dependantName;
		}
		public void setDependantName(String dependantName) {
			this.dependantName = dependantName;
		}
		public String getDependantRelationShip() {
			return dependantRelationShip;
		}
		public void setDependantRelationShip(String dependantRelationShip) {
			this.dependantRelationShip = dependantRelationShip;
		}
		public String getDependantDOB() {
			return dependantDOB;
		}
		public void setDependantDOB(String dependantDOB) {
			this.dependantDOB = dependantDOB;
		}
		public String getEmContactRelationship() {
			return emContactRelationship;
		}
		public void setEmContactRelationship(String emContactRelationship) {
			this.emContactRelationship = emContactRelationship;
		}
		public String getEmContactName() {
			return emContactName;
		}
		public void setEmContactName(String emContactName) {
			this.emContactName = emContactName;
		}
		public String getEmContactHomeTel() {
			return emContactHomeTel;
		}
		public void setEmContactHomeTel(String emContactHomeTel) {
			this.emContactHomeTel = emContactHomeTel;
		}
		public String getEmContactWorkTel() {
			return emContactWorkTel;
		}
		public void setEmContactWorkTel(String emContactWorkTel) {
			this.emContactWorkTel = emContactWorkTel;
		}
		public String getEmContactMobile() {
			return emContactMobile;
		}
		public void setEmContactMobile(String emContactMobile) {
			this.emContactMobile = emContactMobile;
		}
		public String getPassportNo() {
			return passportNo;
		}
		public void setPassportNo(String passportNo) {
			this.passportNo = passportNo;
		}
		
		public String getPassportStatus() {
			return passportStatus;
		}
		public void setPassportStatus(String passportStatus) {
			this.passportStatus = passportStatus;
		}
		public String getPassportExpiryDate() {
			return passportExpiryDate;
		}
		public void setPassportExpiryDate(String passportExpiryDate) {
			this.passportExpiryDate = passportExpiryDate;
		}
		public String getPassportReviewStatus() {
			return passportReviewStatus;
		}
		public void setPassportReviewStatus(String passportReviewStatus) {
			this.passportReviewStatus = passportReviewStatus;
		}
		public String getPassportComments() {
			return passportComments;
		}
		public void setPassportComments(String passportComments) {
			this.passportComments = passportComments;
		}
		public String getPassportCountryId() {
			return passportCountryId;
		}
		public void setPassportCountryId(String passportCountryId) {
			this.passportCountryId = passportCountryId;
		}
		public Map<String, String> getPassportCountryMap() {
			return passportCountryMap;
		}
		public void setPassportCountryMap(Map<String, String> passportCountryMap) {
			this.passportCountryMap = passportCountryMap;
		}
		public String getVisaCountryId() {
			return visaCountryId;
		}
		public void setVisaCountryId(String visaCountryId) {
			this.visaCountryId = visaCountryId;
		}
		public Map<String, String> getVisaCountryMap() {
			return visaCountryMap;
		}
		public void setVisaCountryMap(Map<String, String> visaCountryMap) {
			this.visaCountryMap = visaCountryMap;
		}
		public String getVisaNo() {
			return visaNo;
		}
		public void setVisaNo(String visaNo) {
			this.visaNo = visaNo;
		}

		public String getVisaStatus() {
			return visaStatus;
		}
		public void setVisaStatus(String visaStatus) {
			this.visaStatus = visaStatus;
		}
		public String getVisaExpiryDate() {
			return visaExpiryDate;
		}
		public void setVisaExpiryDate(String visaExpiryDate) {
			this.visaExpiryDate = visaExpiryDate;
		}
		public String getVisaReviewStatus() {
			return visaReviewStatus;
		}
		public void setVisaReviewStatus(String visaReviewStatus) {
			this.visaReviewStatus = visaReviewStatus;
		}
		public String getVisaComments() {
			return visaComments;
		}
		public void setVisaComments(String visaComments) {
			this.visaComments = visaComments;
		}
		public String getStartingTimeHours() {
			return startingTimeHours;
		}
		public void setStartingTimeHours(String startingTimeHours) {
			this.startingTimeHours = startingTimeHours;
		}
		public String getStartingTimeMins() {
			return startingTimeMins;
		}
		public void setStartingTimeMins(String startingTimeMins) {
			this.startingTimeMins = startingTimeMins;
		}
		public String getEndingTimeHours() {
			return endingTimeHours;
		}
		public void setEndingTimeHours(String endingTimeHours) {
			this.endingTimeHours = endingTimeHours;
		}
		public String getEndingTimeMins() {
			return endingTimeMins;
		}
		public void setEndingTimeMins(String endingTimeMins) {
			this.endingTimeMins = endingTimeMins;
		}
		public String getSatEndingTimeMins() {
			return satEndingTimeMins;
		}
		public void setSatEndingTimeMins(String satEndingTimeMins) {
			this.satEndingTimeMins = satEndingTimeMins;
		}
		public String getSatEndingTimeHours() {
			return satEndingTimeHours;
		}
		public void setSatEndingTimeHours(String satEndingTimeHours) {
			this.satEndingTimeHours = satEndingTimeHours;
		}
		public String getReasonOfLeaving() {
			return reasonOfLeaving;
		}
		public void setReasonOfLeaving(String reasonOfLeaving) {
			this.reasonOfLeaving = reasonOfLeaving;
		}
		public String getDateOfResignation() {
			return dateOfResignation;
		}
		public void setDateOfResignation(String dateOfResignation) {
			this.dateOfResignation = dateOfResignation;
		}
		public String getDateOfLeaving() {
			return dateOfLeaving;
		}
		public void setDateOfLeaving(String dateOfLeaving) {
			this.dateOfLeaving = dateOfLeaving;
		}
		
		
		public String getLoanDate() {
			return loanDate;
		}
		public void setLoanDate(String loanDate) {
			this.loanDate = loanDate;
		}
		public String getLoanAmount() {
			return loanAmount;
		}
		public void setLoanAmount(String loanAmount) {
			this.loanAmount = loanAmount;
		}
		public String getLoanDetails() {
			return loanDetails;
		}
		public void setLoanDetails(String loanDetails) {
			this.loanDetails = loanDetails;
		}
		public String getFinancialDate() {
			return financialDate;
		}
		public void setFinancialDate(String financialDate) {
			this.financialDate = financialDate;
		}
		public String getFinancialAmount() {
			return financialAmount;
		}
		public void setFinancialAmount(String financialAmount) {
			this.financialAmount = financialAmount;
		}
		
		public String getIncentivesDate() {
			return incentivesDate;
		}
		public void setIncentivesDate(String incentivesDate) {
			this.incentivesDate = incentivesDate;
		}
		public String getIncentivesAmount() {
			return incentivesAmount;
		}
		public void setIncentivesAmount(String incentivesAmount) {
			this.incentivesAmount = incentivesAmount;
		}
		
		public String getFeeConcessionDate() {
			return feeConcessionDate;
		}
		public void setFeeConcessionDate(String feeConcessionDate) {
			this.feeConcessionDate = feeConcessionDate;
		}
		public String getFeeConcessionAmount() {
			return feeConcessionAmount;
		}
		public void setFeeConcessionAmount(String feeConcessionAmount) {
			this.feeConcessionAmount = feeConcessionAmount;
		}
		public String getRemarkDate() {
			return remarkDate;
		}
		public void setRemarkDate(String remarkDate) {
			this.remarkDate = remarkDate;
		}
		
		
		public String getTimeIn() {
			return timeIn;
		}
		public void setTimeIn(String timeIn) {
			this.timeIn = timeIn;
		}
		public String getTimeInEnds() {
			return timeInEnds;
		}
		public void setTimeInEnds(String timeInEnds) {
			this.timeInEnds = timeInEnds;
		}
		public String getTimeOut() {
			return timeOut;
		}
		public void setTimeOut(String timeOut) {
			this.timeOut = timeOut;
		}
		public String getSaturdayTimeOut() {
			return saturdayTimeOut;
		}
		public void setSaturdayTimeOut(String saturdayTimeOut) {
			this.saturdayTimeOut = saturdayTimeOut;
		}
		public String getHalfDayStartTime() {
			return halfDayStartTime;
		}
		public void setHalfDayStartTime(String halfDayStartTime) {
			this.halfDayStartTime = halfDayStartTime;
		}
		public String getHalfDayEndTime() {
			return halfDayEndTime;
		}
		public void setHalfDayEndTime(String halfDayEndTime) {
			this.halfDayEndTime = halfDayEndTime;
		}
		public List<EmpTypeTo> getEmpTypeTo() {
			return empTypeTo;
		}
		public void setEmpTypeTo(List<EmpTypeTo> empTypeTo) {
			this.empTypeTo = empTypeTo;
		}
		public String getuId() {
			return uId;
		}
		public void setuId(String uId) {
			this.uId = uId;
		}
		public List<EmpLeaveAllotTO> getEmpLeaveAllotTo() {
			return empLeaveAllotTo;
		}
		public void setEmpLeaveAllotTo(List<EmpLeaveAllotTO> empLeaveAllotTo) {
			this.empLeaveAllotTo = empLeaveAllotTo;
		}
		public String getDetails() {
			return details;
		}
		public void setDetails(String details) {
			this.details = details;
		}
		public String getFinancialDetails() {
			return financialDetails;
		}
		public void setFinancialDetails(String financialDetails) {
			this.financialDetails = financialDetails;
		}
		public String getIncentivesDetails() {
			return incentivesDetails;
		}
		public void setIncentivesDetails(String incentivesDetails) {
			this.incentivesDetails = incentivesDetails;
		}
		public String getFeeConcessionDetails() {
			return feeConcessionDetails;
		}
		public void setFeeConcessionDetails(String feeConcessionDetails) {
			this.feeConcessionDetails = feeConcessionDetails;
		}
		public String getRemarkDetails() {
			return remarkDetails;
		}
		public void setRemarkDetails(String remarkDetails) {
			this.remarkDetails = remarkDetails;
		}
		public String getAcheivementName() {
			return acheivementName;
		}
		public void setAcheivementName(String acheivementName) {
			this.acheivementName = acheivementName;
		}
		public String getPassportIssueDate() {
			return passportIssueDate;
		}
		public void setPassportIssueDate(String passportIssueDate) {
			this.passportIssueDate = passportIssueDate;
		}
		public String getVisaIssueDate() {
			return visaIssueDate;
		}
		public void setVisaIssueDate(String visaIssueDate) {
			this.visaIssueDate = visaIssueDate;
		}
		public String getFingerPrintId() {
			return fingerPrintId;
		}
		public void setFingerPrintId(String fingerPrintId) {
			this.fingerPrintId = fingerPrintId;
		}
		public String getLoanListSize() {
			return loanListSize;
		}
		public void setLoanListSize(String loanListSize) {
			this.loanListSize = loanListSize;
		}
		public String getRemarksListSize() {
			return remarksListSize;
		}
		public void setRemarksListSize(String remarksListSize) {
			this.remarksListSize = remarksListSize;
		}
		public String getFeeListSize() {
			return feeListSize;
		}
		public void setFeeListSize(String feeListSize) {
			this.feeListSize = feeListSize;
		}
		public String getFinancialListSize() {
			return financialListSize;
		}
		public void setFinancialListSize(String financialListSize) {
			this.financialListSize = financialListSize;
		}
		public String getDependantsListSize() {
			return dependantsListSize;
		}
		public void setDependantsListSize(String dependantsListSize) {
			this.dependantsListSize = dependantsListSize;
		}
		public String getImmigrationListSize() {
			return immigrationListSize;
		}
		public void setImmigrationListSize(String immigrationListSize) {
			this.immigrationListSize = immigrationListSize;
		}
		public String getIncentivesListSize() {
			return incentivesListSize;
		}
		public void setIncentivesListSize(String incentivesListSize) {
			this.incentivesListSize = incentivesListSize;
		}
		public String getAchievementListSize() {
			return achievementListSize;
		}
		public void setAchievementListSize(String achievementListSize) {
			this.achievementListSize = achievementListSize;
		}
		public String getLevelSize() {
			return levelSize;
		}
		public void setLevelSize(String levelSize) {
			this.levelSize = levelSize;
		}
		public String getDesignation() {
			return designation;
		}
		public void setDesignation(String designation) {
			this.designation = designation;
		}
		public String getHighQualifForAlbum() {
			return highQualifForAlbum;
		}
		public void setHighQualifForAlbum(String highQualifForAlbum) {
			this.highQualifForAlbum = highQualifForAlbum;
		}
		public String getRelevantExpMonths() {
			return relevantExpMonths;
		}
		public void setRelevantExpMonths(String relevantExpMonths) {
			this.relevantExpMonths = relevantExpMonths;
		}
		public String getRelevantExpYears() {
			return relevantExpYears;
		}
		public void setRelevantExpYears(String relevantExpYears) {
			this.relevantExpYears = relevantExpYears;
		}
		public String getOtherPermanentState() {
			return otherPermanentState;
		}
		public void setOtherPermanentState(String otherPermanentState) {
			this.otherPermanentState = otherPermanentState;
		}
		public String getOtherCurrentState() {
			return otherCurrentState;
		}
		public void setOtherCurrentState(String otherCurrentState) {
			this.otherCurrentState = otherCurrentState;
		}
		public String getEmployeeId() {
			return employeeId;
		}
		public void setEmployeeId(String employeeId) {
			this.employeeId = employeeId;
		}
		public String getTempEmployeeId() {
			return tempEmployeeId;
		}
		public void setTempEmployeeId(String tempEmployeeId) {
			this.tempEmployeeId = tempEmployeeId;
		}
		public Map<String, String> getTitleMap() {
			return titleMap;
		}
		public void setTitleMap(Map<String, String> titleMap) {
			this.titleMap = titleMap;
		}
		public String getTitleId() {
			return titleId;
		}
		public void setTitleId(String titleId) {
			this.titleId = titleId;
		}
		public String getHomePhone1() {
			return homePhone1;
		}
		public void setHomePhone1(String homePhone1) {
			this.homePhone1 = homePhone1;
		}
		public String getHomePhone2() {
			return homePhone2;
		}
		public void setHomePhone2(String homePhone2) {
			this.homePhone2 = homePhone2;
		}
		public String getHomePhone3() {
			return homePhone3;
		}
		public void setHomePhone3(String homePhone3) {
			this.homePhone3 = homePhone3;
		}
		public String getWorkPhNo1() {
			return workPhNo1;
		}
		public void setWorkPhNo1(String workPhNo1) {
			this.workPhNo1 = workPhNo1;
		}
		public String getWorkPhNo2() {
			return workPhNo2;
		}
		public void setWorkPhNo2(String workPhNo2) {
			this.workPhNo2 = workPhNo2;
		}
		public String getWorkPhNo3() {
			return workPhNo3;
		}
		public void setWorkPhNo3(String workPhNo3) {
			this.workPhNo3 = workPhNo3;
		}
		public String getTimeInMin() {
			return timeInMin;
		}
		public void setTimeInMin(String timeInMin) {
			this.timeInMin = timeInMin;
		}
		public String getTimeInEndMIn() {
			return timeInEndMIn;
		}
		public void setTimeInEndMIn(String timeInEndMIn) {
			this.timeInEndMIn = timeInEndMIn;
		}
		public String getTimeOutMin() {
			return timeOutMin;
		}
		public void setTimeOutMin(String timeOutMin) {
			this.timeOutMin = timeOutMin;
		}
		public String getSaturdayTimeOutMin() {
			return saturdayTimeOutMin;
		}
		public void setSaturdayTimeOutMin(String saturdayTimeOutMin) {
			this.saturdayTimeOutMin = saturdayTimeOutMin;
		}
		public String getHalfDayStartTimeMin() {
			return halfDayStartTimeMin;
		}
		public void setHalfDayStartTimeMin(String halfDayStartTimeMin) {
			this.halfDayStartTimeMin = halfDayStartTimeMin;
		}
		public String getHalfDayEndTimeMin() {
			return halfDayEndTimeMin;
		}
		public void setHalfDayEndTimeMin(String halfDayEndTimeMin) {
			this.halfDayEndTimeMin = halfDayEndTimeMin;
		}
		public String getCurrentlyWorking() {
			return currentlyWorking;
		}
		public void setCurrentlyWorking(String currentlyWorking) {
			this.currentlyWorking = currentlyWorking;
		}
		public void setReservationCategory1(String reservationCategory1) {
			this.reservationCategory1 = reservationCategory1;
		}
		public String getReservationCategory1() {
			return reservationCategory1;
		}
		public String getSameAddress() {
			return sameAddress;
		}
		public void setSameAddress(String sameAddress) {
			this.sameAddress = sameAddress;
		}
		public EmployeeInfoEditTO getEmployeeInfoTONew() {
			return employeeInfoTONew;
		}
		public void setEmployeeInfoTONew(EmployeeInfoEditTO employeeInfoTONew) {
			this.employeeInfoTONew = employeeInfoTONew;
		}
		public String getTempUid() {
			return tempUid;
		}
		public void setTempUid(String tempUid) {
			this.tempUid = tempUid;
		}
		public String getTempFingerPrintId() {
			return tempFingerPrintId;
		}
		public void setTempFingerPrintId(String tempFingerPrintId) {
			this.tempFingerPrintId = tempFingerPrintId;
		}
		public String getTempName() {
			return tempName;
		}
		public void setTempName(String tempName) {
			this.tempName = tempName;
		}
		public String getTempCode() {
			return tempCode;
		}
		public void setTempCode(String tempCode) {
			this.tempCode = tempCode;
		}
		public String getTempDesignationPfId() {
			return tempDesignationPfId;
		}
		public void setTempDesignationPfId(String tempDesignationPfId) {
			this.tempDesignationPfId = tempDesignationPfId;
		}
		
		public List<EmployeeTO> getEmployeeToList() {
			return employeeToList;
		}
		public void setEmployeeToList(List<EmployeeTO> employeeToList) {
			this.employeeToList = employeeToList;
		}
		public String getSelectedEmployeeId() {
			return selectedEmployeeId;
		}
		public void setSelectedEmployeeId(String selectedEmployeeId) {
			this.selectedEmployeeId = selectedEmployeeId;
		}
		public String getTempDepartmentId() {
			return tempDepartmentId;
		}
		public void setTempDepartmentId(String tempDepartmentId) {
			this.tempDepartmentId = tempDepartmentId;
		}
		public Map<String, String> getTempDesignationMap() {
			return tempDesignationMap;
		}
		public void setTempDesignationMap(Map<String, String> tempDesignationMap) {
			this.tempDesignationMap = tempDesignationMap;
		}
		public Map<String, String> getTempDepartmentMap() {
			return tempDepartmentMap;
		}
		public void setTempDepartmentMap(Map<String, String> tempDepartmentMap) {
			this.tempDepartmentMap = tempDepartmentMap;
		}
		public String getTempDesignationName() {
			return tempDesignationName;
		}
		public void setTempDesignationName(String tempDesignationName) {
			this.tempDesignationName = tempDesignationName;
		}
		public String getTempDepartmentName() {
			return tempDepartmentName;
		}
		public void setTempDepartmentName(String tempDepartmentName) {
			this.tempDepartmentName = tempDepartmentName;
		}
		
		public String getTempStreamId() {
			return tempStreamId;
		}
		public void setTempStreamId(String tempStreamId) {
			this.tempStreamId = tempStreamId;
		}
		public String getTempStreamName() {
			return tempStreamName;
		}
		public void setTempStreamName(String tempStreamName) {
			this.tempStreamName = tempStreamName;
		}
		public String getTempActive() {
			return tempActive;
		}
		public void setTempActive(String tempActive) {
			this.tempActive = tempActive;
		}
		public Map<String, String> getTempStreamMap() {
			return tempStreamMap;
		}
		public void setTempStreamMap(Map<String, String> tempStreamMap) {
			this.tempStreamMap = tempStreamMap;
		}
		public String getTempTeachingStaff() {
			return tempTeachingStaff;
		}
		public void setTempTeachingStaff(String tempTeachingStaff) {
			this.tempTeachingStaff = tempTeachingStaff;
		}
		public String getIsSmartCardDataGenerated() {
			return isSmartCardDataGenerated;
		}
		public void setIsSmartCardDataGenerated(String isSmartCardDataGenerated) {
			this.isSmartCardDataGenerated = isSmartCardDataGenerated;
		}
		public String getIsSmartCardDataDelivered() {
			return isSmartCardDataDelivered;
		}
		public void setIsSmartCardDataDelivered(String isSmartCardDataDelivered) {
			this.isSmartCardDataDelivered = isSmartCardDataDelivered;
		}
		public String getSmartCardNo() {
			return smartCardNo;
		}
		public void setSmartCardNo(String smartCardNo) {
			this.smartCardNo = smartCardNo;
		}
		public String getIsPunchingExcemption() {
			return isPunchingExcemption;
		}
		public void setIsPunchingExcemption(String isPunchingExcemption) {
			this.isPunchingExcemption = isPunchingExcemption;
		}
		public String getEmpTypeIdOld() {
			return empTypeIdOld;
		}
		public void setEmpTypeIdOld(String empTypeIdOld) {
			this.empTypeIdOld = empTypeIdOld;
		}
		public byte[] getPhotoBytes() {
			return photoBytes;
		}
		public void setPhotoBytes(byte[] photoBytes) {
			this.photoBytes = photoBytes;
		}
		public String getReservationCategory() {
			return reservationCategory;
		}
		public void setReservationCategory(String reservationCategory) {
			this.reservationCategory = reservationCategory;
		}
		public String getEmpLeaveInitOldMonth() {
			return empLeaveInitOldMonth;
		}
		public void setEmpLeaveInitOldMonth(String empLeaveInitOldMonth) {
			this.empLeaveInitOldMonth = empLeaveInitOldMonth;
		}
		public String getEmpLeaveInitNewMonth() {
			return empLeaveInitNewMonth;
		}
		public void setEmpLeaveInitNewMonth(String empLeaveInitNewMonth) {
			this.empLeaveInitNewMonth = empLeaveInitNewMonth;
		}
		public EditMyProfile getEditMyProfile() {
			return editMyProfile;
		}
		public void setEditMyProfile(EditMyProfile editMyProfile) {
			this.editMyProfile = editMyProfile;
		}
		public String getEligibilityTestNET() {
			return eligibilityTestNET;
		}
		public void setEligibilityTestNET(String eligibilityTestNET) {
			this.eligibilityTestNET = eligibilityTestNET;
		}
		public String getEligibilityTestSLET() {
			return eligibilityTestSLET;
		}
		public void setEligibilityTestSLET(String eligibilityTestSLET) {
			this.eligibilityTestSLET = eligibilityTestSLET;
		}
		public String getEligibilityTestSET() {
			return eligibilityTestSET;
		}
		public void setEligibilityTestSET(String eligibilityTestSET) {
			this.eligibilityTestSET = eligibilityTestSET;
		}
		public String getEligibilityTestNone() {
			return eligibilityTestNone;
		}
		public void setEligibilityTestNone(String eligibilityTestNone) {
			this.eligibilityTestNone = eligibilityTestNone;
		}
		public String getTempEmptypeId() {
			return tempEmptypeId;
		}
		public void setTempEmptypeId(String tempEmptypeId) {
			this.tempEmptypeId = tempEmptypeId;
		}
		public Map<String, String> getTempEmpTypeMap() {
			return tempEmpTypeMap;
		}
		public void setTempEmpTypeMap(Map<String, String> tempEmpTypeMap) {
			this.tempEmpTypeMap = tempEmpTypeMap;
		}
		public String getEmpImageId() {
			return empImageId;
		}
		public void setEmpImageId(String empImageId) {
			this.empImageId = empImageId;
		}
		public String getFocusValue() {
			return focusValue;
		}
		public void setFocusValue(String focusValue) {
			this.focusValue = focusValue;
		}
		public String getListSize() {
			return listSize;
		}
		public void setListSize(String listSize) {
			this.listSize = listSize;
		}
		public String getOtherEligibilityTestValue() {
			return otherEligibilityTestValue;
		}
		public void setOtherEligibilityTestValue(String otherEligibilityTestValue) {
			this.otherEligibilityTestValue = otherEligibilityTestValue;
		}
		public String getEligibilityTestOther() {
			return eligibilityTestOther;
		}
		public void setEligibilityTestOther(String eligibilityTestOther) {
			this.eligibilityTestOther = eligibilityTestOther;
		}
		public String getIndustryFunctionalArea() {
			return industryFunctionalArea;
		}
		public void setIndustryFunctionalArea(String industryFunctionalArea) {
			this.industryFunctionalArea = industryFunctionalArea;
		}
		public String getHandicappedDescription() {
			return handicappedDescription;
		}
		public void setHandicappedDescription(String handicappedDescription) {
			this.handicappedDescription = handicappedDescription;
		}
		public String getEmContactAddress() {
			return emContactAddress;
		}
		public void setEmContactAddress(String emContactAddress) {
			this.emContactAddress = emContactAddress;
		}
		public List<EligibilityTestTO> getEligibilityList() {
			return eligibilityList;
		}
		public void setEligibilityList(List<EligibilityTestTO> eligibilityList) {
			this.eligibilityList = eligibilityList;
		}
		public Boolean getIsCjc() {
			return isCjc;
		}
		public void setIsCjc(Boolean isCjc) {
			this.isCjc = isCjc;
		}
		public String getAlbumDesignation() {
			return albumDesignation;
		}
		public void setAlbumDesignation(String albumDesignation) {
			this.albumDesignation = albumDesignation;
		}
		public int getCurrentExpYears() {
			return currentExpYears;
		}
		public void setCurrentExpYears(int currentExpYears) {
			this.currentExpYears = currentExpYears;
		}
		public int getCurrentExpMonths() {
			return currentExpMonths;
		}
		public void setCurrentExpMonths(int currentExpMonths) {
			this.currentExpMonths = currentExpMonths;
		}
		public int getTotalCurrentExpYears() {
			return totalCurrentExpYears;
		}
		public void setTotalCurrentExpYears(int totalCurrentExpYears) {
			this.totalCurrentExpYears = totalCurrentExpYears;
		}
		public int getTotalCurrentExpMonths() {
			return totalCurrentExpMonths;
		}
		public void setTotalCurrentExpMonths(int totalCurrentExpMonths) {
			this.totalCurrentExpMonths = totalCurrentExpMonths;
		}
		public String getTeaching() {
			return teaching;
		}
		public void setTeaching(String teaching) {
			this.teaching = teaching;
		}
		public String getIndustry() {
			return industry;
		}
		public void setIndustry(String industry) {
			this.industry = industry;
		}
		public String getExtensionNumber() {
			return extensionNumber;
		}
		public void setExtensionNumber(String extensionNumber) {
			this.extensionNumber = extensionNumber;
		}
		public String getLicGratuityNo() {
			return licGratuityNo;
		}
		public void setLicGratuityNo(String licGratuityNo) {
			this.licGratuityNo = licGratuityNo;
		}
		public String getLicGratuityDate() {
			return licGratuityDate;
		}
		public void setLicGratuityDate(String licGratuityDate) {
			this.licGratuityDate = licGratuityDate;
		}
		public String getNomineePfNo() {
			return nomineePfNo;
		}
		public void setNomineePfNo(String nomineePfNo) {
			this.nomineePfNo = nomineePfNo;
		}
		public String getNomineePfDate() {
			return nomineePfDate;
		}
		public void setNomineePfDate(String nomineePfDate) {
			this.nomineePfDate = nomineePfDate;
		}
		public String getNameAdressNominee() {
			return nameAdressNominee;
		}
		public void setNameAdressNominee(String nameAdressNominee) {
			this.nameAdressNominee = nameAdressNominee;
		}
		public String getMemberRelationship() {
			return memberRelationship;
		}
		public void setMemberRelationship(String memberRelationship) {
			this.memberRelationship = memberRelationship;
		}
		public String getDobMember() {
			return dobMember;
		}
		public void setDobMember(String dobMember) {
			this.dobMember = dobMember;
		}
		public String getShare() {
			return share;
		}
		public void setShare(String share) {
			this.share = share;
		}
		public String getNameAdressGuardian() {
			return nameAdressGuardian;
		}
		public void setNameAdressGuardian(String nameAdressGuardian) {
			this.nameAdressGuardian = nameAdressGuardian;
		}
		public String getPfGratuityListSize() {
			return pfGratuityListSize;
		}
		public void setPfGratuityListSize(String pfGratuityListSize) {
			this.pfGratuityListSize = pfGratuityListSize;
		}
		public byte[] getPhoto() {
			return photo;
		}
		public void setPhoto(byte[] photo) {
			this.photo = photo;
		}
		public String getStartTimeInEndsHours() {
			return startTimeInEndsHours;
		}
		public void setStartTimeInEndsHours(String startTimeInEndsHours) {
			this.startTimeInEndsHours = startTimeInEndsHours;
		}
		public String getStartTimeInEndsMins() {
			return startTimeInEndsMins;
		}
		public void setStartTimeInEndsMins(String startTimeInEndsMins) {
			this.startTimeInEndsMins = startTimeInEndsMins;
		}
		public String getResearchPapersRefereed() {
			return researchPapersRefereed;
		}
		public void setResearchPapersRefereed(String researchPapersRefereed) {
			this.researchPapersRefereed = researchPapersRefereed;
		}
		public String getResearchPapersNonRefereed() {
			return researchPapersNonRefereed;
		}
		public void setResearchPapersNonRefereed(String researchPapersNonRefereed) {
			this.researchPapersNonRefereed = researchPapersNonRefereed;
		}
		public String getResearchPapersProceedings() {
			return researchPapersProceedings;
		}
		public void setResearchPapersProceedings(String researchPapersProceedings) {
			this.researchPapersProceedings = researchPapersProceedings;
		}
		public String getNationalPublications() {
			return nationalPublications;
		}
		public void setNationalPublications(String nationalPublications) {
			this.nationalPublications = nationalPublications;
		}
		public String getInternationalPublications() {
			return internationalPublications;
		}
		public void setInternationalPublications(String internationalPublications) {
			this.internationalPublications = internationalPublications;
		}
		public String getLocalPublications() {
			return localPublications;
		}
		public void setLocalPublications(String localPublications) {
			this.localPublications = localPublications;
		}
		public String getInternational() {
			return international;
		}
		public void setInternational(String international) {
			this.international = international;
		}
		public String getNational() {
			return national;
		}
		public void setNational(String national) {
			this.national = national;
		}
		public String getMajorProjects() {
			return majorProjects;
		}
		public void setMajorProjects(String majorProjects) {
			this.majorProjects = majorProjects;
		}
		public String getMinorProjects() {
			return minorProjects;
		}
		public void setMinorProjects(String minorProjects) {
			this.minorProjects = minorProjects;
		}
		public String getConsultancyPrjects1() {
			return consultancyPrjects1;
		}
		public void setConsultancyPrjects1(String consultancyPrjects1) {
			this.consultancyPrjects1 = consultancyPrjects1;
		}
		public String getConsultancyProjects2() {
			return consultancyProjects2;
		}
		public void setConsultancyProjects2(String consultancyProjects2) {
			this.consultancyProjects2 = consultancyProjects2;
		}
		public String getPhdResearchGuidance() {
			return phdResearchGuidance;
		}
		public void setPhdResearchGuidance(String phdResearchGuidance) {
			this.phdResearchGuidance = phdResearchGuidance;
		}
		public String getMphilResearchGuidance() {
			return mphilResearchGuidance;
		}
		public void setMphilResearchGuidance(String mphilResearchGuidance) {
			this.mphilResearchGuidance = mphilResearchGuidance;
		}
		public String getFdp1Training() {
			return fdp1Training;
		}
		public void setFdp1Training(String fdp1Training) {
			this.fdp1Training = fdp1Training;
		}
		public String getFdp2Training() {
			return fdp2Training;
		}
		public void setFdp2Training(String fdp2Training) {
			this.fdp2Training = fdp2Training;
		}
		public String getRegionalConference() {
			return regionalConference;
		}
		public void setRegionalConference(String regionalConference) {
			this.regionalConference = regionalConference;
		}
		public String getInternationalConference() {
			return internationalConference;
		}
		public void setInternationalConference(String internationalConference) {
			this.internationalConference = internationalConference;
		}
		public String getNationalConference() {
			return nationalConference;
		}
		public void setNationalConference(String nationalConference) {
			this.nationalConference = nationalConference;
		}
		public String getLocalConference() {
			return localConference;
		}
		public void setLocalConference(String localConference) {
			this.localConference = localConference;
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
		public String getDeputationDepartmentId() {
			return deputationDepartmentId;
		}
		public void setDeputationDepartmentId(String deputationDepartmentId) {
			this.deputationDepartmentId = deputationDepartmentId;
		}
		public String getAppointmentLetterDate() {
			return appointmentLetterDate;
		}
		public void setAppointmentLetterDate(String appointmentLetterDate) {
			this.appointmentLetterDate = appointmentLetterDate;
		}
		public String getReferenceNumberForAppointment() {
			return referenceNumberForAppointment;
		}
		public void setReferenceNumberForAppointment(
				String referenceNumberForAppointment) {
			this.referenceNumberForAppointment = referenceNumberForAppointment;
		}
		public String getReferenceNumberForReleaving() {
			return referenceNumberForReleaving;
		}
		public void setReferenceNumberForReleaving(String referenceNumberForReleaving) {
			this.referenceNumberForReleaving = referenceNumberForReleaving;
		}
		public String getReleavingOrderDate() {
			return releavingOrderDate;
		}
		public void setReleavingOrderDate(String releavingOrderDate) {
			this.releavingOrderDate = releavingOrderDate;
		}
		public Map<String, String> getDeputationDepartmentMap() {
			return deputationDepartmentMap;
		}
		public void setDeputationDepartmentMap(
				Map<String, String> deputationDepartmentMap) {
			this.deputationDepartmentMap = deputationDepartmentMap;
		}
		public String getAdditionalRemarks() {
			return additionalRemarks;
		}
		public void setAdditionalRemarks(String additionalRemarks) {
			this.additionalRemarks = additionalRemarks;
		}
		public String getIsDirectpage() {
			return isDirectpage;
		}
		public void setIsDirectpage(String isDirectpage) {
			this.isDirectpage = isDirectpage;
		}
		public String getRefresherCourse() {
			return refresherCourse;
		}
		public void setRefresherCourse(String refresherCourse) {
			this.refresherCourse = refresherCourse;
		}
		public String getOrientationCourse() {
			return orientationCourse;
		}
		public void setOrientationCourse(String orientationCourse) {
			this.orientationCourse = orientationCourse;
		}
		public String getTitleOthers() {
			return titleOthers;
		}
		public void setTitleOthers(String titleOthers) {
			this.titleOthers = titleOthers;
		}
		public String getQualificationOthers() {
			return qualificationOthers;
		}
		public void setQualificationOthers(String qualificationOthers) {
			this.qualificationOthers = qualificationOthers;
		}
		public String getHighestQualification() {
			return highestQualification;
		}
		public void setHighestQualification(String highestQualification) {
			this.highestQualification = highestQualification;
		}
		

	}

