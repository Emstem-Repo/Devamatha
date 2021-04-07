package com.kp.cms.to.admission;

import java.io.Serializable;

public class AssignSubjectGroupHistoryTO implements Serializable,Comparable<AssignSubjectGroupHistoryTO> {
	private int id;
	private String regNo;
	private int studentId;
	private String studentName;
	private String tempChecked;
	private String checked;
	private int semNo;
	private int stuPreviousSubGrpId;
	private String registerNo;
	private int admAppln;
	private String subjectGroupName;
	private int appSubId;
	private int subjectGroupId;
	private String commonSubjectGroup;
	private String isCommSubGrp;
	private String secondLanguage;
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
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public void setTempChecked(String tempChecked) {
		this.tempChecked = tempChecked;
	}
	public String getTempChecked() {
		return tempChecked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getChecked() {
		return checked;
	}
	public void setSemNo(int semNo) {
		this.semNo = semNo;
	}
	public int getSemNo() {
		return semNo;
	}
	public void setStuPreviousSubGrpId(int stuPreviousSubGrpId) {
		this.stuPreviousSubGrpId = stuPreviousSubGrpId;
	}
	public int getStuPreviousSubGrpId() {
		return stuPreviousSubGrpId;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public int getAdmAppln() {
		return admAppln;
	}
	public void setAdmAppln(int admAppln) {
		this.admAppln = admAppln;
	}
	public String getSubjectGroupName() {
		return subjectGroupName;
	}
	public void setSubjectGroupName(String subjectGroupName) {
		this.subjectGroupName = subjectGroupName;
	}
	public int getAppSubId() {
		return appSubId;
	}
	public void setAppSubId(int appSubId) {
		this.appSubId = appSubId;
	}
	public int getSubjectGroupId() {
		return subjectGroupId;
	}
	public void setSubjectGroupId(int subjectGroupId) {
		this.subjectGroupId = subjectGroupId;
	}
	public String getCommonSubjectGroup() {
		return commonSubjectGroup;
	}
	public void setCommonSubjectGroup(String commonSubjectGroup) {
		this.commonSubjectGroup = commonSubjectGroup;
	}
	public String getIsCommSubGrp() {
		return isCommSubGrp;
	}
	public void setIsCommSubGrp(String isCommSubGrp) {
		this.isCommSubGrp = isCommSubGrp;
	}
	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}
	public String getSecondLanguage() {
		return secondLanguage;
	}
	@Override
	public int compareTo(AssignSubjectGroupHistoryTO arg0) {
		if(arg0!=null && this!=null && arg0.getRegNo()!=null
				 && this.getRegNo()!=null){
				return this.getRegNo().compareTo(arg0.getRegNo());
		}else
		return 0;
	}
	
	
	
}
