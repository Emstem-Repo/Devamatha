package com.kp.cms.transactions.phd;

import java.util.List;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.phd.PhdVoucherNumber;
import com.kp.cms.forms.phd.PhdVoucherNumberForm;
/**
 * 
 * @author kolli.ramamohan
 * @version 1.0
 */
public interface IPhdVoucherNumberTransaction {
	/**
	 * Used to get PCFinancialYear Details
	 * @return
	 * @throws Exception
	 */
	public List<PhdVoucherNumber> setDataToToList () throws Exception;
	/** 
	 * Used to add PCFinancialYear
	 * @param errors 
	 * @param phdVoucherNumberForm 
	 * @param PCFinancialYear
	 * @return
	 * @throws Exception
	 */
	public boolean addValuationNumber(PhdVoucherNumber phdVoucherNumber, ActionErrors errors, PhdVoucherNumberForm phdVoucherNumberForm) throws Exception;
	/**
	 * Used to get PCFinancialYear with Id
	 * @param phdVoucherNumberForm
	 * @return
	 * @throws Exception
	 */
	public PhdVoucherNumber getVoucherNumberById(PhdVoucherNumberForm phdVoucherNumberForm) throws Exception;
	/**
	 * Used to get PCFinancialYear
	 * @param PCFinancialYear
	 * @return
	 * @throws Exception
	 */
	public PhdVoucherNumber getPhdFinancialYear(String phdYear) throws Exception;
	/**
	 * Used to update PCFinancialYear
	 * @param PCFinancialYear
	 * @return
	 * @throws Exception
	 */
	public boolean updateVoucherNumber(PhdVoucherNumber phdVoucherNumber)throws Exception;

	/**
	 * Used to delete PCFinancialYear Details
	 * @param id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteVoucherNumber(int id, String userId) throws Exception;
	/**
	 * Used to reactivate PCFinancialYear
	 * @param year
	 * @param userId
	 * @param phdVoucherNumberForm 
	 * @return
	 * @throws Exception
	 */
	public boolean reActivateVoucherNumber(String year, String userId, PhdVoucherNumberForm phdVoucherNumberForm)throws Exception;
	
	/**
	 * @param phdVoucherNumberForm
	 * @return
	 */
	public boolean changeAllToNo(PhdVoucherNumberForm phdVoucherNumberForm)throws Exception;
	/**
	 * @param errors
	 * @param phdVoucherNumberForm
	 * @return
	 * @throws Exception
	 */
	public boolean duplicatecheck(ActionErrors errors,PhdVoucherNumberForm phdVoucherNumberForm) throws Exception;
}
