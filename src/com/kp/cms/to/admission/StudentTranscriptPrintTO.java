package com.kp.cms.to.admission;

import java.io.Serializable;
import java.math.BigDecimal;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamDefinition;

/**
 * @author dIlIp
 *
 */
@SuppressWarnings("serial")
public class StudentTranscriptPrintTO implements Serializable,Comparable<StudentTranscriptPrintTO> {
	
	private int id;
	private ExamDefinition exam;
	private Student student;
	private Classes classes;
	private Subject subject;
	private Course course;
	private String subjectName;
	private String studentName;
	private String section;
	private int subjectOrder;
	
	private String regNo;
	private String subType;
	private double theoryPercentage;
	private double practicalPercentage;
	private Boolean showOnlyGrade;
	private Boolean dontShowSubType;
	
	private int appliedYear;
	private int academicYear;
	
	private String passOrFail;
	private BigDecimal finalTheoryInternalMinimumMark;
	private BigDecimal finalPracticalInternalMinimumMark;
	private BigDecimal theoryMin;
	private BigDecimal practicalMin;
	private Boolean dontConsiderFailureTotalResult;
	private Boolean dontAddInTotal;
	private Boolean isCertificateCourse;
	private String courseName;
	private String grade;
	private int termNumber;
	private String schemeName;
	private Boolean isSuppTheoryAppeared;
	private Boolean isSuppPracticalAppeared;
	private BigDecimal theCiaMaxMarks;
	private String theCiaMarksAwarded;
	private BigDecimal attMaxMarks;
	private String theAttMarksAwarded;
	private BigDecimal theEndSemMaxMarks;
	private BigDecimal theEndSemMinMarks;
	private String theEndSemMarksAwarded;
	private BigDecimal theTotMaxMarks;
	private double theTotMarksAwarded;
	private int theTotMarksAwardedDisplay;
	private int theCredit;
	private String totalMarksInWords;
	private String result;
	private String totalCreditsAwarded;
	private String totalGradePoint;
	private int percentage;
	private int totalMaxMarks;
	private int totalMaxMarksAwarded;
	
	private BigDecimal praCiaMaxMarks;
	private String praCiaMarksAwarded;
	private String praAttMarksAwarded;
	private BigDecimal praEndSemMaxMarks;
	private BigDecimal praEndSemMinMarks;
	private String praEndSemMarksAwarded;
	private BigDecimal praTotMaxMarks;
	private double praTotMarksAwarded;
	private int praTotMarksAwardedDisplay;
	private int praCredit;
	private String monthYear;
	private boolean showSuppl;
	private boolean showSupplDisplay;
	private BigDecimal gradePoint;
	
	public int compareTo(StudentTranscriptPrintTO studentTranscriptPrintTO) {
		if(studentTranscriptPrintTO!=null && this!=null){
			if(this.getSubjectOrder() > studentTranscriptPrintTO.getSubjectOrder()){
				return 1;
			}else if(this.getSubjectOrder() < studentTranscriptPrintTO.getSubjectOrder())
				return -1;
			else
				return 0;
		}
		return 0;
		
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

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public int getSubjectOrder() {
		return subjectOrder;
	}

	public void setSubjectOrder(int subjectOrder) {
		this.subjectOrder = subjectOrder;
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

	public int getAppliedYear() {
		return appliedYear;
	}

	public void setAppliedYear(int appliedYear) {
		this.appliedYear = appliedYear;
	}

	public int getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}

	public String getPassOrFail() {
		return passOrFail;
	}

	public void setPassOrFail(String passOrFail) {
		this.passOrFail = passOrFail;
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

	public Boolean getDontAddInTotal() {
		return dontAddInTotal;
	}

	public void setDontAddInTotal(Boolean dontAddInTotal) {
		this.dontAddInTotal = dontAddInTotal;
	}

	public Boolean getIsCertificateCourse() {
		return isCertificateCourse;
	}

	public void setIsCertificateCourse(Boolean isCertificateCourse) {
		this.isCertificateCourse = isCertificateCourse;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getTermNumber() {
		return termNumber;
	}

	public void setTermNumber(int termNumber) {
		this.termNumber = termNumber;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public Boolean getIsSuppTheoryAppeared() {
		return isSuppTheoryAppeared;
	}

	public void setIsSuppTheoryAppeared(Boolean isSuppTheoryAppeared) {
		this.isSuppTheoryAppeared = isSuppTheoryAppeared;
	}

	public Boolean getIsSuppPracticalAppeared() {
		return isSuppPracticalAppeared;
	}

	public void setIsSuppPracticalAppeared(Boolean isSuppPracticalAppeared) {
		this.isSuppPracticalAppeared = isSuppPracticalAppeared;
	}

	public BigDecimal getTheCiaMaxMarks() {
		return theCiaMaxMarks;
	}

	public void setTheCiaMaxMarks(BigDecimal theCiaMaxMarks) {
		this.theCiaMaxMarks = theCiaMaxMarks;
	}

	public String getTheCiaMarksAwarded() {
		return theCiaMarksAwarded;
	}

	public void setTheCiaMarksAwarded(String theCiaMarksAwarded) {
		this.theCiaMarksAwarded = theCiaMarksAwarded;
	}

	public BigDecimal getAttMaxMarks() {
		return attMaxMarks;
	}

	public void setAttMaxMarks(BigDecimal attMaxMarks) {
		this.attMaxMarks = attMaxMarks;
	}

	public String getTheAttMarksAwarded() {
		return theAttMarksAwarded;
	}

	public void setTheAttMarksAwarded(String theAttMarksAwarded) {
		this.theAttMarksAwarded = theAttMarksAwarded;
	}

	public BigDecimal getTheEndSemMaxMarks() {
		return theEndSemMaxMarks;
	}

	public void setTheEndSemMaxMarks(BigDecimal theEndSemMaxMarks) {
		this.theEndSemMaxMarks = theEndSemMaxMarks;
	}

	public BigDecimal getTheEndSemMinMarks() {
		return theEndSemMinMarks;
	}

	public void setTheEndSemMinMarks(BigDecimal theEndSemMinMarks) {
		this.theEndSemMinMarks = theEndSemMinMarks;
	}

	public String getTheEndSemMarksAwarded() {
		return theEndSemMarksAwarded;
	}

	public void setTheEndSemMarksAwarded(String theEndSemMarksAwarded) {
		this.theEndSemMarksAwarded = theEndSemMarksAwarded;
	}

	public BigDecimal getTheTotMaxMarks() {
		return theTotMaxMarks;
	}

	public void setTheTotMaxMarks(BigDecimal theTotMaxMarks) {
		this.theTotMaxMarks = theTotMaxMarks;
	}

	public double getTheTotMarksAwarded() {
		return theTotMarksAwarded;
	}

	public void setTheTotMarksAwarded(double theTotMarksAwarded) {
		this.theTotMarksAwarded = theTotMarksAwarded;
	}

	public int getTheTotMarksAwardedDisplay() {
		return theTotMarksAwardedDisplay;
	}

	public void setTheTotMarksAwardedDisplay(int theTotMarksAwardedDisplay) {
		this.theTotMarksAwardedDisplay = theTotMarksAwardedDisplay;
	}

	public int getTheCredit() {
		return theCredit;
	}

	public void setTheCredit(int theCredit) {
		this.theCredit = theCredit;
	}

	public String getTotalMarksInWords() {
		return totalMarksInWords;
	}

	public void setTotalMarksInWords(String totalMarksInWords) {
		this.totalMarksInWords = totalMarksInWords;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	
	public String getTotalCreditsAwarded() {
		return totalCreditsAwarded;
	}

	public void setTotalCreditsAwarded(String totalCreditsAwarded) {
		this.totalCreditsAwarded = totalCreditsAwarded;
	}

	public String getTotalGradePoint() {
		return totalGradePoint;
	}

	public void setTotalGradePoint(String totalGradePoint) {
		this.totalGradePoint = totalGradePoint;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

	public int getTotalMaxMarks() {
		return totalMaxMarks;
	}

	public void setTotalMaxMarks(int totalMaxMarks) {
		this.totalMaxMarks = totalMaxMarks;
	}

	public int getTotalMaxMarksAwarded() {
		return totalMaxMarksAwarded;
	}

	public void setTotalMaxMarksAwarded(int totalMaxMarksAwarded) {
		this.totalMaxMarksAwarded = totalMaxMarksAwarded;
	}

	public BigDecimal getPraCiaMaxMarks() {
		return praCiaMaxMarks;
	}

	public void setPraCiaMaxMarks(BigDecimal praCiaMaxMarks) {
		this.praCiaMaxMarks = praCiaMaxMarks;
	}

	public String getPraCiaMarksAwarded() {
		return praCiaMarksAwarded;
	}

	public void setPraCiaMarksAwarded(String praCiaMarksAwarded) {
		this.praCiaMarksAwarded = praCiaMarksAwarded;
	}

	public String getPraAttMarksAwarded() {
		return praAttMarksAwarded;
	}

	public void setPraAttMarksAwarded(String praAttMarksAwarded) {
		this.praAttMarksAwarded = praAttMarksAwarded;
	}

	public BigDecimal getPraEndSemMaxMarks() {
		return praEndSemMaxMarks;
	}

	public void setPraEndSemMaxMarks(BigDecimal praEndSemMaxMarks) {
		this.praEndSemMaxMarks = praEndSemMaxMarks;
	}

	public BigDecimal getPraEndSemMinMarks() {
		return praEndSemMinMarks;
	}

	public void setPraEndSemMinMarks(BigDecimal praEndSemMinMarks) {
		this.praEndSemMinMarks = praEndSemMinMarks;
	}

	public String getPraEndSemMarksAwarded() {
		return praEndSemMarksAwarded;
	}

	public void setPraEndSemMarksAwarded(String praEndSemMarksAwarded) {
		this.praEndSemMarksAwarded = praEndSemMarksAwarded;
	}

	public BigDecimal getPraTotMaxMarks() {
		return praTotMaxMarks;
	}

	public void setPraTotMaxMarks(BigDecimal praTotMaxMarks) {
		this.praTotMaxMarks = praTotMaxMarks;
	}

	public double getPraTotMarksAwarded() {
		return praTotMarksAwarded;
	}

	public void setPraTotMarksAwarded(double praTotMarksAwarded) {
		this.praTotMarksAwarded = praTotMarksAwarded;
	}

	public int getPraTotMarksAwardedDisplay() {
		return praTotMarksAwardedDisplay;
	}

	public void setPraTotMarksAwardedDisplay(int praTotMarksAwardedDisplay) {
		this.praTotMarksAwardedDisplay = praTotMarksAwardedDisplay;
	}

	public int getPraCredit() {
		return praCredit;
	}

	public void setPraCredit(int praCredit) {
		this.praCredit = praCredit;
	}

	public String getMonthYear() {
		return monthYear;
	}

	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}

	public boolean getShowSuppl() {
		return showSuppl;
	}

	public void setShowSuppl(boolean showSuppl) {
		this.showSuppl = showSuppl;
	}

	public boolean isShowSupplDisplay() {
		return showSupplDisplay;
	}

	public void setShowSupplDisplay(boolean showSupplDisplay) {
		this.showSupplDisplay = showSupplDisplay;
	}

	public BigDecimal getGradePoint() {
		return gradePoint;
	}

	public void setGradePoint(BigDecimal gradePoint) {
		this.gradePoint = gradePoint;
	}

}
