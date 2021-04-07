package com.kp.cms.actions.exam;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.bo.exam.ExamValuators;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.exam.ExternalEvaluatorForm;
import com.kp.cms.handlers.admin.CountryHandler;
import com.kp.cms.handlers.exam.ExternalEvaluatorHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.exam.ExternalEvaluatorTO;
import com.kp.cms.utilities.CommonUtil;

/**
 * @author dIlIp
 *
 */
@SuppressWarnings("deprecation")
public class ExternalEvaluatorAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ExternalEvaluatorAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExternalEvaluator(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Start of ExternalEvaluatorAction --- initExternalEvaluator");
		ExternalEvaluatorForm externalEvaluatorForm=(ExternalEvaluatorForm)form;		
		try {
			setCountriesMapToRequest(request);
			assignListToForm(externalEvaluatorForm);
			externalEvaluatorForm.clearAll();			
		} catch (Exception e) {
			log.error("Error in initializing ExternalEvaluator",e);
				log.error("Error occured in initExternalEvaluator of ExternalEvaluatorAction");
				String msg = super.handleApplicationException(e);
				externalEvaluatorForm.setErrorMessage(msg);
				externalEvaluatorForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving of ExternalEvaluatorAction --- initExternalEvaluator");
		return mapping.findForward(CMSConstants.INIT_EXTERNALEVALUATOR);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addExternalEvaluator(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entring into-- ExternalEvaluatorAction --- addExternalEvaluator");
		setCountriesMapToRequest(request);
		ExternalEvaluatorForm externalEvaluatorForm=(ExternalEvaluatorForm)form;
		 ActionErrors errors = externalEvaluatorForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		
		//Get the stateMap by passing countyId by calling the commanAjax method and keep in request scope
		
			if(externalEvaluatorForm.getCountryId()!=null && !externalEvaluatorForm.getCountryId().isEmpty()){
				Map<Integer,String> stateMap = CommonAjaxHandler.getInstance().getStatesByCountryId(Integer.parseInt(externalEvaluatorForm.getCountryId()));
				request.setAttribute("stateMap", stateMap);
				request.setAttribute("externalOperation", "add");
			}
		try {
			if(isCancelled(request))
			{
				externalEvaluatorForm.clearAll();
				return mapping.findForward(CMSConstants.RESET_ALL);
			}			
			if(errors.isEmpty())
			{
			setUserId(request, externalEvaluatorForm);
			ExamValuators examValuators=ExternalEvaluatorHandler.getInstance().checkForDuplicateonName(externalEvaluatorForm.getName());
			if(examValuators!=null ){
				if(examValuators.getIsActive()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.EXAM_EXTERNALEVALUATOR_EXISTS));
					assignListToForm(externalEvaluatorForm);
					setCountriesMapToRequest(request);
					}

					else if(!examValuators.getIsActive()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.EXAM_EXTERNALEVALUATOR_REACTIVATE));
					assignListToForm(externalEvaluatorForm);
					setCountriesMapToRequest(request);
					}
			}
			else{
			boolean isExternalEvaluatorAdded;

			isExternalEvaluatorAdded = ExternalEvaluatorHandler.getInstance().addExternalEvaluator(externalEvaluatorForm);
			//If add operation is success then display the success message.
			if(isExternalEvaluatorAdded)
			{
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.EXAM_EXTERNALEVALUATOR_ADD_SUCCESS));
				saveMessages(request, messages);
				externalEvaluatorForm.clearAll();
				assignListToForm(externalEvaluatorForm);
				setCountriesMapToRequest(request);
				request.removeAttribute("stateMap");
				return mapping.findForward(CMSConstants.INIT_EXTERNALEVALUATOR);
			}
			//If add operation is failure then add the error message.
			else{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.EXAM_EXTERNALEVALUATOR_ADD_FAILED));
				saveErrors(request, errors);
				assignListToForm(externalEvaluatorForm);
				setCountriesMapToRequest(request);
				}
			}
			}
		}catch (Exception e) {
				log.error("Error in adding ExternalEvaluator in ExternalEvaluator Action",e);
				String msg = super.handleApplicationException(e);
				externalEvaluatorForm.setErrorMessage(msg);
				externalEvaluatorForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from -- ExternalEvaluatorAction --- addExternalEvaluator");
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.INIT_EXTERNALEVALUATOR);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteExternalEvaluator(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into--- ExternalEvaluatorAction --- deleteExternalEvaluator");
		ExternalEvaluatorForm externalEvaluatorForm=(ExternalEvaluatorForm)form;
		 ActionErrors errors = externalEvaluatorForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		
		try {
			setUserId(request, externalEvaluatorForm);
			
			boolean isExternalEvaluatorFormDeleted;
			/**
			 * Request for deleting a deleteExternalEvaluator based on the Id
			 */
			isExternalEvaluatorFormDeleted = ExternalEvaluatorHandler.getInstance().deleteExternalEvaluator(externalEvaluatorForm.getId(), externalEvaluatorForm.getUserId());
			//If delete operation is success then append the success message.
			if (isExternalEvaluatorFormDeleted) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.EXAM_EXTERNALEVALUATOR_DELETE_SUCCESS));
				saveMessages(request, messages);
				assignListToForm(externalEvaluatorForm);
				externalEvaluatorForm.clearAll();
				setCountriesMapToRequest(request);
			}
			//If delete operation is failure then add the error message.
			else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.EXAM_EXTERNALEVALUATOR_DELETE_FAILED));
				saveErrors(request, errors);
				assignListToForm(externalEvaluatorForm);
				setCountriesMapToRequest(request);
			}
		} catch (Exception e) {
			log.error("Error in deleting ExternalEvaluator in ExternalEvaluator Action",e);
				String msg = super.handleApplicationException(e);
				externalEvaluatorForm.setErrorMessage(msg);
				externalEvaluatorForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from-- ExternalEvaluatorAction --- deleteExternalEvaluator");
		return mapping.findForward(CMSConstants.INIT_EXTERNALEVALUATOR);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editExternalEvaluator(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into---- ExternalEvaluatorAction --- editExternalEvaluator");
		ExternalEvaluatorForm externalEvaluatorForm=(ExternalEvaluatorForm)form;
		
		try {
			//Used to clear the form.
			externalEvaluatorForm.clearAll();
			/**
			 * Get the particular row based on the id while clicking edit button
			 */
			ExternalEvaluatorTO externalEvaluatorTO=ExternalEvaluatorHandler.getInstance().getDetailsonId(externalEvaluatorForm.getId());
				
				Map<Integer,String> stateMap= new LinkedHashMap<Integer,String>();
				//Set the TO properties to formbean
				if(externalEvaluatorTO!=null){
					if (externalEvaluatorTO.getName() != null && !externalEvaluatorTO.getName().isEmpty()) {
					externalEvaluatorForm.setName(externalEvaluatorTO.getName());
					}
					if (externalEvaluatorTO.getAddressLine1() != null && !externalEvaluatorTO.getAddressLine1().isEmpty()) {
					externalEvaluatorForm.setAddressLine1(externalEvaluatorTO.getAddressLine1());
					}
					if(externalEvaluatorTO.getAddressLine2()!=null && !externalEvaluatorTO.getAddressLine2().isEmpty()){
						externalEvaluatorForm.setAddressLine2(externalEvaluatorTO.getAddressLine2());
					}
					if (externalEvaluatorTO.getCity() != null && !externalEvaluatorTO.getCity().isEmpty()) {
					externalEvaluatorForm.setCity(externalEvaluatorTO.getCity());
					}
					if (externalEvaluatorTO.getPin() != null && !externalEvaluatorTO.getPin().isEmpty()) {
					externalEvaluatorForm.setPin(externalEvaluatorTO.getPin());
					}
					if (externalEvaluatorTO.getStateTO()!=null){
					if(externalEvaluatorTO.getStateTO().getId()>0){
						externalEvaluatorForm.setStateId(String.valueOf(externalEvaluatorTO.getStateTO().getId()));
					}
					}
					if (externalEvaluatorTO.getCountryTO()!=null){
					if(externalEvaluatorTO.getCountryTO().getId()>0){
						externalEvaluatorForm.setCountryId(String.valueOf(externalEvaluatorTO.getCountryTO().getId()));
					}
					}
					if (externalEvaluatorTO.getEmail() != null && !externalEvaluatorTO.getEmail().isEmpty()) {
					externalEvaluatorForm.setEmail(externalEvaluatorTO.getEmail());
					}
					if (externalEvaluatorTO.getMobile() != null && !externalEvaluatorTO.getMobile().isEmpty()) {
					externalEvaluatorForm.setMobile(externalEvaluatorTO.getMobile());
					}
					if (externalEvaluatorTO.getDepartment() != null && !externalEvaluatorTO.getDepartment().isEmpty()) {
					externalEvaluatorForm.setDepartment(externalEvaluatorTO.getDepartment());
					}
					if (externalEvaluatorTO.getPan() != null && !externalEvaluatorTO.getPan().isEmpty()) {
					externalEvaluatorForm.setPan(externalEvaluatorTO.getPan());
					}
					if (externalEvaluatorTO.getBankAccNo() != null && !externalEvaluatorTO.getBankAccNo().isEmpty()) {
						externalEvaluatorForm.setBankAccNo(externalEvaluatorTO.getBankAccNo());
					}
					if (externalEvaluatorTO.getBankName() != null && !externalEvaluatorTO.getBankName().isEmpty()) {
						externalEvaluatorForm.setBankName(externalEvaluatorTO.getBankName());
					}
					if (externalEvaluatorTO.getBankBranch() != null && !externalEvaluatorTO.getBankBranch().isEmpty()) {
						externalEvaluatorForm.setBankBranch(externalEvaluatorTO.getBankBranch());
					}
					if (externalEvaluatorTO.getBankIfscCode() != null && !externalEvaluatorTO.getBankIfscCode().isEmpty()) {
						externalEvaluatorForm.setBankIfscCode(externalEvaluatorTO.getBankIfscCode());
					}

			}
			
				if(externalEvaluatorForm.getCountryId()!=null && !externalEvaluatorForm.getCountryId().isEmpty()){
					stateMap = CommonAjaxHandler.getInstance().getStatesByCountryId(Integer.parseInt(externalEvaluatorForm.getCountryId()));
				}
			request.setAttribute("stateMap", stateMap);
			request.setAttribute("externalOperation", CMSConstants.EDIT_OPERATION);
			assignListToForm(externalEvaluatorForm);
			setCountriesMapToRequest(request);
		} catch (Exception e) {
			log.error("Error in editing ExternalEvaluator in Action",e);
				String msg = super.handleApplicationException(e);
				externalEvaluatorForm.setErrorMessage(msg);
				externalEvaluatorForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from-- ExternalEvaluatorAction --- editExternalEvaluator");
		return mapping.findForward(CMSConstants.INIT_EXTERNALEVALUATOR);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateExternalEvaluator(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into-- ExternalEvaluatorAction --- updateExternalEvaluator");
		ExternalEvaluatorForm externalEvaluatorForm=(ExternalEvaluatorForm)form; 
		ActionErrors errors = externalEvaluatorForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			if(errors.isEmpty())
			{
				setUserId(request, externalEvaluatorForm);
				
				boolean isUpdated;
						/**
						 * Pass the properties which have to update and call the handler method
						 */
						isUpdated=ExternalEvaluatorHandler.getInstance().updateExternalEvaluator(externalEvaluatorForm);
						
						//If update is successful then add the success message else show the error message
						
						if(isUpdated){
							messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.EXAM_EXTERNALEVALUATOR_UPDATE_SUCCESS));
							saveMessages(request, messages);
							externalEvaluatorForm.clearAll();
							assignListToForm(externalEvaluatorForm);
							setCountriesMapToRequest(request);
							return mapping.findForward(CMSConstants.INIT_EXTERNALEVALUATOR);				
						}
						else {
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.EXAM_EXTERNALEVALUATOR_UPDATE_FAILED));
							saveErrors(request, errors);
							assignListToForm(externalEvaluatorForm);
							setCountriesMapToRequest(request);
							return mapping.findForward(CMSConstants.INIT_EXTERNALEVALUATOR);
						}

			}
		else{
			setCountriesMapToRequest(request);
			if(externalEvaluatorForm.getCountryId()!=null && !externalEvaluatorForm.getCountryId().isEmpty()){
			Map<Integer,String> stateMap = CommonAjaxHandler.getInstance().getStatesByCountryId(Integer.parseInt(externalEvaluatorForm.getCountryId()));
			request.setAttribute("stateMap", stateMap);			
			}
			request.setAttribute("externalOperation",CMSConstants.EDIT_OPERATION);
		}
		}catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.exam.externalevaluator.exists"));
			saveErrors(request, errors);
			assignListToForm(externalEvaluatorForm);
			return mapping.findForward(CMSConstants.INIT_EXTERNALEVALUATOR);
		}
		catch (Exception e) {
			log.error("Error in updating ExternalEvaluator in ExternalEvaluator Action",e);
				String msg = super.handleApplicationException(e);
				externalEvaluatorForm.setErrorMessage(msg);
				externalEvaluatorForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from --- ExternalEvaluatorAction --- updateExternalEvaluator");
		saveErrors(request, errors);
		assignListToForm(externalEvaluatorForm);
		return mapping.findForward(CMSConstants.INIT_EXTERNALEVALUATOR);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reActivateExternalEvaluator(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into-- ExternalEvaluatorAction --- reActivateExternalEvaluator");
		ExternalEvaluatorForm externalEvaluatorForm=(ExternalEvaluatorForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();		
		try {
			setUserId(request, externalEvaluatorForm);
			boolean isReacivate;
			//Request for reactivate by calling the handler method and pass the name
			isReacivate = ExternalEvaluatorHandler.getInstance().reActivateExternalEvaluator(externalEvaluatorForm.getName(),externalEvaluatorForm.getUserId());
			//If reactivation successful show the message else add the error message
			if (isReacivate) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.EXAM_EXTERNALEVALUATOR_REACTIVATE_SUCCESS));
				saveMessages(request, messages);
				externalEvaluatorForm.clearAll();
				setCountriesMapToRequest(request);
				assignListToForm(externalEvaluatorForm);
				return mapping.findForward(CMSConstants.INIT_EXTERNALEVALUATOR);
			} else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.EXAM_EXTERNALEVALUATOR_REACTIVATE_FAILED));
				saveErrors(request, errors);
				setCountriesMapToRequest(request);
				assignListToForm(externalEvaluatorForm);
			}
		} catch (Exception e) {
			log.error("Error in reactivating ExternalEvaluatorAction",e);
				String msg = super.handleApplicationException(e);
				externalEvaluatorForm.setErrorMessage(msg);
				externalEvaluatorForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from-- ExternalEvaluatorAction --- reActivateExternalEvaluator");
		return mapping.findForward(CMSConstants.INIT_EXTERNALEVALUATOR);
	}
	
	public void setCountriesMapToRequest(HttpServletRequest request) {
		Map<Integer, String> countriesMap = CountryHandler.getInstance().getCountriesMap();
		request.setAttribute("countriesMap",countriesMap);
		log.debug("No of countries"+countriesMap.size());
	}
	
	/**
	 * @param form
	 * @throws Exception
	 */
	public void assignListToForm(ActionForm form) throws Exception
	{
		log.info("Entering into -- ExternalEvaluatorAction --- assignListToForm");
		ExternalEvaluatorForm externalEvaluatorForm	= (ExternalEvaluatorForm) form;
		try {
			List<ExternalEvaluatorTO> externalEvaluatorList = ExternalEvaluatorHandler.getInstance().getExternalEvaluatorDetails();
			externalEvaluatorForm.setExternalList(externalEvaluatorList);
		} catch (Exception e) {
			log.error("Error in assignListToForm of ExternalEvaluator Action",e);
				String msg = super.handleApplicationException(e);
				externalEvaluatorForm.setErrorMessage(msg);
				externalEvaluatorForm.setErrorStack(e.getMessage());
			}
		log.info("Leaving from -- ExternalEvaluatorAction --- assignListToForm");
	}
}
