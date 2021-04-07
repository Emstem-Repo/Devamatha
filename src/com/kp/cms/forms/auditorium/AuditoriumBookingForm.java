package com.kp.cms.forms.auditorium;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.auditorium.AuditoriumBooking;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.auditorium.AuditoriumBookingTo;
import com.kp.cms.to.auditorium.VenueDetailsTO;

public class AuditoriumBookingForm extends BaseActionForm{
   private int id;
   private String date;
   private List<VenueDetailsTO> venueToList;
   private String month;
   private String day;
   private String year;
   private String timeFrom;
   private String timeTo;
   private String mode;
   private String startTime;
   private String endTime;
   private Map<Integer,String> blockMap;
   private String blockId;
   private String venueId;
   private String remarks;
   private String[] requirements;
   private Map<Integer,String> bookingRequirements;
   private String eventId;
   private Map<Integer, String> venueMap;
   private String multipleCheckValues;
   private String userName;
   private String mailId;
   private String mobileNo;
   private AuditoriumBooking auditoriumBooking; 
   private String adminRemarks;
   private String checkDuplicateCalendarEvents;
   private Map<String, List<AuditoriumBookingTo>> audiBookingMap;
   
   
   
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public List<VenueDetailsTO> getVenueToList() {
	return venueToList;
}
public void setVenueToList(List<VenueDetailsTO> venueToList) {
		this.venueToList = venueToList;
}
public String getMonth() {
	return month;
}
public void setMonth(String month) {
	this.month = month;
}
public String getDay() {
	return day;
}
public void setDay(String day) {
	this.day = day;
}
public String getYear() {
	return year;
}
public void setYear(String year) {
	this.year = year;
}
public String getTimeFrom() {
	return timeFrom;
}
public void setTimeFrom(String timeFrom) {
	this.timeFrom = timeFrom;
}
public String getTimeTo() {
	return timeTo;
}
public void setTimeTo(String timeTo) {
	this.timeTo = timeTo;
}

public ActionErrors validate(ActionMapping mapping,
		HttpServletRequest request) {
	String formName = request.getParameter(CMSConstants.FORMNAME);
	ActionErrors actionErrors = super.validate(mapping, request, formName);

	return actionErrors;
}
public void reset(){
	this.timeFrom = null;
	this.timeTo = null;
	this.blockId=null;
	this.venueId=null;
	this.checkDuplicateCalendarEvents=null;
}
public String getMode() {
	return mode;
}
public void setMode(String mode) {
	this.mode = mode;
}

public String getStartTime() {
	return startTime;
}
public void setStartTime(String startTime) {
	this.startTime = startTime;
}
public String getEndTime() {
	return endTime;
}
public void setEndTime(String endTime) {
	this.endTime = endTime;
}
public Map<Integer, String> getBlockMap() {
	return blockMap;
}
public void setBlockMap(Map<Integer, String> blockMap) {
	this.blockMap = blockMap;
}
public String getBlockId() {
	return blockId;
}
public void setBlockId(String blockId) {
	this.blockId = blockId;
}
public String getVenueId() {
	return venueId;
}
public void setVenueId(String venueId) {
	this.venueId = venueId;
}

public String getRemarks() {
	return remarks;
}
public void setRemarks(String remarks) {
	this.remarks = remarks;
}
public String[] getRequirements() {
	return requirements;
}
public void setRequirements(String[] requirements) {
	this.requirements = requirements;
}
public void setBookingRequirements(Map<Integer, String> bookingRequirements) {
	this.bookingRequirements = bookingRequirements;
}
public Map<Integer, String> getBookingRequirements() {
	return bookingRequirements;
}
public String getEventId() {
	return eventId;
}
public void setEventId(String eventId) {
	this.eventId = eventId;
}
public Map<Integer, String> getVenueMap() {
	return venueMap;
}
public void setVenueMap(Map<Integer, String> venueMap) {
	this.venueMap = venueMap;
}
public String getMultipleCheckValues() {
	return multipleCheckValues;
}
public void setMultipleCheckValues(String multipleCheckValues) {
	this.multipleCheckValues = multipleCheckValues;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getMailId() {
	return mailId;
}
public void setMailId(String mailId) {
	this.mailId = mailId;
}
public String getMobileNo() {
	return mobileNo;
}
public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
}

public AuditoriumBooking getAuditoriumBooking() {
	return auditoriumBooking;
}
public void setAuditoriumBooking(AuditoriumBooking auditoriumBooking) {
	this.auditoriumBooking = auditoriumBooking;
}
public String getAdminRemarks() {
	return adminRemarks;
}
public void setAdminRemarks(String adminRemarks) {
	this.adminRemarks = adminRemarks;
}
public String getCheckDuplicateCalendarEvents() {
	return checkDuplicateCalendarEvents;
}
public void setCheckDuplicateCalendarEvents(String checkDuplicateCalendarEvents) {
	this.checkDuplicateCalendarEvents = checkDuplicateCalendarEvents;
}
public Map<String, List<AuditoriumBookingTo>> getAudiBookingMap() {
	return audiBookingMap;
}
public void setAudiBookingMap(
		Map<String, List<AuditoriumBookingTo>> audiBookingMap) {
	this.audiBookingMap = audiBookingMap;
}
}
