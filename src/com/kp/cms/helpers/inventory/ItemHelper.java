package com.kp.cms.helpers.inventory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvItemCategory;
import com.kp.cms.bo.admin.InvItemType;
import com.kp.cms.bo.admin.InvSubCategoryBo;
import com.kp.cms.bo.admin.InvUom;
import com.kp.cms.forms.inventory.ItemForm;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.ItemTO;

public class ItemHelper {
	
	/**
	 * @param item
	 * @return TO
	 * @throws Exception
	 */
	public List<ItemTO> convertBOstoTOs(List<InvItem> item) throws Exception {
	
		List<ItemTO> itemTOList = new ArrayList<ItemTO>();
		InvItem invItem;
		
		if(item != null ){
			ItemTO itemTO = null;
			Iterator<InvItem> iterator = item.iterator();
			while (iterator.hasNext()) {
				itemTO = new ItemTO();
			
				invItem = (InvItem) iterator.next();
				
				itemTO.setId(String.valueOf(invItem.getId()));
				
				StringBuffer buffer = new StringBuffer();
				
				if(invItem.getName() != null ){
					itemTO.setName(invItem.getName());
					buffer.append(invItem.getName());
				}
				if(invItem.getCode() != null){
					buffer.append("("+ invItem.getCode() + ")");
					itemTO.setCode(invItem.getCode());
					itemTO.setNameWithCode(buffer.toString());
				}
				if(invItem.getDescription() != null ){
					itemTO.setDescription(invItem.getDescription());
				}
				if(invItem.getConversion() != null ){
					itemTO.setConversion(invItem.getConversion());
				}
				if(invItem.getDescription() != null ){
					itemTO.setDescription(invItem.getDescription());
				}
				itemTO.setIsWarranty(invItem.getIsWarranty());
				
				if(invItem.getInvItemCategory() !=null){
					itemTO.setItemCategoryId(invItem.getInvItemCategory().getId());
					itemTO.setItemCategoryName(invItem.getInvItemCategory().getName());
				}
				if(invItem.getInvUomByInvPurchaseUomId() !=null){
					itemTO.setPurchaseUomId(invItem.getInvUomByInvPurchaseUomId().getId());
					itemTO.setPurchaseUomName(invItem.getInvUomByInvPurchaseUomId().getName());
				}
				if(invItem.getInvUomByInvIssueUomId() !=null){
					itemTO.setIssueUomId(invItem.getInvUomByInvIssueUomId().getId());
					itemTO.setIssueUomName(invItem.getInvUomByInvIssueUomId().getName());
				}
				if(invItem.getInvItemType() != null ){
					itemTO.setItemType(invItem.getInvItemType().getName());
					itemTO.setItemTypeId(invItem.getInvItemType().getId());
				}
				if(invItem.getInvItemSubCategory()!=null){
				itemTO.setItemSubCategory(invItem.getInvItemSubCategory().getSubCategoryName());
				itemTO.setItemSubCategoryId(invItem.getInvItemSubCategory().getId());
				}
				if(invItem.getRemarks()!=null){
					itemTO.setRemarks(invItem.getRemarks());
				}
				itemTO.setMinStockQuantity(invItem.getMinStockQuantity());
				itemTO.setPurchaseCost(invItem.getPurchaseCost()!=null?invItem.getPurchaseCost():new BigDecimal(0));
				itemTOList.add(itemTO);
			}
		}
		Collections.sort(itemTOList);
		return itemTOList;
	}

	/**
	 * @param itemForm
	 * @param mode
	 * @return InvItem
	 * @throws Exception
	 */
	public InvItem convertBOs(ItemForm itemForm, String mode) throws Exception {
		InvItem invItem = null;
		if(itemForm != null ){
			invItem = new InvItem();
			if( itemForm.getPurchaseUomId() != null){
				InvUom invUom = new InvUom();
				invUom.setId(Integer.parseInt(itemForm.getPurchaseUomId()));
				invItem.setInvUomByInvPurchaseUomId(invUom);
			}
			if( itemForm.getIssueUomId() != null){
				InvUom invUom = new InvUom();
				invUom.setId(Integer.parseInt(itemForm.getIssueUomId()));
				invItem.setInvUomByInvIssueUomId(invUom);
			}
			InvItemCategory itemCategory = new InvItemCategory();
			if( itemForm.getItemCategoryId() !=  null && !itemForm.getItemCategoryId().trim().isEmpty()){
				itemCategory.setId(Integer.parseInt(itemForm.getItemCategoryId()));
				invItem.setInvItemCategory(itemCategory);
			} else invItem.setInvItemCategory(null);
			invItem.setId(itemForm.getId());
			
			invItem.setCode(itemForm.getItemCode().trim());
			invItem.setName(itemForm.getItemName().trim());
			if(itemForm.getItemDescription()!=null){
				invItem.setDescription(itemForm.getItemDescription().trim());
			}
			if(itemForm.getConversion()!=null){
				invItem.setConversion(new BigDecimal(itemForm.getConversion().trim()));
			}
			invItem.setIsWarranty(itemForm.isWarranty());
			
			InvItemType itemType=new InvItemType();
			itemType.setId(Integer.parseInt(itemForm.getItemTypeId()));
			invItem.setInvItemType(itemType);
			
			InvSubCategoryBo subCategory=new InvSubCategoryBo();
			if(itemForm.getItemSubCategoryId()!=null && !itemForm.getItemSubCategoryId().trim().isEmpty()){
			subCategory.setId(Integer.parseInt(itemForm.getItemSubCategoryId()));
			invItem.setInvItemSubCategory(subCategory);
			} else invItem.setInvItemSubCategory(null);
			
			if(itemForm.getMinStockQuantity()>0)
			invItem.setMinStockQuantity(itemForm.getMinStockQuantity());
			
			if(itemForm.getRemarks()!=null && !itemForm.getRemarks().trim().isEmpty())
				invItem.setRemarks(itemForm.getRemarks());
			if(itemForm.getPurchaseCost()!=null && !itemForm.getPurchaseCost().trim().isEmpty()){
				invItem.setPurchaseCost(new BigDecimal(itemForm.getPurchaseCost()));
			}
			
			if (mode.equalsIgnoreCase("Add")) {
				invItem.setCreatedDate(new Date());
				invItem.setCreatedBy(itemForm.getUserId());
			}
			invItem.setLastModifiedDate(new Date());
			invItem.setModifiedBy(itemForm.getUserId());
			invItem.setIsActive(true);
		}
		return invItem;
	}

	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<SingleFieldMasterTO> converttoTo(List<InvItemType> list) throws Exception {
		List<SingleFieldMasterTO> toList=new ArrayList<SingleFieldMasterTO>();
		if(list!=null && !list.isEmpty()){
			Iterator<InvItemType> itr=list.iterator();
			while (itr.hasNext()) {
				InvItemType invItemType = (InvItemType) itr.next();
				SingleFieldMasterTO to=new SingleFieldMasterTO();
				to.setId(invItemType.getId());
				to.setName(invItemType.getName());
				toList.add(to);
			}
		}
		return toList;
	}
}