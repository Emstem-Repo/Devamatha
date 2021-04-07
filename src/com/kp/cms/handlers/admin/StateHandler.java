package com.kp.cms.handlers.admin;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.State;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.CountryStateCityForm;
import com.kp.cms.helpers.admin.CountryStateCityHelper;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.transactions.admin.IStateTransaction;
import com.kp.cms.transactionsimpl.admin.StateTransactionImpl;


/**
 * 
 * @author Date 08/jan/2009 Handler class for State Masters
 */

public class StateHandler {
	private static Log log = LogFactory.getLog(StateHandler.class);
	public static volatile StateHandler stateHandler = null;

	public static StateHandler getInstance() {
		if (stateHandler == null) {
			stateHandler = new StateHandler();
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
	public boolean addState(CountryStateCityForm countryStateCityForm) throws Exception {
		IStateTransaction iStateTransaction = StateTransactionImpl.getInstance();
		boolean isAdded = false;

		//State duplState = CountryStateCityHelper.getInstance().populateStatesDataFormForm(countryStateCityForm);

		State duplState = iStateTransaction.isStateNameDuplcated(countryStateCityForm);  //duplication checking
		if (duplState != null && duplState.getIsActive()) {
			throw new DuplicateException();
		} else if (duplState != null && !duplState.getIsActive()) {
			countryStateCityForm.setDuplStateId(duplState.getId());
			throw new ReActivateException();
		}
		State state = CountryStateCityHelper.getInstance().populateStatesDataFormForm(countryStateCityForm);
	
		state.setCreatedDate(new Date());
		state.setLastModifiedDate(new Date());
		state.setCreatedBy(countryStateCityForm.getUserId());
		state.setModifiedBy(countryStateCityForm.getUserId());
		isAdded = iStateTransaction.addState(state);
		log.debug("leaving addState");
		return isAdded;
	}

	/**
	 * 
	 * @return list of StatesTO objects, will be used in UI to dispaly.
	 * @throws ApplicationException 
	 */
	public List<StateTO> getStates() throws Exception {
		IStateTransaction iStateTransaction = StateTransactionImpl.getInstance();
		List<State> stateList = iStateTransaction.getStates();
		List<StateTO> stateToList = CountryStateCityHelper.getInstance().copyStatesBosToTos(stateList);
		log.debug("leaving getStates");
		return stateToList;
	}
	/**
	 * 
	 * @return list of StatesTO objects, will be used in UI to dispaly.
	 * @throws ApplicationException 
	 */
	public List<StateTO> getNativeStates(int nativeCountryId) throws Exception {
		IStateTransaction iStateTransaction = StateTransactionImpl.getInstance();
		List<State> stateList = iStateTransaction.getNativeStates(nativeCountryId);
		List<StateTO> stateToList = CountryStateCityHelper.getInstance().copyStatesBosToTos(stateList);
		log.debug("leaving getNativeStates");
		return stateToList;
	}
	/**
	 * 
	 * @return States Details in Map <key, value> Ex <1,karnataka><2,kerala>
	 * @throws ApplicationException 
	 */
	public Map<Integer, String> getStatesMap() throws Exception {
		IStateTransaction iStateTransaction = StateTransactionImpl.getInstance();
		List<State> stateList = iStateTransaction.getStates();
//		Map<Integer, String> statesMap = new HashMap<Integer, String>();
		Map<Integer, String> statesMap = new LinkedHashMap<Integer, String>();		
		Iterator<State> i = stateList.iterator();
		State state;
		while (i.hasNext()) {
			state = (State) i.next();
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
	public boolean updateState(CountryStateCityForm countryStateCityForm) throws Exception {
		IStateTransaction iStateTransaction = StateTransactionImpl.getInstance();
		boolean isUpdate = false;
		//original variables are used for checking duplication
		Boolean originalNotChanged = false;

		String stateName = "";
		String origStateName = "";

		stateName = countryStateCityForm.getStateName().trim();
		origStateName = countryStateCityForm.getOrigStateName().trim();
		
		int countryId = 0;
		if ((countryStateCityForm.getCountryId() != null) || (!countryStateCityForm.getCountryId().isEmpty())){
			countryId = Integer.parseInt(countryStateCityForm.getCountryId());
		}
		int origCountryId = countryStateCityForm.getEditedCountryId();
		
		if (stateName.trim().equalsIgnoreCase(origStateName.trim()) && countryId == origCountryId) {
			originalNotChanged = true;
		}

		if (!originalNotChanged) {
			//State duplState = CountryStateCityHelper.getInstance().populateStatesDataFormForm(countryStateCityForm);

			State duplState = iStateTransaction.isStateNameDuplcated(countryStateCityForm);
			if (duplState != null && duplState.getIsActive()) {
				throw new DuplicateException();
			} else if (duplState != null && !duplState.getIsActive()) {
				countryStateCityForm.setDuplStateId(duplState.getId());
				throw new ReActivateException();
			}
		}
		State state = CountryStateCityHelper.getInstance().populateStatesDataFormForm(countryStateCityForm);
	
		state.setLastModifiedDate(new Date());
		state.setModifiedBy(countryStateCityForm.getUserId());
		isUpdate = iStateTransaction.updateState(state);
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
	public boolean deleteState(int id, Boolean activate, CountryStateCityForm countryStateCityForm) throws Exception {
		IStateTransaction iStateTransaction = StateTransactionImpl.getInstance();
		boolean result = iStateTransaction.deleteState(id, activate, countryStateCityForm);
		log.debug("leaving deleteState");
		return result;
	}
}
