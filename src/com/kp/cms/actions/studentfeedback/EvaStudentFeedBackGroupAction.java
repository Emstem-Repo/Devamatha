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
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackGroup;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.studentfeedback.EvaStudentFeedBackGroupForm;
import com.kp.cms.handlers.studentfeedback.EvaStudentFeedBackGroupHandler;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackGroupTo;

public class EvaStudentFeedBackGroupAction extends BaseDispatchAction
{
	private static final Log log = LogFactory.getLog(EvaStudentFeedBackGroupAction.class);
 
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward initStudentFeedBackGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        log.debug("Entering initStudentFeedBackGroup");
        EvaStudentFeedBackGroupForm studentFeedBackGroupForm = (EvaStudentFeedBackGroupForm)form;
        try{
            studentFeedBackGroupForm.reset(mapping, request);
            List<EvaStudentFeedBackGroupTo> studentlist = EvaStudentFeedBackGroupHandler.getInstance().getStudentFeedBackEntry();
            studentFeedBackGroupForm.setFeedBackGroupList(studentlist);
        }catch(BusinessException businessException){
            String msgKey = super.handleBusinessException(businessException);
            studentFeedBackGroupForm.setErrorMessage(msgKey);
            studentFeedBackGroupForm.setErrorStack(businessException.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }catch(Exception exception){
            String msg = super.handleApplicationException(exception);
            studentFeedBackGroupForm.setErrorMessage(msg);
            studentFeedBackGroupForm.setErrorStack(exception.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        log.debug("Leaving initStudentFeedBackGroup ");
        return mapping.findForward(CMSConstants.STUDENT_FEEDBACK_GROUP);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addFeedBackGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
    	log.info("call of addFeedBackGroup in EvaStudentFeedBackGroupAction class. ");
    	EvaStudentFeedBackGroupForm studentFeedBackGroupForm = (EvaStudentFeedBackGroupForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = studentFeedBackGroupForm.validate(mapping, request);
		HttpSession session=request.getSession();
		boolean isAdded = false;
		String name="";
		String order = "";
		try
		{  if(errors.isEmpty()){
			       setUserId(request, studentFeedBackGroupForm);
					if(studentFeedBackGroupForm.getName()!=null && !studentFeedBackGroupForm.getName().isEmpty()){
						name = studentFeedBackGroupForm.getName().trim();
						order=studentFeedBackGroupForm.getDisOrder().trim();
					}else{
						return mapping.findForward(CMSConstants.LOGIN_PAGE);
					}
					EvaStudentFeedbackGroup feedbackGroup = EvaStudentFeedBackGroupHandler.getInstance().isNameExist(name, order, studentFeedBackGroupForm);
					if(feedbackGroup!=null && feedbackGroup.getIsActive()){
						errors.add(CMSConstants.FEEDBACK_GROUPAQ_EXIST, new ActionError(CMSConstants.FEEDBACK_GROUPAQ_EXIST));
						saveErrors(request, errors);
					}else if(feedbackGroup!=null && !feedbackGroup.getIsActive()){
						errors.add(CMSConstants.FEEDBACK_GROUP_REACTIVATE, new ActionError(CMSConstants.FEEDBACK_GROUP_REACTIVATE));
						session.setAttribute("FeedBack",feedbackGroup.getId());
						saveErrors(request, errors);			
					}else{
						isAdded = EvaStudentFeedBackGroupHandler.getInstance().addFeedBackGroup(studentFeedBackGroupForm);
				
					if(isAdded){
						ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_ADD_SUCCESS);// Adding success message.
						messages.add("messages", message);
						saveMessages(request, messages);
						studentFeedBackGroupForm.reset(mapping, request);	
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
			studentFeedBackGroupForm.setErrorMessage(msg);
			studentFeedBackGroupForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		List<EvaStudentFeedBackGroupTo> studentlist = EvaStudentFeedBackGroupHandler.getInstance().getStudentFeedBackEntry();
        studentFeedBackGroupForm.setFeedBackGroupList(studentlist);
        log.info("end of addFeedBackGroup in EvaStudentFeedBackGroupAction class. ");
        return mapping.findForward(CMSConstants.STUDENT_FEEDBACK_GROUP);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editFeedBackGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
    	log.info("call of editFeedBackGroup method in EvaStudentFeedBackGroupAction class.");
    	EvaStudentFeedBackGroupForm studentFeedBackGroupForm = (EvaStudentFeedBackGroupForm)form;
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = studentFeedBackGroupForm.validate(mapping, request);
        try{
	    	if(errors.isEmpty()){
	    		//errors are empty
	    		EvaStudentFeedBackGroupTo evaStudentFeedBackGroupTo = EvaStudentFeedBackGroupHandler.getInstance().editFeedBackGroup(studentFeedBackGroupForm.getId());
	    		 studentFeedBackGroupForm.setName(evaStudentFeedBackGroupTo.getName().trim());
	    	        if(evaStudentFeedBackGroupTo.getDisOrder() != null)
	    	        {
	    	            studentFeedBackGroupForm.setDisOrder(evaStudentFeedBackGroupTo.getDisOrder().trim());
	    	        }
	    	    studentFeedBackGroupForm.setId(evaStudentFeedBackGroupTo.getId());
	    		request.setAttribute("FeedBackGroup","edit");
	    		HttpSession session=request.getSession(false);
	    		if(session == null){
	    			return mapping.findForward(CMSConstants.LOGIN_PAGE);
	    		}else{
	    			session.setAttribute("name", evaStudentFeedBackGroupTo.getName());
	    	        session.setAttribute("disOrder", evaStudentFeedBackGroupTo.getDisOrder());
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
			studentFeedBackGroupForm.setErrorMessage(msg);
			studentFeedBackGroupForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		List<EvaStudentFeedBackGroupTo> studentlist = EvaStudentFeedBackGroupHandler.getInstance().getStudentFeedBackEntry();
        studentFeedBackGroupForm.setFeedBackGroupList(studentlist);
        log.info("end of editFeedBackGroup in EvaStudentFeedBackGroupAction class. ");
        return mapping.findForward(CMSConstants.STUDENT_FEEDBACK_GROUP);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateFeedBackGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
    	log.info("call of updateFeedBackGroup method in EvaStudentFeedBackGroupAction class.");
    	EvaStudentFeedBackGroupForm studentFeedBackGroupForm = (EvaStudentFeedBackGroupForm)form;
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = studentFeedBackGroupForm.validate(mapping, request);
        try{
        	if(isCancelled(request)){
        		EvaStudentFeedBackGroupTo evaStudentFeedBackGroupTo = EvaStudentFeedBackGroupHandler.getInstance().editFeedBackGroup(studentFeedBackGroupForm.getId());
                studentFeedBackGroupForm.setName(evaStudentFeedBackGroupTo.getName().trim());
                studentFeedBackGroupForm.setDisOrder(evaStudentFeedBackGroupTo.getDisOrder().trim());
                studentFeedBackGroupForm.setId(evaStudentFeedBackGroupTo.getId());
                request.setAttribute("FeedBackGroup", "edit");
                List<EvaStudentFeedBackGroupTo> studentlist = EvaStudentFeedBackGroupHandler.getInstance().getStudentFeedBackEntry();
                studentFeedBackGroupForm.setFeedBackGroupList(studentlist);
                return mapping.findForward(CMSConstants.STUDENT_FEEDBACK_GROUP);
    		} else if(errors.isEmpty()){
                setUserId(request, studentFeedBackGroupForm);
                HttpSession session = request.getSession(false);
                String cname = session.getAttribute("name").toString();
                String csname = session.getAttribute("disOrder").toString();
                String name = studentFeedBackGroupForm.getName().trim();
                String order = studentFeedBackGroupForm.getDisOrder().trim();
                if(!cname.equalsIgnoreCase(name) || !csname.equalsIgnoreCase(order))
                {
                    if(!cname.equalsIgnoreCase(name) && csname.equalsIgnoreCase(order))
                    {
                        order = null;
                        EvaStudentFeedbackGroup feedbackGroup = EvaStudentFeedBackGroupHandler.getInstance().isNameExist(name, order, studentFeedBackGroupForm);
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
                            boolean isUpdated = EvaStudentFeedBackGroupHandler.getInstance().updateStudentFeedBackGroup(studentFeedBackGroupForm);
                            if(isUpdated)
                            {
                                session.removeAttribute("name");
                                session.removeAttribute("order");
                                ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_ADD_SUCCESS);
                                messages.add("messages", message);
                                saveMessages(request, messages);
                                studentFeedBackGroupForm.reset(mapping, request);
                            }
                            if(!isUpdated)
                            {
                                ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_ADD_FAILURE);
                                messages.add("messages", message);
                                saveMessages(request, messages);
                                studentFeedBackGroupForm.reset(mapping, request);
                            }
                        }
                    } else if(!csname.equalsIgnoreCase(order) && cname.equalsIgnoreCase(name))
                    {
                        name = null;
                        EvaStudentFeedbackGroup feedbackGroup = EvaStudentFeedBackGroupHandler.getInstance().isNameExist(name, order, studentFeedBackGroupForm);
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
                            boolean isUpdated = EvaStudentFeedBackGroupHandler.getInstance().updateStudentFeedBackGroup(studentFeedBackGroupForm);
                            if(isUpdated)
                            {
                                session.removeAttribute("name");
                                session.removeAttribute("order");
                                ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_UPDATE_SUCCESS);
                                messages.add("messages", message);
                                saveMessages(request, messages);
                                studentFeedBackGroupForm.reset(mapping, request);
                            }
                            if(!isUpdated)
                            {
                                ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_UPDATE_FAILURE);
                                messages.add("messages", message);
                                saveMessages(request, messages);
                                studentFeedBackGroupForm.reset(mapping, request);
                            }
                        }
                    } else
                    if(!csname.equalsIgnoreCase(order) && !cname.equalsIgnoreCase(name))
                    {
                        EvaStudentFeedbackGroup feedbackGroup = EvaStudentFeedBackGroupHandler.getInstance().isNameExist(name, order, studentFeedBackGroupForm);
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
                            boolean isUpdated = EvaStudentFeedBackGroupHandler.getInstance().updateStudentFeedBackGroup(studentFeedBackGroupForm);
                            if(isUpdated)
                            {
                                session.removeAttribute("name");
                                session.removeAttribute("order");
                                ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_UPDATE_SUCCESS);
                                messages.add("messages", message);
                                saveMessages(request, messages);
                                studentFeedBackGroupForm.reset(mapping, request);
                            }
                            if(!isUpdated)
                            {
                                ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_UPDATE_FAILURE);
                                messages.add("messages", message);
                                saveMessages(request, messages);
                                studentFeedBackGroupForm.reset(mapping, request);
                            }
                        }
                    }
                } else
                {
                    boolean isUpdated = EvaStudentFeedBackGroupHandler.getInstance().updateStudentFeedBackGroup(studentFeedBackGroupForm);
                    if(isUpdated)
                    {
                        session.removeAttribute("name");
                        session.removeAttribute("order");
                        ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_UPDATE_SUCCESS);
                        messages.add("messages", message);
                        saveMessages(request, messages);
                        studentFeedBackGroupForm.reset(mapping, request);
                    }
                    if(!isUpdated)
                    {
                        ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_UPDATE_FAILURE);
                        messages.add("messages", message);
                        saveMessages(request, messages);
                        studentFeedBackGroupForm.reset(mapping, request);
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
            studentFeedBackGroupForm.setErrorMessage(msg);
            studentFeedBackGroupForm.setErrorStack(exception.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        List<EvaStudentFeedBackGroupTo> studentlist = EvaStudentFeedBackGroupHandler.getInstance().getStudentFeedBackEntry();
        studentFeedBackGroupForm.setFeedBackGroupList(studentlist);
        log.info("end of updateFeedBackGroup method in EvaStudentFeedBackGroupAction class. ");
        return mapping.findForward(CMSConstants.STUDENT_FEEDBACK_GROUP);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteFeedBackGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        log.info("call of deleteFeedBackGroup method in EvaStudentFeedBackGroupAction class.");
        EvaStudentFeedBackGroupForm studentFeedBackGroupForm = (EvaStudentFeedBackGroupForm)form;
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = studentFeedBackGroupForm.validate(mapping, request);
        try
        {
            if(errors.isEmpty())
            {
                setUserId(request, studentFeedBackGroupForm);
                boolean isDelete = EvaStudentFeedBackGroupHandler.getInstance().deleteFeedBackGroup(studentFeedBackGroupForm.getId(), studentFeedBackGroupForm.getUserId());
                if(isDelete)
                {
                    ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_DELETE_SUCCESS);
                    messages.add("messages", message);
                    saveMessages(request, messages);
                    studentFeedBackGroupForm.reset(mapping, request);
                }
                if(!isDelete)
                {
                    ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_DELETE_FAILURE);
                    messages.add("messages", message);
                    saveMessages(request, messages);
                    studentFeedBackGroupForm.reset(mapping, request);
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
            studentFeedBackGroupForm.setErrorMessage(msg);
            studentFeedBackGroupForm.setErrorStack(exception.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        List<EvaStudentFeedBackGroupTo> studentlist = EvaStudentFeedBackGroupHandler.getInstance().getStudentFeedBackEntry();
        studentFeedBackGroupForm.setFeedBackGroupList(studentlist);
        log.info("end of deleteFeedBackGroup method in EvaStudentFeedBackGroupAction class. ");
        return mapping.findForward(CMSConstants.STUDENT_FEEDBACK_GROUP);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward reActivateFeedBackGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        log.info("call of reActivateFeedBackGroup method in EvaStudentFeedBackGroupAction class.");
        EvaStudentFeedBackGroupForm studentFeedBackGroupForm = (EvaStudentFeedBackGroupForm)form;
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = studentFeedBackGroupForm.validate(mapping, request);
        HttpSession session = request.getSession();
        try
        {
            if(errors.isEmpty())
            {
                setUserId(request, studentFeedBackGroupForm);
                int id = ((Integer)session.getAttribute("FeedBack")).intValue();
                boolean isActivated = EvaStudentFeedBackGroupHandler.getInstance().reActivateFeedBackGroup(studentFeedBackGroupForm.getName(), studentFeedBackGroupForm.getUserId(), id);
                if(isActivated)
                {
                    ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_REACTIVATE_SUCCESS);
                    messages.add("messages", message);
                    saveMessages(request, messages);
                    studentFeedBackGroupForm.reset(mapping, request);
                } else
                {
                    ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_REACTIVATE_FAILURE);
                    messages.add("messages", message);
                    saveMessages(request, messages);
                    studentFeedBackGroupForm.reset(mapping, request);
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
            studentFeedBackGroupForm.setErrorMessage(msg);
            studentFeedBackGroupForm.setErrorStack(exception.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        List<EvaStudentFeedBackGroupTo> studentlist = EvaStudentFeedBackGroupHandler.getInstance().getStudentFeedBackEntry();
        studentFeedBackGroupForm.setFeedBackGroupList(studentlist);
        log.info("end of updateFeedBackGroup method in EvaStudentFeedBackGroupAction class. ");
        return mapping.findForward(CMSConstants.STUDENT_FEEDBACK_GROUP);
    }

}
