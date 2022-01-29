package com.kp.cms.actions.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.MobNewsEventsCategory;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.admin.MobNewsEventsCategoryForm;
import com.kp.cms.handlers.admin.CurrencyMasterHandler;
import com.kp.cms.handlers.admin.MobNewsEventsCategoryHandler;
import com.kp.cms.to.admin.CurrencyMasterTO;
import com.kp.cms.to.admin.MobNewsEventsCategoryTO;

public class MobNewsEventsCategoryAction extends BaseDispatchAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(MobNewsEventsCategoryAction.class);
	/**
	 * This method performs the retrieving of Category  in MobNewsEventsCategoryAction class.
	 * @param mapping  - The ActionMapping used to select this instance
	 * @param form - The optional ActionForm bean for this request (if any)
	 * @param request - The HTTP request we are processing
	 * @param response - The HTTP response we are creating
	 * @return - The forward to which control should be transferred.
	 * @throws - Exception if an exception occurs
	 */
	public ActionForward initMobNewsEventsCategoryEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		log.info("call of initMobNewsEventsCategoryEntry in MobNewsEventsCategoryAction class. ");
		MobNewsEventsCategoryForm mobNewsEventsCategoryFrom=(MobNewsEventsCategoryForm) form;
		HttpSession session=request.getSession();
		session.setAttribute("field","MobNewsEventsCategory");
		try 
		{
			mobNewsEventsCategoryFrom.reset(mapping, request);
			List<MobNewsEventsCategoryTO> mobNewsEventsCategoryList = MobNewsEventsCategoryHandler.getInstance().getMobNewsEventsCategory();
			mobNewsEventsCategoryFrom.setMobNewsEventsCategoryList(mobNewsEventsCategoryList);
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			mobNewsEventsCategoryFrom.setErrorMessage(msgKey);
			mobNewsEventsCategoryFrom.setErrorStack(businessException.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			mobNewsEventsCategoryFrom.setErrorMessage(msg);
			mobNewsEventsCategoryFrom.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}	
		log.info("end of initMobNewsEventsCategoryEntry in MobNewsEventsCategoryAction class. ");
		return mapping.findForward(CMSConstants.MobNews_Events_Category_Entry);
	}
	/**
	 * This method performs the adding of mobNewsEventsCategory  in MobNewsEventsCategoryAction class.
	 * @param mapping  - The ActionMapping used to select this instance
	 * @param form - The optional ActionForm bean for this request (if any)
	 * @param request - The HTTP request we are processing
	 * @param response - The HTTP response we are creating
	 * @return - The forward to which control should be transferred.
	 * @throws - Exception if an exception occurs
	 */
	public ActionForward addMobNewsEventsCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception,BusinessException {
		log.info("call of MobNewsEventsCategory in MobNewsEventsCategoryAction class. ");
		MobNewsEventsCategoryForm mobNewsEventsCategoryFrom=(MobNewsEventsCategoryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = mobNewsEventsCategoryFrom.validate(mapping, request);
		boolean  isMobNewsEventsCategoryAdded= false;
		String category="";
		try
		{
			if (errors.isEmpty()) {
				setUserId(request, mobNewsEventsCategoryFrom);
				if(mobNewsEventsCategoryFrom.getCategory()!=null && !mobNewsEventsCategoryFrom.getCategory().isEmpty()){
					category = mobNewsEventsCategoryFrom.getCategory().trim();
				}else{
					return mapping.findForward(CMSConstants.LOGIN_PAGE);
				}
				MobNewsEventsCategory mobNewsEventsCategory =MobNewsEventsCategoryHandler.getInstance().isCategoryExist(category);
				if(mobNewsEventsCategory!=null && (mobNewsEventsCategory.getIsActive()==true)){
					errors.add(CMSConstants.MobNews_Category_EXIST, new ActionError(CMSConstants.MobNews_Category_EXIST));
					saveErrors(request, errors);
				}
				else if(mobNewsEventsCategory!=null && !mobNewsEventsCategory.getIsActive()){
					mobNewsEventsCategoryFrom.setDupId(mobNewsEventsCategory.getId());
					errors.add(CMSConstants.MobNews_Category_REACTIVATE, new ActionError(CMSConstants.MobNews_Category_REACTIVATE));
					saveErrors(request, errors);		
					 return mapping.findForward(CMSConstants.MobNews_Events_Category_Entry);
				}else{
					isMobNewsEventsCategoryAdded = MobNewsEventsCategoryHandler.getInstance().addMobNewsEventsCategory(mobNewsEventsCategoryFrom);
				}
				if (isMobNewsEventsCategoryAdded) {
					ActionMessage message = new ActionMessage(CMSConstants.MobNews_Category_ADD_SUCCESS);// Adding success message.
					messages.add("messages", message);
					saveMessages(request, messages);
					mobNewsEventsCategoryFrom.reset(mapping, request);	
				}else{
					errors.add(CMSConstants.MobNews_Category_ADD_FAILURE, new ActionError(CMSConstants.MobNews_Category_ADD_FAILURE));// Adding failure message
				}
				}
			else {
				saveErrors(request, errors);
				}
			/*}catch (BusinessException businessException) {
				log.info("Exception addMobNewsEventsCategoryEntry");
				String msgKey = super.handleBusinessException(businessException);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add(CMSConstants.MESSAGES, message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);*/
			} catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				mobNewsEventsCategoryFrom.setErrorMessage(msg);
				mobNewsEventsCategoryFrom.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			List<MobNewsEventsCategoryTO> mobNewsEventsCategoryList = MobNewsEventsCategoryHandler.getInstance().getMobNewsEventsCategory();
			mobNewsEventsCategoryFrom.setMobNewsEventsCategoryList(mobNewsEventsCategoryList);
			log.info("end of addMobNewaEventsCategoryEntry in MobNewsEventsCategoryAction class. ");
		
		 return mapping.findForward(CMSConstants.MobNews_Events_Category_Entry);
	}
	/**
	 * This method performs the editing one record of Category  in MobNewsEventsCategoryAction class.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred,
     * @throws Exception if an exception occurs
     */
	public ActionForward editMobNewsEventsCategory(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		
		log.info("call of editMobNewsEventsCategory method in MobNewsEventsCategoryAction class.");
		MobNewsEventsCategoryForm mobNewsEventsCategoryForm=(MobNewsEventsCategoryForm)form;
	    ActionMessages messages = new ActionMessages();
	    ActionErrors errors = mobNewsEventsCategoryForm.validate(mapping, request);
	    try
	    {
	    	if(errors.isEmpty())
	    	{
	    		MobNewsEventsCategoryTO mobNewsEventsCategoryTO=MobNewsEventsCategoryHandler.getInstance()
	    		.editMobNewsEventsCategory(mobNewsEventsCategoryForm.getMobNewsEventsCategoryId());
	    		mobNewsEventsCategoryForm.setCategory(mobNewsEventsCategoryTO.getCategory().trim());
	    		mobNewsEventsCategoryForm.setMobNewsEventsCategoryId(mobNewsEventsCategoryTO.getId());
	    		request.setAttribute("mobNewsEventCategoryOperation", "edit");
	    		HttpSession session=request.getSession(false);
	    		if(session == null){
	    			return mapping.findForward(CMSConstants.LOGIN_PAGE);
	    		}else{
	    			session.setAttribute("Category",mobNewsEventsCategoryTO.getCategory());
	    			
	    		}
	    	
	    	
	    }else{
    		//errors are present
			errors.add(messages);
			saveErrors(request, errors);
		}
    /*}catch (BusinessException businessException) {
		log.info("Exception editCurrencyMaster");
		String msgKey = super.handleBusinessException(businessException);
		ActionMessage message = new ActionMessage(msgKey);
		messages.add(CMSConstants.MESSAGES, message);
		return mapping.findForward(CMSConstants.ERROR_PAGE);*/
	} catch (Exception exception) {	
		String msg = super.handleApplicationException(exception);
		mobNewsEventsCategoryForm.setErrorMessage(msg);
		mobNewsEventsCategoryForm.setErrorStack(exception.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
    //getting all Category  details.
		
		
		
		
		log.info("end of editMobNewsEventsCategory method in MobNewsEventsCategoryAction class.");
		return mapping.findForward(CMSConstants.MobNews_Events_Category_Entry);
		
	}
	/**
     * This method performs the updating of one record of Category  details in MobNewsEventsCategoryAction.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred,
     * @throws Exception if an exception occurs
     */
	public ActionForward updateMobNewsEventsCategory(ActionMapping mapping,ActionForm form,HttpServletRequest request
			,HttpServletResponse response)throws Exception{
		log.info("call of updateMobNewsEventsCategory method in MobNewsEventsCategoryAction class.");
		MobNewsEventsCategoryForm mobNewsEventsCategoryForm=(MobNewsEventsCategoryForm)form;
	    ActionMessages messages = new ActionMessages();
	    ActionErrors errors=mobNewsEventsCategoryForm.validate(mapping, request);
	    try
	    {
	    	if(isCancelled(request)){
    			MobNewsEventsCategoryTO mobNewsEventsCategoryTO =MobNewsEventsCategoryHandler.getInstance().
    			editMobNewsEventsCategory(mobNewsEventsCategoryForm.getMobNewsEventsCategoryId());
	    		mobNewsEventsCategoryForm.setCategory(mobNewsEventsCategoryTO.getCategory());
	    		
	    		mobNewsEventsCategoryForm.setMobNewsEventsCategoryId(mobNewsEventsCategoryTO.getId());
	    		request.setAttribute("mobNewsEventCategoryOperation","edit");
	    		 List<MobNewsEventsCategoryTO> mobNewsEventsCategoryList = MobNewsEventsCategoryHandler.getInstance().getMobNewsEventsCategory();
    		    mobNewsEventsCategoryForm.setMobNewsEventsCategoryList(mobNewsEventsCategoryList);
    		    return mapping.findForward(CMSConstants.MobNews_Events_Category_Entry);
    		}
    		    if(errors.isEmpty()){
		    		setUserId(request, mobNewsEventsCategoryForm);
		    		HttpSession session=request.getSession(false);
		    		String category = mobNewsEventsCategoryForm.getCategory().trim();
		    		if(!category.equalsIgnoreCase(category) ){
		    			MobNewsEventsCategory mobNewsEventsCategory = MobNewsEventsCategoryHandler.getInstance().isCategoryExist(category);
		    		if(mobNewsEventsCategory!=null && mobNewsEventsCategory.getIsActive() && mobNewsEventsCategory.getCategory().equalsIgnoreCase(category)){
						errors.add(CMSConstants.MobNews_Category_EXIST, new ActionError(CMSConstants.MobNews_Category_EXIST));
						request.setAttribute("mobNewsEventCategoryOperation","edit");
						saveErrors(request, errors);
		    		
					}else if(mobNewsEventsCategory!=null && !mobNewsEventsCategory.getIsActive()){
						errors.add(CMSConstants.MobNews_Category_REACTIVATE, new ActionError(CMSConstants.MobNews_Category_REACTIVATE));
						saveErrors(request, errors);			
						request.setAttribute("mobNewsEventCategoryOperation","edit");
					}else if(mobNewsEventsCategory!=null && mobNewsEventsCategory.getIsActive() && mobNewsEventsCategory!=null && mobNewsEventsCategory
							.getCategory().equalsIgnoreCase(category)){
						request.setAttribute("mobNewsEventCategoryOperation","edit");
						saveErrors(request, errors);
	    		}else{
					boolean isUpdated =MobNewsEventsCategoryHandler.getInstance().updateMobNewsEventsCategory(mobNewsEventsCategoryForm);
		    		if(isUpdated){
		    			//if update is success.
		    			session.removeAttribute("Category");
		    			
		    			ActionMessage message = new ActionMessage(CMSConstants.MobNews_Category_UPDATE_SUCCESS);
						messages.add("messages", message);
						saveMessages(request, messages);
						mobNewsEventsCategoryForm.reset(mapping, request);
		    		}if(!isUpdated){
		    			//if update is failure.
		    			ActionMessage message = new ActionMessage(CMSConstants.MobNews_Category_UPDATE_FAILURE);
						messages.add("messages", message);
						saveMessages(request, messages);
						mobNewsEventsCategoryForm.reset(mapping, request);
		    		}
		    	}
	    		}else{
		    		boolean isUpdated =MobNewsEventsCategoryHandler.getInstance().updateMobNewsEventsCategory(mobNewsEventsCategoryForm);
		    		if(isUpdated){
		    			//if update is success.
		    			session.removeAttribute("Category");
		    			ActionMessage message = new ActionMessage(CMSConstants.MobNews_Category_UPDATE_SUCCESS);
						messages.add("messages", message);
						saveMessages(request, messages);
						mobNewsEventsCategoryForm.reset(mapping, request);
		    		}if(!isUpdated){
		    			//if update is failure.
		    			ActionMessage message = new ActionMessage(CMSConstants.MobNews_Category_UPDATE_FAILURE);
						messages.add("messages", message);
						saveMessages(request, messages);
						mobNewsEventsCategoryForm.reset(mapping, request);
		    		}
		    	}
	    	}else{
	    		//errors are present
				errors.add(messages);
				request.setAttribute("mobNewsEventCategoryOperation","edit");
				saveErrors(request, errors);
			}
	    	
	    }catch (BusinessException businessException) {
			log.info("Exception updateMobNewsEventsCategory");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			mobNewsEventsCategoryForm.setErrorMessage(msg);
			mobNewsEventsCategoryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	    //getting all currency details.
	    List<MobNewsEventsCategoryTO> mobNewsEventsCategoryList = MobNewsEventsCategoryHandler.getInstance().getMobNewsEventsCategory();
	    mobNewsEventsCategoryForm.setMobNewsEventsCategoryList(mobNewsEventsCategoryList);	
		log.info("end of updateMobNewsCategory method in MobNewsEventsCategoryAction class.");
   
		return mapping.findForward(CMSConstants.MobNews_Events_Category_Entry);
	}
	/**
     * This method performs the deleting one record of category  in MobNewsEventsCategoryAction class.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred,
     * @throws Exception if an exception occurs
     */
	public ActionForward deleteMobNewsEventsCategory(ActionMapping mapping,ActionForm form
			,HttpServletRequest request,HttpServletResponse response)throws Exception{
				
		MobNewsEventsCategoryForm mobNewsEventsCategoryForm=(MobNewsEventsCategoryForm)form;
	    ActionMessages messages = new ActionMessages();
	    ActionErrors errors=mobNewsEventsCategoryForm.validate(mapping, request);
	    try
	    {
	    	if(errors.isEmpty())
	    	{
	    		setUserId(request, mobNewsEventsCategoryForm);
	    		boolean isDelete=MobNewsEventsCategoryHandler.getInstance().deleteMobNewsEventsCategory(mobNewsEventsCategoryForm.getUserId(),mobNewsEventsCategoryForm.getMobNewsEventsCategoryId());
	    		if(isDelete){
					//if delete is success.
					ActionMessage message = new ActionMessage(CMSConstants.MobNews_Category_DELETE_SUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
					mobNewsEventsCategoryForm.reset(mapping, request);
				}if(!isDelete){
					//if delete is failure.
					ActionMessage message = new ActionMessage(CMSConstants.MobNews_Category_DELETE_FAILURE);
					messages.add("messages", message);
					saveMessages(request, messages);
					mobNewsEventsCategoryForm.reset(mapping, request);
				}
	    	}else{
	    		//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
	    }catch (BusinessException businessException) {
			log.info("Exception MobNewsEventsCategory");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			mobNewsEventsCategoryForm.setErrorMessage(msg);
			mobNewsEventsCategoryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	    //getting all currency details.
	    List<MobNewsEventsCategoryTO> mobNewsEventsCategoryList = MobNewsEventsCategoryHandler.getInstance().getMobNewsEventsCategory();
	    mobNewsEventsCategoryForm.setMobNewsEventsCategoryList(mobNewsEventsCategoryList);	
	 
		log.info("end of deteteMobNewsCategory method in MobNewsEventsCategoryAction class.");
		return mapping.findForward(CMSConstants.MobNews_Events_Category_Entry);
		
	}
	/**
	 * This method performs reactivation of category  in MobNewsEventsCategoryAction class.
	 * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred,
     * @throws Exception if an exception occurs
	 */
	public ActionForward reActivateMobNewsEventsCategory(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("call of reActivateMobNewsEventsCategory method in MobNewsEventsCategoryAction class.");
		MobNewsEventsCategoryForm mobNewsEventsCategoryForm=(MobNewsEventsCategoryForm)form;
	    ActionMessages messages = new ActionMessages();
	    ActionErrors errors=mobNewsEventsCategoryForm.validate(mapping, request);
	    try
	    {
	    	if(errors.isEmpty())
	    	{
	    		setUserId(request, mobNewsEventsCategoryForm);
	    		boolean isActivated = MobNewsEventsCategoryHandler.getInstance().reActivateMobNewsEventsCategory(mobNewsEventsCategoryForm.getDupId(),mobNewsEventsCategoryForm.getUserId());
	    		if(isActivated){
	    			//if reactivation is success.
		    		ActionMessage message = new ActionMessage(CMSConstants.MobNews_Category_REACTIVATE_SUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
					mobNewsEventsCategoryForm.reset(mapping, request);
	    		}else{
	    			//if reactivation is failure.
	    			ActionMessage message = new ActionMessage(CMSConstants.MobNews_Category_REACTIVATE_FAILURE);
					messages.add("messages", message);
					saveMessages(request, messages);
					mobNewsEventsCategoryForm.reset(mapping, request);
	    		}
	    	}else{
	    		//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
	    }catch (BusinessException businessException) {
			log.info("Exception reActivateMobNewsEventsCategory");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			mobNewsEventsCategoryForm.setErrorMessage(msg);
			mobNewsEventsCategoryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	    //getting all currency details.
	    List<MobNewsEventsCategoryTO> mobNewsEventsCategoryList = MobNewsEventsCategoryHandler.getInstance().getMobNewsEventsCategory();
	    mobNewsEventsCategoryForm.setMobNewsEventsCategoryList(mobNewsEventsCategoryList);
	   
		
		log.info("end of reActivateMobNewsEventsCategory method in MobNewsEventsCategoryAction class.");
		return mapping.findForward(CMSConstants.MobNews_Events_Category_Entry);
	}
	
	}

