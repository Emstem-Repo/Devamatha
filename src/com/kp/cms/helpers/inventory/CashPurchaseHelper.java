package com.kp.cms.helpers.inventory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.InvCashPurchase;
import com.kp.cms.bo.admin.InvCashPurchaseItem;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.bo.admin.InvVendor;
import com.kp.cms.forms.inventory.CashPurchaseForm;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.utilities.CommonUtil;

public class CashPurchaseHelper {

	/**
	 * 
	 * @param transferItemList
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public List<InvCashPurchase> convertTOtoBO(List<ItemTO> transferItemList,String user)throws Exception{
		List<InvCashPurchase> cashPurchaseList = new ArrayList<InvCashPurchase>();
		Set<InvCashPurchaseItem> cashPurchaseSet = new HashSet<InvCashPurchaseItem>();
		InvCashPurchaseItem invCashPurchaseItem;
		InvCashPurchase invCashPurchase;
		InvLocation invLocation;
		ItemTO itemTO;
		Iterator<ItemTO> itr = transferItemList.iterator();
		while (itr.hasNext()) {
			itemTO = (ItemTO) itr.next();
			
			invCashPurchaseItem = new InvCashPurchaseItem();
			if(itemTO.getDescription()!=null && !itemTO.getDescription().isEmpty()){
				invCashPurchaseItem.setComments(itemTO.getDescription());
			}
			invCashPurchaseItem.setCreatedBy(user);
			invCashPurchaseItem.setCreatedDate(new Date());
			invCashPurchaseItem.setIsActive(true);
			invCashPurchaseItem.setLastModifiedDate(new Date());
			invCashPurchaseItem.setModifiedBy(user);
			invCashPurchaseItem.setItemName(itemTO.getName());
			invCashPurchaseItem.setPurchasePrice(itemTO.getPurchaseCost());
			invCashPurchaseItem.setQuantity(new BigDecimal(itemTO.getQuantityIssued()));
			cashPurchaseSet.add(invCashPurchaseItem);
			
			invLocation = new InvLocation();
			invCashPurchase = new InvCashPurchase();
//			invCashPurchase.setCashPurchaseDate(new Date());
			invCashPurchase.setCreatedBy(user);
			invCashPurchase.setCreatedDate(new Date());
			invLocation.setId(Integer.parseInt(itemTO.getInvLocationId()));
			invCashPurchase.setInvLocation(invLocation);
			invCashPurchase.setIsActive(true);
			invCashPurchase.setLastModifiedDate(new Date());
			invCashPurchase.setModifiedBy(user);
			if(itemTO.getVendorId()!=null && !itemTO.getVendorId().isEmpty() && !itemTO.getVendorId().equals("Other")){
				InvVendor vendor = new InvVendor();
				vendor.setId(Integer.parseInt(itemTO.getVendorId()));
				invCashPurchase.setInvVendor(vendor);
			}else{
				invCashPurchase.setVendorName(itemTO.getVendorName());
			}
		//	invCashPurchase.setVendorName(itemTO.getVendorName());
			invCashPurchase.setCashPurchaseDate(CommonUtil.ConvertStringToSQLDate(itemTO.getPurchaseDate()));
			invCashPurchase.setInvCashPurchaseItems(cashPurchaseSet);
			
			cashPurchaseList.add(invCashPurchase);
		}
		return cashPurchaseList;
	}
	
	/**
	 * 
	 * @param cashPurchaseForm
	 * @return
	 * @throws Exception
	 */
	public InvCashPurchase convertTOtoBO(CashPurchaseForm cashPurchaseForm)throws Exception{
		InvCashPurchaseItem invCashPurchaseItem;
		InvCashPurchase invCashPurchase;
		InvLocation invLocation;
		Set<InvCashPurchaseItem> cashPurchaseSet = new HashSet<InvCashPurchaseItem>();
			
			invCashPurchaseItem = new InvCashPurchaseItem();
			if(cashPurchaseForm.getComments()!=null && !cashPurchaseForm.getComments().isEmpty()){
				invCashPurchaseItem.setComments(cashPurchaseForm.getComments());
			}
			invCashPurchaseItem.setCreatedBy(cashPurchaseForm.getUserId());
			invCashPurchaseItem.setCreatedDate(new Date());
			invCashPurchaseItem.setIsActive(true);
			invCashPurchaseItem.setLastModifiedDate(new Date());
			invCashPurchaseItem.setModifiedBy(cashPurchaseForm.getUserId());
			invCashPurchaseItem.setPurchasePrice(new BigDecimal(cashPurchaseForm.getPurchasePrice()));
			invCashPurchaseItem.setQuantity(new BigDecimal(cashPurchaseForm.getQuantity()));
			invCashPurchaseItem.setItemName(cashPurchaseForm.getItemName());
			cashPurchaseSet.add(invCashPurchaseItem);
			
			invLocation = new InvLocation();
			invCashPurchase = new InvCashPurchase();
//			invCashPurchase.setCashPurchaseDate(new Date());
			invCashPurchase.setCreatedBy(cashPurchaseForm.getUserId());
			invCashPurchase.setCreatedDate(new Date());
			invLocation.setId(Integer.parseInt(cashPurchaseForm.getInvLocationId()));
			invCashPurchase.setInvLocation(invLocation);
			invCashPurchase.setIsActive(true);
			invCashPurchase.setLastModifiedDate(new Date());
			invCashPurchase.setModifiedBy(cashPurchaseForm.getUserId());
			invCashPurchase.setVendorName(cashPurchaseForm.getVendorName());
			invCashPurchase.setCashPurchaseDate(CommonUtil.ConvertStringToSQLDate(cashPurchaseForm.getDate()));
			invCashPurchase.setInvCashPurchaseItems(cashPurchaseSet);
			
		return invCashPurchase;
	}
}