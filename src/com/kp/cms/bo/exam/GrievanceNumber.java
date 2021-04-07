package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;

public class GrievanceNumber implements Serializable{


	private int id;
	private Integer startNo;
	private String prefix;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Integer year;
	private Integer currentNo;
	private Integer slNo;
	public GrievanceNumber() {
	}

	public GrievanceNumber(int id) {
		this.id = id;
	}

	public GrievanceNumber(int id, String type, Integer startNo, String prefix,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, Boolean isActive,Integer year,Integer currentNo,Integer slNo) {
		this.id = id;
		this.startNo = startNo;
		this.prefix = prefix;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.year=year;
		this.currentNo=currentNo;
		this.slNo=slNo;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Integer getStartNo() {
		return this.startNo;
	}

	public void setStartNo(Integer startNo) {
		this.startNo = startNo;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getCurrentNo() {
		return currentNo;
	}

	public void setCurrentNo(Integer currentNo) {
		this.currentNo = currentNo;
	}

	public Integer getSlNo() {
		return slNo;
	}

	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
	}

	

}
