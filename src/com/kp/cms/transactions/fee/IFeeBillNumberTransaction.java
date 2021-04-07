package com.kp.cms.transactions.fee;

import java.util.List;

import com.kp.cms.bo.admin.FeeBillNumber;

public interface IFeeBillNumberTransaction {
	/**
	 * 
	 * @return
	 * Used getting all fee bill number
	 * @throws Exception
	 */
	public List<FeeBillNumber> getFeeBillNumberDetails()throws Exception;
	
	/**
	 * Used while adding
	 */
	public boolean addFeeBillNumber(FeeBillNumber number)throws Exception;

	/**
	 * Used while deleting
	 */
	
	public boolean deleteFeeBillNumber(int id, String userId) throws Exception;
	
	/**
	 * Check for duplicate on bill no and year
	 */
	
	public FeeBillNumber getFeeBillNoYear(int year)throws Exception;
	
	/**
	 * Used in reactivation
	 */
	public boolean reActivateFeeBillNumber(int year, String userId)throws Exception;
	/**
	 * Used in editing
	 */
	public FeeBillNumber getFeeBillNumberDetailsonId(int id)throws Exception;
	
	/**
	 * Used in updating
	 */
	public boolean updateFeeBillNumber(FeeBillNumber number)throws Exception;
}
