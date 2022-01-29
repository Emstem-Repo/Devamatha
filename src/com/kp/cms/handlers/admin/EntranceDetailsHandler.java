package com.kp.cms.handlers.admin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.actions.admin.EntranceDetailsAction;
import com.kp.cms.bo.admin.Entrance;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.EntranceDetailsForm;
import com.kp.cms.helpers.admin.EntranceDetailsHelper;
import com.kp.cms.to.admin.EntrancedetailsTO;
import com.kp.cms.transactions.admin.IEntranceDetails;
import com.kp.cms.transactionsimpl.admin.EntranceDetailsTransactionImpl;

public class EntranceDetailsHandler {
	public static volatile EntranceDetailsHandler entranceDetailsHandler = null;
	private static final Log log = LogFactory.getLog(EntranceDetailsHandler.class);
	public static EntranceDetailsHandler getInstance() {
		if (entranceDetailsHandler == null) {
			entranceDetailsHandler = new EntranceDetailsHandler();
			return entranceDetailsHandler;
		}
		return entranceDetailsHandler;
	}
	/**
	 * getting entrance details from table
	 * @return
	 * @throws Exception
	 */

	public List<EntrancedetailsTO> getEntranceDeatils() throws Exception {
		IEntranceDetails ieDetails = new EntranceDetailsTransactionImpl();
		List<Entrance> enList = ieDetails.getEntranceDetails();

		List<EntrancedetailsTO> enListToList = EntranceDetailsHelper.getInstance().copyEntranceDetailsBosToTos(enList);
		log.error("ending of getEntranceDetails method in EntranceDetailsHandler");
		return enListToList;

	}
	public List<EntrancedetailsTO> getEntranceDeatilsByid(int id) throws Exception {
		IEntranceDetails ieDetails = new EntranceDetailsTransactionImpl();
		List<Entrance> enList = ieDetails.getEntranceDetailsById(id);

		List<EntrancedetailsTO> enListToList = EntranceDetailsHelper.getInstance().copyEntranceDetailsBosToTos(enList);
		log.error("ending of getEntranceDeatilsByid method in EntranceDetailsHandler");
		return enListToList;

	}	
	/**
	 * 
	 * @param enForm
	 * @param mode
	 * @return
	 * @throws DuplicateException
	 * @throws Exception
	 */
	public boolean addEntranceDetails(EntranceDetailsForm enForm, String mode) throws DuplicateException, Exception {
		IEntranceDetails ieDetails = EntranceDetailsTransactionImpl.getInstance();
		boolean isAdded;
		Entrance duplEntrance = EntranceDetailsHelper.getInstance().populateEntranceDetails(enForm);  

		duplEntrance = ieDetails.isEntranceNameDuplcated(duplEntrance); 
		if (duplEntrance  != null && duplEntrance.getIsActive()) {
			throw new DuplicateException();
		} else if (duplEntrance  != null && !duplEntrance .getIsActive()) {
			enForm.setDuplId(duplEntrance.getId());
			throw new ReActivateException();
		}		
		
		Entrance entrance = EntranceDetailsHelper.getInstance().populateEntranceDetails(enForm); 
		isAdded = ieDetails.addEntranceDetails(entrance, mode); 
		log.error("ending of addEntranceDetails method in EntranceDetailsHandler");
		return isAdded;
	}
	
	/**
	 * Deletes the entrance  from the database.
	 * @param 
	 * @return - returns true/false based on the result
	 */
	public boolean deleteEntranceDetails(int id, EntranceDetailsForm enForm, Boolean activate)throws Exception {
		IEntranceDetails iDetails = new EntranceDetailsTransactionImpl();
		boolean isEntranceDeleted = false;
		isEntranceDeleted = iDetails.deleteEntranceDetails(id, enForm, activate);
		log.error("ending of deleteEntranceDetails method in EntranceDetailsHandler");
		return isEntranceDeleted;
	}
	
	/**
	 * getting entrance details from table
	 * @return
	 * @throws Exception
	 */

	public List<EntrancedetailsTO> getEntranceDeatilsByProgram(int programId) throws Exception {
		IEntranceDetails ieDetails = new EntranceDetailsTransactionImpl();
		List<Entrance> enList = ieDetails.getEntranceDetailsByProgram(programId);

		List<EntrancedetailsTO> enListToList = EntranceDetailsHelper.getInstance().copyEntranceDetailsBosToTos(enList);
		log.error("ending of getEntranceDeatilsByProgram method in EntranceDetailsHandler");
		return enListToList;

	}
}
