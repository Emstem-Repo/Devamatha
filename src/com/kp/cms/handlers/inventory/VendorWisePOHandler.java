package com.kp.cms.handlers.inventory;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvPurchaseOrder;
import com.kp.cms.bo.admin.InvPurchaseReturn;
import com.kp.cms.helpers.inventory.VendorWisePOHelper;
import com.kp.cms.to.inventory.InvPurchaseOrderItemTO;
import com.kp.cms.to.inventory.InvPurchaseOrderTO;
import com.kp.cms.to.inventory.InvPurchaseReturnTO;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.transactions.inventory.IVendorWisePOTransaction;
import com.kp.cms.transactionsimpl.inventory.VendorWisePOTransactionImpl;

public class VendorWisePOHandler {
	private static final Log log = LogFactory.getLog(VendorWisePOHandler.class);
	public static volatile VendorWisePOHandler vendorWisePOHandler = null;

	private VendorWisePOHandler(){
	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static VendorWisePOHandler getInstance() {
		if (vendorWisePOHandler == null) {
			vendorWisePOHandler = new VendorWisePOHandler();
		}
		return vendorWisePOHandler;
	}
	
	/**
	 * Gets purchase orders by vendor
	 */
	public List<InvPurchaseOrderTO> getPurchaseOrdersByVendor(int vendorId) throws Exception{
		log.info("start of getPurchaseOrdersByVendor of VendorWisePOHandler");
		IVendorWisePOTransaction transaction = new VendorWisePOTransactionImpl();
		List<InvPurchaseOrder> purchaseOrderList = transaction.getPurchaseOrdersByVendor(vendorId);
		log.info("End of getPurchaseOrdersByVendor of VendorWisePOHandler");
		return VendorWisePOHelper.getInstance().convertPurchaseOrderBosToTOS(purchaseOrderList);
	}
	
	/**
	 * Gets purchase order item details by purchase order Id
	 */
	public List<InvPurchaseOrderItemTO> getPurchaseOrderDetailsByID(int purchaseOrderID)throws Exception{
		log.info("start of getPurchaseOrderDetailsByID of VendorWisePOHandler");
		IVendorWisePOTransaction transaction = new VendorWisePOTransactionImpl();
		InvPurchaseOrder order = transaction.getPurchaseOrderDetailsByID(purchaseOrderID);
		log.info("End of getPurchaseOrderDetailsByID of VendorWisePOHandler");
		return VendorWisePOHelper.getInstance().convertPurchaseOrderItemBosToTOS(order);
	}	
	/**
	 * Gets all active purchase order nos
	 */
	public List<InvPurchaseOrderTO>getAllPurchaseOrderNos()throws Exception{
		log.info("start of getAllPurchaseOrderNos of VendorWisePOHandler");
		IVendorWisePOTransaction transaction = new VendorWisePOTransactionImpl();
		List<String> orderNoBOList = transaction.getAllPurchaseOrderNos();
		log.info("End of getAllPurchaseOrderNos of VendorWisePOHandler");
		return VendorWisePOHelper.getInstance().convertPurchaseOrderNo(orderNoBOList);
	}	
	/**
	 * Gets purchase order returns based on the purchaseorder nos.
	 * 
	 */
	public List<InvPurchaseReturnTO> getPurchaseReturnsByPurchaseOrderNo(String purchaseOrderNo)throws Exception{
		log.info("start of getPurchaseReturnsByPurchaseOrderNo of VendorWisePOHandler");
		IVendorWisePOTransaction transaction = new VendorWisePOTransactionImpl();
		List<InvPurchaseReturn> poRetunBOList = transaction.getPurchaseReturnsByPurchaseOrderNo(purchaseOrderNo);
		log.info("End of getPurchaseReturnsByPurchaseOrderNo of VendorWisePOHandler");
		return VendorWisePOHelper.getInstance().covertPurchaseReturnBOToTO(poRetunBOList);
	}
	/**
	 * Gets purchase Return item details by purchase return Id
	 */
	public List<ItemTO> getPurchaseReturnDetailsByID(int purchaseReturnId)throws Exception{
		log.info("start of getPurchaseReturnDetailsByID of VendorWisePOHandler");
		IVendorWisePOTransaction transaction = new VendorWisePOTransactionImpl();
		InvPurchaseReturn purchaseReturn = transaction.getPurchaseReturnDetailsByID(purchaseReturnId);
		log.info("End of getPurchaseReturnDetailsByID of VendorWisePOHandler");
		return VendorWisePOHelper.getInstance().convertPurchaseReturnItemBosToTOS(purchaseReturn);
	}	
}
