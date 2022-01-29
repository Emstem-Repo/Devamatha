package com.kp.cms.transactions.inventory;

import java.util.List;

import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvItemType;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.to.admin.SingleFieldMasterTO;

public interface IItemTransaction {
	
	public List<InvItem> getItemList(int id) throws Exception;
	
	public boolean addItem(InvItem invItem, String mode, Boolean originalChangedNotChanged) throws DuplicateException, Exception;
	
	public boolean deleteItem(int id, Boolean activate, String userId) throws Exception;
	
	public InvItem isItemDuplicated(InvItem oldInvItem) throws Exception;

	public List<InvItemType> getItemTypeList() throws Exception;

	public String getId(String newEntryName,String boName) throws Exception;
}
