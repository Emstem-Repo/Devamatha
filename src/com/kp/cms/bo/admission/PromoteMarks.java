package com.kp.cms.bo.admission;

import java.io.Serializable;

public class PromoteMarks implements Serializable{
	private int id;
	private String regNo;
	private String classCode;
	private String mrkSub1;
	private String mrkSub2;
	private String mrkSub3;
	private String mrkSub4;
	private String mrkSub5;
	private String mrkSub6;
	private String mrkSub7;
	private String gradeSub1;
	private String gradeSub2;
	private String gradeSub3;
	private String gradeSub4;
	private String gradeSub5;
	private String gradeSub6;
	private String gradeSub7;
	private String section;
	private Boolean withHeld;
	private Boolean supAttend;
	private String academicYear;
	
	public PromoteMarks() {
		
	}
	
	
	public PromoteMarks(int id, String regNo, String classCode, String mrkSub1,
			String mrkSub2, String mrkSub3, String mrkSub4, String mrkSub5,
			String mrkSub6, String mrkSub7, String gradeSub1, String gradeSub2,
			String gradeSub3, String gradeSub4, String gradeSub5,
			String gradeSub6, String gradeSub7, String section,
			Boolean withHeld, Boolean supAttend, String academicYear) {
		super();
		this.id = id;
		this.regNo = regNo;
		this.classCode = classCode;
		this.mrkSub1 = mrkSub1;
		this.mrkSub2 = mrkSub2;
		this.mrkSub3 = mrkSub3;
		this.mrkSub4 = mrkSub4;
		this.mrkSub5 = mrkSub5;
		this.mrkSub6 = mrkSub6;
		this.mrkSub7 = mrkSub7;
		this.gradeSub1 = gradeSub1;
		this.gradeSub2 = gradeSub2;
		this.gradeSub3 = gradeSub3;
		this.gradeSub4 = gradeSub4;
		this.gradeSub5 = gradeSub5;
		this.gradeSub6 = gradeSub6;
		this.gradeSub7 = gradeSub7;
		this.section = section;
		this.withHeld = withHeld;
		this.supAttend = supAttend;
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

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
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

	public String getMrkSub5() {
		return mrkSub5;
	}

	public void setMrkSub5(String mrkSub5) {
		this.mrkSub5 = mrkSub5;
	}

	public String getMrkSub6() {
		return mrkSub6;
	}

	public void setMrkSub6(String mrkSub6) {
		this.mrkSub6 = mrkSub6;
	}

	public String getMrkSub7() {
		return mrkSub7;
	}

	public void setMrkSub7(String mrkSub7) {
		this.mrkSub7 = mrkSub7;
	}

	public String getGradeSub1() {
		return gradeSub1;
	}

	public void setGradeSub1(String gradeSub1) {
		this.gradeSub1 = gradeSub1;
	}

	public String getGradeSub2() {
		return gradeSub2;
	}

	public void setGradeSub2(String gradeSub2) {
		this.gradeSub2 = gradeSub2;
	}

	public String getGradeSub3() {
		return gradeSub3;
	}

	public void setGradeSub3(String gradeSub3) {
		this.gradeSub3 = gradeSub3;
	}

	public String getGradeSub4() {
		return gradeSub4;
	}

	public void setGradeSub4(String gradeSub4) {
		this.gradeSub4 = gradeSub4;
	}

	public String getGradeSub5() {
		return gradeSub5;
	}

	public void setGradeSub5(String gradeSub5) {
		this.gradeSub5 = gradeSub5;
	}

	public String getGradeSub6() {
		return gradeSub6;
	}

	public void setGradeSub6(String gradeSub6) {
		this.gradeSub6 = gradeSub6;
	}

	public String getGradeSub7() {
		return gradeSub7;
	}

	public void setGradeSub7(String gradeSub7) {
		this.gradeSub7 = gradeSub7;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public Boolean getWithHeld() {
		return withHeld;
	}

	public void setWithHeld(Boolean withHeld) {
		this.withHeld = withHeld;
	}

	public Boolean getSupAttend() {
		return supAttend;
	}

	public void setSupAttend(Boolean supAttend) {
		this.supAttend = supAttend;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	
}
