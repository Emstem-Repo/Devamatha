package com.kp.cms.bo.employee;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class InterviewRatingFactor implements Serializable {
private int id;
private String ratingFactor;
private Integer maxScore;
private Integer displayOrder;
private Boolean teaching;
private String createdBy;
private Date createdDate;
private String modifiedBy;
private Date lastModifiedDate;
private Boolean isActive;
private Set<NewInterviewCommentsDetails> interviewCommentDetails = new HashSet<NewInterviewCommentsDetails>();
/**
 * @param id
 * @param ratingFactor
 * @param maxScore
 * @param displayOrder
 * @param teaching
 * @param createdBy
 * @param createdDate
 * @param modifiedBy
 * @param lastModifiedDate
 * @param isActive
 */
public InterviewRatingFactor(int id, String ratingFactor, Integer maxScore,
		Integer displayOrder, Boolean teaching, String createdBy, Date createdDate,
		String modifiedBy, Date lastModifiedDate, Boolean isActive,Set<NewInterviewCommentsDetails> interviewCommentDetails ) {
	super();
	this.id = id;
	this.ratingFactor = ratingFactor;
	this.maxScore = maxScore;
	this.displayOrder = displayOrder;
	this.teaching = teaching;
	this.createdBy = createdBy;
	this.createdDate = createdDate;
	this.modifiedBy = modifiedBy;
	this.lastModifiedDate = lastModifiedDate;
	this.isActive = isActive;
}
public InterviewRatingFactor() {
	// TODO Auto-generated constructor stub
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getRatingFactor() {
	return ratingFactor;
}
public void setRatingFactor(String ratingFactor) {
	this.ratingFactor = ratingFactor;
}
public Integer getMaxScore() {
	return maxScore;
}
public void setMaxScore(Integer maxScore) {
	this.maxScore = maxScore;
}
public Integer getDisplayOrder() {
	return displayOrder;
}
public void setDisplayOrder(Integer displayOrder) {
	this.displayOrder = displayOrder;
}
public Boolean getTeaching() {
	return teaching;
}
public void setTeaching(Boolean teaching) {
	this.teaching = teaching;
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
public Boolean getIsActive() {
	return isActive;
}
public void setIsActive(Boolean isActive) {
	this.isActive = isActive;
}
public void setInterviewCommentDetails(Set<NewInterviewCommentsDetails> interviewCommentDetails) {
	this.interviewCommentDetails = interviewCommentDetails;
}
public Set<NewInterviewCommentsDetails> getInterviewCommentDetails() {
	return interviewCommentDetails;
}

}
