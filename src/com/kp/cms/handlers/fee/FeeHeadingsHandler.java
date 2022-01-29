package com.kp.cms.handlers.fee;

import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.kp.cms.bo.admin.FeeGroup;
import com.kp.cms.bo.admin.FeeHeading;
import com.kp.cms.forms.fee.FeeHeadingsForm;
import com.kp.cms.helpers.fee.FeeHeadingsHelper;
import com.kp.cms.to.fee.FeeHeadingTO;
import com.kp.cms.transactions.fee.IFeeHeadingTransaction;
import com.kp.cms.transactionsimpl.fee.FeeHeadingTransactionImpl;

public class FeeHeadingsHandler {

	private static final Logger log=Logger.getLogger(FeeHeadingsHandler.class);
	
	/**
	 * @return a single instance of FeeHeadingsHandler.
	 * @throws Exception
	 */
	
	private static FeeHeadingsHandler feeHeadingsHandler= null;
	public static FeeHeadingsHandler getInstance() {
	      if(feeHeadingsHandler == null) {
	    	  feeHeadingsHandler = new FeeHeadingsHandler();
	    	  return feeHeadingsHandler;
	      }
	      return feeHeadingsHandler;
	}
	/**
	 * This method is used to get all FeeHeading details from database.
	 * @return list of type FeeHeadingTO for displaying in UI. 
	 * @throws Exception
	 */
	
	public List<FeeHeadingTO> getFeeHeadingDetails() throws Exception {
		log.info("call of getFeeHeadingsDetails in FeeHeadingsHandler class.");
		IFeeHeadingTransaction feeHeadingTransaction= FeeHeadingTransactionImpl.getInstance();
		//getting the FeeHeading details as a list from Impl.
		List<FeeHeading> list = feeHeadingTransaction.getAllFeeHeadings();
		//sending the list of BO to helper for converting to TO.
		List<FeeHeadingTO> feeHeadinglist = FeeHeadingsHelper.getInstance().convertBOstoTOs(list);
		log.info("end of getFeeHeadingsDetails in FeeHeadingsHandler class.");
		return feeHeadinglist;
	}
	
	/**
	 * This method is used to check for Duplicate Entry.
	 * @param feeGroupId
	 * @param feesName
	 * @return FeeHeading Bo instance from Impl.
	 * @throws Exception
	 */
	
	public FeeHeading isFeeHeadingNameExist(int feeGroupId, String feesName) throws Exception {
		log.info("call of FeeHeadingNameExist in FeeHeadingsHandler class.");
		IFeeHeadingTransaction feeHeadingTransaction= FeeHeadingTransactionImpl.getInstance();
		//sending feeGroupId, fees name to FeeHeadingTransactionImpl for Duplicate Entry checking 
		FeeHeading feeHeading = feeHeadingTransaction.isFeeHeadingNameExist(feeGroupId, feesName);
		log.info("end of FeeHeadingNameExist in FeeHeadingsHandler class.");
		return feeHeading;
	}
	
	/**
	 * This method is used for saving a record to database.
	 * @param feeHeadingsForm
	 * @return boolean variable after saving of a record in database.
	 * @throws Exception
	 */
	public boolean addFeeHeadings(FeeHeadingsForm feeHeadingsForm) throws Exception {
		log.info("call of addFeeHeadings in FeeHeadingsHandler class.");
		//sending feeHeadingForm to helper to convertTostoBOs.
		FeeHeading feeHeading = FeeHeadingsHelper.getInstance().convertTOstoBOs(feeHeadingsForm);
		IFeeHeadingTransaction feeHeadingTransaction = FeeHeadingTransactionImpl.getInstance();
		//sending BO instance to FeeHeadingTransactionImpl for saving a record to database.
		boolean isAdded = feeHeadingTransaction.addFeeHeading(feeHeading);
		log.info("end of addFeeHeadings in FeeHeadingsHandler class.");
		return isAdded;
	}
	
	/**
	 * This method is used for editing and getting the details based on id.
	 * @param id
	 * @return FeeHeadingTO instance after getting a record from database based on id.
	 * @throws Exception
	 */
	
	public FeeHeadingTO editFeeHeadings(int id) throws Exception {
		log.info("call of editFeeHeadings in FeeHeadingsHandler class.");
		IFeeHeadingTransaction feeHeadingTransaction = FeeHeadingTransactionImpl.getInstance();
		//getting a list of type FeeHeading BO from FeeHeadingTransactionImpl based on id.
		List<FeeHeading> feeHeadingList = feeHeadingTransaction.editFeeHeading(id);
		//converting list of type FeeHeading BO to FeeHeading TO.
		FeeHeadingTO feeHeadingTO = FeeHeadingsHelper.getInstance().convertBOstoTOsForEdit(feeHeadingList);
		log.info("end of editFeeHeadings in FeeHeadingsHandler class.");
		return feeHeadingTO;
	}
	
	/**
	 * This method is used to updating a record to database.
	 * @param feeHeadingsForm
	 * @return boolean variable after updating a record from database.
	 * @throws Exception
	 */
	
	public boolean updateFeeHeadings(FeeHeadingsForm feeHeadingsForm) throws Exception {
		log.info("call of updateFeeHeadings in FeeHeadingsHandler class.");
		IFeeHeadingTransaction feeHeadingTransaction = FeeHeadingTransactionImpl.getInstance();
		//setting the data form feeHeadingForm to BO for updating.
		FeeHeading feeHeading = new FeeHeading();
		FeeGroup feeGroup = new FeeGroup();
			feeGroup.setId(Integer.valueOf(feeHeadingsForm.getFeeGroup()));
			feeHeading.setFeeGroup(feeGroup);
			feeHeading.setId(feeHeadingsForm.getFeeHeadingsId());
			feeHeading.setName(feeHeadingsForm.getFeesName());
			feeHeading.setIsActive(new Boolean(true));
			feeHeading.setModifiedBy(feeHeadingsForm.getUserId());
			feeHeading.setLastModifiedDate(new Date());
			//sending the BO instance to FeeHeadingTransactionImpl for updating a record in database.
		boolean isUpdated = feeHeadingTransaction.updateFeeHeadings(feeHeading);
		log.info("end of updateFeeHeadings in FeeHeadingsHandler class.");
		return isUpdated;
	}
	
	/**
	 * This method is used to delete a record from database based on id.
	 * @param id
	 * @return boolean variable after deleting a record from database.
	 * @throws Exception
	 */
	
	public boolean deleteFeeHeadings(int id, String userId) throws Exception {
		log.info("call of deleteFeeHeadings in FeeHeadingsHandler class.");
		IFeeHeadingTransaction feeHeadingTransaction = FeeHeadingTransactionImpl.getInstance();
		//sending id to FeeHeadingTransactionImpl for deleting a record from database.
		boolean isDeleted = feeHeadingTransaction.deleteFeeHeadings(id,userId);
		log.info("end of deleteFeeHeadings in FeeHeadingsHandler class.");
		return isDeleted;
	}
	
	/**
	 * This method is reactivation or restoring a record from database based on feesName.
	 * @param feesName
	 * @throws Exception
	 */
	
	public void reActivateFeeHeadings(String feesName, String userId)throws Exception {
		log.info("call of reActivateFeeHeadings in FeeHeadingsHandler class.");
		IFeeHeadingTransaction feeHeadingTransaction = FeeHeadingTransactionImpl.getInstance();
		//restoring a record from database based on feesName through FeeHeadingTransactionImpl.
		feeHeadingTransaction.reActivateFeeHeadings(feesName,userId);
		log.info("end of reActivateFeeHeadings in FeeHeadingsHandler class.");
	}
}