package com.kp.cms.to.attendance;

import java.io.Serializable;

public class AttnPucCetMarksTo implements Serializable{
	private int id;
    private String regNo;
    private String classes;
    private String testId;
    private String mrkSub1;
    private String mrkSub2;
    private String mrkSub3;
    private String mrkSub4;
    private String userCode;
    private String pcbRank;
    private String pcmRank;
    private String aieee;
    private String academicYear;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public String getTestId() {
		return testId;
	}
	public void setTestId(String testId) {
		this.testId = testId;
	}
	public String getMrkSub1() {
		return mrkSub1;
	}
	public void setMrkSub1(String mrkSub1) {
		this.mrkSub1 = mrkSub1;
	}
	public String getMrkSub2() {
		return mrkSub2;
	}
	public void setMrkSub2(String mrkSub2) {
		this.mrkSub2 = mrkSub2;
	}
	public String getMrkSub3() {
		return mrkSub3;
	}
	public void setMrkSub3(String mrkSub3) {
		this.mrkSub3 = mrkSub3;
	}
	public String getMrkSub4() {
		return mrkSub4;
	}
	public void setMrkSub4(String mrkSub4) {
		this.mrkSub4 = mrkSub4;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getPcbRank() {
		return pcbRank;
	}
	public void setPcbRank(String pcbRank) {
		this.pcbRank = pcbRank;
	}
	public String getPcmRank() {
		return pcmRank;
	}
	public void setPcmRank(String pcmRank) {
		this.pcmRank = pcmRank;
	}
	public String getAieee() {
		return aieee;
	}
	public void setAieee(String aieee) {
		this.aieee = aieee;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
}
