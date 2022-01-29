package com.kp.cms.bo.admin;

// Generated Sep 10, 2009 2:39:18 PM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * InvQuotation generated by hbm2java
 */
public class InvQuotation implements java.io.Serializable {

	private int id;
	private InvVendor invVendor;
	private String quoteNo;
	private Date quoteDate;
	private String remarks;
	private String termsandconditions;
	private String deliverySite;
	private BigDecimal totalCost;
	private BigDecimal additionalCost;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<InvQuotationItem> invQuotationItems = new HashSet<InvQuotationItem>(
			0);
	private InvCampus invCampus;
	private InvLocation invLocation;
	private BigDecimal additionalDiscount;
	private Integer year;
	private InvCompany invCompany;
	private BigDecimal serviceTax;
	private BigDecimal serviceCost;
	
	public InvQuotation() {
	}

	public InvQuotation(int id) {
		this.id = id;
	}

	public InvQuotation(int id, InvVendor invVendor, String quoteNo,
			Date quoteDate, String remarks, String termsandconditions,
			String deliverySite, BigDecimal totalCost,
			BigDecimal additionalCost, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive,
			Set<InvQuotationItem> invQuotationItems,InvCampus invCampus,
			InvLocation invLocation,BigDecimal additionalDiscount) {
		this.id = id;
		this.invVendor = invVendor;
		this.quoteNo = quoteNo;
		this.quoteDate = quoteDate;
		this.remarks = remarks;
		this.termsandconditions = termsandconditions;
		this.deliverySite = deliverySite;
		this.totalCost = totalCost;
		this.additionalCost = additionalCost;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.invQuotationItems = invQuotationItems;
		this.invCampus=invCampus;
		this.invLocation=invLocation;
		this.additionalDiscount=additionalDiscount;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public InvVendor getInvVendor() {
		return this.invVendor;
	}

	public void setInvVendor(InvVendor invVendor) {
		this.invVendor = invVendor;
	}

	public String getQuoteNo() {
		return this.quoteNo;
	}

	public void setQuoteNo(String quoteNo) {
		this.quoteNo = quoteNo;
	}

	public Date getQuoteDate() {
		return this.quoteDate;
	}

	public void setQuoteDate(Date quoteDate) {
		this.quoteDate = quoteDate;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTermsandconditions() {
		return this.termsandconditions;
	}

	public void setTermsandconditions(String termsandconditions) {
		this.termsandconditions = termsandconditions;
	}

	public String getDeliverySite() {
		return this.deliverySite;
	}

	public void setDeliverySite(String deliverySite) {
		this.deliverySite = deliverySite;
	}

	public BigDecimal getTotalCost() {
		return this.totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public BigDecimal getAdditionalCost() {
		return this.additionalCost;
	}

	public void setAdditionalCost(BigDecimal additionalCost) {
		this.additionalCost = additionalCost;
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

	public Set<InvQuotationItem> getInvQuotationItems() {
		return this.invQuotationItems;
	}

	public void setInvQuotationItems(Set<InvQuotationItem> invQuotationItems) {
		this.invQuotationItems = invQuotationItems;
	}

	public InvCampus getInvCampus() {
		return invCampus;
	}

	public void setInvCampus(InvCampus invCampus) {
		this.invCampus = invCampus;
	}

	public InvLocation getInvLocation() {
		return invLocation;
	}

	public void setInvLocation(InvLocation invLocation) {
		this.invLocation = invLocation;
	}


	public BigDecimal getAdditionalDiscount() {
		return additionalDiscount;
	}

	public void setAdditionalDiscount(BigDecimal additionalDiscount) {
		this.additionalDiscount = additionalDiscount;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public InvCompany getInvCompany() {
		return invCompany;
	}

	public void setInvCompany(InvCompany invCompany) {
		this.invCompany = invCompany;
	}

	public BigDecimal getServiceCost() {
		return serviceCost;
	}

	public void setServiceCost(BigDecimal serviceCost) {
		this.serviceCost = serviceCost;
	}

	public BigDecimal getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(BigDecimal serviceTax) {
		this.serviceTax = serviceTax;
	}

}
