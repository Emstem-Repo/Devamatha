package com.kp.cms.actions.phd;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.DuplicateException1;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.employee.QualificationLevelForm;
import com.kp.cms.forms.phd.PhdQualificationLevelForm;
import com.kp.cms.handlers.employee.QualificationLevelHandler;
import com.kp.cms.handlers.phd.PhdQualificationLevelHandler;
import com.kp.cms.to.phd.PhdQualificationLevelTo;

public class PhdQualificationLevelAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(PhdQualificationLevelAction.class);
	private static final String QUALIFICATION_ENTRY_OPERATION = "QualificationLevelEntryOperation";
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initQualificationLevel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PhdQualificationLevelForm phdQualificationLevelForm=(PhdQualificationLevelForm)form;
		try{

			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setQualificationListToRequest(request);
			setUserId(request, phdQualificationLevelForm);
			initFields(phdQualificationLevelForm);
		
		} catch (Exception e) {
			log.error("error initEducationMaster...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				phdQualificationLevelForm.setErrorMessage(msg);
				phdQualificationLevelForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.PHD_QUALIFICATION_LEVEL);
	}

	private void initFields(PhdQualificationLevelForm phdQualificationLevelForm) {
		phdQualificationLevelForm.setFixedDisplay(false);
		phdQualificationLevelForm.setName(null);
		phdQualificationLevelForm.setDisplayOrder(null);
		}
	/**
	 * @param request
	 * @throws Exception
	 */
		private void setQualificationListToRequest(HttpServletRequest request) throws Exception {
		List<PhdQualificationLevelTo> qualificationLevelList=PhdQualificationLevelHandler.getInstance().getQualificationList();
		request.setAttribute("QualificationLevelList", qualificationLevelList);
	}
		/** add the phd qualification level details
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("deprecation")
		public ActionForward addQualificationLevel(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			PhdQualificationLevelForm phdQualificationLevelForm=(PhdQualificationLevelForm)form;
			ActionMessages messages =new ActionMessages();
			ActionErrors errors = phdQualificationLevelForm.validate(mapping, request);
			setUserId(request, phdQualificationLevelForm);
			HttpSession session = request.getSession();
			boolean isAdded = false;
			try{
				if(!errors.isEmpty()){
					saveErrors(request, errors);
					setQualificationListToRequest(request);
					return mapping.findForward(CMSConstants.PHD_QUALIFICATION_LEVEL);
				}
				if(phdQualificationLevelForm.getName() ==null || phdQualificationLevelForm.getName().isEmpty()){
					errors.add("error", new ActionError("knowledgepro.employee.qualification.level.required"));
					saveErrors(request, errors);
					setQualificationListToRequest(request);
					initFields(phdQualificationLevelForm);
					return mapping.findForward(CMSConstants.PHD_QUALIFICATION_LEVEL);
				}
				if(phdQualificationLevelForm.getDisplayOrder()== null || phdQualificationLevelForm.getDisplayOrder()==0){
					errors.add("error", new ActionError("knowledgepro.employee.qualification.level.display.order.required"));
					saveErrors(request, errors);
					setQualificationListToRequest(request);
					initFields(phdQualificationLevelForm);
					return mapping.findForward(CMSConstants.PHD_QUALIFICATION_LEVEL);
				}
				isAdded=PhdQualificationLevelHandler.getInstance().addQualificationLevel(phdQualificationLevelForm, "Add");
				setQualificationListToRequest(request);
			}catch (DuplicateException e) {
				errors.add("error", new ActionError("knowledgepro.employee.qualification.level.exists"));
				saveErrors(request, errors);
				setQualificationListToRequest(request);
				return mapping.findForward(CMSConstants.PHD_QUALIFICATION_LEVEL);
			}
			catch (ReActivateException e2) {
				errors.add("error", new ActionError(CMSConstants.QUALIFICATION_LEVEL_REACTIVATE));
				saveErrors(request, errors);
				setQualificationListToRequest(request);
				session.setAttribute("REACTIVATEID", phdQualificationLevelForm.getDuplId());
				session.setAttribute("NAME", phdQualificationLevelForm.getName());
				session.setAttribute("DISPLAYORDER", phdQualificationLevelForm.getDisplayOrder());
				session.setAttribute("FIXEDDISPLAY", phdQualificationLevelForm.isFixedDisplay());
				return mapping.findForward(CMSConstants.PHD_QUALIFICATION_LEVEL);
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
					phdQualificationLevelForm.setErrorMessage(msg);
					phdQualificationLevelForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
			if (isAdded) {
				// success .
				ActionMessage message = new ActionMessage("knowledgepro.employee.qualification.level.addsuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				initFields(phdQualificationLevelForm);
			}
			else
			{
				// failed
				errors.add("error", new ActionError("knowledgepro.employee.qualification.level.addfailure"));
				saveErrors(request, errors);
			}
			log.debug("Leaving addQualificationLevel Action");
			return mapping.findForward(CMSConstants.PHD_QUALIFICATION_LEVEL);
		}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward deleteQualificationLevel(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			PhdQualificationLevelForm phdQualificationLevelForm=(PhdQualificationLevelForm)form;
			log.debug("inside deleteEducation");
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = new ActionErrors();
			setUserId(request, phdQualificationLevelForm);
			boolean isDeleted = false;
			try{
				if(phdQualificationLevelForm.getId() !=0){
					int id=phdQualificationLevelForm.getId();
					isDeleted=PhdQualificationLevelHandler.getInstance().deleteQualificationLevel(id,false,phdQualificationLevelForm);
				}
			}catch (Exception e) {
				log.error("error in deleteEducation...", e);
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					phdQualificationLevelForm.setErrorMessage(msg);
					phdQualificationLevelForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
			setQualificationListToRequest(request);
		if(isDeleted){
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.employee.qualification.level.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(phdQualificationLevelForm);
		
		}else{

			// failure error message.
			errors.add("error", new ActionError("knowledgepro.employee.qualification.level.deletefailure"));
			saveErrors(request, errors);
		}
		log.debug("Leaving deleteQualificationLevel Action");
		return mapping.findForward(CMSConstants.PHD_QUALIFICATION_LEVEL);
		}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward editQualificationLevel(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			PhdQualificationLevelForm phdQualificationLevelForm=(PhdQualificationLevelForm)form;
			log.debug("inside updateQualificationLevel Action");
			ActionMessages messages = new ActionMessages();
			setUserId(request, phdQualificationLevelForm);
			
			try{
				if(phdQualificationLevelForm.getId() !=0){
					int id=phdQualificationLevelForm.getId();
				PhdQualificationLevelTo qualifiactionTo=PhdQualificationLevelHandler.getInstance().editQualificationLevel(id);
				phdQualificationLevelForm.setName(qualifiactionTo.getName());
				phdQualificationLevelForm.setDisplayOrder(qualifiactionTo.getDisplayOrder());
				phdQualificationLevelForm.setOrigName(qualifiactionTo.getName());
				phdQualificationLevelForm.setOrigDisplayOrder(qualifiactionTo.getDisplayOrder());
				if(qualifiactionTo.getFixedDisplay().equalsIgnoreCase("Yes")){
					phdQualificationLevelForm.setFixedDisplay(true);
				}
				if(qualifiactionTo.getFixedDisplay().equalsIgnoreCase("No")){
					phdQualificationLevelForm.setFixedDisplay(false);
				}
				if(qualifiactionTo.getFixedDisplay().equalsIgnoreCase("Yes")){
					phdQualificationLevelForm.setOrigFixedDisplay(true);
				}
				if(qualifiactionTo.getFixedDisplay().equalsIgnoreCase("No")){
					phdQualificationLevelForm.setOrigFixedDisplay(false);
				}
				}
				setQualificationListToRequest(request);
			}catch (Exception e) {
				log.error("error in final submit of education page...", e);
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					phdQualificationLevelForm.setErrorMessage(msg);
					phdQualificationLevelForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
			request.setAttribute(QUALIFICATION_ENTRY_OPERATION,CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.PHD_QUALIFICATION_LEVEL);
			
		}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward updateQualificationLevel(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			PhdQualificationLevelForm phdQualificationLevelForm=(PhdQualificationLevelForm)form;
			log.debug("inside updateQualificationLevel Action");
			ActionMessages messages = new ActionMessages();
			setUserId(request, phdQualificationLevelForm);
			ActionErrors errors = phdQualificationLevelForm.validate(mapping, request);
			boolean isUpdate = false;
			try{
				if (!errors.isEmpty()) {
					saveErrors(request, errors);
					setQualificationListToRequest(request);
					request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.PHD_QUALIFICATION_LEVEL);
				}
				if(phdQualificationLevelForm.getName() ==null || phdQualificationLevelForm.getName().isEmpty()){
					errors.add("error", new ActionError("knowledgepro.employee.qualification.level.required"));
					saveErrors(request, errors);
					setQualificationListToRequest(request);
				//	initFields(qualificationLevelForm);
					request.setAttribute(QUALIFICATION_ENTRY_OPERATION,CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.PHD_QUALIFICATION_LEVEL);
				}
				if(phdQualificationLevelForm.getDisplayOrder()== null || phdQualificationLevelForm.getDisplayOrder()==0){
					errors.add("error", new ActionError("knowledgepro.employee.qualification.level.display.order.required"));
					saveErrors(request, errors);
					setQualificationListToRequest(request);
				//	initFields(qualificationLevelForm);
					request.setAttribute(QUALIFICATION_ENTRY_OPERATION,CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.PHD_QUALIFICATION_LEVEL);
				}
				isUpdate = PhdQualificationLevelHandler.getInstance().updateQualificationLevel(phdQualificationLevelForm, "Edit");
				setQualificationListToRequest(request);
				
			}catch (DuplicateException e1) {
				errors.add("error", new ActionError("knowledgepro.employee.qualification.level.exists"));
				saveErrors(request, errors);
				setQualificationListToRequest(request);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				request.setAttribute(QUALIFICATION_ENTRY_OPERATION,	CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.PHD_QUALIFICATION_LEVEL);
			} 
			catch (ReActivateException e2) {
				errors.add("error", new ActionError(CMSConstants.QUALIFICATION_LEVEL_REACTIVATE));
				saveErrors(request, errors);
				setQualificationListToRequest(request);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.PHD_QUALIFICATION_LEVEL);
			} catch (Exception e) {
				log.error("error in final submit of education page...", e);
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					phdQualificationLevelForm.setErrorMessage(msg);
					phdQualificationLevelForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}if (isUpdate) {
				// success .
				ActionMessage message = new ActionMessage("knowledgepro.employee.qualification.level.updatesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				initFields(phdQualificationLevelForm);
			}
			else
			{
				// failed
				errors.add("error", new ActionError("knowledgepro.employee.qualification.level.updatefailure"));
				saveErrors(request, errors);
			}
			
			request.setAttribute(CMSConstants.OPERATION, "add");
			log.debug("Leaving updateQualificationLevel Action");
			return mapping.findForward(CMSConstants.PHD_QUALIFICATION_LEVEL);
		}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward activateQualificationLevel(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			PhdQualificationLevelForm phdQualificationLevelForm=(PhdQualificationLevelForm)form;
			ActionErrors errors = new ActionErrors();
			ActionMessages messages = new ActionMessages();
			HttpSession session = request.getSession();
			boolean isActivated = false;
			try{
				
				int id=(Integer) session.getAttribute("REACTIVATEID");
				isActivated=PhdQualificationLevelHandler.getInstance().deleteQualificationLevel( id, true, phdQualificationLevelForm);
			
			}catch (DuplicateException e1) {
				errors.add("error", new ActionError("knowledgepro.employee.qualification.level.exists"));
				saveErrors(request, errors);
				setQualificationListToRequest(request);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.PHD_QUALIFICATION_LEVEL);
			} catch (DuplicateException1 e1) {
				errors.add("error", new ActionError("knowledgepro.employee.qualification.level.display.order.exists"));
				saveErrors(request, errors);
				setQualificationListToRequest(request);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				request.setAttribute(QUALIFICATION_ENTRY_OPERATION,CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.PHD_QUALIFICATION_LEVEL);
			}
			catch (Exception e) {
				errors.add("error", new ActionError(CMSConstants.QUALIFICATION_LEVEL_ACTIVATE_FAILURE));
				saveErrors(request, errors);
			}
			setQualificationListToRequest(request);
			if (isActivated) {
				ActionMessage message = new ActionMessage(CMSConstants.QUALIFICATION_LEVEL_ACTIVATE_SUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				initFields(phdQualificationLevelForm);
			}
			log.debug("leaving activateEducation");
			return mapping.findForward(CMSConstants.PHD_QUALIFICATION_LEVEL);
			
		}

}
