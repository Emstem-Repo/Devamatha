package com.kp.cms.handlers.inventory;

import java.util.List;

import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.bo.admin.InvRequestItem;
import com.kp.cms.helpers.admin.SingleFieldMasterHelper;
import com.kp.cms.helpers.inventory.InventoryRequestHelper;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.InventoryRequestTO;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.inventory.IInventoryRequestTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.inventory.InventoryRequestTxnImpl;

public class InventoryRequestHandler {

	private static volatile InventoryRequestHandler inventoryRequestHandler = null;

	private InventoryRequestHandler() {
	}

	public static InventoryRequestHandler getInstance() {
		if (inventoryRequestHandler == null) {
			inventoryRequestHandler = new InventoryRequestHandler();
		}
		return inventoryRequestHandler;
	}
	
	/**
	 * @return inventoryLocationList
	 * @throws Exception
	 */
	public List<SingleFieldMasterTO> getInventoryLocation() throws Exception {
		ISingleFieldMasterTransaction singleFieldMasterTransaction = SingleFieldMasterTransactionImpl.getInstance();
		List<SingleFieldMasterTO> iventoryLocationTOList = null;
		
		List<InvLocation> invLocation = singleFieldMasterTransaction.getInventoryLocations();
		iventoryLocationTOList = SingleFieldMasterHelper.getInstance().copySingleFieldMasterHelper(invLocation, "InvLocation");
		
		return iventoryLocationTOList;
	}
	
	/**
	 * 
	 * @param invLocationId
	 * @return
	 * @throws Exception
	 */
	public List<InventoryRequestTO> getInventoryRequestList(int invLocationId) throws Exception {
		IInventoryRequestTransaction inventoryRequestTransaction = new InventoryRequestTxnImpl();
		InventoryRequestHelper inventoryRequestHelper = new InventoryRequestHelper();
		
		List<InvRequestItem> inventoryRequestList = inventoryRequestTransaction.getInventoryRequest(invLocationId);
		List<InvItemStock> availableStockList = inventoryRequestTransaction.getAvailableStocks(invLocationId);
		
		List<InventoryRequestTO> iventoryRequestTOList = inventoryRequestHelper.convertBOstoTOs(inventoryRequestList, availableStockList);
		
		return iventoryRequestTOList;
	}
}
