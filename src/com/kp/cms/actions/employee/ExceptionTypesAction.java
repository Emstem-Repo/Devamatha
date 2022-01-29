package com.kp.cms.actions.employee;

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
import com.kp.cms.forms.admin.DocumentExamEntryForm;
import com.kp.cms.forms.employee.ExceptionTypesForm;
import com.kp.cms.handlers.admin.DocumentExamEntryHandler;
import com.kp.cms.handlers.employee.ExceptionTypesHandler;

@SuppressWarnings("deprecation")
public class ExceptionTypesAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ExceptionTypesAction.class);

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExceptionTypes(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExceptionTypesForm exceptionTypesForm = (ExceptionTypesForm) form;
		exceptionTypesForm.resetFields();
		setRequestedDataToForm(exceptionTypesForm);
		return mapping.findForward(CMSConstants.EXCEPTION_TYPES);
	}

	/**
	 * @param exceptionTypesForm
	 */
	private void setRequestedDataToForm(ExceptionTypesForm exceptionTypesForm) throws Exception {
		exceptionTypesForm.setListExceptionType(ExceptionTypesHandler.getInstance().getExceptionTypes());
		
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addExceptionTypes(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("inside addException Types Action");
		ExceptionTypesForm objForm = (ExceptionTypesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = objForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.EXCEPTION_TYPES);
			}
			setUserId(request, objForm);
			isAdded = ExceptionTypesHandler.getInstance().addExceptionTypes(objForm);
		} catch (DuplicateException e) {
			errors.add(CMSConstants.ERROR, new ActionError(
					"knowledgepro.admin.alreadyExist", "Exception Type"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.EXCEPTION_TYPES);
		}catch (ReActivateException e) {
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.exceptionTypes.reActivate"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.EXCEPTION_TYPES);
		}  catch (Exception e) {
			log.error("error in final submit of education page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			objForm.resetFields();
			setRequestedDataToForm(objForm);
			ActionMessage message = new ActionMessage(
					"knowledgepro.employee.exceptionTypes.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);

		} else {
			// failed
			errors.add("error", new ActionError(
					"knowledgepro.employee.exceptionTypes.addfailure"));
			saveErrors(request, errors);
		}
		log.debug("Leaving addException Types Action");
		return mapping.findForward(CMSConstants.EXCEPTION_TYPES);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteExceptionTypes(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("inside deleteExceptionTypes Types Action");
		ExceptionTypesForm objForm = (ExceptionTypesForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isAdded = false;
		try {
			setUserId(request, objForm);
			isAdded = ExceptionTypesHandler.getInstance().deleteExceptionTypes(objForm);
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			objForm.resetFields();
			setRequestedDataToForm(objForm);
			ActionMessage message = new ActionMessage(
					"knowledgepro.employee.exceptionTypes.deleteuccess");
			messages.add("messages", message);
			saveMessages(request, messages);

		} else {
			// failed
			errors.add("error", new ActionError(
					"knowledgepro.employee.exceptionTypes.deletefailure"));
			saveErrors(request, errors);
		}
		log.debug("inside deleteExceptionTypes Types Action");
		return mapping.findForward(CMSConstants.EXCEPTION_TYPES);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editExceptionTypes(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("inside deleteExceptionTypes Types Action");
		ExceptionTypesForm objForm = (ExceptionTypesForm) form;
		objForm = ExceptionTypesHandler.getInstance().getUpdatableForm(objForm);
		request.setAttribute("ExceptionTypesOperation", "edit");
		log.debug("inside deleteExceptionTypes Types Action");
		return mapping.findForward(CMSConstants.EXCEPTION_TYPES);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateExceptionTypes(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("inside addException Types Action");
		ExceptionTypesForm objForm=(ExceptionTypesForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = objForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if(isCancelled(request)){
				objForm = ExceptionTypesHandler.getInstance().getUpdatableForm(objForm);
				request.setAttribute("ExceptionTypesOperation", "edit");
				return mapping.findForward(CMSConstants.EXCEPTION_TYPES);
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.EXCEPTION_TYPES);
			}
			setUserId(request, objForm);
			isAdded = ExceptionTypesHandler.getInstance().updateExceptionTypes(
					objForm);
			if (isAdded) {
				// success .
				objForm.resetFields();
				ActionMessage message = new ActionMessage(
						"knowledgepro.employee.exceptionTypes.updatesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);

			} else {
				// failed
				errors.add("error", new ActionError(
						"knowledgepro.employee.exceptionTypes.updatefailure"));
				saveErrors(request, errors);
			}
			setRequestedDataToForm(objForm);
		} catch (DuplicateException e) {
			errors.add(CMSConstants.ERROR, new ActionError(
					"knowledgepro.admin.alreadyExist", "Exception Type"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.EXCEPTION_TYPES);
		}catch (ReActivateException e) {
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.exceptionTypes.reActivate"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.EXCEPTION_TYPES);
		}		
		catch (Exception e) {
			log.error("error in final submit of education page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.debug("Leaving addException Types Action");
		return mapping.findForward(CMSConstants.EXCEPTION_TYPES);
	}
	/**
	 * Used to reactivate roomtype
	 */
	public ActionForward reactivateExceptionType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into reactivateRoomType of RoomType Action");
		ExceptionTypesForm objForm = (ExceptionTypesForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, objForm);
			boolean isReactivate;
			isReactivate = ExceptionTypesHandler.getInstance().reactivateExceptionType(objForm.getOldId(),objForm.getUserId());
			if(isReactivate){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.employee.exceptionTypes.reActivatesuccess"));
				objForm.resetFields();
				setRequestedDataToForm(objForm);
				saveMessages(request, messages);
			}
			else{
				setRequestedDataToForm(objForm);
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.exceptionTypes.reActivatefailure"));
				addErrors(request, errors);
			}
			
		} catch (Exception e) {
			log.error("Error occured in reactivateRoomType of RoomTypeAction", e);
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into reactivateRoomType of RoomType Action");
		return mapping.findForward(CMSConstants.EXCEPTION_TYPES);
	}
}
