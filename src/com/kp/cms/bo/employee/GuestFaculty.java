package com.kp.cms.bo.employee;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpDesiredPost;
import com.kp.cms.bo.admin.EmpEducationMaster;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.QualificationLevelBO;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.SubjectAreaBO;

public class GuestFaculty implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Country country;
	private State state;
	private EmpEducationMaster empEducationDetailses;
	private EmpDesiredPost empDesiredPost;
	private int id;
	private Department department;
	private State stateByCommunicationAddressStateId;
	private State stateByPermanentAddressStateId;
	private Designation designation;
	private Country countryByPermanentAddressCountryId;
	private Country countryByCommunicationAddressCountryId;
	private String firstName;
	private String fatherName;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Date dob;
	private String permanentAddressLine1;
	private String permanentAddressLine2;
	private String permanentAddressCity;
	private String permanentAddressZip;
	private String communicationAddressLine1;
	private String communicationAddressLine2;
	private String communicationAddressCity;
	private String communicationAddressZip;
	
	private String email;
	
	private String bloodGroup;
	private String communicationAddressStateOthers;
	private String permanentAddressStateOthers;
	private Nationality nationality;
	private String code;

	private String maritalStatus;
	private String gender;
	
	private String emergencyContName;
	private String emContactRelationship;
	private String emergencyMobile;
	private String emergencyHomeTelephone;
	private String emergencyWorkTelephone;
	private String workEmail;
	
	private String uid;
	private String panNo;
	private String currentAddressHomeTelephone1;
	private String currentAddressHomeTelephone2;
	private String currentAddressHomeTelephone3;
	private String currentAddressWorkTelephone1;
	private String currentAddressWorkTelephone2;
	private String currentAddressWorkTelephone3;
	
	private String currentAddressMobile1;
	
	private EmployeeStreamBO streamId;
	private EmployeeWorkLocationBO workLocationId;
	private Boolean isActive;
	private boolean active;
	
	private String bankAccNo;
	private String pfNo;
	
	private String twoWheelerNo;
	private String fourWheelerNo;
	
	private String noOfPublicationsRefered;
	private String noOfPublicationsNotRefered;
	private String books;
	private String otherInfo;
	private SubjectAreaBO empSubjectArea;
	private Boolean currentlyWorking;
	private Designation currentDesignation;
	private String currentOrganization;
	private String totalExpYear;
	private String totalExpMonths;
	private QualificationLevelBO empQualificationLevel;
	private String reservationCategory;
	private Boolean teachingStaff;
	private String timeIn;
	
	private String desig;
	private String highQualifForAlbum;
	private String relevantExpMonths;
	private String relevantExpYears;
	private Religion religionId;
	private String designationName;
	private EmpJobTitle titleId;
	private String organistionName;
	private Boolean isSameAddress;
	private String eligibilityTest;
	
	private String eligibilityTestOther;
	private String industryFunctionalArea;
	private String handicappedDescription;
	private String emContactAddress;
	
	private String subjectSpecilization;
	/*private Date startDate;
	private Date endDate;
	private String semester;
	private Boolean isCurrentWorkingDates;*/
	private String workingHoursPerWeek;
	private String honorariumPerHours;
	private String referredBy;
	
	private Set<GuestEducationalDetails> educationalDetailsSet = new HashSet<GuestEducationalDetails>();
	private Set<GuestPreviousChristWorkDetails> previousChristDetails = new HashSet<GuestPreviousChristWorkDetails>();
	private Set<GuestPreviousExperience> previousExpSet = new HashSet<GuestPreviousExperience>();
	private Set<GuestImages> empImages = new HashSet<GuestImages>();
	private Set<Users> userses = new HashSet<Users>(0);
	private String staffId;
	private String highQualifForWebsite;
	private boolean displayInWebsite;
	private String bankBranch;
	private String bankIfscCode;
	private Set<EmpAcademicQualificationBO> empAcademicDeatils = new HashSet<EmpAcademicQualificationBO>();
	private Set<EmpIntrestsBO> empIntrestDetails = new HashSet<EmpIntrestsBO>();
	private Set<EmpResearchBo> empResearchDetails = new HashSet<EmpResearchBo>();
	private Set<EmpGuideshipDetailsBo> empGuideshipDetails = new HashSet<EmpGuideshipDetailsBo>();
	private Set<EmpDutiesDetailsBO> empDutiesDeatils = new HashSet<EmpDutiesDetailsBO>();
	private Set<EmpProjectResearchDetailsBO> empProjectDetails = new HashSet<EmpProjectResearchDetailsBO>();
	private Set<EmpReaserchPublishcationDetailsBO> empResearchPublication = new HashSet<EmpReaserchPublishcationDetailsBO>();
	private Set<EmpBooksPublishedDetailsBO> empBooksPublished = new HashSet<EmpBooksPublishedDetailsBO>();
	private Set<EmpPaperPresentationBO> empPaperPresentaion = new HashSet<EmpPaperPresentationBO>();
	private Set<EmpSeminarAttendedDetailsBO> empSeminarAttended = new HashSet<EmpSeminarAttendedDetailsBO>();
	private Set<EmpProfessionalDevelopmentBO> empProfeDevelopment = new HashSet<EmpProfessionalDevelopmentBO>();
	private Set<EmpAwardDetailsBO> empAwardDetails = new HashSet<EmpAwardDetailsBO>();
	private Set<EmpMemeberShipDetailsBO> empMemDetailsBOs = new HashSet<EmpMemeberShipDetailsBO>();
	
	

	public GuestFaculty(Country country, State state,
			EmpEducationMaster empEducationDetailses,
			EmpDesiredPost empDesiredPost, int id, Department department,
			State stateByCommunicationAddressStateId,
			State stateByPermanentAddressStateId, Designation designation,
			Country countryByPermanentAddressCountryId,
			Country countryByCommunicationAddressCountryId, String firstName,
			String fatherName, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Date dob,
			String permanentAddressLine1, String permanentAddressLine2,
			String permanentAddressCity, String permanentAddressZip,
			String communicationAddressLine1, String communicationAddressLine2,
			String communicationAddressCity, String communicationAddressZip,
			String email, String bloodGroup,
			String communicationAddressStateOthers,
			String permanentAddressStateOthers, Nationality nationality,
			String code, String maritalStatus, String gender,
			String emergencyContName, String emContactRelationship,
			String emergencyMobile, String emergencyHomeTelephone,
			String emergencyWorkTelephone, String workEmail, String uid,
			String panNo, String currentAddressHomeTelephone1,
			String currentAddressHomeTelephone2,
			String currentAddressHomeTelephone3,
			String currentAddressWorkTelephone1,
			String currentAddressWorkTelephone2,
			String currentAddressWorkTelephone3, String currentAddressMobile1,
			EmployeeStreamBO streamId, EmployeeWorkLocationBO workLocationId,
			Boolean isActive, boolean active, String bankAccNo, String pfNo,
			String twoWheelerNo, String fourWheelerNo,
			String noOfPublicationsRefered, String noOfPublicationsNotRefered,
			String books, String otherInfo, SubjectAreaBO empSubjectArea,
			Boolean currentlyWorking, Designation currentDesignation,
			String currentOrganization, String totalExpYear,
			String totalExpMonths, QualificationLevelBO empQualificationLevel,
			String reservationCategory, Boolean teachingStaff, String timeIn,
			String desig, String highQualifForAlbum, String relevantExpMonths,
			String relevantExpYears, Religion religionId,
			String designationName, EmpJobTitle titleId,
			String organistionName, Boolean isSameAddress,
			String eligibilityTest, String eligibilityTestOther,
			String industryFunctionalArea, String handicappedDescription,
			String emContactAddress, String subjectSpecilization,
			String workingHoursPerWeek,
			String honorariumPerHours, String referredBy,
			Set<GuestEducationalDetails> educationalDetailsSet,
			Set<GuestPreviousChristWorkDetails> previousChristDetails,
			Set<GuestPreviousExperience> previousExpSet,
			Set<GuestImages> empImages, Set<Users> userses, 
			String staffId, String highQualifForWebsite, boolean displayInWebsite, String bankBranch, String bankIfscCode,Set<EmpAcademicQualificationBO> empAcademicDeatils,Set<EmpIntrestsBO> empIntrestDetails,
			Set<EmpResearchBo> empResearchDetails,Set<EmpGuideshipDetailsBo> empGuideshipDetails,Set<EmpDutiesDetailsBO> empDutiesDeatils,Set<EmpProjectResearchDetailsBO> empProjectDetails,
			Set<EmpReaserchPublishcationDetailsBO> empResearchPublication,Set<EmpBooksPublishedDetailsBO> empBooksPublished,Set<EmpPaperPresentationBO> empPaperPresentaion,
			Set<EmpSeminarAttendedDetailsBO> empSeminarAttended,Set<EmpProfessionalDevelopmentBO> empProfeDevelopment,Set<EmpAwardDetailsBO> empAwardDetails,Set<EmpMemeberShipDetailsBO> empMemDetailsBOs) {
		super();
		this.country = country;
		this.state = state;
		this.empEducationDetailses = empEducationDetailses;
		this.empDesiredPost = empDesiredPost;
		this.id = id;
		this.department = department;
		this.stateByCommunicationAddressStateId = stateByCommunicationAddressStateId;
		this.stateByPermanentAddressStateId = stateByPermanentAddressStateId;
		this.designation = designation;
		this.countryByPermanentAddressCountryId = countryByPermanentAddressCountryId;
		this.countryByCommunicationAddressCountryId = countryByCommunicationAddressCountryId;
		this.firstName = firstName;
		this.fatherName = fatherName;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.dob = dob;
		this.permanentAddressLine1 = permanentAddressLine1;
		this.permanentAddressLine2 = permanentAddressLine2;
		this.permanentAddressCity = permanentAddressCity;
		this.permanentAddressZip = permanentAddressZip;
		this.communicationAddressLine1 = communicationAddressLine1;
		this.communicationAddressLine2 = communicationAddressLine2;
		this.communicationAddressCity = communicationAddressCity;
		this.communicationAddressZip = communicationAddressZip;
		this.email = email;
		this.bloodGroup = bloodGroup;
		this.communicationAddressStateOthers = communicationAddressStateOthers;
		this.permanentAddressStateOthers = permanentAddressStateOthers;
		this.nationality = nationality;
		this.code = code;
		this.maritalStatus = maritalStatus;
		this.gender = gender;
		this.emergencyContName = emergencyContName;
		this.emContactRelationship=emContactRelationship;
		this.emergencyMobile = emergencyMobile;
		this.emergencyHomeTelephone = emergencyHomeTelephone;
		this.emergencyWorkTelephone = emergencyWorkTelephone;
		this.workEmail = workEmail;
		this.uid = uid;
		this.panNo = panNo;
		this.currentAddressHomeTelephone1 = currentAddressHomeTelephone1;
		this.currentAddressHomeTelephone2 = currentAddressHomeTelephone2;
		this.currentAddressHomeTelephone3 = currentAddressHomeTelephone3;
		this.currentAddressWorkTelephone1 = currentAddressWorkTelephone1;
		this.currentAddressWorkTelephone2 = currentAddressWorkTelephone2;
		this.currentAddressWorkTelephone3 = currentAddressWorkTelephone3;
		this.currentAddressMobile1 = currentAddressMobile1;
		this.streamId = streamId;
		this.workLocationId = workLocationId;
		this.isActive = isActive;
		this.active = active;
		this.bankAccNo = bankAccNo;
		this.pfNo = pfNo;
		this.twoWheelerNo = twoWheelerNo;
		this.fourWheelerNo = fourWheelerNo;
		this.noOfPublicationsRefered = noOfPublicationsRefered;
		this.noOfPublicationsNotRefered = noOfPublicationsNotRefered;
		this.books = books;
		this.otherInfo = otherInfo;
		this.empSubjectArea = empSubjectArea;
		this.currentlyWorking = currentlyWorking;
		this.currentDesignation = currentDesignation;
		this.currentOrganization = currentOrganization;
		this.totalExpYear = totalExpYear;
		this.totalExpMonths = totalExpMonths;
		this.empQualificationLevel = empQualificationLevel;
		this.reservationCategory = reservationCategory;
		this.teachingStaff = teachingStaff;
		this.timeIn = timeIn;
		this.desig = desig;
		this.highQualifForAlbum = highQualifForAlbum;
		this.relevantExpMonths = relevantExpMonths;
		this.relevantExpYears = relevantExpYears;
		this.religionId = religionId;
		this.designationName = designationName;
		this.titleId = titleId;
		this.organistionName = organistionName;
		this.isSameAddress = isSameAddress;
		this.eligibilityTest = eligibilityTest;
		this.eligibilityTestOther = eligibilityTestOther;
		this.industryFunctionalArea = industryFunctionalArea;
		this.handicappedDescription = handicappedDescription;
		this.emContactAddress = emContactAddress;
		this.subjectSpecilization = subjectSpecilization;
		this.workingHoursPerWeek = workingHoursPerWeek;
		this.honorariumPerHours = honorariumPerHours;
		this.referredBy = referredBy;
		this.educationalDetailsSet = educationalDetailsSet;
		this.previousChristDetails = previousChristDetails;
		this.previousExpSet = previousExpSet;
		this.empImages = empImages;
		this.userses = userses;
		this.staffId = staffId;
		this.displayInWebsite = displayInWebsite;
		this.highQualifForWebsite = highQualifForWebsite;
		this.bankBranch = bankBranch;
		this.bankIfscCode = bankIfscCode;
		this.empAcademicDeatils = empAcademicDeatils;
		this.empIntrestDetails = empIntrestDetails;
		this.empResearchDetails = empResearchDetails;
		this.empGuideshipDetails = empGuideshipDetails;
		this.empDutiesDeatils = empDutiesDeatils;
		this.empProjectDetails = empProjectDetails;
		this.empResearchPublication = empResearchPublication;
		this.empBooksPublished = empBooksPublished;
		this.empPaperPresentaion = empPaperPresentaion;
		this.empSeminarAttended = empSeminarAttended;
		this.empProfeDevelopment = empProfeDevelopment;
		this.empAwardDetails = empAwardDetails;
		this.empMemDetailsBOs = empMemDetailsBOs;
	}

	public GuestFaculty() {
	}

	public GuestFaculty(int id) {
		this.id = id;
	}

		
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public State getStateByCommunicationAddressStateId() {
		return this.stateByCommunicationAddressStateId;
	}

	public void setStateByCommunicationAddressStateId(
			State stateByCommunicationAddressStateId) {
		this.stateByCommunicationAddressStateId = stateByCommunicationAddressStateId;
	}

	public State getStateByPermanentAddressStateId() {
		return this.stateByPermanentAddressStateId;
	}

	public void setStateByPermanentAddressStateId(
			State stateByPermanentAddressStateId) {
		this.stateByPermanentAddressStateId = stateByPermanentAddressStateId;
	}

	public Designation getDesignation() {
		return this.designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	public Country getCountryByPermanentAddressCountryId() {
		return this.countryByPermanentAddressCountryId;
	}

	public void setCountryByPermanentAddressCountryId(
			Country countryByPermanentAddressCountryId) {
		this.countryByPermanentAddressCountryId = countryByPermanentAddressCountryId;
	}

	public Country getCountryByCommunicationAddressCountryId() {
		return this.countryByCommunicationAddressCountryId;
	}

	public void setCountryByCommunicationAddressCountryId(
			Country countryByCommunicationAddressCountryId) {
		this.countryByCommunicationAddressCountryId = countryByCommunicationAddressCountryId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	

	public String getPermanentAddressLine1() {
		return this.permanentAddressLine1;
	}

	public void setPermanentAddressLine1(String permanentAddressLine1) {
		this.permanentAddressLine1 = permanentAddressLine1;
	}

	public String getPermanentAddressLine2() {
		return this.permanentAddressLine2;
	}

	public void setPermanentAddressLine2(String permanentAddressLine2) {
		this.permanentAddressLine2 = permanentAddressLine2;
	}

	public String getPermanentAddressCity() {
		return this.permanentAddressCity;
	}

	public void setPermanentAddressCity(String permanentAddressCity) {
		this.permanentAddressCity = permanentAddressCity;
	}

	public String getPermanentAddressZip() {
		return this.permanentAddressZip;
	}

	public void setPermanentAddressZip(String permanentAddressZip) {
		this.permanentAddressZip = permanentAddressZip;
	}

	public String getCommunicationAddressLine1() {
		return this.communicationAddressLine1;
	}

	public void setCommunicationAddressLine1(String communicationAddressLine1) {
		this.communicationAddressLine1 = communicationAddressLine1;
	}

	public String getCommunicationAddressLine2() {
		return this.communicationAddressLine2;
	}

	public void setCommunicationAddressLine2(String communicationAddressLine2) {
		this.communicationAddressLine2 = communicationAddressLine2;
	}

	public String getCommunicationAddressCity() {
		return this.communicationAddressCity;
	}

	public void setCommunicationAddressCity(String communicationAddressCity) {
		this.communicationAddressCity = communicationAddressCity;
	}

	public String getCommunicationAddressZip() {
		return this.communicationAddressZip;
	}

	public void setCommunicationAddressZip(String communicationAddressZip) {
		this.communicationAddressZip = communicationAddressZip;
	}


	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public String getBloodGroup() {
		return this.bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getCommunicationAddressStateOthers() {
		return communicationAddressStateOthers;
	}

	public void setCommunicationAddressStateOthers(
			String communicationAddressStateOthers) {
		this.communicationAddressStateOthers = communicationAddressStateOthers;
	}

	public String getPermanentAddressStateOthers() {
		return permanentAddressStateOthers;
	}

	public void setPermanentAddressStateOthers(String permanentAddressStateOthers) {
		this.permanentAddressStateOthers = permanentAddressStateOthers;
	}


	

	public Nationality getNationality() {
		return nationality;
	}

	public void setNationality(Nationality nationality) {
		this.nationality = nationality;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getEmergencyContName() {
		return emergencyContName;
	}

	public void setEmergencyContName(String emergencyContName) {
		this.emergencyContName = emergencyContName;
	}

	

	public String getEmergencyMobile() {
		return emergencyMobile;
	}

	public void setEmergencyMobile(String emergencyMobile) {
		this.emergencyMobile = emergencyMobile;
	}

	public String getEmergencyHomeTelephone() {
		return emergencyHomeTelephone;
	}

	public void setEmergencyHomeTelephone(String emergencyHomeTelephone) {
		this.emergencyHomeTelephone = emergencyHomeTelephone;
	}

	public String getEmergencyWorkTelephone() {
		return emergencyWorkTelephone;
	}

	public void setEmergencyWorkTelephone(String emergencyWorkTelephone) {
		this.emergencyWorkTelephone = emergencyWorkTelephone;
	}

	public String getWorkEmail() {
		return workEmail;
	}

	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}

	
	public Set<Users> getUserses() {
		return userses;
	}

	public void setUserses(Set<Users> userses) {
		this.userses = userses;
	}

	

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getCurrentAddressHomeTelephone1() {
		return currentAddressHomeTelephone1;
	}

	public void setCurrentAddressHomeTelephone1(String currentAddressHomeTelephone1) {
		this.currentAddressHomeTelephone1 = currentAddressHomeTelephone1;
	}

	public String getCurrentAddressHomeTelephone2() {
		return currentAddressHomeTelephone2;
	}

	public void setCurrentAddressHomeTelephone2(String currentAddressHomeTelephone2) {
		this.currentAddressHomeTelephone2 = currentAddressHomeTelephone2;
	}

	public String getCurrentAddressHomeTelephone3() {
		return currentAddressHomeTelephone3;
	}

	public void setCurrentAddressHomeTelephone3(String currentAddressHomeTelephone3) {
		this.currentAddressHomeTelephone3 = currentAddressHomeTelephone3;
	}

	public String getCurrentAddressWorkTelephone1() {
		return currentAddressWorkTelephone1;
	}

	public void setCurrentAddressWorkTelephone1(String currentAddressWorkTelephone1) {
		this.currentAddressWorkTelephone1 = currentAddressWorkTelephone1;
	}

	public String getCurrentAddressWorkTelephone2() {
		return currentAddressWorkTelephone2;
	}

	public void setCurrentAddressWorkTelephone2(String currentAddressWorkTelephone2) {
		this.currentAddressWorkTelephone2 = currentAddressWorkTelephone2;
	}

	public String getCurrentAddressWorkTelephone3() {
		return currentAddressWorkTelephone3;
	}

	public void setCurrentAddressWorkTelephone3(String currentAddressWorkTelephone3) {
		this.currentAddressWorkTelephone3 = currentAddressWorkTelephone3;
	}

	public String getCurrentAddressMobile1() {
		return currentAddressMobile1;
	}

	public void setCurrentAddressMobile1(String currentAddressMobile1) {
		this.currentAddressMobile1 = currentAddressMobile1;
	}

	
	

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	
	public EmployeeStreamBO getStreamId() {
		return streamId;
	}

	public void setStreamId(EmployeeStreamBO streamId) {
		this.streamId = streamId;
	}

	public EmployeeWorkLocationBO getWorkLocationId() {
		return workLocationId;
	}

	public void setWorkLocationId(EmployeeWorkLocationBO workLocationId) {
		this.workLocationId = workLocationId;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean getActive() {
		return active;
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

	public SubjectAreaBO getEmpSubjectArea() {
		return empSubjectArea;
	}

	public void setEmpSubjectArea(SubjectAreaBO empSubjectArea) {
		this.empSubjectArea = empSubjectArea;
	}

	public Boolean isCurrentlyWorking() {
		return currentlyWorking;
	}

	public void setCurrentlyWorking(Boolean currentlyWorking) {
		this.currentlyWorking = currentlyWorking;
	}

	public Designation getCurrentDesignation() {
		return currentDesignation;
	}

	public void setCurrentDesignation(Designation currentDesignation) {
		this.currentDesignation = currentDesignation;
	}

	public String getCurrentOrganization() {
		return currentOrganization;
	}

	public void setCurrentOrganization(String currentOrganization) {
		this.currentOrganization = currentOrganization;
	}

	public String getTotalExpYear() {
		return totalExpYear;
	}

	public void setTotalExpYear(String totalExpYear) {
		this.totalExpYear = totalExpYear;
	}

	public String getTotalExpMonths() {
		return totalExpMonths;
	}

	public void setTotalExpMonths(String totalExpMonths) {
		this.totalExpMonths = totalExpMonths;
	}

	public QualificationLevelBO getEmpQualificationLevel() {
		return empQualificationLevel;
	}

	public void setEmpQualificationLevel(QualificationLevelBO empQualificationLevel) {
		this.empQualificationLevel = empQualificationLevel;
	}

	public String getReservationCategory() {
		return reservationCategory;
	}

	public void setReservationCategory(String reservationCategory) {
		this.reservationCategory = reservationCategory;
	}
	

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public EmpEducationMaster getEmpEducationDetailses() {
		return empEducationDetailses;
	}

	public void setEmpEducationDetailses(EmpEducationMaster empEducationDetailses) {
		this.empEducationDetailses = empEducationDetailses;
	}

	

	public EmpDesiredPost getEmpDesiredPost() {
		return empDesiredPost;
	}

	public void setEmpDesiredPost(EmpDesiredPost empDesiredPost) {
		this.empDesiredPost = empDesiredPost;
	}

	public String getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(String timeIn) {
		this.timeIn = timeIn;
	}

	


	
	public Boolean getTeachingStaff() {
		return teachingStaff;
	}

	public void setTeachingStaff(Boolean teachingStaff) {
		this.teachingStaff = teachingStaff;
	}

	public String getDesig() {
		return desig;
	}

	public void setDesig(String desig) {
		this.desig = desig;
	}

	public String getHighQualifForAlbum() {
		return highQualifForAlbum;
	}

	public void setHighQualifForAlbum(String highQualifForAlbum) {
		this.highQualifForAlbum = highQualifForAlbum;
	}

	public String getRelevantExpYears() {
		return relevantExpYears;
	}

	public void setRelevantExpYears(String relevantExpYears) {
		this.relevantExpYears = relevantExpYears;
	}

	public String getRelevantExpMonths() {
		return relevantExpMonths;
	}

	public void setRelevantExpMonths(String relevantExpMonths) {
		this.relevantExpMonths = relevantExpMonths;
	}

	

	
	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public Religion getReligionId() {
		return religionId;
	}

	public void setReligionId(Religion religionId) {
		this.religionId = religionId;
	}

	public EmpJobTitle getTitleId() {
		return titleId;
	}

	public void setTitleId(EmpJobTitle titleId) {
		this.titleId = titleId;
	}

	public String getOrganistionName() {
		return organistionName;
	}

	public void setOrganistionName(String organistionName) {
		this.organistionName = organistionName;
	}

	public Boolean getIsSameAddress() {
		return isSameAddress;
	}

	public void setIsSameAddress(Boolean isSameAddress) {
		this.isSameAddress = isSameAddress;
	}

	public Boolean getCurrentlyWorking() {
		return currentlyWorking;
	}


	public String getEligibilityTest() {
		return eligibilityTest;
	}

	public void setEligibilityTest(String eligibilityTest) {
		this.eligibilityTest = eligibilityTest;
	}


	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
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

	public String getSubjectSpecilization() {
		return subjectSpecilization;
	}

	public void setSubjectSpecilization(String subjectSpecilization) {
		this.subjectSpecilization = subjectSpecilization;
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

	public Set<GuestEducationalDetails> getEducationalDetailsSet() {
		return educationalDetailsSet;
	}

	public void setEducationalDetailsSet(
			Set<GuestEducationalDetails> educationalDetailsSet) {
		this.educationalDetailsSet = educationalDetailsSet;
	}

	public Set<GuestPreviousExperience> getPreviousExpSet() {
		return previousExpSet;
	}

	public void setPreviousExpSet(Set<GuestPreviousExperience> previousExpSet) {
		this.previousExpSet = previousExpSet;
	}

	public Set<GuestImages> getEmpImages() {
		return empImages;
	}

	public void setEmpImages(Set<GuestImages> empImages) {
		this.empImages = empImages;
	}

	public Set<GuestPreviousChristWorkDetails> getPreviousChristDetails() {
		return previousChristDetails;
	}

	public void setPreviousChristDetails(
			Set<GuestPreviousChristWorkDetails> previousChristDetails) {
		this.previousChristDetails = previousChristDetails;
	}

	public String getEmContactRelationship() {
		return emContactRelationship;
	}

	public void setEmContactRelationship(String emContactRelationship) {
		this.emContactRelationship = emContactRelationship;
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

	public boolean getDisplayInWebsite() {
		return displayInWebsite;
	}

	public void setDisplayInWebsite(boolean displayInWebsite) {
		this.displayInWebsite = displayInWebsite;
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

	public Set<EmpAcademicQualificationBO> getEmpAcademicDeatils() {
		return empAcademicDeatils;
	}

	public void setEmpAcademicDeatils(
			Set<EmpAcademicQualificationBO> empAcademicDeatils) {
		this.empAcademicDeatils = empAcademicDeatils;
	}

	public Set<EmpIntrestsBO> getEmpIntrestDetails() {
		return empIntrestDetails;
	}

	public void setEmpIntrestDetails(Set<EmpIntrestsBO> empIntrestDetails) {
		this.empIntrestDetails = empIntrestDetails;
	}

	public Set<EmpResearchBo> getEmpResearchDetails() {
		return empResearchDetails;
	}

	public void setEmpResearchDetails(Set<EmpResearchBo> empResearchDetails) {
		this.empResearchDetails = empResearchDetails;
	}

	public Set<EmpGuideshipDetailsBo> getEmpGuideshipDetails() {
		return empGuideshipDetails;
	}

	public void setEmpGuideshipDetails(
			Set<EmpGuideshipDetailsBo> empGuideshipDetails) {
		this.empGuideshipDetails = empGuideshipDetails;
	}

	public Set<EmpDutiesDetailsBO> getEmpDutiesDeatils() {
		return empDutiesDeatils;
	}

	public void setEmpDutiesDeatils(Set<EmpDutiesDetailsBO> empDutiesDeatils) {
		this.empDutiesDeatils = empDutiesDeatils;
	}

	public Set<EmpProjectResearchDetailsBO> getEmpProjectDetails() {
		return empProjectDetails;
	}

	public void setEmpProjectDetails(
			Set<EmpProjectResearchDetailsBO> empProjectDetails) {
		this.empProjectDetails = empProjectDetails;
	}

	public Set<EmpReaserchPublishcationDetailsBO> getEmpResearchPublication() {
		return empResearchPublication;
	}

	public void setEmpResearchPublication(
			Set<EmpReaserchPublishcationDetailsBO> empResearchPublication) {
		this.empResearchPublication = empResearchPublication;
	}

	public Set<EmpBooksPublishedDetailsBO> getEmpBooksPublished() {
		return empBooksPublished;
	}

	public void setEmpBooksPublished(
			Set<EmpBooksPublishedDetailsBO> empBooksPublished) {
		this.empBooksPublished = empBooksPublished;
	}

	public Set<EmpPaperPresentationBO> getEmpPaperPresentaion() {
		return empPaperPresentaion;
	}

	public void setEmpPaperPresentaion(
			Set<EmpPaperPresentationBO> empPaperPresentaion) {
		this.empPaperPresentaion = empPaperPresentaion;
	}

	public Set<EmpSeminarAttendedDetailsBO> getEmpSeminarAttended() {
		return empSeminarAttended;
	}

	public void setEmpSeminarAttended(
			Set<EmpSeminarAttendedDetailsBO> empSeminarAttended) {
		this.empSeminarAttended = empSeminarAttended;
	}

	public Set<EmpProfessionalDevelopmentBO> getEmpProfeDevelopment() {
		return empProfeDevelopment;
	}

	public void setEmpProfeDevelopment(
			Set<EmpProfessionalDevelopmentBO> empProfeDevelopment) {
		this.empProfeDevelopment = empProfeDevelopment;
	}

	public Set<EmpAwardDetailsBO> getEmpAwardDetails() {
		return empAwardDetails;
	}

	public void setEmpAwardDetails(Set<EmpAwardDetailsBO> empAwardDetails) {
		this.empAwardDetails = empAwardDetails;
	}

	public Set<EmpMemeberShipDetailsBO> getEmpMemDetailsBOs() {
		return empMemDetailsBOs;
	}

	public void setEmpMemDetailsBOs(Set<EmpMemeberShipDetailsBO> empMemDetailsBOs) {
		this.empMemDetailsBOs = empMemDetailsBOs;
	}

	
}
