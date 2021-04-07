package com.kp.cms.handlers.inventory;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.InvCampus;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.inventory.InventoryLocationForm;
import com.kp.cms.helpers.inventory.InventoryLocationHelper;
import com.kp.cms.to.inventory.InvCampusTo;
import com.kp.cms.to.inventory.InvLocationTO;
import com.kp.cms.transactions.inventory.IInventoryLocationTransaction;
import com.kp.cms.transactionsimpl.inventory.InventoryLocationTransactionImpl;
import com.kp.cms.utilities.CommonUtil;


public class InventoryLocationHandler {
	private static final Log log = LogFactory.getLog(InventoryLocationTransactionImpl.class);	
	public static volatile InventoryLocationHandler inventoryLocationHandler = null;

	public static InventoryLocationHandler getInstance() {
		if (inventoryLocationHandler == null) {
			inventoryLocationHandler = new InventoryLocationHandler();
		}
		return inventoryLocationHandler;
	}
	
	/**
	 * @return emplMap
	 * @throws Exception
	 */
	public Map<Integer, String> getEmployee() throws Exception {
		log.debug("start getEmployee");
		IInventoryLocationTransaction locationTransaction = InventoryLocationTransactionImpl.getInstance();
		Map<Integer, String> emplMap = null;
		
		List<Employee> employeeBOList = locationTransaction.getEmployee();
		emplMap = InventoryLocationHelper.getInstance().copyEmployeeBoToMap(employeeBOList);
		log.debug("end getEmployee");
		return CommonUtil.sortMapByValue(emplMap);
	}
	
	
	
	
	
	/**
	 * @return locationList
	 * @throws Exception
	 */
	public List<InvLocationTO> getInvLocation() throws Exception {
		log.debug("start getEmployee");
		IInventoryLocationTransaction locationTransaction = InventoryLocationTransactionImpl.getInstance();
		List<InvLocationTO> locationList = null;
		
		List<InvLocation> employeeBOList = locationTransaction.getInventoryLocations();
		locationList = InventoryLocationHelper.getInstance().copyLocationBoToTO(employeeBOList);
		log.debug("end getEmployee");
		return locationList;
	}	
	/**
	 * 
	 * @param educationMasterForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addInventoryLocation(InventoryLocationForm locationForm, String mode) throws Exception {
		log.debug("inside addInventoryLocation");
		IInventoryLocationTransaction locationTransaction = InventoryLocationTransactionImpl.getInstance();
		boolean isAdded = false;
		
		InvLocation invLocation = locationTransaction.isLocationDuplcated(locationForm);  

		if (invLocation != null && invLocation.getIsActive()) {
			throw new DuplicateException();
		}
		else if (invLocation != null && !invLocation.getIsActive())
		{
			locationForm.setDuplId(invLocation.getId());
			throw new ReActivateException();
		}		
				
		invLocation = InventoryLocationHelper.getInstance().copyDataFromFormToBO(locationForm); 
		isAdded = locationTransaction.addInventoryLocation(invLocation, mode); 
		log.debug("leaving addInventoryLocation");
		return isAdded;
	}
	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */

	public boolean deleteLocation(int id, Boolean activate, InventoryLocationForm locationForm) throws Exception {
		IInventoryLocationTransaction inLocationTransaction = InventoryLocationTransactionImpl.getInstance();
		return inLocationTransaction.deleteInvLocation(id, activate, locationForm);
	}

	public List<InvCampusTo> getCampus() throws Exception{	
		IInventoryLocationTransaction locationTransaction = InventoryLocationTransactionImpl.getInstance();
		List<InvCampus> campusList=locationTransaction.getCampus();
		List<InvCampusTo> getCampusList=InventoryLocationHelper.getInstance().convetBosToTos(campusList);
		return getCampusList;
	}		
}
