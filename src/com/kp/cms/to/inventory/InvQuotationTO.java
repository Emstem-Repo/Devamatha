package com.kp.cms.to.inventory;

import java.math.BigDecimal;
import java.util.Date;

public class InvQuotationTO {
	private int id;
	private InvVendorTO invVendorTO;
	private String quoteNo;
	private String quoteDate;
	private String remarks;
	private String termsandconditions;
	private String deliverySite;
	private BigDecimal totalCost;
	private BigDecimal additionalCost;
	private Boolean isActive;
	public int getId() {
		return id;
	}
	public InvVendorTO getInvVendorTO() {
		return invVendorTO;
	}
	public String getQuoteNo() {
		return quoteNo;
	}
	public String getRemarks() {
		return remarks;
	}
	public String getTermsandconditions() {
		return termsandconditions;
	}
	public String getDeliverySite() {
		return deliverySite;
	}
	public BigDecimal getTotalCost() {
		return totalCost;
	}
	public BigDecimal getAdditionalCost() {
		return additionalCost;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setInvVendorTO(InvVendorTO invVendorTO) {
		this.invVendorTO = invVendorTO;
	}
	public void setQuoteNo(String quoteNo) {
		this.quoteNo = quoteNo;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public void setTermsandconditions(String termsandconditions) {
		this.termsandconditions = termsandconditions;
	}
	public void setDeliverySite(String deliverySite) {
		this.deliverySite = deliverySite;
	}
	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}
	public void setAdditionalCost(BigDecimal additionalCost) {
		this.additionalCost = additionalCost;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getQuoteDate() {
		return quoteDate;
	}
	public void setQuoteDate(String quoteDate) {
		this.quoteDate = quoteDate;
	}
	
	
}
