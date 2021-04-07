package com.kp.cms.bo.admin;

import java.io.Serializable;

public class ApplicationAdmittedThrough implements Serializable{
	
	private int id;
	private AdmAppln admAppln;
	private AdmittedThrough admittedThrough;
	
	
	/**
	 * 
	 */
	public ApplicationAdmittedThrough(){
		
	}
	
	/**
	 * 
	 * @param id
	 */
	public ApplicationAdmittedThrough(int id) {
		this.id = id;
	}
	/**
	 * @param admAppln
	 * @param admittedThrough
	 * @param id
	 */
	public ApplicationAdmittedThrough(AdmAppln admAppln,
			AdmittedThrough admittedThrough, int id) {
		super();
		this.admAppln = admAppln;
		this.admittedThrough = admittedThrough;
		this.id = id;
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
	 * @return the admittedThrough
	 */
	public AdmittedThrough getAdmittedThrough() {
		return admittedThrough;
	}

	/**
	 * @param admittedThrough the admittedThrough to set
	 */
	public void setAdmittedThrough(AdmittedThrough admittedThrough) {
		this.admittedThrough = admittedThrough;
	}

	
	
}
