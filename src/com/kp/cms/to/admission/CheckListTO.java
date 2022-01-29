package com.kp.cms.to.admission;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTO;

public class CheckListTO implements Serializable {

	private int programType;
	private CourseTO courseTo;
	private ProgramTO programTO; 
	private DocTO docTO;
	private String isActive;
	private String isMarks_card;
	private String consolidated_Marks;
	private String need_To_Produce;
	private String name;
	private Date createdDate;
	private Date lastModifiedDate;
	private String year;
	private int docChecklistId;
	private int docTypeId;
	private boolean select;
	private String semesterWise;
	private String previousExam;
	private String createdBy;;
	private String modifiedBy;
	private String combinedYear;
	private String isIncludeLanguage;
	private String isExamRequired;
	private boolean checkListSel;
	private String course_year;
	
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
	public boolean isSelect() {
		return select;
	}
	public void setSelect(boolean select) {
		this.select = select;
	}
	public DocTO getDocTO() {
		return docTO;
	}
	public void setDocTO(DocTO docTO) {
		this.docTO = docTO;
	}
	public int getDocChecklistId() {
		return docChecklistId;
	}
	public int getDocTypeId() {
		return docTypeId;
	}
	public void setDocTypeId(int docTypeId) {
		this.docTypeId = docTypeId;
	}
	public ProgramTO getProgramTO() {
		return programTO;
	}
	public void setProgramTO(ProgramTO programTO) {
		this.programTO = programTO;
	}
	public void setDocChecklistId(int docChecklistId) {
		this.docChecklistId = docChecklistId;
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
	public String getIsMarks_card() {
		return isMarks_card;
	}
	public void setIsMarks_card(String isMarks_card) {
		this.isMarks_card = isMarks_card;
	}
	public String getConsolidated_Marks() {
		return consolidated_Marks;
	}
	public void setConsolidated_Marks(String consolidated_Marks) {
		this.consolidated_Marks = consolidated_Marks;
	}
	public String getNeed_To_Produce() {
		return need_To_Produce;
	}
	public void setNeed_To_Produce(String need_To_Produce) {
		this.need_To_Produce = need_To_Produce;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getIsActive() {
		return isActive;
	}
	public int getProgramType() {
		return programType;
	}
	public void setProgramType(int programType) {
		this.programType = programType;
	}
	public CourseTO getCourseTo() {
		return courseTo;
	}
	public void setCourseTo(CourseTO courseTo) {
		this.courseTo = courseTo;
	}
	/**
	 * @return the combinedYear
	 */
	public String getCombinedYear() {
		return combinedYear;
	}
	/**
	 * @param combinedYear the combinedYear to set
	 */
	public void setCombinedYear(String combinedYear) {
		this.combinedYear = combinedYear;
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
	public String isExamRequired() {
		return isExamRequired;
	}
	public void setExamRequired(String isExamRequired) {
		this.isExamRequired = isExamRequired;
	}
	public boolean isCheckListSel() {
		return checkListSel;
	}
	public void setCheckListSel(boolean checkListSel) {
		this.checkListSel = checkListSel;
	}
	public String getCourse_year() {
		return course_year;
	}
	public void setCourse_year(String courseYear) {
		course_year = courseYear;
	}
}