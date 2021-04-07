package com.kp.cms.to.exam;

/**
 * Mar 1, 2010 Created By 9Elements Team
 */
import java.io.Serializable;

@SuppressWarnings("serial")
public class ExamAssignOverallMarksTO implements Serializable,Comparable<ExamAssignOverallMarksTO> {
	private int id;
	private Integer examId;
	private int courseId;
	private int subjectId;
	private int studentId;
	private int dummyStudentId;
	private int schemeNo;
	private String overallName;
	private Integer overallId;
	private String theoryMarks;
	private String practicalMarks;
	private String rollNo;
	private String regNo;
	private String studentName;
	private String isTheoryPractical;
	private int classId;
	
	public ExamAssignOverallMarksTO() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getOverallName() {
		return overallName;
	}

	public void setOverallName(String overallName) {
		this.overallName = overallName;
	}

	public Integer getOverallId() {
		return overallId;
	}

	public void setOverallId(Integer overallId) {
		this.overallId = overallId;
	}

	

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
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

	public void setTheoryMarks(String theoryMarks) {
		this.theoryMarks = theoryMarks;
	}

	public String getTheoryMarks() {
		return theoryMarks;
	}

	public void setPracticalMarks(String practicalMarks) {
		this.practicalMarks = practicalMarks;
	}

	public String getPracticalMarks() {
		return practicalMarks;
	}

	public void setIsTheoryPractical(String isTheoryPractical) {
		this.isTheoryPractical = isTheoryPractical;
	}

	public String getIsTheoryPractical() {
		return isTheoryPractical;
	}

	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}

	public int getSchemeNo() {
		return schemeNo;
	}

	public void setDummyStudentId(int dummyStudentId) {
		this.dummyStudentId = dummyStudentId;
	}

	public int getDummyStudentId() {
		return dummyStudentId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	@Override
	public int compareTo(ExamAssignOverallMarksTO arg0) {
		if(arg0!=null && this!=null && arg0.getRegNo()!=null 
				 && this.getRegNo()!=null){
			return this.getRegNo().compareTo(arg0.getRegNo());
		}else
			return 0;
	}
	
	
}
