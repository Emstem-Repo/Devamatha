package com.kp.cms.bo.exam;

@SuppressWarnings("serial")
public class ExamStudentAttendanceMarkDetailsBO extends ExamGenBO {

	private Integer studentId;
	private Integer classId;
	private Integer attendanceTypeId;
	private Integer attendanceMinMark;
	private Integer studentAttendanceMark;

	private StudentUtilBO studentUtilBO;
	private ClassUtilBO classUtilBO;

	public ExamStudentAttendanceMarkDetailsBO() {
		super();
	}

	public ExamStudentAttendanceMarkDetailsBO(Integer studentId,
			Integer classId, Integer attendanceTypeId,
			Integer attendanceMinMark, Integer studentAttendanceMark) {
		super();
		this.studentId = studentId;
		this.classId = classId;
		this.attendanceTypeId = attendanceTypeId;
		this.attendanceMinMark = attendanceMinMark;
		this.studentAttendanceMark = studentAttendanceMark;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getAttendanceTypeId() {
		return attendanceTypeId;
	}

	public void setAttendanceTypeId(Integer attendanceTypeId) {
		this.attendanceTypeId = attendanceTypeId;
	}

	public Integer getAttendanceMinMark() {
		return attendanceMinMark;
	}

	public void setAttendanceMinMark(Integer attendanceMinMark) {
		this.attendanceMinMark = attendanceMinMark;
	}

	public Integer getStudentAttendanceMark() {
		return studentAttendanceMark;
	}

	public void setStudentAttendanceMark(Integer studentAttendanceMark) {
		this.studentAttendanceMark = studentAttendanceMark;
	}

	public StudentUtilBO getStudentUtilBO() {
		return studentUtilBO;
	}

	public void setStudentUtilBO(StudentUtilBO studentUtilBO) {
		this.studentUtilBO = studentUtilBO;
	}

	public ClassUtilBO getClassUtilBO() {
		return classUtilBO;
	}

	public void setClassUtilBO(ClassUtilBO classUtilBO) {
		this.classUtilBO = classUtilBO;
	}

}
