package com.kp.cms.handlers.fee;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.kp.cms.bo.admin.ApplicableFees;
import com.kp.cms.transactions.fee.IFeeApplicableTransaction;
import com.kp.cms.transactionsimpl.fee.FeeApplicableTransactionImpl;

/**
 * @Date 19/jan/2009
 * This is an handler class for Applicable fees.
 */
public class FeeHeadingHandler {
		private static FeeHeadingHandler feeApplicableHandler= null;
		public static FeeHeadingHandler getInstance() {
		      if(feeApplicableHandler == null) {
		    	  feeApplicableHandler = new FeeHeadingHandler();
		    	  return feeApplicableHandler;
		      }
		      return feeApplicableHandler;
		}
		
		/**
		 * 
		 * @return list of All Fee Applicables.
		 * @throws Exception
		 */
		public List getAllFeeApplicables() throws Exception {
			IFeeApplicableTransaction feeApplicableTransaction = FeeApplicableTransactionImpl.getInstance();
	        List feeApplicableList = feeApplicableTransaction.getAllFeeApplicable();
	        return feeApplicableList;
		}
		
		/**
		 * 
		 * @return list of All Fee Applicables.
		 * @throws Exception
		 */
		public Map getAllFeeApplicablesMap() throws Exception {
			IFeeApplicableTransaction feeApplicableTransaction = FeeApplicableTransactionImpl.getInstance();
	        List feeApplicableList = feeApplicableTransaction.getAllFeeApplicable();
	        Map feeApplicablesMap = new HashMap();
	        Iterator itr = feeApplicableList.iterator();
	        ApplicableFees applicableFees;
	        while(itr.hasNext()) {
	        	applicableFees = (ApplicableFees) itr.next();
	        	feeApplicablesMap.put(applicableFees.getId(), applicableFees.getName());
	        }
	        return feeApplicablesMap;
		}
	
}
