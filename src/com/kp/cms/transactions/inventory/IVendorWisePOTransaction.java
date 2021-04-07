package com.kp.cms.transactions.inventory;

import java.util.List;

import com.kp.cms.bo.admin.InvPurchaseOrder;
import com.kp.cms.bo.admin.InvPurchaseReturn;

public interface IVendorWisePOTransaction {

	/**
	 * Gets purchase order details by vendor
	 */
	public List<InvPurchaseOrder> getPurchaseOrdersByVendor(int vendorId) throws Exception;
	
	/**
	 * Gets purchase order bu purchase order ID
	 */
	public InvPurchaseOrder getPurchaseOrderDetailsByID(int purchaseOrderID)throws Exception;
	
	/**
	 * Used to get all purchase order Nos
	 */
	public List<String>getAllPurchaseOrderNos()throws Exception;
	
	/**
	 * Gets Puchase order returns based on purchase order no.s
	 */
	public List<InvPurchaseReturn> getPurchaseReturnsByPurchaseOrderNo(String puchaseNumber) throws Exception;
	
	/**
	 * Gets purchase returns base on the purchase return id
	 */
	public InvPurchaseReturn getPurchaseReturnDetailsByID(int purchaseReturnId)throws Exception;
}
