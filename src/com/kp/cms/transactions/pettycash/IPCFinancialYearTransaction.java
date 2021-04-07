package com.kp.cms.transactions.pettycash;

import java.util.List;

import com.kp.cms.bo.admin.PcFinancialYear;
/**
 * 
 * @author kolli.ramamohan
 * @version 1.0
 */
public interface IPCFinancialYearTransaction {
	/** 
	 * Used to add PCFinancialYear
	 * @param PCFinancialYear
	 * @return
	 * @throws Exception
	 */
	public boolean addPcFinancialYear(PcFinancialYear pcFinancialYear) throws Exception;
	/**
	 * Used to update PCFinancialYear
	 * @param PCFinancialYear
	 * @return
	 * @throws Exception
	 */
	public boolean updatePcFinancialYear(PcFinancialYear pcFinancialYear)throws Exception;
	/**
	 * Used to get PCFinancialYear Details
	 * @return
	 * @throws Exception
	 */
	public List<PcFinancialYear> getPcFinancialYearDetails() throws Exception;
	/**
	 * Used to get PCFinancialYear
	 * @param PCFinancialYear
	 * @return
	 * @throws Exception
	 */
	public PcFinancialYear getPcFinancialYear(String pcFinancialYear) throws Exception;
	/**
	 * Used to get PCFinancialYear with Id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PcFinancialYear getPcFinancialDetailsWithId(int id) throws Exception;
	/**
	 * Used to delete PCFinancialYear Details
	 * @param id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean deletePcFinancialYearDetails(int id, String userId) throws Exception;
	/**
	 * Used to reactivate PCFinancialYear
	 * @param year
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean reActivatePcFinancialYear(String year, String userId)throws Exception;	
}
