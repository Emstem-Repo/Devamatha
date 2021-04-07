package com.kp.cms.forms.admission;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.ApplnAcknowledgementTo;

public class ApplicationAcknowledgeForm extends BaseActionForm{
     private int id;
     private String appNo;
     private String receivedThrough;
     private String receivedDate;
     private String name;
     private String remarks;
     private String flag;
     private String printReceipt;
     private byte[] logo;
     private String slipNo;
     private ApplnAcknowledgementTo applnTo;
     private String slipRequred;
     private boolean availability;
     private String mode;
     private Map<Integer, String> coursesMap;
     private String courseId;
     private String dob;
     private String trackingNo;
     private String mobileNo;
     private boolean setFocus;
     private Boolean isPhotoUpload;
     private Boolean isPhotoRequired;
     private String interviewSelectionDate;
 	private String interviewVenue;
 	private String interviewTime;
 	private String tempVenue;
 	private String selectedDate;
 	private String selectedVenue;
 	private String admApplnId;
 	private String applicationYear;
 	private boolean examCenterRequired;
 	private String applnMode;
 	
     
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getReceivedThrough() {
		return receivedThrough;
	}
	public void setReceivedThrough(String receivedThrough) {
		this.receivedThrough = receivedThrough;
	}
	public String getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
     public void reset(){
    	 this.id = 0;
    	 this.appNo =null;
    	 this.name = null;
    	 this.receivedDate = null;
    	 this.receivedThrough = null;
    	 this.remarks = null;
    	 this.applnTo = null;
    	 this.mode = null;
    	 this.courseId=null;
    	 this.trackingNo=null;
    	 this.dob=null;
    	 this.mobileNo=null;
    	 this.setFocus = false;
    	 this.isPhotoUpload=false;
    	 this.isPhotoRequired=false;
    	 this.interviewSelectionDate=null;
    	 this.interviewTime=null;
    	 this.interviewVenue=null;
    	 this.selectedDate=null;
    	 this.selectedVenue=null;
     }
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getPrintReceipt() {
		return printReceipt;
	}
	public void setPrintReceipt(String printReceipt) {
		this.printReceipt = printReceipt;
	}
	 public ActionErrors validate(ActionMapping mapping,
				HttpServletRequest request) {
			String formName = request.getParameter(CMSConstants.FORMNAME);
			ActionErrors actionErrors = super.validate(mapping, request, formName);

			return actionErrors;
	}
	public byte[] getLogo() {
		return logo;
	}
	public void setLogo(byte[] logo) {
		this.logo = logo;
	}
	public String getSlipNo() {
		return slipNo;
	}
	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}
	public ApplnAcknowledgementTo getApplnTo() {
		return applnTo;
	}
	public void setApplnTo(ApplnAcknowledgementTo applnTo) {
		this.applnTo = applnTo;
	}
	public String getSlipRequred() {
		return slipRequred;
	}
	public void setSlipRequred(String slipRequred) {
		this.slipRequred = slipRequred;
	}
	public boolean isAvailability() {
		return availability;
	}
	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public Map<Integer, String> getCoursesMap() {
		return coursesMap;
	}
	public void setCoursesMap(Map<Integer, String> coursesMap) {
		this.coursesMap = coursesMap;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getTrackingNo() {
		return trackingNo;
	}
	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	/**
	 * to reset fields everytime details of entered appln no has to be set to form
	 */
	public void resetFields() {
		// TODO Auto-generated method stub
		 this.name = null;
    	 this.receivedDate = null;
    	 this.receivedThrough = null;
    	 this.remarks = null;
    	 this.applnTo = null;
    	 this.mode = null;
    	 this.courseId=null;
    	 this.trackingNo=null;
    	 this.dob=null;
    	 this.mobileNo=null;
    	 this.isPhotoUpload=false;
    	 this.isPhotoRequired=false;
	}
	public boolean isSetFocus() {
		return setFocus;
	}
	public void setSetFocus(boolean setFocus) {
		this.setFocus = setFocus;
	}
	public Boolean getIsPhotoUpload() {
		return isPhotoUpload;
	}
	public void setIsPhotoUpload(Boolean isPhotoUpload) {
		this.isPhotoUpload = isPhotoUpload;
	}
	public Boolean getIsPhotoRequired() {
		return isPhotoRequired;
	}
	public void setIsPhotoRequired(Boolean isPhotoRequired) {
		this.isPhotoRequired = isPhotoRequired;
	}
	public String getInterviewSelectionDate() {
		return interviewSelectionDate;
	}
	public void setInterviewSelectionDate(String interviewSelectionDate) {
		this.interviewSelectionDate = interviewSelectionDate;
	}
	public String getInterviewVenue() {
		return interviewVenue;
	}
	public void setInterviewVenue(String interviewVenue) {
		this.interviewVenue = interviewVenue;
	}
	public String getInterviewTime() {
		return interviewTime;
	}
	public void setInterviewTime(String interviewTime) {
		this.interviewTime = interviewTime;
	}
	public String getTempVenue() {
		return tempVenue;
	}
	public void setTempVenue(String tempVenue) {
		this.tempVenue = tempVenue;
	}
	public String getSelectedDate() {
		return selectedDate;
	}
	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}
	public String getSelectedVenue() {
		return selectedVenue;
	}
	public void setSelectedVenue(String selectedVenue) {
		this.selectedVenue = selectedVenue;
	}
	public String getAdmApplnId() {
		return admApplnId;
	}
	public void setAdmApplnId(String admApplnId) {
		this.admApplnId = admApplnId;
	}
	public String getApplicationYear() {
		return applicationYear;
	}
	public void setApplicationYear(String applicationYear) {
		this.applicationYear = applicationYear;
	}
	public boolean isExamCenterRequired() {
		return examCenterRequired;
	}
	public void setExamCenterRequired(boolean examCenterRequired) {
		this.examCenterRequired = examCenterRequired;
	}
	public String getApplnMode() {
		return applnMode;
	}
	public void setApplnMode(String applnMode) {
		this.applnMode = applnMode;
	}
}
