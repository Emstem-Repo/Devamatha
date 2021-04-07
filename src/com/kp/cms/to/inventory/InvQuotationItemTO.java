package com.kp.cms.to.inventory;

import java.math.BigDecimal;

public class InvQuotationItemTO {
	private int id;
	private InvQuotationTO invQuotationTO;
	private InvItemTO invItemTO;
	private BigDecimal quantity;
	private BigDecimal unitCost;
	public int getId() {
		return id;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public BigDecimal getUnitCost() {
		return unitCost;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}
	public InvQuotationTO getInvQuotationTO() {
		return invQuotationTO;
	}
	public InvItemTO getInvItemTO() {
		return invItemTO;
	}
	public void setInvQuotationTO(InvQuotationTO invQuotationTO) {
		this.invQuotationTO = invQuotationTO;
	}
	public void setInvItemTO(InvItemTO invItemTO) {
		this.invItemTO = invItemTO;
	}
	
}
