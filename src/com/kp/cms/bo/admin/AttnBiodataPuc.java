package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class AttnBiodataPuc implements Serializable{
	private int id;
	private int appNo;
	private int percentage;
	private String regNo;
	private String classes;
	private String name;
	private String year;
	private String section;
	private String fatherName;
	private String secndLang;
	private String religion;
	private String caste;
	private String scstbcbt;
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
	private String offRemarks;
	private String prnRemarks;
	private Boolean scholarship;
	private Date dateAdm;
	private int annIncome;
	private Float maintFees;
	private Boolean failed;
	private String dmmyNotUsd;
	private String dmmyNotUsd1;
	private String elecCode1;
	private String elecCode2;
	private String elecCode3;
	private String elecCode4;
	private String elecCode5;
	private String elecCode6;
	private String elecCode7;
	private String elecCode8;
	private int elecPos1;
	private int elecPos2;
	private int elecPos3;
	private int elecPos4;
	private int elecPos5;
	private int elecPos6;
	private int elecPos7;
	private int elecPos8;
	private String bloodGroup;
	private Boolean cetFeePaid;
	private Boolean aieeeFee;
	private String userCode;
	private int academicYear;
	public AttnBiodataPuc() {
		
	}
	public AttnBiodataPuc(int id, int appNo, int percentage, String regNo,
			String classes, String name, String year, String section,
			String fatherName, String secndLang, String religion, String caste,
			String scstbcbt, String sex, Date dob, String nationality,
			String state, String lastInst, String telephone, String address1,
			String address2, String address3, String address4,
			String offRemarks, String prnRemarks, Boolean scholarship,
			Date dateAdm, int annIncome, Float maintFees, Boolean failed,
			String dmmyNotUsd, String dmmyNotUsd1, String elecCode1,
			String elecCode2, String elecCode3, String elecCode4,
			String elecCode5, String elecCode6, String elecCode7,
			String elecCode8, int elecPos1, int elecPos2, int elecPos3,
			int elecPos4, int elecPos5, int elecPos6, int elecPos7,
			int elecPos8, String bloodGroup, Boolean cetFeePaid,
			Boolean aieeeFee, String userCode, int academicYear) {
		super();
		this.id = id;
		this.appNo = appNo;
		this.percentage = percentage;
		this.regNo = regNo;
		this.classes = classes;
		this.name = name;
		this.year = year;
		this.section = section;
		this.fatherName = fatherName;
		this.secndLang = secndLang;
		this.religion = religion;
		this.caste = caste;
		this.scstbcbt = scstbcbt;
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
		this.offRemarks = offRemarks;
		this.prnRemarks = prnRemarks;
		this.scholarship = scholarship;
		this.dateAdm = dateAdm;
		this.annIncome = annIncome;
		this.maintFees = maintFees;
		this.failed = failed;
		this.dmmyNotUsd = dmmyNotUsd;
		this.dmmyNotUsd1 = dmmyNotUsd1;
		this.elecCode1 = elecCode1;
		this.elecCode2 = elecCode2;
		this.elecCode3 = elecCode3;
		this.elecCode4 = elecCode4;
		this.elecCode5 = elecCode5;
		this.elecCode6 = elecCode6;
		this.elecCode7 = elecCode7;
		this.elecCode8 = elecCode8;
		this.elecPos1 = elecPos1;
		this.elecPos2 = elecPos2;
		this.elecPos3 = elecPos3;
		this.elecPos4 = elecPos4;
		this.elecPos5 = elecPos5;
		this.elecPos6 = elecPos6;
		this.elecPos7 = elecPos7;
		this.elecPos8 = elecPos8;
		this.bloodGroup = bloodGroup;
		this.cetFeePaid = cetFeePaid;
		this.aieeeFee = aieeeFee;
		this.userCode = userCode;
		this.academicYear = academicYear;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAppNo() {
		return appNo;
	}
	public void setAppNo(int appNo) {
		this.appNo = appNo;
	}
	public int getPercentage() {
		return percentage;
	}
	public void setPercentage(int percentage) {
		this.percentage = percentage;
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
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getSecndLang() {
		return secndLang;
	}
	public void setSecndLang(String secndLang) {
		this.secndLang = secndLang;
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
	public String getScstbcbt() {
		return scstbcbt;
	}
	public void setScstbcbt(String scstbcbt) {
		this.scstbcbt = scstbcbt;
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
	public String getOffRemarks() {
		return offRemarks;
	}
	public void setOffRemarks(String offRemarks) {
		this.offRemarks = offRemarks;
	}
	public String getPrnRemarks() {
		return prnRemarks;
	}
	public void setPrnRemarks(String prnRemarks) {
		this.prnRemarks = prnRemarks;
	}
	public Boolean getScholarship() {
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
	public int getAnnIncome() {
		return annIncome;
	}
	public void setAnnIncome(int annIncome) {
		this.annIncome = annIncome;
	}
	public Float getMaintFees() {
		return maintFees;
	}
	public void setMaintFees(Float maintFees) {
		this.maintFees = maintFees;
	}
	public Boolean getFailed() {
		return failed;
	}
	public void setFailed(Boolean failed) {
		this.failed = failed;
	}
	public String getDmmyNotUsd() {
		return dmmyNotUsd;
	}
	public void setDmmyNotUsd(String dmmyNotUsd) {
		this.dmmyNotUsd = dmmyNotUsd;
	}
	public String getDmmyNotUsd1() {
		return dmmyNotUsd1;
	}
	public void setDmmyNotUsd1(String dmmyNotUsd1) {
		this.dmmyNotUsd1 = dmmyNotUsd1;
	}
	public String getElecCode1() {
		return elecCode1;
	}
	public void setElecCode1(String elecCode1) {
		this.elecCode1 = elecCode1;
	}
	public String getElecCode2() {
		return elecCode2;
	}
	public void setElecCode2(String elecCode2) {
		this.elecCode2 = elecCode2;
	}
	public String getElecCode3() {
		return elecCode3;
	}
	public void setElecCode3(String elecCode3) {
		this.elecCode3 = elecCode3;
	}
	public String getElecCode4() {
		return elecCode4;
	}
	public void setElecCode4(String elecCode4) {
		this.elecCode4 = elecCode4;
	}
	public String getElecCode5() {
		return elecCode5;
	}
	public void setElecCode5(String elecCode5) {
		this.elecCode5 = elecCode5;
	}
	public String getElecCode6() {
		return elecCode6;
	}
	public void setElecCode6(String elecCode6) {
		this.elecCode6 = elecCode6;
	}
	public String getElecCode7() {
		return elecCode7;
	}
	public void setElecCode7(String elecCode7) {
		this.elecCode7 = elecCode7;
	}
	public String getElecCode8() {
		return elecCode8;
	}
	public void setElecCode8(String elecCode8) {
		this.elecCode8 = elecCode8;
	}
	public int getElecPos1() {
		return elecPos1;
	}
	public void setElecPos1(int elecPos1) {
		this.elecPos1 = elecPos1;
	}
	public int getElecPos2() {
		return elecPos2;
	}
	public void setElecPos2(int elecPos2) {
		this.elecPos2 = elecPos2;
	}
	public int getElecPos3() {
		return elecPos3;
	}
	public void setElecPos3(int elecPos3) {
		this.elecPos3 = elecPos3;
	}
	public int getElecPos4() {
		return elecPos4;
	}
	public void setElecPos4(int elecPos4) {
		this.elecPos4 = elecPos4;
	}
	public int getElecPos5() {
		return elecPos5;
	}
	public void setElecPos5(int elecPos5) {
		this.elecPos5 = elecPos5;
	}
	public int getElecPos6() {
		return elecPos6;
	}
	public void setElecPos6(int elecPos6) {
		this.elecPos6 = elecPos6;
	}
	public int getElecPos7() {
		return elecPos7;
	}
	public void setElecPos7(int elecPos7) {
		this.elecPos7 = elecPos7;
	}
	public int getElecPos8() {
		return elecPos8;
	}
	public void setElecPos8(int elecPos8) {
		this.elecPos8 = elecPos8;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public Boolean getCetFeePaid() {
		return cetFeePaid;
	}
	public void setCetFeePaid(Boolean cetFeePaid) {
		this.cetFeePaid = cetFeePaid;
	}
	public Boolean getAieeeFee() {
		return aieeeFee;
	}
	public void setAieeeFee(Boolean aieeeFee) {
		this.aieeeFee = aieeeFee;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public int getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}
	
}
