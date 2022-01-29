package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;

public class CourseSchemeUtilBO implements Serializable {

	private int id;
	private String name;
	// private int schemeNo;
	private int programId;

	private ExamProgramUtilBO examProgramUtilBO;

	protected boolean isActive;

	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;

	private CurriculumSchemeUtilBO curriculumSchemeUtilBO;

	public CurriculumSchemeUtilBO getCurriculumSchemeUtilBO() {
		return curriculumSchemeUtilBO;
	}

	public void setCurriculumSchemeUtilBO(
			CurriculumSchemeUtilBO curriculumSchemeUtilBO) {
		this.curriculumSchemeUtilBO = curriculumSchemeUtilBO;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// public int getSchemeNo() {
	// return schemeNo;
	// }
	// public void setSchemeNo(int schemeNo) {
	// this.schemeNo = schemeNo;
	// }
	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public void setProgramId(int programId) {
		this.programId = programId;
	}

	public int getProgramId() {
		return programId;
	}

	public void setExamProgramUtilBO(ExamProgramUtilBO examProgramUtilBO) {
		this.examProgramUtilBO = examProgramUtilBO;
	}

	public ExamProgramUtilBO getExamProgramUtilBO() {
		return examProgramUtilBO;
	}
}
