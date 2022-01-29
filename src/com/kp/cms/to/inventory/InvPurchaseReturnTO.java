package com.kp.cms.to.inventory;

import java.io.Serializable;

public class InvPurchaseReturnTO implements Serializable{

	private int id;
	private String purchaseOrderNo;
	private String vendorBillNo;
	private String venderBillDate;
	private String reasonForReturn;
	private String purchaseReturnDate;
	private String invLocationName;
	private String vendorName;
	
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public int getId() {
		return id;
	}
	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}
	public String getVendorBillNo() {
		return vendorBillNo;
	}
	public String getVenderBillDate() {
		return venderBillDate;
	}
	public String getReasonForReturn() {
		return reasonForReturn;
	}
	public String getPurchaseReturnDate() {
		return purchaseReturnDate;
	}
	public String getInvLocationName() {
		return invLocationName;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}
	public void setVendorBillNo(String vendorBillNo) {
		this.vendorBillNo = vendorBillNo;
	}
	public void setVenderBillDate(String venderBillDate) {
		this.venderBillDate = venderBillDate;
	}
	public void setReasonForReturn(String reasonForReturn) {
		this.reasonForReturn = reasonForReturn;
	}
	public void setPurchaseReturnDate(String purchaseReturnDate) {
		this.purchaseReturnDate = purchaseReturnDate;
	}
	public void setInvLocationName(String invLocationName) {
		this.invLocationName = invLocationName;
	}
	
}
