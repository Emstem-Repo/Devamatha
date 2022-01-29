package com.kp.cms.to.admin;

import java.util.Date;
import com.kp.cms.to.admission.AdmApplnTO;
// Generated Jan 22, 2009 3:35:47 PM by Hibernate Tools 3.2.0.b9

/**
 * CandidatePreference generated by hbm2java
 */
public class StudentCourseAllotmentTo implements java.io.Serializable {

	private int id;
	private com.kp.cms.to.admission.AdmApplnTO admApplnTO;
	
	private Double indexMark;
	private CourseTO courseTO;
	private String remark;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private boolean isActive;
	private Double language1Marks;
	private Double language2Marks;
	private Double groupMarks;
	private Integer rank;
	private Integer prefNo;
	private boolean isAlloted;
	private String tempChecked;
	private String checked;
	private String studentId;
	private String studentName;
	private String tempChecked1;
	private boolean isSatisfied;
	
	
	
	public boolean getIsSatisfied() {
		return isSatisfied;
	}



	public void setIsSatisfied(boolean isSatisfied) {
		this.isSatisfied = isSatisfied;
	}

	public boolean getIsAlloted() {
		return isAlloted;
	}



	public void setAlloted(boolean isAlloted) {
		this.isAlloted = isAlloted;
	}



	public Integer getPrefNo() {
		return prefNo;
	}



	public void setPrefNo(Integer prefNo) {
		this.prefNo = prefNo;
	}



	public Integer getRank() {
		return rank;
	}



	public void setRank(Integer rank) {
		this.rank = rank;
	}



	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	


	public Double getIndexMark() {
		return indexMark;
	}

	public void setIndexMark(Double indexMark) {
		this.indexMark = indexMark;
	}

	

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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



	public boolean getIsActive() {
		return isActive;
	}



	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}



	public Double getLanguage1Marks() {
		return language1Marks;
	}



	public void setLanguage1Marks(Double language1Marks) {
		this.language1Marks = language1Marks;
	}



	public Double getLanguage2Marks() {
		return language2Marks;
	}



	public void setLanguage2Marks(Double language2Marks) {
		this.language2Marks = language2Marks;
	}



	public Double getGroupMarks() {
		return groupMarks;
	}



	public void setGroupMarks(Double groupMarks) {
		this.groupMarks = groupMarks;
	}



	public AdmApplnTO getAdmApplnTO() {
		return admApplnTO;
	}



	public void setAdmApplnTO(AdmApplnTO admApplnTO) {
		this.admApplnTO = admApplnTO;
	}



	public CourseTO getCourseTO() {
		return courseTO;
	}



	public void setCourseTO(CourseTO courseTO) {
		this.courseTO = courseTO;
	}



	public String getTempChecked() {
		return tempChecked;
	}



	public void setTempChecked(String tempChecked) {
		this.tempChecked = tempChecked;
	}



	public String getChecked() {
		return checked;
	}



	public void setChecked(String checked) {
		this.checked = checked;
	}



	public String getStudentId() {
		return studentId;
	}



	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}



	public String getStudentName() {
		return studentName;
	}



	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}



	public String getTempChecked1() {
		return tempChecked1;
	}



	public void setTempChecked1(String tempChecked1) {
		this.tempChecked1 = tempChecked1;
	}


	


	
	
	

}
