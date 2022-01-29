package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.Recommendor;

public interface IRecommendedByTransaction {

	/**
	 * 
	 * @param Used for adding a recommendedBy
	 * @return
	 * @throws Exception
	 */
	public boolean addRecommendedBy(Recommendor recommendor)throws Exception;
	
	/**
	 * 
	 * @return Used for getting all the recommendedBy where isActive=1
	 * @throws Exception
	 */
	
	public List<Recommendor> getRecommendedByDetails()throws Exception;
	
	/**
	 * 
	 * @param id
	 * @return Deletes a recommendedBy
	 * @throws Exception
	 */
	
	public boolean deleteRecommendedBy(int id, String userId)throws Exception;
	
	/**
	 * 
	 * @param id
	 * @return updates a recommendedBy
	 * @throws Exception
	 */
	
	public boolean updateRecommendedBy(Recommendor recommendor)throws Exception;
	
	/**
	 * 
	 * @param id
	 * @return Check for duplicate record (If recommendedBy exists based on the code)
	 * @throws Exception
	 */
	
	public Recommendor checkForDuplicateonCode(String code)throws Exception;
	
	/**
	 * 
	 * @param id
	 * @return Used for reactivating a recommendedBy (If user want then make isActive=1)
	 * @throws Exception
	 */
	
	public boolean reActivateRecommendedBy(String code, String userId)throws Exception;
	
	/**
	 * Returns a row based on the id
	 */
	public Recommendor getDetailsonId(int id)throws Exception ;
}
