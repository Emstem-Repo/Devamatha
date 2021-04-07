package com.kp.cms.to.exam;

import java.io.Serializable;

public class StudentMarksTO implements Serializable,Comparable<StudentMarksTO> {
	
	private int id;
	private String theoryMarks;
	private String practicalMarks;
	private int classId;
	private int marksId;
	private Boolean isTheory;
	private Boolean isPractical;
	private int studentId;
	private String registerNo;
	private String name;
	private String mistake;
	private String retest;
	private Boolean isMarksAbscent;
	private String marksAbscent;
	private int courseId;
	private int year;
	private int schemeNo;
	private Boolean isTheorySecured;
	private Boolean isPracticalSecured;
	private String evaId;
	private String ansId;
	private String dupMistake;
	private String oldTheoryMarks;
	private String oldPracticalMarks;
	private double maxMarks;
	private boolean marksValidationCompleted;
	private String oldMarks;
	private Boolean isGracing;
	private String rollNo;
	private boolean allInternalDateExceeded;
	private Boolean isConversion;
	private String examId;
	private String examName;
	private String attendanceMark;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public int getMarksId() {
		return marksId;
	}
	public void setMarksId(int marksId) {
		this.marksId = marksId;
	}
	public Boolean getIsTheory() {
		return isTheory;
	}
	public void setIsTheory(Boolean isTheory) {
		this.isTheory = isTheory;
	}
	public Boolean getIsPractical() {
		return isPractical;
	}
	public void setIsPractical(Boolean isPractical) {
		this.isPractical = isPractical;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMistake() {
		return mistake;
	}
	public void setMistake(String mistake) {
		this.mistake = mistake;
	}
	public String getRetest() {
		return retest;
	}
	public void setRetest(String retest) {
		this.retest = retest;
	}
	public Boolean getIsMarksAbscent() {
		return isMarksAbscent;
	}
	public void setIsMarksAbscent(Boolean isMarksAbscent) {
		this.isMarksAbscent = isMarksAbscent;
	}
	public String getMarksAbscent() {
		return marksAbscent;
	}
	public void setMarksAbscent(String marksAbscent) {
		this.marksAbscent = marksAbscent;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}
	public Boolean getIsTheorySecured() {
		return isTheorySecured;
	}
	public void setIsTheorySecured(Boolean isTheorySecured) {
		this.isTheorySecured = isTheorySecured;
	}
	public Boolean getIsPracticalSecured() {
		return isPracticalSecured;
	}
	public void setIsPracticalSecured(Boolean isPracticalSecured) {
		this.isPracticalSecured = isPracticalSecured;
	}
	public String getEvaId() {
		return evaId;
	}
	public void setEvaId(String evaId) {
		this.evaId = evaId;
	}
	public String getAnsId() {
		return ansId;
	}
	public void setAnsId(String ansId) {
		this.ansId = ansId;
	}
	public String getDupMistake() {
		return dupMistake;
	}
	public void setDupMistake(String dupMistake) {
		this.dupMistake = dupMistake;
	}
	public String getOldTheoryMarks() {
		return oldTheoryMarks;
	}
	public void setOldTheoryMarks(String oldTheoryMarks) {
		this.oldTheoryMarks = oldTheoryMarks;
	}
	public String getOldPracticalMarks() {
		return oldPracticalMarks;
	}
	public void setOldPracticalMarks(String oldPracticalMarks) {
		this.oldPracticalMarks = oldPracticalMarks;
	}
	
	
	public double getMaxMarks() {
		return maxMarks;
	}
	public void setMaxMarks(double maxMarks) {
		this.maxMarks = maxMarks;
	}
	@Override
	public boolean equals(Object obj) {
	    if(obj instanceof StudentMarksTO)
	    {
	    	StudentMarksTO temp = (StudentMarksTO) obj;
	        if(this.studentId == temp.studentId && this.classId== temp.classId)
	            return true;
	    }
	    return false;
	}
	@Override
	public int hashCode() {
	    return (Integer.valueOf(this.studentId).hashCode() + Integer.valueOf(this.classId).hashCode());        
	}
	public boolean isMarksValidationCompleted() {
		return marksValidationCompleted;
	}
	public void setMarksValidationCompleted(boolean marksValidationCompleted) {
		this.marksValidationCompleted = marksValidationCompleted;
	}
	@Override
	public int compareTo(StudentMarksTO arg0) {
		if(arg0!=null && this!=null && arg0.getRegisterNo()!=null
				 && this.getRegisterNo()!=null){
			return this.getRegisterNo().compareTo(arg0.getRegisterNo());
		}else
		return -1;
	}
	public String getOldMarks() {
		return oldMarks;
	}
	public void setOldMarks(String oldMarks) {
		this.oldMarks = oldMarks;
	}
	public Boolean getIsGracing() {
		return isGracing;
	}
	public void setIsGracing(Boolean isGracing) {
		this.isGracing = isGracing;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public boolean isAllInternalDateExceeded() {
		return allInternalDateExceeded;
	}
	public void setAllInternalDateExceeded(boolean allInternalDateExceeded) {
		this.allInternalDateExceeded = allInternalDateExceeded;
	}
	public Boolean getIsConversion() {
		return isConversion;
	}
	public void setIsConversion(Boolean isConversion) {
		this.isConversion = isConversion;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getAttendanceMark() {
		return attendanceMark;
	}
	public void setAttendanceMark(String attendanceMark) {
		this.attendanceMark = attendanceMark;
	}
	
}