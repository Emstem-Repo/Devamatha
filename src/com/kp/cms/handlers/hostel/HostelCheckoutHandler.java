package com.kp.cms.handlers.hostel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.to.hostel.HlDamageTO;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlCheckinCheckoutFacility;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.HlStatus;
import com.kp.cms.forms.hostel.HostelCheckoutForm;
import com.kp.cms.helpers.hostel.HostelCheckoutHelper;
import com.kp.cms.to.hostel.HostelCheckoutTo;
import com.kp.cms.to.hostel.HostelDamageTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.transactions.hostel.IHostelCheckoutTransactions;
import com.kp.cms.transactionsimpl.hostel.HostelCheckoutTransactionImpl;

public class HostelCheckoutHandler {
	
	private static Log log = LogFactory.getLog(HostelCheckoutHandler.class);
	private static volatile HostelCheckoutHandler hostelCheckoutHandler = null;

	private HostelCheckoutHandler() {
	}

	/**
	 * This method returns the single instance of this HostelCheckoutHandler.
	 * 
	 * @return
	 */
	public static HostelCheckoutHandler getInstance() {
		if (hostelCheckoutHandler == null) {
			hostelCheckoutHandler = new HostelCheckoutHandler();
		}
		return hostelCheckoutHandler;
	}

	IHostelCheckoutTransactions transaction = HostelCheckoutTransactionImpl
	.getInstance();
	/**
	 * 
	 * @param HostelCheckinForm
	 * @return
	 * @throws Exception
	 */
	public HostelCheckoutTo getApplicantHostelDetails(
			HostelCheckoutForm hostelCheckoutForm) throws Exception {

		log.info("Entering getApplicantHostelDetails of HostelCheckoutHandler");
		HostelCheckoutHelper hostelCheckoutHelper = HostelCheckoutHelper.getInstance();
		List<Object> applicantHostelDetails = transaction.getApplicantHostelDetailsList(hostelCheckoutHelper.getSearchCriteria(hostelCheckoutForm));
		HostelCheckoutTo hostelCheckoutTo  = hostelCheckoutHelper.convertBOtoTO(applicantHostelDetails,hostelCheckoutForm );
		log.info("Exiting getApplicantHostelDetails of HostelCheckoutHandler");
		return hostelCheckoutTo;
	}
	
	/**
	 * getting details from HostelDamage table
	 * @param HostelCheckinForm
	 * @return
	 * @throws Exception
	 */
	public List<HlDamageTO> getDamageDetails(
			HostelCheckoutForm hostelCheckoutForm) throws Exception {

		log.info("Entering getApplicantHostelDetails of HostelCheckoutHandler");
		HostelCheckoutHelper hostelCheckoutHelper = HostelCheckoutHelper.getInstance();
		List<Object> damageDetails = transaction.getDamageDetails(hostelCheckoutHelper.getDamageCriteria(hostelCheckoutForm));
		List<HlDamageTO> hlDamageToList   = hostelCheckoutHelper.convertDamageBOtoTO(damageDetails,hostelCheckoutForm );
		log.info("Exiting getApplicantHostelDetails of HostelCheckoutHandler");
		return hlDamageToList;
	}
	/**
	 * getting hosteldetails from Hostel table
	 * @param HostelCheckinForm
	 * @return
	 * @throws Exception
	 */
	
	public List<HostelTO> getHostelDetails() throws Exception {
		log.info("Entering getHostelDetails of HostelCheckoutHandler");
		List<HlHostel> hostelList = transaction.getHostelNames();
		List<HostelTO> hostelTOList = HostelCheckoutHelper.getInstance()
				.copyHostelBosToTos(hostelList);
		log.info("Exitinging getHostelDetails of HostelCheckoutHandler");
		return hostelTOList;
	}
	
	/**
	 * Setting the data hostelCheckoutForm
	 * @param HostelCheckinForm
	 * @return
	 * @throws Exception
	 */	
	public HostelCheckoutTo setAllDataToForm (HostelCheckoutForm hostelCheckoutform,HostelCheckoutTo hto) throws Exception {
		log.info("inside setAllDataToForm of HostelCheckoutHelper");		
		//HostelCheckoutTo hto = new HostelCheckoutTo();
			if(hto != null){
				if(hto.getApplicantName()!= null && !hto.getApplicantName().isEmpty()){
					hostelCheckoutform.setApplicantName(hto.getApplicantName());
				}
				if(hto.getRoomTypeId()!= null && !hto.getRoomTypeId().isEmpty()){
					hostelCheckoutform.setRoomTypeId(hto.getRoomTypeId());
				}
				if(hto.getRoomType()!= null && !hto.getRoomType().isEmpty()){
					hostelCheckoutform.setRoomType(hto.getRoomType());
				}
				if(hto.getRoomId()!= null && !hto.getRoomId().isEmpty()){
					hostelCheckoutform.setRoomId(hto.getRoomId());
				}				
				if(hto.getRoomName()!= null && !hto.getRoomName().isEmpty()){
					hostelCheckoutform.setRoomName(hto.getRoomName());
				}
				if(hto.getBedNo()!= null && !hto.getBedNo().isEmpty()){
					hostelCheckoutform.setBedNo(hto.getBedNo());
				}
				/*if(hto.getRoomFacilityList()!= null && !hto.getRoomFacilityList().isEmpty()){
					hostelCheckoutform.setFecilityList(hto.getRoomFacilityList());
				}*/
				if(hto.getCheckoutList()!= null && !hto.getCheckoutList().isEmpty()){
					
					hostelCheckoutform.setCheckoutList(hto.getCheckoutList());
				}
				if(hto.getTxnDate()!= null ){
					hostelCheckoutform.setTxnDate(hto.getTxnDate());
				}
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				java.util.Date dates = new java.util.Date();
				String paidDate = dateFormat.format(dates);
				hostelCheckoutform.setCheckoutDate(paidDate);
			}
			
		log.info("Exiting setAllDataToForm of HostelCheckinHelper");
		return hto;
	 }
	/**
	 * saving the data to database
	 * 
	 * @param hostelCheckinForm
	 * @param hostelApplicantDetails
	 * @return
	 * @throws Exception
	 */
	public String saveCheckoutDetails(HostelCheckoutForm hostelCheckoutForm,HostelCheckoutTo hostelCheckoutTo) throws Exception {
		boolean fecilityStatus =false;
		boolean checkoutStatus =false;
		boolean hlAppStatus =false;
		String isSaved="false";
		
		log.info("Entering saveCheckoutDetails of HostelCheckoutHandler");
		HostelCheckoutHelper hostelCheckoutHelper = HostelCheckoutHelper.getInstance();
		HlRoomTransaction hlRoomTransaction = hostelCheckoutHelper.saveCheckoutDetails(hostelCheckoutForm, hostelCheckoutTo);
		 checkoutStatus = transaction.saveCheckoutDetails(hlRoomTransaction); 
		int hlAppFormId= hostelCheckoutForm.getFormId();
		List<HlApplicationForm>  hlAppFormDet = transaction.getAppFormDetails(hlAppFormId);
		Iterator<HlApplicationForm > appItr = hlAppFormDet.iterator();
		while (appItr.hasNext()) {
			HlStatus hlStatus = new HlStatus();
			hlStatus.setId(3);
			HlApplicationForm  hlApplicationForm = (HlApplicationForm ) appItr.next();
			hlApplicationForm.setHlStatus(hlStatus);
			hlAppStatus = transaction.updateAppFormDetails(hlApplicationForm);
		}
		
		List<HlCheckinCheckoutFacility> facilityList = hostelCheckoutHelper.saveFecilities(hostelCheckoutForm, hostelCheckoutTo);
		if(!facilityList.isEmpty()){
		Iterator<HlCheckinCheckoutFacility> facilityItr=facilityList.iterator();
		while (facilityItr.hasNext()) {
			HlCheckinCheckoutFacility hlCheckinCheckoutFacility = (HlCheckinCheckoutFacility) facilityItr.next();
			fecilityStatus = transaction.saveCheckinCheckoutFecilities(hlCheckinCheckoutFacility);
		}
		}else{
			fecilityStatus= true;
		}
		log.info("Exiting saveCheckoutDetails of Checkout Handler");
		if(fecilityStatus== true &&checkoutStatus == true && hlAppStatus == true){
			return isSaved="true";
		}
		return isSaved;
	}

	

}
