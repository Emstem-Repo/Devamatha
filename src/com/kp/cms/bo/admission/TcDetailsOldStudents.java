package com.kp.cms.bo.admission;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.Category;
import com.kp.cms.bo.admin.CharacterAndConduct;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Religion;

public class TcDetailsOldStudents implements Serializable,Comparable<TcDetailsOldStudents>{
	private int id;
	private Integer acadamicYear;
	private String tcFor;
	private String tcType;
	private String tcNo;
	private String registerNo;
	private String name;
	private String studentNo;
	private Date dateOfBirth;
	private String gender;
	private String caste;
	private String subCaste;
	private String fatherName;
	private String motherName;
	private Date admissionDate;
	private Date dateOfLeaving;
	private String className;
	private String part1Subjects;
	private String part2Subjects;
	private String passed;
	private String publicExamName;
	private String examRegisterNo;
	private String scolorship;
	private String month;
	private Integer year;
	private String feePaid;
	private Date dateOfApplication;
	private Date dateOfIssue;
	private String tcSerialNo;
	private Religion religion;
	private Nationality nationality;
	private Caste category;
	private CharacterAndConduct characterAndConduct;
	private String religionOthers;
	private String nationalityOthers;
	private String categoryOthers;
	private String subjectsPassed;
	
	public TcDetailsOldStudents() {
		// TODO Auto-generated constructor stub
	}
	
	public TcDetailsOldStudents(int id){
		this.id=id;
	}
	
	/**
	 * @param id
	 * @param acadamicYear
	 * @param tcFor
	 * @param tcType
	 * @param registerNo
	 * @param name
	 * @param studentNo
	 * @param dateOfBirth
	 * @param gender
	 * @param caste
	 * @param subCaste
	 * @param fatherName
	 * @param motherName
	 * @param admissionDate
	 * @param dateOfLeaving
	 * @param className
	 * @param part1Subjects
	 * @param part2Subjects
	 * @param passed
	 * @param publicExamName
	 * @param examRegisterNo
	 * @param scolorship
	 * @param month
	 * @param year
	 * @param feePaid
	 * @param dateOfApplication
	 * @param dateOfIssue
	 * @param tcSerialNo
	 * @param religion
	 * @param nationality
	 * @param category
	 * @param characterAndConduct
	 */
	public TcDetailsOldStudents(int id, Integer acadamicYear, String tcFor,
			String tcType,String tcNo, String registerNo, String name, String studentNo,
			Date dateOfBirth, String gender, String caste, String subCaste,
			String fatherName, String motherName, Date admissionDate,
			Date dateOfLeaving, String className, String part1Subjects,
			String part2Subjects, String passed, String publicExamName,
			String examRegisterNo, String scolorship, String month,
			Integer year, String feePaid, Date dateOfApplication,
			Date dateOfIssue, String tcSerialNo, Religion religion,
			Nationality nationality, Caste category,
			CharacterAndConduct characterAndConduct,String religionOthers,String nationalityOthers,
			String categoryOthers, String subjectsPassed) {
		super();
		this.id = id;
		this.acadamicYear = acadamicYear;
		this.tcFor = tcFor;
		this.tcType = tcType;
		this.tcNo=tcNo;
		this.registerNo = registerNo;
		this.name = name;
		this.studentNo = studentNo;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.caste = caste;
		this.subCaste = subCaste;
		this.fatherName = fatherName;
		this.motherName = motherName;
		this.admissionDate = admissionDate;
		this.dateOfLeaving = dateOfLeaving;
		this.className = className;
		this.part1Subjects = part1Subjects;
		this.part2Subjects = part2Subjects;
		this.passed = passed;
		this.publicExamName = publicExamName;
		this.examRegisterNo = examRegisterNo;
		this.scolorship = scolorship;
		this.month = month;
		this.year = year;
		this.feePaid = feePaid;
		this.dateOfApplication = dateOfApplication;
		this.dateOfIssue = dateOfIssue;
		this.tcSerialNo = tcSerialNo;
		this.religion = religion;
		this.nationality = nationality;
		this.category = category;
		this.characterAndConduct = characterAndConduct;
		this.religionOthers=religionOthers;
		this.nationalityOthers=nationalityOthers;
		this.categoryOthers=categoryOthers;
		this.subjectsPassed=subjectsPassed;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getAcadamicYear() {
		return acadamicYear;
	}

	public void setAcadamicYear(Integer acadamicYear) {
		this.acadamicYear = acadamicYear;
	}

	public String getTcFor() {
		return tcFor;
	}

	public void setTcFor(String tcFor) {
		this.tcFor = tcFor;
	}

	public String getTcType() {
		return tcType;
	}

	public void setTcType(String tcType) {
		this.tcType = tcType;
	}

	public String getTcNo() {
		return tcNo;
	}

	public void setTcNo(String tcNo) {
		this.tcNo = tcNo;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCaste() {
		return caste;
	}

	public void setCaste(String caste) {
		this.caste = caste;
	}

	public String getSubCaste() {
		return subCaste;
	}

	public void setSubCaste(String subCaste) {
		this.subCaste = subCaste;
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

	public Date getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(Date admissionDate) {
		this.admissionDate = admissionDate;
	}

	public Date getDateOfLeaving() {
		return dateOfLeaving;
	}

	public void setDateOfLeaving(Date dateOfLeaving) {
		this.dateOfLeaving = dateOfLeaving;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPart1Subjects() {
		return part1Subjects;
	}

	public void setPart1Subjects(String part1Subjects) {
		this.part1Subjects = part1Subjects;
	}

	public String getPart2Subjects() {
		return part2Subjects;
	}

	public void setPart2Subjects(String part2Subjects) {
		this.part2Subjects = part2Subjects;
	}

	public String getPassed() {
		return passed;
	}

	public void setPassed(String passed) {
		this.passed = passed;
	}

	public String getPublicExamName() {
		return publicExamName;
	}

	public void setPublicExamName(String publicExamName) {
		this.publicExamName = publicExamName;
	}

	public String getExamRegisterNo() {
		return examRegisterNo;
	}

	public void setExamRegisterNo(String examRegisterNo) {
		this.examRegisterNo = examRegisterNo;
	}

	public String getScolorship() {
		return scolorship;
	}

	public void setScolorship(String scolorship) {
		this.scolorship = scolorship;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getFeePaid() {
		return feePaid;
	}

	public void setFeePaid(String feePaid) {
		this.feePaid = feePaid;
	}

	public Date getDateOfApplication() {
		return dateOfApplication;
	}

	public void setDateOfApplication(Date dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}

	public Date getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public String getTcSerialNo() {
		return tcSerialNo;
	}

	public void setTcSerialNo(String tcSerialNo) {
		this.tcSerialNo = tcSerialNo;
	}

	public Religion getReligion() {
		return religion;
	}

	public void setReligion(Religion religion) {
		this.religion = religion;
	}

	public Nationality getNationality() {
		return nationality;
	}

	public void setNationality(Nationality nationality) {
		this.nationality = nationality;
	}

	public Caste getCategory() {
		return category;
	}

	public void setCategory(Caste category) {
		this.category = category;
	}

	public CharacterAndConduct getCharacterAndConduct() {
		return characterAndConduct;
	}

	public void setCharacterAndConduct(CharacterAndConduct characterAndConduct) {
		this.characterAndConduct = characterAndConduct;
	}

	public String getReligionOthers() {
		return religionOthers;
	}

	public void setReligionOthers(String religionOthers) {
		this.religionOthers = religionOthers;
	}

	public String getNationalityOthers() {
		return nationalityOthers;
	}

	public void setNationalityOthers(String nationalityOthers) {
		this.nationalityOthers = nationalityOthers;
	}

	public String getCategoryOthers() {
		return categoryOthers;
	}

	public void setCategoryOthers(String categoryOthers) {
		this.categoryOthers = categoryOthers;
	}

	public String getSubjectsPassed() {
		return subjectsPassed;
	}

	public void setSubjectsPassed(String subjectsPassed) {
		this.subjectsPassed = subjectsPassed;
	}

	@Override
	public int compareTo(TcDetailsOldStudents tc1) {
		return this.getRegisterNo().compareTo(tc1.getRegisterNo());
	}

}
