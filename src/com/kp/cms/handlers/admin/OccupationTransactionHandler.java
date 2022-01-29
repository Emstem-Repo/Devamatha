package com.kp.cms.handlers.admin;

import java.util.List;

import com.kp.cms.bo.admin.Occupation;
import com.kp.cms.helpers.admin.OccupationEntryHelper;
import com.kp.cms.to.admin.OccupationTO;
import com.kp.cms.transactionsimpl.admin.OccupationTransactionImpl;

/**
 * Manages the operations of add,edit, delete of Occupation.
 * @version 1.0 12 Jan 2009
 */
public class OccupationTransactionHandler {
	
	/** Represents OccupationTransactionHandler object.*/
	private static volatile OccupationTransactionHandler occupationTransactionHandler = null;
	
	private OccupationTransactionHandler() {
		// An empty constructor.
	}
	
	/**
	 * Returns the single instance object of OccupationTransactionHandler.
	 * @return OccupationTransactionHandler - OccupationTransactionHandler object.
	 */
	public static OccupationTransactionHandler getInstance() {
		if(occupationTransactionHandler == null) {
			occupationTransactionHandler = new OccupationTransactionHandler();
		}
		return occupationTransactionHandler;
	}
	
	/**
	 * Add Occupation to the database.
	 * @param occupationName - Represents the meritsetName.
	 * @return - true for successful addition, false otherwise.
	 */
	public boolean addOccupation(String occupationName) {
		boolean isOccupationAdded = false;
		OccupationTransactionImpl occupationTransactionImpl = new OccupationTransactionImpl();
		if(occupationTransactionImpl.addOccupation(occupationName)) {
			isOccupationAdded = true;
		}
		return isOccupationAdded;
	}
	
	
	/**
	 * Updates the occupation.
	 * @param occupationId - Occupation id.
	 * @param occupationName - edited occupation name.
	 * @return - true , if the data is updated successfully in the database , false otherwise.
	 */
	public boolean editOccupation(int occupationId,String occupationName) {
		boolean isOccupationedited = false;
		OccupationTransactionImpl occupationTransactionImpl = new OccupationTransactionImpl();
		if(occupationTransactionImpl.editOccupation(occupationId,occupationName)) {
			isOccupationedited = true;
		}
		return isOccupationedited;
	}
	
	
	/**
	 * Get all occupation list from the database.
	 * 
	 * @return List - Occupation transaction List object
	 */
	public List<OccupationTO> getAllOccupation() {
		OccupationTransactionImpl occupationTransactionImpl = new OccupationTransactionImpl();
		List<Occupation> occupationList =	occupationTransactionImpl.getOccupationList();
		List<OccupationTO> occupationToList = OccupationEntryHelper.convertBoToTo(occupationList);
		return occupationToList;		
	}
	
	/**
	 * Deletes the occupation from the database.
	 * @param occupationId - Represents the occupation id to be deleted.
	 * @return - true, if the occupation is deleted successfully, false otherwise.
	 */
	public boolean deleteOccupation(int occupationId) {
		OccupationTransactionImpl occupationTransactionImpl = new OccupationTransactionImpl();
		boolean	isoccupationdeleted =	occupationTransactionImpl.deleteOccupation(occupationId);
		return isoccupationdeleted;		
	}

}
