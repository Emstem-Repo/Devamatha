package com.kp.cms.handlers.inventory;

import java.util.List;

import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvItemCategory;
import com.kp.cms.bo.admin.InvItemType;
import com.kp.cms.bo.admin.InvUom;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.inventory.ItemForm;
import com.kp.cms.helpers.admin.SingleFieldMasterHelper;
import com.kp.cms.helpers.inventory.ItemHelper;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.inventory.IItemTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.inventory.ItemTxnImpl;


public class ItemHandler {
	
	private static volatile ItemHandler itemMasterHandler = null;

	private ItemHandler() {
	}

	public static ItemHandler getInstance() {
		if (itemMasterHandler == null) {
			itemMasterHandler = new ItemHandler();
		}
		return itemMasterHandler;
	}

	/**
	 * @return UOMList
	 * @throws Exception
	 */
	public List<SingleFieldMasterTO> getUOM() throws Exception {
		ISingleFieldMasterTransaction singleFieldMasterTransaction = SingleFieldMasterTransactionImpl.getInstance();
		List<SingleFieldMasterTO> uomTOList = null;
		
		List<InvUom> invUom = singleFieldMasterTransaction.getUOMFields();
		uomTOList = SingleFieldMasterHelper.getInstance().copySingleFieldMasterHelper(invUom, "InvUom");
		
		return uomTOList;
	}
	
	/**
	 * @return itemCategoryList
	 * @throws Exception
	 */
	public List<SingleFieldMasterTO> getItemCategory() throws Exception {
		ISingleFieldMasterTransaction singleFieldMasterTransaction = SingleFieldMasterTransactionImpl.getInstance();
		List<SingleFieldMasterTO> itemCategoryTOList = null;
		
		List<InvItemCategory> invUom = singleFieldMasterTransaction.getItemCategory();
		itemCategoryTOList = SingleFieldMasterHelper.getInstance().copySingleFieldMasterHelper(invUom, "InvItemCategory");
		
		return itemCategoryTOList;
	}
	
	/**
	 * @return interviewDefinitionList
	 * @throws Exception
	 */
	public List<ItemTO> getItemList(int id) throws Exception {
		IItemTransaction transaction = new ItemTxnImpl();
		ItemHelper itemMasterHelper = new ItemHelper();

		List<InvItem> itemMasterList = transaction.getItemList(id);

		//to copy the BO properties to TO
		List<ItemTO> itemMasterTOList = itemMasterHelper.convertBOstoTOs(itemMasterList);

		return itemMasterTOList;
	}
	
	/**
	 * @param interviewDefinitionForm
	 * @param mode
	 * @return boolean value, true if added/updated successfully else false
	 * @throws DuplicateException
	 * @throws Exception
	 */
	public boolean addItem(ItemForm itemForm, String mode) throws DuplicateException, Exception {
		IItemTransaction transaction = new ItemTxnImpl();
		ItemHelper itemMasterHelper = new ItemHelper();
		
		boolean originalNotChanged = false;
		String itemCode = itemForm.getItemCode();
		String orginalItemCode = itemForm.getOriginalItemCode();
		
		if (itemCode.equals(orginalItemCode)){ 
			originalNotChanged = true; 
		}
		if (mode.equals("Add")) {
			originalNotChanged = false; // for add no need to check original changed
		}
		
		if (!originalNotChanged) {
			InvItem duplicateInvItem = itemMasterHelper.convertBOs(itemForm, mode);
			duplicateInvItem = transaction.isItemDuplicated(duplicateInvItem);
			if ( duplicateInvItem != null && duplicateInvItem.getIsActive()){
				throw new DuplicateException();
			} else if ( duplicateInvItem != null && !duplicateInvItem.getIsActive()){
				itemForm.setActivationRefId(duplicateInvItem.getId());
				throw new ReActivateException(); 
			}
		}
		 InvItem invItem = itemMasterHelper.convertBOs(itemForm, mode);
		
		return transaction.addItem(invItem, mode, originalNotChanged);
	}
	
	/**
	 * @param id
	 * @param activate
	 * @param userId
	 * @return boolean value, true if deleted successfully else false
	 * @throws Exception
	 */
	public boolean deleteItem(int id, Boolean activate, String userId) throws Exception {
		IItemTransaction transaction = new ItemTxnImpl();
		
		boolean isItemDeleted = false;
		if (transaction != null) {
			isItemDeleted = transaction.deleteItem(id, activate, userId);
		}
		return isItemDeleted;
	}

	/**
	 * @return
	 */
	public List<SingleFieldMasterTO> getItemType() throws Exception {
		IItemTransaction txn=new ItemTxnImpl();
		List<InvItemType> list= txn.getItemTypeList();
		ItemHelper helper=new ItemHelper();
		return helper.converttoTo(list);
	}

	public String getNewEntryId(String newEntryName,String boName) throws Exception {
		IItemTransaction txn=new ItemTxnImpl();
		String id="";
		if(boName!=null && !boName.trim().isEmpty())
		id= txn.getId(newEntryName,boName);
		return id;
	}
}
