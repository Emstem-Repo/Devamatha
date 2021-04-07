package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmittedThrough;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.AdmittedThroughForm;
import com.kp.cms.helpers.admin.AdmittedThroughHelper;
import com.kp.cms.to.admin.AdmittedThroughTO;
import com.kp.cms.to.admin.SeatAllocationTO;
import com.kp.cms.transactions.admin.IAdmittedThroughTransaction;
import com.kp.cms.transactionsimpl.admin.AdmittedThroughTransactionImpl;

public class AdmittedThroughHandler {
	public static volatile AdmittedThroughHandler admittedThroughHandler = null;
    public static final Log log = LogFactory.getLog(AdmittedThroughHandler.class);
    private AdmittedThroughHandler(){
    	
    }
	public static AdmittedThroughHandler getInstance() {
		if (admittedThroughHandler == null) {
			admittedThroughHandler = new AdmittedThroughHandler();
			return admittedThroughHandler;
		}
		return admittedThroughHandler;
	}

	/**
	 * 
	 * @return list of admittedThroughTO objects, will be used in UI to display.
	 * @throws Exception 
	 */
	public List<AdmittedThroughTO> getAdmittedThrough() throws Exception {
		IAdmittedThroughTransaction iAdmittedThroughTransaction = AdmittedThroughTransactionImpl.getInstance();
		List<AdmittedThrough> admittedThroughList = iAdmittedThroughTransaction.getAdmittedThrough();
		List<AdmittedThroughTO> admittedThroughTOList = AdmittedThroughHelper.getInstance().copyAdmittedThroughBosToTos(admittedThroughList);
		log.error("ending of getAdmittedThrough method in AdmittedThroughHandler");
		return admittedThroughTOList;
	}

	/**
	 * 
	 * @return Map of admittedThrough <KEY,VALUE> EX :<id,name>.
	 * @throws Exception
	 */
	public Map<Integer, String> getAdmittedThroughMap() throws Exception {
		IAdmittedThroughTransaction iAdmittedThroughTransaction = AdmittedThroughTransactionImpl.getInstance();
		List<AdmittedThrough> admittedThroughList = iAdmittedThroughTransaction.getAdmittedThrough();
		Map<Integer, String> admittedthroughMap = new HashMap<Integer, String>();
		Iterator<AdmittedThrough> itr = admittedThroughList.iterator();
		while (itr.hasNext()) {
			AdmittedThrough admittedThrough = itr.next();
			admittedthroughMap.put(admittedThrough.getId(), admittedThrough.getName());
		}
		log.error("ending of getAdmittedThrough method in AdmittedThroughHandler");
		return admittedthroughMap;
	}

	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */
	public boolean addAdmittedThrough(AdmittedThroughForm admittedThroughForm, String mode) throws Exception {
		IAdmittedThroughTransaction iAdmittedThroughTransaction = AdmittedThroughTransactionImpl.getInstance();
		boolean isAdded = false;

		Boolean originalNotChanged = false;
		String admThrough = "";
		String origadmThrough = "";
		if(admittedThroughForm.getAdmittedThrough() != null && !admittedThroughForm.getAdmittedThrough().equals("")){
			admThrough = admittedThroughForm.getAdmittedThrough().trim();
		}
		
		if(admittedThroughForm.getOrigAdmittedThrough() != null && !admittedThroughForm.getOrigAdmittedThrough().equals("")){
			origadmThrough = admittedThroughForm.getOrigAdmittedThrough().trim();
		}
		
		if (admThrough.equalsIgnoreCase(origadmThrough)) {
			originalNotChanged = true;
		}

		if (mode.equals("Add")) {
			originalNotChanged = false; // for add no need to check original
										// changed
		}

		if (!originalNotChanged) {
			AdmittedThrough dupladmittedThrough = AdmittedThroughHelper.getInstance().populateAdmittedThroughDataFormForm(admittedThroughForm);

			dupladmittedThrough = iAdmittedThroughTransaction.isAdmittedThroughDuplcated(dupladmittedThrough);
			if (dupladmittedThrough != null	&& dupladmittedThrough.getIsActive() == true) {
				throw new DuplicateException();
			} else if (dupladmittedThrough != null && dupladmittedThrough.getIsActive() == false) {
				admittedThroughForm.setDuplId(dupladmittedThrough.getId());
				throw new ReActivateException();
			}
		}

		//AdmittedThrough admittedThrough = new AdmittedThrough();
		AdmittedThrough admittedThrough = AdmittedThroughHelper.getInstance().populateAdmittedThroughDataFormForm(admittedThroughForm);

		if (mode.equals("Add")) {
			admittedThrough.setCreatedBy(admittedThroughForm.getUserId());
			admittedThrough.setModifiedBy(admittedThroughForm.getUserId());
			admittedThrough.setCreatedDate(new Date());
			admittedThrough.setLastModifiedDate(new Date());
		} else // edit
		{
			admittedThrough.setModifiedBy(admittedThroughForm.getUserId());
			admittedThrough.setLastModifiedDate(new Date());

		}

		isAdded = iAdmittedThroughTransaction.addAdmittedThrough(admittedThrough, mode);
		log.error("ending of getAdmittedThrough method in AdmittedThroughHandler");
		return isAdded;
	}

	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */

	public boolean deleteAdmittedThrough(int id, Boolean activate, AdmittedThroughForm admittedThroughForm)	throws Exception {
		IAdmittedThroughTransaction iAdmittedThroughTransaction = AdmittedThroughTransactionImpl.getInstance();
		boolean result = iAdmittedThroughTransaction.deleteAdmittedThrough(id, activate, admittedThroughForm);
		log.error("ending of deleteAdmittedThrough method in AdmittedThroughHandler");
		return result;
	}

	/**
	 * 
	 * @return list of admittedThroughTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<SeatAllocationTO> addSeatAllocationWithAdmittedThrough() throws Exception {
		IAdmittedThroughTransaction iAdmittedThroughTransaction = AdmittedThroughTransactionImpl
				.getInstance();
		List<AdmittedThrough> admittedThroughList = iAdmittedThroughTransaction
				.getAdmittedThrough();

		Iterator<AdmittedThrough> itr = admittedThroughList.iterator();
		List<SeatAllocationTO> admSeatAllocList = new ArrayList<SeatAllocationTO>();


		while (itr.hasNext()) {
			AdmittedThrough admittedThrough = itr.next();
			SeatAllocationTO seatAllocationTO = new SeatAllocationTO();
			AdmittedThroughTO admittedThroughTO = new AdmittedThroughTO();

			seatAllocationTO.setAdmittedThroughId(admittedThrough.getId());

			admittedThroughTO.setId(admittedThrough.getId());
			admittedThroughTO.setName(admittedThrough.getName());
			seatAllocationTO.setAdmittedThroughTO(admittedThroughTO);
			seatAllocationTO.setId(0);
			seatAllocationTO.setCourseId(0);
			seatAllocationTO.setNoofSeats(0);
			admSeatAllocList.add(seatAllocationTO);
		}
		log.error("ending of addSeatAllocationWithAdmittedThrough method in AdmittedThroughHandler");
		return admSeatAllocList;
	}
}
