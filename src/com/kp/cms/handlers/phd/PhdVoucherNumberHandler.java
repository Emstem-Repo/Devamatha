package com.kp.cms.handlers.phd;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.phd.PhdVoucherNumber;
import com.kp.cms.forms.phd.PhdVoucherNumberForm;
import com.kp.cms.helpers.phd.PhdVoucherNumberHelper;
import com.kp.cms.to.phd.PhdVoucherNumberTO;
import com.kp.cms.transactions.phd.IPhdVoucherNumberTransaction;
import com.kp.cms.transactionsimpl.phd.PhdVoucherNumberTransactionImpl;
import common.Logger;


/**
 * @author kolli.ramamohan
 * @version 1.0
 */
public class PhdVoucherNumberHandler {

	private static final Logger log = Logger
			.getLogger(PhdVoucherNumberHandler.class);
	private static volatile PhdVoucherNumberHandler phdVoucherNumberHandler = null;

	IPhdVoucherNumberTransaction phdVoucherTransaction = new PhdVoucherNumberTransactionImpl();

	@SuppressWarnings("unused")
	private PhdVoucherNumberHandler() {

	}
	public static PhdVoucherNumberHandler getInstance() {
		if (phdVoucherNumberHandler == null) {
			phdVoucherNumberHandler = new PhdVoucherNumberHandler();
		}
		return phdVoucherNumberHandler;
	}
	/** 
	 * Used to get PCFinancialYear Details	
	 * @param void
	 * @return List<PCFinancialYear>
	 * @throws Exception
	 */
	public List<PhdVoucherNumberTO> setDataToToList() throws Exception {
		log.info("Start of setDataToToList of PhdVoucherNumberHandler");

		if (phdVoucherTransaction != null) {
			List<PhdVoucherNumber> PhdFinancialYearList = phdVoucherTransaction.setDataToToList();
			if (PhdFinancialYearList != null) {
				return PhdVoucherNumberHelper.getInstance().poupulateBOtoTO(PhdFinancialYearList);
			}
		}
		log.info("End of getPCFinancialDetails of PCFinancialYearHandler");
		return new ArrayList<PhdVoucherNumberTO>();
	}
	/** 
	 * Used to add PCFinancialYear	
	 * @param com.kp.cms.forms.PC.PCFinancialYearEntryForm, javax.servlet.http.HttpServletRequest
	 * @return boolean
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public boolean addValuationNumber(PhdVoucherNumberForm phdVoucherNumberForm, ActionErrors errors) throws Exception {
		log.info("Start of addValuationNumber of PhdVoucherNumberHandler");

		PhdVoucherNumber phdVoucherNumber =PhdVoucherNumberHelper.getInstance().populateTOtoBO(phdVoucherNumberForm);
			if (phdVoucherNumber != null && phdVoucherTransaction != null) {
				return phdVoucherTransaction.addValuationNumber(phdVoucherNumber,errors,phdVoucherNumberForm);
			}
		
		log.info("End of addValuationNumber of PhdVoucherNumberHandler");
		return false;
	}
	/** 
	 * Used to get PCFinancialYear with Id	
	 * @param int
	 * @return com.kp.cms.bo.admin.PCFinancialYear
	 * @throws Exception
	 */
	public PhdVoucherNumberTO getVoucherNumberById(PhdVoucherNumberForm phdVoucherNumberForm)throws Exception {
		log.info("Start of getVoucherNumberById of PhdVoucherNumberHandler");
		if (phdVoucherTransaction != null) {
			PhdVoucherNumber phdVoucherNumber = phdVoucherTransaction.getVoucherNumberById(phdVoucherNumberForm);
			if (phdVoucherNumber != null) {
				return PhdVoucherNumberHelper.getInstance().populateBOtoTOEdit(phdVoucherNumber,phdVoucherNumberForm);
			}
		}
		log.info("End of getVoucherNumberById of PhdVoucherNumberHandler");
		return new PhdVoucherNumberTO();
	}
	/** 
	 * Used to get PCFinancialYear	
	 * @param java.lang.String
	 * @return com.kp.cms.bo.admin.PCFinancialYear
	 * @throws Exception
	 */
	public PhdVoucherNumber getPhdFinancialYear(String currentYear)throws Exception {
		log.info("Start of getPhdFinancialYear of PhdVoucherNumberHandler");
		if (phdVoucherTransaction != null) {
			return phdVoucherTransaction.getPhdFinancialYear(currentYear);
		}
		log.info("End of getPhdFinancialYear of PhdVoucherNumberHandler");
		return new PhdVoucherNumber();
	}

	/** 
	 * Used to update PCFinancialYear
	 * @param com.kp.cms.forms.fee.PCFinancialYearEntryForm
	 * @return boolean
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public boolean updateVoucherNumber(PhdVoucherNumberForm phdVoucherNumberForm,ActionErrors errors)
			throws Exception {
		log.info("Start of updateVoucherNumber of PhdVoucherNumberHandler");	
		
		PhdVoucherNumber phdVoucherNumber= PhdVoucherNumberHelper.getInstance().populateTOtoBOUpdate(phdVoucherNumberForm);
			if (phdVoucherTransaction != null && phdVoucherNumber != null) {
				return phdVoucherTransaction.updateVoucherNumber(phdVoucherNumber);
			}
		log.info("End of updateVoucherNumber of PhdVoucherNumberHandler");
		return false;
	}
	
	/** 
	 * Used to delete PCFinancialYear Details
	 * @param int, java.lang.String
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deleteVoucherNumber(int id, String userId)
			throws Exception {
		log.info("Start of deleteVoucherNumber of PhdVoucherNumberHandler");
		if (phdVoucherTransaction != null) {
			return phdVoucherTransaction.deleteVoucherNumber(id, userId);
		}
		log.info("End of deleteVoucherNumber of PhdVoucherNumberHandler");
		return false;
	}
	
	/** 
	 * Used to reactivate PCFinancialYear	
	 * @param phdVoucherNumberForm 
	 * @param java.lang.String, java.lang.String
	 * @return boolean
	 * @throws Exception
	 */
	public boolean reActivateVoucherNumber(String year, String userId, PhdVoucherNumberForm phdVoucherNumberForm)
			throws Exception {
		log.info("Start of reActivateVoucherNumber of PhdVoucherNumberHandler");
		if (phdVoucherTransaction != null) {
			return phdVoucherTransaction.reActivateVoucherNumber(year, userId,phdVoucherNumberForm);
		}
		log.info("End of reActivateVoucherNumber of PhdVoucherNumberHandler");
		return false;
	}
	/**
	 * @param phdVoucherNumberForm
	 */
	public boolean changeAllToNo(PhdVoucherNumberForm phdVoucherNumberForm) throws Exception{
		log.info("Start of reActivateVoucherNumber of PhdVoucherNumberHandler");
		if (phdVoucherTransaction != null) {
			return phdVoucherTransaction.changeAllToNo(phdVoucherNumberForm);
		}
		log.info("End of reActivateVoucherNumber of PhdVoucherNumberHandler");
		return false;
	}
	public boolean duplicatecheck(PhdVoucherNumberForm phdVoucherNumberForm,ActionErrors errors) throws Exception{
		log.info("Start of addValuationNumber of PhdVoucherNumberHandler");
		return phdVoucherTransaction.duplicatecheck(errors,phdVoucherNumberForm);
	}

	
}
