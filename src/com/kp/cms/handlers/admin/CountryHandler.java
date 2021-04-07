package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.State;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.transactions.admin.ICountryTransaction;
import com.kp.cms.transactionsimpl.admin.CountryTranscationImpl;

/**
 * 
 * Handler class for Country 
 */

public class CountryHandler {

	   public static volatile CountryHandler countryHandler = null;
	   private static final Log log = LogFactory.getLog(CountryHandler.class);
	   
	   public static CountryHandler getInstance() {
		      if(countryHandler == null) {
		    	  countryHandler = new CountryHandler();
		    	  return countryHandler;
		      }
		      return countryHandler;
	   }
	   /**
	    * 
	    * @param countryName
	    *         This will add a country name passed as param.
	    * @return boolean true / false based on result.
	    */
	   public boolean addCountry(String countryName) throws DuplicateException,ApplicationException{
		   	   log.debug("Inside addCountry handler method");
		   	   ICountryTransaction iCountryTransaction= CountryTranscationImpl.getInstance();
			   Country country = new Country();
			   country.setName(countryName);
			  
			   country.setCreatedDate(new Date());
			   country.setLastModifiedDate(new Date());
			   boolean isAdded = iCountryTransaction.addCountry(country);
			   log.debug("return from addCountry handler method with success " +isAdded);
	   return isAdded;
	   }
       
	   /**
	    * 
	    * @return list of CountriesTO objects.
	    */
	   public List<CountryTO> getCountries() {
			   ICountryTransaction iCountryTransaction= CountryTranscationImpl.getInstance();
			   List<Country> countryList = iCountryTransaction.getCountries();
			   List<CountryTO> countryToList = new ArrayList<CountryTO>();
			   Iterator<Country> i = countryList.iterator();
			   Country country;
			   CountryTO countryTo;
			   while(i.hasNext()) {
				   countryTo = new CountryTO();
				   country = (Country)i.next();
				   Set<State> stateSet=country.getStates();
				   List<StateTO> stateList=new ArrayList<StateTO>();
					   if(stateSet!=null){
						   Iterator<State> st=stateSet.iterator();
						   while (st.hasNext()) {
							State state = (State) st.next();
							if (state!=null && state.getIsActive()) {
								StateTO toState = new StateTO();
								toState.setId(state.getId());
								toState.setName(state.getName());
								stateList.add(toState);
							}
						}
					   }
					   countryTo.setStateList(stateList);
				   countryTo.setId(country.getId());
				   countryTo.setName(country.getName());
				   countryToList.add(countryTo);
			   }
	   return countryToList;
	   }
	   /**
	    * 
	    * @return Countries Details in Map <key, value> Ex <1,india><2,us>
	    */
	   public Map<Integer, String> getCountriesMap() {
		   ICountryTransaction iCountryTransaction= CountryTranscationImpl.getInstance();
		   List<Country> countryList = iCountryTransaction.getCountries();
//		   Map<Integer, String> countriesMap = new TreeMap<Integer, String>();
		   Map<Integer, String> countriesMap = new LinkedHashMap<Integer, String>();
		   Iterator<Country> i = countryList.iterator();
		   Country country;
		    while(i.hasNext()) {
			   country = (Country)i.next();
			   countriesMap.put(country.getId(), country.getName());
			   
			}
	   return countriesMap;
      }
	   
	   /**
	    * 
	    * @param countryName
	    *         This will update a country name passed in param.
	    * @return boolean true / false based on result.
	    */
	   public boolean updateCountry(int id,String countryName) throws Exception{
		       log.debug("Inside updateCountry handler method");
		       ICountryTransaction iCountryTransaction= CountryTranscationImpl.getInstance();
			   Country country = new Country();
			   country.setId(id);
			   country.setName(countryName);
			 
			   country.setLastModifiedDate(new Date());
			   boolean isUpdated = iCountryTransaction.updateCountry(country);
			   log.debug("return from updateCountry handler method with success " +isUpdated);
	   return isUpdated;
	   }
	   
	   /**
	    * 
	    * @param countryName
	    *         This will delete a country by id passed as param.
	    * @return boolean true / false based on result.
	    */
	   public boolean deleteCountry(int id) throws Exception {
		   	   log.debug("Inside deleteCountry handler method");
		   	   ICountryTransaction iCountryTransaction= CountryTranscationImpl.getInstance();
			   Country country = new Country();
			   country.setId(id);
			   boolean isDeleted = iCountryTransaction.deleteCountry(country);
			   log.debug("return from updateCountry handler method with success " +isDeleted);
	   return isDeleted;
	   }
}
