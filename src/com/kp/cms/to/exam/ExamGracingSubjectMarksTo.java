package com.kp.cms.to.exam;

import java.math.BigDecimal;
import java.util.Date;

import com.kp.cms.to.admin.StudentTO;

public class ExamGracingSubjectMarksTo implements Comparable<ExamGracingSubjectMarksTo>{

	private int id;
	private Integer gracingProcessId;
	private Integer examId;
	private Integer studentId;
	private Integer classesId;
	private Integer subjectId;
	private Integer courseId;
	private String theoryTotalSubInternalMark;
	private String practicalTotalSubInternalMark;
	private BigDecimal theoryeseMinimumMark;
	private BigDecimal practicaleseMinimumMark;
	private BigDecimal theoryeseMaximumMark;
	private BigDecimal practicaleseMaximumMark;
	private String studentTheoryMark;
	private String studentPracticalMark;
	private String section;
	private int subjectOrder;
	private BigDecimal finalPracticalInternalMaximumMark;
	private BigDecimal finalTheoryInternalMaximumMark;
	private int termNumber;
	private String subType;
	private double theoryObtain;
	private double practicalObtain;
	private BigDecimal theoryMax;
	private BigDecimal practicalMax;
	private int appliedYear;
	private int sectionId;
	private int academicYear;
	private String passOrFail;
	private BigDecimal finalTheoryInternalMinimumMark;
	private BigDecimal finalPracticalInternalMinimumMark;
	private BigDecimal theoryMin;
	private BigDecimal practicalMin;
	private Boolean isTheoryAppeared;
	private Boolean isPracticalAppeared;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	
	private double theoryInternalGrace;
	private double theoryEseGrace;
	private double practicalInternalGrace;
	private double practicalEseGrace;
	private double theoryObtainGraced;
	private double practicalObtainGraced;
	private Boolean isGraced;
	private String noGracingReason;
	private double totalExtObain;
	private double totalExtMax;
	private double totalExtMin;
	private Double sortingDiff;
	
	public double getTotalExtObain() {
		return totalExtObain;
	}
	public void setTotalExtObain(double totalExtObain) {
		this.totalExtObain = totalExtObain;
	}
	public double getTotalExtMax() {
		return totalExtMax;
	}
	public void setTotalExtMax(double totalExtMax) {
		this.totalExtMax = totalExtMax;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getGracingProcessId() {
		return gracingProcessId;
	}
	public void setGracingProcessId(Integer gracingProcessId) {
		this.gracingProcessId = gracingProcessId;
	}
	public Integer getExamId() {
		return examId;
	}
	public void setExamId(Integer examId) {
		this.examId = examId;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public Integer getClassesId() {
		return classesId;
	}
	public void setClassesId(Integer classesId) {
		this.classesId = classesId;
	}
	public Integer getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public String getTheoryTotalSubInternalMark() {
		return theoryTotalSubInternalMark;
	}
	public void setTheoryTotalSubInternalMark(String theoryTotalSubInternalMark) {
		this.theoryTotalSubInternalMark = theoryTotalSubInternalMark;
	}
	public String getPracticalTotalSubInternalMark() {
		return practicalTotalSubInternalMark;
	}
	public void setPracticalTotalSubInternalMark(
			String practicalTotalSubInternalMark) {
		this.practicalTotalSubInternalMark = practicalTotalSubInternalMark;
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
	public int getTermNumber() {
		return termNumber;
	}
	public void setTermNumber(int termNumber) {
		this.termNumber = termNumber;
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public double getTheoryInternalGrace() {
		return theoryInternalGrace;
	}
	public void setTheoryInternalGrace(double theoryInternalGrace) {
		this.theoryInternalGrace = theoryInternalGrace;
	}
	public double getTheoryEseGrace() {
		return theoryEseGrace;
	}
	public void setTheoryEseGrace(double theoryEseGrace) {
		this.theoryEseGrace = theoryEseGrace;
	}
	public double getPracticalInternalGrace() {
		return practicalInternalGrace;
	}
	public void setPracticalInternalGrace(double practicalInternalGrace) {
		this.practicalInternalGrace = practicalInternalGrace;
	}
	public double getPracticalEseGrace() {
		return practicalEseGrace;
	}
	public void setPracticalEseGrace(double practicalEseGrace) {
		this.practicalEseGrace = practicalEseGrace;
	}
	public double getTheoryObtainGraced() {
		return theoryObtainGraced;
	}
	public void setTheoryObtainGraced(double theoryObtainGraced) {
		this.theoryObtainGraced = theoryObtainGraced;
	}
	public double getPracticalObtainGraced() {
		return practicalObtainGraced;
	}
	public void setPracticalObtainGraced(double practicalObtainGraced) {
		this.practicalObtainGraced = practicalObtainGraced;
	}
	public Boolean getIsGraced() {
		return isGraced;
	}
	public void setIsGraced(Boolean isGraced) {
		this.isGraced = isGraced;
	}
	public String getNoGracingReason() {
		return noGracingReason;
	}
	public void setNoGracingReason(String noGracingReason) {
		this.noGracingReason = noGracingReason;
	}
	public void setTotalExtMin(double totalExtMin) {
		this.totalExtMin = totalExtMin;
	}
	public double getTotalExtMin() {
		return totalExtMin;
	}
	@Override
	public int compareTo(ExamGracingSubjectMarksTo arg0) {
		if(arg0 instanceof ExamGracingSubjectMarksTo && arg0.getSortingDiff()!=null ){
			return arg0.getSortingDiff().compareTo(this.getSortingDiff());
	}else
		return 0;
}
	public void setSortingDiff(Double sortingDiff) {
		this.sortingDiff = sortingDiff;
	}
	public Double getSortingDiff() {
		return sortingDiff;
	}

}
