package com.kp.cms.to.admission;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.to.admin.CourseTO;

public class DocTO implements Serializable {
	
	private int id;
	private String createdBy;;
	private String modifiedBy;
	private Course course;
	private int docTypeId;
	private String name;
	private Date createdDate;
	private Date lastModifiedDate;
	private String year;
	private String isMarksCard;
	private String isConsolidatedMarks;
	private String needToProduce;
	private String select;
	private String semesterWise;
	private String previousExam;
	private CourseTO courseTo;
	private String tempSelect;
	private boolean isActive;
	private String isIncludeLanguage;
	private String isExamRequired;
	private String needToProduceSemwiseMc;
	private String isEducationalInfo;
	public CourseTO getCourseTo() {
		return courseTo;
	}
	public void setCourseTo(CourseTO courseTo) {
		this.courseTo = courseTo;
	}
	public String getSemesterWise() {
		return semesterWise;
	}
	public void setSemesterWise(String semesterWise) {
		this.semesterWise = semesterWise;
	}
	public String getPreviousExam() {
		return previousExam;
	}
	public void setPreviousExam(String previousExam) {
		this.previousExam = previousExam;
	}
	
	public String getIsMarksCard() {
		return isMarksCard;
	}
	public void setIsMarksCard(String isMarksCard) {
		this.isMarksCard = isMarksCard;
	}
	public String getIsConsolidatedMarks() {
		return isConsolidatedMarks;
	}
	public void setIsConsolidatedMarks(String isConsolidatedMarks) {
		this.isConsolidatedMarks = isConsolidatedMarks;
	}
	public String getNeedToProduce() {
		return needToProduce;
	}
	public void setNeedToProduce(String needToProduce) {
		this.needToProduce = needToProduce;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy)  {
		this.createdBy = createdBy;
	}
	public String getModifiedBy()  {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * @return the docTypeId
	 */
	public int getDocTypeId() {
		return docTypeId;
	}
	/**
	 * @param docTypeId the docTypeId to set
	 */
	public void setDocTypeId(int docTypeId) {
		this.docTypeId = docTypeId;
	}
	/**
	 * @return the select
	 */
	public String getSelect() {
		return select;
	}
	/**
	 * @param select the select to set
	 */
	public void setSelect(String select) {
		this.select = select;
	}
	/**
	 * @return the tempSelect
	 */
	public String getTempSelect() {
		return tempSelect;
	}
	/**
	 * @param tempSelect the tempSelect to set
	 */
	public void setTempSelect(String tempSelect) {
		this.tempSelect = tempSelect;
	}
	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	/**
	 * @return the isIncludeLanguage
	 */
	public String getIsIncludeLanguage() {
		return isIncludeLanguage;
	}
	/**
	 * @param isIncludeLanguage the isIncludeLanguage to set
	 */
	public void setIsIncludeLanguage(String isIncludeLanguage) {
		this.isIncludeLanguage = isIncludeLanguage;
	}
	public String getIsExamRequired() {
		return isExamRequired;
	}
	public void setIsExamRequired(String isExamRequired) {
		this.isExamRequired = isExamRequired;
	}
	public String getNeedToProduceSemwiseMc() {
		return needToProduceSemwiseMc;
	}
	public void setNeedToProduceSemwiseMc(String needToProduceSemwiseMc) {
		this.needToProduceSemwiseMc = needToProduceSemwiseMc;
	}
	public String getIsEducationalInfo() {
		return isEducationalInfo;
	}
	public void setIsEducationalInfo(String isEducationalInfo) {
		this.isEducationalInfo = isEducationalInfo;
	}
	
}