package com.kp.cms.to.admission;

import com.kp.cms.bo.admin.Student;

public class PrintTcDetailsTo implements java.io.Serializable,Comparable<PrintTcDetailsTo>
{
	private String studentName;
	private String studentNo;
	private String conduct;
	private String passed;
	private String feePaid;
	private String dateOfApplication;
	private String reasonOfLeaving;
	private String dateOfLeaving;
	private String scholarship;
	private String dobWords;
	private String dobFigures;
	private String sex;
	private String religion;
	private String nationality;
	private String fatherName;
	private String motherName;
	private String dateOfAdmission;
	private String className;
	private String subjectsPart1;
	private String subjectsPart2;
	private String regMonthYear;
	private String caste;
	private String dateOfIssue;
	private String tcNo;
	private String slNo;
	private String mcNo;
	private String subjectsStudied;
	private String eligible;
	private String reason;
	private String course;
	private String regNo;
	private String tcDate;
	private String admissionClass;
	private String leavingYear;
	private String publicExamName;
	private String tcType;
	private String feeDueToUni;
	private String admittedYear;
	private String admissionnumber;
	private String promotionToNextClass;
	private String rollNo;
	private String academicYear;
	private String exammonth;
	private int examyear; 
	//code added by sudhir
	private String classOfLeaving;
	// added by smitha for display of old register nos
	private String detainedRegNos;
	
	private String duration;
	private Boolean isKerala;
	private String programType;
	private String programDuration;
	private String leavingAccademicYear;
	private String curDate;
	private String subjectPassedCore;
	private String subjectsPassedComplimentary;
	private String ccNo;
	
	public String getSubjectPassedCore() {
		return subjectPassedCore;
	}
	public void setSubjectPassedCore(String subjectPassedCore) {
		this.subjectPassedCore = subjectPassedCore;
	}
	public String getProgramDuration() {
		return programDuration;
	}
	public void setProgramDuration(String programDuration) {
		this.programDuration = programDuration;
	}
	public String getProgramType() {
		return programType;
	}
	public void setProgramType(String programType) {
		this.programType = programType;
	}
	public Boolean getIsKerala() {
		return isKerala;
	}
	public void setIsKerala(Boolean isKerala) {
		this.isKerala = isKerala;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentNo() {
		return studentNo;
	}
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	public String getConduct() {
		return conduct;
	}
	public void setConduct(String conduct) {
		this.conduct = conduct;
	}
	public String getPassed() {
		return passed;
	}
	public void setPassed(String passed) {
		this.passed = passed;
	}
	public String getFeePaid() {
		return feePaid;
	}
	public void setFeePaid(String feePaid) {
		this.feePaid = feePaid;
	}
	public String getDateOfApplication() {
		return dateOfApplication;
	}
	public void setDateOfApplication(String dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}
	public String getReasonOfLeaving() {
		return reasonOfLeaving;
	}
	public void setReasonOfLeaving(String reasonOfLeaving) {
		this.reasonOfLeaving = reasonOfLeaving;
	}
	public String getDateOfLeaving() {
		return dateOfLeaving;
	}
	public void setDateOfLeaving(String dateOfLeaving) {
		this.dateOfLeaving = dateOfLeaving;
	}
	public String getScholarship() {
		return scholarship;
	}
	public void setScholarship(String scholarship) {
		this.scholarship = scholarship;
	}
	public String getDobWords() {
		return dobWords;
	}
	public void setDobWords(String dobWords) {
		this.dobWords = dobWords;
	}
	public String getDobFigures() {
		return dobFigures;
	}
	public void setDobFigures(String dobFigures) {
		this.dobFigures = dobFigures;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
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
	public String getDateOfAdmission() {
		return dateOfAdmission;
	}
	public void setDateOfAdmission(String dateOfAdmission) {
		this.dateOfAdmission = dateOfAdmission;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSubjectsPart1() {
		return subjectsPart1;
	}
	public void setSubjectsPart1(String subjectsPart1) {
		this.subjectsPart1 = subjectsPart1;
	}
	public String getSubjectsPart2() {
		return subjectsPart2;
	}
	public void setSubjectsPart2(String subjectsPart2) {
		this.subjectsPart2 = subjectsPart2;
	}
	public String getRegMonthYear() {
		return regMonthYear;
	}
	public void setRegMonthYear(String regMonthYear) {
		this.regMonthYear = regMonthYear;
	}
	public String getCaste() {
		return caste;
	}
	public void setCaste(String caste) {
		this.caste = caste;
	}
	public String getDateOfIssue() {
		return dateOfIssue;
	}
	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}
	public String getTcNo() {
		return tcNo;
	}
	public void setTcNo(String tcNo) {
		this.tcNo = tcNo;
	}
	public String getSlNo() {
		return slNo;
	}
	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}
	public String getMcNo() {
		return mcNo;
	}
	public void setMcNo(String mcNo) {
		this.mcNo = mcNo;
	}
	public String getSubjectsStudied() {
		return subjectsStudied;
	}
	public void setSubjectsStudied(String subjectsStudied) {
		this.subjectsStudied = subjectsStudied;
	}
	public String getEligible() {
		return eligible;
	}
	public void setEligible(String eligible) {
		this.eligible = eligible;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getTcDate() {
		return tcDate;
	}
	public void setTcDate(String tcDate) {
		this.tcDate = tcDate;
	}
	public String getAdmissionClass() {
		return admissionClass;
	}
	public void setAdmissionClass(String admissionClass) {
		this.admissionClass = admissionClass;
	}
	public String getLeavingYear() {
		return leavingYear;
	}
	public void setLeavingYear(String leavingYear) {
		this.leavingYear = leavingYear;
	}
	public String getPublicExamName() {
		return publicExamName;
	}
	public void setPublicExamName(String publicExamName) {
		this.publicExamName = publicExamName;
	}
	public String getTcType() {
		return tcType;
	}
	public void setTcType(String tcType) {
		this.tcType = tcType;
	}
	public String getFeeDueToUni() {
		return feeDueToUni;
	}
	public void setFeeDueToUni(String feeDueToUni) {
		this.feeDueToUni = feeDueToUni;
	}
	public String getAdmittedYear() {
		return admittedYear;
	}
	public void setAdmittedYear(String admittedYear) {
		this.admittedYear = admittedYear;
	}
	public void setClassOfLeaving(String classOfLeaving) {
		this.classOfLeaving = classOfLeaving;
	}
	public String getClassOfLeaving() {
		return classOfLeaving;
	}
	public String getDetainedRegNos() {
		return detainedRegNos;
	}
	public void setDetainedRegNos(String detainedRegNos) {
		this.detainedRegNos = detainedRegNos;
	}
	
	public String getAdmissionnumber() {
		return admissionnumber;
	}
	public void setAdmissionnumber(String admissionnumber) {
		this.admissionnumber = admissionnumber;
	}
	
	public String getPromotionToNextClass() {
		return promotionToNextClass;
	}
	public void setPromotionToNextClass(String promotionToNextClass) {
		this.promotionToNextClass = promotionToNextClass;
	}
	
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	
	public String getExammonth() {
		return exammonth;
	}
	public void setExammonth(String exammonth) {
		this.exammonth = exammonth;
	}
	public int getExamyear() {
		return examyear;
	}
	public void setExamyear(int examyear) {
		this.examyear = examyear;
	}
	
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public int compareTo(PrintTcDetailsTo arg0) {
		if(arg0!=null && this!=null && arg0.getRegNo()!=null
				 && this.getRegNo()!=null){
				return this.getRegNo().compareTo(arg0.getRegNo());
		}else
		return 0;
	}
	public String getLeavingAccademicYear() {
		return leavingAccademicYear;
	}
	public void setLeavingAccademicYear(String leavingAccademicYear) {
		this.leavingAccademicYear = leavingAccademicYear;
	}
	public String getCurDate() {
		return curDate;
	}
	public void setCurDate(String curDate) {
		this.curDate = curDate;
	}
	public String getSubjectsPassedComplimentary() {
		return subjectsPassedComplimentary;
	}
	public void setSubjectsPassedComplimentary(String subjectsPassedComplimentary) {
		this.subjectsPassedComplimentary = subjectsPassedComplimentary;
	}
	public String getCcNo() {
		return ccNo;
	}
	public void setCcNo(String ccNo) {
		this.ccNo = ccNo;
	}
	
}
