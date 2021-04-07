package com.kp.cms.handlers.fee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.FeeFinancialYear;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.fee.FeeFinancialYearEntryForm;
import com.kp.cms.helpers.fee.FeeFinancialYearHelper;
import com.kp.cms.to.fee.FeeFinancialYearTO;
import com.kp.cms.transactions.fee.IFeeFinancialYearTransaction;
import com.kp.cms.transactionsimpl.fee.FeeFinancialYearTransactionImpl;
import common.Logger;


/**
 * @author kolli.ramamohan
 * @version 1.0
 */
public class FeeFinancialYearHandler {

	private static final Logger log = Logger
			.getLogger(FeeFinancialYearHandler.class);
	private static FeeFinancialYearHandler feeFinancialYearHandler = null;

	IFeeFinancialYearTransaction iFeeFinancialYearTransaction = new FeeFinancialYearTransactionImpl();

	@SuppressWarnings("unused")
	private FeeFinancialYearHandler() {

	}
	public static FeeFinancialYearHandler getInstance() {
		if (feeFinancialYearHandler == null) {
			feeFinancialYearHandler = new FeeFinancialYearHandler();
		}
		return feeFinancialYearHandler;
	}
	
	/** 
	 * Used to add FeeFinancialYear	
	 * @param com.kp.cms.forms.fee.FeeFinancialYearEntryForm, javax.servlet.http.HttpServletRequest
	 * @return boolean
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public boolean addFeeFinancialYear(
			FeeFinancialYearEntryForm feeFinancialYearEntryForm, ActionErrors errors) throws Exception {
		log.info("Start of addFeeFinancialYear of FeeFinancialYearHandler");

		FeeFinancialYear feeFinancialYear = null;
		FeeFinancialYearTO feeFinancialYearTO = new FeeFinancialYearTO();	
		
		List<FeeFinancialYearTO> feeFinancialYearTOList=getFeeFinancialDetails();
		Iterator<FeeFinancialYearTO> feeFinancialYearTOIterator=feeFinancialYearTOList.iterator();

		if (feeFinancialYearTO != null) {
			while(feeFinancialYearTOIterator.hasNext()){
				feeFinancialYearTO = new FeeFinancialYearTO();			
				feeFinancialYearTO=feeFinancialYearTOIterator.next();
				if(feeFinancialYearEntryForm.getIsCurrent().equals("true") && feeFinancialYearTO.getIsCurrent().equals("Yes")){				
					//errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_FINANCIAL_YEAR_NOT_CURRENT + " "+ feeFinancialYearTO.getYear()+" "+ CMSConstants.FEE_FINANCIAL_YEAR_ALREADY_STORED));
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_FINANCIAL_YEAR_MORETHAN_ONE));
					throw new BusinessException();					
				}
			}
			feeFinancialYearTO.setCreatedBy(feeFinancialYearEntryForm.getUserId());
			feeFinancialYearTO.setModifiedBy(feeFinancialYearEntryForm.getUserId());
			feeFinancialYearTO.setYear(feeFinancialYearEntryForm.getFeeFinancialYear());
			feeFinancialYearTO.setIsCurrent(feeFinancialYearEntryForm.getIsCurrent());
			feeFinancialYear = FeeFinancialYearHelper.getInstance().populateTOtoBO(feeFinancialYearTO);
			if (feeFinancialYear != null && iFeeFinancialYearTransaction != null) {
				return iFeeFinancialYearTransaction.addFeeFinancialYear(feeFinancialYear);
			}
		}	
		log.info("End of addFeeFinancialYear of FeeFinancialYearHandler");
		return false;
	}

	
	/** 
	 * Used to get FeeFinancialYear Details	
	 * @param void
	 * @return List<FeeFinancialYear>
	 * @throws Exception
	 */
	public List<FeeFinancialYearTO> getFeeFinancialDetails() throws Exception {
		log.info("Start of getFeeFinancialDetails of FeeFinancialYearHandler");

		if (iFeeFinancialYearTransaction != null) {
			List<FeeFinancialYear> feeFinancialYearList = iFeeFinancialYearTransaction
					.getFeeFinancialYearDetails();
			if (feeFinancialYearList != null) {
				return FeeFinancialYearHelper.getInstance().poupulateBOtoTO(
						feeFinancialYearList);
			}
		}
		log.info("End of getFeeFinancialDetails of FeeFinancialYearHandler");
		return new ArrayList<FeeFinancialYearTO>();
	}
	
	/** 
	 * Used to get FeeFinancialYear	
	 * @param java.lang.String
	 * @return com.kp.cms.bo.admin.FeeFinancialYear
	 * @throws Exception
	 */
	public FeeFinancialYear getFeeFinancialYear(String feeFinancialYear)
			throws Exception {
		log.info("Start of getFeeFinancialYear of FeeFinancialYearHandler");
		if (iFeeFinancialYearTransaction != null) {
			return iFeeFinancialYearTransaction
					.getFeeFinancialYear(feeFinancialYear);
		}
		log.info("End of getFeeFinancialYear of FeeFinancialYearHandler");
		return new FeeFinancialYear();
	}
	
	/** 
	 * Used to get FeeFinancialYear with Id	
	 * @param int
	 * @return com.kp.cms.bo.admin.FeeFinancialYear
	 * @throws Exception
	 */
	public FeeFinancialYearTO getFeeFinancialDetailsWithId(int id)
			throws Exception {
		log.info("Start of getFeeFinancialDetailsWithId of FeeFinancialYearHandler");
		if (iFeeFinancialYearTransaction != null) {
			FeeFinancialYear feeFinancialYear = iFeeFinancialYearTransaction
					.getFeeFinancialDetailsWithId(id);
			if (feeFinancialYear != null) {
				return FeeFinancialYearHelper.getInstance().populateBOtoTOEdit(
						feeFinancialYear);
			}
		}
		log.info("End of getFeeFinancialDetailsWithId of FeeFinancialYearHandler");
		return new FeeFinancialYearTO();
	}
	/** 
	 * Used to update FeeFinancialYear
	 * @param com.kp.cms.forms.fee.FeeFinancialYearEntryForm
	 * @return boolean
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public boolean updateFeeFinancialYear(
			FeeFinancialYearEntryForm feeFinancialYearEntryForm,ActionErrors errors)
			throws Exception {
		log.info("Start of updateFeeFinancialYear of FeeFinancialYearHandler");	
		
		FeeFinancialYear feeFinancialYear = null;
		FeeFinancialYearTO feeFinancialYearTO = new FeeFinancialYearTO();		
		List<FeeFinancialYearTO> feeFinancialYearTOList=getFeeFinancialDetails();
		Iterator<FeeFinancialYearTO> feeFinancialYearTOIterator=feeFinancialYearTOList.iterator();
	
		if (feeFinancialYearTO != null) {
			while(feeFinancialYearTOIterator.hasNext()){
				feeFinancialYearTO = new FeeFinancialYearTO();			
				feeFinancialYearTO=feeFinancialYearTOIterator.next();
				if(feeFinancialYearEntryForm.getIsCurrent().equals("true") && feeFinancialYearTO.getIsCurrent().equals("Yes")){					
					//errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_FINANCIAL_YEAR_NOT_CURRENT + " "+ feeFinancialYearTO.getYear()+" "+ CMSConstants.FEE_FINANCIAL_YEAR_ALREADY_STORED));
					
					
					if(feeFinancialYearEntryForm.getFeeFinancialYear().equals(feeFinancialYearTO.getYear())){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_FINANCIAL_YEAR_IS_ALREADY_CURRENT));
						throw new BusinessException();
					}
					
					
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_FINANCIAL_YEAR_MORETHAN_ONE));
					throw new BusinessException();				
				}
			}
			if (feeFinancialYearEntryForm != null) {
				feeFinancialYearTO.setId(feeFinancialYearEntryForm.getId());
				feeFinancialYearTO.setIsCurrent(feeFinancialYearEntryForm
						.getIsCurrent());
				feeFinancialYearTO.setYear(feeFinancialYearEntryForm
						.getFeeFinancialYear());
				feeFinancialYearTO.setModifiedBy(feeFinancialYearEntryForm
						.getUserId());
				feeFinancialYearTO.setCreatedBy(feeFinancialYearEntryForm
						.getUserId());
			}
			feeFinancialYear = FeeFinancialYearHelper.getInstance().populateTOtoBOUpdate(feeFinancialYearTO);
			if (iFeeFinancialYearTransaction != null && feeFinancialYear != null) {
				return iFeeFinancialYearTransaction.updateFeeFinancialYear(feeFinancialYear);
			}
		}
		log.info("End of updateFeeFinancialYear of FeeFinancialYearHandler");
		return false;
	}
	
	/** 
	 * Used to delete FeeFinancialYear Details
	 * @param int, java.lang.String
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deleteFeeFinancialYearDetails(int id, String userId)
			throws Exception {
		log.info("Start of deleteFeeFinancialYearDetails of FeeFinancialYearHandler");
		if (iFeeFinancialYearTransaction != null) {
			return iFeeFinancialYearTransaction.deleteFeeFinancialYearDetails(
					id, userId);
		}
		log.info("End of deleteFeeFinancialYearDetails of FeeFinancialYearHandler");
		return false;
	}
	
	/** 
	 * Used to reactivate FeeFinancialYear	
	 * @param java.lang.String, java.lang.String
	 * @return boolean
	 * @throws Exception
	 */
	public boolean reActivateFeeFinancialYear(String year, String userId)
			throws Exception {
		log.info("Start of reActivateFeeFinancialYear of FeeFinancialYearHandler");
		if (iFeeFinancialYearTransaction != null) {
			return iFeeFinancialYearTransaction.reActivateFeeFinancialYear(
					year, userId);
		}
		log.info("End of reActivateFeeFinancialYear of FeeFinancialYearHandler");
		return false;
	}
}
