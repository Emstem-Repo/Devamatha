package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.City;
import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.District;
import com.kp.cms.bo.admin.State;
import com.kp.cms.forms.admin.CountryStateCityForm;
import com.kp.cms.to.admin.CityTO;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.admin.DistrictTO;
import com.kp.cms.to.admin.StateTO;

/**
 * 
 * @version 1.0
 * Date 09/jan/2009
 * This is helper class for Country State & City Master configurations
 */
public class CountryStateCityHelper {
	private static final Log log = LogFactory.getLog(CountryStateCityHelper.class);
	public static volatile CountryStateCityHelper countryStateCityHelper = null;
	   public static CountryStateCityHelper getInstance() {
		      if(countryStateCityHelper == null) {
		    	  countryStateCityHelper = new CountryStateCityHelper();
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
	public List<StateTO> copyStatesBosToTos(List<State> stateList) {
		List<StateTO> stateToList = new ArrayList<StateTO>();
		Iterator<State> iterator = stateList.iterator();
		State state;
		StateTO stateTo;
		while(iterator.hasNext()) {
			stateTo = new StateTO();
			state = (State)iterator.next();
			stateTo.setId(state.getId());
			stateTo.setName(state.getName());
			
			//raghu
			Set<District> districtSet=state.getDistrict();
			   List<DistrictTO> districtList=new ArrayList<DistrictTO>();
				   if(districtSet!=null){
					   Iterator<District> district=districtSet.iterator();
					   while (district.hasNext()) {
						   District district1 = (District) district.next();
						if (district1!=null && district1.getIsActive()) {
							DistrictTO todistrict = new DistrictTO();
							todistrict.setId(district1.getId());
							todistrict.setName(district1.getName());
							districtList.add(todistrict);
						}
					}
				 }
			stateTo.setDistrictList(districtList);
			
			
			stateTo.setCountryId(state.getCountry().getId());
			stateTo.setCountryName(state.getCountry().getName());
			stateTo.setBankStateId(state.getBankStateId());
			stateToList.add(stateTo);
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
	public State populateStatesDataFormForm(CountryStateCityForm countryStateCityForm) throws Exception{
		State state = new State();
		Country country = new Country();
	       // this stateId is ''->empty when adding state and notnull/empty when updating state.
	    if(countryStateCityForm.getStateId() != null && countryStateCityForm.getStateId().length() != 0) {
	    	state.setId(Integer.parseInt(countryStateCityForm.getStateId()));
	     }
	    country.setId(Integer.parseInt(countryStateCityForm.getCountryId()));
		state.setName(countryStateCityForm.getStateName().trim());
		state.setCountry(country);
		state.setBankStateId(countryStateCityForm.getBankStateId());
		state.setIsActive(true);
		log.debug("leaving populateStatesDataFormForm");
	    return state;
	}
	
	/**
	 *    
	 * @param stateList
	 *         will copy the City BO's to TO's
	 * @return cityToList having CityTO objects.
	 */
	public List<CityTO> copyCityBosToTos(List<City> cityList) throws Exception{
		List<CityTO> cityToList = new ArrayList<CityTO>();
		Iterator<City> iterator = cityList.iterator();
		City city;
		CityTO cityTo;
		CountryTO countryTo;
		StateTO stateTo;
		while(iterator.hasNext()) {
			cityTo = new CityTO();
			countryTo = new CountryTO();
			stateTo = new StateTO();
	   
			city = (City)iterator.next();
			cityTo.setId(city.getId());
			cityTo.setName(city.getName());
			// creating to countryTo object by setting name.
		    countryTo.setName(city.getState().getCountry().getName());
		    countryTo.setId(city.getState().getCountry().getId());
		   // creating stateTo object setting above country object. 
		    stateTo.setName(city.getState().getName());
		    stateTo.setId(city.getState().getId());
		    stateTo.setCountryTo(countryTo);
		   // setting above state object to city object
			cityTo.setStateTo(stateTo);
		   
			 cityToList.add(cityTo);
			 }
		log.debug("leaving copyCityBosToTos");
		return cityToList;   
	}
	
	/**
	 * 
	 * @param countryStateCityForm
	 *        Creates State BO from CountryStateCityForm.
	 * @return State BO Object.
	 * @throws Exception may throw Number format Exception while copying.
	 */
	public City populateCityDataFormForm(CountryStateCityForm countryStateCityForm) throws Exception{
		City city = new City();
	    State state = new State();
       // this stateId is ''->empty when adding state and notnull/empty when updating state.
        if(countryStateCityForm.getCityId() != null && countryStateCityForm.getCityId().length() != 0) {
    	    city.setId(Integer.parseInt(countryStateCityForm.getCityId()));
        }
        state.setId(Integer.parseInt(countryStateCityForm.getStateId()));
        city.setName(countryStateCityForm.getCityName());
        city.setState(state);
        log.debug("leaving populateCityDataFormForm");
	    return city;
	}
	
	/**
	 * 
	 * @param countryStateCityForm
	 * @param city
	 * @throws Exception
	 */
	public void setCityDateToForm(CountryStateCityForm countryStateCityForm,City city) throws Exception{
	    countryStateCityForm.setCountryId(String.valueOf(city.getState().getCountry().getId()));
	    countryStateCityForm.setStateId(String.valueOf(city.getState().getId()));
	    countryStateCityForm.setCityId(String.valueOf(city.getId()));
	    countryStateCityForm.setCityName(city.getName());
	    log.debug("leaving setCityDateToForm");
	}
}
