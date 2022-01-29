package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.ApplicationNumber;
import com.kp.cms.bo.admin.OfflineDetails;

/**
 * 
 * @author kshirod.k
 * A transaction interface for OfflineDetails Entry 
 */

public interface IOfflineDetailsTransaction {
	
	/**
	 * 
	 * @param details
	 * @return
	 * Used in add
	 */
	public boolean addOfflineDetails(OfflineDetails details) throws Exception;
	
	/**
	 * Used in getting max receipt number
	 */
	public int getMaxReceiptNo()throws Exception;
	/**
	 * Gets all the offlineDetails from DB
	 * 
	 */
	public List<OfflineDetails> getAllOfflineDetails()throws Exception;
	/**
	 * Used in delete
	 */
	public boolean deleteOfflineDetails(int id)throws Exception;
	
	/**
	 * Used while edit button is clicked
	 */
	public OfflineDetails getOfflineDetailsbyId(int id)throws Exception;
	/**
	 * Used in update
	 */
	public boolean updateOfflineDetails(OfflineDetails offlineDetails)throws Exception;
	/**
	 * Duplicate check on course and application no.
	 */
	public OfflineDetails getOfflineDetailsonApplNoYear(String applnNo, int year)throws Exception;
	
	/**
	 * Load the data based on year and application year.
	 */
	public OfflineDetails getOfflineDetailsByNoYear(int applnNo, int year)throws Exception;
	
	/**
	 * Check for the entered application no. in the offline application no. range for a course
	 */
	
	public ApplicationNumber checkValidOfflineNumberForCourse(int applicationNo, int year)throws Exception;

}
