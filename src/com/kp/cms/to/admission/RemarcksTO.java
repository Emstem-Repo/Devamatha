package com.kp.cms.to.admission;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RemarcksTO implements Serializable{
	private int id;
	private String date;
	private String details;
	private String enteredBy;
	
	public RemarcksTO() {
		super();
	}
	public RemarcksTO(int id, String date, String details, String enteredBy) {
		super();
		this.id = id;
		this.date = date;
		this.details = details;
		this.enteredBy = enteredBy;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getEnteredBy() {
		return enteredBy;
	}
	public void setEnteredBy(String enteredBy) {
		this.enteredBy = enteredBy;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
