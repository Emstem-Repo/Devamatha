package com.kp.cms.to.hostel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class HlDisciplinaryDetailsTO implements Serializable {
	private int id;
	private String disciplineTypeId;
	private String disciplineTypeName;
	private DisciplinaryTypeTO disciplinaryTypeTO;
	private String year;
	private int hl_admission_id;
	private HlAdmissionTo hlAdmissionTo;
	private String registerNo;
	private String date;
	private String description;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String userId;	
	private List<String> studRegList;


	public List<String> getStudRegList() {
		return studRegList;
	}

	public void setStudRegList(List<String> studRegList) {
		this.studRegList = studRegList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDisciplineTypeId() {
		return disciplineTypeId;
	}

	public void setDisciplineTypeId(String disciplineTypeId) {
		this.disciplineTypeId = disciplineTypeId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	private String studentName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDisciplineTypeName() {
		return disciplineTypeName;
	}

	public void setDisciplineTypeName(String disciplineTypeName) {
		this.disciplineTypeName = disciplineTypeName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getHl_admission_id() {
		return hl_admission_id;
	}

	public void setHl_admission_id(int hlAdmissionId) {
		hl_admission_id = hlAdmissionId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DisciplinaryTypeTO getDisciplinaryTypeTO() {
		return disciplinaryTypeTO;
	}

	public void setDisciplinaryTypeTO(DisciplinaryTypeTO disciplinaryTypeTO) {
		this.disciplinaryTypeTO = disciplinaryTypeTO;
	}

	public HlAdmissionTo getHlAdmissionTo() {
		return hlAdmissionTo;
	}

	public void setHlAdmissionTo(HlAdmissionTo hlAdmissionTo) {
		this.hlAdmissionTo = hlAdmissionTo;
	}


}
