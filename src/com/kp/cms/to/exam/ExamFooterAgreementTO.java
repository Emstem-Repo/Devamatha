package com.kp.cms.to.exam;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExamFooterAgreementTO implements Serializable{
	
	private int id;
	private String name;
	private String type;
	private String classCode;
	private String date;
	private String hallTicketorMarksCard;
	private String year;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHallTicketorMarksCard() {
		return hallTicketorMarksCard;
	}
	public void setHallTicketorMarksCard(String hallTicketorMarksCard) {
		this.hallTicketorMarksCard = hallTicketorMarksCard;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}

}
