package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.EligibilityTestTO;
import com.kp.cms.to.employee.EmpResumeSubmissionTo;

public class EmpResumeSubmissionForm extends BaseActionForm {
	
	private String nationalityId;
	private String countryId;
	private String empJobTypeId;
	private String departmentId;
	private String stateId;
	private String empEducationDetailsId;
	private String empAcheivementId;
	private String empDesiredPostId;

	private String name;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String code;
	private String gender;
	private String maritalStatus;
	private String city;
	private String dateOfBirth;
	private String phNo1;
	private String phNo2;
	private String phNo3;
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
	private String[] reservationCategory;
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
	//private boolean sameAddress;
	private String currentlyWorking;
	private String designationPfId;
	private String orgAddress;
	private EmpResumeSubmissionTo empResumeSubmissionTo;
	private boolean adminUser;
	private String mode;
	private String jobType;
	private String yearComp;
	private String appliedYear;
	private FormFile file;
	private Map<String,String> postAppliedMap;
	private Map<String,String> currentCountryMap;
	private Map<String,String> currentStateMap;
	private Map<String,String> jobTypeMap;
	private String qualificationId;
	private String expYears;
	private String expMonths;
	private String teachingExpLength;
	private String industryExpLength;
	private String currentStateOthers;
	private String tempState;
	private String tempPermanentState;
	private String levelSize;
	private String otherCurrentState;
	private String otherPermanentState;
	private String sameAddress;
	private Boolean isCjc;
	private Boolean isDeptDisabled;
	private String focusValue;
	// added newly *changes by Smitha */
	private String bloodGroup;
	private String religionId;
	private Map<String,String> religionMap;
	private String otherEligibilityTestValue;
	private String industryFunctionalArea;
	private String handicappedDescription;
	private List<EligibilityTestTO> eligibilityList;
	// added by sudhir
	private String teaching;
	private String industry;
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
	private String fatherName;
	private String motherName;
	private String alternativeMobile;
	private Map<Integer,String> empSubjectMap;
	private String empSubjectId;
	private String serverDownMessage;
	
	

	public String getServerDownMessage() {
		return serverDownMessage;
	}
	public void setServerDownMessage(String serverDownMessage) {
		this.serverDownMessage = serverDownMessage;
	}
	public String getSameAddress() {
		return sameAddress;
	}
	public void setSameAddress(String sameAddress) {
		this.sameAddress = sameAddress;
	}
	public void reset(){
		otherEligibilityTestValue=null;
		industryFunctionalArea=null;
		eligibilityList=null;
		handicappedDescription=null;
		focusValue=null;
		isDeptDisabled=false;
		nationalityId=null;
		countryId=null;
		empJobTypeId=null;
		departmentId=null;
		stateId=null;
		empEducationDetailsId=null;
		empAcheivementId=null;
		empDesiredPostId=null;

		name=null;
		addressLine1=null;
		addressLine2=null;
		addressLine3=null;
		code=null;
		gender=null;
		maritalStatus=null;
		city=null;
		dateOfBirth=null;
		phNo1=null;
		phNo2=null;
		phNo3=null;
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
		noOfPublicationsRefered=null;
		noOfPublicationsNotRefered=null;
		books=null;
		otherInfo=null; 
		applicationNO=null;
		empSubjectAreaId=null;
		
		sameAddress="false";
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
		currentStateOthers=null;
		tempState="";
		tempPermanentState="";
		currentlyWorking="YES";
		levelSize=null;
		otherCurrentState=null;
		otherPermanentState=null;
		workPhNo1=null;
		workPhNo2=null;
		workPhNo3=null;
		designationId=null;
		isCjc=false;
		religionId=null;
		bloodGroup=null;
		researchPapersRefereed=null;
		researchPapersNonRefereed=null;
		researchPapersProceedings=null;
		nationalPublications=null;
		internationalPublications=null;
		localPublications=null;
		international=null;
		national=null;
		majorProjects=null;
		minorProjects=null;
		consultancyPrjects1=null;
		consultancyProjects2=null;
		phdResearchGuidance=null;
		mphilResearchGuidance=null;
		fdp1Training=null;
		fdp2Training=null;
		regionalConference=null;
		internationalConference=null;
		nationalConference=null;
		localConference=null;
		fatherName = null;
		motherName = null;
		alternativeMobile = null;
		empSubjectId=null;
		serverDownMessage=null;
	}
	public EmpResumeSubmissionForm() {
		// TODO Auto-generated constructor stub
		this.empResumeSubmissionTo=new EmpResumeSubmissionTo();
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
	public String getEmpDesiredPostId() {
		return empDesiredPostId;
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
	public String getPhNo1() {
		return phNo1;
	}
	public String getPhNo2() {
		return phNo2;
	}
	public String getPhNo3() {
		return phNo3;
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
	public String getLevelSize() {
		return levelSize;
	}
	public void setLevelSize(String levelSize) {
		this.levelSize = levelSize;
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
	public String[] getReservationCategory() {
		return reservationCategory;
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
	public void setEmpDesiredPostId(String empDesiredPostId) {
		this.empDesiredPostId = empDesiredPostId;
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
	public void setPhNo1(String phNo1) {
		this.phNo1 = phNo1;
	}
	public void setPhNo2(String phNo2) {
		this.phNo2 = phNo2;
	}
	public void setPhNo3(String phNo3) {
		this.phNo3 = phNo3;
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
	public void setReservationCategory(String[] reservationCategory) {
		this.reservationCategory = reservationCategory;
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
	
	public String getCurrentlyWorking() {
		return currentlyWorking;
	}
	public void setCurrentlyWorking(String currentlyWorking) {
		this.currentlyWorking = currentlyWorking;
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
	public EmpResumeSubmissionTo getEmpResumeSubmissionTo() {
		return empResumeSubmissionTo;
	}
	public boolean isAdminUser() {
		return adminUser;
	}
	public void setEmpResumeSubmissionTo(EmpResumeSubmissionTo empResumeSubmissionTo) {
		this.empResumeSubmissionTo = empResumeSubmissionTo;
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
	public String getTeachingExpLength() {
		return teachingExpLength;
	}
	public String getIndustryExpLength() {
		return industryExpLength;
	}
	public void setTeachingExpLength(String teachingExpLength) {
		this.teachingExpLength = teachingExpLength;
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
	public String getOtherCurrentState() {
		return otherCurrentState;
	}
	public void setOtherCurrentState(String otherCurrentState) {
		this.otherCurrentState = otherCurrentState;
	}
	public String getOtherPermanentState() {
		return otherPermanentState;
	}
	public void setOtherPermanentState(String otherPermanentState) {
		this.otherPermanentState = otherPermanentState;
	}
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
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
	public Boolean getIsCjc() {
		return isCjc;
	}
	public void setIsCjc(Boolean isCjc) {
		this.isCjc = isCjc;
	}
	public Boolean getIsDeptDisabled() {
		return isDeptDisabled;
	}
	public void setIsDeptDisabled(Boolean isDeptDisabled) {
		this.isDeptDisabled = isDeptDisabled;
	}
	public String getFocusValue() {
		return focusValue;
	}
	public void setFocusValue(String focusValue) {
		this.focusValue = focusValue;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
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
	public List<EligibilityTestTO> getEligibilityList() {
		return eligibilityList;
	}
	public void setEligibilityList(List<EligibilityTestTO> eligibilityList) {
		this.eligibilityList = eligibilityList;
	}
	public String getOtherEligibilityTestValue() {
		return otherEligibilityTestValue;
	}
	public void setOtherEligibilityTestValue(String otherEligibilityTestValue) {
		this.otherEligibilityTestValue = otherEligibilityTestValue;
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
	public String getAlternativeMobile() {
		return alternativeMobile;
	}
	public void setAlternativeMobile(String alternativeMobile) {
		this.alternativeMobile = alternativeMobile;
	}
	public Map<Integer, String> getEmpSubjectMap() {
		return empSubjectMap;
	}
	public void setEmpSubjectMap(Map<Integer, String> empSubjectMap) {
		this.empSubjectMap = empSubjectMap;
	}
	public String getEmpSubjectId() {
		return empSubjectId;
	}
	public void setEmpSubjectId(String empSubjectId) {
		this.empSubjectId = empSubjectId;
	}


}
