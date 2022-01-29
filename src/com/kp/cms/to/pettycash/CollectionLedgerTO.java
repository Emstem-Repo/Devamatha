package com.kp.cms.to.pettycash;

import java.io.Serializable;

public class CollectionLedgerTO implements Serializable {
	private String date;
	private String time;
	private String receiptNumber;
	private String applRegRollNo;
	private String name;
	private String applNo;
	private String regNo;
	private String rollNo;
	private String course; 
	private String amount;
	private String className;
	
	private short datePos;
	private short timePos;
	private short receiptNumberPos;
	private short applNoPos;
	private short regNoPos;
	private short namePos;
	private short amountPos;
	private short classNamePos;
	
	private String dateDis;
	private String timeDis;
	private String receiptNumberDis;
	private String applNoDis;
	private String regNoDis;
	private String nameDis;
	private String amountDis;
	private String classNameDis;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getReceiptNumber() {
		return receiptNumber;
	}
	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}
	
	public String getApplRegRollNo() {
		return applRegRollNo;
	}
	public void setApplRegRollNo(String applRegRollNo) {
		this.applRegRollNo = applRegRollNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public String getApplNo() {
		return applNo;
	}
	public void setApplNo(String applNo) {
		this.applNo = applNo;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public short getDatePos() {
		return datePos;
	}
	public void setDatePos(short datePos) {
		this.datePos = datePos;
	}
	public short getTimePos() {
		return timePos;
	}
	public void setTimePos(short timePos) {
		this.timePos = timePos;
	}
	public short getReceiptNumberPos() {
		return receiptNumberPos;
	}
	public void setReceiptNumberPos(short receiptNumberPos) {
		this.receiptNumberPos = receiptNumberPos;
	}
	public short getApplNoPos() {
		return applNoPos;
	}
	public void setApplNoPos(short applNoPos) {
		this.applNoPos = applNoPos;
	}
	public short getRegNoPos() {
		return regNoPos;
	}
	public void setRegNoPos(short regNoPos) {
		this.regNoPos = regNoPos;
	}
	public short getNamePos() {
		return namePos;
	}
	public void setNamePos(short namePos) {
		this.namePos = namePos;
	}
	public short getAmountPos() {
		return amountPos;
	}
	public void setAmountPos(short amountPos) {
		this.amountPos = amountPos;
	}
	public short getClassNamePos() {
		return classNamePos;
	}
	public void setClassNamePos(short classNamePos) {
		this.classNamePos = classNamePos;
	}
	public String getDateDis() {
		return dateDis;
	}
	public void setDateDis(String dateDis) {
		this.dateDis = dateDis;
	}
	public String getTimeDis() {
		return timeDis;
	}
	public void setTimeDis(String timeDis) {
		this.timeDis = timeDis;
	}
	public String getReceiptNumberDis() {
		return receiptNumberDis;
	}
	public void setReceiptNumberDis(String receiptNumberDis) {
		this.receiptNumberDis = receiptNumberDis;
	}
	public String getApplNoDis() {
		return applNoDis;
	}
	public void setApplNoDis(String applNoDis) {
		this.applNoDis = applNoDis;
	}
	public String getRegNoDis() {
		return regNoDis;
	}
	public void setRegNoDis(String regNoDis) {
		this.regNoDis = regNoDis;
	}
	public String getNameDis() {
		return nameDis;
	}
	public void setNameDis(String nameDis) {
		this.nameDis = nameDis;
	}
	public String getAmountDis() {
		return amountDis;
	}
	public void setAmountDis(String amountDis) {
		this.amountDis = amountDis;
	}
	public String getClassNameDis() {
		return classNameDis;
	}
	public void setClassNameDis(String classNameDis) {
		this.classNameDis = classNameDis;
	}
	
}
