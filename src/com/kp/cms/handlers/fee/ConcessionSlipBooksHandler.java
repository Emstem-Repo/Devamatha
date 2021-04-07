package com.kp.cms.handlers.fee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeeFinancialYear;
import com.kp.cms.bo.admin.FeeVoucher;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.fee.ConcessionSlipBooksForm;
import com.kp.cms.helpers.fee.ConcessionSlipBooksHelper;
import com.kp.cms.to.fee.FeeVoucherTO;
import com.kp.cms.transactions.fee.IConcessionSlipBooksTxn;
import com.kp.cms.transactionsimpl.fee.ConcessionSlipBooksTransactionImpl;

public class ConcessionSlipBooksHandler {
	private static final Log log = LogFactory.getLog(ConcessionSlipBooksHandler.class);
	public static ConcessionSlipBooksHandler concessionSlipBooksHandler = null;

	public static ConcessionSlipBooksHandler getInstance() {
		if (concessionSlipBooksHandler == null) {
			concessionSlipBooksHandler = new ConcessionSlipBooksHandler();
			return concessionSlipBooksHandler;
		}
		return concessionSlipBooksHandler;
	}	
	/**
	 * 
	 * @param counterMasterForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addConcSlipBooks(ConcessionSlipBooksForm slipBooksForm, String mode) throws Exception {
		log.debug("inside addConcSlipBooks");
		IConcessionSlipBooksTxn iTransaction = ConcessionSlipBooksTransactionImpl.getInstance();
		boolean isAdded = false;
		
		FeeVoucher duplFeeVoucher = iTransaction.isBookNoDuplcated(slipBooksForm); 

		if (duplFeeVoucher != null && duplFeeVoucher.getIsActive()) {
			throw new DuplicateException();
		}
		else if (duplFeeVoucher != null && !duplFeeVoucher.getIsActive())
		{
			slipBooksForm.setDuplId(duplFeeVoucher.getId());
			throw new ReActivateException();
		}		
				
		FeeVoucher feeVoucher = new FeeVoucher();
		feeVoucher = ConcessionSlipBooksHelper.getInstance().copyDataFromFormToBO(slipBooksForm); 
		isAdded = iTransaction.addSlipBook(feeVoucher, mode); 
		log.debug("leaving addConcSlipBooks");
		return isAdded;
	}
	
	/**
	 * @return list of InvCounterTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<FeeVoucherTO> getConcessionSlipBookDetails() throws Exception {
		log.debug("inside getConcessionSlipBookDetails");
		IConcessionSlipBooksTxn iConcessionSlipBooksTxn = ConcessionSlipBooksTransactionImpl.getInstance();
		List<FeeVoucher> voucherList = iConcessionSlipBooksTxn.getSlipBookDetails();
		List<FeeVoucherTO> feeVoucherTOList = ConcessionSlipBooksHelper.getInstance().copyVoucherBosToTos(voucherList); 
		log.debug("leaving getConcessionSlipBookDetails");
		return feeVoucherTOList;
	}
	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */

	public boolean deleteSlipBook(int id, Boolean activate, ConcessionSlipBooksForm slipForm) throws Exception {
		IConcessionSlipBooksTxn iTxn = ConcessionSlipBooksTransactionImpl.getInstance();
		return iTxn.deleteSlipBook(id, activate, slipForm); 
	}	
	/**
	 * 
	 * @param slipBooksForm
	 * @throws Exception
	 */
	public void setCurrentfinancialYear(ConcessionSlipBooksForm slipBooksForm) throws Exception{
		IConcessionSlipBooksTxn iTxn = ConcessionSlipBooksTransactionImpl.getInstance();
		FeeFinancialYear feeFinancialYear = iTxn.getCurrentFeeFinancialYear();
		if(feeFinancialYear!= null){
			slipBooksForm.setFinacialYear(feeFinancialYear.getYear());
		}
		
	}

}
