package com.kp.cms.bo.admin;

import java.io.Serializable;

public class ApplicationSchemeSubjectGroup implements Serializable{

	private int id;
	private AdmAppln admAppln;
	private SubjectGroup subjectGroup;
	private Integer schemeNo;
	
	
	public ApplicationSchemeSubjectGroup() {}
	
	/**
	 * 
	 * @param id
	 */
	public ApplicationSchemeSubjectGroup(int id) {
		this.id = id;
	}
	/**
	 * @param admAppln
	 * @param id
	 * @param semister
	 * @param subjectGroup
	 */
	public ApplicationSchemeSubjectGroup(AdmAppln admAppln, int id,
			Integer schemeNo, SubjectGroup subjectGroup) {
		super();
		this.admAppln = admAppln;
		this.id = id;
		this.schemeNo = schemeNo;
		this.subjectGroup = subjectGroup;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the admAppln
	 */
	public AdmAppln getAdmAppln() {
		return admAppln;
	}

	/**
	 * @param admAppln the admAppln to set
	 */
	public void setAdmAppln(AdmAppln admAppln) {
		this.admAppln = admAppln;
	}

	/**
	 * @return the subjectGroup
	 */
	public SubjectGroup getSubjectGroup() {
		return subjectGroup;
	}

	/**
	 * @param subjectGroup the subjectGroup to set
	 */
	public void setSubjectGroup(SubjectGroup subjectGroup) {
		this.subjectGroup = subjectGroup;
	}

	/**
	 * @return the schemeNo
	 */
	public Integer getSchemeNo() {
		return schemeNo;
	}

	/**
	 * @param schemeNo the schemeNo to set
	 */
	public void setSchemeNo(Integer schemeNo) {
		this.schemeNo = schemeNo;
	}
}
