package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.MobNewsEventsCategory;
import com.kp.cms.forms.admin.MobNewsEventsCategoryForm;
import com.kp.cms.to.admin.CurrencyMasterTO;
import com.kp.cms.to.admin.MobNewsEventsCategoryTO;
import com.kp.cms.transactions.admin.ICurrencyMasterTransaction;
import com.kp.cms.transactions.admin.IMobNewsEventsCategoryTransaction;
import com.kp.cms.transactionsimpl.admin.CurrencyMasterTransactionImpl;
import com.kp.cms.transactionsimpl.admin.MobNewsEventsCategoryTransactionImpl;

public class MobNewsEventsCategoryHandler {
	
	
	private static final Logger log=Logger.getLogger(MobNewsEventsCategoryHandler.class);	
	public static volatile MobNewsEventsCategoryHandler mobNewsEventsCategoryHandler = null;

	/**
	 * This method is used to create a single instance of MobNewsEventsCategoryHandler.
	 * @return unique instance of MobNewsEventsCategoryHandler class.
	 */
     public static MobNewsEventsCategoryHandler getInstance()
     {
    	 if (mobNewsEventsCategoryHandler == null) {
    		 mobNewsEventsCategoryHandler = new MobNewsEventsCategoryHandler();
 			return mobNewsEventsCategoryHandler;
 		}
		return mobNewsEventsCategoryHandler;
    	 
     }
     /**
 	 * This method will get MobNewsEventsCategory  by calling IMPL class.
 	 * @return list of type MobNewsEventsCategoryTO.
 	 * @throws Exception
 	 */
     public List<MobNewsEventsCategoryTO> getMobNewsEventsCategory() throws Exception{
 		log.info("call of getMobNewsEventsCategory method in mobNewsEventsCategoryHandler class.");
 		List<MobNewsEventsCategoryTO> mobNewsEventsCategoryList = new ArrayList<MobNewsEventsCategoryTO>();
 		IMobNewsEventsCategoryTransaction mobNewsEventsCategoryTransaction=MobNewsEventsCategoryTransactionImpl.getInstance();
 		List<MobNewsEventsCategory> list=mobNewsEventsCategoryTransaction.getMobNewsEventsCategory();
 		Iterator<MobNewsEventsCategory> itr = list.iterator();
 		MobNewsEventsCategory mobNewsEventsCategory; 
 		MobNewsEventsCategoryTO mobNewsEventsCategoryTO;
		while (itr.hasNext()) {
			mobNewsEventsCategory = (MobNewsEventsCategory) itr.next();
			mobNewsEventsCategoryTO = new MobNewsEventsCategoryTO();
			
			mobNewsEventsCategoryTO.setId(mobNewsEventsCategory.getId());
			mobNewsEventsCategoryTO.setCategory(mobNewsEventsCategory.getCategory());
			mobNewsEventsCategoryList.add(mobNewsEventsCategoryTO);
		}
		log.info("end of getCategory method in MobNewsEventCategoryHandler class.");
 		
		return mobNewsEventsCategoryList;
	}
	public  MobNewsEventsCategory isCategoryExist(String category) throws Exception {
		
		log.info("call of isCategoryExist method in MobNewsEventCategoryHandler class.");
		IMobNewsEventsCategoryTransaction mobNewsEventsCategoryTransaction = MobNewsEventsCategoryTransactionImpl.getInstance();
		MobNewsEventsCategory mobNewsEventsCategory = mobNewsEventsCategoryTransaction.isCategoryExis(category);
		log.info("end of isCategoryExist method in MobNewsEventCategoryHandler class.");
		return mobNewsEventsCategory;
	}
	// End of is categoryExist 	
	//add method
	public boolean addMobNewsEventsCategory(
			MobNewsEventsCategoryForm mobNewsEventsCategoryFrom)throws Exception {
		IMobNewsEventsCategoryTransaction mobNewsEventsCategoryTransaction=MobNewsEventsCategoryTransactionImpl.getInstance();
		MobNewsEventsCategory mobNewsEventsCategory=new MobNewsEventsCategory();
		mobNewsEventsCategory.setCreatedDate(new Date());
		mobNewsEventsCategory.setCreatedBy(mobNewsEventsCategoryFrom.getUserId());
		mobNewsEventsCategory.setCategory(mobNewsEventsCategoryFrom.getCategory());
		mobNewsEventsCategory.setModifiedBy(mobNewsEventsCategoryFrom.getUserId());
		mobNewsEventsCategory.setIsActive(true);
		mobNewsEventsCategory.setLastModifiedDate(new Date());
		boolean isAdded =mobNewsEventsCategoryTransaction.addMobNewsEventsCategory(mobNewsEventsCategory);
		log.info("end of addMobNewsEventsCategory method in MobNewsEventsCategoryHandler class.");
		return isAdded;
	}
	//end of add mob news and events category master 
	
	
	public MobNewsEventsCategoryTO editMobNewsEventsCategory(
			int mobNewsEventsCategoryId) throws Exception{
		log.info("call of editMobNewsEventsCategory method in MobNewsEventsCategoryHandler class.");
		MobNewsEventsCategoryTO mobNewsEventsCategoryTO= new MobNewsEventsCategoryTO();
		IMobNewsEventsCategoryTransaction mobNewsEventsCategoryTransaction=MobNewsEventsCategoryTransactionImpl.getInstance();
		MobNewsEventsCategory mobNewsEventsCategory=mobNewsEventsCategoryTransaction.editMobNewsEventsCategory(mobNewsEventsCategoryId);
		mobNewsEventsCategoryTO.setId(mobNewsEventsCategory.getId());
		mobNewsEventsCategoryTO.setCategory(mobNewsEventsCategory.getCategory());
		log.info("end of editMobNewsEventsCategory method in MobNewsEventsCategoryHandler class.");
		return mobNewsEventsCategoryTO;
	}
	
	public boolean updateMobNewsEventsCategory(
			MobNewsEventsCategoryForm mobNewsEventsCategoryForm) throws Exception {
		log.info("call of updateMobNewsEventsCategory method in MobNewsEventsCategoryHandler class.");
		IMobNewsEventsCategoryTransaction mobNewsEventsCategoryTransaction=MobNewsEventsCategoryTransactionImpl.getInstance();
		MobNewsEventsCategory mobNewsEventsCategory = new MobNewsEventsCategory();
		mobNewsEventsCategory.setId(mobNewsEventsCategoryForm.getMobNewsEventsCategoryId());
		mobNewsEventsCategory.setCategory(mobNewsEventsCategoryForm.getCategory());
		mobNewsEventsCategory.setModifiedBy(mobNewsEventsCategoryForm.getUserId());
		mobNewsEventsCategory.setLastModifiedDate(new Date());
		mobNewsEventsCategory.setIsActive(Boolean.TRUE);
		boolean isUpdated = mobNewsEventsCategoryTransaction.updateMobNewsEventsCategory(mobNewsEventsCategory);
		log.info("end of updateMobNewsEventsCategory method in MobNewsEventsCategoryHandler class.");
		return isUpdated;
		
	}
	/**
	 * This method is used to update a record of Category  by calling MobNewsEventsCategoryTransactionImpl class.
	 * @param currencyMasterForm
	 * @return boolean value.
	 * @throws Exception
	 */
	public boolean deleteMobNewsEventsCategory(String userId,
			int mobNewsEventsCategoryId)throws Exception {
		log.info("call of deleteMobNewsEventsCategory method in MobNewsEventsCategoryHandler class.");
		IMobNewsEventsCategoryTransaction mobNewsEventsCategoryTransaction = MobNewsEventsCategoryTransactionImpl.getInstance();
		boolean isDeleted = mobNewsEventsCategoryTransaction.deleteMobNewsEventsCategory(mobNewsEventsCategoryId, userId);
		log.info("end of deleteMobNewsEventsCategory method in MobNewsEventsCategoryHandler class.");
		return isDeleted;
		
		
	}
	
	public boolean reActivateMobNewsEventsCategory(int dupId,
			String userId)throws Exception {
		
		log.info("call of reActivateMobNewsEventsCategory method in MobNewsEventsCategoryHandler class.");
		IMobNewsEventsCategoryTransaction mobNewsEventsCategoryTransaction = MobNewsEventsCategoryTransactionImpl.getInstance();
		boolean isReactivated = mobNewsEventsCategoryTransaction.reActivateMobNewsEventsCategory(dupId, userId);
		log.info("end of reActivateMobNewsEventsCategory method in MobNewsEventsCategoryHandler class.");
		
		return isReactivated;
	}

	
	 
     
     
     
}
