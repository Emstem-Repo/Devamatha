package com.kp.cms.handlers.fee;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.fee.FeePaidForm;
import com.kp.cms.forms.fee.FeePaymentForm;
import com.kp.cms.helpers.fee.FeePaidHelper;
import com.kp.cms.to.fee.FeePaymentTO;
import com.kp.cms.transactions.fee.IFeePaidTransaction;
import com.kp.cms.transactions.fee.IFeePaymentTransaction;
import com.kp.cms.transactionsimpl.fee.FeePaidTransactionImpl;
import com.kp.cms.transactionsimpl.fee.FeePaymentTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

/**
 * @author microhard
 * Handler class for fee paid. This class will handler all the request to database for all CRUD operations.
 * 
 */
public class FeePaidHandler {
	private static FeePaidHandler feePaidHandler= null;
	private static final Log log = LogFactory.getLog(FeePaidHandler.class);	
	/**
	 * 
	 * @return the singleton object
	 */
	public static FeePaidHandler getInstance() {
		if(feePaidHandler == null) {
			feePaidHandler = new FeePaidHandler();
			return feePaidHandler;
		}
		return feePaidHandler;
	}
		
	/**
	 * This method will search the fee payment between 2 dates.
	 * @param feePaidForm
	 * @throws Exception means failed to search record.
	 */
	public void feePaymentSearch(FeePaidForm feePaidForm,String search) throws DataNotFoundException,Exception {
		log.debug("Handler : Entering feePaymentSearch");			  
		IFeePaymentTransaction feePaymentTransaction = FeePaymentTransactionImpl.getInstance();
		Date startDate = CommonUtil.ConvertStringToSQLDate(feePaidForm.getStartDate());
		Date endDate = CommonUtil.ConvertStringToSQLDate(feePaidForm.getEndDate());
		List<FeePayment> feePaymentsList = null;
		if(search.equals("paidSearch")) {
			feePaymentsList = feePaymentTransaction.getFeePaymentsBetweenDates(startDate,endDate, feePaidForm.getDivisionid());
		}	
		if(search.equals("cancelSearch")) {
			feePaymentsList = feePaymentTransaction.getFeePaymentsBetweenDatesForCancel(startDate,endDate);
		}	
		// True when no record found in searching.
		if(feePaymentsList == null || feePaymentsList.isEmpty()) {
			throw new DataNotFoundException();
		}
		// setting searched record back to form.
		List<FeePaymentTO> feePaymentToList = FeePaidHelper.getInstance().copyBotoTo(feePaymentsList);
		feePaidForm.setFeePaymentList(feePaymentToList);
		log.debug("Handler : Leaving feePaymentSearch");
	}
		
	/**
	 * This method will mark fee as paid
	 * @param feePaidForm
	 * @throws Exception means failed to update.
	 */
	public void markAsPaid(FeePaidForm feePaidForm) throws Exception {
		log.debug("Handler : Entering feePaymentSearch");		  
//		Set<Integer> ids = FeePaidHelper.getInstance().getMarkAsPaidIds(feePaidForm);
		Set<FeePaymentTO> feePaymentToSet = FeePaidHelper.getInstance().getMarkAsPaidIds(feePaidForm);  
		
		IFeePaidTransaction feePaidTransaction = FeePaidTransactionImpl.getInstance();
//		feePaidTransaction.markAsPaid(ids);
		Date paidDate = new Date();
		if(feePaidForm.getPaidDate()!= null){
			paidDate = CommonUtil.ConvertStringToSQLDate(feePaidForm.getPaidDate());
		}
		feePaidTransaction.markAsPaid(feePaymentToSet, paidDate);
 		feePaidTransaction.updatePaidDateInDetailTable(feePaymentToSet, paidDate);
		log.debug("Handler : Entering feePaymentSearch");		  
	}
	
	/**
	 * This method will mark fee challan as canceled.
	 * @param feePaidForm
	 * @throws Exception means failed to mark as paid.
	 */
	public void markAsCancel(FeePaidForm feePaidForm) throws Exception {
		log.debug("Handler : Entering feePaymentSearch");		  
		//Set<Integer> ids = FeePaidHelper.getInstance().getMarkAsCancelIds(feePaidForm);  
		List<FeePaymentTO> reasonId = FeePaidHelper.getInstance().getMarkAsCancelIds(feePaidForm);  
		IFeePaidTransaction feePaidTransaction = FeePaidTransactionImpl.getInstance();
		feePaidTransaction.markAsCancel(reasonId);
		log.debug("Handler : Entering feePaymentSearch");		  
	}

	public List<FeePayment> checkPreviousBalance(Student student,
			FeePaymentForm feePaymentForm)throws Exception {
		// TODO Auto-generated method stub
		IFeePaidTransaction feePaidTransaction = FeePaidTransactionImpl.getInstance();
		return feePaidTransaction.checkPreviousBalance(student,feePaymentForm);
	}

	public void setFinancialYearId(FeePaymentForm feePaymentForm)throws Exception {
		// TODO Auto-generated method stub
		IFeePaidTransaction feePaidTransaction = FeePaidTransactionImpl.getInstance();
		feePaidTransaction.setFinancialYearId(feePaymentForm);
	}

}
