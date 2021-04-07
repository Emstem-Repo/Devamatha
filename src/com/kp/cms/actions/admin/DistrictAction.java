package com.kp.cms.actions.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.DistrictForm;
import com.kp.cms.handlers.admin.CountryHandler;
import com.kp.cms.handlers.admin.StateHandler;
import com.kp.cms.handlers.admin.DistrictHandler;
import com.kp.cms.to.admin.DistrictTO;

/**
 * 
 * @author Date Created : 09 Jan 2009 
 *         This action class used for state related CRUD operation
 */
@SuppressWarnings("deprecation")
public class DistrictAction extends BaseDispatchAction {

	private static Log log = LogFactory.getLog(StateAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return set statesList having statesTo objects to request, forward to
	 *         stateEntry
	 * @throws Exception
	 */
	public ActionForward initDistrict(ActionMapping mapping, ActionForm form,	HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		DistrictForm countryStateCityForm = (DistrictForm)form;
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setStatesListToRequest(request);  //setting statelist to request for UI display
			setCountriesMapToRequest(request);  //setting countrymap to request for populating country
		} catch (Exception e) {
			log.error("error submit state page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				countryStateCityForm.setErrorMessage(msg);
				countryStateCityForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		return mapping.findForward(CMSConstants.DISTRICT_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new Country
	 * @return to mapping countryEntry
	 * @throws Exception
	 */
	public ActionForward addDistrict(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside addState Action");
		DistrictForm countryStateCityForm = (DistrictForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = countryStateCityForm.validate(mapping, request);
		//if(countryStateCityForm.getBankStateId().length()>2){
			//errors.add("error", new ActionError("knowledgepro.admin.district.bankdistrictid.toolong"));
		//}
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setStatesListToRequest(request);
				setCountriesMapToRequest(request);
				//empty should not allow
				if(countryStateCityForm.getDistrictName().trim().isEmpty()){
					countryStateCityForm.setDistrictName(null);
				}
				return mapping.findForward(CMSConstants.DISTRICT_ENTRY);
			}
			boolean isSpcl=nameValidate(countryStateCityForm.getDistrictName()); //validation checking for special characters
			if(isSpcl)
			{
				errors.add("error", new ActionError("knowledgepro.admin.special"));
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setStatesListToRequest(request);
				setCountriesMapToRequest(request);
				//empty should not allow
				if((countryStateCityForm.getStateName().trim()).isEmpty()){
					countryStateCityForm.setStateName(null);
				}
				return mapping.findForward(CMSConstants.DISTRICT_ENTRY);
			}
			
			setUserId(request, countryStateCityForm);
			isAdded = DistrictHandler.getInstance().addDistrict(countryStateCityForm);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.district.name.exists"));
			saveErrors(request, errors);
			setStatesListToRequest(request);
			setCountriesMapToRequest(request);
			return mapping.findForward(CMSConstants.DISTRICT_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.DISTRICT_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setStatesListToRequest(request);
			setCountriesMapToRequest(request);
			return mapping.findForward(CMSConstants.DISTRICT_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of district page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				countryStateCityForm.setErrorMessage(msg);
				countryStateCityForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		setStatesListToRequest(request);
		setCountriesMapToRequest(request);
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.district.addsuccess", countryStateCityForm.getDistrictName());
			messages.add("messages", message);
			saveMessages(request, messages);
			countryStateCityForm.reset(mapping, request);
		}
		return mapping.findForward(CMSConstants.DISTRICT_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward This action method will called when particular
	 *         State need to be deleted based on the stateId.
	 * @throws Exception
	 */
	public ActionForward deleteDistrict(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		DistrictForm countryStateCityForm = (DistrictForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (countryStateCityForm.getDistrictId() != null) {
				int stateId = Integer.parseInt(countryStateCityForm.getDistrictId());
				setUserId(request, countryStateCityForm);  //setting user id for updating last changed details
				isDeleted = DistrictHandler.getInstance().deleteDistrict(stateId, false, countryStateCityForm);
			}
			setStatesListToRequest(request);
			setCountriesMapToRequest(request);
		} catch (Exception e) {
			log.error("error in delete state in action...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				countryStateCityForm.setErrorMessage(msg);
				countryStateCityForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.district.deletesuccess",	countryStateCityForm.getDistrictName());
			messages.add("messages", message);
			saveMessages(request, messages);
			countryStateCityForm.reset(mapping, request);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.admin.district.deletefailure",countryStateCityForm.getDistrictName()));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.DISTRICT_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return This action method will called when particular State need to be
	 *         updated based on the stateId.
	 * @throws Exception
	 */
	public ActionForward updateDistrict(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.debug("inside update State Action");
		DistrictForm countryStateCityForm = (DistrictForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = countryStateCityForm.validate(mapping, request);
		boolean isUpdated = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setStatesListToRequest(request);
				setCountriesMapToRequest(request);
				if(countryStateCityForm.getStateName().trim().isEmpty()){
					countryStateCityForm.setStateName(null);
				}
				request.setAttribute("districtOperation", "edit");
				return mapping.findForward(CMSConstants.DISTRICT_ENTRY);
			}
			boolean isSpcl=nameValidate(countryStateCityForm.getDistrictName()); //validation checking for special characters
			if(isSpcl)
			{
				errors.add("error", new ActionError("knowledgepro.admin.special"));
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setStatesListToRequest(request);
				setCountriesMapToRequest(request);
				if(countryStateCityForm.getStateName().trim().isEmpty()){
					countryStateCityForm.setStateName(null);
				}
				request.setAttribute("districtOperation", "edit");
				return mapping.findForward(CMSConstants.DISTRICT_ENTRY);
			}
			
			if (countryStateCityForm.getStateId() != null) {
				setUserId(request, countryStateCityForm);
				isUpdated = DistrictHandler.getInstance().updateDistrict(countryStateCityForm);
			}
			setStatesListToRequest(request);
			setCountriesMapToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.district.name.exists"));
			saveErrors(request, errors);
			setStatesListToRequest(request);
			setCountriesMapToRequest(request);
			request.setAttribute("districtOperation", "edit");
			return mapping.findForward(CMSConstants.DISTRICT_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.DISTRICT_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setStatesListToRequest(request);
			setCountriesMapToRequest(request);
			request.setAttribute("districtOperation", "edit");
			return mapping.findForward(CMSConstants.DISTRICT_ENTRY);
		} catch (Exception e) {
			log.error("error in update district page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				request.setAttribute("districtOperation", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				countryStateCityForm.setErrorMessage(msg);
				countryStateCityForm.setErrorStack(e.getMessage());
				request.setAttribute("districtOperation", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isUpdated) {
			// successfully deleted.
			ActionMessage message = new ActionMessage("knowledgepro.admin.district.updatesuccess",countryStateCityForm.getDistrictName());
			messages.add("messages", message);
			countryStateCityForm.reset(mapping, request);
			saveMessages(request, messages);
			countryStateCityForm.reset(mapping, request);
		} else {
			// failed to update.
			errors.add("error", new ActionError("knowledgepro.admin.district.updatefailure",countryStateCityForm.getDistrictName()));
			saveErrors(request, errors);
			request.setAttribute("districtOperation", "edit");
			return mapping.findForward(CMSConstants.DISTRICT_ENTRY);
		}
		request.setAttribute("districtOperation", "add");
		return mapping.findForward(CMSConstants.DISTRICT_ENTRY);
	}

	/**
	 * 
	 * @param request
	 *            This method sets the countries map to Request useful in
	 *            populating in country selection.
	 */
	public void setCountriesMapToRequest(HttpServletRequest request) {
		log.debug("inside setCountriesMapToRequest");
		Map<Integer, String> countriesMap;
		try {
			countriesMap = StateHandler.getInstance().getStatesMap();
			request.setAttribute("statesMap", countriesMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * 
	 * @param request
	 *            This method sets the StatesList to Request used to display
	 *            states record on UI.
	 * @throws Exception 
	 */
	public void setStatesListToRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setStatesListToRequest");
		List<DistrictTO> statesList = DistrictHandler.getInstance().getDistrict();
		request.setAttribute("districtList", statesList);
	}

	/*
	 *method for activating state 
	 */
	public ActionForward activateDistrict(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DistrictForm countryStateCityForm = (DistrictForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (countryStateCityForm.getDuplStateId() != 0) {
				int stateId = countryStateCityForm.getDuplStateId();
				setUserId(request, countryStateCityForm);
				isActivated = DistrictHandler.getInstance().deleteDistrict(stateId, true, countryStateCityForm);
				//deleteState(stateId, true, countryStateCityForm) method called for delete & reactivate. so for identifying activate tru/false param sending 
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.DISTRICT_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setStatesListToRequest(request);
		setCountriesMapToRequest(request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.DISTRICT_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		return mapping.findForward(CMSConstants.DISTRICT_ENTRY);
	}
	/**
	 * validation for special characters
	 * @param name
	 * @return
	 */
	private boolean nameValidate(String name)
	{
		boolean result=false;
		Pattern pattern = Pattern.compile("[^A-Za-z0-9 \t]+");
        Matcher matcher = pattern.matcher(name);
        result = matcher.find();
        return result;

	}

}
