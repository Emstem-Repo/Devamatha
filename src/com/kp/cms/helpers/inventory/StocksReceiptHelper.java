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

import com.kp.cms.bo.admin.InvAmc;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.bo.admin.InvPurchaseOrder;
import com.kp.cms.bo.admin.InvPurchaseOrderItem;
import com.kp.cms.bo.admin.InvStockReceipt;
import com.kp.cms.bo.admin.InvStockReceiptItem;
import com.kp.cms.forms.inventory.StockReceiptForm;
import com.kp.cms.to.inventory.InvAmcTO;
import com.kp.cms.to.inventory.InvLocationTO;
import com.kp.cms.to.inventory.InvPurchaseOrderTO;
import com.kp.cms.to.inventory.InvStockRecieptItemTo;
import com.kp.cms.transactions.inventory.IPurchaseReturnTransaction;
import com.kp.cms.transactions.inventory.IStocksReceiptTransaction;
import com.kp.cms.transactionsimpl.inventory.PurchaseReturnTransactionImpl;
import com.kp.cms.transactionsimpl.inventory.StocksReceiptTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

/**
 * Helper for Stocks Receipt action
 *
 */
public class StocksReceiptHelper {
private static final Log log = LogFactory.getLog(StocksReceiptHelper.class);
	
	public static volatile StocksReceiptHelper self=null;
	public static StocksReceiptHelper getInstance(){
		if(self==null){
			self= new StocksReceiptHelper();
		}
		return self;
	}
	private StocksReceiptHelper(){
		
	}
	
	/**
	 * @param orderBO
	 * @param returnForm
	 * @return
	 * @throws Exception
	 */
	public InvPurchaseOrderTO convertPurchaseOrderBoToTO(
			InvPurchaseOrder orderBO, StockReceiptForm returnForm) throws Exception {
		InvPurchaseOrderTO orderTO=new InvPurchaseOrderTO();
		if(orderBO!=null){
			orderTO.setId(orderBO.getId());
				if(orderBO.getInvVendor()!=null){
					orderTO.setVendorId(orderBO.getInvVendor().getId());
					orderTO.setVendorName(orderBO.getInvVendor().getName());
					StringBuffer nameAddr=new StringBuffer();
					if(nameAddr!=null){
					if(orderBO.getInvVendor().getAddressLine1()!=null 
							&& !StringUtils.isEmpty(orderBO.getInvVendor().getAddressLine1())){
						
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
					orderTO.setVendorAddr(nameAddr.toString());
				}
			orderTO.setActive(orderBO.getIsActive());
			orderTO.setDeliverySite(orderBO.getDeliverySite());
			orderTO.setTermsandconditions(orderBO.getTermsandconditions());
			orderTO.setRemarks(orderBO.getRemarks());
			orderTO.setOrderDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(orderBO.getOrderDate()), "dd-MMM-yyyy","dd/MM/yyyy"));
				if(orderBO.getInvPurchaseOrderItems()!=null && !orderBO.getInvPurchaseOrderItems().isEmpty()){
					List<InvStockRecieptItemTo> items=this.convertStockReceiptItemBosToTOs(orderBO);
					returnForm.setReceiptItems(items);
				}
			}
		return orderTO;
	}
	
	/**
	 * @param itemBos
	 * @return
	 */
	public  List<InvStockRecieptItemTo> convertStockReceiptItemBosToTOs(InvPurchaseOrder orderBO)throws Exception {
		Set<InvPurchaseOrderItem> itemBos=orderBO.getInvPurchaseOrderItems();
		List<InvStockRecieptItemTo> itemtos=new ArrayList<InvStockRecieptItemTo>();
		IStocksReceiptTransaction txn= new StocksReceiptTransactionImpl();
		if(itemBos!=null){
			int count=1;
			Iterator<InvPurchaseOrderItem> itmItr=itemBos.iterator();
			while (itmItr.hasNext()) {
				InvPurchaseOrderItem item = (InvPurchaseOrderItem) itmItr.next();
				InvStockRecieptItemTo itmTO= new InvStockRecieptItemTo();
				itmTO.setInvItem(item.getInvItem());
				itmTO.setCountId(count);
				if(item.getQuantity()!=null && item.getUnitCost()!=null)
					itmTO.setOrderPrice(String.valueOf(item.getQuantity().doubleValue()* item.getUnitCost().doubleValue()));
				if(item.getInvItem().getIsWarranty()!=null && item.getInvItem().getIsWarranty()){
					itmTO.setWarranty(true);

				}
				if(item.getQuantity()!=null){
				itmTO.setOrderQty(item.getQuantity().intValue());
				}
				if(item.getInvItem().getInvUomByInvPurchaseUomId()!=null)
					itmTO.setUom(item.getInvItem().getInvUomByInvPurchaseUomId().getName());
				
				double alreadyreturnedqty=txn.getAlreadyRecievedQty(itmTO.getInvItem().getId(),orderBO.getId());
				itmTO.setAlreadyRcvQty(alreadyreturnedqty);
				if(itmTO.getInvItem().getInvUomByInvPurchaseUomId()!=null)
					itmTO.setAlreadyRcvUnit(alreadyreturnedqty+itmTO.getInvItem().getInvUomByInvPurchaseUomId().getName());
				else
					itmTO.setAlreadyRcvUnit(String.valueOf(alreadyreturnedqty));
				
				itemtos.add(itmTO);
				count++;
			}
		}
		return itemtos;
	}
	/**
	 * @param returnForm
	 * @return
	 */
	public InvStockReceipt prepareFinalStockReceipt(StockReceiptForm returnForm) {

		InvStockReceipt stReciept= new InvStockReceipt();
		stReciept.setIsActive(true);
		if(returnForm.getPurchaseOrder()!=null){
			InvPurchaseOrder prcOrd= new InvPurchaseOrder();
			prcOrd.setId(returnForm.getPurchaseOrder().getId());
			stReciept.setInvPurchaseOrder(prcOrd);
		}
		if(returnForm.getReceiptDate()!=null && !StringUtils.isEmpty(returnForm.getReceiptDate())
				&& CommonUtil.isValidDate(returnForm.getReceiptDate()))
		stReciept.setReceiptDate(CommonUtil.ConvertStringToSQLDate(returnForm.getReceiptDate()));
		
		if(returnForm.getInventoryId()!=null && !StringUtils.isEmpty(returnForm.getInventoryId())
				&& StringUtils.isNumeric(returnForm.getInventoryId())){
			InvLocation location= new InvLocation();
			location.setId(Integer.parseInt(returnForm.getInventoryId()));
			stReciept.setInvLocation(location);
		}
		
		stReciept.setCreatedBy(returnForm.getUserId());
		stReciept.setCreatedDate(new Date());
		
		// receipt items set
		if(returnForm.getReceiptItems()!=null && !returnForm.getReceiptItems().isEmpty()){
			Set<InvStockReceiptItem> itemBOs= new HashSet<InvStockReceiptItem>();
			Iterator<InvStockRecieptItemTo> itmItr=returnForm.getReceiptItems().iterator();
			while (itmItr.hasNext()) {
				InvStockRecieptItemTo rtnItmTO = (InvStockRecieptItemTo) itmItr.next();
				if(rtnItmTO.getQuantity()!=null && !StringUtils.isEmpty(rtnItmTO.getQuantity())){
					InvStockReceiptItem itemBO= new InvStockReceiptItem();
				itemBO.setCreatedBy(returnForm.getUserId());
				itemBO.setCreatedDate(new Date());
				itemBO.setIsActive(true);
				itemBO.setInvItem(rtnItmTO.getInvItem());
				if(CommonUtil.isValidDecimal(rtnItmTO.getQuantity()))
				itemBO.setQuantity(new BigDecimal(rtnItmTO.getQuantity()));
				
				if(rtnItmTO.getPurchasePrice()!=null && !StringUtils.isEmpty(rtnItmTO.getPurchasePrice())
						&& CommonUtil.isValidDecimal(rtnItmTO.getPurchasePrice()))
				{
					itemBO.setPurchasePrice(new BigDecimal(rtnItmTO.getPurchasePrice()));
					
				}else if(rtnItmTO.getQuantity()!=null && !StringUtils.isEmpty(rtnItmTO.getQuantity())
						&& (rtnItmTO.getPurchasePrice()==null || StringUtils.isEmpty(rtnItmTO.getPurchasePrice())) )
				{
					if(rtnItmTO.getOrderPrice()!=null && !StringUtils.isEmpty(rtnItmTO.getOrderPrice()) && CommonUtil.isValidDecimal(rtnItmTO.getOrderPrice())){
						itemBO.setPurchasePrice(new BigDecimal(rtnItmTO.getOrderPrice()));
					}
				}
				// amcs set
				if(rtnItmTO.getInvAmcs()!=null){
					Set<InvAmc> amcBos= new HashSet<InvAmc>();
					Iterator<InvAmcTO> amcItr=rtnItmTO.getInvAmcs().iterator();
					while (amcItr.hasNext()) {
						InvAmcTO invAmcTO = (InvAmcTO) amcItr.next();
						InvAmc amcBo= new InvAmc();
						amcBo.setIsActive(true);
						amcBo.setWarrantyAmcFlag('W');
						amcBo.setCreatedBy(returnForm.getUserId());
						amcBo.setCreatedDate(new Date());
						if(rtnItmTO.getInvItem()!=null && rtnItmTO.getInvItem().getInvItemCategory()!=null)
						amcBo.setInvItemCategory(rtnItmTO.getInvItem().getInvItemCategory());
						amcBo.setInvItem(rtnItmTO.getInvItem());
						if(invAmcTO.getItemNo()!=null && !StringUtils.isEmpty(invAmcTO.getItemNo())){
							amcBo.setItemNo(invAmcTO.getItemNo());
						}
						if(invAmcTO.getWarrantyStartDate()!=null && !StringUtils.isEmpty(invAmcTO.getWarrantyStartDate())
								&& CommonUtil.isValidDate(invAmcTO.getWarrantyStartDate())){
							amcBo.setWarrantyStartDate(CommonUtil.ConvertStringToSQLDate(invAmcTO.getWarrantyStartDate()));
						}
						
						if(invAmcTO.getWarrantyEndDate()!=null && !StringUtils.isEmpty(invAmcTO.getWarrantyEndDate())
								&& CommonUtil.isValidDate(invAmcTO.getWarrantyEndDate())){
							amcBo.setWarrantyEndDate(CommonUtil.ConvertStringToSQLDate(invAmcTO.getWarrantyEndDate()));
						}
						amcBos.add(amcBo);
					}
					itemBO.setInvAmcs(amcBos);
				}
				
				itemBOs.add(itemBO);
				}
			}
			stReciept.setInvStockReceiptItems(itemBOs);
		}
		return stReciept;
	
	}
	/**
	 * @param invLocation
	 * @return
	 */
	public List<InvLocationTO> copyInventoryLocationBosToTos(
			List<InvLocation> invLocation) throws Exception{
		List<InvLocationTO> locations = new ArrayList<InvLocationTO>();
		if(invLocation!=null){
			
			Iterator<InvLocation> locItr=invLocation.iterator();
			while (locItr.hasNext()) {
				InvLocationTO locationTo = new InvLocationTO();
				InvLocation location = (InvLocation) locItr.next();
				if(location.getIsActive()!=null && location.getIsActive()){
					locationTo.setId(location.getId());
					locationTo.setName(location.getName());
					locations.add(locationTo);
				}
	
			}
		}
		return locations;
	}
}
