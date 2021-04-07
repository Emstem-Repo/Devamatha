package com.kp.cms.to.employee;

import java.io.Serializable;
import java.util.Date;

public class HolidayDetailsTO implements Serializable{
private int id;
private String startDate;
private String endDate;
private String description;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getStartDate() {
	return startDate;
}
public void setStartDate(String startDate) {
	this.startDate = startDate;
}
public void setEndDate(String endDate) {
	this.endDate = endDate;
}
public String getEndDate() {
	return endDate;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}


}
