package com.kp.cms.to.employee;

public class EmpBooksPublishedTO {
 private int id;
 private String titleName;
 private String year;
 private String publisherName;
 private String ISBNISSN;
 private String contibutionName;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getTitleName() {
	return titleName;
}
public void setTitleName(String titleName) {
	this.titleName = titleName;
}
public String getYear() {
	return year;
}
public void setYear(String year) {
	this.year = year;
}
public String getPublisherName() {
	return publisherName;
}
public void setPublisherName(String publisherName) {
	this.publisherName = publisherName;
}
public String getISBNISSN() {
	return ISBNISSN;
}
public void setISBNISSN(String iSBNISSN) {
	ISBNISSN = iSBNISSN;
}
public String getContibutionName() {
	return contibutionName;
}
public void setContibutionName(String contibutionName) {
	this.contibutionName = contibutionName;
}
}
