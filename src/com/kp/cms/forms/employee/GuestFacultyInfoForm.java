package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.DownloadEmployeeResumeTO;
import com.kp.cms.to.admin.EmpOnlineEducationalDetailsTO;
import com.kp.cms.to.admin.EmpOnlinePreviousExperienceTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.employee.EligibilityTestTO;
import com.kp.cms.to.employee.EmployeeInfoEditTO;
import com.kp.cms.to.employee.EmployeeStreamTO;
import com.kp.cms.to.employee.GuestFacultyInfoTo;
import com.kp.cms.to.employee.GuestFacultyTO;
import com.kp.cms.to.employee.GuestEducationalDetailsTO;
import com.kp.cms.to.employee.GuestPreviousExperienceTO;

public class GuestFacultyInfoForm extends BaseActionForm{
	
	private static final long serialVersionUID = 1L;
	private String guestId;
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
	private Map<String,String> religionMap;
	private Map<String,String> streamMap;
	private Map<String,String> workLocationMap;
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
	private GuestFacultyInfoTo employeeInfoTONew;
    private String teachingStaff;
	private String officialEmail;
	
	private List<GuestFacultyTO> employeeToList;
	private List<EmployeeStreamTO> streamTO;

	private boolean employeeFound;
	
	private GuestFacultyTO employeeDetail;
	private String active;
	private String emContactRelationship;
	private String emContactName;
	private String emContactHomeTel;
	private String emContactWorkTel;
	private String emContactMobile;
	
	
	private String eligibilityTestNET;
	private String eligibilityTestSLET;
	private String eligibilityTestSET;
	private String eligibilityTestNone;
	private String flag;
	
	
	
	private String uId;
	
	private String designation;
	private String highQualifForAlbum;
	private String relevantExpMonths;
	private String relevantExpYears;
	private String otherPermanentState;
	private String otherCurrentState;
	private String titleId;
	private String reservationCategory1;
	
	private String tempUid;
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
	private String subjectSpecilization;
	private String startDate;
	private String endDate;
	private String semester;
	private String isCurrentWorkingDates;
	private String workingHoursPerWeek;
	private String honorariumPerHours;
	private String referredBy;
	private String levelSize;
	private String prevWorkListSize;
	private String printPage;
	private List<GuestEducationalDetailsTO> empEducationalDetails;
	private List<GuestEducationalDetailsTO> additionalQualification;
	private List<GuestPreviousExperienceTO> teachingExperience;
	private List<GuestPreviousExperienceTO> industryExperience;
	private List<Department> departmentList;
	private List<Designation> designationList;
	private List<EmpQualificationLevel> qualificationList;
	private List<GuestFacultyTO> tos;
	private String forwardFlag;
	private String staffId;
	private String highQualifForWebsite;
	private String displayInWebsite;
	private String bankBranch;
	private String bankIfscCode;
	private String previousStaffId;
	private Map<Integer,String> guestFacultyMap;
	private GuestFacultyTO guestFacultyTo;
	private String originalBankAccountNo;
	private String originalBankBranch;
	private String originalIfscCode;
	private String originalPanNo;
	
	public void reset(){
		
		eligibilityList = null;
		selectedEmployeeId=null;
		age=null;
		guestId=null;
		otherPermanentState=null;
		otherCurrentState=null;
		relevantExpMonths="0";
		relevantExpYears="0";
		highQualifForAlbum=null;
		designation=null;
		code=null;
		uId=null;
		emContactWorkTel=null;
		emContactHomeTel=null;
		emContactName=null;
		emContactMobile=null;
		emContactRelationship=null;
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
		printPage=null;
		staffId=null;
		highQualifForWebsite=null;
		displayInWebsite="0";
		bankBranch=null;
		bankIfscCode=null;
		previousStaffId=null;

	}

	public String getTempEmployeeId() {
		return tempEmployeeId;
	}

	public void setTempEmployeeId(String tempEmployeeId) {
		this.tempEmployeeId = tempEmployeeId;
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

	public byte[] getPhotoBytes() {
		return photoBytes;
	}

	public void setPhotoBytes(byte[] photoBytes) {
		this.photoBytes = photoBytes;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getPassportCountryId() {
		return passportCountryId;
	}

	public void setPassportCountryId(String passportCountryId) {
		this.passportCountryId = passportCountryId;
	}

	public String getVisaCountryId() {
		return visaCountryId;
	}

	public void setVisaCountryId(String visaCountryId) {
		this.visaCountryId = visaCountryId;
	}

	public String getPayScaleId() {
		return payScaleId;
	}

	public void setPayScaleId(String payScaleId) {
		this.payScaleId = payScaleId;
	}

	public String getNationalityId() {
		return nationalityId;
	}

	public void setNationalityId(String nationalityId) {
		this.nationalityId = nationalityId;
	}

	public String getReligionId() {
		return religionId;
	}

	public void setReligionId(String religionId) {
		this.religionId = religionId;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getEmpJobTypeId() {
		return empJobTypeId;
	}

	public void setEmpJobTypeId(String empJobTypeId) {
		this.empJobTypeId = empJobTypeId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getEmpEducationDetailsId() {
		return empEducationDetailsId;
	}

	public void setEmpEducationDetailsId(String empEducationDetailsId) {
		this.empEducationDetailsId = empEducationDetailsId;
	}

	public String getEmpAcheivementId() {
		return empAcheivementId;
	}

	public void setEmpAcheivementId(String empAcheivementId) {
		this.empAcheivementId = empAcheivementId;
	}

	public String getEmptypeId() {
		return emptypeId;
	}

	public void setEmptypeId(String emptypeId) {
		this.emptypeId = emptypeId;
	}

	public String getStreamId() {
		return streamId;
	}

	public void setStreamId(String streamId) {
		this.streamId = streamId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
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

	public String getMobileNo3() {
		return mobileNo3;
	}

	public void setMobileNo3(String mobileNo3) {
		this.mobileNo3 = mobileNo3;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	public String getExpectedSalaryLakhs() {
		return expectedSalaryLakhs;
	}

	public void setExpectedSalaryLakhs(String expectedSalaryLakhs) {
		this.expectedSalaryLakhs = expectedSalaryLakhs;
	}

	public String getExpectedSalaryThousands() {
		return expectedSalaryThousands;
	}

	public void setExpectedSalaryThousands(String expectedSalaryThousands) {
		this.expectedSalaryThousands = expectedSalaryThousands;
	}

	public String getDesiredPost() {
		return desiredPost;
	}

	public void setDesiredPost(String desiredPost) {
		this.desiredPost = desiredPost;
	}

	public String getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getInformationKnown() {
		return informationKnown;
	}

	public void setInformationKnown(String informationKnown) {
		this.informationKnown = informationKnown;
	}

	public String getRecommendedBy() {
		return recommendedBy;
	}

	public void setRecommendedBy(String recommendedBy) {
		this.recommendedBy = recommendedBy;
	}

	public FormFile getEmpPhoto() {
		return empPhoto;
	}

	public void setEmpPhoto(FormFile empPhoto) {
		this.empPhoto = empPhoto;
	}

	public String getBankAccNo() {
		return bankAccNo;
	}

	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}

	public String getPfNo() {
		return pfNo;
	}

	public void setPfNo(String pfNo) {
		this.pfNo = pfNo;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVacancyInformation() {
		return vacancyInformation;
	}

	public void setVacancyInformation(String vacancyInformation) {
		this.vacancyInformation = vacancyInformation;
	}

	public String getDesignationId() {
		return designationId;
	}

	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getCurrentAddressLine1() {
		return currentAddressLine1;
	}

	public void setCurrentAddressLine1(String currentAddressLine1) {
		this.currentAddressLine1 = currentAddressLine1;
	}

	public String getCurrentAddressLine2() {
		return currentAddressLine2;
	}

	public void setCurrentAddressLine2(String currentAddressLine2) {
		this.currentAddressLine2 = currentAddressLine2;
	}

	public String getCurrentAddressLine3() {
		return currentAddressLine3;
	}

	public void setCurrentAddressLine3(String currentAddressLine3) {
		this.currentAddressLine3 = currentAddressLine3;
	}

	public String getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	public String getCurrentState() {
		return currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	public String getCurrentCountryId() {
		return currentCountryId;
	}

	public void setCurrentCountryId(String currentCountryId) {
		this.currentCountryId = currentCountryId;
	}

	public String getPermanentZipCode() {
		return permanentZipCode;
	}

	public void setPermanentZipCode(String permanentZipCode) {
		this.permanentZipCode = permanentZipCode;
	}

	public String getCurrentZipCode() {
		return currentZipCode;
	}

	public void setCurrentZipCode(String currentZipCode) {
		this.currentZipCode = currentZipCode;
	}

	public String getReservationCategory() {
		return reservationCategory;
	}

	public void setReservationCategory(String reservationCategory) {
		this.reservationCategory = reservationCategory;
	}

	public String getEmpQualificationLevelId() {
		return empQualificationLevelId;
	}

	public void setEmpQualificationLevelId(String empQualificationLevelId) {
		this.empQualificationLevelId = empQualificationLevelId;
	}

	public String[] getEligibilityTest() {
		return eligibilityTest;
	}

	public void setEligibilityTest(String[] eligibilityTest) {
		this.eligibilityTest = eligibilityTest;
	}

	public String getNoOfPublicationsRefered() {
		return noOfPublicationsRefered;
	}

	public void setNoOfPublicationsRefered(String noOfPublicationsRefered) {
		this.noOfPublicationsRefered = noOfPublicationsRefered;
	}

	public String getNoOfPublicationsNotRefered() {
		return noOfPublicationsNotRefered;
	}

	public void setNoOfPublicationsNotRefered(String noOfPublicationsNotRefered) {
		this.noOfPublicationsNotRefered = noOfPublicationsNotRefered;
	}

	public String getBooks() {
		return books;
	}

	public void setBooks(String books) {
		this.books = books;
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public String getApplicationNO() {
		return applicationNO;
	}

	public void setApplicationNO(String applicationNO) {
		this.applicationNO = applicationNO;
	}

	public String getEmpSubjectAreaId() {
		return empSubjectAreaId;
	}

	public void setEmpSubjectAreaId(String empSubjectAreaId) {
		this.empSubjectAreaId = empSubjectAreaId;
	}

	public Map<String, String> getDesignationMap() {
		return designationMap;
	}

	public void setDesignationMap(Map<String, String> designationMap) {
		this.designationMap = designationMap;
	}

	public Map<String, String> getDepartmentMap() {
		return departmentMap;
	}

	public void setDepartmentMap(Map<String, String> departmentMap) {
		this.departmentMap = departmentMap;
	}

	public Map<String, String> getCountryMap() {
		return countryMap;
	}

	public void setCountryMap(Map<String, String> countryMap) {
		this.countryMap = countryMap;
	}

	public Map<String, String> getStateMap() {
		return stateMap;
	}

	public void setStateMap(Map<String, String> stateMap) {
		this.stateMap = stateMap;
	}

	public Map<String, String> getNationalityMap() {
		return nationalityMap;
	}

	public void setNationalityMap(Map<String, String> nationalityMap) {
		this.nationalityMap = nationalityMap;
	}

	public Map<String, String> getQualificationLevelMap() {
		return qualificationLevelMap;
	}

	public void setQualificationLevelMap(Map<String, String> qualificationLevelMap) {
		this.qualificationLevelMap = qualificationLevelMap;
	}

	public Map<String, String> getQualificationFixedMap() {
		return qualificationFixedMap;
	}

	public void setQualificationFixedMap(Map<String, String> qualificationFixedMap) {
		this.qualificationFixedMap = qualificationFixedMap;
	}

	public Map<String, String> getQualificationMap() {
		return qualificationMap;
	}

	public void setQualificationMap(Map<String, String> qualificationMap) {
		this.qualificationMap = qualificationMap;
	}

	public Map<String, String> getSubjectAreaMap() {
		return subjectAreaMap;
	}

	public void setSubjectAreaMap(Map<String, String> subjectAreaMap) {
		this.subjectAreaMap = subjectAreaMap;
	}

	public Map<String, String> getReligionMap() {
		return religionMap;
	}

	public void setReligionMap(Map<String, String> religionMap) {
		this.religionMap = religionMap;
	}

	public Map<String, String> getStreamMap() {
		return streamMap;
	}

	public void setStreamMap(Map<String, String> streamMap) {
		this.streamMap = streamMap;
	}

	public Map<String, String> getWorkLocationMap() {
		return workLocationMap;
	}

	public void setWorkLocationMap(Map<String, String> workLocationMap) {
		this.workLocationMap = workLocationMap;
	}

	public Map<String, String> getTitleMap() {
		return titleMap;
	}

	public void setTitleMap(Map<String, String> titleMap) {
		this.titleMap = titleMap;
	}

	public String getSameAddress() {
		return sameAddress;
	}

	public void setSameAddress(String sameAddress) {
		this.sameAddress = sameAddress;
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

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

	public Map<String, String> getPostAppliedMap() {
		return postAppliedMap;
	}

	public void setPostAppliedMap(Map<String, String> postAppliedMap) {
		this.postAppliedMap = postAppliedMap;
	}

	public Map<String, String> getCurrentCountryMap() {
		return currentCountryMap;
	}

	public void setCurrentCountryMap(Map<String, String> currentCountryMap) {
		this.currentCountryMap = currentCountryMap;
	}

	public Map<String, String> getCurrentStateMap() {
		return currentStateMap;
	}

	public void setCurrentStateMap(Map<String, String> currentStateMap) {
		this.currentStateMap = currentStateMap;
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

	public Map<String, String> getJobTypeMap() {
		return jobTypeMap;
	}

	public void setJobTypeMap(Map<String, String> jobTypeMap) {
		this.jobTypeMap = jobTypeMap;
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

	public void setExpYears(String expYears) {
		this.expYears = expYears;
	}

	public String getExpMonths() {
		return expMonths;
	}

	public void setExpMonths(String expMonths) {
		this.expMonths = expMonths;
	}

	public String getTeachingStaff() {
		return teachingStaff;
	}

	public void setTeachingStaff(String teachingStaff) {
		this.teachingStaff = teachingStaff;
	}

	public String getOfficialEmail() {
		return officialEmail;
	}

	public void setOfficialEmail(String officialEmail) {
		this.officialEmail = officialEmail;
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

	

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
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

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	public String getReservationCategory1() {
		return reservationCategory1;
	}

	public void setReservationCategory1(String reservationCategory1) {
		this.reservationCategory1 = reservationCategory1;
	}

	public String getTempUid() {
		return tempUid;
	}

	public void setTempUid(String tempUid) {
		this.tempUid = tempUid;
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

	public Map<String, String> getTempStreamMap() {
		return tempStreamMap;
	}

	public void setTempStreamMap(Map<String, String> tempStreamMap) {
		this.tempStreamMap = tempStreamMap;
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

	public String getSelectedEmployeeId() {
		return selectedEmployeeId;
	}

	public void setSelectedEmployeeId(String selectedEmployeeId) {
		this.selectedEmployeeId = selectedEmployeeId;
	}

	public String getTempActive() {
		return tempActive;
	}

	public void setTempActive(String tempActive) {
		this.tempActive = tempActive;
	}

	public String getTempTeachingStaff() {
		return tempTeachingStaff;
	}

	public void setTempTeachingStaff(String tempTeachingStaff) {
		this.tempTeachingStaff = tempTeachingStaff;
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

	public String getSubjectSpecilization() {
		return subjectSpecilization;
	}

	public void setSubjectSpecilization(String subjectSpecilization) {
		this.subjectSpecilization = subjectSpecilization;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getIsCurrentWorkingDates() {
		return isCurrentWorkingDates;
	}

	public void setIsCurrentWorkingDates(String isCurrentWorkingDates) {
		this.isCurrentWorkingDates = isCurrentWorkingDates;
	}

	public String getWorkingHoursPerWeek() {
		return workingHoursPerWeek;
	}

	public void setWorkingHoursPerWeek(String workingHoursPerWeek) {
		this.workingHoursPerWeek = workingHoursPerWeek;
	}

	public String getHonorariumPerHours() {
		return honorariumPerHours;
	}

	public void setHonorariumPerHours(String honorariumPerHours) {
		this.honorariumPerHours = honorariumPerHours;
	}

	public String getReferredBy() {
		return referredBy;
	}

	public void setReferredBy(String referredBy) {
		this.referredBy = referredBy;
	}

	public GuestFacultyInfoTo getEmployeeInfoTONew() {
		return employeeInfoTONew;
	}

	public void setEmployeeInfoTONew(GuestFacultyInfoTo employeeInfoTONew) {
		this.employeeInfoTONew = employeeInfoTONew;
	}

	public String getLevelSize() {
		return levelSize;
	}

	public void setLevelSize(String levelSize) {
		this.levelSize = levelSize;
	}

	public List<GuestFacultyTO> getEmployeeToList() {
		return employeeToList;
	}

	public void setEmployeeToList(List<GuestFacultyTO> employeeToList) {
		this.employeeToList = employeeToList;
	}

	public GuestFacultyTO getEmployeeDetail() {
		return employeeDetail;
	}

	public void setEmployeeDetail(GuestFacultyTO employeeDetail) {
		this.employeeDetail = employeeDetail;
	}

	public String getPrevWorkListSize() {
		return prevWorkListSize;
	}

	public void setPrevWorkListSize(String prevWorkListSize) {
		this.prevWorkListSize = prevWorkListSize;
	}

	public String getPrintPage() {
		return printPage;
	}

	public void setPrintPage(String printPage) {
		this.printPage = printPage;
	}

	public List<GuestEducationalDetailsTO> getAdditionalQualification() {
		return additionalQualification;
	}

	public void setAdditionalQualification(
			List<GuestEducationalDetailsTO> additionalQualification) {
		this.additionalQualification = additionalQualification;
	}

	public List<GuestPreviousExperienceTO> getTeachingExperience() {
		return teachingExperience;
	}

	public void setTeachingExperience(
			List<GuestPreviousExperienceTO> teachingExperience) {
		this.teachingExperience = teachingExperience;
	}

	public List<GuestPreviousExperienceTO> getIndustryExperience() {
		return industryExperience;
	}

	public void setIndustryExperience(
			List<GuestPreviousExperienceTO> industryExperience) {
		this.industryExperience = industryExperience;
	}

	public List<Department> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<Department> departmentList) {
		this.departmentList = departmentList;
	}

	public List<Designation> getDesignationList() {
		return designationList;
	}

	public void setDesignationList(List<Designation> designationList) {
		this.designationList = designationList;
	}

	public List<EmpQualificationLevel> getQualificationList() {
		return qualificationList;
	}

	public void setQualificationList(List<EmpQualificationLevel> qualificationList) {
		this.qualificationList = qualificationList;
	}

	public List<GuestFacultyTO> getTos() {
		return tos;
	}

	public void setTos(List<GuestFacultyTO> tos) {
		this.tos = tos;
	}

	public List<GuestEducationalDetailsTO> getEmpEducationalDetails() {
		return empEducationalDetails;
	}

	public void setEmpEducationalDetails(
			List<GuestEducationalDetailsTO> empEducationalDetails) {
		this.empEducationalDetails = empEducationalDetails;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}



	public String getGuestId() {
		return guestId;
	}

	public void setGuestId(String guestId) {
		this.guestId = guestId;
	}

	public String getForwardFlag() {
		return forwardFlag;
	}

	public void setForwardFlag(String forwardFlag) {
		this.forwardFlag = forwardFlag;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getHighQualifForWebsite() {
		return highQualifForWebsite;
	}

	public void setHighQualifForWebsite(String highQualifForWebsite) {
		this.highQualifForWebsite = highQualifForWebsite;
	}

	public String getDisplayInWebsite() {
		return displayInWebsite;
	}

	public void setDisplayInWebsite(String displayInWebsite) {
		this.displayInWebsite = displayInWebsite;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getBankIfscCode() {
		return bankIfscCode;
	}

	public void setBankIfscCode(String bankIfscCode) {
		this.bankIfscCode = bankIfscCode;
	}

	public String getPreviousStaffId() {
		return previousStaffId;
	}

	public void setPreviousStaffId(String previousStaffId) {
		this.previousStaffId = previousStaffId;
	}

	public Map<Integer, String> getGuestFacultyMap() {
		return guestFacultyMap;
	}

	public void setGuestFacultyMap(Map<Integer, String> guestFacultyMap) {
		this.guestFacultyMap = guestFacultyMap;
	}

	public GuestFacultyTO getGuestFacultyTo() {
		return guestFacultyTo;
	}

	public void setGuestFacultyTo(GuestFacultyTO guestFacultyTo) {
		this.guestFacultyTo = guestFacultyTo;
	}

	public String getOriginalBankAccountNo() {
		return originalBankAccountNo;
	}

	public void setOriginalBankAccountNo(String originalBankAccountNo) {
		this.originalBankAccountNo = originalBankAccountNo;
	}

	public String getOriginalBankBranch() {
		return originalBankBranch;
	}

	public void setOriginalBankBranch(String originalBankBranch) {
		this.originalBankBranch = originalBankBranch;
	}

	public String getOriginalIfscCode() {
		return originalIfscCode;
	}

	public void setOriginalIfscCode(String originalIfscCode) {
		this.originalIfscCode = originalIfscCode;
	}

	public String getOriginalPanNo() {
		return originalPanNo;
	}

	public void setOriginalPanNo(String originalPanNo) {
		this.originalPanNo = originalPanNo;
	}
	
	public void reset1(){
		this.guestFacultyTo=null;
		this.guestId=null;
	}
}
