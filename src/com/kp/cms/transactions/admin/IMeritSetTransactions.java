package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.MeritSet;

/**
 * An interface to manage the transactions related to Meritset
 * 
 */
public interface IMeritSetTransactions {

	
	/**
	 * Add merit set object to the database
	 * 
	 * @param meritSetName - Represents the merit set name.
	 * 
	 * @return  - true if the merit set is added successfully, false otherwise.
	 */
	public boolean addMeritSet(String meritSetName);

	/**
	 * Updates the merit set.
	 * @param meritSetId - Merit set id.
	 * @param meritSetName - edited merit set name.
	 * @return - true , if the data is updated successfully in the database , false otherwise.
	 */
	public boolean editMeritSet(int meritSetId,String meritSetName);

	
	/**
	 * Deletes the merit set from the database.
	 * @param meritSetId - Represents the merit set id to be deleted.
	 * @return - true, if the merit set is deleted successfully, false otherwise.
	 */
	public boolean deleteMeritSet(int meritSetId);

	/**
	 * Get all merit set list from the database.
	 * 
	 * @return List - Merit set transaction List object
	 */
	public List<MeritSet> getAllMeritSets();

}
