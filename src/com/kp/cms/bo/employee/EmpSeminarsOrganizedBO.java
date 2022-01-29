package com.kp.cms.bo.employee;

public class EmpSeminarsOrganizedBO {
	
	private int id;
	private String nameConferencesSeminar;
	private String nameOrganisers;
	private String resoursePerson;
	private String place;
	private String sponsors;
	private String language;
	private String monthYear;
	private Boolean isSeminarOrganized;
	
	public EmpSeminarsOrganizedBO() {
		super();
	}
	
	public EmpSeminarsOrganizedBO(int id) {
		super();
		this.id = id;
	}

	public EmpSeminarsOrganizedBO(String nameConferencesSeminar,
			String nameOrganisers, String resoursePerson, String place,
			String sponsors, String language, String monthYear, Boolean isSeminarOrganized) {
		super();
		this.nameConferencesSeminar = nameConferencesSeminar;
		this.nameOrganisers = nameOrganisers;
		this.resoursePerson = resoursePerson;
		this.place = place;
		this.sponsors = sponsors;
		this.language = language;
		this.monthYear = monthYear;
		this.isSeminarOrganized = isSeminarOrganized;
	}
	public String getNameConferencesSeminar() {
		return nameConferencesSeminar;
	}
	public void setNameConferencesSeminar(String nameConferencesSeminar) {
		this.nameConferencesSeminar = nameConferencesSeminar;
	}
	public String getNameOrganisers() {
		return nameOrganisers;
	}
	public void setNameOrganisers(String nameOrganisers) {
		this.nameOrganisers = nameOrganisers;
	}
	public String getResoursePerson() {
		return resoursePerson;
	}
	public void setResoursePerson(String resoursePerson) {
		this.resoursePerson = resoursePerson;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getSponsors() {
		return sponsors;
	}
	public void setSponsors(String sponsors) {
		this.sponsors = sponsors;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getMonthYear() {
		return monthYear;
	}
	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}
	public Boolean getIsSeminarOrganized() {
		return isSeminarOrganized;
	}
	public void setIsSeminarOrganized(Boolean isSeminarOrganized) {
		this.isSeminarOrganized = isSeminarOrganized;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
