package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.Caste;
import com.kp.cms.forms.admin.CasteForm;

/**
 * An interface to manage the transactions related to Caste
 * @author prashanth.mh.
 */
public interface ICasteTransaction {
	/**
	 * Add Caste object to the database
	 * @param casteName - Caste name to be added.
	 * @return - true if the merit set is added successfully, false otherwise.
	 */
	public boolean addCaste(Caste caste) throws Exception;

	/**
	 * Get all Caste list from the database.
	 * @return List - Caste transaction List object
	 */
	public List<Caste> getCastes();

	/**
	 * Updates the Caste.
	 * @param casteId - Caste Id to be edited.
	 * @param casteName - Caste Name to be edited.
	 * @return - true , if the data is updated successfully in the database , false otherwise.
	 */
	public boolean updateCaste(Caste caste) throws Exception;


	/**
	 * Deletes the Caste from the database.
	 * @param casteId - Program Id to be deleted.
	 * @return - true, if the merit set is deleted successfully, false
	 *         otherwise.
	 */
	public boolean deleteCaste(int casteId,String userId) throws Exception;

	/**
	 * checking the caste Duplication in the database
	 * @param caste
	 * @return
	 * @throws Exception
	 */
	public Caste isCastDuplcated(Caste caste) throws Exception;

	/**
	 * reactivate the deleted caste
	 * @param caste
	 * @return
	 * @throws Exception
	 */
	public boolean reActivateCaste(Caste caste,String userId) throws Exception;
}
