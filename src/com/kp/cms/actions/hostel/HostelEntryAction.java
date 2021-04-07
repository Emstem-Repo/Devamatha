package com.kp.cms.actions.hostel;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.apache.struts.upload.FormFile;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.hostel.HostelEntryForm;
import com.kp.cms.forms.hostel.HostelFeesTypeForm;
import com.kp.cms.handlers.admin.CountryHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.utilities.RenderYearList;

@SuppressWarnings("deprecation")
public class HostelEntryAction extends BaseDispatchAction{
	private static Log log = LogFactory.getLog(HostelEntryAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getHostelDeatils(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("inside getHostelDeatils");
		HostelEntryForm hostelEntryForm = (HostelEntryForm) form;
		try {
			setHostelEntryDetailsToRequest(request);
			setCountriesMapToRequest(request);  //setting countrymap to request for populating country
			setUserId(request, hostelEntryForm);
		} catch (Exception e) {
			log.error("error in getHostelDeatils...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hostelEntryForm.setErrorMessage(msg);
				hostelEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
			}
		return mapping.findForward(CMSConstants.HOSTEL_ENTRY);
	}	

	/**
	 * setting hostelList to request
	 * @param request
	 * @throws Exception 
	 */
	public void setHostelEntryDetailsToRequest(HttpServletRequest request) throws Exception{
		log.debug("start setHostelEntryDetailsToRequest");
		List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetails();
		request.setAttribute("hostelList", hostelList);
		log.debug("exit setHostelEntryDetailsToRequest");
	}
	/**
	 * 
	 * @param request
	 *            This method sets the countries map to Request useful in
	 *            populating in country selection.
	 */
	public void setCountriesMapToRequest(HttpServletRequest request) {
		log.debug("inside setCountriesMapToRequest");
		 Map<Integer,String> countriesMap = CountryHandler.getInstance().getCountriesMap();
		request.setAttribute("countriesMap", countriesMap);
	}

	/**
	 * save method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addHostelEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		log.debug("inside addHostelEntry Action");
		HostelEntryForm hostelEntryForm = (HostelEntryForm) form;
		hostelEntryForm.setId(0);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = hostelEntryForm.validate(mapping, request);
		
		validateFormSpecialCharacter(hostelEntryForm, errors, request);
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setHostelEntryDetailsToRequest(request);
				setCountriesMapToRequest(request);  //setting countrymap to request for populating country
				setstateMapToRequest(request,hostelEntryForm);
				return mapping.findForward(CMSConstants.HOSTEL_ENTRY);
			}
			isAdded = HostelEntryHandler.getInstance().addHostelEntry(hostelEntryForm, "add");
			setHostelEntryDetailsToRequest(request);
			setCountriesMapToRequest(request);  //setting countrymap to request for populating country
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.hostel.hostel.entry.exists", hostelEntryForm.getName()));
			saveErrors(request, errors);
			setHostelEntryDetailsToRequest(request);
			setCountriesMapToRequest(request);  //setting countrymap to request for populating country
			return mapping.findForward(CMSConstants.HOSTEL_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.HOSTEL_ENTRY_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setHostelEntryDetailsToRequest(request);
			setCountriesMapToRequest(request);  //setting countrymap to request for populating country
			return mapping.findForward(CMSConstants.HOSTEL_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of addHostelEntry page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hostelEntryForm.setErrorMessage(msg);
				hostelEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.hostel.hostel.entry.addsuccess", hostelEntryForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			hostelEntryForm.reset(mapping, request);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.hostel.hostel.entry.addfailure", hostelEntryForm.getName()));
			saveErrors(request, errors);
		}
		log.debug("Leaving addDisciplinaryType Action");
		return mapping.findForward(CMSConstants.HOSTEL_ENTRY);
	}
	/**
	 * load details in edit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward LoadHostelEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		HostelEntryForm hlForm = (HostelEntryForm)form;
		setRequireddataToform(hlForm, request);
		return mapping.findForward(CMSConstants.HOSTEL_ENTRY);
	}
	/**
	 * setting data to form in edit
	 * @param hlForm
	 * @param request
	 * @throws Exception
	 */
	public void setRequireddataToform(HostelEntryForm hlForm, HttpServletRequest request ) throws Exception{
		int hlId = hlForm.getId();
		hlForm.setStateOthers(null);
		List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetailsById(hlId);
		Iterator<HostelTO> itr = hostelList.iterator();
		HostelTO hostelTO;
		while(itr.hasNext()){
			hostelTO = itr.next();
			hlForm.setName(hostelTO.getName());
			if(hostelTO.getGender()!= null){
				hlForm.setGender(hostelTO.getGender());				
			}
			hlForm.setAddressLine1(hostelTO.getAddressLine1());
			hlForm.setAddressLine2(hostelTO.getAddressLine2());
			if(hostelTO.getCountryTO()!= null){
				hlForm.setCountryId(Integer.toString(hostelTO.getCountryTO().getId()));
			}
			if(hostelTO.getStateTO()!= null){
				hlForm.setStateId(Integer.toString(hostelTO.getStateTO().getId()));
			}
			hlForm.setCity(hostelTO.getCity());
			if(hostelTO.getZipCode()!= 0){
				hlForm.setZipCode(Integer.toString(hostelTO.getZipCode()));
			}
			hlForm.setPhone(hostelTO.getPhone());
			hlForm.setFaxNo(hostelTO.getFaxNo());
			hlForm.setEmail(hostelTO.getEmail());
			hlForm.setPrimaryContactName(hostelTO.getPrimaryContactName());
			hlForm.setPrimaryContactDesignation(hostelTO.getPrimaryContactDesignation());
			hlForm.setPrimaryContactPhone(hostelTO.getPrimaryContactPhone());
			hlForm.setPrimaryContactMobile(hostelTO.getPrimaryContactMobile());
			hlForm.setPrimaryContactEmail(hostelTO.getPrimaryContactEmail());

			hlForm.setSecContactName(hostelTO.getSecContactName());
			hlForm.setSecContactDesignation(hostelTO.getSecContactDesignation());
			hlForm.setSecContactPhone(hostelTO.getSecContactPhone());
			hlForm.setSecContactMobile(hostelTO.getSecContactMobile());
			hlForm.setSecContactEmail(hostelTO.getSecContactEmail());
			if(hostelTO.getNoOfFloors()!= 0){
				hlForm.setNoOfFloors(Integer.toString(hostelTO.getNoOfFloors()));
			}
			if(hostelTO.getStateOthers()!= null){
				hlForm.setStateId("Other");
				hlForm.setStateOthers(hostelTO.getStateOthers());
			}
			hlForm.setFileName(hostelTO.getFileName());
			hlForm.setContentType(hostelTO.getContentType());
		}
		setHostelEntryDetailsToRequest(request);
		setCountriesMapToRequest(request);  //setting countrymap to request for populating country
		setstateMapToRequest(request,hlForm);
		request.setAttribute("hlOperation", "edit");
	}
	/**
	 * setting stateMap to request
	 * @param request
	 * @param hlForm
	 */
	public void setstateMapToRequest(HttpServletRequest request, HostelEntryForm hlForm) {
		if (hlForm.getCountryId() != null
				&& (!hlForm.getCountryId().trim().isEmpty())) {
			Map<Integer, String> stateMap = CommonAjaxHandler.getInstance().getStatesByCountryId(Integer.parseInt(hlForm.getCountryId()));
			request.setAttribute("stateMap", stateMap);
		}
		
	}
	/**
	 * update method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateHostelEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		log.debug("inside updateHostelEntry Action");
		HostelEntryForm hostelEntryForm = (HostelEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = hostelEntryForm.validate(mapping, request);
		
		validateFormSpecialCharacter(hostelEntryForm, errors, request);
		boolean isUpdated = false;
		try {
			if(isCancelled(request)){
				setRequireddataToform(hostelEntryForm, request);
				return mapping.findForward(CMSConstants.HOSTEL_ENTRY);
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setHostelEntryDetailsToRequest(request);
				setCountriesMapToRequest(request);  //setting countrymap to request for populating country
				setstateMapToRequest(request,hostelEntryForm);
				request.setAttribute("hlOperation", "edit");
				return mapping.findForward(CMSConstants.HOSTEL_ENTRY);
			}
			isUpdated = HostelEntryHandler.getInstance().addHostelEntry(hostelEntryForm, "edit");
			setHostelEntryDetailsToRequest(request);
			setCountriesMapToRequest(request);  //setting countrymap to request for populating country
			setstateMapToRequest(request,hostelEntryForm);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.hostel.hostel.entry.exists", hostelEntryForm.getName()));
			saveErrors(request, errors);
			setHostelEntryDetailsToRequest(request);
			setCountriesMapToRequest(request);  //setting countrymap to request for populating country
			setstateMapToRequest(request,hostelEntryForm);
			request.setAttribute("hlOperation", "edit");
			return mapping.findForward(CMSConstants.HOSTEL_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.HOSTEL_ENTRY_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setHostelEntryDetailsToRequest(request);
			setCountriesMapToRequest(request);  //setting countrymap to request for populating country
			setstateMapToRequest(request,hostelEntryForm);
			request.setAttribute("hlOperation", "edit");
			return mapping.findForward(CMSConstants.HOSTEL_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of addHostelEntry page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hostelEntryForm.setErrorMessage(msg);
				hostelEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isUpdated) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.hostel.hostel.entry.updatesuccess", hostelEntryForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			hostelEntryForm.reset(mapping, request);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.hostel.hostel.entry.updatefailure", hostelEntryForm.getName()));
			saveErrors(request, errors);
		}
		log.debug("Leaving updateHostelEntry Action");
		return mapping.findForward(CMSConstants.HOSTEL_ENTRY);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this will delete the Hostel Entry
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteHostelEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		log.debug("inside deleteHostelEntry");
		HostelEntryForm hostelEntryForm = (HostelEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (hostelEntryForm.getId() != 0) {
				int hId = hostelEntryForm.getId();
				isDeleted = HostelEntryHandler.getInstance().deleteHostelEntry(hId, false, hostelEntryForm);
			}
		} catch (Exception e) {
			log.error("error in deleteHostelEntry...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hostelEntryForm.setErrorMessage(msg);
				hostelEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		setHostelEntryDetailsToRequest(request);
		setCountriesMapToRequest(request);  //setting countrymap to request for populating country
		setstateMapToRequest(request,hostelEntryForm);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.hostel.entry.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			hostelEntryForm.reset(mapping, request);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.hostel.entry.deletefailure"));
			saveErrors(request, errors);
		}
		log.debug("leaving deleteDisciplinaryType");
		return mapping.findForward(CMSConstants.HOSTEL_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this method is to activate the hostel entry
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateHostel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			 HttpServletResponse response) throws Exception {
		log.debug("Entering activateHostel");
		HostelEntryForm hostelEntryForm = (HostelEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (hostelEntryForm.getDuplId() != 0) {
				int id = hostelEntryForm.getDuplId();  //setting id for activate
				isActivated = HostelEntryHandler.getInstance().deleteHostelEntry(id, true, hostelEntryForm);
				//using for activate & delete. so for identifying activate true/false param sending to handler
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.HOSTEL_ENTRY_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setHostelEntryDetailsToRequest(request);
		setCountriesMapToRequest(request);  //setting countrymap to request for populating country
		setstateMapToRequest(request,hostelEntryForm);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.HOSTEL_ENTRY_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		log.debug("leaving activateDisciplinaryType");
		return mapping.findForward(CMSConstants.HOSTEL_ENTRY);
	}
	
	/**
	 * validation for special characters
	 * @param name
	 * @return
	 */
	private boolean nameValidate1(String name)
	{
		boolean result=false;
		Pattern p = Pattern.compile("[^A-Za-z \t]+");
        Matcher m = p.matcher(name);
        result = m.find();
        return result;
	}
	
	private void validateFormSpecialCharacter(HostelEntryForm hostelEntryForm, ActionErrors errors, HttpServletRequest request)throws Exception
	{
		if(hostelEntryForm.getCity()!=null && !hostelEntryForm.getCity().isEmpty() && nameValidate1(hostelEntryForm.getCity())){
			errors.add("error", new ActionError("knowledgepro.hostel.valid.city"));
		}
	}
	
}
