package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.ApplicationStatusUpdateTO;

public class ApplicationStatusUpdateForm extends BaseActionForm{
private int id;
private String applicationNo;
private String applicationStatus;
private String selected;
private Map<Integer,String> applicationStatusMap;
private List<ApplicationStatusUpdateTO> statusUpdateTO;
private String origYear;
private FormFile csvFile;
private List<Integer> applnNos;
private List<Integer> applnNosUnavailable;
private List<Integer> applnStatusUnavailable;
private List<Integer> mailNotSentIds;
private int mailNotSentId;
private String reason;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getApplicationNo() {
	return applicationNo;
}
public void setApplicationNo(String applicationNo) {
	this.applicationNo = applicationNo;
}
public String getApplicationStatus() {
	return applicationStatus;
}
public void setApplicationStatus(String applicationStatus) {
	this.applicationStatus = applicationStatus;
}
public String getSelected() {
	return selected;
}
public void setSelected(String selected) {
	this.selected = selected;
}
public Map<Integer, String> getApplicationStatusMap() {
	return applicationStatusMap;
}
public void setApplicationStatusMap(Map<Integer, String> applicationStatusMap) {
	this.applicationStatusMap = applicationStatusMap;
}
public void reset(){
	this.applicationNo=null;
	this.applicationStatus=null;
	this.selected=null;
	this.csvFile=null;
}
public List<ApplicationStatusUpdateTO> getStatusUpdateTO() {
	return statusUpdateTO;
}
public void setStatusUpdateTO(List<ApplicationStatusUpdateTO> statusUpdateTO) {
	this.statusUpdateTO = statusUpdateTO;
}
public String getOrigYear() {
	return origYear;
}
public void setOrigYear(String origYear) {
	this.origYear = origYear;
}
public FormFile getCsvFile() {
	return csvFile;
}
public void setCsvFile(FormFile csvFile) {
	this.csvFile = csvFile;
}
public List<Integer> getApplnNos() {
	return applnNos;
}
public void setApplnNos(List<Integer> applnNos) {
	this.applnNos = applnNos;
}
public List<Integer> getApplnNosUnavailable() {
	return applnNosUnavailable;
}
public void setApplnNosUnavailable(List<Integer> applnNosUnavailable) {
	this.applnNosUnavailable = applnNosUnavailable;
}
public List<Integer> getApplnStatusUnavailable() {
	return applnStatusUnavailable;
}
public void setApplnStatusUnavailable(List<Integer> applnStatusUnavailable) {
	this.applnStatusUnavailable = applnStatusUnavailable;
}
public List<Integer> getMailNotSentIds() {
	return mailNotSentIds;
}
public void setMailNotSentIds(List<Integer> mailNotSentIds) {
	this.mailNotSentIds = mailNotSentIds;
}
public int getMailNotSentId() {
	return mailNotSentId;
}
public void setMailNotSentId(int mailNotSentId) {
	this.mailNotSentId = mailNotSentId;
}
public String getReason() {
	return reason;
}
public void setReason(String reason) {
	this.reason = reason;
}
}
