package com.kp.cms.bo.employee;

import java.util.Date;

public class NewInterviewCommentsDetails {
private int id;
private NewInterviewComments newInterviewComments;
private InterviewRatingFactor interviewRatingFactor;
private String internalInterviewer1;
private String internalInterviewer2;
private String internalInterviewer3;
private String externalInterviewer1;
private String externalInterviewer2;
private String externalInterviewer3;
private String createdBy;
private Date createdDate;
private String modifiedBy;
private Date lastModifiedDate;
//Added by sudhir
private String internalInterviewer4;
private String internalInterviewer5;
private String internalInterviewer6;
private String externalInterviewer4;
private String externalInterviewer5;
private String externalInterviewer6;
public NewInterviewCommentsDetails() {
	super();
}
public NewInterviewCommentsDetails(int id,
		NewInterviewComments newInterviewComments,
		InterviewRatingFactor interviewRatingFactor,
		String internalInterviewer1, String internalInterviewer2,
		String internalInterviewer3, String externalInterviewer1,
		String externalInterviewer2, String externalInterviewer3,
		String createdBy, Date createdDate, String modifiedBy,
		Date lastModifiedDate,String internalInterviewer4,String internalInterviewer5,String internalInterviewer6,
		String externalInterviewer4,String externalInterviewer5,String externalInterviewer6) {
	super();
	this.id = id;
	this.newInterviewComments = newInterviewComments;
	this.interviewRatingFactor = interviewRatingFactor;
	this.internalInterviewer1 = internalInterviewer1;
	this.internalInterviewer2 = internalInterviewer2;
	this.internalInterviewer3 = internalInterviewer3;
	this.externalInterviewer1 = externalInterviewer1;
	this.externalInterviewer2 = externalInterviewer2;
	this.externalInterviewer3 = externalInterviewer3;
	this.createdBy = createdBy;
	this.createdDate = createdDate;
	this.modifiedBy = modifiedBy;
	this.lastModifiedDate = lastModifiedDate;
	this.internalInterviewer4 = internalInterviewer4;
	this.internalInterviewer5 = internalInterviewer5;
	this.internalInterviewer6 = internalInterviewer6;
	this.externalInterviewer4 = externalInterviewer4;
	this.externalInterviewer5 = externalInterviewer5;
	this.externalInterviewer6 = externalInterviewer6;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public NewInterviewComments getNewInterviewComments() {
	return newInterviewComments;
}
public void setNewInterviewComments(NewInterviewComments newInterviewComments) {
	this.newInterviewComments = newInterviewComments;
}
public InterviewRatingFactor getInterviewRatingFactor() {
	return interviewRatingFactor;
}
public void setInterviewRatingFactor(InterviewRatingFactor interviewRatingFactor) {
	this.interviewRatingFactor = interviewRatingFactor;
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
public String getInternalInterviewer1() {
	return internalInterviewer1;
}
public void setInternalInterviewer1(String internalInterviewer1) {
	this.internalInterviewer1 = internalInterviewer1;
}
public String getInternalInterviewer2() {
	return internalInterviewer2;
}
public void setInternalInterviewer2(String internalInterviewer2) {
	this.internalInterviewer2 = internalInterviewer2;
}
public String getInternalInterviewer3() {
	return internalInterviewer3;
}
public void setInternalInterviewer3(String internalInterviewer3) {
	this.internalInterviewer3 = internalInterviewer3;
}
public String getExternalInterviewer1() {
	return externalInterviewer1;
}
public void setExternalInterviewer1(String externalInterviewer1) {
	this.externalInterviewer1 = externalInterviewer1;
}
public String getExternalInterviewer2() {
	return externalInterviewer2;
}
public void setExternalInterviewer2(String externalInterviewer2) {
	this.externalInterviewer2 = externalInterviewer2;
}
public String getExternalInterviewer3() {
	return externalInterviewer3;
}
public void setExternalInterviewer3(String externalInterviewer3) {
	this.externalInterviewer3 = externalInterviewer3;
}
public String getInternalInterviewer4() {
	return internalInterviewer4;
}
public void setInternalInterviewer4(String internalInterviewer4) {
	this.internalInterviewer4 = internalInterviewer4;
}
public String getInternalInterviewer5() {
	return internalInterviewer5;
}
public void setInternalInterviewer5(String internalInterviewer5) {
	this.internalInterviewer5 = internalInterviewer5;
}
public String getInternalInterviewer6() {
	return internalInterviewer6;
}
public void setInternalInterviewer6(String internalInterviewer6) {
	this.internalInterviewer6 = internalInterviewer6;
}
public String getExternalInterviewer4() {
	return externalInterviewer4;
}
public void setExternalInterviewer4(String externalInterviewer4) {
	this.externalInterviewer4 = externalInterviewer4;
}
public String getExternalInterviewer5() {
	return externalInterviewer5;
}
public void setExternalInterviewer5(String externalInterviewer5) {
	this.externalInterviewer5 = externalInterviewer5;
}
public String getExternalInterviewer6() {
	return externalInterviewer6;
}
public void setExternalInterviewer6(String externalInterviewer6) {
	this.externalInterviewer6 = externalInterviewer6;
}

}
