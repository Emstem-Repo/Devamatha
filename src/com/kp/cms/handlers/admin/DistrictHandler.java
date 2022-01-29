package com.kp.cms.handlers.admin;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.District;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.DistrictForm;
import com.kp.cms.helpers.admin.DistrictHelper;
import com.kp.cms.to.admin.DistrictTO;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.transactions.admin.IDistrictTransaction;
import com.kp.cms.transactionsimpl.admin.DistrictTransactionImpl;


/**
 * 
 * @author Date 08/jan/2009 Handler class for State Masters
 */

public class DistrictHandler {
	private static Log log = LogFactory.getLog(DistrictHandler.class);
	public static volatile DistrictHandler stateHandler = null;

	public static DistrictHandler getInstance() {
		if (stateHandler == null) {
			stateHandler = new DistrictHandler();
			return stateHandler;
		}
		return stateHandler;
	}

	/**
	 * 
	 * @param stateName
	 *            This will add a state name passed as param.
	 * @return boolean true / false based on result.
	 * @throws Exception
	 */
	public boolean addDistrict(DistrictForm countryStateCityForm) throws Exception {
		IDistrictTransaction iStateTransaction = DistrictTransactionImpl.getInstance();
		boolean isAdded = false;

		//State duplState = CountryStateCityHelper.getInstance().populateStatesDataFormForm(countryStateCityForm);

		District duplState = iStateTransaction.isDistrictNameDuplcated(countryStateCityForm);  //duplication checking
		if (duplState != null && duplState.getIsActive()) {
			throw new DuplicateException();
		} else if (duplState != null && !duplState.getIsActive()) {
			countryStateCityForm.setDuplStateId(duplState.getId());
			throw new ReActivateException();
		}
		District state = DistrictHelper.getInstance().populateDistrictDataFormForm(countryStateCityForm);
	
		state.setCreatedDate(new Date());
		state.setLastModifiedDate(new Date());
		state.setCreatedBy(countryStateCityForm.getUserId());
		state.setModifiedBy(countryStateCityForm.getUserId());
		isAdded = iStateTransaction.addDistrict(state);
		log.debug("leaving addState");
		return isAdded;
	}

	/**
	 * 
	 * @return list of StatesTO objects, will be used in UI to dispaly.
	 * @throws ApplicationException 
	 */
	public List<DistrictTO> getDistrict() throws Exception {
		IDistrictTransaction iDistrictTransaction = DistrictTransactionImpl.getInstance();
		List<District> stateList = iDistrictTransaction.getDistrict();
		List<DistrictTO> stateToList = DistrictHelper.getInstance().copyDistrictBosToTos(stateList);
		log.debug("leaving getStates");
		return stateToList;
	}
	/**
	 * 
	 * @return list of StatesTO objects, will be used in UI to dispaly.
	 * @throws ApplicationException 
	 */
	public List<DistrictTO> getNativeDistrict(int nativeCountryId) throws Exception {
		IDistrictTransaction iStateTransaction = DistrictTransactionImpl.getInstance();
		List<District> stateList = iStateTransaction.getNativeDistrict(nativeCountryId);
		List<DistrictTO> stateToList = DistrictHelper.getInstance().copyDistrictBosToTos(stateList);
		log.debug("leaving getNativeStates");
		return stateToList;
	}
	/**
	 * 
	 * @return States Details in Map <key, value> Ex <1,karnataka><2,kerala>
	 * @throws ApplicationException 
	 */
	public Map<Integer, String> getDistrictMap() throws Exception {
		IDistrictTransaction iStateTransaction = DistrictTransactionImpl.getInstance();
		List<District> stateList = iStateTransaction.getDistrict();
//		Map<Integer, String> statesMap = new HashMap<Integer, String>();
		Map<Integer, String> statesMap = new LinkedHashMap<Integer, String>();		
		Iterator<District> i = stateList.iterator();
		District state;
		while (i.hasNext()) {
			state = (District) i.next();
			statesMap.put(state.getId(), state.getName());
		}
		log.debug("inside getStatesMap");
		return statesMap;
	}

	/**
	 * 
	 * @param stateName
	 *            This will update a state name passed in param.
	 * @return boolean true / false based on result.
	 * @throws Exception
	 */
	public boolean updateDistrict(DistrictForm countryStateCityForm) throws Exception {
		IDistrictTransaction iStateTransaction = DistrictTransactionImpl.getInstance();
		boolean isUpdate = false;
		//original variables are used for checking duplication
		Boolean originalNotChanged = false;

		String districtName = "";
		String origDistrictName = "";

		districtName = countryStateCityForm.getDistrictName().trim();
		origDistrictName = countryStateCityForm.getOrigDistrictName().trim();
		
		int stateId = 0;
		if ((countryStateCityForm.getStateId() != null) || (!countryStateCityForm.getStateId().isEmpty())){
			stateId = Integer.parseInt(countryStateCityForm.getStateId());
		}
		int origStateId = countryStateCityForm.getEditedStateId();
		
		if (districtName.trim().equalsIgnoreCase(origDistrictName.trim()) && stateId == origStateId) {
			originalNotChanged = true;
		}

		if (!originalNotChanged) {
			//State duplState = CountryStateCityHelper.getInstance().populateStatesDataFormForm(countryStateCityForm);

			District duplDistrict = iStateTransaction.isDistrictNameDuplcated(countryStateCityForm);
			if (duplDistrict != null && duplDistrict.getIsActive()) {
				throw new DuplicateException();
			} else if (duplDistrict != null && !duplDistrict.getIsActive()) {
				countryStateCityForm.setDuplStateId(duplDistrict.getId());
				throw new ReActivateException();
			}
		}
		District state = DistrictHelper.getInstance().populateDistrictDataFormForm(countryStateCityForm);	
		state.setLastModifiedDate(new Date());
		state.setModifiedBy(countryStateCityForm.getUserId());
		isUpdate = iStateTransaction.updateDistrict(state);
		log.debug("leaving updateState");
		return isUpdate;
	}

	/**
	 * 
	 * @param stateName
	 *            This will delete a state by id passed as param.
	 * @return boolean true / false based on result.
	 * @throws Exception 
	 */
	public boolean deleteDistrict(int id, Boolean activate, DistrictForm countryStateCityForm) throws Exception {
		IDistrictTransaction iStateTransaction = DistrictTransactionImpl.getInstance();
		boolean result = iStateTransaction.deleteDistrict(id, activate, countryStateCityForm);
		log.debug("leaving deleteState");
		return result;
	}
}
