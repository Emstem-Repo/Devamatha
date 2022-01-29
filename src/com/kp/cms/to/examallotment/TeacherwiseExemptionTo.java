package com.kp.cms.to.examallotment;

import java.util.Map;

public class TeacherwiseExemptionTo {
	private int id;
	private String date;
	private String session;
	private Map<Integer,String> sessionMap;
	private String hiddenDate;
	private String hiddenSession;
	private int facId;
	private String createdBy;
	private String createdDate;
	
	
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public int getFacId() {
		return facId;
	}
	public void setFacId(int facId) {
		this.facId = facId;
	}
	public String getHiddenDate() {
		return hiddenDate;
	}
	public void setHiddenDate(String hiddenDate) {
		this.hiddenDate = hiddenDate;
	}
	public String getHiddenSession() {
		return hiddenSession;
	}
	public void setHiddenSession(String hiddenSession) {
		this.hiddenSession = hiddenSession;
	}
	
	public Map<Integer, String> getSessionMap() {
		return sessionMap;
	}
	public void setSessionMap(Map<Integer, String> sessionMap) {
		this.sessionMap = sessionMap;
	}
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
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
}
