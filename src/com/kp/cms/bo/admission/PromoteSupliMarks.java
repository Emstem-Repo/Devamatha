package com.kp.cms.bo.admission;

import java.io.Serializable;
import java.math.BigDecimal;


public class PromoteSupliMarks implements Serializable{
    private int id;
    private String regNo;
    private String classCode;
    private BigDecimal markSub1;
    private BigDecimal markSub2;
    private BigDecimal markSub3;
    private BigDecimal markSub4;
    private BigDecimal markSub5;
    private BigDecimal markSub6;
    private BigDecimal markSub7;
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
    private Boolean suplSub1;
    private Boolean suplSub2;
    private Boolean suplSub3;
    private Boolean suplSub4;
    private Boolean suplSub5;
    private Boolean suplSub6;
    private Boolean suplSub7;
    private Integer academicYear;
    public PromoteSupliMarks(){
    	
    }
	public PromoteSupliMarks(int id, String regNo, String classCode,
			BigDecimal markSub1, BigDecimal markSub2, BigDecimal markSub3,
			BigDecimal markSub4, BigDecimal markSub5, BigDecimal markSub6,
			BigDecimal markSub7, String gradeSub1, String gradeSub2,
			String gradeSub3, String gradeSub4, String gradeSub5,
			String gradeSub6, String gradeSub7, String section,
			Boolean withHeld, Boolean supAttend, Boolean suplSub1,
			Boolean suplSub2, Boolean suplSub3, Boolean suplSub4,
			Boolean suplSub5, Boolean suplSub6, Boolean suplSub7,Integer academicYear) {
		super();
		this.id = id;
		this.regNo = regNo;
		this.classCode = classCode;
		this.markSub1 = markSub1;
		this.markSub2 = markSub2;
		this.markSub3 = markSub3;
		this.markSub4 = markSub4;
		this.markSub5 = markSub5;
		this.markSub6 = markSub6;
		this.markSub7 = markSub7;
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
		this.suplSub1 = suplSub1;
		this.suplSub2 = suplSub2;
		this.suplSub3 = suplSub3;
		this.suplSub4 = suplSub4;
		this.suplSub5 = suplSub5;
		this.suplSub6 = suplSub6;
		this.suplSub7 = suplSub7;
		this.academicYear=academicYear;
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
	public BigDecimal getMarkSub1() {
		return markSub1;
	}
	public void setMarkSub1(BigDecimal markSub1) {
		this.markSub1 = markSub1;
	}
	public BigDecimal getMarkSub2() {
		return markSub2;
	}
	public void setMarkSub2(BigDecimal markSub2) {
		this.markSub2 = markSub2;
	}
	public BigDecimal getMarkSub3() {
		return markSub3;
	}
	public void setMarkSub3(BigDecimal markSub3) {
		this.markSub3 = markSub3;
	}
	public BigDecimal getMarkSub4() {
		return markSub4;
	}
	public void setMarkSub4(BigDecimal markSub4) {
		this.markSub4 = markSub4;
	}
	public BigDecimal getMarkSub5() {
		return markSub5;
	}
	public void setMarkSub5(BigDecimal markSub5) {
		this.markSub5 = markSub5;
	}
	public BigDecimal getMarkSub6() {
		return markSub6;
	}
	public void setMarkSub6(BigDecimal markSub6) {
		this.markSub6 = markSub6;
	}
	public BigDecimal getMarkSub7() {
		return markSub7;
	}
	public void setMarkSub7(BigDecimal markSub7) {
		this.markSub7 = markSub7;
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
	public Boolean getSuplSub1() {
		return suplSub1;
	}
	public void setSuplSub1(Boolean suplSub1) {
		this.suplSub1 = suplSub1;
	}
	public Boolean getSuplSub2() {
		return suplSub2;
	}
	public void setSuplSub2(Boolean suplSub2) {
		this.suplSub2 = suplSub2;
	}
	public Boolean getSuplSub3() {
		return suplSub3;
	}
	public void setSuplSub3(Boolean suplSub3) {
		this.suplSub3 = suplSub3;
	}
	public Boolean getSuplSub4() {
		return suplSub4;
	}
	public void setSuplSub4(Boolean suplSub4) {
		this.suplSub4 = suplSub4;
	}
	public Boolean getSuplSub5() {
		return suplSub5;
	}
	public void setSuplSub5(Boolean suplSub5) {
		this.suplSub5 = suplSub5;
	}
	public Boolean getSuplSub6() {
		return suplSub6;
	}
	public void setSuplSub6(Boolean suplSub6) {
		this.suplSub6 = suplSub6;
	}
	public Boolean getSuplSub7() {
		return suplSub7;
	}
	public void setSuplSub7(Boolean suplSub7) {
		this.suplSub7 = suplSub7;
	}
	public Integer getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}
}
