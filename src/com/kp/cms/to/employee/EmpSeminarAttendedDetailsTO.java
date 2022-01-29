package com.kp.cms.to.employee;

public class EmpSeminarAttendedDetailsTO {
	private int id;
	private String seminar;
	private String seminarName;
	private String participation;
	private String fromDate2;
	private String toDate2;
	
	private String organisation;
	private String interRegional;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSeminar() {
		return seminar;
	}
	public void setSeminar(String seminar) {
		this.seminar = seminar;
	}
	public String getSeminarName() {
		return seminarName;
	}
	public void setSeminarName(String seminarName) {
		this.seminarName = seminarName;
	}
	public String getParticipation() {
		return participation;
	}
	public void setParticipation(String participation) {
		this.participation = participation;
	}
	
	public String getOrganisation() {
		return organisation;
	}
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}
	public String getInterRegional() {
		return interRegional;
	}
	public void setInterRegional(String interRegional) {
		this.interRegional = interRegional;
	}
	public String getFromDate2() {
		return fromDate2;
	}
	public void setFromDate2(String fromDate2) {
		this.fromDate2 = fromDate2;
	}
	public String getToDate2() {
		return toDate2;
	}
	public void setToDate2(String toDate2) {
		this.toDate2 = toDate2;
	}
}
