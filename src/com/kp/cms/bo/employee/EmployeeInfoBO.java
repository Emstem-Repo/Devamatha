package com.kp.cms.bo.employee;

// Generated Mar 9, 2009 5:02:52 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpAcheivement;
import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpAppraisal;
import com.kp.cms.bo.admin.EmpAttendance;
import com.kp.cms.bo.admin.EmpDependents;
import com.kp.cms.bo.admin.EmpEducation;
import com.kp.cms.bo.admin.EmpEducationDetails;
import com.kp.cms.bo.admin.EmpImmigration;
import com.kp.cms.bo.admin.EmpJob;
import com.kp.cms.bo.admin.EmpLanguage;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.EmpPreviousOrg;
import com.kp.cms.bo.admin.EmpSkills;
import com.kp.cms.bo.admin.EmpWorkExperience;
import com.kp.cms.bo.admin.EmpWorkTime;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.admin.StudentAppraisal;
import com.kp.cms.bo.admin.Users;
/**
 * Employee generated by hbm2java
 */
public class EmployeeInfoBO implements java.io.Serializable {

	private int id;
	private EmployeeInfoBO employeeByReportToId;
	private EmployeeInfoBO employeeByLeaveApproveAuthId;
	private Department department;
	private State stateByCommunicationAddressStateId;
	private State stateByPermanentAddressStateId;
	private Designation designation;
	private Country countryByPermanentAddressCountryId;
	private Country countryByCommunicationAddressCountryId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Date dob;
	private Date doj;
	private String permanentAddressLine1;
	private String permanentAddressLine2;
	private String permanentAddressCity;
	private String permanentAddressZip;
	private String communicationAddressLine1;
	private String communicationAddressLine2;
	private String communicationAddressCity;
	private String communicationAddressZip;
	private String phone1;
	private String phone2;
	private String email;
	private String fatherName;
	private String bloodGroup;
	private String communicationAddressStateOthers;
	private String permanentAddressStateOthers;
	private byte[] empPhoto;
	private EmpImmigration empImmigration;
	private EmpWorkTime empWorkTime;
	private Nationality nationality;
	private String code;
	private String nickName;
	private Integer mobile;
	private Boolean smoker;
	private String maritalStatus;
	private String gender;
	private String drivingLicenseNo;
	private Date licenseExpDate;
	private String militaryService;
	private String ethinicRace;
	private String toTime;
	private String city;
	private String emergencyContName;
	private String relationship;
	private String emergencyMobile;
	private String emergencyHomeTelephone;
	private String emergencyWorkTelephone;
	private String workEmail;
	private String otherEmail;
	private String approvalRemarks;
	private String uid;
	private String panNo;
	private String currentAddressHomeTelephone1;
	private String currentAddressHomeTelephone2;
	private String currentAddressHomeTelephone3;
	private String currentAddressWorkTelephone1;
	private String currentAddressWorkTelephone2;
	private String currentAddressWorkTelephone3;
	private String permanentAddressHomeTelephone1;
	private String permanentAddressHomeTelephone2;
	private String permanentAddressHomeTelephone3;
	private String currentAddressMobile1;
	private String currentAddressMobile2;
	private String currentAddressMobile3;
	private String permanentAddressMobile1;
	private String permanentAddressMobile2;
	private String permanentAddressMobile3;
	private EmployeeStreamBO streamId;
	private EmployeeWorkLocationBO workLocationId;
	private Boolean isActive;
	private boolean active;
	private String fingerPrintId;
	private String motherName;
	private EmpType emptype;
	private String bankAccNo;
	private String pfNo;
	private String vehicleNo;
	private String twoWheelerNo;
	private String fourWheelerNo;
	private String panCardNo; 
	
	private Set<EmpWorkExperience> empWorkExperiences = new HashSet<EmpWorkExperience>(
			0);
	private Set<EmployeeInfoBO> employeesForLeaveApproveAuthId = new HashSet<EmployeeInfoBO>(
			0);
	private Set<StudentAppraisal> studentAppraisals = new HashSet<StudentAppraisal>(
			0);
	private Set<EmployeeInfoBO> employeesForReportToId = new HashSet<EmployeeInfoBO>(0);
	private Set<EmpSkills> empSkillses = new HashSet<EmpSkills>(0);
	private Set<EmpImmigration> empImmigrations = new HashSet<EmpImmigration>(0);
	private Set<Users> userses = new HashSet<Users>(0);
	private Set<EmpAppraisal> empAppraisalsForAppraiserEmpId = new HashSet<EmpAppraisal>(
			0);
	private Set<EmpEducation> empEducations = new HashSet<EmpEducation>(0);
	private Set<EmpAcheivement> empAcheivements = new HashSet<EmpAcheivement>(0);
	private Set<EmpAppraisal> empAppraisalsForAppraiseEmpId = new HashSet<EmpAppraisal>(
			0);
	private Set<EmpAttendance> empAttendances = new HashSet<EmpAttendance>(0);
	private Set<EmpApplyLeave> empApplyLeaves = new HashSet<EmpApplyLeave>(0);
	private Set<EmpDependents> empDependentses = new HashSet<EmpDependents>(0);
	private Set<InvLocation> invLocations = new HashSet<InvLocation>(0);
	private Set<EmpLanguage> empLanguages = new HashSet<EmpLanguage>(0);
	private Set<EmpLeave> empLeaves = new HashSet<EmpLeave>(0);
	private Set<EmpJob> empJobs = new HashSet<EmpJob>(0);
	//private Set<EmpType> empType = new HashSet<EmpType>(0);
	
	//New additions
	
	private Set<EmpEducationDetails> educationDetailsSet = new HashSet<EmpEducationDetails>();
	private Set<EmpOnlineEducationalDetails> educationalDetailsSet = new HashSet<EmpOnlineEducationalDetails>();
	private Set<EmpAcheivement> acheivementSet = new HashSet<EmpAcheivement>();
	private Set<EmpOnlinePreviousExperience> previousExpSet = new HashSet<EmpOnlinePreviousExperience>();
	private Set<EmpPreviousOrg> previousOrgSet=new HashSet<EmpPreviousOrg>();

	public EmployeeInfoBO() {
	}

	public EmployeeInfoBO(int id) {
		this.id = id;
	}

	public EmployeeInfoBO(int id, EmployeeInfoBO employeeByReportToId,
			EmpImmigration empImmigration, Department department,
			EmpWorkTime empWorkTime, Nationality nationality,
			State stateByCommunicationAddressStateId,
			State stateByPermanentAddressStateId, Designation designation,
			Country countryByPermanentAddressCountryId,
			Country countryByCommunicationAddressCountryId,
			EmployeeInfoBO employeeByLeaveApproveAuthId, String firstName,
			String middleName, String lastName, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Date dob, Date doj, String permanentAddressLine1,
			String permanentAddressLine2, String permanentAddressCity,
			String permanentAddressZip, String communicationAddressLine1,
			String communicationAddressLine2, String communicationAddressCity,
			String communicationAddressZip, String phone1, String phone2,
			String email, String fatherName, String bloodGroup,
			String communicationAddressStateOthers,
			String permanentAddressStateOthers, byte[] empPhoto, String code,
			String nickName, Integer mobile, Boolean smoker,
			String maritalStatus, String gender, String drivingLicenseNo,
			Date licenseExpDate, String militaryService, String ethinicRace,
			String toTime, String city, String emergencyContName,
			String relationship, String emergencyMobile,
			String emergencyHomeTelephone, String emergencyWorkTelephone,
			String workEmail, String otherEmail, String approvalRemarks,
			String uid, String panNo, String currentAddressHomeTelephone1,
			String currentAddressHomeTelephone2,Boolean isActive,
			String currentAddressHomeTelephone3,
			String currentAddressWorkTelephone1,
			String currentAddressWorkTelephone2,
			String currentAddressWorkTelephone3,
			String permanentAddressHomeTelephone1,
			String permanentAddressHomeTelephone2,
			String permanentAddressHomeTelephone3,
			String currentAddressMobile1, String currentAddressMobile2,
			String currentAddressMobile3, String permanentAddressMobile1,
			String permanentAddressMobile2, String permanentAddressMobile3,
			Set<EmpWorkExperience> empWorkExperiences,
			Set<EmployeeInfoBO> employeesForLeaveApproveAuthId,
			Set<StudentAppraisal> studentAppraisals,
			Set<EmployeeInfoBO> employeesForReportToId, Set<EmpSkills> empSkillses,
			Set<EmpImmigration> empImmigrations, Set<Users> userses,
			Set<EmpAppraisal> empAppraisalsForAppraiserEmpId,
			Set<EmpEducation> empEducations,
			Set<EmpAppraisal> empAppraisalsForAppraiseEmpId,
			Set<EmpAttendance> empAttendances,
			Set<EmpApplyLeave> empApplyLeaves,Set<EmpAcheivement> empAcheivements,
			Set<EmpDependents> empDependentses,Set<InvLocation> invLocations, 
			Set<EmpLanguage> empLanguages, Set<EmpLeave> empLeaves, Set<EmpJob> empJobs,EmployeeStreamBO streamId,
	 		EmployeeWorkLocationBO workLocationId,String motherName) {
		this.id = id;
		this.employeeByReportToId = employeeByReportToId;
		this.empImmigration = empImmigration;
		this.department = department;
		this.empWorkTime = empWorkTime;
		this.nationality = nationality;
		this.stateByCommunicationAddressStateId = stateByCommunicationAddressStateId;
		this.stateByPermanentAddressStateId = stateByPermanentAddressStateId;
		this.designation = designation;
		this.countryByPermanentAddressCountryId = countryByPermanentAddressCountryId;
		this.countryByCommunicationAddressCountryId = countryByCommunicationAddressCountryId;
		this.employeeByLeaveApproveAuthId = employeeByLeaveApproveAuthId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.dob = dob;
		this.doj = doj;
		this.isActive = isActive;
		this.empAcheivements = empAcheivements;
		this.permanentAddressLine1 = permanentAddressLine1;
		this.permanentAddressLine2 = permanentAddressLine2;
		this.permanentAddressCity = permanentAddressCity;
		this.permanentAddressZip = permanentAddressZip;
		this.communicationAddressLine1 = communicationAddressLine1;
		this.communicationAddressLine2 = communicationAddressLine2;
		this.communicationAddressCity = communicationAddressCity;
		this.communicationAddressZip = communicationAddressZip;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.email = email;
		this.fatherName = fatherName;
		this.bloodGroup = bloodGroup;
		this.communicationAddressStateOthers = communicationAddressStateOthers;
		this.permanentAddressStateOthers = permanentAddressStateOthers;
		this.empPhoto = empPhoto;
		this.code = code;
		this.nickName = nickName;
		this.mobile = mobile;
		this.smoker = smoker;
		this.maritalStatus = maritalStatus;
		this.gender = gender;
		this.drivingLicenseNo = drivingLicenseNo;
		this.licenseExpDate = licenseExpDate;
		this.militaryService = militaryService;
		this.ethinicRace = ethinicRace;
		this.toTime = toTime;
		this.city = city;
		this.emergencyContName = emergencyContName;
		this.relationship = relationship;
		this.emergencyMobile = emergencyMobile;
		this.emergencyHomeTelephone = emergencyHomeTelephone;
		this.emergencyWorkTelephone = emergencyWorkTelephone;
		this.workEmail = workEmail;
		this.otherEmail = otherEmail;
		this.approvalRemarks = approvalRemarks;
		this.uid = uid;
		this.panNo = panNo;
		this.currentAddressHomeTelephone1 = currentAddressHomeTelephone1;
		this.currentAddressHomeTelephone2 = currentAddressHomeTelephone2;
		this.currentAddressHomeTelephone3 = currentAddressHomeTelephone3;
		this.currentAddressWorkTelephone1 = currentAddressWorkTelephone1;
		this.currentAddressWorkTelephone2 = currentAddressWorkTelephone2;
		this.currentAddressWorkTelephone3 = currentAddressWorkTelephone3;
		this.permanentAddressHomeTelephone1 = permanentAddressHomeTelephone1;
		this.permanentAddressHomeTelephone2 = permanentAddressHomeTelephone2;
		this.permanentAddressHomeTelephone3 = permanentAddressHomeTelephone3;
		this.currentAddressMobile1 = currentAddressMobile1;
		this.currentAddressMobile2 = currentAddressMobile2;
		this.currentAddressMobile3 = currentAddressMobile3;
		this.permanentAddressMobile1 = permanentAddressMobile1;
		this.permanentAddressMobile2 = permanentAddressMobile2;
		this.permanentAddressMobile3 = permanentAddressMobile3;
		this.empWorkExperiences = empWorkExperiences;
		this.employeesForLeaveApproveAuthId = employeesForLeaveApproveAuthId;
		this.studentAppraisals = studentAppraisals;
		this.employeesForReportToId = employeesForReportToId;
		this.empSkillses = empSkillses;
		this.empImmigrations = empImmigrations;
		this.userses = userses;
		this.empAppraisalsForAppraiserEmpId = empAppraisalsForAppraiserEmpId;
		this.empEducations = empEducations;
		this.empAppraisalsForAppraiseEmpId = empAppraisalsForAppraiseEmpId;
		this.empAttendances = empAttendances;
		this.empApplyLeaves = empApplyLeaves;
		this.empDependentses = empDependentses;
		this.invLocations = invLocations;
		this.empLanguages = empLanguages;
		this.empLeaves = empLeaves;
		this.empJobs = empJobs;
		this.streamId=streamId;
		this.workLocationId=workLocationId;
		this.motherName=motherName;
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

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public Date getDoj() {
		return this.doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
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

	public String getPhone1() {
		return this.phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return this.phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFatherName() {
		return this.fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
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

	public byte[] getEmpPhoto() {
		return empPhoto;
	}

	public void setEmpPhoto(byte[] empPhoto) {
		this.empPhoto = empPhoto;
	}

	public EmpImmigration getEmpImmigration() {
		return empImmigration;
	}

	public void setEmpImmigration(EmpImmigration empImmigration) {
		this.empImmigration = empImmigration;
	}

	public EmpWorkTime getEmpWorkTime() {
		return empWorkTime;
	}

	public void setEmpWorkTime(EmpWorkTime empWorkTime) {
		this.empWorkTime = empWorkTime;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getMobile() {
		return mobile;
	}

	public void setMobile(Integer mobile) {
		this.mobile = mobile;
	}

	public Boolean getSmoker() {
		return smoker;
	}

	public void setSmoker(Boolean smoker) {
		this.smoker = smoker;
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

	public String getDrivingLicenseNo() {
		return drivingLicenseNo;
	}

	public void setDrivingLicenseNo(String drivingLicenseNo) {
		this.drivingLicenseNo = drivingLicenseNo;
	}

	public Date getLicenseExpDate() {
		return licenseExpDate;
	}

	public void setLicenseExpDate(Date licenseExpDate) {
		this.licenseExpDate = licenseExpDate;
	}

	public String getMilitaryService() {
		return militaryService;
	}

	public void setMilitaryService(String militaryService) {
		this.militaryService = militaryService;
	}

	public String getEthinicRace() {
		return ethinicRace;
	}

	public void setEthinicRace(String ethinicRace) {
		this.ethinicRace = ethinicRace;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmergencyContName() {
		return emergencyContName;
	}

	public void setEmergencyContName(String emergencyContName) {
		this.emergencyContName = emergencyContName;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
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

	public String getOtherEmail() {
		return otherEmail;
	}

	public void setOtherEmail(String otherEmail) {
		this.otherEmail = otherEmail;
	}

	public String getApprovalRemarks() {
		return approvalRemarks;
	}

	public void setApprovalRemarks(String approvalRemarks) {
		this.approvalRemarks = approvalRemarks;
	}

	public Set<EmpWorkExperience> getEmpWorkExperiences() {
		return empWorkExperiences;
	}

	public void setEmpWorkExperiences(Set<EmpWorkExperience> empWorkExperiences) {
		this.empWorkExperiences = empWorkExperiences;
	}

	public Set<StudentAppraisal> getStudentAppraisals() {
		return studentAppraisals;
	}

	public void setStudentAppraisals(Set<StudentAppraisal> studentAppraisals) {
		this.studentAppraisals = studentAppraisals;
	}

	public Set<EmpSkills> getEmpSkillses() {
		return empSkillses;
	}

	public void setEmpSkillses(Set<EmpSkills> empSkillses) {
		this.empSkillses = empSkillses;
	}

	public Set<EmpImmigration> getEmpImmigrations() {
		return empImmigrations;
	}

	public void setEmpImmigrations(Set<EmpImmigration> empImmigrations) {
		this.empImmigrations = empImmigrations;
	}

	public Set<Users> getUserses() {
		return userses;
	}

	public void setUserses(Set<Users> userses) {
		this.userses = userses;
	}

	public Set<EmpAppraisal> getEmpAppraisalsForAppraiserEmpId() {
		return empAppraisalsForAppraiserEmpId;
	}

	public void setEmpAppraisalsForAppraiserEmpId(
			Set<EmpAppraisal> empAppraisalsForAppraiserEmpId) {
		this.empAppraisalsForAppraiserEmpId = empAppraisalsForAppraiserEmpId;
	}

	public Set<EmpEducation> getEmpEducations() {
		return empEducations;
	}

	public void setEmpEducations(Set<EmpEducation> empEducations) {
		this.empEducations = empEducations;
	}

	public Set<EmpAppraisal> getEmpAppraisalsForAppraiseEmpId() {
		return empAppraisalsForAppraiseEmpId;
	}

	public void setEmpAppraisalsForAppraiseEmpId(
			Set<EmpAppraisal> empAppraisalsForAppraiseEmpId) {
		this.empAppraisalsForAppraiseEmpId = empAppraisalsForAppraiseEmpId;
	}

	public Set<EmpAttendance> getEmpAttendances() {
		return empAttendances;
	}

	public void setEmpAttendances(Set<EmpAttendance> empAttendances) {
		this.empAttendances = empAttendances;
	}

	public Set<EmpApplyLeave> getEmpApplyLeaves() {
		return empApplyLeaves;
	}

	public void setEmpApplyLeaves(Set<EmpApplyLeave> empApplyLeaves) {
		this.empApplyLeaves = empApplyLeaves;
	}

	public Set<EmpDependents> getEmpDependentses() {
		return empDependentses;
	}

	public void setEmpDependentses(Set<EmpDependents> empDependentses) {
		this.empDependentses = empDependentses;
	}

	public Set<EmpLanguage> getEmpLanguages() {
		return empLanguages;
	}

	public void setEmpLanguages(Set<EmpLanguage> empLanguages) {
		this.empLanguages = empLanguages;
	}

	public Set<EmpJob> getEmpJobs() {
		return empJobs;
	}

	public void setEmpJobs(Set<EmpJob> empJobs) {
		this.empJobs = empJobs;
	}

	public EmployeeInfoBO getEmployeeByReportToId() {
		return employeeByReportToId;
	}

	public void setEmployeeByReportToId(EmployeeInfoBO employeeByReportToId) {
		this.employeeByReportToId = employeeByReportToId;
	}

	public EmployeeInfoBO getEmployeeByLeaveApproveAuthId() {
		return employeeByLeaveApproveAuthId;
	}

	public void setEmployeeByLeaveApproveAuthId(
			EmployeeInfoBO employeeByLeaveApproveAuthId) {
		this.employeeByLeaveApproveAuthId = employeeByLeaveApproveAuthId;
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

	public String getCurrentAddressMobile2() {
		return currentAddressMobile2;
	}

	public void setCurrentAddressMobile2(String currentAddressMobile2) {
		this.currentAddressMobile2 = currentAddressMobile2;
	}

	public String getCurrentAddressMobile3() {
		return currentAddressMobile3;
	}

	public void setCurrentAddressMobile3(String currentAddressMobile3) {
		this.currentAddressMobile3 = currentAddressMobile3;
	}

	public String getPermanentAddressMobile1() {
		return permanentAddressMobile1;
	}

	public void setPermanentAddressMobile1(String permanentAddressMobile1) {
		this.permanentAddressMobile1 = permanentAddressMobile1;
	}

	public String getPermanentAddressMobile2() {
		return permanentAddressMobile2;
	}

	public void setPermanentAddressMobile2(String permanentAddressMobile2) {
		this.permanentAddressMobile2 = permanentAddressMobile2;
	}

	public String getPermanentAddressMobile3() {
		return permanentAddressMobile3;
	}

	public void setPermanentAddressMobile3(String permanentAddressMobile3) {
		this.permanentAddressMobile3 = permanentAddressMobile3;
	}

	public Set<EmployeeInfoBO> getEmployeesForLeaveApproveAuthId() {
		return employeesForLeaveApproveAuthId;
	}

	public void setEmployeesForLeaveApproveAuthId(
			Set<EmployeeInfoBO> employeesForLeaveApproveAuthId) {
		this.employeesForLeaveApproveAuthId = employeesForLeaveApproveAuthId;
	}

	public Set<EmployeeInfoBO> getEmployeesForReportToId() {
		return employeesForReportToId;
	}

	public void setEmployeesForReportToId(Set<EmployeeInfoBO> employeesForReportToId) {
		this.employeesForReportToId = employeesForReportToId;
	}

	public Set<EmpLeave> getEmpLeaves() {
		return empLeaves;
	}

	public void setEmpLeaves(Set<EmpLeave> empLeaves) {
		this.empLeaves = empLeaves;
	}

	public String getPermanentAddressHomeTelephone1() {
		return permanentAddressHomeTelephone1;
	}

	public void setPermanentAddressHomeTelephone1(
			String permanentAddressHomeTelephone1) {
		this.permanentAddressHomeTelephone1 = permanentAddressHomeTelephone1;
	}

	public String getPermanentAddressHomeTelephone2() {
		return permanentAddressHomeTelephone2;
	}

	public void setPermanentAddressHomeTelephone2(
			String permanentAddressHomeTelephone2) {
		this.permanentAddressHomeTelephone2 = permanentAddressHomeTelephone2;
	}

	public String getPermanentAddressHomeTelephone3() {
		return permanentAddressHomeTelephone3;
	}

	public void setPermanentAddressHomeTelephone3(
			String permanentAddressHomeTelephone3) {
		this.permanentAddressHomeTelephone3 = permanentAddressHomeTelephone3;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<EmpAcheivement> getEmpAcheivements() {
		return empAcheivements;
	}

	public void setEmpAcheivements(Set<EmpAcheivement> empAcheivements) {
		this.empAcheivements = empAcheivements;
	}

	public Set<InvLocation> getInvLocations() {
		return invLocations;
	}

	public void setInvLocations(Set<InvLocation> invLocations) {
		this.invLocations = invLocations;
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

	public void setFingerPrintId(String fingerPrintId) {
		this.fingerPrintId = fingerPrintId;
	}

	public String getFingerPrintId() {
		return fingerPrintId;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getMotherName() {
		return motherName;
	}
	
	public EmpType getEmptype() {
		return emptype;
	}

	public void setEmptype(EmpType emptype) {
		this.emptype = emptype;
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

	public String getPanCardNo() {
		return panCardNo;
	}

	public void setPanCardNo(String panCardNo) {
		this.panCardNo = panCardNo;
	}

	public Set<EmpEducationDetails> getEducationDetailsSet() {
		return educationDetailsSet;
	}

	public void setEducationDetailsSet(Set<EmpEducationDetails> educationDetailsSet) {
		this.educationDetailsSet = educationDetailsSet;
	}

	public Set<EmpOnlineEducationalDetails> getEducationalDetailsSet() {
		return educationalDetailsSet;
	}

	public void setEducationalDetailsSet(
			Set<EmpOnlineEducationalDetails> educationalDetailsSet) {
		this.educationalDetailsSet = educationalDetailsSet;
	}

	public Set<EmpAcheivement> getAcheivementSet() {
		return acheivementSet;
	}

	public void setAcheivementSet(Set<EmpAcheivement> acheivementSet) {
		this.acheivementSet = acheivementSet;
	}

	public Set<EmpOnlinePreviousExperience> getPreviousExpSet() {
		return previousExpSet;
	}

	public void setPreviousExpSet(Set<EmpOnlinePreviousExperience> previousExpSet) {
		this.previousExpSet = previousExpSet;
	}

	public Set<EmpPreviousOrg> getPreviousOrgSet() {
		return previousOrgSet;
	}

	public void setPreviousOrgSet(Set<EmpPreviousOrg> previousOrgSet) {
		this.previousOrgSet = previousOrgSet;
	}
}