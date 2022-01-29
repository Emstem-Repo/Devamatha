package com.kp.cms.to.exam;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExamInternalMarksSupplementaryTO implements Serializable,Comparable<ExamInternalMarksSupplementaryTO> {

	private Integer id;
	private Integer subjectId;
	private Integer studentId;
	private String subjectName;
	private String subjectCode;
	private String theoryMarks;
	private String practicalMarks;
	private String isTheoryPrac;
	private int examId;
	private int classId;
	private String theoryTotalSubInternalMarks;
	private String theoryTotalAttendenceMarks;
	private String theoryTotalAssignmentMarks;
	private String practicalTotalSubInternalMarks;
	private String practicalTotalAttendenceMarks;
	private String practicalTotalAssignmentMarks;
	private String passOrFail;
	private String comments;
	private String originalTotalSubInternalMarks;
	private String originalPracticalSubInternalMarks;
	
	public ExamInternalMarksSupplementaryTO() {
		super();
	}

	public ExamInternalMarksSupplementaryTO(Integer id, Integer subjectId,
			Integer studentId, String subjectName, String subjectCode,
			String theoryMarks, String practicalMarks, String isTheoryPrac) {
		super();
		this.id = id;
		this.subjectId = subjectId;
		this.studentId = studentId;
		this.subjectName = subjectName;
		this.subjectCode = subjectCode;
		this.theoryMarks = theoryMarks;
		this.practicalMarks = practicalMarks;
		this.isTheoryPrac = isTheoryPrac;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getTheoryMarks() {
		return theoryMarks;
	}

	public void setTheoryMarks(String theoryMarks) {
		this.theoryMarks = theoryMarks;
	}

	public String getPracticalMarks() {
		return practicalMarks;
	}

	public void setPracticalMarks(String practicalMarks) {
		this.practicalMarks = practicalMarks;
	}

	public void setIsTheoryPrac(String isTheoryPrac) {
		this.isTheoryPrac = isTheoryPrac;
	}

	public String getIsTheoryPrac() {
		return isTheoryPrac;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getTheoryTotalSubInternalMarks() {
		return theoryTotalSubInternalMarks;
	}

	public void setTheoryTotalSubInternalMarks(String theoryTotalSubInternalMarks) {
		this.theoryTotalSubInternalMarks = theoryTotalSubInternalMarks;
	}

	public String getTheoryTotalAttendenceMarks() {
		return theoryTotalAttendenceMarks;
	}

	public void setTheoryTotalAttendenceMarks(String theoryTotalAttendenceMarks) {
		this.theoryTotalAttendenceMarks = theoryTotalAttendenceMarks;
	}

	public String getTheoryTotalAssignmentMarks() {
		return theoryTotalAssignmentMarks;
	}

	public void setTheoryTotalAssignmentMarks(String theoryTotalAssignmentMarks) {
		this.theoryTotalAssignmentMarks = theoryTotalAssignmentMarks;
	}

	public String getPracticalTotalSubInternalMarks() {
		return practicalTotalSubInternalMarks;
	}

	public void setPracticalTotalSubInternalMarks(
			String practicalTotalSubInternalMarks) {
		this.practicalTotalSubInternalMarks = practicalTotalSubInternalMarks;
	}

	public String getPracticalTotalAttendenceMarks() {
		return practicalTotalAttendenceMarks;
	}

	public void setPracticalTotalAttendenceMarks(
			String practicalTotalAttendenceMarks) {
		this.practicalTotalAttendenceMarks = practicalTotalAttendenceMarks;
	}

	public String getPracticalTotalAssignmentMarks() {
		return practicalTotalAssignmentMarks;
	}

	public void setPracticalTotalAssignmentMarks(
			String practicalTotalAssignmentMarks) {
		this.practicalTotalAssignmentMarks = practicalTotalAssignmentMarks;
	}

	public String getPassOrFail() {
		return passOrFail;
	}

	public void setPassOrFail(String passOrFail) {
		this.passOrFail = passOrFail;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getOriginalTotalSubInternalMarks() {
		return originalTotalSubInternalMarks;
	}

	public void setOriginalTotalSubInternalMarks(
			String originalTotalSubInternalMarks) {
		this.originalTotalSubInternalMarks = originalTotalSubInternalMarks;
	}

	public String getOriginalPracticalSubInternalMarks() {
		return originalPracticalSubInternalMarks;
	}

	public void setOriginalPracticalSubInternalMarks(
			String originalPracticalSubInternalMarks) {
		this.originalPracticalSubInternalMarks = originalPracticalSubInternalMarks;
	}

	@Override
	public int compareTo(ExamInternalMarksSupplementaryTO arg0) {
		if(arg0!=null && this!=null && arg0.getSubjectName()!=null
				 && this.getSubjectName()!=null){
			return this.getSubjectName().compareTo(arg0.getSubjectName());
		}else
		return 0;
	}

}