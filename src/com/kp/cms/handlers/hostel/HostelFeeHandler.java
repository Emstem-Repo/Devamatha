package com.kp.cms.handlers.hostel;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.HlFeeType;
import com.kp.cms.bo.admin.HlFees;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.HostelFeesForm;
import com.kp.cms.helpers.hostel.HostelFeeHelper;
import com.kp.cms.to.hostel.HostelFeesTo;
import com.kp.cms.to.hostel.HostelFeesTypeTo;
import com.kp.cms.transactions.hostel.IHostelFeeTransactions;
import com.kp.cms.transactionsimpl.hostel.HostelFeeTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HostelFeeHandler {
	private static Log log = LogFactory.getLog(HostelFeeHandler.class);
	private static volatile HostelFeeHandler hostelFeeHandler = null;
	IHostelFeeTransactions transaction = HostelFeeTransactionImpl.getInstance();

	private HostelFeeHandler() {
	}

	/**
	 * This method returns the single instance of this HostelAllocationHandler.
	 * 
	 * @return
	 */
	public static HostelFeeHandler getInstance() {
		if (hostelFeeHandler == null) {
			hostelFeeHandler = new HostelFeeHandler();
		}
		return hostelFeeHandler;
	}
	
	public List<HostelFeesTypeTo> getFeeListToDisplay() throws Exception {
		log.debug("Entering getFeeListToDisplay in HostelFeeHandler");
		List<HlFeeType> feeListBo = transaction.getFeeListToDisplay();
		List<HostelFeesTypeTo> feeListTo = HostelFeeHelper.getInstance().copyFeeBosToTos(feeListBo);
		log.debug("Exiting getFeeListToDisplay in HostelFeeHandler");
		return feeListTo;
	}
	
	public List<HostelFeesTo> getFeeDetailedListToDisplay() throws Exception {
		log.debug("Entering getFeeDetailedListToDisplay in HostelFeeHandler");
		List<HlFees> feeListBo = transaction.getFeeDetailedListToDisplay();
		List<HostelFeesTo> feeDetailedListTo = HostelFeeHelper.getInstance().copyFeeDetailedListBosToTos(feeListBo);
		log.debug("Exiting getFeeDetailedListToDisplay in HostelFeeHandler");
		return feeDetailedListTo;
	}
	public List<HostelFeesTo> getFeeDetailedListToView(int hostelId,int roomTypeId) throws Exception {
		log.debug("Entering getFeeDetailedListToDisplay in HostelFeeHandler");
		List<HlFees> feeListBo = transaction.getFeeDetailedListToView(hostelId,roomTypeId);
		List<HostelFeesTo> feeDetailedListTo = HostelFeeHelper.getInstance().copyFeeDetailedListBosToTosToView(feeListBo);
		log.debug("Exiting getFeeDetailedListToDisplay in HostelFeeHandler");
		return feeDetailedListTo;
	}
	
	public boolean deleteFeeDetails(int hostelId, int roomId) throws Exception {
		log.debug("Entering deleteFeeDetails in HostelFeeHandler");
		 return transaction.deleteFeeDetails(hostelId,roomId);
	}
	
/*	public List<HostelFeesTypeTo> prepareListWithAmount(HostelFeesForm hostelFeesForm) throws Exception
	{ 
		log.debug("Entering prepareListWithAmount in HostelFeeHandler");
		int i = 0;
		int j = 0;
		List<HostelFeesTypeTo> feeListTo = getFeeListToDisplay();
		String [] elements = hostelFeesForm.getElements();
		String element = elements[0];
		String []words = element.split(",");
		for (HostelFeesTypeTo hostelFeesTypeTo : feeListTo) {
			for(i = j;i<words.length;i++){
				hostelFeesTypeTo.setAmount(words[i]);
				j++;
				feeListTo.set(i, hostelFeesTypeTo);
				break;
			}
		}
		log.debug("Exiting prepareListWithAmount in HostelFeeHandler");
		return feeListTo;
	}*/
	
	public String saveFeeDetails(HostelFeesForm hostelFeesForm,List<HostelFeesTypeTo> hostelFeesTypeToList)throws Exception{
		log.info("Entering saveFeeDetails of HostelFeeHandler");
		String isSaved = "false";
		 List<HlFees> hlFeesBoList =  HostelFeeHelper.getInstance().prepareBoToSave(hostelFeesForm,hostelFeesTypeToList);
		 //check active raws are there or not already for this hostel id and roomtypeid
		 List<HlFees> boListTocheckDuplication = transaction.getFeeDetailedListToView(Integer.valueOf(hostelFeesForm.getHostelId()),Integer.valueOf(hostelFeesForm.getRoomType()));
		 //get the bo list to check weather non active records are there or not
		 List<HlFees> boListTocheckDForActive = transaction.getFeeDetailedListForActive(Integer.valueOf(hostelFeesForm.getHostelId()),Integer.valueOf(hostelFeesForm.getRoomType()));
		 if((boListTocheckDuplication==null || boListTocheckDuplication.isEmpty()) && (boListTocheckDForActive ==null || boListTocheckDForActive.isEmpty()) ) {
			 isSaved = transaction.saveFeeDetails(hlFeesBoList); 
		 }
		 else if((boListTocheckDuplication !=null && !boListTocheckDuplication.isEmpty()) && (boListTocheckDForActive ==null || boListTocheckDForActive.isEmpty())){
			 isSaved = CMSConstants.HOSTEL_FEES_ACTIVEEXISTS;
			 
		 }
		 else if((boListTocheckDForActive !=null || !boListTocheckDForActive.isEmpty())){
			 isSaved =CMSConstants.HOSTEL_FEES_NONACTIVEEXISTS;
			
		 }
		 log.info("Exiting saveFeeDetails of HostelFeeHandler");
		return  isSaved;
		
	}
	
	
	public boolean reActivateFeeDetails(int hostelid,int roomType, String userId)throws Exception
	{
		log.info("Entering to reActivateFeeDetails of HostelFeeHandler");
		List<HlFees> boListTocheckDForActive = transaction.getFeeDetailedListForActive(hostelid,roomType);
		List<HlFees> boListToUpdate = HostelFeeHelper.getInstance().prepareBoListToMakeActive(boListTocheckDForActive,userId);
		if(transaction!=null)
		{
			return transaction.reActivateFeeList(boListToUpdate);
		}
		log.info("Exiting reActivateFeeDetails of HostelFeeHandler");
		return false;
	}
	
	
	public String checkForAmountFeildsAreNull(List<HostelFeesTypeTo> listWithAmount) throws Exception
	{ 
		log.debug("Entering checkForAmountFeildsAreNull in HostelFeeHandler");
		String status="empty";
		List<HostelFeesTypeTo> feeListTo = listWithAmount;
		
		for (HostelFeesTypeTo hostelFeesTypeTo : feeListTo) {
			if(hostelFeesTypeTo.getAmount()==null || hostelFeesTypeTo.getAmount().isEmpty()){
				status ="empty";
			}
			else
			{
				status ="notempty";
				return status;
			}
		}
		log.debug("Exiting checkForAmountFeildsAreNull in HostelFeeHandler");
		return status;
	}

	public List<HostelFeesTypeTo> getFeeDetailsToEdit(HostelFeesForm hostelFeesForm)throws Exception {
		log.debug("get fee deatils to editg in HostelFeeHandler");
		int hostelId = Integer.parseInt(hostelFeesForm.getHostelId());
		int roomTypeId = Integer.parseInt(hostelFeesForm.getRoomType());
		List<HlFees> feeListBo = transaction.getFeeDetailedListToView(hostelId,roomTypeId);
		List<HostelFeesTypeTo>feeListToDisplay=getFeeListToDisplay();
		List<HostelFeesTypeTo> feeDetailsList=HostelFeeHelper.getInstance().convertFeesToFeesType(feeListBo,hostelFeesForm,feeListToDisplay);
		return feeDetailsList;
	}
	
	public boolean updateFeeDetails(HostelFeesForm hostelFeesForm,List<HostelFeesTypeTo> hostelFeesTypeToList)throws Exception
	{
		
		List<HlFees> feeListBo = HostelFeeHelper.getInstance().updateFeeDetailsTosBos(hostelFeesForm, hostelFeesTypeToList);
		if(transaction != null)
		{
			return transaction.updateFeeDetails(feeListBo);
		}
		
		return false;
		
	}
	
	public void validateFeeAmount(List<HostelFeesTypeTo> listWithAmount,ActionErrors errors)throws Exception{
		
		String valid ="false";
		List<HostelFeesTypeTo> feeListTo = listWithAmount;
		for (HostelFeesTypeTo hostelFeesTypeTo : feeListTo) {
			if(hostelFeesTypeTo.getAmount()!=null && !hostelFeesTypeTo.getAmount().isEmpty()){
				if(!CommonUtil.isValidDecimal(hostelFeesTypeTo.getAmount()))
				{
					errors.add("errors",new ActionError(""));
				}
			}
		}
	}
	
}
