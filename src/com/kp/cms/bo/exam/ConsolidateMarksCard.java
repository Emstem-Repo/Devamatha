package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;

public class ConsolidateMarksCard implements Serializable {
	
	private int id;
	private ExamDefinition exam;
	private Student student;
	private Classes classes;
	private Subject subject;
	private Course course;
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
	private String grade;
	private String passOrFail;
	private int courseCompleteYear;
	private int batchEndYear;
	private BigDecimal finalTheoryInternalMinimumMark;
	private BigDecimal finalPracticalInternalMinimumMark;
	private BigDecimal theoryMin;
	private BigDecimal practicalMin;
	private Boolean dontConsiderFailureTotalResult;
	private Boolean isTheoryAppeared;
	private Boolean isPracticalAppeared;
	private Boolean dontAddInTotal;
	private BigDecimal gradePoint;
	private Boolean isCertificateCourse;
	private String createdBy;
	private Date createdDate;
	private String year;
	private String month;
	private Integer subjectStreamId;
	private Integer subjectTypeId;
	private String subjectFinalMin;
	private String subjectFinalMax;
	private String isExternalOrInternalSubject;
	private int chance;
	private Boolean isImprovement;
	private Boolean isSupplymentary;
	private String internalMarksBeforeGrace;
	private String theoryMarksBeforeGrace;
	private boolean isGraced;
	
	
	public ConsolidateMarksCard() {
		super();
	}

	public ConsolidateMarksCard(int id, ExamDefinition exam, Student student,
			Classes classes, Subject subject, Course course,
			String subjectCode, String subjectName,
			String theoryTotalSubInternalMark,
			String theoryTotalAttendanceMark,
			String practicalTotalSubInternalMark,
			String practicalTotalAttendanceMark,
			BigDecimal theoryeseMinimumMark,
			BigDecimal practicaleseMinimumMark,
			BigDecimal theoryeseMaximumMark,
			BigDecimal practicaleseMaximumMark, String studentTheoryMark,
			String studentPracticalMark, String firstName, String middleName,
			String lastName, String section, Boolean isInitialise,
			int subjectOrder, BigDecimal finalPracticalInternalMaximumMark,
			BigDecimal finalTheoryInternalMaximumMark, int practicalCredit,
			int theoryCredit, int termNumber, String regNo, String subType,
			double theoryObtain, double practicalObtain, BigDecimal theoryMax,
			BigDecimal practicalMax, double theoryPercentage,
			double practicalPercentage, Boolean dontShowMaxMarks,
			Boolean dontShowMinMarks, Boolean showOnlyGrade,
			Boolean dontShowSubType, String name, int appliedYear,
			int sectionId, int academicYear, int selectedCourseId,
			String grade, String passOrFail, int courseCompleteYear,
			int batchEndYear, BigDecimal finalTheoryInternalMinimumMark,
			BigDecimal finalPracticalInternalMinimumMark, BigDecimal theoryMin,
			BigDecimal practicalMin, Boolean dontConsiderFailureTotalResult,
			Boolean isTheoryAppeared, Boolean isPracticalAppeared,
			Boolean dontAddInTotal, BigDecimal gradePoint,
			Boolean isCertificateCourse,
			Integer subjectStreamId, Integer subjectTypeId) {
		super();
		this.id = id;
		this.exam = exam;
		this.student = student;
		this.classes = classes;
		this.subject = subject;
		this.course = course;
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
		this.dontShowMaxMarks = dontShowMaxMarks;
		this.dontShowMinMarks = dontShowMinMarks;
		this.showOnlyGrade = showOnlyGrade;
		this.dontShowSubType = dontShowSubType;
		this.name = name;
		this.appliedYear = appliedYear;
		this.sectionId = sectionId;
		this.academicYear = academicYear;
		this.selectedCourseId = selectedCourseId;
		this.grade = grade;
		this.passOrFail = passOrFail;
		this.courseCompleteYear = courseCompleteYear;
		this.batchEndYear = batchEndYear;
		this.finalTheoryInternalMinimumMark = finalTheoryInternalMinimumMark;
		this.finalPracticalInternalMinimumMark = finalPracticalInternalMinimumMark;
		this.theoryMin = theoryMin;
		this.practicalMin = practicalMin;
		this.dontConsiderFailureTotalResult = dontConsiderFailureTotalResult;
		this.isTheoryAppeared = isTheoryAppeared;
		this.isPracticalAppeared = isPracticalAppeared;
		this.dontAddInTotal = dontAddInTotal;
		this.gradePoint = gradePoint;
		this.isCertificateCourse = isCertificateCourse;
		this.subjectStreamId = subjectStreamId;
		this.subjectTypeId = subjectTypeId;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ExamDefinition getExam() {
		return exam;
	}

	public void setExam(ExamDefinition exam) {
		this.exam = exam;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
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

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getPassOrFail() {
		return passOrFail;
	}

	public void setPassOrFail(String passOrFail) {
		this.passOrFail = passOrFail;
	}

	public int getCourseCompleteYear() {
		return courseCompleteYear;
	}

	public void setCourseCompleteYear(int courseCompleteYear) {
		this.courseCompleteYear = courseCompleteYear;
	}

	public int getBatchEndYear() {
		return batchEndYear;
	}

	public void setBatchEndYear(int batchEndYear) {
		this.batchEndYear = batchEndYear;
	}

	public BigDecimal getFinalTheoryInternalMinimumMark() {
		return finalTheoryInternalMinimumMark;
	}

	public void setFinalTheoryInternalMinimumMark(
			BigDecimal finalTheoryInternalMinimumMark) {
		this.finalTheoryInternalMinimumMark = finalTheoryInternalMinimumMark;
	}

	public BigDecimal getFinalPracticalInternalMinimumMark() {
		return finalPracticalInternalMinimumMark;
	}

	public void setFinalPracticalInternalMinimumMark(
			BigDecimal finalPracticalInternalMinimumMark) {
		this.finalPracticalInternalMinimumMark = finalPracticalInternalMinimumMark;
	}

	public BigDecimal getTheoryMin() {
		return theoryMin;
	}

	public void setTheoryMin(BigDecimal theoryMin) {
		this.theoryMin = theoryMin;
	}

	public BigDecimal getPracticalMin() {
		return practicalMin;
	}

	public void setPracticalMin(BigDecimal practicalMin) {
		this.practicalMin = practicalMin;
	}

	public Boolean getDontConsiderFailureTotalResult() {
		return dontConsiderFailureTotalResult;
	}

	public void setDontConsiderFailureTotalResult(
			Boolean dontConsiderFailureTotalResult) {
		this.dontConsiderFailureTotalResult = dontConsiderFailureTotalResult;
	}

	public Boolean getIsTheoryAppeared() {
		return isTheoryAppeared;
	}

	public void setIsTheoryAppeared(Boolean isTheoryAppeared) {
		this.isTheoryAppeared = isTheoryAppeared;
	}

	public Boolean getIsPracticalAppeared() {
		return isPracticalAppeared;
	}

	public void setIsPracticalAppeared(Boolean isPracticalAppeared) {
		this.isPracticalAppeared = isPracticalAppeared;
	}

	public Boolean getDontAddInTotal() {
		return dontAddInTotal;
	}

	public void setDontAddInTotal(Boolean dontAddInTotal) {
		this.dontAddInTotal = dontAddInTotal;
	}

	public BigDecimal getGradePoint() {
		return gradePoint;
	}

	public void setGradePoint(BigDecimal gradePoint) {
		this.gradePoint = gradePoint;
	}

	public Boolean getIsCertificateCourse() {
		return isCertificateCourse;
	}

	public void setIsCertificateCourse(Boolean isCertificateCourse) {
		this.isCertificateCourse = isCertificateCourse;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getSubjectStreamId() {
		return subjectStreamId;
	}

	public void setSubjectStreamId(Integer subjectStreamId) {
		this.subjectStreamId = subjectStreamId;
	}

	public Integer getSubjectTypeId() {
		return subjectTypeId;
	}

	public void setSubjectTypeId(Integer subjectTypeId) {
		this.subjectTypeId = subjectTypeId;
	}

	public String getSubjectFinalMin() {
		return subjectFinalMin;
	}

	public void setSubjectFinalMin(String subjectFinalMin) {
		this.subjectFinalMin = subjectFinalMin;
	}

	public String getSubjectFinalMax() {
		return subjectFinalMax;
	}

	public void setSubjectFinalMax(String subjectFinalMax) {
		this.subjectFinalMax = subjectFinalMax;
	}

	public String getIsExternalOrInternalSubject() {
		return isExternalOrInternalSubject;
	}

	public void setIsExternalOrInternalSubject(String isExternalOrInternalSubject) {
		this.isExternalOrInternalSubject = isExternalOrInternalSubject;
	}

	public int getChance() {
		return chance;
	}

	public void setChance(int chance) {
		this.chance = chance;
	}

	public Boolean getIsImprovement() {
		return isImprovement;
	}

	public void setIsImprovement(Boolean isImprovement) {
		this.isImprovement = isImprovement;
	}

	public Boolean getIsSupplymentary() {
		return isSupplymentary;
	}

	public void setIsSupplymentary(Boolean isSupplymentary) {
		this.isSupplymentary = isSupplymentary;
	}
	public String getInternalMarksBeforeGrace() {
		return internalMarksBeforeGrace;
	}

	public void setInternalMarksBeforeGrace(String internalMarksBeforeGrace) {
		this.internalMarksBeforeGrace = internalMarksBeforeGrace;
	}

	public String getTheoryMarksBeforeGrace() {
		return theoryMarksBeforeGrace;
	}

	public void setTheoryMarksBeforeGrace(String theoryMarksBeforeGrace) {
		this.theoryMarksBeforeGrace = theoryMarksBeforeGrace;
	}

	public boolean getIsGraced() {
		return isGraced;
	}

	public void setIsGraced(boolean isGraced) {
		this.isGraced = isGraced;
	}
}