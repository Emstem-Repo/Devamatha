package com.kp.cms.to.reports;

import java.io.Serializable;

public class ExportDataSearchTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String regNoDisp;
	private short regNoPois;
	private String rollNoDisp;
	private short rollNoPois;
	private String admissionDateDisp;
	private short admissionDatePois;
	private String courseNameDisp;
	private short courseNamePois;
	private String courseCodeDisp;
	private short courseCodePois;
	private String secondLanguageDisp;
	private short secondLanguagePois;
	private String acedamicYearDisp;
	private short acedamicYearPois;
	private String religionDisp;
	private short religionPois;
	private String nationalityDisp; 
	private short nationalityPois;
	private String studentPhoneNumberDisp; 
	private short studentPhoneNumberPois;
	private String studentMobileNumberDisp; 
	private short studentMobileNumberPois;
	private String permanentAddressLine1Disp;
	private short permanentAddressLine1Pois;
	private String permanentAddressLine2Disp;
	private short permanentAddressLine2Pois;
	private String currentAddressLine1Disp;
	private short currentAddressLine1Pois;
	private String currentAddressLine2Disp;
	private short currentAddressLine2Pois;
	private String fathersNameDisp;
	private short fathersNamePois;
	private String mothersNameDisp;
	private short mothersNamePois;
	private String parentAdressLine1Disp; 
	private short parentAdressLine1Pois;
	private String parentAdressLine2Disp; 
	private short parentAdressLine2Pois;
	private String parentAdressLine3Disp; 
	private short parentAdressLine3Pois;
	private String parentPhoneDisp; 
	private short parentPhonePois;
	private String parentMobileNoDisp; 
	private short parentMobileNoPois;
	private String applnNoDisp;
	private short applnNoPois;
	private String dateOfBirthDisp;
	private short dateOfBirthPois;
	private String genderDisp;
	private short genderPois;
	private String bloodGroupDisp;
	private short bloodGroupPois;
	private String nameDisp;
	private short namePois;
	private String semesterNoDisp;
	private short semesterNoPois;
	private String semesterNameDisp;
	private short semesterNamePois;
	private String phoneNoDisp;
	private short phoneNoPois;
	private String mobileNoDisp;
	private short mobileNoPois;
	private short classCodePois;
	
	private String curAddressCityDisp;
	private short curAddressCityPois;
	
	private String curAddressCountryDisp;
	private short curAddressCountryPois;

	private String curAddressStateDisp;
	private short curAddressStatePois;

	private String curAddressZipDisp;
	private short curAddressZipPois;


	private String perAddressCityDisp;
	private short perAddressCityPois;
	
	private String perAddressCountryDisp;
	private short perAddressCountryPois;

	private String perAddressStateDisp;
	private short perAddressStatePois;

	private String perAddressZipDisp;
	private short perAddressZipPois;
	
	private short validTillPos;
	
	private String programTypeName;
	private String programName;
	private String courseName;
	private String courseCode;
	private String appliedYear;
	private int applnNo;
	private String name;
	private String dateOfBirth;
	private String gender;
	private String bloodGroup;
	private String religion;
	private String religionOther;
	private String nationality;
	private String studentPhoneNo;
	private String studentMobileNo;
	private String permanentAddressline1;
	private String permanentAddressline2;
	private String permanentAddressline3;
	private String currentAddressline1;
	private String currentAddressline2;
	private String currentAddressline3;
	private String fatherName;
	private String motherName;
	private String parentPhone;	
	private String parentMobileNo;
	private String regNo;
	private String rollNo;
	private String secondLanguage;
	private String acedamicYear;
	private String semesterNo;
	private String semesterName;
	private boolean checked;
	private boolean updatedChecked;
	private String phoneNo;
	private String mobileNo;
	private String curAddressCity;
	private String curAddressCountry;
	private String curAddressState;
	private String curAddressZip;
	
	private String perAddressCity;
	private String perAddressCountry;
	private String perAddressState;
	private String perAddressZip;
	private String classCode;
	
	
	public boolean isUpdatedChecked() {
		return updatedChecked;
	}
	public void setUpdatedChecked(boolean updatedChecked) {
		this.updatedChecked = updatedChecked;
	}
	public String getRegNoDisp() {
		return regNoDisp;
	}
	public void setRegNoDisp(String regNoDisp) {
		this.regNoDisp = regNoDisp;
	}
	public short getRegNoPois() {
		return regNoPois;
	}
	public void setRegNoPois(short regNoPois) {
		this.regNoPois = regNoPois;
	}
	public String getRollNoDisp() {
		return rollNoDisp;
	}
	public void setRollNoDisp(String rollNoDisp) {
		this.rollNoDisp = rollNoDisp;
	}
	public short getRollNoPois() {
		return rollNoPois;
	}
	public void setRollNoPois(short rollNoPois) {
		this.rollNoPois = rollNoPois;
	}
	public String getAdmissionDateDisp() {
		return admissionDateDisp;
	}
	public void setAdmissionDateDisp(String admissionDateDisp) {
		this.admissionDateDisp = admissionDateDisp;
	}
	public short getAdmissionDatePois() {
		return admissionDatePois;
	}
	public void setAdmissionDatePois(short admissionDatePois) {
		this.admissionDatePois = admissionDatePois;
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
	public String getCourseCodeDisp() {
		return courseCodeDisp;
	}
	public void setCourseCodeDisp(String courseCodeDisp) {
		this.courseCodeDisp = courseCodeDisp;
	}
	public short getCourseCodePois() {
		return courseCodePois;
	}
	public void setCourseCodePois(short courseCodePois) {
		this.courseCodePois = courseCodePois;
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
	public String getReligionDisp() {
		return religionDisp;
	}
	public void setReligionDisp(String religionDisp) {
		this.religionDisp = religionDisp;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public short getReligionPois() {
		return religionPois;
	}
	public void setReligionPois(short religionPois) {
		this.religionPois = religionPois;
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
	public String getSemesterNoDisp() {
		return semesterNoDisp;
	}
	public void setSemesterNoDisp(String semesterNoDisp) {
		this.semesterNoDisp = semesterNoDisp;
	}
	public short getSemesterNoPois() {
		return semesterNoPois;
	}
	public void setSemesterNoPois(short semesterNoPois) {
		this.semesterNoPois = semesterNoPois;
	}
	public String getSemesterNameDisp() {
		return semesterNameDisp;
	}
	public void setSemesterNameDisp(String semesterNameDisp) {
		this.semesterNameDisp = semesterNameDisp;
	}
	public short getSemesterNamePois() {
		return semesterNamePois;
	}
	public void setSemesterNamePois(short semesterNamePois) {
		this.semesterNamePois = semesterNamePois;
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
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getAppliedYear() {
		return appliedYear;
	}
	public void setAppliedYear(String appliedYear) {
		this.appliedYear = appliedYear;
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
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getReligionOther() {
		return religionOther;
	}
	public void setReligionOther(String religionOther) {
		this.religionOther = religionOther;
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
	public String getPermanentAddressline3() {
		return permanentAddressline3;
	}
	public void setPermanentAddressline3(String permanentAddressline3) {
		this.permanentAddressline3 = permanentAddressline3;
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
	public String getCurrentAddressline3() {
		return currentAddressline3;
	}
	public void setCurrentAddressline3(String currentAddressline3) {
		this.currentAddressline3 = currentAddressline3;
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
	public String getSecondLanguage() {
		return secondLanguage;
	}
	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}
	public String getAcedamicYear() {
		return acedamicYear;
	}
	public void setAcedamicYear(String acedamicYear) {
		this.acedamicYear = acedamicYear;
	}
	public String getSemesterNo() {
		return semesterNo;
	}
	public void setSemesterNo(String semesterNo) {
		this.semesterNo = semesterNo;
	}
	public String getSemesterName() {
		return semesterName;
	}
	public void setSemesterName(String semesterName) {
		this.semesterName = semesterName;
	}
	public String getPhoneNoDisp() {
		return phoneNoDisp;
	}
	public void setPhoneNoDisp(String phoneNoDisp) {
		this.phoneNoDisp = phoneNoDisp;
	}
	public short getPhoneNoPois() {
		return phoneNoPois;
	}
	public void setPhoneNoPois(short phoneNoPois) {
		this.phoneNoPois = phoneNoPois;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getMobileNoDisp() {
		return mobileNoDisp;
	}
	public void setMobileNoDisp(String mobileNoDisp) {
		this.mobileNoDisp = mobileNoDisp;
	}
	public short getMobileNoPois() {
		return mobileNoPois;
	}
	public void setMobileNoPois(short mobileNoPois) {
		this.mobileNoPois = mobileNoPois;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getCurAddressCityDisp() {
		return curAddressCityDisp;
	}
	public void setCurAddressCityDisp(String curAddressCityDisp) {
		this.curAddressCityDisp = curAddressCityDisp;
	}
	public short getCurAddressCityPois() {
		return curAddressCityPois;
	}
	public void setCurAddressCityPois(short curAddressCityPois) {
		this.curAddressCityPois = curAddressCityPois;
	}
	public String getCurAddressCountryDisp() {
		return curAddressCountryDisp;
	}
	public void setCurAddressCountryDisp(String curAddressCountryDisp) {
		this.curAddressCountryDisp = curAddressCountryDisp;
	}
	public short getCurAddressCountryPois() {
		return curAddressCountryPois;
	}
	public void setCurAddressCountryPois(short curAddressCountryPois) {
		this.curAddressCountryPois = curAddressCountryPois;
	}
	public String getCurAddressStateDisp() {
		return curAddressStateDisp;
	}
	public void setCurAddressStateDisp(String curAddressStateDisp) {
		this.curAddressStateDisp = curAddressStateDisp;
	}
	public short getCurAddressStatePois() {
		return curAddressStatePois;
	}
	public void setCurAddressStatePois(short curAddressStatePois) {
		this.curAddressStatePois = curAddressStatePois;
	}
	public String getCurAddressZipDisp() {
		return curAddressZipDisp;
	}
	public void setCurAddressZipDisp(String curAddressZipDisp) {
		this.curAddressZipDisp = curAddressZipDisp;
	}
	public short getCurAddressZipPois() {
		return curAddressZipPois;
	}
	public void setCurAddressZipPois(short curAddressZipPois) {
		this.curAddressZipPois = curAddressZipPois;
	}
	public String getCurAddressCity() {
		return curAddressCity;
	}
	public void setCurAddressCity(String curAddressCity) {
		this.curAddressCity = curAddressCity;
	}
	public String getCurAddressCountry() {
		return curAddressCountry;
	}
	public void setCurAddressCountry(String curAddressCountry) {
		this.curAddressCountry = curAddressCountry;
	}
	public String getCurAddressState() {
		return curAddressState;
	}
	public void setCurAddressState(String curAddressState) {
		this.curAddressState = curAddressState;
	}
	public String getCurAddressZip() {
		return curAddressZip;
	}
	public void setCurAddressZip(String curAddressZip) {
		this.curAddressZip = curAddressZip;
	}
	public String getPerAddressCityDisp() {
		return perAddressCityDisp;
	}
	public void setPerAddressCityDisp(String perAddressCityDisp) {
		this.perAddressCityDisp = perAddressCityDisp;
	}
	public short getPerAddressCityPois() {
		return perAddressCityPois;
	}
	public void setPerAddressCityPois(short perAddressCityPois) {
		this.perAddressCityPois = perAddressCityPois;
	}
	public String getPerAddressCountryDisp() {
		return perAddressCountryDisp;
	}
	public void setPerAddressCountryDisp(String perAddressCountryDisp) {
		this.perAddressCountryDisp = perAddressCountryDisp;
	}
	public short getPerAddressCountryPois() {
		return perAddressCountryPois;
	}
	public void setPerAddressCountryPois(short perAddressCountryPois) {
		this.perAddressCountryPois = perAddressCountryPois;
	}
	public String getPerAddressStateDisp() {
		return perAddressStateDisp;
	}
	public void setPerAddressStateDisp(String perAddressStateDisp) {
		this.perAddressStateDisp = perAddressStateDisp;
	}
	public short getPerAddressStatePois() {
		return perAddressStatePois;
	}
	public void setPerAddressStatePois(short perAddressStatePois) {
		this.perAddressStatePois = perAddressStatePois;
	}
	public String getPerAddressZipDisp() {
		return perAddressZipDisp;
	}
	public void setPerAddressZipDisp(String perAddressZipDisp) {
		this.perAddressZipDisp = perAddressZipDisp;
	}
	public short getPerAddressZipPois() {
		return perAddressZipPois;
	}
	public void setPerAddressZipPois(short perAddressZipPois) {
		this.perAddressZipPois = perAddressZipPois;
	}
	public String getPerAddressCity() {
		return perAddressCity;
	}
	public void setPerAddressCity(String perAddressCity) {
		this.perAddressCity = perAddressCity;
	}
	public String getPerAddressCountry() {
		return perAddressCountry;
	}
	public void setPerAddressCountry(String perAddressCountry) {
		this.perAddressCountry = perAddressCountry;
	}
	public String getPerAddressState() {
		return perAddressState;
	}
	public void setPerAddressState(String perAddressState) {
		this.perAddressState = perAddressState;
	}
	public String getPerAddressZip() {
		return perAddressZip;
	}
	public void setPerAddressZip(String perAddressZip) {
		this.perAddressZip = perAddressZip;
	}
	public short getClassCodePois() {
		return classCodePois;
	}
	public void setClassCodePois(short classCodePois) {
		this.classCodePois = classCodePois;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public short getValidTillPos() {
		return validTillPos;
	}
	public void setValidTillPos(short validTillPos) {
		this.validTillPos = validTillPos;
	}
	

}