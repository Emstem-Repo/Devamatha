package com.kp.cms.to.inventory;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.to.admin.SingleFieldMasterTO;

public class ItemStockTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private SingleFieldMasterTO invLocationTO;
	private ItemTO itemTO;
	private String availableStock;
	private boolean isActive;
	private int invRequestId;
	private Date createdDate;	
	private String createdBy;
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getId() {
		return id;
	}
	public SingleFieldMasterTO getInvLocationTO() {
		return invLocationTO;
	}
	public ItemTO getItemTO() {
		return itemTO;
	}
	public String getAvailableStock() {
		return availableStock;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setInvLocationTO(SingleFieldMasterTO invLocationTO) {
		this.invLocationTO = invLocationTO;
	}
	public void setItemTO(ItemTO itemTO) {
		this.itemTO = itemTO;
	}
	public void setAvailableStock(String availableStock) {
		this.availableStock = availableStock;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public int getInvRequestId() {
		return invRequestId;
	}
	public void setInvRequestId(int invRequestId) {
		this.invRequestId = invRequestId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
}
