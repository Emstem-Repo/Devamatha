package com.kp.cms.transactions.fee;

import java.util.List;

import com.kp.cms.bo.admin.FeeBillNumber;
import com.kp.cms.bo.admin.FeeFinancialYear;
/**
 * 
 * @author kolli.ramamohan
 * @version 1.0
 */
public interface IFeeFinancialYearTransaction {
	/** 
	 * Used to add FeeFinancialYear
	 * @param feeFinancialYear
	 * @return
	 * @throws Exception
	 */
	public boolean addFeeFinancialYear(FeeFinancialYear feeFinancialYear) throws Exception;
	/**
	 * Used to update FeeFinancialYear
	 * @param feeFinancialYear
	 * @return
	 * @throws Exception
	 */
	public boolean updateFeeFinancialYear(FeeFinancialYear feeFinancialYear)throws Exception;
	/**
	 * Used to get FeeFinancialYear Details
	 * @return
	 * @throws Exception
	 */
	public List<FeeFinancialYear> getFeeFinancialYearDetails() throws Exception;
	/**
	 * Used to get FeeFinancialYear
	 * @param feeFinancialYear
	 * @return
	 * @throws Exception
	 */
	public FeeFinancialYear getFeeFinancialYear(String feeFinancialYear) throws Exception;
	/**
	 * Used to get FeeFinancialYear with Id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public FeeFinancialYear getFeeFinancialDetailsWithId(int id) throws Exception;
	/**
	 * Used to delete FeeFinancialYear Details
	 * @param id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteFeeFinancialYearDetails(int id, String userId) throws Exception;
	/**
	 * Used to reactivate FeeFinancialYear
	 * @param year
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean reActivateFeeFinancialYear(String year, String userId)throws Exception;	
}
