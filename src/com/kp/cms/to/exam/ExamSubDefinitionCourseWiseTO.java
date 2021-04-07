package com.kp.cms.to.exam;

import java.io.Serializable;

/**
 * Dec 31, 2009
 * Created By 9Elements Team
 */
public class ExamSubDefinitionCourseWiseTO implements Serializable,Comparable<ExamSubDefinitionCourseWiseTO>
{
	private int id;
	private int subjectId;
	private int schemeId;
	private int schemeNo;
	private int academicYear;
	private int courseId;
	private String subjectCode;
	private String subjectName;
	private String subjectOrder;
	private String subjectSection;
	private String theoryCredit;
	private String practicalCredit;
	private String checked;
	private String tempChecked;
	private String cerCourseId;
	private String cerCourseName;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public ExamSubDefinitionCourseWiseTO() {
		super();
	}
	
	public ExamSubDefinitionCourseWiseTO(int subjectId, String subjectCode,
			String subjectName) {
		super();
		this.subjectId = subjectId;
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSchemeId(int schemeId) {
		this.schemeId = schemeId;
	}
	public int getSchemeId() {
		return schemeId;
	}
	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}
	public int getSchemeNo() {
		return schemeNo;
	}
	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}
	public int getAcademicYear() {
		return academicYear;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public int getCourseId() {
		return courseId;
	}
	public String getSubjectOrder() {
		return subjectOrder;
	}
	public void setSubjectOrder(String subjectOrder) {
		this.subjectOrder = subjectOrder;
	}
	public String getSubjectSection() {
		return subjectSection;
	}
	public void setSubjectSection(String subjectSection) {
		this.subjectSection = subjectSection;
	}
	public String getTheoryCredit() {
		return theoryCredit;
	}
	public void setTheoryCredit(String theoryCredit) {
		this.theoryCredit = theoryCredit;
	}
	@Override
	public int compareTo(ExamSubDefinitionCourseWiseTO arg0) {
		if(arg0!=null && this!=null && arg0.getSubjectName()!=null 
				 && this.getSubjectName()!=null){
			
				return this.getSubjectName().compareTo(arg0.getSubjectName());
		}else
		return 0;
	}
	public String getPracticalCredit() {
		return practicalCredit;
	}
	public void setPracticalCredit(String practicalCredit) {
		this.practicalCredit = practicalCredit;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(String tempChecked) {
		this.tempChecked = tempChecked;
	}
	public String getCerCourseName() {
		return cerCourseName;
	}
	public void setCerCourseName(String cerCourseName) {
		this.cerCourseName = cerCourseName;
	}
	public String getCerCourseId() {
		return cerCourseId;
	}
	public void setCerCourseId(String cerCourseId) {
		this.cerCourseId = cerCourseId;
	}
	
	
	
}
