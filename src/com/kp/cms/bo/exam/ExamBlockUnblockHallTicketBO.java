package com.kp.cms.bo.exam;

import java.util.Date;

/**
 * Dec 26, 2009 Created By 9Elements Team
 */

public class ExamBlockUnblockHallTicketBO extends ExamGenBO {

	private int examId;
	private int studentId;
	private int classId;
	private String hallTktOrMarksCard;

	private StudentUtilBO studentUtilBO;
	private ClassUtilBO classUtilBO;
	private ExamDefinitionUtilBO examDefinitionUtilBO;
	private String blockReason;

	public ExamBlockUnblockHallTicketBO() {
		super();
	}

	public ExamBlockUnblockHallTicketBO(String classId, String examId,
			String hallTktOrMarksCard, String studentId, String userId, String blockReason) {
		super();
		this.classId = getInt(classId);
		this.examId = getInt(examId);
		this.hallTktOrMarksCard = hallTktOrMarksCard;
		this.studentId = getInt(studentId);
		this.createdBy = userId;
		this.createdDate = new Date();
		this.blockReason = blockReason;
	}

	private int getInt(String st) {
		try {
			return Integer.parseInt(st.trim());
		} catch (NumberFormatException e) {
			return 0;
		}
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

	public ExamDefinitionUtilBO getExamDefinitionUtilBO() {
		return examDefinitionUtilBO;
	}

	public void setExamDefinitionUtilBO(
			ExamDefinitionUtilBO examDefinitionUtilBO) {
		this.examDefinitionUtilBO = examDefinitionUtilBO;
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

	public String getHallTktOrMarksCard() {
		return hallTktOrMarksCard;
	}

	public void setHallTktOrMarksCard(String hallTktOrMarksCard) {
		this.hallTktOrMarksCard = hallTktOrMarksCard;
	}

	public String getBlockReason() {
		return blockReason;
	}

	public void setBlockReason(String blockReason) {
		this.blockReason = blockReason;
	}
	
}
