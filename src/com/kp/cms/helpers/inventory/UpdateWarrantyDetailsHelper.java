package com.kp.cms.helpers.inventory;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvAmc;
import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvItemCategory;
import com.kp.cms.bo.admin.InvStockReceiptItem;
import com.kp.cms.forms.inventory.StockReceiptForm;
import com.kp.cms.to.inventory.InvAmcTO;
import com.kp.cms.to.inventory.InvStockRecieptItemTo;
import com.kp.cms.utilities.CommonUtil;

public class UpdateWarrantyDetailsHelper {
	private static final Log log = LogFactory.getLog(UpdateWarrantyDetailsHelper.class);
	public static volatile UpdateWarrantyDetailsHelper updateWarrantyDetailsHelper = null;
	
	public static UpdateWarrantyDetailsHelper getInstance() {
		if (updateWarrantyDetailsHelper == null) {
			updateWarrantyDetailsHelper = new UpdateWarrantyDetailsHelper();
			return updateWarrantyDetailsHelper;
		}
		return updateWarrantyDetailsHelper;
	}
	public List<InvStockRecieptItemTo> copyBosToTos(List<InvStockReceiptItem> itemList) {
		Iterator<InvStockReceiptItem> itemItr = itemList.iterator();
		List<InvStockRecieptItemTo>  stockRecieptItemToList = new ArrayList<InvStockRecieptItemTo>();
		InvStockReceiptItem invStockReceiptItem;
		InvStockRecieptItemTo invStockRecieptItemTo;
		InvItem invItem;
		int count = 1;
		while (itemItr.hasNext()){
			invStockRecieptItemTo = new InvStockRecieptItemTo(); 
			invStockReceiptItem = itemItr.next(); 
			if(invStockReceiptItem.getInvStockReceipt()!= null){
				invStockRecieptItemTo.setId(invStockReceiptItem.getId());
			}
			if(invStockReceiptItem.getInvItem()!= null){
				invItem = new InvItem();
				invItem.setId(invStockReceiptItem.getInvItem().getId());
				invItem.setCode(invStockReceiptItem.getInvItem().getCode());
				invItem.setName(invStockReceiptItem.getInvItem().getName());
				invStockRecieptItemTo.setInvItem(invItem);
			}
			if(invStockReceiptItem.getQuantity()!= null){
				invStockRecieptItemTo.setQuantity(invStockReceiptItem.getQuantity().toString());
			}
			if(invStockReceiptItem.getPurchasePrice()!=null){
				invStockRecieptItemTo.setPurchasePrice(invStockReceiptItem.getPurchasePrice().toString());
			}
			if(invStockReceiptItem.getProductNo()!= null){
				invStockRecieptItemTo.setProductNo(invStockReceiptItem.getProductNo());
			}
			invStockRecieptItemTo.setCountId(count);
			if(invStockReceiptItem.getQuantity()!= null){
				invStockRecieptItemTo.setQuantity(Integer.toString(invStockReceiptItem.getQuantity().intValue()));
			}
			if(invStockReceiptItem.getInvItem().getInvItemCategory()!= null){
				invStockRecieptItemTo.setInvItemCategoryId(invStockReceiptItem.getInvItem().getInvItemCategory().getId());
			}
			
			/*
			if(invStockReceiptItem.getQuantity()!= null){
				int noofitems= invStockReceiptItem.getQuantity().intValue();
				List<InvAmcTO> amcs= new ArrayList<InvAmcTO>();
				for(int i=0; i<noofitems;i++){
					InvAmcTO amcTo= new InvAmcTO();
					amcs.add(amcTo);
				}
				invStockRecieptItemTo.setItemAmcs(amcs);
			}
			*/
			count++;
			stockRecieptItemToList.add(invStockRecieptItemTo);		
		}
		
		return stockRecieptItemToList;
		
	}

	/**
	 * copy form values to BO
	 * @param roomForm
	 * @return
	 * @throws Exception
	 */
	public List<InvAmc> populateWarrantyDetails(StockReceiptForm stockReceiptForm) throws Exception {
		log.debug("populateWarrantyDetails");
		List<InvAmc> amcBoList = new ArrayList<InvAmc>();
		Iterator<InvStockRecieptItemTo> iter = stockReceiptForm.getReceiptItems().iterator(); 
		InvItemCategory invItemCategory;
		InvItem invItem;
		InvStockReceiptItem invStockReceiptItem;
		InvAmcTO invAmcTO;
		while(iter.hasNext()){
			invItemCategory = new InvItemCategory();
			invItem = new InvItem();
			invStockReceiptItem = new InvStockReceiptItem(); 

			InvStockRecieptItemTo itemTo = iter.next();
			if(itemTo.getInvAmcs() == null || itemTo.getInvAmcs().size() <= 0){
				continue;
			}
			invItemCategory.setId(itemTo.getInvItemCategoryId());
			invItem.setId(itemTo.getInvItem().getId());
			invStockReceiptItem.setId(itemTo.getId());
			Iterator<InvAmcTO> amcitr = itemTo.getInvAmcs().iterator();
			while(amcitr.hasNext()){
				InvAmc invAmc = new InvAmc();
				invAmcTO = amcitr.next(); 
				invAmc.setInvItemCategory(invItemCategory);
				invAmc.setInvItem(invItem);
				invAmc.setInvStockReceiptItem(invStockReceiptItem);
				if(invAmcTO.getItemNo()!= null && !invAmcTO.getItemNo().trim().isEmpty()){
					invAmc.setItemNo(invAmcTO.getItemNo());
				}
				if(invAmcTO.getWarrantyStartDate()!= null){
					invAmc.setWarrantyStartDate(CommonUtil.ConvertStringToSQLDate(invAmcTO.getWarrantyStartDate()));
				}
				if(invAmcTO.getWarrantyEndDate()!= null){
					invAmc.setWarrantyEndDate(CommonUtil.ConvertStringToSQLDate(invAmcTO.getWarrantyEndDate()));
				}
				invAmc.setWarrantyAmcFlag('W');
				invAmc.setCreatedDate(new Date());
				invAmc.setLastModifiedDate(new Date());
				invAmc.setCreatedBy(stockReceiptForm.getUserId());
				invAmc.setModifiedBy(stockReceiptForm.getUserId());
				invAmc.setIsActive(true);
				amcBoList.add(invAmc);
			}
			
		}
		log.debug("exit populateWarrantyDetails");	
		return amcBoList;
		
	}	
	
	
}
