package com.kp.cms.bo.exam;

/**
 * Mar 1, 2010 Created By 9Elements Team
 */
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class ExamGracingProcessBO implements Serializable {

	private Integer id;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private ExamGracingBO gracingBo;
	private String totalSubMark;
	private String totalGraceMark;
	Set<ExamGracingSubjectMarksBo> subjectMarkBoSet = new HashSet<ExamGracingSubjectMarksBo>();
	
	public Set<ExamGracingSubjectMarksBo> getSubjectMarkBoSet() {
		return subjectMarkBoSet;
	}
	public void setSubjectMarkBoSet(Set<ExamGracingSubjectMarksBo> subjectMarkBoSet) {
		this.subjectMarkBoSet = subjectMarkBoSet;
	}
	public ExamGracingBO getGracingBo() {
		return gracingBo;
	}
	public void setGracingBo(ExamGracingBO gracingBo) {
		this.gracingBo = gracingBo;
	}
	public String getTotalSubMark() {
		return totalSubMark;
	}
	public void setTotalSubMark(String totalSubMark) {
		this.totalSubMark = totalSubMark;
	}
	public String getTotalGraceMark() {
		return totalGraceMark;
	}
	public void setTotalGraceMark(String totalGraceMark) {
		this.totalGraceMark = totalGraceMark;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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





}
