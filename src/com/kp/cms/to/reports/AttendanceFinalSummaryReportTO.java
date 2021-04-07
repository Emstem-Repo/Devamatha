package com.kp.cms.to.reports;

import java.util.Map;

public class AttendanceFinalSummaryReportTO {

	private String registerNo;

	private String rollNo;

	private String studentName;

	private int aboveEightyFive;

	private int aboveEighty;

	private int aboveSeventyFive;

	private int aboveSeventy;

	private int aboveSixtyFive;

	private int aboveSixty;

	private int belowSixty;

	private String remarks;

	private String signature;

	private Map<Integer, Map<Integer, StudentFinalSummaryTO>> studentFinalSummaryMap;

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getAboveEightyFive() {
		return aboveEightyFive;
	}

	public void setAboveEightyFive(int aboveEightyFive) {
		this.aboveEightyFive = aboveEightyFive;
	}

	public int getAboveEighty() {
		return aboveEighty;
	}

	public void setAboveEighty(int aboveEighty) {
		this.aboveEighty = aboveEighty;
	}

	public int getAboveSeventyFive() {
		return aboveSeventyFive;
	}

	public void setAboveSeventyFive(int aboveSeventyFive) {
		this.aboveSeventyFive = aboveSeventyFive;
	}

	public int getAboveSeventy() {
		return aboveSeventy;
	}

	public void setAboveSeventy(int aboveSeventy) {
		this.aboveSeventy = aboveSeventy;
	}

	public int getAboveSixtyFive() {
		return aboveSixtyFive;
	}

	public void setAboveSixtyFive(int aboveSixtyFive) {
		this.aboveSixtyFive = aboveSixtyFive;
	}

	public int getAboveSixty() {
		return aboveSixty;
	}

	public void setAboveSixty(int aboveSixty) {
		this.aboveSixty = aboveSixty;
	}

	public int getBelowSixty() {
		return belowSixty;
	}

	public void setBelowSixty(int belowSixty) {
		this.belowSixty = belowSixty;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Map<Integer, Map<Integer, StudentFinalSummaryTO>> getStudentFinalSummaryMap() {
		return studentFinalSummaryMap;
	}

	public void setStudentFinalSummaryMap(
			Map<Integer, Map<Integer, StudentFinalSummaryTO>> studentFinalSummaryMap) {
		this.studentFinalSummaryMap = studentFinalSummaryMap;
	}

}
