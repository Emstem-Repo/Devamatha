package com.kp.cms.handlers.admin;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.to.admin.DetailedSubjectsTO;

public class CandidateMarkTO implements Serializable {
	
	private int id;
	private String subject1;
	private String subject2;
	private String subject3;
	private String subject4;
	private String subject5;
	private String subject6;
	private String subject7;
	private String subject8;
	private String subject9;
	private String subject10;
	private String subject11;
	private String subject12;
	private String subject13;
	private String subject14;
	private String subject15;
	private String subject16;
	private String subject17;
	private String subject18;
	private String subject19;
	private String subject20;
	private String subject1TotalMarks;
	private String subject2TotalMarks;
	private String subject3TotalMarks;
	private String subject4TotalMarks;
	private String subject5TotalMarks;
	private String subject6TotalMarks;
	private String subject7TotalMarks;
	private String subject8TotalMarks;
	private String subject9TotalMarks;
	private String subject10TotalMarks;
	private String subject11TotalMarks;
	private String subject12TotalMarks;
	private String subject13TotalMarks;
	private String subject14TotalMarks;
	private String subject15TotalMarks;
	private String subject16TotalMarks;
	private String subject17TotalMarks;
	private String subject18TotalMarks;
	private String subject19TotalMarks;
	private String subject20TotalMarks;
	private String subject1ObtainedMarks;
	private String subject2ObtainedMarks;
	private String subject3ObtainedMarks;
	private String subject4ObtainedMarks;
	private String subject5ObtainedMarks;
	private String subject6ObtainedMarks;
	private String subject7ObtainedMarks;
	private String subject8ObtainedMarks;
	private String subject9ObtainedMarks;
	private String subject10ObtainedMarks;
	private String subject11ObtainedMarks;
	private String subject12ObtainedMarks;
	private String subject13ObtainedMarks;
	private String subject14ObtainedMarks;
	private String subject15ObtainedMarks;
	private String subject16ObtainedMarks;
	private String subject17ObtainedMarks;
	private String subject18ObtainedMarks;
	private String subject19ObtainedMarks;
	private String subject20ObtainedMarks;
	private DetailedSubjectsTO detailedSubjects1;
	private DetailedSubjectsTO detailedSubjects2;
	private DetailedSubjectsTO detailedSubjects3;
	private DetailedSubjectsTO detailedSubjects4;
	private DetailedSubjectsTO detailedSubjects5;
	private DetailedSubjectsTO detailedSubjects6;
	private DetailedSubjectsTO detailedSubjects7;
	private DetailedSubjectsTO detailedSubjects8;
	private DetailedSubjectsTO detailedSubjects9;
	private DetailedSubjectsTO detailedSubjects10;
	private DetailedSubjectsTO detailedSubjects11;
	private DetailedSubjectsTO detailedSubjects12;
	private DetailedSubjectsTO detailedSubjects13;
	private DetailedSubjectsTO detailedSubjects14;
	private DetailedSubjectsTO detailedSubjects15;
	private DetailedSubjectsTO detailedSubjects16;
	private DetailedSubjectsTO detailedSubjects17;
	private DetailedSubjectsTO detailedSubjects18;
	private DetailedSubjectsTO detailedSubjects19;
	private DetailedSubjectsTO detailedSubjects20;
	private String overallTotal;
	private String totalMarks;
	private String totalObtainedMarks;
	private boolean lastExam;
	private boolean semesterMark;
	private boolean populated;
	private String createdBy;
	private Date createdDate;
	
	private String subject1_languagewise_TotalMarks;
	private String subject2_languagewise_TotalMarks;
	private String subject3_languagewise_TotalMarks;
	private String subject4_languagewise_TotalMarks;
	private String subject5_languagewise_TotalMarks;
	private String subject6_languagewise_TotalMarks;
	private String subject7_languagewise_TotalMarks;
	private String subject8_languagewise_TotalMarks;
	private String subject9_languagewise_TotalMarks;
	private String subject10_languagewise_TotalMarks;
	
	private String subject1_languagewise_ObtainedMarks;
	private String subject2_languagewise_ObtainedMarks;
	private String subject3_languagewise_ObtainedMarks;
	private String subject4_languagewise_ObtainedMarks;
	private String subject5_languagewise_ObtainedMarks;
	private String subject6_languagewise_ObtainedMarks;
	private String subject7_languagewise_ObtainedMarks;
	private String subject8_languagewise_ObtainedMarks;
	private String subject9_languagewise_ObtainedMarks;
	private String subject10_languagewise_ObtainedMarks;
	
	private String total_languagewise_Marks;
	private String total_languagewise_ObtainedMarks;
	
	private boolean subject1Mandatory;
	private boolean subject2Mandatory;
	private boolean subject3Mandatory;
	private boolean subject4Mandatory;
	private boolean subject5Mandatory;
	private boolean subject6Mandatory;
	private boolean subject7Mandatory;
	private boolean subject8Mandatory;
	private boolean subject9Mandatory;
	private boolean subject10Mandatory;
	private boolean subject11Mandatory;
	private boolean subject12Mandatory;
	private boolean subject13Mandatory;
	private boolean subject14Mandatory;
	private boolean subject15Mandatory;
	private boolean subject16Mandatory;
	private boolean subject17Mandatory;
	private boolean subject18Mandatory;
	private boolean subject19Mandatory;
	private boolean subject20Mandatory;
	
	private String subject1Credit;
	private String subject2Credit;
	private String subject3Credit;
	private String subject4Credit;
	private String subject5Credit;
	private String subject6Credit;
	private String subject7Credit;
	private String subject8Credit;
	private String subject9Credit;
	private String subject10Credit;
	private String subject11Credit;
	private String subject12Credit;
	private String subject13Credit;
	private String subject14Credit;
	private String subject15Credit;
	private String subject16Credit;
	private String subject17Credit;
	private String subject18Credit;
	private String subject19Credit;
	private String subject20Credit;
	
	//new implement
	private String subjectid1;
	private String subjectid2;
	private String subjectid3;
	private String subjectid4;
	private String subjectid5;
	private String subjectid6;
	private String subjectid7;
	private String subjectid8;
	private String subjectid9;
	private String subjectid10;
	private String subjectid11;
	private String subjectid12;
	private String subjectid13;
	private String subjectid14;
	private String subjectid15;
	private String subjectid16;
	private String subjectid17;
	private String subjectid18;
	private String subjectid19;
	private String subjectid20;
	
	private String subjectmarkid1;
	private String subjectmarkid2;
	private String subjectmarkid3;
	private String subjectmarkid4;
	private String subjectmarkid5;
	private String subjectmarkid6;
	private String subjectmarkid7;
	private String subjectmarkid8;
	private String subjectmarkid9;
	private String subjectmarkid10;
	private String subjectmarkid11;
	private String subjectmarkid12;
	private String subjectmarkid13;
	private String subjectmarkid14;
	private String subjectmarkid15;
	private String subjectmarkid16;
	private String subjectmarkid17;
	private String subjectmarkid18;
	private String subjectmarkid19;
	private String subjectmarkid20;

	private String subjectOther1;
	private String subjectOther2;
	private String subjectOther3;
	private String subjectOther4;
	private String subjectOther5;
	private String subjectOther6;
	private String subjectOther7;
	private String subjectOther8;
	private String subjectOther9;
	private String subjectOther10;
	private String subjectOther11;
	private String subjectOther12;
	private String subjectOther13;
	private String subjectOther14;
	private String subjectOther15;
	private String subjectOther16;
	private String subjectOther17;
	private String subjectOther18;
	private String subjectOther19;
	private String subjectOther20;
	
	private String totalMarksCGPA;
	private String totalObtainedMarksCGPA;
	
	public CandidateMarkTO() {
		this.detailedSubjects1 = new DetailedSubjectsTO();
		this.detailedSubjects2 = new DetailedSubjectsTO();
		this.detailedSubjects3 = new DetailedSubjectsTO();
		this.detailedSubjects4 = new DetailedSubjectsTO();
		this.detailedSubjects5 = new DetailedSubjectsTO();
		this.detailedSubjects6 = new DetailedSubjectsTO();
		this.detailedSubjects7 = new DetailedSubjectsTO();
		this.detailedSubjects8 = new DetailedSubjectsTO();
		this.detailedSubjects9 = new DetailedSubjectsTO();
		this.detailedSubjects10 = new DetailedSubjectsTO();
		this.detailedSubjects11 = new DetailedSubjectsTO();
		this.detailedSubjects12 = new DetailedSubjectsTO();
		this.detailedSubjects13 = new DetailedSubjectsTO();
		this.detailedSubjects14 = new DetailedSubjectsTO();
		this.detailedSubjects15 = new DetailedSubjectsTO();
		this.detailedSubjects16 = new DetailedSubjectsTO();
		this.detailedSubjects17 = new DetailedSubjectsTO();
		this.detailedSubjects18 = new DetailedSubjectsTO();
		this.detailedSubjects19 = new DetailedSubjectsTO();
		this.detailedSubjects20 = new DetailedSubjectsTO();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubject1() {
		return subject1;
	}
	public void setSubject1(String subject1) {
		this.subject1 = subject1;
	}
	public String getSubject2() {
		return subject2;
	}
	public void setSubject2(String subject2) {
		this.subject2 = subject2;
	}
	public String getSubject3() {
		return subject3;
	}
	public void setSubject3(String subject3) {
		this.subject3 = subject3;
	}
	public String getSubject4() {
		return subject4;
	}
	public void setSubject4(String subject4) {
		this.subject4 = subject4;
	}
	public String getSubject5() {
		return subject5;
	}
	public void setSubject5(String subject5) {
		this.subject5 = subject5;
	}
	public String getSubject6() {
		return subject6;
	}
	public void setSubject6(String subject6) {
		this.subject6 = subject6;
	}
	public String getSubject7() {
		return subject7;
	}
	public void setSubject7(String subject7) {
		this.subject7 = subject7;
	}
	public String getSubject8() {
		return subject8;
	}
	public void setSubject8(String subject8) {
		this.subject8 = subject8;
	}
	public String getSubject9() {
		return subject9;
	}
	public void setSubject9(String subject9) {
		this.subject9 = subject9;
	}
	public String getSubject10() {
		return subject10;
	}
	public void setSubject10(String subject10) {
		this.subject10 = subject10;
	}
	public String getSubject11() {
		return subject11;
	}
	public void setSubject11(String subject11) {
		this.subject11 = subject11;
	}
	public String getSubject12() {
		return subject12;
	}
	public void setSubject12(String subject12) {
		this.subject12 = subject12;
	}
	public String getSubject13() {
		return subject13;
	}
	public void setSubject13(String subject13) {
		this.subject13 = subject13;
	}
	public String getSubject14() {
		return subject14;
	}
	public void setSubject14(String subject14) {
		this.subject14 = subject14;
	}
	public String getSubject15() {
		return subject15;
	}
	public void setSubject15(String subject15) {
		this.subject15 = subject15;
	}
	public String getSubject16() {
		return subject16;
	}
	public void setSubject16(String subject16) {
		this.subject16 = subject16;
	}
	public String getSubject17() {
		return subject17;
	}
	public void setSubject17(String subject17) {
		this.subject17 = subject17;
	}
	public String getSubject18() {
		return subject18;
	}
	public void setSubject18(String subject18) {
		this.subject18 = subject18;
	}
	public String getSubject19() {
		return subject19;
	}
	public void setSubject19(String subject19) {
		this.subject19 = subject19;
	}
	public String getSubject20() {
		return subject20;
	}
	public void setSubject20(String subject20) {
		this.subject20 = subject20;
	}
	public String getSubject1TotalMarks() {
		return subject1TotalMarks;
	}
	public void setSubject1TotalMarks(String subject1TotalMarks) {
		this.subject1TotalMarks = subject1TotalMarks;
	}
	public String getSubject2TotalMarks() {
		return subject2TotalMarks;
	}
	public void setSubject2TotalMarks(String subject2TotalMarks) {
		this.subject2TotalMarks = subject2TotalMarks;
	}
	public String getSubject3TotalMarks() {
		return subject3TotalMarks;
	}
	public void setSubject3TotalMarks(String subject3TotalMarks) {
		this.subject3TotalMarks = subject3TotalMarks;
	}
	public String getSubject4TotalMarks() {
		return subject4TotalMarks;
	}
	public void setSubject4TotalMarks(String subject4TotalMarks) {
		this.subject4TotalMarks = subject4TotalMarks;
	}
	public String getSubject5TotalMarks() {
		return subject5TotalMarks;
	}
	public void setSubject5TotalMarks(String subject5TotalMarks) {
		this.subject5TotalMarks = subject5TotalMarks;
	}
	public String getSubject6TotalMarks() {
		return subject6TotalMarks;
	}
	public void setSubject6TotalMarks(String subject6TotalMarks) {
		this.subject6TotalMarks = subject6TotalMarks;
	}
	public String getSubject7TotalMarks() {
		return subject7TotalMarks;
	}
	public void setSubject7TotalMarks(String subject7TotalMarks) {
		this.subject7TotalMarks = subject7TotalMarks;
	}
	public String getSubject8TotalMarks() {
		return subject8TotalMarks;
	}
	public void setSubject8TotalMarks(String subject8TotalMarks) {
		this.subject8TotalMarks = subject8TotalMarks;
	}
	public String getSubject9TotalMarks() {
		return subject9TotalMarks;
	}
	public void setSubject9TotalMarks(String subject9TotalMarks) {
		this.subject9TotalMarks = subject9TotalMarks;
	}
	public String getSubject10TotalMarks() {
		return subject10TotalMarks;
	}
	public void setSubject10TotalMarks(String subject10TotalMarks) {
		this.subject10TotalMarks = subject10TotalMarks;
	}
	public String getSubject11TotalMarks() {
		return subject11TotalMarks;
	}
	public void setSubject11TotalMarks(String subject11TotalMarks) {
		this.subject11TotalMarks = subject11TotalMarks;
	}
	public String getSubject12TotalMarks() {
		return subject12TotalMarks;
	}
	public void setSubject12TotalMarks(String subject12TotalMarks) {
		this.subject12TotalMarks = subject12TotalMarks;
	}
	public String getSubject13TotalMarks() {
		return subject13TotalMarks;
	}
	public void setSubject13TotalMarks(String subject13TotalMarks) {
		this.subject13TotalMarks = subject13TotalMarks;
	}
	public String getSubject14TotalMarks() {
		return subject14TotalMarks;
	}
	public void setSubject14TotalMarks(String subject14TotalMarks) {
		this.subject14TotalMarks = subject14TotalMarks;
	}
	public String getSubject15TotalMarks() {
		return subject15TotalMarks;
	}
	public void setSubject15TotalMarks(String subject15TotalMarks) {
		this.subject15TotalMarks = subject15TotalMarks;
	}
	public String getSubject16TotalMarks() {
		return subject16TotalMarks;
	}
	public void setSubject16TotalMarks(String subject16TotalMarks) {
		this.subject16TotalMarks = subject16TotalMarks;
	}
	public String getSubject17TotalMarks() {
		return subject17TotalMarks;
	}
	public void setSubject17TotalMarks(String subject17TotalMarks) {
		this.subject17TotalMarks = subject17TotalMarks;
	}
	public String getSubject18TotalMarks() {
		return subject18TotalMarks;
	}
	public void setSubject18TotalMarks(String subject18TotalMarks) {
		this.subject18TotalMarks = subject18TotalMarks;
	}
	public String getSubject19TotalMarks() {
		return subject19TotalMarks;
	}
	public void setSubject19TotalMarks(String subject19TotalMarks) {
		this.subject19TotalMarks = subject19TotalMarks;
	}
	public String getSubject20TotalMarks() {
		return subject20TotalMarks;
	}
	public void setSubject20TotalMarks(String subject20TotalMarks) {
		this.subject20TotalMarks = subject20TotalMarks;
	}
	public String getSubject1ObtainedMarks() {
		return subject1ObtainedMarks;
	}
	public void setSubject1ObtainedMarks(String subject1ObtainedMarks) {
		this.subject1ObtainedMarks = subject1ObtainedMarks;
	}
	public String getSubject2ObtainedMarks() {
		return subject2ObtainedMarks;
	}
	public void setSubject2ObtainedMarks(String subject2ObtainedMarks) {
		this.subject2ObtainedMarks = subject2ObtainedMarks;
	}
	public String getSubject3ObtainedMarks() {
		return subject3ObtainedMarks;
	}
	public void setSubject3ObtainedMarks(String subject3ObtainedMarks) {
		this.subject3ObtainedMarks = subject3ObtainedMarks;
	}
	public String getSubject4ObtainedMarks() {
		return subject4ObtainedMarks;
	}
	public void setSubject4ObtainedMarks(String subject4ObtainedMarks) {
		this.subject4ObtainedMarks = subject4ObtainedMarks;
	}
	public String getSubject5ObtainedMarks() {
		return subject5ObtainedMarks;
	}
	public void setSubject5ObtainedMarks(String subject5ObtainedMarks) {
		this.subject5ObtainedMarks = subject5ObtainedMarks;
	}
	public String getSubject6ObtainedMarks() {
		return subject6ObtainedMarks;
	}
	public void setSubject6ObtainedMarks(String subject6ObtainedMarks) {
		this.subject6ObtainedMarks = subject6ObtainedMarks;
	}
	public String getSubject7ObtainedMarks() {
		return subject7ObtainedMarks;
	}
	public void setSubject7ObtainedMarks(String subject7ObtainedMarks) {
		this.subject7ObtainedMarks = subject7ObtainedMarks;
	}
	public String getSubject8ObtainedMarks() {
		return subject8ObtainedMarks;
	}
	public void setSubject8ObtainedMarks(String subject8ObtainedMarks) {
		this.subject8ObtainedMarks = subject8ObtainedMarks;
	}
	public String getSubject9ObtainedMarks() {
		return subject9ObtainedMarks;
	}
	public void setSubject9ObtainedMarks(String subject9ObtainedMarks) {
		this.subject9ObtainedMarks = subject9ObtainedMarks;
	}
	public String getSubject10ObtainedMarks() {
		return subject10ObtainedMarks;
	}
	public void setSubject10ObtainedMarks(String subject10ObtainedMarks) {
		this.subject10ObtainedMarks = subject10ObtainedMarks;
	}
	public String getSubject11ObtainedMarks() {
		return subject11ObtainedMarks;
	}
	public void setSubject11ObtainedMarks(String subject11ObtainedMarks) {
		this.subject11ObtainedMarks = subject11ObtainedMarks;
	}
	public String getSubject12ObtainedMarks() {
		return subject12ObtainedMarks;
	}
	public void setSubject12ObtainedMarks(String subject12ObtainedMarks) {
		this.subject12ObtainedMarks = subject12ObtainedMarks;
	}
	public String getSubject13ObtainedMarks() {
		return subject13ObtainedMarks;
	}
	public void setSubject13ObtainedMarks(String subject13ObtainedMarks) {
		this.subject13ObtainedMarks = subject13ObtainedMarks;
	}
	public String getSubject14ObtainedMarks() {
		return subject14ObtainedMarks;
	}
	public void setSubject14ObtainedMarks(String subject14ObtainedMarks) {
		this.subject14ObtainedMarks = subject14ObtainedMarks;
	}
	public String getSubject15ObtainedMarks() {
		return subject15ObtainedMarks;
	}
	public void setSubject15ObtainedMarks(String subject15ObtainedMarks) {
		this.subject15ObtainedMarks = subject15ObtainedMarks;
	}
	public String getSubject16ObtainedMarks() {
		return subject16ObtainedMarks;
	}
	public void setSubject16ObtainedMarks(String subject16ObtainedMarks) {
		this.subject16ObtainedMarks = subject16ObtainedMarks;
	}
	public String getSubject17ObtainedMarks() {
		return subject17ObtainedMarks;
	}
	public void setSubject17ObtainedMarks(String subject17ObtainedMarks) {
		this.subject17ObtainedMarks = subject17ObtainedMarks;
	}
	public String getSubject18ObtainedMarks() {
		return subject18ObtainedMarks;
	}
	public void setSubject18ObtainedMarks(String subject18ObtainedMarks) {
		this.subject18ObtainedMarks = subject18ObtainedMarks;
	}
	public String getSubject19ObtainedMarks() {
		return subject19ObtainedMarks;
	}
	public void setSubject19ObtainedMarks(String subject19ObtainedMarks) {
		this.subject19ObtainedMarks = subject19ObtainedMarks;
	}
	public String getSubject20ObtainedMarks() {
		return subject20ObtainedMarks;
	}
	public void setSubject20ObtainedMarks(String subject20ObtainedMarks) {
		this.subject20ObtainedMarks = subject20ObtainedMarks;
	}
	public String getOverallTotal() {
		return overallTotal;
	}
	public void setOverallTotal(String overallTotal) {
		this.overallTotal = overallTotal;
	}
	public String getTotalMarks() {
		return totalMarks;
	}
	public void setTotalMarks(String totalMarks) {
		this.totalMarks = totalMarks;
	}
	public String getTotalObtainedMarks() {
		return totalObtainedMarks;
	}
	public void setTotalObtainedMarks(String totalObtainedMarks) {
		this.totalObtainedMarks = totalObtainedMarks;
	}
	public boolean isLastExam() {
		return lastExam;
	}
	public void setLastExam(boolean lastExam) {
		this.lastExam = lastExam;
	}
	public boolean isSemesterMark() {
		return semesterMark;
	}
	public void setSemesterMark(boolean semesterMark) {
		this.semesterMark = semesterMark;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the detailedSubjects1
	 */
	public DetailedSubjectsTO getDetailedSubjects1() {
		return detailedSubjects1;
	}
	/**
	 * @param detailedSubjects1 the detailedSubjects1 to set
	 */
	public void setDetailedSubjects1(DetailedSubjectsTO detailedSubjects1) {
		this.detailedSubjects1 = detailedSubjects1;
	}
	/**
	 * @return the detailedSubjects2
	 */
	public DetailedSubjectsTO getDetailedSubjects2() {
		return detailedSubjects2;
	}
	/**
	 * @param detailedSubjects2 the detailedSubjects2 to set
	 */
	public void setDetailedSubjects2(DetailedSubjectsTO detailedSubjects2) {
		this.detailedSubjects2 = detailedSubjects2;
	}
	/**
	 * @return the detailedSubjects3
	 */
	public DetailedSubjectsTO getDetailedSubjects3() {
		return detailedSubjects3;
	}
	/**
	 * @param detailedSubjects3 the detailedSubjects3 to set
	 */
	public void setDetailedSubjects3(DetailedSubjectsTO detailedSubjects3) {
		this.detailedSubjects3 = detailedSubjects3;
	}
	/**
	 * @return the detailedSubjects4
	 */
	public DetailedSubjectsTO getDetailedSubjects4() {
		return detailedSubjects4;
	}
	/**
	 * @param detailedSubjects4 the detailedSubjects4 to set
	 */
	public void setDetailedSubjects4(DetailedSubjectsTO detailedSubjects4) {
		this.detailedSubjects4 = detailedSubjects4;
	}
	/**
	 * @return the detailedSubjects5
	 */
	public DetailedSubjectsTO getDetailedSubjects5() {
		return detailedSubjects5;
	}
	/**
	 * @param detailedSubjects5 the detailedSubjects5 to set
	 */
	public void setDetailedSubjects5(DetailedSubjectsTO detailedSubjects5) {
		this.detailedSubjects5 = detailedSubjects5;
	}
	/**
	 * @return the detailedSubjects6
	 */
	public DetailedSubjectsTO getDetailedSubjects6() {
		return detailedSubjects6;
	}
	/**
	 * @param detailedSubjects6 the detailedSubjects6 to set
	 */
	public void setDetailedSubjects6(DetailedSubjectsTO detailedSubjects6) {
		this.detailedSubjects6 = detailedSubjects6;
	}
	/**
	 * @return the detailedSubjects7
	 */
	public DetailedSubjectsTO getDetailedSubjects7() {
		return detailedSubjects7;
	}
	/**
	 * @param detailedSubjects7 the detailedSubjects7 to set
	 */
	public void setDetailedSubjects7(DetailedSubjectsTO detailedSubjects7) {
		this.detailedSubjects7 = detailedSubjects7;
	}
	/**
	 * @return the detailedSubjects8
	 */
	public DetailedSubjectsTO getDetailedSubjects8() {
		return detailedSubjects8;
	}
	/**
	 * @param detailedSubjects8 the detailedSubjects8 to set
	 */
	public void setDetailedSubjects8(DetailedSubjectsTO detailedSubjects8) {
		this.detailedSubjects8 = detailedSubjects8;
	}
	/**
	 * @return the detailedSubjects9
	 */
	public DetailedSubjectsTO getDetailedSubjects9() {
		return detailedSubjects9;
	}
	/**
	 * @param detailedSubjects9 the detailedSubjects9 to set
	 */
	public void setDetailedSubjects9(DetailedSubjectsTO detailedSubjects9) {
		this.detailedSubjects9 = detailedSubjects9;
	}
	/**
	 * @return the detailedSubjects10
	 */
	public DetailedSubjectsTO getDetailedSubjects10() {
		return detailedSubjects10;
	}
	/**
	 * @param detailedSubjects10 the detailedSubjects10 to set
	 */
	public void setDetailedSubjects10(DetailedSubjectsTO detailedSubjects10) {
		this.detailedSubjects10 = detailedSubjects10;
	}
	/**
	 * @return the detailedSubjects11
	 */
	public DetailedSubjectsTO getDetailedSubjects11() {
		return detailedSubjects11;
	}
	/**
	 * @param detailedSubjects11 the detailedSubjects11 to set
	 */
	public void setDetailedSubjects11(DetailedSubjectsTO detailedSubjects11) {
		this.detailedSubjects11 = detailedSubjects11;
	}
	/**
	 * @return the detailedSubjects12
	 */
	public DetailedSubjectsTO getDetailedSubjects12() {
		return detailedSubjects12;
	}
	/**
	 * @param detailedSubjects12 the detailedSubjects12 to set
	 */
	public void setDetailedSubjects12(DetailedSubjectsTO detailedSubjects12) {
		this.detailedSubjects12 = detailedSubjects12;
	}
	/**
	 * @return the detailedSubjects13
	 */
	public DetailedSubjectsTO getDetailedSubjects13() {
		return detailedSubjects13;
	}
	/**
	 * @param detailedSubjects13 the detailedSubjects13 to set
	 */
	public void setDetailedSubjects13(DetailedSubjectsTO detailedSubjects13) {
		this.detailedSubjects13 = detailedSubjects13;
	}
	/**
	 * @return the detailedSubjects14
	 */
	public DetailedSubjectsTO getDetailedSubjects14() {
		return detailedSubjects14;
	}
	/**
	 * @param detailedSubjects14 the detailedSubjects14 to set
	 */
	public void setDetailedSubjects14(DetailedSubjectsTO detailedSubjects14) {
		this.detailedSubjects14 = detailedSubjects14;
	}
	/**
	 * @return the detailedSubjects15
	 */
	public DetailedSubjectsTO getDetailedSubjects15() {
		return detailedSubjects15;
	}
	/**
	 * @param detailedSubjects15 the detailedSubjects15 to set
	 */
	public void setDetailedSubjects15(DetailedSubjectsTO detailedSubjects15) {
		this.detailedSubjects15 = detailedSubjects15;
	}
	/**
	 * @return the detailedSubjects16
	 */
	public DetailedSubjectsTO getDetailedSubjects16() {
		return detailedSubjects16;
	}
	/**
	 * @param detailedSubjects16 the detailedSubjects16 to set
	 */
	public void setDetailedSubjects16(DetailedSubjectsTO detailedSubjects16) {
		this.detailedSubjects16 = detailedSubjects16;
	}
	/**
	 * @return the detailedSubjects17
	 */
	public DetailedSubjectsTO getDetailedSubjects17() {
		return detailedSubjects17;
	}
	/**
	 * @param detailedSubjects17 the detailedSubjects17 to set
	 */
	public void setDetailedSubjects17(DetailedSubjectsTO detailedSubjects17) {
		this.detailedSubjects17 = detailedSubjects17;
	}
	/**
	 * @return the detailedSubjects18
	 */
	public DetailedSubjectsTO getDetailedSubjects18() {
		return detailedSubjects18;
	}
	/**
	 * @param detailedSubjects18 the detailedSubjects18 to set
	 */
	public void setDetailedSubjects18(DetailedSubjectsTO detailedSubjects18) {
		this.detailedSubjects18 = detailedSubjects18;
	}
	/**
	 * @return the detailedSubjects19
	 */
	public DetailedSubjectsTO getDetailedSubjects19() {
		return detailedSubjects19;
	}
	/**
	 * @param detailedSubjects19 the detailedSubjects19 to set
	 */
	public void setDetailedSubjects19(DetailedSubjectsTO detailedSubjects19) {
		this.detailedSubjects19 = detailedSubjects19;
	}
	/**
	 * @return the detailedSubjects20
	 */
	public DetailedSubjectsTO getDetailedSubjects20() {
		return detailedSubjects20;
	}
	/**
	 * @param detailedSubjects20 the detailedSubjects20 to set
	 */
	public void setDetailedSubjects20(DetailedSubjectsTO detailedSubjects20) {
		this.detailedSubjects20 = detailedSubjects20;
	}

	public boolean isPopulated() {
		return populated;
	}

	public void setPopulated(boolean populated) {
		this.populated = populated;
	}

	public String getSubject1_languagewise_TotalMarks() {
		return subject1_languagewise_TotalMarks;
	}

	public void setSubject1_languagewise_TotalMarks(
			String subject1_languagewise_TotalMarks) {
		this.subject1_languagewise_TotalMarks = subject1_languagewise_TotalMarks;
	}

	public String getSubject2_languagewise_TotalMarks() {
		return subject2_languagewise_TotalMarks;
	}

	public void setSubject2_languagewise_TotalMarks(
			String subject2_languagewise_TotalMarks) {
		this.subject2_languagewise_TotalMarks = subject2_languagewise_TotalMarks;
	}

	public String getSubject3_languagewise_TotalMarks() {
		return subject3_languagewise_TotalMarks;
	}

	public void setSubject3_languagewise_TotalMarks(
			String subject3_languagewise_TotalMarks) {
		this.subject3_languagewise_TotalMarks = subject3_languagewise_TotalMarks;
	}

	public String getSubject4_languagewise_TotalMarks() {
		return subject4_languagewise_TotalMarks;
	}

	public void setSubject4_languagewise_TotalMarks(
			String subject4_languagewise_TotalMarks) {
		this.subject4_languagewise_TotalMarks = subject4_languagewise_TotalMarks;
	}

	public String getSubject5_languagewise_TotalMarks() {
		return subject5_languagewise_TotalMarks;
	}

	public void setSubject5_languagewise_TotalMarks(
			String subject5_languagewise_TotalMarks) {
		this.subject5_languagewise_TotalMarks = subject5_languagewise_TotalMarks;
	}

	public String getSubject6_languagewise_TotalMarks() {
		return subject6_languagewise_TotalMarks;
	}

	public void setSubject6_languagewise_TotalMarks(
			String subject6_languagewise_TotalMarks) {
		this.subject6_languagewise_TotalMarks = subject6_languagewise_TotalMarks;
	}

	public String getSubject7_languagewise_TotalMarks() {
		return subject7_languagewise_TotalMarks;
	}

	public void setSubject7_languagewise_TotalMarks(
			String subject7_languagewise_TotalMarks) {
		this.subject7_languagewise_TotalMarks = subject7_languagewise_TotalMarks;
	}

	public String getSubject8_languagewise_TotalMarks() {
		return subject8_languagewise_TotalMarks;
	}

	public void setSubject8_languagewise_TotalMarks(
			String subject8_languagewise_TotalMarks) {
		this.subject8_languagewise_TotalMarks = subject8_languagewise_TotalMarks;
	}

	public String getSubject9_languagewise_TotalMarks() {
		return subject9_languagewise_TotalMarks;
	}

	public void setSubject9_languagewise_TotalMarks(
			String subject9_languagewise_TotalMarks) {
		this.subject9_languagewise_TotalMarks = subject9_languagewise_TotalMarks;
	}

	public String getSubject10_languagewise_TotalMarks() {
		return subject10_languagewise_TotalMarks;
	}

	public void setSubject10_languagewise_TotalMarks(
			String subject10_languagewise_TotalMarks) {
		this.subject10_languagewise_TotalMarks = subject10_languagewise_TotalMarks;
	}

	public String getSubject1_languagewise_ObtainedMarks() {
		return subject1_languagewise_ObtainedMarks;
	}

	public void setSubject1_languagewise_ObtainedMarks(
			String subject1_languagewise_ObtainedMarks) {
		this.subject1_languagewise_ObtainedMarks = subject1_languagewise_ObtainedMarks;
	}

	public String getSubject2_languagewise_ObtainedMarks() {
		return subject2_languagewise_ObtainedMarks;
	}

	public void setSubject2_languagewise_ObtainedMarks(
			String subject2_languagewise_ObtainedMarks) {
		this.subject2_languagewise_ObtainedMarks = subject2_languagewise_ObtainedMarks;
	}

	public String getSubject3_languagewise_ObtainedMarks() {
		return subject3_languagewise_ObtainedMarks;
	}

	public void setSubject3_languagewise_ObtainedMarks(
			String subject3_languagewise_ObtainedMarks) {
		this.subject3_languagewise_ObtainedMarks = subject3_languagewise_ObtainedMarks;
	}

	public String getSubject4_languagewise_ObtainedMarks() {
		return subject4_languagewise_ObtainedMarks;
	}

	public void setSubject4_languagewise_ObtainedMarks(
			String subject4_languagewise_ObtainedMarks) {
		this.subject4_languagewise_ObtainedMarks = subject4_languagewise_ObtainedMarks;
	}

	public String getSubject5_languagewise_ObtainedMarks() {
		return subject5_languagewise_ObtainedMarks;
	}

	public void setSubject5_languagewise_ObtainedMarks(
			String subject5_languagewise_ObtainedMarks) {
		this.subject5_languagewise_ObtainedMarks = subject5_languagewise_ObtainedMarks;
	}

	public String getSubject6_languagewise_ObtainedMarks() {
		return subject6_languagewise_ObtainedMarks;
	}

	public void setSubject6_languagewise_ObtainedMarks(
			String subject6_languagewise_ObtainedMarks) {
		this.subject6_languagewise_ObtainedMarks = subject6_languagewise_ObtainedMarks;
	}

	public String getSubject7_languagewise_ObtainedMarks() {
		return subject7_languagewise_ObtainedMarks;
	}

	public void setSubject7_languagewise_ObtainedMarks(
			String subject7_languagewise_ObtainedMarks) {
		this.subject7_languagewise_ObtainedMarks = subject7_languagewise_ObtainedMarks;
	}

	public String getSubject8_languagewise_ObtainedMarks() {
		return subject8_languagewise_ObtainedMarks;
	}

	public void setSubject8_languagewise_ObtainedMarks(
			String subject8_languagewise_ObtainedMarks) {
		this.subject8_languagewise_ObtainedMarks = subject8_languagewise_ObtainedMarks;
	}

	public String getSubject9_languagewise_ObtainedMarks() {
		return subject9_languagewise_ObtainedMarks;
	}

	public void setSubject9_languagewise_ObtainedMarks(
			String subject9_languagewise_ObtainedMarks) {
		this.subject9_languagewise_ObtainedMarks = subject9_languagewise_ObtainedMarks;
	}

	public String getSubject10_languagewise_ObtainedMarks() {
		return subject10_languagewise_ObtainedMarks;
	}

	public void setSubject10_languagewise_ObtainedMarks(
			String subject10_languagewise_ObtainedMarks) {
		this.subject10_languagewise_ObtainedMarks = subject10_languagewise_ObtainedMarks;
	}

	public String getTotal_languagewise_Marks() {
		return total_languagewise_Marks;
	}

	public void setTotal_languagewise_Marks(String total_languagewise_Marks) {
		this.total_languagewise_Marks = total_languagewise_Marks;
	}

	public String getTotal_languagewise_ObtainedMarks() {
		return total_languagewise_ObtainedMarks;
	}

	public void setTotal_languagewise_ObtainedMarks(
			String total_languagewise_ObtainedMarks) {
		this.total_languagewise_ObtainedMarks = total_languagewise_ObtainedMarks;
	}

	public boolean isSubject1Mandatory() {
		return subject1Mandatory;
	}

	public void setSubject1Mandatory(boolean subject1Mandatory) {
		this.subject1Mandatory = subject1Mandatory;
	}

	public boolean isSubject2Mandatory() {
		return subject2Mandatory;
	}

	public void setSubject2Mandatory(boolean subject2Mandatory) {
		this.subject2Mandatory = subject2Mandatory;
	}

	public boolean isSubject3Mandatory() {
		return subject3Mandatory;
	}

	public void setSubject3Mandatory(boolean subject3Mandatory) {
		this.subject3Mandatory = subject3Mandatory;
	}

	public boolean isSubject4Mandatory() {
		return subject4Mandatory;
	}

	public void setSubject4Mandatory(boolean subject4Mandatory) {
		this.subject4Mandatory = subject4Mandatory;
	}

	public boolean isSubject5Mandatory() {
		return subject5Mandatory;
	}

	public void setSubject5Mandatory(boolean subject5Mandatory) {
		this.subject5Mandatory = subject5Mandatory;
	}

	public boolean isSubject6Mandatory() {
		return subject6Mandatory;
	}

	public void setSubject6Mandatory(boolean subject6Mandatory) {
		this.subject6Mandatory = subject6Mandatory;
	}

	public boolean isSubject7Mandatory() {
		return subject7Mandatory;
	}

	public void setSubject7Mandatory(boolean subject7Mandatory) {
		this.subject7Mandatory = subject7Mandatory;
	}

	public boolean isSubject8Mandatory() {
		return subject8Mandatory;
	}

	public void setSubject8Mandatory(boolean subject8Mandatory) {
		this.subject8Mandatory = subject8Mandatory;
	}

	public boolean isSubject9Mandatory() {
		return subject9Mandatory;
	}

	public void setSubject9Mandatory(boolean subject9Mandatory) {
		this.subject9Mandatory = subject9Mandatory;
	}

	public boolean isSubject10Mandatory() {
		return subject10Mandatory;
	}

	public void setSubject10Mandatory(boolean subject10Mandatory) {
		this.subject10Mandatory = subject10Mandatory;
	}

	public boolean isSubject11Mandatory() {
		return subject11Mandatory;
	}

	public void setSubject11Mandatory(boolean subject11Mandatory) {
		this.subject11Mandatory = subject11Mandatory;
	}

	public boolean isSubject12Mandatory() {
		return subject12Mandatory;
	}

	public void setSubject12Mandatory(boolean subject12Mandatory) {
		this.subject12Mandatory = subject12Mandatory;
	}

	public boolean isSubject13Mandatory() {
		return subject13Mandatory;
	}

	public void setSubject13Mandatory(boolean subject13Mandatory) {
		this.subject13Mandatory = subject13Mandatory;
	}

	public boolean isSubject14Mandatory() {
		return subject14Mandatory;
	}

	public void setSubject14Mandatory(boolean subject14Mandatory) {
		this.subject14Mandatory = subject14Mandatory;
	}

	public boolean isSubject15Mandatory() {
		return subject15Mandatory;
	}

	public void setSubject15Mandatory(boolean subject15Mandatory) {
		this.subject15Mandatory = subject15Mandatory;
	}

	public boolean isSubject16Mandatory() {
		return subject16Mandatory;
	}

	public void setSubject16Mandatory(boolean subject16Mandatory) {
		this.subject16Mandatory = subject16Mandatory;
	}

	public boolean isSubject17Mandatory() {
		return subject17Mandatory;
	}

	public void setSubject17Mandatory(boolean subject17Mandatory) {
		this.subject17Mandatory = subject17Mandatory;
	}

	public boolean isSubject18Mandatory() {
		return subject18Mandatory;
	}

	public void setSubject18Mandatory(boolean subject18Mandatory) {
		this.subject18Mandatory = subject18Mandatory;
	}

	public boolean isSubject19Mandatory() {
		return subject19Mandatory;
	}

	public void setSubject19Mandatory(boolean subject19Mandatory) {
		this.subject19Mandatory = subject19Mandatory;
	}

	public boolean isSubject20Mandatory() {
		return subject20Mandatory;
	}

	public void setSubject20Mandatory(boolean subject20Mandatory) {
		this.subject20Mandatory = subject20Mandatory;
	}

	public String getSubject1Credit() {
		return subject1Credit;
	}

	public void setSubject1Credit(String subject1Credit) {
		this.subject1Credit = subject1Credit;
	}

	public String getSubject2Credit() {
		return subject2Credit;
	}

	public void setSubject2Credit(String subject2Credit) {
		this.subject2Credit = subject2Credit;
	}

	public String getSubject3Credit() {
		return subject3Credit;
	}

	public void setSubject3Credit(String subject3Credit) {
		this.subject3Credit = subject3Credit;
	}

	public String getSubject4Credit() {
		return subject4Credit;
	}

	public void setSubject4Credit(String subject4Credit) {
		this.subject4Credit = subject4Credit;
	}

	public String getSubject5Credit() {
		return subject5Credit;
	}

	public void setSubject5Credit(String subject5Credit) {
		this.subject5Credit = subject5Credit;
	}

	public String getSubject6Credit() {
		return subject6Credit;
	}

	public void setSubject6Credit(String subject6Credit) {
		this.subject6Credit = subject6Credit;
	}

	public String getSubject7Credit() {
		return subject7Credit;
	}

	public void setSubject7Credit(String subject7Credit) {
		this.subject7Credit = subject7Credit;
	}

	public String getSubject15Credit() {
		return subject15Credit;
	}

	public void setSubject15Credit(String subject15Credit) {
		this.subject15Credit = subject15Credit;
	}

	public String getSubject16Credit() {
		return subject16Credit;
	}

	public void setSubject16Credit(String subject16Credit) {
		this.subject16Credit = subject16Credit;
	}

	public String getSubject8Credit() {
		return subject8Credit;
	}

	public void setSubject8Credit(String subject8Credit) {
		this.subject8Credit = subject8Credit;
	}

	public String getSubject9Credit() {
		return subject9Credit;
	}

	public void setSubject9Credit(String subject9Credit) {
		this.subject9Credit = subject9Credit;
	}

	public String getSubject10Credit() {
		return subject10Credit;
	}

	public void setSubject10Credit(String subject10Credit) {
		this.subject10Credit = subject10Credit;
	}

	public String getSubjectid1() {
		return subjectid1;
	}

	public void setSubjectid1(String subjectid1) {
		this.subjectid1 = subjectid1;
	}

	public String getSubjectid2() {
		return subjectid2;
	}

	public void setSubjectid2(String subjectid2) {
		this.subjectid2 = subjectid2;
	}

	public String getSubjectid3() {
		return subjectid3;
	}

	public void setSubjectid3(String subjectid3) {
		this.subjectid3 = subjectid3;
	}

	public String getSubjectid4() {
		return subjectid4;
	}

	public void setSubjectid4(String subjectid4) {
		this.subjectid4 = subjectid4;
	}

	public String getSubjectid5() {
		return subjectid5;
	}

	public void setSubjectid5(String subjectid5) {
		this.subjectid5 = subjectid5;
	}

	public String getSubjectid6() {
		return subjectid6;
	}

	public void setSubjectid6(String subjectid6) {
		this.subjectid6 = subjectid6;
	}

	public String getSubjectid7() {
		return subjectid7;
	}

	public void setSubjectid7(String subjectid7) {
		this.subjectid7 = subjectid7;
	}

	public String getSubjectid8() {
		return subjectid8;
	}

	public void setSubjectid8(String subjectid8) {
		this.subjectid8 = subjectid8;
	}

	public String getSubjectid9() {
		return subjectid9;
	}

	public void setSubjectid9(String subjectid9) {
		this.subjectid9 = subjectid9;
	}

	public String getSubjectid10() {
		return subjectid10;
	}

	public void setSubjectid10(String subjectid10) {
		this.subjectid10 = subjectid10;
	}

	public String getSubjectid11() {
		return subjectid11;
	}

	public void setSubjectid11(String subjectid11) {
		this.subjectid11 = subjectid11;
	}

	public String getSubjectid12() {
		return subjectid12;
	}

	public void setSubjectid12(String subjectid12) {
		this.subjectid12 = subjectid12;
	}

	public String getSubjectid13() {
		return subjectid13;
	}

	public void setSubjectid13(String subjectid13) {
		this.subjectid13 = subjectid13;
	}

	public String getSubjectid14() {
		return subjectid14;
	}

	public void setSubjectid14(String subjectid14) {
		this.subjectid14 = subjectid14;
	}

	public String getSubjectid15() {
		return subjectid15;
	}

	public void setSubjectid15(String subjectid15) {
		this.subjectid15 = subjectid15;
	}

	public String getSubjectid16() {
		return subjectid16;
	}

	public void setSubjectid16(String subjectid16) {
		this.subjectid16 = subjectid16;
	}

	public String getSubjectid17() {
		return subjectid17;
	}

	public void setSubjectid17(String subjectid17) {
		this.subjectid17 = subjectid17;
	}

	public String getSubjectid18() {
		return subjectid18;
	}

	public void setSubjectid18(String subjectid18) {
		this.subjectid18 = subjectid18;
	}

	public String getSubjectid19() {
		return subjectid19;
	}

	public void setSubjectid19(String subjectid19) {
		this.subjectid19 = subjectid19;
	}

	public String getSubjectid20() {
		return subjectid20;
	}

	public void setSubjectid20(String subjectid20) {
		this.subjectid20 = subjectid20;
	}

	public String getSubjectmarkid1() {
		return subjectmarkid1;
	}

	public void setSubjectmarkid1(String subjectmarkid1) {
		this.subjectmarkid1 = subjectmarkid1;
	}

	public String getSubjectmarkid2() {
		return subjectmarkid2;
	}

	public void setSubjectmarkid2(String subjectmarkid2) {
		this.subjectmarkid2 = subjectmarkid2;
	}

	public String getSubjectmarkid3() {
		return subjectmarkid3;
	}

	public void setSubjectmarkid3(String subjectmarkid3) {
		this.subjectmarkid3 = subjectmarkid3;
	}

	public String getSubjectmarkid4() {
		return subjectmarkid4;
	}

	public void setSubjectmarkid4(String subjectmarkid4) {
		this.subjectmarkid4 = subjectmarkid4;
	}

	public String getSubjectmarkid5() {
		return subjectmarkid5;
	}

	public void setSubjectmarkid5(String subjectmarkid5) {
		this.subjectmarkid5 = subjectmarkid5;
	}

	public String getSubjectmarkid6() {
		return subjectmarkid6;
	}

	public void setSubjectmarkid6(String subjectmarkid6) {
		this.subjectmarkid6 = subjectmarkid6;
	}

	public String getSubjectmarkid7() {
		return subjectmarkid7;
	}

	public void setSubjectmarkid7(String subjectmarkid7) {
		this.subjectmarkid7 = subjectmarkid7;
	}

	public String getSubjectmarkid8() {
		return subjectmarkid8;
	}

	public void setSubjectmarkid8(String subjectmarkid8) {
		this.subjectmarkid8 = subjectmarkid8;
	}

	public String getSubjectmarkid9() {
		return subjectmarkid9;
	}

	public void setSubjectmarkid9(String subjectmarkid9) {
		this.subjectmarkid9 = subjectmarkid9;
	}

	public String getSubjectmarkid10() {
		return subjectmarkid10;
	}

	public void setSubjectmarkid10(String subjectmarkid10) {
		this.subjectmarkid10 = subjectmarkid10;
	}

	public String getSubjectmarkid11() {
		return subjectmarkid11;
	}

	public void setSubjectmarkid11(String subjectmarkid11) {
		this.subjectmarkid11 = subjectmarkid11;
	}

	public String getSubjectmarkid12() {
		return subjectmarkid12;
	}

	public void setSubjectmarkid12(String subjectmarkid12) {
		this.subjectmarkid12 = subjectmarkid12;
	}

	public String getSubjectmarkid13() {
		return subjectmarkid13;
	}

	public void setSubjectmarkid13(String subjectmarkid13) {
		this.subjectmarkid13 = subjectmarkid13;
	}

	public String getSubjectmarkid14() {
		return subjectmarkid14;
	}

	public void setSubjectmarkid14(String subjectmarkid14) {
		this.subjectmarkid14 = subjectmarkid14;
	}

	public String getSubjectmarkid15() {
		return subjectmarkid15;
	}

	public void setSubjectmarkid15(String subjectmarkid15) {
		this.subjectmarkid15 = subjectmarkid15;
	}

	public String getSubjectmarkid16() {
		return subjectmarkid16;
	}

	public void setSubjectmarkid16(String subjectmarkid16) {
		this.subjectmarkid16 = subjectmarkid16;
	}

	public String getSubjectmarkid17() {
		return subjectmarkid17;
	}

	public void setSubjectmarkid17(String subjectmarkid17) {
		this.subjectmarkid17 = subjectmarkid17;
	}

	public String getSubjectmarkid18() {
		return subjectmarkid18;
	}

	public void setSubjectmarkid18(String subjectmarkid18) {
		this.subjectmarkid18 = subjectmarkid18;
	}

	public String getSubjectmarkid19() {
		return subjectmarkid19;
	}

	public void setSubjectmarkid19(String subjectmarkid19) {
		this.subjectmarkid19 = subjectmarkid19;
	}

	public String getSubjectmarkid20() {
		return subjectmarkid20;
	}

	public void setSubjectmarkid20(String subjectmarkid20) {
		this.subjectmarkid20 = subjectmarkid20;
	}

	public String getSubjectOther1() {
		return subjectOther1;
	}

	public void setSubjectOther1(String subjectOther1) {
		this.subjectOther1 = subjectOther1;
	}

	public String getSubjectOther2() {
		return subjectOther2;
	}

	public void setSubjectOther2(String subjectOther2) {
		this.subjectOther2 = subjectOther2;
	}

	public String getSubjectOther3() {
		return subjectOther3;
	}

	public void setSubjectOther3(String subjectOther3) {
		this.subjectOther3 = subjectOther3;
	}

	public String getSubjectOther4() {
		return subjectOther4;
	}

	public void setSubjectOther4(String subjectOther4) {
		this.subjectOther4 = subjectOther4;
	}

	public String getSubjectOther5() {
		return subjectOther5;
	}

	public void setSubjectOther5(String subjectOther5) {
		this.subjectOther5 = subjectOther5;
	}

	public String getSubjectOther6() {
		return subjectOther6;
	}

	public void setSubjectOther6(String subjectOther6) {
		this.subjectOther6 = subjectOther6;
	}

	public String getSubjectOther7() {
		return subjectOther7;
	}

	public void setSubjectOther7(String subjectOther7) {
		this.subjectOther7 = subjectOther7;
	}

	public String getSubjectOther8() {
		return subjectOther8;
	}

	public void setSubjectOther8(String subjectOther8) {
		this.subjectOther8 = subjectOther8;
	}

	public String getSubjectOther9() {
		return subjectOther9;
	}

	public void setSubjectOther9(String subjectOther9) {
		this.subjectOther9 = subjectOther9;
	}

	public String getSubjectOther10() {
		return subjectOther10;
	}

	public void setSubjectOther10(String subjectOther10) {
		this.subjectOther10 = subjectOther10;
	}

	public String getSubjectOther11() {
		return subjectOther11;
	}

	public void setSubjectOther11(String subjectOther11) {
		this.subjectOther11 = subjectOther11;
	}

	public String getSubjectOther12() {
		return subjectOther12;
	}

	public void setSubjectOther12(String subjectOther12) {
		this.subjectOther12 = subjectOther12;
	}

	public String getSubjectOther13() {
		return subjectOther13;
	}

	public void setSubjectOther13(String subjectOther13) {
		this.subjectOther13 = subjectOther13;
	}

	public String getSubjectOther14() {
		return subjectOther14;
	}

	public void setSubjectOther14(String subjectOther14) {
		this.subjectOther14 = subjectOther14;
	}

	public String getSubjectOther15() {
		return subjectOther15;
	}

	public void setSubjectOther15(String subjectOther15) {
		this.subjectOther15 = subjectOther15;
	}

	public String getSubjectOther16() {
		return subjectOther16;
	}

	public void setSubjectOther16(String subjectOther16) {
		this.subjectOther16 = subjectOther16;
	}

	public String getSubjectOther17() {
		return subjectOther17;
	}

	public void setSubjectOther17(String subjectOther17) {
		this.subjectOther17 = subjectOther17;
	}

	public String getSubjectOther18() {
		return subjectOther18;
	}

	public void setSubjectOther18(String subjectOther18) {
		this.subjectOther18 = subjectOther18;
	}

	public String getSubjectOther19() {
		return subjectOther19;
	}

	public void setSubjectOther19(String subjectOther19) {
		this.subjectOther19 = subjectOther19;
	}

	public String getSubjectOther20() {
		return subjectOther20;
	}

	public void setSubjectOther20(String subjectOther20) {
		this.subjectOther20 = subjectOther20;
	}

	public String getSubject11Credit() {
		return subject11Credit;
	}

	public void setSubject11Credit(String subject11Credit) {
		this.subject11Credit = subject11Credit;
	}

	public String getSubject12Credit() {
		return subject12Credit;
	}

	public void setSubject12Credit(String subject12Credit) {
		this.subject12Credit = subject12Credit;
	}

	public String getSubject13Credit() {
		return subject13Credit;
	}

	public void setSubject13Credit(String subject13Credit) {
		this.subject13Credit = subject13Credit;
	}

	public String getSubject14Credit() {
		return subject14Credit;
	}

	public void setSubject14Credit(String subject14Credit) {
		this.subject14Credit = subject14Credit;
	}

	public String getSubject17Credit() {
		return subject17Credit;
	}

	public void setSubject17Credit(String subject17Credit) {
		this.subject17Credit = subject17Credit;
	}

	public String getSubject18Credit() {
		return subject18Credit;
	}

	public void setSubject18Credit(String subject18Credit) {
		this.subject18Credit = subject18Credit;
	}

	public String getSubject19Credit() {
		return subject19Credit;
	}

	public void setSubject19Credit(String subject19Credit) {
		this.subject19Credit = subject19Credit;
	}

	public String getSubject20Credit() {
		return subject20Credit;
	}

	public void setSubject20Credit(String subject20Credit) {
		this.subject20Credit = subject20Credit;
	}

	public String getTotalMarksCGPA() {
		return totalMarksCGPA;
	}

	public void setTotalMarksCGPA(String totalMarksCGPA) {
		this.totalMarksCGPA = totalMarksCGPA;
	}

	public String getTotalObtainedMarksCGPA() {
		return totalObtainedMarksCGPA;
	}

	public void setTotalObtainedMarksCGPA(String totalObtainedMarksCGPA) {
		this.totalObtainedMarksCGPA = totalObtainedMarksCGPA;
	}
	
	
}
