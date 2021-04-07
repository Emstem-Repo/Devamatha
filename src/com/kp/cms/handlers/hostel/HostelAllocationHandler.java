package com.kp.cms.handlers.hostel;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.forms.hostel.HostelAllocationForm;
import com.kp.cms.helpers.hostel.HostelAllocationHelper;
import com.kp.cms.to.hostel.HostelAllocationTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.transactions.hostel.IHostelAllocationTransactions;
import com.kp.cms.transactionsimpl.hostel.HostelAllocationTransactionImpl;

public class HostelAllocationHandler {
	private static Log log = LogFactory.getLog(HostelAllocationHandler.class);
	private static volatile HostelAllocationHandler hostelAllocationHandler = null;
	IHostelAllocationTransactions transaction = HostelAllocationTransactionImpl.getInstance();

	private HostelAllocationHandler() {
	}

	/**
	 * This method returns the single instance of this HostelAllocationHandler.
	 * 
	 * @return
	 */
	public static HostelAllocationHandler getInstance() {
		if (hostelAllocationHandler == null) {
			hostelAllocationHandler = new HostelAllocationHandler();
		}
		return hostelAllocationHandler;
	}
	
	/**
	 * 
	 * @param hostelAllocationForm
	 * @return
	 * @throws Exception
	 */
	public HostelAllocationTO getApplicantHostelDetails(HostelAllocationForm hostelAllocationForm) throws Exception{
	
		log.info("Entering getApplicantHostelDetails of HostelAllocationHandler");
		HostelAllocationHelper hostelAllocationHelper = HostelAllocationHelper.getInstance();
		List<Object> applicantHostelDetails = transaction.getApplicantHostelDetailsList(hostelAllocationHelper.getSearchCriteria(hostelAllocationForm));  
		HostelAllocationTO applicantHostelDetailsTO = hostelAllocationHelper.convertBOtoTO(applicantHostelDetails);
		log.info("Exiting getApplicantHostelDetails of HostelAllocationHandler");
		return applicantHostelDetailsTO; 
	}
	
	public String saveAllocationDetails(HostelAllocationForm hostelAllocationForm,HostelAllocationTO hostelApplicantDetails)throws Exception{
		
		String status="failed";
		log.info("Entering saveAllocationDetails of HostelAllocationHandler");
		HostelAllocationHelper hostelAllocationHelper = HostelAllocationHelper.getInstance();
		status = hostelAllocationHelper.saveAllocationDetails(hostelAllocationForm,hostelApplicantDetails);
		log.info("Exiting saveAllocationDetails of HostelAllocationHandler");
		return  status;
	}
	
	public List<HostelTO> getHostelDetails() throws Exception {
		log.info("Entering getHostelDetails of HostelAllocationHandler");
		List<HlHostel> hostelList = transaction.getHostelNames();
		List<HostelTO> hostelTOList = HostelAllocationHelper.getInstance().copyHostelBosToTos(hostelList);
		log.info("Exitinging getHostelDetails of HostelAllocationHandler");
		return hostelTOList;
	}
	
	
	public List<HostelAllocationTO> getReservedHostelDetails(HostelAllocationForm hostelAllocationForm) throws Exception{
		
		log.info("Entering getApplicantHostelDetails of HostelAllocationHandler");
		HostelAllocationHelper hostelAllocationHelper = HostelAllocationHelper.getInstance();
		List<Object> applicantHostelDetails = transaction.getApplicantHostelDetailsList(hostelAllocationHelper.getSearchDetails(hostelAllocationForm));  
		List<HostelAllocationTO> applicantHostelDetailsTO = hostelAllocationHelper.convertBOtoTOList(applicantHostelDetails);
		log.info("Exiting getApplicantHostelDetails of HostelAllocationHandler");
		return applicantHostelDetailsTO; 
	}
	
}
