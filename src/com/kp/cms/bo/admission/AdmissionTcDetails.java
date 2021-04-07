package com.kp.cms.bo.admission;

import java.io.Serializable;
import java.util.Date;

public class AdmissionTcDetails implements Serializable{
	private int id;
	private String regNo;
	private Date dateAdm;
	private String orderNo;
	private Date orderDate;
	private String name;
	private String sex;
	private String fatherName;
	private String motherName;
	private String nationality;
	private String religion;
	private String scStBcBt;
	private Date dateOfBirth;
	private String classes;
	private String year;
	private String section;
	private String secndLang;
	private String subject1;
	private String subject2;
	private String subject3;
	private String subject4;
	private String publicExam;
	private String exmRegNo;
	private String exmMonyr;
	private Boolean passed;
	private String passedLang;
	private String passedSubj;
	private Boolean govtSchlp;
	private Boolean freeShip;
	private String lastAttnDt;
	private Boolean feesPaid;
	private String degClass;
	private int firstYearMrk;
	private int secondYearMrk;
	private int thirdYearMrk;
	private int fourthYearMrk;
	private int outOfMrk;
	private Boolean leftStudnt;
	private Date dateLeave;
	private String reasonLeave;
	private String caste;
	private Boolean tcGiven;
	private String academicYear;
	
	public AdmissionTcDetails() {
		
	}

	public AdmissionTcDetails(int id, String regNo, Date dateAdm,
			String orderNo, Date orderDate, String name, String sex,
			String fatherName, String motherName, String nationality,
			String religion, String scStBcBt, Date dateOfBirth,
			String classes, String year, String section, String secndLang,
			String subject1, String subject2, String subject3, String subject4,
			String publicExam, String exmRegNo, String exmMonyr,
			Boolean passed, String passedLang, String passedSubj,
			Boolean govtSchlp, Boolean freeShip, String lastAttnDt,
			Boolean feesPaid, String degClass, int firstYearMrk,
			int secondYearMrk, int thirdYearMrk, int fourthYearMrk,
			int outOfMrk, Boolean leftStudnt, Date dateLeave,
			String reasonLeave, String caste, Boolean tcGiven,String academicYear) {
		super();
		this.id = id;
		this.regNo = regNo;
		this.dateAdm = dateAdm;
		this.orderNo = orderNo;
		this.orderDate = orderDate;
		this.name = name;
		this.sex = sex;
		this.fatherName = fatherName;
		this.motherName = motherName;
		this.nationality = nationality;
		this.religion = religion;
		this.scStBcBt = scStBcBt;
		this.dateOfBirth = dateOfBirth;
		this.classes = classes;
		this.year = year;
		this.section = section;
		this.secndLang = secndLang;
		this.subject1 = subject1;
		this.subject2 = subject2;
		this.subject3 = subject3;
		this.subject4 = subject4;
		this.publicExam = publicExam;
		this.exmRegNo = exmRegNo;
		this.exmMonyr = exmMonyr;
		this.passed = passed;
		this.passedLang = passedLang;
		this.passedSubj = passedSubj;
		this.govtSchlp = govtSchlp;
		this.freeShip = freeShip;
		this.lastAttnDt = lastAttnDt;
		this.feesPaid = feesPaid;
		this.degClass = degClass;
		this.firstYearMrk = firstYearMrk;
		this.secondYearMrk = secondYearMrk;
		this.thirdYearMrk = thirdYearMrk;
		this.fourthYearMrk = fourthYearMrk;
		this.outOfMrk = outOfMrk;
		this.leftStudnt = leftStudnt;
		this.dateLeave = dateLeave;
		this.reasonLeave = reasonLeave;
		this.caste = caste;
		this.tcGiven = tcGiven;
		this.academicYear = academicYear;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public Date getDateAdm() {
		return dateAdm;
	}

	public void setDateAdm(Date dateAdm) {
		this.dateAdm = dateAdm;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getScStBcBt() {
		return scStBcBt;
	}

	public void setScStBcBt(String scStBcBt) {
		this.scStBcBt = scStBcBt;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
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

	public String getSecndLang() {
		return secndLang;
	}

	public void setSecndLang(String secndLang) {
		this.secndLang = secndLang;
	}

	public String getSubject1() {
		return subject1;
	}

	public void setSubject1(String subject1) {
		this.subject1 = subject1;
	}

	public String getSubject2() {
		return subject2;
	}

	public void setSubject2(String subject2) {
		this.subject2 = subject2;
	}

	public String getSubject3() {
		return subject3;
	}

	public void setSubject3(String subject3) {
		this.subject3 = subject3;
	}

	public String getSubject4() {
		return subject4;
	}

	public void setSubject4(String subject4) {
		this.subject4 = subject4;
	}

	public String getPublicExam() {
		return publicExam;
	}

	public void setPublicExam(String publicExam) {
		this.publicExam = publicExam;
	}

	public String getExmRegNo() {
		return exmRegNo;
	}

	public void setExmRegNo(String exmRegNo) {
		this.exmRegNo = exmRegNo;
	}

	public String getExmMonyr() {
		return exmMonyr;
	}

	public void setExmMonyr(String exmMonyr) {
		this.exmMonyr = exmMonyr;
	}

	public Boolean getPassed() {
		return passed;
	}

	public void setPassed(Boolean passed) {
		this.passed = passed;
	}

	public String getPassedLang() {
		return passedLang;
	}

	public void setPassedLang(String passedLang) {
		this.passedLang = passedLang;
	}

	public String getPassedSubj() {
		return passedSubj;
	}

	public void setPassedSubj(String passedSubj) {
		this.passedSubj = passedSubj;
	}

	public Boolean getGovtSchlp() {
		return govtSchlp;
	}

	public void setGovtSchlp(Boolean govtSchlp) {
		this.govtSchlp = govtSchlp;
	}

	public Boolean getFreeShip() {
		return freeShip;
	}

	public void setFreeShip(Boolean freeShip) {
		this.freeShip = freeShip;
	}

	public String getLastAttnDt() {
		return lastAttnDt;
	}

	public void setLastAttnDt(String lastAttnDt) {
		this.lastAttnDt = lastAttnDt;
	}

	public Boolean getFeesPaid() {
		return feesPaid;
	}

	public void setFeesPaid(Boolean feesPaid) {
		this.feesPaid = feesPaid;
	}

	public String getDegClass() {
		return degClass;
	}

	public void setDegClass(String degClass) {
		this.degClass = degClass;
	}

	public int getFirstYearMrk() {
		return firstYearMrk;
	}

	public void setFirstYearMrk(int firstYearMrk) {
		this.firstYearMrk = firstYearMrk;
	}

	public int getSecondYearMrk() {
		return secondYearMrk;
	}

	public void setSecondYearMrk(int secondYearMrk) {
		this.secondYearMrk = secondYearMrk;
	}

	public int getThirdYearMrk() {
		return thirdYearMrk;
	}

	public void setThirdYearMrk(int thirdYearMrk) {
		this.thirdYearMrk = thirdYearMrk;
	}

	public int getFourthYearMrk() {
		return fourthYearMrk;
	}

	public void setFourthYearMrk(int fourthYearMrk) {
		this.fourthYearMrk = fourthYearMrk;
	}

	public int getOutOfMrk() {
		return outOfMrk;
	}

	public void setOutOfMrk(int outOfMrk) {
		this.outOfMrk = outOfMrk;
	}

	public Boolean getLeftStudnt() {
		return leftStudnt;
	}

	public void setLeftStudnt(Boolean leftStudnt) {
		this.leftStudnt = leftStudnt;
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

	public String getCaste() {
		return caste;
	}

	public void setCaste(String caste) {
		this.caste = caste;
	}

	public Boolean getTcGiven() {
		return tcGiven;
	}

	public void setTcGiven(Boolean tcGiven) {
		this.tcGiven = tcGiven;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getAcademicYear() {
		return academicYear;
	}
	
}
