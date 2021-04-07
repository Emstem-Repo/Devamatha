package com.kp.cms.handlers.sap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.sap.SapVenue;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.sap.SapVenueForm;
import com.kp.cms.helpers.sap.SapVenueHelper;
import com.kp.cms.to.admin.EmployeeWorkLocationTO;
import com.kp.cms.to.sap.SapVenueTO;
import com.kp.cms.transactions.sap.ISapVenueTransactions;
import com.kp.cms.transactionsimpl.sap.SapVenueTransactionImpl;

public class SapVenueHandler {
	
	public static Log log = LogFactory.getLog(SapVenueHandler.class);
	public static volatile SapVenueHandler sapVenueHandler;
	
	public static SapVenueHandler getInstance(){
		if(sapVenueHandler == null){
			sapVenueHandler = new SapVenueHandler();
			return sapVenueHandler;
		}
		return sapVenueHandler;
	}
	
	/**
	 * 
	 * @return list of LocationTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<EmployeeWorkLocationTO> getLocationList() throws Exception {
		log.debug("inside getLocationList");
		ISapVenueTransactions iSapVenueTransactions = SapVenueTransactionImpl.getInstance();
		List<EmployeeWorkLocationBO> locationList = iSapVenueTransactions.getLocationList();
		List<EmployeeWorkLocationTO> locationTOList = SapVenueHelper.getInstance().copyLocationBosToTos(locationList);
		log.debug("leaving getLocationList");
		return locationTOList;
	}
	
	public SapVenue checkForDuplicateonNameAndLoc(String workLocId, String venue)throws Exception
	{
		ISapVenueTransactions iSapVenueTransactions = SapVenueTransactionImpl.getInstance();
		return iSapVenueTransactions.checkForDuplicateonNameAndLoc(workLocId, venue);
	}
	
	public boolean addSapVenue(SapVenueForm sapVenueForm)throws Exception
	{
		ISapVenueTransactions iSapVenueTransactions = SapVenueTransactionImpl.getInstance();
		SapVenue sapVenue = new SapVenue();
		if(sapVenueForm!=null){
			
			sapVenue.setVenueName(sapVenueForm.getVenueName());
			sapVenue.setCapacity(Integer.parseInt(sapVenueForm.getCapacity()));
			sapVenue.setCreatedBy(sapVenueForm.getUserId());
			sapVenue.setCreatedDate(new Date());
			sapVenue.setModifiedBy(sapVenueForm.getUserId());
			sapVenue.setLastModifiedDate(new Date());
			sapVenue.setIsActive(true);
			
			EmployeeWorkLocationBO locationBO = new EmployeeWorkLocationBO();
			locationBO.setId(Integer.parseInt(sapVenueForm.getWorkLocId()));
			sapVenue.setWorkLocationId(locationBO);
		}
			return iSapVenueTransactions.addSapVenue(sapVenue);
		
	}
	
	public List<SapVenueTO> getSapVenueDetails()throws Exception
	{
		ISapVenueTransactions iSapVenueTransactions = SapVenueTransactionImpl.getInstance();
		List<SapVenue> venueList =iSapVenueTransactions.getSapVenueDetails();
		if(venueList!=null && !venueList.isEmpty()){
			return SapVenueHelper.getInstance().pupulateSapVenueBOtoTO(venueList);
		}
		return new ArrayList<SapVenueTO>();
	}
	
	public boolean deleteSapVenue(int sapVenueId, String userId)throws Exception
	{
		ISapVenueTransactions iSapVenueTransactions = SapVenueTransactionImpl.getInstance();
		if(iSapVenueTransactions!=null){
			return iSapVenueTransactions.deleteSapVenue(sapVenueId, userId);
		}
		return false;
	}
	
	public SapVenue getDetailsonId(int venueId) throws Exception
	{
		ISapVenueTransactions iSapVenueTransactions = SapVenueTransactionImpl.getInstance();
		return iSapVenueTransactions.getDetailsonId(venueId);
	}
	
	public boolean updateSapVenue(SapVenueForm byForm)throws Exception
	{
		log.info("Inside of updateSapVenue of SapVenueHandler");
		ISapVenueTransactions iSapVenueTransactions = SapVenueTransactionImpl.getInstance();
		SapVenue sapVenue = new SapVenue();
		boolean orgVenueName=false;
		orgVenueName = iSapVenueTransactions.checkForDuplicateonNameUpdate(byForm);
		if (orgVenueName){
			throw new DuplicateException();
		}
		else{
		if(byForm!=null){
			sapVenue.setId(byForm.getId());
			sapVenue.setVenueName(byForm.getVenueName());
			sapVenue.setCapacity(Integer.parseInt(byForm.getCapacity()));
			sapVenue.setModifiedBy(byForm.getUserId());
			sapVenue.setLastModifiedDate(new Date());
			sapVenue.setIsActive(true);
			
			EmployeeWorkLocationBO employeeWorkLocationBO = new EmployeeWorkLocationBO();
			employeeWorkLocationBO.setId(Integer.parseInt(byForm.getWorkLocId()));
			sapVenue.setWorkLocationId(employeeWorkLocationBO);
			
		}
		log.info("Leaving of updateSapVenue of SapVenueHandler");
		return iSapVenueTransactions.updateSapVenue(sapVenue);				
		
		}
	}
	
	public boolean reActivateSapVenue(String venueName, String userId)throws Exception
	{
	log.info("Inside into reActivateSapVenue of HostelBlocksHandler");
	ISapVenueTransactions iSapVenueTransactions = SapVenueTransactionImpl.getInstance();
	if(iSapVenueTransactions!=null){
		return iSapVenueTransactions.reActivateSapVenue(venueName, userId);
	}
	log.info("Leaving of reActivateSapVenue of HostelBlocksHandler");
	return false;
	}
	

}
