package com.kp.cms.actions.studentfeedback;

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
import com.kp.cms.bo.studentfeedback.EvaFacultyFeedBackGroup;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.studentfeedback.EvaFacultyFeedBackGroupForm;
import com.kp.cms.handlers.studentfeedback.EvaFacultyFeedBackGroupHandler;
import com.kp.cms.to.studentfeedback.EvaFacultyFeedBackGroupTo;

public class EvaFacultyFeedBackGroupAction extends BaseDispatchAction
{
	private static final Log log = LogFactory.getLog(EvaFacultyFeedBackGroupAction.class);
 
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward initFacultyFeedBackGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        log.debug("Entering initFacultyFeedBackGroup");
        EvaFacultyFeedBackGroupForm facultyFeedBackGroupForm = (EvaFacultyFeedBackGroupForm)form;
        try{
        	facultyFeedBackGroupForm.reset(mapping, request);
            List<EvaFacultyFeedBackGroupTo> facultylist = EvaFacultyFeedBackGroupHandler.getInstance().getFacultyFeedBackEntry();
            facultyFeedBackGroupForm.setFacultGroupList(facultylist);
        }catch(BusinessException businessException){
            String msgKey = super.handleBusinessException(businessException);
            facultyFeedBackGroupForm.setErrorMessage(msgKey);
            facultyFeedBackGroupForm.setErrorStack(businessException.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }catch(Exception exception){
            String msg = super.handleApplicationException(exception);
            facultyFeedBackGroupForm.setErrorMessage(msg);
            facultyFeedBackGroupForm.setErrorStack(exception.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        log.debug("Leaving initFacultyFeedBackGroup ");
        return mapping.findForward(CMSConstants.FACULTY_FEEDBACK_GROUP);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addFacultyFeedBackGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
    	log.info("call of addFeedBackGroup in EvaFacultyFeedBackGroupAction class. ");
    	EvaFacultyFeedBackGroupForm facultyFeedBackGroupForm = (EvaFacultyFeedBackGroupForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = facultyFeedBackGroupForm.validate(mapping, request);
		HttpSession session=request.getSession();
		boolean isAdded = false;
		String name="";
		String order = "";
		try
		{  if(errors.isEmpty()){
			       setUserId(request, facultyFeedBackGroupForm);
					if(facultyFeedBackGroupForm.getName()!=null && !facultyFeedBackGroupForm.getName().isEmpty()){
						name = facultyFeedBackGroupForm.getName().trim();
						order=facultyFeedBackGroupForm.getOrder().trim();
					}else{
						return mapping.findForward(CMSConstants.LOGIN_PAGE);
					}
					EvaFacultyFeedBackGroup feedbackGroup = EvaFacultyFeedBackGroupHandler.getInstance().isNameExist(name, order, facultyFeedBackGroupForm);
					if(feedbackGroup!=null && feedbackGroup.getIsActive()){
						errors.add(CMSConstants.FEEDBACK_GROUPAQ_EXIST, new ActionError(CMSConstants.FEEDBACK_GROUPAQ_EXIST));
						saveErrors(request, errors);
					}else if(feedbackGroup!=null && !feedbackGroup.getIsActive()){
						errors.add(CMSConstants.FEEDBACK_GROUP_REACTIVATE, new ActionError(CMSConstants.FEEDBACK_GROUP_REACTIVATE));
						session.setAttribute("FeedBack",feedbackGroup.getId());
						saveErrors(request, errors);			
					}else{
						isAdded = EvaFacultyFeedBackGroupHandler.getInstance().addFeedBackGroup(facultyFeedBackGroupForm);
				
					if(isAdded){
						ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_ADD_SUCCESS);// Adding success message.
						messages.add("messages", message);
						saveMessages(request, messages);
						facultyFeedBackGroupForm.reset(mapping, request);	
					}else{
						errors.add(CMSConstants.FEEDBACK_GROUP_ADD_FAILURE, new ActionError(CMSConstants.FEEDBACK_GROUP_ADD_FAILURE));// Adding failure message
					}}				
		        }else{
					saveErrors(request, errors);
				}
		}catch(BusinessException businessException){
			log.info("Exception addFeedBackGroup");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}catch(Exception exception){	
			String msg = super.handleApplicationException(exception);
			facultyFeedBackGroupForm.setErrorMessage(msg);
			facultyFeedBackGroupForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		List<EvaFacultyFeedBackGroupTo> facultylist = EvaFacultyFeedBackGroupHandler.getInstance().getFacultyFeedBackEntry();
		facultyFeedBackGroupForm.setFacultGroupList(facultylist);
        log.info("end of addFeedBackGroup in EvaFacultyFeedBackGroupAction class. ");
        return mapping.findForward(CMSConstants.FACULTY_FEEDBACK_GROUP);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editFacultyFeedBackGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
    	log.info("call of editFeedBackGroup method in EvaFacultyFeedBackGroupAction class.");
    	EvaFacultyFeedBackGroupForm facultyFeedBackGroupForm = (EvaFacultyFeedBackGroupForm)form;
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = facultyFeedBackGroupForm.validate(mapping, request);
        try{
	    	if(errors.isEmpty()){
	    		//errors are empty
	    		EvaFacultyFeedBackGroupTo evaFacultyFeedBackGroupTo = EvaFacultyFeedBackGroupHandler.getInstance().editFeedBackGroup(facultyFeedBackGroupForm.getId());
	    		 facultyFeedBackGroupForm.setName(evaFacultyFeedBackGroupTo.getName().trim());
	    	        if(evaFacultyFeedBackGroupTo.getOrder() != null)
	    	        {
	    	            facultyFeedBackGroupForm.setOrder(evaFacultyFeedBackGroupTo.getOrder().trim());
	    	        }
	    	    facultyFeedBackGroupForm.setId(evaFacultyFeedBackGroupTo.getId());
	    		request.setAttribute("FeedBackGroup","edit");
	    		HttpSession session=request.getSession(false);
	    		if(session == null){
	    			return mapping.findForward(CMSConstants.LOGIN_PAGE);
	    		}else{
	    			session.setAttribute("name", evaFacultyFeedBackGroupTo.getName());
	    	        session.setAttribute("disOrder", evaFacultyFeedBackGroupTo.getOrder());
	    		}
	    	}
	    	else{
	    		//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
	    }catch (BusinessException businessException) {
			log.info("Exception editFeedBackGroup");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			facultyFeedBackGroupForm.setErrorMessage(msg);
			facultyFeedBackGroupForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		List<EvaFacultyFeedBackGroupTo> facultylist = EvaFacultyFeedBackGroupHandler.getInstance().getFacultyFeedBackEntry();
        facultyFeedBackGroupForm.setFacultGroupList(facultylist);
        log.info("end of editFeedBackGroup in EvaFacultyFeedBackGroupAction class. ");
        return mapping.findForward(CMSConstants.FACULTY_FEEDBACK_GROUP);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateFacultyFeedBackGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
    	log.info("call of updateFeedBackGroup method in EvaFacultyFeedBackGroupAction class.");
    	EvaFacultyFeedBackGroupForm facultyFeedBackGroupForm = (EvaFacultyFeedBackGroupForm)form;
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = facultyFeedBackGroupForm.validate(mapping, request);
        try{
        	if(isCancelled(request)){
        		EvaFacultyFeedBackGroupTo evaFacultyFeedBackGroupTo = EvaFacultyFeedBackGroupHandler.getInstance().editFeedBackGroup(facultyFeedBackGroupForm.getId());
                facultyFeedBackGroupForm.setName(evaFacultyFeedBackGroupTo.getName().trim());
                facultyFeedBackGroupForm.setOrder(evaFacultyFeedBackGroupTo.getOrder().trim());
                facultyFeedBackGroupForm.setId(evaFacultyFeedBackGroupTo.getId());
                request.setAttribute("FeedBackGroup", "edit");
                List<EvaFacultyFeedBackGroupTo> facultylist = EvaFacultyFeedBackGroupHandler.getInstance().getFacultyFeedBackEntry();
                facultyFeedBackGroupForm.setFacultGroupList(facultylist);
                return mapping.findForward(CMSConstants.FACULTY_FEEDBACK_GROUP);
    		} else if(errors.isEmpty()){
                setUserId(request, facultyFeedBackGroupForm);
                HttpSession session = request.getSession(false);
                String cname = session.getAttribute("name").toString();
                String csname = session.getAttribute("disOrder").toString();
                String name = facultyFeedBackGroupForm.getName().trim();
                String order = facultyFeedBackGroupForm.getOrder().trim();
                if(!cname.equalsIgnoreCase(name) || !csname.equalsIgnoreCase(order))
                {
                    if(!cname.equalsIgnoreCase(name) && csname.equalsIgnoreCase(order))
                    {
                        order = null;
                        EvaFacultyFeedBackGroup feedbackGroup = EvaFacultyFeedBackGroupHandler.getInstance().isNameExist(name, order, facultyFeedBackGroupForm);
                        if(feedbackGroup != null && feedbackGroup.getIsActive().booleanValue()){
                            errors.add(CMSConstants.FEEDBACK_GROUP_EXIST, new ActionError(CMSConstants.FEEDBACK_GROUP_EXIST));
                            request.setAttribute("FeedBackGroup", "edit");
                            saveErrors(request, errors);
                        } else if(feedbackGroup != null && !feedbackGroup.getIsActive().booleanValue())
                        {
                            errors.add(CMSConstants.FEEDBACK_GROUP_REACTIVATE, new ActionError(CMSConstants.FEEDBACK_GROUP_REACTIVATE));
                            saveErrors(request, errors);
                            request.setAttribute("FeedBackGroup", "edit");
                            session.setAttribute("FeedBack",feedbackGroup.getId());
                        } else
                        {
                            boolean isUpdated = EvaFacultyFeedBackGroupHandler.getInstance().updateFacultyFeedBackGroup(facultyFeedBackGroupForm);
                            if(isUpdated)
                            {
                                session.removeAttribute("name");
                                session.removeAttribute("order");
                                ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_ADD_SUCCESS);
                                messages.add("messages", message);
                                saveMessages(request, messages);
                                facultyFeedBackGroupForm.reset(mapping, request);
                            }
                            if(!isUpdated)
                            {
                                ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_ADD_FAILURE);
                                messages.add("messages", message);
                                saveMessages(request, messages);
                                facultyFeedBackGroupForm.reset(mapping, request);
                            }
                        }
                    } else if(!csname.equalsIgnoreCase(order) && cname.equalsIgnoreCase(name))
                    {
                        name = null;
                        EvaFacultyFeedBackGroup feedbackGroup = EvaFacultyFeedBackGroupHandler.getInstance().isNameExist(name, order, facultyFeedBackGroupForm);
                        if(feedbackGroup != null && feedbackGroup.getIsActive().booleanValue())
                        {
                            errors.add(CMSConstants.FEEDBACK_GROUP_ORDER_EXIST, new ActionError(CMSConstants.FEEDBACK_GROUP_ORDER_EXIST));
                            request.setAttribute("FeedBackGroup", "edit");
                            saveErrors(request, errors);
                        } else if(feedbackGroup != null && !feedbackGroup.getIsActive().booleanValue())
                        {
                            errors.add(CMSConstants.FEEDBACK_GROUP_REACTIVATE, new ActionError(CMSConstants.FEEDBACK_GROUP_REACTIVATE));
                            saveErrors(request, errors);
                            request.setAttribute("FeedBackGroup", "edit");
                            session.setAttribute("FeedBack",feedbackGroup.getId());
                        } else
                        {
                            boolean isUpdated = EvaFacultyFeedBackGroupHandler.getInstance().updateFacultyFeedBackGroup(facultyFeedBackGroupForm);
                            if(isUpdated)
                            {
                                session.removeAttribute("name");
                                session.removeAttribute("order");
                                ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_UPDATE_SUCCESS);
                                messages.add("messages", message);
                                saveMessages(request, messages);
                                facultyFeedBackGroupForm.reset(mapping, request);
                            }
                            if(!isUpdated)
                            {
                                ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_UPDATE_FAILURE);
                                messages.add("messages", message);
                                saveMessages(request, messages);
                                facultyFeedBackGroupForm.reset(mapping, request);
                            }
                        }
                    } else
                    if(!csname.equalsIgnoreCase(order) && !cname.equalsIgnoreCase(name))
                    {
                        EvaFacultyFeedBackGroup feedbackGroup = EvaFacultyFeedBackGroupHandler.getInstance().isNameExist(name, order, facultyFeedBackGroupForm);
                        if(feedbackGroup != null && feedbackGroup.getIsActive().booleanValue())
                        {
                            errors.add(CMSConstants.FEEDBACK_GROUP_EXIST, new ActionError(CMSConstants.FEEDBACK_GROUP_EXIST));
                            request.setAttribute("FeedBackGroup", "edit");
                            saveErrors(request, errors);
                        } else if(feedbackGroup != null && !feedbackGroup.getIsActive().booleanValue())
                        {
                            errors.add(CMSConstants.FEEDBACK_GROUP_REACTIVATE, new ActionError(CMSConstants.FEEDBACK_GROUP_REACTIVATE));
                            saveErrors(request, errors);
                            request.setAttribute("FeedBackGroup", "edit");
                            session.setAttribute("FeedBack",feedbackGroup.getId());
                        } else
                        {
                            boolean isUpdated = EvaFacultyFeedBackGroupHandler.getInstance().updateFacultyFeedBackGroup(facultyFeedBackGroupForm);
                            if(isUpdated)
                            {
                                session.removeAttribute("name");
                                session.removeAttribute("order");
                                ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_UPDATE_SUCCESS);
                                messages.add("messages", message);
                                saveMessages(request, messages);
                                facultyFeedBackGroupForm.reset(mapping, request);
                            }
                            if(!isUpdated)
                            {
                                ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_UPDATE_FAILURE);
                                messages.add("messages", message);
                                saveMessages(request, messages);
                                facultyFeedBackGroupForm.reset(mapping, request);
                            }
                        }
                    }
                } else
                {
                    boolean isUpdated = EvaFacultyFeedBackGroupHandler.getInstance().updateFacultyFeedBackGroup(facultyFeedBackGroupForm);
                    if(isUpdated)
                    {
                        session.removeAttribute("name");
                        session.removeAttribute("order");
                        ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_UPDATE_SUCCESS);
                        messages.add("messages", message);
                        saveMessages(request, messages);
                        facultyFeedBackGroupForm.reset(mapping, request);
                    }
                    if(!isUpdated)
                    {
                        ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_UPDATE_FAILURE);
                        messages.add("messages", message);
                        saveMessages(request, messages);
                        facultyFeedBackGroupForm.reset(mapping, request);
                    }
                }
            } else
            {
                errors.add(messages);
                request.setAttribute("FeedBackGroup", "edit");
                saveErrors(request, errors);
            }
        	
        }catch(BusinessException businessException)
        {
            log.info("Exception updateFeedBackGroup");
            String msgKey = super.handleBusinessException(businessException);
            ActionMessage message = new ActionMessage(msgKey);
            messages.add("messages", message);
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        catch(Exception exception)
        {
            String msg = super.handleApplicationException(exception);
            facultyFeedBackGroupForm.setErrorMessage(msg);
            facultyFeedBackGroupForm.setErrorStack(exception.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        List<EvaFacultyFeedBackGroupTo> facultylist = EvaFacultyFeedBackGroupHandler.getInstance().getFacultyFeedBackEntry();
        facultyFeedBackGroupForm.setFacultGroupList(facultylist);
        log.info("end of updateFeedBackGroup method in EvaFacultyFeedBackGroupAction class. ");
        return mapping.findForward(CMSConstants.FACULTY_FEEDBACK_GROUP);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteFacultyFeedBackGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        log.info("call of deleteFeedBackGroup method in EvaFacultyFeedBackGroupAction class.");
        EvaFacultyFeedBackGroupForm facultyFeedBackGroupForm = (EvaFacultyFeedBackGroupForm)form;
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = facultyFeedBackGroupForm.validate(mapping, request);
        try
        {
            if(errors.isEmpty())
            {
                setUserId(request, facultyFeedBackGroupForm);
                boolean isDelete = EvaFacultyFeedBackGroupHandler.getInstance().deleteFeedBackGroup(facultyFeedBackGroupForm.getId(), facultyFeedBackGroupForm.getUserId());
                if(isDelete)
                {
                    ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_DELETE_SUCCESS);
                    messages.add("messages", message);
                    saveMessages(request, messages);
                    facultyFeedBackGroupForm.reset(mapping, request);
                }
                if(!isDelete)
                {
                    ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_DELETE_FAILURE);
                    messages.add("messages", message);
                    saveMessages(request, messages);
                    facultyFeedBackGroupForm.reset(mapping, request);
                }
            } else
            {
                errors.add(messages);
                saveErrors(request, errors);
            }
        }catch(BusinessException businessException)
        {
            log.info("Exception deleteFeedBackGroup");
            String msgKey = super.handleBusinessException(businessException);
            ActionMessage message = new ActionMessage(msgKey);
            messages.add("messages", message);
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }catch(Exception exception)
        {
            String msg = super.handleApplicationException(exception);
            facultyFeedBackGroupForm.setErrorMessage(msg);
            facultyFeedBackGroupForm.setErrorStack(exception.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        List<EvaFacultyFeedBackGroupTo> facultylist = EvaFacultyFeedBackGroupHandler.getInstance().getFacultyFeedBackEntry();
        facultyFeedBackGroupForm.setFacultGroupList(facultylist);
        log.info("end of deleteFeedBackGroup method in EvaFacultyFeedBackGroupAction class. ");
        return mapping.findForward(CMSConstants.FACULTY_FEEDBACK_GROUP);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward reActivateFacultyFeedBackGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        log.info("call of reActivateFeedBackGroup method in EvaFacultyFeedBackGroupAction class.");
        EvaFacultyFeedBackGroupForm facultyFeedBackGroupForm = (EvaFacultyFeedBackGroupForm)form;
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = facultyFeedBackGroupForm.validate(mapping, request);
        HttpSession session = request.getSession();
        try
        {
            if(errors.isEmpty())
            {
                setUserId(request, facultyFeedBackGroupForm);
                int id = ((Integer)session.getAttribute("FeedBack")).intValue();
                boolean isActivated = EvaFacultyFeedBackGroupHandler.getInstance().reActivateFeedBackGroup(facultyFeedBackGroupForm.getName(), facultyFeedBackGroupForm.getUserId(), id);
                if(isActivated)
                {
                    ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_REACTIVATE_SUCCESS);
                    messages.add("messages", message);
                    saveMessages(request, messages);
                    facultyFeedBackGroupForm.reset(mapping, request);
                } else
                {
                    ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_REACTIVATE_FAILURE);
                    messages.add("messages", message);
                    saveMessages(request, messages);
                    facultyFeedBackGroupForm.reset(mapping, request);
                }
            } else
            {
                errors.add(messages);
                saveErrors(request, errors);
            }
        }
        catch(BusinessException businessException)
        {
            log.info("Exception reActivateFeedBackGroup");
            String msgKey = super.handleBusinessException(businessException);
            ActionMessage message = new ActionMessage(msgKey);
            messages.add("messages", message);
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        catch(Exception exception)
        {
            String msg = super.handleApplicationException(exception);
            facultyFeedBackGroupForm.setErrorMessage(msg);
            facultyFeedBackGroupForm.setErrorStack(exception.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        List<EvaFacultyFeedBackGroupTo> facultylist = EvaFacultyFeedBackGroupHandler.getInstance().getFacultyFeedBackEntry();
        facultyFeedBackGroupForm.setFacultGroupList(facultylist);
        log.info("end of updateFeedBackGroup method in EvaFacultyFeedBackGroupAction class. ");
        return mapping.findForward(CMSConstants.FACULTY_FEEDBACK_GROUP);
    }

}
