package com.kp.cms.bo.admin;

import java.util.Date;

public class AdmBioDataCJC implements java.io.Serializable {
 private int id;
 private int applnNo;
 private Float percentage;
 private String regNo;
 private String classes;
 private String name;
 private String section;
 private String year;
 private String fatherName;
 private String motherName;
 private String secondLanguage;
 private String religion;
 private String caste;
 private String scStBcBt;
 private String sex;
 private Date dob;
 private String nationality;
 private String state;
 private String lastInst;
 private String telephone;
 private String address1;
 private String address2;
 private String address3;
 private String address4;
 private String offRemark;
 private String prnRemark;
 private Float feesAmt;
 private Date fpaiddate;
 private String chalanNo;
 private Float annFees;
 private Date afPdDate;
 private String afChlnNo;
 private Boolean scholarship;
 private Date dateAdm;
 private Boolean tcGiven;
 private String passportNo;
 private String placeIssued;
 private Date dateIssued;
 private Float annIncome;
 private String placeBirth;
 private String stateBirth;
 private String country;
 private String fOccupation;
 private String examPassed;
 private String exRegNo;
 private int yearPass;
 private String monthPass;
 private String admTcNo;
 private Date admTcDt;
 private Date dateLeave;
 private String reasonLeave;
 private String tcNo;
 private Date tcDate;
 private String examResult;
 private String remarks;
 private Boolean nri;
 private Boolean lig;
 private Boolean religious;
 private Boolean selfFinan;
 private String sFinanCat;
 private Boolean cet;
 private String examRegNo;
 private String studentNo;
 private String feePayable;
 private Boolean foreign;
 private String admitCat;
 private String bloodGroup;
 private Boolean indSpCdt;
 private String phyHandicapped;
 private String medmInstr;
 private Boolean sEvnyKar;
 private String mobileNumber;
 private String academicYear;
 
public AdmBioDataCJC() {
	
}



public AdmBioDataCJC(int id, int applnNo, Float percentage, String regNo,
		String classes, String name, String section, String year,
		String fatherName, String motherName, String secondLanguage,
		String religion, String caste, String scStBcBt, String sex, Date dob,
		String nationality, String state, String lastInst, String telephone,
		String address1, String address2, String address3, String address4,
		String offRemark, String prnRemark, Float feesAmt, Date fpaiddate,
		String chalanNo, Float annFees, Date afPdDate, String afChlnNo,
		Boolean scholarship, Date dateAdm, Boolean tcGiven, String passportNo,
		String placeIssued, Date dateIssued, Float annIncome,
		String placeBirth, String stateBirth, String country,
		String fOccupation, String examPassed, String exRegNo, int yearPass,
		String monthPass, String admTcNo, Date admTcDt, Date dateLeave,
		String reasonLeave, String tcNo, Date tcDate, String examResult,
		String remarks, Boolean nri, Boolean lig, Boolean religious,
		Boolean selfFinan, String sFinanCat, Boolean cet, String examRegNo,
		String studentNo, String feePayable, Boolean foreign, String admitCat,
		String bloodGroup, Boolean indSpCdt, String phyHandicapped,
		String medmInstr, Boolean sEvnyKar, String mobileNumber,
		String academicYear) {
	super();
	this.id = id;
	this.applnNo = applnNo;
	this.percentage = percentage;
	this.regNo = regNo;
	this.classes = classes;
	this.name = name;
	this.section = section;
	this.year = year;
	this.fatherName = fatherName;
	this.motherName = motherName;
	this.secondLanguage = secondLanguage;
	this.religion = religion;
	this.caste = caste;
	this.scStBcBt = scStBcBt;
	this.sex = sex;
	this.dob = dob;
	this.nationality = nationality;
	this.state = state;
	this.lastInst = lastInst;
	this.telephone = telephone;
	this.address1 = address1;
	this.address2 = address2;
	this.address3 = address3;
	this.address4 = address4;
	this.offRemark = offRemark;
	this.prnRemark = prnRemark;
	this.feesAmt = feesAmt;
	this.fpaiddate = fpaiddate;
	this.chalanNo = chalanNo;
	this.annFees = annFees;
	this.afPdDate = afPdDate;
	this.afChlnNo = afChlnNo;
	this.scholarship = scholarship;
	this.dateAdm = dateAdm;
	this.tcGiven = tcGiven;
	this.passportNo = passportNo;
	this.placeIssued = placeIssued;
	this.dateIssued = dateIssued;
	this.annIncome = annIncome;
	this.placeBirth = placeBirth;
	this.stateBirth = stateBirth;
	this.country = country;
	this.fOccupation = fOccupation;
	this.examPassed = examPassed;
	this.exRegNo = exRegNo;
	this.yearPass = yearPass;
	this.monthPass = monthPass;
	this.admTcNo = admTcNo;
	this.admTcDt = admTcDt;
	this.dateLeave = dateLeave;
	this.reasonLeave = reasonLeave;
	this.tcNo = tcNo;
	this.tcDate = tcDate;
	this.examResult = examResult;
	this.remarks = remarks;
	this.nri = nri;
	this.lig = lig;
	this.religious = religious;
	this.selfFinan = selfFinan;
	this.sFinanCat = sFinanCat;
	this.cet = cet;
	this.examRegNo = examRegNo;
	this.studentNo = studentNo;
	this.feePayable = feePayable;
	this.foreign = foreign;
	this.admitCat = admitCat;
	this.bloodGroup = bloodGroup;
	this.indSpCdt = indSpCdt;
	this.phyHandicapped = phyHandicapped;
	this.medmInstr = medmInstr;
	this.sEvnyKar = sEvnyKar;
	this.mobileNumber = mobileNumber;
	this.academicYear = academicYear;
}



public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public int getApplnNo() {
	return applnNo;
}

public void setApplnNo(int applnNo) {
	this.applnNo = applnNo;
}


public String getRegNo() {
	return regNo;
}

public void setRegNo(String regNo) {
	this.regNo = regNo;
}

public String getClasses() {
	return classes;
}

public void setClasses(String classes) {
	this.classes = classes;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getSection() {
	return section;
}

public void setSection(String section) {
	this.section = section;
}

public String getYear() {
	return year;
}

public void setYear(String year) {
	this.year = year;
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

public String getSecondLanguage() {
	return secondLanguage;
}

public void setSecondLanguage(String secondLanguage) {
	this.secondLanguage = secondLanguage;
}

public String getReligion() {
	return religion;
}

public void setReligion(String religion) {
	this.religion = religion;
}

public String getCaste() {
	return caste;
}

public void setCaste(String caste) {
	this.caste = caste;
}

public String getScStBcBt() {
	return scStBcBt;
}

public void setScStBcBt(String scStBcBt) {
	this.scStBcBt = scStBcBt;
}

public String getSex() {
	return sex;
}

public void setSex(String sex) {
	this.sex = sex;
}

public Date getDob() {
	return dob;
}

public void setDob(Date dob) {
	this.dob = dob;
}

public String getNationality() {
	return nationality;
}

public void setNationality(String nationality) {
	this.nationality = nationality;
}

public String getState() {
	return state;
}

public void setState(String state) {
	this.state = state;
}

public String getLastInst() {
	return lastInst;
}

public void setLastInst(String lastInst) {
	this.lastInst = lastInst;
}

public String getTelephone() {
	return telephone;
}

public void setTelephone(String telephone) {
	this.telephone = telephone;
}

public String getAddress1() {
	return address1;
}

public void setAddress1(String address1) {
	this.address1 = address1;
}

public String getAddress2() {
	return address2;
}

public void setAddress2(String address2) {
	this.address2 = address2;
}

public String getAddress3() {
	return address3;
}

public void setAddress3(String address3) {
	this.address3 = address3;
}

public String getAddress4() {
	return address4;
}

public void setAddress4(String address4) {
	this.address4 = address4;
}

public String getOffRemark() {
	return offRemark;
}

public void setOffRemark(String offRemark) {
	this.offRemark = offRemark;
}

public String getPrnRemark() {
	return prnRemark;
}

public void setPrnRemark(String prnRemark) {
	this.prnRemark = prnRemark;
}

public Float getFeesAmt() {
	return feesAmt;
}

public void setFeesAmt(Float feesAmt) {
	this.feesAmt = feesAmt;
}

public Date getFpaiddate() {
	return fpaiddate;
}

public void setFpaiddate(Date fpaiddate) {
	this.fpaiddate = fpaiddate;
}

public String getChalanNo() {
	return chalanNo;
}

public void setChalanNo(String chalanNo) {
	this.chalanNo = chalanNo;
}

public Float getAnnFees() {
	return annFees;
}

public void setAnnFees(Float annFees) {
	this.annFees = annFees;
}

public Date getAfPdDate() {
	return afPdDate;
}

public void setAfPdDate(Date afPdDate) {
	this.afPdDate = afPdDate;
}

public String getAfChlnNo() {
	return afChlnNo;
}

public void setAfChlnNo(String afChlnNo) {
	this.afChlnNo = afChlnNo;
}

public Boolean isScholarship() {
	return scholarship;
}

public void setScholarship(Boolean scholarship) {
	this.scholarship = scholarship;
}

public Date getDateAdm() {
	return dateAdm;
}

public void setDateAdm(Date dateAdm) {
	this.dateAdm = dateAdm;
}

public Boolean isTcGiven() {
	return tcGiven;
}

public void setTcGiven(Boolean tcGiven) {
	this.tcGiven = tcGiven;
}

public String getPassportNo() {
	return passportNo;
}

public void setPassportNo(String passportNo) {
	this.passportNo = passportNo;
}

public String getPlaceIssued() {
	return placeIssued;
}

public void setPlaceIssued(String placeIssued) {
	this.placeIssued = placeIssued;
}

public Date getDateIssued() {
	return dateIssued;
}

public void setDateIssued(Date dateIssued) {
	this.dateIssued = dateIssued;
}

public Float getAnnIncome() {
	return annIncome;
}

public void setAnnIncome(Float annIncome) {
	this.annIncome = annIncome;
}

public String getPlaceBirth() {
	return placeBirth;
}

public void setPlaceBirth(String placeBirth) {
	this.placeBirth = placeBirth;
}

public String getStateBirth() {
	return stateBirth;
}

public void setStateBirth(String stateBirth) {
	this.stateBirth = stateBirth;
}

public String getCountry() {
	return country;
}

public void setCountry(String country) {
	this.country = country;
}

public String getfOccupation() {
	return fOccupation;
}

public void setfOccupation(String fOccupation) {
	this.fOccupation = fOccupation;
}

public String getExamPassed() {
	return examPassed;
}

public void setExamPassed(String examPassed) {
	this.examPassed = examPassed;
}

public String getExRegNo() {
	return exRegNo;
}

public void setExRegNo(String exRegNo) {
	this.exRegNo = exRegNo;
}

public int getYearPass() {
	return yearPass;
}

public void setYearPass(int yearPass) {
	this.yearPass = yearPass;
}

public String getMonthPass() {
	return monthPass;
}

public void setMonthPass(String monthPass) {
	this.monthPass = monthPass;
}

public String getAdmTcNo() {
	return admTcNo;
}

public void setAdmTcNo(String admTcNo) {
	this.admTcNo = admTcNo;
}

public Date getAdmTcDt() {
	return admTcDt;
}

public void setAdmTcDt(Date admTcDt) {
	this.admTcDt = admTcDt;
}

public Date getDateLeave() {
	return dateLeave;
}

public void setDateLeave(Date dateLeave) {
	this.dateLeave = dateLeave;
}

public String getReasonLeave() {
	return reasonLeave;
}

public void setReasonLeave(String reasonLeave) {
	this.reasonLeave = reasonLeave;
}

public String getTcNo() {
	return tcNo;
}

public void setTcNo(String tcNo) {
	this.tcNo = tcNo;
}

public Date getTcDate() {
	return tcDate;
}

public void setTcDate(Date tcDate) {
	this.tcDate = tcDate;
}

public String getExamResult() {
	return examResult;
}

public void setExamResult(String examResult) {
	this.examResult = examResult;
}

public String getRemarks() {
	return remarks;
}

public void setRemarks(String remarks) {
	this.remarks = remarks;
}

public Boolean isNri() {
	return nri;
}

public void setNri(Boolean nri) {
	this.nri = nri;
}

public Boolean isLig() {
	return lig;
}

public void setLig(Boolean lig) {
	this.lig = lig;
}

public Boolean isReligious() {
	return religious;
}

public void setReligious(Boolean religious) {
	this.religious = religious;
}

public Boolean isSelfFinan() {
	return selfFinan;
}

public void setSelfFinan(Boolean selfFinan) {
	this.selfFinan = selfFinan;
}

public String getsFinanCat() {
	return sFinanCat;
}

public void setsFinanCat(String sFinanCat) {
	this.sFinanCat = sFinanCat;
}

public Boolean isCet() {
	return cet;
}

public void setCet(Boolean cet) {
	this.cet = cet;
}

public String getExamRegNo() {
	return examRegNo;
}

public void setExamRegNo(String examRegNo) {
	this.examRegNo = examRegNo;
}

public String getStudentNo() {
	return studentNo;
}

public void setStudentNo(String studentNo) {
	this.studentNo = studentNo;
}

public String getFeePayable() {
	return feePayable;
}

public void setFeePayable(String feePayable) {
	this.feePayable = feePayable;
}

public Boolean isForeign() {
	return foreign;
}

public void setForeign(Boolean foreign) {
	this.foreign = foreign;
}

public String getAdmitCat() {
	return admitCat;
}

public void setAdmitCat(String admitCat) {
	this.admitCat = admitCat;
}

public String getBloodGroup() {
	return bloodGroup;
}

public void setBloodGroup(String bloodGroup) {
	this.bloodGroup = bloodGroup;
}

public Boolean isIndSpCdt() {
	return indSpCdt;
}

public void setIndSpCdt(Boolean indSpCdt) {
	this.indSpCdt = indSpCdt;
}

public String getPhyHandicapped() {
	return phyHandicapped;
}

public void setPhyHandicapped(String phyHandicapped) {
	this.phyHandicapped = phyHandicapped;
}

public String getMedmInstr() {
	return medmInstr;
}

public void setMedmInstr(String medmInstr) {
	this.medmInstr = medmInstr;
}

public Boolean issEvnyKar() {
	return sEvnyKar;
}

public void setsEvnyKar(Boolean sEvnyKar) {
	this.sEvnyKar = sEvnyKar;
}

public String getMobileNumber() {
	return mobileNumber;
}

public void setMobileNumber(String mobileNumber) {
	this.mobileNumber = mobileNumber;
}

public void setPercentage(Float percentage) {
	this.percentage = percentage;
}

public Float getPercentage() {
	return percentage;
}

public void setAcademicYear(String academicYear) {
	this.academicYear = academicYear;
}

public String getAcademicYear() {
	return academicYear;
}
 
}