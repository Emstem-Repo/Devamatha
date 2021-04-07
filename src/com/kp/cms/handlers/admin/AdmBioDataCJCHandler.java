package com.kp.cms.handlers.admin;

import java.util.List;

import com.kp.cms.bo.admin.AdmBioDataCJC;
import com.kp.cms.forms.admin.AdmBioDataCJCForm;
import com.kp.cms.helpers.admin.AdmBioDataCJCHelper;
import com.kp.cms.to.admin.AdmBioDataCJCTo;
import com.kp.cms.transactions.admin.IAdmBioDataCJCTransaction;
import com.kp.cms.transactionsimpl.admin.AdmBioDataCJCTransctionImpl;

public class AdmBioDataCJCHandler {
	private static volatile AdmBioDataCJCHandler admBioDataCJCHandler=null;
	private AdmBioDataCJCHandler(){
		
	}
	public static AdmBioDataCJCHandler getInstance(){
		if(admBioDataCJCHandler == null){
			admBioDataCJCHandler = new AdmBioDataCJCHandler();
			return admBioDataCJCHandler;
		}
		return admBioDataCJCHandler;
	}
	/**
	 * @param admBioDataForm 
	 * @param admBioDataList
	 * @return
	 * @throws Exception 
	 */
	public boolean addAdmBioData(List<AdmBioDataCJCTo> admBioDataToLList, AdmBioDataCJCForm admBioDataForm) throws Exception {
		IAdmBioDataCJCTransaction transaction = AdmBioDataCJCTransctionImpl.getInstance();
		List<AdmBioDataCJC> list = AdmBioDataCJCHelper.getInstance().populateToTOBO(admBioDataToLList);
		return transaction.addAdmBioData(list,admBioDataForm);
	}
}
