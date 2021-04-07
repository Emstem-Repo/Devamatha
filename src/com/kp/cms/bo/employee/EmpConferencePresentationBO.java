package com.kp.cms.bo.employee;

public class EmpConferencePresentationBO {

	private int id;
	private String title;
	private String nameTalksPresentation;
	private String nameConferencesSeminar;
	private String language;
	private String monthYear;
	private String placePublication;
	private String companyInstitution;
	private Boolean isConferencePresentation;
	
	public EmpConferencePresentationBO(int id) {
		super();
		this.id = id;
	}
	public EmpConferencePresentationBO()
	{}
	public EmpConferencePresentationBO(String title,
			String nameTalksPresentation, String nameConferencesSeminar,
			String language, String monthYear, String placePublication,
			String companyInstitution, Boolean isConferencePresentation) {
		super();
		this.title = title;
		this.nameTalksPresentation = nameTalksPresentation;
		this.nameConferencesSeminar = nameConferencesSeminar;
		this.language = language;
		this.monthYear = monthYear;
		this.placePublication = placePublication;
		this.companyInstitution = companyInstitution;
		this.isConferencePresentation = isConferencePresentation;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNameTalksPresentation() {
		return nameTalksPresentation;
	}

	public void setNameTalksPresentation(String nameTalksPresentation) {
		this.nameTalksPresentation = nameTalksPresentation;
	}

	public String getNameConferencesSeminar() {
		return nameConferencesSeminar;
	}

	public void setNameConferencesSeminar(String nameConferencesSeminar) {
		this.nameConferencesSeminar = nameConferencesSeminar;
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

	public String getPlacePublication() {
		return placePublication;
	}

	public void setPlacePublication(String placePublication) {
		this.placePublication = placePublication;
	}

	public String getCompanyInstitution() {
		return companyInstitution;
	}

	public void setCompanyInstitution(String companyInstitution) {
		this.companyInstitution = companyInstitution;
	}

	public Boolean getIsConferencePresentation() {
		return isConferencePresentation;
	}

	public void setIsConferencePresentation(Boolean isConferencePresentation) {
		this.isConferencePresentation = isConferencePresentation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
