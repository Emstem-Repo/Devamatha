package com.kp.cms.to.admin;

import java.io.Serializable;


public class EligibleSubjectsTO implements Serializable{
	private int id;
	private DetailedSubjectsTO detailedSubjectsTO;
	private EligibilityCriteriaTO eligibilityCriteriaTO;
	private Boolean isActive;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public DetailedSubjectsTO getDetailedSubjectsTO() {
		return detailedSubjectsTO;
	}
	public EligibilityCriteriaTO getEligibilityCriteriaTO() {
		return eligibilityCriteriaTO;
	}
	public void setDetailedSubjectsTO(DetailedSubjectsTO detailedSubjectsTO) {
		this.detailedSubjectsTO = detailedSubjectsTO;
	}
	public void setEligibilityCriteriaTO(EligibilityCriteriaTO eligibilityCriteriaTO) {
		this.eligibilityCriteriaTO = eligibilityCriteriaTO;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}


}
