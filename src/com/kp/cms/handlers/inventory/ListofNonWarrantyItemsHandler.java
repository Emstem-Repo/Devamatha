package com.kp.cms.handlers.inventory;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.helpers.inventory.ItemHelper;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.transactions.inventory.IListofNonWarrantyItemsTxn;
import com.kp.cms.transactionsimpl.inventory.ListofNonWarrantyItemsTxnImpl;

public class ListofNonWarrantyItemsHandler {
	private static Log log = LogFactory.getLog(ListofNonWarrantyItemsHandler.class);
	
	public static volatile ListofNonWarrantyItemsHandler listofNonWarrantyItemsHandler = null;
	
	public static ListofNonWarrantyItemsHandler getInstance() {
		if (listofNonWarrantyItemsHandler == null) {
			listofNonWarrantyItemsHandler = new ListofNonWarrantyItemsHandler();
			return listofNonWarrantyItemsHandler;
		}
		return listofNonWarrantyItemsHandler;
	}
	
	/** Warranty items
	 * @return list of ItemTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<ItemTO> getNonWarrantyDetails(int itemCategId) throws Exception {
		log.debug("inside getWarrantyDetails");
		IListofNonWarrantyItemsTxn iLTxn = ListofNonWarrantyItemsTxnImpl.getInstance();
		List<InvItem> itemList = iLTxn.getWarrantyItems(itemCategId);
		ItemHelper itemHelper = new ItemHelper();
		List<ItemTO> InvItemTOList = itemHelper.convertBOstoTOs(itemList);
		log.debug("leaving getWarrantyDetails");
		return InvItemTOList;
	}	
	
	
}
