package com.kp.cms.bo.exam;

import java.math.BigDecimal;
import java.util.Date;

public class ExamInternalCalculationMarksBO extends ExamGenBO{

	private int courseId;
	private BigDecimal startPercentage;
	private BigDecimal endPercentage;
	private BigDecimal marks;
	private ExamCourseUtilBO course;
	private int theory;
	private int practical;
	private String theoryPractical;

	public ExamInternalCalculationMarksBO(int id, int courseId,
			BigDecimal startPercentage, BigDecimal endPercentage,
			BigDecimal marks, int theory, int practical,
			String theoryPractical, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, boolean isActive) {
		super();
		this.id = id;
		this.courseId = courseId;
		this.startPercentage = startPercentage;
		this.endPercentage = endPercentage;
		this.marks = marks;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.isActive = isActive;
		this.theory = theory;
		this.practical = practical;
		this.theoryPractical = theoryPractical;
	}

	public ExamInternalCalculationMarksBO(int id, String modifiedBy,
			Date lastModifiedDate, boolean isActive) {
		super();
		this.id = id;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}
	public ExamInternalCalculationMarksBO() {
		super();
	}
	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public BigDecimal getStartPercentage() {
		return startPercentage;
	}

	public void setStartPercentage(BigDecimal startPercentage) {
		this.startPercentage = startPercentage;
	}

	public BigDecimal getEndPercentage() {
		return endPercentage;
	}

	public void setEndPercentage(BigDecimal endPercentage) {
		this.endPercentage = endPercentage;
	}

	public BigDecimal getMarks() {
		return marks;
	}

	public void setMarks(BigDecimal marks) {
		this.marks = marks;
	}

	public ExamCourseUtilBO getCourse() {
		return course;
	}

	public void setCourse(ExamCourseUtilBO course) {
		this.course = course;
	}

	public int getTheory() {
		return theory;
	}

	public void setTheory(int theory) {
		this.theory = theory;
	}

	public int getPractical() {
		return practical;
	}

	public void setPractical(int practical) {
		this.practical = practical;
	}

	public String getTheoryPractical() {
		return theoryPractical;
	}

	public void setTheoryPractical(String theoryPractical) {
		this.theoryPractical = theoryPractical;
	}

	
}
