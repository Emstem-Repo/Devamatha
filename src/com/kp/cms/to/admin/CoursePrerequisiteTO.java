package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;





public class CoursePrerequisiteTO implements Serializable{
	private int id;
	private  WeightageTO weightageTO;
	private CourseTO courseTO;
	private PrerequisiteTO prerequisiteTO;
	private double percentage;
	private double userPercentage;
	private double userMark;
	private double totalMark;
	private String prereqexamid1;
	private String prereqexamid2;
	private String prereqexamName1;
	private String prereqexamName2;
	private String courseName;
	private String courseid;
	private String programid;
	private String programName;
	private String programTypeid;
	private String programTypeName;
	private String percentage1;
	private String precentage2;
	private String rollNo;
	private int examMonth;
	private String examYear;
	private String totalMark1;
	private String totalMark2;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;	
	private String isActive;
	private String cDate;
	private String lDate;
	private Map<String,String> monthMap;

    public CoursePrerequisiteTO(){
    	
    }
	public CoursePrerequisiteTO(int id, WeightageTO weightageTO,
			CourseTO courseTO, PrerequisiteTO prerequisiteTO,
			double percentage, double userPercentage, double userMark,
			double totalMark, String prereqexamid1, String prereqexamid2,
			String prereqexamName1, String prereqexamName2, String courseName,
			String courseid, String programid, String programName,
			String programTypeid, String programTypeName, String percentage1,
			String precentage2, String rollNo, int examMonth, String examYear,
			String totalMark1, String totalMark2, String createdBy,
			String modifiedBy, Date createdDate, Date lastModifiedDate,
			String isActive, String cDate, String lDate,
			Map<String, String> monthMap) {
		super();
		this.id = id;
		this.weightageTO = weightageTO;
		this.courseTO = courseTO;
		this.prerequisiteTO = prerequisiteTO;
		this.percentage = percentage;
		this.userPercentage = userPercentage;
		this.userMark = userMark;
		this.totalMark = totalMark;
		this.prereqexamid1 = prereqexamid1;
		this.prereqexamid2 = prereqexamid2;
		this.prereqexamName1 = prereqexamName1;
		this.prereqexamName2 = prereqexamName2;
		this.courseName = courseName;
		this.courseid = courseid;
		this.programid = programid;
		this.programName = programName;
		this.programTypeid = programTypeid;
		this.programTypeName = programTypeName;
		this.percentage1 = percentage1;
		this.precentage2 = precentage2;
		this.rollNo = rollNo;
		this.examMonth = examMonth;
		this.examYear = examYear;
		this.totalMark1 = totalMark1;
		this.totalMark2 = totalMark2;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = (Date)createdDate.clone();
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
		this.isActive = isActive;
		this.cDate = cDate;
		this.lDate = lDate;
		this.monthMap = monthMap;
	}
	public String getCDate() {
		return cDate;
	}
	public void setCDate(String date) {
		cDate = date;
	}
	public String getLDate() {
		return lDate;
	}
	public void setLDate(String date) {
		lDate = date;
	}
	public WeightageTO getWeightageTO() {
		return weightageTO;
	}
	public void setWeightageTO(WeightageTO weightageTO) {
		this.weightageTO = weightageTO;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getCreatedDate() {
		return (Date)createdDate.clone();
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = (Date)createdDate.clone();
	}
	public Date getLastModifiedDate() {
		return (Date)lastModifiedDate.clone();
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public CourseTO getCourseTO() {
		return courseTO;
	}
	public void setCourseTO(CourseTO courseTO) {
		this.courseTO = courseTO;
	}
	public PrerequisiteTO getPrerequisiteTO() {
		return prerequisiteTO;
	}
	public void setPrerequisiteTO(PrerequisiteTO prerequisiteTO) {
		this.prerequisiteTO = prerequisiteTO;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public double getUserPercentage() {
		return userPercentage;
	}
	public void setUserPercentage(double userPercentage) {
		this.userPercentage = userPercentage;
	}
	public String getPrereqexamid1() {
		return prereqexamid1;
	}
	public void setPrereqexamid1(String prereqexamid1) {
		this.prereqexamid1 = prereqexamid1;
	}
	public String getPrereqexamid2() {
		return prereqexamid2;
	}
	public void setPrereqexamid2(String prereqexamid2) {
		this.prereqexamid2 = prereqexamid2;
	}
	public String getPrereqexamName1() {
		return prereqexamName1;
	}
	public void setPrereqexamName1(String prereqexamName1) {
		this.prereqexamName1 = prereqexamName1;
	}
	public String getPrereqexamName2() {
		return prereqexamName2;
	}
	public void setPrereqexamName2(String prereqexamName2) {
		this.prereqexamName2 = prereqexamName2;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseid() {
		return courseid;
	}
	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}
	public String getProgramid() {
		return programid;
	}
	public void setProgramid(String programid) {
		this.programid = programid;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getProgramTypeid() {
		return programTypeid;
	}
	public void setProgramTypeid(String programTypeid) {
		this.programTypeid = programTypeid;
	}
	public String getProgramTypeName() {
		return programTypeName;
	}
	public void setProgramTypeName(String programTypeName) {
		this.programTypeName = programTypeName;
	}
	public String getPercentage1() {
		return percentage1;
	}
	public void setPercentage1(String percentage1) {
		this.percentage1 = percentage1;
	}
	public String getPrecentage2() {
		return precentage2;
	}
	public void setPrecentage2(String precentage2) {
		this.precentage2 = precentage2;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public int getExamMonth() {
		return examMonth;
	}
	public void setExamMonth(int examMonth) {
		this.examMonth = examMonth;
	}
	public String getExamYear() {
		return examYear;
	}
	public void setExamYear(String examYear) {
		this.examYear = examYear;
	}
	public String getTotalMark1() {
		return totalMark1;
	}
	public String getTotalMark2() {
		return totalMark2;
	}
	public void setTotalMark1(String totalMark1) {
		this.totalMark1 = totalMark1;
	}
	public void setTotalMark2(String totalMark2) {
		this.totalMark2 = totalMark2;
	}
	public double getUserMark() {
		return userMark;
	}
	public void setUserMark(double userMark) {
		this.userMark = userMark;
	}
	public double getTotalMark() {
		return totalMark;
	}
	public void setTotalMark(double totalMark) {
		this.totalMark = totalMark;
	}
	public Map<String, String> getMonthMap() {
		return monthMap;
	}
	public void setMonthMap(Map<String, String> monthMap) {
		this.monthMap = monthMap;
	}
	
	
}
