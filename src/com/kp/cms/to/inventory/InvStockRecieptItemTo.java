package com.kp.cms.to.inventory;

import java.util.List;

import com.kp.cms.bo.admin.InvItem;

public class InvStockRecieptItemTo {
	private int id;
	private int countId;
	private int invStockReceiptId;
	private InvItem invItem;
	private String quantity;
	private double alreadyRcvQty;
	private String alreadyRcvUnit;
	private String purchasePrice;
	private String orderPrice;
	private int orderQty;
	private String productNo;
	private String uom;
	private boolean warranty;
	private int invItemCategoryId;	

	private boolean isActive;
	private List<InvAmcTO> invAmcs;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInvStockReceiptId() {
		return invStockReceiptId;
	}
	public void setInvStockReceiptId(int invStockReceiptId) {
		this.invStockReceiptId = invStockReceiptId;
	}
	public InvItem getInvItem() {
		return invItem;
	}
	public void setInvItem(InvItem invItem) {
		this.invItem = invItem;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public List<InvAmcTO> getInvAmcs() {
		return invAmcs;
	}
	public void setInvAmcs(List<InvAmcTO> invAmcs) {
		this.invAmcs = invAmcs;
	}
	public String getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	public int getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
	}
	public boolean isWarranty() {
		return warranty;
	}
	public void setWarranty(boolean warranty) {
		this.warranty = warranty;
	}
	public int getCountId() {
		return countId;
	}
	public void setCountId(int countId) {
		this.countId = countId;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getAlreadyRcvUnit() {
		return alreadyRcvUnit;
	}
	public void setAlreadyRcvUnit(String alreadyRcvUnit) {
		this.alreadyRcvUnit = alreadyRcvUnit;
	}
	public double getAlreadyRcvQty() {
		return alreadyRcvQty;
	}
	public void setAlreadyRcvQty(double alreadyRcvQty) {
		this.alreadyRcvQty = alreadyRcvQty;
	}
	public int getInvItemCategoryId() {
		return invItemCategoryId;
	}
	public void setInvItemCategoryId(int invItemCategoryId) {
		this.invItemCategoryId = invItemCategoryId;
	}
	
}
