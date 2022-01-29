package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.List;

/**
 * Feb 6, 2010 Created By 9Elements Team
 */
@SuppressWarnings("serial")
public class ExamMarksEntryDetailsTO implements Serializable {

	private Integer id;
	private Integer examMasterId;
	private int subjectId;
	private String subjectName;
	private String theoryMarks;
	private String practicalMarks;
	private String isTheoryPractical;
	private String examCode;
	private String marksCardDate;
	private String examName;
	private String theoryMaxMarks;
	private String practicalMaxMarks;
	private String regNo;
	private String studentName;
	private String className;
	List<ExamMarksEntryDetailsTO> tos;
	private int examId;
	
	
	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
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

	public void setExamMasterId(Integer examMasterid) {
		this.examMasterId = examMasterid;
	}

	public Integer getExamMasterId() {
		return examMasterId;
	}

	public void setIsTheoryPractical(String isTheoryPractical) {
		this.isTheoryPractical = isTheoryPractical;
	}

	public String getIsTheoryPractical() {
		return isTheoryPractical;
	}

	public String getExamCode() {
		return examCode;
	}

	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}

	public void setMarksCardDate(String marksCardDate) {
		this.marksCardDate = marksCardDate;
	}

	public String getMarksCardDate() {
		return marksCardDate;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getTheoryMaxMarks() {
		return theoryMaxMarks;
	}

	public void setTheoryMaxMarks(String theoryMaxMarks) {
		this.theoryMaxMarks = theoryMaxMarks;
	}

	public String getPracticalMaxMarks() {
		return practicalMaxMarks;
	}

	public void setPracticalMaxMarks(String practicalMaxMarks) {
		this.practicalMaxMarks = practicalMaxMarks;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public List<ExamMarksEntryDetailsTO> getTos() {
		return tos;
	}

	public void setTos(List<ExamMarksEntryDetailsTO> tos) {
		this.tos = tos;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
}
