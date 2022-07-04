package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.to.exam.PreviousClassDetailsTO;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.to.fee.FeeChallanVerificationTO;

public class StudentTO implements Serializable,Comparable<StudentTO>{

	private int id;
	private String rollNo;
	private String registerNo;
	private String studentName;
	private boolean tempChecked;
	private boolean checked;
	private int applicationNo;
	private int appliedYear;
	private AttendanceStudent student;
	private boolean activityPresent;
	private boolean coCurricularLeavePresent;
	private boolean studentLeave;
	private String gender;
	private int slNo;
	private String admittedThroughCode;
	private StudentLogin studentLogin;
	private PersonalData personalData;
	private AdmApplnTO admApplnTO;	
	private String courseName;
	private String semester;
	private String className;
	private String secondLanguage;
	private String percentageOfMarks;
	private int courseId;
	private boolean isPhoto;
	private String mobileNo1;
	private String mobileNo2;
	private int classId;
	private String dob;
	private String countryName;
	private String stateName;
	private String checked1;
	private Boolean isHide;
	private Date hidedetailsDate;
	private String hidedetailReasons;
	private List<SubjectTO> subjectList;
	private List<Integer> subjectIdList;
	private String status;
	// below properties are added for generate smart card data requirement
	private String courseCode;
	private String bankAccNo;
	private String bankName;
	private String bankNameFull;
	private String bankIncludeSection;
	private String prgmName;
	private String prgmCode;
	private String bloodGrp;
	private String fatherName;
	private String motherName;
	private String email;
	private String phNo1;
	private String phNo2;
	private String phNo3;
	private String permAddress1;
	private String permAddress2;
	private String permAddressCity;
	private String permAddressState;
	private String permAddressCountry;
	private String permAddressZip;
	private String currentAddress1;
	private String currentAddress2;
	private String currentAddressCity;
	private String currentAddressState;
	private String currentAddressCountry;
	private String currentAddressZip;
	private String no_scheme;
	private String sectionName;
	private String bankAdmittedThrough;
	private String courseCompletion;
	private String currentCityCode;
	private String currentBankStateId;
	private String permanantCityCode;
	private String permanantBankStateId;
	private String pbankCCode;
	private boolean isSCDataGenerated;
	private boolean isPhotoAvaliable;
	private int studentId;
	private String isChecked;
	// property for subject grp and subjects
	private Map<String, List<SubjectTO>> subjectsMap;
	private Map<PreviousClassDetailsTO, Map<String, List<SubjectTO>>> previousClassDetailsMap;
	private boolean previousHistoryAvailable;
	private Map<String,PreviousClassDetailsTO> studentHistoryMap;
	private List<PreviousClassDetailsTO> previousDetailsList;
	//added for challan verification requirement by smitha
	private String  challanNo;
	private int totalNonAdditionalfees;
	private int feePaymentId;
	List<FeeChallanVerificationTO> chTolist;
	private int admApplnId;
	private String subjectId;
	private String subjectName;
	private String parentNo;
	private String cardType;
	private String smartCardNo;
	private String appliedDate;
	private String remarks;
	private String reasonForRejection;
	private String approvedDate;
	private String religion;
	private String motherTongue;
	private String birthPlace;
	private String passportNo;
	private String passportValidity;
	private String universityEmail;
	private String nationality;
	private String caste;
	private String guardianAddress1;
	private String guardianAddress2;
	private String guardianAddressCity;
	private String guardianAddressState;
	private String guardianAddressCountry;
	private String guardianAddressZip;
	private String isHandicapped;
	private String handicappedDescription;
	private float totalAggrPer;
	
	//raghu
	private boolean isTaken;
	private boolean isCurrent;
	private String teacherName;
	private boolean isModify;
	
	//sms
	//raghu
	private List<Integer> periodList=new ArrayList<Integer>();
	private String ClassSchemeId;
	private String attendanceStudentId;
	private String periodName="";
	//private String periodName;
	private List<Integer> subList=  new ArrayList<Integer>();
	private Boolean isSmsSent;
	private boolean isAbsent;
	private String categoryName;
	private String headingName;
	private String amount;
	private String assignedAmount;
	private String totalPaidAmt;
	private String totalBalanceAmt;
	private String maxMarksInternal;
	private String totalInternalMarksT;
	private String totalInternalMarksP;
	private List<StudentMarksTO> studentMarksList;
	private StudentMarksTO studentAttMarksList;
	
	
	
	public String getTotalPaidAmt() {
		return totalPaidAmt;
	}
	public void setTotalPaidAmt(String totalPaidAmt) {
		this.totalPaidAmt = totalPaidAmt;
	}
	public String getTotalBalanceAmt() {
		return totalBalanceAmt;
	}
	public void setTotalBalanceAmt(String totalBalanceAmt) {
		this.totalBalanceAmt = totalBalanceAmt;
	}
	public String getAssignedAmount() {
		return assignedAmount;
	}
	public void setAssignedAmount(String assignedAmount) {
		this.assignedAmount = assignedAmount;
	}
	private List<PeriodTO> periodTOs;
	
	public List<Integer> getPeriodList() {
		return periodList;
	}
	public void setPeriodList(List<Integer> periodList) {
		this.periodList = periodList;
	}
	public String getClassSchemeId() {
		return ClassSchemeId;
	}
	public void setClassSchemeId(String classSchemeId) {
		ClassSchemeId = classSchemeId;
	}
	public String getAttendanceStudentId() {
		return attendanceStudentId;
	}
	public void setAttendanceStudentId(String attendanceStudentId) {
		this.attendanceStudentId = attendanceStudentId;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public List<Integer> getSubList() {
		return subList;
	}
	public void setSubList(List<Integer> subList) {
		this.subList = subList;
	}
	public Boolean getIsSmsSent() {
		return isSmsSent;
	}
	public void setIsSmsSent(Boolean isSmsSent) {
		this.isSmsSent = isSmsSent;
	}
	public boolean getIsAbsent() {
		return isAbsent;
	}
	public void setIsAbsent(boolean isAbsent) {
		this.isAbsent = isAbsent;
	}
	public boolean getIsModify() {
		return isModify;
	}
	public void setIsModify(boolean isModify) {
		this.isModify = isModify;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public boolean getIsCurrent() {
		return isCurrent;
	}
	public void setIsCurrent(boolean isCurrent) {
		this.isCurrent = isCurrent;
	}
	public boolean getIsTaken() {
		return isTaken;
	}
	public void setIsTaken(boolean isTaken) {
		this.isTaken = isTaken;
	}
	public String getClassName() {
		return className;
	}
	public String getSecondLanguage() {
		return secondLanguage;
	}
	public String getPercentageOfMarks() {
		return percentageOfMarks;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}
	public void setPercentageOfMarks(String percentageOfMarks) {
		this.percentageOfMarks = percentageOfMarks;
	}	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the rollNo
	 */
	public String getRollNo() {
		return rollNo;
	}
	/**
	 * @param rollNo the rollNo to set
	 */
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	/**
	 * @return the registerNo
	 */
	public String getRegisterNo() {
		return registerNo;
	}
	/**
	 * @param registerNo the registerNo to set
	 */
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	/**
	 * @return the tempChecked
	 */
	public boolean isTempChecked() {
		return tempChecked;
	}
	/**
	 * @param tempChecked the tempChecked to set
	 */
	public void setTempChecked(boolean tempChecked) {
		this.tempChecked = tempChecked;
	}
	/**
	 * @return the checked
	 */
	public boolean isChecked() {
		return checked;
	}
	/**
	 * @param checked the checked to set
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	/**
	 * @return the studentName
	 */
	public String getStudentName() {
		return studentName;
	}
	/**
	 * @param studentName the studentName to set
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(int applicationNo) {
		this.applicationNo = applicationNo;
	}
	public int getAppliedYear() {
		return appliedYear;
	}
	public void setAppliedYear(int appliedYear) {
		this.appliedYear = appliedYear;
	}
	/**
	 * @return the student
	 */
	public AttendanceStudent getStudent() {
		return student;
	}
	/**
	 * @param student the student to set
	 */
	public void setStudent(AttendanceStudent student) {
		this.student = student;
	}
	/**
	 * @return the activityPresent
	 */
	public boolean getActivityPresent() {
		return activityPresent;
	}
	/**
	 * @param activityPresent the activityPresent to set
	 */
	public void setActivityPresent(boolean activityPresent) {
		this.activityPresent = activityPresent;
	}
	/**
	 * @return the studentLeave
	 */
	public boolean getStudentLeave() {
		return studentLeave;
	}
	/**
	 * @param studentLeave the studentLeave to set
	 */
	public void setStudentLeave(boolean studentLeave) {
		this.studentLeave = studentLeave;
	}
	public int getSlNo() {
		return slNo;
	}
	public void setSlNo(int slNo) {
		this.slNo = slNo;
	}
	public String getAdmittedThroughCode() {
		return admittedThroughCode;
	}
	public void setAdmittedThroughCode(String admittedThroughCode) {
		this.admittedThroughCode = admittedThroughCode;
	}
	public StudentLogin getStudentLogin() {
		return studentLogin;
	}
	public PersonalData getPersonalData() {
		return personalData;
	}
	public AdmApplnTO getAdmApplnTO() {
		return admApplnTO;
	}
	public void setStudentLogin(StudentLogin studentLogin) {
		this.studentLogin = studentLogin;
	}
	public void setPersonalData(PersonalData personalData) {
		this.personalData = personalData;
	}
	public void setAdmApplnTO(AdmApplnTO admApplnTO) {
		this.admApplnTO = admApplnTO;
	}
	public boolean getCoCurricularLeavePresent() {
		return coCurricularLeavePresent;
	}
	public void setCoCurricularLeavePresent(boolean coCurricularLeavePresent) {
		this.coCurricularLeavePresent = coCurricularLeavePresent;
	}
	public String getCourseName() {
		return courseName;
	}
	public String getSemester() {
		return semester;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public boolean getIsPhoto() {
		return this.isPhoto;
	}
	public void setIsPhoto(boolean isPhoto) {
		this.isPhoto = isPhoto;
	}
	public String getMobileNo1() {
		return mobileNo1;
	}
	public void setMobileNo1(String mobileNo1) {
		this.mobileNo1 = mobileNo1;
	}
	public String getMobileNo2() {
		return mobileNo2;
	}
	public void setMobileNo2(String mobileNo2) {
		this.mobileNo2 = mobileNo2;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getChecked1() {
		return checked1;
	}
	public void setChecked1(String checked1) {
		this.checked1 = checked1;
	}
	
	
	public String getHidedetailReasons() {
		return hidedetailReasons;
	}
	public void setHidedetailReasons(String hidedetailReasons) {
		this.hidedetailReasons = hidedetailReasons;
	}
	
	public Boolean getIsHide() {
		return isHide;
	}
	public void setIsHide(Boolean isHide) {
		this.isHide = isHide;
	}
	public void setHidedetailsDate(Date hidedetailsDate) {
		this.hidedetailsDate = hidedetailsDate;
	}
	public Date getHidedetailsDate() {
		return hidedetailsDate;
	}
	public List<SubjectTO> getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(List<SubjectTO> subjectList) {
		this.subjectList = subjectList;
	}
	public List<Integer> getSubjectIdList() {
		return subjectIdList;
	}
	public void setSubjectIdList(List<Integer> subjectIdList) {
		this.subjectIdList = subjectIdList;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getBankAccNo() {
		return bankAccNo;
	}
	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankNameFull() {
		return bankNameFull;
	}
	public void setBankNameFull(String bankNameFull) {
		this.bankNameFull = bankNameFull;
	}
	public String getBankIncludeSection() {
		return bankIncludeSection;
	}
	public void setBankIncludeSection(String bankIncludeSection) {
		this.bankIncludeSection = bankIncludeSection;
	}
	public String getPrgmName() {
		return prgmName;
	}
	public void setPrgmName(String prgmName) {
		this.prgmName = prgmName;
	}
	public String getPrgmCode() {
		return prgmCode;
	}
	public void setPrgmCode(String prgmCode) {
		this.prgmCode = prgmCode;
	}
	public String getBloodGrp() {
		return bloodGrp;
	}
	public void setBloodGrp(String bloodGrp) {
		this.bloodGrp = bloodGrp;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhNo1() {
		return phNo1;
	}
	public void setPhNo1(String phNo1) {
		this.phNo1 = phNo1;
	}
	public String getPhNo2() {
		return phNo2;
	}
	public void setPhNo2(String phNo2) {
		this.phNo2 = phNo2;
	}
	public String getPhNo3() {
		return phNo3;
	}
	public void setPhNo3(String phNo3) {
		this.phNo3 = phNo3;
	}
	public String getPermAddress1() {
		return permAddress1;
	}
	public void setPermAddress1(String permAddress1) {
		this.permAddress1 = permAddress1;
	}
	public String getPermAddress2() {
		return permAddress2;
	}
	public void setPermAddress2(String permAddress2) {
		this.permAddress2 = permAddress2;
	}
	public String getPermAddressCity() {
		return permAddressCity;
	}
	public void setPermAddressCity(String permAddressCity) {
		this.permAddressCity = permAddressCity;
	}
	public String getPermAddressState() {
		return permAddressState;
	}
	public void setPermAddressState(String permAddressState) {
		this.permAddressState = permAddressState;
	}
	public String getPermAddressCountry() {
		return permAddressCountry;
	}
	public void setPermAddressCountry(String permAddressCountry) {
		this.permAddressCountry = permAddressCountry;
	}
	public String getPermAddressZip() {
		return permAddressZip;
	}
	public void setPermAddressZip(String permAddressZip) {
		this.permAddressZip = permAddressZip;
	}
	public String getCurrentAddress1() {
		return currentAddress1;
	}
	public void setCurrentAddress1(String currentAddress1) {
		this.currentAddress1 = currentAddress1;
	}
	public String getCurrentAddress2() {
		return currentAddress2;
	}
	public void setCurrentAddress2(String currentAddress2) {
		this.currentAddress2 = currentAddress2;
	}
	public String getCurrentAddressCity() {
		return currentAddressCity;
	}
	public void setCurrentAddressCity(String currentAddressCity) {
		this.currentAddressCity = currentAddressCity;
	}
	public String getCurrentAddressState() {
		return currentAddressState;
	}
	public void setCurrentAddressState(String currentAddressState) {
		this.currentAddressState = currentAddressState;
	}
	public String getCurrentAddressCountry() {
		return currentAddressCountry;
	}
	public void setCurrentAddressCountry(String currentAddressCountry) {
		this.currentAddressCountry = currentAddressCountry;
	}
	public String getCurrentAddressZip() {
		return currentAddressZip;
	}
	public void setCurrentAddressZip(String currentAddressZip) {
		this.currentAddressZip = currentAddressZip;
	}
	public String getNo_scheme() {
		return no_scheme;
	}
	public void setNo_scheme(String noScheme) {
		no_scheme = noScheme;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getBankAdmittedThrough() {
		return bankAdmittedThrough;
	}
	public void setBankAdmittedThrough(String bankAdmittedThrough) {
		this.bankAdmittedThrough = bankAdmittedThrough;
	}
	public String getCourseCompletion() {
		return courseCompletion;
	}
	public void setCourseCompletion(String courseCompletion) {
		this.courseCompletion = courseCompletion;
	}
	public String getCurrentCityCode() {
		return currentCityCode;
	}
	public void setCurrentCityCode(String currentCityCode) {
		this.currentCityCode = currentCityCode;
	}
	public String getCurrentBankStateId() {
		return currentBankStateId;
	}
	public void setCurrentBankStateId(String currentBankStateId) {
		this.currentBankStateId = currentBankStateId;
	}
	public String getPermanantCityCode() {
		return permanantCityCode;
	}
	public void setPermanantCityCode(String permanantCityCode) {
		this.permanantCityCode = permanantCityCode;
	}
	public String getPermanantBankStateId() {
		return permanantBankStateId;
	}
	public void setPermanantBankStateId(String permanantBankStateId) {
		this.permanantBankStateId = permanantBankStateId;
	}
	public String getPbankCCode() {
		return pbankCCode;
	}
	public void setPbankCCode(String pbankCCode) {
		this.pbankCCode = pbankCCode;
	}
	public boolean getisSCDataGenerated() {
		return isSCDataGenerated;
	}
	public void setSCDataGenerated(boolean isSCDataGenerated) {
		this.isSCDataGenerated = isSCDataGenerated;
	}
	public boolean isPhotoAvaliable() {
		return isPhotoAvaliable;
	}
	public void setPhotoAvaliable(boolean isPhotoAvaliable) {
		this.isPhotoAvaliable = isPhotoAvaliable;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	public Map<String, List<SubjectTO>> getSubjectsMap() {
		return subjectsMap;
	}
	public void setSubjectsMap(Map<String, List<SubjectTO>> subjectsMap) {
		this.subjectsMap = subjectsMap;
	}
	public Map<PreviousClassDetailsTO, Map<String, List<SubjectTO>>> getPreviousClassDetailsMap() {
		return previousClassDetailsMap;
	}
	public void setPreviousClassDetailsMap(
			Map<PreviousClassDetailsTO, Map<String, List<SubjectTO>>> previousClassDetailsMap) {
		this.previousClassDetailsMap = previousClassDetailsMap;
	}
	public boolean isPreviousHistoryAvailable() {
		return previousHistoryAvailable;
	}
	public void setPreviousHistoryAvailable(boolean previousHistoryAvailable) {
		this.previousHistoryAvailable = previousHistoryAvailable;
	}
	public Map<String, PreviousClassDetailsTO> getStudentHistoryMap() {
		return studentHistoryMap;
	}
	public void setStudentHistoryMap(
			Map<String, PreviousClassDetailsTO> studentHistoryMap) {
		this.studentHistoryMap = studentHistoryMap;
	}
	public List<PreviousClassDetailsTO> getPreviousDetailsList() {
		return previousDetailsList;
	}
	public void setPreviousDetailsList(
			List<PreviousClassDetailsTO> previousDetailsList) {
		this.previousDetailsList = previousDetailsList;
	}
	public String getChallanNo() {
		return challanNo;
	}
	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}
	
	public int getTotalNonAdditionalfees() {
		return totalNonAdditionalfees;
	}
	public void setTotalNonAdditionalfees(int totalNonAdditionalfees) {
		this.totalNonAdditionalfees = totalNonAdditionalfees;
	}
	public int getFeePaymentId() {
		return feePaymentId;
	}
	public void setFeePaymentId(int feePaymentId) {
		this.feePaymentId = feePaymentId;
	}
	public List<FeeChallanVerificationTO> getChTolist() {
		return chTolist;
	}
	public void setChTolist(List<FeeChallanVerificationTO> chTolist) {
		this.chTolist = chTolist;
	}
	public int getAdmApplnId() {
		return admApplnId;
	}
	public void setAdmApplnId(int admApplnId) {
		this.admApplnId = admApplnId;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	@Override
	public int compareTo(StudentTO arg0) {
		if(this.getRollNo()!=null && !this.rollNo.isEmpty() && arg0 instanceof StudentTO && arg0.getRollNo()!=null && !arg0.getRollNo().isEmpty()){
			//Integer n1=new Integer(this.getRollNo());
			//Integer n2=new Integer(arg0.rollNo);
			if (arg0.rollNo==null || (arg0.rollNo=="" || arg0.rollNo.equalsIgnoreCase(""))) {
				System.out.println(this.getRollNo() +" " +arg0.rollNo);
				return -1;
			}
			if (this.getRollNo()==null || (this.getRollNo()=="" || this.getRollNo().equalsIgnoreCase(""))) {
				System.out.println(this.getRollNo() +" " +arg0.rollNo);
				return 1;
			}
			System.out.println(this.getRollNo() +" " +arg0.rollNo);
			return this.getRollNo().compareTo(arg0.rollNo);
		
		}else if(this.getRegisterNo()!=null && !this.registerNo.isEmpty() && arg0 instanceof StudentTO && arg0.getRegisterNo()!=null && !arg0.getRegisterNo().isEmpty()){
			return this.getRegisterNo().compareTo(arg0.registerNo);
		}else if (this.getStudentName()!=null && !this.studentName.isEmpty() && arg0 instanceof StudentTO && arg0.getStudentName()!=null && !arg0.getStudentName().isEmpty()) {
			return this.getStudentName().compareTo(arg0.studentName);
		}
		return 0;
	}
	public String getParentNo() {
		return parentNo;
	}
	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getSmartCardNo() {
		return smartCardNo;
	}
	public void setSmartCardNo(String smartCardNo) {
		this.smartCardNo = smartCardNo;
	}
	public String getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(String appliedDate) {
		this.appliedDate = appliedDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getReasonForRejection() {
		return reasonForRejection;
	}
	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}
	public String getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getMotherTongue() {
		return motherTongue;
	}
	public void setMotherTongue(String motherTongue) {
		this.motherTongue = motherTongue;
	}
	public String getBirthPlace() {
		return birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	public String getPassportValidity() {
		return passportValidity;
	}
	public void setPassportValidity(String passportValidity) {
		this.passportValidity = passportValidity;
	}
	public String getUniversityEmail() {
		return universityEmail;
	}
	public void setUniversityEmail(String universityEmail) {
		this.universityEmail = universityEmail;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getCaste() {
		return caste;
	}
	public void setCaste(String caste) {
		this.caste = caste;
	}
	public String getGuardianAddress1() {
		return guardianAddress1;
	}
	public void setGuardianAddress1(String guardianAddress1) {
		this.guardianAddress1 = guardianAddress1;
	}
	public String getGuardianAddress2() {
		return guardianAddress2;
	}
	public void setGuardianAddress2(String guardianAddress2) {
		this.guardianAddress2 = guardianAddress2;
	}
	public String getGuardianAddressCity() {
		return guardianAddressCity;
	}
	public void setGuardianAddressCity(String guardianAddressCity) {
		this.guardianAddressCity = guardianAddressCity;
	}
	public String getGuardianAddressState() {
		return guardianAddressState;
	}
	public void setGuardianAddressState(String guardianAddressState) {
		this.guardianAddressState = guardianAddressState;
	}
	public String getGuardianAddressCountry() {
		return guardianAddressCountry;
	}
	public void setGuardianAddressCountry(String guardianAddressCountry) {
		this.guardianAddressCountry = guardianAddressCountry;
	}
	public String getGuardianAddressZip() {
		return guardianAddressZip;
	}
	public void setGuardianAddressZip(String guardianAddressZip) {
		this.guardianAddressZip = guardianAddressZip;
	}
	public String getIsHandicapped() {
		return isHandicapped;
	}
	public void setIsHandicapped(String isHandicapped) {
		this.isHandicapped = isHandicapped;
	}
	public String getHandicappedDescription() {
		return handicappedDescription;
	}
	public void setHandicappedDescription(String handicappedDescription) {
		this.handicappedDescription = handicappedDescription;
	}
	public float getTotalAggrPer() {
		return totalAggrPer;
	}
	public void setTotalAggrPer(float totalAggrPer) {
		this.totalAggrPer = totalAggrPer;
	}
	public List<PeriodTO> getPeriodTOs() {
		return periodTOs;
	}
	public void setPeriodTOs(List<PeriodTO> periodTOs) {
		this.periodTOs = periodTOs;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getHeadingName() {
		return headingName;
	}
	public void setHeadingName(String headingName) {
		this.headingName = headingName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getMaxMarksInternal() {
		return maxMarksInternal;
	}
	public void setMaxMarksInternal(String maxMarksInternal) {
		this.maxMarksInternal = maxMarksInternal;
	}
	public String getTotalInternalMarksT() {
		return totalInternalMarksT;
	}
	public void setTotalInternalMarksT(String totalInternalMarksT) {
		this.totalInternalMarksT = totalInternalMarksT;
	}
	public String getTotalInternalMarksP() {
		return totalInternalMarksP;
	}
	public void setTotalInternalMarksP(String totalInternalMarksP) {
		this.totalInternalMarksP = totalInternalMarksP;
	}
	public List<StudentMarksTO> getStudentMarksList() {
		return studentMarksList;
	}
	public void setStudentMarksList(List<StudentMarksTO> studentMarksList) {
		this.studentMarksList = studentMarksList;
	}
	public StudentMarksTO getStudentAttMarksList() {
		return studentAttMarksList;
	}
	public void setStudentAttMarksList(StudentMarksTO studentAttMarksList) {
		this.studentAttMarksList = studentAttMarksList;
	}
	
	
}