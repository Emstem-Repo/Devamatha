package com.kp.cms.bo.employee;

import java.io.Serializable;

public class EditMyProfile implements Serializable{
private int id;
private Integer empId;
private Integer name;
private Integer dateOfBirth;
private Integer dateOfJoin;
private Integer department;
private Integer designation;
private Integer currentAddress;
private Integer permanentAddress;
private Integer otherEmail;
private Integer workEmail;
private Integer fingerPrintId;
private Integer code;
private Integer bloodGroup;
private Integer uid;
private Integer gender;
private Integer maritalStatus;
private Integer nationality;
private Integer bankAccountNo;
private Integer twoWheelerNo;
private Integer fourWheelerNo;
private Integer smartCardNo;
private Integer reservationCategory;
private Integer mobile;
private Integer empLeave;
private Integer empLoan;
private Integer empFinancial;
private Integer empFeeConcession;
private Integer empEducationDetails;
private Integer empPreviousExp;
private Integer empPayAllowances;
private Integer empImmigrations;
private Integer empRemarks;
private Integer panNo;
private Integer pfNo;
private Integer resignationDetails;
private Integer grade;
private Integer empType;
private Integer workLocation;
private Integer empStream;
private Integer sameAddress;
private Integer isTeachingStaff;
private Integer religion;
private Integer subjectArea;
private Integer payScale;
private Integer dateOfRetirement;
private Integer workTimeEntry;
private Integer empContactDetails;
private Integer isSmartCardDelivered;
private Integer isSCDataGenerated;
private Integer empJobType;
private Integer noOfPublicationsRefered;
private Integer noOfPublicationsNotRefered;
private Integer active;
private Integer books;
private Integer homeTelephone;
private Integer empIncentives;
private Integer empDependents;
private Integer empAcheivements;
private Integer title;
private Integer reportTo;
private Integer qualificationLevel;
private Integer workTelephone;
public EditMyProfile(){
	
}

public EditMyProfile(int id, Integer empId, Integer name, Integer dateOfBirth,
		Integer dateOfJoin, Integer department, Integer designation,
		Integer currentAddress, Integer permanentAddress, Integer otherEmail,
		Integer workEmail, Integer fingerPrintId, Integer code,
		Integer bloodGroup, Integer uid, Integer gender, Integer maritalStatus,
		Integer nationality, Integer bankAccountNo, Integer twoWheelerNo,
		Integer fourWheelerNo, Integer smartCardNo,
		Integer reservationCategory, Integer mobile, Integer empLeave,
		Integer empLoan, Integer empFinancial, Integer empFeeConcession,
		Integer empEducationDetails, Integer empPreviousExp,
		Integer empPayAllowances, Integer empImmigrations, Integer empRemarks,
		Integer panNo, Integer pfNo, Integer resignationDetails, Integer grade,
		Integer empType, Integer workLocation, Integer empStream,
		Integer sameAddress, Integer isTeachingStaff, Integer religion,
		Integer subjectArea, Integer payScale, Integer dateOfRetirement,
		Integer workTimeEntry, Integer empContactDetails,
		Integer isSmartCardDelivered, Integer isSCDataGenerated,
		Integer empJobType, Integer noOfPublicationsRefered,
		Integer noOfPublicationsNotRefered, Integer active, Integer books,
		Integer homeTelephone, Integer empIncentives, Integer empDependents,
		Integer empAcheivements, Integer title,
		Integer reportTo, Integer qualificationLevel, Integer workTelephone) {
	super();
	this.id = id;
	this.empId = empId;
	this.name = name;
	this.dateOfBirth = dateOfBirth;
	this.dateOfJoin = dateOfJoin;
	this.department = department;
	this.designation = designation;
	this.currentAddress = currentAddress;
	this.permanentAddress = permanentAddress;
	this.otherEmail = otherEmail;
	this.workEmail = workEmail;
	this.fingerPrintId = fingerPrintId;
	this.code = code;
	this.bloodGroup = bloodGroup;
	this.uid = uid;
	this.gender = gender;
	this.maritalStatus = maritalStatus;
	this.nationality = nationality;
	this.bankAccountNo = bankAccountNo;
	this.twoWheelerNo = twoWheelerNo;
	this.fourWheelerNo = fourWheelerNo;
	this.smartCardNo = smartCardNo;
	this.reservationCategory = reservationCategory;
	this.mobile = mobile;
	this.empLeave = empLeave;
	this.empLoan = empLoan;
	this.empFinancial = empFinancial;
	this.empFeeConcession = empFeeConcession;
	this.empEducationDetails = empEducationDetails;
	this.empPreviousExp = empPreviousExp;
	this.empPayAllowances = empPayAllowances;
	this.empImmigrations = empImmigrations;
	this.empRemarks = empRemarks;
	this.panNo = panNo;
	this.pfNo = pfNo;
	this.resignationDetails = resignationDetails;
	this.grade = grade;
	this.empType = empType;
	this.workLocation = workLocation;
	this.empStream = empStream;
	this.sameAddress = sameAddress;
	this.isTeachingStaff = isTeachingStaff;
	this.religion = religion;
	this.subjectArea = subjectArea;
	this.payScale = payScale;
	this.dateOfRetirement = dateOfRetirement;
	this.workTimeEntry = workTimeEntry;
	this.empContactDetails = empContactDetails;
	this.isSmartCardDelivered = isSmartCardDelivered;
	this.isSCDataGenerated = isSCDataGenerated;
	this.empJobType = empJobType;
	this.noOfPublicationsRefered = noOfPublicationsRefered;
	this.noOfPublicationsNotRefered = noOfPublicationsNotRefered;
	this.active = active;
	this.books = books;
	this.homeTelephone = homeTelephone;
	this.empIncentives = empIncentives;
	this.empDependents = empDependents;
	this.empAcheivements = empAcheivements;
	this.title = title;
	this.reportTo = reportTo;
	this.qualificationLevel = qualificationLevel;
	this.workTelephone = workTelephone;
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Integer getEmpId() {
	return empId;
}
public void setEmpId(Integer empId) {
	this.empId = empId;
}
public Integer getName() {
	return name;
}
public void setName(Integer name) {
	this.name = name;
}
public Integer getDateOfBirth() {
	return dateOfBirth;
}
public void setDateOfBirth(Integer dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
}
public Integer getDateOfJoin() {
	return dateOfJoin;
}
public void setDateOfJoin(Integer dateOfJoin) {
	this.dateOfJoin = dateOfJoin;
}
public Integer getDepartment() {
	return department;
}
public void setDepartment(Integer department) {
	this.department = department;
}
public Integer getDesignation() {
	return designation;
}
public void setDesignation(Integer designation) {
	this.designation = designation;
}
public Integer getCurrentAddress() {
	return currentAddress;
}
public void setCurrentAddress(Integer currentAddress) {
	this.currentAddress = currentAddress;
}
public Integer getPermanentAddress() {
	return permanentAddress;
}
public void setPermanentAddress(Integer permanentAddress) {
	this.permanentAddress = permanentAddress;
}
public Integer getOtherEmail() {
	return otherEmail;
}
public void setOtherEmail(Integer otherEmail) {
	this.otherEmail = otherEmail;
}
public Integer getWorkEmail() {
	return workEmail;
}
public void setWorkEmail(Integer workEmail) {
	this.workEmail = workEmail;
}
public Integer getFingerPrintId() {
	return fingerPrintId;
}
public void setFingerPrintId(Integer fingerPrintId) {
	this.fingerPrintId = fingerPrintId;
}
public Integer getCode() {
	return code;
}
public void setCode(Integer code) {
	this.code = code;
}
public Integer getBloodGroup() {
	return bloodGroup;
}
public void setBloodGroup(Integer bloodGroup) {
	this.bloodGroup = bloodGroup;
}
public Integer getUid() {
	return uid;
}
public void setUid(Integer uid) {
	this.uid = uid;
}
public Integer getGender() {
	return gender;
}
public void setGender(Integer gender) {
	this.gender = gender;
}
public Integer getMaritalStatus() {
	return maritalStatus;
}
public void setMaritalStatus(Integer maritalStatus) {
	this.maritalStatus = maritalStatus;
}
public Integer getNationality() {
	return nationality;
}
public void setNationality(Integer nationality) {
	this.nationality = nationality;
}
public Integer getBankAccountNo() {
	return bankAccountNo;
}
public void setBankAccountNo(Integer bankAccountNo) {
	this.bankAccountNo = bankAccountNo;
}
public Integer getTwoWheelerNo() {
	return twoWheelerNo;
}
public void setTwoWheelerNo(Integer twoWheelerNo) {
	this.twoWheelerNo = twoWheelerNo;
}
public Integer getFourWheelerNo() {
	return fourWheelerNo;
}
public void setFourWheelerNo(Integer fourWheelerNo) {
	this.fourWheelerNo = fourWheelerNo;
}
public Integer getSmartCardNo() {
	return smartCardNo;
}
public void setSmartCardNo(Integer smartCardNo) {
	this.smartCardNo = smartCardNo;
}
public Integer getReservationCategory() {
	return reservationCategory;
}
public void setReservationCategory(Integer reservationCategory) {
	this.reservationCategory = reservationCategory;
}
public Integer getMobile() {
	return mobile;
}
public void setMobile(Integer mobile) {
	this.mobile = mobile;
}
public Integer getEmpLeave() {
	return empLeave;
}
public void setEmpLeave(Integer empLeave) {
	this.empLeave = empLeave;
}
public Integer getEmpLoan() {
	return empLoan;
}
public void setEmpLoan(Integer empLoan) {
	this.empLoan = empLoan;
}
public Integer getEmpFinancial() {
	return empFinancial;
}
public void setEmpFinancial(Integer empFinancial) {
	this.empFinancial = empFinancial;
}
public Integer getEmpFeeConcession() {
	return empFeeConcession;
}
public void setEmpFeeConcession(Integer empFeeConcession) {
	this.empFeeConcession = empFeeConcession;
}
public Integer getEmpEducationDetails() {
	return empEducationDetails;
}
public void setEmpEducationDetails(Integer empEducationDetails) {
	this.empEducationDetails = empEducationDetails;
}
public Integer getEmpPreviousExp() {
	return empPreviousExp;
}
public void setEmpPreviousExp(Integer empPreviousExp) {
	this.empPreviousExp = empPreviousExp;
}
public Integer getEmpPayAllowances() {
	return empPayAllowances;
}
public void setEmpPayAllowances(Integer empPayAllowances) {
	this.empPayAllowances = empPayAllowances;
}
public Integer getEmpImmigrations() {
	return empImmigrations;
}
public void setEmpImmigrations(Integer empImmigrations) {
	this.empImmigrations = empImmigrations;
}
public Integer getEmpRemarks() {
	return empRemarks;
}
public void setEmpRemarks(Integer empRemarks) {
	this.empRemarks = empRemarks;
}
public Integer getPanNo() {
	return panNo;
}
public void setPanNo(Integer panNo) {
	this.panNo = panNo;
}
public Integer getPfNo() {
	return pfNo;
}
public void setPfNo(Integer pfNo) {
	this.pfNo = pfNo;
}
public Integer getResignationDetails() {
	return resignationDetails;
}
public void setResignationDetails(Integer resignationDetails) {
	this.resignationDetails = resignationDetails;
}
public Integer getGrade() {
	return grade;
}
public void setGrade(Integer grade) {
	this.grade = grade;
}
public Integer getEmpType() {
	return empType;
}
public void setEmpType(Integer empType) {
	this.empType = empType;
}
public Integer getWorkLocation() {
	return workLocation;
}
public void setWorkLocation(Integer workLocation) {
	this.workLocation = workLocation;
}
public Integer getEmpStream() {
	return empStream;
}
public void setEmpStream(Integer empStream) {
	this.empStream = empStream;
}
public Integer getSameAddress() {
	return sameAddress;
}
public void setSameAddress(Integer sameAddress) {
	this.sameAddress = sameAddress;
}
public Integer getIsTeachingStaff() {
	return isTeachingStaff;
}
public void setIsTeachingStaff(Integer isTeachingStaff) {
	this.isTeachingStaff = isTeachingStaff;
}
public Integer getReligion() {
	return religion;
}
public void setReligion(Integer religion) {
	this.religion = religion;
}
public Integer getSubjectArea() {
	return subjectArea;
}
public void setSubjectArea(Integer subjectArea) {
	this.subjectArea = subjectArea;
}
public Integer getPayScale() {
	return payScale;
}
public void setPayScale(Integer payScale) {
	this.payScale = payScale;
}
public Integer getDateOfRetirement() {
	return dateOfRetirement;
}
public void setDateOfRetirement(Integer dateOfRetirement) {
	this.dateOfRetirement = dateOfRetirement;
}
public Integer getWorkTimeEntry() {
	return workTimeEntry;
}
public void setWorkTimeEntry(Integer workTimeEntry) {
	this.workTimeEntry = workTimeEntry;
}
public Integer getEmpContactDetails() {
	return empContactDetails;
}
public void setEmpContactDetails(Integer empContactDetails) {
	this.empContactDetails = empContactDetails;
}
public Integer getIsSmartCardDelivered() {
	return isSmartCardDelivered;
}
public void setIsSmartCardDelivered(Integer isSmartCardDelivered) {
	this.isSmartCardDelivered = isSmartCardDelivered;
}
public Integer getIsSCDataGenerated() {
	return isSCDataGenerated;
}
public void setIsSCDataGenerated(Integer isSCDataGenerated) {
	this.isSCDataGenerated = isSCDataGenerated;
}
public Integer getEmpJobType() {
	return empJobType;
}
public void setEmpJobType(Integer empJobType) {
	this.empJobType = empJobType;
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
public Integer getActive() {
	return active;
}
public void setActive(Integer active) {
	this.active = active;
}
public Integer getBooks() {
	return books;
}
public void setBooks(Integer books) {
	this.books = books;
}
public Integer getHomeTelephone() {
	return homeTelephone;
}
public void setHomeTelephone(Integer homeTelephone) {
	this.homeTelephone = homeTelephone;
}
public Integer getEmpIncentives() {
	return empIncentives;
}
public void setEmpIncentives(Integer empIncentives) {
	this.empIncentives = empIncentives;
}
public Integer getEmpDependents() {
	return empDependents;
}
public void setEmpDependents(Integer empDependents) {
	this.empDependents = empDependents;
}
public Integer getEmpAcheivements() {
	return empAcheivements;
}
public void setEmpAcheivements(Integer empAcheivements) {
	this.empAcheivements = empAcheivements;
}
public Integer getTitle() {
	return title;
}
public void setTitle(Integer title) {
	this.title = title;
}
public Integer getReportTo() {
	return reportTo;
}
public void setReportTo(Integer reportTo) {
	this.reportTo = reportTo;
}
public Integer getQualificationLevel() {
	return qualificationLevel;
}
public void setQualificationLevel(Integer qualificationLevel) {
	this.qualificationLevel = qualificationLevel;
}
public Integer getWorkTelephone() {
	return workTelephone;
}
public void setWorkTelephone(Integer workTelephone) {
	this.workTelephone = workTelephone;
}

}
