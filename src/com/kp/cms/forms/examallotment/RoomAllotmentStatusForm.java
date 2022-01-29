package com.kp.cms.forms.examallotment;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.examallotment.RoomAllotmentStatusDetailsTo;
import com.kp.cms.to.examallotment.RoomAllotmentStatusTo;

public class RoomAllotmentStatusForm extends BaseActionForm {

	private String cycleId;
	private String roomNo;
	private String campusName;
	private String allottedDate;
	private String midEndSem;
	private Map<Integer, String> workLocationMap;
	private Map<Integer, String> examMap;
	private Map<Integer, String> cycleMap;
	private Map<Integer, String> roomMasterMap;
	private boolean displayStudents;
	private List<RoomAllotmentStatusTo> roomAllotmentStatusToList;
	private String disableFields;
	private List<RoomAllotmentStatusDetailsTo> statusDetailsToList;
	private String cycleName;
	private Map<Integer,String> sessionMap;
	private String sessionId;
	private String sessionName;
	public String getCycleId() {
		return cycleId;
	}
	public void setCycleId(String cycleId) {
		this.cycleId = cycleId;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getCampusName() {
		return campusName;
	}
	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}
	public String getAllottedDate() {
		return allottedDate;
	}
	public void setAllottedDate(String allottedDate) {
		this.allottedDate = allottedDate;
	}
	public String getMidEndSem() {
		return midEndSem;
	}
	public void setMidEndSem(String midEndSem) {
		this.midEndSem = midEndSem;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public Map<Integer, String> getWorkLocationMap() {
		return workLocationMap;
	}
	public void setWorkLocationMap(Map<Integer, String> workLocationMap) {
		this.workLocationMap = workLocationMap;
	}
	public Map<Integer, String> getExamMap() {
		return examMap;
	}
	public void setExamMap(Map<Integer, String> examMap) {
		this.examMap = examMap;
	}
	public Map<Integer, String> getCycleMap() {
		return cycleMap;
	}
	public void setCycleMap(Map<Integer, String> cycleMap) {
		this.cycleMap = cycleMap;
	}
	public Map<Integer, String> getRoomMasterMap() {
		return roomMasterMap;
	}
	public void setRoomMasterMap(Map<Integer, String> roomMasterMap) {
		this.roomMasterMap = roomMasterMap;
	}
    public void reset(){
    	this.allottedDate=null;
    	this.cycleId=null;
    	this.campusName=null;
    	this.examMap=null;
    	this.roomMasterMap=null;
    	this.midEndSem=null;
    	this.cycleMap=null;
    	this.roomNo=null;
    	super.setExamid(0);
    	this.workLocationMap=null;
    	this.roomAllotmentStatusToList=null;
    	this.displayStudents=false;
    	this.disableFields = "false";
    	this.sessionId =null;
    	this.sessionName = null;
    }
	public boolean isDisplayStudents() {
		return displayStudents;
	}
	public void setDisplayStudents(boolean displayStudents) {
		this.displayStudents = displayStudents;
	}
	public List<RoomAllotmentStatusTo> getRoomAllotmentStatusToList() {
		return roomAllotmentStatusToList;
	}
	public void setRoomAllotmentStatusToList(
			List<RoomAllotmentStatusTo> roomAllotmentStatusToList) {
		this.roomAllotmentStatusToList = roomAllotmentStatusToList;
	}
	public List<RoomAllotmentStatusDetailsTo> getStatusDetailsToList() {
		return statusDetailsToList;
	}
	public void setStatusDetailsToList(
			List<RoomAllotmentStatusDetailsTo> statusDetailsToList) {
		this.statusDetailsToList = statusDetailsToList;
	}
	public String getCycleName() {
		return cycleName;
	}
	public void setCycleName(String cycleName) {
		this.cycleName = cycleName;
	}
	public String getDisableFields() {
		return disableFields;
	}
	public void setDisableFields(String disableFields) {
		this.disableFields = disableFields;
	}
	public Map<Integer, String> getSessionMap() {
		return sessionMap;
	}
	public void setSessionMap(Map<Integer, String> sessionMap) {
		this.sessionMap = sessionMap;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getSessionName() {
		return sessionName;
	}
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}
	public void reset1() {
    	this.allottedDate=null;
    	this.cycleId=null;
    	this.campusName=null;
    	this.examMap=null;
    	this.roomMasterMap=null;
    	this.midEndSem=null;
    	this.cycleMap=null;
    	this.roomNo=null;
    	super.setExamid(0);
    	this.workLocationMap=null;
    	this.roomAllotmentStatusToList=null;
    	this.displayStudents=false;
    	this.disableFields = "false";
    	this.sessionId =null;
    	this.sessionName = null;
    	this.setStatusDetailsToList(null);
    	super.setAcademicYear(null);
    }
	
}
