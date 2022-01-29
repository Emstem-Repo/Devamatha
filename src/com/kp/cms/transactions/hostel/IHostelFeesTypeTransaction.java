package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlFeeType;
/**
 * 
 * @author kolli.ramamohan
 * @version 1.0
 */
public interface IHostelFeesTypeTransaction {
	/** 
	 * Used to add Hostel FeesType
	 * @param hlFeeType
	 * @return
	 * @throws Exception
	 */
	public boolean addHostelFeesType(HlFeeType hlFeeType) throws Exception;
	/**
	 * Used to update HlFeeType
	 * @param hlFeeType
	 * @return
	 * @throws Exception
	 */
	public boolean updateHostelFeesType(HlFeeType hlFeeType)throws Exception;
	/**
	 * Used to get Hostel Fees Type Details
	 * @return
	 * @throws Exception
	 */
	public List<HlFeeType> getHostelFeesTypeDetails() throws Exception;
	/**
	 * Used to get HlFeeType
	 * @param feeType
	 * @return
	 * @throws Exception
	 */
	public HlFeeType getHostelFeesType(String feeType) throws Exception;
	/**
	 * Used to get HlFeeType with Id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public HlFeeType getHostelFeesTypeDetailsWithId(int id) throws Exception;
	/**
	 * Used to delete HlFeeType Details
	 * @param id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteHostelFeesTypeDetails(int id, String userId) throws Exception;
	/**
	 * Used to reactivate HlFeeType
	 * @param feeType
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean reActivateHostelFeesType(String feeType, String userId)throws Exception;	
}
