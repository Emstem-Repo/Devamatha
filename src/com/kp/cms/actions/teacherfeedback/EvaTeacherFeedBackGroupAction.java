package com.kp.cms.actions.teacherfeedback;

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
import com.kp.cms.bo.teacherfeedback.EvaTeacherFeedbackGroup;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.teacherfeedback.EvaTeacherFeedBackGroupForm;
import com.kp.cms.handlers.teacherfeedback.EvaTeacherFeedBackGroupHandler;
import com.kp.cms.to.teacherfeedback.EvaTeacherFeedBackGroupTo;

public class EvaTeacherFeedBackGroupAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(EvaTeacherFeedBackGroupAction.class);

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward initTeacherFeedBackGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		log.debug("Entering initTeacherFeedBackGroup");
		EvaTeacherFeedBackGroupForm teacherFeedBackGroupForm = (EvaTeacherFeedBackGroupForm)form;
		try{
			teacherFeedBackGroupForm.reset(mapping, request);
			List<EvaTeacherFeedBackGroupTo> studentlist = EvaTeacherFeedBackGroupHandler.getInstance().getTeacherFeedBackEntry();
			teacherFeedBackGroupForm.setFeedBackGroupList(studentlist);
		}catch(BusinessException businessException){
			String msgKey = super.handleBusinessException(businessException);
			teacherFeedBackGroupForm.setErrorMessage(msgKey);
			teacherFeedBackGroupForm.setErrorStack(businessException.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}catch(Exception exception){
			String msg = super.handleApplicationException(exception);
			teacherFeedBackGroupForm.setErrorMessage(msg);
			teacherFeedBackGroupForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.debug("Leaving initTeacherFeedBackGroup ");
		return mapping.findForward(CMSConstants.TEACHER_FEEDBACK_GROUP);
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
		log.info("call of addFeedBackGroup in EvaTeacherFeedBackGroupAction class. ");
		EvaTeacherFeedBackGroupForm teacherFeedBackGroupForm = (EvaTeacherFeedBackGroupForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = teacherFeedBackGroupForm.validate(mapping, request);
		HttpSession session=request.getSession();
		boolean isAdded = false;
		String name="";
		String order = "";
		try
		{  if(errors.isEmpty()){
			setUserId(request, teacherFeedBackGroupForm);
			if(teacherFeedBackGroupForm.getName()!=null && !teacherFeedBackGroupForm.getName().isEmpty()){
				name = teacherFeedBackGroupForm.getName().trim();
				order=teacherFeedBackGroupForm.getDisOrder().trim();
			}else{
				return mapping.findForward(CMSConstants.LOGIN_PAGE);
			}
			EvaTeacherFeedbackGroup feedbackGroup = EvaTeacherFeedBackGroupHandler.getInstance().isNameExist(name, order, teacherFeedBackGroupForm);
			if(feedbackGroup!=null && feedbackGroup.getIsActive()){
				errors.add(CMSConstants.FEEDBACK_GROUPAQ_EXIST, new ActionError(CMSConstants.FEEDBACK_GROUPAQ_EXIST));
				saveErrors(request, errors);
			}else if(feedbackGroup!=null && !feedbackGroup.getIsActive()){
				errors.add(CMSConstants.FEEDBACK_GROUP_REACTIVATE, new ActionError(CMSConstants.FEEDBACK_GROUP_REACTIVATE));
				session.setAttribute("FeedBack",feedbackGroup.getId());
				saveErrors(request, errors);			
			}else{
				isAdded = EvaTeacherFeedBackGroupHandler.getInstance().addFeedBackGroup(teacherFeedBackGroupForm);

				if(isAdded){
					ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_ADD_SUCCESS);// Adding success message.
					messages.add("messages", message);
					saveMessages(request, messages);
					teacherFeedBackGroupForm.reset(mapping, request);	
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
			teacherFeedBackGroupForm.setErrorMessage(msg);
			teacherFeedBackGroupForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		List<EvaTeacherFeedBackGroupTo> studentlist = EvaTeacherFeedBackGroupHandler.getInstance().getTeacherFeedBackEntry();
		teacherFeedBackGroupForm.setFeedBackGroupList(studentlist);
		log.info("end of addFeedBackGroup in EvaTeacherFeedBackGroupAction class. ");
		return mapping.findForward(CMSConstants.TEACHER_FEEDBACK_GROUP);
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
		log.info("call of editFeedBackGroup method in EvaTeacherFeedBackGroupAction class.");
		EvaTeacherFeedBackGroupForm teacherFeedBackGroupForm = (EvaTeacherFeedBackGroupForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = teacherFeedBackGroupForm.validate(mapping, request);
		try{
			if(errors.isEmpty()){
				//errors are empty
				EvaTeacherFeedBackGroupTo evaTeacherFeedBackGroupTo = EvaTeacherFeedBackGroupHandler.getInstance().editFeedBackGroup(teacherFeedBackGroupForm.getId());
				teacherFeedBackGroupForm.setName(evaTeacherFeedBackGroupTo.getName().trim());
				if(evaTeacherFeedBackGroupTo.getDisOrder() != null)
				{
					teacherFeedBackGroupForm.setDisOrder(evaTeacherFeedBackGroupTo.getDisOrder().trim());
				}
				teacherFeedBackGroupForm.setId(evaTeacherFeedBackGroupTo.getId());
				request.setAttribute("FeedBackGroup","edit");
				HttpSession session=request.getSession(false);
				if(session == null){
					return mapping.findForward(CMSConstants.LOGIN_PAGE);
				}else{
					session.setAttribute("name", evaTeacherFeedBackGroupTo.getName());
					session.setAttribute("disOrder", evaTeacherFeedBackGroupTo.getDisOrder());
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
			teacherFeedBackGroupForm.setErrorMessage(msg);
			teacherFeedBackGroupForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		List<EvaTeacherFeedBackGroupTo> studentlist = EvaTeacherFeedBackGroupHandler.getInstance().getTeacherFeedBackEntry();
		teacherFeedBackGroupForm.setFeedBackGroupList(studentlist);
		log.info("end of editFeedBackGroup in EvaTeacherFeedBackGroupAction class. ");
		return mapping.findForward(CMSConstants.TEACHER_FEEDBACK_GROUP);
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
		log.info("call of updateFeedBackGroup method in EvaTeacherFeedBackGroupAction class.");
		EvaTeacherFeedBackGroupForm teacherFeedBackGroupForm = (EvaTeacherFeedBackGroupForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = teacherFeedBackGroupForm.validate(mapping, request);
		try{
			if(isCancelled(request)){
				EvaTeacherFeedBackGroupTo evaTeacherFeedBackGroupTo = EvaTeacherFeedBackGroupHandler.getInstance().editFeedBackGroup(teacherFeedBackGroupForm.getId());
				teacherFeedBackGroupForm.setName(evaTeacherFeedBackGroupTo.getName().trim());
				teacherFeedBackGroupForm.setDisOrder(evaTeacherFeedBackGroupTo.getDisOrder().trim());
				teacherFeedBackGroupForm.setId(evaTeacherFeedBackGroupTo.getId());
				request.setAttribute("FeedBackGroup", "edit");
				List<EvaTeacherFeedBackGroupTo> studentlist = EvaTeacherFeedBackGroupHandler.getInstance().getTeacherFeedBackEntry();
				teacherFeedBackGroupForm.setFeedBackGroupList(studentlist);
				return mapping.findForward(CMSConstants.TEACHER_FEEDBACK_GROUP);
			} else if(errors.isEmpty()){
				setUserId(request, teacherFeedBackGroupForm);
				HttpSession session = request.getSession(false);
				String cname = session.getAttribute("name").toString();
				String csname = session.getAttribute("disOrder").toString();
				String name = teacherFeedBackGroupForm.getName().trim();
				String order = teacherFeedBackGroupForm.getDisOrder().trim();
				if(!cname.equalsIgnoreCase(name) || !csname.equalsIgnoreCase(order))
				{
					if(!cname.equalsIgnoreCase(name) && csname.equalsIgnoreCase(order))
					{
						order = null;
						EvaTeacherFeedbackGroup feedbackGroup = EvaTeacherFeedBackGroupHandler.getInstance().isNameExist(name, order, teacherFeedBackGroupForm);
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
							boolean isUpdated = EvaTeacherFeedBackGroupHandler.getInstance().updateTeacherFeedBackGroup(teacherFeedBackGroupForm);
							if(isUpdated)
							{
								session.removeAttribute("name");
								session.removeAttribute("order");
								ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_ADD_SUCCESS);
								messages.add("messages", message);
								saveMessages(request, messages);
								teacherFeedBackGroupForm.reset(mapping, request);
							}
							if(!isUpdated)
							{
								ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_ADD_FAILURE);
								messages.add("messages", message);
								saveMessages(request, messages);
								teacherFeedBackGroupForm.reset(mapping, request);
							}
						}
					} else if(!csname.equalsIgnoreCase(order) && cname.equalsIgnoreCase(name))
					{
						name = null;
						EvaTeacherFeedbackGroup feedbackGroup = EvaTeacherFeedBackGroupHandler.getInstance().isNameExist(name, order, teacherFeedBackGroupForm);
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
							boolean isUpdated = EvaTeacherFeedBackGroupHandler.getInstance().updateTeacherFeedBackGroup(teacherFeedBackGroupForm);
							if(isUpdated)
							{
								session.removeAttribute("name");
								session.removeAttribute("order");
								ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_UPDATE_SUCCESS);
								messages.add("messages", message);
								saveMessages(request, messages);
								teacherFeedBackGroupForm.reset(mapping, request);
							}
							if(!isUpdated)
							{
								ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_UPDATE_FAILURE);
								messages.add("messages", message);
								saveMessages(request, messages);
								teacherFeedBackGroupForm.reset(mapping, request);
							}
						}
					} else
						if(!csname.equalsIgnoreCase(order) && !cname.equalsIgnoreCase(name))
						{
							EvaTeacherFeedbackGroup feedbackGroup = EvaTeacherFeedBackGroupHandler.getInstance().isNameExist(name, order, teacherFeedBackGroupForm);
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
								boolean isUpdated = EvaTeacherFeedBackGroupHandler.getInstance().updateTeacherFeedBackGroup(teacherFeedBackGroupForm);
								if(isUpdated)
								{
									session.removeAttribute("name");
									session.removeAttribute("order");
									ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_UPDATE_SUCCESS);
									messages.add("messages", message);
									saveMessages(request, messages);
									teacherFeedBackGroupForm.reset(mapping, request);
								}
								if(!isUpdated)
								{
									ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_UPDATE_FAILURE);
									messages.add("messages", message);
									saveMessages(request, messages);
									teacherFeedBackGroupForm.reset(mapping, request);
								}
							}
						}
				} else
				{
					boolean isUpdated = EvaTeacherFeedBackGroupHandler.getInstance().updateTeacherFeedBackGroup(teacherFeedBackGroupForm);
					if(isUpdated)
					{
						session.removeAttribute("name");
						session.removeAttribute("order");
						ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_UPDATE_SUCCESS);
						messages.add("messages", message);
						saveMessages(request, messages);
						teacherFeedBackGroupForm.reset(mapping, request);
					}
					if(!isUpdated)
					{
						ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_UPDATE_FAILURE);
						messages.add("messages", message);
						saveMessages(request, messages);
						teacherFeedBackGroupForm.reset(mapping, request);
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
			teacherFeedBackGroupForm.setErrorMessage(msg);
			teacherFeedBackGroupForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		List<EvaTeacherFeedBackGroupTo> studentlist = EvaTeacherFeedBackGroupHandler.getInstance().getTeacherFeedBackEntry();
		teacherFeedBackGroupForm.setFeedBackGroupList(studentlist);
		log.info("end of updateFeedBackGroup method in EvaTeacherFeedBackGroupAction class. ");
		return mapping.findForward(CMSConstants.TEACHER_FEEDBACK_GROUP);
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
		log.info("call of deleteFeedBackGroup method in EvaTeacherFeedBackGroupAction class.");
		EvaTeacherFeedBackGroupForm teacherFeedBackGroupForm = (EvaTeacherFeedBackGroupForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = teacherFeedBackGroupForm.validate(mapping, request);
		try
		{
			if(errors.isEmpty())
			{
				setUserId(request, teacherFeedBackGroupForm);
				boolean isDelete = EvaTeacherFeedBackGroupHandler.getInstance().deleteFeedBackGroup(teacherFeedBackGroupForm.getId(), teacherFeedBackGroupForm.getUserId());
				if(isDelete)
				{
					ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_DELETE_SUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
					teacherFeedBackGroupForm.reset(mapping, request);
				}
				if(!isDelete)
				{
					ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_DELETE_FAILURE);
					messages.add("messages", message);
					saveMessages(request, messages);
					teacherFeedBackGroupForm.reset(mapping, request);
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
			teacherFeedBackGroupForm.setErrorMessage(msg);
			teacherFeedBackGroupForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		List<EvaTeacherFeedBackGroupTo> studentlist = EvaTeacherFeedBackGroupHandler.getInstance().getTeacherFeedBackEntry();
		teacherFeedBackGroupForm.setFeedBackGroupList(studentlist);
		log.info("end of deleteFeedBackGroup method in EvaTeacherFeedBackGroupAction class. ");
		return mapping.findForward(CMSConstants.TEACHER_FEEDBACK_GROUP);
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
		log.info("call of reActivateFeedBackGroup method in EvaTeacherFeedBackGroupAction class.");
		EvaTeacherFeedBackGroupForm teacherFeedBackGroupForm = (EvaTeacherFeedBackGroupForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = teacherFeedBackGroupForm.validate(mapping, request);
		HttpSession session = request.getSession();
		try
		{
			if(errors.isEmpty())
			{
				setUserId(request, teacherFeedBackGroupForm);
				int id = ((Integer)session.getAttribute("FeedBack")).intValue();
				boolean isActivated = EvaTeacherFeedBackGroupHandler.getInstance().reActivateFeedBackGroup(teacherFeedBackGroupForm.getName(), teacherFeedBackGroupForm.getUserId(), id);
				if(isActivated)
				{
					ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_REACTIVATE_SUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
					teacherFeedBackGroupForm.reset(mapping, request);
				} else
				{
					ActionMessage message = new ActionMessage(CMSConstants.FEEDBACK_GROUP_REACTIVATE_FAILURE);
					messages.add("messages", message);
					saveMessages(request, messages);
					teacherFeedBackGroupForm.reset(mapping, request);
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
			teacherFeedBackGroupForm.setErrorMessage(msg);
			teacherFeedBackGroupForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		List<EvaTeacherFeedBackGroupTo> studentlist = EvaTeacherFeedBackGroupHandler.getInstance().getTeacherFeedBackEntry();
		teacherFeedBackGroupForm.setFeedBackGroupList(studentlist);
		log.info("end of updateFeedBackGroup method in EvaTeacherFeedBackGroupAction class. ");
		return mapping.findForward(CMSConstants.TEACHER_FEEDBACK_GROUP);
	}

}
