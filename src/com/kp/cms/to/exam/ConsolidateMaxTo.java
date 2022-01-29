package com.kp.cms.to.exam;

import java.io.Serializable;

public class ConsolidateMaxTo implements Serializable {
	private String theoryCia;
	private String theoryEse;
	private String theoryAtt;
	private String practicalCia;
	private String practicalEse;
	private String practicalAtt;
	private Boolean isAppearedTheory;
	private Boolean isAppearedPractical;
	private String examId;
	private String passingMonth;
	private String passingYear;
	private Boolean isImprovement;
	private int chanceCount; 
	private String internalMarksBeforeGrace;
	private boolean gracedMark;
	private String theoryMarksBeforeGrace;
	
	public String getTheoryCia() {
		return theoryCia;
	}
	public void setTheoryCia(String theoryCia) {
		this.theoryCia = theoryCia;
	}
	public String getTheoryEse() {
		return theoryEse;
	}
	public void setTheoryEse(String theoryEse) {
		this.theoryEse = theoryEse;
	}
	public String getTheoryAtt() {
		return theoryAtt;
	}
	public void setTheoryAtt(String theoryAtt) {
		this.theoryAtt = theoryAtt;
	}
	public String getPracticalCia() {
		return practicalCia;
	}
	public void setPracticalCia(String practicalCia) {
		this.practicalCia = practicalCia;
	}
	public String getPracticalEse() {
		return practicalEse;
	}
	public void setPracticalEse(String practicalEse) {
		this.practicalEse = practicalEse;
	}
	public String getPracticalAtt() {
		return practicalAtt;
	}
	public void setPracticalAtt(String practicalAtt) {
		this.practicalAtt = practicalAtt;
	}
	public Boolean getIsAppearedTheory() {
		return isAppearedTheory;
	}
	public void setIsAppearedTheory(Boolean isAppearedTheory) {
		this.isAppearedTheory = isAppearedTheory;
	}
	public Boolean getIsAppearedPractical() {
		return isAppearedPractical;
	}
	public void setIsAppearedPractical(Boolean isAppearedPractical) {
		this.isAppearedPractical = isAppearedPractical;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getPassingMonth() {
		return passingMonth;
	}
	public void setPassingMonth(String passingMonth) {
		this.passingMonth = passingMonth;
	}
	public String getPassingYear() {
		return passingYear;
	}
	public void setPassingYear(String passingYear) {
		this.passingYear = passingYear;
	}
	public Boolean getIsImprovement() {
		return isImprovement;
	}
	public void setIsImprovement(Boolean isImprovement) {
		this.isImprovement = isImprovement;
	}
	public int getChanceCount() {
		return chanceCount;
	}
	public void setChanceCount(int chanceCount) {
		this.chanceCount = chanceCount;
	}
	public String getInternalMarksBeforeGrace() {
		return internalMarksBeforeGrace;
	}
	public void setInternalMarksBeforeGrace(String internalMarksBeforeGrace) {
		this.internalMarksBeforeGrace = internalMarksBeforeGrace;
	}
	public boolean getGracedMark() {
		return gracedMark;
	}
	public void setGracedMark(boolean gracedMark) {
		this.gracedMark = gracedMark;
	}
	public String getTheoryMarksBeforeGrace() {
		return theoryMarksBeforeGrace;
	}
	public void setTheoryMarksBeforeGrace(String theoryMarksBeforeGrace) {
		this.theoryMarksBeforeGrace = theoryMarksBeforeGrace;
	}
	
}