package com.kp.cms.helpers.inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvPurchaseOrder;
import com.kp.cms.bo.admin.InvPurchaseOrderItem;
import com.kp.cms.bo.admin.InvPurchaseReturn;
import com.kp.cms.bo.admin.InvPurchaseReturnItem;
import com.kp.cms.to.inventory.InvItemTO;
import com.kp.cms.to.inventory.InvPurchaseOrderItemTO;
import com.kp.cms.to.inventory.InvPurchaseOrderTO;
import com.kp.cms.to.inventory.InvPurchaseReturnTO;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.utilities.CommonUtil;

public class VendorWisePOHelper {
	private static final Log log = LogFactory.getLog(VendorWisePOHelper.class);
	public static volatile VendorWisePOHelper vendorWisePOHelper = null;

	private VendorWisePOHelper(){
	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static VendorWisePOHelper getInstance() {
		if (vendorWisePOHelper == null) {
			vendorWisePOHelper = new VendorWisePOHelper();
		}
		return vendorWisePOHelper;
	}
	
	/**
	 * Converts purchase order Bos to TOS
	 */
	public List<InvPurchaseOrderTO>convertPurchaseOrderBosToTOS(List<InvPurchaseOrder> purchaseOrderList)throws Exception{
		log.info("start of convertPurchaseOrderBosToTOS of VendorWisePOHelper");
		List<InvPurchaseOrderTO> purchaseToList = new ArrayList<InvPurchaseOrderTO>();
		InvPurchaseOrderTO purchaseOrderTO = null;
		if(purchaseOrderList!=null && !purchaseOrderList.isEmpty()){
			Iterator<InvPurchaseOrder> purchaseIterator = purchaseOrderList.iterator();
			while (purchaseIterator.hasNext()) {
				InvPurchaseOrder invPurchaseOrder = purchaseIterator.next();
				if(invPurchaseOrder.getOrderNo()!=null){
				purchaseOrderTO = new InvPurchaseOrderTO();
				purchaseOrderTO.setId(invPurchaseOrder.getId());
				if(invPurchaseOrder.getInvVendor()!=null && invPurchaseOrder.getInvVendor().getName()!=null){
					purchaseOrderTO.setVendorName(invPurchaseOrder.getInvVendor().getName());
				}
				if(invPurchaseOrder.getOrderNo()!=null){
					
					purchaseOrderTO.setOrderNo(invPurchaseOrder.getOrderNo());
				}
				if(invPurchaseOrder.getOrderDate()!=null){
					purchaseOrderTO.setOrderDate(CommonUtil.getStringDate(invPurchaseOrder.getOrderDate()));
				}
				if(invPurchaseOrder.getRemarks()!=null){
					purchaseOrderTO.setRemarks(invPurchaseOrder.getRemarks());
				}
				if(invPurchaseOrder.getTermsandconditions()!=null){
					purchaseOrderTO.setTermsandconditions(invPurchaseOrder.getTermsandconditions());
				}
				if(invPurchaseOrder.getDeliverySite()!=null){
					purchaseOrderTO.setDeliverySite(invPurchaseOrder.getDeliverySite());
				}
				if(invPurchaseOrder.getTotalCost()!=null){
					purchaseOrderTO.setTotalCost(String.valueOf(invPurchaseOrder.getTotalCost()));
				}
				if(invPurchaseOrder.getAdditionalCost()!=null){
					purchaseOrderTO.setAdditionalCost(String.valueOf(invPurchaseOrder.getAdditionalCost()));
				}
				purchaseToList.add(purchaseOrderTO);
				}
			}
		}
		log.info("End of convertPurchaseOrderBosToTOS of VendorWisePOHelper");
		return purchaseToList;
	}
	
	/**
	 * Used to convert purchase order item BOS to Tos
	 */
	public List<InvPurchaseOrderItemTO> convertPurchaseOrderItemBosToTOS(InvPurchaseOrder order)throws Exception{
		log.info("start of convertPurchaseOrderItemBosToTOS of VendorWisePOHelper");
		List<InvPurchaseOrderItemTO> itemList = new ArrayList<InvPurchaseOrderItemTO>();
		InvPurchaseOrderItemTO itemTO = null;
		InvItemTO invItem = null;
		if(order!=null){
			if(order.getInvPurchaseOrderItems()!=null && !order.getInvPurchaseOrderItems().isEmpty()){
				Iterator<InvPurchaseOrderItem> iterator = order.getInvPurchaseOrderItems().iterator();
				while (iterator.hasNext()) {
					InvPurchaseOrderItem invPurchaseOrderItem = iterator.next();
					if(invPurchaseOrderItem.getIsActive()){
						itemTO = new InvPurchaseOrderItemTO();
						invItem = new InvItemTO();
						if(invPurchaseOrderItem.getInvItem()!=null){
							StringBuffer buffer = new StringBuffer();
							buffer.append(invPurchaseOrderItem.getInvItem().getName());
							buffer.append("(" + invPurchaseOrderItem.getInvItem().getCode()+")");
							invItem.setNameWithCode(buffer.toString());
						}
						itemTO.setInvItem(invItem);
						if(invPurchaseOrderItem.getQuantity()!=null){
							itemTO.setQuantity(String.valueOf(invPurchaseOrderItem.getQuantity()));
						}
						if(invPurchaseOrderItem.getUnitCost()!=null){
							itemTO.setUnitCost(String.valueOf(invPurchaseOrderItem.getUnitCost()));
						}
						itemList.add(itemTO);
					}					
				}
			}
		}
		log.info("End of convertPurchaseOrderItemBosToTOS of VendorWisePOHelper");
		return itemList;
	}
	/**
	 * Used to converts purchase order no BOS to TO
	 */
	public List<InvPurchaseOrderTO> convertPurchaseOrderNo(List<String> orderNoBOList)throws Exception{
		log.info("start of convertPurchaseOrderNo of VendorWisePOHelper");
		List<InvPurchaseOrderTO> orderNoList = new ArrayList<InvPurchaseOrderTO>();
		InvPurchaseOrderTO invPurchaseOrderTO = null;
		if(orderNoBOList != null && !orderNoBOList.isEmpty()){
			Iterator<String> iterator = orderNoBOList.iterator();
			while (iterator.hasNext()) {
				String orderNo = iterator.next();
				invPurchaseOrderTO = new InvPurchaseOrderTO();
				invPurchaseOrderTO.setOrderNo(orderNo);
				orderNoList.add(invPurchaseOrderTO);
			}
		}
		log.info("End of convertPurchaseOrderNo of VendorWisePOHelper");
		return orderNoList;
	}
	/**
	 * Used to convert purchase return BOs to TOS
	 */
	public List<InvPurchaseReturnTO>covertPurchaseReturnBOToTO(List<InvPurchaseReturn>poRetunBOList)throws Exception{
		log.info("start of covertPurchaseReturnBOToTO of VendorWisePOHelper");
		List<InvPurchaseReturnTO> poReturnList = new ArrayList<InvPurchaseReturnTO>();
		InvPurchaseReturnTO returnTO = null;
		if(poRetunBOList!=null && !poRetunBOList.isEmpty()){
			Iterator<InvPurchaseReturn> iterator = poRetunBOList.iterator();
			while (iterator.hasNext()) {
				InvPurchaseReturn invPurchaseReturn = iterator.next();
				returnTO = new InvPurchaseReturnTO();
				returnTO.setId(invPurchaseReturn.getId());
				if(invPurchaseReturn.getInvPurchaseOrder()!=null && invPurchaseReturn.getInvPurchaseOrder().getOrderNo()!=null){
					returnTO.setPurchaseOrderNo(invPurchaseReturn.getInvPurchaseOrder().getOrderNo());
				}
				if(invPurchaseReturn.getVendorBillNo()!=null){
					returnTO.setVendorBillNo(invPurchaseReturn.getVendorBillNo());
				}
				if(invPurchaseReturn.getVendorBillDate()!=null){
					returnTO.setVenderBillDate(CommonUtil.getStringDate(invPurchaseReturn.getVendorBillDate()));
				}
				if(invPurchaseReturn.getReasonForReturn()!=null){
					returnTO.setReasonForReturn(invPurchaseReturn.getReasonForReturn());
				}
				if(invPurchaseReturn.getPurchaseReturnDate()!=null){
					returnTO.setPurchaseReturnDate(CommonUtil.getStringDate(invPurchaseReturn.getPurchaseReturnDate()));
				}
				if(invPurchaseReturn.getInvLocation()!=null && invPurchaseReturn.getInvLocation().getName()!=null){
					returnTO.setInvLocationName(invPurchaseReturn.getInvLocation().getName());
				}
				if(invPurchaseReturn.getInvPurchaseOrder()!=null && 
				invPurchaseReturn.getInvPurchaseOrder().getInvVendor()!=null && 
				invPurchaseReturn.getInvPurchaseOrder().getInvVendor().getName()!=null){
					returnTO.setVendorName(invPurchaseReturn.getInvPurchaseOrder().getInvVendor().getName());
				}
				poReturnList.add(returnTO);
			}
		}
		log.info("End of covertPurchaseReturnBOToTO of VendorWisePOHelper");
		return poReturnList;
	}
	
	/**
	 * Used to conver purchase return item BOs to TOS
	 */
	public List<ItemTO>convertPurchaseReturnItemBosToTOS(InvPurchaseReturn purchaseReturn)throws Exception{
		log.info("start of convertPurchaseReturnItemBosToTOS of VendorWisePOHelper");
		List<ItemTO> itemList = new ArrayList<ItemTO>();
		ItemTO itemTO = null;
		if(purchaseReturn != null && purchaseReturn.getInvPurchaseReturnItems()!=null){
			Iterator<InvPurchaseReturnItem> iterator = purchaseReturn.getInvPurchaseReturnItems().iterator();
			while (iterator.hasNext()) {
				InvPurchaseReturnItem returnItem = iterator.next();
				if(returnItem.getInvItem()!=null){
					itemTO = new ItemTO();
					StringBuffer buffer = new StringBuffer();
					if(returnItem.getInvItem().getName()!=null){
						buffer.append(returnItem.getInvItem().getName());
					}
					if(returnItem.getInvItem().getCode()!=null){
						buffer.append("(" + returnItem.getInvItem().getCode() + ")");
					}
					itemTO.setNameWithCode(buffer.toString());
					if(returnItem.getQuantity()!=null){
						itemTO.setQuantityIssued(String.valueOf(returnItem.getQuantity()));
					}
					itemList.add(itemTO);
				}
			}
		}
		log.info("End of convertPurchaseReturnItemBosToTOS of VendorWisePOHelper");
		return itemList;
	}
}
