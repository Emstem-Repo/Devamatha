package com.kp.cms.handlers.fee;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeeAccount;
import com.kp.cms.bo.admin.FeeDivision;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.fee.FeeDivisionEntryForm;
import com.kp.cms.helpers.fee.FeeDivisionHelper;
import com.kp.cms.to.fee.FeeDivisionTO;
import com.kp.cms.transactions.fee.IFeeDivisionTransaction;
import com.kp.cms.transactionsimpl.fee.FeeDivisionTransactionImpl;

/**
 * 
 * Handler class for FeeDivision. This will help in CRUD operaion of Feedivision
 */
public class FeeDivisionHandler {
		
	   public static FeeDivisionHandler feeDivisionHandler = null;
	   private static Log log = LogFactory.getLog(FeeDivisionHandler.class);
	   
	   public static FeeDivisionHandler getInstance() {
		      if(feeDivisionHandler == null) {
		    	  feeDivisionHandler = new FeeDivisionHandler();
		    	  return feeDivisionHandler;
		      }
		      return feeDivisionHandler;
	   }
	   
	   /**
	    * 
	    * @return Map of FeeDivisions objects.
	    */
	   public Map<Integer,String> getFeeDivisionMap() throws Exception {
		   	  Map<Integer,String> feeDivisionMap = new HashMap<Integer,String>();
		   	  IFeeDivisionTransaction feeDivisionTransaction= FeeDivisionTransactionImpl.getInstance();
		      List<FeeDivision> feeDivisionList = feeDivisionTransaction.getFeeDivisions();
			  Iterator<FeeDivision> itr = feeDivisionList.iterator();
			  FeeDivision feeDivision;
			  while(itr.hasNext()) {
				   feeDivision = itr.next();
				   if(feeDivision.getFeeAccounts().size() != 0) {
					   	   Iterator<FeeAccount> itr2 = feeDivision.getFeeAccounts().iterator();
					   	   while(itr2.hasNext()){
						   if(itr2.next().getIsActive() == true){
							   feeDivisionMap.put(new Integer(feeDivision.getId()), feeDivision.getName());
							   break;
						   }	   
				   	   }
				   }
			  } 
		      return feeDivisionMap;
	   }
	   
	   public List<FeeDivisionTO> getFeeDivisionList() throws Exception {
		IFeeDivisionTransaction feeDivisionTransaction = FeeDivisionTransactionImpl
				.getInstance();
		List<FeeDivisionTO> feeDivisionList = FeeDivisionHelper
				.convertBoToTo(feeDivisionTransaction.getFeeDivisions());
		return feeDivisionList;
	}
	   
	   /**
	    * 
	    * @param divisionId
	    * @return Map of feeAccounts based on divisionId passed.
	    * @throws Exception
	    */
	   public Map<Integer,String> getFeeAccountsByDivision(int divisionId) throws Exception {
		   	  Map<Integer,String> feeAccountsMap = new HashMap<Integer,String>();
		   	  IFeeDivisionTransaction feeDivisionTransaction= FeeDivisionTransactionImpl.getInstance();
		      FeeDivision feeDivision = feeDivisionTransaction.getFeeDivision(divisionId);
			  Iterator<FeeAccount> itr = feeDivision.getFeeAccounts().iterator();
			  FeeAccount feeAccount;
			  while(itr.hasNext()) {
				   feeAccount = itr.next();
				   if(feeAccount.getIsActive() == true) {
					   feeAccountsMap.put(new Integer(feeAccount.getId()), feeAccount.getName());
				   }
			  } 
		      return feeAccountsMap;
	   }
	   
	   public void addFeeDivision(FeeDivisionEntryForm feeDivisionEntryForm)
			throws DuplicateException,ReActivateException,Exception {
		IFeeDivisionTransaction feeDivisionTransaction = FeeDivisionTransactionImpl
				.getInstance();
		feeDivisionTransaction.addFeeDivisionEntry(feeDivisionEntryForm
				.getDivisionName(), feeDivisionEntryForm.getUserId());
	}

	public void deleteFeeDivisionEntry(int feeDivisionId) throws Exception {
		IFeeDivisionTransaction feeDivisionTransaction = FeeDivisionTransactionImpl
				.getInstance();
		feeDivisionTransaction.deleteFeeDivisionEntry(feeDivisionId);
	}

	public void updateFeeDivisionEntry(int feeDivisionId, String feeDivisionName , String originalDivisionName, String userName)
			throws DuplicateException,ReActivateException,Exception {
		IFeeDivisionTransaction feeDivisionTransaction = FeeDivisionTransactionImpl
				.getInstance();
		feeDivisionTransaction.updateFeeDivisionEntry(feeDivisionId,
				feeDivisionName,originalDivisionName, userName);
	}
	
	public void reActivateFeeDivision(String feeDivisionName) throws Exception {
		IFeeDivisionTransaction feeDivisionTransaction = FeeDivisionTransactionImpl
		.getInstance();
 feeDivisionTransaction.reActivateFeeDivisionEntry(feeDivisionName);
	}
	   
	   
}
