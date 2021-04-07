package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.List;

public class ShowMarksCardTO implements Serializable {
	
	private int supMCClassId;
	private int supMCsemesterYearNo;
	private int supMCexamID;
	private boolean showSupMC;
	private boolean supMCBlockedStudent;
	private String supMCBlockReason;
	private int cnt;
	private int regMCClassId;
	private int regMCsemesterYearNo;
	private int regMCexamID;
	private boolean showRegMC;
	private boolean regMCBlockedStudent;
	private String regMCBlockReason;
	private int count;
	private List<Integer> classIds;
	private String supHallTicketagreement;
	private String examName;
	private boolean showSupRevalAppln;
	
	public int getSupMCClassId() {
		return supMCClassId;
	}
	public void setSupMCClassId(int supMCClassId) {
		this.supMCClassId = supMCClassId;
	}
	public int getSupMCsemesterYearNo() {
		return supMCsemesterYearNo;
	}
	public void setSupMCsemesterYearNo(int supMCsemesterYearNo) {
		this.supMCsemesterYearNo = supMCsemesterYearNo;
	}
	public int getSupMCexamID() {
		return supMCexamID;
	}
	public void setSupMCexamID(int supMCexamID) {
		this.supMCexamID = supMCexamID;
	}
	public boolean isShowSupMC() {
		return showSupMC;
	}
	public void setShowSupMC(boolean showSupMC) {
		this.showSupMC = showSupMC;
	}
	public String getSupMCBlockReason() {
		return supMCBlockReason;
	}
	public void setSupMCBlockReason(String supMCBlockReason) {
		this.supMCBlockReason = supMCBlockReason;
	}
	public boolean isSupMCBlockedStudent() {
		return supMCBlockedStudent;
	}
	public void setSupMCBlockedStudent(boolean supMCBlockedStudent) {
		this.supMCBlockedStudent = supMCBlockedStudent;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public int getRegMCClassId() {
		return regMCClassId;
	}
	public void setRegMCClassId(int regMCClassId) {
		this.regMCClassId = regMCClassId;
	}
	public int getRegMCsemesterYearNo() {
		return regMCsemesterYearNo;
	}
	public void setRegMCsemesterYearNo(int regMCsemesterYearNo) {
		this.regMCsemesterYearNo = regMCsemesterYearNo;
	}
	public int getRegMCexamID() {
		return regMCexamID;
	}
	public void setRegMCexamID(int regMCexamID) {
		this.regMCexamID = regMCexamID;
	}
	public boolean isShowRegMC() {
		return showRegMC;
	}
	public void setShowRegMC(boolean showRegMC) {
		this.showRegMC = showRegMC;
	}
	public boolean isRegMCBlockedStudent() {
		return regMCBlockedStudent;
	}
	public void setRegMCBlockedStudent(boolean regMCBlockedStudent) {
		this.regMCBlockedStudent = regMCBlockedStudent;
	}
	public String getRegMCBlockReason() {
		return regMCBlockReason;
	}
	public void setRegMCBlockReason(String regMCBlockReason) {
		this.regMCBlockReason = regMCBlockReason;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<Integer> getClassIds() {
		return classIds;
	}
	public void setClassIds(List<Integer> classIds) {
		this.classIds = classIds;
	}
	public String getSupHallTicketagreement() {
		return supHallTicketagreement;
	}
	public void setSupHallTicketagreement(String supHallTicketagreement) {
		this.supHallTicketagreement = supHallTicketagreement;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public boolean getShowSupRevalAppln() {
		return showSupRevalAppln;
	}
	public void setShowSupRevalAppln(boolean showSupRevalAppln) {
		this.showSupRevalAppln = showSupRevalAppln;
	}
}
