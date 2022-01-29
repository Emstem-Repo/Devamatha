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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.DepartmentEntryForm;
import com.kp.cms.forms.admin.NewsEventsForm;
import com.kp.cms.handlers.admin.DepartmentEntryHandler;
import com.kp.cms.handlers.admin.NewsEventsHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.NewsEventsTO;

@SuppressWarnings("deprecation")
public class NewsEventsAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(NewsEventsAction.class);
	
	/**
	 * This method will load the all news and events details when link is clicked.
	 * @param mapping  - The ActionMapping used to select this instance
	 * @param form - The optional ActionForm bean for this request (if any)
	 * @param request - The HTTP request we are processing
	 * @param response - The HTTP response we are creating
	 * @return - The forward to which control should be transferred.
	 * @throws - Exception if an exception occurs
	 */
	
	public ActionForward initNewsEvents(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewsEventsForm newsEventsForm = (NewsEventsForm)form;
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			newsEventsForm.reset(mapping, request);
			//call of setNewsEvents method.
			setNewsEvents(newsEventsForm);
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			newsEventsForm.setErrorMessage(msgKey);
			newsEventsForm.setErrorStack(businessException.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			newsEventsForm.setErrorMessage(msg);
			newsEventsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}	
		log.info("end of initNewsEvents in NewsEventsAction class. ");
		return mapping.findForward(CMSConstants.NEWS_EVENTS_VIEW);
	}
	
	/**
	 * This method is used to add the news and events details to database.
	 * @param mapping  - The ActionMapping used to select this instance
	 * @param form - The optional ActionForm bean for this request (if any)
	 * @param request - The HTTP request we are processing
	 * @param response - The HTTP response we are creating
	 * @return - The forward to which control should be transferred.
	 * @throws - Exception if an exception occurs
	 */

	public ActionForward addNewsEvents(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewsEventsForm newsEventsForm = (NewsEventsForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = newsEventsForm.validate(mapping, request);
		//call of setUserId method and setting to form.
		setUserId(request, newsEventsForm);
		try {
			if(errors.isEmpty()){
				String descMessage = newsEventsForm.getDescription();
				boolean isExist = NewsEventsHandler.getInstance().checkDuplicateNewsEvents(descMessage, newsEventsForm);
				if(!isExist){
				//call of NewsEventsHandler class by sending newsEventsForm.
				boolean isAdded = NewsEventsHandler.getInstance().addNewsEvents(newsEventsForm);
				if(isAdded){
					//if adding is success.
					ActionMessage message = new ActionMessage("knowledgepro.admin.newsEventsAddedSuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					newsEventsForm.reset(mapping, request);	
				}
				if(!isAdded){
					//if adding is failure.
					ActionMessage message = new ActionMessage("knowledgepro.admin.newsEventsAddedFailure");
					messages.add("messages", message);
					saveMessages(request, messages);
					newsEventsForm.reset(mapping, request);	
				}
				}else{
					errors.add("knowledgepro.admin.newsEventsExist", new ActionError("knowledgepro.admin.newsEventsExist"));
					saveErrors(request, errors);
				}
			}else{
				errors.add(messages);
				saveErrors(request, errors);
			}
		}catch (ReActivateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.newsevents.addfailure.alreadyexist.reactivate"));
			saveErrors(request, errors);
			setNewsEvents(newsEventsForm);
			return mapping.findForward(CMSConstants.NEWS_EVENTS_VIEW);
		}catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			newsEventsForm.setErrorMessage(msg);
			newsEventsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//call of setNewsEvents method.
		setNewsEvents(newsEventsForm);
		log.info("end of addNewsEvents in NewsEventsAction class. ");
		return mapping.findForward(CMSConstants.NEWS_EVENTS_VIEW);
	}
	
	public ActionForward editNewsEvents(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewsEventsForm newsEventsForm=(NewsEventsForm)form;
		// ActionErrors errors = newsEventsForm.validate(mapping, request);
		
		try {
			newsEventsForm.reset(mapping, request);
			/**
			 * Get the particular row based on the id while clicking edit button
			 */
			NewsEventsTO newsEventsTO=NewsEventsHandler.getInstance().getDetailsOnId(newsEventsForm.getNewsEventsId());
				
				//Set the TO properties to formbean
					if (newsEventsTO.getName() != null && !newsEventsTO.getName().isEmpty()) {
					newsEventsForm.setDescription(newsEventsTO.getName());
					}
					if (newsEventsTO.getRequired() != null && !newsEventsTO.getRequired().isEmpty()) {
						newsEventsForm.setRequired(newsEventsTO.getRequired());
					}
			request.setAttribute("externalOperation", CMSConstants.EDIT_OPERATION);
			setNewsEvents(newsEventsForm);

		} catch (Exception e) {
			log.error("Error in editing NewsEvents in Action",e);
				String msg = super.handleApplicationException(e);
				newsEventsForm.setErrorMessage(msg);
				newsEventsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		return mapping.findForward(CMSConstants.NEWS_EVENTS_VIEW);
	}
	
	public ActionForward updateNewsEvents(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewsEventsForm newsEventsForm=(NewsEventsForm)form; 
		ActionErrors errors = newsEventsForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			if(errors.isEmpty())
			{
				setUserId(request, newsEventsForm);				
				boolean isUpdated;
						/**
						 * Pass the properties which have to update and call the handler method
						 */
						isUpdated=NewsEventsHandler.getInstance().updateNewsEvents(newsEventsForm);
						
						//If update is successful then add the success message else show the error message
						
						if(isUpdated){
							messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ADMIN_NEWSEVENTS_UPDATE_SUCCESS));
							saveMessages(request, messages);
							newsEventsForm.reset(mapping, request);
							setNewsEvents(newsEventsForm);
							return mapping.findForward(CMSConstants.NEWS_EVENTS_VIEW);				
						}
						else {
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_NEWSEVENTS_UPDATE_FAILED));
							saveErrors(request, errors);
							setNewsEvents(newsEventsForm);
							return mapping.findForward(CMSConstants.NEWS_EVENTS_VIEW);
						}

			}
			else{
						request.setAttribute("externalOperation",CMSConstants.EDIT_OPERATION);
		}
		}
		catch (Exception e) {
			log.error("Error in updating NewsEvents in NewsEventsAction",e);
				String msg = super.handleApplicationException(e);
				newsEventsForm.setErrorMessage(msg);
				newsEventsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from --- NewsEventsAction --- updateNewsEvents");
		saveErrors(request, errors);
		setNewsEvents(newsEventsForm);
		return mapping.findForward(CMSConstants.NEWS_EVENTS_VIEW);
	}
	
	/**
	 * This method is used to delete news and events details from database.
	 * @param mapping  - The ActionMapping used to select this instance
	 * @param form - The optional ActionForm bean for this request (if any)
	 * @param request - The HTTP request we are processing
	 * @param response - The HTTP response we are creating
	 * @return - The forward to which control should be transferred.
	 * @throws - Exception if an exception occurs
	 */
	
	public ActionForward deleteNewsEvents(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewsEventsForm newsEventsForm = (NewsEventsForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = newsEventsForm.validate(mapping, request);
		setUserId(request, newsEventsForm);
		int newsEventsId = newsEventsForm.getNewsEventsId();
		try {
			if(errors.isEmpty()){
				//call of NewsEventsHandler class by sending newsEventsid.
				boolean isDeleted = NewsEventsHandler.getInstance().deleteNewsEvents(newsEventsId);
				if(isDeleted){
					//if delete is success.
					ActionMessage message = new ActionMessage("knowledgepro.admin.newsEventsDeleteSuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					newsEventsForm.reset(mapping, request);	
				}if(!isDeleted){
					//if delete is failure.
					ActionMessage message = new ActionMessage("knowledgepro.admin.newsEventsDeleteFailure");
					messages.add("messages", message);
					saveMessages(request, messages);
					newsEventsForm.reset(mapping, request);	
				}
			}else{
				errors.add(messages);
				saveErrors(request, errors);
			}
		}catch (BusinessException businessException) {
			log.info("Exception addInterviewDefinition");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			newsEventsForm.setErrorMessage(msg);
			newsEventsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//call of setNewsEvents method.
		setNewsEvents(newsEventsForm);
		return mapping.findForward(CMSConstants.NEWS_EVENTS_VIEW);
	}
	
	/**
	 * This method is used to get the news and events details from database.
	 * @param newsEventsForm
	 * @throws Exception
	 */
	
	public void setNewsEvents(ActionForm form) throws Exception{
		//getting the data from news events table and setting to list of type NewsEventsTO.
		NewsEventsForm newsEventsForm	= (NewsEventsForm) form;
		try {
			List<NewsEventsTO> newsEventsList = NewsEventsHandler.getInstance().getNewsEvents();
			newsEventsForm.setNewsEventsList(newsEventsList);
		} catch (Exception e) {
			log.error("Error in assignListToForm of NewsEvents Action",e);
				String msg = super.handleApplicationException(e);
				newsEventsForm.setErrorMessage(msg);
				newsEventsForm.setErrorStack(e.getMessage());
			}
	}
	
}