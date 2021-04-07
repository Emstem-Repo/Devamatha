package com.kp.cms.handlers.fee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;
import com.kp.cms.bo.admin.FeeAdditional;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.fee.FeeAdditionalForm;
import com.kp.cms.helpers.fee.FeeAdditionalHelper;
import com.kp.cms.to.fee.FeeAdditionalAccountAssignmentTO;
import com.kp.cms.to.fee.FeeAdditionalTO;
import com.kp.cms.transactions.fee.IFeeAdditionalTransaction;
import com.kp.cms.transactionsimpl.fee.FeeAddtionalTransactionImpl;

/**
 * @author microhard
 * This is an handler class for Fee Additional.
 */
public class FeeAdditionalHandler {
	
	private static FeeAdditionalHandler feeAdditionalHandler= null;
	private static final Log log = LogFactory.getLog(FeeAdditionalHandler.class);
	/**
	 * 
	 * @return singleton object.
	 */
	public static FeeAdditionalHandler getInstance() {
	      if(feeAdditionalHandler == null) {
	    	  feeAdditionalHandler = new FeeAdditionalHandler();
	    	  return feeAdditionalHandler;
	      }
	      return feeAdditionalHandler;
	}
	
	/**
	 * 
	 * @param feeGroupSet
	 * @return List<FeeAdditional>
	 * @throws Exception
	 * 		   method will return the feeadditional based on groups.
	 */
	public List<FeeAdditional> getFeeAdditionalByGroup(Set<Integer> feeGroupSet) throws Exception {
		log.debug("Handler : Entering getFeeAdditionalByGroup ");
		IFeeAdditionalTransaction feeAdditionalTransaction = FeeAddtionalTransactionImpl.getInstance();
		List<FeeAdditional> feeAdditionalList = feeAdditionalTransaction.getFeeAdditional(feeGroupSet);
		log.debug("Handler : Leaving getFeeAdditionalByGroup ");
	return feeAdditionalList;
	}
	
	/**
	 * 
	 * @return List of FeeAssingment TO's object.
	 * @throws Exception
	 */
	public List<FeeAdditionalTO> getAllAdditionalFees() throws Exception{
		log.debug("Handler : Entering getAllAdditionalFees ");
		IFeeAdditionalTransaction feeAdditionalTransaction = FeeAddtionalTransactionImpl.getInstance();
        List<FeeAdditional> feeAdditionalAssignments = feeAdditionalTransaction.getAllAdditionalFees();
		List<FeeAdditionalTO> feeAssignmentsTOs = FeeAdditionalHelper.getInstance().copyFeeAdditionalBosToTos(feeAdditionalAssignments);
		log.debug("Handler : Leaving getAllAdditionalFees ");
	return feeAssignmentsTOs;
	}
		
	
	/**
	 * 
	 * @param feeAdditionalForm
	 * @return boolean true is successfully added.
	 * @throws Exception
	 */
	public boolean saveFeeAssignmentAccount(FeeAdditionalForm feeAdditionalForm) throws ConstraintViolationException,Exception {
		log.debug("Handler : Entering saveFeeAssignmentAccount ");
		FeeAdditional feeAdditional = FeeAdditionalHelper.getInstance().copyFormDataToBo(feeAdditionalForm);
		IFeeAdditionalTransaction feeAdditionalTransaction = FeeAddtionalTransactionImpl.getInstance();
		boolean isAdded = false;
		isAdded = feeAdditionalTransaction.addFeeAdditionalAssignment(feeAdditional);
		log.debug("Handler : Leaving saveFeeAssignmentAccount ");
	return isAdded;
	}
	
	/**
	 * 
	 * @param feeAdditionalForm
	 * @return boolean true is successfully added.
	 *         This add the data to FEE and FeeAccountAssignment table.
	 * @throws Exception
	 */
	public boolean updateFeeAssignmentAccount(FeeAdditionalForm feeAdditionalForm) throws Exception {
		log.debug("Handler : Entering updateFeeAssignmentAccount ");
		FeeAdditional feeAdditional = FeeAdditionalHelper.getInstance().copyFormDataToBo(feeAdditionalForm);
		feeAdditional.setId(Integer.parseInt(feeAdditionalForm.getId()));
		IFeeAdditionalTransaction feeAdditionalTransaction = FeeAddtionalTransactionImpl.getInstance();
		boolean isUpdated = false;
		isUpdated = feeAdditionalTransaction.updateFeeAdditionalAssignment(feeAdditional);
		log.debug("Handler : Leaving updateFeeAssignmentAccount ");
	return isUpdated;
	}
	
	/**
	 * 
	 * @param feeAdditionalForm
	 * @return boolean based on deleted status.
	 *         This delete the FEE and FEEACCOUNTASSIGNMENT detail.
	 * @throws Exception
	 */
	public boolean deleteFeeAssignment(FeeAdditionalForm feeAdditionalForm) throws Exception {
		log.debug("Handler : Entering deleteFeeAssignment ");
		FeeAdditional feeAdditional = new FeeAdditional();
		feeAdditional.setId(Integer.parseInt(feeAdditionalForm.getId())); 
		feeAdditional.setModifiedBy(feeAdditionalForm.getUserId());
		feeAdditional.setLastModifiedDate(new Date());
		IFeeAdditionalTransaction feeAdditionalTransaction = FeeAddtionalTransactionImpl.getInstance();
		boolean isDeleted = false;
		isDeleted = feeAdditionalTransaction.deleteFeeAdditionalAssignment(feeAdditional);
		log.debug("Handler : Leaving deleteFeeAssignment ");
	return isDeleted;
	}
	
	/**
	 * 
	 * @param feeAdditionalForm
	 * @return boolean based on deleted status.
	 *         This delete the FEE and FEEACCOUNTASSIGNMENT detail.
	 * @throws Exception
	 */
	public boolean activateFeeAssignment(FeeAdditionalForm feeAdditionalForm) throws Exception {
		log.debug("Handler : Entering activateFeeAssignment ");
		FeeAdditional feeAdditional = new FeeAdditional();
		feeAdditional.setId(Integer.parseInt(feeAdditionalForm.getId())); 
		feeAdditional.setModifiedBy(feeAdditionalForm.getUserId());
		feeAdditional.setLastModifiedDate(new Date());
		IFeeAdditionalTransaction feeAdditionalTransaction = FeeAddtionalTransactionImpl.getInstance();
		boolean isActivated = false;
		isActivated = feeAdditionalTransaction.activateFeeAdditionalAssignment(feeAdditional);
		log.debug("Handler : Leaving activateFeeAssignment ");
	return isActivated;
	}
	
	/**
	 * 
	 * @return sets the data back to form.
	 *         Will get FEE and FEEACCOUNTASSIGNMENT  data from DB and setting to form.
	 * @throws Exception
	 */
	public void viewFeeAssignment(FeeAdditionalForm feeAdditionalForm) throws Exception {
		
		log.debug("Handler: Entering viewFeeAssignment ");
		IFeeAdditionalTransaction feeAdditionalTransaction = FeeAddtionalTransactionImpl.getInstance();
        FeeAdditional fee = feeAdditionalTransaction.getFeeAdditionalAssignmentById(Integer.parseInt(feeAdditionalForm.getId()));
				
		Map<Integer,String> feeAccountsMap = FeeAccountHandler.getInstance().getAllFeeAccountsMap();
	    Map<Integer,String> feeHeadingsMap = FeeGroupHandler.getInstance().getFeeHeadingsByGroup(fee.getFeeGroup().getId());
		
	    List<FeeAdditionalAccountAssignmentTO> feeAssignmentList = FeeAdditionalHelper.getInstance().copyFeeAssignmentBosToTo(fee.getFeeAdditionalAccountAssignments());
	    feeAdditionalForm.setFeeAssignmentList(feeAssignmentList);

		feeAdditionalForm.setFeeAccountsMap(feeAccountsMap);
		feeAdditionalForm.setFeeHeadingsMap(feeHeadingsMap);
	    feeAdditionalForm.setFeeGroupName(fee.getFeeGroup().getName());
	    log.debug("Handler: Leaving viewFeeAssignment ");
	}
	
	/**
	 * This method load pre-necessary data while loading new fee account.
	 * @param feeAdditionalForm
	 * @throws Exception
	 */
	public void initAddFeeAssignmentAccount(FeeAdditionalForm feeAdditionalForm) throws ReActivateException,DuplicateException,Exception{
		log.debug("Handler: Entering initAddFeeAssignmentAccount ");
		IFeeAdditionalTransaction feeAdditionalTransaction = FeeAddtionalTransactionImpl.getInstance();
		FeeAdditional duplicateFee = FeeAdditionalHelper.getInstance().copyFormDataToBo(feeAdditionalForm);
		duplicateFee = feeAdditionalTransaction.getFeeAdditionalByCompositeKeys(duplicateFee);
		if(duplicateFee != null && duplicateFee.getIsActive()) {
			throw new DuplicateException();
		} else if(duplicateFee != null && !duplicateFee.getIsActive()) {
			feeAdditionalForm.setId(Integer.valueOf(duplicateFee.getId()).toString());
			throw new ReActivateException();
		}
		
		Map<Integer,String> feeAccountsMap = FeeAccountHandler.getInstance().getAllFeeAccountsMap();
	    Map<Integer,String> feeHeadingsMap = FeeGroupHandler.getInstance().getFeeHeadingsByGroup(Integer.parseInt(feeAdditionalForm.getFeeGroupId()));
		List<FeeAdditionalAccountAssignmentTO> feeAssignmentList = new ArrayList<FeeAdditionalAccountAssignmentTO>();
		int count = feeAccountsMap.size()* feeHeadingsMap.size();
		FeeAdditionalAccountAssignmentTO feeAccountAssignmentTo;
		// setting each feeAssignmentTO with default data.
		for(int i=0;i<count;i++) {
			feeAccountAssignmentTo = new FeeAdditionalAccountAssignmentTO(0.0,0,0,0);
			feeAssignmentList.add(feeAccountAssignmentTo);
		}
		//setting all data to form.
		feeAdditionalForm.setFeeAssignmentList(feeAssignmentList);
		feeAdditionalForm.setFeeAccountsMap(feeAccountsMap);
		feeAdditionalForm.setFeeHeadingsMap(feeHeadingsMap);
		log.debug("Handler: Leaving initAddFeeAssignmentAccount ");
	}
	
	/**
	 * This method load pre-necessary data while assigning new Fee account amounts means 2 page.
	 * @param feeAdditionalForm
	 * @throws Exception
	 */
	public void initEditFeeAssignmentAccount(FeeAdditionalForm feeAdditionalForm) throws ReActivateException,DuplicateException,Exception{
		log.debug("Handler: Entering initEditFeeAssignmentAccount ");
		IFeeAdditionalTransaction feeAdditionalTransaction = FeeAddtionalTransactionImpl.getInstance();
		FeeAdditional dummyFeeAdditional = FeeAdditionalHelper.getInstance().copyFormDataToBo(feeAdditionalForm); 
		FeeAdditionalTO feeTo = feeAdditionalForm.getFeeAdditionalTo();
		boolean duplicateCheck = FeeAdditionalHelper.getInstance().checkForDuplicateWhileUpdate(feeTo,feeAdditionalForm);
		if(duplicateCheck) {
			FeeAdditional duplicateFee = feeAdditionalTransaction.getFeeAdditionalByCompositeKeys(dummyFeeAdditional);
			if(duplicateFee != null && duplicateFee.getIsActive()) {
				throw new DuplicateException();
			} else if(duplicateFee != null && !duplicateFee.getIsActive()){
				feeAdditionalForm.setId(Integer.valueOf(duplicateFee.getId()).toString());
				throw new ReActivateException();
			}
		}

		FeeAdditional feeAdditional = feeAdditionalTransaction.getFeeAdditionalAssignmentById(Integer.parseInt(feeAdditionalForm.getId()));
        feeAdditionalForm.setId(Integer.valueOf(feeAdditional.getId()).toString());
        
        List<FeeAdditionalAccountAssignmentTO> feeAssignmentList = FeeAdditionalHelper.getInstance().copyFeeAssignmentBosToTo(feeAdditional.getFeeAdditionalAccountAssignments());
        		
		// following will have selected data selected from fee assignment
		Map<Integer,String> feeAccountsMap = FeeAccountHandler.getInstance().getAllFeeAccountsMap();
	    Map<Integer,String> feeHeadingsMap = FeeGroupHandler.getInstance().getFeeHeadingsByGroup(Integer.parseInt(feeAdditionalForm.getFeeGroupId()));
	    
	    // here checking feeassignment account data fetched from database is less than
        // the user requested by selecting accounts, applicables * with admitted through
        // this logic useful when user has selected more accounts and applicable while updating.
        // this will need to more no of TO's in list. for those initializing with default value.
		int count = feeAccountsMap.size() * feeHeadingsMap.size();
		int size = count - feeAssignmentList.size();
		FeeAdditionalAccountAssignmentTO feeAccountAssignmentTo;
		if(count > feeAssignmentList.size()) {
			for(int i=0;i<size;i++){
				feeAccountAssignmentTo = new FeeAdditionalAccountAssignmentTO(0.0,0,0,0);
				feeAssignmentList.add(feeAccountAssignmentTo);
			}
		}
        feeAdditionalForm.setFeeGroupName(feeAdditional.getFeeGroup().getName());
		feeAdditionalForm.setFeeAssignmentList(feeAssignmentList);
		feeAdditionalForm.setFeeAccountsMap(feeAccountsMap);
		feeAdditionalForm.setFeeHeadingsMap(feeHeadingsMap);
		log.debug("Handler: Leaving initEditFeeAssignmentAccount ");
	}
	
	/**
	 * 
	 * @param feeAdditionalForm
	 * @throws Exception
	 */
	public void editFeeAssignment(FeeAdditionalForm feeAdditionalForm) throws Exception {
		log.debug("Handler: Entering editFeeAssignment ");
		IFeeAdditionalTransaction feeAdditionalTransaction = FeeAddtionalTransactionImpl.getInstance();
		FeeAdditional feeAdditional = feeAdditionalTransaction.getFeeAdditionalAssignmentById(Integer.parseInt(feeAdditionalForm.getId()));
        FeeAdditionalTO feeTO = FeeAdditionalHelper.getInstance().copyFeeAdditionalToFeeAdditionalTO(feeAdditional);
        feeAdditionalForm.setId(Integer.valueOf(feeAdditional.getId()).toString());
        feeAdditionalForm.setFeeGroupId(Integer.valueOf(feeAdditional.getFeeGroup().getId()).toString());
        feeAdditionalForm.setFeeAdditionalTo(feeTO);
        log.debug("Handler: Leaving editFeeAssignment ");
 	}
}
