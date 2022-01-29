package com.kp.cms.to.phd;





public class PhdConferenceTO implements Comparable<PhdConferenceTO>{
	
	private Integer id;
	private String participated;
	private String organizedBy;
	private String nameProgram;
	private String dateFrom;
	private String dateTo;
	private String level;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getParticipated() {
		return participated;
	}
	public void setParticipated(String participated) {
		this.participated = participated;
	}
	public String getOrganizedBy() {
		return organizedBy;
	}
	public void setOrganizedBy(String organizedBy) {
		this.organizedBy = organizedBy;
	}
	public String getNameProgram() {
		return nameProgram;
	}
	public void setNameProgram(String nameProgram) {
		this.nameProgram = nameProgram;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	@Override
	public int compareTo(PhdConferenceTO arg0) {
		if(arg0 instanceof PhdConferenceTO && arg0.getId()>0 ){
			return this.getId().compareTo(arg0.id);
	}else
		return 0;
}

	
}
