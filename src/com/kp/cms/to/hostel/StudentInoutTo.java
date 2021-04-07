package com.kp.cms.to.hostel;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StudentInoutTo implements Serializable {
	
 private static final long serialVersionUID = 1L;
 private int appFormId;
 private int admId;
 private int personId;
 private int empid;
 private String firstName;
 private String middleName;
 private String lastName;
 private String inTime;
 private String outTime;
 private String inTimeone="00";
 private String inTimetwo="00";
 private String outTimeone="00";
 private String outTimetwo="00";
 private String hostelName;
 
 
 
 
public String getOutTime() {
	return outTime;
}
public void setOutTime(String outTime) {
	this.outTime = outTime;
}

public String getInTime() {
	return inTime;
}
public void setInTime(String inTime) {
	this.inTime = inTime;
}
public String getInTimeone() {
	return inTimeone;
}
public void setInTimeone(String inTimeone) {
	this.inTimeone = inTimeone;
}
public String getInTimetwo() {
	return inTimetwo;
}
public void setInTimetwo(String inTimetwo) {
	this.inTimetwo = inTimetwo;
}
public String getOutTimeone() {
	return outTimeone;
}
public void setOutTimeone(String outTimeone) {
	this.outTimeone = outTimeone;
}
public String getOutTimetwo() {
	return outTimetwo;
}
public void setOutTimetwo(String outTimetwo) {
	this.outTimetwo = outTimetwo;
}
public int getAppFormId() {
	return appFormId;
}
public void setAppFormId(int appFormId) {
	this.appFormId = appFormId;
}
public int getAdmId() {
	return admId;
}
public void setAdmId(int admId) {
	this.admId = admId;
}
public int getPersonId() {
	return personId;
}
public void setPersonId(int personId) {
	this.personId = personId;
}
public int getEmpid() {
	return empid;
}
public void setEmpid(int empid) {
	this.empid = empid;
}
public static long getSerialVersionUID() {
	return serialVersionUID;
}

public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getMiddleName() {
	return middleName;
}
public void setMiddleName(String middleName) {
	this.middleName = middleName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public String getHostelName() {
	return hostelName;
}
public void setHostelName(String hostelName) {
	this.hostelName = hostelName;
}


}
