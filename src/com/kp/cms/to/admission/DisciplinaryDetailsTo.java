package com.kp.cms.to.admission;

import java.io.Serializable;

public class DisciplinaryDetailsTo implements Serializable{
	private String regRollNo;
	private String course;
	private String name;
	private String dateOfBirth;
	private String gender;
	private String eMail;
	private String mobNumber;
	private String phoneNo;
	private String nationality;
	private String dateOfAddmission;
	private String currentAddress;
	private String permanentAddress;
	private String passportNumber;
	private String issueCountry;
	private String validUpTo;
	private String resedentPermitNo;
	private String obtainedDate;
//	private byte[] document;
	private String fatherName;
	private String fatherEducation;
	private String fatherIncomeCurrency;
	private String fatherIncome;
	private String fatherOccupaction;
	private String fatheremail;
	private String fatherPhone;
	
	private String motherName;
	private String motherEducation;
	private String motherincomeCurrency;
	private String motherIncome;
	private String motheroccupaction;
	private String motherEmail;
	private String motherPhone;
	
	private String detentionDate;
	private int courseId;
	private String recommendedBy;
	//added for subject order
	private int semNo;
	private int semesterAcademicYear;
	private String hostelName;
	private String block;
	private String unit;
	private String roomNo;
	private String bedNo;
	
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getBedNo() {
		return bedNo;
	}
	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}
	public String getRegRollNo() {
		return regRollNo;
	}
	public void setRegRollNo(String regRollNo) {
		this.regRollNo = regRollNo;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getMobNumber() {
		return mobNumber;
	}
	public void setMobNumber(String mobNumber) {
		this.mobNumber = mobNumber;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getDateOfAddmission() {
		return dateOfAddmission;
	}
	public void setDateOfAddmission(String dateOfAddmission) {
		this.dateOfAddmission = dateOfAddmission;
	}
	public String getCurrentAddress() {
		return currentAddress;
	}
	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}
	public String getPermanentAddress() {
		return permanentAddress;
	}
	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}
	public String getPassportNumber() {
		return passportNumber;
	}
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
	public String getIssueCountry() {
		return issueCountry;
	}
	public void setIssueCountry(String issueCountry) {
		this.issueCountry = issueCountry;
	}
	public String getValidUpTo() {
		return validUpTo;
	}
	public void setValidUpTo(String validUpTo) {
		this.validUpTo = validUpTo;
	}
	public String getResedentPermitNo() {
		return resedentPermitNo;
	}
	public void setResedentPermitNo(String resedentPermitNo) {
		this.resedentPermitNo = resedentPermitNo;
	}
	public String getObtainedDate() {
		return obtainedDate;
	}
	public void setObtainedDate(String obtainedDate) {
		this.obtainedDate = obtainedDate;
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
	public String getFatherIncomeCurrency() {
		return fatherIncomeCurrency;
	}
	public void setFatherIncomeCurrency(String fatherIncomeCurrency) {
		this.fatherIncomeCurrency = fatherIncomeCurrency;
	}
	public String getFatherIncome() {
		return fatherIncome;
	}
	public void setFatherIncome(String fatherIncome) {
		this.fatherIncome = fatherIncome;
	}
	public String getFatherOccupaction() {
		return fatherOccupaction;
	}
	public void setFatherOccupaction(String fatherOccupaction) {
		this.fatherOccupaction = fatherOccupaction;
	}
	public String getFatheremail() {
		return fatheremail;
	}
	public void setFatheremail(String fatheremail) {
		this.fatheremail = fatheremail;
	}
	public String getFatherPhone() {
		return fatherPhone;
	}
	public void setFatherPhone(String fatherPhone) {
		this.fatherPhone = fatherPhone;
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
	public String getMotherincomeCurrency() {
		return motherincomeCurrency;
	}
	public void setMotherincomeCurrency(String motherincomeCurrency) {
		this.motherincomeCurrency = motherincomeCurrency;
	}
	public String getMotherIncome() {
		return motherIncome;
	}
	public void setMotherIncome(String motherIncome) {
		this.motherIncome = motherIncome;
	}
	public String getMotheroccupaction() {
		return motheroccupaction;
	}
	public void setMotheroccupaction(String motheroccupaction) {
		this.motheroccupaction = motheroccupaction;
	}
	public String getMotherEmail() {
		return motherEmail;
	}
	public void setMotherEmail(String motherEmail) {
		this.motherEmail = motherEmail;
	}
	public String getMotherPhone() {
		return motherPhone;
	}
	public void setMotherPhone(String motherPhone) {
		this.motherPhone = motherPhone;
	}
	public String getDetentionDate() {
		return detentionDate;
	}
	public void setDetentionDate(String detentionDate) {
		this.detentionDate = detentionDate;
	}
	public String getDetentionReson() {
		return detentionReson;
	}
	public void setDetentionReson(String detentionReson) {
		this.detentionReson = detentionReson;
	}
	public String getAbsentDays() {
		return absentDays;
	}
	public void setAbsentDays(String absentDays) {
		this.absentDays = absentDays;
	}
	public String getDisciplinaryDeails() {
		return disciplinaryDeails;
	}
	public void setDisciplinaryDeails(String disciplinaryDeails) {
		this.disciplinaryDeails = disciplinaryDeails;
	}
	private String detentionReson;
	private String absentDays;
	private String disciplinaryDeails;

	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getRecommendedBy() {
		return recommendedBy;
	}
	public void setRecommendedBy(String recommendedBy) {
		this.recommendedBy = recommendedBy;
	}
	public int getSemNo() {
		return semNo;
	}
	public void setSemNo(int semNo) {
		this.semNo = semNo;
	}
	public int getSemesterAcademicYear() {
		return semesterAcademicYear;
	}
	public void setSemesterAcademicYear(int semesterAcademicYear) {
		this.semesterAcademicYear = semesterAcademicYear;
	}

//	public byte[] getDocument() {
//		return document;
//	}
//	public void setDocument(byte[] document) {
//		this.document = document;
//	}
	
	

}
