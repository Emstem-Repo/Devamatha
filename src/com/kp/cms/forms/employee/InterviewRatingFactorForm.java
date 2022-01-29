package com.kp.cms.forms.employee;

import com.kp.cms.forms.BaseActionForm;

public class InterviewRatingFactorForm extends BaseActionForm {
	private int id;
	private String ratingFactor;
	private Integer maxScore;
	private Integer displayOrder;
	private Boolean teaching;
	private int duplId;
	private String orgRatingFactor;
	private int orgMaxScore;
	private int orgDisplayOrder;
	private Boolean orgTeaching;
	private int reactivate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRatingFactor() {
		return ratingFactor;
	}
	public void setRatingFactor(String ratingFactor) {
		this.ratingFactor = ratingFactor;
	}
	public Integer getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(Integer maxScore) {
		this.maxScore = maxScore;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	public Boolean getTeaching() {
		return teaching;
	}
	public void setTeaching(Boolean teaching) {
		this.teaching = teaching;
	}
	public int getDuplId() {
		return duplId;
	}
	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}
	public String getOrgRatingFactor() {
		return orgRatingFactor;
	}
	public void setOrgRatingFactor(String orgRatingFactor) {
		this.orgRatingFactor = orgRatingFactor;
	}
	public int getOrgMaxScore() {
		return orgMaxScore;
	}
	public void setOrgMaxScore(int orgMaxScore) {
		this.orgMaxScore = orgMaxScore;
	}
	public int getOrgDisplayOrder() {
		return orgDisplayOrder;
	}
	public void setOrgDisplayOrder(int orgDisplayOrder) {
		this.orgDisplayOrder = orgDisplayOrder;
	}
	public Boolean getOrgTeaching() {
		return orgTeaching;
	}
	public void setOrgTeaching(Boolean orgTeaching) {
		this.orgTeaching = orgTeaching;
	}
	public int getReactivate() {
		return reactivate;
	}
	public void setReactivate(int reactivate) {
		this.reactivate = reactivate;
	}
	
}
