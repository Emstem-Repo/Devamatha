package com.kp.cms.helpers.inventory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvPurchaseOrder;
import com.kp.cms.bo.admin.InvPurchaseOrderItem;
import com.kp.cms.bo.admin.InvPurchaseReturn;
import com.kp.cms.bo.admin.InvPurchaseReturnItem;
import com.kp.cms.bo.admin.InvStockReceipt;
import com.kp.cms.bo.admin.InvStockReceiptItem;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.inventory.PurchaseReturnForm;
import com.kp.cms.helpers.admission.AdmissionFormHelper;
import com.kp.cms.to.inventory.InvItemTO;
import com.kp.cms.to.inventory.InvPurchaseOrderItemTO;
import com.kp.cms.to.inventory.InvPurchaseOrderTO;
import com.kp.cms.to.inventory.InvPurchaseReturnItemTO;
import com.kp.cms.to.inventory.InvTxTO;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.transactions.inventory.IPurchaseReturnTransaction;
import com.kp.cms.transactionsimpl.inventory.PurchaseReturnTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class PurchaseReturnHelper {
	private static final Log log = LogFactory.getLog(PurchaseReturnHelper.class);
	
	public static volatile PurchaseReturnHelper self=null;
	public static PurchaseReturnHelper getInstance(){
		if(self==null){
			self= new PurchaseReturnHelper();
		}
		return self;
	}
	private PurchaseReturnHelper(){
		
	}
	public InvPurchaseOrderTO convertPurchaseOrderBoToTO(
			InvPurchaseOrder orderBO, PurchaseReturnForm returnForm) throws Exception {
		InvPurchaseOrderTO orderTO=new InvPurchaseOrderTO();
		if(orderBO!=null){
			orderTO.setId(orderBO.getId());
				if(orderBO.getInvVendor()!=null){
					orderTO.setVendorId(orderBO.getInvVendor().getId());
					StringBuffer nameAddr=new StringBuffer(orderBO.getInvVendor().getName());
					if(nameAddr!=null){
					if(orderBO.getInvVendor().getAddressLine1()!=null 
							&& !StringUtils.isEmpty(orderBO.getInvVendor().getAddressLine1())){
						nameAddr.append(",");
						nameAddr.append(orderBO.getInvVendor().getAddressLine1());
					}
					if(orderBO.getInvVendor().getAddressLine2()!=null 
							&& !StringUtils.isEmpty(orderBO.getInvVendor().getAddressLine2())){
						nameAddr.append(",");
						nameAddr.append(orderBO.getInvVendor().getAddressLine2());
					}
					if(orderBO.getInvVendor().getCity()!=null 
						&& !StringUtils.isEmpty(orderBO.getInvVendor().getCity()))
						nameAddr.append(",");
						nameAddr.append(orderBO.getInvVendor().getCity());
					}
					if(orderBO.getInvVendor().getState()!=null 
							&& !StringUtils.isEmpty(orderBO.getInvVendor().getState().getName())){
							nameAddr.append(",");
							nameAddr.append(orderBO.getInvVendor().getState().getName());
					}
					if(orderBO.getInvVendor().getCountry()!=null 
							&& !StringUtils.isEmpty(orderBO.getInvVendor().getCountry().getName())){
							nameAddr.append(",");
							nameAddr.append(orderBO.getInvVendor().getCountry().getName());
					}
					if(orderBO.getInvVendor().getZipCode()!=0){
							nameAddr.append(",");
							nameAddr.append(orderBO.getInvVendor().getZipCode());
					}
					orderTO.setVendorName(nameAddr.toString());
				}
			orderTO.setActive(orderBO.getIsActive());
			orderTO.setDeliverySite(orderBO.getDeliverySite());
			orderTO.setTermsandconditions(orderBO.getTermsandconditions());
			orderTO.setRemarks(orderBO.getRemarks());
			orderTO.setOrderDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(orderBO.getOrderDate()), "dd-MMM-yyyy","dd/MM/yyyy"));
				if(orderBO.getInvStockReceipts()!=null && !orderBO.getInvStockReceipts().isEmpty()){
					List<InvPurchaseReturnItemTO> items=this.convertPurchaseOrderItemBosToTOs(orderBO);
					returnForm.setReturnItems(items);
				}
			}
		return orderTO;
	}
	
	/**
	 * @param orderBO
	 * @return
	 */
	public static List<InvPurchaseReturnItemTO> convertPurchaseOrderItemBosToTOs(InvPurchaseOrder orderBO) throws Exception {
		List<InvPurchaseReturnItemTO> itemtos=new ArrayList<InvPurchaseReturnItemTO>();
		IPurchaseReturnTransaction txn= new PurchaseReturnTransactionImpl();
		List<Integer> itemIds= new ArrayList<Integer>();
		if(orderBO.getInvStockReceipts()!=null){
			Iterator<InvStockReceipt> itmItr=orderBO.getInvStockReceipts().iterator();
			while (itmItr.hasNext()) {
				InvStockReceipt item = (InvStockReceipt) itmItr.next();
				if(item.getInvStockReceiptItems()!=null){
					Iterator<InvStockReceiptItem> ritmItr=item.getInvStockReceiptItems().iterator();
					while (ritmItr.hasNext()) {
						InvStockReceiptItem stockItem = (InvStockReceiptItem) ritmItr.next();
						// if old product
						if(itemIds.contains(stockItem.getInvItem().getId())){
							if(itemtos!=null){
								Iterator<InvPurchaseReturnItemTO> retItr=itemtos.iterator();
								while (retItr.hasNext()) {
									InvPurchaseReturnItemTO returnItmTO = (InvPurchaseReturnItemTO) retItr.next();
									returnItmTO.setStockReciept(item);
									if(returnItmTO.getInvItemId()!=null 
											&& returnItmTO.getInvItemId().getId()==stockItem.getInvItem().getId()){
										//add qty to existing value
										if(stockItem.getInvItem()!=null && stockItem.getInvItem().getInvUomByInvPurchaseUomId()!=null){
											double newvalue=stockItem.getQuantity().doubleValue();
											double oldvalue=returnItmTO.getReceivedQuantity();
											double total=newvalue+oldvalue;
											returnItmTO.setRecievedQty(total+stockItem.getInvItem().getInvUomByInvPurchaseUomId().getName());
										}else{
											returnItmTO.setRecievedQty(String.valueOf(stockItem.getQuantity()));
										}
										if(stockItem.getQuantity()!=null)
											returnItmTO.setReceivedQuantity(returnItmTO.getReceivedQuantity()+stockItem.getQuantity().doubleValue());
										double alreadyreturnedqty=txn.getAlreadyReturnedQty(returnItmTO.getInvItemId().getId(),orderBO.getId());
										returnItmTO.setAlreadyRtndQty(alreadyreturnedqty);
										if(returnItmTO.getInvItemId().getInvUomByInvPurchaseUomId()!=null)
											returnItmTO.setAlreadyRtndUnits(alreadyreturnedqty+returnItmTO.getInvItemId().getInvUomByInvPurchaseUomId().getName());
										else
											returnItmTO.setAlreadyRtndUnits(String.valueOf(alreadyreturnedqty));
									}
								}
								
							}
							
							
						}else{
							InvPurchaseReturnItemTO itmTO= new InvPurchaseReturnItemTO();
							itmTO.setInvItemId(stockItem.getInvItem());
							itmTO.setStockReciept(item);
							itemIds.add(stockItem.getInvItem().getId());
							if(stockItem.getInvItem()!=null && stockItem.getInvItem().getInvUomByInvPurchaseUomId()!=null){
								itmTO.setRecievedQty(String.valueOf(stockItem.getQuantity())+stockItem.getInvItem().getInvUomByInvPurchaseUomId().getName());
							}else{
							itmTO.setRecievedQty(String.valueOf(stockItem.getQuantity()));
							}
							if(stockItem.getQuantity()!=null)
							itmTO.setReceivedQuantity(stockItem.getQuantity().doubleValue());
							double alreadyreturnedqty=txn.getAlreadyReturnedQty(itmTO.getInvItemId().getId(),orderBO.getId());
							itmTO.setAlreadyRtndQty(alreadyreturnedqty);
							if(itmTO.getInvItemId().getInvUomByInvPurchaseUomId()!=null)
								itmTO.setAlreadyRtndUnits(alreadyreturnedqty+itmTO.getInvItemId().getInvUomByInvPurchaseUomId().getName());
							else
								itmTO.setAlreadyRtndUnits(String.valueOf(alreadyreturnedqty));
							itemtos.add(itmTO);
						}
					}
				}
				
				
				
				
				
				
				
			}
		}
		return itemtos;
	}
	/**
	 * prepares final purchase return final object graph
	 * @param returnForm
	 * @return
	 * @throws Exception
	 */
	public InvPurchaseReturn prepareFinalPurchaseReturn(
			PurchaseReturnForm returnForm) throws Exception {
		InvPurchaseReturn prcReturn= new InvPurchaseReturn();
		prcReturn.setIsActive(true);
		if(returnForm.getPurchaseOrder()!=null){
			InvPurchaseOrder prcOrd= new InvPurchaseOrder();
			prcOrd.setId(returnForm.getPurchaseOrder().getId());
			prcReturn.setInvPurchaseOrder(prcOrd);
		}
		prcReturn.setReasonForReturn(returnForm.getReturnReason());
		if(returnForm.getVendorBillDt()!=null && !StringUtils.isEmpty(returnForm.getVendorBillDt())
				&& CommonUtil.isValidDate(returnForm.getVendorBillDt()))
		prcReturn.setVendorBillDate(CommonUtil.ConvertStringToSQLDate(returnForm.getVendorBillDt()));
		
		prcReturn.setVendorBillNo(returnForm.getVendorBillNo());
		if(returnForm.getPurchaseRtnDate()!=null && !StringUtils.isEmpty(returnForm.getPurchaseRtnDate())
				&& CommonUtil.isValidDate(returnForm.getPurchaseRtnDate()))
		prcReturn.setPurchaseReturnDate(CommonUtil.ConvertStringToSQLDate(returnForm.getPurchaseRtnDate()));
		prcReturn.setCreatedBy(returnForm.getUserId());
		prcReturn.setCreatedDate(new Date());
		if(returnForm.getReturnItems()!=null && !returnForm.getReturnItems().isEmpty()){
			Set<InvPurchaseReturnItem> itemBOs= new HashSet<InvPurchaseReturnItem>();
			Iterator<InvPurchaseReturnItemTO> itmItr=returnForm.getReturnItems().iterator();
			while (itmItr.hasNext()) {
				InvPurchaseReturnItemTO rtnItmTO = (InvPurchaseReturnItemTO) itmItr.next();
				if(rtnItmTO.getQuantity()!=null && !StringUtils.isEmpty(rtnItmTO.getQuantity())){
				InvPurchaseReturnItem itemBO= new InvPurchaseReturnItem();
				itemBO.setCreatedBy(returnForm.getUserId());
				itemBO.setCreatedDate(new Date());
				itemBO.setIsActive(true);
				itemBO.setInvItem(rtnItmTO.getInvItemId());
				if(CommonUtil.isValidDecimal(rtnItmTO.getQuantity()))
				itemBO.setQuantity(new BigDecimal(rtnItmTO.getQuantity()));
				if(rtnItmTO.getStockReciept()!=null && rtnItmTO.getStockReciept().getInvLocation()!=null)
					prcReturn.setInvLocation(rtnItmTO.getStockReciept().getInvLocation());
				itemBOs.add(itemBO);
				}
			}
			prcReturn.setInvPurchaseReturnItems(itemBOs);
		}
		return prcReturn;
	}
	/**
	 * reduces the item stock which has returned
	 * @param returnForm
	 * @return
	 */
	public Set<InvItemStock> prepareFinalItemStocks(PurchaseReturnForm returnForm) throws Exception 
	{
		List<InvTxTO> transactions= new ArrayList<InvTxTO>();
		Set<InvItemStock> itemstocks=null;
		if(returnForm.getReturnItems()!=null && !returnForm.getReturnItems().isEmpty())
		{
			Iterator<InvPurchaseReturnItemTO> itmItr=returnForm.getReturnItems().iterator();
			while (itmItr.hasNext()) {
				InvPurchaseReturnItemTO rtnItmTO = (InvPurchaseReturnItemTO) itmItr.next();
				if(rtnItmTO.getStockReciept()!=null && rtnItmTO.getStockReciept().getInvLocation()!=null && rtnItmTO.getStockReciept().getInvLocation().getInvItemStocks().size()!=0)
				{
					itemstocks=rtnItmTO.getStockReciept().getInvLocation().getInvItemStocks();
					Iterator<InvItemStock> itemstkItr=itemstocks.iterator();
					while (itemstkItr.hasNext()) 
					{
						InvItemStock itemStock = (InvItemStock) itemstkItr.next();
						// 	reduce the item stock
						if(itemStock.getInvItem()!=null && rtnItmTO.getInvItemId()!=null && itemStock.getInvItem().getId()==rtnItmTO.getInvItemId().getId())
						{
							InvTxTO txnTo= new InvTxTO();
							txnTo.setInvItem(itemStock.getInvItem());
							txnTo.setInvLocation(itemStock.getInvLocation());
							txnTo.setTxType(CMSConstants.RETURN_TX_TYPE);
							txnTo.setTxDate(new Date());
							txnTo.setCreatedBy(returnForm.getUserId());
							txnTo.setCreatedDate(new Date());
							if(rtnItmTO.getQuantity()!=null && !StringUtils.isEmpty(rtnItmTO.getQuantity()) && CommonUtil.isValidDecimal(rtnItmTO.getQuantity()))
							{
								if(itemStock.getInvItem().getConversion()!=null)
								{
									double available=itemStock.getAvailableStock().doubleValue();
									double toreturn=Double.parseDouble(rtnItmTO.getQuantity())*itemStock.getInvItem().getConversion().doubleValue();
									double finalNo=available-toreturn;
									txnTo.setOpeningBalance(available);
									txnTo.setClosingBalance(finalNo);
									txnTo.setQuantity(Double.parseDouble(rtnItmTO.getQuantity()));
									itemStock.setAvailableStock(new BigDecimal(finalNo));
								}
								else
								{
									double available=itemStock.getAvailableStock().doubleValue();
									double toreturn=Double.parseDouble(rtnItmTO.getQuantity());
									double finalNo=available-toreturn;
									txnTo.setOpeningBalance(available);
									txnTo.setClosingBalance(finalNo);
									txnTo.setQuantity(toreturn);
									itemStock.setAvailableStock(new BigDecimal(finalNo));
								}
							}
							transactions.add(txnTo);
						}
						itemStock.setModifiedBy(returnForm.getUserId());
						itemStock.setLastModifiedDate(new Date());
					}
				}
			}
		}
		returnForm.setTransactions(transactions);
		return itemstocks;
	
	}
}
