package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class PucttnAttendance implements Serializable {
	private int id;
	private String regNo;
	private String classes;
	private int absSub1;
	private int absSub2;
	private int absSub3;
	private int absSub4;
	private int absSub5;
	private int absSub6;
	private int absSub7;
	private int absSub8;
	private int absSub9;
	private int absSub10;
	private int absLang;
	private int absPra1;
	private int absPra2;
	private int absPra3;
	private int absPra4;
	private String prnRemarks;
	private String splAchvmt;
	private Date lastUpdte;
	private String userCode;
	private int academicYear;
	public PucttnAttendance(){
		
	}
	public PucttnAttendance(int id, String regNo, String classes, int absSub1,
			int absSub2, int absSub3, int absSub4, int absSub5, int absSub6,
			int absSub7, int absSub8, int absSub9, int absSub10, int absLang,
			int absPra1, int absPra2, int absPra3, int absPra4,
			String prnRemarks, String splAchvmt, Date lastUpdte,
			String userCode, int academicYear) {
		super();
		this.id = id;
		this.regNo = regNo;
		this.classes = classes;
		this.absSub1 = absSub1;
		this.absSub2 = absSub2;
		this.absSub3 = absSub3;
		this.absSub4 = absSub4;
		this.absSub5 = absSub5;
		this.absSub6 = absSub6;
		this.absSub7 = absSub7;
		this.absSub8 = absSub8;
		this.absSub9 = absSub9;
		this.absSub10 = absSub10;
		this.absLang = absLang;
		this.absPra1 = absPra1;
		this.absPra2 = absPra2;
		this.absPra3 = absPra3;
		this.absPra4 = absPra4;
		this.prnRemarks = prnRemarks;
		this.splAchvmt = splAchvmt;
		this.lastUpdte = lastUpdte;
		this.userCode = userCode;
		this.academicYear = academicYear;
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
	public int getAbsSub1() {
		return absSub1;
	}
	public void setAbsSub1(int absSub1) {
		this.absSub1 = absSub1;
	}
	public int getAbsSub2() {
		return absSub2;
	}
	public void setAbsSub2(int absSub2) {
		this.absSub2 = absSub2;
	}
	public int getAbsSub3() {
		return absSub3;
	}
	public void setAbsSub3(int absSub3) {
		this.absSub3 = absSub3;
	}
	public int getAbsSub4() {
		return absSub4;
	}
	public void setAbsSub4(int absSub4) {
		this.absSub4 = absSub4;
	}
	public int getAbsSub5() {
		return absSub5;
	}
	public void setAbsSub5(int absSub5) {
		this.absSub5 = absSub5;
	}
	public int getAbsSub6() {
		return absSub6;
	}
	public void setAbsSub6(int absSub6) {
		this.absSub6 = absSub6;
	}
	public int getAbsSub7() {
		return absSub7;
	}
	public void setAbsSub7(int absSub7) {
		this.absSub7 = absSub7;
	}
	public int getAbsSub8() {
		return absSub8;
	}
	public void setAbsSub8(int absSub8) {
		this.absSub8 = absSub8;
	}
	public int getAbsSub9() {
		return absSub9;
	}
	public void setAbsSub9(int absSub9) {
		this.absSub9 = absSub9;
	}
	public int getAbsSub10() {
		return absSub10;
	}
	public void setAbsSub10(int absSub10) {
		this.absSub10 = absSub10;
	}
	public int getAbsLang() {
		return absLang;
	}
	public void setAbsLang(int absLang) {
		this.absLang = absLang;
	}
	public int getAbsPra1() {
		return absPra1;
	}
	public void setAbsPra1(int absPra1) {
		this.absPra1 = absPra1;
	}
	public int getAbsPra2() {
		return absPra2;
	}
	public void setAbsPra2(int absPra2) {
		this.absPra2 = absPra2;
	}
	public int getAbsPra3() {
		return absPra3;
	}
	public void setAbsPra3(int absPra3) {
		this.absPra3 = absPra3;
	}
	public int getAbsPra4() {
		return absPra4;
	}
	public void setAbsPra4(int absPra4) {
		this.absPra4 = absPra4;
	}
	public String getPrnRemarks() {
		return prnRemarks;
	}
	public void setPrnRemarks(String prnRemarks) {
		this.prnRemarks = prnRemarks;
	}
	public String getSplAchvmt() {
		return splAchvmt;
	}
	public void setSplAchvmt(String splAchvmt) {
		this.splAchvmt = splAchvmt;
	}
	public Date getLastUpdte() {
		return lastUpdte;
	}
	public void setLastUpdte(Date lastUpdte) {
		this.lastUpdte = lastUpdte;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public int getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}
	
	
}
