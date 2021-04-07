package com.kp.cms.bo.exam;

import java.math.BigDecimal;
import java.util.Date;

public class ExamAttendanceMarksBO extends ExamGenBO {

	private int courseId;
	private BigDecimal fromPercentage;
	private BigDecimal toPercentage;
	private BigDecimal marks;
	private int theory;
	private int practical;
	private String theoryPractical;
	
	private ExamCourseUtilBO course;

	public ExamAttendanceMarksBO() {
		super();
	}

	public ExamAttendanceMarksBO(int id, int courseId,
			BigDecimal fromPercentage, BigDecimal toPercentage,
			BigDecimal marks, int theory, int practical,
			String theoryPractical, String userID) {
		super();
		this.id = id;
		this.courseId = courseId;
		this.fromPercentage = fromPercentage;
		this.toPercentage = toPercentage;
		this.marks = marks;
		this.theory = theory;
		this.practical = practical;
		this.theoryPractical = theoryPractical;
		this.createdBy = userID;
		this.createdDate = new Date();
		this.modifiedBy = userID;
		this.lastModifiedDate = new Date();
		this.isActive = true;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public BigDecimal getFromPercentage() {
		return fromPercentage;
	}

	public void setFromPercentage(BigDecimal fromPercentage) {
		this.fromPercentage = fromPercentage;
	}

	public BigDecimal getToPercentage() {
		return toPercentage;
	}

	public void setToPercentage(BigDecimal toPercentage) {
		this.toPercentage = toPercentage;
	}

	public BigDecimal getMarks() {
		return marks;
	}

	public void setMarks(BigDecimal marks) {
		this.marks = marks;
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

	public ExamCourseUtilBO getCourse() {
		return course;
	}

	public void setCourse(ExamCourseUtilBO course) {
		this.course = course;
	}

	

}
