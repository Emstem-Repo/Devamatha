package com.kp.cms.bo.sap;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.kp.cms.bo.admin.PcReceipts;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.OnlinePaymentReciepts;

public class ExamRegistrationDetails implements Serializable{
private int id;
private Student studentId;
private SapVenue sapVenueId;
private ExamScheduleDate examScheduleDateId;
private OnlinePaymentReciepts onlinePaymentReceipt;
private PcReceipts pcReceipts;
private Boolean isOnline;
private Boolean isCancelled;
private Boolean isPaymentFailed;
private Boolean isPresent;
private Date createdDate;
private String createdBy;
private Date lastModifiedDate;
private String modifiedBy;
private Boolean isActive;
public ExamRegistrationDetails() {
	super();
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Student getStudentId() {
	return studentId;
}
public void setStudentId(Student studentId) {
	this.studentId = studentId;
}
public SapVenue getSapVenueId() {
	return sapVenueId;
}
public void setSapVenueId(SapVenue sapVenueId) {
	this.sapVenueId = sapVenueId;
}
public ExamScheduleDate getExamScheduleDateId() {
	return examScheduleDateId;
}
public void setExamScheduleDateId(ExamScheduleDate examScheduleDateId) {
	this.examScheduleDateId = examScheduleDateId;
}
public Boolean getIsOnline() {
	return isOnline;
}
public void setIsOnline(Boolean isOnline) {
	this.isOnline = isOnline;
}
public Boolean getIsCancelled() {
	return isCancelled;
}
public void setIsCancelled(Boolean isCancelled) {
	this.isCancelled = isCancelled;
}
public Boolean getIsPaymentFailed() {
	return isPaymentFailed;
}
public void setIsPaymentFailed(Boolean isPaymentFailed) {
	this.isPaymentFailed = isPaymentFailed;
}
public Boolean getIsPresent() {
	return isPresent;
}
public void setIsPresent(Boolean isPresent) {
	this.isPresent = isPresent;
}
public Date getCreatedDate() {
	return createdDate;
}
public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
}
public String getCreatedBy() {
	return createdBy;
}
public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}
public Date getLastModifiedDate() {
	return lastModifiedDate;
}
public void setLastModifiedDate(Date lastModifiedDate) {
	this.lastModifiedDate = lastModifiedDate;
}
public String getModifiedBy() {
	return modifiedBy;
}
public void setModifiedBy(String modifiedBy) {
	this.modifiedBy = modifiedBy;
}
public Boolean getIsActive() {
	return isActive;
}
public void setIsActive(Boolean isActive) {
	this.isActive = isActive;
}
public OnlinePaymentReciepts getOnlinePaymentReceipt() {
	return onlinePaymentReceipt;
}
public void setOnlinePaymentReceipt(OnlinePaymentReciepts onlinePaymentReceipt) {
	this.onlinePaymentReceipt = onlinePaymentReceipt;
}
public PcReceipts getPcReceipts() {
	return pcReceipts;
}
public void setPcReceipts(PcReceipts pcReceipts) {
	this.pcReceipts = pcReceipts;
}

}
