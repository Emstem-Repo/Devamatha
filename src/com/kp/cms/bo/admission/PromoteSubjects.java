package com.kp.cms.bo.admission;

import java.io.Serializable;

public class PromoteSubjects implements Serializable{
 private int id;
 private String classCode;
 private String section;
 private Integer startNo;
 private Integer endNo;
 private String groupCode;
 private String subCde1;
 private String subject1;
 private String subCde3;
 private String subject3;
 private String subCde4;
 private String subject4;
 private String subCde5;
 private String subject5;
 private String subCde6;
 private String subject6;
 private String subCde7;
 private String subject7;
 private Boolean hasPract1;
 private Boolean hasPract3;
 private Boolean hasPract4;
 private Boolean hasPract5;
 private Boolean hasPract6;
 private Boolean hasPract7;
 private Integer unSubCd1;
 private Integer unSubcd3;
 private Integer unSubcd4;
 private Integer unSubcd5;
 private Integer unSubcd6;
 private Integer unSubcd7;
 private Integer academicYear;
public PromoteSubjects() {
}
public PromoteSubjects(int id, String classCode, String section,
		Integer startNo, Integer endNo, String groupCode, String subCde1,
		String subject1, String subCde3, String subject3, String subCde4,
		String subject4, String subCde5, String subject5, String subCde6,
		String subject6, String subCde7, String subject7, Boolean hasPract1,
		Boolean hasPract3, Boolean hasPract4, Boolean hasPract5,
		Boolean hasPract6, Boolean hasPract7, Integer unSubCd1,
		Integer unSubcd3, Integer unSubcd4, Integer unSubcd5, Integer unSubcd6,
		Integer unSubcd7, Integer academicYear) {
	super();
	this.id = id;
	this.classCode = classCode;
	this.section = section;
	this.startNo = startNo;
	this.endNo = endNo;
	this.groupCode = groupCode;
	this.subCde1 = subCde1;
	this.subject1 = subject1;
	this.subCde3 = subCde3;
	this.subject3 = subject3;
	this.subCde4 = subCde4;
	this.subject4 = subject4;
	this.subCde5 = subCde5;
	this.subject5 = subject5;
	this.subCde6 = subCde6;
	this.subject6 = subject6;
	this.subCde7 = subCde7;
	this.subject7 = subject7;
	this.hasPract1 = hasPract1;
	this.hasPract3 = hasPract3;
	this.hasPract4 = hasPract4;
	this.hasPract5 = hasPract5;
	this.hasPract6 = hasPract6;
	this.hasPract7 = hasPract7;
	this.unSubCd1 = unSubCd1;
	this.unSubcd3 = unSubcd3;
	this.unSubcd4 = unSubcd4;
	this.unSubcd5 = unSubcd5;
	this.unSubcd6 = unSubcd6;
	this.unSubcd7 = unSubcd7;
	this.academicYear = academicYear;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getClassCode() {
	return classCode;
}
public void setClassCode(String classCode) {
	this.classCode = classCode;
}
public String getSection() {
	return section;
}
public void setSection(String section) {
	this.section = section;
}
public Integer getStartNo() {
	return startNo;
}
public void setStartNo(Integer startNo) {
	this.startNo = startNo;
}
public Integer getEndNo() {
	return endNo;
}
public void setEndNo(Integer endNo) {
	this.endNo = endNo;
}
public String getGroupCode() {
	return groupCode;
}
public void setGroupCode(String groupCode) {
	this.groupCode = groupCode;
}
public String getSubCde1() {
	return subCde1;
}
public void setSubCde1(String subCde1) {
	this.subCde1 = subCde1;
}
public String getSubject1() {
	return subject1;
}
public void setSubject1(String subject1) {
	this.subject1 = subject1;
}
public String getSubCde3() {
	return subCde3;
}
public void setSubCde3(String subCde3) {
	this.subCde3 = subCde3;
}
public String getSubject3() {
	return subject3;
}
public void setSubject3(String subject3) {
	this.subject3 = subject3;
}
public String getSubCde4() {
	return subCde4;
}
public void setSubCde4(String subCde4) {
	this.subCde4 = subCde4;
}
public String getSubject4() {
	return subject4;
}
public void setSubject4(String subject4) {
	this.subject4 = subject4;
}
public String getSubCde5() {
	return subCde5;
}
public void setSubCde5(String subCde5) {
	this.subCde5 = subCde5;
}
public String getSubject5() {
	return subject5;
}
public void setSubject5(String subject5) {
	this.subject5 = subject5;
}
public String getSubCde6() {
	return subCde6;
}
public void setSubCde6(String subCde6) {
	this.subCde6 = subCde6;
}
public String getSubject6() {
	return subject6;
}
public void setSubject6(String subject6) {
	this.subject6 = subject6;
}
public String getSubCde7() {
	return subCde7;
}
public void setSubCde7(String subCde7) {
	this.subCde7 = subCde7;
}
public String getSubject7() {
	return subject7;
}
public void setSubject7(String subject7) {
	this.subject7 = subject7;
}
public Boolean getHasPract1() {
	return hasPract1;
}
public void setHasPract1(Boolean hasPract1) {
	this.hasPract1 = hasPract1;
}
public Boolean getHasPract3() {
	return hasPract3;
}
public void setHasPract3(Boolean hasPract3) {
	this.hasPract3 = hasPract3;
}
public Boolean getHasPract4() {
	return hasPract4;
}
public void setHasPract4(Boolean hasPract4) {
	this.hasPract4 = hasPract4;
}
public Boolean getHasPract5() {
	return hasPract5;
}
public void setHasPract5(Boolean hasPract5) {
	this.hasPract5 = hasPract5;
}
public Boolean getHasPract6() {
	return hasPract6;
}
public void setHasPract6(Boolean hasPract6) {
	this.hasPract6 = hasPract6;
}
public Boolean getHasPract7() {
	return hasPract7;
}
public void setHasPract7(Boolean hasPract7) {
	this.hasPract7 = hasPract7;
}
public Integer getUnSubCd1() {
	return unSubCd1;
}
public void setUnSubCd1(Integer unSubCd1) {
	this.unSubCd1 = unSubCd1;
}
public Integer getUnSubcd3() {
	return unSubcd3;
}
public void setUnSubcd3(Integer unSubcd3) {
	this.unSubcd3 = unSubcd3;
}
public Integer getUnSubcd4() {
	return unSubcd4;
}
public void setUnSubcd4(Integer unSubcd4) {
	this.unSubcd4 = unSubcd4;
}
public Integer getUnSubcd5() {
	return unSubcd5;
}
public void setUnSubcd5(Integer unSubcd5) {
	this.unSubcd5 = unSubcd5;
}
public Integer getUnSubcd6() {
	return unSubcd6;
}
public void setUnSubcd6(Integer unSubcd6) {
	this.unSubcd6 = unSubcd6;
}
public Integer getUnSubcd7() {
	return unSubcd7;
}
public void setUnSubcd7(Integer unSubcd7) {
	this.unSubcd7 = unSubcd7;
}
public Integer getAcademicYear() {
	return academicYear;
}
public void setAcademicYear(Integer academicYear) {
	this.academicYear = academicYear;
}

 
 
}
