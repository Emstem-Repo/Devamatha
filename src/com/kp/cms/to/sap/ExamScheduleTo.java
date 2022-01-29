package com.kp.cms.to.sap;

import java.util.Map;

public class ExamScheduleTo {

	private  int id;
	private String examDate;
	private String session;
	private String sessionOrder;
	private String workLocation;
	private String venue;
	private String priority;
	private String[] teachesFrom;
	private String[] teachesTo;
	private Boolean isActive;
	private Map<Integer,String> venueMap;
	private Map<Integer,String> teachersMap;
	private Map<Integer,String> selectedTeachersMap;
	private Map<Integer,String> workLocationMap;
	private int examSchVenueId;
	private int examSchUserId;
	private Map<Integer,Integer> usersBOIdMap;
	private int deleteCount;
	private Map<Integer,Integer> inActiveUsersBOIdMap;
	
	public Map<Integer, String> getTeachersMap() {
		return teachersMap;
	}
	public void setTeachersMap(Map<Integer, String> teachersMap) {
		this.teachersMap = teachersMap;
	}
	public Map<Integer, String> getWorkLocationMap() {
		return workLocationMap;
	}
	public void setWorkLocationMap(Map<Integer, String> workLocationMap) {
		this.workLocationMap = workLocationMap;
	}
	public String getWorkLocation() {
		return workLocation;
	}
	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Map<Integer, String> getVenueMap() {
		return venueMap;
	}
	public void setVenueMap(Map<Integer, String> venueMap) {
		this.venueMap = venueMap;
	}
	public String[] getTeachesFrom() {
		return teachesFrom;
	}
	public void setTeachesFrom(String[] teachesFrom) {
		this.teachesFrom = teachesFrom;
	}
	public String[] getTeachesTo() {
		return teachesTo;
	}
	public void setTeachesTo(String[] teachesTo) {
		this.teachesTo = teachesTo;
	}
	public Map<Integer, String> getSelectedTeachersMap() {
		return selectedTeachersMap;
	}
	public void setSelectedTeachersMap(Map<Integer, String> selectedTeachersMap) {
		this.selectedTeachersMap = selectedTeachersMap;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getExamDate() {
		return examDate;
	}
	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public String getSessionOrder() {
		return sessionOrder;
	}
	public void setSessionOrder(String sessionOrder) {
		this.sessionOrder = sessionOrder;
	}
	public int getExamSchVenueId() {
		return examSchVenueId;
	}
	public void setExamSchVenueId(int examSchVenueId) {
		this.examSchVenueId = examSchVenueId;
	}
	public int getExamSchUserId() {
		return examSchUserId;
	}
	public void setExamSchUserId(int examSchUserId) {
		this.examSchUserId = examSchUserId;
	}
	public Map<Integer, Integer> getUsersBOIdMap() {
		return usersBOIdMap;
	}
	public void setUsersBOIdMap(Map<Integer, Integer> usersBOIdMap) {
		this.usersBOIdMap = usersBOIdMap;
	}
	public int getDeleteCount() {
		return deleteCount;
	}
	public void setDeleteCount(int deleteCount) {
		this.deleteCount = deleteCount;
	}
	public Map<Integer, Integer> getInActiveUsersBOIdMap() {
		return inActiveUsersBOIdMap;
	}
	public void setInActiveUsersBOIdMap(Map<Integer, Integer> inActiveUsersBOIdMap) {
		this.inActiveUsersBOIdMap = inActiveUsersBOIdMap;
	}
	



}
