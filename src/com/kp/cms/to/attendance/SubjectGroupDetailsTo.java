package com.kp.cms.to.attendance;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SubjectGroupDetailsTo implements Serializable,Comparable<SubjectGroupDetailsTo> {
	private String registerNo;
	private String studentName;
	private int admAppln;
	private String subjectGroupName;
	private String checked;
	private int appSubId;
	private int subjectGroupId;
	private String tempChecked;
	private String name;
	private String commonSubjectGroup;
	private String isCommSubGrp;
	private String[] applicantSubGrpIds;
	private String secondlanguage;
	private int studentId;
	private String specializationName;
	public int getSubjectGroupId() {
		return subjectGroupId;
	}
	public void setSubjectGroupId(int subjectGroupId) {
		this.subjectGroupId = subjectGroupId;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public void setSubjectGroupName(String subjectGroupName) {
		this.subjectGroupName = subjectGroupName;
	}
	public String getSubjectGroupName() {
		return subjectGroupName;
	}
	public void setAdmAppln(int admAppln) {
		this.admAppln = admAppln;
	}
	public int getAdmAppln() {
		return admAppln;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public int getAppSubId() {
		return appSubId;
	}
	public void setAppSubId(int appSubId) {
		this.appSubId = appSubId;
	}
	public void setTempChecked(String tempChecked) {
		this.tempChecked = tempChecked;
	}
	public String getTempChecked() {
		return tempChecked;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCommonSubjectGroup() {
		return commonSubjectGroup;
	}
	public void setCommonSubjectGroup(String commonSubjectGroup) {
		this.commonSubjectGroup = commonSubjectGroup;
	}
	public void setIsCommSubGrp(String isCommSubGrp) {
		this.isCommSubGrp = isCommSubGrp;
	}
	public String getIsCommSubGrp() {
		return isCommSubGrp;
	}
	public void setApplicantSubGrpIds(String[] applicantSubGrpIds) {
		this.applicantSubGrpIds = applicantSubGrpIds;
	}
	public String[] getApplicantSubGrpIds() {
		return applicantSubGrpIds;
	}
	public void setSecondlanguage(String secondlanguage) {
		this.secondlanguage = secondlanguage;
	}
	public String getSecondlanguage() {
		return secondlanguage;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getStudentId() {
		return studentId;
	}
	@Override
	public int compareTo(SubjectGroupDetailsTo temp) {
		if(temp!=null && this!=null && temp.getRegisterNo()!=null
				 && this.getRegisterNo()!=null){
				return this.getRegisterNo().compareTo(temp.getRegisterNo());
		}else
		return 0;
	}
	public String getSpecializationName() {
		return specializationName;
	}
	public void setSpecializationName(String specializationName) {
		this.specializationName = specializationName;
	}
}
