package com.kp.cms.to.admission;

import java.util.TreeMap;




public class CandidateSearchTO {
//	modifications done by priyatham
	private String regNoDisp;
	private short regNoPois;
	private String rollNoDisp;
	private short rollNoPois;
	private String admissionDateDisp;
	private short admissionDatePois;
	private String classNameDisp;	
	private short classNamePois;
	private String className;
	private String programNameDisp;
	private short programNamePois;
	private String programTypeNameDisp;
	private short programTypeNamePois;
	private String courseNameDisp;
	private short courseNamePois;
	private String secondLanguageDisp;
	private short secondLanguagePois;
	private String journalNoDisp;
	private short journalNoPois;
	private String acedamicYearDisp;
	private short acedamicYearPois;
	private String admittedThroughDisp;
	private short admittedThroughPois;
	private String residentCategoryDisp;
	private short residentCategoryPois;
	private String religionDisp;
	private short religionPois;
	private String religionOtherDisp; 
	private short religionOtherPois;
	private String subReligionDisp; 
	private short subReligionPois;
	private String subReligionOtherDisp; 
	private short subReligionOtherPois;
	private String castCategoryDisp; 
	private short castCategoryPois;
	private String castCategoryOtherDisp; 
	private short castCategoryOtherPois;
	private String placeOfBirthDisp; 
	private short placeOfBirthPois;
	private String stateOfBirthDisp; 
	private short stateOfBirthPois;
	private String stateOfBirthOtherDisp; 
	private short stateOfBirthOtherPois;
	private String countryOfBirthDisp; 
	private short countryOfBirthPois;
	private String belongsToDisp; 
	private short belongsToPois;
	private String nationalityDisp; 
	private short nationalityPois;
	private String studentPhoneNumberDisp; 
	private short studentPhoneNumberPois;
	private String studentMobileNumberDisp; 
	private short studentMobileNumberPois;
	private String passportNumberDisp; 
	private short passportNumberPois;
	private String passportIssuingCountryDisp; 
	private short passportIssuingCountryPois;
	private String passportValidUpToDisp;
	private short passportValidUpToPois;
	private String firstPreferenceProgramTypeDisp; 
	private short firstPreferenceProgramTypePois;
	private String firstPreferenceProgramDisp; 
	private short firstPreferenceProgramPois;
	private String firstPreferenceCourseDisp; 
	private short firstPreferenceCoursePois;
	private String secondPreferenceProgramTypeDisp; 
	private short secondPreferenceProgramTypePois;
	private String secondPreferenceProgramDisp; 
	private short secondPreferenceProgramPois;
	private String secondPreferenceCourseDisp; 
	private short secondPreferenceCoursePois;
	private String thirdPreferenceProgramTypeDisp; 
	private short thirdPreferenceProgramTypePois;
	private String thirdPreferenceProgramDisp; 
	private short thirdPreferenceProgramPois;
	private String thirdPreferenceCourseDisp; 
	private short thirdPreferenceCoursePois;
	private String firstNameofOrganisationDisp; 
	private short firstNameofOrganisationPois;
	private String firstDesignationDisp; 
	private short firstDesignationPois;
	private String firstFromDateDisp; 
	private short firstFromDatePois;
	private String firstToDateDisp; 
	private short firstToDatePois;
	private String secondNameofOrganisationDisp; 
	private short secondNameofOrganisationPois;
	private String secondDesignationDisp; 
	private short secondDesignationPois;
	private String secondFromDateDisp;
	private short secondFromDatePois;
	private String secondToDateDisp;
	private short secondToDatePois;
	private String thirdNameofOrganisationDisp;
	private short thirdNameofOrganisationPois;
	private String thirdDesignationDisp;
	private short thirdDesignationPois;
	private String thirdFromDateDisp;
	private short thirdFromDatePois;
	private String thirdToDateDisp;
	private short thirdToDatePois;
	private String permanentAddressLine1Disp;
	private short permanentAddressLine1Pois;
	private String permanentAddressLine2Disp;
	private short permanentAddressLine2Pois;
	private String permanentStateDisp;
	private short permanentStatePois;
	private String permanentStateOthersDisp;
	private short permanentStateOthersPois;
	private String permanentCityDisp;
	private short permanentCityPois;
	private String permanentCountryDisp;
	private short permanentCountryPois;
	private String permanentZipCodeDisp;
	private short permanentZipCodePois;
	private String currentAddressLine1Disp;
	private short currentAddressLine1Pois;
	private String currentAddressLine2Disp;
	private short currentAddressLine2Pois;
	private String currentStateDisp;
	private short currentStatePois;
	private String currentStateOthersDisp;
	private short currentStateOthersPois;
	private String currentCityDisp;
	private short currentCityPois;
	private String currentCountryDisp;
	private short currentCountryPois;
	private String currentZipCodeDisp;
	private short currentZipCodePois;
	private String fathersNameDisp;
	private short fathersNamePois;
	private String fathersEducationDisp;
	private short fathersEducationPois;
	private String fathersIncomeDisp;
	private short fathersIncomePois;
	private String fathersCurrencyDisp;
	private short fathersCurrencyPois;
	private String fathersOccupationDisp;
	private short fathersOccupationPois;
	private String fathersEmailDisp;
	private short fathersEmailPois;
	private String mothersNameDisp;
	private short mothersNamePois;
	private String mothersEducationDisp;
	private short mothersEducationPois;
	private String mothersIncomeDisp;
	private short mothersIncomePois;
	private String mothersCurrencyDisp;
	private short mothersCurrencyPois;
	private String mothersOccupationDisp; 
	private short mothersOccupationPois;
	private String mothersEmailDisp; 
	private short mothersEmailPois;
	private String parentAdressLine1Disp; 
	private short parentAdressLine1Pois;
	private String parentAdressLine2Disp; 
	private short parentAdressLine2Pois;
	private String parentAdressLine3Disp; 
	private short parentAdressLine3Pois;
	private String parentCityDisp; 
	private short parentCityPois;
	private String parentStateDisp; 
	private short parentStatePois;
	private String parentStateOtherDisp; 
	private short parentStateOtherPois;
	private String parentCountryDisp; 
	private short parentCountryPois;
	private String parentZipCodeDisp; 
	private short parentZipCodePois;
	private String parentPhoneDisp; 
	private short parentPhonePois;
	private String parentMobileNoDisp; 
	private short parentMobileNoPois;
	private String applnNoDisp;
	private short applnNoPois;
	private String dateOfBirthDisp;
	private short dateOfBirthPois;
	private String challanNoDisp;
	private short challanNoPois;
	private String genderDisp;
	private short genderPois;
	private String bloodGroupDisp;
	private short bloodGroupPois;
	private String emailDisp;
	private short emailPois;
	private String totalWeightageDisp;
	private short totalWeightagePois;
	private String nameDisp;
	private short namePois;
	private short seatNoPois;
	
	
	private String programTypeName;
	private String programName;
	private String courseName;
	private String journalNo;
	private String appliedYear;
	private String admittedThrough;
	private String subjectGroup;
	private int applnNo;
	private String name;
	private double totalWeightage;
	private String dateOfBirth;
	private String challanNo;
	private String challanDate;
	private String challanAmount;
	private String gender;
	private String bloodGroup;
	private String email;
	private String fileName;
	
	private String residentCategory;
	private String religion;
	private String religionOther;
	private String subReligion;
	private String subReligionOther;
	private String castCategory;
	private String castCategoryOther;
	private String placeOfBirth;
	private String stateOfBirth;
	private String stateOfBirthOther;	
	private String countryOfBirth;	
	private String belongsTo;
	private String nationality;
	private String studentPhoneNo;
	private String studentMobileNo;
	private String passportNo;
	private String passportIssuingCountry;
	private String passportValidUpTo;
	
	private String firstPrePT;
	private String firstPreProgram;
	private String firstCourse;
	private String secondPrePT;
	private String secondPreProgram;
	private String secondCourse;
	private String thirdPrePT;
	private String thirdPreProgram;
	private String thirdCourse;
	

	private String firstNameOfOra;
	private String firstDesignation;
	private String firstFromDate;
	private String firstTODate;
	private String secondNameOfOra;
	private String secondDesignation;
	private String secondFromDate;
	private String secondTODate;
	private String thirdNameOfOra;
	private String thirdDesignation;
	private String thirdFromDate;
	private String thirdTODate;

	private String permanentAddressline1;
	private String permanentAddressline2;
	private String permanentAddressline3;
	private String permanentState;
	private String permanentStateOther;
	private String permanentCity;
	private String permanentCountry;
	private String permanentZipCode;
	
	private String currentAddressline1;
	private String currentAddressline2;
	private String currentAddressline3;
	private String currentState;
	private String currentStateOther;
	private String currentCity;
	private String currentCountry;
	private String currentZipCode;
	
	private String instituteName;
	private String passYear;
	private String obtainedMark;
	private String totalmark;
	private String status;

	private String fatherName;
	private String fatherEducation;
	private String fatherIncome;
	private String fatherIncomeCurrency;
	private String fatherOccupation;
	private String fatherEmail;
	

	private String motherName;
	private String motherEducation;
	private String motherIncome;
	private String motherIncomeCurrency;
	private String motherOccupation;	
	private String motherEmail;
	
	private String parentAdressLine1;
	private String parentAdressLine2;
	private String parentAdressLine3;
	private String parentCity;
	private String parentState;
	private String parentStateOther;
	private String parentCountry;	
	private String parentZipCode;
	private String parentPhone;	
	private String parentMobileNo;

	private String regNo;
	private String rollNo;
	private String admissionDate;
	private String secondLanguage;
	private String classStudent;
	private String acedamicYear;

	private String percentage;
	private String seatNo;
	private String seatNoDisp;
	
	private String examCenterDisp;
	private short examCenterPois;
	
	private String examCenter;
	private TreeMap<String, String> gradeMap;
	private TreeMap<String, String> docTypePerMap;
	private TreeMap<String, String> docTypeExamNameMap;
	private TreeMap<String, String> uniMap;
	private TreeMap<String, String> instMap;
	private String interviewResultDisp;
	private String prvExamDetDisp;
	private boolean dispStatus;
	private String admStatus;
	private String intDateTimeDisp;
	private String preReqDisp;
	private Short preReqDispPois;
	TreeMap<String, String> intDateMap = new TreeMap<String, String>();
	TreeMap<String, String> intTimeMap = new TreeMap<String, String>();
	private TreeMap<String,String> yearMap;
	
	TreeMap<String, String> pExamMap = new TreeMap<String, String>();
	TreeMap<String, String> pHeadMap = new TreeMap<String, String>();
	TreeMap<String, String> pRollMap = new TreeMap<String, String>();
	TreeMap<String,String> pYopMap =new TreeMap<String, String>();
	TreeMap<String,String> pMopMap =new TreeMap<String, String>();
	
	private String motherTongueDisp;
	private short motherTonguePois;
	
	private String motherTongue;
	
	private String admissionStatusDisp;
	private short admissionStatusPois;
	private String isAdmittedDisp;
	private short isAdmittedPois;
	private String currentClassDisp;
	private short currentClassPois;
	
	private String admissionStatus;
	private String isAdmitted;
	private String currentClass;
//	isSelected,isFinalMeritApproved,userName and password added by priyatham
	private String isSelected;
	private String isFinalMeritApproved;
	private String isSelectedDisp;
	private short isSelectedPois;
	private String isFinalMeritApprovedDisp;
	private short isFinalMeritApprovedPois;
	

	private String userName;
	private String userNameDisp;
	private short userNamePois;
	private String password;
	private String passwordDisp;
	private Short passwordPois;
	
	//mary additions
	
	private String appliedDate;
	private String appliedDateDisp;
	private short appliedDatePois;
	private String challanPaymentDate;
	private String challanPaymentDateDisp;
	private Short challanPaymentDatePois;
	
	private String feeChallanDate;
	private String feeChallanDateDisp;
	private short feeChallanDatePois;
	private String feeChallanNo;
	private String feeChallanNoDisp;
	private Short feeChallanNoPois;
	
	private String totalFeePaid;
	private String totalFeePaidDisp;
	private short totalFeePaidPois;
	private Boolean isHandicaped;
	private String isHandicapedDisp;
	private Short isHandicapedPois;
	
	private String handicapDetails;
	private String handicapDetailsDisp;
	private Short handicapDetailsPois;
	
	private String totalExpYear;
	private String totalExpYearDisp;
	private short totalExpYearPois;
	
	//mary additions ends
	// added for challan verification display
	private String isChallanVerified;
	private String challanVerfiedDisp;
	private short challanVerifiedPois;
	
	// added for photo status display
	private String photo;
	private String photoDisp;
	private short photoPois;
	//added for display of comments
	private TreeMap<String, String> commentsMap;
	//added for specializationPrefered and backlogs
	private String specializationPrefered;
	private String specializationPreferedDisp;
	private short specializationPreferedPois;
	private String backLogs;
	private String backLogsDisp;
	private short backLogsPois;
	private String canceled;
	private String canceledDisp;
	private short canceledPois;
	private String newbackLogs;
	private String newbackLogsDisp;
	private short newbackLogsPois;
	// added by sudhir
	private String applicantFeedback;
	private String applicantFeedbackDisp;
	private short applicantFeedbackPois;
	//Added by Cimi
	private String universityEmail;
	private String universityEmailDisp;
	private short universityEmailPois;
	//added by chandra
	private String comedk;
	private String comedkDisp;
	private short comedkPois;
	//added by Manu
	private String remarks;
	private String remarksDisp;
	private short remarkPois;
	
	//added by Mary
	private String selectionProcessDate;
	private String selectionProcessDateDisp;
	private short selectionProcessDatePois;
	//added by giri
	private String photoUploaded;
	private String photoUploadedDisp;
	private short photoUploadedPois;
	
	
	public String getPhotoUploaded() {
		return photoUploaded;
	}
	public void setPhotoUploaded(String photoUploaded) {
		this.photoUploaded = photoUploaded;
	}
	public String getPhotoUploadedDisp() {
		return photoUploadedDisp;
	}
	public void setPhotoUploadedDisp(String photoUploadedDisp) {
		this.photoUploadedDisp = photoUploadedDisp;
	}
	public short getPhotoUploadedPois() {
		return photoUploadedPois;
	}
	public void setPhotoUploadedPois(short photoUploadedPois) {
		this.photoUploadedPois = photoUploadedPois;
	}
	public String getAdmissionDate() {
		return admissionDate;
	}
	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}
	public String getSecondLanguage() {
		return secondLanguage;
	}
	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}
	public String getClassStudent() {
		return classStudent;
	}
	public void setClassStudent(String classStudent) {
		this.classStudent = classStudent;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
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
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getFatherEducation() {
		return fatherEducation;
	}
	public void setFatherEducation(String fatherEducation) {
		this.fatherEducation = fatherEducation;
	}
	public String getFatherIncome() {
		return fatherIncome;
	}
	public void setFatherIncome(String fatherIncome) {
		this.fatherIncome = fatherIncome;
	}
	public String getFatherIncomeCurrency() {
		return fatherIncomeCurrency;
	}
	public void setFatherIncomeCurrency(String fatherIncomeCurrency) {
		this.fatherIncomeCurrency = fatherIncomeCurrency;
	}
	public String getFatherEmail() {
		return fatherEmail;
	}
	public void setFatherEmail(String fatherEmail) {
		this.fatherEmail = fatherEmail;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	public String getMotherEducation() {
		return motherEducation;
	}
	public void setMotherEducation(String motherEducation) {
		this.motherEducation = motherEducation;
	}
	public String getMotherIncome() {
		return motherIncome;
	}
	public void setMotherIncome(String motherIncome) {
		this.motherIncome = motherIncome;
	}
	public String getMotherIncomeCurrency() {
		return motherIncomeCurrency;
	}
	public void setMotherIncomeCurrency(String motherIncomeCurrency) {
		this.motherIncomeCurrency = motherIncomeCurrency;
	}
	public String getMotherEmail() {
		return motherEmail;
	}
	public void setMotherEmail(String motherEmail) {
		this.motherEmail = motherEmail;
	}
	public String getParentAdressLine1() {
		return parentAdressLine1;
	}
	public void setParentAdressLine1(String parentAdressLine1) {
		this.parentAdressLine1 = parentAdressLine1;
	}
	public String getParentAdressLine2() {
		return parentAdressLine2;
	}
	public void setParentAdressLine2(String parentAdressLine2) {
		this.parentAdressLine2 = parentAdressLine2;
	}
	public String getParentAdressLine3() {
		return parentAdressLine3;
	}
	public void setParentAdressLine3(String parentAdressLine3) {
		this.parentAdressLine3 = parentAdressLine3;
	}
	public String getParentCity() {
		return parentCity;
	}
	public void setParentCity(String parentCity) {
		this.parentCity = parentCity;
	}
	public String getParentState() {
		return parentState;
	}
	public void setParentState(String parentState) {
		this.parentState = parentState;
	}
	public String getParentStateOther() {
		return parentStateOther;
	}
	public void setParentStateOther(String parentStateOther) {
		this.parentStateOther = parentStateOther;
	}
	public String getParentCountry() {
		return parentCountry;
	}
	public void setParentCountry(String parentCountry) {
		this.parentCountry = parentCountry;
	}
	public String getParentZipCode() {
		return parentZipCode;
	}
	public void setParentZipCode(String parentZipCode) {
		this.parentZipCode = parentZipCode;
	}
	public String getParentPhone() {
		return parentPhone;
	}
	public void setParentPhone(String parentPhone) {
		this.parentPhone = parentPhone;
	}
	public String getParentMobileNo() {
		return parentMobileNo;
	}
	public void setParentMobileNo(String parentMobileNo) {
		this.parentMobileNo = parentMobileNo;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public String getPassYear() {
		return passYear;
	}
	public void setPassYear(String passYear) {
		this.passYear = passYear;
	}
	public String getObtainedMark() {
		return obtainedMark;
	}
	public void setObtainedMark(String obtainedMark) {
		this.obtainedMark = obtainedMark;
	}
	public String getTotalmark() {
		return totalmark;
	}
	public void setTotalmark(String totalmark) {
		this.totalmark = totalmark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPermanentAddressline3() {
		return permanentAddressline3;
	}
	public void setPermanentAddressline3(String permanentAddressline3) {
		this.permanentAddressline3 = permanentAddressline3;
	}
	public String getCurrentAddressline3() {
		return currentAddressline3;
	}
	public void setCurrentAddressline3(String currentAddressline3) {
		this.currentAddressline3 = currentAddressline3;
	}
	public String getPermanentAddressline1() {
		return permanentAddressline1;
	}
	public void setPermanentAddressline1(String permanentAddressline1) {
		this.permanentAddressline1 = permanentAddressline1;
	}
	public String getPermanentAddressline2() {
		return permanentAddressline2;
	}
	public void setPermanentAddressline2(String permanentAddressline2) {
		this.permanentAddressline2 = permanentAddressline2;
	}
	public String getPermanentState() {
		return permanentState;
	}
	public void setPermanentState(String permanentState) {
		this.permanentState = permanentState;
	}
	public String getPermanentStateOther() {
		return permanentStateOther;
	}
	public void setPermanentStateOther(String permanentStateOther) {
		this.permanentStateOther = permanentStateOther;
	}
	public String getPermanentCity() {
		return permanentCity;
	}
	public void setPermanentCity(String permanentCity) {
		this.permanentCity = permanentCity;
	}
	public String getPermanentCountry() {
		return permanentCountry;
	}
	public void setPermanentCountry(String permanentCountry) {
		this.permanentCountry = permanentCountry;
	}
	public String getPermanentZipCode() {
		return permanentZipCode;
	}
	public void setPermanentZipCode(String permanentZipCode) {
		this.permanentZipCode = permanentZipCode;
	}
	public String getCurrentAddressline1() {
		return currentAddressline1;
	}
	public void setCurrentAddressline1(String currentAddressline1) {
		this.currentAddressline1 = currentAddressline1;
	}
	public String getCurrentAddressline2() {
		return currentAddressline2;
	}
	public void setCurrentAddressline2(String currentAddressline2) {
		this.currentAddressline2 = currentAddressline2;
	}
	public String getCurrentState() {
		return currentState;
	}
	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}
	public String getCurrentStateOther() {
		return currentStateOther;
	}
	public void setCurrentStateOther(String currentStateOther) {
		this.currentStateOther = currentStateOther;
	}
	public String getCurrentCity() {
		return currentCity;
	}
	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}
	public String getCurrentCountry() {
		return currentCountry;
	}
	public void setCurrentCountry(String currentCountry) {
		this.currentCountry = currentCountry;
	}
	public String getCurrentZipCode() {
		return currentZipCode;
	}
	public void setCurrentZipCode(String currentZipCode) {
		this.currentZipCode = currentZipCode;
	}
	public String getFirstNameOfOra() {
		return firstNameOfOra;
	}
	public void setFirstNameOfOra(String firstNameOfOra) {
		this.firstNameOfOra = firstNameOfOra;
	}
	public String getFirstDesignation() {
		return firstDesignation;
	}
	public void setFirstDesignation(String firstDesignation) {
		this.firstDesignation = firstDesignation;
	}
	public String getFirstFromDate() {
		return firstFromDate;
	}
	public void setFirstFromDate(String firstFromDate) {
		this.firstFromDate = firstFromDate;
	}
	public String getFirstTODate() {
		return firstTODate;
	}
	public void setFirstTODate(String firstTODate) {
		this.firstTODate = firstTODate;
	}
	public String getSecondNameOfOra() {
		return secondNameOfOra;
	}
	public void setSecondNameOfOra(String secondNameOfOra) {
		this.secondNameOfOra = secondNameOfOra;
	}
	public String getSecondDesignation() {
		return secondDesignation;
	}
	public void setSecondDesignation(String secondDesignation) {
		this.secondDesignation = secondDesignation;
	}
	public String getSecondFromDate() {
		return secondFromDate;
	}
	public void setSecondFromDate(String secondFromDate) {
		this.secondFromDate = secondFromDate;
	}
	public String getSecondTODate() {
		return secondTODate;
	}
	public void setSecondTODate(String secondTODate) {
		this.secondTODate = secondTODate;
	}
	public String getThirdNameOfOra() {
		return thirdNameOfOra;
	}
	public void setThirdNameOfOra(String thirdNameOfOra) {
		this.thirdNameOfOra = thirdNameOfOra;
	}
	public String getThirdDesignation() {
		return thirdDesignation;
	}
	public void setThirdDesignation(String thirdDesignation) {
		this.thirdDesignation = thirdDesignation;
	}
	public String getThirdFromDate() {
		return thirdFromDate;
	}
	public void setThirdFromDate(String thirdFromDate) {
		this.thirdFromDate = thirdFromDate;
	}
	public String getThirdTODate() {
		return thirdTODate;
	}
	public void setThirdTODate(String thirdTODate) {
		this.thirdTODate = thirdTODate;
	}
	public String getFirstPrePT() {
		return firstPrePT;
	}
	public void setFirstPrePT(String firstPrePT) {
		this.firstPrePT = firstPrePT;
	}
	public String getFirstPreProgram() {
		return firstPreProgram;
	}
	public void setFirstPreProgram(String firstPreProgram) {
		this.firstPreProgram = firstPreProgram;
	}
	public String getFirstCourse() {
		return firstCourse;
	}
	public void setFirstCourse(String firstCourse) {
		this.firstCourse = firstCourse;
	}
	public String getSecondPrePT() {
		return secondPrePT;
	}
	public void setSecondPrePT(String secondPrePT) {
		this.secondPrePT = secondPrePT;
	}
	public String getSecondPreProgram() {
		return secondPreProgram;
	}
	public void setSecondPreProgram(String secondPreProgram) {
		this.secondPreProgram = secondPreProgram;
	}
	public String getSecondCourse() {
		return secondCourse;
	}
	public void setSecondCourse(String secondCourse) {
		this.secondCourse = secondCourse;
	}
	public String getThirdPrePT() {
		return thirdPrePT;
	}
	public void setThirdPrePT(String thirdPrePT) {
		this.thirdPrePT = thirdPrePT;
	}
	public String getThirdPreProgram() {
		return thirdPreProgram;
	}
	public void setThirdPreProgram(String thirdPreProgram) {
		this.thirdPreProgram = thirdPreProgram;
	}
	public String getThirdCourse() {
		return thirdCourse;
	}
	public void setThirdCourse(String thirdCourse) {
		this.thirdCourse = thirdCourse;
	}
	public String getReligionOther() {
		return religionOther;
	}
	public void setReligionOther(String religionOther) {
		this.religionOther = religionOther;
	}
	public String getSubReligionOther() {
		return subReligionOther;
	}
	public void setSubReligionOther(String subReligionOther) {
		this.subReligionOther = subReligionOther;
	}
	public String getCastCategoryOther() {
		return castCategoryOther;
	}
	public void setCastCategoryOther(String castCategoryOther) {
		this.castCategoryOther = castCategoryOther;
	}
	public String getStateOfBirthOther() {
		return stateOfBirthOther;
	}
	public void setStateOfBirthOther(String stateOfBirthOther) {
		this.stateOfBirthOther = stateOfBirthOther;
	}
	public String getResidentCategory() {
		return residentCategory;
	}
	public void setResidentCategory(String residentCategory) {
		this.residentCategory = residentCategory;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getSubReligion() {
		return subReligion;
	}
	public void setSubReligion(String subReligion) {
		this.subReligion = subReligion;
	}
	public String getCastCategory() {
		return castCategory;
	}
	public void setCastCategory(String castCategory) {
		this.castCategory = castCategory;
	}
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}
	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}
	public String getStateOfBirth() {
		return stateOfBirth;
	}
	public void setStateOfBirth(String stateOfBirth) {
		this.stateOfBirth = stateOfBirth;
	}
	public String getCountryOfBirth() {
		return countryOfBirth;
	}
	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}
	public String getBelongsTo() {
		return belongsTo;
	}
	public void setBelongsTo(String belongsTo) {
		this.belongsTo = belongsTo;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getStudentPhoneNo() {
		return studentPhoneNo;
	}
	public void setStudentPhoneNo(String studentPhoneNo) {
		this.studentPhoneNo = studentPhoneNo;
	}
	public String getStudentMobileNo() {
		return studentMobileNo;
	}
	public void setStudentMobileNo(String studentMobileNo) {
		this.studentMobileNo = studentMobileNo;
	}
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	public String getPassportIssuingCountry() {
		return passportIssuingCountry;
	}
	public void setPassportIssuingCountry(String passportIssuingCountry) {
		this.passportIssuingCountry = passportIssuingCountry;
	}
	public String getPassportValidUpTo() {
		return passportValidUpTo;
	}
	public void setPassportValidUpTo(String passportValidUpTo) {
		this.passportValidUpTo = passportValidUpTo;
	}
	public String getChallanDate() {
		return challanDate;
	}
	public void setChallanDate(String challanDate) {
		this.challanDate = challanDate;
	}
	public String getChallanAmount() {
		return challanAmount;
	}
	public void setChallanAmount(String challanAmount) {
		this.challanAmount = challanAmount;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSubjectGroup() {
		return subjectGroup;
	}
	public void setSubjectGroup(String subjectGroup) {
		this.subjectGroup = subjectGroup;
	}
	public int getApplnNo() {
		return applnNo;
	}
	public void setApplnNo(int applnNo) {
		this.applnNo = applnNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public double getTotalWeightage() {
		return totalWeightage;
	}
	public void setTotalWeightage(double totalWeightage) {
		this.totalWeightage = totalWeightage;
	}
	public String getChallanNo() {
		return challanNo;
	}
	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getProgramTypeName() {
		return programTypeName;
	}
	public void setProgramTypeName(String programTypeName) {
		this.programTypeName = programTypeName;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getJournalNo() {
		return journalNo;
	}
	public void setJournalNo(String journalNo) {
		this.journalNo = journalNo;
	}
	public String getAppliedYear() {
		return appliedYear;
	}
	public void setAppliedYear(String appliedYear) {
		this.appliedYear = appliedYear;
	}
	public String getAdmittedThrough() {
		return admittedThrough;
	}
	public void setAdmittedThrough(String admittedThrough) {
		this.admittedThrough = admittedThrough;
	}
	public String getRegNoDisp() {
		return regNoDisp;
	}
	public void setRegNoDisp(String regNoDisp) {
		this.regNoDisp = regNoDisp;
	}
	public String getRollNoDisp() {
		return rollNoDisp;
	}
	public void setRollNoDisp(String rollNoDisp) {
		this.rollNoDisp = rollNoDisp;
	}
	public String getAdmissionDateDisp() {
		return admissionDateDisp;
	}
	public String getClassNameDisp() {
		return classNameDisp;
	}
	public void setAdmissionDateDisp(String admissionDateDisp) {
		this.admissionDateDisp = admissionDateDisp;
	}
	public void setClassNameDisp(String classNameDisp) {
		this.classNameDisp = classNameDisp;
	}
	public short getRegNoPois() {
		return regNoPois;
	}
	public short getRollNoPois() {
		return rollNoPois;
	}
	public short getAdmissionDatePois() {
		return admissionDatePois;
	}
	public short getClassNamePois() {
		return classNamePois;
	}
	public void setRegNoPois(short regNoPois) {
		this.regNoPois = regNoPois;
	}
	public void setRollNoPois(short rollNoPois) {
		this.rollNoPois = rollNoPois;
	}
	public void setAdmissionDatePois(short admissionDatePois) {
		this.admissionDatePois = admissionDatePois;
	}
	public void setClassNamePois(short classNamePois) {
		this.classNamePois = classNamePois;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getCourseNameDisp() {
		return courseNameDisp;
	}
	public void setCourseNameDisp(String courseNameDisp) {
		this.courseNameDisp = courseNameDisp;
	}
	public short getCourseNamePois() {
		return courseNamePois;
	}
	public void setCourseNamePois(short courseNamePois) {
		this.courseNamePois = courseNamePois;
	}
	public String getSecondLanguageDisp() {
		return secondLanguageDisp;
	}
	public void setSecondLanguageDisp(String secondLanguageDisp) {
		this.secondLanguageDisp = secondLanguageDisp;
	}
	public short getSecondLanguagePois() {
		return secondLanguagePois;
	}
	public void setSecondLanguagePois(short secondLanguagePois) {
		this.secondLanguagePois = secondLanguagePois;
	}
	public String getJournalNoDisp() {
		return journalNoDisp;
	}
	public void setJournalNoDisp(String journalNoDisp) {
		this.journalNoDisp = journalNoDisp;
	}
	public short getJournalNoPois() {
		return journalNoPois;
	}
	public void setJournalNoPois(short journalNoPois) {
		this.journalNoPois = journalNoPois;
	}
	public String getAcedamicYearDisp() {
		return acedamicYearDisp;
	}
	public void setAcedamicYearDisp(String acedamicYearDisp) {
		this.acedamicYearDisp = acedamicYearDisp;
	}
	public short getAcedamicYearPois() {
		return acedamicYearPois;
	}
	public void setAcedamicYearPois(short acedamicYearPois) {
		this.acedamicYearPois = acedamicYearPois;
	}
	public String getAdmittedThroughDisp() {
		return admittedThroughDisp;
	}
	public void setAdmittedThroughDisp(String admittedThroughDisp) {
		this.admittedThroughDisp = admittedThroughDisp;
	}
	public short getAdmittedThroughPois() {
		return admittedThroughPois;
	}
	public void setAdmittedThroughPois(short admittedThroughPois) {
		this.admittedThroughPois = admittedThroughPois;
	}
	public String getResidentCategoryDisp() {
		return residentCategoryDisp;
	}
	public void setResidentCategoryDisp(String residentCategoryDisp) {
		this.residentCategoryDisp = residentCategoryDisp;
	}
	public short getResidentCategoryPois() {
		return residentCategoryPois;
	}
	public void setResidentCategoryPois(short residentCategoryPois) {
		this.residentCategoryPois = residentCategoryPois;
	}
	public String getReligionDisp() {
		return religionDisp;
	}
	public void setReligionDisp(String religionDisp) {
		this.religionDisp = religionDisp;
	}
	public short getReligionPois() {
		return religionPois;
	}
	public void setReligionPois(short religionPois) {
		this.religionPois = religionPois;
	}
	public String getReligionOtherDisp() {
		return religionOtherDisp;
	}
	public void setReligionOtherDisp(String religionOtherDisp) {
		this.religionOtherDisp = religionOtherDisp;
	}
	public short getReligionOtherPois() {
		return religionOtherPois;
	}
	public void setReligionOtherPois(short religionOtherPois) {
		this.religionOtherPois = religionOtherPois;
	}
	public String getSubReligionDisp() {
		return subReligionDisp;
	}
	public void setSubReligionDisp(String subReligionDisp) {
		this.subReligionDisp = subReligionDisp;
	}
	public short getSubReligionPois() {
		return subReligionPois;
	}
	public void setSubReligionPois(short subReligionPois) {
		this.subReligionPois = subReligionPois;
	}
	public String getSubReligionOtherDisp() {
		return subReligionOtherDisp;
	}
	public void setSubReligionOtherDisp(String subReligionOtherDisp) {
		this.subReligionOtherDisp = subReligionOtherDisp;
	}
	public short getSubReligionOtherPois() {
		return subReligionOtherPois;
	}
	public void setSubReligionOtherPois(short subReligionOtherPois) {
		this.subReligionOtherPois = subReligionOtherPois;
	}
	public String getCastCategoryDisp() {
		return castCategoryDisp;
	}
	public void setCastCategoryDisp(String castCategoryDisp) {
		this.castCategoryDisp = castCategoryDisp;
	}
	public short getCastCategoryPois() {
		return castCategoryPois;
	}
	public void setCastCategoryPois(short castCategoryPois) {
		this.castCategoryPois = castCategoryPois;
	}
	public String getCastCategoryOtherDisp() {
		return castCategoryOtherDisp;
	}
	public void setCastCategoryOtherDisp(String castCategoryOtherDisp) {
		this.castCategoryOtherDisp = castCategoryOtherDisp;
	}
	public short getCastCategoryOtherPois() {
		return castCategoryOtherPois;
	}
	public void setCastCategoryOtherPois(short castCategoryOtherPois) {
		this.castCategoryOtherPois = castCategoryOtherPois;
	}
	public String getPlaceOfBirthDisp() {
		return placeOfBirthDisp;
	}
	public void setPlaceOfBirthDisp(String placeOfBirthDisp) {
		this.placeOfBirthDisp = placeOfBirthDisp;
	}
	public short getPlaceOfBirthPois() {
		return placeOfBirthPois;
	}
	public void setPlaceOfBirthPois(short placeOfBirthPois) {
		this.placeOfBirthPois = placeOfBirthPois;
	}
	public String getStateOfBirthDisp() {
		return stateOfBirthDisp;
	}
	public void setStateOfBirthDisp(String stateOfBirthDisp) {
		this.stateOfBirthDisp = stateOfBirthDisp;
	}
	public short getStateOfBirthPois() {
		return stateOfBirthPois;
	}
	public void setStateOfBirthPois(short stateOfBirthPois) {
		this.stateOfBirthPois = stateOfBirthPois;
	}
	public String getStateOfBirthOtherDisp() {
		return stateOfBirthOtherDisp;
	}
	public void setStateOfBirthOtherDisp(String stateOfBirthOtherDisp) {
		this.stateOfBirthOtherDisp = stateOfBirthOtherDisp;
	}
	public short getStateOfBirthOtherPois() {
		return stateOfBirthOtherPois;
	}
	public void setStateOfBirthOtherPois(short stateOfBirthOtherPois) {
		this.stateOfBirthOtherPois = stateOfBirthOtherPois;
	}
	public String getCountryOfBirthDisp() {
		return countryOfBirthDisp;
	}
	public void setCountryOfBirthDisp(String countryOfBirthDisp) {
		this.countryOfBirthDisp = countryOfBirthDisp;
	}
	public short getCountryOfBirthPois() {
		return countryOfBirthPois;
	}
	public void setCountryOfBirthPois(short countryOfBirthPois) {
		this.countryOfBirthPois = countryOfBirthPois;
	}
	public String getBelongsToDisp() {
		return belongsToDisp;
	}
	public void setBelongsToDisp(String belongsToDisp) {
		this.belongsToDisp = belongsToDisp;
	}
	public short getBelongsToPois() {
		return belongsToPois;
	}
	public void setBelongsToPois(short belongsToPois) {
		this.belongsToPois = belongsToPois;
	}
	public String getNationalityDisp() {
		return nationalityDisp;
	}
	public void setNationalityDisp(String nationalityDisp) {
		this.nationalityDisp = nationalityDisp;
	}
	public short getNationalityPois() {
		return nationalityPois;
	}
	public void setNationalityPois(short nationalityPois) {
		this.nationalityPois = nationalityPois;
	}
	public String getStudentPhoneNumberDisp() {
		return studentPhoneNumberDisp;
	}
	public void setStudentPhoneNumberDisp(String studentPhoneNumberDisp) {
		this.studentPhoneNumberDisp = studentPhoneNumberDisp;
	}
	public short getStudentPhoneNumberPois() {
		return studentPhoneNumberPois;
	}
	public void setStudentPhoneNumberPois(short studentPhoneNumberPois) {
		this.studentPhoneNumberPois = studentPhoneNumberPois;
	}
	public String getStudentMobileNumberDisp() {
		return studentMobileNumberDisp;
	}
	public void setStudentMobileNumberDisp(String studentMobileNumberDisp) {
		this.studentMobileNumberDisp = studentMobileNumberDisp;
	}
	public short getStudentMobileNumberPois() {
		return studentMobileNumberPois;
	}
	public void setStudentMobileNumberPois(short studentMobileNumberPois) {
		this.studentMobileNumberPois = studentMobileNumberPois;
	}
	public String getPassportNumberDisp() {
		return passportNumberDisp;
	}
	public void setPassportNumberDisp(String passportNumberDisp) {
		this.passportNumberDisp = passportNumberDisp;
	}
	public short getPassportNumberPois() {
		return passportNumberPois;
	}
	public void setPassportNumberPois(short passportNumberPois) {
		this.passportNumberPois = passportNumberPois;
	}
	public String getPassportIssuingCountryDisp() {
		return passportIssuingCountryDisp;
	}
	public void setPassportIssuingCountryDisp(String passportIssuingCountryDisp) {
		this.passportIssuingCountryDisp = passportIssuingCountryDisp;
	}
	public short getPassportIssuingCountryPois() {
		return passportIssuingCountryPois;
	}
	public void setPassportIssuingCountryPois(short passportIssuingCountryPois) {
		this.passportIssuingCountryPois = passportIssuingCountryPois;
	}
	public String getFirstPreferenceProgramTypeDisp() {
		return firstPreferenceProgramTypeDisp;
	}
	public void setFirstPreferenceProgramTypeDisp(
			String firstPreferenceProgramTypeDisp) {
		this.firstPreferenceProgramTypeDisp = firstPreferenceProgramTypeDisp;
	}
	public short getFirstPreferenceProgramTypePois() {
		return firstPreferenceProgramTypePois;
	}
	public void setFirstPreferenceProgramTypePois(
			short firstPreferenceProgramTypePois) {
		this.firstPreferenceProgramTypePois = firstPreferenceProgramTypePois;
	}
	public String getFirstPreferenceProgramDisp() {
		return firstPreferenceProgramDisp;
	}
	public void setFirstPreferenceProgramDisp(String firstPreferenceProgramDisp) {
		this.firstPreferenceProgramDisp = firstPreferenceProgramDisp;
	}
	public short getFirstPreferenceProgramPois() {
		return firstPreferenceProgramPois;
	}
	public void setFirstPreferenceProgramPois(short firstPreferenceProgramPois) {
		this.firstPreferenceProgramPois = firstPreferenceProgramPois;
	}
	public String getFirstPreferenceCourseDisp() {
		return firstPreferenceCourseDisp;
	}
	public void setFirstPreferenceCourseDisp(String firstPreferenceCourseDisp) {
		this.firstPreferenceCourseDisp = firstPreferenceCourseDisp;
	}
	public short getFirstPreferenceCoursePois() {
		return firstPreferenceCoursePois;
	}
	public void setFirstPreferenceCoursePois(short firstPreferenceCoursePois) {
		this.firstPreferenceCoursePois = firstPreferenceCoursePois;
	}
	public String getSecondPreferenceProgramTypeDisp() {
		return secondPreferenceProgramTypeDisp;
	}
	public void setSecondPreferenceProgramTypeDisp(
			String secondPreferenceProgramTypeDisp) {
		this.secondPreferenceProgramTypeDisp = secondPreferenceProgramTypeDisp;
	}
	public short getSecondPreferenceProgramTypePois() {
		return secondPreferenceProgramTypePois;
	}
	public void setSecondPreferenceProgramTypePois(
			short secondPreferenceProgramTypePois) {
		this.secondPreferenceProgramTypePois = secondPreferenceProgramTypePois;
	}
	public String getSecondPreferenceProgramDisp() {
		return secondPreferenceProgramDisp;
	}
	public void setSecondPreferenceProgramDisp(String secondPreferenceProgramDisp) {
		this.secondPreferenceProgramDisp = secondPreferenceProgramDisp;
	}
	public short getSecondPreferenceProgramPois() {
		return secondPreferenceProgramPois;
	}
	public void setSecondPreferenceProgramPois(short secondPreferenceProgramPois) {
		this.secondPreferenceProgramPois = secondPreferenceProgramPois;
	}
	public String getSecondPreferenceCourseDisp() {
		return secondPreferenceCourseDisp;
	}
	public void setSecondPreferenceCourseDisp(String secondPreferenceCourseDisp) {
		this.secondPreferenceCourseDisp = secondPreferenceCourseDisp;
	}
	public short getSecondPreferenceCoursePois() {
		return secondPreferenceCoursePois;
	}
	public void setSecondPreferenceCoursePois(short secondPreferenceCoursePois) {
		this.secondPreferenceCoursePois = secondPreferenceCoursePois;
	}
	public String getThirdPreferenceProgramTypeDisp() {
		return thirdPreferenceProgramTypeDisp;
	}
	public void setThirdPreferenceProgramTypeDisp(
			String thirdPreferenceProgramTypeDisp) {
		this.thirdPreferenceProgramTypeDisp = thirdPreferenceProgramTypeDisp;
	}
	public short getThirdPreferenceProgramTypePois() {
		return thirdPreferenceProgramTypePois;
	}
	public void setThirdPreferenceProgramTypePois(
			short thirdPreferenceProgramTypePois) {
		this.thirdPreferenceProgramTypePois = thirdPreferenceProgramTypePois;
	}
	public String getThirdPreferenceProgramDisp() {
		return thirdPreferenceProgramDisp;
	}
	public void setThirdPreferenceProgramDisp(String thirdPreferenceProgramDisp) {
		this.thirdPreferenceProgramDisp = thirdPreferenceProgramDisp;
	}
	public short getThirdPreferenceProgramPois() {
		return thirdPreferenceProgramPois;
	}
	public void setThirdPreferenceProgramPois(short thirdPreferenceProgramPois) {
		this.thirdPreferenceProgramPois = thirdPreferenceProgramPois;
	}
	public String getThirdPreferenceCourseDisp() {
		return thirdPreferenceCourseDisp;
	}
	public void setThirdPreferenceCourseDisp(String thirdPreferenceCourseDisp) {
		this.thirdPreferenceCourseDisp = thirdPreferenceCourseDisp;
	}
	public short getThirdPreferenceCoursePois() {
		return thirdPreferenceCoursePois;
	}
	public void setThirdPreferenceCoursePois(short thirdPreferenceCoursePois) {
		this.thirdPreferenceCoursePois = thirdPreferenceCoursePois;
	}
	public String getFirstNameofOrganisationDisp() {
		return firstNameofOrganisationDisp;
	}
	public void setFirstNameofOrganisationDisp(String firstNameofOrganisationDisp) {
		this.firstNameofOrganisationDisp = firstNameofOrganisationDisp;
	}
	public short getFirstNameofOrganisationPois() {
		return firstNameofOrganisationPois;
	}
	public void setFirstNameofOrganisationPois(short firstNameofOrganisationPois) {
		this.firstNameofOrganisationPois = firstNameofOrganisationPois;
	}
	public String getFirstDesignationDisp() {
		return firstDesignationDisp;
	}
	public void setFirstDesignationDisp(String firstDesignationDisp) {
		this.firstDesignationDisp = firstDesignationDisp;
	}
	public short getFirstDesignationPois() {
		return firstDesignationPois;
	}
	public void setFirstDesignationPois(short firstDesignationPois) {
		this.firstDesignationPois = firstDesignationPois;
	}
	public String getFirstFromDateDisp() {
		return firstFromDateDisp;
	}
	public void setFirstFromDateDisp(String firstFromDateDisp) {
		this.firstFromDateDisp = firstFromDateDisp;
	}
	public short getFirstFromDatePois() {
		return firstFromDatePois;
	}
	public void setFirstFromDatePois(short firstFromDatePois) {
		this.firstFromDatePois = firstFromDatePois;
	}
	public String getFirstToDateDisp() {
		return firstToDateDisp;
	}
	public void setFirstToDateDisp(String firstToDateDisp) {
		this.firstToDateDisp = firstToDateDisp;
	}
	public short getFirstToDatePois() {
		return firstToDatePois;
	}
	public void setFirstToDatePois(short firstToDatePois) {
		this.firstToDatePois = firstToDatePois;
	}
	public String getSecondNameofOrganisationDisp() {
		return secondNameofOrganisationDisp;
	}
	public void setSecondNameofOrganisationDisp(String secondNameofOrganisationDisp) {
		this.secondNameofOrganisationDisp = secondNameofOrganisationDisp;
	}
	public short getSecondNameofOrganisationPois() {
		return secondNameofOrganisationPois;
	}
	public void setSecondNameofOrganisationPois(short secondNameofOrganisationPois) {
		this.secondNameofOrganisationPois = secondNameofOrganisationPois;
	}
	public String getSecondDesignationDisp() {
		return secondDesignationDisp;
	}
	public void setSecondDesignationDisp(String secondDesignationDisp) {
		this.secondDesignationDisp = secondDesignationDisp;
	}
	public short getSecondDesignationPois() {
		return secondDesignationPois;
	}
	public void setSecondDesignationPois(short secondDesignationPois) {
		this.secondDesignationPois = secondDesignationPois;
	}
	public String getSecondFromDateDisp() {
		return secondFromDateDisp;
	}
	public void setSecondFromDateDisp(String secondFromDateDisp) {
		this.secondFromDateDisp = secondFromDateDisp;
	}
	public short getSecondFromDatePois() {
		return secondFromDatePois;
	}
	public void setSecondFromDatePois(short secondFromDatePois) {
		this.secondFromDatePois = secondFromDatePois;
	}
	public String getSecondToDateDisp() {
		return secondToDateDisp;
	}
	public void setSecondToDateDisp(String secondToDateDisp) {
		this.secondToDateDisp = secondToDateDisp;
	}
	public short getSecondToDatePois() {
		return secondToDatePois;
	}
	public void setSecondToDatePois(short secondToDatePois) {
		this.secondToDatePois = secondToDatePois;
	}
	public String getThirdNameofOrganisationDisp() {
		return thirdNameofOrganisationDisp;
	}
	public void setThirdNameofOrganisationDisp(String thirdNameofOrganisationDisp) {
		this.thirdNameofOrganisationDisp = thirdNameofOrganisationDisp;
	}
	public short getThirdNameofOrganisationPois() {
		return thirdNameofOrganisationPois;
	}
	public void setThirdNameofOrganisationPois(short thirdNameofOrganisationPois) {
		this.thirdNameofOrganisationPois = thirdNameofOrganisationPois;
	}
	public String getThirdDesignationDisp() {
		return thirdDesignationDisp;
	}
	public void setThirdDesignationDisp(String thirdDesignationDisp) {
		this.thirdDesignationDisp = thirdDesignationDisp;
	}
	public short getThirdDesignationPois() {
		return thirdDesignationPois;
	}
	public void setThirdDesignationPois(short thirdDesignationPois) {
		this.thirdDesignationPois = thirdDesignationPois;
	}
	public String getThirdFromDateDisp() {
		return thirdFromDateDisp;
	}
	public void setThirdFromDateDisp(String thirdFromDateDisp) {
		this.thirdFromDateDisp = thirdFromDateDisp;
	}
	public short getThirdFromDatePois() {
		return thirdFromDatePois;
	}
	public void setThirdFromDatePois(short thirdFromDatePois) {
		this.thirdFromDatePois = thirdFromDatePois;
	}
	public String getThirdToDateDisp() {
		return thirdToDateDisp;
	}
	public void setThirdToDateDisp(String thirdToDateDisp) {
		this.thirdToDateDisp = thirdToDateDisp;
	}
	public short getThirdToDatePois() {
		return thirdToDatePois;
	}
	public void setThirdToDatePois(short thirdToDatePois) {
		this.thirdToDatePois = thirdToDatePois;
	}
	public String getPermanentAddressLine1Disp() {
		return permanentAddressLine1Disp;
	}
	public void setPermanentAddressLine1Disp(String permanentAddressLine1Disp) {
		this.permanentAddressLine1Disp = permanentAddressLine1Disp;
	}
	public short getPermanentAddressLine1Pois() {
		return permanentAddressLine1Pois;
	}
	public void setPermanentAddressLine1Pois(short permanentAddressLine1Pois) {
		this.permanentAddressLine1Pois = permanentAddressLine1Pois;
	}
	public String getPermanentAddressLine2Disp() {
		return permanentAddressLine2Disp;
	}
	public void setPermanentAddressLine2Disp(String permanentAddressLine2Disp) {
		this.permanentAddressLine2Disp = permanentAddressLine2Disp;
	}
	public short getPermanentAddressLine2Pois() {
		return permanentAddressLine2Pois;
	}
	public void setPermanentAddressLine2Pois(short permanentAddressLine2Pois) {
		this.permanentAddressLine2Pois = permanentAddressLine2Pois;
	}
	public String getPermanentStateDisp() {
		return permanentStateDisp;
	}
	public void setPermanentStateDisp(String permanentStateDisp) {
		this.permanentStateDisp = permanentStateDisp;
	}
	public short getPermanentStatePois() {
		return permanentStatePois;
	}
	public void setPermanentStatePois(short permanentStatePois) {
		this.permanentStatePois = permanentStatePois;
	}
	public String getPermanentStateOthersDisp() {
		return permanentStateOthersDisp;
	}
	public void setPermanentStateOthersDisp(String permanentStateOthersDisp) {
		this.permanentStateOthersDisp = permanentStateOthersDisp;
	}
	public short getPermanentStateOthersPois() {
		return permanentStateOthersPois;
	}
	public void setPermanentStateOthersPois(short permanentStateOthersPois) {
		this.permanentStateOthersPois = permanentStateOthersPois;
	}
	public String getPermanentCityDisp() {
		return permanentCityDisp;
	}
	public void setPermanentCityDisp(String permanentCityDisp) {
		this.permanentCityDisp = permanentCityDisp;
	}
	public short getPermanentCityPois() {
		return permanentCityPois;
	}
	public void setPermanentCityPois(short permanentCityPois) {
		this.permanentCityPois = permanentCityPois;
	}
	public String getPermanentCountryDisp() {
		return permanentCountryDisp;
	}
	public void setPermanentCountryDisp(String permanentCountryDisp) {
		this.permanentCountryDisp = permanentCountryDisp;
	}
	public short getPermanentCountryPois() {
		return permanentCountryPois;
	}
	public void setPermanentCountryPois(short permanentCountryPois) {
		this.permanentCountryPois = permanentCountryPois;
	}
	public String getPermanentZipCodeDisp() {
		return permanentZipCodeDisp;
	}
	public void setPermanentZipCodeDisp(String permanentZipCodeDisp) {
		this.permanentZipCodeDisp = permanentZipCodeDisp;
	}
	public short getPermanentZipCodePois() {
		return permanentZipCodePois;
	}
	public void setPermanentZipCodePois(short permanentZipCodePois) {
		this.permanentZipCodePois = permanentZipCodePois;
	}
	public String getCurrentAddressLine1Disp() {
		return currentAddressLine1Disp;
	}
	public void setCurrentAddressLine1Disp(String currentAddressLine1Disp) {
		this.currentAddressLine1Disp = currentAddressLine1Disp;
	}
	public short getCurrentAddressLine1Pois() {
		return currentAddressLine1Pois;
	}
	public void setCurrentAddressLine1Pois(short currentAddressLine1Pois) {
		this.currentAddressLine1Pois = currentAddressLine1Pois;
	}
	public String getCurrentAddressLine2Disp() {
		return currentAddressLine2Disp;
	}
	public void setCurrentAddressLine2Disp(String currentAddressLine2Disp) {
		this.currentAddressLine2Disp = currentAddressLine2Disp;
	}
	public short getCurrentAddressLine2Pois() {
		return currentAddressLine2Pois;
	}
	public void setCurrentAddressLine2Pois(short currentAddressLine2Pois) {
		this.currentAddressLine2Pois = currentAddressLine2Pois;
	}
	public String getCurrentStateDisp() {
		return currentStateDisp;
	}
	public void setCurrentStateDisp(String currentStateDisp) {
		this.currentStateDisp = currentStateDisp;
	}
	public short getCurrentStatePois() {
		return currentStatePois;
	}
	public void setCurrentStatePois(short currentStatePois) {
		this.currentStatePois = currentStatePois;
	}
	public String getCurrentStateOthersDisp() {
		return currentStateOthersDisp;
	}
	public void setCurrentStateOthersDisp(String currentStateOthersDisp) {
		this.currentStateOthersDisp = currentStateOthersDisp;
	}
	public short getCurrentStateOthersPois() {
		return currentStateOthersPois;
	}
	public void setCurrentStateOthersPois(short currentStateOthersPois) {
		this.currentStateOthersPois = currentStateOthersPois;
	}
	public String getCurrentCityDisp() {
		return currentCityDisp;
	}
	public void setCurrentCityDisp(String currentCityDisp) {
		this.currentCityDisp = currentCityDisp;
	}
	public short getCurrentCityPois() {
		return currentCityPois;
	}
	public void setCurrentCityPois(short currentCityPois) {
		this.currentCityPois = currentCityPois;
	}
	public String getCurrentCountryDisp() {
		return currentCountryDisp;
	}
	public void setCurrentCountryDisp(String currentCountryDisp) {
		this.currentCountryDisp = currentCountryDisp;
	}
	public short getCurrentCountryPois() {
		return currentCountryPois;
	}
	public void setCurrentCountryPois(short currentCountryPois) {
		this.currentCountryPois = currentCountryPois;
	}
	public String getCurrentZipCodeDisp() {
		return currentZipCodeDisp;
	}
	public void setCurrentZipCodeDisp(String currentZipCodeDisp) {
		this.currentZipCodeDisp = currentZipCodeDisp;
	}
	public short getCurrentZipCodePois() {
		return currentZipCodePois;
	}
	public void setCurrentZipCodePois(short currentZipCodePois) {
		this.currentZipCodePois = currentZipCodePois;
	}
	public String getFathersNameDisp() {
		return fathersNameDisp;
	}
	public void setFathersNameDisp(String fathersNameDisp) {
		this.fathersNameDisp = fathersNameDisp;
	}
	public short getFathersNamePois() {
		return fathersNamePois;
	}
	public void setFathersNamePois(short fathersNamePois) {
		this.fathersNamePois = fathersNamePois;
	}
	public String getFathersEducationDisp() {
		return fathersEducationDisp;
	}
	public void setFathersEducationDisp(String fathersEducationDisp) {
		this.fathersEducationDisp = fathersEducationDisp;
	}
	public short getFathersEducationPois() {
		return fathersEducationPois;
	}
	public void setFathersEducationPois(short fathersEducationPois) {
		this.fathersEducationPois = fathersEducationPois;
	}
	public String getFathersIncomeDisp() {
		return fathersIncomeDisp;
	}
	public void setFathersIncomeDisp(String fathersIncomeDisp) {
		this.fathersIncomeDisp = fathersIncomeDisp;
	}
	public short getFathersIncomePois() {
		return fathersIncomePois;
	}
	public void setFathersIncomePois(short fathersIncomePois) {
		this.fathersIncomePois = fathersIncomePois;
	}
	public String getFathersCurrencyDisp() {
		return fathersCurrencyDisp;
	}
	public void setFathersCurrencyDisp(String fathersCurrencyDisp) {
		this.fathersCurrencyDisp = fathersCurrencyDisp;
	}
	public short getFathersCurrencyPois() {
		return fathersCurrencyPois;
	}
	public void setFathersCurrencyPois(short fathersCurrencyPois) {
		this.fathersCurrencyPois = fathersCurrencyPois;
	}
	public String getFathersOccupationDisp() {
		return fathersOccupationDisp;
	}
	public void setFathersOccupationDisp(String fathersOccupationDisp) {
		this.fathersOccupationDisp = fathersOccupationDisp;
	}
	public short getFathersOccupationPois() {
		return fathersOccupationPois;
	}
	public void setFathersOccupationPois(short fathersOccupationPois) {
		this.fathersOccupationPois = fathersOccupationPois;
	}
	public String getFathersEmailDisp() {
		return fathersEmailDisp;
	}
	public void setFathersEmailDisp(String fathersEmailDisp) {
		this.fathersEmailDisp = fathersEmailDisp;
	}
	public short getFathersEmailPois() {
		return fathersEmailPois;
	}
	public void setFathersEmailPois(short fathersEmailPois) {
		this.fathersEmailPois = fathersEmailPois;
	}
	public String getMothersNameDisp() {
		return mothersNameDisp;
	}
	public void setMothersNameDisp(String mothersNameDisp) {
		this.mothersNameDisp = mothersNameDisp;
	}
	public short getMothersNamePois() {
		return mothersNamePois;
	}
	public void setMothersNamePois(short mothersNamePois) {
		this.mothersNamePois = mothersNamePois;
	}
	public String getMothersEducationDisp() {
		return mothersEducationDisp;
	}
	public void setMothersEducationDisp(String mothersEducationDisp) {
		this.mothersEducationDisp = mothersEducationDisp;
	}
	public short getMothersEducationPois() {
		return mothersEducationPois;
	}
	public void setMothersEducationPois(short mothersEducationPois) {
		this.mothersEducationPois = mothersEducationPois;
	}
	public String getMothersIncomeDisp() {
		return mothersIncomeDisp;
	}
	public void setMothersIncomeDisp(String mothersIncomeDisp) {
		this.mothersIncomeDisp = mothersIncomeDisp;
	}
	public short getMothersIncomePois() {
		return mothersIncomePois;
	}
	public void setMothersIncomePois(short mothersIncomePois) {
		this.mothersIncomePois = mothersIncomePois;
	}
	public String getMothersCurrencyDisp() {
		return mothersCurrencyDisp;
	}
	public void setMothersCurrencyDisp(String mothersCurrencyDisp) {
		this.mothersCurrencyDisp = mothersCurrencyDisp;
	}
	public short getMothersCurrencyPois() {
		return mothersCurrencyPois;
	}
	public void setMothersCurrencyPois(short mothersCurrencyPois) {
		this.mothersCurrencyPois = mothersCurrencyPois;
	}
	public String getMothersOccupationDisp() {
		return mothersOccupationDisp;
	}
	public void setMothersOccupationDisp(String mothersOccupationDisp) {
		this.mothersOccupationDisp = mothersOccupationDisp;
	}
	public short getMothersOccupationPois() {
		return mothersOccupationPois;
	}
	public void setMothersOccupationPois(short mothersOccupationPois) {
		this.mothersOccupationPois = mothersOccupationPois;
	}
	public String getMothersEmailDisp() {
		return mothersEmailDisp;
	}
	public void setMothersEmailDisp(String mothersEmailDisp) {
		this.mothersEmailDisp = mothersEmailDisp;
	}
	public short getMothersEmailPois() {
		return mothersEmailPois;
	}
	public void setMothersEmailPois(short mothersEmailPois) {
		this.mothersEmailPois = mothersEmailPois;
	}
	public String getParentAdressLine1Disp() {
		return parentAdressLine1Disp;
	}
	public void setParentAdressLine1Disp(String parentAdressLine1Disp) {
		this.parentAdressLine1Disp = parentAdressLine1Disp;
	}
	public short getParentAdressLine1Pois() {
		return parentAdressLine1Pois;
	}
	public void setParentAdressLine1Pois(short parentAdressLine1Pois) {
		this.parentAdressLine1Pois = parentAdressLine1Pois;
	}
	public String getParentAdressLine2Disp() {
		return parentAdressLine2Disp;
	}
	public void setParentAdressLine2Disp(String parentAdressLine2Disp) {
		this.parentAdressLine2Disp = parentAdressLine2Disp;
	}
	public short getParentAdressLine2Pois() {
		return parentAdressLine2Pois;
	}
	public void setParentAdressLine2Pois(short parentAdressLine2Pois) {
		this.parentAdressLine2Pois = parentAdressLine2Pois;
	}
	public String getParentAdressLine3Disp() {
		return parentAdressLine3Disp;
	}
	public void setParentAdressLine3Disp(String parentAdressLine3Disp) {
		this.parentAdressLine3Disp = parentAdressLine3Disp;
	}
	public short getParentAdressLine3Pois() {
		return parentAdressLine3Pois;
	}
	public void setParentAdressLine3Pois(short parentAdressLine3Pois) {
		this.parentAdressLine3Pois = parentAdressLine3Pois;
	}
	public String getParentCityDisp() {
		return parentCityDisp;
	}
	public void setParentCityDisp(String parentCityDisp) {
		this.parentCityDisp = parentCityDisp;
	}
	public short getParentCityPois() {
		return parentCityPois;
	}
	public void setParentCityPois(short parentCityPois) {
		this.parentCityPois = parentCityPois;
	}
	public String getParentStateDisp() {
		return parentStateDisp;
	}
	public void setParentStateDisp(String parentStateDisp) {
		this.parentStateDisp = parentStateDisp;
	}
	public short getParentStatePois() {
		return parentStatePois;
	}
	public void setParentStatePois(short parentStatePois) {
		this.parentStatePois = parentStatePois;
	}
	public String getParentStateOtherDisp() {
		return parentStateOtherDisp;
	}
	public void setParentStateOtherDisp(String parentStateOtherDisp) {
		this.parentStateOtherDisp = parentStateOtherDisp;
	}
	public short getParentStateOtherPois() {
		return parentStateOtherPois;
	}
	public void setParentStateOtherPois(short parentStateOtherPois) {
		this.parentStateOtherPois = parentStateOtherPois;
	}
	public String getParentCountryDisp() {
		return parentCountryDisp;
	}
	public void setParentCountryDisp(String parentCountryDisp) {
		this.parentCountryDisp = parentCountryDisp;
	}
	public short getParentCountryPois() {
		return parentCountryPois;
	}
	public void setParentCountryPois(short parentCountryPois) {
		this.parentCountryPois = parentCountryPois;
	}
	public short getParentZipCodePois() {
		return parentZipCodePois;
	}
	public void setParentZipCodePois(short parentZipCodePois) {
		this.parentZipCodePois = parentZipCodePois;
	}
	public String getParentPhoneDisp() {
		return parentPhoneDisp;
	}
	public void setParentPhoneDisp(String parentPhoneDisp) {
		this.parentPhoneDisp = parentPhoneDisp;
	}
	public short getParentPhonePois() {
		return parentPhonePois;
	}
	public void setParentPhonePois(short parentPhonePois) {
		this.parentPhonePois = parentPhonePois;
	}
	public String getParentMobileNoDisp() {
		return parentMobileNoDisp;
	}
	public void setParentMobileNoDisp(String parentMobileNoDisp) {
		this.parentMobileNoDisp = parentMobileNoDisp;
	}
	public short getParentMobileNoPois() {
		return parentMobileNoPois;
	}
	public void setParentMobileNoPois(short parentMobileNoPois) {
		this.parentMobileNoPois = parentMobileNoPois;
	}
	public String getProgramNameDisp() {
		return programNameDisp;
	}
	public void setProgramNameDisp(String programNameDisp) {
		this.programNameDisp = programNameDisp;
	}
	public short getProgramNamePois() {
		return programNamePois;
	}
	public void setProgramNamePois(short programNamePois) {
		this.programNamePois = programNamePois;
	}
	public String getProgramTypeNameDisp() {
		return programTypeNameDisp;
	}
	public void setProgramTypeNameDisp(String programTypeNameDisp) {
		this.programTypeNameDisp = programTypeNameDisp;
	}
	public short getProgramTypeNamePois() {
		return programTypeNamePois;
	}
	public void setProgramTypeNamePois(short programTypeNamePois) {
		this.programTypeNamePois = programTypeNamePois;
	}
	public String getPassportValidUpToDisp() {
		return passportValidUpToDisp;
	}
	public void setPassportValidUpToDisp(String passportValidUpToDisp) {
		this.passportValidUpToDisp = passportValidUpToDisp;
	}
	public short getPassportValidUpToPois() {
		return passportValidUpToPois;
	}
	public void setPassportValidUpToPois(short passportValidUpToPois) {
		this.passportValidUpToPois = passportValidUpToPois;
	}
	public String getAcedamicYear() {
		return acedamicYear;
	}
	public void setAcedamicYear(String acedamicYear) {
		this.acedamicYear = acedamicYear;
	}
	public String getApplnNoDisp() {
		return applnNoDisp;
	}
	public void setApplnNoDisp(String applnNoDisp) {
		this.applnNoDisp = applnNoDisp;
	}
	public short getApplnNoPois() {
		return applnNoPois;
	}
	public void setApplnNoPois(short applnNoPois) {
		this.applnNoPois = applnNoPois;
	}
	public String getChallanNoDisp() {
		return challanNoDisp;
	}
	public void setChallanNoDisp(String challanNoDisp) {
		this.challanNoDisp = challanNoDisp;
	}
	public short getChallanNoPois() {
		return challanNoPois;
	}
	public void setChallanNoPois(short challanNoPois) {
		this.challanNoPois = challanNoPois;
	}
	public String getGenderDisp() {
		return genderDisp;
	}
	public void setGenderDisp(String genderDisp) {
		this.genderDisp = genderDisp;
	}
	public short getGenderPois() {
		return genderPois;
	}
	public void setGenderPois(short genderPois) {
		this.genderPois = genderPois;
	}
	public String getBloodGroupDisp() {
		return bloodGroupDisp;
	}
	public void setBloodGroupDisp(String bloodGroupDisp) {
		this.bloodGroupDisp = bloodGroupDisp;
	}
	public short getBloodGroupPois() {
		return bloodGroupPois;
	}
	public void setBloodGroupPois(short bloodGroupPois) {
		this.bloodGroupPois = bloodGroupPois;
	}
	public String getEmailDisp() {
		return emailDisp;
	}
	public void setEmailDisp(String emailDisp) {
		this.emailDisp = emailDisp;
	}
	public short getEmailPois() {
		return emailPois;
	}
	public void setEmailPois(short emailPois) {
		this.emailPois = emailPois;
	}
	public String getTotalWeightageDisp() {
		return totalWeightageDisp;
	}
	public void setTotalWeightageDisp(String totalWeightageDisp) {
		this.totalWeightageDisp = totalWeightageDisp;
	}
	public short getTotalWeightagePois() {
		return totalWeightagePois;
	}
	public void setTotalWeightagePois(short totalWeightagePois) {
		this.totalWeightagePois = totalWeightagePois;
	}
	public String getNameDisp() {
		return nameDisp;
	}
	public void setNameDisp(String nameDisp) {
		this.nameDisp = nameDisp;
	}
	public short getNamePois() {
		return namePois;
	}
	public void setNamePois(short namePois) {
		this.namePois = namePois;
	}
	public String getDateOfBirthDisp() {
		return dateOfBirthDisp;
	}
	public void setDateOfBirthDisp(String dateOfBirthDisp) {
		this.dateOfBirthDisp = dateOfBirthDisp;
	}
	public short getDateOfBirthPois() {
		return dateOfBirthPois;
	}
	public void setDateOfBirthPois(short dateOfBirthPois) {
		this.dateOfBirthPois = dateOfBirthPois;
	}
	
	public String getPercentage() {
		return percentage;
	}
	
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public short getSeatNoPois() {
		return seatNoPois;
	}
	public void setSeatNoPois(short seatNoPois) {
		this.seatNoPois = seatNoPois;
	}
	public String getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}
	public String getSeatNoDisp() {
		return seatNoDisp;
	}
	public void setSeatNoDisp(String seatNoDisp) {
		this.seatNoDisp = seatNoDisp;
	}
	public String getExamCenterDisp() {
		return examCenterDisp;
	}
	public void setExamCenterDisp(String examCenterDisp) {
		this.examCenterDisp = examCenterDisp;
	}
	public short getExamCenterPois() {
		return examCenterPois;
	}
	public void setExamCenterPois(short examCenterPois) {
		this.examCenterPois = examCenterPois;
	}
	public String getExamCenter() {
		return examCenter;
	}
	public void setExamCenter(String examCenter) {
		this.examCenter = examCenter;
	}
	public TreeMap<String, String> getGradeMap() {
		return gradeMap;
	}
	public void setGradeMap(TreeMap<String, String> gradeMap) {
		this.gradeMap = gradeMap;
	}
	public TreeMap<String, String> getDocTypePerMap() {
		return docTypePerMap;
	}
	public void setDocTypePerMap(TreeMap<String, String> docTypePerMap) {
		this.docTypePerMap = docTypePerMap;
	}
	public TreeMap<String, String> getDocTypeExamNameMap() {
		return docTypeExamNameMap;
	}
	public void setDocTypeExamNameMap(TreeMap<String, String> docTypeExamNameMap) {
		this.docTypeExamNameMap = docTypeExamNameMap;
	}
	public TreeMap<String, String> getUniMap() {
		return uniMap;
	}
	public void setUniMap(TreeMap<String, String> uniMap) {
		this.uniMap = uniMap;
	}
	public TreeMap<String, String> getInstMap() {
		return instMap;
	}
	public void setInstMap(TreeMap<String, String> instMap) {
		this.instMap = instMap;
	}
	public String getInterviewResultDisp() {
		return interviewResultDisp;
	}
	public void setInterviewResultDisp(String interviewResultDisp) {
		this.interviewResultDisp = interviewResultDisp;
	}
	public String getPrvExamDetDisp() {
		return prvExamDetDisp;
	}
	public void setPrvExamDetDisp(String prvExamDetDisp) {
		this.prvExamDetDisp = prvExamDetDisp;
	}
	public boolean isDispStatus() {
		return dispStatus;
	}
	public void setDispStatus(boolean dispStatus) {
		this.dispStatus = dispStatus;
	}
	public String getAdmStatus() {
		return admStatus;
	}
	public void setAdmStatus(String admStatus) {
		this.admStatus = admStatus;
	}
	public String getIntDateTimeDisp() {
		return intDateTimeDisp;
	}
	public void setIntDateTimeDisp(String intDateTimeDisp) {
		this.intDateTimeDisp = intDateTimeDisp;
	}
	public TreeMap<String, String> getIntDateMap() {
		return intDateMap;
	}
	public void setIntDateMap(TreeMap<String, String> intDateMap) {
		this.intDateMap = intDateMap;
	}
	public TreeMap<String, String> getIntTimeMap() {
		return intTimeMap;
	}
	public void setIntTimeMap(TreeMap<String, String> intTimeMap) {
		this.intTimeMap = intTimeMap;
	}
	public TreeMap<String, String> getYearMap() {
		return yearMap;
	}
	public void setYearMap(TreeMap<String, String> yearMap) {
		this.yearMap = yearMap;
	}
	public String getPreReqDisp() {
		return preReqDisp;
	}
	public void setPreReqDisp(String preReqDisp) {
		this.preReqDisp = preReqDisp;
	}
	public TreeMap<String, String> getpExamMap() {
		return pExamMap;
	}
	public void setpExamMap(TreeMap<String, String> pExamMap) {
		this.pExamMap = pExamMap;
	}
	public TreeMap<String, String> getpHeadMap() {
		return pHeadMap;
	}
	public void setpHeadMap(TreeMap<String, String> pHeadMap) {
		this.pHeadMap = pHeadMap;
	}
	public TreeMap<String, String> getpRollMap() {
		return pRollMap;
	}
	public void setpRollMap(TreeMap<String, String> pRollMap) {
		this.pRollMap = pRollMap;
	}
	public TreeMap<String, String> getpYopMap() {
		return pYopMap;
	}
	public void setpYopMap(TreeMap<String, String> pYopMap) {
		this.pYopMap = pYopMap;
	}
	public TreeMap<String, String> getpMopMap() {
		return pMopMap;
	}
	public void setpMopMap(TreeMap<String, String> pMopMap) {
		this.pMopMap = pMopMap;
	}
	public String getMotherTongueDisp() {
		return motherTongueDisp;
	}
	public void setMotherTongueDisp(String motherTongueDisp) {
		this.motherTongueDisp = motherTongueDisp;
	}
	public short getMotherTonguePois() {
		return motherTonguePois;
	}
	public void setMotherTonguePois(short motherTonguePois) {
		this.motherTonguePois = motherTonguePois;
	}
	public String getMotherTongue() {
		return motherTongue;
	}
	public void setMotherTongue(String motherTongue) {
		this.motherTongue = motherTongue;
	}
	public String getAdmissionStatusDisp() {
		return admissionStatusDisp;
	}
	public void setAdmissionStatusDisp(String admissionStatusDisp) {
		this.admissionStatusDisp = admissionStatusDisp;
	}
	public short getAdmissionStatusPois() {
		return admissionStatusPois;
	}
	public void setAdmissionStatusPois(short admissionStatusPois) {
		this.admissionStatusPois = admissionStatusPois;
	}
	public String getIsAdmittedDisp() {
		return isAdmittedDisp;
	}
	public void setIsAdmittedDisp(String isAdmittedDisp) {
		this.isAdmittedDisp = isAdmittedDisp;
	}
	public short getIsAdmittedPois() {
		return isAdmittedPois;
	}
	public void setIsAdmittedPois(short isAdmittedPois) {
		this.isAdmittedPois = isAdmittedPois;
	}
	public String getAdmissionStatus() {
		return admissionStatus;
	}
	public void setAdmissionStatus(String admissionStatus) {
		this.admissionStatus = admissionStatus;
	}
	public String getIsAdmitted() {
		return isAdmitted;
	}
	public void setIsAdmitted(String isAdmitted) {
		this.isAdmitted = isAdmitted;
	}
	public String getCurrentClass() {
		return currentClass;
	}
	public void setCurrentClass(String currentClass) {
		this.currentClass = currentClass;
	}

	public String getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}
	public String getIsFinalMeritApproved() {
		return isFinalMeritApproved;
	}
	public void setIsFinalMeritApproved(String isFinalMeritApproved) {
		this.isFinalMeritApproved = isFinalMeritApproved;
	}
	public String getIsSelectedDisp() {
		return isSelectedDisp;
	}
	public void setIsSelectedDisp(String isSelectedDisp) {
		this.isSelectedDisp = isSelectedDisp;
	}
	public short getIsSelectedPois() {
		return isSelectedPois;
	}
	public void setIsSelectedPois(short isSelectedPois) {
		this.isSelectedPois = isSelectedPois;
	}
	public String getIsFinalMeritApprovedDisp() {
		return isFinalMeritApprovedDisp;
	}
	public void setIsFinalMeritApprovedDisp(String isFinalMeritApprovedDisp) {
		this.isFinalMeritApprovedDisp = isFinalMeritApprovedDisp;
	}
	public short getIsFinalMeritApprovedPois() {
		return isFinalMeritApprovedPois;
	}
	public void setIsFinalMeritApprovedPois(short isFinalMeritApprovedPois) {
		this.isFinalMeritApprovedPois = isFinalMeritApprovedPois;
	}
	public Short getPreReqDispPois() {
		return preReqDispPois;
	}
	public void setPreReqDispPois(Short preReqDispPois) {
		this.preReqDispPois = preReqDispPois;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserNameDisp() {
		return userNameDisp;
	}
	public void setUserNameDisp(String userNameDisp) {
		this.userNameDisp = userNameDisp;
	}
	public short getUserNamePois() {
		return userNamePois;
	}
	public void setUserNamePois(short userNamePois) {
		this.userNamePois = userNamePois;
	}
	public String getPasswordDisp() {
		return passwordDisp;
	}
	public void setPasswordDisp(String passwordDisp) {
		this.passwordDisp = passwordDisp;
	}
	public Short getPasswordPois() {
		return passwordPois;
	}
	public void setPasswordPois(Short passwordPois) {
		this.passwordPois = passwordPois;
	}
	public String getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(String appliedDate) {
		this.appliedDate = appliedDate;
	}
	public String getAppliedDateDisp() {
		return appliedDateDisp;
	}
	public void setAppliedDateDisp(String appliedDateDisp) {
		this.appliedDateDisp = appliedDateDisp;
	}
	public short getAppliedDatePois() {
		return appliedDatePois;
	}
	public void setAppliedDatePois(short appliedDatePois) {
		this.appliedDatePois = appliedDatePois;
	}
	public String getChallanPaymentDate() {
		return challanPaymentDate;
	}
	public void setChallanPaymentDate(String challanPaymentDate) {
		this.challanPaymentDate = challanPaymentDate;
	}
	public String getChallanPaymentDateDisp() {
		return challanPaymentDateDisp;
	}
	public void setChallanPaymentDateDisp(String challanPaymentDateDisp) {
		this.challanPaymentDateDisp = challanPaymentDateDisp;
	}
	public Short getChallanPaymentDatePois() {
		return challanPaymentDatePois;
	}
	public void setChallanPaymentDatePois(Short challanPaymentDatePois) {
		this.challanPaymentDatePois = challanPaymentDatePois;
	}
	public String getFeeChallanDate() {
		return feeChallanDate;
	}
	public void setFeeChallanDate(String feeChallanDate) {
		this.feeChallanDate = feeChallanDate;
	}
	public String getFeeChallanDateDisp() {
		return feeChallanDateDisp;
	}
	public void setFeeChallanDateDisp(String feeChallanDateDisp) {
		this.feeChallanDateDisp = feeChallanDateDisp;
	}
	public short getFeeChallanDatePois() {
		return feeChallanDatePois;
	}
	public void setFeeChallanDatePois(short feeChallanDatePois) {
		this.feeChallanDatePois = feeChallanDatePois;
	}
	
	public String getFeeChallanNoDisp() {
		return feeChallanNoDisp;
	}
	public void setFeeChallanNoDisp(String feeChallanNoDisp) {
		this.feeChallanNoDisp = feeChallanNoDisp;
	}
	public Short getFeeChallanNoPois() {
		return feeChallanNoPois;
	}
	public void setFeeChallanNoPois(Short feeChallanNoPois) {
		this.feeChallanNoPois = feeChallanNoPois;
	}
	public String getTotalFeePaid() {
		return totalFeePaid;
	}
	public void setTotalFeePaid(String totalFeePaid) {
		this.totalFeePaid = totalFeePaid;
	}
	public String getTotalFeePaidDisp() {
		return totalFeePaidDisp;
	}
	public void setTotalFeePaidDisp(String totalFeePaidDisp) {
		this.totalFeePaidDisp = totalFeePaidDisp;
	}
	public short getTotalFeePaidPois() {
		return totalFeePaidPois;
	}
	public void setTotalFeePaidPois(short totalFeePaidPois) {
		this.totalFeePaidPois = totalFeePaidPois;
	}
	
	public String getIsHandicapedDisp() {
		return isHandicapedDisp;
	}
	public void setIsHandicapedDisp(String isHandicapedDisp) {
		this.isHandicapedDisp = isHandicapedDisp;
	}
	public Short getIsHandicapedPois() {
		return isHandicapedPois;
	}
	public void setIsHandicapedPois(Short isHandicapedPois) {
		this.isHandicapedPois = isHandicapedPois;
	}
	public String getHandicapDetails() {
		return handicapDetails;
	}
	public void setHandicapDetails(String handicapDetails) {
		this.handicapDetails = handicapDetails;
	}
	public String getHandicapDetailsDisp() {
		return handicapDetailsDisp;
	}
	public void setHandicapDetailsDisp(String handicapDetailsDisp) {
		this.handicapDetailsDisp = handicapDetailsDisp;
	}
	public Short getHandicapDetailsPois() {
		return handicapDetailsPois;
	}
	public void setHandicapDetailsPois(Short handicapDetailsPois) {
		this.handicapDetailsPois = handicapDetailsPois;
	}
	public String getFeeChallanNo() {
		return feeChallanNo;
	}
	public void setFeeChallanNo(String feeChallanNo) {
		this.feeChallanNo = feeChallanNo;
	}
	public void setIsHandicaped(Boolean isHandicaped) {
		this.isHandicaped = isHandicaped;
	}
	public Boolean getIsHandicaped() {
		return isHandicaped;
	}
	public String getIsChallanVerified() {
		return isChallanVerified;
	}
	public void setIsChallanVerified(String isChallanVerified) {
		this.isChallanVerified = isChallanVerified;
	}
	public String getChallanVerfiedDisp() {
		return challanVerfiedDisp;
	}
	public void setChallanVerfiedDisp(String challanVerfiedDisp) {
		this.challanVerfiedDisp = challanVerfiedDisp;
	}
	public short getChallanVerifiedPois() {
		return challanVerifiedPois;
	}
	public void setChallanVerifiedPois(short challanVerifiedPois) {
		this.challanVerifiedPois = challanVerifiedPois;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getPhotoDisp() {
		return photoDisp;
	}
	public void setPhotoDisp(String photoDisp) {
		this.photoDisp = photoDisp;
	}
	public short getPhotoPois() {
		return photoPois;
	}
	public void setPhotoPois(short photoPois) {
		this.photoPois = photoPois;
	}
	public TreeMap<String, String> getCommentsMap() {
		return commentsMap;
	}
	public void setCommentsMap(TreeMap<String, String> commentsMap) {
		this.commentsMap = commentsMap;
	}
	public String getSpecializationPrefered() {
		return specializationPrefered;
	}
	public void setSpecializationPrefered(String specializationPrefered) {
		this.specializationPrefered = specializationPrefered;
	}
	public String getSpecializationPreferedDisp() {
		return specializationPreferedDisp;
	}
	public void setSpecializationPreferedDisp(String specializationPreferedDisp) {
		this.specializationPreferedDisp = specializationPreferedDisp;
	}
	public short getSpecializationPreferedPois() {
		return specializationPreferedPois;
	}
	public void setSpecializationPreferedPois(short specializationPreferedPois) {
		this.specializationPreferedPois = specializationPreferedPois;
	}
	public String getBackLogs() {
		return backLogs;
	}
	public void setBackLogs(String backLogs) {
		this.backLogs = backLogs;
	}
	public String getBackLogsDisp() {
		return backLogsDisp;
	}
	public void setBackLogsDisp(String backLogsDisp) {
		this.backLogsDisp = backLogsDisp;
	}
	public short getBackLogsPois() {
		return backLogsPois;
	}
	public void setBackLogsPois(short backLogsPois) {
		this.backLogsPois = backLogsPois;
	}
	public String getCanceled() {
		return canceled;
	}
	public void setCanceled(String canceled) {
		this.canceled = canceled;
	}
	public String getCanceledDisp() {
		return canceledDisp;
	}
	public void setCanceledDisp(String canceledDisp) {
		this.canceledDisp = canceledDisp;
	}
	public short getCanceledPois() {
		return canceledPois;
	}
	public void setCanceledPois(short canceledPois) {
		this.canceledPois = canceledPois;
	}
	public String getNewbackLogs() {
		return newbackLogs;
	}
	public void setNewbackLogs(String newbackLogs) {
		this.newbackLogs = newbackLogs;
	}
	public String getNewbackLogsDisp() {
		return newbackLogsDisp;
	}
	public void setNewbackLogsDisp(String newbackLogsDisp) {
		this.newbackLogsDisp = newbackLogsDisp;
	}
	public short getNewbackLogsPois() {
		return newbackLogsPois;
	}
	public void setNewbackLogsPois(short newbackLogsPois) {
		this.newbackLogsPois = newbackLogsPois;
	}
	public String getApplicantFeedback() {
		return applicantFeedback;
	}
	public void setApplicantFeedback(String applicantFeedback) {
		this.applicantFeedback = applicantFeedback;
	}
	public String getApplicantFeedbackDisp() {
		return applicantFeedbackDisp;
	}
	public void setApplicantFeedbackDisp(String applicantFeedbackDisp) {
		this.applicantFeedbackDisp = applicantFeedbackDisp;
	}
	public short getApplicantFeedbackPois() {
		return applicantFeedbackPois;
	}
	public void setApplicantFeedbackPois(short applicantFeedbackPois) {
		this.applicantFeedbackPois = applicantFeedbackPois;
	}
	public void setUniversityEmail(String universityEmail) {
		this.universityEmail = universityEmail;
	}
	public String getUniversityEmail() {
		return universityEmail;
	}
	public void setUniversityEmailDisp(String universityEmailDisp) {
		this.universityEmailDisp = universityEmailDisp;
	}
	public String getUniversityEmailDisp() {
		return universityEmailDisp;
	}
	public void setUniversityEmailPois(short universityEmailPois) {
		this.universityEmailPois = universityEmailPois;
	}
	public short getUniversityEmailPois() {
		return universityEmailPois;
	}
	public String getComedk() {
		return comedk;
	}
	public void setComedk(String comedk) {
		this.comedk = comedk;
	}
	public String getComedkDisp() {
		return comedkDisp;
	}
	public void setComedkDisp(String comedkDisp) {
		this.comedkDisp = comedkDisp;
	}
	public short getComedkPois() {
		return comedkPois;
	}
	public void setComedkPois(short comedkPois) {
		this.comedkPois = comedkPois;
	}
	public String getCurrentClassDisp() {
		return currentClassDisp;
	}
	public void setCurrentClassDisp(String currentClassDisp) {
		this.currentClassDisp = currentClassDisp;
	}
	public short getCurrentClassPois() {
		return currentClassPois;
	}
	public void setCurrentClassPois(short currentClassPois) {
		this.currentClassPois = currentClassPois;
	}
	public String getParentZipCodeDisp() {
		return parentZipCodeDisp;
	}
	public void setParentZipCodeDisp(String parentZipCodeDisp) {
		this.parentZipCodeDisp = parentZipCodeDisp;
	}
	public String getTotalExpYear() {
		return totalExpYear;
	}
	public void setTotalExpYear(String totalExpYear) {
		this.totalExpYear = totalExpYear;
	}
	public String getTotalExpYearDisp() {
		return totalExpYearDisp;
	}
	public void setTotalExpYearDisp(String totalExpYearDisp) {
		this.totalExpYearDisp = totalExpYearDisp;
	}
	public short getTotalExpYearPois() {
		return totalExpYearPois;
	}
	public void setTotalExpYearPois(short totalExpYearPois) {
		this.totalExpYearPois = totalExpYearPois;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarksDisp() {
		return remarksDisp;
	}
	public void setRemarksDisp(String remarksDisp) {
		this.remarksDisp = remarksDisp;
	}
	public short getRemarkPois() {
		return remarkPois;
	}
	public void setRemarkPois(short remarkPois) {
		this.remarkPois = remarkPois;
	}
	public String getSelectionProcessDate() {
		return selectionProcessDate;
	}
	public void setSelectionProcessDate(String selectionProcessDate) {
		this.selectionProcessDate = selectionProcessDate;
	}
	public String getSelectionProcessDateDisp() {
		return selectionProcessDateDisp;
	}
	public void setSelectionProcessDateDisp(String selectionProcessDateDisp) {
		this.selectionProcessDateDisp = selectionProcessDateDisp;
	}
	public short getSelectionProcessDatePois() {
		return selectionProcessDatePois;
	}
	public void setSelectionProcessDatePois(short selectionProcessDatePois) {
		this.selectionProcessDatePois = selectionProcessDatePois;
	}
	
	
}
