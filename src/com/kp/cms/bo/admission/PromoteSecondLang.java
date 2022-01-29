package com.kp.cms.bo.admission;

import java.io.Serializable;

public class PromoteSecondLang implements Serializable{
   private int id;
   private String secondLanguage;
   private String langCode;
   private Integer academicYear;
   public PromoteSecondLang(){
	   
   }
public PromoteSecondLang(int id, String secondLanguage, String langCode) {
	super();
	this.id = id;
	this.secondLanguage = secondLanguage;
	this.langCode = langCode;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getSecondLanguage() {
	return secondLanguage;
}
public void setSecondLanguage(String secondLanguage) {
	this.secondLanguage = secondLanguage;
}
public String getLangCode() {
	return langCode;
}
public void setLangCode(String langCode) {
	this.langCode = langCode;
}
public Integer getAcademicYear() {
	return academicYear;
}
public void setAcademicYear(Integer academicYear) {
	this.academicYear = academicYear;
}
}
