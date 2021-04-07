package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.Date;

public class CurrencyMasterTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5861614170084073062L;
	private int id;
	private String createdBy;;
	private String modifiedBy;
	private String name;
	private String currencySubdivision;
	private String currencyCode;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String symbol;
	private String active;
	private String cDate;
	private String lDate;
	
	public CurrencyMasterTO(){
		
	}
	public CurrencyMasterTO(int id, String createdBy, String modifiedBy,
			String name, String currencySubdivision, String currencyCode,
			Date createdDate, Date lastModifiedDate, Boolean isActive,
			String symbol, String active, String cDate, String lDate) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.name = name;
		this.currencySubdivision = currencySubdivision;
		this.currencyCode = currencyCode;
		this.createdDate = (Date)createdDate.clone();
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
		this.isActive = isActive;
		this.symbol = symbol;
		this.active = active;
		this.cDate = cDate;
		this.lDate = lDate;
	}
	public String getCDate() {
		return cDate;
	}
	public void setCDate(String date) {
		cDate = date;
	}
	public String getLDate() {
		return lDate;
	}
	public void setLDate(String date) {
		lDate = date;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy)  {
		this.createdBy = createdBy;
	}
	public String getModifiedBy()  {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCurrencySubdivision() {
		return currencySubdivision;
	}
	public void setCurrencySubdivision(String currencySubdivision) {
		this.currencySubdivision = currencySubdivision;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public Date getCreatedDate() {
		return (Date)createdDate.clone();
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = (Date)createdDate.clone();
	}
	public Date getLastModifiedDate() {
		return (Date)lastModifiedDate.clone();
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
}
