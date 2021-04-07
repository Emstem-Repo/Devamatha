package com.kp.cms.to.admin;

import java.io.Serializable;



public class DownloadEmployeeResumeTO implements Serializable {
	
	private int id;
	private String empCode;
	private String fingerPrintId;
	private String startDate;
	private String endDate;
	private String employeeName;
	private String designationName;
	private String departmentName;
	private String employeeId;
	private String qualificationId;
	private String applicationNO;
	private String checked1;
	private String mailId;
	private String tempChecked;
	
	// resume print  fields
	
	private String postAppliedFor;
	private String department;
	private String gender;
	private String jobCode;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String nationality;
	private String currentLocation;
	private String marital;
	private String dateOfBirth;
	private String email;
	private String reservationCategory;
	private String contactNumber;
	private String mobileNumber;
	private String workStatus;
	private String designation;
	private String currentAddress1;
	private String currentAddress2;
	private String currentAddress3;
	private String totalExpYears;
	private String totalExpMonths;
	private Integer noOfPublicationsRefered;
	private Integer noOfPublicationsNotRefered;
	private Integer books;
	private String checked;
	private String totalTeachingExperience;
	private String industryExperience;
	private String totalExp;
	private String qualificationLevel;
	private String subjectArea;
	private String jobType;
	private String empStatus;
	private String expectedSalary;
	private String eligibilityTest;
	private String otherInfo;
	private String submitedDate;
	private String highQualification;
	private String courseNames;
	private String dateOfApplication;
	// new additions by Smitha
	private String industryFunctionalArea;
	private String bloodGroup;
	private String religion;
	private String age;
	private int count;
	private String currentStatus;
	private String currentDate;
	//added by sudhir
	private byte[] photo;
	private String forwardedUsers;
	private String researchPapersRefereed;
	private String researchPapersNonRefereed;
	private String researchPapersProceedings;
	private String internationalBookPublications;
	private String nationalBookPublications;
	private String localBookPublications;
	private String chaptersEditedBooksInternational;
	private String chaptersEditedBooksNational;
	private String majorSponseredProjects;
	private String minorSponseredProjects;
	private String consultancy1SponseredProjects;
	private String consultancy2SponseredProjects;
	private String phdResearchGuidance;
	private String mphilResearchGuidance;
	private String trainingAttendedFdp2Weeks;
	private String trainingAttendedFdp1Weeks;
	private String internationalConferencePresentaion;
	private String nationalConferencePresentaion;
	private String regionalConferencePresentaion;
	private String localConferencePresentaion;
	private String fatherName;
	private String motherName;
	private String alternateMobile;
	private String empSubject;
	private String empSubjectName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getFingerPrintId() {
		return fingerPrintId;
	}
	public void setFingerPrintId(String fingerPrintId) {
		this.fingerPrintId = fingerPrintId;
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
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getQualificationId() {
		return qualificationId;
	}
	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
	}
	public String getApplicationNO() {
		return applicationNO;
	}
	public void setApplicationNO(String applicationNO) {
		this.applicationNO = applicationNO;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public String getTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(String tempChecked) {
		this.tempChecked = tempChecked;
	}
	public String getChecked1() {
		return checked1;
	}
	public void setChecked1(String checked1) {
		this.checked1 = checked1;
	}
	public String getPostAppliedFor() {
		return postAppliedFor;
	}
	public void setPostAppliedFor(String postAppliedFor) {
		this.postAppliedFor = postAppliedFor;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
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
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	public String getMarital() {
		return marital;
	}
	public void setMarital(String marital) {
		this.marital = marital;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getReservationCategory() {
		return reservationCategory;
	}
	public void setReservationCategory(String reservationCategory) {
		this.reservationCategory = reservationCategory;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getCurrentAddress1() {
		return currentAddress1;
	}
	public void setCurrentAddress1(String currentAddress1) {
		this.currentAddress1 = currentAddress1;
	}
	public String getCurrentAddress2() {
		return currentAddress2;
	}
	public void setCurrentAddress2(String currentAddress2) {
		this.currentAddress2 = currentAddress2;
	}
	public String getCurrentAddress3() {
		return currentAddress3;
	}
	public void setCurrentAddress3(String currentAddress3) {
		this.currentAddress3 = currentAddress3;
	}
	public String getTotalExpYears() {
		return totalExpYears;
	}
	public void setTotalExpYears(String totalExpYears) {
		this.totalExpYears = totalExpYears;
	}
	public String getTotalExpMonths() {
		return totalExpMonths;
	}
	public void setTotalExpMonths(String totalExpMonths) {
		this.totalExpMonths = totalExpMonths;
	}
	public Integer getNoOfPublicationsRefered() {
		return noOfPublicationsRefered;
	}
	public void setNoOfPublicationsRefered(Integer noOfPublicationsRefered) {
		this.noOfPublicationsRefered = noOfPublicationsRefered;
	}
	public Integer getNoOfPublicationsNotRefered() {
		return noOfPublicationsNotRefered;
	}
	public void setNoOfPublicationsNotRefered(Integer noOfPublicationsNotRefered) {
		this.noOfPublicationsNotRefered = noOfPublicationsNotRefered;
	}
	public Integer getBooks() {
		return books;
	}
	public void setBooks(Integer books) {
		this.books = books;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getTotalTeachingExperience() {
		return totalTeachingExperience;
	}
	public void setTotalTeachingExperience(String totalTeachingExperience) {
		this.totalTeachingExperience = totalTeachingExperience;
	}
	public String getIndustryExperience() {
		return industryExperience;
	}
	public void setIndustryExperience(String industryExperience) {
		this.industryExperience = industryExperience;
	}
	public String getTotalExp() {
		return totalExp;
	}
	public void setTotalExp(String totalExp) {
		this.totalExp = totalExp;
	}
	public String getQualificationLevel() {
		return qualificationLevel;
	}
	public void setQualificationLevel(String qualificationLevel) {
		this.qualificationLevel = qualificationLevel;
	}
	public String getSubjectArea() {
		return subjectArea;
	}
	public void setSubjectArea(String subjectArea) {
		this.subjectArea = subjectArea;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getEmpStatus() {
		return empStatus;
	}
	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}
	public String getExpectedSalary() {
		return expectedSalary;
	}
	public void setExpectedSalary(String expectedSalary) {
		this.expectedSalary = expectedSalary;
	}
	public String getEligibilityTest() {
		return eligibilityTest;
	}
	public void setEligibilityTest(String eligibilityTest) {
		this.eligibilityTest = eligibilityTest;
	}
	public String getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}
	public String getIndustryFunctionalArea() {
		return industryFunctionalArea;
	}
	public void setIndustryFunctionalArea(String industryFunctionalArea) {
		this.industryFunctionalArea = industryFunctionalArea;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getSubmitedDate() {
		return submitedDate;
	}
	public void setSubmitedDate(String submitedDate) {
		this.submitedDate = submitedDate;
	}
	public String getHighQualification() {
		return highQualification;
	}
	public void setHighQualification(String highQualification) {
		this.highQualification = highQualification;
	}
	public String getCourseNames() {
		return courseNames;
	}
	public void setCourseNames(String courseNames) {
		this.courseNames = courseNames;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getDateOfApplication() {
		return dateOfApplication;
	}
	public void setDateOfApplication(String dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public String getForwardedUsers() {
		return forwardedUsers;
	}
	public void setForwardedUsers(String forwardedUsers) {
		this.forwardedUsers = forwardedUsers;
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
	public String getInternationalBookPublications() {
		return internationalBookPublications;
	}
	public void setInternationalBookPublications(
			String internationalBookPublications) {
		this.internationalBookPublications = internationalBookPublications;
	}
	public String getNationalBookPublications() {
		return nationalBookPublications;
	}
	public void setNationalBookPublications(String nationalBookPublications) {
		this.nationalBookPublications = nationalBookPublications;
	}
	public String getLocalBookPublications() {
		return localBookPublications;
	}
	public void setLocalBookPublications(String localBookPublications) {
		this.localBookPublications = localBookPublications;
	}
	public String getChaptersEditedBooksInternational() {
		return chaptersEditedBooksInternational;
	}
	public void setChaptersEditedBooksInternational(
			String chaptersEditedBooksInternational) {
		this.chaptersEditedBooksInternational = chaptersEditedBooksInternational;
	}
	public String getChaptersEditedBooksNational() {
		return chaptersEditedBooksNational;
	}
	public void setChaptersEditedBooksNational(String chaptersEditedBooksNational) {
		this.chaptersEditedBooksNational = chaptersEditedBooksNational;
	}
	public String getMajorSponseredProjects() {
		return majorSponseredProjects;
	}
	public void setMajorSponseredProjects(String majorSponseredProjects) {
		this.majorSponseredProjects = majorSponseredProjects;
	}
	public String getMinorSponseredProjects() {
		return minorSponseredProjects;
	}
	public void setMinorSponseredProjects(String minorSponseredProjects) {
		this.minorSponseredProjects = minorSponseredProjects;
	}
	public String getConsultancy1SponseredProjects() {
		return consultancy1SponseredProjects;
	}
	public void setConsultancy1SponseredProjects(
			String consultancy1SponseredProjects) {
		this.consultancy1SponseredProjects = consultancy1SponseredProjects;
	}
	public String getConsultancy2SponseredProjects() {
		return consultancy2SponseredProjects;
	}
	public void setConsultancy2SponseredProjects(
			String consultancy2SponseredProjects) {
		this.consultancy2SponseredProjects = consultancy2SponseredProjects;
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
	public String getTrainingAttendedFdp2Weeks() {
		return trainingAttendedFdp2Weeks;
	}
	public void setTrainingAttendedFdp2Weeks(String trainingAttendedFdp2Weeks) {
		this.trainingAttendedFdp2Weeks = trainingAttendedFdp2Weeks;
	}
	public String getTrainingAttendedFdp1Weeks() {
		return trainingAttendedFdp1Weeks;
	}
	public void setTrainingAttendedFdp1Weeks(String trainingAttendedFdp1Weeks) {
		this.trainingAttendedFdp1Weeks = trainingAttendedFdp1Weeks;
	}
	public String getInternationalConferencePresentaion() {
		return internationalConferencePresentaion;
	}
	public void setInternationalConferencePresentaion(
			String internationalConferencePresentaion) {
		this.internationalConferencePresentaion = internationalConferencePresentaion;
	}
	public String getNationalConferencePresentaion() {
		return nationalConferencePresentaion;
	}
	public void setNationalConferencePresentaion(
			String nationalConferencePresentaion) {
		this.nationalConferencePresentaion = nationalConferencePresentaion;
	}
	public String getRegionalConferencePresentaion() {
		return regionalConferencePresentaion;
	}
	public void setRegionalConferencePresentaion(
			String regionalConferencePresentaion) {
		this.regionalConferencePresentaion = regionalConferencePresentaion;
	}
	public String getLocalConferencePresentaion() {
		return localConferencePresentaion;
	}
	public void setLocalConferencePresentaion(String localConferencePresentaion) {
		this.localConferencePresentaion = localConferencePresentaion;
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
	public String getAlternateMobile() {
		return alternateMobile;
	}
	public void setAlternateMobile(String alternateMobile) {
		this.alternateMobile = alternateMobile;
	}
	public String getEmpSubject() {
		return empSubject;
	}
	public void setEmpSubject(String empSubject) {
		this.empSubject = empSubject;
	}
	public String getEmpSubjectName() {
		return empSubjectName;
	}
	public void setEmpSubjectName(String empSubjectName) {
		this.empSubjectName = empSubjectName;
	}
}