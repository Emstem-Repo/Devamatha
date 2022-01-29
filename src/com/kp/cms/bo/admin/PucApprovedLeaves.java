package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class PucApprovedLeaves implements Serializable{
    private int id;
    private String regNo;
    private String classes;
    private Date leaveStartDate;
    private Date leaveEndDate;
    private Integer leaveSub1;
    private Integer leaveSub2;
    private Integer leaveSub3;
    private Integer leaveSub4;
    private Integer leaveSub5;
    private Integer leaveSub6;
    private Integer leaveSub7;
    private Integer leaveSub8;
    private Integer leaveSub9;
    private Integer leaveSub10;
    private Integer leaveLang;
    private Integer leavePracl;
    private Integer leavePrac2;
    private Integer leavePrac3;
    private Integer leavePrac4;
    private Integer academicYear;
    public PucApprovedLeaves(){
    	
    }
	public PucApprovedLeaves(int id, String regNo, String classes,
			Date leaveStartDate, Date leaveEndDate, Integer leaveSub1,
			Integer leaveSub2, Integer leaveSub3, Integer leaveSub4,
			Integer leaveSub5, Integer leaveSub6, Integer leaveSub7,
			Integer leaveSub8, Integer leaveSub9, Integer leaveSub10,
			Integer leaveLang, Integer leavePracl, Integer leavePrac2,
			Integer leavePrac3, Integer leavePrac4) {
		super();
		this.id = id;
		this.regNo = regNo;
		this.classes = classes;
		this.leaveStartDate = leaveStartDate;
		this.leaveEndDate = leaveEndDate;
		this.leaveSub1 = leaveSub1;
		this.leaveSub2 = leaveSub2;
		this.leaveSub3 = leaveSub3;
		this.leaveSub4 = leaveSub4;
		this.leaveSub5 = leaveSub5;
		this.leaveSub6 = leaveSub6;
		this.leaveSub7 = leaveSub7;
		this.leaveSub8 = leaveSub8;
		this.leaveSub9 = leaveSub9;
		this.leaveSub10 = leaveSub10;
		this.leaveLang = leaveLang;
		this.leavePracl = leavePracl;
		this.leavePrac2 = leavePrac2;
		this.leavePrac3 = leavePrac3;
		this.leavePrac4 = leavePrac4;
	}
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
	public Integer getLeaveSub1() {
		return leaveSub1;
	}
	public void setLeaveSub1(Integer leaveSub1) {
		this.leaveSub1 = leaveSub1;
	}
	public Integer getLeaveSub2() {
		return leaveSub2;
	}
	public void setLeaveSub2(Integer leaveSub2) {
		this.leaveSub2 = leaveSub2;
	}
	public Integer getLeaveSub3() {
		return leaveSub3;
	}
	public void setLeaveSub3(Integer leaveSub3) {
		this.leaveSub3 = leaveSub3;
	}
	public Integer getLeaveSub4() {
		return leaveSub4;
	}
	public void setLeaveSub4(Integer leaveSub4) {
		this.leaveSub4 = leaveSub4;
	}
	public Integer getLeaveSub5() {
		return leaveSub5;
	}
	public void setLeaveSub5(Integer leaveSub5) {
		this.leaveSub5 = leaveSub5;
	}
	public Integer getLeaveSub6() {
		return leaveSub6;
	}
	public void setLeaveSub6(Integer leaveSub6) {
		this.leaveSub6 = leaveSub6;
	}
	public Integer getLeaveSub7() {
		return leaveSub7;
	}
	public void setLeaveSub7(Integer leaveSub7) {
		this.leaveSub7 = leaveSub7;
	}
	public Integer getLeaveSub8() {
		return leaveSub8;
	}
	public void setLeaveSub8(Integer leaveSub8) {
		this.leaveSub8 = leaveSub8;
	}
	public Integer getLeaveSub9() {
		return leaveSub9;
	}
	public void setLeaveSub9(Integer leaveSub9) {
		this.leaveSub9 = leaveSub9;
	}
	public Integer getLeaveSub10() {
		return leaveSub10;
	}
	public void setLeaveSub10(Integer leaveSub10) {
		this.leaveSub10 = leaveSub10;
	}
	public Integer getLeaveLang() {
		return leaveLang;
	}
	public void setLeaveLang(Integer leaveLang) {
		this.leaveLang = leaveLang;
	}
	public Integer getLeavePracl() {
		return leavePracl;
	}
	public void setLeavePracl(Integer leavePracl) {
		this.leavePracl = leavePracl;
	}
	public Integer getLeavePrac2() {
		return leavePrac2;
	}
	public void setLeavePrac2(Integer leavePrac2) {
		this.leavePrac2 = leavePrac2;
	}
	public Integer getLeavePrac3() {
		return leavePrac3;
	}
	public void setLeavePrac3(Integer leavePrac3) {
		this.leavePrac3 = leavePrac3;
	}
	public Integer getLeavePrac4() {
		return leavePrac4;
	}
	public void setLeavePrac4(Integer leavePrac4) {
		this.leavePrac4 = leavePrac4;
	}
	public Date getLeaveStartDate() {
		return leaveStartDate;
	}
	public void setLeaveStartDate(Date leaveStartDate) {
		this.leaveStartDate = leaveStartDate;
	}
	public Date getLeaveEndDate() {
		return leaveEndDate;
	}
	public void setLeaveEndDate(Date leaveEndDate) {
		this.leaveEndDate = leaveEndDate;
	}
	public Integer getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}
    
}
