package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AdmMeritList implements Serializable{
    private int id;
    private String name;
    private Integer appNo;
    private String urbanRural;
    private String religion;
    private String scStScbt;
    private String cthPrOth;
    private String lastInstitute;
    private String karNonkar;
    private String examPass;
    private String yearPass;
    private String monthPass;
    private Integer noOfAttempts;
    private String mediumInstr;
    private BigDecimal percentage;
    private String firstPreference;
    private String remarks;
    private Integer totalMarks;
    private Integer maxMarks;
    private String rank;
    private Boolean christee;
    private String oldRegisterNo;
    private String adjRank;
    private String category;
    private String meritSet;
    private String ph;
    private String sex;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String applnOthers;
    private String otherColleges;
    private String distance;
    private Date submitDate;
    private BigDecimal overPerWtg;
    private String secondPreference;
    private String secondLanguage;
    private String bloodGroup;
    private Date dateOfBirth;
    private String pinCode;
    private String nationality;
    private String sheetNo;
    private Boolean sevnyrKar;
    private String placeOfBirth;
    private String mobileNo;
    private Integer academicYear;
    public AdmMeritList(){
    	
    }
	public AdmMeritList(int id, String name, Integer appNo, String urbanRural,
			String religion, String scStScbt, String cthPrOth,
			String lastInstitute, String karNonkar, String examPass,
			String yearPass, String monthPass, Integer noOfAttempts,
			String mediumInstr, BigDecimal percentage, String firstPreference,
			String remarks, Integer totalMarks, Integer maxMarks, String rank,
			Boolean christee, String oldRegisterNo, String adjRank,
			String category, String meritSet, String ph, String sex,
			String address1, String address2, String address3, String address4,
			String applnOthers, String otherColleges, String distance,
			Date submitDate, BigDecimal overPerWtg, String secondPreference,
			String secondLanguage, String bloodGroup, Date dateOfBirth,
			String pinCode, String nationality, String sheetNo,
			Boolean sevnyrKar, String placeOfBirth, String mobileNo) {
		super();
		this.id = id;
		this.name = name;
		this.appNo = appNo;
		this.urbanRural = urbanRural;
		this.religion = religion;
		this.scStScbt = scStScbt;
		this.cthPrOth = cthPrOth;
		this.lastInstitute = lastInstitute;
		this.karNonkar = karNonkar;
		this.examPass = examPass;
		this.yearPass = yearPass;
		this.monthPass = monthPass;
		this.noOfAttempts = noOfAttempts;
		this.mediumInstr = mediumInstr;
		this.percentage = percentage;
		this.firstPreference = firstPreference;
		this.remarks = remarks;
		this.totalMarks = totalMarks;
		this.maxMarks = maxMarks;
		this.rank = rank;
		this.christee = christee;
		this.oldRegisterNo = oldRegisterNo;
		this.adjRank = adjRank;
		this.category = category;
		this.meritSet = meritSet;
		this.ph = ph;
		this.sex = sex;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.address4 = address4;
		this.applnOthers = applnOthers;
		this.otherColleges = otherColleges;
		this.distance = distance;
		this.submitDate = submitDate;
		this.overPerWtg = overPerWtg;
		this.secondPreference = secondPreference;
		this.secondLanguage = secondLanguage;
		this.bloodGroup = bloodGroup;
		this.dateOfBirth = dateOfBirth;
		this.pinCode = pinCode;
		this.nationality = nationality;
		this.sheetNo = sheetNo;
		this.sevnyrKar = sevnyrKar;
		this.placeOfBirth = placeOfBirth;
		this.mobileNo = mobileNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAppNo() {
		return appNo;
	}
	public void setAppNo(Integer appNo) {
		this.appNo = appNo;
	}
	public String getUrbanRural() {
		return urbanRural;
	}
	public void setUrbanRural(String urbanRural) {
		this.urbanRural = urbanRural;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getScStScbt() {
		return scStScbt;
	}
	public void setScStScbt(String scStScbt) {
		this.scStScbt = scStScbt;
	}
	public String getCthPrOth() {
		return cthPrOth;
	}
	public void setCthPrOth(String cthPrOth) {
		this.cthPrOth = cthPrOth;
	}
	public String getLastInstitute() {
		return lastInstitute;
	}
	public void setLastInstitute(String lastInstitute) {
		this.lastInstitute = lastInstitute;
	}
	public String getKarNonkar() {
		return karNonkar;
	}
	public void setKarNonkar(String karNonkar) {
		this.karNonkar = karNonkar;
	}
	public String getExamPass() {
		return examPass;
	}
	public void setExamPass(String examPass) {
		this.examPass = examPass;
	}
	public String getYearPass() {
		return yearPass;
	}
	public void setYearPass(String yearPass) {
		this.yearPass = yearPass;
	}
	public String getMonthPass() {
		return monthPass;
	}
	public void setMonthPass(String monthPass) {
		this.monthPass = monthPass;
	}
	public Integer getNoOfAttempts() {
		return noOfAttempts;
	}
	public void setNoOfAttempts(Integer noOfAttempts) {
		this.noOfAttempts = noOfAttempts;
	}
	public String getMediumInstr() {
		return mediumInstr;
	}
	public void setMediumInstr(String mediumInstr) {
		this.mediumInstr = mediumInstr;
	}
	public BigDecimal getPercentage() {
		return percentage;
	}
	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}
	public String getFirstPreference() {
		return firstPreference;
	}
	public void setFirstPreference(String firstPreference) {
		this.firstPreference = firstPreference;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getTotalMarks() {
		return totalMarks;
	}
	public void setTotalMarks(Integer totalMarks) {
		this.totalMarks = totalMarks;
	}
	public Integer getMaxMarks() {
		return maxMarks;
	}
	public void setMaxMarks(Integer maxMarks) {
		this.maxMarks = maxMarks;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public Boolean getChristee() {
		return christee;
	}
	public void setChristee(Boolean christee) {
		this.christee = christee;
	}
	public String getOldRegisterNo() {
		return oldRegisterNo;
	}
	public void setOldRegisterNo(String oldRegisterNo) {
		this.oldRegisterNo = oldRegisterNo;
	}
	public String getAdjRank() {
		return adjRank;
	}
	public void setAdjRank(String adjRank) {
		this.adjRank = adjRank;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getMeritSet() {
		return meritSet;
	}
	public void setMeritSet(String meritSet) {
		this.meritSet = meritSet;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
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
	public String getApplnOthers() {
		return applnOthers;
	}
	public void setApplnOthers(String applnOthers) {
		this.applnOthers = applnOthers;
	}
	public String getOtherColleges() {
		return otherColleges;
	}
	public void setOtherColleges(String otherColleges) {
		this.otherColleges = otherColleges;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	public BigDecimal getOverPerWtg() {
		return overPerWtg;
	}
	public void setOverPerWtg(BigDecimal overPerWtg) {
		this.overPerWtg = overPerWtg;
	}
	public String getSecondPreference() {
		return secondPreference;
	}
	public void setSecondPreference(String secondPreference) {
		this.secondPreference = secondPreference;
	}
	public String getSecondLanguage() {
		return secondLanguage;
	}
	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getSheetNo() {
		return sheetNo;
	}
	public void setSheetNo(String sheetNo) {
		this.sheetNo = sheetNo;
	}
	public Boolean getSevnyrKar() {
		return sevnyrKar;
	}
	public void setSevnyrKar(Boolean sevnyrKar) {
		this.sevnyrKar = sevnyrKar;
	}
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}
	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public Integer getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}
    
}
