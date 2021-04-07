package com.kp.cms.handlers.hostel;


import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.hostel.HostelEntryForm;
import com.kp.cms.helpers.hostel.HostelEntryHelper;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.transactions.hostel.IHostelEntryTransactions;
import com.kp.cms.transactionsimpl.hostel.HostelEntryTransactionImpl;


public class HostelEntryHandler {
	public static Log log = LogFactory.getLog(HostelEntryHandler.class);
	public static volatile HostelEntryHandler hostelEntryHandler;
	
	public static HostelEntryHandler getInstance(){
		if(hostelEntryHandler == null){
			hostelEntryHandler = new HostelEntryHandler();
			return hostelEntryHandler;
		}
		return hostelEntryHandler;
	}
	IHostelEntryTransactions transactions = HostelEntryTransactionImpl.getInstance();
	/**
	 * 
	 * @return list of HostelTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<HostelTO> getHostelDetails() throws Exception {
		log.debug("inside getHostelDetails");
		IHostelEntryTransactions iHostelEntryTransactions = HostelEntryTransactionImpl.getInstance();
		List<HlHostel> hostelList = iHostelEntryTransactions.getHostelDeatils();
		List<HostelTO> hostelTOList = HostelEntryHelper.getInstance().copyHostelBosToTos(hostelList);
		log.debug("leaving getHostelDetails");
		return hostelTOList;
	}
	  
	/**
	 * 
	 * @param hostelEntryForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addHostelEntry(HostelEntryForm hostelEntryForm, String mode) throws Exception {
		log.debug("inside addHostelEntry");
		IHostelEntryTransactions ihTransactions = HostelEntryTransactionImpl.getInstance();
		boolean isAdded = false;
		//duplication checking
		HlHostel duplHostel =  ihTransactions.isHostelEntryDuplcated(hostelEntryForm.getName(), hostelEntryForm.getId());
		
		if (duplHostel != null && duplHostel.getIsActive()) {
			throw new DuplicateException();
		}
		else if (duplHostel != null && !duplHostel.getIsActive())
		{
			hostelEntryForm.setDuplId(duplHostel.getId());
			throw new ReActivateException();
		}		
				
		 HlHostel hostel = HostelEntryHelper.getInstance().copyDataFromFormToBO(hostelEntryForm);
		isAdded = ihTransactions.addHostelEntry(hostel, mode);
		log.debug("leaving addHostelEntry");
		return isAdded;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<HostelTO> getHostelDetailsById(int id) throws Exception {
		log.debug("inside getHostelDetails");
		IHostelEntryTransactions iHostelEntryTransactions = HostelEntryTransactionImpl.getInstance();
		List<HlHostel> hostelList = iHostelEntryTransactions.getHostelDetailsById(id);
		List<HostelTO> hostelTOList = HostelEntryHelper.getInstance().copyHostelBosToTos(hostelList);
		log.debug("leaving getHostelDetails");
		return hostelTOList;
	}
	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */

	public boolean deleteHostelEntry(int id, Boolean activate, HostelEntryForm hForm)	throws Exception {
		IHostelEntryTransactions ihEntryTransactions = HostelEntryTransactionImpl.getInstance();
		return ihEntryTransactions.deleteHostelEntry(id, activate, hForm); 
	}
	/**
	 * @param hostelId
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getBlocks(String hostelId) throws Exception{
		return transactions.getBlocks(hostelId);
	}

	/**
	 * @param blockId
	 * @return
	 */
	public Map<Integer, String> getUnits(String blockId) throws Exception{
		return transactions.getUnits(blockId);
	}

	
}
