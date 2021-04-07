package com.kp.cms.handlers.hostel;

import java.util.Iterator;
import java.util.List;
import com.kp.cms.constants.CMSConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlStatus;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.HlCheckinCheckoutFacility;
import com.kp.cms.bo.admin.HlApplicationForm; 
import com.kp.cms.forms.hostel.HostelCheckinForm;
import com.kp.cms.helpers.hostel.HostelCheckinHelper;
import com.kp.cms.to.hostel.HostelCheckinTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.transactions.hostel.IHostelCheckinTransactions;
import com.kp.cms.transactionsimpl.hostel.HostelCheckinTransactionImpl;

public class HostelCheckinHandler {
	private static Log log = LogFactory.getLog(HostelCheckinHandler.class);
	private static volatile HostelCheckinHandler hostelCheckinHandler = null;
	IHostelCheckinTransactions transaction = HostelCheckinTransactionImpl
			.getInstance();

	private HostelCheckinHandler() {
	}

	/**
	 * This method returns the single instance of this HostelCheckinHandler.
	 * 
	 * @return
	 */
	public static HostelCheckinHandler getInstance() {
		if (hostelCheckinHandler == null) {
			hostelCheckinHandler = new HostelCheckinHandler();
		}
		return hostelCheckinHandler;
	}

	/**
	 * 
	 * @param HostelCheckinForm
	 * @return
	 * @throws Exception
	 */
	public List<HostelCheckinTo> getApplicantHostelDetails(
			HostelCheckinForm hostelCheckinForm) throws Exception {

		log.info("Entering getApplicantHostelDetails of HostelCheckinHandler");
		HostelCheckinHelper hostelCheckinHelper = HostelCheckinHelper
				.getInstance();
		List<Object> applicantHostelDetails = transaction
				.getApplicantHostelDetailsList(hostelCheckinHelper
						.getSearchCriteria(hostelCheckinForm));
		List<HostelCheckinTo> hostelCheckinToList = hostelCheckinHelper
				.convertBOtoTOList(applicantHostelDetails,hostelCheckinForm );
		log.info("Exiting getApplicantHostelDetails of HostelCheckinHandler");
		return hostelCheckinToList;
	}

	/**
	 * saving the data to database
	 * 
	 * @param hostelCheckinForm
	 * @param hostelApplicantDetails
	 * @return
	 * @throws Exception
	 */
	public String saveCheckinDetails(HostelCheckinForm hostelCheckinForm,
			HostelCheckinTo hostelCheckinTo) throws Exception {
		boolean fecilityStatus =false;
		boolean checkinStatus =false;
		boolean status =false;
		boolean hlAppStatus =false;
		String isSaved="false";
		Integer transactionId=0;
		
		log.info("Entering saveCheckinDetails of HostelCheckinHandler");
		HostelCheckinHelper hostelCheckinHelper = HostelCheckinHelper
				.getInstance();
		HlRoomTransaction hlRoomTransaction = hostelCheckinHelper
				.saveCheckinDetails(hostelCheckinForm, hostelCheckinTo);
		//check active raws are there or not already 

		 List<HlRoomTransaction> boListTocheckDuplication = transaction.getCheckinDetailedListToView(Integer.valueOf(hostelCheckinForm.getStatusId()),Integer.valueOf(hostelCheckinForm.getRoomId()),Integer.valueOf(hostelCheckinForm.getBedNo()));
		 //get the bo list to check weather non active records are there or not
		 
		List<HlRoomTransaction> boListTocheckDForActive = transaction.getCheckinDetailedListForActive(Integer.valueOf(hostelCheckinForm.getStatusId()),Integer.valueOf(hostelCheckinForm.getRoomId()));


		 if((boListTocheckDuplication==null || boListTocheckDuplication.isEmpty()) &&(boListTocheckDForActive ==null || boListTocheckDForActive.isEmpty()) ) {
			 transactionId = transaction.saveCheckinDetails(hlRoomTransaction); 
		 }
		 else if((!boListTocheckDuplication.isEmpty() && boListTocheckDuplication !=null) && (boListTocheckDForActive.isEmpty() || boListTocheckDForActive ==null)){
			 isSaved = CMSConstants.HOSTEL_CHECKIN_ACTIVEEXISTS;			 
		 }
		 else if((boListTocheckDForActive !=null || !boListTocheckDForActive.isEmpty())){
			 isSaved =CMSConstants.HOSTEL_CHECKIN_NONACTIVEEXISTS;
		}
		
		//checkinStatus = transaction.saveCheckinDetails(hlRoomTransaction);
		int hlAppFormId= hostelCheckinForm.getFormId();
		List<HlApplicationForm>  hlAppFormDet = transaction.getAppFormDetails(hlAppFormId);
		//transaction.saveAppFormDetails
		Iterator<HlApplicationForm > appItr = hlAppFormDet.iterator();
		
		while (appItr.hasNext()) {
			HlStatus hlStatus = new HlStatus();
			hlStatus.setId(2);
			HlApplicationForm  hlApplicationForm = (HlApplicationForm ) appItr.next();
			 hlApplicationForm.setFingerPrintId(hostelCheckinForm.getFingerPrintId());
			 hlApplicationForm.setHlStatus(hlStatus);
			hlAppStatus = transaction.updateAppFormDetails(hlApplicationForm);
		}
		if(transactionId!=0)
		{	
			List<HlCheckinCheckoutFacility> facilityList = hostelCheckinHelper.saveFecilities(hostelCheckinForm, hostelCheckinTo,transactionId);
			if(facilityList!=null && !facilityList.isEmpty()){
				Iterator<HlCheckinCheckoutFacility> facilityItr=facilityList.iterator();
				while (facilityItr.hasNext()) {
					HlCheckinCheckoutFacility hlCheckinCheckoutFacility = (HlCheckinCheckoutFacility) facilityItr.next();
					fecilityStatus = transaction.saveFecilities(hlCheckinCheckoutFacility);
				}
			}else{
				fecilityStatus=true;
			}
			
		}	
		log.info("Exiting saveCheckinDetails of Checkin Handler");
		if(fecilityStatus== true && transactionId!=0 && hlAppStatus == true){
			return isSaved="true";
		}
		return isSaved;
	}
	
	
	public boolean reActivateCheckinDetails(int statusId,int roomId, String userId)throws Exception
	{
		log.info("Entering to reActivateCheckinDetails of HostelCheckinHandler");
		List<HlRoomTransaction> boListTocheckDForActive = transaction.getCheckinDetailedListForActive(statusId,roomId);
		List<HlRoomTransaction> boListToUpdate = HostelCheckinHelper.getInstance().prepareBoListToMakeActive(boListTocheckDForActive,userId);
		if(transaction!=null)
		{
			return transaction.reActivateCheckinList(boListToUpdate);
		}
		log.info("Exiting reActivateFeeDetails of HostelFeeHandler");
		return false;
	}
	
	/**
	 * Setting the data hostelCheckInForm
	 * @param HostelCheckinForm
	 * @return
	 * @throws Exception
	 */	
	public HostelCheckinTo setAllDataToForm (HostelCheckinForm hostelCheckinform, List<HostelCheckinTo> hostelApplicantDetails) throws Exception {
		log.info("inside setAllDataToForm of HostelCheckinHelper");		
		HostelCheckinTo hto = new HostelCheckinTo();
		if(hostelApplicantDetails!=null){
			hto=hostelApplicantDetails.get(0);				
			if(hto != null){
				if(hto.getApplicantName()!= null && !hto.getApplicantName().isEmpty()){
					hostelCheckinform.setApplicantName(hto.getApplicantName());
				}
				if(hto.getRoomTypeId()!= null && !hto.getRoomTypeId().isEmpty()){
					hostelCheckinform.setRoomTypeId(hto.getRoomTypeId());
				}
				if(hto.getRoomType()!= null && !hto.getRoomType().isEmpty()){
					hostelCheckinform.setRoomType(hto.getRoomType());
				}
				if(hto.getRoomId()!= null && !hto.getRoomId().isEmpty()){
					hostelCheckinform.setRoomId(hto.getRoomId());
				}				
				if(hto.getRoomName()!= null && !hto.getRoomName().isEmpty()){
					hostelCheckinform.setRoomName(hto.getRoomName());
				}
				if(hto.getBedNo()!= null && !hto.getBedNo().isEmpty()){
					hostelCheckinform.setBedNo(hto.getBedNo());
				}
				if(hto.getRoomFacilityList()!= null && !hto.getRoomFacilityList().isEmpty()){
					hostelCheckinform.setFecilityList(hto.getRoomFacilityList());
				}
				if(hto.getAllotedDate()!= null && !hto.getAllotedDate().isEmpty()){
					hostelCheckinform.setAllotedDate(hto.getAllotedDate());
				}
			}
		}	
		log.info("Exiting setAllDataToForm of HostelCheckinHelper");
		return hto;
	 }

	public List<HostelTO> getHostelDetails() throws Exception {
		log.info("Entering getHostelDetails of HostelCheckinHandler");

		List<HlHostel> hostelList = transaction.getHostelNames();
		List<HostelTO> hostelTOList = HostelCheckinHelper.getInstance()
				.copyHostelBosToTos(hostelList);
		log.info("Exitinging getHostelDetails of HostelCheckinHandler");
		return hostelTOList;
	}

	/**
	 * @param fingerPrintId
	 * @return
	 * @throws Exception
	 */
	public boolean checkDuplicateFingerPrintId(String fingerPrintId,String hostelId) throws Exception{
		IHostelCheckinTransactions transaction=new HostelCheckinTransactionImpl();
		return transaction.checkForDuplicate(fingerPrintId,hostelId);
	}

	public List<HostelCheckinTo> getAllotedHostelDetails(HostelCheckinForm hostelCheckinForm) throws Exception
	{
		log.info("Entering getAllotedHostelDetails of HostelCheckinHandler");
		HostelCheckinHelper hostelCheckinHelper = HostelCheckinHelper
				.getInstance();
		List<Object> applicantHostelDetails = transaction
				.getApplicantHostelDetailsList(hostelCheckinHelper
						.getChecklinDetailsCriteria(hostelCheckinForm));
		List<HostelCheckinTo> hostelCheckinToList = hostelCheckinHelper
				.convertBOtoTO(applicantHostelDetails,hostelCheckinForm );
		log.info("Exiting getAllotedHostelDetails of HostelCheckinHandler");
		return hostelCheckinToList;
	}

	public boolean isCheckedIn(HostelCheckinForm hostelCheckinForm)throws Exception {
		return transaction.isCheckedin(hostelCheckinForm);
	}

}
