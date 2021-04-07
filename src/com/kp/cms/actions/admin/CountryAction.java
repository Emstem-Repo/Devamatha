package com.kp.cms.actions.admin;


import java.util.List;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admin.CountryStateCityForm;
import com.kp.cms.handlers.admin.CountryHandler;

/**
 * 
 * 
 * Action class for Country Management
 */
@SuppressWarnings("deprecation")
public class CountryAction extends BaseDispatchAction{
	
	
	private static Log log = LogFactory.getLog(CountryAction.class);
	
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
	public ActionForward addCountry(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		    
		    log.debug("inside addCountry");
		    CountryStateCityForm countryStateCityForm = (CountryStateCityForm)form;
		    ActionMessages messages = new ActionMessages();
		    ActionErrors errors = countryStateCityForm.validate(mapping, request);
		    boolean isAdded = false;
		    try {
			    if(errors.isEmpty()) {
			    	String countryName = countryStateCityForm.getCountryName();
				    log.debug("Country Name"+countryName);
				    isAdded = CountryHandler.getInstance().addCountry(countryName);
			    } else {
			    	saveErrors(request, errors);
			    }
		    }
		    catch (DuplicateException e) {
		    	// failed to add country
	    		errors.add("error", new ActionError("knowledgepro.admin.country.addexist",countryStateCityForm.getCountryName()));
	    		saveErrors(request,errors);
			}
            catch (ApplicationException e) {
		    	// failed to add country
            	String msg = super.handleApplicationException(e);
            	countryStateCityForm.setErrorMessage(msg);
            	countryStateCityForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		    }
		    setCountriesListToRequest(request);
			if(isAdded){
	    		// success message added successfully.
				ActionMessage message = new ActionMessage("knowledgepro.admin.country.addsuccess",countryStateCityForm.getCountryName());
				messages.add("messages", message);
				saveMessages(request, messages);
				countryStateCityForm.reset(mapping, request);
	    	}
			log.debug("Leaving CountryAction");
		    return mapping.findForward("countryEntry");
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return set countriesList to request, forward to countryEntry
	 * @throws Exception
	 */
	public ActionForward initCountries(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		
		    log.debug("Entering initCountries ");
		    setCountriesListToRequest(request);
			log.debug("Leaving initCountries");
		  
	return mapping.findForward("countryEntry");	
	}	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 *        This action method is to delete single country. 
	 * @return returns error messages based on success / failure.
	 * @throws Exception
	 */
	public ActionForward deleteCountry(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{

		    CountryStateCityForm countryStateCityForm = (CountryStateCityForm)form;
		    ActionErrors errors = new ActionErrors();
		    ActionMessages messages = new ActionMessages();
		    boolean isDeleted = false;
		    String countryName = countryStateCityForm.getCountryName();
		    try {
			    if(countryStateCityForm.getCountryId() != null) {
			    	int countryId = Integer.parseInt(countryStateCityForm.getCountryId());
			    	isDeleted = CountryHandler.getInstance().deleteCountry(countryId);
			    	countryStateCityForm.reset(mapping, request);
			    }
		    } catch (Exception e) {
		    	log.error("error occured in deleteCountry of CountryAction class.",e);
		    	// failure error message.
	    		errors.add("error", new ActionError("knowledgepro.admin.country.deletefailure",countryName));
	    		saveErrors(request,errors);
		    }
		    setCountriesListToRequest(request);
	    	if(isDeleted){
	    		// success deleted
	    		ActionMessage message = new ActionMessage("knowledgepro.admin.country.deletesuccess",countryName);
				messages.add("messages", message);
				saveMessages(request, messages);
	    	}
		  
	return mapping.findForward("countryEntry");	
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *        This will update Country name 
	 * @return to mapping countryEntry
	 * @throws Exception
	 */
	public ActionForward updateCountry(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		    
		    CountryStateCityForm countryStateCityForm = (CountryStateCityForm)form;
		    ActionErrors errors = new ActionErrors();
		    ActionMessages messages = new ActionMessages();
		    boolean isUpdated = false;
		    try {
		    	String countryName = countryStateCityForm.getCountryName();
		    	if(countryStateCityForm.getCountryId() != null) {
		    		int countryId = Integer.parseInt(countryStateCityForm.getCountryId());
		    		isUpdated = CountryHandler.getInstance().updateCountry(countryId,countryName);
		    	}
		    } catch (Exception e) {
		    	log.error("error occured in updateCountry of CountryAction class.",e);
		    	 //    	failed to update data.
		    	errors.add("error", new ActionError("knowledgepro.admin.country.updatefailure",countryStateCityForm.getCountryName()));
	    		saveErrors(request,errors);
		    }
		    if(isUpdated) {
        	    	// successfully deleted.
		    	 ActionMessage message = new ActionMessage("knowledgepro.admin.country.updatesuccess",countryStateCityForm.getCountryName());
				 messages.add("messages", message);
				 saveMessages(request, messages);
				 countryStateCityForm.reset(mapping, request);
		    }
		    setCountriesListToRequest(request);

		    return mapping.findForward("countryEntry");
	}
	
	/**
	 * 
	 * @param request
	 * This method sets the Countries to Request
	 * used to display states record on UI.
	 */
	public void setCountriesListToRequest(HttpServletRequest request) {
		List countriesList = CountryHandler.getInstance().getCountries();
		request.setAttribute("countriesList",countriesList);
		log.debug("No of Countries in list"+countriesList.size());
	}
	
}
