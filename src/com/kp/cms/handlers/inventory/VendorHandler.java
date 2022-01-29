package com.kp.cms.handlers.inventory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvItemCategory;
import com.kp.cms.bo.admin.InvVendor;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.inventory.VendorForm;
import com.kp.cms.helpers.admin.SingleFieldMasterHelper;
import com.kp.cms.helpers.inventory.VendorHelper;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.VendorTO;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.inventory.IVendorTransactions;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.inventory.VendorTransactionImpl;


public class VendorHandler {
	private static Log log = LogFactory.getLog(VendorHandler.class); 
	public static volatile VendorHandler vendorHandler;
	public static VendorHandler getInstance() {
		if (vendorHandler == null) {
			vendorHandler = new VendorHandler();
		}
		return vendorHandler;
	}
	/**
	 * setting categoryMap for multi select
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getCategoryMap() throws Exception{
		log.debug("inside getCategoryMap");
		ISingleFieldMasterTransaction iTransactions = SingleFieldMasterTransactionImpl.getInstance();
		List<InvItemCategory> invItemCategory = iTransactions.getItemCategory();
		List<SingleFieldMasterTO> catgList = SingleFieldMasterHelper.getInstance().copySingleFieldMasterHelper(invItemCategory, "InvItemCategory");
		Iterator<SingleFieldMasterTO> i = catgList.iterator();
		SingleFieldMasterTO singleFieldMasterTO;
		Map<Integer, String> categoryMap = new HashMap<Integer, String>();
		while (i.hasNext()){
			singleFieldMasterTO = i.next();
			categoryMap.put(singleFieldMasterTO.getId(), singleFieldMasterTO.getName());
		}
		log.debug("exit getCategoryMap");
		return categoryMap;
	}

	/**
	 * 
	 * @param vendorForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addVendor(VendorForm vendorForm, String mode) throws Exception {
		log.debug("inside addVendor");
		IVendorTransactions iTransactions = VendorTransactionImpl.getInstance();
		boolean isAdded = false;
		//duplication checking
		
		InvVendor invVendor =  iTransactions.isVendorNameDuplcated(vendorForm.getVendorName(), vendorForm.getId());
		
		if (invVendor != null && invVendor.getIsActive()) {
			throw new DuplicateException();
		}
		else if (invVendor != null && !invVendor.getIsActive())
		{
			vendorForm.setDuplId(invVendor.getId());
			throw new ReActivateException();
		}		
	
		if(mode.equalsIgnoreCase("add")){
			invVendor = VendorHelper.getInstance().copyFromFormToBO(vendorForm);
		}
		else
		{
			invVendor = VendorHelper.getInstance().copyFromFormToBOForUpdate(vendorForm);
		}
		isAdded = iTransactions.addVendor(invVendor, mode);
		log.debug("leaving addVendor");
		return isAdded;
	}
	/**
	 * 
	 * @return list of VendorTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<VendorTO> getVendorDetails() throws Exception {
		log.debug("inside getVendorDetails");
		IVendorTransactions iTransactions = VendorTransactionImpl.getInstance();
		List<InvVendor> vendorList = iTransactions.getVendorDetails();
		List<VendorTO> vendorTOList = VendorHelper.getInstance().copyVendorBosToTos(vendorList);
		log.debug("leaving getHostelDetails");
		return vendorTOList;
	}
	  	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	
	public InvVendor getVendor(int id) throws Exception {
		log.debug("inside getVendor");
		IVendorTransactions iTransactions = VendorTransactionImpl.getInstance();
		InvVendor invVendor = iTransactions.getVendor(id);
		log.debug("leaving getVendor");
		return invVendor;
	}
	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */

	public boolean deleteVendor(int id, Boolean activate, VendorForm vendorForm) throws Exception {
		IVendorTransactions iVendorTransactions = VendorTransactionImpl.getInstance();
		return iVendorTransactions.deleteVendor(id, activate, vendorForm);
	}

	  		
}
