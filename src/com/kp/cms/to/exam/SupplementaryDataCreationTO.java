package com.kp.cms.to.exam;

public class SupplementaryDataCreationTO {
	private int id;
	private int studentId;
	private int classId;
	private int subjectId;
	private boolean isTheoryFail = false;
	private boolean isPracticalFail = false;
	private String theoryMarks;
	private String practicalMarks;
	private boolean subjectPart1;
	private boolean subjectPart2;
	private String schemeNo;
	private int chanceCount ;
	private String theoryPracticalMarks;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isTheoryFail() {
		return isTheoryFail;
	}
	public void setTheoryFail(boolean isTheoryFail) {
		this.isTheoryFail = isTheoryFail;
	}
	public boolean isPracticalFail() {
		return isPracticalFail;
	}
	public void setPracticalFail(boolean isPracticalFail) {
		this.isPracticalFail = isPracticalFail;
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
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
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
	public boolean isSubjectPart1() {
		return subjectPart1;
	}
	public void setSubjectPart1(boolean subjectPart1) {
		this.subjectPart1 = subjectPart1;
	}
	public boolean isSubjectPart2() {
		return subjectPart2;
	}
	public void setSubjectPart2(boolean subjectPart2) {
		this.subjectPart2 = subjectPart2;
	}
	public String getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}
	public void setChanceCount(int chanceCount) {
		this.chanceCount = chanceCount;
	}
	public int getChanceCount() {
		return chanceCount;
	}
	public String getTheoryPracticalMarks() {
		return theoryPracticalMarks;
	}
	public void setTheoryPracticalMarks(String theoryPracticalMarks) {
		this.theoryPracticalMarks = theoryPracticalMarks;
	}
}
