package com.kp.cms.actions.admin;

import java.util.LinkedHashMap;
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
import com.kp.cms.bo.admin.Recommendor;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.RecommendedByForm;
import com.kp.cms.handlers.admin.CountryHandler;
import com.kp.cms.handlers.admin.RecommendedByHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.RecommendedByTO;



@SuppressWarnings("deprecation")
public class RecommendedByAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(RecommendedByAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request Initializes recommendedBy 
	 * @param response Shows all the records of recommendedBy from DB where isActive=1
	 * @return
	 * @throws Exception
	 */
	public ActionForward initRecommendedBy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RecommendedByForm recommendedByForm=(RecommendedByForm)form;		
		try {
			setCountriesMapToRequest(request);
			assignListToForm(recommendedByForm);
			recommendedByForm.clearAll();			
		} catch (Exception e) {
			log.error("Error in initializing RecommendedBy",e);
				log.error("Error occured in initRecommendedBy of RecommendedByAction");
				String msg = super.handleApplicationException(e);
				recommendedByForm.setErrorMessage(msg);
				recommendedByForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		return mapping.findForward(CMSConstants.INIT_RECOMMENDEDBY);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request for adding a recommendedBy for a particular code
	 * @param 
	 * @return
	 * @throws Exception
	 */

	public ActionForward addRecommendedBy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setCountriesMapToRequest(request);
		RecommendedByForm recommendedByForm=(RecommendedByForm)form;
		ActionErrors errors = recommendedByForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		
		//Get the stateMap by passing countyId by calling the commanAjax method and keep in request scope
		
			if(recommendedByForm.getCountryId()!=null && !recommendedByForm.getCountryId().isEmpty()){
				Map<Integer,String> stateMap = CommonAjaxHandler.getInstance().getStatesByCountryId(Integer.parseInt(recommendedByForm.getCountryId()));
				request.setAttribute("stateMap", stateMap);
				request.setAttribute("recommendedOperation", "add");
			}
		try {			
			if(isCancelled(request))
			{
				recommendedByForm.clearAll();
				return mapping.findForward(CMSConstants.RESET_ALL);
			}			
			if(errors.isEmpty())
			{
			setUserId(request, recommendedByForm);			
			Recommendor recommendor=RecommendedByHandler.getInstance().checkForDuplicateonCode(recommendedByForm.getCode());
			/**
			 * Check for the duplicate entry based on the code. If yes then add the error message.
			 * If is in inactive mode then reactivate same.
			 */
			if(recommendor!=null )
			{
				if(recommendor.getIsActive()){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_RECOMMENDEDBY_EXISTS));
				assignListToForm(recommendedByForm);
				setCountriesMapToRequest(request);
				}

				else if(!recommendor.getIsActive()){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_RECOMMENDEDBY_REACTIVATE));
				assignListToForm(recommendedByForm);
				setCountriesMapToRequest(request);
				}
			}
			else{
			boolean isRecommendedByAdded;
			/**
			 * Request for adding the the recommendedBy
			 */
			isRecommendedByAdded = RecommendedByHandler.getInstance().addRecommendedBy(recommendedByForm);
			//If add operation is success then display the success message.
			if(isRecommendedByAdded)
			{
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ADMIN_RECOMMENDEDBY_ADD_SUCCESS));
				saveMessages(request, messages);
				recommendedByForm.clearAll();
				assignListToForm(recommendedByForm);
				setCountriesMapToRequest(request);
				request.removeAttribute("stateMap");
				return mapping.findForward(CMSConstants.INIT_RECOMMENDEDBY);
			}
			//If add operation is failure then add the error message.
			else{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_RECOMMENDEDBY_ADD_FAILED));
				saveErrors(request, errors);
				assignListToForm(recommendedByForm);
				setCountriesMapToRequest(request);
			}
			}
		} 
		}catch (Exception e) {
				log.error("Error in adding RecommendedBy in RecommendedBy Action",e);
				String msg = super.handleApplicationException(e);
				recommendedByForm.setErrorMessage(msg);
				recommendedByForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.INIT_RECOMMENDEDBY);
	}
	
	/**
	 * Used for deleting a recommended By
	 * 
	 */
	
	public ActionForward deleteRecommendedBy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RecommendedByForm recommendedByForm=(RecommendedByForm)form;
		ActionErrors errors = recommendedByForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		
		try {
			setUserId(request, recommendedByForm);
			int recommendedById = recommendedByForm.getId();
			String userId=recommendedByForm.getUserId();
			boolean isRecommendedByDeleted;
			/**
			 * Request for deleting a guidelinesEntry based on the Id
			 */
			isRecommendedByDeleted = RecommendedByHandler.getInstance().deleteRecommendedBy(recommendedById, userId);
			//If delete operation is success then append the success message.
			if (isRecommendedByDeleted) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ADMIN_RECOMMENDEDBY_DELETE_SUCCESS));
				saveMessages(request, messages);
				assignListToForm(recommendedByForm);
				recommendedByForm.clearAll();
				setCountriesMapToRequest(request);
			}
			//If delete operation is failure then add the error message.
			else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_RECOMMENDEDBY_DELETE_FAILED));
				saveErrors(request, errors);
				assignListToForm(recommendedByForm);
				setCountriesMapToRequest(request);
			}
		} catch (Exception e) {
			log.error("Error in deleting RecommendedBy in RecommendedBy Action",e);
				String msg = super.handleApplicationException(e);
				recommendedByForm.setErrorMessage(msg);
				recommendedByForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		return mapping.findForward(CMSConstants.INIT_RECOMMENDEDBY);
	}
	
	/**
	 * Used for editing (Clicking on the edit button in UI it displays those properties in respective fields)
	 */
	

	public ActionForward editRecommendedBy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RecommendedByForm recommendedByForm=(RecommendedByForm)form;
		
		try {
			//Used to clear the form.
				recommendedByForm.clearAll();
			/**
			 * Get the particular row based on the id while clicking edit button
			 */
				RecommendedByTO recommendedByTO=RecommendedByHandler.getInstance().getDetailsonId(recommendedByForm.getId());
				
				Map<Integer,String> stateMap= new LinkedHashMap<Integer,String>();
				//Set the TO properties to formbean
					recommendedByForm.setCode(recommendedByTO.getCode());
					recommendedByForm.setOldCode(recommendedByTO.getCode());
					recommendedByForm.setName(recommendedByTO.getName());
					recommendedByForm.setAddressLine1(recommendedByTO.getAddressLine1());
					if(recommendedByTO.getAddressLine2()!=null && !recommendedByTO.getAddressLine2().isEmpty()){
					recommendedByForm.setAddressLine2(recommendedByTO.getAddressLine2());
					}
					recommendedByForm.setCity(recommendedByTO.getCity());
					recommendedByForm.setPhone(recommendedByTO.getPhone());
					if(recommendedByTO.getComments()!=null && !recommendedByTO.getComments().isEmpty()){
					recommendedByForm.setComments(recommendedByTO.getComments());
					}
					if(recommendedByTO.getStateTO().getId()!=0){
					recommendedByForm.setStateId(String.valueOf(recommendedByTO.getStateTO().getId()));
					}
					if(recommendedByTO.getCountryTO().getId()!=0){
						recommendedByForm.setCountryId(String.valueOf(recommendedByTO.getCountryTO().getId()));
					}
			
				if(recommendedByForm.getCountryId()!=null && !recommendedByForm.getCountryId().isEmpty()){
					stateMap = CommonAjaxHandler.getInstance().getStatesByCountryId(Integer.parseInt(recommendedByForm.getCountryId()));
				}
			request.setAttribute("stateMap", stateMap);
			request.setAttribute("recommendedOperation", CMSConstants.EDIT_OPERATION);
			assignListToForm(recommendedByForm);
			setCountriesMapToRequest(request);
		} catch (Exception e) {
			log.error("Error in editing RecommendedBy in Action",e);
				String msg = super.handleApplicationException(e);
				recommendedByForm.setErrorMessage(msg);
				recommendedByForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		return mapping.findForward(CMSConstants.INIT_RECOMMENDEDBY);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request Updates a recommendedBy (Taken the updated vales from UI)
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward updateRecommendedBy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RecommendedByForm recommendedByForm=(RecommendedByForm)form; 
		ActionErrors errors = recommendedByForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			if(errors.isEmpty())
			{
				setUserId(request, recommendedByForm);
				/**
				 * Check for the duplicate if is in inactive state
				 */
				
				if(!recommendedByForm.getOldCode().equalsIgnoreCase(recommendedByForm.getCode())){
					Recommendor recommendor=RecommendedByHandler.getInstance().checkForDuplicateonCode(recommendedByForm.getCode());
					if(recommendor!=null){
						if(recommendor.getIsActive()){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_RECOMMENDEDBY_EXISTS));
							saveErrors(request, errors);
							assignListToForm(recommendedByForm);
							setCountriesMapToRequest(request);
							return mapping.findForward(CMSConstants.INIT_RECOMMENDEDBY);	
						}
						if(!recommendor.getIsActive()){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_RECOMMENDEDBY_REACTIVATE));
							saveErrors(request, errors);
							assignListToForm(recommendedByForm);
							setCountriesMapToRequest(request);
							return mapping.findForward(CMSConstants.INIT_RECOMMENDEDBY);	
						}
					}
					else{
						boolean isUpdated;
						/**
						 * Pass the properties which have to update and call the handler method
						 */
						isUpdated=RecommendedByHandler.getInstance().updateRecommendedBy(recommendedByForm);
						
						//If update is successful then add the success message else show the error message
						
						if(isUpdated){
							messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ADMIN_RECOMMENDEDBY_UPDATE_SUCCESS));
							saveMessages(request, messages);
							recommendedByForm.clearAll();
							assignListToForm(recommendedByForm);
							setCountriesMapToRequest(request);
							return mapping.findForward(CMSConstants.INIT_RECOMMENDEDBY);				
						}
						else {
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_RECOMMENDEDBY_UPDATE_FAILED));
							saveErrors(request, errors);
							assignListToForm(recommendedByForm);
							setCountriesMapToRequest(request);
							return mapping.findForward(CMSConstants.INIT_RECOMMENDEDBY);
						}
					}
				}
		else{	
			boolean isUpdated;
			/**
			 * Pass the properties which have to update and call the handler method
			 */
			isUpdated=RecommendedByHandler.getInstance().updateRecommendedBy(recommendedByForm);
			
			//If update is successful then add the success message else show the error message
			
			if(isUpdated){
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ADMIN_RECOMMENDEDBY_UPDATE_SUCCESS));
				saveMessages(request, messages);
				recommendedByForm.clearAll();
				assignListToForm(recommendedByForm);
				setCountriesMapToRequest(request);
				return mapping.findForward(CMSConstants.INIT_RECOMMENDEDBY);				
			}
			else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_RECOMMENDEDBY_UPDATE_FAILED));
				saveErrors(request, errors);
				assignListToForm(recommendedByForm);
				setCountriesMapToRequest(request);
				return mapping.findForward(CMSConstants.INIT_RECOMMENDEDBY);
			}
		}
			}
		else{
			setCountriesMapToRequest(request);
			if(recommendedByForm.getCountryId()!=null && !recommendedByForm.getCountryId().isEmpty()){
			Map<Integer,String> stateMap = CommonAjaxHandler.getInstance().getStatesByCountryId(Integer.parseInt(recommendedByForm.getCountryId()));
			request.setAttribute("stateMap", stateMap);			
			}
			request.setAttribute("recommendedOperation",CMSConstants.EDIT_OPERATION);
		}
		}
		catch (Exception e) {
			log.error("Error in updating RecommendedBy in RecommendedBy Action",e);
				String msg = super.handleApplicationException(e);
				recommendedByForm.setErrorMessage(msg);
				recommendedByForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		saveErrors(request, errors);
		assignListToForm(recommendedByForm);
		return mapping.findForward(CMSConstants.INIT_RECOMMENDEDBY);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request Used for reactivating a recommendedBy for a particular code
	 * @param response That record is exist in DB but isActive=0. So make the isActive=1
	 * @return
	 * @throws Exception
	 */

	public ActionForward reActivateRecommendedBy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RecommendedByForm recommendedByForm=(RecommendedByForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();		
		try {
			setUserId(request, recommendedByForm);
			boolean isReacivate;
			//Request for reactivate by calling the handler method and pass the code
			isReacivate = RecommendedByHandler.getInstance()
					.reActivateRecommendedBy(recommendedByForm.getCode(),recommendedByForm.getUserId());
			//If reactivation successful show the message else add the error message
			if (isReacivate) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ADMIN_RECOMMENDEDBY_REACTIVATE_SUCCESS));
				saveMessages(request, messages);
				recommendedByForm.clearAll();
				setCountriesMapToRequest(request);
				assignListToForm(recommendedByForm);
				return mapping.findForward(CMSConstants.INIT_RECOMMENDEDBY);
			} else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_RECOMMENDEDBY_REACTIVATE_FAILED));
				saveErrors(request, errors);
				setCountriesMapToRequest(request);
				assignListToForm(recommendedByForm);
			}
		} catch (Exception e) {
			log.error("Error in reactivating RecommendedByAction",e);
				String msg = super.handleApplicationException(e);
				recommendedByForm.setErrorMessage(msg);
				recommendedByForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		return mapping.findForward(CMSConstants.INIT_RECOMMENDEDBY);
	}
	
	
	/**
	 * 
	 * @param request
	 * This method sets the countries map to Request
	 * useful in populating in country selection.
	 */
	
	public void setCountriesMapToRequest(HttpServletRequest request) {
		Map<Integer, String> countriesMap = CountryHandler.getInstance().getCountriesMap();
		request.setAttribute("countriesMap",countriesMap);
		log.debug("No of countries"+countriesMap.size());
	}

	/**
	 * 
	 * @param form
	 * Returns the all recommendedBy Details present 
	 * @throws Exception
	 */
	public void assignListToForm(ActionForm form) throws Exception
	{
		RecommendedByForm recommendedByForm	= (RecommendedByForm) form;
		try {
			List<RecommendedByTO> recommendedByList = RecommendedByHandler.getInstance().getRecommendedByDetails();
				recommendedByForm.setRecommendedList(recommendedByList);
		} catch (Exception e) {
			log.error("Error in assignListToForm of RecommendedBy Action",e);
				String msg = super.handleApplicationException(e);
				recommendedByForm.setErrorMessage(msg);
				recommendedByForm.setErrorStack(e.getMessage());
			}
	}
}
