package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.District;
import com.kp.cms.bo.admin.State;
import com.kp.cms.forms.admin.DistrictForm;
import com.kp.cms.to.admin.DistrictTO;

/**
 * 
 * @version 1.0
 * Date 09/jan/2009
 * This is helper class for Country State & City Master configurations
 */
public class DistrictHelper {
	private static final Log log = LogFactory.getLog(DistrictHelper.class);
	public static volatile DistrictHelper countryStateCityHelper = null;
	   public static DistrictHelper getInstance() {
		      if(countryStateCityHelper == null) {
		    	  countryStateCityHelper = new DistrictHelper();
		    	  return countryStateCityHelper;
		      }
		      return countryStateCityHelper;
	   }
	/**
	 *    
	 * @param stateList
	 *         will copy the State BO's to TO's
	 * @return stateToList having StatesTO objects.
	 */
	public List<DistrictTO> copyDistrictBosToTos(List<District> stateList) {
		List<DistrictTO> stateToList = new ArrayList<DistrictTO>();
		Iterator<District> iterator = stateList.iterator();
		District district;
		DistrictTO districtTo;
		while(iterator.hasNext()) {
			districtTo = new DistrictTO();
			district = (District)iterator.next();
			districtTo.setId(district.getId());
			districtTo.setName(district.getName());
			districtTo.setStateId(district.getState().getId());
			districtTo.setStateName(district.getState().getName());
			//stateTo.setBankStateId(state.getBankStateId());
			stateToList.add(districtTo);
		}
		log.debug("leaving copyStatesBosToTos");
		return stateToList;   
	}
	
	/**
	 * 
	 * @param countryStateCityForm
	 *        Creates State BO from CountryStateCityForm.
	 * @return State BO Object.
	 * @throws Exception may throw Number format Exception while copying.
	 */
	public District populateDistrictDataFormForm(DistrictForm countryStateCityForm) throws Exception{
		
		District district = new District();
	       // this stateId is ''->empty when adding state and notnull/empty when updating state.
	    if(countryStateCityForm.getDistrictId() != null && countryStateCityForm.getDistrictId().length() != 0) {
	    	district.setId(Integer.parseInt(countryStateCityForm.getDistrictId()));
	     }
	    State state = new State();
	    state.setId(Integer.parseInt(countryStateCityForm.getStateId()));
	    district.setState(state);
	    
	    district.setName(countryStateCityForm.getDistrictName().trim());
	    district.setIsActive(true);
		log.debug("leaving populateStatesDataFormForm");
	    return district;
	}
	
	
	
	
}
