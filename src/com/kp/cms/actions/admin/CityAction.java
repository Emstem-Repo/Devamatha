package com.kp.cms.actions.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.forms.admin.CountryStateCityForm;
import com.kp.cms.handlers.admin.CityHandler;
import com.kp.cms.handlers.admin.CountryHandler;
import com.kp.cms.handlers.admin.StateHandler;

@SuppressWarnings("deprecation")
public class CityAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(CityAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *         This will set countriesMap <key,value> Ex:<1,india><2,us>.
	 *         
	 * @return to Forward cityEntry.
	 * @throws Exception
	 */
	public ActionForward initCities(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		
		    setCountriesMapToRequest(request);
		    setCityListToRequest(request);
		    
	return mapping.findForward("cityEntry");	
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *        This will add new Country 
	 * @return to mapping countryEntry
	 * @throws Exception
	 */
	public ActionForward addCity(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		    
		    log.debug("inside addCity Action");
		    CountryStateCityForm countryStateCityForm = (CountryStateCityForm)form;
		    ActionMessages messages = new ActionMessages();
		    ActionErrors errors = countryStateCityForm.validate(mapping, request);
		    boolean isAdded = false;
		    if(errors.isEmpty()) {
		        try {
		        	isAdded = CityHandler.getInstance().addCity(countryStateCityForm);
		        } catch (Exception e){
		        	log.error("error occured in addCity method of CityAction class",e);
		        }
		    } else {
		    	saveErrors(request, errors);
		    }
		    setCityListToRequest(request);
			setCountriesMapToRequest(request);
			if(isAdded){
		    	//successfully added city
				ActionMessage message = new ActionMessage("knowledgepro.admin.city.addsuccess",countryStateCityForm.getCityName());
				messages.add("messages", message);
				saveMessages(request, messages);
				countryStateCityForm.reset(mapping, request);
		    } else {
		    	// failure
		    	errors.add("error", new ActionError("knowledgepro.admin.city.addfailure",countryStateCityForm.getCountryName()));
	    		saveErrors(request,errors);
		    }
			log.debug("Leaving addCity Action");
		    return mapping.findForward("cityEntry");
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward 
	 *         This action method will called when particular City based on City
	 *         based on the stateId.  
	 * @throws Exception
	 */
	public ActionForward deleteCity(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		    
			log.debug("inside delete City Action");
	    	CountryStateCityForm countryStateCityForm = (CountryStateCityForm)form;
	    	ActionMessages messages = new ActionMessages();
	    	ActionErrors errors = new ActionErrors();
	    	boolean isDeleted = false;
		    try {
			    if(countryStateCityForm.getCityId() != null) {
			    	int cityId = Integer.parseInt(countryStateCityForm.getCityId());
			    	isDeleted = CityHandler.getInstance().deleteCity(cityId);
			    }
		    } catch (Exception e) {
		    	log.error("error occured in deleteCity method of CityAction class",e);
		    }
		    
		    setCityListToRequest(request);
			setCountriesMapToRequest(request);
		    if(isDeleted) {
		    	// success msg.
		    	ActionMessage message = new ActionMessage("knowledgepro.admin.city.deletesuccess",countryStateCityForm.getCityName());
				messages.add("messages", message);
				saveMessages(request, messages);
		    } else {
		    	// error msg.
		    	errors.add("error", new ActionError("knowledgepro.admin.city.deletefailure",countryStateCityForm.getCityName()));
	    		saveErrors(request,errors);
		    }
		    log.debug("Leaving delete City Action");
		return mapping.findForward("cityEntry");
	}	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 
	 *         This action method will called when particular City need to be updated
	 *         based on the stateId.
	 * @throws Exception
	 */
	public ActionForward updateCity(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		
			log.debug("inside update State Action");
	    	CountryStateCityForm countryStateCityForm = (CountryStateCityForm)form;
	    	ActionMessages messages = new ActionMessages();
	    	ActionErrors errors = new ActionErrors();
	    	boolean isUpdated = false;
		    try {
			    if(countryStateCityForm.getCityId() != null) {
			    	isUpdated = CityHandler.getInstance().updateCity(countryStateCityForm);
			    }
		    } catch (Exception e) {
		    	log.error("error occured in updateCity method of CityAction class",e);
		    }
		    
		    setCityListToRequest(request);
			setCountriesMapToRequest(request); 
			if(isUpdated) {
				// success
				ActionMessage message = new ActionMessage("knowledgepro.admin.city.updatesuccess",countryStateCityForm.getCityName());
				messages.add("messages", message);
				saveMessages(request, messages);
				countryStateCityForm.reset(mapping, request);
			} else {
				// failure
				errors.add("error", new ActionError("knowledgepro.admin.city.updatefailure",countryStateCityForm.getCityName()));
	    		saveErrors(request,errors);
			}
		    log.debug("leaving update City action");
		return mapping.findForward("cityEntry");
	}
	
	/**
	 * 
	 * @param request
	 * This method sets the countries map to Request
	 * useful in populating in country selection.
	 */
	public void setCountriesMapToRequest(HttpServletRequest request) {
		Map countriesMap = CountryHandler.getInstance().getCountriesMap();
		request.setAttribute("countriesMap",countriesMap);
		log.debug("No of countries"+countriesMap.size());
	}

	/**
	 * 
	 * @param request
	 * This method sets the cityList to Request having CityTo objects
	 * used to display city record on UI.
	 */
	public void setCityListToRequest(HttpServletRequest request) {
		List cityList = CityHandler.getInstance().getCitys();
		request.setAttribute("cityList",cityList);
		log.debug("No of City in list"+cityList.size());
	}
	
	/**
	 * 
	 * @param request
	 * This method sets the stateList to Request having StateTo objects
	 * used to display city record on UI.
	 * @throws Exception 
	 */
	public void setStatesMapToRequest(HttpServletRequest request) throws Exception {
		Map stateMap = StateHandler.getInstance().getStatesMap();
		request.setAttribute("stateMap",stateMap);
		log.debug("No of States in Map"+stateMap.size());
	}
}
