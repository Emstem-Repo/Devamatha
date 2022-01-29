package com.kp.cms.transactions.inventory;

import java.util.List;

import com.kp.cms.bo.admin.InvItem;

public interface IListofNonWarrantyItemsTxn {
	public List<InvItem> getWarrantyItems(int itemCategoryId) throws Exception;

}
