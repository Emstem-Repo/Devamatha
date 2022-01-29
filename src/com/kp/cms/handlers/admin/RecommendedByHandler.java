package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.kp.cms.bo.admin.Recommendor;
import com.kp.cms.forms.admin.RecommendedByForm;
import com.kp.cms.helpers.admin.RecommendedByHelper;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.admin.RecommendedByTO;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.transactions.admin.IRecommendedByTransaction;
import com.kp.cms.transactionsimpl.admin.RecommendedByTransactionImpl;

public class RecommendedByHandler {
	private static final Log log = LogFactory.getLog(RecommendedByHandler.class);
	
	private static volatile RecommendedByHandler recommendedByHandler = null;

	private RecommendedByHandler() {
	}
	/**
	 * 
	 * @returns a single instance when called.
	 */
	public static RecommendedByHandler getInstance() {
		if (recommendedByHandler == null) {
			recommendedByHandler = new RecommendedByHandler();
		}
		return recommendedByHandler;
	}
	
	IRecommendedByTransaction transaction=new RecommendedByTransactionImpl();
	
	/**
	 * 
	 * @param recommendedByForm
	 * @return Used to add a recommended By
	 * @throws Exception
	 */
	public boolean addRecommendedBy(RecommendedByForm recommendedByForm)throws Exception
	{
		//Capture the formbean properties. and set those to recommendedByTO
		
		RecommendedByTO recommendedByTO=new RecommendedByTO();
		if(recommendedByForm!=null){
			recommendedByTO.setCode(recommendedByForm.getCode());
			recommendedByTO.setName(recommendedByForm.getName());
			if(recommendedByForm.getAddressLine1()!=null && !recommendedByForm.getAddressLine1().isEmpty()){
			recommendedByTO.setAddressLine1(recommendedByForm.getAddressLine1());
			}
			if(recommendedByForm.getAddressLine2()!=null && !recommendedByForm.getAddressLine2().isEmpty()){
			recommendedByTO.setAddressLine2(recommendedByForm.getAddressLine2());
			}
			recommendedByTO.setCity(recommendedByForm.getCity());
			StateTO stateTO=new StateTO();
			stateTO.setId(Integer.parseInt(recommendedByForm.getStateId()));
			recommendedByTO.setStateTO(stateTO);
			CountryTO countryTO=new CountryTO();
			countryTO.setId(Integer.parseInt(recommendedByForm.getCountryId()));
			recommendedByTO.setCountryTO(countryTO);
			recommendedByTO.setPhone(recommendedByForm.getPhone());
			recommendedByTO.setCreatedBy(recommendedByForm.getUserId());
			recommendedByTO.setModifiedBy(recommendedByForm.getUserId());
			if(recommendedByForm.getComments()!=null && !recommendedByForm.getComments().isEmpty())
			{
			recommendedByTO.setComments(recommendedByForm.getComments());
		}
		}
		Recommendor recommendor=RecommendedByHelper.getInstance().populateTOtoBO(recommendedByTO);
		if(transaction!=null){
			return transaction.addRecommendedBy(recommendor);
		}
		log.info("Leaving of addRecommendedBy of RecommendedByHandler");
		return false;
	}
	
	/**
	 * Gets all the recommendedBy where isActive=1
	 */
	
	public List<RecommendedByTO> getRecommendedByDetails()throws Exception
	{
		List<Recommendor> recommendedList =transaction.getRecommendedByDetails();
		if(recommendedList!=null && !recommendedList.isEmpty()){
			return RecommendedByHelper.getInstance().pupulateRecommendorBOtoTO(recommendedList);
		}
		log.info("Leaving from getRecommendedByDetails of RecommendedByHandler");
		return new ArrayList<RecommendedByTO>();
	}
	

	/**
	 * Deletes a recommendedBy for a code
	 */
	
	public boolean deleteRecommendedBy(int recommendedById, String userId)throws Exception{
		if(transaction!=null){
			return transaction.deleteRecommendedBy(recommendedById, userId);
		}
		log.info("Leaving of deleteRecommendedBy of RecommendedByHandler");
		return false;
	}
	
	/**
	 * Updates a recommendedBy
	 * First converts forbean properties to TO and then send helper to construct a BO
	 */
	
	
	public boolean updateRecommendedBy(RecommendedByForm byForm)throws Exception
	{
		RecommendedByTO byTO=new RecommendedByTO();
		
		if(byForm!=null){
			byTO.setId(byForm.getId());
			byTO.setCode(byForm.getCode());
			byTO.setName(byForm.getName());
			byTO.setAddressLine1(byForm.getAddressLine1());
			byTO.setModifiedBy(byForm.getUserId());
			if(byForm.getAddressLine2()!=null && !byForm.getAddressLine2().isEmpty()){
			byTO.setAddressLine2(byForm.getAddressLine2());
			}
			byTO.setCity(byForm.getCity());
			byTO.setPhone(byForm.getPhone());
			if(byForm.getComments()!=null && !byForm.getComments().isEmpty()){
			byTO.setComments(byForm.getComments());
			}
			CountryTO countryTO=new CountryTO();
			countryTO.setId(Integer.parseInt(byForm.getCountryId()));
			byTO.setCountryTO(countryTO);
			StateTO stateTO=new StateTO();
			stateTO.setId(Integer.parseInt(byForm.getStateId()));
			byTO.setStateTO(stateTO);
		}
	    Recommendor recommendor=RecommendedByHelper.getInstance().populateTotoBoUpdate(byTO);
		if(transaction!=null){
			return transaction.updateRecommendedBy(recommendor);				
		}
		log.info("Leaving of updateRecommendedBy of RecommendedByHandler");
		return false;		
	}
	
	/**
	 * Checks for duplicate record on code of recommendedBy
	 */
	
	public Recommendor checkForDuplicateonCode(String code)throws Exception
	{
		log.info("Inside into checkForDuplicateonCode of RecommendedByHandler");
		return transaction.checkForDuplicateonCode(code);
		
	}
	
	/**
	 * Reactivate recommendedBy (Makes isActive=1 if it is 0)
	 */
	
	public boolean reActivateRecommendedBy(String code, String userId)throws Exception
	{
	if(transaction!=null){
		return transaction.reActivateRecommendedBy(code, userId);
	}
	log.info("Leaving of reActivateRecommendedBy of RecommendedByHandler");
	return false;
	}
	
	/**
	 * Gets a row based on the Id
	 */
	public RecommendedByTO getDetailsonId(int recId) throws Exception{
		Recommendor recommendor=transaction.getDetailsonId(recId);
		log.info("End of getDetailsonId of RecommendedByHandler");
		return RecommendedByHelper.getInstance().populateBotoToEdit(recommendor);
	}
}


