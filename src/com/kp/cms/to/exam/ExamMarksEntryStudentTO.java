package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.Date;

public class ExamMarksEntryStudentTO implements Serializable,Comparable<ExamMarksEntryStudentTO>{

	private Integer marksEntryId;
	private Integer detailId;
	private int studentId;
	private String regNo;
	private String rolNo;
	private String studentName;
	private String marks;
	private String theoryMarks;
	private String practicalMarks;
	private String isTheoryPractical;
	private Date marksCardDate;
	private boolean isTheoryMarksFromDb;
	private boolean isPracticalMarksFromDb;
	
	public ExamMarksEntryStudentTO() {
		super();
	}

	public ExamMarksEntryStudentTO(Integer marksEntryId, Integer detailId,
			int studentId, String studentName, String regNo, String rolNo,
			String marks) {
		super();
		this.marksEntryId = marksEntryId;
		this.detailId = detailId;
		this.studentId = studentId;
		this.studentName = studentName;
		this.regNo = regNo;
		this.rolNo = rolNo;
		this.marks = marks;
	}

	public Integer getMarksEntryId() {
		return marksEntryId;
	}

	public void setMarksEntryId(Integer marksEntryId) {
		this.marksEntryId = marksEntryId;
	}

	public Integer getDetailId() {
		return detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getRolNo() {
		return rolNo;
	}

	public void setRolNo(String rolNo) {
		this.rolNo = rolNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getMarks() {
		return marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
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

	public void setMarksCardDate(Date marksCardDate) {
		this.marksCardDate = marksCardDate;
	}

	public Date getMarksCardDate() {
		return marksCardDate;
	}

	public boolean getIsTheoryMarksFromDb() {
		return isTheoryMarksFromDb;
	}

	public void setIsTheoryMarksFromDb(boolean isTheoryMarksFromDb) {
		this.isTheoryMarksFromDb = isTheoryMarksFromDb;
	}

	public boolean getIsPracticalMarksFromDb() {
		return isPracticalMarksFromDb;
	}

	public void setIsPracticalMarksFromDb(boolean isPracticalMarksFromDb) {
		this.isPracticalMarksFromDb = isPracticalMarksFromDb;
	}

	@Override
	public int compareTo(ExamMarksEntryStudentTO arg0) {
		if(arg0!=null && this!=null && arg0.getRegNo()!=null
				 && this.getRegNo()!=null){
			return this.getRegNo().compareTo(arg0.getRegNo());
		}else
		return 0;
	}


}
