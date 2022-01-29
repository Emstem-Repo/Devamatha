package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.Occupation;

/**
 * An interface to manage the transactions related to Occupation
 * 
 */
public interface IOccupationTransaction {
	/**
	 * Add occupation object to the database
	 * 
	 * @param occupationName - Represents the occupation name.
	 * 
	 * @return  - true if the occupation is added successfully, false otherwise.
	 */
	public boolean addOccupation(String occupationName);

	/**
	 * Updates the occupation.
	 * @param occupationId - Occupation id.
	 * @param occupationName - Represents the occupation name.
	 * @return - true , if the data is updated successfully in the database , false otherwise.
	 */
	public boolean editOccupation(int occupationId,String occupationName);


	/**
	 * Deletes the selection occupation  from the database.
	 * @param occupationId - Occupation id.
	 * @return - true, if the occupation is deleted successfully, false otherwise.
	 */
	public boolean deleteOccupation(int occupationId);

	/**
	 * Get all occupation list from the database.
	 * 
	 * @return List - Merit set transaction List object
	 */
	public List<Occupation> getOccupationList();
}
