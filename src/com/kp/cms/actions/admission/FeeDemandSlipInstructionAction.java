package com.kp.cms.actions.admission;

import java.util.List;

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
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.FeeDemandSlipInstructionForm;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.admission.FeeDemandSlipInstructionHandler;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admission.DemandSlipInstructionTO;

	public class FeeDemandSlipInstructionAction extends BaseDispatchAction{
		private static Log log = LogFactory.getLog(FeeDemandSlipInstructionAction.class);
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
		public ActionForward initDemandSlipInstruction(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response){
			log.debug("inside initDemandSlipInstruction in Action");
			FeeDemandSlipInstructionForm feSlipInstructionForm = (FeeDemandSlipInstructionForm) form;
			ActionMessages messages = new ActionMessages();
			try{
				feSlipInstructionForm.clear();
				setUserId(request, feSlipInstructionForm);
				assignListToForm(feSlipInstructionForm);
			}catch (BusinessException businessException) {
				String msgKey = super.handleBusinessException(businessException);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add(CMSConstants.MESSAGES, message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				feSlipInstructionForm.setErrorMessage(msg);
				feSlipInstructionForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			
			return mapping.findForward(CMSConstants.DEMAND_SLIP_INST);
			
		}
		
		/**
		 * @param feSlipInstructionForm
		 * @throws Exception
		 */
		public void assignListToForm(FeeDemandSlipInstructionForm feSlipInstructionForm) throws Exception{
			List<CourseTO> courseList = CourseHandler.getInstance().getCourses();
			feSlipInstructionForm.setCourseList(courseList);
			
			List<DemandSlipInstructionTO> deSlipInstructionTOsList = FeeDemandSlipInstructionHandler.getInstance().getDetailsToDisplay();
			feSlipInstructionForm.setDeTosList(deSlipInstructionTOsList);
		}
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward addSlipInstruction(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response) throws Exception{
			log.debug("inside addSlipInstruction in Action");
			FeeDemandSlipInstructionForm feSlipInstructionForm = (FeeDemandSlipInstructionForm) form;
			 ActionErrors errors = feSlipInstructionForm.validate(mapping, request);
			ActionMessages messages = new ActionMessages();
			boolean isAdded;
			try {
				if (errors.isEmpty()) {
					
					isAdded=FeeDemandSlipInstructionHandler.getInstance().addSlipInstruction(feSlipInstructionForm,"Add");
					if(isAdded){
						ActionMessage message = new ActionMessage("knowledgepro.admission.demandSlip.added.success");
						messages.add(CMSConstants.MESSAGES, message);
						saveMessages(request, messages);
						assignListToForm(feSlipInstructionForm);
						feSlipInstructionForm.clear();
					}else{
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.demandSlip.added.failure"));
						saveErrors(request, errors);
					}
				}else{
					saveErrors(request, errors);
				}
			}catch (DuplicateException e1) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.demandSlip.already.exists"));
					saveErrors(request, errors);
			} catch (ReActivateException e1) {
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.demandSlip.reActivate",feSlipInstructionForm.getId()));
				saveErrors(request, errors);
			}catch (Exception e) {
				log.debug("Action :addSlipInstruction exception occured");
				String msg = super.handleApplicationException(e);
				feSlipInstructionForm.setErrorMessage(msg);
				feSlipInstructionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.DEMAND_SLIP_INST);
		}
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward editSlipInstruction(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response) throws Exception{
			log.debug("inside editSlipInstruction in Action");
			FeeDemandSlipInstructionForm feSlipInstructionForm = (FeeDemandSlipInstructionForm) form;
			int id=feSlipInstructionForm.getId();
			FeeDemandSlipInstructionHandler.getInstance().getDetailsToEdit(feSlipInstructionForm,id);
			request.setAttribute("operation", "edit");
			return mapping.findForward(CMSConstants.DEMAND_SLIP_INST);
		}
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward deleteSlipInstruction(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response) throws Exception{
			log.debug("inside deleteSlipInstruction in Action");
			FeeDemandSlipInstructionForm feSlipInstructionForm = (FeeDemandSlipInstructionForm) form;
			ActionErrors errors = new ActionErrors();
			ActionMessages messages = new ActionMessages();
			boolean isDeleted=false;
			try {
				setUserId(request, feSlipInstructionForm);
				String userId = feSlipInstructionForm.getUserId();
				if(feSlipInstructionForm.getId()>0){
					int id=feSlipInstructionForm.getId();
					isDeleted = FeeDemandSlipInstructionHandler.getInstance().deleteSlipInstruction(id,false,userId);
				}
				
				/**
				 * If delete operation is success then add the success message. Else
				 * add the appropriate error message
				 */
				if (isDeleted) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admission.demandSlip.deletesuccess"));
					saveMessages(request, messages);
					assignListToForm(feSlipInstructionForm);
				} else {
					errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.admission.demandSlip.deletefailure"));
					saveErrors(request, errors);
					assignListToForm(feSlipInstructionForm); 
				}
			} catch (Exception e) {
				log.error("error in deleting Exception Details");
				String msgKey = super.handleApplicationException(e);
				feSlipInstructionForm.setErrorMessage(msgKey);
				feSlipInstructionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			log.info("Existing from deleting SlipInstruction details");
			return mapping.findForward(CMSConstants.DEMAND_SLIP_INST);
		}
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward updateSlipInstruction(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response) throws Exception{
			log.debug("inside updateSlipInstruction in Action");
			FeeDemandSlipInstructionForm feSlipInstructionForm = (FeeDemandSlipInstructionForm) form;
			 ActionErrors errors = feSlipInstructionForm.validate(mapping, request);
			ActionMessages messages = new ActionMessages();
			boolean isUpdated;
			try {
				if(isCancelled(request)){
					int id=feSlipInstructionForm.getId();
					FeeDemandSlipInstructionHandler.getInstance().getDetailsToEdit(feSlipInstructionForm,id);
					request.setAttribute("operation", "edit");
					return mapping.findForward(CMSConstants.DEMAND_SLIP_INST);
				}else
				if (errors.isEmpty()) {
					
					isUpdated=FeeDemandSlipInstructionHandler.getInstance().addSlipInstruction(feSlipInstructionForm,"Edit");
					if(isUpdated){
						ActionMessage message = new ActionMessage("knowledgepro.admission.demandSlip.update.success");
						messages.add(CMSConstants.MESSAGES, message);
						saveMessages(request, messages);
						assignListToForm(feSlipInstructionForm); 
						feSlipInstructionForm.clear();
					}else{
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.demandSlip.update.failure"));
						saveErrors(request, errors);
						request.setAttribute("operation", "edit");
					}
				}else{
					saveErrors(request, errors);
					request.setAttribute("operation", "edit");
				}
			}catch (DuplicateException e1) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.demandSlip.already.exists"));
					saveErrors(request, errors);
					request.setAttribute("operation", "edit");
			} catch (ReActivateException e1) {
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.demandSlip.reActivate",feSlipInstructionForm.getId()));
				saveErrors(request, errors);
				request.setAttribute("operation", "edit");
			}catch (Exception e) {
				log.debug("Action updateSlipInstruction exception occured");
				String msg = super.handleApplicationException(e);
				feSlipInstructionForm.setErrorMessage(msg);
				feSlipInstructionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.DEMAND_SLIP_INST);
		}
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward reActivateSlipInstruction(ActionMapping mapping, ActionForm form,	HttpServletRequest request, 
				HttpServletResponse response) throws Exception {

				FeeDemandSlipInstructionForm feSlipInstructionForm = (FeeDemandSlipInstructionForm) form;
				 ActionErrors errors = feSlipInstructionForm.validate(mapping, request);
				ActionMessages messages = new ActionMessages();
				boolean isActivated = false;
				try {
					setUserId(request, feSlipInstructionForm);
					String userId = feSlipInstructionForm.getUserId();
					if (feSlipInstructionForm.getId() >0) {
						int dupid = feSlipInstructionForm.getId();
						isActivated = FeeDemandSlipInstructionHandler.getInstance().deleteSlipInstruction(dupid, true, userId);
					}
				} catch (Exception e) {
						errors.add("error", new ActionError("knowledgepro.admission.demandSlip.reActivatefailure"));
						saveErrors(request, errors);
					}
				if(isActivated) {
					ActionMessage message = new ActionMessage("knowledgepro.admission.demandSlip.reActivatesuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					assignListToForm(feSlipInstructionForm);
					feSlipInstructionForm.clear();
				}
				log.debug("leaving reActivateSlipInstruction");
				return mapping.findForward(CMSConstants.DEMAND_SLIP_INST);
			}
	}
