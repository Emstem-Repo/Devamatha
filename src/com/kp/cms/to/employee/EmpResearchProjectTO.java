package com.kp.cms.to.employee;

import java.io.Serializable;

public class EmpResearchProjectTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String empResPubMasterId;
	private String title;
	private String investigators;
	private String sponsors;
	private String abstractObjectives;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInvestigators() {
		return investigators;
	}
	public void setInvestigators(String investigators) {
		this.investigators = investigators;
	}
	public String getSponsors() {
		return sponsors;
	}
	public void setSponsors(String sponsors) {
		this.sponsors = sponsors;
	}
	public String getAbstractObjectives() {
		return abstractObjectives;
	}
	public void setAbstractObjectives(String abstractObjectives) {
		this.abstractObjectives = abstractObjectives;
	}
	public String getEmpResPubMasterId() {
		return empResPubMasterId;
	}
	public void setEmpResPubMasterId(String empResPubMasterId) {
		this.empResPubMasterId = empResPubMasterId;
	}
	

}
