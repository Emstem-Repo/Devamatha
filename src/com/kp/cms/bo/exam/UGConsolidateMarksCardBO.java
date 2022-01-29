package com.kp.cms.bo.exam;

import java.math.BigDecimal;
import java.util.Date;

public class UGConsolidateMarksCardBO extends ExamGenBO{
	
	private int id;
	private int examId;
	private int studentId;
	private int classId;
	private int subjectId;
	private BigDecimal courseId;
	private String subjectCode;
	private String subjectName;
	private String theoryTotalSubInternalMark;
	private String theoryTotalAttendanceMark;
	private String practicalTotalSubInternalMark;
	private String practicalTotalAttendanceMark;
	private BigDecimal theoryeseMinimumMark;
	private BigDecimal practicaleseMinimumMark;
	private BigDecimal theoryeseMaximumMark;
	private BigDecimal practicaleseMaximumMark;
	private String studentTheoryMark;
	private String studentPracticalMark;
	private String firstName;
	private String middleName;
	private String lastName;
	private String section;
	private Boolean isInitialise;
	private int subjectOrder;
	private BigDecimal finalPracticalInternalMaximumMark;
	private BigDecimal finalTheoryInternalMaximumMark;
	private int practicalCredit;
	private int theoryCredit;
	private int termNumber;
	private String regNo;
	private String subType;
	private double theoryObtain;
	private double practicalObtain;
	private BigDecimal theoryMax;
	private BigDecimal practicalMax;
	private double theoryPercentage;
	private double practicalPercentage;
	private Boolean dontShowMaxMarks;
	private Boolean dontShowMinMarks;
	private Boolean showOnlyGrade;
	private Boolean dontShowSubType;
	private String name;
	private int appliedYear;
	private int sectionId;
	private int academicYear;
	private int selectedCourseId;
	
	public UGConsolidateMarksCardBO() {
		super();
	}

	public UGConsolidateMarksCardBO(int examId, int studentId,
			int classId, int subjectId, BigDecimal courseId,
			String subjectCode, String subjectName,
			String theoryTotalSubInternalMark, String theoryTotalAttendanceMark, String practicalTotalSubInternalMark,String practicalTotalAttendanceMark,
			BigDecimal theoryeseMinimumMark,BigDecimal practicaleseMinimumMark,BigDecimal theoryeseMaximumMark,BigDecimal practicaleseMaximumMark,String studentTheoryMark,
	 String studentPracticalMark,String firstName,String middleName,String lastName,String section,Boolean isInitialise,int subjectOrder,
	 BigDecimal finalPracticalInternalMaximumMark,BigDecimal finalTheoryInternalMaximumMark,int practicalCredit,int theoryCredit,int termNumber,
	 String regNo,String subType,double theoryObtain,double practicalObtain,BigDecimal theoryMax,BigDecimal practicalMax,
	 double theoryPercentage,double practicalPercentage,
	 Boolean dontShowMaxMarks,Boolean dontShowMinMarks,Boolean showOnlyGrade,Boolean dontShowSubType,String name,int appliedYear,int sectionId,int academicYear,int selectedCourseId) {
		
		this.examId = examId;
		this.studentId = studentId;
		this.classId = classId;
		this.subjectId = subjectId;
		this.courseId = courseId;
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.theoryTotalSubInternalMark = theoryTotalSubInternalMark;
		this.theoryTotalAttendanceMark = theoryTotalAttendanceMark;
		this.practicalTotalSubInternalMark = practicalTotalSubInternalMark;
		this.practicalTotalAttendanceMark = practicalTotalAttendanceMark;
		this.theoryeseMinimumMark = theoryeseMinimumMark;
		this.practicaleseMinimumMark = practicaleseMinimumMark;
		this.theoryeseMaximumMark = theoryeseMaximumMark;
		this.practicaleseMaximumMark = practicaleseMaximumMark;
		this.studentTheoryMark = studentTheoryMark;
		this.studentPracticalMark = studentPracticalMark;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.section = section;
		this.isInitialise = isInitialise;
		this.subjectOrder = subjectOrder;
		this.finalPracticalInternalMaximumMark = finalPracticalInternalMaximumMark;
		this.finalTheoryInternalMaximumMark = finalTheoryInternalMaximumMark;
		this.practicalCredit = practicalCredit;
		this.theoryCredit = theoryCredit;
		this.termNumber = termNumber;
		this.regNo = regNo;
		this.subType = subType;
		this.theoryObtain = theoryObtain;
		this.practicalObtain = practicalObtain;
		this.theoryMax = theoryMax;
		this.practicalMax = practicalMax;
		this.theoryPercentage = theoryPercentage;
		this.practicalPercentage = practicalPercentage;
		this.dontShowMinMarks = dontShowMinMarks;
		this.showOnlyGrade = showOnlyGrade;
		this.dontShowSubType = dontShowSubType;
		this.dontShowMaxMarks = dontShowMaxMarks;
		this.name = name;
		this.appliedYear = appliedYear;
		this.sectionId = sectionId;
		this.academicYear=academicYear;
		this.selectedCourseId=selectedCourseId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public BigDecimal getCourseId() {
		return courseId;
	}
	public void setCourseId(BigDecimal courseId) {
		this.courseId = courseId;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getTheoryTotalSubInternalMark() {
		return theoryTotalSubInternalMark;
	}
	public void setTheoryTotalSubInternalMark(String theoryTotalSubInternalMark) {
		this.theoryTotalSubInternalMark = theoryTotalSubInternalMark;
	}
	public String getTheoryTotalAttendanceMark() {
		return theoryTotalAttendanceMark;
	}
	public void setTheoryTotalAttendanceMark(String theoryTotalAttendanceMark) {
		this.theoryTotalAttendanceMark = theoryTotalAttendanceMark;
	}
	public String getPracticalTotalSubInternalMark() {
		return practicalTotalSubInternalMark;
	}
	public void setPracticalTotalSubInternalMark(
			String practicalTotalSubInternalMark) {
		this.practicalTotalSubInternalMark = practicalTotalSubInternalMark;
	}
	public String getPracticalTotalAttendanceMark() {
		return practicalTotalAttendanceMark;
	}
	public void setPracticalTotalAttendanceMark(String practicalTotalAttendanceMark) {
		this.practicalTotalAttendanceMark = practicalTotalAttendanceMark;
	}
	public BigDecimal getTheoryeseMinimumMark() {
		return theoryeseMinimumMark;
	}
	public void setTheoryeseMinimumMark(BigDecimal theoryeseMinimumMark) {
		this.theoryeseMinimumMark = theoryeseMinimumMark;
	}
	public BigDecimal getPracticaleseMinimumMark() {
		return practicaleseMinimumMark;
	}
	public void setPracticaleseMinimumMark(BigDecimal practicaleseMinimumMark) {
		this.practicaleseMinimumMark = practicaleseMinimumMark;
	}
	public BigDecimal getTheoryeseMaximumMark() {
		return theoryeseMaximumMark;
	}
	public void setTheoryeseMaximumMark(BigDecimal theoryeseMaximumMark) {
		this.theoryeseMaximumMark = theoryeseMaximumMark;
	}
	public BigDecimal getPracticaleseMaximumMark() {
		return practicaleseMaximumMark;
	}
	public void setPracticaleseMaximumMark(BigDecimal practicaleseMaximumMark) {
		this.practicaleseMaximumMark = practicaleseMaximumMark;
	}
	public String getStudentTheoryMark() {
		return studentTheoryMark;
	}
	public void setStudentTheoryMark(String studentTheoryMark) {
		this.studentTheoryMark = studentTheoryMark;
	}
	public String getStudentPracticalMark() {
		return studentPracticalMark;
	}
	public void setStudentPracticalMark(String studentPracticalMark) {
		this.studentPracticalMark = studentPracticalMark;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public Boolean getIsInitialise() {
		return isInitialise;
	}
	public void setIsInitialise(Boolean isInitialise) {
		this.isInitialise = isInitialise;
	}
	public int getSubjectOrder() {
		return subjectOrder;
	}
	public void setSubjectOrder(int subjectOrder) {
		this.subjectOrder = subjectOrder;
	}
	public BigDecimal getFinalPracticalInternalMaximumMark() {
		return finalPracticalInternalMaximumMark;
	}
	public void setFinalPracticalInternalMaximumMark(
			BigDecimal finalPracticalInternalMaximumMark) {
		this.finalPracticalInternalMaximumMark = finalPracticalInternalMaximumMark;
	}
	public BigDecimal getFinalTheoryInternalMaximumMark() {
		return finalTheoryInternalMaximumMark;
	}
	public void setFinalTheoryInternalMaximumMark(
			BigDecimal finalTheoryInternalMaximumMark) {
		this.finalTheoryInternalMaximumMark = finalTheoryInternalMaximumMark;
	}
	public int getPracticalCredit() {
		return practicalCredit;
	}
	public void setPracticalCredit(int practicalCredit) {
		this.practicalCredit = practicalCredit;
	}
	public int getTheoryCredit() {
		return theoryCredit;
	}
	public void setTheoryCredit(int theoryCredit) {
		this.theoryCredit = theoryCredit;
	}
	public int getTermNumber() {
		return termNumber;
	}
	public void setTermNumber(int termNumber) {
		this.termNumber = termNumber;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public double getTheoryObtain() {
		return theoryObtain;
	}
	public void setTheoryObtain(double theoryObtain) {
		this.theoryObtain = theoryObtain;
	}
	public double getPracticalObtain() {
		return practicalObtain;
	}
	public void setPracticalObtain(double practicalObtain) {
		this.practicalObtain = practicalObtain;
	}
	public BigDecimal getTheoryMax() {
		return theoryMax;
	}
	public void setTheoryMax(BigDecimal theoryMax) {
		this.theoryMax = theoryMax;
	}
	public BigDecimal getPracticalMax() {
		return practicalMax;
	}
	public void setPracticalMax(BigDecimal practicalMax) {
		this.practicalMax = practicalMax;
	}
	public double getTheoryPercentage() {
		return theoryPercentage;
	}
	public void setTheoryPercentage(double theoryPercentage) {
		this.theoryPercentage = theoryPercentage;
	}
	public double getPracticalPercentage() {
		return practicalPercentage;
	}
	public void setPracticalPercentage(double practicalPercentage) {
		this.practicalPercentage = practicalPercentage;
	}
	public Boolean getDontShowMaxMarks() {
		return dontShowMaxMarks;
	}
	public void setDontShowMaxMarks(Boolean dontShowMaxMarks) {
		this.dontShowMaxMarks = dontShowMaxMarks;
	}
	public Boolean getDontShowMinMarks() {
		return dontShowMinMarks;
	}
	public void setDontShowMinMarks(Boolean dontShowMinMarks) {
		this.dontShowMinMarks = dontShowMinMarks;
	}
	public Boolean getShowOnlyGrade() {
		return showOnlyGrade;
	}
	public void setShowOnlyGrade(Boolean showOnlyGrade) {
		this.showOnlyGrade = showOnlyGrade;
	}
	public Boolean getDontShowSubType() {
		return dontShowSubType;
	}
	public void setDontShowSubType(Boolean dontShowSubType) {
		this.dontShowSubType = dontShowSubType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getAppliedYear() {
		return appliedYear;
	}

	public void setAppliedYear(int appliedYear) {
		this.appliedYear = appliedYear;
	}

	public int getSectionId() {
		return sectionId;
	}

	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}

	public int getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}

	public int getSelectedCourseId() {
		return selectedCourseId;
	}

	public void setSelectedCourseId(int selectedCourseId) {
		this.selectedCourseId = selectedCourseId;
	}
}
