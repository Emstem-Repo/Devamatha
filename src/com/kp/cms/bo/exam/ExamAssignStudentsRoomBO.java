package com.kp.cms.bo.exam;

import java.util.Date;
import java.util.Set;

@SuppressWarnings("serial")
public class ExamAssignStudentsRoomBO extends ExamGenBO {

	private int examId;
	private int subjectId;
	private int classId;
	private int roomId;
	private Date dateTime;

	private ExamDefinitionBO examDefinitionBO;
	private SubjectUtilBO subjectUtilBO;
	private ClassUtilBO classUtilBO;
	private ExamRoomMasterBO examRoomMasterBO;

	private Set<ExamAssignStudentsRoomStudentListBO> examAssignStudentsRoomStudentListBOset;

	public ExamAssignStudentsRoomBO() {
		super();
	}

	public ExamAssignStudentsRoomBO(int examId, int subjectId, int classId,
			int roomId, Date dateTime) {
		super();
		this.examId = examId;
		this.subjectId = subjectId;
		this.classId = classId;
		this.roomId = roomId;
		this.dateTime = dateTime;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public ExamDefinitionBO getExamDefinitionBO() {
		return examDefinitionBO;
	}

	public void setExamDefinitionBO(ExamDefinitionBO examDefinitionBO) {
		this.examDefinitionBO = examDefinitionBO;
	}

	public SubjectUtilBO getSubjectUtilBO() {
		return subjectUtilBO;
	}

	public void setSubjectUtilBO(SubjectUtilBO subjectUtilBO) {
		this.subjectUtilBO = subjectUtilBO;
	}

	public ClassUtilBO getClassUtilBO() {
		return classUtilBO;
	}

	public void setClassUtilBO(ClassUtilBO classUtilBO) {
		this.classUtilBO = classUtilBO;
	}

	public ExamRoomMasterBO getExamRoomMasterBO() {
		return examRoomMasterBO;
	}

	public void setExamRoomMasterBO(ExamRoomMasterBO examRoomMasterBO) {
		this.examRoomMasterBO = examRoomMasterBO;
	}

	public void setExamAssignStudentsRoomStudentListBOset(
			Set<ExamAssignStudentsRoomStudentListBO> examAssignStudentsRoomStudentListBOset) {
		this.examAssignStudentsRoomStudentListBOset = examAssignStudentsRoomStudentListBOset;
	}

	public Set<ExamAssignStudentsRoomStudentListBO> getExamAssignStudentsRoomStudentListBOset() {
		return examAssignStudentsRoomStudentListBOset;
	}

}
