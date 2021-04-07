package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.City;
import com.kp.cms.forms.admin.CountryStateCityForm;
import com.kp.cms.helpers.admin.CountryStateCityHelper;
import com.kp.cms.transactions.admin.ICityTransaction;
import com.kp.cms.transactionsimpl.admin.CityTransactionImpl;


public class CityHandler {
    
	public static volatile CityHandler cityHandler = null;
	private static final Log log = LogFactory.getLog(CityHandler.class);
    private CityHandler(){
    	
    }
	   public static CityHandler getInstance() {
		      if(cityHandler == null) {
		    	  cityHandler = new CityHandler();
		    	  return cityHandler;
		      }
		      return cityHandler;
	   }
	   /**
	    * 
	    * @param cityName
	    *         This will add a city name passed as param.
	    * @return boolean true / false based on result.
	    */
	   public boolean addCity(CountryStateCityForm countryStateCityForm){
		   	   ICityTransaction iCityTransaction= CityTransactionImpl.getInstance();
		   	   boolean isAdded = false;
			   try {
				   City city = CountryStateCityHelper.getInstance().populateCityDataFormForm(countryStateCityForm);
				 
				   city.setCreatedDate(new Date());
				   city.setLastModifiedDate(new Date());
				   isAdded = iCityTransaction.addCity(city);
			   }catch (Exception e) {
					log.debug("Error in addCity method");
			}
	   return isAdded;
	   }
    
	   /**
	    * 
	    * @return list of CitysTO objects, will be used in UI to dispaly.
	    */
	   public List getCitys() {
			   ICityTransaction iCityTransaction= CityTransactionImpl.getInstance();
			   List<City> cityList = iCityTransaction.getCities();
			   List cityToList = new ArrayList();
			   try {
				   cityToList = CountryStateCityHelper.getInstance().copyCityBosToTos(cityList);
			   } catch (Exception e) {
			}
	   return  cityToList;
	   }
	   /**
	    * 
	    * @return Citys Details in Map <key, value> Ex <1,karnataka><2,kerala>
	    */
	   public Map getCitysMap() {           
		   ICityTransaction iCityTransaction= CityTransactionImpl.getInstance();
		   List<City> cityList = iCityTransaction.getCities();
		   Map citysMap = new HashMap();
		   Iterator cityItr = cityList.iterator();
		   City city;
		    while(cityItr.hasNext()) {
			   city = (City)cityItr.next();
			   citysMap.put(city.getId(), city.getName());
			}
	   return citysMap;
   }
	   
	   /**
	    * 
	    * @param cityName
	    *         This will update a city name passed in param.
	    * @return boolean true / false based on result.
	    */
	   public boolean updateCity(CountryStateCityForm countryStateCityForm){
		   	   ICityTransaction iCityTransaction= CityTransactionImpl.getInstance();
		   	   boolean isUpdate = false;
		   	   try{
				   City city = CountryStateCityHelper.getInstance().populateCityDataFormForm(countryStateCityForm);
				
				   city.setLastModifiedDate(new Date());
			       isUpdate = iCityTransaction.updateCity(city);
			   }catch (Exception e) {
					log.debug("Error in updateCity method");
			}    
	   return isUpdate;
	   }
	   
	   /**
	    * 
	    * @param cityName
	    *         This will delete a city by id passed as param.
	    * @return boolean true / false based on result.
	    */
	   public boolean deleteCity(int id){
		   	   ICityTransaction iCityTransaction= CityTransactionImpl.getInstance();
			   City city = new City();
			   city.setId(id);
			  return iCityTransaction.deleteCity(city);
	   }
	  
}
