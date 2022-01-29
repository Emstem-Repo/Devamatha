package com.kp.cms.handlers.admin;

import java.util.List;

import com.kp.cms.bo.admin.MeritSet;
import com.kp.cms.helpers.admin.MeritSetHelper;
import com.kp.cms.to.admin.MeritSetTO;
import com.kp.cms.transactionsimpl.admin.MeritSetTransactionImpl;

/**
 *  A Singleton class to manages the operations of add,edit, delete of merit set.
 *  @version 1.0 12 Jan 2009
 */
public class MeritSetHandler {

	/**
	 *  Represents MeritSetHandler object.
	 */
	private static volatile MeritSetHandler meritSetHandler = null;

	
	private MeritSetHandler() {
		// An empty constructor.
	}
	
	
	/**
	 * Returns the single instance object of merit set handler.
	 * @return MeritSetHandler - MeritSetHandler object.
	 */
	public static MeritSetHandler getInstance() {
		if(meritSetHandler == null) {
			meritSetHandler = new MeritSetHandler();
		}
		return meritSetHandler;
	}

	
	/**
	 * Add Merit set to the database.
	 * @param meritSetName - Represents the meritsetName.
	 * @return - true for successful addition, false otherwise.
	 */
	public boolean addMeritSet(String meritSetName) {
		boolean isMeritsetAdded = false;
		MeritSetTransactionImpl meritSetTransactionImpl = new MeritSetTransactionImpl();
		if(meritSetTransactionImpl.addMeritSet(meritSetName)) {
			isMeritsetAdded = true;
		}
		return isMeritsetAdded;
	}
	
	
	/**
	 * Updates the merit set.
	 * @param meritSetId - Merit set id.
	 * @param meritSetName - edited merit set name.
	 * @return - true , if the data is updated successfully in the database , false otherwise.
	 */
	public boolean editMeritSet(int meritSetId,String meritSetName) {
		boolean isMeritsetedited = false;
		MeritSetTransactionImpl meritSetTransactionImpl = new MeritSetTransactionImpl();
		if(meritSetTransactionImpl.editMeritSet(meritSetId,meritSetName)) {
			isMeritsetedited = true;
		}
		return isMeritsetedited;
	}
	
	
	/**
	 * Get all merit set list from the database.
	 * 
	 * @return List - Merit set transaction List object
	 */
	public List<MeritSetTO> getAllMeritSet() {
		MeritSetTransactionImpl meritSetTransactionImpl = new MeritSetTransactionImpl();
		List<MeritSet> meritSetList =	meritSetTransactionImpl.getAllMeritSets();
		List<MeritSetTO> meritSetToList = MeritSetHelper.convertBoToTo(meritSetList);
		return meritSetToList;		
	}
	
	/**
	 * Deletes the merit set from the database.
	 * @param meritSetId - Represents the merit set id to be deleted.
	 * @return - true, if the merit set is deleted successfully, false otherwise.
	 */
	public boolean deleteMeritSet(int meritSetId) {
		MeritSetTransactionImpl meritSetTransactionImpl = new MeritSetTransactionImpl();
		boolean	ismeritSetdeleted =	meritSetTransactionImpl.deleteMeritSet(meritSetId);
		return ismeritSetdeleted;		
	}

}
