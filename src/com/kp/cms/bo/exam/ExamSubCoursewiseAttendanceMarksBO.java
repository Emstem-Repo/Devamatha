package com.kp.cms.bo.exam;

import java.math.BigDecimal;

@SuppressWarnings("serial")
public class ExamSubCoursewiseAttendanceMarksBO extends ExamGenBO {

	private int subjectId;
	private BigDecimal attendanceMarks;
	private BigDecimal fromPrcntgAttndnc;
	private BigDecimal toPrcntgAttndnc;
	private int courseId;

	private SubjectUtilBO subjectUtilBO;

	public ExamSubCoursewiseAttendanceMarksBO() {
		super();
	}

	public ExamSubCoursewiseAttendanceMarksBO(int subjectId,
			BigDecimal attendanceMarks, BigDecimal fromPrcntgAttndnc,
			BigDecimal toPrcntgAttndnc, int courseId) {
		super();
		this.subjectId = subjectId;
		this.attendanceMarks = attendanceMarks;
		this.fromPrcntgAttndnc = fromPrcntgAttndnc;
		this.toPrcntgAttndnc = toPrcntgAttndnc;
		this.courseId = courseId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public BigDecimal getAttendanceMarks() {
		return attendanceMarks;
	}

	public void setAttendanceMarks(BigDecimal attendanceMarks) {
		this.attendanceMarks = attendanceMarks;
	}

	public BigDecimal getFromPrcntgAttndnc() {
		return fromPrcntgAttndnc;
	}

	public void setFromPrcntgAttndnc(BigDecimal fromPrcntgAttndnc) {
		this.fromPrcntgAttndnc = fromPrcntgAttndnc;
	}

	public BigDecimal getToPrcntgAttndnc() {
		return toPrcntgAttndnc;
	}

	public void setToPrcntgAttndnc(BigDecimal toPrcntgAttndnc) {
		this.toPrcntgAttndnc = toPrcntgAttndnc;
	}

	public SubjectUtilBO getSubjectUtilBO() {
		return subjectUtilBO;
	}

	public void setSubjectUtilBO(SubjectUtilBO subjectUtilBO) {
		this.subjectUtilBO = subjectUtilBO;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getCourseId() {
		return courseId;
	}

}
