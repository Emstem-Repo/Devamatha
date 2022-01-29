package com.kp.cms.handlers.fee;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeeGroup;
import com.kp.cms.bo.admin.FeeHeading;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.fee.FeeGroupEntryForm;
import com.kp.cms.helpers.fee.FeeGroupHelper;
import com.kp.cms.to.fee.FeeGroupTO;
import com.kp.cms.transactions.fee.IFeeGroupTransaction;
import com.kp.cms.transactionsimpl.fee.FeeGroupTransactionImpl;

public class FeeGroupHandler {

	   public static FeeGroupHandler feeGroupHandler = null;
	   private static final Log log = LogFactory.getLog(FeeGroupHandler.class);
	   
	   public static FeeGroupHandler getInstance() {
		      if(feeGroupHandler == null) {
		    	  feeGroupHandler = new FeeGroupHandler();
		    	  return feeGroupHandler;
		      }
		      return feeGroupHandler;
	   }
	  
     
	 /**
	 *	This method is used to get Fee Group details from database. 
	 * @return list of FeeCategories objects.
	 */
	public List<FeeGroupTO> getFeeGroups() throws Exception {
		log.info("call of getFeeGroups method in FeeGroupHandler class.");
		IFeeGroupTransaction feeGroupTransaction = FeeGroupTransactionImpl
				.getInstance();

		List<FeeGroupTO> feeGroupList = FeeGroupHelper
				.convertBoToTo(feeGroupTransaction.getFeeGroups());
		log.info("end of getFeeGroups method in FeeGroupHandler class.");
		return feeGroupList;
	}
	   
	   /**
	    * This method is used to get Fee Group details as Map.
	    * @return list of FeeCategories objects.
	    */
	
	   public Map<Integer,String> getFeeGroupsMap() throws Exception {
		   log.info("call of getFeeGroupsMap method in FeeGroupHandler class.");
		   	   IFeeGroupTransaction feeGroupTransaction= FeeGroupTransactionImpl.getInstance();
			   List<FeeGroup> feeGroupList = feeGroupTransaction.getFeeGroups();
			   Map<Integer,String> feeGroupMap = new HashMap<Integer,String>();
			   Iterator<FeeGroup> itr = feeGroupList.iterator();
			   FeeGroup feeGroup;
			   while(itr.hasNext()) {
				   feeGroup = itr.next();
				   if(feeGroup.getFeeHeadings().size() != 0){
					   feeGroupMap.put(Integer.valueOf(feeGroup.getId()), feeGroup.getName());
				   }
			   }
		   log.info("end of getFeeGroupsMap method in FeeGroupHandler class.");
	       return feeGroupMap;
	   }	
	   
	   /**
	    * This method is used to get Fee OptionalGroup details as Map.
	    * @return list of FeeCategories objects.
	    */
	   public Map<Integer,String> getFeeOptionalGroupsMap() throws Exception {
		   log.info("call of getFeeOptionalGroupsMap method in FeeGroupHandler class.");
		   	   IFeeGroupTransaction feeGroupTransaction= FeeGroupTransactionImpl.getInstance();
			   List<FeeGroup> feeGroupList = feeGroupTransaction.getOptionalFeeGroups();
			   Map<Integer,String> feeGroupMap = new HashMap<Integer,String>();
			   Iterator<FeeGroup> itr = feeGroupList.iterator();
			   FeeGroup feeGroup;
			   while(itr.hasNext()) {
				   feeGroup = itr.next();
				   if(feeGroup.getFeeHeadings().size() != 0) {
					   Iterator<FeeHeading> itr2 = feeGroup.getFeeHeadings().iterator();
					   while(itr2.hasNext()){
						   if(itr2.next().getIsActive() == true){
							   feeGroupMap.put(Integer.valueOf(feeGroup.getId()), feeGroup.getName());
							   break;
						   }	   
					   }					   
				   }	   
			   }
		   log.info("end of getFeeOptionalGroupsMap method in FeeGroupHandler class.");
	       return feeGroupMap;
	   }	
	   
	   /**
	    * This method is used to get FeeNonOptionalGroup details as Map.
	    * @return list of FeeCategories objects.
	    */
	   public Map<Integer,String> getFeeNonOptionalGroupsMap() throws Exception {
		   log.info("call of getFeeNonOptionalGroupsMap method in FeeGroupHandler class.");
		       IFeeGroupTransaction feeGroupTransaction= FeeGroupTransactionImpl.getInstance();
			   List<FeeGroup> feeGroupList = feeGroupTransaction.getNonOptionalFeeGroups();
			   Map<Integer,String> feeGroupMap = new HashMap<Integer,String>();
			   Iterator<FeeGroup> itr = feeGroupList.iterator();
			   FeeGroup feeGroup;
			   while(itr.hasNext()) {
				   feeGroup = itr.next();
				   if(feeGroup.getFeeHeadings().size() != 0) {
					   Iterator<FeeHeading> itr2 = feeGroup.getFeeHeadings().iterator();
					   while(itr2.hasNext()){
						   if(itr2.next().getIsActive() == true){
							   feeGroupMap.put(Integer.valueOf(feeGroup.getId()), feeGroup.getName());
							   break;
						   }	   
					   }
				   }	   
			   }
		   log.info("end of getFeeNonOptionalGroupsMap method in FeeGroupHandler class.");
		   return feeGroupMap;
	   }	
	   
	   /**
	    * This method is used to get FeeHeadingsByGroup details.
	    * @param groupId
	    * @return
	    * @throws Exception
	    */
	   public Map<Integer,String> getFeeHeadingsByGroup(int groupId) throws Exception {
		   log.info("call of getFeeHeadingsByGroup method in FeeGroupHandler class.");
	   	   IFeeGroupTransaction feeGroupTransaction= FeeGroupTransactionImpl.getInstance();
		   FeeGroup feeGroup = feeGroupTransaction.getFeeGroup(groupId);
		   Map<Integer,String> feeHeadingMap = new HashMap<Integer,String>();
		   Iterator<FeeHeading> itr = feeGroup.getFeeHeadings().iterator();
		   FeeHeading feeHeading;
		   while(itr.hasNext()) {
			   feeHeading = itr.next();
			   if(feeHeading.getIsActive() == true) {
				   feeHeadingMap.put(Integer.valueOf(feeHeading.getId()), feeHeading.getName());
			   }	   
		   }
		   log.info("end of getFeeHeadingsByGroup method in FeeGroupHandler class.");
	       return feeHeadingMap;
    }
	   
	   /**
	    * This method is used to add Fee Group details to database.
	    * @param feeGroupEntryForm
	    * @throws DuplicateException
	    * @throws ReActivateException
	    * @throws Exception
	    */
	   
	   public void addFeeGroup(FeeGroupEntryForm feeGroupEntryForm)
		throws DuplicateException, ReActivateException, Exception {
		   log.info("call of addFeeGroup method in FeeGroupHandler class.");
		IFeeGroupTransaction feeGroupTransaction = FeeGroupTransactionImpl
				.getInstance();
		feeGroupTransaction.addFeeGroupEntry(feeGroupEntryForm
				.getFeeGroupName(),feeGroupEntryForm.getOptional(),feeGroupEntryForm.getUserId());
		 log.info("end of addFeeGroup method in FeeGroupHandler class.");
        }
	   
	   /**
	    * This method is used to deleteFeeGroup details from database.
	    * @param feeGroupId
	    * @param userId
	    * @throws Exception
	    */
	   
	   public void deleteFeeGroupEntry(int feeGroupId, String userId) throws Exception {
		   log.info("call of deleteFeeGroupEntry method in FeeGroupHandler class.");
			IFeeGroupTransaction feeGroupTransaction = FeeGroupTransactionImpl
					.getInstance();
			feeGroupTransaction.deleteFeeGroupEntry(feeGroupId,userId);
			log.info("end of deleteFeeGroupEntry method in FeeGroupHandler class.");
		}

	   /**
	    * This method is used to update FeeGroup details in database.
	    * @param feeGroupId
	    * @param feeGroupName
	    * @param option
	    * @param feeGroupNameOriginal
	    * @param userId
	    * @throws DuplicateException
	    * @throws ReActivateException
	    * @throws Exception
	    */
	   
		public void updateFeeGroupEntry(int feeGroupId, String feeGroupName, String option, String feeGroupNameOriginal, String userId)
				throws DuplicateException,ReActivateException,Exception {
			log.info("call of updateFeeGroupEntry method in FeeGroupHandler class.");
			IFeeGroupTransaction feeGroupTransaction = FeeGroupTransactionImpl
					.getInstance();
			feeGroupTransaction.updateFeeGroupEntry(feeGroupId,
					feeGroupName, option,feeGroupNameOriginal, userId);
			log.info("end of updateFeeGroupEntry method in FeeGroupHandler class.");
		}
		
		/**
		 * This method is used to restore FeeGroup details from database.
		 * @param feeGroupName
		 * @param userId
		 * @throws Exception
		 */
		
		public void reActivateFeeGroup(String feeGroupName, String userId) throws Exception {
			IFeeGroupTransaction feeGroupTransaction = FeeGroupTransactionImpl
			.getInstance();
			log.info("call of reActivateFeeGroup method in FeeGroupHandler class.");
				feeGroupTransaction.reActivateFeeGroupEntry(feeGroupName,userId);
			log.info("end of reActivateFeeGroup method in FeeGroupHandler class.");
		}
		
		/**
		 * This method is used to get OptionalFeeGroup details as Map.
		 * @return Map.
		 * @throws Exception
		 */
		
	 public Map<Integer,String> getOptionalFeeGroupsMap() throws Exception {
		 log.info("call of getOptionalFeeGroupsMap method in FeeGroupHandler class.");
		   	   IFeeGroupTransaction feeGroupTransaction= FeeGroupTransactionImpl.getInstance();
			   List<FeeGroup> feeGroupList = feeGroupTransaction.getOptionalFeeGroups();
			   Map<Integer,String> feeGroupMap = new HashMap<Integer,String>();
			   Iterator<FeeGroup> itr = feeGroupList.iterator();
			   FeeGroup feeGroup;
			   while(itr.hasNext()) {
				   feeGroup = itr.next();
				   if(feeGroup.getFeeHeadings().size() != 0)
					   feeGroupMap.put(Integer.valueOf(feeGroup.getId()), feeGroup.getName());
			   }
		   log.info("end of getOptionalFeeGroupsMap method in FeeGroupHandler class.");
	       return feeGroupMap;
	   }	
}