package com.kp.cms.helpers.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvRequestItem;
import com.kp.cms.to.inventory.InventoryRequestTO;

public class InventoryRequestHelper {

	/**
	 * @param item
	 * @return TO
	 * @throws Exception
	 */
	public List<InventoryRequestTO> convertBOstoTOs(List<InvRequestItem> inventoryRequestList, List<InvItemStock> availableStockList) throws Exception {
	
		List<InventoryRequestTO> inventoryRequestTOList = new ArrayList<InventoryRequestTO>();
		InvRequestItem inventoryRequest;
		InvItemStock invItemStock;
		Map<Integer, InventoryRequestTO> inventoryRequestMap = new HashMap<Integer, InventoryRequestTO>();
		InventoryRequestTO inventoryRequestTO = null;
		Double difference;
		
		if(inventoryRequestList != null ){
			
			Iterator<InvRequestItem> iterator = inventoryRequestList.iterator();
			
			while (iterator.hasNext()) {
				
				inventoryRequestTO = new InventoryRequestTO();
				inventoryRequest = (InvRequestItem) iterator.next();
				
				if(inventoryRequest.getInvItem() !=null && inventoryRequest.getInvItem().getName()!=null){
					inventoryRequestTO.setItemName(inventoryRequest.getInvItem().getName());
				}
				if(inventoryRequest.getInvRequest()!=null && inventoryRequest.getInvRequest().getRequestedBy()!=null){
					inventoryRequestTO.setRequestedBy(inventoryRequest.getInvRequest().getRequestedBy());
				}
				if(inventoryRequest.getQuantity()!=null){
					inventoryRequestTO.setRequestedQuantity(inventoryRequest.getQuantity().toString());
				}
				inventoryRequestTO.setAvailableQuantity("0");
				inventoryRequestTO.setDifference(inventoryRequest.getQuantity().toString());
				
				inventoryRequestMap.put(inventoryRequest.getInvItem().getId(), inventoryRequestTO);
			}
		}
		
		if(availableStockList != null ){
			Iterator<InvItemStock> iterator = availableStockList.iterator();
			
			while (iterator.hasNext()) {
				
				invItemStock = (InvItemStock) iterator.next();
				
				inventoryRequestTO = inventoryRequestMap.get(invItemStock.getInvItem().getId());
				
				if(inventoryRequestTO != null && invItemStock.getAvailableStock()!=null){
					inventoryRequestTO.setAvailableQuantity(invItemStock.getAvailableStock().toString());
				}
				if(inventoryRequestTO != null && inventoryRequestTO.getAvailableQuantity() != null){
					difference = (Double.valueOf(inventoryRequestTO.getAvailableQuantity()) - Double.valueOf(inventoryRequestTO.getRequestedQuantity()));
					String d=difference.toString();
					if(d.contains("-")){
						d=d.replace("-","");
					}
					inventoryRequestTO.setDifference(d);
				}
				if(inventoryRequestTO != null){
					inventoryRequestMap.put(invItemStock.getInvItem().getId(), inventoryRequestTO);
				}
			}
		}
		inventoryRequestTOList.addAll(inventoryRequestMap.values());
		
		return inventoryRequestTOList;
	}
}
