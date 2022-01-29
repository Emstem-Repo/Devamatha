package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.ReligionSection;

/**
 * An interface to manage the transactions related to Religion
 * @author prashanth.mh.
 */
public interface IReligionTransaction {
	/**
	 * Add Religion object to the database
	 * @param programTypeName - Religion name to be added.
	 * @return - true if the merit set is added successfully, false otherwise.
	 */
	public boolean addReligion(Religion religion)throws Exception;

	/**
	 * Get all Religion list from the database.
	 * @return List - Religion transaction List object
	 */
	public List<Religion> getReligion();

	/**
	 * Updates the Religion.
	 * @param programTypeId - Religion Id to be edited.
	 * @param religionName - Religion Name to be edited.
	 * @return - true , if the data is updated successfully in the database , false otherwise.
	 */
	public boolean editReligion(Religion religion)throws Exception;


	/**
	 * Deletes the Religion from the database.
	 * @param religionId - Program Id to be deleted.
	 * @return - true, if the merit set is deleted successfully, false
	 *         otherwise.
	 */
	public boolean deleteReligion(int religionId);
	/**
	 * Searching religion from database.
	 * @param regionname - name to be searched.
	 * @return - true, if its already there, false
	 *         otherwise.
	 */
	
	public boolean existanceCheck(Religion religion) throws Exception;
	
	public List<ReligionSection> getReligionSection()throws Exception;
}
